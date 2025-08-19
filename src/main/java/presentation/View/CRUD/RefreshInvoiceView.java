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

    public void setShowInvoiceListController(ShowInvoiceListController controller) {
        this.showInvoiceListController = controller;
    }

    @FXML
    public void refreshInvoice(ActionEvent event) {
        System.out.println("Refresh button clicked");
        RefreshInvoiceController refreshController = new RefreshInvoiceController(showInvoiceListController);
        refreshController.refreshInvoice();
    }
}
