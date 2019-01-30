package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.autonomous.actions.CoreAction;
import org.firstinspires.ftc.teamcode.autonomous.actions.IntakeControl;
import org.firstinspires.ftc.teamcode.autonomous.actions.LegControl;
import org.firstinspires.ftc.teamcode.autonomous.actions.MoveByEncoder;
import org.firstinspires.ftc.teamcode.autonomous.actions.SampleDetection;
import org.firstinspires.ftc.teamcode.autonomous.actions.WaitForTime;

import java.util.ArrayList;

@Autonomous(name="CraterSideAuto", group="LinearOpMode")
public class CraterSideAuto extends CoreAuto {

    //Initializes action list
    private ArrayList<CoreAction> path = new ArrayList<>();

    @Override
    public void runOpMode() {
        // Add paths for autonomous
        path.add(new LegControl(5.3, 4.7, -1, 1, 1));
        path.add(new MoveByEncoder(0.5, 0.3, 1));
        path.add(new LegControl(0, 1.8, 0, -1, 1));
        path.add(new WaitForTime(0.5, 1));
        path.add(new SampleDetection(1, 6, 8, 10));

        // Left path
        path.add(new LegControl(0, 1.8, 0, 1, 1));
        path.add(new MoveByEncoder(0.3, -0.2, 1));

        path.add(new MoveByEncoder(0.5, 0.5, -0.35, 0.35, 1));
        path.add(new WaitForTime(0.5, 1));
        path.add(new MoveByEncoder(0.9, -0.35, END));

        // Center path
        path.add(new LegControl(0, 1.8, 0, 1, 1));
        path.add(new MoveByEncoder(1.4, -0.3, END));

        // Right Path
        path.add(new LegControl(0, 1.8, 0, 1, 1));
        path.add(new MoveByEncoder(0.3, -0.2, 1));

        path.add(new MoveByEncoder(0.6, 0.6, 0.35, -0.35, 1));
        path.add(new WaitForTime(0.5, 1));
        path.add(new MoveByEncoder(0.9, -0.35, END));


        // Update telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Running");

        // Run the paths created earlier
        runPath(path);
    }
}