package com.github.rosjava.savi_ros_java.savi_ros_bdi;

/**
 * Singleton class for passing perceptions, actions, messages between the agent and the environment.
 */
public class SyncAgentState {
    private String perception;
    private boolean perceptionAvailable;

    // Static instance of this singleton class
    private static SyncAgentState agentState;


    /**
     * Mehthod for accessing this singleton class
     * @return      Reference to the singleton object
     */
    public static SyncAgentState getSyncAgentState() {
        if (SyncAgentState.agentState == null) {
            SyncAgentState.agentState = new SyncAgentState();

        }
        return SyncAgentState.agentState;
    }


    /**
     *  Constructor for the SyncAgentState class.
     *  This is a private constructor as this is a singleton class.
     */
    private SyncAgentState() {
        this.perceptionAvailable = false;
    }


    /**
     * Check if there is a fresh perception.
     * @return
     */
    public synchronized boolean isPerceptionAvailable() {
        return this.perceptionAvailable;
    }

    /**
     * Set the perceptions, as received from the sensors (environment side)
     * @param newPerception
     */
    public synchronized void setPerceptions(String newPerception) {
        this.perception = String.valueOf(newPerception);
        this.perceptionAvailable = true;
    }

    /**
     * Get the perceptions. (Agent side)
     * @return
     */
    public synchronized String getPerceptions() {
        this.perceptionAvailable = false;
        return this.perception;
    }

    /**
     * Provide the action the agent wants to execute (Agent side)
     * @param action
     */
    public synchronized void addAction(String action) {
        System.out.println("Action to execute: ");
        System.out.println(action);
    }
}
