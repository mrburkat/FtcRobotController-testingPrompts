package org.firstinspires.ftc.teamcode.common.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Owns the shared baseline hardware wrappers.
 *
 * <p>Drive hardware is required and fails loudly when configured motors are
 * missing. Intake and vision are optional and must not prevent drivetrain
 * initialization during early testing.</p>
 */
public class RobotHardware {
    private final DriveHardware driveHardware = new DriveHardware();
    private final IntakeHardware intakeHardware = new IntakeHardware();
    private final VisionHardware visionHardware = new VisionHardware();

    /**
     * Initializes hardware wrappers in the baseline order: drive, intake, then
     * vision.
     *
     * @param hardwareMap FTC hardware map supplied by the robot composition layer
     */
    public void initialize(HardwareMap hardwareMap) {
        if (hardwareMap == null) {
            throw new IllegalArgumentException("HardwareMap cannot be null.");
        }

        driveHardware.initialize(hardwareMap);
        intakeHardware.initialize(hardwareMap);
        visionHardware.initialize(hardwareMap);
    }

    /**
     * Returns the required drive hardware wrapper.
     */
    public DriveHardware getDriveHardware() {
        return driveHardware;
    }

    /**
     * Returns the optional intake hardware wrapper.
     */
    public IntakeHardware getIntakeHardware() {
        return intakeHardware;
    }

    /**
     * Returns the optional vision hardware wrapper.
     */
    public VisionHardware getVisionHardware() {
        return visionHardware;
    }

    /**
     * Stops all shared hardware wrappers.
     */
    public void stopAll() {
        driveHardware.stop();
        intakeHardware.stop();
        visionHardware.stop();
    }
}
