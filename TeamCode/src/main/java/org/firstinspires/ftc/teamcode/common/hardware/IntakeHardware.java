package org.firstinspires.ftc.teamcode.common.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Wraps the optional intake motor.
 *
 * <p>The intake is optional during early robot bring-up. If the configured
 * motor is missing, this wrapper records that the intake is unavailable and all
 * commands become safe no-ops.</p>
 */
public class IntakeHardware {
    public static final String INTAKE_MOTOR_NAME = "intake";

    private DcMotorEx intakeMotor;
    private boolean available;
    private double lastPower;

    /**
     * Attempts to find and configure the optional intake motor.
     *
     * @param hardwareMap FTC hardware map supplied by the robot composition layer
     */
    public void initialize(HardwareMap hardwareMap) {
        if (hardwareMap == null) {
            throw new IllegalArgumentException("HardwareMap cannot be null.");
        }

        try {
            intakeMotor = hardwareMap.get(DcMotorEx.class, INTAKE_MOTOR_NAME);
            intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            available = true;
            stop();
        } catch (IllegalArgumentException exception) {
            intakeMotor = null;
            available = false;
            lastPower = 0.0;
        }
    }

    /**
     * Runs the intake forward at full power when available.
     */
    public void forward() {
        setPowerIfAvailable(1.0);
    }

    /**
     * Runs the intake backward at full power when available.
     */
    public void reverse() {
        setPowerIfAvailable(-1.0);
    }

    /**
     * Stops the intake when available.
     */
    public void stop() {
        setPowerIfAvailable(0.0);
    }

    /**
     * Reports whether the optional intake motor was found.
     *
     * @return true when intake commands can control a motor
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Returns the last intake power command for telemetry.
     *
     * @return the last commanded intake power, or 0.0 when unavailable
     */
    public double getLastPower() {
        return lastPower;
    }

    private void setPowerIfAvailable(double power) {
        if (!available) {
            lastPower = 0.0;
            return;
        }

        lastPower = power;
        intakeMotor.setPower(lastPower);
    }
}
