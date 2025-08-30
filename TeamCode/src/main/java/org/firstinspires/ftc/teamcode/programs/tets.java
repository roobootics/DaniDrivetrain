package org.firstinspires.ftc.teamcode.programs;

import static org.firstinspires.ftc.teamcode.base.Commands.executor;
import static org.firstinspires.ftc.teamcode.pedroPathing.Pedro.follower;
import static org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain.leftFront;
import static org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain.leftRear;
import static org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain.rightFront;
import static org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain.rightRear;

import org.firstinspires.ftc.teamcode.base.Commands.*;
import org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain;
import org.firstinspires.ftc.teamcode.pedroPathing.Pedro.*;

import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Pedro;
import org.firstinspires.ftc.teamcode.base.Components.BotMotor;
import org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain;

@TeleOp
public class tets extends LinearOpMode {
    @Override
    public void runOpMode(){
        DaniDrivetrain.init(hardwareMap,telemetry);
        Pedro.setStartingPose(new Pose(48,24,0));
        waitForStart();
        executor.setCommands(
            new SequentialCommand(
                    new PedroLinearCommand(72,12,45,false),
                    new PedroLinearChainCommand(false,
                            new Pose(96,24,0),
                            new Pose(48,24,-90)
                    ),
                    new PedroLinearTransformCommand(24,-12,90,false),
                    new PedroInstantLinearCommand(120,48,-45,false),
                    new SleepCommand(0.5),
                    new PedroInstantLinearCommand(48,24,0,false),
                    new PedroSleepUntilPose(63,29,0,2,5),
                    new InstantCommand(()->follower.setMaxPower(0.6)),
                    new SleepUntilTrue(()->!follower.isBusy()),
                    new InstantCommand(()->follower.setMaxPower(1.0)),
                    new RunResettingLoop(
                            new PressTrigger(
                                    new IfThen(
                                            ()->gamepad1.a,
                                            new PedroLinearCommand(48,24,0,false)
                                    )
                            ),
                            new ConditionalCommand(
                                    new IfThen(
                                            ()->!follower.isBusy(),
                                            new RobotCentricMecanumCommand(new BotMotor[]{leftFront,leftRear,rightFront,rightRear},()-> (double) gamepad1.left_stick_x,()-> (double) gamepad1.left_stick_y,()-> (double) gamepad1.right_stick_x)
                                    )
                            )

                    )
            ),
            Pedro.updateCommand()
        );
        executor.runLoop(this::opModeIsActive);
    }
}
