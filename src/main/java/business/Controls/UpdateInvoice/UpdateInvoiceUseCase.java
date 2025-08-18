package business.Controls.UpdateInvoice;

import persistence.UpdateInvoice.UpdateInvoiceDAO;
import persistence.UpdateInvoice.UpdateInvoiceDTO;

public class UpdateInvoiceUseCase {
	private final UpdateInvoiceDAO invoiceDAO;

	public UpdateInvoiceUseCase(UpdateInvoiceDAO invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}

	public boolean updateInvoice(UpdateInvoiceDTO invoiceDTO) {
		// Validate the invoice data
		if (invoiceDTO == null || invoiceDTO.getId() == null || invoiceDTO.getId().isEmpty()) {
			return false; // Invalid data
		}
		// Update the invoice in the database
		return invoiceDAO.updateInvoice(invoiceDTO);
	}
}
