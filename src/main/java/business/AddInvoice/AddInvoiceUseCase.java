package business.AddInvoice;

import business.Invoice;
import business.InvoiceFactory;
import persistence.AddInvoiceDAOGateway;
import persistence.InvoiceDTO;

public class AddInvoiceUseCase {
    private final AddInvoiceDAOGateway DAO;

    public AddInvoiceUseCase(AddInvoiceDAOGateway DAO) {
        this.DAO = DAO;
    }

    public boolean execute(AddInvoiceItem addItem) {
        InvoiceDTO list;
        list = convertToDTO(addItem);
        Invoice invoice = InvoiceFactory.createInvoice(list);
        if (invoice.calculateTotal() < 0){
            return false;
        } else {
            return DAO.insertInvoice(list);
        }
    }
    // convert AddInvoiceItem to InvoiceDTO
    public InvoiceDTO convertToDTO(AddInvoiceItem addItem) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.customer = addItem.customer;
        dto.room_id = addItem.room_id;
        dto.date = addItem.date;
        dto.type = addItem.type;
        dto.unitPrice = addItem.unitPrice;
        dto.hour = addItem.hour;
        dto.day = addItem.day;
        return dto;
    }
}
