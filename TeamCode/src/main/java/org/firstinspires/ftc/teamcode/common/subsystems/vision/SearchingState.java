package org.firstinspires.ftc.teamcode.common.subsystems.vision;

import org.firstinspires.ftc.teamcode.core.fsm.State;

/**
 * Vision state that updates hardware while waiting for an external detection.
 */
public class SearchingState implements State {
    private static final String NAME = "Searching";

    private final VisionSubsystem visionSubsystem;

    SearchingState(VisionSubsystem visionSubsystem) {
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
        // No hardware shutdown here; another active vision state may follow.
    }

    @Override
    public String getName() {
        return NAME;
    }
}
