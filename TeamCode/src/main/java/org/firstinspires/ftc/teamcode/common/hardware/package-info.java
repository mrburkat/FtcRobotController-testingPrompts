/**
 * Contains shared hardware wrapper classes for FTC devices.
 *
 * <p>Hardware wrappers own direct access to configured FTC devices. They expose
 * beginner-readable methods for higher layers without reading driver controls,
 * running state machines, or making autonomous decisions.</p>
 *
 * <p>The baseline drivetrain is required and fails loudly when configured
 * motors are missing. Intake and vision are optional during early testing and
 * report availability instead of stopping drivetrain initialization.</p>
 */
package org.firstinspires.ftc.teamcode.common.hardware;
