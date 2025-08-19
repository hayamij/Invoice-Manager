package business.Controls.ShowInvoiceList;

import java.util.ArrayList;
import java.util.List;

import business.Entities.Invoice;
import business.Entities.InvoiceFactory;
import business.DTO.InvoiceViewDTO;
import persistence.InvoiceList.InvoiceDAOGateway;
import persistence.InvoiceList.InvoiceDTO;


// input: InvoiceDAOGateway
// dto -> object -> model
// output: List<InvoiceViewDTO> (list of invoices)


public class ShowInvoiceListUseCase {
    private InvoiceDAOGateway InvoiceDAOGateway;

    public ShowInvoiceListUseCase(InvoiceDAOGateway DAO) {
        this.InvoiceDAOGateway = DAO;
    }
    
    public List<InvoiceViewDTO> execute(){
        List<InvoiceDTO> ListDTO = InvoiceDAOGateway.getAll();
        List<Invoice> List = this.convertToObject(ListDTO);
        return this.convertToViewDTO(List);
    }

    private List<Invoice> convertToObject(List<InvoiceDTO> ListDTO) {
        List<Invoice> invoices = new ArrayList<>();
        for (InvoiceDTO invoiceDTO : ListDTO) {
            Invoice invoice = InvoiceFactory.createInvoice(invoiceDTO);
            if (invoice != null) {
                invoices.add(invoice);
            }
        }
        System.out.println("Converted " + invoices.size() + " invoices from DTO to object.");
        return invoices;
    }

    private List<InvoiceViewDTO> convertToViewDTO(List<Invoice> invoices) {
        List<InvoiceViewDTO> viewDTOs = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceViewDTO dto = new InvoiceViewDTO();
            dto.id = invoice.getId();
            dto.date = invoice.getDate();
            dto.customer = invoice.getCustomer();
            dto.room_id = invoice.getRoom_id();
            dto.type = invoice.type();
            dto.total = invoice.calculateTotal();
            viewDTOs.add(dto);
        }
        System.out.println("Converted " + viewDTOs.size() + " invoices to view DTO.");
        return viewDTOs;
    }
}
