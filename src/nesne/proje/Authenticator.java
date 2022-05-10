package nesne.proje;

import nesne.proje.pojo.User;

import java.util.Objects;

public class Authenticator implements IAuthenticator{
    IDAO dao;

    public Authenticator(IDAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean isRegistered(String username, String passphrase) {
        try {
            User user = dao.getUser(username);
            return Objects.equals(user.getUsername(), username) && Objects.equals(user.getPassword(), passphrase);
        }catch (Exception e) {
        }
        return false;
    }
}
