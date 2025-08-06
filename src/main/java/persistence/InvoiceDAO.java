package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class InvoiceDAO implements InvoiceDAOGateway {
    private Connection conn;

    public InvoiceDAO(databaseAuthGateway databaseAuthGateway) {
        try {
			//load driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("Driver loaded successfully!");

			String url = "jdbc:sqlserver://" + databaseAuthGateway.getServer() + ";databaseName=" + databaseAuthGateway.getDatabase() + ";trustServerCertificate=true;";

			System.out.println("Attempting to connect to: " + url);
			conn = DriverManager.getConnection(url, databaseAuthGateway.getUsername(), databaseAuthGateway.getPassword());
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
                invoice.setId(resultSet.getString("id"));
                invoice.setDate(resultSet.getTimestamp("date"));
                invoice.setCustomer(resultSet.getString("customer"));
                invoice.setRoom_id(resultSet.getString("room_id"));
                invoice.setUnitPrice(resultSet.getDouble("unitPrice"));
                invoice.setHour(resultSet.getInt("hour"));
                invoice.setDay(resultSet.getInt("day"));
                invoice.setType(resultSet.getString("type"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving invoices: " + e.getMessage());
            e.printStackTrace();
        }
        
        return invoices;
    }
    
    @Override
    public boolean add(InvoiceDTO invoice) {
        // TODO: Implement add functionality
        // This method should insert a new invoice into the database
        try {
            // Implementation will be added later
            System.out.println("Add invoice method called - not implemented yet");
            return false;
        } catch (Exception e) {
            System.err.println("Error adding invoice: " + e.getMessage());
            return false;
        }
    }
    
    // public void closeConnection() {
    //     if (conn != null) {
    //         try {
    //             conn.close();
    //             System.out.println("Database connection closed.");
    //         } catch (SQLException e) {
    //             System.err.println("Error closing connection: " + e.getMessage());
    //         }
    //     }
    // }

    // public static void main(String[] args) {
    //     databaseAuthGateway auth = new databaseKey();
    //     InvoiceDAO dao = new InvoiceDAO(auth);
    //     System.out.println("InvoiceDAO initialized with database authentication gateway.");
        
    //     // Lấy danh sách invoice
    //     List<InvoiceDTO> invoices = dao.getAll();
    //     System.out.println("\n=== DANH SÁCH INVOICE ===");
    //     System.out.println("Retrieved " + invoices.size() + " invoices from database:");
        
    //     for (InvoiceDTO invoice : invoices) {
    //         System.out.println("ID: " + invoice.id + 
    //                          " | Customer: " + invoice.customer + 
    //                          " | Room: " + invoice.room_id + 
    //                          " | Type: " + invoice.type + 
    //                          " | Price: " + invoice.unitPrice);
    //     }
        
    //     // Đóng connection
    //     dao.closeConnection();
    // }
}
