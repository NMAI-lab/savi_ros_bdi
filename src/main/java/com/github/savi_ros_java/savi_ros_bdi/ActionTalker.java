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

    // The node for passing messages to
    private ConnectedNode connectedNode;

    /**
     * Default constructor - don't use
     */
    private ActionTalker() {}

    /**
     * Constructor
     * @param connectedNode Connection to the ROS node for messages
     */
    public ActionTalker(final ConnectedNode connectedNode) {

        // Set the connectedNode parameter
        this.connectedNode = connectedNode;

        // Get access to the agent state (a singleton)
        this.agentState = SyncAgentState.getSyncAgentState();
    }

    /**
     * The run process for the ActionTalker
     * It checks if there is an action and sends it to ROS.
     */
    public void run() {
        String action;
        for(;;) {
            if (this.agentState.isActionAvailable()) {
                action = String.valueOf(this.agentState.getAction());
                System.out.println("***** The Action requested was: " + action + " *****");
            }
        }
    }
}
