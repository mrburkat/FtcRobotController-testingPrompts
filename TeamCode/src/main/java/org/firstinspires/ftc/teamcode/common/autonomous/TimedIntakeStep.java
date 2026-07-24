package org.firstinspires.ftc.teamcode.common.autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Requests intake for a fixed non-blocking duration.
 */
public class TimedIntakeStep implements AutoStep {
    private static final String NAME = "TimedIntake";

    private final AutonomousIntakeControl intakeControl;
    private final double durationSeconds;
    private final ElapsedTime timer = new ElapsedTime();

    private boolean started;

    /**
     * Creates a timed intake step.
     */
    public TimedIntakeStep(AutonomousIntakeControl intakeControl, double durationSeconds) {
        if (intakeControl == null) {
            throw new IllegalArgumentException("AutonomousIntakeControl cannot be null.");
        }

        this.intakeControl = intakeControl;
        this.durationSeconds = cleanDuration(durationSeconds);
    }

    @Override
    public void start() {
        timer.reset();
        started = true;
        intakeControl.startIntake();
    }

    @Override
    public void update() {
        intakeControl.startIntake();
    }

    @Override
    public boolean isFinished() {
        return started && timer.seconds() >= durationSeconds;
    }

    @Override
    public void stop() {
        intakeControl.stopIntake();
    }

    @Override
    public String getName() {
        return NAME;
    }

    private double cleanDuration(double durationSeconds) {
        if (Double.isNaN(durationSeconds)) {
            throw new IllegalArgumentException("Duration cannot be NaN.");
        }
        return Math.max(0.0, durationSeconds);
    }
}
