package presentation.View.CRUD;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import presentation.Controller.DeleteInvoiceController;
import business.DTO.DeleteInvoiceViewDTO;

public class DeleteInvoiceView {

    @FXML
    private Button deleteButton;

    private DeleteInvoiceController deleteInvoiceController;
    private DeleteInvoiceViewDTO currentInvoice; // Hóa đơn đang chọn
    private Runnable onSuccessCallback; // Callback để refresh statistics

    public void setDeleteInvoiceController(DeleteInvoiceController controller) {
        this.deleteInvoiceController = controller;
    }

    public void setCurrentInvoice(DeleteInvoiceViewDTO invoice) {
        this.currentInvoice = invoice;
    }

    public void setOnSuccessCallback(Runnable callback) {
        this.onSuccessCallback = callback;
    }

    @FXML
    public void deleteInvoice(ActionEvent event) {
        if (deleteInvoiceController != null && currentInvoice != null) {
            // Kiểm tra xem có hóa đơn nào được chọn không
            if (currentInvoice.id == null || currentInvoice.id.isEmpty()) {
                showErrorAlert("Lỗi!", "Vui lòng chọn một hóa đơn để xóa.");
                return;
            }

            boolean success = deleteInvoiceController.execute(currentInvoice);
            if (success) {
                showSuccessAlert("Xóa hóa đơn thành công!");
                System.out.println("Invoice deleted and UI refreshed.");
                // Refresh statistics nếu có callback
                if (onSuccessCallback != null) {
                    onSuccessCallback.run();
                }
            } else {
                showErrorAlert("Xóa thất bại!", "Không thể xóa hóa đơn. Vui lòng thử lại.");
                System.out.println("Delete failed.");
            }
        } else {
            showErrorAlert("Lỗi hệ thống!", "Controller hoặc hóa đơn chưa được thiết lập.");
            System.out.println("Controller or invoice not set.");
        }
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}