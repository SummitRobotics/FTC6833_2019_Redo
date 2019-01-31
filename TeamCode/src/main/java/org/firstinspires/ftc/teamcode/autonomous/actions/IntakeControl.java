package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.main.Hardware;

public class IntakeControl extends CoreAction {

    private int power;

    public IntakeControl(int power, int nextPos) {

        this.power = power;
        this.nextPos = nextPos;
    }

    @Override
    public void actionInit(Hardware robot, ElapsedTime runtime, Telemetry telemetry) {
        this.robot = robot;
        this.runtime = runtime;
        this.telemetry = telemetry;
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
