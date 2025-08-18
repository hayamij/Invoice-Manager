package business.Controls.ShowInvoiceList;

import java.util.ArrayList;
import java.util.List;

import business.Entities.Invoice;
import business.Entities.InvoiceFactory;
import business.Models.InvoiceModel;
import persistence.InvoiceList.InvoiceDAOGateway;
import persistence.InvoiceList.InvoiceDTO;


// output: List<InvoiceModel> (list of invoices)


public class ShowInvoiceListUseCase {
    private InvoiceDAOGateway InvoiceDAOGateway;

    public ShowInvoiceListUseCase(InvoiceDAOGateway DAO) {
        this.InvoiceDAOGateway = DAO;
    }
    
    public List<InvoiceModel> execute(){
        List<Invoice> List = null;
        List<InvoiceDTO> ListDTO = null;
        ListDTO = InvoiceDAOGateway.getAll();
        List = this.convertToObject(ListDTO);
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
        return invoices;
    }

    private List<InvoiceModel> convertToViewDTO(List<Invoice> invoices) {
        List<InvoiceModel> model = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceModel invoiceModel = new InvoiceModel();
            invoiceModel.id = invoice.getId();
            invoiceModel.date = invoice.getDate();
            invoiceModel.customer = invoice.getCustomer();
            invoiceModel.room_id = invoice.getRoomId();
            invoiceModel.type = invoice.type();
            invoiceModel.total = invoice.calculateTotal();
            model.add(invoiceModel);
        }
        return model;
    }
}
