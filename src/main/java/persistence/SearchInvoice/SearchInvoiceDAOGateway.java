package persistence.SearchInvoice;

public interface SearchInvoiceDAOGateway {
    // search invoice by all fields
    SearchInvoiceDTO searchInvoice(SearchInvoiceDTO invoiceDTO);
}
