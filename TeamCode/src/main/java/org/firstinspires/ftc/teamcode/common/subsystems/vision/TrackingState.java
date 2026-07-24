package org.firstinspires.ftc.teamcode.common.subsystems.vision;

import org.firstinspires.ftc.teamcode.core.fsm.State;

/**
 * Vision state representing continued target detection.
 */
public class TrackingState implements State {
    private static final String NAME = "Tracking";

    private final VisionSubsystem visionSubsystem;

    TrackingState(VisionSubsystem visionSubsystem) {
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
        // No hardware shutdown here; LostTarget or Disabled will handle safety.
    }

    @Override
    public String getName() {
        return NAME;
    }
}
