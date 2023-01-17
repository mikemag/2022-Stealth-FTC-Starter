package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.AlignToTapeCommand;
import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesCommand;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectory;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectorySequence;
import org.firstinspires.ftc.teamcode.commands.GripperCloseCommand;
import org.firstinspires.ftc.teamcode.commands.GripperOpenCommand;
import org.firstinspires.ftc.teamcode.commands.MoveElevatorPercentage;
import org.firstinspires.ftc.teamcode.commands.StrafeForInches;
import org.firstinspires.ftc.teamcode.commands.TurnToDegrees;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.pipelines.SleeveDetection;
import org.firstinspires.ftc.teamcode.trajectories.LeftAutoTrajectories;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.commands.EndOpModeCommand;
import org.stealthrobotics.library.commands.SaveAutoHeadingCommand;
import org.stealthrobotics.library.opmodes.StealthOpMode;

/**
 * This is a sample auto command that drives forward a bit, spins a wheel, then drives right a bit.
 * <p>
 * The @Autonomous annotation says that this is an autonomous opmode. The name and group show up
 * on the driver station so you can select the right mode. If you have "blue" or "red" in either
 * name then your Alliance color will be set correctly for use throughout.
 */
@SuppressWarnings("unused")
@Autonomous(name = "RoadrunnerLeftAuto", preselectTeleOp = "BLUE | Tele-Op")
public class RoadrunnerLeftAuto extends StealthOpMode {

    // Subsystems
    SampleMecanumDrive mecanumDrive;
    ElevatorSubsystem elevator;
    GripperSubsystem gripper;
    CameraSubsystem camera;
    ColorSensorSubsystem colorSensor;
    DriveSubsystem drive;


    /**
     * Executed when you init the selected opmode. This is where you setup your hardware.
     */
    @Override
    public void initialize() {
        mecanumDrive = new SampleMecanumDrive(hardwareMap);
        drive = new DriveSubsystem(mecanumDrive, hardwareMap);
        elevator = new ElevatorSubsystem(hardwareMap);
        gripper = new GripperSubsystem(hardwareMap);
        camera = new CameraSubsystem(hardwareMap);
        colorSensor = new ColorSensorSubsystem(hardwareMap);
        register(drive, elevator, gripper, camera, colorSensor);

        camera.setLeftSide();
    }

    @Override
    public void whileWaitingToStart() {
        CommandScheduler.getInstance().run();
    }

    /**
     * This is where we create the one command we want to run in our autonomous opmode.
     * <p>
     * You create a SequentialCommandGroup, which is a list of commands that will run one after
     * another. This can be as simple or as complex as you'd like, and there are many ways to
     * group your commands in sequence, parallel, or conditionally.
     * <p>
     * Check out these links for more details:
     * - https://docs.ftclib.org/ftclib/command-base/command-system/convenience-commands
     * - https://docs.ftclib.org/ftclib/command-base/command-system/command-groups
     */
    @Override
    public Command getAutoCommand() {
        AutoToTeleStorage.finalAutoHeading = 0;
        SleeveDetection.ParkingPosition position = SleeveDetection.ParkingPosition.LEFT;
        //IF THIS IS CHANGED, CHANGE IN LEFT AUTO TRAJECTORIES
        drive.setPoseEstimate(-31, -64.5, Math.toRadians(90));

        if (position == SleeveDetection.ParkingPosition.LEFT) {
            return new SequentialCommandGroup(
                    new GripperCloseCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.1),
//                    new InstantCommand(() -> drive.setPoseEstimate(LeftAutoTrajectories.startingPose.getX(), LeftAutoTrajectories.startingPose.getY(),LeftAutoTrajectories.startingPose.getHeading())),
                    new FollowTrajectory(mecanumDrive, LeftAutoTrajectories.trajectory1),
                    new FollowTrajectory(mecanumDrive, LeftAutoTrajectories.trajectory2),
                    new FollowTrajectory(mecanumDrive, LeftAutoTrajectories.trajectory3),
                    new MoveElevatorPercentage(elevator, 0.63),
                    new FollowTrajectory(mecanumDrive, LeftAutoTrajectories.trajectory4),
                    new MoveElevatorPercentage(elevator, 0.59),
                    new GripperOpenCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.64),
                    new ParallelCommandGroup(
                            new FollowTrajectory(mecanumDrive, LeftAutoTrajectories.trajectory5),
                            new MoveElevatorPercentage(elevator, 0)
                    ),
                    new FollowTrajectory(mecanumDrive, LeftAutoTrajectories.trajectory6),
                    new FollowTrajectory(mecanumDrive, LeftAutoTrajectories.trajectory7),
                    new FollowTrajectory(mecanumDrive, LeftAutoTrajectories.trajectory8),
                    new FollowTrajectory(mecanumDrive, LeftAutoTrajectories.trajectory9)


            );
        } else if (position == SleeveDetection.ParkingPosition.CENTER) {
            return new SequentialCommandGroup(

            );
        } else { // RIGHT
            return new SequentialCommandGroup(


            );
        }

    }
}
