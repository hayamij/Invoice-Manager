package presentation.Controller;

import business.Controls.DeleteInvoice.DeleteInvoiceUseCase;
import business.DTO.DeleteInvoiceViewDTO;
import persistence.DeleteInvoice.DeleteInvoiceDAOGateway;
import persistence.DeleteInvoice.DeleteInvoiceDTO;

public class DeleteInvoiceController {
    private DeleteInvoiceUseCase deleteInvoiceUseCase;

    public DeleteInvoiceController(DeleteInvoiceDAOGateway deleteInvoiceDAO) {
        this.deleteInvoiceUseCase = new DeleteInvoiceUseCase(deleteInvoiceDAO);
    }

    public boolean execute(DeleteInvoiceViewDTO viewDTO) {
        // Convert from ViewDTO to DTO for persistence layer
        DeleteInvoiceDTO deleteDTO = convertToDTO(viewDTO);
        
        boolean success = deleteInvoiceUseCase.execute(deleteDTO);
        
        if (success) {
            System.out.println("Invoice deleted successfully.");
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