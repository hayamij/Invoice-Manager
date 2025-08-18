package business.Controls.UpdateInvoice;

// import business.Controls.AddInvoice.InvoiceAddRequest;
import business.Entities.Invoice;
import business.DTO.UpdateInvoiceViewDTO;
import persistence.UpdateInvoice.UpdateInvoiceDAOGateway;
import persistence.UpdateInvoice.UpdateInvoiceDTO;


// input: UpdateInvoiceViewDTO
// model -> object -> dto
// output: boolean (true if updated successfully, false otherwise)


public class UpdateInvoiceUseCase {
	private final UpdateInvoiceDAOGateway invoiceDAO;

	public UpdateInvoiceUseCase(UpdateInvoiceDAOGateway invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}

	public boolean execute(UpdateInvoiceViewDTO dto) {
		// Validate the invoice data
		if (dto == null || 
			dto.id == null || dto.id.isEmpty() || 
			dto.customer == null || dto.customer.isEmpty() || 
			dto.room_id == null || dto.room_id.isEmpty() || 
			dto.unitPrice <= 0 || 
			dto.date == null || 
			dto.hour < 0 || 
			dto.day < 0 || 
			dto.type == null || dto.type.isEmpty()) {
			return false; // Invalid data
		}

		// Convert model to object
		Invoice invoice = convertToObject(dto);
		if (invoice == null) {
			return false; // Invalid type
		}

		// Convert object to DTO
		UpdateInvoiceDTO invoiceDTO = convertToDTO(dto);

		// Update the invoice in the database
		return invoiceDAO.updateInvoice(invoiceDTO);
	}

	public Invoice convertToObject(UpdateInvoiceViewDTO dto) {
		Invoice invoice = InvoiceUpdateRequest.createUpdateRequest(dto);
		if (invoice == null) {
			return null; // or throw an exception if type is invalid
		} else {
			invoice.setId(dto.id); // Set the ID if needed
			return invoice; // Return the created invoice object
		}
	}
	public UpdateInvoiceDTO convertToDTO(UpdateInvoiceViewDTO dto) {
		UpdateInvoiceDTO invoiceDTO = new UpdateInvoiceDTO();
		invoiceDTO.id = dto.id;
		invoiceDTO.date = dto.date;
		invoiceDTO.customer = dto.customer;
		invoiceDTO.room_id = dto.room_id;
		invoiceDTO.unitPrice = dto.unitPrice;
		invoiceDTO.hour = dto.hour;
		invoiceDTO.day = dto.day;
		invoiceDTO.type = dto.type;
		return invoiceDTO;
	}
}
