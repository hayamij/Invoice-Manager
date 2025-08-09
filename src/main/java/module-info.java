module hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens presentation to javafx.fxml;
    opens business to javafx.base;
    opens persistence to javafx.base;
    
    exports presentation;
    exports business;
    exports persistence;
}
