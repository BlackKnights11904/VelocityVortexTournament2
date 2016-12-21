package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/* This is our autonomous file, in here we put our four autonomous programs */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous", group="11904")
//@Disabled
public class Autonomous extends LinearOpMode {

    // Hook into hardware file
    DefineRobot robot = new DefineRobot();

    // Autonomous constants
    public double COUNTS_PER_INCH = (1120 / (4 * Math.PI));     // Find encoder counts per inch
    public double DRIVE_SPEED = 1;
    public double TURN_SPEED = 0.7;
    public double SHOOT_SPEED = 1;
    public double FLICK_SPEED = 0.6;

    // Autonomous selection
    int ALLIANCE_COEFF = 0;     // Change angles for different side of the field
    int PROGRAM_COEFF  = 0;     // Change the program using the dpad

    // Gyro calibration
    boolean GYRO_IS_CALIBRATED = false;

    @Override
    public void runOpMode() {

        // Hardware initialization
        robot.init(hardwareMap);

        // Say instructions for selecting autonomous
        telemetry.addData("Instructions", "Press X to join blue alliance, or B to join red alliance");
        telemetry.addData("Instructions", "Use the dpad to select which program is run");
        telemetry.addData("Instructions", "Press Y to calibrate the gyro before autonomous starts");

        // Select alliance
        if (gamepad1.x) {
            ALLIANCE_COEFF = 1;
        } if (gamepad1.b) {
            ALLIANCE_COEFF = -1;
        }

        // Select program
        if (gamepad1.dpad_up) {
            PROGRAM_COEFF = 1;
        } if (gamepad1.dpad_right) {
            PROGRAM_COEFF = 2;
        } if (gamepad1.dpad_down) {
            PROGRAM_COEFF = 3;
        } if (gamepad1.dpad_left) {
            PROGRAM_COEFF = 4;
        }

        // Calibrate gyro in the init by using Y
        if (gamepad1.y) {
            calibrateGyro();
            GYRO_IS_CALIBRATED = true;
        }

        // Wait for coach to press play
        waitForStart();

        if (GYRO_IS_CALIBRATED == false) {
            calibrateGyro();
            GYRO_IS_CALIBRATED = true;
        }

        /* Program 1, use dpad up to select it */
        if (PROGRAM_COEFF == 1) {
            /* Steps for program */

        }

        /* Program 2, use dpad right to select it */
        if (PROGRAM_COEFF == 2) {
            /* Steps for program */

        }

        /* Program 3, use dpad down to select it */
        if (PROGRAM_COEFF == 3) {
            /* Steps for program */

        }

        /* Program 4, use dpad left to select it */
        if (PROGRAM_COEFF == 4) {
            /* Steps for program */

        }

    }

    /* Start autonomous methods */
    public void drive(double inches) {

        if (opModeIsActive()) {

            // Determine heading at start to not drift
            double heading = robot.gyro.getHeading();

            // Determine how many counts to move
            int MOVE_COUNTS = (int) (inches * COUNTS_PER_INCH);

            // Set motors target
            robot.leftMotor.setTargetPosition(robot.leftMotor.getCurrentPosition() + MOVE_COUNTS);
            robot.rightMotor.setTargetPosition(robot.rightMotor.getCurrentPosition() + MOVE_COUNTS);

            // Set motors to run to target
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Turn on drive motors
            robot.leftMotor.setPower(DRIVE_SPEED);
            robot.rightMotor.setPower(DRIVE_SPEED);

            // Loop while not on target
            while (robot.leftMotor.isBusy() && robot.rightMotor.isBusy()) {

                // Fix error in heading if drifting to one side
                if (heading != robot.gyro.getHeading()) {

                    if (heading > robot.gyro.getHeading()) {
                        robot.leftMotor.setPower(0.95);
                        robot.rightMotor.setPower(1);
                    } if (heading < robot.gyro.getHeading()) {
                        robot.leftMotor.setPower(1);
                        robot.rightMotor.setPower(0.95);
                    }
                }

                // After error in heading is fixed return speeds to normal
                if (heading == robot.gyro.getHeading()) {
                    robot.leftMotor.setPower(1);
                    robot.rightMotor.setPower(1);
                }

                idle();
            }

            // After reached destination, turn off drive motors
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);

            telemetry.addData(">", "Finished path");
            telemetry.update();

            // Turn off run to position
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void turn(double angle) {

        // Multiply angle by alliance coefficient
        angle *= ALLIANCE_COEFF;

        // Get start heading
        double heading = robot.gyro.getHeading();

        while (heading != (heading + angle)) {
            if (angle > 0) {
                robot.leftMotor.setPower(TURN_SPEED);
                robot.rightMotor.setPower(-TURN_SPEED);
            }

            if (angle < 0) {
                robot.leftMotor.setPower(-TURN_SPEED);
                robot.rightMotor.setPower(TURN_SPEED);
            }

            idle();
        }

        // Turn off drive motors
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);

    }

    public void shootParticles(int seconds) {

        robot.spinner.setPower(SHOOT_SPEED);
        sleep((int)(seconds * 1000));
        robot.spinner.setPower(0);
    }

    /* public void flickParticles(int rotations) { } */

    public void calibrateGyro() {

        robot.gyro.calibrate();

        // Loop while gyro is calibrating
        while (!isStopRequested() && robot.gyro.isCalibrating())  {

            sleep(10);
            idle();
        }

        // Reset Z axis integrator
        robot.gyro.resetZAxisIntegrator();
    }
}