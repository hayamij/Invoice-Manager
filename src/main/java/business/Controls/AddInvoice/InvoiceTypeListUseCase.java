package business.Controls.AddInvoice;


import java.util.List;
import java.util.ArrayList;

import business.Entities.DailyInvoice;
import business.Entities.HourlyInvoice;
import business.DTO.InvoiceTypeViewDTO;


// output: List<InvoiceTypeViewDTO> (list of invoice types)


public class InvoiceTypeListUseCase {
    public List<InvoiceTypeViewDTO> execute() {
        List<InvoiceTypeViewDTO> invoiceTypes = new ArrayList<>();

        HourlyInvoice hourly = new HourlyInvoice();
        DailyInvoice daily = new DailyInvoice();

        InvoiceTypeViewDTO hourlyDTO = new InvoiceTypeViewDTO();
        hourlyDTO.type = hourly.type();

        InvoiceTypeViewDTO dailyDTO = new InvoiceTypeViewDTO();
        dailyDTO.type = daily.type();

        invoiceTypes.add(hourlyDTO);
        invoiceTypes.add(dailyDTO);

        return invoiceTypes;
    }
}
