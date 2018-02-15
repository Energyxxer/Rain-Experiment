package com.energyxxer.experiments.rain;

public class ExperimentGoal {
    public enum Mode {
        DROPLETS("# droplets hit", (experiment, amount) -> experiment.getSubject().getHits() >= amount),
        DISTANCE("# meters covered", (experiment, amount) -> experiment.getSubject().pos.x >= amount),
        TIME("# seconds elapsed", (experiment, amount) -> experiment.getCycle()*experiment.deltaTime() >= amount);

        private String format;
        private GoalConfirmation confirmation;

        Mode(String format, GoalConfirmation confirmation) {
            this.format = format;
            this.confirmation = confirmation;
        }

        public String format(double amount) {
            return format.replace("#",Double.toString(amount));
        }

        public boolean confirm(RainExperiment experiment, double amount) {
            return confirmation.confirm(experiment, amount);
        }

        private interface GoalConfirmation {
            boolean confirm(RainExperiment experiment, double amount);
        }
    }

    private final Mode mode;
    private final double amount;

    public ExperimentGoal(Mode mode, double amount) {
        this.mode = mode;
        this.amount = amount;
    }

    public boolean reached(RainExperiment experiment) {
        return mode.confirm(experiment, amount);
    }

    @Override
    public String toString() {
        return mode.format(amount);
    }
}