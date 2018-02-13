package com.energyxxer.experiments.rain;

import java.awt.geom.Point2D;

public class Droplet extends PhysicsObject {

    private static final double SIZE = 2.0/1000.0; //2 millimeters

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
    protected void markForRemoval() {
        super.markForRemoval();
        //System.out.println("Droplet had y motion " + motion.y + " at y = " + pos.y + " after " + (environment.getCycle() * environment.deltaTime()) + " simulated seconds");
        //System.out.println("Droplet's final bounds: " + this.getBounds());
    }

    @Override
    public void hit(PhysicsObject object) {
        this.markForRemoval();
    }

    @Override
    public void gotHitBy(PhysicsObject object) {

    }
}
