package com.energyxxer.experiments.rain;

import java.awt.Color;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Collections;

public class Subject extends PhysicsObject implements Renderable {
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
    }

    @Override
    public Collection<ColoredRectangle> getVisibleParts() {
        return Collections.singletonList(new ColoredRectangle(new Color(80, 180, 120), getBounds()));
    }
}
