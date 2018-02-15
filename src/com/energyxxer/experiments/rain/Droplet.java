package com.energyxxer.experiments.rain;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

public class Droplet extends PhysicsObject implements Renderable {

    private static final double SIZE = 2.0/1000.0; //2 millimeters
    private static final double APPARENT_SIZE = SIZE*20; // 1/16 of a meter

    public Droplet(PhysicsEnvironment environment, Point2D.Double pos, double initialSpeed) {
        super(environment, pos, new DimensionDouble(SIZE));
        motion.y = -initialSpeed;
    }

    @Override
    public void cycle() {
        super.cycle();
        if(this.pos.y <= 0) markForRemoval();
        //System.out.println("Droplet at " + pos + " with motion " + motion);
        //System.out.println(this + " is at " + pos);
    }

    @Override
    public void hit(PhysicsObject object) {
        environment.addObject(new Splash(environment, this.pos));
        this.markForRemoval();
    }

    @Override
    public void gotHitBy(PhysicsObject object) {

    }

    @Override
    public Collection<ColoredRectangle> getVisibleParts() {
        ArrayList<ColoredRectangle> parts = new ArrayList<>();
        parts.add(new ColoredRectangle(new Color(80, 120, 180), new Rectangle2D.Double(pos.x - APPARENT_SIZE / 2, pos.y, APPARENT_SIZE, APPARENT_SIZE)));
        parts.add(new ColoredRectangle(new Color(80, 120, 180, 80), new Rectangle2D.Double(pos.x - APPARENT_SIZE / 2, pos.y + motion.y, APPARENT_SIZE, motion.y - APPARENT_SIZE)));
        return parts;
    }
}
