package business.AddInvoice;

import persistence.InvoiceDAOGateway;
import persistence.InvoiceDTO;

public class AddInvoiceUseCase {
    private final InvoiceDAOGateway invoiceDAO;

    public AddInvoiceUseCase(InvoiceDAOGateway invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public boolean execute(InvoiceDTO invoiceDTO) {
        return invoiceDAO.insertInvoice(invoiceDTO);
    }
}
