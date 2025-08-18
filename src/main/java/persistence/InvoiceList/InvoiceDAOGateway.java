package persistence.InvoiceList;
import java.util.List;

public interface InvoiceDAOGateway {
    List<InvoiceDTO> getAll();
}
