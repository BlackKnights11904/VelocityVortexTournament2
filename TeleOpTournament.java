package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Tournament TeleOp", group="11904")
//@Disabled
public class TeleOpTournament extends DefineRobot {

    // Loop during game
    public void loop() {

        leftMotor.setPower(-gamepad1.left_stick_y);
        rightMotor.setPower(-gamepad1.right_stick_y);

        if (gamepad1.right_bumper && !gamepad1.left_bumper) {
            spinner.setPower(0.9);
        } if (gamepad1.left_bumper && !gamepad1.right_bumper) {
            spinner.setPower(-0.8);
        } if (!gamepad1.right_bumper && !gamepad1.left_bumper) {
            spinner.setPower(0);
        }

        if (gamepad1.dpad_right && !gamepad1.dpad_left) {
            buttonPusher.setPower(0.45);
        } if (gamepad1.dpad_left && !gamepad1.dpad_right) {
            buttonPusher.setPower(-0.45);
        } if (!gamepad1.dpad_right && !gamepad1.dpad_left) {
            buttonPusher.setPower(0);
        }

    }
}