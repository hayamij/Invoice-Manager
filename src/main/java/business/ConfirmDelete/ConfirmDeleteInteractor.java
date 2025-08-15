package business.ConfirmDelete;

// SRP: chỉ tạo thông điệp xác nhận (không đụng UI hay DB)
public class ConfirmDeleteInteractor implements ConfirmDeleteUseCase {
  @Override
  public void execute(ConfirmDeleteRequest req, ConfirmDeleteOutputBoundary out) {
    out.present(new ConfirmDeleteResponse(
        "Confirmation",
        "Confirmation",
        "Xóa hóa đơn #" + req.invoiceId + "?"
    ));
  }
}
