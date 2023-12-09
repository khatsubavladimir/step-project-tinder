package tinder;

import java.util.List;

public class UserSqlService {
    UserSqlDAO dao;

    public UserSqlService(UserSqlDAO dao) {
        this.dao = dao;
    }

    public void addLikedProfile(Profile profile) {
        dao.addProfile(profile);
        System.out.println("Added liked profile: " + profile.getName());
    }

    public List<Profile> getLikedProfiles() {
        return dao.getAllProfiles();
    }
}
