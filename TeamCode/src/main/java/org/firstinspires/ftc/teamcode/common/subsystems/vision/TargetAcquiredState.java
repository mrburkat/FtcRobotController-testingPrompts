package org.firstinspires.ftc.teamcode.common.subsystems.vision;

import org.firstinspires.ftc.teamcode.core.fsm.State;

/**
 * Vision state for the first positive target observation.
 *
 * <p>Policy: this state lasts for one FSM update cycle. On the next update,
 * continued detection moves to Tracking; loss moves to LostTarget.</p>
 */
public class TargetAcquiredState implements State {
    private static final String NAME = "TargetAcquired";

    private final VisionSubsystem visionSubsystem;

    TargetAcquiredState(VisionSubsystem visionSubsystem) {
        this.visionSubsystem = visionSubsystem;
    }

    @Override
    public void enter() {
        visionSubsystem.updateVisionHardware();
    }

    @Override
    public void update() {
        visionSubsystem.updateVisionHardware();
    }

    @Override
    public void exit() {
        // Detection reports are owned by VisionSubsystem.
    }

    @Override
    public String getName() {
        return NAME;
    }
}
