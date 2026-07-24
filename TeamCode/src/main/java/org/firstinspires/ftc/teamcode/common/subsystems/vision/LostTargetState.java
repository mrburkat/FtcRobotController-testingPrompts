package org.firstinspires.ftc.teamcode.common.subsystems.vision;

import org.firstinspires.ftc.teamcode.core.fsm.State;

/**
 * Vision state representing loss after a previous target detection.
 *
 * <p>Policy: if the next externally reported observation is still missing,
 * the FSM returns to Searching. If the target is reported again, the FSM returns
 * to TargetAcquired.</p>
 */
public class LostTargetState implements State {
    private static final String NAME = "LostTarget";

    private final VisionSubsystem visionSubsystem;

    LostTargetState(VisionSubsystem visionSubsystem) {
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
        // The next state decides whether to search, track, or disable.
    }

    @Override
    public String getName() {
        return NAME;
    }
}
