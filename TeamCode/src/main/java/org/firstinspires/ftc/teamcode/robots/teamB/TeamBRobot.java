package org.firstinspires.ftc.teamcode.robots.teamB;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.common.autonomous.AutonomousDriveControl;
import org.firstinspires.ftc.teamcode.common.autonomous.AutonomousIntakeControl;
import org.firstinspires.ftc.teamcode.common.hardware.DriveHardware;
import org.firstinspires.ftc.teamcode.common.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.common.subsystems.drive.DriveSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.vision.VisionSubsystem;
import org.firstinspires.ftc.teamcode.core.robot.Robot;

/**
 * Team B robot composition skeleton.
 *
 * <p>This class starts from the shared baseline hardware and subsystem
 * composition so Team B can compile and test lifecycle code before
 * robot-specific wiring, mechanisms, and constants are finalized.</p>
 */
public class TeamBRobot extends Robot implements AutonomousDriveControl, AutonomousIntakeControl {
    private final RobotHardware robotHardware;
    private final DriveSubsystem driveSubsystem;
    private final IntakeSubsystem intakeSubsystem;
    private final VisionSubsystem visionSubsystem;
    private boolean hardwareInitialized;

    /**
     * Creates Team B's robot using the standard shared hardware composition.
     */
    public TeamBRobot() {
        this(new RobotHardware());
    }

    /**
     * Creates Team B's robot using supplied hardware wrappers.
     *
     * <p>This constructor keeps composition explicit while registering each
     * subsystem exactly once.</p>
     *
     * @param robotHardware shared hardware wrapper composition
     */
    public TeamBRobot(RobotHardware robotHardware) {
        if (robotHardware == null) {
            throw new IllegalArgumentException("RobotHardware cannot be null.");
        }

        this.robotHardware = robotHardware;
        // TODO: Team B may replace shared wrappers when final wiring and motor
        // directions are known.
        driveSubsystem = new DriveSubsystem(robotHardware.getDriveHardware());
        intakeSubsystem = new IntakeSubsystem(robotHardware.getIntakeHardware());
        visionSubsystem = new VisionSubsystem(robotHardware.getVisionHardware());
        // TODO: Add or swap Team B-specific subsystems here as unique
        // mechanisms are designed.
        registerSubsystem(driveSubsystem);
        registerSubsystem(intakeSubsystem);
        registerSubsystem(visionSubsystem);
    }

    /**
     * Initializes Team B hardware and registered subsystems.
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
    @Override
    public void drive(double forward, double strafe, double rotate) {
        driveSubsystem.drive(forward, strafe, rotate);
    }

    /**
     * Requests manual drive mode.
     */
    @Override
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
     * Requests safe stopped drive output.
     */
    @Override
    public void stopDrive() {
        driveSubsystem.drive(0.0, 0.0, 0.0);
        driveSubsystem.disableDrive();
    }

    /**
     * Requests placeholder heading-hold drive mode.
     */
    public void enableHeadingHold() {
        driveSubsystem.enableHeadingHold();
    }

    /**
     * Requests intake mode.
     */
    @Override
    public void startIntake() {
        intakeSubsystem.startIntake();
    }

    /**
     * Requests safe idle intake mode.
     */
    @Override
    public void stopIntake() {
        intakeSubsystem.stopIntake();
    }

    /**
     * Requests baseline holding mode.
     */
    public void holdIntake() {
        intakeSubsystem.hold();
    }

    /**
     * Requests ejecting mode.
     */
    public void ejectIntake() {
        intakeSubsystem.eject();
    }

    /**
     * Requests vision processing when optional vision hardware is available.
     */
    public void enableVision() {
        visionSubsystem.enableVision();
    }

    /**
     * Requests safe disabled vision mode.
     */
    public void disableVision() {
        visionSubsystem.disableVision();
    }

    /**
     * Returns the current drive state name for telemetry.
     */
    public String getDriveStateName() {
        return driveSubsystem.getCurrentStateName();
    }

    /**
     * Returns the current intake state name for telemetry.
     */
    public String getIntakeStateName() {
        return intakeSubsystem.getCurrentStateName();
    }

    /**
     * Returns the current vision state name for telemetry.
     */
    public String getVisionStateName() {
        return visionSubsystem.getCurrentStateName();
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
        return intakeSubsystem.isAvailable();
    }

    /**
     * Reports whether the optional vision hardware is available.
     */
    public boolean isVisionAvailable() {
        return visionSubsystem.isAvailable();
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
