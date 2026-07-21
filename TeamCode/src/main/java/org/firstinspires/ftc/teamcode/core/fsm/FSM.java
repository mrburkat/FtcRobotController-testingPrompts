package org.firstinspires.ftc.teamcode.core.fsm;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs a small deterministic finite-state machine.
 *
 * <p>Transitions are checked in the order they are registered. During one call
 * to {@link #update()}, at most one transition can fire. If a transition fires,
 * the old state exits, the new state enters, and this FSM then updates the new
 * active state during that same update cycle.</p>
 *
 * <p>Hardware-independent example:</p>
 *
 * <pre>{@code
 * State waiting = new NamedState("Waiting");
 * State ready = new NamedState("Ready");
 * FSM fsm = new FSM(waiting);
 * fsm.addTransition(new Transition(waiting, ready, () -> buttonPressed));
 * fsm.update();
 * }</pre>
 */
public class FSM {
    private final List<Transition> transitions = new ArrayList<>();
    private State currentState;
    private boolean active;

    /**
     * Creates an FSM with its required initial state.
     *
     * <p>The initial state's {@link State#enter()} method is called exactly once
     * when this FSM is first activated by {@link #start()} or {@link #update()}.
     * Calling {@link #update()} before an initial state exists is rejected by
     * using this constructor's null check.</p>
     *
     * @param initialState the first active state
     * @throws IllegalArgumentException if {@code initialState} is {@code null}
     */
    public FSM(State initialState) {
        if (initialState == null) {
            throw new IllegalArgumentException("Initial state cannot be null.");
        }

        currentState = initialState;
    }

    /**
     * Adds a transition to the end of the deterministic transition list.
     *
     * @param transition the transition to add
     * @throws IllegalArgumentException if {@code transition} is {@code null}
     */
    public void addTransition(Transition transition) {
        if (transition == null) {
            throw new IllegalArgumentException("Transition cannot be null.");
        }

        transitions.add(transition);
    }

    /**
     * Activates this FSM without running the active state's update behavior.
     *
     * <p>This is useful when a subsystem wants all starting states to enter
     * during initialization. Calling this method more than once is safe.</p>
     */
    public void start() {
        ensureInitialStateExists();

        if (active) {
            return;
        }

        active = true;
        currentState.enter();
    }

    /**
     * Updates the FSM once.
     *
     * <p>If this FSM has not been started yet, it starts first so the initial
     * state's {@code enter()} method still runs exactly once. Transitions valid
     * for the current state are then evaluated in registration order. At most one
     * transition fires, and the active state after that transition is updated
     * once.</p>
     *
     * @throws IllegalStateException if no initial state exists
     */
    public void update() {
        start();

        for (Transition transition : transitions) {
            if (transition.appliesTo(currentState) && transition.isConditionSatisfied()) {
                changeState(transition.getTargetState());
                break;
            }
        }

        currentState.update();
    }

    /**
     * Returns the current active state.
     *
     * @return the current state
     */
    public State getCurrentState() {
        ensureInitialStateExists();
        return currentState;
    }

    /**
     * Returns the current state's human-readable name.
     *
     * @return the current state name
     */
    public String getCurrentStateName() {
        return getCurrentState().getName();
    }

    private void changeState(State newState) {
        currentState.exit();
        currentState = newState;
        currentState.enter();
    }

    private void ensureInitialStateExists() {
        if (currentState == null) {
            throw new IllegalStateException("FSM requires an initial state before update.");
        }
    }
}
