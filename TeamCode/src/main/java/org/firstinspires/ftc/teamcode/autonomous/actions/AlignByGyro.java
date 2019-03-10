package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.main.Hardware;

// Class to move forward or turn
public class AlignByGyro extends CoreAction {

    private Orientation firstAngle;

    public AlignByGyro(int nextPos, Orientation firstAngle) {

        this.firstAngle = firstAngle;
        this.nextPos = nextPos;

    }



    @Override
    public void actionInit(Hardware robot, ElapsedTime runtime, Telemetry telemetry) {
        this.robot = robot;
        this.runtime = runtime;
        this.telemetry = telemetry;


        // Turn On RUN_WITHOUT_ENCODER
        robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("firstangle", firstAngle.secondAngle);
        telemetry.addData("curentangle", robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle);
        telemetry.update();


    }

    @Override
    public int run() {
        telemetry.addData("firstangle", firstAngle.secondAngle);
        telemetry.addData("curentangle", robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle);
        telemetry.update();



        if (firstAngle.secondAngle < robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle) {

            while (firstAngle.secondAngle < robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle) {


                robot.leftFrontDrive.setPower(-.5);
                robot.leftBackDrive.setPower(-.5);
                robot.rightFrontDrive.setPower(.5);
                robot.rightBackDrive.setPower(.5);

                telemetry.addData("firstangle", firstAngle.secondAngle);
                telemetry.addData("curentangle", robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle);
                telemetry.update();
            }


        }

        if (firstAngle.secondAngle > robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle) {

            while (firstAngle.secondAngle > robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle) {


                robot.leftFrontDrive.setPower(.5);
                robot.leftBackDrive.setPower(.5);
                robot.rightFrontDrive.setPower(-.5);
                robot.rightBackDrive.setPower(-.5);

                telemetry.addData("firstangle", firstAngle.secondAngle);
                telemetry.addData("curentangle", robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle);
                telemetry.update();
            }


        }



        telemetry.addData("firstangle", firstAngle.firstAngle);
        telemetry.addData("curentangle", robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
        telemetry.update();



        return nextPos;
    }

    @Override
    public void actionEnd() {

        // Set power to 0
        robot.leftFrontDrive.setPower(0);
        robot.rightFrontDrive.setPower(0);
        robot.leftBackDrive.setPower(0);
        robot.rightBackDrive.setPower(0);

        robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
