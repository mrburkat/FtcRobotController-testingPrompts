package org.firstinspires.ftc.teamcode.common.subsystems.drive;

import org.firstinspires.ftc.teamcode.core.fsm.State;

/**
 * Placeholder heading-hold drive state.
 *
 * <p>No heading sensor correction exists yet. This state allows translation and
 * suppresses rotation so later prompts have a safe, honest place to add heading
 * feedback.</p>
 */
public class HeadingHoldState implements State {
    private static final String NAME = "HeadingHold";

    private final DriveSubsystem driveSubsystem;

    HeadingHoldState(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
    }

    @Override
    public void enter() {
        driveSubsystem.applyHeadingHoldPlaceholder();
    }

    @Override
    public void update() {
        driveSubsystem.applyHeadingHoldPlaceholder();
    }

    @Override
    public void exit() {
        driveSubsystem.stopDrive();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
