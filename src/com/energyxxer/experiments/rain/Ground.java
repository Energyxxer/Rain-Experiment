package com.energyxxer.experiments.rain;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

public class Ground implements Renderable {

    @Override
    public Collection<ColoredRectangle> getVisibleParts() {
        ArrayList<ColoredRectangle> parts = new ArrayList<>();
        parts.add(new ColoredRectangle(new Color(120, 130, 140), new Rectangle2D.Double(-100, -100, 1000, 100)));
        return parts;
    }
}
