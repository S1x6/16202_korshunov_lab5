package model.json;

public class UserObject {
    private int id;
    private String username;
    private boolean isOnline;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public UserObject(int id, String username, boolean isOnline) {
        this.id = id;
        this.username = username;
        this.isOnline = isOnline;
    }
}
