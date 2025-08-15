package presentation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import business.ShowInvoiceList.InvoiceViewDTO;
import business.ShowInvoiceList.ShowInvoiceListUseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShowListController {

    private InvoiceViewModel viewModel;
    private ShowInvoiceListUseCase showInvoiceListUseCase;

    private TableView<InvoiceViewItem> invoiceTable;
    private TableColumn<InvoiceViewItem, Integer> sttColumn;
    private TableColumn<InvoiceViewItem, String> idColumn, customerColumn, dateColumn, roomColumn, typeColumn;
    private TableColumn<InvoiceViewItem, Double> totalColumn;
    private Label statusLabel;

    public void init(
            InvoiceViewModel viewModel,
            ShowInvoiceListUseCase showInvoiceListUseCase,
            TableView<InvoiceViewItem> invoiceTable,
            TableColumn<InvoiceViewItem, Integer> sttColumn,
            TableColumn<InvoiceViewItem, String> idColumn,
            TableColumn<InvoiceViewItem, String> customerColumn,
            TableColumn<InvoiceViewItem, String> dateColumn,
            TableColumn<InvoiceViewItem, String> roomColumn,
            TableColumn<InvoiceViewItem, String> typeColumn,
            TableColumn<InvoiceViewItem, Double> totalColumn,
            Label statusLabel
    ) {
        this.viewModel = viewModel;
        this.showInvoiceListUseCase = showInvoiceListUseCase;
        this.invoiceTable = invoiceTable;
        this.sttColumn = sttColumn;
        this.idColumn = idColumn;
        this.customerColumn = customerColumn;
        this.dateColumn = dateColumn;
        this.roomColumn = roomColumn;
        this.typeColumn = typeColumn;
        this.totalColumn = totalColumn;
        this.statusLabel = statusLabel;

        this.sttColumn.setCellValueFactory(new PropertyValueFactory<>("stt"));
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.roomColumn.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        this.typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    public void loadAndDisplay() {
        List<InvoiceViewDTO> dtoList = showInvoiceListUseCase.execute();
        viewModel.invoiceItems = convert(dtoList);

        ObservableList<InvoiceViewItem> items = FXCollections.observableArrayList(viewModel.invoiceItems);
        invoiceTable.setItems(items);
        if (statusLabel != null) statusLabel.setText("Đã tải " + items.size() + " hóa đơn.");
    }

    private List<InvoiceViewItem> convert(List<InvoiceViewDTO> listDTO) {
        List<InvoiceViewItem> list = new ArrayList<>();
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
        return list;
    }
}