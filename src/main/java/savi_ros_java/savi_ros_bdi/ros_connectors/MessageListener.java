package savi_ros_java.savi_ros_bdi.ros_connectors;

import org.apache.commons.logging.Log;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;
import savi_ros_java.savi_ros_bdi.agent_state.SyncAgentState;
import std_msgs.String;

public class MessageListener implements Runnable {

    // The node for passing messages to
    private ConnectedNode connectedNode;
    private Log log;
    private Subscriber<String> subscriber;

    private MessageListener() { }

    public MessageListener(final ConnectedNode connectedNode) {
        // Set the connectedNode parameter
        this.connectedNode = connectedNode;

        log = connectedNode.getLog();
        subscriber = connectedNode.newSubscriber("inbox", std_msgs.String._TYPE);
    }

    public void run() {
        subscriber.addMessageListener(new org.ros.message.MessageListener<String>() {
            @Override
            public void onNewMessage(std_msgs.String message) {
                // Handle the message
                SyncAgentState agentState = SyncAgentState.getSyncAgentState();
                agentState.addToInbox(message.getData());
                log.info("Inbox received: " + message.getData());
            }
        });
    }
}