package business.Controls.DeleteInvoice;

// ...existing code...
import persistence.DeleteInvoice.DeleteInvoiceDAOGateway;
import persistence.DeleteInvoice.DeleteInvoiceDTO;


// input: DeleteInvoiceDTO
// output: boolean (true if deleted successfully, false otherwise)


public class DeleteInvoiceUseCase {
	private final DeleteInvoiceDAOGateway invoiceDAO;

	public DeleteInvoiceUseCase(DeleteInvoiceDAOGateway invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}

	public boolean execute(DeleteInvoiceDTO invoiceDTO) {
		// Validate the invoice data
		if (invoiceDTO == null || invoiceDTO.id == null || invoiceDTO.id.isEmpty()) {
			return false; // Invalid data
		}
		return invoiceDAO.deleteInvoice(invoiceDTO);
	}
}
