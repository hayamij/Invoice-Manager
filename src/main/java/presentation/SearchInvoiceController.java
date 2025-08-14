package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SearchInvoiceController {
    
    @FXML private Button clearSearchButton;
    @FXML private Button searchButton;
    @FXML private TextField searchField;
    
    private InvoiceViewModel viewModel;
    private Label statusLabel;
    private MainController mainController;

    @FXML
    public void searchInvoices(ActionEvent event) {
        String searchText = searchField.getText().trim();
        
        if (searchText.isEmpty()) {
            updateStatus("Vui lòng nhập từ khóa tìm kiếm.");
            return;
        }
        
        // TODO: Implement search functionality
        // This would involve:
        // 1. Creating a SearchInvoiceUseCase
        // 2. Filtering invoices based on customer name or room ID
        // 3. Updating the main table view
        
        updateStatus("Đang tìm kiếm: " + searchText + " (Chức năng đang được phát triển)");
        
        System.out.println("Search button clicked with text: " + searchText);
    }
    
    @FXML
    void clearSearch(ActionEvent event) {
        searchField.clear();
        
        // TODO: Reset the invoice list to show all invoices
        // This would involve calling the main controller to refresh the full list
        
        if (mainController != null) {
            mainController.refreshInvoiceList();
        }
        
        updateStatus("Đã xóa bộ lọc tìm kiếm.");
        System.out.println("Search cleared, showing all invoices.");
    }
    
    private void updateStatus(String message) {
        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }
    
    // Setters for dependency injection
    public void setViewModel(InvoiceViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setStatusLabel(Label statusLabel) {
        this.statusLabel = statusLabel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}