// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.limelight;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.LimelightConstants;
import frc.robot.Constants.SwerveConstants;
import frc.robot.subsystems.drive.DriveSubsystem;

public class AlignWithGridApril extends CommandBase {
    /** Creates a new AlignWithNodeApril. */
    LimelightSubsystem m_limelight;
    DriveSubsystem m_drive;
    PIDController m_pidControllerY, m_pidControllerX;
    boolean m_end;
    double xTrans = 0.0;
    double yTrans = 0.0;
    double count = 0;
    private PIDController m_thetaController;

    public AlignWithGridApril(LimelightSubsystem limelight, DriveSubsystem drive) {
        m_drive = drive;
        m_limelight = limelight;
        addRequirements(m_drive, m_limelight);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        m_pidControllerY = new PIDController(LimelightConstants.GAINS_VISION_Y.kP, LimelightConstants.GAINS_VISION_Y.kI,
                LimelightConstants.GAINS_VISION_Y.kD);
        m_pidControllerY.setIntegratorRange(0.0, LimelightConstants.GAINS_VISION_Y.kIzone);
        m_pidControllerY.setTolerance(LimelightConstants.VISION_POS_TOLLERANCE);

        m_pidControllerX = new PIDController(LimelightConstants.GAINS_VISION_X.kP, LimelightConstants.GAINS_VISION_X.kI,
                LimelightConstants.GAINS_VISION_X.kD);
        m_pidControllerX.setIntegratorRange(0.0, LimelightConstants.GAINS_VISION_X.kIzone);
        m_pidControllerX.setTolerance(LimelightConstants.VISION_POS_TOLLERANCE);

        m_thetaController = new PIDController(SwerveConstants.GAINS_ANGLE_SNAP.kP * 2,
                SwerveConstants.GAINS_ANGLE_SNAP.kI, SwerveConstants.GAINS_ANGLE_SNAP.kD);

        m_thetaController.enableContinuousInput(-180, 180);

        m_end = false;

        m_limelight.setVisionModeOn();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (!m_limelight.hasTargetFront()) {
            m_end = true;
            System.out.println("No target");
        }

        m_thetaController.setSetpoint(180.0);

        double rotationVal = m_thetaController
                .calculate((MathUtil.inputModulus(m_drive.getYaw().getDegrees(), -180, 180)), 180.0);
        rotationVal = MathUtil.clamp(rotationVal, -SwerveConstants.MAX_ANGULAR_VELOCITY * 0.4,
                SwerveConstants.MAX_ANGULAR_VELOCITY * 0.4);

        m_pidControllerX.setSetpoint(LimelightConstants.ALIGNED_GRID_APRIL_X);
        xTrans = m_pidControllerX.calculate(m_limelight.getXFront());
        xTrans = MathUtil.clamp(xTrans, -0.5, 0.5);

        m_pidControllerY.setSetpoint(LimelightConstants.ALIGNED_GRID_APRIL_AREA);
        yTrans = m_pidControllerY.calculate(m_limelight.getAreaFront());
        yTrans = MathUtil.clamp(yTrans, -0.5, 0.5);

        m_drive.drive(new Translation2d(-yTrans, -xTrans), rotationVal, true, true);

        if (Constants.tuningMode) {
            SmartDashboard.putNumber("X Error", m_pidControllerX.getPositionError());
            SmartDashboard.putNumber("Y Error", m_pidControllerY.getPositionError());
        }

        if (m_pidControllerX.atSetpoint() && m_pidControllerY.atSetpoint() && count > 50) {
            m_end = true;
        } else {
            m_end = false;
        }

        count++;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drive.stopDrive();
        m_limelight.setVisionModeOff();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_end;
    }
}
