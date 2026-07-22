package org.firstinspires.ftc.teamcode.robots.teamA;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.common.hardware.DriveHardware;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.subsystems.drive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.core.robot.Robot;

/**
 * Team A robot composition for the shared baseline robot.
 *
 * <p>OpModes should call this robot's public methods instead of reaching into
 * hardware wrappers or drive states directly. That keeps FTC entry points
 * focused on reading controls and reporting telemetry while this class owns the
 * robot composition boundary.</p>
 */
public class TeamARobot extends Robot {
    private final RobotHardware robotHardware;
    private final DriveSubsystem driveSubsystem;
    private boolean hardwareInitialized;

    /**
     * Creates Team A's robot using the standard shared hardware composition.
     */
    public TeamARobot() {
        this(new RobotHardware());
    }

    /**
     * Creates Team A's robot using supplied hardware wrappers.
     *
     * <p>This constructor keeps composition explicit and testable while still
     * registering the drive subsystem exactly once.</p>
     *
     * @param robotHardware the shared hardware wrapper composition
     */
    public TeamARobot(RobotHardware robotHardware) {
        if (robotHardware == null) {
            throw new IllegalArgumentException("RobotHardware cannot be null.");
        }

        this.robotHardware = robotHardware;
        driveSubsystem = new DriveSubsystem(robotHardware.getDriveHardware());
        registerSubsystem(driveSubsystem);
    }

    /**
     * Initializes Team A hardware and registered subsystems.
     *
     * <p>This is the robot boundary where FTC hardware mapping enters Team A
     * code. The hardware wrappers own all direct device lookup, and the inherited
     * robot lifecycle initializes the registered subsystems exactly once.</p>
     *
     * @param hardwareMap FTC hardware map supplied by the OpMode
     */
    public void initialize(HardwareMap hardwareMap) {
        if (hardwareMap == null) {
            throw new IllegalArgumentException("HardwareMap cannot be null.");
        }

        if (!hardwareInitialized) {
            robotHardware.initialize(hardwareMap);
            hardwareInitialized = true;
        }

        super.initialize();
    }

    /**
     * Stores a drive request for the next robot update.
     */
    public void drive(double forward, double strafe, double rotate) {
        driveSubsystem.drive(forward, strafe, rotate);
    }

    /**
     * Requests manual drive mode.
     */
    public void enableManualDrive() {
        driveSubsystem.enableManualDrive();
    }

    /**
     * Requests disabled drive mode.
     */
    public void disableDrive() {
        driveSubsystem.disableDrive();
    }

    /**
     * Requests placeholder heading-hold drive mode.
     */
    public void enableHeadingHold() {
        driveSubsystem.enableHeadingHold();
    }

    /**
     * Returns the current drive state name for telemetry.
     */
    public String getDriveStateName() {
        return driveSubsystem.getCurrentStateName();
    }

    /**
     * Returns the last requested forward drive input.
     */
    public double getRequestedForward() {
        return driveSubsystem.getRequestedForward();
    }

    /**
     * Returns the last requested strafe drive input.
     */
    public double getRequestedStrafe() {
        return driveSubsystem.getRequestedStrafe();
    }

    /**
     * Returns the last requested rotation drive input.
     */
    public double getRequestedRotate() {
        return driveSubsystem.getRequestedRotate();
    }

    /**
     * Returns the last commanded front-left drive power.
     */
    public double getLastFrontLeftPower() {
        return getDriveHardware().getLastFrontLeftPower();
    }

    /**
     * Returns the last commanded front-right drive power.
     */
    public double getLastFrontRightPower() {
        return getDriveHardware().getLastFrontRightPower();
    }

    /**
     * Returns the last commanded rear-left drive power.
     */
    public double getLastRearLeftPower() {
        return getDriveHardware().getLastRearLeftPower();
    }

    /**
     * Returns the last commanded rear-right drive power.
     */
    public double getLastRearRightPower() {
        return getDriveHardware().getLastRearRightPower();
    }

    /**
     * Reports whether the optional intake hardware is available.
     */
    public boolean isIntakeAvailable() {
        return robotHardware.getIntakeHardware().isAvailable();
    }

    /**
     * Reports whether the optional vision hardware is available.
     */
    public boolean isVisionAvailable() {
        return robotHardware.getVisionHardware().isAvailable();
    }

    /**
     * Stops the robot lifecycle and then stops all hardware wrappers.
     */
    @Override
    protected void onStop() {
        robotHardware.stopAll();
    }

    private DriveHardware getDriveHardware() {
        return robotHardware.getDriveHardware();
    }
}
