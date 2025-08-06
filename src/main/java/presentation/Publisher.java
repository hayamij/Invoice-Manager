package presentation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Enhanced Publisher for event-driven architecture
 * Supports different event types and data passing
 */
public abstract class Publisher<T> {
    private final List<Subscriber<T>> subscribers = new CopyOnWriteArrayList<>();

    public void registerSubscriber(Subscriber<T> subscriber) {
        if (subscriber != null && !subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
            System.out.println("Subscriber registered: " + subscriber.getClass().getSimpleName());
        }
    }
    
    public void removeSubscriber(Subscriber<T> subscriber) {
        if (subscribers.remove(subscriber)) {
            System.out.println("Subscriber removed: " + subscriber.getClass().getSimpleName());
        }
    }
    
    public void notifySubscribers() {
        notifySubscribers(null);
    }
    
    public void notifySubscribers(T data) {
        for (Subscriber<T> subscriber : subscribers) {
            try {
                subscriber.update(data);
            } catch (Exception e) {
                System.err.println("Error notifying subscriber: " + e.getMessage());
            }
        }
    }
    
    public int getSubscriberCount() {
        return subscribers.size();
    }
    
    public void clearSubscribers() {
        subscribers.clear();
    }
}