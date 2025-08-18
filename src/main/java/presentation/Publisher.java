package presentation;

import java.util.List;

public class Publisher {
    List<Subscriber> subscribers;
    public Publisher(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }
    public void notifySubscribers() {
        for (Subscriber subscriber : subscribers) {
            subscriber.update();
        }
    }
}
