package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// Class to move forward or turn
public class LegControl extends CoreAction {

    private double frontSpeed, backSpeed, frontTime, backTime, start;
    private ElapsedTime runtime;


    public LegControl(double frontTime, double backTime, double frontSpeed, double backSpeed, int nextPos) {

        this.frontSpeed = frontSpeed;
        this.backSpeed = backSpeed;
        this.nextPos = nextPos;

        this.frontTime = frontTime;
        this.backTime = backTime;
    }

    public LegControl(double speed, int nextPos) {
        this.frontSpeed = speed;
        this.backSpeed = speed;
        this.nextPos = nextPos;

        frontTime = -1;
        backTime = -1;

        if (frontSpeed < 0) {
            frontSpeed *= -1;
        }

        if (backSpeed > 0) {
            backSpeed *= -1;
        }
    }

    @Override
    public void actionInit(HardwareMap hardwareMap, ElapsedTime runtime) {
        robot.init(hardwareMap);
        this.runtime = runtime;
        start = runtime.seconds();
    }

    @Override
    public int run() {
        // Set motor power until finished
        if (frontTime >= 0 && backTime >= 0) {
            if (runtime.seconds() - start < frontTime || runtime.seconds() - start < backTime) {
                if (runtime.seconds() - start > frontTime) {
                    frontSpeed = 0;
                }

                if (runtime.seconds() - start > backTime) {
                    backSpeed = 0;
                }

                robot.backLeg.setPower(backSpeed);
                robot.frontLeg.setPower(frontSpeed);
                return 0;

            }
            return nextPos;

        } else {
            if (robot.frontLimit.getState() || robot.backLimit.getState()) {
                if (!robot.frontLimit.getState()) {
                    frontSpeed = 0;
                }

                if (!robot.backLimit.getState()) {
                    backSpeed = 0;
                }

                robot.frontLeg.setPower(frontSpeed);
                robot.backLeg.setPower(backSpeed);
                return 0;

            }
            return nextPos;
        }
    }

    @Override
    public void actionEnd() {

        // Set power to 0
        robot.frontLeg.setPower(0);
        robot.backLeg.setPower(0);
    }
}
