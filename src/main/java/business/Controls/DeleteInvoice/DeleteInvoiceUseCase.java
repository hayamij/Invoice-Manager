package business.Controls.DeleteInvoice;

import business.Models.DeleteInvoiceModel;
import persistence.DeleteInvoice.DeleteInvoiceDAOGateway;
import persistence.DeleteInvoice.DeleteInvoiceDTO;


// input: DeleteInvoiceModel
// output: boolean (true if deleted successfully, false otherwise)


public class DeleteInvoiceUseCase {
	private final DeleteInvoiceDAOGateway invoiceDAO;

	public DeleteInvoiceUseCase(DeleteInvoiceDAOGateway invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}

	public boolean deleteInvoice(DeleteInvoiceModel model) {
		// Validate the invoice data
		if (model == null || model.id == null || model.id.isEmpty()) {
			return false; // Invalid data
		}
		DeleteInvoiceDTO invoiceDTO = convertToDTO(model);
		return invoiceDAO.deleteInvoice(invoiceDTO);
	}
	public DeleteInvoiceDTO convertToDTO (DeleteInvoiceModel model) {
		DeleteInvoiceDTO invoiceDTO = new DeleteInvoiceDTO();
		invoiceDTO.id = model.id;
		return invoiceDTO;
	}
}
