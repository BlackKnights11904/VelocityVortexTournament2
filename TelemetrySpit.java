package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="telemetry", group="11904")
//@Disabled
public class TelemetrySpit extends DefineRobot {

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
    }

    @Override
    public void loop() {
        telemetry.addData("telemetry", gyro.getIntegratedZValue());
    }
}