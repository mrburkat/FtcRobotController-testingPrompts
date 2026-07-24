package org.firstinspires.ftc.teamcode.common.autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Non-blocking wait step based on elapsed time.
 */
public class WaitStep implements AutoStep {
    private static final String NAME = "Wait";

    private final double durationSeconds;
    private final ElapsedTime timer = new ElapsedTime();
    private boolean started;

    /**
     * Creates a wait step.
     *
     * @param durationSeconds wait duration in seconds; negative values are
     *                        treated as zero
     */
    public WaitStep(double durationSeconds) {
        this.durationSeconds = Math.max(0.0, durationSeconds);
    }

    @Override
    public void start() {
        timer.reset();
        started = true;
    }

    @Override
    public void update() {
        // Time advances outside this method; there is no blocking work to do.
    }

    @Override
    public boolean isFinished() {
        return started && timer.seconds() >= durationSeconds;
    }

    @Override
    public void stop() {
        // Waiting controls no mechanism.
    }

    @Override
    public String getName() {
        return NAME;
    }
}
