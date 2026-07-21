package org.firstinspires.ftc.teamcode.core.fsm;

import java.util.function.BooleanSupplier;

/**
 * Describes one possible move from a source state to a target state.
 *
 * <p>A transition is valid only when the finite-state machine is currently in
 * its source state. When valid, the transition fires only if its condition is
 * true.</p>
 */
public class Transition {
    private final State sourceState;
    private final State targetState;
    private final BooleanSupplier condition;

    /**
     * Creates a transition between two states.
     *
     * @param sourceState the state this transition can leave
     * @param targetState the state this transition can enter
     * @param condition returns true when this transition should fire
     * @throws IllegalArgumentException if any argument is {@code null}
     */
    public Transition(State sourceState, State targetState, BooleanSupplier condition) {
        if (sourceState == null) {
            throw new IllegalArgumentException("Source state cannot be null.");
        }
        if (targetState == null) {
            throw new IllegalArgumentException("Target state cannot be null.");
        }
        if (condition == null) {
            throw new IllegalArgumentException("Transition condition cannot be null.");
        }

        this.sourceState = sourceState;
        this.targetState = targetState;
        this.condition = condition;
    }

    /**
     * Returns the state this transition can leave.
     *
     * @return the source state
     */
    public State getSourceState() {
        return sourceState;
    }

    /**
     * Returns the state this transition can enter.
     *
     * @return the target state
     */
    public State getTargetState() {
        return targetState;
    }

    /**
     * Reports whether this transition can be considered for the active state.
     *
     * @param currentState the finite-state machine's current state
     * @return true when {@code currentState} is this transition's source state
     */
    public boolean appliesTo(State currentState) {
        return sourceState == currentState;
    }

    /**
     * Reports whether this transition's condition is currently true.
     *
     * @return true when this transition should fire
     */
    public boolean isConditionSatisfied() {
        return condition.getAsBoolean();
    }
}
