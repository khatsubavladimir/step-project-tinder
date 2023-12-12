package tinder;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSqlService {
    UserSqlDAO dao;

    public UserSqlService(UserSqlDAO dao) {
        this.dao = dao;
    }
    public List<Profile> getAllProfiles() {
        PreparedStatement st = null;
        try {
            st = dao.conn.prepareStatement("SELECT * from users;");
            ResultSet rs = st.executeQuery();

            ArrayList<Profile> list = new ArrayList<>();
            dao.resultSetToIterable(rs).forEach(x -> list.add(new Profile(x.getName(), x.getPhotoUrl())));

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addLike(int user_id, int profile_id) {
        PreparedStatement st = null;
        try {
            st = dao.conn.prepareStatement("INSERT INTO liked (user_id, pf_id) VALUES (?,?);");
            st.setInt(1, user_id);
            st.setInt(2, profile_id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Profile> getLikedProfiles(int id) { //виводить профілі які перний юзер (id) лайнкув
        PreparedStatement st = null;
        try {
            st = dao.conn.prepareStatement("SELECT username, photoUrl, users.user_id, password_ from users join liked on users.user_id = liked.pf_id where liked.user_id = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            ArrayList<Profile> list = new ArrayList<>();
            dao.resultSetToIterable(rs).forEach(x -> list.add(x.toProfile()));

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Profile getNextProfile() {
        //щоб дізнатись кількість елементів в таблиці доводиться доставати її усю, це погано
        if (dao.currentProfileID < dao.getAll().toArray().length) dao.currentProfileID += 1;
        else dao.currentProfileID = 1;

        PreparedStatement st = null;
        try {
            st = dao.conn.prepareStatement("SELECT * from users where user_id = ?;");
            st.setInt(1, dao.currentProfileID);
            ResultSet rs = st.executeQuery();

            ArrayList<User> list = new ArrayList<>();
            dao.resultSetToIterable(rs).forEach(list::add);

            return new Profile(list.get(0).getName(), list.get(0).getPhotoUrl());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void hardRestart(String url, String username, String password) {  //повністю чистить БД і записує туди наново дані за замовчуванням
            FluentConfiguration config = new FluentConfiguration().dataSource(
                    url,
                    username,
                    password
            );
            Flyway flyway = new Flyway(config);
            flyway.clean();
            flyway.migrate();
        }

    public void add(User user){
        dao.add(user);
    }

    public User getByID(int id) {
        return dao.getByID(id);
    }

    public List<User> getAll() {
        return dao.getAll();
    }
}
