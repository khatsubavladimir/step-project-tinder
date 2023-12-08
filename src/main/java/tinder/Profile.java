package tinder;

public class Profile {
    private String name;
    private String photoUrl;

    public Profile(String name, String photoUrl) {
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}

