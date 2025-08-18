package business.Controls.AddInvoice;

import business.Entities.DailyInvoice;
import business.Entities.HourlyInvoice;
import business.Entities.Invoice;
import business.DTO.AddInvoiceViewDTO;

public class InvoiceAddRequest {
    public static Invoice createAddRequest(AddInvoiceViewDTO dto){
        if (dto.type.equals("Hourly Invoice")) {
            return new HourlyInvoice(
                dto.id != null ? dto.id : "", // Ensure id is not null
                // date is current timestamp
                dto.date != null ? dto.date : new java.util.Date(),
                dto.customer != null ? dto.customer : "",
                dto.room_id != null ? dto.room_id : "",
                dto.unitPrice != 0 ? dto.unitPrice : 0.0,
                dto.hour != 0 ? dto.hour : 0.0
            );
        } else if (dto.type.equals("Daily Invoice")) {
            return new DailyInvoice(
                dto.id != null ? dto.id : "", // Ensure id is not null
                dto.date != null ? dto.date : new java.util.Date(),
                dto.customer != null ? dto.customer : "",
                dto.room_id != null ? dto.room_id : "",
                dto.unitPrice != 0 ? dto.unitPrice : 0.0,
                dto.day != 0 ? dto.day : 0.0
            );
        }
        return null; // or throw an exception if type is invalid
    }
}
