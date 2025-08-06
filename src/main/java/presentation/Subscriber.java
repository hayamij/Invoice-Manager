package presentation;

/**
 * Enhanced Subscriber interface supporting data passing
 * @param <T> Type of data passed in update notifications
 */
public interface Subscriber<T> {
    /**
     * Called when publisher notifies with no data
     */
    default void update() {
        update(null);
    }
    
    /**
     * Called when publisher notifies with data
     * @param data The data from the publisher
     */
    void update(T data);
    
    /**
     * Get subscriber name for debugging
     */
    default String getSubscriberName() {
        return this.getClass().getSimpleName();
    }
}