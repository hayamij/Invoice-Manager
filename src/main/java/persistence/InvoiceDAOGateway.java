package persistence;
import java.util.List;

public interface InvoiceDAOGateway {
    List<InvoiceDTO> getAll();
    boolean insertInvoice(InvoiceDTO invoiceDTO);
    // boolean updateInvoice(InvoiceDTO invoiceDTO);
    // boolean deleteInvoice(String id);
}
