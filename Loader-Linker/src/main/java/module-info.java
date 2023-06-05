module com.example.loaderlinker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.loaderlinker to javafx.fxml;
    exports com.example.loaderlinker;
}