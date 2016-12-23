package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/* This is our hardware file, or where we define all the hardware on our robot */

public class DefineRobot {

    // Define drive motors
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;

    // Define miscellaneous motors
    public DcMotor spinner = null;
    public DcMotor flicker = null;

    // Define peripherals
    public ModernRoboticsI2cGyro gyro = null;

    // Define ElapsedTime
    private ElapsedTime period = new ElapsedTime();

    // Find components on hardware map
    public void init(HardwareMap hwMap) {

        // Hardware map drive motors
        leftMotor = hwMap.dcMotor.get("left drive");
        rightMotor = hwMap.dcMotor.get("right drive");

        // Hardware map miscellaneous motors
        spinner = hwMap.dcMotor.get("spinner");
        // flicker = hwMap.dcMotor.get("flicker");

        // Set direction of drive motors
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        // Set direction of miscellaneous motors
        spinner.setDirection(DcMotor.Direction.FORWARD);
        //flicker.setDirection(DcMotor.Direction.FORWARD);

        // Turn off all motors
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        spinner.setPower(0);
        //flicker.setPower(0);

        // Enable encoders on drive motors
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Disable encoder on spinner
        spinner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //flick.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // Dont change anything here
    public void waitForTick(long periodMs) throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // Makes the script do nothing after the init phase
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset clock when this class is run again
        period.reset();
    }
}