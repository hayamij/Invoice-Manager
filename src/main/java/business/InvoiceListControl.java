package business;

import java.util.List;

import persistence.InvoiceDAOGateway;
import persistence.InvoiceDTO;
import presentation.InvoiceListItem;

/**
 * Business Control class chỉ tập trung vào điều phối business logic
 * Đã tách conversion và statistics ra các service riêng
 */
public class InvoiceListControl {
    
    private InvoiceDAOGateway invoiceDAOGateway;
    
    // Constructor for Dependency Injection (PROPER DIP)
    public InvoiceListControl(InvoiceDAOGateway invoiceDAOGateway) {
        this.invoiceDAOGateway = invoiceDAOGateway;
    }
    
    /**
     * Lấy tất cả invoice dưới dạng presentation model (cho UI)
     * Không expose business entities ra presentation layer
     */
    public List<InvoiceListItem> getAllInvoiceItems() {
        try {
            List<InvoiceDTO> dtoList = invoiceDAOGateway.getAll();
            return InvoiceConverter.convertDTOsToInvoiceListItems(dtoList);
        } catch (Exception e) {
            System.err.println("Error loading invoice items: " + e.getMessage());
            return List.of(); // Return empty list on error
        }
    }

    /**
     * Lấy tất cả invoice từ database và convert sang business objects
     */
    public List<Invoice> getAllInvoices() {
        try {
            List<InvoiceDTO> dtoList = invoiceDAOGateway.getAll();
            return InvoiceConverter.convertDTOsToBusinessObjects(dtoList);
        } catch (Exception e) {
            System.err.println("Error loading invoices: " + e.getMessage());
            return List.of(); // Return empty list on error
        }
    }
    
    /**
     * Đóng connection khi không dùng nữa
     */
    public void closeConnection() {
        if (invoiceDAOGateway != null) {
            // TODO: Add closeConnection() method to InvoiceDAOGateway if needed
            // invoiceDAOGateway.closeConnection();
        }
    }
    
    /**
     * Tính tổng doanh thu - delegate to StatisticsService
     */
    public double getTotalRevenue() {
        return StatisticsService.calculateTotalRevenue(getAllInvoices());
    }
    
    /**
     * Đếm số lượng invoice - delegate to StatisticsService
     */
    public int getTotalInvoiceCount() {
        return StatisticsService.countInvoices(getAllInvoices());
    }
    
    /**
     * Tính doanh thu trung bình
     */
    public double getAverageRevenue() {
        return StatisticsService.calculateAverageRevenue(getAllInvoices());
    }
}
