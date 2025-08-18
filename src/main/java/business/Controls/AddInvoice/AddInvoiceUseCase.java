package business.Controls.AddInvoice;

import business.Entities.Invoice;
import business.Models.AddInvoiceModel;
import persistence.AddInvoice.AddInvoiceDAOGateway;
import persistence.AddInvoice.AddInvoiceDTO;


// input: AddInvoiceModel
// model -> object -> DTO
// output: boolean (true if added successfully, false otherwise)


public class AddInvoiceUseCase {
    private AddInvoiceDAOGateway invoiceDAO;

    public AddInvoiceUseCase(AddInvoiceDAOGateway invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }
    
    public boolean addInvoice(AddInvoiceModel invoiceModel) {
        // Validate the invoice data
        if (invoiceModel == null || 
            invoiceModel.customer == null || invoiceModel.customer.isEmpty() || 
            invoiceModel.room_id == null || invoiceModel.room_id.isEmpty() || 
            invoiceModel.unitPrice <= 0 || 
            invoiceModel.date == null || 
            invoiceModel.hour < 0 || 
            invoiceModel.day < 0 || 
            invoiceModel.type == null || invoiceModel.type.isEmpty()) {
            return false; // Invalid data
        }

        // Convert model to object
        Invoice invoice = convertToObject(invoiceModel);
        if (invoice == null) {
            return false; // Invalid type
        }

        // Convert object to DTO
        AddInvoiceDTO invoiceDTO = convertToDTO(invoiceModel);

        // Add the invoice to the database
        return invoiceDAO.addInvoice(invoiceDTO);
    }

    // convert model to object using reverse factory method (request)
    private Invoice convertToObject(AddInvoiceModel model){
        Invoice invoice;
        invoice = InvoiceAddRequest.createAddRequest(model);
        if (invoice == null) {
            return null; // or throw an exception if type is invalid
        } else {
            invoice.setId(model.id); // Set the ID if needed
            return invoice; // Return the created invoice object
        }
    }

    private AddInvoiceDTO convertToDTO(AddInvoiceModel models) {
        AddInvoiceDTO invoiceDTO = new AddInvoiceDTO();
        invoiceDTO.customer = models.customer;
        invoiceDTO.room_id = models.room_id;
        invoiceDTO.unitPrice = models.unitPrice;
        invoiceDTO.date = models.date;
        invoiceDTO.hour = models.hour;
        invoiceDTO.day = models.day;
        invoiceDTO.type = models.type;
        return invoiceDTO;
    }
}
