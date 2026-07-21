package org.firstinspires.ftc.teamcode.common.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Wraps the required four-motor drivetrain hardware.
 *
 * <p>The drivetrain is required for the baseline robot. If any configured motor
 * is missing, initialization fails with an error that includes the missing
 * hardware name.</p>
 */
public class DriveHardware {
    public static final String FRONT_LEFT_NAME = "frontLeft";
    public static final String FRONT_RIGHT_NAME = "frontRight";
    public static final String REAR_LEFT_NAME = "rearLeft";
    public static final String REAR_RIGHT_NAME = "rearRight";

    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx rearLeft;
    private DcMotorEx rearRight;

    private double lastFrontLeftPower;
    private double lastFrontRightPower;
    private double lastRearLeftPower;
    private double lastRearRightPower;

    /**
     * Finds and configures the four required drivetrain motors.
     *
     * @param hardwareMap FTC hardware map supplied by the robot composition layer
     */
    public void initialize(HardwareMap hardwareMap) {
        if (hardwareMap == null) {
            throw new IllegalArgumentException("HardwareMap cannot be null.");
        }

        frontLeft = getRequiredMotor(hardwareMap, FRONT_LEFT_NAME);
        frontRight = getRequiredMotor(hardwareMap, FRONT_RIGHT_NAME);
        rearLeft = getRequiredMotor(hardwareMap, REAR_LEFT_NAME);
        rearRight = getRequiredMotor(hardwareMap, REAR_RIGHT_NAME);

        configureDirections();
        setBrakeMode();
        setRunWithoutEncoderMode();
        stop();
    }

    /**
     * Sets drivetrain motor powers after clamping each command to the safe FTC
     * motor range of -1.0 through 1.0.
     *
     * @throws IllegalArgumentException if any power command is not a number
     */
    public void setMotorPowers(
            double frontLeftPower,
            double frontRightPower,
            double rearLeftPower,
            double rearRightPower) {
        ensureInitialized();

        lastFrontLeftPower = clampPower(frontLeftPower);
        lastFrontRightPower = clampPower(frontRightPower);
        lastRearLeftPower = clampPower(rearLeftPower);
        lastRearRightPower = clampPower(rearRightPower);

        frontLeft.setPower(lastFrontLeftPower);
        frontRight.setPower(lastFrontRightPower);
        rearLeft.setPower(lastRearLeftPower);
        rearRight.setPower(lastRearRightPower);
    }

    /**
     * Stops all drivetrain motors.
     */
    public void stop() {
        setMotorPowers(0.0, 0.0, 0.0, 0.0);
    }

    /**
     * Sets all drivetrain motors to brake when power is zero.
     */
    public void setBrakeMode() {
        ensureInitialized();
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Sets all drivetrain motors to coast when power is zero.
     */
    public void setFloatMode() {
        ensureInitialized();
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    /**
     * Returns the last commanded front-left drive power for telemetry.
     */
    public double getLastFrontLeftPower() {
        return lastFrontLeftPower;
    }

    /**
     * Returns the last commanded front-right drive power for telemetry.
     */
    public double getLastFrontRightPower() {
        return lastFrontRightPower;
    }

    /**
     * Returns the last commanded rear-left drive power for telemetry.
     */
    public double getLastRearLeftPower() {
        return lastRearLeftPower;
    }

    /**
     * Returns the last commanded rear-right drive power for telemetry.
     */
    public double getLastRearRightPower() {
        return lastRearRightPower;
    }

    private DcMotorEx getRequiredMotor(HardwareMap hardwareMap, String configuredName) {
        try {
            return hardwareMap.get(DcMotorEx.class, configuredName);
        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException(
                    "Required drive motor is missing from the hardware map: "
                            + configuredName,
                    exception);
        }
    }

    private void configureDirections() {
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        rearLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        rearRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void setRunWithoutEncoderMode() {
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        frontLeft.setZeroPowerBehavior(zeroPowerBehavior);
        frontRight.setZeroPowerBehavior(zeroPowerBehavior);
        rearLeft.setZeroPowerBehavior(zeroPowerBehavior);
        rearRight.setZeroPowerBehavior(zeroPowerBehavior);
    }

    private void ensureInitialized() {
        if (frontLeft == null || frontRight == null || rearLeft == null || rearRight == null) {
            throw new IllegalStateException("DriveHardware must be initialized before use.");
        }
    }

    private double clampPower(double power) {
        if (Double.isNaN(power)) {
            throw new IllegalArgumentException("Drive motor power cannot be NaN.");
        }
        if (power > 1.0) {
            return 1.0;
        }
        if (power < -1.0) {
            return -1.0;
        }
        return power;
    }
}
