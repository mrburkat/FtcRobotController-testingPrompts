package org.firstinspires.ftc.teamcode.common.subsystems.intake;

import org.firstinspires.ftc.teamcode.common.hardware.IntakeHardware;
import org.firstinspires.ftc.teamcode.core.fsm.FSM;
import org.firstinspires.ftc.teamcode.core.fsm.Transition;
import org.firstinspires.ftc.teamcode.core.robot.Subsystem;

/**
 * Shared intake subsystem controlled by a small finite-state machine.
 *
 * <p>Higher layers request intake behavior through this subsystem's public
 * methods. They do not choose concrete states directly. The optional intake
 * hardware wrapper keeps commands safe when the motor is not configured, so a
 * missing intake does not affect drivetrain operation.</p>
 */
public class IntakeSubsystem implements Subsystem {
    public static final double DEFAULT_INTAKE_POWER = 1.0;
    public static final double DEFAULT_EJECT_POWER = 1.0;

    private static final String NAME = "IntakeSubsystem";

    private final IntakeHardware intakeHardware;
    private final FSM fsm;
    private final double intakePower;
    private final double ejectPower;

    private IntakeMode requestedMode = IntakeMode.IDLE;
    private boolean initialized;

    /**
     * Creates an intake subsystem using full-power intake and eject requests.
     *
     * @param intakeHardware optional intake hardware wrapper owned by the robot
     */
    public IntakeSubsystem(IntakeHardware intakeHardware) {
        this(intakeHardware, DEFAULT_INTAKE_POWER, DEFAULT_EJECT_POWER);
    }

    /**
     * Creates an intake subsystem with configurable intake and eject powers.
     *
     * @param intakeHardware optional intake hardware wrapper owned by the robot
     * @param intakePower positive motor power used while intaking
     * @param ejectPower positive motor power used while ejecting
     */
    public IntakeSubsystem(IntakeHardware intakeHardware, double intakePower, double ejectPower) {
        if (intakeHardware == null) {
            throw new IllegalArgumentException("IntakeHardware cannot be null.");
        }

        this.intakeHardware = intakeHardware;
        this.intakePower = cleanPower(intakePower, "intakePower");
        this.ejectPower = cleanPower(ejectPower, "ejectPower");

        IdleIntakeState idleState = new IdleIntakeState(this);
        IntakingState intakingState = new IntakingState(this);
        HoldingState holdingState = new HoldingState(this);
        EjectingState ejectingState = new EjectingState(this);

        fsm = new FSM(idleState);
        addTransitionsFrom(idleState, intakingState, holdingState, ejectingState);
        addTransitionsFrom(intakingState, idleState, holdingState, ejectingState);
        addTransitionsFrom(holdingState, idleState, intakingState, ejectingState);
        addTransitionsFrom(ejectingState, idleState, intakingState, holdingState);
    }

    /**
     * Starts the intake FSM in its safe idle state.
     */
    @Override
    public void initialize() {
        if (initialized) {
            return;
        }

        requestedMode = IntakeMode.IDLE;
        fsm.start();
        initialized = true;
    }

    /**
     * Updates the active intake state once.
     */
    @Override
    public void update() {
        fsm.update();
    }

    /**
     * Stops intake output and requests the safe idle mode.
     */
    @Override
    public void stop() {
        requestedMode = IntakeMode.IDLE;
        stopIntakeOutput();
    }

    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Requests intake mode. The FSM transitions on the next update.
     */
    public void startIntake() {
        requestedMode = IntakeMode.INTAKING;
    }

    /**
     * Requests idle mode and stops intake output immediately.
     */
    public void stopIntake() {
        requestedMode = IntakeMode.IDLE;
        stopIntakeOutput();
    }

    /**
     * Requests holding mode.
     *
     * <p>The baseline holding behavior stops the motor. Future mechanisms may
     * use a low holding power here if their game pieces need it.</p>
     */
    public void hold() {
        requestedMode = IntakeMode.HOLDING;
    }

    /**
     * Requests ejecting mode. The FSM transitions on the next update.
     */
    public void eject() {
        requestedMode = IntakeMode.EJECTING;
    }

    /**
     * Returns the active intake FSM state name for telemetry.
     */
    public String getCurrentStateName() {
        return fsm.getCurrentStateName();
    }

    /**
     * Reports whether the optional intake motor is available.
     */
    public boolean isAvailable() {
        return intakeHardware.isAvailable();
    }

    void stopIntakeOutput() {
        intakeHardware.stop();
    }

    void runIntakeForward() {
        intakeHardware.setPower(intakePower);
    }

    void holdGamePiece() {
        intakeHardware.stop();
    }

    void runIntakeReverse() {
        intakeHardware.setPower(-ejectPower);
    }

    private void addTransitionsFrom(
            IdleIntakeState sourceState,
            IntakingState intakingState,
            HoldingState holdingState,
            EjectingState ejectingState) {
        fsm.addTransition(new Transition(
                sourceState,
                intakingState,
                () -> requestedMode == IntakeMode.INTAKING));
        fsm.addTransition(new Transition(
                sourceState,
                holdingState,
                () -> requestedMode == IntakeMode.HOLDING));
        fsm.addTransition(new Transition(
                sourceState,
                ejectingState,
                () -> requestedMode == IntakeMode.EJECTING));
    }

    private void addTransitionsFrom(
            IntakingState sourceState,
            IdleIntakeState idleState,
            HoldingState holdingState,
            EjectingState ejectingState) {
        fsm.addTransition(new Transition(
                sourceState,
                idleState,
                () -> requestedMode == IntakeMode.IDLE));
        fsm.addTransition(new Transition(
                sourceState,
                holdingState,
                () -> requestedMode == IntakeMode.HOLDING));
        fsm.addTransition(new Transition(
                sourceState,
                ejectingState,
                () -> requestedMode == IntakeMode.EJECTING));
    }

    private void addTransitionsFrom(
            HoldingState sourceState,
            IdleIntakeState idleState,
            IntakingState intakingState,
            EjectingState ejectingState) {
        fsm.addTransition(new Transition(
                sourceState,
                idleState,
                () -> requestedMode == IntakeMode.IDLE));
        fsm.addTransition(new Transition(
                sourceState,
                intakingState,
                () -> requestedMode == IntakeMode.INTAKING));
        fsm.addTransition(new Transition(
                sourceState,
                ejectingState,
                () -> requestedMode == IntakeMode.EJECTING));
    }

    private void addTransitionsFrom(
            EjectingState sourceState,
            IdleIntakeState idleState,
            IntakingState intakingState,
            HoldingState holdingState) {
        fsm.addTransition(new Transition(
                sourceState,
                idleState,
                () -> requestedMode == IntakeMode.IDLE));
        fsm.addTransition(new Transition(
                sourceState,
                intakingState,
                () -> requestedMode == IntakeMode.INTAKING));
        fsm.addTransition(new Transition(
                sourceState,
                holdingState,
                () -> requestedMode == IntakeMode.HOLDING));
    }

    private double cleanPower(double power, String powerName) {
        if (Double.isNaN(power)) {
            throw new IllegalArgumentException("Intake power cannot be NaN: " + powerName);
        }
        if (power < 0.0 || power > 1.0) {
            throw new IllegalArgumentException(
                    "Intake power must be between 0.0 and 1.0: " + powerName);
        }
        return power;
    }

    private enum IntakeMode {
        IDLE,
        INTAKING,
        HOLDING,
        EJECTING
    }
}
