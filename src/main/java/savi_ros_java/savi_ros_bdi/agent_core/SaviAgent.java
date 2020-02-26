package savi_ros_java.savi_ros_bdi.agent_core;

import jason.architecture.AgArch;
import jason.asSemantics.*;
import jason.asSyntax.*;
import savi_ros_java.savi_ros_bdi.agent_state.SyncAgentState;

import java.io.*;
import java.util.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * The agent class. This is responsible for the agent reasoning cycle with the Jason bDI engine.
 * It runs without all the Jason IDE stuff. (see Jason FAQ)
 *
 * Based upon a class found in SAVI: https://github.com/NMAI-lab/SAVI
 *
 * @author Patrick Gavigan
 * @date 6 December 2019
 */
public class SaviAgent extends AgArch implements Runnable {
    private static final String broadcastID = "BROADCAST";

    private final String configFilePath = "../../../resources/main/settings.cfg";

    private String name;
    private SyncAgentState agentState;
    private boolean running;

    //public SaviAgent(String id, String type) {
    public SaviAgent() {

        // Load parameters from configuration file
        Properties agentProperties = this.loadProperties();

        // Load the agent
        this.loadAgent(agentProperties);

        // Get the agent state
        agentState = SyncAgentState.getSyncAgentState();
    }

    /**
     * Execute the BDI reaoning cycle.
     */
    public void run() {
        System.out.println("I'm a Jason agent_core and I'm starting");

        try {
            running = true;

            while (isRunning()) {
                // calls the Jason engine to perform one reasoning cycle
                System.out.println("agent_core is reasoning....");
                getTS().reasoningCycle();

                if (getTS().canSleep()) {
                    sleep();
                }
            }
            System.out.println("agent_core stopped.");
        } catch (Exception e) {
            System.out.println("Run error " + e.toString());
        }
    }

    /**
     * Get the agent's name
     * @return
     */
    public String getAgName() {
        return name;
    }


    /**
     * Get the perceptions for the agent. This is automatically called by the agent's reasoning cycle.
     * @return
     */
    @Override
    public List<Literal> perceive() {

        System.out.println("I'm in the perceive method");
        while(this.agentState.isPerceptionAvailable() == false) {
            sleep();       // Wait until there is a perception
        }

        // Get the list of literals to send to the agent
        List<Literal> perceptionLiterals = this.agentState.getPerceptions();
        System.out.println("agent_core Perceiving: ");
        System.out.println(perceptionLiterals.toString());

        return perceptionLiterals;
    }

    /**
     * This method gets the agent actions. This is automatically called by the agent's reasoning cycle.
     */
    @Override
    public void act(ActionExec action) {
        // Get the action term
        Structure actionTerm = action.getActionTerm();
        String actionString = actionTerm.toString();

        // Add the action to agentState
        agentState.addAction(actionString);

        // Set that the execution was OK and flag it as complete.
        action.setResult(true);
        actionExecuted(action);
    }

    /**
     * Determine if the agent can sleep
     * @return
     */
    @Override
    public boolean canSleep() {
        return !agentState.isPerceptionAvailable();
    }

    /**
     * Determine if the agent is running
     * @return
     */
    @Override
    public boolean isRunning() {
        return running;
    }

    /**
     * Stop the agent
     */
    @Override
    public void stop() {
        running = false;
    }

    /**
     * A very simple implementation of sleep
     */
    public void sleep() {
        //logger.log(Level.FINE, "Snoozing");
        //System.out.println("Snoozing");
        try {
            Thread.sleep(100);                    // TODO: Need to revisit this
        } catch (InterruptedException e) {
        }
    }

    /**
     * Send message to another agent (via simulated wifi).
     */
    @Override
    public void sendMsg(Message m) throws Exception {
        System.out.println("I'm broadcasting!!!!!!!!!!!!!!!!!!!!");
        System.out.println(m.toString());

        // Make sure sender parameter is set
        if (m.getSender() == null)  m.setSender(getAgName());

        System.out.println("Made it past sender check!!!!!!!!!!!!!!!!!!!");

        // Put the message in the wifi queue
        this.agentState.addToOutbox(m.toString());

        System.out.println("Made it past add to outbox!!!!!!!!!!!!!!!!!!!");


    }

    /**
     * in case agent is sleeping
     * TODO: in case we get problems of agents not waking up on messages, this is what to use!!
     * */
    public void wakeAgent() {
        wakeUpSense();
    }

    @Override
    public void broadcast(Message m) throws Exception {
        m.setReceiver(broadcastID);
        this.sendMsg(m);
    }

    @Override
    public void checkMail() {
        if (agentState.checkInboxMailAvailable()) {
            Circumstance circ = getTS().getC();
            List<Message> messages = this.agentState.getInbox();
            for (Message currentMessage:messages) {
                if (currentMessage.getReceiver().equals(broadcastID) || currentMessage.getReceiver().equals(this.getAgName())) {
                    circ.addMsg(currentMessage);
                }
            }
        }
    }

    /**
     * Load the properties of the agent from the configuration file
     * @return
     */
    private Properties loadProperties() {
        Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
        System.out.println("Current directory: " + path.toString());

        // Load parameters from configuration file
        InputStream input = null;
        Properties agentProperties = new Properties();
        String configFileName = this.configFilePath;

        try {
            input = new FileInputStream(configFileName);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Exception occurred");
            System.out.println(e.toString());
        }

        try{
            agentProperties.load(input);
        } catch (Exception e) {
            System.out.println("Exception occurred");
            System.out.println(e.toString());
        }

        return agentProperties;
    }

    /**
     * Set up the agent usign the provided properties
     * @param agentProperties
     */
    private void loadAgent(Properties agentProperties) {

        // Get the properteis
        String aslPath = agentProperties.getProperty("ASL_PATH");
        String agentType = agentProperties.getProperty("AGENT_TYPE");
        String agentName = agentProperties.getProperty("AGENT_NAME");

        // Set up the Jason agent
        try {
            Agent ag = new Agent();
            new TransitionSystem(ag, null, null, this);
            this.name = agentName;

            System.out.println("ASL_PATH: " + aslPath);
            InputStream aslFile = new FileInputStream(aslPath);
            ag.initAg();
            ag.load(aslFile, agentType);
        } catch (Exception e) {
            System.out.println("Init error loading asl file: " + e.toString());
            System.out.println("ASL Path was: " + aslPath);
        }
    }
}
