package org.firstinspires.ftc.teamcode.autonomous.actions;

import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.main.Hardware;

// Class to use phone camera to detect gold mineral
public class SampleDetection extends CoreAction {

    // Variables for action
    private int nextPos1, nextPos2, nextPos3;
    private double start;

    // Variables for Vuforia and Tensor Flow
    private static final String VUFORIA_KEY = "AT1C2Ar/////AAABmfar9R+GH0MImzCa8UF4lUZ8LgjTo9mRlnRVKWMQiuxY/ZtbqSk4lG/4py3aMDqB0hP1FGC9EzgF2wmcVWVtl3hyTngl17UAeBJaROQuvCuBI4BX+PzqZGuvU50uzGRpNAmRs+dbUtOlxgQTn7CAhvMvVPSu30KbWGAVxjS9FoWIm76KsTmO4sJ16usgbwgb+Y6Rj2NMhgYa3Dd+z9z8nIt5dvay8M4/XgtBNfEEf4eH+w0Mbs9tQGc9pSAe3VG7m/T69LmD1Thmd641EflttJ4geP7TbI9q7e2LPhGwLLE+1FaHeaSa2OdCbzFcITVhHtop+F1qNWJbKWpWKHwUEbeAf2do9lTWV0PjoUTcE44y";
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public SampleDetection(int nextPos1, int nextPos2, int nextPos3) {

        this.nextPos1 = nextPos1;
        this.nextPos2 = nextPos2;
        this.nextPos3 = nextPos3;
        this.nextPos =  nextPos2;
    }

    @Override
    public void actionInit(Hardware robot, ElapsedTime runtime, Telemetry telemetry) {
        this.robot = robot;
        this.runtime = runtime;
        this.telemetry = telemetry;
        this.start = runtime.seconds();

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        }

        tfod.activate();
    }

    @Override
    public int run() {
        telemetry.addData("SampleDetection", "Trying...");
        telemetry.update();
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {

                // Check if 3 minerals have been detected
                if (updatedRecognitions.size() == 3) {
                    int goldMineralX = -1;
                    int silverMineral1X = -1;
                    int silverMineral2X = -1;

                    // Get mineral positions
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                        } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                        } else {
                            silverMineral2X = (int) recognition.getLeft();
                        }
                    }

                    // Complete actions based on mineral positions
                    if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            telemetry.addData("SampleDetection", "Left");
                            telemetry.update();
                            return nextPos1; //Left
                        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            telemetry.addData("SampleDetection", "Right");
                            telemetry.update();
                            return nextPos3; //Right
                        } else {
                            telemetry.addData("SampleDetection", "Center");
                            telemetry.update();
                            return nextPos2; //Center
                        }
                    }
                }
            }
        }

        if (runtime.seconds() - start > 5) {
            telemetry.addData("SampleDetection", "Failed");
            telemetry.update();
            return nextPos;
        }

        // Return 0 if action is still in progress
        return 0;
    }

    @Override
    public void actionEnd() {
        // Kill camera
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.FRONT;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = robot.hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", robot.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
