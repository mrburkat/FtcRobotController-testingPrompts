/**
 * Contains shared drive subsystem behavior and drive-related states.
 *
 * <p>Drive code in this package depends on common hardware wrappers and
 * framework state-machine classes, not on entry points or team-specific driver
 * mappings. Mecanum drive math lives here so higher layers can request movement
 * without duplicating wheel-power calculations.</p>
 */
package org.firstinspires.ftc.teamcode.common.subsystems.drive;
