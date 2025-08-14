package presentation;

import presentation.DeleteUI;
import business.DeleteInvoice.DeleteInvoiceUseCase;

import java.net.URL;
import java.util.ResourceBundle;

import business.InvoiceViewItem;
import business.InvoiceViewModel;
import business.AddInvoice.AddInvoiceUseCase;
import business.AddInvoice.InvoiceTypeListUseCase;
import business.ShowInvoiceList.ShowInvoiceListUseCase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import presentation.AddUI;
import presentation.ShowListUI;

public class MainUI implements Initializable {

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
    private final ShowListUI showListController = new ShowListUI();

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
        // Tuyệt đối KHÔNG tạo DAO/use case ở đây.
        // initialize chỉ nên rất nhẹ, ví dụ: set prompt/tooltip nếu muốn.
        // Việc wiring và load data lần đầu sẽ thực hiện trong bootstrap()
        if (refreshButton != null) {
            refreshButton.setOnAction(this::refreshTable);
        }
    }

    /** Gọi sau khi đã setDependencies(...) (từ Application/Bootstrap) */
    public void bootstrap() {
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