package org.firstinspires.ftc.teamcode.common.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Holds the optional vision hardware lifecycle placeholder.
 *
 * <p>No real vision device behavior is implemented in this baseline wrapper.
 * The wrapper reports unavailable until a later prompt adds a vision device.</p>
 */
public class VisionHardware {
    private boolean available;
    private boolean initialized;

    /**
     * Initializes the vision lifecycle placeholder.
     *
     * @param hardwareMap FTC hardware map supplied by the robot composition layer
     */
    public void initialize(HardwareMap hardwareMap) {
        if (hardwareMap == null) {
            throw new IllegalArgumentException("HardwareMap cannot be null.");
        }

        initialized = true;
        available = false;
    }

    /**
     * Updates vision lifecycle state.
     */
    public void update() {
        if (!initialized) {
            throw new IllegalStateException("VisionHardware must be initialized before use.");
        }
    }

    /**
     * Stops vision lifecycle state.
     */
    public void stop() {
        initialized = false;
        available = false;
    }

    /**
     * Reports whether usable vision hardware is currently available.
     *
     * @return false until a later implementation adds a real vision device
     */
    public boolean isAvailable() {
        return available;
    }
}
