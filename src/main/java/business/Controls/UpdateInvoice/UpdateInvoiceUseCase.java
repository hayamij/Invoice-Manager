package business.Controls.UpdateInvoice;

// import business.Controls.AddInvoice.InvoiceAddRequest;
import business.Entities.Invoice;
import business.Models.UpdateInvoiceModel;
import persistence.UpdateInvoice.UpdateInvoiceDAO;
import persistence.UpdateInvoice.UpdateInvoiceDTO;


// input: UpdateInvoiceModel
// model -> object -> dto
// output: boolean (true if updated successfully, false otherwise)


public class UpdateInvoiceUseCase {
	private final UpdateInvoiceDAO invoiceDAO;

	public UpdateInvoiceUseCase(UpdateInvoiceDAO invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}

	public boolean updateInvoice(UpdateInvoiceModel model) {
		// Validate the invoice data
		if (model == null || 
			model.id == null || model.id.isEmpty() || 
			model.customer == null || model.customer.isEmpty() || 
			model.room_id == null || model.room_id.isEmpty() || 
			model.unitPrice <= 0 || 
			model.date == null || 
			model.hour < 0 || 
			model.day < 0 || 
			model.type == null || model.type.isEmpty()) {
			return false; // Invalid data
		}

		// Convert model to object
		Invoice invoice = convertToObject(model);
		if (invoice == null) {
			return false; // Invalid type
		}

		// Convert object to DTO
		UpdateInvoiceDTO invoiceDTO = convertToDTO(model);

		// Update the invoice in the database
		return invoiceDAO.updateInvoice(invoiceDTO);
	}

	public Invoice convertToObject(UpdateInvoiceModel model) {
		Invoice invoice = InvoiceUpdateRequest.createUpdateRequest(model);
		if (invoice == null) {
			return null; // or throw an exception if type is invalid
		} else {
			invoice.setId(model.id); // Set the ID if needed
			return invoice; // Return the created invoice object
		}
	}
	public UpdateInvoiceDTO convertToDTO(UpdateInvoiceModel model) {
		UpdateInvoiceDTO invoiceDTO = new UpdateInvoiceDTO();
		invoiceDTO.id = model.id;
		invoiceDTO.date = model.date;
		invoiceDTO.customer = model.customer;
		invoiceDTO.room_id = model.room_id;
		invoiceDTO.unitPrice = model.unitPrice;
		invoiceDTO.hour = model.hour;
		invoiceDTO.day = model.day;
		invoiceDTO.type = model.type;
		return invoiceDTO;
	}
}
