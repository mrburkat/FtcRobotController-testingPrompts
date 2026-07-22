package org.firstinspires.ftc.teamcode.common.subsystems.drive;

import org.firstinspires.ftc.teamcode.core.fsm.State;

/**
 * Drive state that applies stored manual mecanum drive requests.
 */
public class ManualDriveState implements State {
    private static final String NAME = "Manual";

    private final DriveSubsystem driveSubsystem;

    ManualDriveState(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
    }

    @Override
    public void enter() {
        driveSubsystem.applyManualDrive();
    }

    @Override
    public void update() {
        driveSubsystem.applyManualDrive();
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
