//package com.github.savi_ros_java.savi_ros_bdi;

package savi_ros_java.savi_ros_bdi;

import org.apache.commons.logging.Log;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Subscriber;
import savi_ros_java.savi_ros_bdi.agent_core.SaviAgent;
import savi_ros_java.savi_ros_bdi.ros_connectors.ActionTalker;
import savi_ros_java.savi_ros_bdi.ros_connectors.MessageListener;
import savi_ros_java.savi_ros_bdi.ros_connectors.MessageTalker;
import savi_ros_java.savi_ros_bdi.ros_connectors.PerceptionListener;

/**
 * Based upon a class found in SAVI: https://github.com/NMAI-lab/SAVI
 *
 * @author Patrick Gavigan
 * @date 6 December 2019
 */
public class SaviArchitecture {

    private SaviAgent theAgent;
    private ActionTalker actionTalker;
    private PerceptionListener perceptionListener;
    private MessageTalker messageTalker;
    private MessageListener messageListener;

    /**
     * Need a connectedNode, can't have default constructor
     */
    private SaviArchitecture(){}

    /**
     * Creates the Jason MAS Builder
     * For now there's only one type of Jason agent in the sense of its capabilities towards the environment
     * (go, stop, turn right, turn left) but each agent can have its plans. The plans should be in a file
     * type.asl where type is the agent attribute "type".
     */
    public SaviArchitecture(final ConnectedNode connectedNode) {
        // Build the agent
        this.theAgent = new SaviAgent();

        // Setup the nodes for communicating perceptions, actions and messages to and from ROS
        this.perceptionListener = new PerceptionListener(connectedNode);
        this.actionTalker = new ActionTalker(connectedNode);
        this.messageListener = new MessageListener(connectedNode);
        this.messageTalker = new MessageTalker(connectedNode);
    }

    /**
     * Start the agent.
     */
    public void startAgents() {

        // Run the agent thread
        Thread agentThread = new Thread(this.theAgent);
        agentThread.start();

        // Run the action talking thread
        Thread actionTalkingThread = new Thread(this.actionTalker);
        actionTalkingThread.start();

        // Run the perception listening thread
        Thread perceptionListeningThread = new Thread(this.perceptionListener);
        perceptionListeningThread.start();
    }

    /**
     * TODO: check what this actually does...
     */
    void stopAgents() {
        theAgent.stop();
    }
}
