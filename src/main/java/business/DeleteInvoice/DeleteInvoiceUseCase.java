package business.DeleteInvoice;

import persistence.DeleteInvoiceDAOGateway;

public final class DeleteInvoiceUseCase {
    private final DeleteInvoiceDAOGateway dao;

    public DeleteInvoiceUseCase(DeleteInvoiceDAOGateway dao) {
        this.dao = dao;
    }

    public boolean execute(int id) {
        if (id <= 0) return false;
        return dao.deleteInvoice(id);
    }
}
