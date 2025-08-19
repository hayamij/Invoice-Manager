package presentation.View.CRUD;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.collections.FXCollections;

import business.Controls.AddInvoice.AddInvoiceUseCase;
import business.DTO.AddInvoiceViewDTO;
import persistence.AddInvoice.AddInvoiceDAO;
import persistence.AddInvoice.AddInvoiceDAOGateway;
import presentation.Controller.AddInvoiceController;
import presentation.Controller.ShowInvoiceListController;

import java.util.Date;

public class InvoiceFormView {

    @FXML private Button addButton;
    @FXML private TextField customerField;
    @FXML private TextField roomField;
    @FXML private TextField unitPriceField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField hourField;
    @FXML private TextField dayField;

    private ShowInvoiceListController showInvoiceListController;
    private AddInvoiceController addInvoiceController;

    @FXML
    public void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList(
            "Hourly Invoice", 
            "Daily Invoice"
        ));
        typeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            toggleFieldsBasedOnType(newVal);
        });
        // Initialize AddInvoiceController with DAO
        AddInvoiceDAOGateway addInvoiceDAO = new AddInvoiceDAO();
        addInvoiceController = new AddInvoiceController(addInvoiceDAO);
    }
    public void setShowInvoiceListController(ShowInvoiceListController controller) {
        this.showInvoiceListController = controller;
    }

    @FXML
    void addInvoice(ActionEvent event) {
        try {
            // Validate input
            if (!validateInput()) {
                return;
            }
            AddInvoiceViewDTO dto = createDTOFromForm();
            // Gọi UseCase để thêm hóa đơn
            boolean success = addInvoiceController.execute(dto);
            if (success) {
                showSuccessAlert("Thêm hóa đơn thành côngg!");
                clearForm();
                // Refresh danh sách hóa đơn
                if (showInvoiceListController != null) {
                    showInvoiceListController.execute();
                }
            } else {
                showErrorAlert("Thêm hóa đơn thất bại!", "Vui lòng kiểm tra lại thông tin.");
            }
        } catch (Exception e) {
            showErrorAlert("Lỗi hệ thống!", "Đã xảy ra lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();
        if (customerField.getText().trim().isEmpty()) {
            errors.append("- Tên khách hàng không được để trống\n");
        }
        if (roomField.getText().trim().isEmpty()) {
            errors.append("- Số phòng không được để trống\n");
        }
        if (unitPriceField.getText().trim().isEmpty()) {
            errors.append("- Đơn giá không được để trống\n");
        } else {
            try {
                double price = Double.parseDouble(unitPriceField.getText().trim());
                if (price <= 0) {
                    errors.append("- Đơn giá phải lớn hơn 0\n");
                }
            } catch (NumberFormatException e) {
                errors.append("- Đơn giá phải là số hợp lệ\n");
            }
        }
        if (typeComboBox.getSelectionModel().getSelectedItem() == null) {
            errors.append("- Vui lòng chọn loại hóa đơn\n");
        } else {
            String type = typeComboBox.getSelectionModel().getSelectedItem();
            if ("Hourly Invoice".equals(type)) {
                if (hourField.getText().trim().isEmpty()) {
                    errors.append("- Số giờ không được để trống với hóa đơn theo giờ\n");
                } else {
                    try {
                        int hour = Integer.parseInt(hourField.getText().trim());
                        if (hour < 0) {
                            errors.append("- Số giờ không được âm\n");
                        }
                    } catch (NumberFormatException e) {
                        errors.append("- Số giờ phải là số nguyên hợp lệ\n");
                    }
                }
            } else if ("Daily Invoice".equals(type)) {
                if (dayField.getText().trim().isEmpty()) {
                    errors.append("- Số ngày không được để trống với hóa đơn theo ngày\n");
                } else {
                    try {
                        int day = Integer.parseInt(dayField.getText().trim());
                        if (day < 0) {
                            errors.append("- Số ngày không được âm\n");
                        }
                    } catch (NumberFormatException e) {
                        errors.append("- Số ngày phải là số nguyên hợp lệ\n");
                    }
                }
            }
        }
        if (errors.length() > 0) {
            showErrorAlert("Dữ liệu không hợp lệ!", errors.toString());
            return false;
        }
        return true;
    }

    private AddInvoiceViewDTO createDTOFromForm() {
        AddInvoiceViewDTO dto = new AddInvoiceViewDTO();
        dto.customer = customerField.getText().trim();
        dto.room_id = roomField.getText().trim();
        dto.unitPrice = Double.parseDouble(unitPriceField.getText().trim());
        dto.type = typeComboBox.getSelectionModel().getSelectedItem();
        dto.date = new Date(); // Sử dụng thời gian hiện tại
        if ("Hourly Invoice".equals(dto.type)) {
            dto.hour = Integer.parseInt(hourField.getText().trim());
            dto.day = 0; // Set default value
        } else if ("Daily Invoice".equals(dto.type)) {
            dto.day = Integer.parseInt(dayField.getText().trim());
            dto.hour = 0; // Set default value
        }
        return dto;
    }

    private void toggleFieldsBasedOnType(String selectedType) {
        if ("Hourly Invoice".equals(selectedType)) {
            hourField.setDisable(false);
            dayField.setDisable(true);
            dayField.clear();
        } else if ("Daily Invoice".equals(selectedType)) {
            hourField.setDisable(true);
            dayField.setDisable(false);
            hourField.clear();
        } else {
            hourField.setDisable(true);
            dayField.setDisable(true);
            hourField.clear();
            dayField.clear();
        }
    }

    private void clearForm() {
        customerField.clear();
        roomField.clear();
        unitPriceField.clear();
        hourField.clear();
        dayField.clear();
        typeComboBox.getSelectionModel().clearSelection();
        
        // Reset field states
        hourField.setDisable(false);
        dayField.setDisable(false);
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}