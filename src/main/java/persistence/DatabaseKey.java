package persistence;

public class DatabaseKey implements DatabaseAuthGateway {
    private final String server;
    private final String database;
    private final String username;
    private final String password;

    public DatabaseKey() {
        this.server = "localhost:1433";
        this.database = "invoice";
        this.username = "invoice_app";
        this.password = "Vietnam@123";
    }

    @Override
    public String getServer() { return server; }
    @Override
    public String getDatabase() { return database; }
    @Override
    public String getUsername() { return username; }
    @Override
    public String getPassword() { return password; }
}
