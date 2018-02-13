package com.energyxxer.experiments.rain;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RainExperiment implements PhysicsEnvironment {
    private final double dropletDensity = 10; //10 droplets per meter;
    private final double dropletSpread = 10; //meters in each direction
    private final double dropletFrequency = 2 * dropletDensity * dropletSpread;

    private final float distanceToCover = 15; //meters
    private final float subjectSpeed = 1; //meters per second

    private final double subjectWidth = 0.25;
    private final double subjectHeight = 1.5;

    private final double dropAltitude = 1981;

    private final double dropletHeight = 3;

    private final double dropletInitialSpeed = Math.sqrt(2*getGravity()*(dropAltitude-dropletHeight));

    private final Subject subject;
    private final ArrayList<PhysicsObject> objects = new ArrayList<>();

    private double dropletTimer = 0; //When 1, it will spawn a droplet and subtract 1 from timer. Used to apply deltaTime to droplet frequency

    private boolean running = false;
    private long cycles = 0;

    public RainExperiment() {
        this.subject = new Subject(this, new Point2D.Double(), new DimensionDouble(subjectWidth, subjectHeight), subjectSpeed);
        objects.add(subject);
        //objects.add(new Droplet(this, new Point2D.Double(0, dropletHeight)));
    }

    private void render(Graphics2D g, Dimension frameSize) {
    }

    private ArrayList<PhysicsObject> toRemove = new ArrayList<>();

    @Override
    public void cycle() {
        dropletTimer += dropletFrequency * deltaTime();

        while(dropletTimer > 1) {
            objects.add(new Droplet(this, new Point2D.Double(((Math.random()-0.5)*(2*dropletSpread)) + subject.pos.x, dropletHeight), dropletInitialSpeed));
            dropletTimer--;
        }

        for(PhysicsObject object : objects) {
            object.cycle();
            if(object.isMarkedForRemoval()) toRemove.add(object);
        }

        objects.removeAll(toRemove);

        //if(objects.size() > 1000) System.out.println("" + objects.size() + " objects");
        toRemove.clear();

        cycles++;
    }

    @Override
    public Collection<PhysicsObject> getAllObjects() {
        return objects;
    }

    @Override
    public Collection<PhysicsObject> getObjectsIntersecting(Rectangle2D.Double bounds) {
        if(subject.getBounds().intersects(bounds)) {
            return Collections.singletonList(subject);
        }
        else return Collections.emptyList();
    }

    @Override
    public double getGravity() {
        return 9.81;
    }

    @Override
    public int getSimulationSpeed() {
        return 1000;
    }

    @Override
    public long getCycle() {
        return cycles;
    }

    @Override
    public void start() {
        cycles = 0;
        running = true;
        while(running) {
            cycle();
            /*try {
                Thread.sleep((long) (1000 * deltaTime()));
            } catch(InterruptedException x) {
                x.printStackTrace();
                running = false;
            }*/
        }
    }

    @Override
    public void end() {
        running = false;
        System.out.println("Simulation ended after " + cycles + " cycles (or " + ((double) cycles * deltaTime()) + " simulated seconds) with " + subject.getHits() + " hits");
    }
}
