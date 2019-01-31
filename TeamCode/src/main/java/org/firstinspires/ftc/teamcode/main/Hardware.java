package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

public class Hardware {
    // Declare Hardware members.
    public DcMotor leftFrontDrive;
    public DcMotor rightFrontDrive;
    public DcMotor leftBackDrive;
    public DcMotor rightBackDrive;
    public DcMotor frontLeg;
    public DcMotor backLeg;
    public DcMotor armMotor;
    public CRServo frontIntake;
    public CRServo backIntake;
    public DigitalChannel frontLimit;
    public DigitalChannel backLimit;

    // Prepare variables for encoder use
    // http://www.revrobotics.com/content/docs/Encoder-Guide.pdf
    private final int HD_HEX_REV_COUNTS = 1120; // 1120 for 40:1, 560 for 20:1
    private final double DRIVE_GEAR_RATIO = 15.0/20.0; // WheelGear / MotorGear
    private final double WHEEL_CIRCUMFERENCE = 3.5 * Math.PI;
    public final double ROBOT_WIDTH = 17;

    public final int DRIVE_COUNTS_PER_INCH = (int) (HD_HEX_REV_COUNTS * DRIVE_GEAR_RATIO /
            WHEEL_CIRCUMFERENCE);

    public final int DRIVE_COUNTS_PER_ROT = (int) -(ROBOT_WIDTH * Math.PI  * DRIVE_COUNTS_PER_INCH);

    private final double HEX_CORE_REV_COUNTS = 288;
    private final double LEG_GEAR_RATIO = (16.0 * 48.0) / 30.0;
    public final double LEG_COUNTS_PER_ROT = (HD_HEX_REV_COUNTS * LEG_GEAR_RATIO);

    // Local opmode hardware map
    public HardwareMap hardwareMap = null;

    // Constructor
    public Hardware() {}

    public void init(HardwareMap hardwareMap) {
        // Save reference to Hardware map
        this.hardwareMap = hardwareMap;

        //Init hardware
        leftFrontDrive = this.hardwareMap.get(DcMotor.class, "leftFrontDrive");
        rightFrontDrive = this.hardwareMap.get(DcMotor.class, "rightFrontDrive");
        leftBackDrive = this.hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightBackDrive = this.hardwareMap.get(DcMotor.class, "rightBackDrive");
        frontLeg = this.hardwareMap.get(DcMotor.class, "frontLeg");
        backLeg = this.hardwareMap.get(DcMotor.class, "backLeg");
        armMotor = this.hardwareMap.get(DcMotor.class, "armMotor");
        frontIntake = this.hardwareMap.get(CRServo.class, "frontIntake");
        backIntake = this.hardwareMap.get(CRServo.class, "backIntake");
        frontLimit = hardwareMap.get(DigitalChannel.class, "frontLimit");
        backLimit = hardwareMap.get(DigitalChannel.class, "backLimit");


        // Reverse the motor that runs backwards, set servo positions.
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeg.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeg.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLimit.setMode(DigitalChannel.Mode.INPUT);
        backLimit.setMode(DigitalChannel.Mode.INPUT);
    }

    public boolean atLimit(DigitalChannel limit) {
        return !limit.getState();
    }
}
