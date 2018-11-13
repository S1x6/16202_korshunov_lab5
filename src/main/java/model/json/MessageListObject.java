package model.json;

import java.util.List;

public class MessageListObject {

    private List<MessageObject> messages;

    public MessageListObject(List<MessageObject> messages) {
        this.messages = messages;
    }

    public List<MessageObject> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageObject> messages) {
        this.messages = messages;
    }
}
