package savi_ros_java.savi_ros_bdi.agent_state;

import jason.asSyntax.Literal;

import java.util.List;

/**
 * Singleton class for passing perceptions, actions, messages between the agent and the environment.
 *
 * Based upon a class found in SAVI: https://github.com/NMAI-lab/SAVI
 *
 * @author Patrick Gavigan
 * @date 6 December 2019
 */
public class SyncAgentState {

    // Location for the perceptions and actions to be stored.
    private LiteralManager perceptionManager;
    private LiteralManager actionManager;

    // Static instance of this singleton class
    private static SyncAgentState agentState;

    /**
     * Method for accessing this singleton class
     * @return      Reference to the singleton object
     */
    public static synchronized SyncAgentState getSyncAgentState() {
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
        this.actionManager = new LiteralManager();
        this.perceptionManager = new LiteralManager();
    }


    /**
     * Check if there is a fresh perception.
     * @return
     */
    public synchronized boolean isPerceptionAvailable() {
        return this.perceptionManager.isItemAvailable();
    }

    /**
     * Set the perceptions, as received from the sensors (environment side)
     * @param newPerception
     */
    public synchronized void setPerceptions(String newPerception) {
        this.perceptionManager.addItem(newPerception);
    }

    /**
     * Get the perceptions. (agent_core side)
     * @return
     */
    public synchronized List<Literal> getPerceptions() {
        if (isPerceptionAvailable()) {
            return this.perceptionManager.getItemList();
        } else {
            return null;
        }
    }

    public synchronized boolean isActionAvailable() {
        return this.actionManager.isItemAvailable();
    }

    /**
     * Provide the action the agent wants to execute (agent_core side)
     * @param newAction
     */
    public synchronized void addAction(String newAction) {
        this.actionManager.addItem(newAction);
    }

    /**
     * Get the next action that the agent has requested
     */
    public synchronized String getAction() {
        if (isActionAvailable()) {
            return this.actionManager.getNextItem().toString();
        } else {
            return null;
        }
    }
}
