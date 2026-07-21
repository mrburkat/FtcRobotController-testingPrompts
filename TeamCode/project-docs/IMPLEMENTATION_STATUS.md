# PVI-FTC Implementation Status

## Repository baseline

- Source repository: PVI-FTC fork of FtcRobotController
- Current sequential prompt: Prompt 4 complete, pending student review
- Last completed prompt: Prompt 4: Create the hardware abstraction layer
- Last verified commit: 7c6cbd5

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

## Build status

- Approved JDK: Not yet recorded by the team.
- Android Studio version: Record the team-approved version here.
- FTC SDK version or tag: Record here.
- TeamCode build command:
    - macOS/Linux: `./gradlew TeamCode:assembleDebug`
    - Windows: `.\gradlew.bat TeamCode:assembleDebug`
- Last result: PASS during Prompt 4 using Android Studio JDK 21. The build
  completed `:TeamCode:assembleDebug` successfully after Gradle cache and
  dependency access were available.

## Known limitations and TODO items

- Verify the Prompt 1 branch independently on the approved student environment.
- Record the team-approved JDK, Android Studio version, and FTC SDK version or
  tag.
- Configure branch protection and pull-request review.
- Consider adding compile-only GitHub Actions validation.
- Prompt 4 intentionally does not create concrete subsystems, OpModes, input
  classes, autonomous classes, FSM behavior inside hardware wrappers, camera
  behavior, AprilTag processing, OpenCV processing, VisionPortal behavior, or a
  scheduler.

## Next planned task

Prompt 5: Create shared input manager.

## Update instructions

After every completed prompt, replace or extend the sections above with:
- prompt number and title;
- completed classes and behavior;
- actual package names and public APIs;
- important implementation decisions;
- build command and result;
- known limitations;
- next prompt.
