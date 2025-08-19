package presentation.View.CRUD;

import java.util.Date;

import business.DTO.UpdateInvoiceViewDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import persistence.UpdateInvoice.UpdateInvoiceDAO;
import persistence.UpdateInvoice.UpdateInvoiceDAOGateway;
import presentation.Controller.UpdateInvoiceController;
import presentation.View.InvoiceList.InvoiceTableView;

public class UpdateInvoiceView {

    @FXML
    private Button updateButton;
    private InvoiceFormView invoiceFormView;
    private InvoiceTableView invoiceTableView;
    private Runnable onSuccessCallback; // Callback để refresh statistics

    public void setInvoiceFormView(InvoiceFormView invoiceFormView) {
        this.invoiceFormView = invoiceFormView;
    }
    public void setInvoiceTableView(InvoiceTableView invoiceTableView) {
        this.invoiceTableView = invoiceTableView;
    }
    
    public void setOnSuccessCallback(Runnable callback) {
        this.onSuccessCallback = callback;
    }
    
    @FXML
    public void updateInvoice(ActionEvent event) {
        try {
            String ID = null;
            Date date = null;
            
            if (invoiceTableView != null) {
                ID = invoiceTableView.getSelectedInvoiceId();
                date = invoiceTableView.getDateFromSelectedInvoice();
            }

            // Kiểm tra xem có hóa đơn nào được chọn không
            if (ID == null || ID.isEmpty()) {
                showErrorAlert("Lỗi!", "Vui lòng chọn một hóa đơn để cập nhật.");
                return;
            }

            // Validate form data
            if (!validateFormData()) {
                return;
            }
            
            if (invoiceFormView != null) {
                UpdateInvoiceViewDTO dto = new UpdateInvoiceViewDTO();
                dto.id = ID;
                dto.customer = invoiceFormView.getCustomer();
                dto.room_id = invoiceFormView.getRoomId();
                dto.date = date;
                dto.unitPrice = Double.parseDouble(invoiceFormView.getUnitPrice());
                dto.type = invoiceFormView.getType();
                dto.hour = invoiceFormView.getHour();
                dto.day = invoiceFormView.getDay();

                UpdateInvoiceDAOGateway dao = new UpdateInvoiceDAO();
                UpdateInvoiceController updateInvoiceController = new UpdateInvoiceController(dao);
                
                System.out.println("Update DTO: id=" + dto.id + ", date=" + dto.date + ", customer=" + dto.customer + ", room_id=" + dto.room_id +
                       ", unitPrice=" + dto.unitPrice + ", type=" + dto.type + ", hour=" + dto.hour + ", day=" + dto.day);
                
                boolean success = updateInvoiceController.updateInvoice(dto);
                if(success) {
                    showSuccessAlert("Cập nhật hóa đơn thành công!");
                    System.out.println("Invoice updated successfully!");
                    // Refresh statistics nếu có callback
                    if (onSuccessCallback != null) {
                        onSuccessCallback.run();
                    }
                } else {
                    showErrorAlert("Cập nhật thất bại!", "Không thể cập nhật hóa đơn. Vui lòng thử lại.");
                    System.out.println("Failed to update invoice.");
                }
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Lỗi định dạng!", "Đơn giá phải là số hợp lệ.");
        } catch (Exception e) {
            showErrorAlert("Lỗi hệ thống!", "Đã xảy ra lỗi: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("update button clicked");
    }

    private boolean validateFormData() {
        if (invoiceFormView == null) {
            showErrorAlert("Lỗi hệ thống!", "Form dữ liệu không khả dụng.");
            return false;
        }

        StringBuilder errors = new StringBuilder();
        
        if (invoiceFormView.getCustomer().isEmpty()) {
            errors.append("- Tên khách hàng không được để trống\n");
        }
        if (invoiceFormView.getRoomId().isEmpty()) {
            errors.append("- Số phòng không được để trống\n");
        }
        if (invoiceFormView.getUnitPrice().isEmpty()) {
            errors.append("- Đơn giá không được để trống\n");
        } else {
            try {
                double price = Double.parseDouble(invoiceFormView.getUnitPrice());
                if (price <= 0) {
                    errors.append("- Đơn giá phải lớn hơn 0\n");
                }
            } catch (NumberFormatException e) {
                errors.append("- Đơn giá phải là số hợp lệ\n");
            }
        }
        if (invoiceFormView.getType() == null || invoiceFormView.getType().isEmpty()) {
            errors.append("- Vui lòng chọn loại hóa đơn\n");
        } else {
            String type = invoiceFormView.getType();
            if ("Hourly Invoice".equals(type)) {
                int hour = invoiceFormView.getHour();
                if (hour < 0) {
                    errors.append("- Số giờ không được âm\n");
                }
            } else if ("Daily Invoice".equals(type)) {
                int day = invoiceFormView.getDay();
                if (day < 0) {
                    errors.append("- Số ngày không được âm\n");
                }
            }
        }

        if (errors.length() > 0) {
            showErrorAlert("Dữ liệu không hợp lệ!", errors.toString());
            return false;
        }
        return true;
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