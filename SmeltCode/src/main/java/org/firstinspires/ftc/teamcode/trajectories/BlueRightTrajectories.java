package org.firstinspires.ftc.teamcode.trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

public class BlueRightTrajectories {
    //goes to pole and scores first cone
    public static Trajectory forward1 = TrajectoryBuilder.buildTrajectory(new Pose2d (-40.5, 62, Math.toRadians(270)))
            .splineTo(new Vector2d(-36.7, 45), Math.toRadians(290))
            .splineTo(new Vector2d(-30.5, 30), Math.toRadians(305))
            .build();

    //drives to cone stack to pick up first stack cone
    public static TrajectorySequence back1 = TrajectorySequenceBuilder.buildTrajectorySequence(forward1.end())
            .back(4.5)
            .splineTo(new Vector2d(-35.3, 10), Math.toRadians(0))
            .forward(24)
            .build();
    //drives to pole to score first stack cone
    public static TrajectorySequence back2 = TrajectorySequenceBuilder.buildTrajectorySequence(back1.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(-34, 14, Math.toRadians(225)), 0)
            .build();
    //gets second cone from stack
    public static TrajectorySequence getCone1 = TrajectorySequenceBuilder.buildTrajectorySequence(back2.end())
            .forward(3)
            .splineToSplineHeading(new Pose2d(-60, 9.5,  Math.toRadians(180)), Math.toRadians(180))
            .build();
    public static TrajectorySequence scoreCone1 = TrajectorySequenceBuilder.buildTrajectorySequence(getCone1.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(-34, 14, Math.toRadians(225)), Math.toRadians(45))
            .build();
    public static TrajectorySequence getCone2 = TrajectorySequenceBuilder.buildTrajectorySequence(scoreCone1.end())
            .splineToSplineHeading(new Pose2d(-60.2, 9.5,  Math.toRadians(180)), Math.toRadians(180))
            .build();
    public static TrajectorySequence scoreCone2 = TrajectorySequenceBuilder.buildTrajectorySequence(getCone2.end())
            .back(5)
            .splineToSplineHeading(new Pose2d(-34, 14, Math.toRadians(225)), Math.toRadians(45))
            .build();
    public static TrajectorySequence park1 = TrajectorySequenceBuilder.buildTrajectorySequence(scoreCone2.end())
            .lineToSplineHeading(new Pose2d(-35, 11, Math.toRadians(270)))
            .strafeLeft(24)
            .build();
    public static TrajectorySequence park2 = TrajectorySequenceBuilder.buildTrajectorySequence(scoreCone2.end())
            .lineToSplineHeading(new Pose2d(-35, 11, Math.toRadians(270)))
            .build();
    public static TrajectorySequence park3 = TrajectorySequenceBuilder.buildTrajectorySequence(scoreCone2.end())
            .lineToSplineHeading(new Pose2d(-35, 11, Math.toRadians(270)))
            .lineToSplineHeading(new Pose2d(-58, 11, Math.toRadians(180)))
            .build();



}