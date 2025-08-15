
/*
Toàn bộ nội dung gốc InvoiceDAO.java:
--------------------------------------------------
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
        System.out.println("retrieved " + invoices.size() + " invoices");
        return invoices;
    }

    // public boolean updateInvoice(InvoiceDTO invoiceDTO) { ... }
    // public boolean deleteInvoice(String id) { ... }
    // public void closeConnection() { ... }
    // public static void main(String[] args) { ... }
}
--------------------------------------------------
*/

package persistence;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO implements InvoiceDAOGateway {
    public InvoiceDAO() {
        // Không thực hiện kết nối database, mock constructor
    }

    public List<InvoiceDTO> getAll() {
        List<InvoiceDTO> invoices = new ArrayList<>();
        String filePath = "c:/Users/canhs/Downloads/no/database/invoices-mock.txt";
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            int idCounter = 1;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split("\\|");
                if (parts.length < 7) continue;
                InvoiceDTO invoice = new InvoiceDTO();
                invoice.id = "INV" + idCounter++;
                invoice.date = java.sql.Timestamp.valueOf(parts[0].trim());
                invoice.customer = parts[1].trim();
                invoice.room_id = parts[2].trim();
                invoice.unitPrice = Double.parseDouble(parts[3].trim());
                invoice.hour = parts[4].trim().equalsIgnoreCase("NULL") ? 0 : Integer.parseInt(parts[4].trim());
                invoice.day = parts[5].trim().equalsIgnoreCase("NULL") ? 0 : Integer.parseInt(parts[5].trim());
                invoice.type = parts[6].trim();
                invoices.add(invoice);
            }
        } catch (Exception e) {
            System.err.println("Lỗi đọc file invoices-mock.txt: " + e.getMessage());
        }
        System.out.println("Read " + invoices.size() + " invoices from file");
        return invoices;
    }

    // Các phương thức dưới đây cũng có thể mock tương tự nếu cần
    // public boolean updateInvoice(InvoiceDTO invoiceDTO) { return true; }
    // public boolean deleteInvoice(String id) { return true; }
    // public void closeConnection() {}
}