package tinder;

import java.sql.*;
import java.util.*;

public class UserSqlDAO implements DAO<Profile>{
    public Connection conn;
    public int currentProfileID;
    public UserSqlDAO(Connection conn) {
        this.conn = conn;
        this.currentProfileID = 0;
    }

    private Iterable<User> resultSetToIterable(ResultSet rs) {
        return () -> new Iterator<>() {
            @Override
            public boolean hasNext() {
                try {
                    return rs.next();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public User next() {
                try {
                    return new User(
                            rs.getString("name"),
                            rs.getString("photoUrl"),
                            rs.getInt("user_id"),
                            rs.getString("password_")
                    );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }


    @Override
    public List<Profile> getAllProfiles() {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("SELECT * from users;");
            ResultSet rs = st.executeQuery();

            ArrayList<Profile> list = new ArrayList<>();
            resultSetToIterable(rs).forEach(x -> list.add(new Profile(x.getName(), x.getPhotoUrl())));

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addProfile(Profile item){
        // профіль це частина юзера - він не зберігається окремо в БД
    }

    @Override
    public Profile getNextProfile() {
        //щоб дізнатись кількість елементів в таблиці доводиться доставати її усю, це погано
        if (currentProfileID < getAllProfiles().toArray().length) currentProfileID += 1;
        else currentProfileID = 1;

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("SELECT * from users where id = ?;");
            st.setInt(1, currentProfileID);
            ResultSet rs = st.executeQuery();

            ArrayList<User> list = new ArrayList<>();
            resultSetToIterable(rs).forEach(list::add);

            return new Profile(list.get(0).getName(), list.get(0).getPhotoUrl());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(User user){
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO users (name, password_, photoUrl) VALUES (?,?,?);");
            st.setString(1, user.getName());
            st.setString(2, user.getPassword());
            st.setString(3, user.getPhotoUrl());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
