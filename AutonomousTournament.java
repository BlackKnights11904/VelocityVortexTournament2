package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Test Selective Autonomous", group="11904")
//@Disabled
public class AutonomousTournament extends DefineRobot {

    // Select autonomous and alliance
    @Override
    public void init_loop() {

        // Alliance selection
        if (gamepad1.x) {
            alliance = blue;
        } if (gamepad1.b) {
            alliance = red;
        } if (alliance != 0) {
            telemetry.addData("Alliance", "Picked alliance ", alliance);
            telemetry.update();
        }

        // Program selection
        if (gamepad1.dpad_up) {
            program = hitCapBallShootUpRamp;
        } if (gamepad1.dpad_right) {
            program = theProgramToDoItAll;
        } if (gamepad1.dpad_down) {
            program = hitCapBallGoBack;
        } if (gamepad1.dpad_left) {
            program = avoidFieldHitBeacons;
        } if (program != 0) {
            telemetry.addData("Program", "Picked program ", program);
            telemetry.update();
        }

        // Calibrate gyro manually ONLY DO IF YOU HAVE MORE THAN 3 SECONDS
        if (gamepad1.y && !CALIBRATED_GYRO) {
            calibrateGyro();
        }
    }

    // Run program
    @Override
    public void start() {

        // Check if gyro is calibrated
        /*if (!CALIBRATED_GYRO) {
            calibrateGyro();
        }*/

        /* Describe programs below */

        /* Hit cap ball and shoot up ramp */
        if (program == hitCapBallShootUpRamp && CALIBRATED_GYRO) {

            /* Steps for program */
            drive(0.6, 42);
            turn(123);
            drive(0.6, 52);
            shootParticles(3);

        }

        /* The program to do it all */
        if (program == theProgramToDoItAll && CALIBRATED_GYRO) {

            /* Steps for program */
            drive(0.6, 46);
            turn(130);
            drive(0.6, 50);
            shootParticles(3);

        }
        /* Hit cap ball and go back */
        if (program == hitCapBallGoBack && CALIBRATED_GYRO) {

            /* Steps for program */

        }

        /* Avoid the main field and hit beacons */
        if (program == avoidFieldHitBeacons && CALIBRATED_GYRO) {

            /* Steps for program */

        }
    }
}