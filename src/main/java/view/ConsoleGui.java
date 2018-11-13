package view;

import model.Message;
import model.Model;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class ConsoleGui {

    private int lastMessageIndex = 0;

    public void printNewMessages() {
        List<Message> messages = Model.getInstance().getMessages();
        for (int i = lastMessageIndex + 1; i < messages.size(); ++i) {
            Message msg = messages.get(i);
            if (msg.getId() > lastMessageIndex) {
                System.out.println(msg.getAuthorName() + ": " + msg.getMessage());
                lastMessageIndex = i;
            }
        }
    }

    public void printUsers() {
        System.out.println("Online users:");
        List<User> users = new ArrayList<>(Model.getInstance().getAllUsers());
        for (User user : users) {
            if (user.isOnline()) {
                System.out.println("  " + user.getName());
            }
        }
    }
}
