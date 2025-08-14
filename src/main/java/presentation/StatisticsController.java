package presentation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StatisticsController {
    
    @FXML private TableColumn<?, ?> averageColumn;
    @FXML private TableView<?> averageTable;
    @FXML private Label dailyLabel;
    @FXML private Label hourlyLabel;
    @FXML private TableColumn<?, ?> monthColumn;
    @FXML private TableColumn<?, ?> yearColumn;
    
    private InvoiceViewModel viewModel;
    private Label statusLabel;
    private MainController mainController;
    
    @FXML
    public void initialize() {
        // Initialize statistics view
        setupStatisticsTable();
    }
    
    private void setupStatisticsTable() {
        // TODO: Setup table columns and data binding for statistics
        // This would involve creating DTOs for monthly statistics
    }
    
    public void calculateStatistics() {
        if (viewModel != null && viewModel.invoiceItems != null) {
            // Count invoice types
            long hourlyCount = viewModel.invoiceItems.stream()
                .filter(item -> "Hourly Invoice".equals(item.type))
                .count();
                
            long dailyCount = viewModel.invoiceItems.stream()
                .filter(item -> "Daily Invoice".equals(item.type))
                .count();
            
            hourlyLabel.setText("Số lượng thuê theo giờ: " + hourlyCount);
            dailyLabel.setText("Số lượng thuê theo ngày: " + dailyCount);
            
            // TODO: Calculate monthly averages and populate table
            calculateMonthlyAverages();
        }
    }
    
    private void calculateMonthlyAverages() {
        // TODO: Group invoices by month/year and calculate averages
        // This would require additional logic to parse dates and group data
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