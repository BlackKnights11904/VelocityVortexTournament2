package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/* This is our hardware file, or where we define all the hardware on our robot */

public class DefineRobot extends OpMode {

    // Define drive motors
    DcMotor leftMotor = null;
    DcMotor rightMotor = null;

    // Define miscellaneous motors
    DcMotor spinner = null;
    DcMotor buttonPusher = null;

    // Define peripherals
    ModernRoboticsI2cGyro gyro = null;
    ColorSensor colorSensor = null;

    // Elapsed time
    ElapsedTime runtime = new ElapsedTime();

    // Find counts per inch
    double COUNTS_PER_INCH = (1120 / (4 * Math.PI));

    // Selective autonomous and alliance variables
    int alliance;
    int program;
    final int blue = 1;
    final int red = -1;
    final int hitCapBallShootUpRamp = 1;
    final int theProgramToDoItAll = 2;
    final int hitCapBallGoBack = 3;
    final int avoidFieldHitBeacons = 4;

    // Stuff here
    double leftSpeed;
    double rightSpeed;

    // Prevent errors from multiple usage of init commands
    boolean CALIBRATED_GYRO = false;
    boolean firstPress = true;

    // Find components on hardware map
    public void init() {

        // Hardware map drive motors
        leftMotor = hardwareMap.dcMotor.get("left motor");
        rightMotor = hardwareMap.dcMotor.get("right motor");

        // Hardware map miscellaneous motors
        spinner = hardwareMap.dcMotor.get("spinner");
        buttonPusher = hardwareMap.dcMotor.get("button pusher");

        // Hardware map gyro and allow use of Z integrated value
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

        // Hardware map color sensor
        colorSensor = hardwareMap.colorSensor.get("color sensor");

        // Set direction of drive motors
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        // Set direction of miscellaneous motors
        spinner.setDirection(DcMotor.Direction.FORWARD);
        buttonPusher.setDirection(DcMotor.Direction.FORWARD);

        // Turn off all motors
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        spinner.setPower(0);
        buttonPusher.setPower(0);

        // Enable encoders on drive motors
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Disable encoder on spinner
        spinner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        buttonPusher.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Send message to console
        telemetry.addLine("Started program");
        telemetry.update();
    }

    public void loop() {
    }

    public void stop() {
    }

    void waitSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            
        }
    }

    /* Start of autonomous methods */
    void drive(double speed, double inches) {

        // Determine heading at start to not drift
        //double heading = gyro.getHeading();

        // Determine how many counts to move
        int MOVE_COUNTS = (int) (inches * COUNTS_PER_INCH);

        // Set motors target
        leftMotor.setTargetPosition(leftMotor.getCurrentPosition() + MOVE_COUNTS);
        rightMotor.setTargetPosition(rightMotor.getCurrentPosition() + MOVE_COUNTS);

        // Set motors to run to target
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Turn on drive motors
        leftSpeed = speed;
        rightSpeed = speed;
        leftMotor.setPower(speed);
        rightMotor.setPower(speed);

        // Loop while not on target
        while (leftMotor.isBusy() && rightMotor.isBusy()) {
            //telemetry.addData("left enc", String.valueOf(leftMotor.getCurrentPosition()) + "\n right enc" + rightMotor.getCurrentPosition());
            telemetry.update();
        }

        // After reached destination, turn off drive motors
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        telemetry.addLine("Finished path");
        telemetry.update();

        // Turn off run to position
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /*
    public void turn(double speed, double angle) {

        // Multiply angle by alliance coefficient
        angle *= alliance;

        // Get start heading
        double heading = gyro.getHeading();

        while (gyro.getHeading() != (angle)) {
            if (angle > (-10 * alliance) 0) {
                leftMotor.setPower(speed);
                rightMotor.setPower(0);
            }

            if (angle < (-10 * alliance) 0) {
                leftMotor.setPower(0);
                rightMotor.setPower(speed);
            }
            telemetry.addData("Heading", "Heading variable", heading);
            telemetry.addData("Actual Heading", "Actual heading variable", gyro.getHeading());
            telemetry.addData("z value", "z accumulated", gyro.getIntegratedZValue());
        }

        // Turn off drive motors
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }*/

    void turn(int angle) {

        angle *= alliance;

        int targetPos = (angle - 10) - gyro.getIntegratedZValue();

        if (angle > 0) { // Turn Right
            while (-gyro.getIntegratedZValue() < targetPos) {
                if (-gyro.getIntegratedZValue() < targetPos) {

                    telemetry.addData("rotation", -gyro.getIntegratedZValue());

                    telemetry.update();

                    leftMotor.setPower(0.2);
                    rightMotor.setPower(-0.2);
                }
            }
            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }

        else if (angle < 0) { // Turn Left
            while (-gyro.getIntegratedZValue() > targetPos) {
                if (-gyro.getIntegratedZValue() > targetPos) {

                    telemetry.addData("rotation", -gyro.getIntegratedZValue());
                    telemetry.update();

                    leftMotor.setPower(0.2);
                    rightMotor.setPower(-0.2);
                }
            }
            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }
    }

    void shootParticles(int seconds) {

        long time1 = System.currentTimeMillis();

        // Shoot particles, then wait selected amount of time
        while (System.currentTimeMillis() <= (time1 + (seconds * 1000))) {
            spinner.setPower(0.9);
        }
        spinner.setPower(0);

    }

    void calibrateGyro() {

        CALIBRATED_GYRO = true;

        gyro.calibrate();

        telemetry.addLine("Gyro is calibrating");
        telemetry.update();

        // Loop while gyro is calibrating
        while (gyro.isCalibrating())  {
            // Doing nothing
        }

        // Reset Z axis integrator
        gyro.resetZAxisIntegrator();

        if (!gyro.isCalibrating()) {
            telemetry.addLine("Gyro done calibrating");
            telemetry.update();
        }
    }


    void pressBeacon() {

        buttonPusher.setPower(0.45);
        waitSleep(500);
        buttonPusher.setPower(0);
        waitSleep(25);
        buttonPusher.setPower(-0.45);
        waitSleep(500);
        buttonPusher.setPower(0);
    }
}