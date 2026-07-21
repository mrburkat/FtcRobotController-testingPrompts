# PVI-FTC Implementation Status

## Repository baseline

- Source repository: PVI-FTC fork of FtcRobotController
- Current sequential prompt: Prompt 1 complete, pending student review
- Last completed prompt: Prompt 1: Inspect the FTC project and create the package structure
- Last verified commit: 8efc30c

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

## Current public APIs

None created by the sequential architecture exercise yet. Prompt 1 added only
package-level documentation files and no public classes, interfaces, methods, or
fields.

## Build status

- Approved JDK: Not yet recorded by the team.
- Android Studio version: Record the team-approved version here.
- FTC SDK version or tag: Record here.
- TeamCode build command:
    - macOS/Linux: `./gradlew TeamCode:assembleDebug`
    - Windows: `.\gradlew.bat TeamCode:assembleDebug`
- Last result: PASS during Prompt 1 using Android Studio JDK 21. The build
  completed `:TeamCode:assembleDebug` successfully after Gradle cache and
  dependency access were available.

## Known limitations and TODO items

- Verify the Prompt 1 branch independently on the approved student environment.
- Record the team-approved JDK, Android Studio version, and FTC SDK version or
  tag.
- Configure branch protection and pull-request review.
- Consider adding compile-only GitHub Actions validation.
- Prompt 1 intentionally does not create framework classes, OpModes, subsystem
  implementations, or hardware wrappers.

## Next planned task

Prompt 2: Create core finite-state-machine interfaces and classes.

## Update instructions

After every completed prompt, replace or extend the sections above with:
- prompt number and title;
- completed classes and behavior;
- actual package names and public APIs;
- important implementation decisions;
- build command and result;
- known limitations;
- next prompt.
