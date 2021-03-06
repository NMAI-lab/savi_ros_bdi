package savi_ros_java.savi_ros_bdi.navigation;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class RangeBearingTest extends TestCase {

    @Test
    public void testCase() {
        double post1Lat = 47.6414824122;
        double post1Lon = -122.140364987;
        double post2Lat = 47.6426242556;
        double post2Lon = -122.140354517;
        double post3Lat = 47.642632115856806;
        double post3Lon = -122.13892325834075;
        double post4Lat = 47.642634517703016;
        double post4Lon = -122.14203898419318;

        //rangeBearing rbTester = new rangeBearing();

        System.out.println("Starting the RangeBearing testCase");

        double rangeValue = range.calculateRange(post2Lat,post2Lon,post4Lat,post4Lon,"meters");
        double bearingValue = bearing.calculateBearing(post2Lat,post2Lon,post4Lat,post4Lon);

        System.out.println("Range: " + Double.toString(rangeValue));
        System.out.println("Bearing: " + Double.toString(bearingValue));

        System.out.println("Test complete");
    }
}