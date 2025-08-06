package business;

import persistence.InvoiceDTO;
import presentation.InvoiceListItem;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service chuyên dụng cho việc convert giữa các models
 * Áp dụng Single Responsibility Principle
 */
public class InvoiceConverter {
    
    /**
     * Convert DTOs từ persistence layer sang presentation models
     */
    public static List<InvoiceListItem> convertDTOsToInvoiceListItems(List<InvoiceDTO> dtoList) {
        return dtoList.stream()
            .map(InvoiceConverter::convertDTOToInvoiceListItem)
            .collect(Collectors.toList());
    }
    
    /**
     * Convert single DTO to InvoiceListItem (presentation model)
     */
    public static InvoiceListItem convertDTOToInvoiceListItem(InvoiceDTO dto) {
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
    public static List<Invoice> convertDTOsToBusinessObjects(List<InvoiceDTO> dtoList) {
        return dtoList.stream()
            .map(InvoiceFactory::createInvoice)
            .collect(Collectors.toList());
    }
}
