package business.AddInvoice;

import java.util.List;
import java.util.ArrayList;
import business.HourlyInvoice;
import business.DailyInvoice;

public class InvoiceTypeListUseCase {

    private List<InvoiceTypeViewDTO> dto;

    public InvoiceTypeListUseCase() {

    }

    public List<InvoiceTypeViewDTO> execute() {
        dto = new ArrayList<>();
        dto.add(convert(new HourlyInvoice()));
        dto.add(convert(new DailyInvoice()));
        return dto;
    }

    private InvoiceTypeViewDTO convert(Object invoice) {
        if (invoice instanceof HourlyInvoice) {
            return new InvoiceTypeViewDTO(((HourlyInvoice) invoice).type());
        } else if (invoice instanceof DailyInvoice) {
            return new InvoiceTypeViewDTO(((DailyInvoice) invoice).type());
        }
        return null;
    }
}
