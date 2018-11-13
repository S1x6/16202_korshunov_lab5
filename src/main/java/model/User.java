package model;

public class User {

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public User(int id, String name, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.isOnline = isOnline;
    }

    private String token;
    private int id;
    private String name;
    private boolean isOnline;


    public User() {
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
