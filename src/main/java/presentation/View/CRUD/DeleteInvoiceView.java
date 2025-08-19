package presentation.View.CRUD;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import presentation.Controller.DeleteInvoiceController;
import business.DTO.DeleteInvoiceViewDTO;

public class DeleteInvoiceView {

    @FXML
    private Button deleteButton;

    private DeleteInvoiceController deleteInvoiceController;
    private DeleteInvoiceViewDTO currentInvoice; // Hóa đơn đang chọn

    public void setDeleteInvoiceController(DeleteInvoiceController controller) {
        this.deleteInvoiceController = controller;
    }

    public void setCurrentInvoice(DeleteInvoiceViewDTO invoice) {
        this.currentInvoice = invoice;
    }

    @FXML
    public void deleteInvoice(ActionEvent event) {
        if (deleteInvoiceController != null && currentInvoice != null) {
            boolean success = deleteInvoiceController.execute(currentInvoice);
            if (success) {
                System.out.println("Invoice deleted and UI refreshed.");
                // Có thể thêm code đóng dialog hoặc thông báo cho người dùng
            } else {
                System.out.println("Delete failed.");
            }
        } else {
            System.out.println("Controller or invoice not set.");
        }
    }
}
