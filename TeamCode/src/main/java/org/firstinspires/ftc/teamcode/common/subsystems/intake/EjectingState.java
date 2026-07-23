package org.firstinspires.ftc.teamcode.common.subsystems.intake;

import org.firstinspires.ftc.teamcode.core.fsm.State;

/**
 * Intake state that runs the optional intake motor in reverse.
 */
public class EjectingState implements State {
    private static final String NAME = "Ejecting";

    private final IntakeSubsystem intakeSubsystem;

    EjectingState(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    @Override
    public void enter() {
        intakeSubsystem.runIntakeReverse();
    }

    @Override
    public void update() {
        intakeSubsystem.runIntakeReverse();
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
