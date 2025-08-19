package business.Controls.AddInvoice;

import business.DTO.AddInvoiceViewDTO;
import persistence.AddInvoice.AddInvoiceDAOGateway;
import presentation.Subscriber;
import presentation.Controller.InvoiceFormController;
import presentation.Controller.ShowInvoiceListController;

public class AddInvoiceUseCaseSubscriber implements Subscriber {
    
    private AddInvoiceUseCase addInvoiceUseCase;
    private InvoiceFormController controller;
    private ShowInvoiceListController showInvoiceListController;

    public AddInvoiceUseCaseSubscriber(AddInvoiceDAOGateway invoiceDAO, 
                                     InvoiceFormController controller,
                                     ShowInvoiceListController showInvoiceListController) {
        this.addInvoiceUseCase = new AddInvoiceUseCase(invoiceDAO);
        this.controller = controller;
        this.showInvoiceListController = showInvoiceListController;
    }

    @Override
    public void update() {
        // Lấy DTO từ controller
        AddInvoiceViewDTO dto = controller.getCurrentDTO();
        
        if (dto != null) {
            try {
                // Thực thi UseCase
                boolean success = addInvoiceUseCase.addInvoice(dto);
                
                if (success) {
                    // Thông báo thành công cho controller
                    controller.onAddInvoiceSuccess();
                    
                    // Refresh danh sách hóa đơn
                    if (showInvoiceListController != null) {
                        showInvoiceListController.execute();
                    }
                    
                    System.out.println("AddInvoiceUseCaseSubscriber: Invoice added successfully.");
                } else {
                    // Thông báo thất bại cho controller
                    controller.onAddInvoiceFailure("Không thể thêm hóa đơn. Vui lòng kiểm tra lại thông tin.");
                    System.out.println("AddInvoiceUseCaseSubscriber: Failed to add invoice.");
                }
                
            } catch (Exception e) {
                // Thông báo lỗi cho controller
                controller.onAddInvoiceFailure("Lỗi hệ thống: " + e.getMessage());
                System.err.println("AddInvoiceUseCaseSubscriber: Error - " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            controller.onAddInvoiceFailure("Dữ liệu không hợp lệ.");
        }
    }
}