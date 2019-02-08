package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.main.Hardware;

// Class to move forward or turn
public class ArmControl extends CoreAction {

    private double speed, distance;
    private int target;


    public ArmControl(double distance, double speed, int nextPos) {

        this.speed = speed;
        this.nextPos = nextPos;
        this.distance = distance;
    }

    @Override
    public void actionInit(Hardware robot, ElapsedTime runtime, Telemetry telemetry) {
        this.robot = robot;
        this.runtime = runtime;
        this.telemetry = telemetry;

        // Prepare motors for encoder movement
        target = (int) (robot.armMotor.getCurrentPosition() - (distance * robot.ARM_COUNTS_PER_ROT));

        // Turn On RUN_TO_POSITION
        robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.armMotor.setTargetPosition(target);
    }

    @Override
    public int run() {
        // Set motor power until finished
        if (robot.armMotor.isBusy()) {
            robot.armMotor.setPower(speed);
            telemetry.addData("ArmControl At", "pos: (%.2f)", (double)robot.armMotor.getCurrentPosition());
            telemetry.addData("ArmControl To", "pos: (%.2f)", (double)target);
            telemetry.update();

            return 0;

        }
        return nextPos;
    }

    @Override
    public void actionEnd() {

        // Set power to 0
        robot.armMotor.setPower(0);

        robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
