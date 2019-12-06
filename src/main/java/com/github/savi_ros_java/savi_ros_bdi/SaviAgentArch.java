//package com.github.savi_ros_java.savi_ros_bdi;

package com.github.rosjava.savi_ros_java.savi_ros_bdi;

import java.util.HashMap;
import java.util.Map;


public class SaviAgentArch {

    //private Map<String, AgentModel> theAgentModels;
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

        Thread t1 = new Thread(theAgent);
        am.setAgentThread(t1);
    }

    /**
     * TODO: check what this actually does...
     */
    public void startAgents() {
        /*
        for (String AgId : theAgentModels.keySet()) {
            Thread t1 = theAgentModels.get(AgId).getAgentThread();
            t1.start();
        }
        */
    }

    /**
     * TODO: check what this actually does...
     */
    void stopAgents() {
        theAgent.stop();
    }
}
