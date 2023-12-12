package tinder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageSqlService {
    MessageSqlDAO dao;

    public MessageSqlService(MessageSqlDAO dao) {
        this.dao = dao;
    }

    public List<Message> getMessages(int from, int to) { //видає всі повідомлення які юзер "from" написав юзеру "to"

        PreparedStatement st = null;
        try {
            st = dao.conn.prepareStatement("SELECT * from messages where from_id = ? and to_id = ?;");
            st.setInt(1, from);
            st.setInt(2, to);
            ResultSet rs = st.executeQuery();

            ArrayList<Message> list = new ArrayList<>();
            dao.resultSetToIterable(rs).forEach(list::add);

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Message message){
        dao.add(message);
    }


    public Message getByID(int id) {
        return dao.getByID(id);
    }
}
