package business.Controls.AddInvoice;

import business.Entities.Invoice;

import business.DTO.AddInvoiceViewDTO;
import persistence.AddInvoice.AddInvoiceDAOGateway;
import persistence.AddInvoice.AddInvoiceDTO;


// input: AddInvoiceViewDTO
// model -> object -> DTO
// output: boolean (true if added successfully, false otherwise)


public class AddInvoiceUseCase {
    private AddInvoiceDAOGateway invoiceDAO;

    public AddInvoiceUseCase(AddInvoiceDAOGateway invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }
    
    public boolean addInvoice(AddInvoiceViewDTO dto) {
        // Validate the invoice data
        if (dto == null || 
            dto.customer == null || dto.customer.isEmpty() || 
            dto.room_id == null || dto.room_id.isEmpty() || 
            dto.unitPrice <= 0 || 
            dto.date == null || 
            dto.hour < 0 || dto.hour > 30 ||
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
        AddInvoiceDTO invoiceDTO = convertToDTO(dto);

        // Add the invoice to the database
        return invoiceDAO.addInvoice(invoiceDTO);
    }

    // convert model to object using reverse factory method (request)
    private Invoice convertToObject(AddInvoiceViewDTO dto){
        Invoice invoice;
        invoice = InvoiceAddRequest.createAddRequest(dto);
        if (invoice == null) {
            return null; // or throw an exception if type is invalid
        } else {
            invoice.setId(dto.id); // Set the ID if needed
            return invoice; // Return the created invoice object
        }
    }

    private AddInvoiceDTO convertToDTO(AddInvoiceViewDTO dto) {
        AddInvoiceDTO invoiceDTO = new AddInvoiceDTO();
        invoiceDTO.customer = dto.customer;
        invoiceDTO.room_id = dto.room_id;
        invoiceDTO.unitPrice = dto.unitPrice;
        invoiceDTO.date = dto.date;
        invoiceDTO.hour = dto.hour;
        invoiceDTO.day = dto.day;
        invoiceDTO.type = dto.type;
        return invoiceDTO;
    }
}
