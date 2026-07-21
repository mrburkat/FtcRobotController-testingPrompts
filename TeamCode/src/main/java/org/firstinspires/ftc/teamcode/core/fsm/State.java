package org.firstinspires.ftc.teamcode.core.fsm;

/**
 * Defines one state in a finite-state machine.
 *
 * <p>A state owns behavior for one named mode, such as "Idle", "Driving", or
 * "Holding". States are hardware-independent in this core package. Higher
 * layers decide what a state should control.</p>
 */
public interface State {
    /**
     * Runs once when this state becomes active.
     */
    void enter();

    /**
     * Runs once while this state is active during the current update cycle.
     */
    void update();

    /**
     * Runs once when this state stops being active.
     */
    void exit();

    /**
     * Returns a short, human-readable name for telemetry and diagnostics.
     *
     * @return the state name
     */
    String getName();
}
