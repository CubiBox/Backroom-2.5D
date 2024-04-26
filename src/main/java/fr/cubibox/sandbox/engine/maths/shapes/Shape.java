package fr.cubibox.sandbox.engine.maths.shapes;

import fr.cubibox.sandbox.engine.maths.vectors.Vector2;

public interface Shape {
    float signedDistanceFunction(Vector2 position);
}
