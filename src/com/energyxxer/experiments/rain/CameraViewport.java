package com.energyxxer.experiments.rain;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class CameraViewport {
    private Point2D.Double pos;
    private Dimension size;
    private double scale = 100;

    public CameraViewport(Dimension size) {
        this.pos = new Point2D.Double();
        this.size = size;
    }

    public Point2D.Double transform(Point2D.Double pos) {
        double x = pos.x;
        double y = pos.y;

        x -= this.pos.x;
        y -= this.pos.y;

        x *= scale;
        y *= scale;

        x += size.getWidth()/2;
        y = size.getHeight()/2 - y;

        return new Point2D.Double(x, y);
    }

    public Rectangle2D.Double transform(Rectangle2D.Double rect) {
        Point2D.Double p1 = new Point2D.Double(rect.x, rect.y);
        Point2D.Double p2 = new Point2D.Double(rect.x + rect.width, rect.y + rect.height);

        p1 = transform(p1);
        p2 = transform(p2);

        double x = p1.x;
        double y = p2.y;

        double width = p2.x - x;
        double height = p1.y - y;

        return new Rectangle2D.Double(x, y, width, height);
    }

    public Point2D.Double getPos() {
        return pos;
    }

    public Dimension getSize() {
        return size;
    }

    public double getScale() {
        return scale;
    }

    public void setPos(double x, double y) {
        pos.x = x;
        pos.y = y;

    }
}
