package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteInvoiceDAO implements DeleteInvoiceDAOGateway {
    private final Connection conn;

    public DeleteInvoiceDAO() {
        try {
            DatabaseAuthGateway key = new DatabaseKey();
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + key.getServer()
                    + ";databaseName=" + key.getDatabase()
                    + ";trustServerCertificate=true;";
            this.conn = DriverManager.getConnection(url, key.getUsername(), key.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("Không thể kết nối DB cho DeleteInvoiceDAO", e);
        }
    }

    @Override
    public boolean deleteInvoice(int id) {
        final String sql = "DELETE FROM invoices WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
