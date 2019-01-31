package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.main.Hardware;

// Interface to create new action classes
public abstract class CoreAction {

    Hardware robot;
    ElapsedTime runtime;
    Telemetry telemetry;
    int nextPos = -1;

    /**
     *
     * @param robot the robot
     * @param runtime the time
     * @param telemetry the telemetry
     */
    public abstract void actionInit(Hardware robot, ElapsedTime runtime, Telemetry telemetry);

    /**
     * Method to be called until action is complete
     * @return Returns 0 if action is incomplete, returns distance between current index and next
     * index when action is complete
     */
    public abstract int run();

    /**
     * Method to run once at the end of action use
     */
    public abstract void actionEnd();
}