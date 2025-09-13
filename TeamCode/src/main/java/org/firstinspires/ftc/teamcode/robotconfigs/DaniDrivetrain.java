package org.firstinspires.ftc.teamcode.robotconfigs;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.base.Components.*;
import org.firstinspires.ftc.teamcode.pedroPathing.Pedro;
import java.util.Collections;

public class DaniDrivetrain implements RobotConfig {
    public static BotMotor leftFront;
    public static BotMotor leftRear;
    public static BotMotor rightFront;
    public static BotMotor rightRear;

    public void init(){
        Pedro.createFollower(new Pose(48,24,0));
        leftFront=new BotMotor("leftFront", Collections.singletonList(new DcMotorExData("leftFront", DcMotorSimple.Direction.REVERSE)),()->1.0,()->-1.0);
        leftRear=new BotMotor("leftRear",Collections.singletonList(new DcMotorExData("leftRear", DcMotorSimple.Direction.REVERSE)),()->1.0,()->-1.0);
        rightFront=new BotMotor("rightFront",Collections.singletonList(new DcMotorExData("rightFront", DcMotorSimple.Direction.FORWARD)),()->1.0,()->-1.0);
        rightRear=new BotMotor("rightRear",Collections.singletonList(new DcMotorExData("rightFront", DcMotorSimple.Direction.FORWARD)),()->1.0,()->-1.0);
    }
}
