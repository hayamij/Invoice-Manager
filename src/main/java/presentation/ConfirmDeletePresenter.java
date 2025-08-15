package presentation;

import business.ConfirmDelete.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * SRP: Lớp này chỉ chịu trách nhiệm hiển thị UI xác nhận và thu kết quả người dùng.
 * Không chứa logic domain hay truy cập DB.
 */
public class ConfirmDeletePresenter implements ConfirmDeleteOutputBoundary {
  private boolean confirmed = false;

  @Override
  public void present(ConfirmDeleteResponse vm) {
    Alert a = new Alert(Alert.AlertType.CONFIRMATION, vm.content, ButtonType.OK, ButtonType.CANCEL);
    a.setTitle(vm.title);
    a.setHeaderText(vm.header);
    Optional<ButtonType> r = a.showAndWait();
    confirmed = r.isPresent() && r.get() == ButtonType.OK;
  }

  public boolean isConfirmed() { return confirmed; }
}
