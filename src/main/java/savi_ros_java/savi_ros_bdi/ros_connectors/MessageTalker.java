package savi_ros_java.savi_ros_bdi.ros_connectors;

import org.ros.concurrent.CancellableLoop;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import savi_ros_java.savi_ros_bdi.agent_state.SyncAgentState;
import std_msgs.String;

public class MessageTalker implements Runnable {

    // The node for passing messages to
    private ConnectedNode connectedNode;

    private Publisher<String> publisher;

    /**
     * Default constructor - don't use
     */
    private MessageTalker() {
    }

    /**
     * Constructor
     *
     * @param connectedNode Connection to the ROS node for messages
     */
    public MessageTalker(final ConnectedNode connectedNode) {
        // Set the connectedNode parameter
        this.connectedNode = connectedNode;

        // Setup the publisher
        this.publisher = connectedNode.newPublisher("outbox", std_msgs.String._TYPE);
    }

    /**
     * The run process for the MessageTalker
     * It checks if there is a message and sends it to ROS.
     * Based on the Talker.java tutorial from ROS.
     */
    public void run() {

        // This CancellableLoop will be canceled automatically when the node shuts down.
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            protected void loop() throws InterruptedException {
                SyncAgentState agentState = SyncAgentState.getSyncAgentState();
                if (agentState.checkOutboxMailAvailable()) {        // Check for an message
                    java.lang.String message = java.lang.String.valueOf(agentState.getOutboxMessage());   // Get the message
                    System.out.println("Sending outbox message: " + message);
                    std_msgs.String str = publisher.newMessage();   // Build a new message
                    str.setData(message);                           // Set the action as the message
                    publisher.publish(str);                         // Send the message
                }
            }
        });
    }
}
