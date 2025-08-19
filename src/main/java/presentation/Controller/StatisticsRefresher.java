package presentation.Controller;

import presentation.View.InvoiceStatistics.InvoiceStatisticView;

public class StatisticsRefresher {
    private InvoiceStatisticView statisticView;
    
    public StatisticsRefresher(InvoiceStatisticView statisticView) {
        this.statisticView = statisticView;
    }
    
    public void refreshStatistics() {
        if (statisticView != null) {
            statisticView.updateBasicStatistics();
            System.out.println("StatisticsRefresher: Statistics refreshed");
        }
    }
}