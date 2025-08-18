package presentation.View.CRUD;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InvoiceFormView {

    @FXML
    private TextField customerField;

    @FXML
    private TextField dayField;

    @FXML
    private Label dayLabel;

    @FXML
    private TextField hourField;

    @FXML
    private Label hourLabel;

    @FXML
    private TextField roomField;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private TextField unitPriceField;

}
