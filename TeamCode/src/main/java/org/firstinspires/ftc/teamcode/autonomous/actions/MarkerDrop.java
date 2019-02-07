package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.main.Hardware;

public class MarkerDrop extends CoreAction {

    private double pos;
    private double start;

    public MarkerDrop(double pos, int nextPos) {

        this.pos = pos;
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
        robot.markerServo.setPosition(pos);

        if (runtime.seconds() - start > 0.5) {
            return nextPos;
        }

        return 0;
    }

    @Override
    public void actionEnd() {

    }
}
