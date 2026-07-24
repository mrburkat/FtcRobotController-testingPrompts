package org.firstinspires.ftc.teamcode.common.autonomous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Runs an ordered list of autonomous steps one at a time.
 *
 * <p>Behavior notes for beginning users:</p>
 *
 * <ul>
 *   <li>An empty sequence is complete as soon as it starts.</li>
 *   <li>Calling {@link #start()} again while running or complete is safe and
 *       does not restart the sequence.</li>
 *   <li>Calling {@link #stop()} before completion stops the active step and
 *       marks the sequence complete.</li>
 *   <li>Calling {@link #update()} after completion is a safe no-op.</li>
 * </ul>
 */
public class AutoSequence {
    private static final String NO_ACTIVE_STEP_NAME = "None";
    private static final String COMPLETE_STEP_NAME = "Complete";

    private final List<AutoStep> steps = new ArrayList<>();

    private int currentStepIndex;
    private boolean started;
    private boolean complete;

    /**
     * Creates an empty sequence.
     */
    public AutoSequence() {
    }

    /**
     * Creates a sequence from the supplied steps in order.
     */
    public AutoSequence(AutoStep... steps) {
        if (steps == null) {
            throw new IllegalArgumentException("Steps cannot be null.");
        }

        for (AutoStep step : Arrays.asList(steps)) {
            addStep(step);
        }
    }

    /**
     * Adds a step before the sequence has started.
     *
     * @param step autonomous step to append
     */
    public void addStep(AutoStep step) {
        if (step == null) {
            throw new IllegalArgumentException("AutoStep cannot be null.");
        }
        if (started) {
            throw new IllegalStateException("Cannot add steps after an AutoSequence has started.");
        }

        steps.add(step);
    }

    /**
     * Starts the first step, or completes immediately when no steps exist.
     */
    public void start() {
        if (started) {
            return;
        }

        started = true;
        currentStepIndex = 0;

        if (steps.isEmpty()) {
            complete = true;
            return;
        }

        getCurrentStep().start();
    }

    /**
     * Updates only the active step and advances at most one step.
     */
    public void update() {
        start();

        if (complete) {
            return;
        }

        AutoStep currentStep = getCurrentStep();
        currentStep.update();

        if (!currentStep.isFinished()) {
            return;
        }

        currentStep.stop();
        currentStepIndex++;

        if (currentStepIndex >= steps.size()) {
            complete = true;
            return;
        }

        getCurrentStep().start();
    }

    /**
     * Stops the active step, if any, and marks this sequence complete.
     */
    public void stop() {
        if (!started) {
            started = true;
            complete = true;
            return;
        }
        if (complete) {
            return;
        }

        getCurrentStep().stop();
        complete = true;
    }

    /**
     * Reports whether every step is complete or the sequence was stopped.
     */
    public boolean isFinished() {
        return complete;
    }

    /**
     * Returns the active step name for telemetry.
     */
    public String getCurrentStepName() {
        if (!started) {
            return NO_ACTIVE_STEP_NAME;
        }
        if (complete) {
            return COMPLETE_STEP_NAME;
        }

        return getCurrentStep().getName();
    }

    private AutoStep getCurrentStep() {
        return steps.get(currentStepIndex);
    }
}
