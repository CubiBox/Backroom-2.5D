module fr.cubibox.backroom2_5d {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing;
    requires java.desktop;


    opens fr.cubibox.backroom2_5d to javafx.fxml;
    exports fr.cubibox.backroom2_5d;
}