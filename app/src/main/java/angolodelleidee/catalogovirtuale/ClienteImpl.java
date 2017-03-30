package angolodelleidee.catalogovirtuale;

/**
 * Created by diego on 18/03/2017.
 */

public class ClienteImpl implements Cliente {
    private final String email;
    private final String password;

    public ClienteImpl(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
