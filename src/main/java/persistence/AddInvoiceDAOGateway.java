package persistence;

public interface AddInvoiceDAOGateway {
    boolean insertInvoice(InvoiceDTO invoiceDTO);
}
