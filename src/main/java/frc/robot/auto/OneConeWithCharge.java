// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.util.GamePiece;
import frc.robot.util.GamePiece.GamePieceType;

public class OneConeWithCharge extends SequentialCommandGroup {

    public OneConeWithCharge(DriveSubsystem drive) {

        PathPlannerTrajectory path1 = PathPlanner.loadPath("ConeWithCharge", 2.0, 1.0);
        addRequirements(drive);
        addCommands(
            new SequentialCommandGroup(
                Commands.runOnce(() -> GamePiece.setGamePiece(GamePieceType.Cone)),
                new WaitCommand(0.4),
                drive.followTrajectoryCommand(path1, true),
                new RunCommand(drive::AutoBalance, drive).withTimeout(6))
        );
    }
}
