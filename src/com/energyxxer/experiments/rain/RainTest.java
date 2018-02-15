package com.energyxxer.experiments.rain;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;

public class RainTest {
    public static void main(String[] args) {
        Dimension frameSize = new Dimension(800, 500);

        RainExperiment experiment = new RainExperiment(new ExperimentGoal(ExperimentGoal.Mode.DISTANCE, 100), frameSize, true);

        JFrame jframe = new JFrame("Rain Experiment");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jframe.setContentPane(new Renderer(experiment));

        jframe.pack();
        jframe.setVisible(true);

        experiment.start();
        System.out.println("Started");

        /*
        * Conclusions:
        * When aiming to cover a specific distance in the rain (goal:distance), it's best to run.
        * When aiming to cover some distance while waiting for the rain to dissipate (goal:time), it's best not to move (or walking).
        * */
    }
}
