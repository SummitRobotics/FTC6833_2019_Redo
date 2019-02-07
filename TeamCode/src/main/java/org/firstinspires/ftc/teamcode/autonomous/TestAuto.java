package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.autonomous.actions.CoreAction;
import org.firstinspires.ftc.teamcode.autonomous.actions.LegControl;
import org.firstinspires.ftc.teamcode.autonomous.actions.MarkerDrop;
import org.firstinspires.ftc.teamcode.autonomous.actions.MoveByEncoder;
import org.firstinspires.ftc.teamcode.autonomous.actions.WaitForTime;

import java.util.ArrayList;

@Autonomous(name="TestAuto", group="LinearOpMode")
public class TestAuto extends CoreAuto {

    //Initializes action list
    private ArrayList<CoreAction> path = new ArrayList<>();

    @Override
    public void runOpMode() {

        path.add(new MoveByEncoder(40, 0.3, END));

        // Update telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // Run the paths created earlier
        runPath(path);
    }
}