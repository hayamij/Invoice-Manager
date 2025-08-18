package presentation.InvoiceStatistics;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InvoiceStatisticFormController {

    @FXML
    private TableColumn<?, ?> averageColumn;

    @FXML
    private TableView<?> averageTable;

    @FXML
    private Label dailyLabel;

    @FXML
    private Label hourlyLabel;

    @FXML
    private TableColumn<?, ?> monthColumn;

    @FXML
    private TableColumn<?, ?> yearColumn;

}
