package business;

import persistence.InvoiceDAOGateway;
import persistence.InvoiceDTO;
import java.util.List;
import java.util.Date;

/**
 * Enhanced Invoice Service with proper business operations
 * Replaces the simple addInvoice class with comprehensive CRUD operations
 */
public class InvoiceService {
    private final InvoiceDAOGateway daoGateway;
    
    public InvoiceService(InvoiceDAOGateway daoGateway) {
        this.daoGateway = daoGateway;
    }
    
    /**
     * Create new invoice with validation
     */
    public boolean createInvoice(InvoiceRequest request) {
        try {
            // Validate request
            ValidationResult validation = ValidationService.validateInvoiceRequest(request);
            if (!validation.isValid()) {
                System.err.println("Validation failed: " + validation.getErrorMessage());
                return false;
            }
            
            // Convert request to DTO
            InvoiceDTO dto = convertRequestToDTO(request);
            
            // Save to database
            return daoGateway.add(dto);
            
        } catch (Exception e) {
            System.err.println("Error creating invoice: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get all invoices
     */
    public List<InvoiceDTO> getAllInvoices() {
        try {
            return daoGateway.getAll();
        } catch (Exception e) {
            System.err.println("Error fetching invoices: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Search invoices by criteria (future implementation)
     */
    public List<InvoiceDTO> searchInvoices(SearchCriteria criteria) {
        // TODO: Implement search functionality
        System.out.println("Search functionality not implemented yet");
        return getAllInvoices();
    }
    
    /**
     * Update invoice (future implementation)
     */
    public boolean updateInvoice(String id, InvoiceRequest request) {
        // TODO: Implement update functionality
        System.out.println("Update functionality not implemented yet");
        return false;
    }
    
    /**
     * Delete invoice (future implementation)
     */
    public boolean deleteInvoice(String id) {
        // TODO: Implement delete functionality
        System.out.println("Delete functionality not implemented yet");
        return false;
    }
    
    /**
     * Convert InvoiceRequest to InvoiceDTO
     */
    private InvoiceDTO convertRequestToDTO(InvoiceRequest request) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setCustomer(request.getCustomer());
        dto.setRoom_id(request.getRoomId());
        dto.setUnitPrice(request.getUnitPrice());
        dto.setType(request.getType());
        dto.setDate(new Date()); // Set current date
        
        if ("hourly".equals(request.getType())) {
            dto.setHour(request.getQuantity());
            dto.setDay(0);
        } else if ("daily".equals(request.getType())) {
            dto.setDay(request.getQuantity());
            dto.setHour(0);
        }
        
        return dto;
    }
}
