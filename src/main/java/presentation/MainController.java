package presentation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import business.AddInvoice.AddInvoiceUseCase;
import business.AddInvoice.InvoiceTypeListUseCase;
import business.ShowInvoiceList.ShowInvoiceListUseCase;
import persistence.InvoiceDAO;
import persistence.InvoiceDAOGateway;

import java.io.IOException;

public class MainController {
    
    @FXML private Label statusLabel;
    @FXML private VBox searchSection;
    @FXML private VBox addInvoiceSection;
    @FXML private VBox invoiceListSection;
    
    private InvoiceDAOGateway dao;
    private InvoiceViewModel viewModel;
    
    // Controllers for each section
    private SearchInvoiceController searchController;
    private AddInvoiceController addInvoiceController;
    private InvoiceListController invoiceListController;
    
    @FXML
    public void initialize() {
        try {
            loadSections();
            initializeWithDependencies();
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Lỗi khởi tạo giao diện: " + e.getMessage());
        }
    }
    
    private void loadSections() throws IOException {
        // Load Search section
        FXMLLoader searchLoader = new FXMLLoader(getClass().getResource("/presentation/SearchSection.fxml"));
        searchSection.getChildren().add(searchLoader.load());
        searchController = searchLoader.getController();
        
        // Load Add Invoice section  
        FXMLLoader addLoader = new FXMLLoader(getClass().getResource("/presentation/AddInvoiceSection.fxml"));
        addInvoiceSection.getChildren().add(addLoader.load());
        addInvoiceController = addLoader.getController();
        
        // Load Invoice List section
        FXMLLoader listLoader = new FXMLLoader(getClass().getResource("/presentation/InvoiceListSection.fxml"));
        invoiceListSection.getChildren().add(listLoader.load());
        invoiceListController = listLoader.getController();
    }
    
    public void initializeWithDependencies() {
        // Initialize DAO and ViewModel
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        persistence.AddInvoiceDAO addInvoiceDAO = new persistence.AddInvoiceDAO();
        viewModel = new InvoiceViewModel();
        // Initialize use cases
        ShowInvoiceListUseCase showInvoiceListUseCase = new ShowInvoiceListUseCase(invoiceDAO);
        InvoiceTypeListUseCase invoiceTypeListUseCase = new InvoiceTypeListUseCase(addInvoiceDAO);
        AddInvoiceUseCase addInvoiceUseCase = new AddInvoiceUseCase(addInvoiceDAO);
        // Setup controllers
        setupInvoiceListController(showInvoiceListUseCase);
        setupAddInvoiceController(addInvoiceUseCase, invoiceTypeListUseCase);
        setupSearchController();
        // Initial load
        loadInitialData();
    }
    
    private void setupInvoiceListController(ShowInvoiceListUseCase showInvoiceListUseCase) {
        invoiceListController.setViewModel(viewModel);
        invoiceListController.setShowInvoiceListUseCase(showInvoiceListUseCase);
        invoiceListController.setStatusLabel(statusLabel);
        invoiceListController.setMainController(this);
    }
    
    private void setupAddInvoiceController(AddInvoiceUseCase addInvoiceUseCase, InvoiceTypeListUseCase invoiceTypeListUseCase) {
        addInvoiceController.setViewModel(viewModel);
        addInvoiceController.setAddInvoiceUseCase(addInvoiceUseCase);
        addInvoiceController.setInvoiceTypeListUseCase(invoiceTypeListUseCase);
        addInvoiceController.setStatusLabel(statusLabel);
        addInvoiceController.setMainController(this);
    }
    
    private void setupSearchController() {
        searchController.setViewModel(viewModel);
        searchController.setStatusLabel(statusLabel);
        searchController.setMainController(this);
    }
    
    private void loadInitialData() {
        invoiceListController.execute();
        invoiceListController.displayInvoices();
        addInvoiceController.loadInvoiceTypes();
    }
    
    // Method được gọi từ các controller con để refresh data
    public void refreshInvoiceList() {
        invoiceListController.execute();
        invoiceListController.displayInvoices();
    }
    
    // Method để update status từ các controller con
    public void updateStatus(String message) {
        statusLabel.setText(message);
    }
}