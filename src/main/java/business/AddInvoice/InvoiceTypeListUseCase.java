package business.AddInvoice;

import java.util.List;
import java.util.Arrays;

import persistence.AddInvoiceDAOGateway;
import business.HourlyInvoice;
import business.DailyInvoice;

public class InvoiceTypeListUseCase {
    private final AddInvoiceDAOGateway DAO;

    public InvoiceTypeListUseCase(AddInvoiceDAOGateway DAO) {
        this.DAO = DAO;
    }

    public List<InvoiceTypeViewDTO> execute() {
        return Arrays.asList(
            new InvoiceTypeViewDTO(new HourlyInvoice().type()),
            new InvoiceTypeViewDTO(new DailyInvoice().type())
        );
    }
}
