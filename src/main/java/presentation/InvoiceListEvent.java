package presentation;

/**
 * Event data class for InvoiceListModel notifications
 */
public class InvoiceListEvent {
    public enum EventType {
        ITEM_ADDED, ITEM_REMOVED, ITEM_UPDATED, 
        LIST_REFRESHED, LIST_CLEARED, STATISTICS_UPDATED
    }
    
    private final EventType eventType;
    private final Object data;
    private final Object oldData;
    
    public InvoiceListEvent(EventType eventType, Object data) {
        this(eventType, data, null);
    }
    
    public InvoiceListEvent(EventType eventType, Object data, Object oldData) {
        this.eventType = eventType;
        this.data = data;
        this.oldData = oldData;
    }
    
    public EventType getEventType() { return eventType; }
    public Object getData() { return data; }
    public Object getOldData() { return oldData; }
    
    @Override
    public String toString() {
        return "InvoiceListEvent{" +
                "eventType=" + eventType +
                ", data=" + data +
                ", oldData=" + oldData +
                '}';
    }
}
