package persistence.SearchInvoice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import persistence.DatabaseAuthGateway;
import persistence.DatabaseKey;

public class SearchInvoiceDAO implements SearchInvoiceDAOGateway {
    private Connection conn;
    private DatabaseAuthGateway databaseAuth;

    public SearchInvoiceDAO() {
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
    public SearchInvoiceDTO searchInvoice(SearchInvoiceDTO invoiceDTO) {
        String sql = "SELECT * FROM invoices WHERE id = ? OR customer = ? OR room_id = ? OR type = ?";
        try (var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, invoiceDTO.getId());
            preparedStatement.setString(2, invoiceDTO.getCustomer());
            preparedStatement.setString(3, invoiceDTO.getRoom_id());
            preparedStatement.setString(4, invoiceDTO.getType());

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                SearchInvoiceDTO result = new SearchInvoiceDTO();
                result.setId(resultSet.getString("id"));
                result.setDate(resultSet.getTimestamp("date"));
                result.setCustomer(resultSet.getString("customer"));
                result.setRoom_id(resultSet.getString("room_id"));
                result.setUnitPrice(resultSet.getDouble("unitPrice"));
                result.setHour(resultSet.getInt("hour"));
                result.setDay(resultSet.getInt("day"));
                result.setType(resultSet.getString("type"));
                return result;
            }
        } catch (SQLException e) {
            System.err.println("Error searching invoice: " + e.getMessage());
        }
        return null; // No matching invoice found
    }
}
