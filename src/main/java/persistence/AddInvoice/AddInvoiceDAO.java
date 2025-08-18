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
            AddInvoiceDTO dto = invoiceDTO;
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(dto.getDate().getTime()));
            preparedStatement.setString(3, dto.getCustomer());
            preparedStatement.setString(4, dto.getRoom_id());
            preparedStatement.setDouble(5, dto.getUnitPrice());
            preparedStatement.setInt(6, dto.getHour());
            preparedStatement.setInt(7, dto.getDay());
            preparedStatement.setString(8, dto.getType());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding invoice: " + e.getMessage());
            return false;
        }
    }
   
}
