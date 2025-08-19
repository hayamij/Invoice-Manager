package presentation.Controller;

import business.Controls.UpdateInvoice.UpdateInvoiceUseCase;
import business.DTO.UpdateInvoiceViewDTO;
import persistence.UpdateInvoice.UpdateInvoiceDAOGateway;
import persistence.UpdateInvoice.UpdateInvoiceDTO;

public class UpdateInvoiceController {
    // This class will handle the logic for updating invoices
    // It will interact with the UpdateInvoiceDAOGateway to perform updates
    // and retrieve invoice data as needed.

    private UpdateInvoiceDAOGateway updateInvoiceDAO;

    public UpdateInvoiceController(UpdateInvoiceDAOGateway updateInvoiceDAO) {
        this.updateInvoiceDAO = updateInvoiceDAO;
        
    }

    public boolean updateInvoice(UpdateInvoiceViewDTO dto) {
        // Call the use case to execute the update
        return new UpdateInvoiceUseCase(updateInvoiceDAO).execute(dto);
    }

    public UpdateInvoiceDTO getInvoiceById(String invoiceId) {
        return updateInvoiceDAO.getInvoiceById(invoiceId);
    }
}
