package model;

public class Message {
    private String message;
    private int author;
    private int id;
    private String authorName;

    public Message(String message, int author, String authorName, int id) {
        this.message = message;
        this.author = author;
        this.authorName = authorName;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public int getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }
}
