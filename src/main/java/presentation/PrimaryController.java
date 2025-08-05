package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class PrimaryController implements Initializable {

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
    @FXML private TableView<?> invoiceTable;
    @FXML private TableColumn<?, ?> idColumn;
    @FXML private TableColumn<?, ?> dateColumn;
    @FXML private TableColumn<?, ?> customerColumn;
    @FXML private TableColumn<?, ?> roomColumn;
    @FXML private TableColumn<?, ?> typeColumn;
    @FXML private TableColumn<?, ?> unitPriceColumn;
    @FXML private TableColumn<?, ?> hourColumn;
    @FXML private TableColumn<?, ?> dayColumn;
    @FXML private TableColumn<?, ?> totalColumn;
    
    // Labels
    @FXML private Label statusLabel;
    @FXML private Label totalInvoicesLabel;
    @FXML private Label totalAmountLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize ComboBox
        typeComboBox.setItems(FXCollections.observableArrayList("hourly", "daily"));
        
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
            }
        });
        
        // Set default selection to "hourly"
        typeComboBox.setValue("hourly");
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
        statusLabel.setText("");
        // TODO: Implement refresh table logic
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
