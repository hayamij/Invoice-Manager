package presentation;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import business.InvoiceListControl;
import business.Invoice;
import business.StatisticsService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class PrimaryController implements Initializable {

    // Business layer - tạo dependencies trực tiếp
    private InvoiceListControl invoiceControl;
    
    // Model with Observer capabilities
    private InvoiceListModel invoiceListModel;
    
    // Search fields
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Button clearSearchButton;
    
    // Form fields
    @FXML private TextField customerField;
    @FXML private TextField roomField;
    @FXML private TextField unitPriceField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField hourField;
    @FXML private TextField dayField;
    
    // Labels for dynamic fields
    @FXML private Label hourLabel;
    @FXML private Label dayLabel;
    
    // Buttons
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button refreshButton;
    
    // Table
    @FXML private TableView<InvoiceListItem> invoiceTable;
    @FXML private TableColumn<InvoiceListItem, String> idColumn;
    @FXML private TableColumn<InvoiceListItem, String> dateColumn;
    @FXML private TableColumn<InvoiceListItem, String> customerColumn;
    @FXML private TableColumn<InvoiceListItem, String> roomColumn;
    @FXML private TableColumn<InvoiceListItem, String> typeColumn;
    @FXML private TableColumn<InvoiceListItem, Double> unitPriceColumn;
    @FXML private TableColumn<InvoiceListItem, Integer> hourColumn;
    @FXML private TableColumn<InvoiceListItem, Integer> dayColumn;
    @FXML private TableColumn<InvoiceListItem, Double> totalColumn;
    
    // Labels
    @FXML private Label statusLabel;
    @FXML private Label totalInvoicesLabel;
    @FXML private Label totalAmountLabel;
    @FXML private Button monthlyStatsButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ✅ Sử dụng Factory method từ InvoiceListControl
        invoiceControl = InvoiceListControl.createInstance();
        
        // Initialize model with Observer pattern
        invoiceListModel = new InvoiceListModel();

        // Initialize ComboBox
        typeComboBox.setItems(FXCollections.observableArrayList("hourly", "daily"));
        
        // Initialize TableView columns
        setupTableColumns();
        
        // Bind table to model
        invoiceTable.itemsProperty().bind(invoiceListModel.invoicesProperty());
        
        // Set default status
        statusLabel.setText("");
        
        // Add listener for typeComboBox to show/hide fields
        typeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("hourly")) {
                    // Show hour field and label, hide day field and label
                    hourLabel.setVisible(true);
                    hourLabel.setManaged(true);
                    hourField.setVisible(true);
                    hourField.setManaged(true);
                    
                    dayLabel.setVisible(false);
                    dayLabel.setManaged(false);
                    dayField.setVisible(false);
                    dayField.setManaged(false);
                    dayField.clear();
                } else if (newValue.equals("daily")) {
                    // Show day field and label, hide hour field and label
                    dayLabel.setVisible(true);
                    dayLabel.setManaged(true);
                    dayField.setVisible(true);
                    dayField.setManaged(true);
                    
                    hourLabel.setVisible(false);
                    hourLabel.setManaged(false);
                    hourField.setVisible(false);
                    hourField.setManaged(false);
                    hourField.clear();
                }
                
                // Set default values directly
                if (newValue.equals("hourly")) {
                    unitPriceField.setText("100.0");
                } else {
                    unitPriceField.setText("500.0");
                }
            }
        });
        
        // Set default selection to "hourly"
        typeComboBox.setValue("hourly");
        
        // Load initial data
        loadInvoiceData();
    }
    
    /**
     * Setup TableView columns with proper cell value factories
     */
    private void setupTableColumns() {
        // Use getters instead of direct field access
        idColumn.setCellValueFactory(cellData -> {
            InvoiceListItem item = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(item.getId());
        });
        
        dateColumn.setCellValueFactory(cellData -> {
            InvoiceListItem item = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(item.getDate());
        });
        
        customerColumn.setCellValueFactory(cellData -> {
            InvoiceListItem item = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(item.getCustomer());
        });
        
        roomColumn.setCellValueFactory(cellData -> {
            InvoiceListItem item = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(item.getRoomId());
        });
        
        typeColumn.setCellValueFactory(cellData -> {
            InvoiceListItem item = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(item.getType());
        });
        
        unitPriceColumn.setCellValueFactory(cellData -> {
            InvoiceListItem item = cellData.getValue();
            return new javafx.beans.property.SimpleObjectProperty<>(Double.parseDouble(item.getUnitPrice()));
        });
        
        hourColumn.setCellValueFactory(cellData -> {
            InvoiceListItem item = cellData.getValue();
            return new javafx.beans.property.SimpleObjectProperty<>(item.getHour() > 0 ? item.getHour() : null);
        });
        
        dayColumn.setCellValueFactory(cellData -> {
            InvoiceListItem item = cellData.getValue();
            return new javafx.beans.property.SimpleObjectProperty<>(item.getDay() > 0 ? item.getDay() : null);
        });
        
        totalColumn.setCellValueFactory(cellData -> {
            InvoiceListItem item = cellData.getValue();
            return new javafx.beans.property.SimpleObjectProperty<>(item.getTotalPrice());
        });
    }
    
    /**
     * Load invoice data from business layer and display in table
     */
    private void loadInvoiceData() {
        try {
            List<InvoiceListItem> invoiceItems = invoiceControl.getAllInvoiceItems();
            
            // Update model (which will automatically notify table due to binding)
            invoiceListModel.setInvoices(invoiceItems);
            
            // Update statistics
            updateStatistics(invoiceItems);
            
            // Trigger statistics update notification
            invoiceListModel.notifyStatisticsUpdate();
            
            statusLabel.setText("Đã tải " + invoiceItems.size() + " hóa đơn");
        } catch (Exception e) {
            statusLabel.setText("Lỗi tải dữ liệu: " + e.getMessage());
        }
    }
    
    /**
     * Update statistics labels
     */
    private void updateStatistics(List<InvoiceListItem> invoiceItems) {
        int totalCount = invoiceItems.size();
        double totalRevenue = invoiceItems.stream()
            .mapToDouble(item -> item.getTotalPrice())
            .sum();
        
        totalInvoicesLabel.setText("Tổng số hóa đơn: " + totalCount);
        totalAmountLabel.setText(String.format("Tổng doanh thu: %.2f VND", totalRevenue));
    }

    @FXML
    private void addInvoice() {
        statusLabel.setText("Thêm hóa đơn...");
        // TODO: Implement add invoice logic
    }

    @FXML
    private void updateInvoice() {
        statusLabel.setText("Cập nhật hóa đơn...");
        // TODO: Implement update invoice logic
    }

    @FXML
    private void deleteInvoice() {
        statusLabel.setText("Xóa hóa đơn...");
        // TODO: Implement delete invoice logic
    }

    @FXML
    private void clearForm() {
        customerField.clear();
        roomField.clear();
        unitPriceField.clear();
        // Keep typeComboBox selection as "hourly" instead of clearing
        typeComboBox.setValue("hourly");
        hourField.clear();
        dayField.clear();
        
        // No need to hide/show fields since setValue("hourly") will trigger the listener
        // which will automatically show hour field and hide day field
        
        statusLabel.setText("Đã làm mới form");
    }

    @FXML
    private void refreshTable() {
        statusLabel.setText("Đang tải lại danh sách...");
        loadInvoiceData();
    }
    
    @FXML
    private void searchInvoices() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadInvoiceData(); // Load all if empty
            return;
        }
        
        statusLabel.setText("Đang tìm kiếm...");
        List<InvoiceListItem> filteredInvoices = invoiceListModel.getInvoices().stream()
            .filter(invoice -> 
                invoice.getCustomer().toLowerCase().contains(searchText.toLowerCase()) ||
                invoice.getRoomId().toLowerCase().contains(searchText.toLowerCase())
            )
            .collect(Collectors.toList());
            
        invoiceTable.setItems(FXCollections.observableArrayList(filteredInvoices));
        statusLabel.setText("Tìm thấy " + filteredInvoices.size() + " hóa đơn");
        updateStatistics(filteredInvoices);
    }
    
    @FXML
    private void clearSearch() {
        searchField.clear();
        loadInvoiceData();
        statusLabel.setText("Đã xóa bộ lọc");
    }
    
    @FXML
    private void showMonthlyStats() {
        try {
            List<Invoice> invoices = invoiceControl.getAllInvoices();
            var monthlyStats = StatisticsService.calculateMonthlyAverageRevenue(invoices);
            var roomTypeStats = StatisticsService.countByRoomType(invoices);
            
            StringBuilder message = new StringBuilder("=== THỐNG KÊ ===\n\n");
            
            message.append("📊 TRUNG BÌNH DOANH THU THEO THÁNG:\n");
            monthlyStats.forEach((month, avg) -> 
                message.append(String.format("• %s: %.2f VND\n", month, avg))
            );
            
            message.append("\n🏨 SỐ LƯỢNG THEO LOẠI PHÒNG:\n");
            roomTypeStats.forEach((type, count) ->
                message.append(String.format("• %s: %d hóa đơn\n", type, count))
            );
            
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Thống kê hệ thống");
            alert.setHeaderText("Báo cáo thống kê chi tiết");
            alert.setContentText(message.toString());
            alert.showAndWait();
            
        } catch (Exception e) {
            statusLabel.setText("Lỗi khi tạo thống kê: " + e.getMessage());
        }
    }
}
