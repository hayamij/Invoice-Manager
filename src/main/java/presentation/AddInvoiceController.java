package presentation;

import java.util.ArrayList;
import java.util.List;

import business.AddInvoice.AddInvoiceItem;
import business.AddInvoice.AddInvoiceUseCase;
import business.AddInvoice.InvoiceTypeListUseCase;
import business.AddInvoice.InvoiceTypeViewDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import persistence.InvoiceDTO;

public class AddInvoiceController {
    
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField customerField;
    @FXML private TextField dayField;
    @FXML private TextField roomField;
    @FXML private TextField hourField;
    @FXML private TextField unitPriceField;    
    @FXML private Label dayLabel;
    @FXML private Label hourLabel;
    
    private InvoiceViewModel viewModel;
    private InvoiceTypeListUseCase invoiceTypeListUseCase;
    private AddInvoiceUseCase addInvoiceUseCase;
    private Label statusLabel;
    private MainController mainController;
    
    private final String hourlyType = new business.HourlyInvoice().type();
    private final String dailyType = new business.DailyInvoice().type();

    public void loadInvoiceTypes() {
        if (invoiceTypeListUseCase != null) {
            List<InvoiceTypeViewDTO> typeDTOs = invoiceTypeListUseCase.execute();
            List<String> typeNames = new ArrayList<>();
            for (InvoiceTypeViewDTO dto : typeDTOs) {
                typeNames.add(dto.getTypeName());
            }
            typeComboBox.setItems(FXCollections.observableArrayList(typeNames));
            
            // Set default value
            if (!typeNames.isEmpty()) {
                typeComboBox.setValue(typeNames.get(0));
                updateInputFields(typeNames.get(0));
            }
            
            // Add listener for combobox
            typeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                updateInputFields(newVal);
            });
        }
    }

    private void updateInputFields(String type) {
        if (type != null) {
            if (type.equals(hourlyType)) {
                dayField.setVisible(false);
                dayLabel.setVisible(false);
                hourField.setVisible(true);
                hourLabel.setVisible(true);
            } else if (type.equals(dailyType)) {
                hourField.setVisible(false);
                hourLabel.setVisible(false);
                dayField.setVisible(true);
                dayLabel.setVisible(true);
            } else {
                dayField.setVisible(true);
                dayLabel.setVisible(true);
                hourField.setVisible(true);
                hourLabel.setVisible(true);
            }
        }
    }
    
    @FXML
    void addInvoice(ActionEvent event) {
        try {
            InvoiceDTO invoiceDTO = createInvoiceDTO();
            // Validate input
            if (!validateInput(invoiceDTO)) {
                updateStatus("Dữ liệu nhập không hợp lệ!");
                return;
            }
            // Gán dữ liệu cho AddInvoiceItem
            AddInvoiceItem addItem = new AddInvoiceItem();
            addItem.customer = invoiceDTO.customer;
            addItem.room_id = invoiceDTO.room_id;
            addItem.date = new java.sql.Timestamp(System.currentTimeMillis());
            addItem.unitPrice = invoiceDTO.unitPrice;
            addItem.hour = invoiceDTO.hour;
            addItem.day = invoiceDTO.day;
            addItem.type = invoiceDTO.type;
            // Execute add invoice
            boolean success = addInvoiceUseCase.execute(addItem);
            if (success) {
                updateStatus("Thêm hóa đơn thành công!");
                clearForm();
                if (mainController != null) {
                    mainController.refreshInvoiceList();
                }
            } else {
                updateStatus("Thêm hóa đơn thất bại!");
            }
        } catch (Exception e) {
            updateStatus("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private InvoiceDTO createInvoiceDTO() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        int nextId = viewModel.invoiceItems == null ? 1 : viewModel.invoiceItems.size() + 1;
        invoiceDTO.id = String.valueOf(nextId);
        invoiceDTO.date = new java.sql.Timestamp(System.currentTimeMillis());
        invoiceDTO.customer = customerField.getText().trim();
        invoiceDTO.room_id = roomField.getText().trim();
        String unitPriceText = unitPriceField.getText().trim();
        invoiceDTO.unitPrice = unitPriceText.isEmpty() ? 0.0 : Double.parseDouble(unitPriceText);
        String hourText = hourField.getText().trim();
        String dayText = dayField.getText().trim();
        invoiceDTO.hour = hourText.isEmpty() ? 0 : Integer.parseInt(hourText);
        invoiceDTO.day = dayText.isEmpty() ? 0 : Integer.parseInt(dayText);
        // Đảm bảo type không bị null
        String typeValue = typeComboBox.getValue();
        if (typeValue == null && typeComboBox.getItems() != null && !typeComboBox.getItems().isEmpty()) {
            typeValue = typeComboBox.getItems().get(0);
        }
        invoiceDTO.type = typeValue;
        return invoiceDTO;
    }
    
    private boolean validateInput(InvoiceDTO invoiceDTO) {
        // Basic validation
        if (invoiceDTO.customer == null || invoiceDTO.customer.isEmpty()) {
            return false;
        }
        if (invoiceDTO.room_id == null || invoiceDTO.room_id.isEmpty()) {
            return false;
        }
        if (invoiceDTO.unitPrice <= 0) {
            return false;
        }
        if (invoiceDTO.type == null || invoiceDTO.type.isEmpty()) {
            return false;
        }
        
        // Type-specific validation
        if (invoiceDTO.type.equals(hourlyType) && invoiceDTO.hour <= 0) {
            return false;
        }
        if (invoiceDTO.type.equals(dailyType) && invoiceDTO.day <= 0) {
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        customerField.clear();
        roomField.clear();
        unitPriceField.clear();
        hourField.clear();
        dayField.clear();
        
        // Reset to first type if available
        if (typeComboBox.getItems() != null && !typeComboBox.getItems().isEmpty()) {
            typeComboBox.setValue(typeComboBox.getItems().get(0));
        }
    }
    
    @FXML
    void updateInvoice(ActionEvent event) {
        // TODO: Implement update functionality
        updateStatus("Chức năng cập nhật đang được phát triển.");
    }    

    @FXML
    void deleteInvoice(ActionEvent event) {
        // TODO: Implement delete functionality
        updateStatus("Chức năng xóa đang được phát triển.");
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

    public void setInvoiceTypeListUseCase(InvoiceTypeListUseCase invoiceTypeListUseCase) {
        this.invoiceTypeListUseCase = invoiceTypeListUseCase;
    }

    public void setAddInvoiceUseCase(AddInvoiceUseCase addInvoiceUseCase) {
        this.addInvoiceUseCase = addInvoiceUseCase;
    }

    public void setStatusLabel(Label statusLabel) {
        this.statusLabel = statusLabel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}