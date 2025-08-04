package persistence;

public class databaseKey implements databaseAuthGateway {

    

    private String server = "phuongtuan";
    private String database = "invoice";
    private String username = "fuongtuan";
    private String password = "toilabanhmochi";

    public databaseKey() {

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
