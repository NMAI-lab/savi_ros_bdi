//package com.github.savi_ros_java.savi_ros_bdi;

package com.github.rosjava.savi_ros_java.savi_ros_bdi;

/**
 * Based upon a class found in SAVI: https://github.com/NMAI-lab/SAVI
 *
 * @author Patrick Gavigan
 * @date 6 December 2019
 */
public class SaviAgentArch {

    private SaviAgent theAgent;
    private ActionTalker actionTalker;

    /**
     * Need a connectedNode, can't have default constructor
     */
    private SaviAgentArch();

    /**
     * Creates the Jason MAS Builder
     * For now there's only one type of Jason agent in the sense of its capabilities towards the environment
     * (go, stop, turn right, turn left) but each agent can have its plans. The plans should be in a file
     * type.asl where type is the agent attribute "type".
     */
    public SaviAgentArch(final ConnectedNode connectedNode) {
        // Build the agent
        this.theAgent = new SaviAgent("0", "demo");

        // Setup the action talker, for replying with actions at the end of the reasoning cycle
        this.actionTalker = new ActionTalker(connectedNode);
    }

    /**
     * Start the agent.
     */
    public void startAgents() {

        // Run the agent thread
        Thread agentThread = new Thread(this.theAgent);
        agentThread.start();

        // Run the action talking thread
        Thread actionTalingThread = new Thread(this.actionTalker);
        actionTalingThread.start();
    }

    /**
     * TODO: check what this actually does...
     */
    void stopAgents() {
        theAgent.stop();
    }
}
