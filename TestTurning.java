package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Test turn", group="11904")
//@Disabled
public class TestTurning extends DefineRobot {

    // Select autonomous and alliance
    @Override
    public void init_loop() {

        // Calibrate gyro manually ONLY DO IF YOU HAVE MORE THAN 3 SECONDS
        if (gamepad1.y && !CALIBRATED_GYRO) {
            calibrateGyro();
        }
    }

    // Run program
    @Override
    public void start() {

        alliance = 1;

        // Check if gyro is calibrated
        if (!CALIBRATED_GYRO) {
            calibrateGyro();
        }

        testTurn(180);

    }

    void testTurn(int angle) {

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
}