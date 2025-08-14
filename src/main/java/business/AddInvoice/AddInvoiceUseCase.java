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
        InvoiceDTO invoiceDTO = convertToDTO(addItem);
        business.Invoice invoice = business.InvoiceFactory.createInvoice(invoiceDTO);
        if (invoice == null) {
            System.err.println("Thêm thất bại: Không nhận diện được loại hóa đơn (type = " + invoiceDTO.type + ")");
            return false;
        }
        double total = invoice.calculateTotal();
        if (total < 0){
            System.err.println("Thêm thất bại: Tổng tiền không hợp lệ (total = " + total + ")");
            return false;
        } else {
            // Tiếp tục xử lý thêm hóa đơn
            return DAO.insertInvoice(invoiceDTO);
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
