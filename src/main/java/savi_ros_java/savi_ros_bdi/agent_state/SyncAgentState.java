package savi_ros_java.savi_ros_bdi.agent_state;

import jason.asSyntax.Literal;
import jason.asSemantics.Message;

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
    private LiteralManager perceptions;
    private LiteralManager actions;
    private MessageManager inbox;
    private MessageManager outbox;

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
        this.actions = new LiteralManager();
        this.perceptions = new LiteralManager();
        this.inbox = new MessageManager();
        this.outbox = new MessageManager();
    }


    /**
     * Check if there is a fresh perception.
     * @return
     */
    public synchronized boolean isPerceptionAvailable() {
        return this.perceptions.isItemAvailable();
    }

    /**
     * Set the perceptions, as received from the sensors (environment side)
     * @param newPerception
     */
    public synchronized void setPerceptions(String newPerception) {
        this.perceptions.addItem(newPerception);
    }

    /**
     * Get the perceptions. (agent_core side)
     * @return
     */
    public synchronized List<Literal> getPerceptions() {
        if (isPerceptionAvailable()) {
            return this.perceptions.getItemList();
        } else {
            return null;
        }
    }

    public synchronized boolean isActionAvailable() {
        return this.actions.isItemAvailable();
    }

    /**
     * Provide the action the agent wants to execute (agent_core side)
     * @param newAction
     */
    public synchronized void addAction(String newAction) {
        this.actions.addItem(newAction);
    }

    /**
     * Get the next action that the agent has requested
     */
    public synchronized String getAction() {
        if (isActionAvailable()) {
            return this.actions.getNextItem().toString();
        } else {
            return null;
        }
    }

    /**
     * Check if there is a fresh messages.
     * @return
     */
    public synchronized boolean checkInboxMailAvailable() {
        return this.inbox.isItemAvailable();
    }

    /**
     * Set the perceptions, as received from the sensors (environment side)
     * @param newMail
     */
    public synchronized void addToInbox(String newMail) {
        this.inbox.addItem(newMail);
    }

    /**
     * Get the perceptions. (agent_core side)
     * @return
     */
    public synchronized List<Message> getInbox() {
        if (checkInboxMailAvailable()) {
            return this.inbox.getItemList();
        } else {
            return null;
        }
    }


    public synchronized boolean checkOutboxMailAvailable() {
        return this.outbox.isItemAvailable();
    }

    /**
     * Provide the action the agent wants to execute (agent_core side)
     * @param newMail
     */
    public synchronized void addToOutbox(String newMail) {
        //System.out.println("Adding to Outbox!!!!!!!!!!!!!!:::  " + newMail);
        this.outbox.addItem(newMail);
        //System.out.println("Check main available!!!!!!!!!::: " + this.checkOutboxMailAvailable());
    }

    /**
     * Get the next action that the agent has requested
     */
    public synchronized String getOutboxMessage() {
        if (checkOutboxMailAvailable()) {
            System.out.println("Returning mail !!!!!!");
            return this.outbox.getNextItem().toString();
        } else {
            return null;
        }
    }
}
