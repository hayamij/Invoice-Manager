package persistence.UpdateInvoice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import persistence.DatabaseAuthGateway;
import persistence.DatabaseKey;

public class UpdateInvoiceDAO implements UpdateInvoiceDAOGateway {
    private Connection conn;
    private DatabaseAuthGateway databaseAuth;

    public UpdateInvoiceDAO() {
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
    public boolean updateInvoice(UpdateInvoiceDTO invoiceDTO) {
        String sql = "UPDATE invoices SET date = ?, customer = ?, room_id = ?, unitPrice = ?, hour = ?, day = ?, type = ? WHERE id = ?";
        try (var preparedStatement = conn.prepareStatement(sql)) {
            UpdateInvoiceDTO dto = invoiceDTO;
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(dto.getDate().getTime()));
            preparedStatement.setString(2, dto.getCustomer());
            preparedStatement.setString(3, dto.getRoom_id());
            preparedStatement.setDouble(4, dto.getUnitPrice());
            preparedStatement.setInt(5, dto.getHour());
            preparedStatement.setInt(6, dto.getDay());
            preparedStatement.setString(7, dto.getType());
            preparedStatement.setString(8, dto.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating invoice: " + e.getMessage());
            return false;
        }
    }
    @Override
    public UpdateInvoiceDTO getInvoiceById(String invoiceId) {
        String sql = "SELECT * FROM invoices WHERE id = ?";
        try (var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, invoiceId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UpdateInvoiceDTO dto = new UpdateInvoiceDTO();
                dto.id = resultSet.getString("id");
                dto.customer = resultSet.getString("customer");
                dto.room_id = resultSet.getString("room_id");
                dto.unitPrice = resultSet.getDouble("unitPrice");
                dto.date = resultSet.getTimestamp("date");
                dto.hour = resultSet.getInt("hour");
                dto.day = resultSet.getInt("day");
                dto.type = resultSet.getString("type");
                return dto;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving invoice: " + e.getMessage());
        }
        return null;
    }
}
