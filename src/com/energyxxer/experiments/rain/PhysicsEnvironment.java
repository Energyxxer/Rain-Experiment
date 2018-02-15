package com.energyxxer.experiments.rain;

import java.awt.geom.Rectangle2D;
import java.util.Collection;

public interface PhysicsEnvironment {
    double getGravity();
    int getSimulationSpeed();

    void cycle();

    default double deltaTime() {
        return 1.0 / getSimulationSpeed();
    }

    void addObject(PhysicsObject obj);

    Collection<PhysicsObject> getAllObjects();

    Collection<PhysicsObject> getObjectsIntersecting(Rectangle2D.Double bounds);

    void start();
    void end();

    long getCycle();
}
