package business.AddInvoice;

import java.util.List;
import java.util.Arrays;

import persistence.AddInvoiceDAOGateway;
import business.HourlyInvoice;
import business.DailyInvoice;

public class InvoiceTypeListUseCase {


    public InvoiceTypeListUseCase() {

    }

    public List<InvoiceTypeViewDTO> execute() {
        return Arrays.asList(
            new InvoiceTypeViewDTO(new HourlyInvoice().type()),
            new InvoiceTypeViewDTO(new DailyInvoice().type())
        );
    }
}
