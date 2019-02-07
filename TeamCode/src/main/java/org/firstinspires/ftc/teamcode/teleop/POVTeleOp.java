package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.autonomous.actions.LegControl;
import org.firstinspires.ftc.teamcode.main.Hardware;

@TeleOp(name="POVTeleOp", group="Iterative Opmode")
public class POVTeleOp extends OpMode{

    private Hardware robot = new Hardware();
    private ElapsedTime runtime = new ElapsedTime();
    private LegControl centerLegs;
    private boolean centering = false;
    private boolean released = true;

    private int highLimit = 0;

    @Override
    public void init() {
        // Initialize all hardware
        robot.init(hardwareMap);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() { }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;
        double frontLegPower;
        double backLegPower;
        double armPower;

        // Get gamepad inputs
        double drive = gamepad1.right_trigger - gamepad1.left_trigger;
        drive = expPower(drive);
        double turn = deadzone(gamepad1.left_stick_x);

        // Set power variables
        leftPower = Range.clip(drive + turn, -1.0, 1.0);
        rightPower = Range.clip(drive - turn, -1.0, 1.0);
        frontLegPower = Range.clip(deadzone(-gamepad1.right_stick_y), -1.0, 1.0);
        armPower = Range.clip(deadzone(gamepad2.left_stick_y), -1.0, 1.0);

        if (gamepad1.dpad_up) {
            backLegPower = 1;
        } else if (gamepad1.dpad_down) {
            backLegPower = -1;
        } else {
            backLegPower = 0;
        }

        if (robot.atLimit(robot.frontLimit) && frontLegPower >= 0) {
            frontLegPower = 0;
        }

        if (robot.atLimit(robot.backLimit) && backLegPower <= 0) {
            backLegPower = 0;
        }

        //zero is a placeholder integer until the correct value can be calculated
        if (!gamepad1.a) {
            if (robot.backLeg.getCurrentPosition() > highLimit && backLegPower >= 0) {
                backLegPower = 0;
            }

            if (robot.frontLeg.getCurrentPosition() > highLimit && frontLegPower >= 0) {
                frontLegPower = 0;
            }
        } else {
            highLimit = robot.backLeg.getCurrentPosition();
        }

        if (gamepad1.left_bumper && released) {
            released = false;
            if (centering) {
                centerLegs.actionEnd();
                centerLegs = null;
                centering = false;
            } else {
                centerLegs = new LegControl(0, 0, 1, 1, 1);
                centerLegs.actionInit(robot, runtime, telemetry);
                centering = true;
            }
        }

        if (!gamepad1.left_bumper) {
            released = true;
        }

        // Send calculated power to hardware
        robot.leftFrontDrive.setPower(leftPower);
        robot.leftBackDrive.setPower(leftPower);
        robot.rightFrontDrive.setPower(rightPower);
        robot.rightBackDrive.setPower(rightPower);
        robot.armMotor.setPower(armPower);

        if (centering) {
            if (centerLegs.run() != 0) {
                centering = false;
                centerLegs.actionEnd();
                centerLegs = null;
            }
        } else {
            robot.frontLeg.setPower(frontLegPower);
            robot.backLeg.setPower(backLegPower);
        }

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "drive (%.2f), turn (%.2f)", drive, turn);
        telemetry.addData("Centering", centering);
    }

    @Override
    public void stop() { telemetry.addData("Status", "Stopped"); }

    private double logPower(double power) {

        if (power >= 0) {
            return 0.96 * Math.log10(power + 0.1) + 0.96;
        } else {
            return -0.96 * Math.log10(-power + 0.1) - 0.96;
        }
    }

    private double expPower(double power) {
        if (power == 0) { return 0; }
        return (power * power) * (Math.abs(power) / power);
    }

    private double deadzone(double num) {
        if (num <= 0.13 && num >= -0.13) {
            return 0;
        }

        return num;
    }

}