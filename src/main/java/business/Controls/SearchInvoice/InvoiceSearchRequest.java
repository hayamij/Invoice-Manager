package business.Controls.SearchInvoice;

import business.Entities.Invoice;
import business.Entities.InvoiceFactory;
import persistence.InvoiceList.InvoiceDTO;
import persistence.SearchInvoice.SearchInvoiceDTO;

public class InvoiceSearchRequest {
    public static Invoice createSearchInvoice(SearchInvoiceDTO dto) {
        if (dto == null) {
            return null;
        }
        InvoiceDTO invoiceDTO = convertToInvoiceDTO(dto);
        return InvoiceFactory.createInvoice(invoiceDTO);
    }
    
    // convert SearchInvoiceDTO to InvoiceDTO
    public static InvoiceDTO convertToInvoiceDTO(SearchInvoiceDTO dto) {
        if (dto == null) {
            return null;
        }
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.id = dto.id;
        invoiceDTO.date = dto.date;
        invoiceDTO.customer = dto.customer;
        invoiceDTO.room_id = dto.room_id;
        invoiceDTO.type = dto.type;
        invoiceDTO.unitPrice = dto.unitPrice;
        invoiceDTO.hour = dto.hour;
        invoiceDTO.day = dto.day;
        return invoiceDTO;
    }
}
