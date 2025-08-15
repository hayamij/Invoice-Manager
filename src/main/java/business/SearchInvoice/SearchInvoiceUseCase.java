package business.SearchInvoice;

import java.util.List;
import java.util.stream.Collectors;

import persistence.InvoiceDTO;

public class SearchInvoiceUseCase {
	public List<InvoiceDTO> filterInvoices(List<InvoiceDTO> invoices, String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) return invoices;
		String lowerKeyword = keyword.trim().toLowerCase();
		return invoices.stream()
			.filter(inv ->
				(inv.customer != null && inv.customer.toLowerCase().contains(lowerKeyword)) ||
				(inv.room_id != null && inv.room_id.toLowerCase().contains(lowerKeyword)) ||
				(inv.type != null && inv.type.toLowerCase().contains(lowerKeyword))
			)
			.collect(Collectors.toList());
	}
}
