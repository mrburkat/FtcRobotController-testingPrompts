package org.firstinspires.ftc.teamcode.core.robot;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for team robot objects that own subsystem lifecycle.
 *
 * <p>An FTC OpMode should talk to a concrete robot subclass through public robot
 * methods. The robot owns its subsystems and calls each subsystem in a
 * deterministic registration order.</p>
 *
 * <p>Subclasses should register subsystems from their constructor by calling
 * {@link #registerSubsystem(Subsystem)}. They may also override the protected
 * hook methods when they need robot-level setup or shutdown behavior that does
 * not belong to a single subsystem.</p>
 */
public abstract class Robot {
    private final List<Subsystem> subsystems = new ArrayList<>();
    private boolean initialized;
    private boolean stopped;

    /**
     * Registers a subsystem with this robot.
     *
     * <p>Subsystems are initialized, updated, and stopped in the same order they
     * are registered. Registering the same subsystem instance twice is treated
     * as a programming error and throws an {@link IllegalArgumentException} with
     * the subsystem name.</p>
     *
     * @param subsystem the subsystem owned by this robot
     * @throws IllegalArgumentException if {@code subsystem} is {@code null} or
     *     was already registered with this robot
     * @throws IllegalStateException if registration is attempted after robot
     *     initialization has started
     */
    protected final void registerSubsystem(Subsystem subsystem) {
        if (subsystem == null) {
            throw new IllegalArgumentException("Subsystem cannot be null.");
        }

        if (initialized) {
            throw new IllegalStateException(
                    "Cannot register subsystem after robot initialization has started: "
                            + subsystem.getName());
        }

        if (subsystems.contains(subsystem)) {
            throw new IllegalArgumentException(
                    "Subsystem is already registered: " + subsystem.getName());
        }

        subsystems.add(subsystem);
    }

    /**
     * Initializes this robot and each registered subsystem once.
     *
     * <p>Calling this method more than once is safe. Later calls are ignored so
     * subsystem initialization still happens exactly once.</p>
     */
    public final void initialize() {
        if (initialized) {
            return;
        }

        initialized = true;
        stopped = false;
        onInitialize();

        for (Subsystem subsystem : subsystems) {
            subsystem.initialize();
        }
    }

    /**
     * Updates each registered subsystem once in registration order.
     *
     * <p>The OpMode should call this once per FTC loop after translating driver
     * input or autonomous sequence decisions into public robot API calls.</p>
     *
     * @throws IllegalStateException if called before {@link #initialize()}
     */
    public final void update() {
        if (!initialized) {
            throw new IllegalStateException("Robot must be initialized before update.");
        }

        for (Subsystem subsystem : subsystems) {
            subsystem.update();
        }
    }

    /**
     * Stops each registered subsystem and then runs robot-level shutdown logic.
     *
     * <p>Calling this method more than once is safe. Later calls are ignored so
     * stop behavior only runs once.</p>
     */
    public final void stop() {
        if (stopped) {
            return;
        }

        for (Subsystem subsystem : subsystems) {
            subsystem.stop();
        }

        onStop();
        stopped = true;
    }

    /**
     * Hook for robot-level initialization in subclasses.
     *
     * <p>The default implementation does nothing. Subclasses can override this
     * method for setup that belongs to the whole robot rather than one
     * subsystem.</p>
     */
    protected void onInitialize() {
        // Default hook for subclasses.
    }

    /**
     * Hook for robot-level shutdown in subclasses.
     *
     * <p>The default implementation does nothing. Subclasses can override this
     * method to stop robot-level resources after all subsystems have been
     * stopped.</p>
     */
    protected void onStop() {
        // Default hook for subclasses.
    }
}
