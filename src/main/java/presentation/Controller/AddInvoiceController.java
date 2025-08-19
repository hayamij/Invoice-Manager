package presentation.Controller;

import business.Controls.AddInvoice.AddInvoiceUseCase;
// import business.Controls.ShowInvoiceList.ShowInvoiceListUseCase;
import business.DTO.AddInvoiceViewDTO;
import persistence.AddInvoice.AddInvoiceDAOGateway;
// import persistence.InvoiceList.InvoiceDAO;
// import persistence.InvoiceList.InvoiceDAOGateway;
// import presentation.Model.InvoiceViewModel;

public class AddInvoiceController {
    private AddInvoiceUseCase addInvoiceUseCase;
    // private AddInvoiceDAOGateway addInvoiceDAOGateway;

    public AddInvoiceController(AddInvoiceDAOGateway addInvoiceDAO) {
        // this.addInvoiceDAOGateway = addInvoiceDAO;
        this.addInvoiceUseCase = new AddInvoiceUseCase(addInvoiceDAO);
    }


    public boolean execute(AddInvoiceViewDTO DTO){
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

