package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import business.ShowInvoiceListUseCase;
import business.SearchInvoiceUseCase;
import business.UpdateInvoiceUseCase;
import business.DeleteInvoiceUseCase;
import business.MonthlyAverageInvoiceUseCase;
import business.ShowInvoiceTypeStatsUseCase;
import business.ShowInvoiceListUseCase.InvoiceDisplayData;
import business.SearchInvoiceUseCase.InvoiceSearchResult;

import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class PrimaryController {
    
    @FXML private TableView<InvoiceDisplayData> invoiceTable;
    @FXML private TableColumn<InvoiceDisplayData, String> idColumn, customerColumn, dateColumn, roomColumn, typeColumn, unitPriceColumn, hourColumn, dayColumn, totalColumn;
    @FXML private ListView<String> invoiceListView;
    @FXML private Label statusLabel, totalInvoicesLabel;
    @FXML private TextField searchField, customerField, roomField, unitPriceField, hourField, dayField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private Button statsButton;

    // Use Cases (Control)
    private ShowInvoiceListUseCase showInvoiceListUseCase = new ShowInvoiceListUseCase();
    private SearchInvoiceUseCase searchInvoiceUseCase = new SearchInvoiceUseCase();
    private UpdateInvoiceUseCase updateInvoiceUseCase;
    private business.AddInvoiceUseCase addInvoiceUseCase;
    private DeleteInvoiceUseCase deleteInvoiceUseCase;
    private ShowInvoiceTypeStatsUseCase showInvoiceTypeStatsUseCase;
    private MonthlyAverageInvoiceUseCase monthlyAverageInvoiceUseCase;

    public void initialize() {
        setupColumns();
        setupTypeComboBox();
        setupTableSelectionListener();
        loadData();
        updateInvoiceUseCase = new UpdateInvoiceUseCase(new persistence.InvoiceDAO());
        addInvoiceUseCase = new business.AddInvoiceUseCase(new persistence.InvoiceDAO());
        deleteInvoiceUseCase = new DeleteInvoiceUseCase(new persistence.InvoiceDAO());
        showInvoiceTypeStatsUseCase = new ShowInvoiceTypeStatsUseCase(new persistence.InvoiceDAO());
        monthlyAverageInvoiceUseCase = new MonthlyAverageInvoiceUseCase(new persistence.InvoiceDAO());
    }

    private void setupColumns() {
        if (idColumn != null) idColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getId()));
        if (customerColumn != null) customerColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCustomer()));
        if (dateColumn != null) dateColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDate()));
        if (roomColumn != null) roomColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getRoom()));
        if (typeColumn != null) typeColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getType()));
        if (unitPriceColumn != null) unitPriceColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUnitPrice()));
        if (hourColumn != null) hourColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getHour()));
        if (dayColumn != null) dayColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDay()));
        if (totalColumn != null) totalColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTotal()));
    }

    private void setupTypeComboBox() {
        if (typeComboBox != null) {
            typeComboBox.getItems().addAll("hourly", "daily");
            typeComboBox.setValue("hourly"); // Mặc định chọn hourly
            if (hourField != null) hourField.setVisible(true);
            if (dayField != null) dayField.setVisible(false);
            typeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null && (newVal.equals("daily"))) {
                    if (hourField != null) hourField.setVisible(false);
                    if (dayField != null) dayField.setVisible(true);
                } else {
                    if (hourField != null) hourField.setVisible(true);
                    if (dayField != null) dayField.setVisible(false);
                }
            });
        }
    }

    private void setupTableSelectionListener() {
        if (invoiceTable != null) {
            invoiceTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    loadInvoiceToEditFields(newSelection);
                }
            });
        }
    }

    private void loadInvoiceToEditFields(InvoiceDisplayData invoice) {
        if (customerField != null) customerField.setText(invoice.getCustomer());
        if (roomField != null) roomField.setText(invoice.getRoom());
        if (typeComboBox != null) typeComboBox.setValue(invoice.getType());
        if (unitPriceField != null) unitPriceField.setText(invoice.getUnitPrice());
        if (hourField != null) hourField.setText(invoice.getHour());
        if (dayField != null) dayField.setText(invoice.getDay());
        
        // Update status to show which invoice is being edited
        if (statusLabel != null) {
            statusLabel.setText("Editing Invoice ID: " + invoice.getId() + " - " + invoice.getCustomer());
        }
    }

    private void loadData() {
        try {
            List<InvoiceDisplayData> displayData = showInvoiceListUseCase.executeForUI();
            displayDataInTable(displayData);
            // Không cập nhật statusLabel ở đây để tránh ghi đè thông điệp hành động
            // if (statusLabel != null) statusLabel.setText("Loaded " + displayData.size() + " invoices");
        } catch (Exception e) {
            if (statusLabel != null) statusLabel.setText("Error: " + e.getMessage());
        }
    }

    private void searchData(String searchTerm) {
        try {
            List<InvoiceSearchResult> searchResults = searchInvoiceUseCase.executeForUI(searchTerm);
            List<InvoiceDisplayData> displayData = convertSearchResultsToDisplayData(searchResults);
            displayDataInTable(displayData);
            
            if (statusLabel != null) {
                if (searchTerm == null || searchTerm.trim().isEmpty()) {
                    statusLabel.setText("Showing all " + displayData.size() + " invoices");
                } else {
                    statusLabel.setText("Found " + displayData.size() + " invoices matching '" + searchTerm + "'");
                }
            }
        } catch (Exception e) {
            if (statusLabel != null) statusLabel.setText("Search error: " + e.getMessage());
        }
    }

    private void clearEditFields() {
        if (customerField != null) customerField.clear();
        if (roomField != null) roomField.clear();
        if (typeComboBox != null) typeComboBox.setValue(null);
        if (unitPriceField != null) unitPriceField.clear();
        if (hourField != null) hourField.clear();
        if (dayField != null) dayField.clear();
        if (invoiceTable != null) {
            invoiceTable.getSelectionModel().clearSelection();
        }
    }

    private void displayDataInTable(List<InvoiceDisplayData> displayData) {
        if (invoiceTable != null) invoiceTable.setItems(FXCollections.observableArrayList(displayData));
        if (totalInvoicesLabel != null) totalInvoicesLabel.setText("Total: " + displayData.size());
    }

    private List<InvoiceDisplayData> convertSearchResultsToDisplayData(List<InvoiceSearchResult> searchResults) {
        List<InvoiceDisplayData> displayData = new ArrayList<>();
        for (InvoiceSearchResult result : searchResults) {
            displayData.add(new InvoiceDisplayData(
                result.getId(), result.getCustomer(), result.getDate(), result.getRoom(),
                result.getType(), result.getUnitPrice(), result.getHour(), result.getDay(), result.getTotal()
            ));
        }
        return displayData;
    }

    // Event Handlers - buttons to trigger use cases
    @FXML void addInvoice(ActionEvent e) {
        try {
            boolean success = addInvoiceUseCase.addInvoice(
                customerField.getText(),
                roomField.getText(),
                typeComboBox.getValue(),
                unitPriceField.getText(),
                hourField.isVisible() ? hourField.getText() : "0",
                dayField.isVisible() ? dayField.getText() : "0"
            );
            if (success) {
                statusLabel.setText("Thêm hóa đơn thành công");
                loadData();
                clearEditFields();
            } else {
                statusLabel.setText("Vui lòng nhập đầy đủ thông tin");
            }
        } catch (IllegalArgumentException ex) {
            statusLabel.setText(ex.getMessage());
        }
    }
    @FXML void clearEditFields(ActionEvent e) {
        clearEditFields();
        statusLabel.setText("Đã xóa dữ liệu nhập");
    }
    @FXML void clearSearch(ActionEvent e) {
        if(searchField != null) searchField.clear();
        loadData();
        statusLabel.setText("Đã xóa từ khóa tìm kiếm và làm mới danh sách");
    }
    @FXML void deleteInvoice(ActionEvent e) {
        InvoiceDisplayData selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
        if (selectedInvoice == null) {
            statusLabel.setText("Vui lòng chọn hóa đơn để xóa");
            return;
        }
        boolean success = deleteInvoiceUseCase.deleteInvoice(selectedInvoice.getId());
        if (success) {
            statusLabel.setText("Xóa hóa đơn thành công");
            loadData();
            clearEditFields();
        } else {
            statusLabel.setText("Xóa hóa đơn thất bại");
        }
    }
    @FXML void refreshTable(ActionEvent e) {
        loadData();
        statusLabel.setText("Đã làm mới danh sách hóa đơn");
    }
    @FXML void searchInvoices(ActionEvent e) {
        String searchTerm = searchField != null ? searchField.getText() : "";
        searchData(searchTerm);
        statusLabel.setText("Đã tìm kiếm hóa đơn với từ khóa: '" + searchTerm + "'");
    }

    @FXML void updateInvoice(ActionEvent e) {
        InvoiceDisplayData selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
        if (selectedInvoice == null) {
            statusLabel.setText("Vui lòng chọn hóa đơn để cập nhật");
            return;
        }
        try {
            boolean success = updateInvoiceUseCase.updateInvoice(
                selectedInvoice.getId(),
                customerField.getText(),
                roomField.getText(),
                typeComboBox.getValue(),
                unitPriceField.getText(),
                hourField.isVisible() ? hourField.getText() : "0",
                dayField.isVisible() ? dayField.getText() : "0"
            );
            if (success) {
                statusLabel.setText("Cập nhật hóa đơn thành công");
                loadData();
            } else {
                statusLabel.setText("Vui lòng nhập đầy đủ thông tin");
            }
        } catch (IllegalArgumentException ex) {
            statusLabel.setText(ex.getMessage());
        }
    }
    @FXML
    private void handleStatsButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/stats.fxml"));
            Parent root = loader.load();
            StatsController statsController = loader.getController();
            statsController.setUseCases(showInvoiceTypeStatsUseCase, monthlyAverageInvoiceUseCase);
            Stage stage = new Stage();
            stage.setTitle("Báo cáo thống kê hóa đơn");
            stage.setScene(new Scene(root));
            stage.initOwner(statsButton.getScene().getWindow());
            stage.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Không thể mở bảng thống kê: " + ex.getMessage());
            alert.showAndWait();
        }
    }
}