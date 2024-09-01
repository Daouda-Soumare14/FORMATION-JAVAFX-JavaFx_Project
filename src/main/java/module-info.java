module rachid.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires de.jensd.fx.glyphs.fontawesome;


    opens rachid.javafx to javafx.fxml;
    exports rachid.javafx;
}