package org.firstinspires.ftc.teamcode.common.subsystems.vision;

import org.firstinspires.ftc.teamcode.core.fsm.State;

/**
 * Safe vision state with no active search or tracking.
 */
public class VisionDisabledState implements State {
    private static final String NAME = "Disabled";

    private final VisionSubsystem visionSubsystem;

    VisionDisabledState(VisionSubsystem visionSubsystem) {
        this.visionSubsystem = visionSubsystem;
    }

    @Override
    public void enter() {
        visionSubsystem.clearTargetReport();
    }

    @Override
    public void update() {
        visionSubsystem.clearTargetReport();
    }

    @Override
    public void exit() {
        visionSubsystem.clearTargetReport();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
