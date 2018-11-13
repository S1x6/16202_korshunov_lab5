package model.json;

public class AuthInfo {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public AuthInfo(String username) {
        this.username = username;
    }
}
