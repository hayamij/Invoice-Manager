package business;

import persistence.InvoiceDAOGateway;
import persistence.InvoiceDTO;
import java.util.List;

public class ShowInvoiceTypeStatsUseCase {
    private final InvoiceDAOGateway invoiceDAO;

    public ShowInvoiceTypeStatsUseCase(InvoiceDAOGateway invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public StatsResult getStats() {
        List<InvoiceDTO> invoices = invoiceDAO.getAll();
        int hourlyCount = 0;
        int dailyCount = 0;
        for (InvoiceDTO invoice : invoices) {
            if ("hourly".equalsIgnoreCase(invoice.getType())) hourlyCount++;
            if ("daily".equalsIgnoreCase(invoice.getType())) dailyCount++;
        }
        return new StatsResult(hourlyCount, dailyCount);
    }

    public static class StatsResult {
        public final int hourlyCount;
        public final int dailyCount;
        public StatsResult(int hourlyCount, int dailyCount) {
            this.hourlyCount = hourlyCount;
            this.dailyCount = dailyCount;
        }
    }
}
