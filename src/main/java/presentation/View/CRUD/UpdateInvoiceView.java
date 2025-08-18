package presentation.View.CRUD;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UpdateInvoiceView {

    @FXML
    private Button updateButton;
    @FXML
    public void updateInvoice(ActionEvent event) {
        // Logic to handle updating an invoice
        System.out.println("Update button clicked");
    }
}
