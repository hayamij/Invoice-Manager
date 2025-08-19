package presentation.View;

import business.Controls.ShowInvoiceList.ShowInvoiceListUseCase;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import persistence.InvoiceList.InvoiceDAO;
import presentation.Controller.ShowInvoiceListController;
import presentation.Model.InvoiceViewModel;
import presentation.View.InvoiceList.InvoiceTableView;
import presentation.View.CRUD.InvoiceFormView;
import presentation.View.CRUD.RefreshInvoiceView;
import persistence.InvoiceList.InvoiceDAOGateway;

public class MainView {
    @FXML
    private VBox invoiceTableView; // Đúng kiểu với invoicetable.fxml
    
    // Thêm reference đến InvoiceTableView controller
    @FXML
    private InvoiceTableView invoiceTableViewController; // Tên này phải khớp với fx:id trong FXML
    
    // QUAN TRỌNG: Thêm reference đến các controller khác
    @FXML
    private InvoiceFormView invoiceformController; // fx:id="invoiceform" trong main.fxml
    
    @FXML 
    private RefreshInvoiceView refreshbuttonController; // Cần thêm fx:id cho refreshbutton.fxml
    
    private static InvoiceDAOGateway invoiceDAOGateway;
    private ShowInvoiceListController showInvoiceListController;
    
    public void setInvoiceViewModel(InvoiceViewModel invoiceViewModel) {
        // usecases
        invoiceDAOGateway = new InvoiceDAO(); 
        ShowInvoiceListUseCase showInvoiceListUseCase = new ShowInvoiceListUseCase(invoiceDAOGateway);
        
        // controllers
        showInvoiceListController = new ShowInvoiceListController(invoiceViewModel, showInvoiceListUseCase);
        
        // QUAN TRỌNG: Kết nối InvoiceTableView với model
        if (invoiceTableViewController != null) {
            invoiceTableViewController.setModel(invoiceViewModel);
            System.out.println("MainView: InvoiceTableView connected to model.");
        } else {
            System.out.println("MainView: WARNING - invoiceTableViewController is null!");
        }
        
        // QUAN TRỌNG: Kết nối InvoiceFormView với controller để refresh danh sách
        if (invoiceformController != null) {
            invoiceformController.setShowInvoiceListController(showInvoiceListController);
            System.out.println("MainView: InvoiceFormView connected to ShowInvoiceListController.");
        } else {
            System.out.println("MainView: WARNING - invoiceformController is null!");
        }
        
        // QUAN TRỌNG: Kết nối RefreshInvoiceView với controller
        if (refreshbuttonController != null) {
            refreshbuttonController.setShowInvoiceListController(showInvoiceListController);
            System.out.println("MainView: RefreshInvoiceView connected to ShowInvoiceListController.");
        } else {
            System.out.println("MainView: WARNING - refreshbuttonController is null!");
        }
        
        // Load initial data
        System.out.println("MainView: InvoiceViewModel set and ShowInvoiceListController executed.");
        showInvoiceListController.execute(); 
    }
}