package business;

import persistence.InvoiceDTO;

/**
 * Factory Pattern for creating Invoice objects
 * Follows Single Responsibility Principle
 */
public class InvoiceFactory {
    
    /**
     * Creates appropriate Invoice type based on DTO
     * @param dto InvoiceDTO from persistence layer
     * @return Invoice business object
     * @throws IllegalArgumentException if type is unknown
     */
    public static Invoice createInvoice(InvoiceDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("InvoiceDTO cannot be null");
        }
        
        validateDTO(dto);
        
        switch (dto.getType().toLowerCase()) {
            case "hourly":
                return new HourlyInvoice(
                    dto.getId(), 
                    dto.getDate(), 
                    dto.getCustomer(), 
                    dto.getRoom_id(), 
                    dto.getUnitPrice(), 
                    dto.getHour()
                );
                
            case "daily":
                return new DailyInvoice(
                    dto.getId(), 
                    dto.getDate(), 
                    dto.getCustomer(), 
                    dto.getRoom_id(), 
                    dto.getUnitPrice(), 
                    dto.getDay()
                );
                
            default:
                throw new IllegalArgumentException("Unknown invoice type: " + dto.getType());
        }
    }
    
    /**
     * Validates DTO before creating Invoice
     */
    private static void validateDTO(InvoiceDTO dto) {
        if (dto.getId() == null || dto.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Invoice ID cannot be null or empty");
        }
        if (dto.getCustomer() == null || dto.getCustomer().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer cannot be null or empty");
        }
        if (dto.getUnitPrice() <= 0) {
            throw new IllegalArgumentException("Unit price must be positive");
        }
    }
}
