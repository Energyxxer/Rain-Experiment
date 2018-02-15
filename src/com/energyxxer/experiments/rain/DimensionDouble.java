package com.energyxxer.experiments.rain;

import java.awt.geom.Dimension2D;

public class DimensionDouble extends Dimension2D {

    public double width;
    private double height;

    public DimensionDouble() {
        this(0);
    }

    public DimensionDouble(double size) {
        this(size, size);
    }

    public DimensionDouble(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public java.awt.Dimension toAwt() {
        return new java.awt.Dimension((int) width, (int) height);
    }

    @Override
    public String toString() {
        return "DimensionDouble{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
