package business.Controls.SearchInvoice;

import business.Entities.Invoice;
import business.Models.InvoiceModel;
import business.Models.SearchInvoiceModel;
import persistence.SearchInvoice.SearchInvoiceDAOGateway;
import persistence.SearchInvoice.SearchInvoiceDTO;

import java.util.List;
import java.util.ArrayList;

// input: SearchInvoiceModel
// output: List<InvoiceModel>

public class SearchInvoiceUseCase {
    private final SearchInvoiceDAOGateway invoiceDAO;

    public SearchInvoiceUseCase(SearchInvoiceDAOGateway invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    // Đầu vào là chuỗi tìm kiếm, đầu ra là List<InvoiceModel>
    public List<InvoiceModel> execute(SearchInvoiceModel SearchInvoiceModel) {
		String searchText = SearchInvoiceModel.searchText;
        if (searchText == null || searchText.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<SearchInvoiceDTO> resultDTOs = invoiceDAO.searchInvoiceByText(searchText);
        List<InvoiceModel> invoiceModels = new ArrayList<>();
        if (resultDTOs != null) {
            for (SearchInvoiceDTO dto : resultDTOs) {
                Invoice invoice = convertToObject(dto);
                InvoiceModel model = convertToModel(invoice);
                if (model != null) {
                    invoiceModels.add(model);
                }
            }
        }
        return invoiceModels;
    }

    // Chuyển đổi từ SearchInvoiceDTO sang Invoice
    public Invoice convertToObject(SearchInvoiceDTO invoiceDTO) {
        if (invoiceDTO == null) {
            return null;
        }
        return InvoiceSearchRequest.createSearchInvoice(invoiceDTO);
    }

    // Chuyển đổi từ Invoice sang InvoiceModel
    public InvoiceModel convertToModel(Invoice invoice) {
        if (invoice == null) {
            return null;
        }
        InvoiceModel model = new InvoiceModel();
        model.id = invoice.getId();
        model.date = invoice.getDate();
        model.customer = invoice.getCustomer();
        model.room_id = invoice.getRoom_id();
        model.type = invoice.type();
        model.total = invoice.calculateTotal();
        return model;
    }
}