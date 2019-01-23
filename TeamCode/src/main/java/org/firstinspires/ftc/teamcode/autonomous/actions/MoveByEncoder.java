package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

// Class to move forward or turn
public class MoveByEncoder extends CoreAction {

    private double leftSpeed, rightSpeed;
    private int leftTicks, rightTicks;
    private int nextPos, leftTarget, rightTarget;

    // Direction variables
    public static final int FORWARD = 1,  TURN = -1;

    public MoveByEncoder(double distance, double speed, int nextPos) {

        this.leftSpeed = speed;
        this.rightSpeed = speed;
        this.nextPos = nextPos;
        this.leftTicks = (int) (distance * robot.DRIVE_COUNTS_PER_INCH);
        this.rightTicks = (int) (distance * robot.DRIVE_COUNTS_PER_INCH);
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
        this.leftTicks = (int) (leftDistance * robot.DRIVE_COUNTS_PER_INCH);
        this.rightTicks = (int) (rightDistance * robot.DRIVE_COUNTS_PER_INCH);
    }

    public MoveByEncoder(double leftDistance, double rightDistance, double leftSpeed, double rightSpeed, int nextPos) {

        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.nextPos = nextPos;
        this.leftTicks = (int) (leftDistance * robot.DRIVE_COUNTS_PER_INCH);
        this.rightTicks = (int) (rightDistance * robot.DRIVE_COUNTS_PER_INCH);
    }

    @Override
    public void actionInit(HardwareMap hardwareMap, Telemetry telemetry) {

        robot.init(hardwareMap);

        // Prepare motors for encoder movement
        leftTarget = robot.leftFrontDrive.getCurrentPosition() + leftTicks;
        rightTarget = robot.rightBackDrive.getCurrentPosition() + rightTicks;

        robot.leftFrontDrive.setTargetPosition(leftTarget);
        robot.rightBackDrive.setTargetPosition(rightTarget);

        // Turn On RUN_TO_POSITION
        robot.leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public int run() {
        // Set motor power until finished
        if (robot.leftFrontDrive.isBusy() && robot.rightFrontDrive.isBusy()) {
            robot.leftFrontDrive.setPower(leftSpeed);
            robot.leftBackDrive.setPower(leftSpeed);
            robot.rightFrontDrive.setPower(rightSpeed);
            robot.rightBackDrive.setPower(rightSpeed);
            return 0;

        } else if (robot.leftFrontDrive.isBusy()) {
            robot.leftFrontDrive.setPower(leftSpeed);
            robot.leftBackDrive.setPower(leftSpeed);
            return 0;

        } else if (robot.rightFrontDrive.isBusy()) {
            robot.rightFrontDrive.setPower(rightSpeed);
            robot.rightBackDrive.setPower(rightSpeed);
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
