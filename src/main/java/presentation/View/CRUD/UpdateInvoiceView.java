package presentation.View.CRUD;

import java.util.Date;

import business.DTO.UpdateInvoiceViewDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import persistence.UpdateInvoice.UpdateInvoiceDAO;
import persistence.UpdateInvoice.UpdateInvoiceDAOGateway;
import presentation.Controller.UpdateInvoiceController;
import presentation.View.InvoiceList.InvoiceTableView;

public class UpdateInvoiceView {

    @FXML
    private Button updateButton;
    private InvoiceFormView invoiceFormView;
    private InvoiceTableView invoiceTableView;

    public void setInvoiceFormView(InvoiceFormView invoiceFormView) {
        this.invoiceFormView = invoiceFormView;
    }
    public void setInvoiceTableView(InvoiceTableView invoiceTableView) {
        this.invoiceTableView = invoiceTableView;
    }
    
    @FXML
    public void updateInvoice(ActionEvent event) {
        String ID = null;
        Date date = null;
        if (invoiceTableView != null) {
            ID = invoiceTableView.getSelectedInvoiceId();
            date = invoiceTableView.getDateFromSelectedInvoice();
        }
        if (invoiceFormView != null) {
            UpdateInvoiceViewDTO dto = new UpdateInvoiceViewDTO();
            dto.id = ID;
            dto.customer = invoiceFormView.getCustomer();
            dto.room_id = invoiceFormView.getRoomId();
            dto.date = date; // Assuming you want to set the current date
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
                // Hiển thị thông báo thành công
                System.out.println("Invoice updated successfully!");
            } else {
                // Hiển thị thông báo lỗi
                System.out.println("Failed to update invoice.");
            }
        }
        System.out.println("update button clicked");
    }
}
