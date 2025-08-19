package presentation.View;

import business.Controls.ShowInvoiceList.ShowInvoiceListUseCase;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import persistence.InvoiceList.InvoiceDAO;
import presentation.Controller.ShowInvoiceListController;
import presentation.Model.InvoiceViewModel;
import presentation.View.InvoiceList.InvoiceTableView;

// import java.util.ArrayList;
// import persistence.InvoiceList.InvoiceDAO;
import persistence.InvoiceList.InvoiceDAOGateway;
// import presentation.Controller.ShowInvoiceListController;
// import presentation.Model.InvoiceViewModel;
// import business.Controls.ShowInvoiceList.ShowInvoiceListUseCase;

public class MainView {
    @FXML
    private VBox invoiceTableView; // Đúng kiểu với invoicetable.fxml
    private static InvoiceDAOGateway invoiceDAOGateway;
    public void setInvoiceViewModel(InvoiceViewModel invoiceViewModel) {
        // usecases
        invoiceDAOGateway = new InvoiceDAO(); // Assuming you have a constructor that initializes the DAO
        ShowInvoiceListUseCase showInvoiceListUseCase = new ShowInvoiceListUseCase(invoiceDAOGateway);
        // controllers
        ShowInvoiceListController showInvoiceListController = new ShowInvoiceListController(invoiceViewModel, showInvoiceListUseCase);
        // Truyền model cho bảng qua MainView
        // mainView.setInvoiceViewModel(invoiceViewModel);
        showInvoiceListController.execute(); // Load initial data
        System.out.println("MainView: InvoiceViewModel set and ShowInvoiceListController executed.");
    }
}
