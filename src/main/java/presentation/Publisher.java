package presentation;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
	private List<Subscriber> subscribers = new ArrayList<>();

	public void registerSubscriber(Subscriber sub) {
		
		this.subscribers.add(sub);

	}

	public void removeSubscriber(Subscriber sub) {
		this.subscribers.remove(sub);

	}

	public void notifySubscribers() {
		
		for (Subscriber subscriber : subscribers) {
			subscriber.update();
		}
        System.out.println("Publisher: Notified all subscribers.");

	}

}