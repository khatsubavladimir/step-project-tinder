package tinder;

import java.util.ArrayList;

public class User extends Profile{
    private int id;
    private String password;
    private ArrayList<User> liked;

    public User(String name, String photoUrl, int id, String password) {
        super(name, photoUrl);
        this.id = id;
        this.password = password;
        this.liked = new ArrayList<User>();
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
