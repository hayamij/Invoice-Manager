package business.Entities;

import persistence.InvoiceList.InvoiceDTO;

public class InvoiceFactory {
    public static Invoice createInvoice(InvoiceDTO invoiceDTO) {
        if ("Hourly Invoice".equalsIgnoreCase(invoiceDTO.type)){
            return new HourlyInvoice(
                invoiceDTO.id != null ? invoiceDTO.id : "", // Ensure id is not null
                invoiceDTO.date != null ? invoiceDTO.date : new java.util.Date(),
                invoiceDTO.customer != null ? invoiceDTO.customer : "",
                invoiceDTO.room_id != null ? invoiceDTO.room_id : "",
                invoiceDTO.unitPrice != 0 ? invoiceDTO.unitPrice : 0.0,
                invoiceDTO.hour != 0 ? invoiceDTO.hour : 0.0
            );
        } else if ("Daily Invoice".equalsIgnoreCase(invoiceDTO.type)) {
            return new DailyInvoice(
                invoiceDTO.id != null ? invoiceDTO.id : "", // Ensure id is not null
                invoiceDTO.date != null ? invoiceDTO.date : new java.util.Date(),
                invoiceDTO.customer != null ? invoiceDTO.customer : "",
                invoiceDTO.room_id != null ? invoiceDTO.room_id : "",
                invoiceDTO.unitPrice != 0 ? invoiceDTO.unitPrice : 0.0,
                invoiceDTO.day != 0 ? invoiceDTO.day : 0.0
            );
        }
        return null;
    }
}
