package org.firstinspires.ftc.teamcode.core.robot;

/**
 * Defines the basic lifecycle for one robot subsystem.
 *
 * <p>A subsystem owns one area of robot behavior, such as a drive base or
 * intake. Robot classes call these methods during the normal FTC lifecycle so
 * each subsystem can prepare itself, run once per loop, and stop safely.</p>
 *
 * <p>Subsystems should not read driver controls or look up devices directly.
 * Higher layers decide what the robot should do, and wrapper classes own direct
 * device access.</p>
 */
public interface Subsystem {
    /**
     * Prepares this subsystem before the robot starts running.
     *
     * <p>This method is called once by the owning {@link Robot} during robot
     * initialization.</p>
     */
    void initialize();

    /**
     * Runs this subsystem once during the current FTC loop.
     *
     * <p>This method should return quickly. Long-running or blocking work would
     * prevent other subsystems from updating on time.</p>
     */
    void update();

    /**
     * Stops this subsystem and puts its outputs in a safe state.
     *
     * <p>The owning {@link Robot} calls this when the robot stops.</p>
     */
    void stop();

    /**
     * Returns a short, human-readable name for telemetry and error messages.
     *
     * @return the subsystem name
     */
    String getName();
}
