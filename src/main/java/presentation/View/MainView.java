package presentation.View;

import business.Controls.ShowInvoiceList.ShowInvoiceListUseCase;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import persistence.InvoiceList.InvoiceDAO;
import persistence.DeleteInvoice.DeleteInvoiceDAO;
import presentation.Controller.ShowInvoiceListController;
import presentation.Controller.DeleteInvoiceController;
import presentation.Model.InvoiceViewModel;
import presentation.View.InvoiceList.InvoiceTableView;
import presentation.View.CRUD.InvoiceFormView;
import presentation.View.CRUD.RefreshInvoiceView;
import presentation.View.CRUD.UpdateInvoiceView;
import presentation.View.CRUD.DeleteInvoiceView;
import persistence.InvoiceList.InvoiceDAOGateway;
import persistence.DeleteInvoice.DeleteInvoiceDAOGateway;
import business.DTO.DeleteInvoiceViewDTO;

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
    
    @FXML
    private DeleteInvoiceView deletebuttonController; // fx:id="deletebutton" trong main.fxml
    @FXML
    private UpdateInvoiceView updateInvoiceViewController;
    
    private InvoiceDAOGateway invoiceDAOGateway;
    private ShowInvoiceListController showInvoiceListController;
    private DeleteInvoiceController deleteInvoiceController;
    private DeleteInvoiceViewDTO invoiceDTO; // Hóa đơn cần xóa
    
    public void setInvoiceViewModel(InvoiceViewModel invoiceViewModel) {
        // usecases
        invoiceDAOGateway = new InvoiceDAO(); 
        ShowInvoiceListUseCase showInvoiceListUseCase = new ShowInvoiceListUseCase(invoiceDAOGateway);
        showInvoiceListController = new ShowInvoiceListController(invoiceViewModel, showInvoiceListUseCase);

        invoiceDTO = new DeleteInvoiceViewDTO();
        DeleteInvoiceDAOGateway deleteInvoiceDAO = new DeleteInvoiceDAO();
        deleteInvoiceController = new DeleteInvoiceController(deleteInvoiceDAO);
        
        
        // QUAN TRỌNG: Kết nối InvoiceTableView với model
        if (invoiceTableViewController != null) {
            invoiceTableViewController.setModel(invoiceViewModel);
            invoiceTableViewController.setSelectedInvoice(deletebuttonController);
            invoiceTableViewController.setInvoiceFormView(invoiceformController);
            System.out.println("MainView: InvoiceTableView connected to model.");
        } else {
            System.out.println("MainView: WARNING - invoiceTableViewController is null!");
        }
        
        // QUAN TRỌNG: Kết nối RefreshInvoiceView với controller
        if (refreshbuttonController != null) {
            refreshbuttonController.setShowInvoiceListController(showInvoiceListController);
            System.out.println("MainView: RefreshInvoiceView connected to ShowInvoiceListController.");
        } else {
            System.out.println("MainView: WARNING - refreshbuttonController is null!");
        }
        
        // QUAN TRỌNG: Kết nối DeleteInvoiceView với controller và table view
        if (deletebuttonController != null) {
            deletebuttonController.setCurrentInvoice(invoiceDTO);
            deletebuttonController.setDeleteInvoiceController(deleteInvoiceController);
            System.out.println("MainView: DeleteInvoiceView connected to controllers.");
        } else {
            System.out.println("MainView: WARNING - deletebuttonController is null!");
        }
        
        if (updateInvoiceViewController != null) {
            updateInvoiceViewController.setInvoiceFormView(invoiceformController);
            updateInvoiceViewController.setInvoiceTableView(invoiceTableViewController);
        }
        // Load initial data
        System.out.println("MainView: InvoiceViewModel set and ShowInvoiceListController executed.");
        showInvoiceListController.execute(); 
    }
}