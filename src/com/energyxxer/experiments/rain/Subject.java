package com.energyxxer.experiments.rain;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class Subject extends PhysicsObject {
    private final double speed;

    private int hits = 0;

    public Subject(PhysicsEnvironment environment, Point2D.Double pos, Dimension2D size, double speed) {
        super(environment, pos, size);
        this.speed = speed;
    }

    @Override
    public void cycle() {
        this.pos.x += speed * environment.deltaTime();
        super.cycle();
    }

    public int getHits() {
        return hits;
    }

    @Override
    public boolean isGravityEnabled() {
        return false;
    }

    @Override
    public void hit(PhysicsObject object) {

    }

    @Override
    public void gotHitBy(PhysicsObject object) {
        if(object instanceof Droplet) hits++;
        if(hits > 100) environment.end();
    }
}
