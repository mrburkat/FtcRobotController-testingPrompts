package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.common.autonomous.AutoSequence;
import org.firstinspires.ftc.teamcode.common.autonomous.TimedDriveStep;
import org.firstinspires.ftc.teamcode.common.autonomous.TimedIntakeStep;
import org.firstinspires.ftc.teamcode.common.autonomous.WaitStep;
import org.firstinspires.ftc.teamcode.robots.teamA.TeamARobot;

/**
 * Demonstration autonomous OpMode for Team A.
 *
 * <p>This OpMode uses the iterative FTC lifecycle so the autonomous sequence
 * and robot subsystems update once per loop without blocking.</p>
 */
@Autonomous(name = "Team A Autonomous", group = "Team A")
public class TeamAAutoOpMode extends OpMode {
    private static final double DRIVE_FORWARD_POWER = 0.25;
    private static final double DRIVE_STRAFE_POWER = 0.0;
    private static final double DRIVE_ROTATE_POWER = 0.0;
    private static final double DRIVE_DURATION_SECONDS = 1.0;
    private static final double STOP_DRIVE_DURATION_SECONDS = 0.0;
    private static final double INTAKE_DURATION_SECONDS = 1.0;
    private static final double STOP_INTAKE_DURATION_SECONDS = 0.0;
    private static final double WAIT_DURATION_SECONDS = 0.5;

    private TeamARobot robot;
    private AutoSequence sequence;
    private boolean initialized;

    /**
     * Builds Team A robot composition and prepares the demonstration sequence.
     */
    @Override
    public void init() {
        robot = new TeamARobot();

        try {
            robot.initialize(hardwareMap);
            sequence = buildDemoSequence();
            initialized = true;
            telemetry.addData("Status", "Team A autonomous initialized");
        } catch (RuntimeException exception) {
            telemetry.addData("Initialization Error", exception.getMessage());
            telemetry.addData("Status", "Check Team A drive motor configuration");
            telemetry.update();
            throw exception;
        }

        telemetry.update();
    }

    /**
     * Starts the autonomous sequence from safe subsystem modes.
     */
    @Override
    public void start() {
        ensureInitialized();
        robot.stopDrive();
        robot.stopIntake();
        sequence.start();

        telemetry.addData("Status", "Team A autonomous started");
        telemetry.update();
    }

    /**
     * Updates the sequence and robot once per FTC loop without blocking.
     */
    @Override
    public void loop() {
        ensureInitialized();

        if (!sequence.isFinished()) {
            sequence.update();
        } else {
            robot.stopDrive();
            robot.stopIntake();
        }

        robot.update();
        publishTelemetry();
    }

    /**
     * Stops the active step and all robot outputs.
     */
    @Override
    public void stop() {
        if (initialized) {
            sequence.stop();
            robot.stop();
        }
    }

    private AutoSequence buildDemoSequence() {
        return new AutoSequence(
                new TimedDriveStep(
                        robot,
                        DRIVE_FORWARD_POWER,
                        DRIVE_STRAFE_POWER,
                        DRIVE_ROTATE_POWER,
                        DRIVE_DURATION_SECONDS),
                new TimedDriveStep(
                        robot,
                        0.0,
                        0.0,
                        0.0,
                        STOP_DRIVE_DURATION_SECONDS),
                new TimedIntakeStep(robot, INTAKE_DURATION_SECONDS),
                new TimedIntakeStep(robot, STOP_INTAKE_DURATION_SECONDS),
                new WaitStep(WAIT_DURATION_SECONDS));
    }

    private void publishTelemetry() {
        telemetry.addData("Autonomous Step", sequence.getCurrentStepName());
        telemetry.addData("Sequence Complete", sequence.isFinished());
        telemetry.addData("Drive State", robot.getDriveStateName());
        telemetry.addData("Intake State", robot.getIntakeStateName());
        telemetry.addData("Vision State", robot.getVisionStateName());
        telemetry.update();
    }

    private void ensureInitialized() {
        if (!initialized) {
            telemetry.addData("Status", "Team A autonomous is not initialized");
            telemetry.update();
            throw new IllegalStateException("Team A robot must initialize before autonomous can run.");
        }
    }
}
