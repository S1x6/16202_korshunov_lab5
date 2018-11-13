package model;

import view.ConsoleGui;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static volatile Model instance;

    private List<Message> messages;
    private User user;
    private List<User> allUsers;
    private ConsoleGui gui;

    private Model() {
        messages = new ArrayList<>();
        user = new User();
        allUsers = new ArrayList<>();
    }

    public static Model getInstance() {
        if (instance == null) {
            synchronized (Model.class) {
                if (instance == null) {
                    instance = new Model();
                }
            }
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setAllUsers(List<User> users) {
        this.allUsers.clear();
        this.allUsers.addAll(users);
    }

    public ConsoleGui getGui() {
        return gui;
    }

    public void setGui(ConsoleGui gui) {
        this.gui = gui;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }
}
