package nesne.proje;

import nesne.proje.pojo.User;

import java.sql.*;

public class DAO implements IDAO{

    private final Connection con;

    public DAO() throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:db");
    }

    @Override
    public int addUser(User user) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO users (name, password) VALUES(?, ?);");
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        return stmt.executeUpdate();
    }

    @Override
    public User getUser(String username) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT id, password FROM users WHERE name=?");
        stmt.setString(1, username);
        ResultSet set = stmt.executeQuery();
        return new User(username, set.getString("password"));
    }
}
