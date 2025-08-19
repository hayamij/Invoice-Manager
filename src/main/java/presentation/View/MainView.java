package presentation.View;

import business.Controls.ShowInvoiceList.ShowInvoiceListUseCase;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import persistence.InvoiceList.InvoiceDAO;
import presentation.Controller.ShowInvoiceListController;
import presentation.Model.InvoiceViewModel;
import presentation.View.InvoiceList.InvoiceTableView;
import persistence.InvoiceList.InvoiceDAOGateway;

public class MainView {
    @FXML
    private VBox invoiceTableView; // Đúng kiểu với invoicetable.fxml
    
    // Thêm reference đến InvoiceTableView controller
    @FXML
    private InvoiceTableView invoiceTableViewController; // Tên này phải khớp với fx:id trong FXML
    
    private static InvoiceDAOGateway invoiceDAOGateway;
    
    public void setInvoiceViewModel(InvoiceViewModel invoiceViewModel) {
        // usecases
        invoiceDAOGateway = new InvoiceDAO(); 
        ShowInvoiceListUseCase showInvoiceListUseCase = new ShowInvoiceListUseCase(invoiceDAOGateway);
        
        // controllers
        ShowInvoiceListController showInvoiceListController = new ShowInvoiceListController(invoiceViewModel, showInvoiceListUseCase);
        
        // QUAN TRỌNG: Kết nối InvoiceTableView với model
        if (invoiceTableViewController != null) {
            invoiceTableViewController.setModel(invoiceViewModel);
            System.out.println("MainView: InvoiceTableView connected to model.");
        } else {
            System.out.println("MainView: WARNING - invoiceTableViewController is null!");
        }
        
        // Load initial data
        System.out.println("MainView: InvoiceViewModel set and ShowInvoiceListController executed.");
        showInvoiceListController.execute(); 
    }
}