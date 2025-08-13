package presentation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import business.AddInvoice.InvoiceTypeListUseCase;
import business.AddInvoice.InvoiceTypeViewDTO;
import business.AddInvoice.AddInvoiceUseCase;
import business.ShowInvoiceList.InvoiceViewDTO;
import business.ShowInvoiceList.ShowInvoiceListUseCase;
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
import persistence.InvoiceDTO;

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
        List<InvoiceViewDTO> invoiceList = showInvoiceListUseCase.execute();
        List<InvoiceViewItem> invoicePresentation = this.convertToPresentation(invoiceList);

        viewModel.invoiceItems = invoicePresentation;
    }

    private List<InvoiceViewItem> convertToPresentation(List<InvoiceViewDTO> listDTO) {
		List<InvoiceViewItem> list = new ArrayList<InvoiceViewItem>();
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		int stt = 1;
		for (InvoiceViewDTO dto : listDTO) {
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

    public void setViewModel(InvoiceViewModel model) {
        this.viewModel = model;

    }

    public void setShowInvoiceListUseCase(ShowInvoiceListUseCase showInvoiceListUseCase) {
        this.showInvoiceListUseCase = showInvoiceListUseCase;
    }

    @FXML
    void refreshTable(ActionEvent event) {
        showInvoiceListUseCase.execute();
        statusLabel.setText("Đã làm mới danh sách hóa đơn.");
        System.out.println("Refresh button clicked, invoice list refreshed.");
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
    
    private InvoiceTypeListUseCase invoiceTypeListUseCase;
    private AddInvoiceUseCase addInvoiceUseCase = new AddInvoiceUseCase(new persistence.InvoiceDAO());


    public void setInvoiceTypeListUseCase(InvoiceTypeListUseCase useCase) {
        this.invoiceTypeListUseCase = useCase;
    }

    public void loadInvoiceTypes() {
        if (invoiceTypeListUseCase != null) {
            List<InvoiceTypeViewDTO> typeDTOs = invoiceTypeListUseCase.execute();
            List<String> typeNames = new ArrayList<>();
            for (InvoiceTypeViewDTO dto : typeDTOs) {
                typeNames.add(dto.getTypeName());
            }
            typeComboBox.setItems(FXCollections.observableArrayList(typeNames));
            // Set default value là loại đầu tiên
            if (!typeNames.isEmpty()) {
                typeComboBox.setValue(typeNames.get(0));
                updateInputFields(typeNames.get(0));
            }
            // Thêm listener cho combobox
            typeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                updateInputFields(newVal);
            });
        }
    }
    
    private String hourlyType = new business.HourlyInvoice().type();
    private String dailyType = new business.DailyInvoice().type();

    private void updateInputFields(String type) {
        if (type.equals(hourlyType)) {
            dayField.setVisible(false);
            hourField.setVisible(true);
        } else if (type.equals(dailyType)) {
            hourField.setVisible(false);
            dayField.setVisible(true);
        } else {
            dayField.setVisible(true);
            hourField.setVisible(true);
        }
    }
    
    @FXML
    void addInvoice(ActionEvent event) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        int nextId = viewModel.invoiceItems == null ? 1 : viewModel.invoiceItems.size() + 1;
        invoiceDTO.id = String.valueOf(nextId);
        invoiceDTO.date = new java.sql.Timestamp(System.currentTimeMillis());
        invoiceDTO.customer = customerField.getText();
        invoiceDTO.room_id = roomField.getText();
        invoiceDTO.unitPrice = Double.parseDouble(unitPriceField.getText());
        invoiceDTO.hour = hourField.getText().isEmpty() ? 0 : Integer.parseInt(hourField.getText());
        invoiceDTO.day = dayField.getText().isEmpty() ? 0 : Integer.parseInt(dayField.getText());
        invoiceDTO.type = typeComboBox.getValue();
        double total = invoiceDTO.unitPrice * (invoiceDTO.hour + invoiceDTO.day);
        if (total < 0) {
            statusLabel.setText("Thêm hóa đơn thất bại!");
            return;
        } else {
            statusLabel.setText("Thêm hóa đơn thành công!");
            addInvoiceUseCase.execute(invoiceDTO);
            execute();
            displayInvoices();
        }
        showInvoiceListUseCase.execute();
        System.out.println("Add button clicked, invoice added: " + invoiceDTO.id);
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
