package business.Controls.SearchInvoice;

import persistence.SearchInvoice.SearchInvoiceDAO;
import persistence.SearchInvoice.SearchInvoiceDTO;

public class SearchInvoiceUseCase {
	private final SearchInvoiceDAO invoiceDAO;

	public SearchInvoiceUseCase(SearchInvoiceDAO invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}

	public SearchInvoiceDTO searchInvoice(SearchInvoiceDTO invoiceDTO) {
		// Validate the search criteria
		if (invoiceDTO == null) {
			return null;
		}
		// Search for the invoice in the database
		return invoiceDAO.searchInvoice(invoiceDTO);
	}
}
