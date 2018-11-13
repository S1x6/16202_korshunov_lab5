package model.json;

public class MessageSendObject {
    private String message;

    public MessageSendObject(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
