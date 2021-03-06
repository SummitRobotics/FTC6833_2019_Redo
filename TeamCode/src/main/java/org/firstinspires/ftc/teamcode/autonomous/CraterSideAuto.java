package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.autonomous.actions.AlignByGyro;
import org.firstinspires.ftc.teamcode.autonomous.actions.ArmControl;
import org.firstinspires.ftc.teamcode.autonomous.actions.CalabrateGyro;
import org.firstinspires.ftc.teamcode.autonomous.actions.CoreAction;
import org.firstinspires.ftc.teamcode.autonomous.actions.LegControl;
import org.firstinspires.ftc.teamcode.autonomous.actions.MoveByEncoder;
import org.firstinspires.ftc.teamcode.autonomous.actions.SampleDetection;
import org.firstinspires.ftc.teamcode.autonomous.actions.WaitForTime;

import java.util.ArrayList;

@Autonomous(name="CraterSideAuto", group="LinearOpMode")
public class CraterSideAuto extends CoreAuto {

    //Initializes action list
    private ArrayList<CoreAction> path = new ArrayList<>();

    public Orientation firstAngle = new Orientation();

    @Override
    public void runOpMode() {
        //path.add(new CalabrateGyro(1,firstAngle));
        path.add(new LegControl(0.55, -0.45, 1, 1,1));
        path.add(new MoveByEncoder(6, 0.25, 1));
        path.add(new LegControl(0, 0.17, 0, 1, 1));
        //path.add(new MoveByEncoder(3, 0.25 , 1));
       // path.add(new AlignByGyro(1,firstAngle));
        path.add(new WaitForTime(0.1, 1));
        path.add(new SampleDetection(1, 3, 7));

        // Left path
        path.add(new LegControl(0, -0.17, 0, 1, 1));
        path.add(new MoveByEncoder(-3, 13, 0.2,  0.2, END));
       // path.add(new MoveByEncoder(-25, 0.2, 1));
       // path.add(new MoveByEncoder(19, -19, 0.2,  0.2, 1));
      //  path.add(new MoveByEncoder(60, 0.2, 1));

        //path.add(new ArmControl(0.25, 0.5, 1));


       // path.add(new MoveByEncoder(-80, 0.2, END));

        // Center path
        path.add(new LegControl(0, -0.17, 0, 1, 1));
        path.add(new MoveByEncoder(-13,.25,1));
        path.add(new MoveByEncoder(-4, 0.25, 1));
        path.add(new MoveByEncoder(4,0.25,END));
        //path.add(new MoveByEncoder(24, -24, 0.2,  0.2, 1));
       // path.add(new MoveByEncoder(28, 0.25, 1));
       // path.add(new MoveByEncoder(-12, 12, 0.25, 1));
       // path.add(new MoveByEncoder(58,0.25,1));

        //path.add(new ArmControl(0.25, 0.5, 1));

        //path.add(new MoveByEncoder(-13, 13, 0.2,  0.2, 1));
       // path.add(new MoveByEncoder(-75, 0.25, END));

        // Right Path
        path.add(new LegControl(0, -0.17, 0, 1, 1));
        path.add(new MoveByEncoder(-3, -13, 0.2,  0.2, END));
        //path.add(new MoveByEncoder(-25, 0.2, 1));
      //  path.add(new MoveByEncoder(19, -19, 0.2,  0.2, 1));
        //path.add(new MoveByEncoder(30, 0.2, 1));

       // path.add(new ArmControl(0.5, 0.5, 1));

        path.add(new MoveByEncoder(-75, 0.2, END));


        // Update telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Running");

        // Run the paths created earlier
        runPath(path);
    }
}