package savi_ros_java.savi_ros_bdi.agent_core;

import jason.architecture.AgArch;
import jason.asSemantics.*;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import org.w3c.dom.Document;
import savi_ros_java.savi_ros_bdi.agent_state.SyncAgentState;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

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

    private String configFilePath;
    private String name;
    private SyncAgentState agentState;
    private boolean running;

    //public SaviAgent(String id, String type) {
    public SaviAgent() {
        this("../../../resources/main/settings.cfg");
        // Load parameters from configuration file
        //Properties agentProperties = this.loadProperties();

        // Load the agent
        //this.loadAgent(agentProperties);

        // Get the agent state
        //agentState = SyncAgentState.getSyncAgentState();
    }

    public SaviAgent(String configPath) {
        this.configFilePath = new String(configPath);

        // Load parameters from configuration file
        Properties agentProperties = this.loadProperties();

        // Load the agent
        this.loadAgent(agentProperties);

        // Get the agent state
        agentState = SyncAgentState.getSyncAgentState();
    }

    /**
     * Execute the BDI reasoning cycle.
     */
    public void run() {
        System.out.println("I'm a Jason agent_core and I'm starting");

        try {
            running = true;

            while (isRunning()) {
                // calls the Jason engine to perform one reasoning cycle

                // This should be a logger print
                System.out.println("agent_core is reasoning....");
                long reasoningStartTime = System.currentTimeMillis();

                // Put mind inspector log here (or before the performance log)
                getTS().reasoningCycle();
                // Put mind inspector log here (or after the performance log)

                Long elapsed = new Long(System.currentTimeMillis() - reasoningStartTime);
                this.agentState.addPerformanceMeasure(elapsed.toString());

                //if (getTS().canSleep()) {
                //    sleep();
                //}
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

        // Add the action to agentState if it is longer than 0 characters
        if (actionString.length() > 0) {
            agentState.addAction(actionString);
        }

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
            Thread.sleep(1);                    // TODO: Need to revisit this
        } catch (InterruptedException e) {
        }
    }

    /**
     * Send message to another agent (via simulated wifi).
     */
    @Override
    public void sendMsg(Message m) throws Exception {
        // Make sure sender parameter is set
        if (m.getSender() == null)  m.setSender(getAgName());

        // Put the message in the wifi queue
        this.agentState.addToOutbox(m.toString());
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
        System.out.println("Config file path: " + configFileName);

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
            Agent ag = new SaviAgentBrain();
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

    /**
     * Adding logging of the agent mind state
     * -- Needs improvement - adjust the logging levels
     * -- Verify that the content is what we are looking for
     * -- Figure out when to call this - Looks like it should be at start of reasoning cycle and at end of reasoning cycle
     * -- https://github.com/jason-lang/jason/blob/master/src/main/java/jason/architecture/MindInspectorAgArch.java#L320
     */
    public void getAgentMind() {
        Document state = this.getTS().getAg().getAgState();
        String mindString = state.toString();
        Logger logger = this.getTS().getLogger();
        logger.info(mindString);
    }
}
