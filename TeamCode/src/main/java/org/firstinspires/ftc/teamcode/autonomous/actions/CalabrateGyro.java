package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.main.Hardware;

import static android.os.SystemClock.sleep;

// Class to move forward or turn
public class CalabrateGyro extends CoreAction {

    private Orientation firstAngle;

    public CalabrateGyro(int nextPos, Orientation firstAngle) {


        this.nextPos = nextPos;
        this.firstAngle = firstAngle;


    }


    @Override
    public void actionInit(Hardware robot, ElapsedTime runtime, Telemetry telemetry) {
        this.robot = robot;
        this.runtime = runtime;
        this.telemetry = telemetry;

        // State used for updating telemetry
        Orientation angles;
        Acceleration gravity;


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".



        robot.gyro.initialize(parameters);

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        sleep(200);




        //inshlises drive motors for turning


        telemetry.addData("Finished", false);
        telemetry.update();



    }

    @Override
    public int run() {

        firstAngle = robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        return nextPos;
    }

    @Override
    public void actionEnd() {


    }
}
