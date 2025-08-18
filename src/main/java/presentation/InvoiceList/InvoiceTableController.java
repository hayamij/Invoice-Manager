package presentation.InvoiceList;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InvoiceTableController {

    @FXML
    private TableColumn<?, ?> customerColumn;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableView<?> invoiceTable;

    @FXML
    private TableColumn<?, ?> roomColumn;

    @FXML
    private TableColumn<?, ?> sttColumn;

    @FXML
    private TableColumn<?, ?> totalColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

}
