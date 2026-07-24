package org.firstinspires.ftc.teamcode.common.autonomous;

/**
 * Defines one non-blocking autonomous action.
 *
 * <p>An OpMode should call {@link AutoSequence#update()} once per loop and then
 * continue calling {@code robot.update()}. Steps must return quickly and must
 * not sleep or wait in a loop.</p>
 */
public interface AutoStep {
    /**
     * Runs once when this step becomes active.
     */
    void start();

    /**
     * Runs once per OpMode loop while this step is active.
     */
    void update();

    /**
     * Reports whether this step is ready for the sequence to advance.
     */
    boolean isFinished();

    /**
     * Stops this step and leaves any mechanism it controls in a safe state.
     *
     * <p>The sequence calls this when a step finishes and when the whole
     * sequence is stopped early.</p>
     */
    void stop();

    /**
     * Returns a short name for telemetry.
     */
    String getName();
}
