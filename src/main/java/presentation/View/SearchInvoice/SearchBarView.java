package presentation.View.SearchInvoice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import business.DTO.SearchInvoiceViewDTO;
import presentation.Controller.SearchInvoiceController;
import presentation.Controller.ShowInvoiceListController;

public class SearchBarView {

    @FXML
    private Button clearSearchButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    private SearchInvoiceController searchInvoiceController;
    private ShowInvoiceListController showInvoiceListController;

    public void setSearchInvoiceController(SearchInvoiceController controller) {
        this.searchInvoiceController = controller;
    }

    public void setShowInvoiceListController(ShowInvoiceListController controller) {
        this.showInvoiceListController = controller;
    }

    @FXML
    void clearSearch(ActionEvent event) {
        try {
            // Clear the search field
            searchField.clear();
            
            // Restore the full invoice list
            if (showInvoiceListController != null) {
                showInvoiceListController.execute();
                showSuccessAlert("Đã xóa bộ lọc và hiển thị toàn bộ danh sách!");
                System.out.println("Search cleared, showing full list");
            } else {
                showErrorAlert("Lỗi hệ thống!", "Không thể khôi phục danh sách đầy đủ.");
            }
        } catch (Exception e) {
            showErrorAlert("Lỗi hệ thống!", "Đã xảy ra lỗi khi xóa bộ lọc: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void searchInvoices(ActionEvent event) {
        try {
            String query = searchField.getText();
            
            if (query == null || query.trim().isEmpty()) {
                showErrorAlert("Lỗi!", "Vui lòng nhập từ khóa tìm kiếm.");
                return;
            }

            if (searchInvoiceController != null) {
                SearchInvoiceViewDTO searchDTO = new SearchInvoiceViewDTO();
                searchDTO.searchText = query.trim();
                
                searchInvoiceController.execute(searchDTO);
                System.out.println("Searching for invoices with query: " + query);
            } else {
                showErrorAlert("Lỗi hệ thống!", "Controller tìm kiếm không khả dụng.");
                System.out.println("SearchInvoiceController is null");
            }
        } catch (Exception e) {
            showErrorAlert("Lỗi hệ thống!", "Đã xảy ra lỗi khi tìm kiếm: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}