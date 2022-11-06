module fr.cubibox.backroom2_5d {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing;
    requires java.desktop;


    opens fr.cubibox.backroom2_5d to javafx.fxml;
    exports fr.cubibox.backroom2_5d;

    opens fr.cubibox.backroom2_5d.scenes to javafx.fxml;
    exports fr.cubibox.backroom2_5d.scenes;

    opens fr.cubibox.backroom2_5d.engine to javafx.fxml;
    exports fr.cubibox.backroom2_5d.engine;

    opens fr.cubibox.backroom2_5d.engine.maths.shapes to javafx.fxml;
    exports fr.cubibox.backroom2_5d.engine.maths.shapes;

    exports fr.cubibox.backroom2_5d.engine.graphics;
    opens fr.cubibox.backroom2_5d.engine.graphics to javafx.fxml;

    exports fr.cubibox.backroom2_5d.engine.observers;
    opens fr.cubibox.backroom2_5d.engine.observers to javafx.fxml;

    exports fr.cubibox.backroom2_5d.engine.observers.events;
    opens fr.cubibox.backroom2_5d.engine.observers.events to javafx.fxml;
}