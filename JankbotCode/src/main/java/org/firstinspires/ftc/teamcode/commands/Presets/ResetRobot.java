package org.firstinspires.ftc.teamcode.commands.Presets;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.commands.ResetElevator;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;

public class ResetRobot extends ParallelCommandGroup {

    public ResetRobot(ExtenderSubsystem extender, GrabberSubsystem grabber) {

        addCommands(
                new SequentialCommandGroup(
                        new InstantCommand(() -> grabber.closeGripper()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> grabber.setArmPositionUp()),
                                new InstantCommand(() -> grabber.setRotationPositionUp())
                        ),
                        new ResetElevator(extender).withTimeout(3000),
                        new InstantCommand(() -> grabber.toggleOpen()),
                new InstantCommand(() -> grabber.setArmPositionDown()),
                new InstantCommand(() -> grabber.setRotationPositionDown())


                )
        );

        addRequirements(extender, grabber);
    }
}