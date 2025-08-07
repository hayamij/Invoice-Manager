package presentation;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Enhanced ViewModel for Invoice List với simplified Observer pattern
 * Combines JavaFX Properties với simplified notifications for real-time updates
 */
public class InvoiceListModel {
    
    /**
     * Simple Subscriber interface
     */
    public interface Subscriber {
        void update();
        default String getSubscriberName() { return this.getClass().getSimpleName(); }
    }
    
    private final ListProperty<InvoiceListItem> invoices;
    private final List<Subscriber> subscribers = new CopyOnWriteArrayList<>();
    
    public InvoiceListModel() {
        this.invoices = new SimpleListProperty<>(FXCollections.observableArrayList());
    }
    
    // Simplified Publisher methods
    public void registerSubscriber(Subscriber subscriber) {
        if (subscriber != null && !subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
            System.out.println("Subscriber registered: " + subscriber.getClass().getSimpleName());
        }
    }
    
    public void removeSubscriber(Subscriber subscriber) {
        if (subscribers.remove(subscriber)) {
            System.out.println("Subscriber removed: " + subscriber.getClass().getSimpleName());
        }
    }
    
    public void notifySubscribers() {
        for (Subscriber subscriber : subscribers) {
            try {
                subscriber.update();
            } catch (Exception e) {
                System.err.println("Error notifying subscriber: " + e.getMessage());
            }
        }
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
        notifySubscribers();
    }
    
    public void setInvoices(List<InvoiceListItem> invoiceList) {
        ObservableList<InvoiceListItem> observableList = FXCollections.observableArrayList(invoiceList);
        setInvoices(observableList);
    }
    
    public void addInvoice(InvoiceListItem invoice) {
        this.invoices.add(invoice);
        // Notify subscribers về item được thêm
        notifySubscribers();
    }
    
    public void updateInvoice(int index, InvoiceListItem invoice) {
        if (index >= 0 && index < this.invoices.size()) {
            this.invoices.set(index, invoice);
            // Notify subscribers về item được update
            notifySubscribers();
        }
    }
    
    public void removeInvoice(InvoiceListItem invoice) {
        if (this.invoices.remove(invoice)) {
            // Notify subscribers về item được xóa
            notifySubscribers();
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
        notifySubscribers();
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
     * Notify statistics update
     */
    public void notifyStatisticsUpdate() {
        // Simply notify that statistics have updated
        notifySubscribers();
    }
}
