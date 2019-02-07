package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.main.Hardware;

// Class to move forward or turn
public class MoveByEncoder extends CoreAction {

    private double leftSpeed, rightSpeed, leftDistance, rightDistance;
    private int leftFrontTarget, rightFrontTarget, leftBackTarget, rightBackTarget;

    public MoveByEncoder(double distance, double speed, int nextPos) {

        this.leftSpeed = speed;
        this.rightSpeed = speed;
        this.nextPos = nextPos;
        this.leftDistance = distance;
        this.rightDistance = distance;
    }

    public MoveByEncoder(double leftDistance, double rightDistance, double speed, int nextPos) {

        if (leftDistance > rightDistance) {
            this.leftSpeed = speed;
            this.rightSpeed = speed * (rightDistance / leftDistance);
        } else {
            this.rightSpeed = speed;
            this.leftSpeed = speed * (leftDistance / rightDistance);
        }

        this.nextPos = nextPos;
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
    }

    public MoveByEncoder(double leftDistance, double rightDistance, double leftSpeed, double rightSpeed, int nextPos) {

        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.nextPos = nextPos;
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
    }

    @Override
    public void actionInit(Hardware robot, ElapsedTime runtime, Telemetry telemetry) {
        this.robot = robot;
        this.runtime = runtime;
        this.telemetry = telemetry;

        // Prepare motors for encoder movement
        leftFrontTarget  = robot.leftFrontDrive .getCurrentPosition()+(int)(leftDistance  * robot.DRIVE_COUNTS_PER_INCH);
        rightFrontTarget = robot.rightFrontDrive.getCurrentPosition()+(int)(rightDistance * robot.DRIVE_COUNTS_PER_INCH);
        leftBackTarget  = robot.leftBackDrive .getCurrentPosition()+(int)(leftDistance  * robot.DRIVE_COUNTS_PER_INCH);
        rightBackTarget = robot.rightBackDrive.getCurrentPosition()+(int)(rightDistance * robot.DRIVE_COUNTS_PER_INCH);


        // Turn On RUN_TO_POSITION
        robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set target positions
        robot.leftFrontDrive.setTargetPosition(leftFrontTarget);
        robot.rightFrontDrive.setTargetPosition(rightFrontTarget);
        robot.leftBackDrive.setTargetPosition(leftBackTarget);
        robot.rightBackDrive.setTargetPosition(rightBackTarget);
    }

    @Override
    public int run() {
        // Set motor power until finished
        if (robot.leftFrontDrive.isBusy() || robot.rightFrontDrive.isBusy()) {
            if (!robot.leftFrontDrive.isBusy()) {
                leftSpeed = 0;
            }

            if (!robot.rightFrontDrive.isBusy()) {
                rightSpeed = 0;
            }

            if (leftSpeed == 0 && rightSpeed == 0) {
                return nextPos;
            }

            robot.leftFrontDrive.setPower(leftSpeed);
            robot.rightFrontDrive.setPower(rightSpeed);
            robot.leftBackDrive.setPower(leftSpeed);
            robot.rightBackDrive.setPower(rightSpeed);
            telemetry.addData("MoveByEncoder At", "Left: (%.2f), Right: (%.2f)", (double) robot.leftFrontDrive.getCurrentPosition(), (double) robot.rightFrontDrive.getCurrentPosition());
            telemetry.addData("MoveByEncoder To", "Left: (%.2f), Right: (%.2f)", (double) leftFrontTarget, (double) rightFrontTarget);
            telemetry.update();
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
        robot.leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
