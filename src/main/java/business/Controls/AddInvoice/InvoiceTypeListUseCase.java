package business.Controls.AddInvoice;


import java.util.List;
import java.util.ArrayList;

import business.Entities.DailyInvoice;
import business.Entities.HourlyInvoice;
import business.Models.InvoiceTypeModel;


// output: List<InvoiceTypeModel> (list of invoice types)


public class InvoiceTypeListUseCase {
    private InvoiceTypeModel model;
    private HourlyInvoice hourly;
    private DailyInvoice daily;

    public List<InvoiceTypeModel> execute() {
        List <InvoiceTypeModel> invoiceTypes = new ArrayList<>();
        model.type = hourly.type();
        model.type = daily.type();
        // extend the model below if needed

        invoiceTypes.add(model);
        return invoiceTypes;
    }
}
