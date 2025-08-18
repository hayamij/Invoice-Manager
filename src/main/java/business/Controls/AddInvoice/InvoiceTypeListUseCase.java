package business.Controls.AddInvoice;


import java.util.List;
import java.util.ArrayList;

import business.Entities.DailyInvoice;
import business.Entities.HourlyInvoice;
import business.DTO.InvoiceTypeViewDTO;


// output: List<InvoiceTypeViewDTO> (list of invoice types)


public class InvoiceTypeListUseCase {
    private InvoiceTypeViewDTO model;
    private HourlyInvoice hourly;
    private DailyInvoice daily;

    public List<InvoiceTypeViewDTO> execute() {
        List <InvoiceTypeViewDTO> invoiceTypes = new ArrayList<>();
        model.type = hourly.type();
        model.type = daily.type();
        // extend the model below if needed

        invoiceTypes.add(model);
        return invoiceTypes;
    }
}
