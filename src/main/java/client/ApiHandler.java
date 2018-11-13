package client;

import model.Model;
import model.json.JSONParser;
import okhttp3.*;

import java.io.IOException;

public class ApiHandler {

    private OkHttpClient okhttp;
    private String baseUrl;

    public ApiHandler(String baseUrl) {
        this.baseUrl = baseUrl;
        okhttp = new OkHttpClient();
    }

    public Response sendLogin(String username) throws IOException {
        String body = JSONParser.encodeLogin(username);
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), body);
        Request request = new Request.Builder()
                .url("http://" + baseUrl + "/login")
                .method("POST", requestBody)
                .build();
        return okhttp.newCall(request).execute();
    }

    public Response getMessages(int offset, int count) throws IOException {
        Request request = new Request.Builder()
                .url("http://" + baseUrl + "/messages?offset=" + offset
                        + "&count=" + count)
                .header("Authorization", Model.getInstance().getUser().getToken())
                .method("GET", null)
                .build();
        return okhttp.newCall(request).execute();
    }

    public void sendMessage(String message) {
        RequestBody body = RequestBody.create(MediaType.get("application/json"), JSONParser.encodeMessage(message));
        Request request = new Request.Builder()
                .url("http://" + baseUrl + "/messages")
                .header("Authorization", Model.getInstance().getUser().getToken())
                .method("POST", body)
                .build();
        okhttp.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    response.body().close();
                }
            }
        });
    }

    public Response sendLogout() throws IOException {
        RequestBody body = RequestBody.create(MediaType.get("application/json"), "");
        Request request = new Request.Builder()
                .url("http://" + baseUrl + "/logout")
                .header("Authorization", Model.getInstance().getUser().getToken())
                .method("POST", body)
                .build();
        return okhttp.newCall(request).execute();
    }


    public Response getUsers() throws IOException {
        Request request = new Request.Builder()
                .url("http://" + baseUrl + "/users")
                .header("Authorization", Model.getInstance().getUser().getToken())
                .method("GET", null)
                .build();
        return okhttp.newCall(request).execute();
    }
}
