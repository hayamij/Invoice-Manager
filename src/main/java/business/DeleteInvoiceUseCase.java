package business;

import persistence.InvoiceDAOGateway;

public class DeleteInvoiceUseCase {
    private final InvoiceDAOGateway invoiceDAO;

    public DeleteInvoiceUseCase(InvoiceDAOGateway invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public boolean deleteInvoice(String id) {
        if (id == null || id.trim().isEmpty()) return false;
        return invoiceDAO.deleteInvoice(id);
    }
}
