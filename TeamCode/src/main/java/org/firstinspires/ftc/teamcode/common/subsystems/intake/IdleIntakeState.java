package org.firstinspires.ftc.teamcode.common.subsystems.intake;

import org.firstinspires.ftc.teamcode.core.fsm.State;

/**
 * Safe intake state that keeps the optional intake motor stopped.
 */
public class IdleIntakeState implements State {
    private static final String NAME = "Idle";

    private final IntakeSubsystem intakeSubsystem;

    IdleIntakeState(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    @Override
    public void enter() {
        intakeSubsystem.stopIntakeOutput();
    }

    @Override
    public void update() {
        intakeSubsystem.stopIntakeOutput();
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
