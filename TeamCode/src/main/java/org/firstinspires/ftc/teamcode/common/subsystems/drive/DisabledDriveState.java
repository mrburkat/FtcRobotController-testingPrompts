package org.firstinspires.ftc.teamcode.common.subsystems.drive;

import org.firstinspires.ftc.teamcode.core.fsm.State;

/**
 * Safe drive state that keeps all drive motors stopped.
 */
public class DisabledDriveState implements State {
    private static final String NAME = "Disabled";

    private final DriveSubsystem driveSubsystem;

    DisabledDriveState(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
    }

    @Override
    public void enter() {
        driveSubsystem.stopDrive();
    }

    @Override
    public void update() {
        driveSubsystem.stopDrive();
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
