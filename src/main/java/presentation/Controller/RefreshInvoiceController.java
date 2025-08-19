package presentation.Controller;

import business.Controls.ShowInvoiceList.ShowInvoiceListUseCase;
import persistence.InvoiceList.InvoiceDAO;
import persistence.InvoiceList.InvoiceDAOGateway;
import presentation.Model.InvoiceViewModel;

public class RefreshInvoiceController {

    private ShowInvoiceListController showInvoiceListController;
    private InvoiceDAOGateway invoiceDAOGateway;
    private InvoiceViewModel invoiceViewModel;
    private ShowInvoiceListUseCase showInvoiceListUseCase;

    public RefreshInvoiceController(ShowInvoiceListController controller) {
        this.showInvoiceListController = controller;
    }

    public void refreshInvoice() {
        invoiceViewModel = new InvoiceViewModel();
        invoiceDAOGateway = new InvoiceDAO();
        showInvoiceListUseCase = new ShowInvoiceListUseCase(invoiceDAOGateway);
        showInvoiceListController = new ShowInvoiceListController(invoiceViewModel, showInvoiceListUseCase);
        if (showInvoiceListController != null) {
            showInvoiceListController.execute();
            System.out.println("ShowInvoiceListController executed to refresh invoice list.");
            invoiceViewModel.notifySubscribers(); // Notify view to update
        } else {
            System.out.println("ShowInvoiceListController is not set.");
        }
    }
}
