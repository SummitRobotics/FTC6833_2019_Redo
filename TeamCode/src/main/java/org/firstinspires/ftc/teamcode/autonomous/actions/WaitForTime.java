package org.firstinspires.ftc.teamcode.autonomous.actions;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.main.Hardware;
import com.qualcomm.robotcore.util.ElapsedTime;

public class WaitForTime extends CoreAction {

    private double start, time;
    private int nextPos;

    public WaitForTime(double time, int nextPos) {

        this.time = time;
        this.nextPos = nextPos;
    }

    @Override
    public void actionInit(Hardware robot, ElapsedTime runtime, Telemetry telemetry) {
        this.robot = robot;
        this.runtime = runtime;
        this.telemetry = telemetry;
        this.start = runtime.seconds();
    }

    @Override
    public int run() {

        if (runtime.seconds() - start > time) {
            return nextPos;
        }

        telemetry.addData("WaitForTime", "(%.2f) seconds remaining", time + (start - runtime.seconds()));
        telemetry.update();
        return 0;
    }

    @Override
    public void actionEnd() {

    }
}
