package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.actions.ArmControl;
import org.firstinspires.ftc.teamcode.autonomous.actions.CoreAction;
import org.firstinspires.ftc.teamcode.autonomous.actions.LegControl;
import org.firstinspires.ftc.teamcode.autonomous.actions.MoveByEncoder;
import org.firstinspires.ftc.teamcode.autonomous.actions.SampleDetection;
import org.firstinspires.ftc.teamcode.autonomous.actions.WaitForTime;

import java.util.ArrayList;

@Autonomous(name="DepotSideAuto", group="LinearOpMode")
public class DepotSideAuto extends CoreAuto {

    //Initializes action list
    private ArrayList<CoreAction> path = new ArrayList<>();

    @Override
    public void runOpMode() {
        path.add(new LegControl(0.53, -0.47, 1, 1,1));
        //front/backPos controls legs rotation
        path.add(new MoveByEncoder(10, 0.4, 1));
        //dist in inches
        path.add(new LegControl(0, 0.21, 0, 1, 1));
        path.add(new SampleDetection(1, 6, 9));
        //left, center, right

        // Left path
        path.add(new LegControl(0, -0.21, 0, 1, 1));
        path.add(new MoveByEncoder(-4, 4, 0.2,  0.2, END));
     //   path.add(new MoveByEncoder(-40, 0.2, 1));
     //   path.add(new MoveByEncoder(13, -13, 0.2,  0.2, 1));
     //   path.add(new MoveByEncoder(-50, 0.2, END));


//        //twitch control
//        path.add(new ArmControl(0.05, 1, 1));
//        path.add(new ArmControl(-0.05, 1, 1));
//        path.add(new ArmControl(0.05, 1, 1));
//        path.add(new ArmControl(-0.05, 1, 1));
//        path.add(new MoveByEncoder(75, 0.2, END));

        // Center path
        path.add(new LegControl(0, -0.21, 0, 1, 1));
        path.add(new MoveByEncoder(-58, 0.2, END));
//        path.add(new ArmControl(0.05, 1, 1));
//        path.add(new ArmControl(-0.05, 1, 1));
//        path.add(new ArmControl(0.05, 1, 1));
//        path.add(new ArmControl(-0.05, 1, 1));

//        path.add(new MoveByEncoder(-12, 12, 0.2,  0.2, 1));
//        path.add(new MoveByEncoder(75, 0.2, END));

        // Right Path
        path.add(new LegControl(0, -0.21, 0, 1, 1));
        path.add(new MoveByEncoder(4, -4, 0.2,  0.2, END));
     //   path.add(new MoveByEncoder(-40, 0.2, 1));
     //   path.add(new MoveByEncoder(-13, 13, 0.2,  0.2, 1));
     //   path.add(new MoveByEncoder(-50, 0.2, 1));

//        //twitch control
//        path.add(new ArmControl(0.05, 1, 1));
//        path.add(new ArmControl(-0.05, 1, 1));
//        path.add(new ArmControl(0.05, 1, 1));
//        path.add(new ArmControl(-0.05, 1, 1));

        path.add(new MoveByEncoder(75, 0.2, END));

        // Update telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // Run the paths created earlier
        runPath(path);
    }
}