package persistence.SearchInvoice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

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
    public List<SearchInvoiceDTO> searchInvoiceByText(String searchText) {
        List<SearchInvoiceDTO> result = new ArrayList<>();
        if (conn == null) {
            System.err.println("Connection is not established.");
            return result;
        }

        // Tìm kiếm theo tất cả các cột: id, customer, room_id, type
        // Cũng có thể tìm theo unitPrice (chuyển đổi sang string để so sánh)
        String query = "SELECT * FROM Invoices WHERE " +
                      "id LIKE ? OR " +
                      "customer LIKE ? OR " +
                      "room_id LIKE ? OR " +
                      "type LIKE ? OR " +
                      "CAST(unitPrice AS VARCHAR) LIKE ? OR " +
                      "CAST(hour AS VARCHAR) LIKE ? OR " +
                      "CAST(day AS VARCHAR) LIKE ? OR " +
                      "FORMAT(date, 'dd/MM/yyyy') LIKE ?";
        
        try (var stmt = conn.prepareStatement(query)) {
            String searchPattern = "%" + searchText + "%";
            // Set tất cả các tham số với cùng searchPattern
            for (int i = 1; i <= 8; i++) {
                stmt.setString(i, searchPattern);
            }

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SearchInvoiceDTO dto = new SearchInvoiceDTO();
                    dto.setId(rs.getString("id"));
                    dto.setDate(rs.getDate("date"));
                    dto.setCustomer(rs.getString("customer"));
                    dto.setRoom_id(rs.getString("room_id"));
                    dto.setUnitPrice(rs.getDouble("unitPrice"));
                    dto.setHour(rs.getInt("hour"));
                    dto.setDay(rs.getInt("day"));
                    dto.setType(rs.getString("type"));
                    result.add(dto);
                }
            }
            System.out.println("SearchInvoiceDAO: Found " + result.size() + " invoices matching '" + searchText + "'");
        } catch (SQLException e) {
            System.err.println("Error executing search query: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}