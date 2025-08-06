package presentation;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * Enhanced ViewModel for Invoice List với Publisher capabilities
 * Combines JavaFX Properties với Observer pattern for real-time updates
 */
public class InvoiceListModel extends Publisher<InvoiceListEvent> {
    private final ListProperty<InvoiceListItem> invoices;
    
    public InvoiceListModel() {
        this.invoices = new SimpleListProperty<>(FXCollections.observableArrayList());
    }
    
    // JavaFX Property for automatic UI binding
    public ListProperty<InvoiceListItem> invoicesProperty() {
        return invoices;
    }
    
    public ObservableList<InvoiceListItem> getInvoices() {
        return invoices.get();
    }
    
    public void setInvoices(ObservableList<InvoiceListItem> invoices) {
        this.invoices.set(invoices);
        // Notify subscribers về data reload
        notifySubscribers(new InvoiceListEvent(InvoiceListEvent.EventType.LIST_REFRESHED, invoices));
    }
    
    public void setInvoices(List<InvoiceListItem> invoiceList) {
        ObservableList<InvoiceListItem> observableList = FXCollections.observableArrayList(invoiceList);
        setInvoices(observableList);
    }
    
    public void addInvoice(InvoiceListItem invoice) {
        this.invoices.add(invoice);
        // Notify subscribers về item được thêm
        notifySubscribers(new InvoiceListEvent(InvoiceListEvent.EventType.ITEM_ADDED, invoice));
    }
    
    public void updateInvoice(int index, InvoiceListItem invoice) {
        if (index >= 0 && index < this.invoices.size()) {
            InvoiceListItem oldInvoice = this.invoices.get(index);
            this.invoices.set(index, invoice);
            // Notify subscribers về item được update
            notifySubscribers(new InvoiceListEvent(InvoiceListEvent.EventType.ITEM_UPDATED, invoice, oldInvoice));
        }
    }
    
    public void removeInvoice(InvoiceListItem invoice) {
        if (this.invoices.remove(invoice)) {
            // Notify subscribers về item được xóa
            notifySubscribers(new InvoiceListEvent(InvoiceListEvent.EventType.ITEM_REMOVED, invoice));
        }
    }
    
    public void removeInvoiceById(String id) {
        InvoiceListItem toRemove = this.invoices.stream()
            .filter(item -> item.getId().equals(id))
            .findFirst()
            .orElse(null);
            
        if (toRemove != null) {
            removeInvoice(toRemove);
        }
    }
    
    public void clear() {
        this.invoices.clear();
        // Notify subscribers về list được clear
        notifySubscribers(new InvoiceListEvent(InvoiceListEvent.EventType.LIST_CLEARED, null));
    }
    
    public int size() {
        return this.invoices.size();
    }
    
    public InvoiceListItem findById(String id) {
        return this.invoices.stream()
            .filter(item -> item.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Calculate và notify thống kê realtime
     */
    public void notifyStatisticsUpdate() {
        double totalRevenue = this.invoices.stream()
            .mapToDouble(InvoiceListItem::getTotalPrice)
            .sum();
        int totalCount = this.invoices.size();
        
        StatisticsData stats = new StatisticsData(totalCount, totalRevenue);
        notifySubscribers(new InvoiceListEvent(InvoiceListEvent.EventType.STATISTICS_UPDATED, stats));
    }
}
