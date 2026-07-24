package org.firstinspires.ftc.teamcode.common.autonomous;

/**
 * Narrow drive API needed by reusable autonomous drive steps.
 *
 * <p>This avoids making shared autonomous steps depend directly on a
 * team-specific robot class while still limiting the methods they can call.</p>
 */
public interface AutonomousDriveControl {
    /**
     * Requests manual drive mode for autonomous drive commands.
     */
    void enableManualDrive();

    /**
     * Stores a drive request for the robot update loop.
     */
    void drive(double forward, double strafe, double rotate);

    /**
     * Requests a safe stop for drivetrain output.
     */
    void stopDrive();
}
