module hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;

    opens presentation to javafx.fxml, javafx.base;
    opens presentation.View to javafx.fxml, javafx.base;
    opens presentation.View.CRUD to javafx.fxml, javafx.base;
    opens presentation.View.InvoiceList to javafx.fxml, javafx.base;
    opens presentation.View.InvoiceStatistics to javafx.fxml, javafx.base;
    opens presentation.View.SearchInvoice to javafx.fxml, javafx.base;
    opens persistence to javafx.base;

    exports presentation;
    exports presentation.View.CRUD;
    exports presentation.View.InvoiceList;
    exports presentation.View.InvoiceStatistics;
    exports presentation.View.SearchInvoice;
    exports persistence;
}