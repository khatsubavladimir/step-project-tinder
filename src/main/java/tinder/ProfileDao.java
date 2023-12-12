package tinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProfileDao implements DAO<Profile> {
    private List<Profile> profiles = new ArrayList<>();

    private static final Profile[] initialProfiles = {
            new Profile("Sheldon", "https://www.vokrug.tv/pic/person/a/7/7/e/a77e7c2fad04c812869cb7fa9bdfd01c.jpeg"),
            new Profile("Emi", "https://prm.ua/wp-content/uploads/2018/03/Emi-Fara-Fauler.jpeg"),
            new Profile("Penni", "https://today.ua/wp-content/uploads/2022/09/2_EH_CHP_200220The-Big-Bang-Theory_5680JPG.jpg"),
            new Profile("Howard", "https://u.movielib.ru/charpics/1001583061/r/oc6b21nl/charpics-r.jpg"),
            new Profile("Leonard", "https://u.movielib.ru/charpics/1001583059/r/i91fpwso/charpics-r.jpg"),
            new Profile("Rajesh", "https://img.gazeta.ru/files3/425/10212425/upload-FtYAvibrsVjnr3ulP8iA-pic905-895x505-83376.jpg")
    };

    public ProfileDao(){
        profiles.addAll(Arrays.asList(initialProfiles));
    }

    @Override
    public List<Profile> getAll() {
        return profiles;
    }

    @Override
    public void add(Profile item) {
        profiles.add(item);
    }

    @Override
    public Profile getByID(int id) {
        return null;
    }

    public Profile getNextProfile() {
        if (profiles.isEmpty()) {
            profiles.addAll(Arrays.asList(initialProfiles));
        }
        return profiles.remove(0);
    }
}
