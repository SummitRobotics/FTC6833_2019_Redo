package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.util.ElapsedTime;

public class WaitForTime extends CoreAction {

    long time;
    int nextPos;
    ElapsedTime runtime = new ElapsedTime();

    public WaitForTime(double time, int nextPos) {

        this.time = (long) time;
        this.nextPos = nextPos;
    }

    @Override
    public void actionInit(HardwareMap hardwareMap, Telemetry telemetry) {
        runtime.reset();
    }

    @Override
    public int run() {

        if (runtime.seconds() > time) {
            return nextPos;
        }

        return 0;
    }

    @Override
    public void actionEnd() {

    }
}
