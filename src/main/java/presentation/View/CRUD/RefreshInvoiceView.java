package presentation.View.CRUD;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RefreshInvoiceView {
    
    @FXML
    private Button refreshButton;
    @FXML
    public void refreshInvoice(ActionEvent event) {
        // Logic to handle refreshing the invoice list
        System.out.println("Refresh button clicked");

    }
}
