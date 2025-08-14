package presentation;

import java.util.ArrayList;
import java.util.List;

import business.AddInvoice.AddInvoiceItem;
import business.AddInvoice.AddInvoiceUseCase;
import business.AddInvoice.InvoiceTypeListUseCase;
import business.AddInvoice.InvoiceTypeViewDTO;
import business.DailyInvoice;
import business.HourlyInvoice;
import business.InvoiceViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddUI {

    private InvoiceViewModel viewModel;
    private AddInvoiceUseCase addInvoiceUseCase;
    private InvoiceTypeListUseCase invoiceTypeListUseCase;

    private ComboBox<String> typeComboBox;
    private TextField customerField;
    private TextField roomField;
    private TextField unitPriceField;
    private TextField hourField;
    private TextField dayField;

    private Label hourLabel;
    private Label dayLabel;
    private Label statusLabel;

    private Runnable onAdded;

    private final String hourlyType = new HourlyInvoice().type();
    private final String dailyType  = new DailyInvoice().type();

    @FXML
    private void handleAdd() {
        try {
            AddInvoiceItem addItem = new AddInvoiceItem();
            addItem.customer = customerField.getText();
            addItem.room_id = roomField.getText();
            addItem.date = new java.sql.Timestamp(System.currentTimeMillis());
            addItem.unitPrice = Double.parseDouble(unitPriceField.getText());
            addItem.hour = hourField.getText().isEmpty() ? 0 : Integer.parseInt(hourField.getText());
            addItem.day  = dayField.getText().isEmpty() ? 0 : Integer.parseInt(dayField.getText());
            addItem.type = typeComboBox.getValue();

            boolean ok = addInvoiceUseCase.execute(addItem);
            statusLabel.setText(ok ? "Thêm hóa đơn thành công!" : "Thêm hóa đơn thất bại!");
            if (ok && onAdded != null) onAdded.run();

        } catch (NumberFormatException ex) {
            statusLabel.setText("Đơn giá/Giờ/Ngày phải là số hợp lệ.");
        } catch (Exception ex) {
            statusLabel.setText("Lỗi khi thêm hóa đơn: " + ex.getMessage());
        }
    }

    public void init(
            InvoiceViewModel viewModel,
            AddInvoiceUseCase addInvoiceUseCase,
            InvoiceTypeListUseCase invoiceTypeListUseCase,
            ComboBox<String> typeComboBox,
            TextField customerField,
            TextField roomField,
            TextField unitPriceField,
            TextField hourField,
            TextField dayField,
            Label hourLabel,
            Label dayLabel,
            Label statusLabel,
            Runnable onAdded
    ) {
        this.viewModel = viewModel;
        this.addInvoiceUseCase = addInvoiceUseCase;
        this.invoiceTypeListUseCase = invoiceTypeListUseCase;

        this.typeComboBox = typeComboBox;
        this.customerField = customerField;
        this.roomField = roomField;
        this.unitPriceField = unitPriceField;
        this.hourField = hourField;
        this.dayField = dayField;

        this.hourLabel = hourLabel;
        this.dayLabel = dayLabel;
        this.statusLabel = statusLabel;

        this.onAdded = onAdded;
    }

    public void prepare() {
        loadInvoiceTypes();
        setupTypeToggle();
    }

    private void loadInvoiceTypes() {
        if (invoiceTypeListUseCase == null) return;

        List<InvoiceTypeViewDTO> typeDTOs = invoiceTypeListUseCase.execute();
        List<String> typeNames = new ArrayList<>();
        for (InvoiceTypeViewDTO dto : typeDTOs) typeNames.add(dto.getTypeName());

        typeComboBox.setItems(FXCollections.observableArrayList(typeNames));
        if (!typeNames.isEmpty()) {
            typeComboBox.setValue(typeNames.get(0));
            updateInputFields(typeNames.get(0));
        }
    }

    private void setupTypeToggle() {
        typeComboBox.valueProperty().addListener((obs, o, n) -> updateInputFields(n));
    }

    private void updateInputFields(String type) {
        boolean isHourly = hourlyType.equals(type);
        boolean isDaily  = dailyType.equals(type);

        hourField.setVisible(isHourly || (!isHourly && !isDaily));
        hourLabel.setVisible(hourField.isVisible());

        dayField.setVisible(isDaily || (!isHourly && !isDaily));
        dayLabel.setVisible(dayField.isVisible());
    }
}