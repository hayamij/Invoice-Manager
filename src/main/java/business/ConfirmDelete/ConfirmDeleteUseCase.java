package business.ConfirmDelete;

public interface ConfirmDeleteUseCase {
  void execute(ConfirmDeleteRequest request, ConfirmDeleteOutputBoundary presenter);
}
