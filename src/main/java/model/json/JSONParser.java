package model.json;

import com.google.gson.Gson;

public class JSONParser {

    private static volatile Gson gson;

    static {
        gson = new Gson();
    }

    public synchronized static String encodeLogin(String username) {
        return gson.toJson(new AuthInfo(username));
    }

    public synchronized static LoginInfoObject decodeLoginInfo(String json) {
        return gson.fromJson(json, LoginInfoObject.class);
    }

    public synchronized static String encodeMessage(String message, String name) {
        return gson.toJson(new MessageSendObject(message, name), MessageSendObject.class);
    }

    public synchronized static MessageListObject decodeMessages(String json) {
        return gson.fromJson(json, MessageListObject.class);
    }

    public synchronized static UserListObject decodeUsers(String json) {
        return gson.fromJson(json, UserListObject.class);
    }

    public synchronized static MessageObject decodeMessage(String text) {
        return gson.fromJson(text, MessageObject.class);
    }
}
