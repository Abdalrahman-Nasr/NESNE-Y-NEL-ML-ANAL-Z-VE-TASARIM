package nesne.proje;

import nesne.proje.pojo.User;

import java.sql.SQLException;

public interface IDAO {

    int addUser(User user) throws SQLException;

    User getUser(String username) throws SQLException;
}
