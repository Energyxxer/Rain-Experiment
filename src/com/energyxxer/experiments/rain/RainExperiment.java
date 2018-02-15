package com.energyxxer.experiments.rain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RainExperiment implements PhysicsEnvironment {
    private final Random random = new Random();

    private final double dropletDensity = 1000; //10 droplets per meter;
    private final double dropletSpread = 25; //meters in each direction
    private final double dropletFrequency = 2 * dropletDensity * dropletSpread;

    private final double subjectSpeed = 50; //meters per second

    private final double subjectWidth = 0.25;
    private final double subjectHeight = 1.25;

    private final double dropAltitude = 1981;

    private final double dropletHeight = 3;

    private final double dropletInitialSpeed = Math.sqrt(2*getGravity()*(dropAltitude-dropletHeight));

    private final Subject subject;
    private final ArrayList<PhysicsObject> objects = new ArrayList<>();

    private double dropletTimer = 0; //When 1, it will spawn a droplet and subtract 1 from timer. Used to apply deltaTime to droplet frequency

    private boolean running = false;
    private long cycles = 0;

    private Ground ground;

    private ExperimentGoal goal;

    private CameraViewport camera;

    private boolean instant;
    private Timer timer;

    private final double speedMultiplier = 1;

    public RainExperiment(ExperimentGoal goal, Dimension frameSize, boolean instant) {
        this.subject = new Subject(this, new Point2D.Double(), new DimensionDouble(subjectWidth, subjectHeight), subjectSpeed);
        objects.add(subject);
        this.goal = goal;
        this.ground = new Ground();

        this.camera = new CameraViewport(frameSize);

        this.instant = instant;

        if(!instant) {
            this.timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(running) cycle();
                }
            }, 0, (int) (1000 / (getSimulationSpeed() * speedMultiplier)));
        }
        //objects.add(new Droplet(this, new Point2D.Double(0, dropletHeight)));
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(20, 20, 20));
        g.fillRect(0, 0, camera.getSize().width, camera.getSize().height);

        ArrayList<Renderable> renderables = new ArrayList<>();
        renderables.add(ground);
        for(int i = 0; i < objects.size(); i++) {
            PhysicsObject o = objects.get(i);
            if(o instanceof Renderable) renderables.add((Renderable) o);
        }

        for(Renderable renderable : renderables) {
            for(ColoredRectangle crect : renderable.getVisibleParts()) {
                g.setColor(crect.getColor());

                Rectangle2D.Double rect = camera.transform(crect.getRect());

                g.fillRect((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height);
            }
        }
    }

    private ArrayList<PhysicsObject> toRemove = new ArrayList<>();

    @Override
    public void cycle() {
        dropletTimer += dropletFrequency * deltaTime();

        while(dropletTimer > 1) {
            objects.add(new Droplet(this, new Point2D.Double((random.nextDouble()-0.5)*(2*dropletSpread) + subject.pos.x, dropletHeight), dropletInitialSpeed));
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

        if(this.goal.reached(this)) this.end();
        this.camera.setPos(subject.getBounds().getCenterX(), subject.getBounds().getCenterY());

        this.objects.addAll(objectQueue);
        objectQueue.clear();
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

    private ArrayList<PhysicsObject> objectQueue = new ArrayList<>();

    @Override
    public void addObject(PhysicsObject obj) {
        objectQueue.add(obj);
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

    public Subject getSubject() {
        return subject;
    }

    public CameraViewport getCamera() {
        return camera;
    }

    @Override
    public void start() {
        cycles = 0;
        running = true;
        if(instant) {
            while(running) {
                cycle();
            }
        }
    }

    @Override
    public void end() {
        running = false;
        System.out.println("Simulation ended after " + goal);
        System.out.println("\tTime elapsed: " + (cycles * deltaTime()) + " seconds (" + cycles + " cycles)");
        System.out.println("\tDistance covered: " + subject.pos.x + " meters");
        System.out.println("\tDroplets hit: " + subject.getHits());
    }
}
