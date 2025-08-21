package presentation.Model;

import presentation.Subscriber;

import presentation.Publisher;
import presentation.Controller.InvoiceStatisticController.StatisticResult;

public class InvoiceStatisticModel extends Publisher {
    private int totalInvoices;
    private double totalRevenue;

    public int getTotalInvoices() {
        return totalInvoices;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void updateStatistics(StatisticResult result) {
        if (result != null) {
            this.totalInvoices = result.totalInvoices;
            this.totalRevenue = result.totalRevenue;
            notifySubscribers();
        }
    }
    public void subscribe(Subscriber sub) {
        registerSubscriber(sub);
    }
}
