package presentation;

import java.util.List;

import business.InvoiceViewItem;
import business.SearchInvoice.SearchInvoiceUseCase;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import persistence.InvoiceDAO;
import persistence.InvoiceDTO;

public class SearchUI {

    @FXML
    private Button clearSearchButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    public void initialize() {
        // Gán sự kiện Enter cho searchField
        searchField.setOnAction(event -> searchInvoices(null));
    }

    private TableView<InvoiceViewItem> invoiceTable;

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final SearchInvoiceUseCase searchUseCase = new SearchInvoiceUseCase();

    // Gọi từ MainUI sau khi TableView đã khởi tạo
    public void setInvoiceTable(TableView<InvoiceViewItem> table) {
        this.invoiceTable = table;
    }

    @FXML
    void clearSearch(ActionEvent event) {
        searchField.clear();
        showAllInvoices();
    }

    @FXML
    void searchInvoices(ActionEvent event) {
        if (invoiceTable == null) {
            System.err.println("invoiceTable chưa được gán từ MainUI!");
            return;
        }
        String keyword = searchField.getText();
        List<InvoiceDTO> allInvoices = invoiceDAO.getAll();
        List<InvoiceDTO> filtered = searchUseCase.filterInvoices(allInvoices, keyword);
        List<InvoiceViewItem> items = convertToViewItems(filtered);
        invoiceTable.setItems(FXCollections.observableArrayList(items));
    }

    private void showAllInvoices() {
        if (invoiceTable == null) return;
        List<InvoiceDTO> allInvoices = invoiceDAO.getAll();
        List<InvoiceViewItem> items = convertToViewItems(allInvoices);
        invoiceTable.setItems(FXCollections.observableArrayList(items));
    }

    private List<InvoiceViewItem> convertToViewItems(List<InvoiceDTO> dtos) {
        List<InvoiceViewItem> items = new java.util.ArrayList<>();
        int stt = 1;
        for (InvoiceDTO dto : dtos) {
            InvoiceViewItem item = new InvoiceViewItem();
            item.stt = stt++;
            item.id = dto.id;
            item.date = dto.date != null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(dto.date) : "";
            item.customer = dto.customer;
            item.room_id = dto.room_id;
            item.type = dto.type;
            item.total = (dto.unitPrice == null) ? 0.0 : dto.unitPrice.doubleValue();
            items.add(item);
        }
        return items;
    }
}
