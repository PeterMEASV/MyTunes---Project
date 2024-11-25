module personalprojects.mytunesproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens personalprojects.mytunesproject to javafx.fxml;
    exports personalprojects.mytunesproject;
}