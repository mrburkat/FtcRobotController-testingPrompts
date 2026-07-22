package org.firstinspires.ftc.teamcode.common.subsystems.drive;

import org.firstinspires.ftc.teamcode.common.hardware.DriveHardware;
import org.firstinspires.ftc.teamcode.core.fsm.FSM;
import org.firstinspires.ftc.teamcode.core.fsm.Transition;
import org.firstinspires.ftc.teamcode.core.robot.Subsystem;

/**
 * Shared mecanum drive subsystem.
 *
 * <p>The subsystem stores drive requests from higher layers, owns the drive FSM,
 * and is the single place where mecanum wheel powers are calculated. Higher
 * layers request a mode; they do not manipulate FSM state directly.</p>
 */
public class DriveSubsystem implements Subsystem {
    private static final String NAME = "DriveSubsystem";

    private final DriveHardware driveHardware;
    private final FSM fsm;

    private DriveMode requestedMode = DriveMode.DISABLED;
    private double requestedForward;
    private double requestedStrafe;
    private double requestedRotate;
    private boolean initialized;

    /**
     * Creates a drive subsystem using the shared drive hardware wrapper.
     *
     * @param driveHardware initialized drive hardware wrapper owned by the robot
     */
    public DriveSubsystem(DriveHardware driveHardware) {
        if (driveHardware == null) {
            throw new IllegalArgumentException("DriveHardware cannot be null.");
        }

        this.driveHardware = driveHardware;

        DisabledDriveState disabledState = new DisabledDriveState(this);
        ManualDriveState manualState = new ManualDriveState(this);
        HeadingHoldState headingHoldState = new HeadingHoldState(this);

        fsm = new FSM(disabledState);
        fsm.addTransition(new Transition(
                disabledState,
                manualState,
                () -> requestedMode == DriveMode.MANUAL));
        fsm.addTransition(new Transition(
                disabledState,
                headingHoldState,
                () -> requestedMode == DriveMode.HEADING_HOLD));
        fsm.addTransition(new Transition(
                manualState,
                disabledState,
                () -> requestedMode == DriveMode.DISABLED));
        fsm.addTransition(new Transition(
                manualState,
                headingHoldState,
                () -> requestedMode == DriveMode.HEADING_HOLD));
        fsm.addTransition(new Transition(
                headingHoldState,
                disabledState,
                () -> requestedMode == DriveMode.DISABLED));
        fsm.addTransition(new Transition(
                headingHoldState,
                manualState,
                () -> requestedMode == DriveMode.MANUAL));
    }

    /**
     * Starts the drive FSM in its safe disabled state.
     */
    @Override
    public void initialize() {
        if (initialized) {
            return;
        }

        requestedMode = DriveMode.DISABLED;
        fsm.start();
        initialized = true;
    }

    /**
     * Updates the active drive state once.
     */
    @Override
    public void update() {
        fsm.update();
    }

    /**
     * Stops drive output and requests disabled mode.
     */
    @Override
    public void stop() {
        requestedMode = DriveMode.DISABLED;
        stopDrive();
    }

    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Stores a driver or autonomous drive request for the next update.
     *
     * @param forward positive values request forward motion
     * @param strafe positive values request right strafe motion
     * @param rotate positive values request clockwise rotation
     */
    public void drive(double forward, double strafe, double rotate) {
        requestedForward = cleanInput(forward, "forward");
        requestedStrafe = cleanInput(strafe, "strafe");
        requestedRotate = cleanInput(rotate, "rotate");
    }

    /**
     * Requests manual mecanum driving.
     */
    public void enableManualDrive() {
        requestedMode = DriveMode.MANUAL;
    }

    /**
     * Requests safe disabled driving.
     */
    public void disableDrive() {
        requestedMode = DriveMode.DISABLED;
    }

    /**
     * Requests heading-hold placeholder mode.
     *
     * <p>This baseline does not have heading feedback yet. The placeholder mode
     * allows translation commands and suppresses rotation so the robot has a safe
     * path until later prompts add real heading feedback.</p>
     */
    public void enableHeadingHold() {
        requestedMode = DriveMode.HEADING_HOLD;
    }

    /**
     * Returns the active drive FSM state name for telemetry.
     */
    public String getCurrentStateName() {
        return fsm.getCurrentStateName();
    }

    public double getRequestedForward() {
        return requestedForward;
    }

    public double getRequestedStrafe() {
        return requestedStrafe;
    }

    public double getRequestedRotate() {
        return requestedRotate;
    }

    void stopDrive() {
        driveHardware.stop();
    }

    void applyManualDrive() {
        setMecanumPowers(requestedForward, requestedStrafe, requestedRotate);
    }

    void applyHeadingHoldPlaceholder() {
        setMecanumPowers(requestedForward, requestedStrafe, 0.0);
    }

    private void setMecanumPowers(double forward, double strafe, double rotate) {
        double frontLeft = forward + strafe + rotate;
        double frontRight = forward - strafe - rotate;
        double rearLeft = forward - strafe + rotate;
        double rearRight = forward + strafe - rotate;

        double scale = maxAbs(1.0, frontLeft, frontRight, rearLeft, rearRight);

        driveHardware.setMotorPowers(
                frontLeft / scale,
                frontRight / scale,
                rearLeft / scale,
                rearRight / scale);
    }

    private double cleanInput(double value, String inputName) {
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

    private double maxAbs(double lowerBound, double... values) {
        double result = lowerBound;
        for (double value : values) {
            result = Math.max(result, Math.abs(value));
        }
        return result;
    }

    private enum DriveMode {
        DISABLED,
        MANUAL,
        HEADING_HOLD
    }
}
