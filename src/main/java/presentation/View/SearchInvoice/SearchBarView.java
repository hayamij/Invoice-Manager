package presentation.View.SearchInvoice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SearchBarView {

    @FXML
    private Button clearSearchButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    void clearSearch(ActionEvent event) {
        // Logic to clear the search field
        searchField.clear();
        System.out.println("Search field cleared");
    }

    @FXML
    void searchInvoices(ActionEvent event) {
        // Logic to search invoices based on the input in the search field
        String query = searchField.getText();
        if (!query.isEmpty()) {
            System.out.println("Searching for invoices with query: " + query);
            // Add search logic here
        } else {
            System.out.println("Search field is empty");
        }
    }

}
