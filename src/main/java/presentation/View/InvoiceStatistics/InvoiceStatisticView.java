package presentation.View.InvoiceStatistics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class InvoiceStatisticView {

    @FXML
    private Button monthlyStatsButton;

    @FXML
    private Label totalAmountLabel;

    @FXML
    private Label totalInvoicesLabel;

    public void showMonthlyStatistics() {
        // Logic to display monthly statistics
        System.out.println("Monthly statistics button clicked");
        // Update totalAmountLabel and totalInvoicesLabel with actual data
        totalAmountLabel.setText("Total Amount: $1000");
        totalInvoicesLabel.setText("Total Invoices: 50");
    }
    @FXML
    public void statsInvoice(ActionEvent event) {
        // Call the method to show monthly statistics
        showMonthlyStatistics();
    }
}
