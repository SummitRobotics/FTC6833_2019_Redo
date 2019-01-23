package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.actions.CoreAction;
import org.firstinspires.ftc.teamcode.autonomous.actions.MarkerServo;
import org.firstinspires.ftc.teamcode.autonomous.actions.MoveByEncoder;
//import org.firstinspires.ftc.teamcode.autonomous.actions.SampleDetection;

import java.util.ArrayList;

@Autonomous(name="CraterSideAuto", group="LinearOpMode")
public class CraterSideAuto extends CoreAuto {

    //Initializes action list
    private ArrayList<CoreAction> path = new ArrayList<>();

    @Override
    public void runOpMode() {
        // Add paths for autonomous
        path.add(new MarkerServo(0.5,1));

        //path.add(new SampleDetection(1, 4, 5, 4));

        // Left path
        path.add(new MoveByEncoder(10, 0.4, MoveByEncoder.FORWARD, 1));
        path.add(new MoveByEncoder(1.1, 0.4, MoveByEncoder.TURN, 1));
        path.add(new MoveByEncoder(40, 0.4, MoveByEncoder.FORWARD, END));

        // Center
        path.add(new MoveByEncoder(50, 0.4, MoveByEncoder.FORWARD, END));

        // Right
        path.add(new MoveByEncoder(10, 0.4, MoveByEncoder.FORWARD, 1));
        path.add(new MoveByEncoder(-1.2, 0.4, MoveByEncoder.TURN, 1));
        path.add(new MoveByEncoder(40, 0.4, MoveByEncoder.FORWARD, END));

        // Update telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Running");

        // Run the paths created earlier
        runPath(path);
    }
}