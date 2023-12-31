// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import frc.robot.util.COTSFalconSwerveConstants;
import frc.robot.util.Gains;
import frc.robot.util.SwerveModuleConstants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final boolean tuningMode = true;

    public static final class OffsetConstants {
        // Module Offsets
        // Mod 0
        public static final double MOD_0_OFFSET = 220.7;
        // Mod 1
        public static final double MOD_1_OFFSET = 218.1;
        // Mod 2
        public static final double MOD_2_OFFSET = 88.9;
        // Mod 3
        public static final double MOD_3_OFFSET = 178.1;

    }

    public static final class CanConstants {
        // drivebase CAN IDs
        public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 1;
        public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 2;
        public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 3;
        public static final int BACK_LEFT_MODULE_STEER_MOTOR = 4;
        public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 5;
        public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 6;
        public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 7;
        public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 8;
        public static final int FRONT_LEFT_MODULE_STEER_ENCODER = 9;
        public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 10;
        public static final int BACK_LEFT_MODULE_STEER_ENCODER = 11;
        public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 12;

        // Gyro CAN ID
        public static final int PIGEON2 = 13;
    }

    public static final class SwerveConstants {

        public static final double DRIVE_DEADBAND = 0.2;

        // Mod 0
        public static final double FRONT_LEFT_MODULE_STEER_OFFSET = OffsetConstants.MOD_0_OFFSET;
        // Mod 1
        public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = OffsetConstants.MOD_1_OFFSET;
        // Mod 2
        public static final double BACK_LEFT_MODULE_STEER_OFFSET = OffsetConstants.MOD_2_OFFSET;
        // Mod 3
        public static final double BACK_RIGHT_MODULE_STEER_OFFSET = OffsetConstants.MOD_3_OFFSET;

        public static final boolean INVERT_GYRO = false; // Always ensure Gyro is CCW+ CW-

        // 2023 Robot
        public static final COTSFalconSwerveConstants CHOSEN_MODULE = COTSFalconSwerveConstants
                .SDSMK4i(COTSFalconSwerveConstants.driveGearRatios.SDSMK4i_L2);

        /* Drivetrain Constants */
        public static final double TRACK_WIDTH = Units.inchesToMeters(18.75);
        public static final double WHEEL_BASE = Units.inchesToMeters(18.75);
        public static final double WHEEL_CIRCUMFRENCE = CHOSEN_MODULE.wheelCircumference;

        /*
         * Swerve Kinematics
         * No need to ever change this unless you are not doing a traditional
         * rectangular/square 4 module swerve
         */
        public static final SwerveDriveKinematics SWERVE_DRIVE_KINEMATICS = new SwerveDriveKinematics(
                new Translation2d(WHEEL_BASE / 2.0, TRACK_WIDTH / 2.0),
                new Translation2d(WHEEL_BASE / 2.0, -TRACK_WIDTH / 2.0),
                new Translation2d(-WHEEL_BASE / 2.0, TRACK_WIDTH / 2.0),
                new Translation2d(-WHEEL_BASE / 2.0, -TRACK_WIDTH / 2.0));

        /* Module Gear Ratios */
        public static final double DRIVE_GEAR_RATIO = CHOSEN_MODULE.driveGearRatio;
        public static final double ANGLE_GEAR_RATIO = CHOSEN_MODULE.angleGearRatio;

        /* Motor Inverts */
        public static final boolean ANGLE_MOTOR_INVERT = CHOSEN_MODULE.angleMotorInvert;
        public static final boolean DRIVE_MOTOR_INVERT = CHOSEN_MODULE.driveMotorInvert;

        /* Angle Encoder Invert */
        public static final boolean CANCODER_INVERT = CHOSEN_MODULE.canCoderInvert;

        /* Swerve Current Limiting */
        public static final int ANGLE_CONTINUOUS_CURRENT_LIMIT = 25;
        public static final int ANGLE_PEAK_CURRENT_LIMIT = 40;
        public static final double ANGLE_PEAK_CURRENT_DURATION = 0.1;
        public static final boolean ANGLE_ENABLE_CURRENT_LIMIT = true;

        public static final int DRIVE_CONTINUOUS_CURRENT_LIMIT = 35;
        public static final int DRIVE_PEAK_CURRENT_LIMIT = 60;
        public static final double DRIVE_PEAK_CURRENT_DURATION = 0.1;
        public static final boolean DRIVE_ENABLE_CURRENT_LIMIT = true;

        /*
         * These values are used by the drive falcon to ramp in open loop and closed
         * loop driving.
         * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc
         */
        public static final double OPEN_LOOP_RAMP = 0.25;
        public static final double CLOSED_LOOP_RAMP = 0.0;

        /* Angle Motor PID Values */
        public static final Gains GAINS_ANGLE_MOTOR = new Gains(CHOSEN_MODULE.angleKP, CHOSEN_MODULE.angleKI,
                CHOSEN_MODULE.angleKD, CHOSEN_MODULE.angleKF, 0.0);

        /* Drive Motor PID Values */
        public static final Gains GAINS_DRIVE_MOTOR = new Gains(0.05, 0.0, 0.0, 0.0, 0);

        /*
         * Drive Motor Characterization Values
         * Divide SYSID values by 12 to convert from volts to percent output for CTRE
         */
        public static final double DRIVE_KS = (0.32 / 12);
        public static final double DRIVE_KV = (1.51 / 12);
        public static final double DRIVE_KA = (0.27 / 12);

        /* Swerve Profiling Values */
        /** Meters per Second */
        public static final double MAX_SPEED = 4.87;
        // 2.5

        /** Radians per Second */
        public static final double MAX_ANGULAR_VELOCITY = 10.0;
        // 4.5

        /* Neutral Modes */
        public static final NeutralMode ANGLE_NEUTRAL_MODE = NeutralMode.Coast;
        public static final NeutralMode DRIVE_NEUTRAL_MODE = NeutralMode.Brake;

        /* Module Specific Constants */
        /* Front Left Module - Module 0 */
        public static final class Mod0 {
            public static final int driveMotorID = CanConstants.FRONT_LEFT_MODULE_DRIVE_MOTOR;
            public static final int angleMotorID = CanConstants.FRONT_LEFT_MODULE_STEER_MOTOR;
            public static final int canCoderID = CanConstants.FRONT_LEFT_MODULE_STEER_ENCODER;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(FRONT_LEFT_MODULE_STEER_OFFSET);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                    canCoderID, angleOffset);
        }

        /* Front Right Module - Module 1 */
        public static final class Mod1 {
            public static final int driveMotorID = CanConstants.FRONT_RIGHT_MODULE_DRIVE_MOTOR;
            public static final int angleMotorID = CanConstants.FRONT_RIGHT_MODULE_STEER_MOTOR;
            public static final int canCoderID = CanConstants.FRONT_RIGHT_MODULE_STEER_ENCODER;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(FRONT_RIGHT_MODULE_STEER_OFFSET);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                    canCoderID, angleOffset);
        }

        /* Back Left Module - Module 2 */
        public static final class Mod2 {
            public static final int driveMotorID = CanConstants.BACK_LEFT_MODULE_DRIVE_MOTOR;
            public static final int angleMotorID = CanConstants.BACK_LEFT_MODULE_STEER_MOTOR;
            public static final int canCoderID = CanConstants.BACK_LEFT_MODULE_STEER_ENCODER;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(BACK_LEFT_MODULE_STEER_OFFSET);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                    canCoderID, angleOffset);
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 {
            public static final int driveMotorID = CanConstants.BACK_RIGHT_MODULE_DRIVE_MOTOR;
            public static final int angleMotorID = CanConstants.BACK_RIGHT_MODULE_STEER_MOTOR;
            public static final int canCoderID = CanConstants.BACK_RIGHT_MODULE_STEER_ENCODER;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(BACK_RIGHT_MODULE_STEER_OFFSET);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                    canCoderID, angleOffset);
        }

        public static final Gains GAINS_ANGLE_SNAP = new Gains(0.02, 0.0, 0.0, 0.0, 50);

        public static final Gains GAINS_BALANCE = new Gains(0.05, 0.0, 0.0, 0.0, 50);

        public static final double SNAP_TOLERANCE = 2.0;
        public static final double BALANCE_TOLERANCE = 0.001;
    }

    public static final class LimelightConstants {
        public static final int APRILTAG_PIPELINE = 0;

        public static final Gains GAINS_VISION_X = new Gains(0.07, 0.03, 0.0, 0.0, 50);
        public static final Gains GAINS_VISION_Y = new Gains(0.085, 0.03, 0.0, 0.0, 50);

        public static final double VISION_POS_TOLLERANCE = 0.5;

        public static final double ALIGNED_GRID_APRIL_X = -12.0;
        public static final double ALIGNED_GRID_APRIL_Y = -3.0;
        public static final double ALIGNED_GRID_APRIL_AREA = 3.7;

        public static final double ALIGNED_SUBSTATION_APRIL_X = -18.3;
        public static final double ALIGNED_SUBSTATION_APRIL_Y = -16.3;
        public static final double ALIGNED_SUBSTATION_APRIL_AREA = 6.0;

        public static final double ALIGNED_LEFT_CONE_X = -18.3;
        public static final double ALIGNED_LEFT_CONE_Y = -16.3;
        public static final double ALIGNED_LEFT_CONE_AREA = 6.0;

        public static final double ALIGNED_RIGHT_CONE_X = -18.3;
        public static final double ALIGNED_RIGHT_CONE_Y = -16.3;
        public static final double ALIGNED_RIGHT_CONE_AREA = 6.0;

        public static final double SETPOINT_DIS_FROM_MID_CONE = 24;
        public static final double SETPOINT_DIS_FROM_TOP_CONE = 40;

        public static final double SETPOINT_DIS_FROM_GRID_APRIL = 14.062222;
        public static final double SETPOINT_DIS_FROM_SUBSTATION_APRIL = 5;
        // height of vision tape center in inches
        public static final double HEIGHT_CONE_NODE_TAP = 24.125;
        public static final double HEIGHT_GRID_APRIL = 18.25;
        public static final double HEIGHT_SUBSTATION_APRIL = 27.375;

        public static final Transform3d CAMERA_TO_ROBOT = new Transform3d(new Translation3d(0.0, 0.0, 0.0),
                new Rotation3d(0.0, 0.0, 0.0));
    }
}
