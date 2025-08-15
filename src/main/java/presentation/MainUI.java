package presentation;


import business.DeleteInvoice.DeleteInvoiceUseCase;
import java.net.URL;
import java.util.ResourceBundle;

import business.AddInvoice.AddInvoiceUseCase;
import business.AddInvoice.InvoiceTypeListUseCase;
import business.DeleteInvoice.DeleteInvoiceUseCase;
import business.InvoiceViewItem;
import business.InvoiceViewModel;
import business.ShowInvoiceList.ShowInvoiceListUseCase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class MainUI implements Initializable {
    // Biến lưu lại ID của hóa đơn đang chọn để chỉnh sửa
    private String selectedInvoiceId = null;
    @FXML
    private void initializeTableSelection() {
        invoiceTable.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                selectedInvoiceId = newItem.getId(); // Lưu lại ID
                customerField.setText(newItem.getCustomer());
                roomField.setText(newItem.getRoom_id());
                unitPriceField.setText(String.valueOf(newItem.getTotal()));
                typeComboBox.setValue(newItem.getType());
                // Giả sử ngày, giờ, ngày cũng cần điền
                // Nếu có trường date, hour, day thì điền tiếp
                // hourField.setText(...); dayField.setText(...);
            }
        });
    }

    // Controller SearchBar từ fx:include
    @FXML private SearchUI searchBarController;

    // ====== Table (Show list) ======
    @FXML private TableView<InvoiceViewItem> invoiceTable;
    @FXML private TableColumn<InvoiceViewItem, Integer> sttColumn;
    @FXML private TableColumn<InvoiceViewItem, String> idColumn, customerColumn, dateColumn, roomColumn, typeColumn;
    @FXML private TableColumn<InvoiceViewItem, Double> totalColumn;
    @FXML private Button refreshButton;

    // ====== Form (Add invoice) ======
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField customerField;
    @FXML private TextField dayField;
    @FXML private TextField roomField;
    @FXML private TextField hourField;
    @FXML private TextField unitPriceField;
    @FXML private Button addButton;

    // ====== Status / labels ======
    @FXML private Label statusLabel;
    @FXML private Label dayLabel;
    @FXML private Label hourLabel;

    // ====== Controller con từ fx:include ======
    // LƯU Ý: trong UI.fxml, <fx:include source="AddButton.fxml" fx:id="addBtn"/>
    // => để lấy được controller con, đặt field tên addBtnController
    @FXML private AddUI addBtnController;
    
    @FXML private DeleteUI deleteBtnController;   // lấy controller con từ <fx:include fx:id="deleteBtn">


    // ====== ViewModel & Use Cases (được inject từ composition root) ======
    private final InvoiceViewModel viewModel = new InvoiceViewModel();
    private ShowInvoiceListUseCase showInvoiceListUseCase;
    private AddInvoiceUseCase addInvoiceUseCase;
    private InvoiceTypeListUseCase invoiceTypeListUseCase;

    private DeleteInvoiceUseCase deleteInvoiceUseCase;
    public void setDeleteInvoiceUseCase(DeleteInvoiceUseCase uc) { this.deleteInvoiceUseCase = uc; }


    // ====== Controller con tự quản (không cần FXML) ======
    private final ShowListController showListController = new ShowListController();

    // --- Inject từ ngoài (composition root) ---
    public void setDependencies(
            ShowInvoiceListUseCase showUC,
            AddInvoiceUseCase addUC,
            InvoiceTypeListUseCase typeUC
    ) {
        this.showInvoiceListUseCase = showUC;
        this.addInvoiceUseCase = addUC;
        this.invoiceTypeListUseCase = typeUC;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (refreshButton != null) {
            refreshButton.setOnAction(this::refreshTable);
        }
        initializeTableSelection();
    }

    /** Gọi sau khi đã setDependencies(...) (từ Application/Bootstrap) */
    public void bootstrap() {
        // Truyền TableView cho SearchUI để enable chức năng tìm kiếm
        if (searchBarController != null) {
            searchBarController.setInvoiceTable(invoiceTable);
        } else {
            System.err.println("Không lấy được SearchUI từ fx:include. Kiểm tra SearchBar.fxml và UI.fxml.");
        }
        // 1) Kiểm tra đã DI đủ chưa
        if (showInvoiceListUseCase == null || addInvoiceUseCase == null || invoiceTypeListUseCase == null) {
            throw new IllegalStateException(
                "UIController chưa được inject đầy đủ use cases. Hãy gọi setDependencies(...) trước bootstrap().");
        }
        if (addBtnController == null) {
            throw new IllegalStateException(
                "Không lấy được AddController từ fx:include. Kiểm tra UI.fxml: fx:include phải có fx:id='addBtn' và AddButton.fxml có fx:controller.");
        }

        // 2) Khởi tạo controller con: ADD
        addBtnController.init(
            viewModel,
            addInvoiceUseCase,
            invoiceTypeListUseCase,
            // Tham chiếu control cha:
            typeComboBox, customerField, roomField, unitPriceField, hourField, dayField,
            hourLabel, dayLabel, statusLabel,
            // callback sau khi thêm thành công -> reload list
            this::reloadInvoiceList
        );
        addBtnController.prepare(); // load loại HĐ + toggle input

        // 3) Khởi tạo controller con: SHOW LIST
        showListController.init(
            viewModel,
            showInvoiceListUseCase,
            invoiceTable, sttColumn, idColumn, customerColumn, dateColumn, roomColumn, typeColumn, totalColumn,
            statusLabel
        );

        // 2b) Khởi tạo controller con: DELETE
        if (deleteBtnController == null) {
            throw new IllegalStateException(
                "Không lấy được DeleteController. Kiểm tra UI.fxml: <fx:include source=\"DeleteButton.fxml\" fx:id=\"deleteBtn\"/> và file con có fx:controller."
            );
        }
        if (deleteInvoiceUseCase == null) {
            throw new IllegalStateException(
                "Chưa inject DeleteInvoiceUseCase. Hãy gọi uiController.setDeleteInvoiceUseCase(...) trong App.java trước bootstrap()."
            );
        }
        deleteBtnController.init(
            deleteInvoiceUseCase,
            invoiceTable,          // để biết dòng nào đang chọn
            statusLabel,           // hiển thị trạng thái
            this::reloadInvoiceList// callback reload sau khi xóa thành công
        );


        // 4) Lần đầu tải danh sách
        reloadInvoiceList();
    }

    // ====== Event từ UI.fxml ======
    @FXML
    private void refreshTable(ActionEvent e) {
        reloadInvoiceList();
    }

    // ====== Điều phối ======
    private void reloadInvoiceList() {
        showListController.loadAndDisplay();
    }
}