package persistence;

public class DatabaseKey implements DatabaseAuthGateway {
    private final String server;
    private final String database;
    private final String username;
    private final String password;

    public DatabaseKey() {
        this.server = "fuongtwan";
        this.database = "invoice";
        this.username = "phuongtuan";
        this.password = "toilabanhmochi";
    }

    public DatabaseKey(String server, String database, String username, String password) {
        this.server = server;
        this.database = database;
        this.username = username;
        this.password = password;
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
