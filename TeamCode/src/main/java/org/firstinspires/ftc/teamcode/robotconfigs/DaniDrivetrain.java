package org.firstinspires.ftc.teamcode.robotconfigs;

import static org.firstinspires.ftc.teamcode.base.Components.initialize;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.Components.*;
import org.firstinspires.ftc.teamcode.pedroPathing.Pedro;

public class DaniDrivetrain {
    public static BotMotor leftFront;
    public static BotMotor leftRear;
    public static BotMotor rightFront;
    public static BotMotor rightRear;

    public static void init(HardwareMap hardwareMap, Telemetry telemetry){
        initialize(hardwareMap,telemetry);
        Pedro.createFollower();
        leftFront=new BotMotor("leftFront",new String[]{"leftFront"},()->1.0,()->-1.0,new DcMotorSimple.Direction[]{DcMotorSimple.Direction.REVERSE});
        leftRear=new BotMotor("leftRear",new String[]{"leftRear"},()->1.0,()->-1.0,new DcMotorSimple.Direction[]{DcMotorSimple.Direction.REVERSE});
        rightFront=new BotMotor("rightFront",new String[]{"rightFront"},()->1.0,()->-1.0,new DcMotorSimple.Direction[]{DcMotorSimple.Direction.FORWARD});
        rightRear=new BotMotor("rightRear",new String[]{"rightRear"},()->1.0,()->-1.0,new DcMotorSimple.Direction[]{DcMotorSimple.Direction.FORWARD});
    }
}
