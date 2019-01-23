package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.code.Attribute;

import org.firstinspires.ftc.teamcode.autonomous.actions.CoreAction;
import org.firstinspires.ftc.teamcode.autonomous.actions.WaitForTime;
import org.firstinspires.ftc.teamcode.main.Hardware;

import java.util.ArrayList;

// Abstract class to create other autonomous programs
public abstract class CoreAuto extends LinearOpMode{

    protected final int END = -1;
    Hardware robot = new Hardware();

    /**
     * runPath method will run through all paths in an autonomous program. Each path will be
     * initialized, then run through until completion
     * @param path The list of paths to be run through
     */

    protected void runPath(ArrayList<CoreAction> path) {

        robot.init(hardwareMap);

        // variables to store the currently running action location in the list of paths and the
        // location of the next path to run.
        int currentAction = 0;
        int nextAction = 0;
        ElapsedTime runtime = new ElapsedTime();

        // Loop through every action until next action == END (-1)
        do {

            // Increase current action by next action. Next action represents the distance between
            // two actions
            currentAction += nextAction;
            nextAction = 0;

            // Initialize current action
            path.get(currentAction).actionInit(hardwareMap, telemetry);

            // Continue looping through run method until action completes and doesn't return 0
            runtime.reset();
            while (nextAction == 0 && opModeIsActive()) {

                nextAction = path.get(currentAction).run();

                // End the program if runtime exceeds 5 seconds
                if (runtime.seconds() > 5 && !(path.get(currentAction) instanceof WaitForTime)) {
                    telemetry.addData("Failed","Next Position: " + path.get(currentAction).nextPos);
                    telemetry.update();
                    nextAction = path.get(currentAction).nextPos;
                }
            }

            // End current actions
            path.get(currentAction).actionEnd();

        } while (nextAction != END && opModeIsActive());

        robot.leftFrontDrive.setPower(0);
        robot.rightFrontDrive.setPower(0);
        robot.leftBackDrive.setPower(0);
        robot.rightBackDrive.setPower(0);
        robot.frontLeg.setPower(0);
        robot.backLeg.setPower(0);
        robot.armMotor.setPower(0);
        robot.frontIntake.setPower(0);
        robot.backIntake.setPower(0);
    }
}