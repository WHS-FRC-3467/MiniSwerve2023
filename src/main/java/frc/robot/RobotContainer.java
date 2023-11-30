// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.auto.OneConeWithCharge;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.drive.TeleopSwerve;
import frc.robot.subsystems.limelight.LimelightSubsystem;

public class RobotContainer {

    private final CommandXboxController m_driverController = new CommandXboxController(0);

    private final DriveSubsystem m_drive = new DriveSubsystem();
    private final LimelightSubsystem m_limelight = new LimelightSubsystem();
    private SendableChooser<Command> m_autoChooser = new SendableChooser<>();

    public RobotContainer() {
        if (Constants.tuningMode) {
            DriverStation.silenceJoystickConnectionWarning(true);
        } else {
            DriverStation.silenceJoystickConnectionWarning(false);
        }

        m_autoChooser.addOption("Charge and no mobility", new OneConeWithCharge(m_drive));
        m_autoChooser.addOption("No Auto", null);
        SmartDashboard.putData("Auto", m_autoChooser);

        configureBindings();

        m_drive.setDefaultCommand(new TeleopSwerve(m_drive,
                () -> -m_driverController.getLeftY(),
                () -> -m_driverController.getLeftX(),
                () -> -m_driverController.getRightX(),
                m_driverController.leftStick(),
                m_driverController.leftBumper(),
                m_driverController.rightStick(),
                m_driverController.y(),
                m_driverController.b(),
                m_driverController.a(),
                m_driverController.x()));

        SmartDashboard.putData("Reset Modules", new InstantCommand(m_drive::resetModulesToAbsolute));
    }

    private void configureBindings() {

        // Driver Controls
        m_driverController.povUp().onTrue(Commands.runOnce(m_drive::reset, m_drive));
        m_driverController.start()
                .whileTrue(Commands.run(m_drive::AutoBalance, m_drive).andThen(m_drive::stopDrive, m_drive));

    }

    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return m_autoChooser.getSelected();
    }
}
