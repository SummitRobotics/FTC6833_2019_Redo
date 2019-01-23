package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

// Class to move forward or turn
public class LegControl extends CoreAction {

    private double frontSpeed, backSpeed;
    private int frontTicks, backTicks;
    private int nextPos, frontTarget, backTarget;


    public LegControl(double frontPosition, double backPosition, double speed, int nextPos) {

        this.frontSpeed = speed;
        this.backSpeed = speed;
        this.nextPos = nextPos;

        this.frontTicks = (int) (frontPosition * robot.LEG_COUNTS_PER_RADIAN);
        this.backTicks = (int) (backPosition * robot.LEG_COUNTS_PER_RADIAN);
    }

    @Override
    public void actionInit(HardwareMap hardwareMap, Telemetry telemetry) {

        robot.init(hardwareMap);

        // Prepare motors for encoder movement
        frontTarget = frontTicks - robot.frontLeg.getCurrentPosition();
        backTarget = backTicks - robot.backLeg.getCurrentPosition();

        if ((frontTarget > 0 && frontSpeed < 0) || (frontTarget < 0 && frontSpeed > 0)) {
            frontSpeed *= -1;
        }

        if ((backTarget > 0 && backSpeed < 0) || (backTarget < 0 && backSpeed > 0)) {
            backSpeed *= -1;
        }

        robot.frontLeg.setTargetPosition(frontTarget);
        robot.backLeg.setTargetPosition(backTarget);

        // Turn On RUN_TO_POSITION
        robot.frontLeg.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeg.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public int run() {
        // Set motor power until finished
        if (robot.frontLeg.isBusy() && robot.backLeg.isBusy()) {
            robot.frontLeg.setPower(frontSpeed);
            robot.backLeg.setPower(backSpeed);
            return 0;

        } else if (robot.frontLeg.isBusy()) {
            robot.frontLeg.setPower(frontSpeed);
            return 0;

        } else if (robot.backLeg.isBusy()) {
            robot.backLeg.setPower(backSpeed);
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
