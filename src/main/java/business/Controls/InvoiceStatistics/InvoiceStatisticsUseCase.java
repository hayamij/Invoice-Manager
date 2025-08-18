package business.Controls.InvoiceStatistics;

import persistence.InvoiceList.InvoiceDAOGateway;
import persistence.InvoiceList.InvoiceDTO;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;

// input: InvoiceDAOGateway (data access object for invoices)
// output: Map<String, Integer> (count of invoices by type)
// output: double (average total of invoices for a specific month and year)



public class InvoiceStatisticsUseCase {
	private final InvoiceDAOGateway invoiceDAOGateway;

	public InvoiceStatisticsUseCase(InvoiceDAOGateway invoiceDAOGateway) {
		this.invoiceDAOGateway = invoiceDAOGateway;
	}

	// Tính tổng số lượng hóa đơn theo từng loại
	public Map<String, Integer> countInvoicesByType() {
		List<InvoiceDTO> invoices = invoiceDAOGateway.getAll();
		Map<String, Integer> typeCount = new HashMap<>();
		for (InvoiceDTO invoice : invoices) {
			String type = invoice.type;
			typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
		}
		return typeCount;
	}

	// Tính trung bình thành tiền các hóa đơn trong một tháng chỉ định
	public double averageTotalByMonth(int month, int year) {
		List<InvoiceDTO> invoices = invoiceDAOGateway.getAll();
		double total = 0;
		int count = 0;
		Calendar cal = Calendar.getInstance();
		for (InvoiceDTO invoice : invoices) {
			if (invoice.date != null) {
				cal.setTime(invoice.date);
				int invoiceMonth = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
				int invoiceYear = cal.get(Calendar.YEAR);
				if (invoiceMonth == month && invoiceYear == year) {
					double invoiceTotal = 0;
					if ("Hourly Invoice".equalsIgnoreCase(invoice.type)) {
						if (invoice.hour >= 24 && invoice.hour <= 30) {
							invoiceTotal = 24 * invoice.unitPrice;
						} else if (invoice.hour > 30) {
							invoiceTotal = 0; // Không dùng loại hóa đơn theo giờ
						} else {
							invoiceTotal = invoice.hour * invoice.unitPrice;
						}
					} else if ("Daily Invoice".equalsIgnoreCase(invoice.type)) {
						if (invoice.day > 7) {
							invoiceTotal = 7 * invoice.unitPrice + (invoice.day - 7) * invoice.unitPrice * 0.8;
						} else {
							invoiceTotal = invoice.day * invoice.unitPrice;
						}
					}
					total += invoiceTotal;
					count++;
				}
			}
		}
		return count > 0 ? total / count : 0;
	}
}
