module hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens presentation to javafx.fxml;
    opens business to javafx.base;
    opens persistence to javafx.base;
    
    exports presentation;
    exports business;
    exports persistence;
}
