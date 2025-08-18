package business.Controls.InvoiceStatistics;

import persistence.InvoiceList.InvoiceDAOGateway;
import persistence.InvoiceList.InvoiceDTO;
import java.util.List;
import java.util.Map;

import business.Entities.Invoice;
import business.Entities.InvoiceFactory;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;

// input: InvoiceDAOGateway
// output: Map<String, Double> averageTotalByMonthYear, Map<String, Integer> countByType


public class InvoiceStatisticsUseCase {
	private final InvoiceDAOGateway invoiceDAOGateway;

	public InvoiceStatisticsUseCase(InvoiceDAOGateway invoiceDAOGateway) {
		this.invoiceDAOGateway = invoiceDAOGateway;
	}

	public void execute() {
		Map<String, Double> averageTotalByMonthYear = averageTotalByMonthYear();
		Map<String, Integer> countByType = countInvoicesByType();
		// In ra kết quả thống kê
		System.out.println("Average Total by Month/Year:");
		for (Map.Entry<String, Double> entry : averageTotalByMonthYear.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		System.out.println("Count of Invoices by Type:");
		for (Map.Entry<String, Integer> entry : countByType.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	private Map<String, Integer> countInvoicesByType() {
		List<InvoiceDTO> invoices = invoiceDAOGateway.getAll();
		Map<String, Integer> countByType = new HashMap<>();
		for (InvoiceDTO invoice : invoices) {
			String type = invoice.getType();
			countByType.put(type, countByType.getOrDefault(type, 0) + 1);
		}
		return countByType;	
	}

	public Map<String, Double> averageTotalByMonthYear() {
        List<InvoiceDTO> invoices = invoiceDAOGateway.getAll();
        Map<String, List<Double>> totalsByMonthYear = new HashMap<>(); // im eating...                                  
        Calendar cal = Calendar.getInstance();
        for (InvoiceDTO invoice : invoices) {
            if (invoice.date != null) {
                cal.setTime(invoice.date);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = cal.get(Calendar.YEAR);
                String key = String.format("%02d/%04d", month, year);
                double invoiceTotal = calculateTotal(invoice);
                totalsByMonthYear.putIfAbsent(key, new ArrayList<>());
                totalsByMonthYear.get(key).add(invoiceTotal);
            }
        }
        Map<String, Double> averageByMonthYear = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : totalsByMonthYear.entrySet()) {
            List<Double> totals = entry.getValue();
            double sum = 0;
            for (double total : totals) {
                sum += total;
            }
            double avg = totals.isEmpty() ? 0 : sum / totals.size();
            averageByMonthYear.put(entry.getKey(), avg);
        }
        return averageByMonthYear;
    }

    // Hàm tính tổng tiền cho một hóa đơn (giống logic trong averageTotalByMonth)
    private double calculateTotal(InvoiceDTO dto) {
        if (dto == null) return 0;
		Invoice invoice = InvoiceFactory.createInvoice(dto);
		if (invoice == null) return 0;
		return invoice.calculateTotal();
    }
}
