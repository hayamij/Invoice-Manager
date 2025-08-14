package presentation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import business.ShowInvoiceList.InvoiceViewDTO;
import business.ShowInvoiceList.ShowInvoiceListUseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InvoiceListController {
    
    @FXML private TableView<InvoiceViewItem> invoiceTable;
    @FXML private TableColumn<InvoiceViewItem, Integer> sttColumn;
    @FXML private TableColumn<InvoiceViewItem, String> idColumn, customerColumn, dateColumn, roomColumn, typeColumn;
    @FXML private TableColumn<InvoiceViewItem, Double> totalColumn;
    @FXML private Button refreshButton;
    @FXML private Label totalInvoicesLabel;
    @FXML private Label totalAmountLabel;
    @FXML private Button monthlyStatsButton;

    private InvoiceViewModel viewModel;
    private ShowInvoiceListUseCase showInvoiceListUseCase;
    private Label statusLabel;
    private MainController mainController;

    public void execute() {
        if (showInvoiceListUseCase != null) {
            List<InvoiceViewDTO> invoiceList = showInvoiceListUseCase.execute();
            List<InvoiceViewItem> invoicePresentation = this.convertToPresentation(invoiceList);
            viewModel.invoiceItems = invoicePresentation;
            updateStatistics();
        }
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
        System.out.println("InvoiceViewItem count: " + list.size());
        return list;
    }

    public void displayInvoices() {
        if (viewModel != null && viewModel.invoiceItems != null) {
            ObservableList<InvoiceViewItem> items = FXCollections.observableArrayList(viewModel.invoiceItems);
            invoiceTable.setItems(items);
            
            // Set up columns
            sttColumn.setCellValueFactory(new PropertyValueFactory<>("stt"));
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            roomColumn.setCellValueFactory(new PropertyValueFactory<>("room_id"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
            
            System.out.println("TableView items: " + items.size());
        }
    }

    private void updateStatistics() {
        if (viewModel != null && viewModel.invoiceItems != null) {
            int totalInvoices = viewModel.invoiceItems.size();
            double totalAmount = viewModel.invoiceItems.stream()
                .mapToDouble(item -> item.total)
                .sum();
            
            totalInvoicesLabel.setText("Tổng số hóa đơn: " + totalInvoices);
            totalAmountLabel.setText(String.format("Tổng doanh thu: %.0f VND", totalAmount));
        }
    }

    @FXML
    void refreshTable(ActionEvent event) {
        execute();
        displayInvoices();
        if (statusLabel != null) {
            statusLabel.setText("Đã làm mới danh sách hóa đơn.");
        }
        System.out.println("Refresh button clicked, invoice list refreshed.");
    }

    @FXML
    void showMonthlyStats(ActionEvent event) {
        // TODO: Implement monthly statistics functionality
        if (statusLabel != null) {
            statusLabel.setText("Chức năng thống kê đang được phát triển.");
        }
    }

    // Setters for dependency injection
    public void setViewModel(InvoiceViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setShowInvoiceListUseCase(ShowInvoiceListUseCase showInvoiceListUseCase) {
        this.showInvoiceListUseCase = showInvoiceListUseCase;
    }

    public void setStatusLabel(Label statusLabel) {
        this.statusLabel = statusLabel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}