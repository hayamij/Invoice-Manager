package persistence.SearchInvoice;

import java.util.List;

public interface SearchInvoiceDAOGateway {
    List<SearchInvoiceDTO> searchInvoiceByText(String searchText);
}
