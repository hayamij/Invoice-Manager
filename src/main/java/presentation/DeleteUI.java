package presentation;

import business.ConfirmDelete.*;

import business.InvoiceViewItem;
import business.DeleteInvoice.DeleteInvoiceUseCase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DeleteUI {

    @FXML private Button deleteButton;

    private DeleteInvoiceUseCase deleteInvoiceUseCase;
    private ConfirmDeleteUseCase confirmDeleteUseCase;

    private TableView<InvoiceViewItem> invoiceTable;
    private Label statusLabel;
    private Runnable reloadCallback;

    public void init(DeleteInvoiceUseCase useCase,
                     TableView<InvoiceViewItem> invoiceTable,
                     Label statusLabel,
                     Runnable reloadCallback) {
        this.deleteInvoiceUseCase = useCase;
        this.invoiceTable = invoiceTable;
        this.statusLabel = statusLabel;
        this.reloadCallback = reloadCallback;

        if (deleteButton != null && invoiceTable != null) {
            deleteButton.disableProperty()
                .bind(invoiceTable.getSelectionModel().selectedItemProperty().isNull());
        }
    }

    public void setConfirmDeleteUseCase(ConfirmDeleteUseCase uc) {
        this.confirmDeleteUseCase = uc;
    }


    @FXML
    private void handleDelete(ActionEvent e) {
        var selected = invoiceTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.INFORMATION, "Vui lòng chọn một hóa đơn để xóa.").showAndWait();
            return;
        }
        int id;
        try {
            id = Integer.parseInt(String.valueOf(selected.getId()));
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "ID hóa đơn không hợp lệ.").showAndWait();
            return;
        }

        ConfirmDeletePresenter presenter = new ConfirmDeletePresenter();
        confirmDeleteUseCase.execute(new ConfirmDeleteRequest(id), presenter);
        if (!presenter.isConfirmed()) return;


        boolean ok = deleteInvoiceUseCase != null && deleteInvoiceUseCase.execute(id);
        if (ok) {
            if (reloadCallback != null) reloadCallback.run();
            if (statusLabel != null) statusLabel.setText("Đã xóa hóa đơn #" + id);
            new Alert(Alert.AlertType.INFORMATION, "Đã xóa hóa đơn #" + id).showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "Không tìm thấy hóa đơn hoặc xóa thất bại.").showAndWait();
        }
    }
}
