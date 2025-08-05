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
        
        switch (dto.type.toLowerCase()) {
            case "hourly":
                return new HourlyInvoice(
                    dto.id, 
                    dto.date, 
                    dto.customer, 
                    dto.room_id, 
                    dto.unitPrice, 
                    dto.hour
                );
                
            case "daily":
                return new DailyInvoice(
                    dto.id, 
                    dto.date, 
                    dto.customer, 
                    dto.room_id, 
                    dto.unitPrice, 
                    dto.day
                );
                
            default:
                throw new IllegalArgumentException("Unknown invoice type: " + dto.type);
        }
    }
    
    /**
     * Validates DTO before creating Invoice
     */
    private static void validateDTO(InvoiceDTO dto) {
        if (dto.id == null || dto.id.trim().isEmpty()) {
            throw new IllegalArgumentException("Invoice ID cannot be null or empty");
        }
        if (dto.customer == null || dto.customer.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer cannot be null or empty");
        }
        if (dto.unitPrice <= 0) {
            throw new IllegalArgumentException("Unit price must be positive");
        }
    }
}
