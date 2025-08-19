package presentation.Controller;

import business.Controls.InvoiceStatistics.InvoiceStatisticsUseCase;
import persistence.InvoiceList.InvoiceDAOGateway;
import persistence.InvoiceList.InvoiceDTO;
import business.Entities.Invoice;
import business.Entities.InvoiceFactory;

import java.util.List;
import java.util.Map;

public class InvoiceStatisticController {
    private final InvoiceStatisticsUseCase statisticsUseCase;
    private final InvoiceDAOGateway invoiceDAOGateway;

    public InvoiceStatisticController(InvoiceDAOGateway invoiceDAOGateway) {
        this.invoiceDAOGateway = invoiceDAOGateway;
        this.statisticsUseCase = new InvoiceStatisticsUseCase(invoiceDAOGateway);
    }

    public StatisticResult getBasicStatistics() {
        List<InvoiceDTO> invoices = invoiceDAOGateway.getAll();
        
        int totalInvoices = invoices.size();
        double totalRevenue = 0.0;
        int hourlyCount = 0;
        int dailyCount = 0;

        for (InvoiceDTO dto : invoices) {
            Invoice invoice = InvoiceFactory.createInvoice(dto);
            if (invoice != null) {
                totalRevenue += invoice.calculateTotal();
                
                if ("Hourly Invoice".equals(dto.type)) {
                    hourlyCount++;
                } else if ("Daily Invoice".equals(dto.type)) {
                    dailyCount++;
                }
            }
        }

        StatisticResult result = new StatisticResult();
        result.totalInvoices = totalInvoices;
        result.totalRevenue = totalRevenue;
        result.hourlyInvoiceCount = hourlyCount;
        result.dailyInvoiceCount = dailyCount;
        result.averagePerInvoice = totalInvoices > 0 ? totalRevenue / totalInvoices : 0;
        
        return result;
    }

    public Map<String, Double> getAverageTotalByMonthYear() {
        return statisticsUseCase.averageTotalByMonthYear();
    }

    public Map<String, Integer> getCountByType() {
        List<InvoiceDTO> invoices = invoiceDAOGateway.getAll();
        Map<String, Integer> countByType = new java.util.HashMap<>();
        
        for (InvoiceDTO invoice : invoices) {
            String type = invoice.getType();
            countByType.put(type, countByType.getOrDefault(type, 0) + 1);
        }
        
        return countByType;
    }

    public void printDetailedStatistics() {
        statisticsUseCase.execute();
    }

    // Inner class để chứa kết quả thống kê cơ bản
    public static class StatisticResult {
        public int totalInvoices;
        public double totalRevenue;
        public int hourlyInvoiceCount;
        public int dailyInvoiceCount;
        public double averagePerInvoice;
    }
}