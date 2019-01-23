package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.autonomous.actions.LegControl;
import org.firstinspires.ftc.teamcode.main.Hardware;

@TeleOp(name="LegTest", group="Iterative Opmode")
public class LegTest extends OpMode{

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor arm;

    @Override
    public void init() {
        // Initialize all hardware
        arm = hardwareMap.get(DcMotor.class, "armMotor");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

        double armPower = Range.clip(gamepad1.left_stick_y, -1, 1);
        arm.setPower(armPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Arm", "power (%.2f)", armPower);
    }

    @Override
    public void stop() { telemetry.addData("Status", "Stopped"); }
}