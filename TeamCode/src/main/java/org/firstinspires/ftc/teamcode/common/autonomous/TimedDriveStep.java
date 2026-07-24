package org.firstinspires.ftc.teamcode.common.autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Requests mecanum drive values for a fixed non-blocking duration.
 */
public class TimedDriveStep implements AutoStep {
    private static final String NAME = "TimedDrive";

    private final AutonomousDriveControl driveControl;
    private final double forward;
    private final double strafe;
    private final double rotate;
    private final double durationSeconds;
    private final ElapsedTime timer = new ElapsedTime();

    private boolean started;

    /**
     * Creates a timed drive step.
     */
    public TimedDriveStep(
            AutonomousDriveControl driveControl,
            double forward,
            double strafe,
            double rotate,
            double durationSeconds) {
        if (driveControl == null) {
            throw new IllegalArgumentException("AutonomousDriveControl cannot be null.");
        }

        this.driveControl = driveControl;
        this.forward = cleanDriveInput(forward, "forward");
        this.strafe = cleanDriveInput(strafe, "strafe");
        this.rotate = cleanDriveInput(rotate, "rotate");
        this.durationSeconds = cleanDuration(durationSeconds);
    }

    @Override
    public void start() {
        timer.reset();
        started = true;
        driveControl.enableManualDrive();
        driveControl.drive(forward, strafe, rotate);
    }

    @Override
    public void update() {
        driveControl.enableManualDrive();
        driveControl.drive(forward, strafe, rotate);
    }

    @Override
    public boolean isFinished() {
        return started && timer.seconds() >= durationSeconds;
    }

    @Override
    public void stop() {
        driveControl.stopDrive();
    }

    @Override
    public String getName() {
        return NAME;
    }

    private double cleanDriveInput(double value, String inputName) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("Drive input cannot be NaN: " + inputName);
        }
        if (value > 1.0) {
            return 1.0;
        }
        if (value < -1.0) {
            return -1.0;
        }
        return value;
    }

    private double cleanDuration(double durationSeconds) {
        if (Double.isNaN(durationSeconds)) {
            throw new IllegalArgumentException("Duration cannot be NaN.");
        }
        return Math.max(0.0, durationSeconds);
    }
}
