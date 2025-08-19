package presentation.Controller;

import business.Controls.SearchInvoice.SearchInvoiceUseCase;
import business.DTO.InvoiceViewDTO;
import business.DTO.SearchInvoiceViewDTO;
import presentation.Model.InvoiceViewModel;
import persistence.SearchInvoice.SearchInvoiceDAOGateway;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SearchInvoiceController {
    private final SearchInvoiceUseCase searchInvoiceUseCase;
    private final InvoiceViewModel model;

    public SearchInvoiceController(InvoiceViewModel model, SearchInvoiceDAOGateway searchDAO) {
        this.model = model;
        this.searchInvoiceUseCase = new SearchInvoiceUseCase(searchDAO);
    }

    public void execute(SearchInvoiceViewDTO searchDTO) {
        String searchText = searchDTO.searchText;
        
        if (searchText == null || searchText.trim().isEmpty()) {
            // Nếu search text rỗng, hiển thị thông báo
            model.listitem = new ArrayList<>();
            model.notifySubscribers();
            return;
        }

        List<InvoiceViewDTO> searchResults = searchInvoiceUseCase.execute(searchText.trim());
        List<InvoiceViewItem> listItem = convertToViewItems(searchResults);
        
        model.listitem = listItem;
        model.notifySubscribers(); // Notify view to update
        
        System.out.println("SearchInvoiceController: Found " + searchResults.size() + " invoices matching '" + searchText + "'");
    }

    private List<InvoiceViewItem> convertToViewItems(List<InvoiceViewDTO> listDTO) {
        List<InvoiceViewItem> list = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        int stt = 1;
        for (InvoiceViewDTO dto : listDTO) {
            InvoiceViewItem item = new InvoiceViewItem();
            item.stt = stt++;
            item.id = dto.id;
            item.date = fmt.format(dto.date);
            item.customer = dto.customer;
            item.room_id = dto.room_id;
            item.type = dto.type;
            item.total = dto.total;
            list.add(item);
        }
        return list;
    }

    public boolean hasResults() {
        return model.listitem != null && !model.listitem.isEmpty();
    }
}