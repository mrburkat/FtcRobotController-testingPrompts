package org.firstinspires.ftc.teamcode.common.subsystems.intake;

import org.firstinspires.ftc.teamcode.core.fsm.State;

/**
 * Intake state that runs the optional intake motor forward.
 */
public class IntakingState implements State {
    private static final String NAME = "Intaking";

    private final IntakeSubsystem intakeSubsystem;

    IntakingState(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    @Override
    public void enter() {
        intakeSubsystem.runIntakeForward();
    }

    @Override
    public void update() {
        intakeSubsystem.runIntakeForward();
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
