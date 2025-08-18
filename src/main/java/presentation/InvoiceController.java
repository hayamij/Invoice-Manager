package presentation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import business.Models.InvoiceModel;
import business.Controls.ShowInvoiceList.ShowInvoiceListUseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class InvoiceController {
    
    // ************************************************
    //
    //                  SHOW INVOICE LIST
    //
    // ************************************************

    @FXML private TableView<InvoiceViewItem> invoiceTable;
    @FXML private TableColumn<InvoiceViewItem, Integer> sttColumn;
    @FXML private TableColumn<InvoiceViewItem, String> idColumn, customerColumn, dateColumn, roomColumn, typeColumn;
    @FXML private TableColumn<InvoiceViewItem, Double> totalColumn;
    @FXML private Button refreshButton;
    @FXML private Label dayLabel;
    @FXML private Label hourLabel;
    @FXML private Label statusLabel;

    private InvoiceViewModel viewModel;
    private ShowInvoiceListUseCase showInvoiceListUseCase;

    public InvoiceController() {

    }

    public InvoiceController(InvoiceViewModel viewModel, ShowInvoiceListUseCase showInvoiceListUseCase) {
        this.viewModel = viewModel;
        this.showInvoiceListUseCase = showInvoiceListUseCase;
    }

    public void execute(){
        List<InvoiceModel> invoiceList = showInvoiceListUseCase.execute();
        List<InvoiceViewItem> invoicePresentation = this.convertToPresentation(invoiceList);

        viewModel.invoiceItems = invoicePresentation;
    }

    private List<InvoiceViewItem> convertToPresentation(List<InvoiceModel> listDTO) {
		List<InvoiceViewItem> list = new ArrayList<InvoiceViewItem>();
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		int stt = 1;
		for (InvoiceModel dto : listDTO) {
			InvoiceViewItem item = new InvoiceViewItem();
            item.stt = stt++;
            item.id = dto.id;
            item.date = fmt.format(dto.date);
            item.customer = dto.customer;
            item.room_id = dto.room_id;
            item.type = dto.type;
            item.total = dto.total;
            list.add(item);

		}
        System.out.println("InvoiceViewItem count: " + list.size()); // Log kiểm tra dữ liệu
		return list;

	}

    @FXML
    void refreshTable(ActionEvent event) {

    }

    public void displayInvoices() {
        ObservableList<InvoiceViewItem> items = FXCollections.observableArrayList(viewModel.invoiceItems);
        invoiceTable.setItems(items);
        sttColumn.setCellValueFactory(new PropertyValueFactory<>("stt"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        System.out.println("TableView items: " + items.size()); // Log kiểm tra dữ liệu
    }

    public void setViewModel(InvoiceViewModel model) {
        this.viewModel = model;

    }

    public void setShowInvoiceListUseCase(ShowInvoiceListUseCase showInvoiceListUseCase) {
        this.showInvoiceListUseCase = showInvoiceListUseCase;
    }


    // ************************************************
    //
    //                  ADD INVOICE
    //
    // ************************************************

    @FXML private Button addButton;

    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField customerField;
    @FXML private TextField dayField;
    @FXML private TextField roomField;
    @FXML private TextField hourField;
    @FXML private TextField unitPriceField;    
    
    @FXML
    void addInvoice(ActionEvent event) {

    }
    
    // ************************************************
    //
    //                  UPDATE INVOICE
    //
    // ************************************************

    @FXML private Button updateButton;

    @FXML
    void updateInvoice(ActionEvent event) {

    }    

    // ************************************************
    //
    //                 DELETE INVOICE
    //
    // ************************************************

    @FXML private Button deleteButton;  

    @FXML
    void deleteInvoice(ActionEvent event) {

    }  

    // ************************************************
    //
    //                INVOICE STATISTICS
    //
    // ************************************************

    @FXML private TableColumn<?, ?> averageColumn;
    @FXML private TableView<?> averageTable;
    @FXML private Label dailyLabel;
    @FXML private Label hourlyLabel;
    @FXML private TableColumn<?, ?> monthColumn;
    @FXML private TableColumn<?, ?> yearColumn;

    @FXML private Label totalAmountLabel;
    @FXML private Label totalInvoicesLabel;
    
    @FXML private Button monthlyStatsButton;

    // ************************************************
    //
    //                  SEARCH INVOICE
    //
    // ************************************************
    
    @FXML private Button clearSearchButton;
    @FXML private Button searchButton;
    @FXML private TextField searchField;

    @FXML
    public void searchInvoices(ActionEvent event) {

    }
    @FXML
    void clearSearch(ActionEvent event) {

    }
}
