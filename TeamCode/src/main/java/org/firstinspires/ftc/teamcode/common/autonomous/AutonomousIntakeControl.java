package org.firstinspires.ftc.teamcode.common.autonomous;

/**
 * Narrow intake API needed by reusable autonomous intake steps.
 */
public interface AutonomousIntakeControl {
    /**
     * Requests intake mode.
     */
    void startIntake();

    /**
     * Requests a safe stop for intake output.
     */
    void stopIntake();
}
