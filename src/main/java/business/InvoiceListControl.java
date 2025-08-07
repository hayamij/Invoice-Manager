package business;

import java.util.List;
import java.util.stream.Collectors;

import persistence.InvoiceDAOGateway;
import persistence.InvoiceDAO;
import persistence.databaseKey;
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
     * Factory method để tạo InvoiceListControl với default dependencies
     * Giúp Presentation layer không phụ thuộc trực tiếp vào Persistence layer
     */
    public static InvoiceListControl createInstance() {
        InvoiceDAOGateway dao = new InvoiceDAO(new databaseKey());
        return new InvoiceListControl(dao);
    }
    
    /**
     * Lấy tất cả invoice dưới dạng presentation model (cho UI)
     * Không expose business entities ra presentation layer
     */
    public List<InvoiceListItem> getAllInvoiceItems() {
        try {
            List<InvoiceDTO> dtoList = invoiceDAOGateway.getAll();
            return convertDTOsToInvoiceListItems(dtoList);
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
            return convertDTOsToBusinessObjects(dtoList);
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
    
    // ===== CONVERTER METHODS (merged from InvoiceConverter) =====
    
    /**
     * Convert DTOs từ persistence layer sang presentation models
     */
    private List<InvoiceListItem> convertDTOsToInvoiceListItems(List<InvoiceDTO> dtoList) {
        return dtoList.stream()
            .map(this::convertDTOToInvoiceListItem)
            .collect(Collectors.toList());
    }
    
    /**
     * Convert single DTO to InvoiceListItem (presentation model)
     */
    private InvoiceListItem convertDTOToInvoiceListItem(InvoiceDTO dto) {
        InvoiceListItem item = new InvoiceListItem();
        item.setId(dto.getId());
        item.setDate(dto.getDate().toString());
        item.setCustomer(dto.getCustomer());
        item.setRoomId(dto.getRoom_id());
        item.setType(dto.getType());
        item.setUnitPrice(String.valueOf(dto.getUnitPrice()));
        item.setHour(dto.getHour());
        item.setDay(dto.getDay());
        
        // Calculate total price based on type
        if ("hourly".equals(dto.getType())) {
            item.setTotalPrice(dto.getUnitPrice() * dto.getHour());
        } else if ("daily".equals(dto.getType())) {
            item.setTotalPrice(dto.getUnitPrice() * dto.getDay());
        } else {
            item.setTotalPrice(0.0);
        }
        
        return item;
    }
    
    /**
     * Convert DTOs từ persistence layer sang business objects using Factory Pattern
     */
    private List<Invoice> convertDTOsToBusinessObjects(List<InvoiceDTO> dtoList) {
        return dtoList.stream()
            .map(InvoiceFactory::createInvoice)
            .collect(Collectors.toList());
    }
}
