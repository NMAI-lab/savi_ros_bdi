package com.github.savi_ros_java.savi_ros_bdi;

import java.util.HashMap;
import java.util.Map;

import com.github.rosjava.savi_ros_java.savi_ros_bdi.SaviAgent;
//import savi.simulation.model.AgentModel;

public class SaviAgentArch {

    //private Map<String, AgentModel> theAgentModels;
    private SaviAgent theAgent;

    private SyncAgentState agentState;

    public void JasonMAS() {
        this(new HashMap<String, AgentModel>());
    }

    /**
     * Creates the Jason MAS Builder
     * For now there's only one type of Jason agent in the sense of its capabilities towards the environment (go, stop, turn right, turn left)
     * but each agent can have its plans. The plans should be in a file type.asl where type is the agent attribute "type".
     *
     * @param agents
     */
    public SaviAgentArch() {
        this.agentState = new SyncAgentState();
        this.theAgent = new SaviAgent("0", "demo", agentState);

        //Thread t1 = new Thread(theAgent);
        //am.setAgentThread(t1);
    }

    /**
     * Get the agent state object, used for passing data between the agent and ROS
     */
    public SyncAgentState getAgentState() {
        // Deal with the null case (basically impossible)
        if (this.agentState == null) {
            this.agentState = new SyncAgentState();
        }

        // Return pointer to agent state
        return this.agentState;
    }


    /*
    public void startAgents() {
        //for (String AgId : theAgentModels.keySet()) {
        //    Thread t1 = theAgentModels.get(AgId).getAgentThread();
        //    t1.start();

        //}
    }
    */

    /**
     * TODO: check what this actually does...
     */
    void stopAgents() {
        theAgent.stop();
    }
}
