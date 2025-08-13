package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AddInvoiceDAO implements AddInvoiceDAOGateway {
    private Connection conn;
    private DatabaseAuthGateway databaseAuth;

    public AddInvoiceDAO() {
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
	public boolean insertInvoice(InvoiceDTO invoiceDTO) {
        String sql = "INSERT INTO invoices (date, customer, room_id, unitPrice, hour, day, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, new java.sql.Timestamp(invoiceDTO.date.getTime()));
            stmt.setString(2, invoiceDTO.customer);
            stmt.setString(3, invoiceDTO.room_id);
            stmt.setDouble(4, invoiceDTO.unitPrice);
            stmt.setInt(5, invoiceDTO.hour);
            stmt.setInt(6, invoiceDTO.day);
            stmt.setString(7, invoiceDTO.type);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting invoice: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
