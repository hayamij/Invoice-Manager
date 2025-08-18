package business.Entities;

import persistence.InvoiceList.InvoiceDTO;

public class InvoiceFactory {
    public static Invoice createInvoice(InvoiceDTO invoiceDTO) {
        if ("Daily Invoice".equalsIgnoreCase(invoiceDTO.type)){
            return new HourlyInvoice(
                invoiceDTO.id,
                invoiceDTO.date,
                invoiceDTO.customer,
                invoiceDTO.room_id,
                invoiceDTO.unitPrice,
                invoiceDTO.hour
            );
        } else if ("daily".equalsIgnoreCase(invoiceDTO.type)) {
            return new DailyInvoice(
                invoiceDTO.id,
                invoiceDTO.date,
                invoiceDTO.customer,
                invoiceDTO.room_id,
                invoiceDTO.unitPrice,
                invoiceDTO.day
            );
        }
        return null;
    }
}
