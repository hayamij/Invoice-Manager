package presentation;

import java.util.ArrayList;
import java.util.List;

public class InvoiceViewModel {
    List<InvoiceViewItem> invoiceItems = new ArrayList<>();
    public List<InvoiceViewItem> getInvoiceItems() {
        return invoiceItems;
    }
}