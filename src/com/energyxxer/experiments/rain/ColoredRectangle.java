package com.energyxxer.experiments.rain;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class ColoredRectangle {
    private Color color;
    private Rectangle2D.Double rect;

    public ColoredRectangle(Color color, Rectangle2D.Double rect) {
        this.color = color;
        this.rect = rect;
    }

    public Color getColor() {
        return color;
    }

    public Rectangle2D.Double getRect() {
        return rect;
    }

    @Override
    public String toString() {
        return "ColoredRectangle{" +
                "color=" + color +
                ", rect=" + rect +
                '}';
    }
}
