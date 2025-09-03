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

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Commands.ConditionalCommand;
import org.firstinspires.ftc.teamcode.base.Commands.IfThen;
import org.firstinspires.ftc.teamcode.base.Commands.InstantCommand;
import org.firstinspires.ftc.teamcode.base.Commands.ParallelCommand;
import org.firstinspires.ftc.teamcode.base.Commands.PressTrigger;
import org.firstinspires.ftc.teamcode.base.Commands.RobotCentricMecanumCommand;
import org.firstinspires.ftc.teamcode.base.Commands.RunResettingLoop;
import org.firstinspires.ftc.teamcode.base.Commands.SequentialCommand;
import org.firstinspires.ftc.teamcode.base.Commands.SleepCommand;
import org.firstinspires.ftc.teamcode.base.Commands.SleepUntilTrue;
import org.firstinspires.ftc.teamcode.base.Components;
import org.firstinspires.ftc.teamcode.base.Components.BotMotor;
import org.firstinspires.ftc.teamcode.pedroPathing.Pedro;
import org.firstinspires.ftc.teamcode.pedroPathing.Pedro.PedroCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.Pedro.PedroInstantLinearCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.Pedro.PedroLinearChainCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.Pedro.PedroLinearCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.Pedro.PedroLinearTransformCommand;
import org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain;

@TeleOp
public class othertets extends LinearOpMode {
    double time;
    @Override
    public void runOpMode(){
        Components.initialize(hardwareMap,telemetry,new DaniDrivetrain(),true);
        PedroCommand path = new PedroCommand((PathBuilder b)->
                b.addPath(new BezierLine(follower::getPose,new Pose(96,24,0)))
                .setLinearHeadingInterpolation(0,0),
                true
        );
        path.buildPath();
        waitForStart();
        executor.setCommands(
                path,
                Pedro.updateCommand()
        );
        executor.setWriteToTelemetry(()->{
            telemetryAddData("busy",follower.isBusy());
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
