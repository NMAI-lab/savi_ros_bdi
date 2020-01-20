package rosjava.savi_ros_java.savi_ros_bdi;

/**
 * Singleton class for passing perceptions, actions, messages between the agent and the environment.
 *
 * Based upon a class found in SAVI: https://github.com/NMAI-lab/SAVI
 *
 * @author Patrick Gavigan
 * @date 6 December 2019
 */
public class SyncAgentState {

    // Location for the perception to be stored. Replace with a queue later
    private String perception;
    private boolean perceptionAvailable;

    // Location for the action to be stored. Replace with a queue later
    private String action;
    private boolean actionAvailable;

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
        this.actionAvailable = false;
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
        if (isPerceptionAvailable()) {
            this.perceptionAvailable = false;
            return this.perception;
        } else {
            return null;
        }
    }

    public synchronized boolean isActionAvailable() {
        return this.actionAvailable;
    }

    /**
     * Provide the action the agent wants to execute (Agent side)
     * @param newAction
     */
    public synchronized void addAction(String newAction) {
        this.action = String.valueOf(newAction);
        this.actionAvailable = true;
    }

    /**
     * Get the action that the agent has requested
     */
    public synchronized String getAction() {
        if (isActionAvailable()) {
            this.actionAvailable = false;
            return this.action;
        } else {
            return null;
        }
    }
}
