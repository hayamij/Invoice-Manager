package presentation.View.CRUD;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DeleteInvoiceView {

    @FXML
    private Button deleteButton;
    
    @FXML
    public void deleteInvoice(ActionEvent event) {
        // Logic to handle deleting an invoice
        System.out.println("Delete button clicked");
    }
}
