module rachid.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;
    requires mysql.connector.java;


    opens rachid.javafx to javafx.fxml;
    exports rachid.javafx;
}