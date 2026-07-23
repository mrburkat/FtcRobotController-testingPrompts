package org.firstinspires.ftc.teamcode.common.subsystems.intake;

import org.firstinspires.ftc.teamcode.core.fsm.State;

/**
 * Intake state for keeping possession after intaking.
 *
 * <p>The baseline holds by stopping the motor. Future intake mechanisms may
 * replace this with low holding power if their game piece requires it.</p>
 */
public class HoldingState implements State {
    private static final String NAME = "Holding";

    private final IntakeSubsystem intakeSubsystem;

    HoldingState(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    @Override
    public void enter() {
        intakeSubsystem.holdGamePiece();
    }

    @Override
    public void update() {
        intakeSubsystem.holdGamePiece();
    }

    @Override
    public void exit() {
        intakeSubsystem.stopIntakeOutput();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
