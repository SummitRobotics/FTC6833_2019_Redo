package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.util.ElapsedTime;

public class WaitForTime extends CoreAction {

    double start, time;
    int nextPos;
    ElapsedTime runtime;

    public WaitForTime(double time, int nextPos) {

        this.time = time;
        this.nextPos = nextPos;
    }

    @Override
    public void actionInit(HardwareMap hardwareMap, ElapsedTime runtime) {
        start = runtime.seconds();
        this.runtime = runtime;
    }

    @Override
    public int run() {

        if (runtime.seconds() - start > time) {
            return nextPos;
        }

        return 0;
    }

    @Override
    public void actionEnd() {

    }
}
