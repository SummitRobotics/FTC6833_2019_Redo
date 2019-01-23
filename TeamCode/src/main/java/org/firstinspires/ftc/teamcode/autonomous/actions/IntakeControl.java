package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeControl extends CoreAction {

    int power;

    public IntakeControl(int power, int nextPos) {

        this.power = power;
        this.nextPos = nextPos;
    }

    @Override
    public void actionInit(HardwareMap hardwareMap, Telemetry telemetry) {
        robot.init(hardwareMap);
    }

    @Override
    public int run() {
        robot.frontIntake.setPower(power);
        robot.backIntake.setPower(-power);

        return nextPos;
    }

    @Override
    public void actionEnd() {

    }
}
