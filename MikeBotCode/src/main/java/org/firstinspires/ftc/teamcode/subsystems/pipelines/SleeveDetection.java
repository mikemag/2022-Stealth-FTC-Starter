// From https://github.com/KookyBotz/PowerPlaySleeveDetection

package org.firstinspires.ftc.teamcode.subsystems.pipelines;

import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class SleeveDetection extends OpenCvPipeline {
    /*
    YELLOW  = Parking Left
    CYAN    = Parking Middle
    MAGENTA = Parking Right
     */

    // TOPLEFT anchor point for the bounding box
    public static Point SLEEVE_TOPLEFT_ANCHOR_POINT = new Point(145, 168);

    // Width and height for the bounding box
    public static int REGION_WIDTH = 30;
    public static int REGION_HEIGHT = 50;

    // Lower and upper boundaries for colors
    public static Scalar
            lower_yellow_bounds = new Scalar(200, 200, 0, 255),
            upper_yellow_bounds = new Scalar(255, 255, 130, 255),
            lower_cyan_bounds = new Scalar(0, 200, 200, 255),
            upper_cyan_bounds = new Scalar(150, 255, 255, 255),
            lower_magenta_bounds = new Scalar(170, 0, 170, 255),
            upper_magenta_bounds = new Scalar(255, 60, 255, 255);

    // Color definitions
    public static Scalar
            YELLOW = new Scalar(255, 255, 0),
            CYAN = new Scalar(0, 255, 255),
            MAGENTA = new Scalar(255, 0, 255);

    // Percent and mat definitions
    private double yelPercent, cyaPercent, magPercent;
    private Mat yelMat = new Mat(), cyaMat = new Mat(), magMat = new Mat(), blurredMat = new Mat(), kernel = new Mat();

    // Anchor point definitions

    // Running variable storing the parking position
    private volatile CameraSubsystem.ParkingPosition position = CameraSubsystem.ParkingPosition.LEFT;

    @Override
    public Mat processFrame(Mat input) {
        Point sleeve_pointA = new Point(
                SLEEVE_TOPLEFT_ANCHOR_POINT.x,
                SLEEVE_TOPLEFT_ANCHOR_POINT.y);
        Point sleeve_pointB = new Point(
                SLEEVE_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                SLEEVE_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);


        // Noise reduction
        Imgproc.blur(input, blurredMat, new Size(5, 5));
        Mat blurredSubMat = blurredMat.submat(new Rect(sleeve_pointA, sleeve_pointB));

        // Apply Morphology
        kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Imgproc.morphologyEx(blurredSubMat, blurredSubMat, Imgproc.MORPH_CLOSE, kernel);

        // Gets channels from given source mat
        Core.inRange(blurredSubMat, lower_yellow_bounds, upper_yellow_bounds, yelMat);
        Core.inRange(blurredSubMat, lower_cyan_bounds, upper_cyan_bounds, cyaMat);
        Core.inRange(blurredSubMat, lower_magenta_bounds, upper_magenta_bounds, magMat);

        // Gets color specific values
        yelPercent = Core.countNonZero(yelMat);
        cyaPercent = Core.countNonZero(cyaMat);
        magPercent = Core.countNonZero(magMat);

        // Calculates the highest amount of pixels being covered on each side
        double maxPercent = Math.max(yelPercent, Math.max(cyaPercent, magPercent));

        // Checks all percentages, will highlight bounding box in camera preview
        // based on what color is being detected
        if (maxPercent == yelPercent) {
            position = CameraSubsystem.ParkingPosition.LEFT;
            Imgproc.rectangle(
                    input,
                    sleeve_pointA,
                    sleeve_pointB,
                    YELLOW,
                    2
            );
        } else if (maxPercent == cyaPercent) {
            position = CameraSubsystem.ParkingPosition.CENTER;
            Imgproc.rectangle(
                    input,
                    sleeve_pointA,
                    sleeve_pointB,
                    CYAN,
                    2
            );
        } else if (maxPercent == magPercent) {
            position = CameraSubsystem.ParkingPosition.RIGHT;
            Imgproc.rectangle(
                    input,
                    sleeve_pointA,
                    sleeve_pointB,
                    MAGENTA,
                    2
            );
        }

        // Memory cleanup
//        blurredMat.release();
//        blurredSubMat.release();
//        yelMat.release();
//        cyaMat.release();
//        magMat.release();
//        kernel.release();

        return input;
    }

    // Returns an enum being the current position where the robot will park
    public CameraSubsystem.ParkingPosition getPosition() {
        return position;
    }
}