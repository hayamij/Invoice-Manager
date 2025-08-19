package presentation.Controller;

import business.Controls.ShowInvoiceList.ShowInvoiceListUseCase;
import business.DTO.InvoiceViewDTO;
import presentation.Model.InvoiceViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowInvoiceListController {
	private final ShowInvoiceListUseCase useCase;
	private final InvoiceViewModel model;
    

	public ShowInvoiceListController(InvoiceViewModel model, ShowInvoiceListUseCase useCase) {
		this.model = model;
		this.useCase = useCase;
	}

	// Controller cập nhật model, model sẽ notify view
	public void execute() {
		List<InvoiceViewDTO> list = useCase.execute();
        List<InvoiceViewItem> listItem = convert(list);
		model.listitem = listItem;
        model.notifySubscribers(); // Notify view to update
	}

    private List<InvoiceViewItem> convert(List<InvoiceViewDTO> listDTO) {
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
}
