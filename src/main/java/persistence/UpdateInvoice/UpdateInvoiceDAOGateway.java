package persistence.UpdateInvoice;

public interface UpdateInvoiceDAOGateway {
    public boolean updateInvoice(UpdateInvoiceDTO invoiceDTO);
    public UpdateInvoiceDTO getInvoiceById(String invoiceId);
}
