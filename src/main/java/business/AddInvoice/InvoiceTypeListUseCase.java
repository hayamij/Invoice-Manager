package business.AddInvoice;

import java.util.List;
import java.util.Arrays;

import persistence.InvoiceDAOGateway;
import business.HourlyInvoice;
import business.DailyInvoice;

public class InvoiceTypeListUseCase {
    private final InvoiceDAOGateway DAO;

    public InvoiceTypeListUseCase(InvoiceDAOGateway DAO) {
        this.DAO = DAO;
    }

    public List<InvoiceTypeViewDTO> execute() {
        return Arrays.asList(
            new InvoiceTypeViewDTO(new HourlyInvoice().type()),
            new InvoiceTypeViewDTO(new DailyInvoice().type())
        );
    }
}
