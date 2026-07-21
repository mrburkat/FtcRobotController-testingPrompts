# PVI-FTC Implementation Status

## Repository baseline

- Source repository: PVI-FTC fork of FtcRobotController
- Current sequential prompt: Prompt 3 complete, pending student review
- Last completed prompt: Prompt 3: Implement the reusable FSM core
- Last verified commit: 7876756

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

## Build status

- Approved JDK: Not yet recorded by the team.
- Android Studio version: Record the team-approved version here.
- FTC SDK version or tag: Record here.
- TeamCode build command:
    - macOS/Linux: `./gradlew TeamCode:assembleDebug`
    - Windows: `.\gradlew.bat TeamCode:assembleDebug`
- Last result: PASS during Prompt 3 using Android Studio JDK 21. The build
  completed `:TeamCode:assembleDebug` successfully after Gradle cache and
  dependency access were available.

## Known limitations and TODO items

- Verify the Prompt 1 branch independently on the approved student environment.
- Record the team-approved JDK, Android Studio version, and FTC SDK version or
  tag.
- Configure branch protection and pull-request review.
- Consider adding compile-only GitHub Actions validation.
- Prompt 3 intentionally does not create concrete subsystems, OpModes, input
  classes, autonomous classes, hardware wrappers, or a scheduler.

## Next planned task

Prompt 4: Create shared input manager.

## Update instructions

After every completed prompt, replace or extend the sections above with:
- prompt number and title;
- completed classes and behavior;
- actual package names and public APIs;
- important implementation decisions;
- build command and result;
- known limitations;
- next prompt.
