package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// Class to move forward or turn
public class MoveByEncoder extends CoreAction {

    private double leftSpeed, rightSpeed, leftTime, rightTime, start;
    private int nextPos, leftTarget, rightTarget;
    private ElapsedTime runtime;

    public MoveByEncoder(double time, double speed, int nextPos) {

        this.leftSpeed = speed;
        this.rightSpeed = speed;
        this.nextPos = nextPos;
        this.leftTime = time;
        this.rightTime = time;
    }

    public MoveByEncoder(double leftTime, double rightTime, double speed, int nextPos) {

        if (leftTime > rightTime) {
            this.leftSpeed = speed;
            this.rightSpeed = speed * (rightTime / leftTime);
        } else {
            this.rightSpeed = speed;
            this.leftSpeed = speed * (leftTime / rightTime);
        }

        this.nextPos = nextPos;
        this.leftTime = leftTime;
        this.rightTime = rightTime;
    }

    public MoveByEncoder(double leftTime, double rightTime, double leftSpeed, double rightSpeed, int nextPos) {

        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.nextPos = nextPos;
        this.leftTime = leftTime;
        this.rightTime = rightTime;
    }

    @Override
    public void actionInit(HardwareMap hardwareMap, ElapsedTime runtime) {

        robot.init(hardwareMap);
        this.runtime = runtime;
        this.start = runtime.seconds();
    }

    @Override
    public int run() {
        // Set motor power until finished
        if (runtime.seconds() - start < leftTime || runtime.seconds() - start < rightTime) {
            if (runtime.seconds() - start < leftTime) {
                robot.leftFrontDrive.setPower(leftSpeed);
                robot.leftBackDrive.setPower(leftSpeed);
            }

            if (runtime.seconds() - start < rightTime) {
                robot.rightFrontDrive.setPower(rightSpeed);
                robot.rightBackDrive.setPower(rightSpeed);
            }
            return 0;

        }
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
    }
}
