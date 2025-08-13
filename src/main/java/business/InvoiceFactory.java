package business;

import persistence.InvoiceDTO;

public class InvoiceFactory {
    public static Invoice createInvoice(InvoiceDTO invoiceDTO) {
        if ("hourly invoice".equalsIgnoreCase(invoiceDTO.type)){
            return new HourlyInvoice(
                invoiceDTO.id,
                invoiceDTO.date,
                invoiceDTO.customer,
                invoiceDTO.room_id,
                invoiceDTO.unitPrice,
                invoiceDTO.hour
            );
        } else if ("daily invoice".equalsIgnoreCase(invoiceDTO.type)) {
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
