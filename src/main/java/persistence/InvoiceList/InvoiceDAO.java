package persistence.InvoiceList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import persistence.DatabaseAuthGateway;
import persistence.DatabaseKey;


public class InvoiceDAO implements InvoiceDAOGateway {
    private Connection conn;
    private DatabaseAuthGateway databaseAuth;

    public InvoiceDAO() {
        // Tự động tạo databaseAuthGateway và connect trong constructor
        this.databaseAuth = new DatabaseKey();

        try {
            //load driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver loaded successfully!");
            String url = "jdbc:sqlserver://" + databaseAuth.getServer() + 
                        ";databaseName=" + databaseAuth.getDatabase() + 
                        ";trustServerCertificate=true;";
            System.out.println("Attempting to connect to: " + url);
            conn = DriverManager.getConnection(url, 
                                             databaseAuth.getUsername(), 
                                             databaseAuth.getPassword());
            System.out.println("Connected successfully to database!");
        } catch (ClassNotFoundException e) {
            System.err.println("SQL Server Driver not found!" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database connection failed!" + e.getMessage());
        }
    }
	
	public List<InvoiceDTO> getAll() {
        List<InvoiceDTO> invoices = new ArrayList<>();
        String sql = "SELECT * FROM invoices";
        // ✅ Sử dụng try-with-resources để tự động đóng resources
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                InvoiceDTO invoice = new InvoiceDTO();
                invoice.id = resultSet.getString("id");
                invoice.date = resultSet.getTimestamp("date");
                invoice.customer = resultSet.getString("customer");
                invoice.room_id = resultSet.getString("room_id");
                invoice.unitPrice = resultSet.getDouble("unitPrice");
                invoice.hour = resultSet.getInt("hour");
                invoice.day = resultSet.getInt("day");
                invoice.type = resultSet.getString("type");
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving invoices: " + e.getMessage());
            e.printStackTrace();
        }
        return invoices;
    }
}