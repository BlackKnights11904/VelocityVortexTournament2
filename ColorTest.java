package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(name="color", group="11904")
//@Disabled
public class ColorTest extends DefineRobot {

    @Override
    public void init() {
    }

    // Select autonomous and alliance
    @Override
    public void init_loop() {

        alliance = blue;

        // Calibrate gyro manually ONLY DO IF YOU HAVE MORE THAN 3 SECONDS
        if (gamepad1.y && !CALIBRATED_GYRO) {
            calibrateGyro();
        }
    }

    // Run program
    @Override
    public void start() {

        colorSensor.enableLed(false);
    }

    @Override
    public void loop() {
        telemetry.addData("color stuff", colorSensor.red());
        telemetry.addData("color stuff 2", colorSensor.blue());
        telemetry.update();

        if (colorSensor.blue() > 1) {
            pressBeacon();
        }
    }
}