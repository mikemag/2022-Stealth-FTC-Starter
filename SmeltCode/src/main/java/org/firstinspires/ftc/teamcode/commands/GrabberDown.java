package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class GrabberDown extends CommandBase {
    final GrabberSubsystem grabber;

    public GrabberDown(GrabberSubsystem grabber) {
        this.grabber = grabber;
        addRequirements(grabber);
    }

    @Override
    public void execute(){
        grabber.setLiftPos(grabber.getLiftPos() + 0.01);
    }


}