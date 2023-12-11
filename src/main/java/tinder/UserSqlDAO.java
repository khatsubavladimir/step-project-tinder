package tinder;

import java.sql.*;
import java.util.*;

public class UserSqlDAO implements DAO<User> {
    public Connection conn;
    public int currentProfileID;
    public UserSqlDAO(Connection conn) {
        this.conn = conn;
        this.currentProfileID = 0;
    }

    Iterable<User> resultSetToIterable(ResultSet rs) {
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
                            rs.getString("username"),
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
    public void add(User user){
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO users (username, password_, photoUrl) VALUES (?,?,?);");
            st.setString(1, user.getName());
            st.setString(2, user.getPassword());
            st.setString(3, user.getPhotoUrl());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByID(int id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("SELECT * from users where user_id = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            ArrayList<User> list = new ArrayList<>();
            resultSetToIterable(rs).forEach(x -> list.add(x));

            return list.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("SELECT * from users;");
            ResultSet rs = st.executeQuery();

            ArrayList<User> list = new ArrayList<>();
            resultSetToIterable(rs).forEach(x -> list.add(x));

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
