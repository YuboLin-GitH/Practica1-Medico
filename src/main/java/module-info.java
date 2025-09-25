module org.example.practica1medicoyubo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens org.example.practica1medicoyubo to javafx.fxml;
    exports org.example.practica1medicoyubo;
}