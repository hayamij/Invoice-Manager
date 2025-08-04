package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class InvoiceDAO {
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
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM invoices");
            
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
        } /* finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        } */
        
        return invoices;
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
