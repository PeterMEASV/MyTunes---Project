module personalprojects.mytunesproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens personalprojects.mytunesproject to javafx.fxml;
    exports personalprojects.mytunesproject;
    exports personalprojects.mytunesproject.gui;
    opens personalprojects.mytunesproject.gui to javafx.fxml;
}