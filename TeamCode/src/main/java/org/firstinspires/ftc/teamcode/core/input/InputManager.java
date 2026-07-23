package org.firstinspires.ftc.teamcode.core.input;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Tracks TeleOp gamepad buttons and axes for one driver controller.
 *
 * <p>Call {@link #update(Gamepad)} exactly once at the start of each OpMode
 * loop, before reading any held, just-pressed, just-released, axis, or trigger
 * values. Edge checks compare the current loop's values with the previous
 * loop's values.</p>
 *
 * <p>On the first update, buttons that are already held are treated as held but
 * not just pressed. This avoids surprising button presses when an OpMode
 * starts while a driver is already holding a button.</p>
 */
public class InputManager {
    private ButtonState aButton = new ButtonState();
    private ButtonState bButton = new ButtonState();
    private ButtonState xButton = new ButtonState();
    private ButtonState yButton = new ButtonState();
    private ButtonState leftBumper = new ButtonState();
    private ButtonState rightBumper = new ButtonState();

    private boolean hasUpdated;
    private double leftStickX;
    private double leftStickY;
    private double rightStickX;
    private double rightStickY;
    private double leftTrigger;
    private double rightTrigger;

    /**
     * Updates all tracked values from the current gamepad snapshot.
     *
     * @param gamepad FTC gamepad to read this loop
     * @throws IllegalArgumentException if {@code gamepad} is {@code null}
     */
    public void update(Gamepad gamepad) {
        if (gamepad == null) {
            throw new IllegalArgumentException("Gamepad cannot be null.");
        }

        updateButtonStates(gamepad);

        leftStickX = gamepad.left_stick_x;
        leftStickY = gamepad.left_stick_y;
        rightStickX = gamepad.right_stick_x;
        rightStickY = gamepad.right_stick_y;
        leftTrigger = gamepad.left_trigger;
        rightTrigger = gamepad.right_trigger;

        hasUpdated = true;
    }

    public boolean isAHeld() {
        ensureUpdated();
        return aButton.isHeld();
    }

    public boolean wasAJustPressed() {
        ensureUpdated();
        return aButton.wasJustPressed();
    }

    public boolean wasAJustReleased() {
        ensureUpdated();
        return aButton.wasJustReleased();
    }

    public boolean isBHeld() {
        ensureUpdated();
        return bButton.isHeld();
    }

    public boolean wasBJustPressed() {
        ensureUpdated();
        return bButton.wasJustPressed();
    }

    public boolean wasBJustReleased() {
        ensureUpdated();
        return bButton.wasJustReleased();
    }

    public boolean isXHeld() {
        ensureUpdated();
        return xButton.isHeld();
    }

    public boolean wasXJustPressed() {
        ensureUpdated();
        return xButton.wasJustPressed();
    }

    public boolean wasXJustReleased() {
        ensureUpdated();
        return xButton.wasJustReleased();
    }

    public boolean isYHeld() {
        ensureUpdated();
        return yButton.isHeld();
    }

    public boolean wasYJustPressed() {
        ensureUpdated();
        return yButton.wasJustPressed();
    }

    public boolean wasYJustReleased() {
        ensureUpdated();
        return yButton.wasJustReleased();
    }

    public boolean isLeftBumperHeld() {
        ensureUpdated();
        return leftBumper.isHeld();
    }

    public boolean wasLeftBumperJustPressed() {
        ensureUpdated();
        return leftBumper.wasJustPressed();
    }

    public boolean wasLeftBumperJustReleased() {
        ensureUpdated();
        return leftBumper.wasJustReleased();
    }

    public boolean isRightBumperHeld() {
        ensureUpdated();
        return rightBumper.isHeld();
    }

    public boolean wasRightBumperJustPressed() {
        ensureUpdated();
        return rightBumper.wasJustPressed();
    }

    public boolean wasRightBumperJustReleased() {
        ensureUpdated();
        return rightBumper.wasJustReleased();
    }

    public double getLeftStickX() {
        ensureUpdated();
        return leftStickX;
    }

    public double getLeftStickY() {
        ensureUpdated();
        return leftStickY;
    }

    public double getRightStickX() {
        ensureUpdated();
        return rightStickX;
    }

    public double getRightStickY() {
        ensureUpdated();
        return rightStickY;
    }

    public double getLeftTrigger() {
        ensureUpdated();
        return leftTrigger;
    }

    public double getRightTrigger() {
        ensureUpdated();
        return rightTrigger;
    }

    private void updateButtonStates(Gamepad gamepad) {
        if (!hasUpdated) {
            aButton = ButtonState.firstUpdate(gamepad.a);
            bButton = ButtonState.firstUpdate(gamepad.b);
            xButton = ButtonState.firstUpdate(gamepad.x);
            yButton = ButtonState.firstUpdate(gamepad.y);
            leftBumper = ButtonState.firstUpdate(gamepad.left_bumper);
            rightBumper = ButtonState.firstUpdate(gamepad.right_bumper);
            return;
        }

        aButton = aButton.next(gamepad.a);
        bButton = bButton.next(gamepad.b);
        xButton = xButton.next(gamepad.x);
        yButton = yButton.next(gamepad.y);
        leftBumper = leftBumper.next(gamepad.left_bumper);
        rightBumper = rightBumper.next(gamepad.right_bumper);
    }

    private void ensureUpdated() {
        if (!hasUpdated) {
            throw new IllegalStateException("InputManager.update(gamepad) must be called before reading input.");
        }
    }

    private static class ButtonState {
        private final boolean previous;
        private final boolean current;

        ButtonState() {
            this(false, false);
        }

        private ButtonState(boolean previous, boolean current) {
            this.previous = previous;
            this.current = current;
        }

        static ButtonState firstUpdate(boolean current) {
            return new ButtonState(current, current);
        }

        ButtonState next(boolean nextCurrent) {
            return new ButtonState(current, nextCurrent);
        }

        boolean isHeld() {
            return current;
        }

        boolean wasJustPressed() {
            return current && !previous;
        }

        boolean wasJustReleased() {
            return !current && previous;
        }
    }
}
