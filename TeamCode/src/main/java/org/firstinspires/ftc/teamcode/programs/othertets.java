package org.firstinspires.ftc.teamcode.programs;

import static org.firstinspires.ftc.teamcode.base.Commands.executor;
import static org.firstinspires.ftc.teamcode.base.Components.telemetryAddData;
import static org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain.leftFront;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Commands;
import org.firstinspires.ftc.teamcode.base.Components;
import org.firstinspires.ftc.teamcode.robotconfigs.DaniDrivetrain;

@TeleOp
public class othertets extends LinearOpMode {
    public double averageVel;
    public double meanError;
    public double velocity;
    public long loop;
    @Override
    public void runOpMode() throws InterruptedException {
        Components.initialize(hardwareMap,telemetry,new DaniDrivetrain(),true);
        waitForStart();
        executor.setCommands(
                new Commands.RunResettingLoop(
                        leftFront.setVelocityCommand(500),
                        new Commands.ConditionalCommand(
                                new Commands.IfThen(
                                        ()->gamepad1.a,
                                        new Commands.InstantCommand(()->{
                                            loop+=1;
                                            double vel = leftFront.getVelocity();
                                            velocity=vel;
                                            double error = Math.abs(500-vel);
                                            averageVel *= (double) (loop - 1) /(loop);
                                            averageVel += (vel/(loop));
                                            meanError *= (double) (loop - 1) /(loop);
                                            meanError += (error/(loop));
                                        })
                                )

                        )

                )

        );
        executor.setWriteToTelemetry(
                ()->{
                    telemetryAddData("vel",velocity);
                    telemetryAddData("mean vel",averageVel);
                    telemetryAddData("mean error",meanError);
                }
        );
        executor.runLoop(this::opModeIsActive);
    }
}
