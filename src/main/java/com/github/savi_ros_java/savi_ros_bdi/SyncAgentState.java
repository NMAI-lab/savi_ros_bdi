package com.github.rosjava.savi_ros_java.savi_ros_bdi;

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
     */
    private SyncAgentState() {
        this.perceptionAvailable = false;
    }


    public synchronized boolean isPerceptionAvailable() {
        return this.perceptionAvailable;
    }

    public synchronized void setPerceptions(String newPerception) {
        this.perception = String.valueOf(newPerception);
        this.perceptionAvailable = true;
    }

    /**
     * Get the perceptions.
     * @return
     */
    public synchronized String getPerceptions() {
        this.perceptionAvailable = false;
        return this.perception;
    }


    public synchronized void addAction(String action) {
        System.out.println("Action to execute: ");
        System.out.println(action);
    }
}
