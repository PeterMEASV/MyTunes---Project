module personalprojects.mytunesproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires com.microsoft.sqlserver.jdbc;
    requires javafx.web;
    requires javafx.media;


    opens personalprojects.mytunesproject to javafx.fxml;
    exports personalprojects.mytunesproject;
    exports personalprojects.mytunesproject.gui;
    opens personalprojects.mytunesproject.gui to javafx.fxml;
    opens personalprojects.mytunesproject.BE to javafx.base;
}