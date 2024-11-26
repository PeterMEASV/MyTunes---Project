module personalprojects.mytunesproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    requires java.naming;


    opens personalprojects.mytunesproject to javafx.fxml;
    exports personalprojects.mytunesproject;
    exports personalprojects.mytunesproject.gui;
    opens personalprojects.mytunesproject.gui to javafx.fxml;
}