package presentation.Controller;

import business.Controls.AddInvoice.AddInvoiceUseCase;
import business.DTO.AddInvoiceViewDTO;
import persistence.AddInvoice.AddInvoiceDAOGateway;

public class AddInvoiceController {
    
    private AddInvoiceUseCase addInvoiceUseCase;
    private AddInvoiceDAOGateway addInvoiceDAO;
    public AddInvoiceController(AddInvoiceDAOGateway addInvoiceDAO) {
        this.addInvoiceDAO = addInvoiceDAO;
        this.addInvoiceUseCase = new AddInvoiceUseCase(addInvoiceDAO);
    }

    public boolean execute(AddInvoiceViewDTO DTO){
        addInvoiceUseCase = new AddInvoiceUseCase(addInvoiceDAO);
        boolean success = addInvoiceUseCase.addInvoice(DTO);
        if (success) {
            System.out.println("Invoice added successfully.");
            return true;
        } else {
            System.out.println("Failed to add invoice.");
            return false;
        }
    }
}
