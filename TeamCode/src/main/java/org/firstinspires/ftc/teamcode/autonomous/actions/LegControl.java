package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.main.Hardware;

// Class to move forward or turn
public class LegControl extends CoreAction {

    private double frontSpeed, backSpeed, frontPos, backPos;
    private int frontTarget, backTarget;


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
        frontTarget = (int) (robot.frontLeg.getCurrentPosition() - (frontPos * robot.LEG_COUNTS_PER_ROT));
        backTarget  = (int) (robot.backLeg .getCurrentPosition() - (backPos  * robot.LEG_COUNTS_PER_ROT));

        // Turn On RUN_TO_POSITION
        robot.frontLeg.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeg.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontLeg.setTargetPosition(frontTarget);
        robot.backLeg.setTargetPosition(backTarget);
    }

    @Override
    public int run() {
        // Set motor power until finished
        if (robot.frontLeg.isBusy() || robot.backLeg.isBusy()) {
            if (!robot.frontLeg.isBusy()) {
                frontSpeed = 0;
            }

            if (!robot.backLeg.isBusy()) {
                backSpeed = 0;
            }

            if (frontSpeed == 0 && backSpeed == 0) {
                return nextPos;
            }

            robot.backLeg.setPower(backSpeed);
            robot.frontLeg.setPower(frontSpeed);
            telemetry.addData("LegsControl Status", "Front Done: " + !robot.frontLeg.isBusy() + ", Back Done: " + !robot.backLeg.isBusy());
            telemetry.addData("LegsControl At", "Front: (%.2f), Back: (%.2f)", (double)robot.frontLeg.getCurrentPosition(), (double)robot.backLeg.getCurrentPosition());
            telemetry.addData("LegsControl To", "Front: (%.2f), Back: (%.2f)", (double)frontTarget, (double)backTarget);
            telemetry.update();

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
