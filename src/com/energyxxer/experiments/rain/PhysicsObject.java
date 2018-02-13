package com.energyxxer.experiments.rain;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;

public abstract class PhysicsObject {
    protected final PhysicsEnvironment environment;

    protected Point2D.Double pos;
    protected Dimension2D size;

    protected Point2D.Double motion;

    private boolean toRemove;

    public PhysicsObject(PhysicsEnvironment environment, Point2D.Double pos, Dimension2D size) {
        this.environment = environment;

        this.pos = pos;
        this.size = size;

        this.motion = new Point2D.Double();
    }

    public boolean isGravityEnabled() {
        return true;
    }

    public void cycle() {
        if(isGravityEnabled()) {
            //System.out.println("Gravity applied: " + environment.getGravity() * environment.deltaTime());
            this.motion.y -= environment.getGravity() * environment.deltaTime();
            //System.out.println("New motion: " + motion);
        }
        this.pos.x += this.motion.x * environment.deltaTime();
        this.pos.y += this.motion.y * environment.deltaTime();

        Collection<PhysicsObject> intersecting = environment.getObjectsIntersecting(this.getBounds());
        intersecting.forEach(o -> {
            if(o != this) this.hit(o);
        });
        intersecting.forEach(o -> {
            if(o != this) o.gotHitBy(this);
        });
    }

    public abstract void hit(PhysicsObject object);

    public abstract void gotHitBy(PhysicsObject object);

    protected void markForRemoval() {
        toRemove = true;
    }

    public boolean isMarkedForRemoval() {
        return toRemove;
    }

    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(pos.x-size.getWidth()/2, pos.y, size.getWidth(), size.getHeight());
    }
}
