module org.example.demo121 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.demo121 to javafx.fxml;
    exports org.example.demo121;
}