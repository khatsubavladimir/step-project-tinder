package tinder;

import java.util.ArrayList;

public class User {
    private String name;
    private String photoUrl;
    private int id;
    private String password;
    private ArrayList<User> liked;

    public User(String name, String photoUrl, String password) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.password = password;
        this.liked = new ArrayList<User>();
    }

    public User(String name, String photoUrl, int id, String password) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.id = id;
        this.password = password;
        this.liked = new ArrayList<User>();
    }
    public Profile toProfile(){
        return new Profile(this.name, this.photoUrl);
    }


    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<User> getLiked() {
        return liked;
    }
}
