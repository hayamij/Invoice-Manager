package business.Controls.SearchInvoice;

import business.Entities.Invoice;
import business.Models.InvoiceModel;
import persistence.SearchInvoice.SearchInvoiceDAO;
import persistence.SearchInvoice.SearchInvoiceDTO;


// input: SearchInvoiceDTO
// dto -> object -> model
// output: InvoiceModel (model of the invoice found)



public class SearchInvoiceUseCase {
	private final SearchInvoiceDAO invoiceDAO;

	public SearchInvoiceUseCase(SearchInvoiceDAO invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}

	public InvoiceModel searchInvoice(SearchInvoiceDTO invoiceDTO) {
		// Validate the search criteria
		if (invoiceDTO == null) {
			return null;
		}
		// Search for the invoice in the database
		SearchInvoiceDTO resultDTO = invoiceDAO.searchInvoice(invoiceDTO);
		if (resultDTO == null) {
			return null; // No invoice found
		}
		// Convert the result DTO to an Invoice object
		Invoice invoice = convertToObject(resultDTO);
		InvoiceModel invoiceModel = convertToModel(invoice);
		return invoiceModel;
	}
	public Invoice convertToObject(SearchInvoiceDTO invoiceDTO) {
		if (invoiceDTO == null) {
			return null;
		}
		Invoice invoice = InvoiceSearchRequest.createSearchInvoice(invoiceDTO);
		if (invoice == null) {
			return null; // Conversion failed
		}
		return invoice;
	}
	public InvoiceModel convertToModel(Invoice invoice) {
		if (invoice == null) {
			return null;
		}
		InvoiceModel model = new InvoiceModel();
		model.id = invoice.getId();
		model.date = invoice.getDate();
		model.customer = invoice.getCustomer();
		model.room_id = invoice.getRoom_id();
        model.type = invoice.type();
        model.total = invoice.calculateTotal();
		return model;
	}
}
