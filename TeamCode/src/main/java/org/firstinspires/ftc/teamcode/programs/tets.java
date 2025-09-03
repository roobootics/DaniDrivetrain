package org.firstinspires.ftc.teamcode.programs;

import static org.firstinspires.ftc.teamcode.base.Commands.executor;
import static org.firstinspires.ftc.teamcode.base.Components.telemetryAddData;
import static org.firstinspires.ftc.teamcode.base.Components.telemetryAddLine;
import static org.firstinspires.ftc.teamcode.base.Components.timer;
import static org.firstinspires.ftc.teamcode.pedroPathing.Pedro.follower;
import static org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain.leftFront;
import static org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain.leftRear;
import static org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain.rightFront;
import static org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain.rightRear;

import org.firstinspires.ftc.teamcode.base.Commands.*;
import org.firstinspires.ftc.teamcode.base.Components;
import org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain;
import org.firstinspires.ftc.teamcode.pedroPathing.Pedro.*;

import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Pedro;
import org.firstinspires.ftc.teamcode.base.Components.BotMotor;

@TeleOp
public class tets extends LinearOpMode {
    double time;
    @Override
    public void runOpMode(){
        Components.initialize(hardwareMap,telemetry,new DaniDrivetrain(),true);
        waitForStart();
        SequentialCommand sequence = new SequentialCommand(
                new PedroLinearCommand(72,24,45,false),
                new PedroLinearChainCommand(false,
                        new Pose(96,24,0),
                        new Pose(48,24,-90)
                ),
                new PedroLinearTransformCommand(24,-12,90,false),
                new PedroInstantLinearCommand(96,48,-45,false),
                new SleepCommand(0.5),
                new PedroInstantLinearCommand(48,24,0,false),
                new SleepUntilTrue(()->follower.getCurrentTValue()>0.7),
                new InstantCommand(()->follower.setMaxPower(0.6)),
                new SleepUntilTrue(()->!follower.isBusy()),
                new InstantCommand(()->follower.setMaxPower(1.0)),
                new PedroCommand(
                        (PathBuilder b)->
                                b.curveThrough(0,new Pose(72,48),new Pose(96,24))
                                .setLinearHeadingInterpolation(follower.getPose().getHeading(),45),
                        false
                ),
                new RunResettingLoop(
                        new InstantCommand(()->telemetryAddLine("teleop")),
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
                                ),
                                new IfThen(
                                        ()->true,
                                        new InstantCommand(()->{})
                                )
                        )

                )
        );
        executor.setCommands(
            sequence,
            Pedro.updateCommand()
        );
        executor.setWriteToTelemetry(()->{
            telemetryAddData("busy",follower.isBusy());
            telemetryAddData("action",sequence.getCurrentAction());
            telemetryAddData("x",follower.getPose().getX());
            telemetryAddData("y",follower.getPose().getY());
            telemetryAddData("heading",follower.getPose().getHeading());
            telemetryAddData("targetx",follower.getCurrentPath().getLastControlPoint().getX());
            telemetryAddData("targety",follower.getCurrentPath().getLastControlPoint().getY());
            telemetryAddData("targetheading",follower.getHeadingGoal(follower.getCurrentTValue()));
            telemetryAddData("drivevectorx",follower.getDriveVector().getXComponent());
            telemetryAddData("drivevectory",follower.getDriveVector().getYComponent());
            telemetryAddData("looptime",timer.time()-time);
            time=timer.time();
        });
        executor.runLoop(this::opModeIsActive);
    }
}
