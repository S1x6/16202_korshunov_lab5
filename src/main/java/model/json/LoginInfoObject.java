package model.json;

public class LoginInfoObject {
    private int id;
    private String username;
    private Boolean online;
    private String token;

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

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginInfoObject(int id, String username, Boolean online, String token) {
        this.id = id;
        this.username = username;
        this.online = online;
        this.token = token;
    }
}
