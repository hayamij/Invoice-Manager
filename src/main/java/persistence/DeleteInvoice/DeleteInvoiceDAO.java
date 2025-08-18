package persistence.DeleteInvoice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import persistence.DatabaseAuthGateway;
import persistence.DatabaseKey;

public class DeleteInvoiceDAO implements DeleteInvoiceDAOGateway {
    private Connection conn;
    private DatabaseAuthGateway databaseAuth;

    public DeleteInvoiceDAO() {
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
    @Override
    public boolean deleteInvoice(DeleteInvoiceDTO invoiceDTO) {
        String sql = "DELETE FROM invoices WHERE id = ?";
        try (var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, invoiceDTO.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting invoice: " + e.getMessage());
            return false;
        }
    }
}
