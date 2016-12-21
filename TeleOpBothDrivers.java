package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FirstTournamentCode.HardwareExcaliBot;

/**
 * This is our main TeleOp for the competition at the Burnsville Center, in this program we use tank
 * controls and the bumpers or dpad for our sweeper.
 */

@TeleOp(name="TeleOp Both Drivers", group="11904")
//@Disabled
public class TeleOpBothDrivers extends LinearOpMode {

    // Link to hardware map
    DefineRobot robot = new DefineRobot();

    // Code ran on OpMode start, initializes hardware map
    @Override
    public void runOpMode() {

        // Initialize all hardware
        robot.init(hardwareMap);

        // Wait for coach to press play
        waitForStart();

        // Loop code until TeleOp ends
        while (opModeIsActive()) {

            // Tank controls using Left Stick and Right Stick on the driver controller
            robot.leftMotor.setPower(-gamepad1.left_stick_y * 1);
            robot.rightMotor.setPower(-gamepad1.right_stick_y * 1);

            // Use the spinner with Left Bumper and Right Bumper on the secondary controller
            if (gamepad2.right_bumper && !gamepad2.left_bumper) {

                // Shoot balls
                robot.spinner.setPower(1);
            } if (gamepad2.left_bumper && !gamepad2.right_bumper) {

                // Take balls into the holder
                robot.spinner.setPower(-0.5);
            } if (!gamepad2.right_bumper && !gamepad2.left_bumper) {

                // Turn off the spinner
                robot.spinner.setPower(0);
            }

            // Push against beacons using Y
            if (gamepad1.y || gamepad2.y && !robot.leftMotor.isBusy() && !robot.rightMotor.isBusy()) {

                robot.leftMotor.setPower(-0.3);
                robot.rightMotor.setPower(-0.3);
            } if (!gamepad1.y || gamepad2.y && !robot.leftMotor.isBusy() && !robot.rightMotor.isBusy()) {

                robot.leftMotor.setPower(0);
                robot.rightMotor.setPower(0);
            }

            // Keep idle to stop the while loop from going forever
            idle();
        }
    }
}