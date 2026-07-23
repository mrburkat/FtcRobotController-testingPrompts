package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.core.input.InputManager;
import org.firstinspires.ftc.teamcode.robots.teamA.TeamARobot;

/**
 * Driver-controlled OpMode for Team A's baseline four-wheel mecanum robot.
 *
 * <p>This OpMode reads gamepad input, calls Team A robot public APIs, and
 * reports telemetry. Drivetrain hardware lookup, mecanum math, subsystem
 * lifecycle, and drive-state logic stay behind the robot boundary.</p>
 */
@TeleOp(name = "Team A TeleOp", group = "Team A")
public class TeamATeleOp extends OpMode {
    private TeamARobot robot;
    private InputManager inputManager;
    private boolean initialized;

    /**
     * Builds Team A robot composition and initializes configured hardware.
     */
    @Override
    public void init() {
        robot = new TeamARobot();
        inputManager = new InputManager();

        try {
            robot.initialize(hardwareMap);
            initialized = true;
            telemetry.addData("Status", "Team A robot initialized");
        } catch (RuntimeException exception) {
            telemetry.addData("Initialization Error", exception.getMessage());
            telemetry.addData("Status", "Check Team A drive motor configuration");
            telemetry.update();
            throw exception;
        }

        telemetry.update();
    }

    /**
     * Requests manual drive and captures the first gamepad snapshot before the
     * first loop. Buttons held while pressing play are treated as held, not as
     * just-pressed edges.
     */
    @Override
    public void start() {
        ensureInitialized();
        robot.enableManualDrive();
        inputManager.update(gamepad1);

        telemetry.addData("Status", "Team A TeleOp started");
        telemetry.update();
    }

    /**
     * Runs one driver-control loop: read input, map controls, update robot, and
     * report diagnostics.
     */
    @Override
    public void loop() {
        ensureInitialized();
        inputManager.update(gamepad1);
        mapDriveControls();
        robot.update();
        publishTelemetry();
    }

    /**
     * Stops Team A robot lifecycle and hardware outputs.
     */
    @Override
    public void stop() {
        if (initialized) {
            robot.stop();
        }
    }

    private void mapDriveControls() {
        double forward = -inputManager.getLeftStickY();
        double strafe = inputManager.getLeftStickX();
        double rotate = inputManager.getRightStickX();

        robot.drive(forward, strafe, rotate);

        if (inputManager.wasYJustPressed()) {
            robot.enableHeadingHold();
        }

        if (inputManager.wasXJustPressed()) {
            robot.enableManualDrive();
        }
    }

    private void publishTelemetry() {
        telemetry.addData("Drive State", robot.getDriveStateName());
        telemetry.addData("Forward", "%.2f", robot.getRequestedForward());
        telemetry.addData("Strafe", "%.2f", robot.getRequestedStrafe());
        telemetry.addData("Rotate", "%.2f", robot.getRequestedRotate());
        telemetry.addData(
                "Wheel Powers",
                "FL %.2f  FR %.2f  RL %.2f  RR %.2f",
                robot.getLastFrontLeftPower(),
                robot.getLastFrontRightPower(),
                robot.getLastRearLeftPower(),
                robot.getLastRearRightPower());
        telemetry.update();
    }

    private void ensureInitialized() {
        if (!initialized) {
            telemetry.addData("Status", "Team A robot is not initialized");
            telemetry.update();
            throw new IllegalStateException("Team A robot must initialize before TeleOp can run.");
        }
    }
}
