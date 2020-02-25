package savi_ros_java.savi_ros_bdi.ros_connectors;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import savi_ros_java.savi_ros_bdi.agent_state.SyncAgentState;

public class ActionTalker implements Runnable {

    // The node for passing messages to
    private ConnectedNode connectedNode;

    private Publisher<std_msgs.String> publisher;

    /**
     * Default constructor - don't use
     */
    private ActionTalker() {
    }

    /**
     * Constructor
     *
     * @param connectedNode Connection to the ROS node for messages
     */
    public ActionTalker(final ConnectedNode connectedNode) {
        // Set the connectedNode parameter
        this.connectedNode = connectedNode;

        // Setup the publisher
        this.publisher = connectedNode.newPublisher("actions", std_msgs.String._TYPE);
    }

    /**
     * The run process for the ActionTalker
     * It checks if there is an action and sends it to ROS.
     * Based on the Talker.java tutorial from ROS.
     */
    public void run() {

        // This CancellableLoop will be canceled automatically when the node shuts down.
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            protected void loop() throws InterruptedException {
                SyncAgentState agentState = SyncAgentState.getSyncAgentState();
                if (agentState.isActionAvailable()) {                  // Check for an action
                    String action = String.valueOf(agentState.getAction());   // Get the action
                    System.out.println("Sending action message: " + action);
                    std_msgs.String str = publisher.newMessage();           // Build a new message
                    str.setData(action);                                    // Set the action as the message
                    publisher.publish(str);                                 // Send the message
                }
            }
        });
    }
}
