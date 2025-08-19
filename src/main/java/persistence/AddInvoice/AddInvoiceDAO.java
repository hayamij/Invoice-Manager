package persistence.AddInvoice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import persistence.DatabaseAuthGateway;
import persistence.DatabaseKey;

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

    public boolean addInvoice(AddInvoiceDTO invoiceDTO) {
        String sql = "INSERT INTO invoices (date, customer, room_id, unitPrice, hour, day, type) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(invoiceDTO.getDate().getTime()));
            preparedStatement.setString(2, invoiceDTO.getCustomer());
            preparedStatement.setString(3, invoiceDTO.getRoom_id());
            preparedStatement.setDouble(4, invoiceDTO.getUnitPrice());
            preparedStatement.setInt(5, invoiceDTO.getHour());
            preparedStatement.setInt(6, invoiceDTO.getDay());
            preparedStatement.setString(7, invoiceDTO.getType());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Invoice added successfully. Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding invoice: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
   
}