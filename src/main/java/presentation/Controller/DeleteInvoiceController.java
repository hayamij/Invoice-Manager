package presentation.Controller;

import business.Controls.DeleteInvoice.DeleteInvoiceUseCase;
import business.DTO.DeleteInvoiceViewDTO;
import persistence.DeleteInvoice.DeleteInvoiceDAOGateway;
import persistence.DeleteInvoice.DeleteInvoiceDTO;

public class DeleteInvoiceController {
    private DeleteInvoiceUseCase deleteInvoiceUseCase;
    private ShowInvoiceListController showInvoiceListController;

    public DeleteInvoiceController(DeleteInvoiceDAOGateway deleteInvoiceDAO) {
        this.deleteInvoiceUseCase = new DeleteInvoiceUseCase(deleteInvoiceDAO);
    }

    public void setShowInvoiceListController(ShowInvoiceListController controller) {
        this.showInvoiceListController = controller;
    }

    public boolean execute(DeleteInvoiceViewDTO viewDTO) {
        // Convert from ViewDTO to DTO for persistence layer
        DeleteInvoiceDTO deleteDTO = convertToDTO(viewDTO);
        
        boolean success = deleteInvoiceUseCase.execute(deleteDTO);
        
        if (success) {
            System.out.println("Invoice deleted successfully.");
            // Refresh table after successful deletion
            if (showInvoiceListController != null) {
                showInvoiceListController.execute();
                System.out.println("ShowInvoiceListController executed after deleting invoice.");
            }
            return true;
        } else {
            System.out.println("Failed to delete invoice.");
            return false;
        }
    }

    private DeleteInvoiceDTO convertToDTO(DeleteInvoiceViewDTO viewDTO) {
        DeleteInvoiceDTO dto = new DeleteInvoiceDTO();
        dto.id = viewDTO.id;
        return dto;
    }
}