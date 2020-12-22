package savi_ros_java.savi_ros_bdi.navigation;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class RangeBearingTest extends TestCase {

    @Test
    public void testCase() {
        double post1Lat = 47.641482370883864;
        double post1Lon = -122.14036499180827;
        double post2Lat = 47.6426242556;
        double post2Lon = -122.140354517;
        double post3Lat = 47.642632115856806;
        double post3Lon = -122.13892325834075;
        double post4Lat = 47.642634517703016;
        double post4Lon = -122.14203898419318;

        rangeBearing rbTester = new rangeBearing();

        System.out.println("Starting the RangeBearing testCase");

        double range = rbTester.calculateRange(post1Lat,post1Lon,post2Lat,post2Lon);
        double bearing = rbTester.calculateBearing(range, post1Lat,post1Lon,post2Lat,post2Lon);

        System.out.println("Range: " + Double.toString(range));
        System.out.println("Bearing: " + Double.toString(bearing));
//        assertTrue("Basic test", true);

        //assertFalse("Checking that empty agentState has no perception available", agentState.isPerceptionAvailable());
        //assertFalse("Checking that empty agentState has no inbox message available", agentState.checkInboxMailAvailable());
        //agentState.addToInbox("<1582740868.91,2,tell,0,anotherTime(1582740868.91)>");
        //assertTrue("Checking that agentState has inbox available after adding", agentState.checkInboxMailAvailable());


        // Test case with batteryOK perception (no brackets scenario)
        //agentState.setPerceptions("battery(ok)");
        //assertTrue("Checking that agentState has perception available after adding", agentState.isPerceptionAvailable());


        System.out.println("Test complete");
    }
}