package business;

import persistence.InvoiceDAO;

public class DeleteInvoiceUseCase {
    private final InvoiceDAO invoiceDAO;

    public DeleteInvoiceUseCase(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public boolean deleteInvoice(String id) {
        if (id == null || id.trim().isEmpty()) return false;
        return invoiceDAO.deleteInvoice(id);
    }
}
