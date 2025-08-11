package business.ShowInvoiceList;

import java.util.ArrayList;
import java.util.List;

import business.Invoice;
import business.InvoiceFactory;
import persistence.InvoiceDAOGateway;
import persistence.InvoiceDTO;

public class ShowInvoiceListUseCase {
    private InvoiceDAOGateway InvoiceDAOGateway;

    public ShowInvoiceListUseCase(InvoiceDAOGateway DAO) {
        this.InvoiceDAOGateway = DAO;
    }
    
    public List<InvoiceViewDTO> execute(){
        List<Invoice> List = null;
        List<InvoiceDTO> ListDTO = null;
        ListDTO = InvoiceDAOGateway.getAll();
        List = this.convertToObject(ListDTO);
        return this.convertToViewDTO(List);
    }

    // function below

    

    private List<Invoice> convertToObject(List<InvoiceDTO> ListDTO) {
        List<Invoice> invoices = new ArrayList<>();
        for (InvoiceDTO invoiceDTO : ListDTO) {
            Invoice invoice = InvoiceFactory.createInvoice(invoiceDTO);
            if (invoice != null) {
                invoices.add(invoice);
            }
        }
        return invoices;
    }

    private List<InvoiceViewDTO> convertToViewDTO(List<Invoice> invoices) {
        List<InvoiceViewDTO> viewDTOs = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceViewDTO viewDTO = new InvoiceViewDTO();
            viewDTO.id = invoice.getId();
            viewDTO.date = invoice.getDate();
            viewDTO.customer = invoice.getCustomer();
            viewDTO.room_id = invoice.getRoomId();
            viewDTO.type = invoice.type();
            viewDTO.total = invoice.calculateTotal();
            viewDTOs.add(viewDTO);
        }
        return viewDTOs;
    }
}
