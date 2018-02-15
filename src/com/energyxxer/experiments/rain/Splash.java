package com.energyxxer.experiments.rain;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Collections;

public class Splash extends PhysicsObject implements Renderable {
    private Point2D.Double pos;
    private int cycles = 500;
    private double size = 0.1;

    public Splash(PhysicsEnvironment environment, Point2D.Double pos) {
        super(environment, pos, new DimensionDouble(0,0));
        this.pos = pos;
        this.motion.y = 2;
    }

    @Override
    public void cycle() {
        super.cycle();

        size = cycles / 5000.0;

        cycles--;
        if(cycles <= 0) this.markForRemoval();
    }

    @Override
    public void hit(PhysicsObject object) {

    }

    @Override
    public void gotHitBy(PhysicsObject object) {

    }

    @Override
    public Collection<ColoredRectangle> getVisibleParts() {
        return Collections.singletonList(new ColoredRectangle(new Color(140, 170, 220), new Rectangle2D.Double(pos.x-size/2, pos.y, size, size)));
    }
}
