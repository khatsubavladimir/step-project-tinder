package tinder;

public class Message {
    private int id;
    private int from;
    private int to;
    private String message;

    public Message(int id, int from, int to, String message) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
    }
    public int getId() {
        return id;
    }
    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }
}
