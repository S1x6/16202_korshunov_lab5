package client;

import model.Message;
import model.Model;
import model.User;
import model.json.JSONParser;
import model.json.LoginInfoObject;
import model.json.MessageObject;
import model.json.UserObject;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class Client implements Runnable {

    //private Timer messageGetterTimer;
    WebSocket ws;
    private Timer usersListenerTimer;
    private ApiHandler apiHandler;
    private String name;

    public Client(String baseUrl) {
        //this.messageGetterTimer = new Timer();
        this.usersListenerTimer = new Timer();
        this.apiHandler = new ApiHandler(baseUrl);
    }

    @Override
    public void run() {
        authenticate();
        getAllMessages();
        openMessagesChannel();
        //messageGetterTimer.schedule(new MessageListener(), 0, 1000);
        usersListenerTimer.schedule(new UserListener(), 100, 1000);
        Scanner scanner = new Scanner(System.in);
        Response response = null;
        while (true) {
            String text = scanner.nextLine();
            switch (text) {
                case "/logout":
                    try {
                        response = apiHandler.sendLogout();
                        System.exit(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (response != null && response.body() != null) {
                            response.body().close();
                        }
                    }
                    break;
                case "/users":
                    Model.getInstance().getGui().printUsers();
                    break;
                default:
                    ws.send(JSONParser.encodeMessage(text, name));
                    break;
            }
        }


    }

    private void openMessagesChannel() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://localhost:8081/messages").build();
        MessagesWebSocketListener listener = new MessagesWebSocketListener();
        ws = client.newWebSocket(request, listener);
        //ws.send("text");
        //client.dispatcher().executorService().shutdown();
    }

    private void getAllMessages() {
        Response response = null;
        //while (true) {
        try {
            response = apiHandler.getMessages(0, 100);
            if (response.body() != null) {
                String body = response.body().string();
                if (response.code() == 403) return;
                List<MessageObject> messages = JSONParser.decodeMessages(body).getMessages();
                if (messages.size() > 0) {
                    Model.getInstance().getMessages().addAll(messages.stream()
                            .map(messageObject -> new Message(
                                    messageObject.getText(),
                                    messageObject.getAuthor(),
                                    messageObject.getAuthorName(),
                                    messageObject.getId()
                            )).collect(Collectors.toList()));
                    Model.getInstance().getGui().printNewMessages();
                    response.body().close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private void authenticate() {
        Scanner scanner = new Scanner(System.in);
        boolean loginSuccess = false;
        Response response = null;
        while (!loginSuccess) {
            try {
                System.out.println("Login as");
                name = scanner.nextLine();
                response = apiHandler.sendLogin(name);
                if (!response.headers("WWW-Authenticate").isEmpty() &&
                        response.headers("WWW-Authenticate").get(0).equals("Token realm='Username is already in use'")) {
                    System.out.println("Such user already exists");
                    continue;
                }
                if (response.body() != null) {
                    String resBody = response.body().string();
                    LoginInfoObject loginInfoObject = JSONParser.decodeLoginInfo(resBody);
                    Model.getInstance().getUser().setId(loginInfoObject.getId());
                    Model.getInstance().getUser().setToken(loginInfoObject.getToken());
                    Model.getInstance().getUser().setName(loginInfoObject.getUsername());
                    loginSuccess = true;
                    response.body().close();
                    System.out.println("Successfully logged in");
                    System.out.println("Welcome to chat!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }
    }

   /* private class MessageListener extends TimerTask {

        private int lastMessageId = 0;

        @Override
        public void run() {
            Response response = null;
            //while (true) {
                try {
                    response = apiHandler.getMessages(lastMessageId, 100);
                    if (response.body() != null) {
                        String body = response.body().string();
                        if (response.code() == 403) return;
                        List<MessageObject> messages = JSONParser.decodeMessages(body).getMessages();
                        if (messages.size() > 0) {
                            Model.getInstance().getMessages().addAll(messages.stream()
                                    .map(messageObject -> new Message(
                                            messageObject.getText(),
                                            messageObject.getAuthor(),
                                            messageObject.getAuthorName(),
                                            messageObject.getId()
                                    )).collect(Collectors.toList()));
                            lastMessageId += messages.size();
                            Model.getInstance().getGui().printNewMessages();
                            response.body().close();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (response != null) {
                        response.close();
                    }
                }
            //}
        }
    }
    */

    private class UserListener extends TimerTask {

        @Override
        public void run() {
            Response response = null;
            //while (true) {
            try {
                response = apiHandler.getUsers();
                if (response.body() != null) {
                    String body = response.body().string();
                    if (response.code() == 403) return; // TODO: check authorization
                    List<UserObject> users = JSONParser.decodeUsers(body).getUsers();
                    if (users.size() > 0) {
                        Model.getInstance().setAllUsers(users.stream()
                                .map(userObject -> new User(
                                        userObject.getId(),
                                        userObject.getUsername(),
                                        userObject.isOnline()
                                )).collect(Collectors.toList()));
                        response.body().close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    response.close();
                }
            }
            //}
        }
    }

    private final class MessagesWebSocketListener extends WebSocketListener {

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            MessageObject messageObject = JSONParser.decodeMessage(text);
            Message message = new Message(
                    messageObject.getText(),
                    messageObject.getAuthor(),
                    messageObject.getAuthorName(),
                    messageObject.getId());
            Model.getInstance().getMessages().add(message);
            Model.getInstance().getGui().printNewMessages();
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            System.out.println("close");
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            System.out.println("Error : " + t.getMessage());
        }
    }
}
