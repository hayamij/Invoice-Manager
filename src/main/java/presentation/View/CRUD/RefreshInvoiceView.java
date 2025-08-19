package presentation.View.CRUD;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import presentation.Controller.RefreshInvoiceController;
import presentation.Controller.ShowInvoiceListController;

public class RefreshInvoiceView {
    @FXML
    private Button refreshButton;

    private ShowInvoiceListController showInvoiceListController;
    private RefreshInvoiceController refreshInvoiceController;

    public void setShowInvoiceListController(ShowInvoiceListController controller) {
        this.showInvoiceListController = controller;
        this.refreshInvoiceController = new RefreshInvoiceController(controller);
        System.out.println("RefreshInvoiceView: ShowInvoiceListController set successfully.");
    }

    @FXML
    public void refreshInvoice(ActionEvent event) {
        System.out.println("RefreshInvoiceView: Refresh button clicked");
        if (refreshInvoiceController != null) {
            refreshInvoiceController.refreshInvoice();
        } else {
            System.out.println("RefreshInvoiceView: RefreshInvoiceController is null! Trying direct refresh...");
            // Fallback: gọi trực tiếp ShowInvoiceListController
            if (showInvoiceListController != null) {
                showInvoiceListController.execute();
                System.out.println("RefreshInvoiceView: Direct refresh executed.");
            } else {
                System.out.println("RefreshInvoiceView: Both controllers are null!");
            }
        }
    }
}