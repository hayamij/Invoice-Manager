package presentation;

import business.AddInvoice.AddInvoiceItem;
import business.AddInvoice.AddInvoiceUseCase;
import javafx.scene.control.Label;

public class AppController {
    private final AddInvoiceUseCase addInvoiceUseCase;
    private final Label statusLabel;
    private final Runnable onAdded;

    public AppController(AddInvoiceUseCase addInvoiceUseCase, Label statusLabel, Runnable onAdded) {
        this.addInvoiceUseCase = addInvoiceUseCase;
        this.statusLabel = statusLabel;
        this.onAdded = onAdded;
    }

    public void handleAdd(AddInvoiceItem addItem) {
        try {
            boolean ok = addInvoiceUseCase.execute(addItem);
            statusLabel.setText(ok ? "Thêm hóa đơn thành công!" : "Thêm hóa đơn thất bại!");
            if (ok && onAdded != null) onAdded.run();
        } catch (Exception ex) {
            statusLabel.setText("Lỗi khi thêm hóa đơn: " + ex.getMessage());
        }
    }
}
