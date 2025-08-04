package business;
import persistence.InvoiceDAOGateway;
import persistence.InvoiceDTO;
import java.util.List;

public class addInvoice {
    private InvoiceDAOGateway invoiceDAOGateway;

    public addInvoice(InvoiceDAOGateway invoiceDAOGateway) {
        this.invoiceDAOGateway = invoiceDAOGateway;
    }
    public boolean execute(InvoiceDTO invoice){
        return invoiceDAOGateway.add(invoice);
    }
    // ...
}
