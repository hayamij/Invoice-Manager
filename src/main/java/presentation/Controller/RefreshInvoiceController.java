package presentation.Controller;

public class RefreshInvoiceController {
    private ShowInvoiceListController showInvoiceListController;

    public RefreshInvoiceController(ShowInvoiceListController controller) {
        this.showInvoiceListController = controller;
    }

    public void refreshInvoice() {
        if (showInvoiceListController != null) {
            showInvoiceListController.execute();
            System.out.println("RefreshInvoiceController: Executed refresh successfully.");
        } else {
            System.out.println("RefreshInvoiceController: ShowInvoiceListController is null!");
        }
    }
}