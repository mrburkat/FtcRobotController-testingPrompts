# PVI-FTC Implementation Status

## Repository baseline

- Source repository: PVI-FTC fork of FtcRobotController
- Current sequential prompt: Prompt 10 complete, pending student review
- Last completed prompt: Prompt 10: Integrate Team A intake through Robot and TeleOp
- Last verified commit: 87c2149

## Completed work

- Added repository instructions and architecture documentation.
- Added sequential student workflow documentation.
- Prompt 1 confirmed the TeamCode Java source root is `TeamCode/src/main/java`.
- Prompt 1 confirmed the TeamCode root package is `org.firstinspires.ftc.teamcode`.
- Prompt 1 created documented package roots under `org.firstinspires.ftc.teamcode` for:
    - `core.fsm`
    - `core.robot`
    - `core.input`
    - `common.hardware`
    - `common.subsystems.drive`
    - `common.subsystems.intake`
    - `common.subsystems.vision`
    - `common.autonomous`
    - `robots.teamA`
    - `robots.teamB`
    - `robots.teamC`
    - `opmodes.teleop`
    - `opmodes.autonomous`
    - `opmodes.testing`
- Prompt 1 confirmed the top-level TeamCode docs directory is `TeamCode/project-docs`.
- Prompt 2 added the core robot lifecycle API:
    - `core.robot.Subsystem`
    - `core.robot.Robot`
- Prompt 2 made `Robot` own subsystem registration, initialization, update, and
  stop order directly without adding a scheduler or subsystem manager.
- Prompt 3 added the reusable finite-state-machine API:
    - `core.fsm.State`
    - `core.fsm.Transition`
    - `core.fsm.FSM`
- Prompt 3 made transition evaluation deterministic by preserving registration
  order and allowing at most one transition per update cycle.
- Prompt 4 added the shared hardware abstraction layer:
    - `common.hardware.DriveHardware`
    - `common.hardware.IntakeHardware`
    - `common.hardware.VisionHardware`
    - `common.hardware.RobotHardware`
- Prompt 4 followed the active hardware-abstraction branch and prompt text even
  though the previous status entry listed input manager as the next task.
- Prompt 4 documented and implemented the required-versus-optional hardware
  policy: drive motors are required; intake and vision are optional.
- Prompt 5 added the shared drive subsystem and drive FSM:
    - `common.subsystems.drive.DriveSubsystem`
    - `common.subsystems.drive.DisabledDriveState`
    - `common.subsystems.drive.ManualDriveState`
    - `common.subsystems.drive.HeadingHoldState`
- Prompt 5 followed the active drive-subsystem branch and prompt text even
  though the previous status entry listed input manager as the next task.
- Prompt 5 centralized mecanum wheel-power calculation in `DriveSubsystem` and
  used the existing reusable FSM API for drive mode transitions.
- Prompt 6 added Team A robot composition:
    - `robots.teamA.TeamARobot`
- Prompt 6 followed the active Team A robot branch and prompt text even though
  the previous status entry listed input manager as the next task.
- Prompt 6 established the Team A hardware initialization boundary at
  `TeamARobot.initialize(HardwareMap)`, which initializes `RobotHardware` before
  the inherited robot lifecycle initializes registered subsystems.
- Prompt 6 registers `DriveSubsystem` exactly once and exposes robot-level drive
  commands and narrow telemetry diagnostics without exposing mutable FSM internals.
- Prompt 7 added the shared TeleOp input API:
    - `core.input.InputManager`
- Prompt 7 made `InputManager` robot-agnostic. It reads one FTC `Gamepad`
  snapshot per OpMode loop and exposes button edge states, held states, axes,
  and triggers without knowing Team A robot composition, subsystems, hardware
  wrappers, telemetry, or autonomous routines.
- Prompt 8 added the Team A driver-controlled OpMode:
    - `opmodes.teleop.TeamATeleOp`
- Prompt 8 uses the iterative FTC `OpMode` lifecycle, owns a `TeamARobot` and
  `InputManager`, initializes Team A hardware through `TeamARobot.initialize`,
  maps `gamepad1` sticks to Team A drive requests, maps Y/X button edges to
  heading-hold/manual drive requests, calls `robot.update()` once per loop, and
  reports drive diagnostics through telemetry.
- Prompt 8 keeps hardware access, mecanum math, drive FSM details, intake, and
  vision behavior out of the OpMode.
- Prompt 9 added the shared intake subsystem and intake FSM:
    - `common.subsystems.intake.IntakeSubsystem`
    - `common.subsystems.intake.IdleIntakeState`
    - `common.subsystems.intake.IntakingState`
    - `common.subsystems.intake.HoldingState`
    - `common.subsystems.intake.EjectingState`
- Prompt 9 made `IntakeSubsystem` own the intake FSM and expose request methods
  instead of exposing concrete state selection to callers.
- Prompt 9 keeps intake hardware optional. If the configured intake motor is
  missing, all intake states remain safe no-ops through `IntakeHardware`, and
  drivetrain operation remains unaffected.
- Prompt 9 added a narrow `IntakeHardware.setPower(double)` API so intake and
  eject states can use named configurable powers while preserving the existing
  `forward()`, `reverse()`, and `stop()` methods.
- Prompt 10 integrated the shared intake subsystem into Team A robot
  composition and TeleOp:
    - `robots.teamA.TeamARobot`
    - `opmodes.teleop.TeamATeleOp`
- Prompt 10 makes `TeamARobot` create `IntakeSubsystem` from the existing
  optional `IntakeHardware`, register it exactly once, and expose narrow intake
  request and telemetry methods without exposing the FSM or state instances.
- Prompt 10 maps `gamepad2` button edges in `TeamATeleOp`: A starts intake, B
  stops intake, X ejects, and Y requests hold. Existing `gamepad1` drive
  mappings are preserved.
- Prompt 10 adds intake state and intake hardware availability to TeleOp
  telemetry.

## Current public APIs

- `org.firstinspires.ftc.teamcode.core.robot.Subsystem`
    - `void initialize()`
    - `void update()`
    - `void stop()`
    - `String getName()`
- `org.firstinspires.ftc.teamcode.core.robot.Robot`
    - `protected final void registerSubsystem(Subsystem subsystem)`
    - `public final void initialize()`
    - `public final void update()`
    - `public final void stop()`
    - `protected void onInitialize()`
    - `protected void onStop()`
- Duplicate registration of the same subsystem instance is a clear programming
  error and throws `IllegalArgumentException`.
- Registering a subsystem after robot initialization has started throws
  `IllegalStateException`.
- `org.firstinspires.ftc.teamcode.core.fsm.State`
    - `void enter()`
    - `void update()`
    - `void exit()`
    - `String getName()`
- `org.firstinspires.ftc.teamcode.core.fsm.Transition`
    - `Transition(State sourceState, State targetState, BooleanSupplier condition)`
    - `State getSourceState()`
    - `State getTargetState()`
    - `boolean appliesTo(State currentState)`
    - `boolean isConditionSatisfied()`
- `org.firstinspires.ftc.teamcode.core.fsm.FSM`
    - `FSM(State initialState)`
    - `void addTransition(Transition transition)`
    - `void start()`
    - `void update()`
    - `State getCurrentState()`
    - `String getCurrentStateName()`
- `FSM.update()` starts the initial state if needed, checks transitions in
  insertion order, fires at most one transition, and updates the active state
  after the transition decision. If a transition fires, the newly active state is
  updated in that same cycle.
- `org.firstinspires.ftc.teamcode.common.hardware.DriveHardware`
    - hardware name constants: `FRONT_LEFT_NAME`, `FRONT_RIGHT_NAME`,
      `REAR_LEFT_NAME`, `REAR_RIGHT_NAME`
    - `void initialize(HardwareMap hardwareMap)`
    - `void setMotorPowers(double frontLeftPower, double frontRightPower, double rearLeftPower, double rearRightPower)`
    - `void stop()`
    - `void setBrakeMode()`
    - `void setFloatMode()`
    - `double getLastFrontLeftPower()`
    - `double getLastFrontRightPower()`
    - `double getLastRearLeftPower()`
    - `double getLastRearRightPower()`
- `org.firstinspires.ftc.teamcode.common.hardware.IntakeHardware`
    - hardware name constant: `INTAKE_MOTOR_NAME`
    - `void initialize(HardwareMap hardwareMap)`
    - `void forward()`
    - `void reverse()`
    - `void stop()`
    - `void setPower(double power)`
    - `boolean isAvailable()`
    - `double getLastPower()`
- `org.firstinspires.ftc.teamcode.common.hardware.VisionHardware`
    - `void initialize(HardwareMap hardwareMap)`
    - `void update()`
    - `void stop()`
    - `boolean isAvailable()`
- `org.firstinspires.ftc.teamcode.common.hardware.RobotHardware`
    - `void initialize(HardwareMap hardwareMap)`
    - `DriveHardware getDriveHardware()`
    - `IntakeHardware getIntakeHardware()`
    - `VisionHardware getVisionHardware()`
    - `void stopAll()`
- Drive motor names are centralized as `frontLeft`, `frontRight`, `rearLeft`,
  and `rearRight`. Intake motor name is centralized as `intake`.
- Drive initialization sets left motors forward, right motors reverse, BRAKE
  zero-power behavior, and `RUN_WITHOUT_ENCODER`.
- Drive power commands are clamped to the safe FTC range of -1.0 through 1.0,
  reject `NaN`, and are stored for telemetry.
- Missing required drive motors throw `IllegalStateException` identifying the
  missing configured name. Missing intake hardware safely reports unavailable.
- Vision is a lifecycle-only placeholder and reports unavailable until a later
  prompt adds a real vision device.
- `IntakeHardware.setPower(double)` clamps commands to the safe FTC range of
  -1.0 through 1.0, rejects `NaN`, and is a safe no-op with stored power 0.0
  when the optional intake motor is unavailable.
- `org.firstinspires.ftc.teamcode.common.subsystems.drive.DriveSubsystem`
    - `DriveSubsystem(DriveHardware driveHardware)`
    - `void initialize()`
    - `void update()`
    - `void stop()`
    - `String getName()`
    - `void drive(double forward, double strafe, double rotate)`
    - `void enableManualDrive()`
    - `void disableDrive()`
    - `void enableHeadingHold()`
    - `String getCurrentStateName()`
    - `double getRequestedForward()`
    - `double getRequestedStrafe()`
    - `double getRequestedRotate()`
- `org.firstinspires.ftc.teamcode.common.subsystems.drive.DisabledDriveState`
    - Implements `State`; stops all drive output while active.
- `org.firstinspires.ftc.teamcode.common.subsystems.drive.ManualDriveState`
    - Implements `State`; applies stored mecanum drive requests.
- `org.firstinspires.ftc.teamcode.common.subsystems.drive.HeadingHoldState`
    - Implements `State`; honest placeholder that allows translation and
      suppresses rotation until heading feedback is added later.
- Drive mode requests update an internal requested mode used by deterministic
  FSM transitions. External code does not set FSM state directly.
- Manual drive uses:
    - `frontLeft = forward + strafe + rotate`
    - `frontRight = forward - strafe - rotate`
    - `rearLeft = forward - strafe + rotate`
    - `rearRight = forward + strafe - rotate`
- Wheel powers are normalized so no absolute value exceeds 1.0 before being sent
  to `DriveHardware`.
- Drive input requests are clamped to -1.0 through 1.0, reject `NaN`, and are
  exposed for telemetry.
- `org.firstinspires.ftc.teamcode.robots.teamA.TeamARobot`
    - `TeamARobot()`
    - `TeamARobot(RobotHardware robotHardware)`
    - `void initialize(HardwareMap hardwareMap)`
    - `void drive(double forward, double strafe, double rotate)`
    - `void enableManualDrive()`
    - `void disableDrive()`
    - `void enableHeadingHold()`
    - `void startIntake()`
    - `void stopIntake()`
    - `void holdIntake()`
    - `void ejectIntake()`
    - `String getDriveStateName()`
    - `String getIntakeStateName()`
    - `double getRequestedForward()`
    - `double getRequestedStrafe()`
    - `double getRequestedRotate()`
    - `double getLastFrontLeftPower()`
    - `double getLastFrontRightPower()`
    - `double getLastRearLeftPower()`
    - `double getLastRearRightPower()`
    - `boolean isIntakeAvailable()`
    - `boolean isVisionAvailable()`
- `TeamARobot` subclasses `Robot`, owns `RobotHardware`, creates a
  `DriveSubsystem` from `RobotHardware.getDriveHardware()`, creates an
  `IntakeSubsystem` from `RobotHardware.getIntakeHardware()`, and registers each
  subsystem once in the constructor.
- `TeamARobot` does not read driver controls, directly fetch configured devices,
  expose drive or intake FSMs, or define any OpMode entry point.
- `org.firstinspires.ftc.teamcode.core.input.InputManager`
    - `void update(Gamepad gamepad)`
    - `boolean isAHeld()`
    - `boolean wasAJustPressed()`
    - `boolean wasAJustReleased()`
    - `boolean isBHeld()`
    - `boolean wasBJustPressed()`
    - `boolean wasBJustReleased()`
    - `boolean isXHeld()`
    - `boolean wasXJustPressed()`
    - `boolean wasXJustReleased()`
    - `boolean isYHeld()`
    - `boolean wasYJustPressed()`
    - `boolean wasYJustReleased()`
    - `boolean isLeftBumperHeld()`
    - `boolean wasLeftBumperJustPressed()`
    - `boolean wasLeftBumperJustReleased()`
    - `boolean isRightBumperHeld()`
    - `boolean wasRightBumperJustPressed()`
    - `boolean wasRightBumperJustReleased()`
    - `double getLeftStickX()`
    - `double getLeftStickY()`
    - `double getRightStickX()`
    - `double getRightStickY()`
    - `double getLeftTrigger()`
    - `double getRightTrigger()`
- `InputManager.update(Gamepad)` must be called exactly once at the start of
  each TeleOp loop before reading inputs. Reading input before the first update
  throws `IllegalStateException`; passing a null `Gamepad` throws
  `IllegalArgumentException`.
- On the first update, buttons that are already held are reported as held but
  not just pressed, so startup-held buttons do not create repeated press edges.
- `org.firstinspires.ftc.teamcode.opmodes.teleop.TeamATeleOp`
    - FTC annotation: `@TeleOp(name = "Team A TeleOp", group = "Team A")`
    - `void init()`
    - `void start()`
    - `void loop()`
    - `void stop()`
- `TeamATeleOp` uses standard Team A drive mappings:
    - `forward = -gamepad1.left_stick_y`
    - `strafe = gamepad1.left_stick_x`
    - `rotate = gamepad1.right_stick_x`
- `TeamATeleOp` requests heading-hold mode when gamepad1 Y is just pressed and
  requests manual drive mode when gamepad1 X is just pressed.
- `TeamATeleOp` uses a second `InputManager` for `gamepad2` operator controls:
    - A just pressed: `TeamARobot.startIntake()`
    - B just pressed: `TeamARobot.stopIntake()`
    - X just pressed: `TeamARobot.ejectIntake()`
    - Y just pressed: `TeamARobot.holdIntake()`
- `TeamATeleOp` reports drive state, requested forward/strafe/rotate values,
  intake state, intake availability, and the four last commanded wheel powers
  exposed by `TeamARobot`.
- `org.firstinspires.ftc.teamcode.common.subsystems.intake.IntakeSubsystem`
    - constants: `DEFAULT_INTAKE_POWER`, `DEFAULT_EJECT_POWER`
    - `IntakeSubsystem(IntakeHardware intakeHardware)`
    - `IntakeSubsystem(IntakeHardware intakeHardware, double intakePower, double ejectPower)`
    - `void initialize()`
    - `void update()`
    - `void stop()`
    - `String getName()`
    - `void startIntake()`
    - `void stopIntake()`
    - `void hold()`
    - `void eject()`
    - `String getCurrentStateName()`
    - `boolean isAvailable()`
- `IntakeSubsystem` starts in `Idle`, evaluates transitions through the shared
  `FSM`, and stores requested intake mode internally. Repeated requests for the
  current mode do not cause duplicate state changes.
- `org.firstinspires.ftc.teamcode.common.subsystems.intake.IdleIntakeState`
    - Implements `State`; stops intake output while active.
- `org.firstinspires.ftc.teamcode.common.subsystems.intake.IntakingState`
    - Implements `State`; runs intake forward using the configured intake power.
- `org.firstinspires.ftc.teamcode.common.subsystems.intake.HoldingState`
    - Implements `State`; stops the motor for the baseline and documents future
      low-power holding as a possible mechanism-specific extension.
- `org.firstinspires.ftc.teamcode.common.subsystems.intake.EjectingState`
    - Implements `State`; runs intake in reverse using the configured eject
      power.

## Build status

- Approved JDK: Not yet recorded by the team.
- Android Studio version: Record the team-approved version here.
- FTC SDK version or tag: Record here.
- TeamCode build command:
    - macOS/Linux: `./gradlew TeamCode:assembleDebug`
    - Windows: `.\gradlew.bat TeamCode:assembleDebug`
- Prompt 8 validation command:
    - `JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home' ./gradlew TeamCode:assembleDebug`
- Prompt 9 validation command:
    - `JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home' ./gradlew TeamCode:assembleDebug`
- Prompt 10 validation command:
    - `JAVA_HOME='/Applications/Android Studio.app/Contents/jbr/Contents/Home' ./gradlew TeamCode:assembleDebug`
- Last result: PASS during Prompt 10 using Android Studio JDK 21. The build
  completed `:TeamCode:assembleDebug` successfully after Gradle cache and
  dependency access were available.

## Known limitations and TODO items

- Verify the Prompt 1 branch independently on the approved student environment.
- Record the team-approved JDK, Android Studio version, and FTC SDK version or
  tag.
- Configure branch protection and pull-request review.
- Consider adding compile-only GitHub Actions validation.
- Prompt 8 intentionally does not add intake controls, vision controls,
  autonomous routines, robot intent objects, command queues, configurable input
  profiles, direct hardware access, or a scheduler.
- Prompt 8 uses the existing placeholder heading-hold mode; later heading
  feedback work is still needed before it can hold a real field heading.
- Prompt 9 does not add sensors, automatic hold detection, timed ejection, or
  team-specific intake geometry.
- Prompt 10 keeps intake optional. If the configured intake motor is absent,
  Team A robot still initializes and drives because `IntakeHardware` remains in
  its safe unavailable no-op state.
- Prompt 10 does not add intake controls to `gamepad1`, autonomous intake
  commands, sensors, automatic hold detection, timed ejection, or team-specific
  intake geometry.

## Next planned task

Prompt 11: To be supplied by the sequential exercise.

## Update instructions

After every completed prompt, replace or extend the sections above with:
- prompt number and title;
- completed classes and behavior;
- actual package names and public APIs;
- important implementation decisions;
- build command and result;
- known limitations;
- next prompt.
