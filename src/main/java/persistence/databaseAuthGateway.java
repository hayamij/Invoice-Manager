package persistence;

public interface databaseAuthGateway {
    String getServer();
    String getDatabase();
    String getUsername();
    String getPassword();
}
