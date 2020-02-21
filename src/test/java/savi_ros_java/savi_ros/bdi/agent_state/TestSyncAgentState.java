package savi_ros_java.savi_ros.bdi.agent_state;

import junit.framework.TestCase;

import org.junit.jupiter.api.Test;
import savi_ros_java.savi_ros_bdi.agent_state.SyncAgentState;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSyncAgentState extends TestCase {

    @Test
    public void testCase() {

        System.out.println("Starting the TestSyncAgentState testCase");

        SyncAgentState agentState = SyncAgentState.getSyncAgentState();


        System.out.println("Test complete");
    }
}
