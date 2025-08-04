package persistence;
import java.util.List;

public interface InvoiceDAOGateway {
    List<InvoiceDTO> getAll();
    boolean add(InvoiceDTO invoice);
}
