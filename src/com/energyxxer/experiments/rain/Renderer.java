package com.energyxxer.experiments.rain;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

public class Renderer extends JComponent {
    private final RainExperiment experiment;

    private final Timer timer;

    public Renderer(RainExperiment experiment) {
        this.experiment = experiment;

        this.timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, 1);

        this.setPreferredSize(experiment.getCamera().getSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        experiment.render(g2d);
    }
}
