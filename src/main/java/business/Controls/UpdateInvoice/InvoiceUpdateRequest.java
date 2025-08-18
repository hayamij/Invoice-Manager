package business.Controls.UpdateInvoice;

import business.Entities.DailyInvoice;
import business.Entities.HourlyInvoice;
import business.Entities.Invoice;
import business.Models.UpdateInvoiceModel;

public class InvoiceUpdateRequest {
    public static Invoice createUpdateRequest(UpdateInvoiceModel model) {
        if (model.type.equals("Hourly Invoice")) {
            return new HourlyInvoice(
                model.id != null ? model.id : "", // Ensure id is not null
                model.date != null ? model.date : new java.util.Date(),
                model.customer != null ? model.customer : "",
                model.room_id != null ? model.room_id : "",
                model.unitPrice != 0 ? model.unitPrice : 0.0,
                model.hour != 0 ? model.hour : 0.0
            );
        } else if (model.type.equals("Daily Invoice")) {
            return new DailyInvoice(
                model.id != null ? model.id : "", // Ensure id is not null
                model.date != null ? model.date : new java.util.Date(),
                model.customer != null ? model.customer : "",
                model.room_id != null ? model.room_id : "",
                model.unitPrice != 0 ? model.unitPrice : 0.0,
                model.day != 0 ? model.day : 0.0
            );
        }
        return null; // or throw an exception if type is invalid
    }
}
