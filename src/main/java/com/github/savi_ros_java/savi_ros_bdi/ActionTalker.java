package com.github.rosjava.savi_ros_java.savi_ros_bdi;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

public class ActionTalker implements Runnable {

    // Agent state object - where the actions will come from
    private SyncAgentState agentState;

    /**
     * Default constructor - don't use
     */
    private ActionTalker() {}

    /**
     * Constructor
     * @param connectedNode Connection to the ROS node for messages
     */
    public ActionTalker(final ConnectedNode connectedNode) {

        // Get access to the agent state (a singleton)
        this.agentState = SyncAgentState.getSyncAgentState();

        System.out.println("***** Built the action talker object. *****");

    }

    public void run() {
        System.out.println("***** The Action talker made it to the run method. *****");
    }

}
