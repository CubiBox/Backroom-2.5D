module fr.cubibox.backroom2_5d {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing;
    requires java.desktop;


    opens fr.cubibox.backroom2_5d to javafx.fxml;
    exports fr.cubibox.backroom2_5d;

    opens fr.cubibox.backroom2_5d.controllers to javafx.fxml;
    exports fr.cubibox.backroom2_5d.controllers;

    opens fr.cubibox.backroom2_5d.engine to javafx.fxml;
    exports fr.cubibox.backroom2_5d.engine;

    opens fr.cubibox.backroom2_5d.engine.maths.shapes to javafx.fxml;
    exports fr.cubibox.backroom2_5d.engine.maths.shapes;

    opens fr.cubibox.backroom2_5d.engine.maths to javafx.fxml;
    exports fr.cubibox.backroom2_5d.engine.maths;

    exports fr.cubibox.backroom2_5d.engine.graphics;
    opens fr.cubibox.backroom2_5d.engine.graphics to javafx.fxml;

    exports fr.cubibox.backroom2_5d.io;
    opens fr.cubibox.backroom2_5d.io to javafx.fxml;
}