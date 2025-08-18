package business.Controls.SearchInvoice;

import business.Entities.Invoice;
import business.DTO.InvoiceViewDTO;
// ...existing code...
import persistence.SearchInvoice.SearchInvoiceDAOGateway;
import persistence.SearchInvoice.SearchInvoiceDTO;

import java.util.List;
import java.util.ArrayList;

// input: String searchText
// output: List<InvoiceViewDTO>

public class SearchInvoiceUseCase {
    private final SearchInvoiceDAOGateway invoiceDAO;

    public SearchInvoiceUseCase(SearchInvoiceDAOGateway invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    // Đầu vào là chuỗi tìm kiếm, đầu ra là List<InvoiceModel>
    public List<InvoiceViewDTO> execute(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<SearchInvoiceDTO> resultDTOs = invoiceDAO.searchInvoiceByText(searchText);
        List<InvoiceViewDTO> invoiceViews = new ArrayList<>();
        if (resultDTOs != null) {
            for (SearchInvoiceDTO dto : resultDTOs) {
                Invoice invoice = convertToObject(dto);
                InvoiceViewDTO viewDTO = convertToViewDTO(invoice);
                if (viewDTO != null) {
                    invoiceViews.add(viewDTO);
                }
            }
        }
        return invoiceViews;
    }

    // Chuyển đổi từ SearchInvoiceDTO sang Invoice
    public Invoice convertToObject(SearchInvoiceDTO invoiceDTO) {
        if (invoiceDTO == null) {
            return null;
        }
        return InvoiceSearchRequest.createSearchInvoice(invoiceDTO);
    }

    // Chuyển đổi từ Invoice sang InvoiceViewDTO
    public InvoiceViewDTO convertToViewDTO(Invoice invoice) {
        if (invoice == null) {
            return null;
        }
        InvoiceViewDTO dto = new InvoiceViewDTO();
        dto.id = invoice.getId();
        dto.date = invoice.getDate();
        dto.customer = invoice.getCustomer();
        dto.room_id = invoice.getRoom_id();
        dto.type = invoice.type();
        dto.total = invoice.calculateTotal();
        return dto;
    }
}