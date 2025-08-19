package presentation.View.InvoiceList;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
import java.util.List;

// import business.DTO.InvoiceViewDTO;
import presentation.Model.InvoiceViewModel;
import presentation.Subscriber;
import presentation.Controller.InvoiceViewItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InvoiceTableView implements Subscriber {

    @FXML private TableView<InvoiceViewItem> invoiceTable;
    @FXML private TableColumn<InvoiceViewItem, Integer> sttColumn;
    @FXML private TableColumn<InvoiceViewItem, String> idColumn, customerColumn, dateColumn, roomColumn, typeColumn;
    @FXML private TableColumn<InvoiceViewItem, Double> totalColumn;

    private InvoiceViewModel model;

    public void setModel(InvoiceViewModel model) {
        this.model = model;
        model.registerSubscriber(this);
        System.out.println("InvoiceTableView: Model set and subscriber registered.");
    }
    
    @Override
    public void update() {
        List<InvoiceViewItem> items = model.listitem;
        ObservableList<InvoiceViewItem> observableItems = FXCollections.observableArrayList(items);
        invoiceTable.setItems(observableItems);
        invoiceTable.refresh(); // Cập nhật lại 
        System.out.println("InvoiceTableView: View updated with new data.");
    }
    @FXML
    public void initialize() {
        // Khởi tạo các cột của bảng
        this.sttColumn.setCellValueFactory(new PropertyValueFactory<>("stt"));
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.roomColumn.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        this.typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    // private List<InvoiceViewItem> convert(List<InvoiceViewDTO> listDTO) {
    //     List<InvoiceViewItem> list = new ArrayList<>();
    //     SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    //     int stt = 1;
    //     for (InvoiceViewDTO dto : listDTO) {
    //         InvoiceViewItem item = new InvoiceViewItem();
    //         item.stt = stt++;
    //         item.id = dto.id;
    //         item.date = fmt.format(dto.date);
    //         item.customer = dto.customer;
    //         item.room_id = dto.room_id;
    //         item.type = dto.type;
    //         item.total = dto.total;
    //         list.add(item);
    //     }
    //     return list;
    // }
    
}
