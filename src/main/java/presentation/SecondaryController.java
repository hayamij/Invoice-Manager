package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

/**
 * Settings/Configuration Controller
 * Manages application settings and reports
 */
public class SecondaryController implements Initializable, Subscriber<InvoiceListEvent> {

    // Settings controls
    @FXML private TextField companyNameField;
    @FXML private TextField companyAddressField;
    @FXML private TextField companyPhoneField;
    @FXML private ComboBox<String> defaultInvoiceTypeComboBox;
    @FXML private Spinner<Double> defaultHourlyRateSpinner;
    @FXML private Spinner<Double> defaultDailyRateSpinner;
    
    // Report controls
    @FXML private Label totalInvoicesReportLabel;
    @FXML private Label totalRevenueReportLabel;
    @FXML private Label averageRevenueLabel;
    @FXML private Label hourlyInvoiceCountLabel;
    @FXML private Label dailyInvoiceCountLabel;
    
    // Buttons
    @FXML private Button saveSettingsButton;
    @FXML private Button resetSettingsButton;
    @FXML private Button refreshReportsButton;
    @FXML private Button backToPrimaryButton;
    
    // Status
    @FXML private Label statusLabel;
    
    // Application settings
    private AppSettings appSettings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize settings
        appSettings = AppSettings.getInstance();
        
        // Setup default invoice type combo box
        defaultInvoiceTypeComboBox.setItems(FXCollections.observableArrayList("hourly", "daily"));
        
        // Setup spinners with value factories
        defaultHourlyRateSpinner.setValueFactory(
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 10000.0, 100.0, 10.0)
        );
        defaultDailyRateSpinner.setValueFactory(
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 50000.0, 500.0, 50.0)
        );
        
        // Load current settings
        loadSettings();
        
        // Load reports
        refreshReports();
        
        statusLabel.setText("Settings loaded successfully");
    }
    
    /**
     * Load current application settings
     */
    private void loadSettings() {
        companyNameField.setText(appSettings.getCompanyName());
        companyAddressField.setText(appSettings.getCompanyAddress());
        companyPhoneField.setText(appSettings.getCompanyPhone());
        defaultInvoiceTypeComboBox.setValue(appSettings.getDefaultInvoiceType());
        defaultHourlyRateSpinner.getValueFactory().setValue(appSettings.getDefaultHourlyRate());
        defaultDailyRateSpinner.getValueFactory().setValue(appSettings.getDefaultDailyRate());
    }
    
    /**
     * Save application settings
     */
    @FXML
    private void saveSettings() {
        try {
            appSettings.setCompanyName(companyNameField.getText());
            appSettings.setCompanyAddress(companyAddressField.getText());
            appSettings.setCompanyPhone(companyPhoneField.getText());
            appSettings.setDefaultInvoiceType(defaultInvoiceTypeComboBox.getValue());
            appSettings.setDefaultHourlyRate(defaultHourlyRateSpinner.getValue());
            appSettings.setDefaultDailyRate(defaultDailyRateSpinner.getValue());
            
            appSettings.saveToFile();
            statusLabel.setText("Settings saved successfully!");
        } catch (Exception e) {
            statusLabel.setText("Error saving settings: " + e.getMessage());
        }
    }
    
    /**
     * Reset settings to default
     */
    @FXML
    private void resetSettings() {
        appSettings.resetToDefaults();
        loadSettings();
        statusLabel.setText("Settings reset to defaults");
    }
    
    /**
     * Refresh reports from business layer
     */
    @FXML
    private void refreshReports() {
        try {
            business.InvoiceListControl invoiceControl = business.DIContainer.getInstance().getInvoiceListControl();
            
            // Get statistics
            int totalCount = invoiceControl.getTotalInvoiceCount();
            double totalRevenue = invoiceControl.getTotalRevenue();
            double averageRevenue = invoiceControl.getAverageRevenue();
            
            // Update labels
            totalInvoicesReportLabel.setText("Total Invoices: " + totalCount);
            totalRevenueReportLabel.setText(String.format("Total Revenue: %.2f VND", totalRevenue));
            averageRevenueLabel.setText(String.format("Average Revenue: %.2f VND", averageRevenue));
            
            // Get invoice counts by type
            var allInvoices = invoiceControl.getAllInvoices();
            long hourlyCount = business.StatisticsService.countInvoicesByType(allInvoices, "hourly");
            long dailyCount = business.StatisticsService.countInvoicesByType(allInvoices, "daily");
            
            hourlyInvoiceCountLabel.setText("Hourly Invoices: " + hourlyCount);
            dailyInvoiceCountLabel.setText("Daily Invoices: " + dailyCount);
            
            statusLabel.setText("Reports refreshed successfully");
        } catch (Exception e) {
            statusLabel.setText("Error refreshing reports: " + e.getMessage());
        }
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    /**
     * Subscriber implementation - auto-update reports when data changes
     */
    @Override
    public void update(InvoiceListEvent event) {
        if (event != null) {
            switch (event.getEventType()) {
                case ITEM_ADDED:
                case ITEM_REMOVED:
                case ITEM_UPDATED:
                case LIST_REFRESHED:
                case LIST_CLEARED:
                    // Auto-refresh reports when data changes
                    javafx.application.Platform.runLater(this::refreshReports);
                    break;
                case STATISTICS_UPDATED:
                    // Handle real-time statistics updates
                    if (event.getData() instanceof StatisticsData) {
                        StatisticsData stats = (StatisticsData) event.getData();
                        javafx.application.Platform.runLater(() -> {
                            totalInvoicesReportLabel.setText("Total Invoices: " + stats.getTotalCount());
                            totalRevenueReportLabel.setText(String.format("Total Revenue: %.2f VND", stats.getTotalRevenue()));
                        });
                    }
                    break;
            }
        }
    }
}

/**
 * Application Settings Singleton
 */
class AppSettings {
    private static AppSettings instance;
    
    // Company settings
    private String companyName = "Invoice Manager Company";
    private String companyAddress = "123 Business Street";
    private String companyPhone = "0123-456-789";
    
    // Default settings
    private String defaultInvoiceType = "hourly";
    private double defaultHourlyRate = 100.0;
    private double defaultDailyRate = 500.0;
    
    private AppSettings() {}
    
    public static AppSettings getInstance() {
        if (instance == null) {
            instance = new AppSettings();
        }
        return instance;
    }
    
    // Getters and Setters
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    
    public String getCompanyAddress() { return companyAddress; }
    public void setCompanyAddress(String companyAddress) { this.companyAddress = companyAddress; }
    
    public String getCompanyPhone() { return companyPhone; }
    public void setCompanyPhone(String companyPhone) { this.companyPhone = companyPhone; }
    
    public String getDefaultInvoiceType() { return defaultInvoiceType; }
    public void setDefaultInvoiceType(String defaultInvoiceType) { this.defaultInvoiceType = defaultInvoiceType; }
    
    public double getDefaultHourlyRate() { return defaultHourlyRate; }
    public void setDefaultHourlyRate(double defaultHourlyRate) { this.defaultHourlyRate = defaultHourlyRate; }
    
    public double getDefaultDailyRate() { return defaultDailyRate; }
    public void setDefaultDailyRate(double defaultDailyRate) { this.defaultDailyRate = defaultDailyRate; }
    
    public void resetToDefaults() {
        companyName = "Invoice Manager Company";
        companyAddress = "123 Business Street";
        companyPhone = "0123-456-789";
        defaultInvoiceType = "hourly";
        defaultHourlyRate = 100.0;
        defaultDailyRate = 500.0;
    }
    
    public void saveToFile() {
        // TODO: Implement file saving for persistence
        System.out.println("Settings saved (file persistence not implemented yet)");
    }
}