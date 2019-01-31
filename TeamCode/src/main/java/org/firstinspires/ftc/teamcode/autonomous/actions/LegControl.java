package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.main.Hardware;

// Class to move forward or turn
public class LegControl extends CoreAction {

    private double frontSpeed, backSpeed, frontPos, backPos;


    public LegControl(double frontPos, double backPos, double frontSpeed, double backSpeed, int nextPos) {

        this.frontSpeed = frontSpeed;
        this.backSpeed = backSpeed;
        this.nextPos = nextPos;

        this.frontPos = frontPos;
        this.backPos = backPos;
    }

    @Override
    public void actionInit(Hardware robot, ElapsedTime runtime, Telemetry telemetry) {
        this.robot = robot;
        this.runtime = runtime;
        this.telemetry = telemetry;

        // Prepare motors for encoder movement
        int frontTarget = (int)(frontPos * robot.LEG_COUNTS_PER_ROT) - robot.frontLeg.getCurrentPosition();
        int backTarget  = (int)(backPos  * robot.LEG_COUNTS_PER_ROT) - robot.backLeg .getCurrentPosition();

        if ((frontTarget > robot.frontLeg.getCurrentPosition() && frontSpeed < 0) ||
                (frontTarget < robot.frontLeg.getCurrentPosition() && frontSpeed > 0)) {
            frontSpeed *= -1;
        }

        if ((backTarget > robot.backLeg.getCurrentPosition() && backSpeed < 0) ||
                (backTarget < robot.backLeg.getCurrentPosition() && backSpeed > 0)) {
            backSpeed *= -1;
        }

        // Turn On RUN_TO_POSITION
        robot.frontLeg.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeg.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontLeg.setTargetPosition(frontTarget);
        robot.backLeg.setTargetPosition(backTarget);
    }

    @Override
    public int run() {
        // Set motor power until finished
        if ((robot.frontLeg.isBusy() || robot.backLeg.isBusy())
                && (!robot.atLimit(robot.frontLimit) || !robot.atLimit(robot.backLimit))) {
            if (!robot.frontLeg.isBusy() || robot.atLimit(robot.frontLimit)) {
                frontSpeed = 0;
            }

            if (!robot.backLeg.isBusy() || robot.atLimit(robot.backLimit)) {
                backSpeed = 0;
            }

            robot.backLeg.setPower(backSpeed);
            robot.frontLeg.setPower(frontSpeed);
            return 0;

        }
        return nextPos;
    }

    @Override
    public void actionEnd() {

        // Set power to 0
        robot.frontLeg.setPower(0);
        robot.backLeg.setPower(0);

        robot.frontLeg.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeg.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
