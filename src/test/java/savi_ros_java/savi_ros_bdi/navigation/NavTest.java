package savi_ros_java.savi_ros_bdi.navigation;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import savi_ros_java.savi_ros_bdi.navigation.a_star.MapSearchFunctions;

public class NavTest extends TestCase {

    @Test
    public void testCase() {
        String currentPositionString = "post1";
        String destinationPositionString = "post3";

        System.out.println("Starting the NavTest testCase");

        String path = MapSearchFunctions.getNavigationPath(currentPositionString, destinationPositionString);

        System.out.println("Path: " + path);

        System.out.println("Test complete");
    }
}