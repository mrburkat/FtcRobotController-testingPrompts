package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.core.input.InputManager;
import org.firstinspires.ftc.teamcode.robots.teamB.TeamBRobot;

/**
 * Driver-controlled skeleton OpMode for Team B.
 *
 * <p>This OpMode uses the shared baseline drive mapping only because
 * Team B's current robot skeleton still uses the shared required drive hardware
 * names. Team-specific mechanism mappings should be added after Team B
 * documents its final wiring and driver-control choices.</p>
 */
@TeleOp(name = "Team B TeleOp", group = "Team B")
public class TeamBTeleOp extends OpMode {
    private TeamBRobot robot;
    private InputManager driverInput;
    private boolean initialized;

    /**
     * Builds Team B robot composition and initializes configured hardware.
     */
    @Override
    public void init() {
        robot = new TeamBRobot();
        driverInput = new InputManager();

        try {
            robot.initialize(hardwareMap);
            initialized = true;
            telemetry.addData("Status", "Team B robot initialized");
        } catch (RuntimeException exception) {
            telemetry.addData("Initialization Error", exception.getMessage());
            telemetry.addData("Status", "Check Team B baseline drive motor configuration");
            telemetry.update();
            throw exception;
        }

        telemetry.update();
    }

    /**
     * Requests manual drive and captures the first gamepad snapshot.
     */
    @Override
    public void start() {
        ensureInitialized();
        robot.enableManualDrive();
        driverInput.update(gamepad1);

        telemetry.addData("Status", "Team B TeleOp started");
        telemetry.update();
    }

    /**
     * Runs one Team B driver-control loop.
     */
    @Override
    public void loop() {
        ensureInitialized();
        driverInput.update(gamepad1);
        mapCommonDriveControls();
        mapFutureTeamControls();
        robot.update();
        publishTelemetry();
    }

    /**
     * Stops Team B robot lifecycle and hardware outputs.
     */
    @Override
    public void stop() {
        if (initialized) {
            robot.stop();
        }
    }

    private void mapCommonDriveControls() {
        double forward = -driverInput.getLeftStickY();
        double strafe = driverInput.getLeftStickX();
        double rotate = driverInput.getRightStickX();

        robot.drive(forward, strafe, rotate);
    }

    private void mapFutureTeamControls() {
        // TODO: Add Team B-specific intake, vision, and mechanism controls only
        // after the team documents final wiring and driver-control choices.
    }

    private void publishTelemetry() {
        telemetry.addData("Drive State", robot.getDriveStateName());
        telemetry.addData("Intake State", robot.getIntakeStateName());
        telemetry.addData("Intake Available", robot.isIntakeAvailable());
        telemetry.addData("Vision State", robot.getVisionStateName());
        telemetry.addData("Vision Available", robot.isVisionAvailable());
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
            telemetry.addData("Status", "Team B robot is not initialized");
            telemetry.update();
            throw new IllegalStateException("Team B robot must initialize before TeleOp can run.");
        }
    }
}
