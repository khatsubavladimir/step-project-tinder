package tinder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageSqlDAO implements DAO<Message>{

    public Connection conn;
    public MessageSqlDAO (Connection conn) {
        this.conn = conn;
    }

    Iterable<Message> resultSetToIterable(ResultSet rs) {
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
            public Message next() {
                try {
                    return new Message(
                            rs.getInt("mg_id"),
                            rs.getInt("from_id"),
                            rs.getInt("to_id"),
                            rs.getString("message")
                    );
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Override
    public List<Message> getAll() {
        return null;
    }

    @Override
    public void add(Message message){
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO messages (from_id, to_id, message) VALUES (?,?,?);");
            st.setInt(1, message.getFrom());
            st.setInt(2, message.getTo());
            st.setString(3, message.getMessage());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message getByID(int id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("SELECT * from messages where mg_id = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            ArrayList<Message> list = new ArrayList<>();
            resultSetToIterable(rs).forEach(list::add);

            return list.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
