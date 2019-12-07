//package com.github.savi_ros_java.savi_ros_bdi;

package com.github.rosjava.savi_ros_java.savi_ros_bdi;

public class SaviAgentArch {

    private SaviAgent theAgent;


    /**
     * Creates the Jason MAS Builder
     * For now there's only one type of Jason agent in the sense of its capabilities towards the environment
     * (go, stop, turn right, turn left) but each agent can have its plans. The plans should be in a file
     * type.asl where type is the agent attribute "type".
     *
     * @param agents
     */
    public SaviAgentArch() {
        this.theAgent = new SaviAgent("0", "demo");
    }

    /**
     * Start the agent.
     */
    public void startAgents() {
        Thread t1 = new Thread(this.theAgent);
        t1.start();
    }

    /**
     * TODO: check what this actually does...
     */
    void stopAgents() {
        theAgent.stop();
    }
}
