package org.firstinspires.ftc.teamcode.programs;

import static org.firstinspires.ftc.teamcode.base.Commands.executor;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain;

@Autonomous
public class tets extends LinearOpMode {
    @Override
    public void runOpMode(){
        DaniDrivetrain.init(hardwareMap,telemetry);
        waitForStart();
        executor.setCommands(

        );
        executor.runLoop(this::opModeIsActive);
    }
}
