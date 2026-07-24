package org.firstinspires.ftc.teamcode.common.subsystems.vision;

import org.firstinspires.ftc.teamcode.common.hardware.VisionHardware;
import org.firstinspires.ftc.teamcode.core.fsm.FSM;
import org.firstinspires.ftc.teamcode.core.fsm.Transition;
import org.firstinspires.ftc.teamcode.core.robot.Subsystem;

/**
 * Shared vision subsystem skeleton controlled by a small finite-state machine.
 *
 * <p>This class does not create cameras, processors, or simulated detections.
 * Future vision processing can call {@link #reportTargetDetected(boolean)} to
 * report what it observed while higher layers use {@link #enableVision()} and
 * {@link #disableVision()} to request whether vision should run.</p>
 *
 * <p>When the optional vision hardware is unavailable, enabling vision remains
 * safe: the FSM stays disabled and reported detections are ignored.</p>
 */
public class VisionSubsystem implements Subsystem {
    private static final String NAME = "VisionSubsystem";

    private final VisionHardware visionHardware;
    private final FSM fsm;

    private boolean visionEnabled;
    private boolean targetDetected;
    private boolean initialized;

    /**
     * Creates a vision subsystem using the shared vision hardware wrapper.
     *
     * @param visionHardware optional vision hardware wrapper owned by the robot
     */
    public VisionSubsystem(VisionHardware visionHardware) {
        if (visionHardware == null) {
            throw new IllegalArgumentException("VisionHardware cannot be null.");
        }

        this.visionHardware = visionHardware;

        VisionDisabledState disabledState = new VisionDisabledState(this);
        SearchingState searchingState = new SearchingState(this);
        TargetAcquiredState targetAcquiredState = new TargetAcquiredState(this);
        TrackingState trackingState = new TrackingState(this);
        LostTargetState lostTargetState = new LostTargetState(this);

        fsm = new FSM(disabledState);
        fsm.addTransition(new Transition(
                disabledState,
                searchingState,
                this::shouldSearch));
        fsm.addTransition(new Transition(
                searchingState,
                disabledState,
                this::shouldBeDisabled));
        fsm.addTransition(new Transition(
                searchingState,
                targetAcquiredState,
                () -> shouldSearch() && targetDetected));
        fsm.addTransition(new Transition(
                targetAcquiredState,
                disabledState,
                this::shouldBeDisabled));
        fsm.addTransition(new Transition(
                targetAcquiredState,
                trackingState,
                () -> shouldSearch() && targetDetected));
        fsm.addTransition(new Transition(
                targetAcquiredState,
                lostTargetState,
                () -> shouldSearch() && !targetDetected));
        fsm.addTransition(new Transition(
                trackingState,
                disabledState,
                this::shouldBeDisabled));
        fsm.addTransition(new Transition(
                trackingState,
                lostTargetState,
                () -> shouldSearch() && !targetDetected));
        fsm.addTransition(new Transition(
                lostTargetState,
                disabledState,
                this::shouldBeDisabled));
        fsm.addTransition(new Transition(
                lostTargetState,
                targetAcquiredState,
                () -> shouldSearch() && targetDetected));
        fsm.addTransition(new Transition(
                lostTargetState,
                searchingState,
                () -> shouldSearch() && !targetDetected));
    }

    /**
     * Starts the vision FSM in its safe disabled state.
     */
    @Override
    public void initialize() {
        if (initialized) {
            return;
        }

        visionEnabled = false;
        targetDetected = false;
        fsm.start();
        initialized = true;
    }

    /**
     * Updates the active vision state once.
     */
    @Override
    public void update() {
        fsm.update();
    }

    /**
     * Disables vision and stops the vision hardware lifecycle safely.
     */
    @Override
    public void stop() {
        disableVision();
        visionHardware.stop();
    }

    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Requests vision processing when optional vision hardware is available.
     *
     * <p>If hardware is unavailable, this request remains safe and the FSM stays
     * disabled instead of inventing detections.</p>
     */
    public void enableVision() {
        visionEnabled = true;
    }

    /**
     * Requests safe disabled vision mode and clears the last reported target.
     */
    public void disableVision() {
        visionEnabled = false;
        targetDetected = false;
    }

    /**
     * Reports whether external vision processing currently sees a target.
     *
     * <p>This is a temporary testing and integration hook. It records
     * observations only while vision is enabled and hardware is available; it
     * does not simulate target results.</p>
     *
     * @param detected true when processing observed a target this loop
     */
    public void reportTargetDetected(boolean detected) {
        targetDetected = shouldSearch() && detected;
    }

    /**
     * Returns the active vision FSM state name for telemetry.
     */
    public String getCurrentStateName() {
        return fsm.getCurrentStateName();
    }

    /**
     * Reports whether usable vision hardware is available.
     */
    public boolean isAvailable() {
        return visionHardware.isAvailable();
    }

    boolean shouldBeDisabled() {
        return !visionEnabled || !isAvailable();
    }

    void clearTargetReport() {
        targetDetected = false;
    }

    void updateVisionHardware() {
        if (isAvailable()) {
            visionHardware.update();
        }
    }

    private boolean shouldSearch() {
        return visionEnabled && isAvailable();
    }
}
