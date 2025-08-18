module hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens presentation to javafx.fxml, javafx.base;
    opens presentation.InvoiceList to javafx.fxml, javafx.base;
    opens presentation.SearchInvoice to javafx.fxml, javafx.base;
    opens presentation.InvoiceStatistics to javafx.fxml, javafx.base;
    opens presentation.CRUD to javafx.fxml, javafx.base;
    opens persistence to javafx.base;

    exports presentation;
    exports presentation.InvoiceList;
    exports presentation.SearchInvoice;
    exports presentation.InvoiceStatistics;
    exports persistence;
}