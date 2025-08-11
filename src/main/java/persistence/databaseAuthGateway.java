package persistence;

public interface DatabaseAuthGateway {
    String getServer();
    String getDatabase();
    String getUsername();
    String getPassword();
}
