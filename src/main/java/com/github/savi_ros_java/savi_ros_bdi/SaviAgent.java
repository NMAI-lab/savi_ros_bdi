package com.github.rosjava.savi_ros_java.savi_ros_bdi;

import jason.architecture.AgArch;
import jason.asSemantics.*;
import jason.asSyntax.*;

import java.io.*;
import java.util.*;

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

    private final String configFilePath = "../../../resources/main/settings.cfg",

    private String name;
    private SyncAgentState agentState;
    private boolean running;

    //public SaviAgent(String id, String type) {
    public SaviAgent() {

        // Load parameters from configuration file
        Properties agentProperties = this.loadProperties();
        String aslPath = agentProperties.getProperty("ASL_PATH");
        String agentType = agentProperties.getProperty("AGENT_TYPE");
        String agentName = agentProperties.getProperty("AGENT_NAME");

        // set up the Jason agent
        try {
            Agent ag = new Agent();
            new TransitionSystem(ag, null, null, this);
            this.name = agentName;

            //InputStream aslFile = new FileInputStream("../../../resources/main/asl/" + type + ".asl");
            InputStream aslFile = new FileInputStream(aslPath);
            ag.initAg();
            ag.load(aslFile, agentType);
        } catch (Exception e) {
            System.out.println("Init error " + e.toString());
        }

        // Get the agent state
        agentState = SyncAgentState.getSyncAgentState();
    }

    /**
     * Execute the BDI reaoning cycle.
     */
    public void run() {
        System.out.println("I'm a Jason Agent and I'm starting");

        try {
            running = true;

            while (isRunning()) {
                // calls the Jason engine to perform one reasoning cycle
                System.out.println("Agent is reasoning....");
                getTS().reasoningCycle();

                if (getTS().canSleep()) {
                    sleep();
                }
            }
            System.out.println("Agent stopped.");
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

        System.out.println("I made it past the delay loop.");
        String receivedPerception = this.agentState.getPerceptions();
        System.out.println("Agent received perception: ");
        System.out.println(receivedPerception);
        Literal perceptionLiteral = Literal.parseLiteral(receivedPerception);

        // Update the history, get the list of literals to send to the agent
        List<Literal> perceptionLiterals = new ArrayList<Literal>();
        perceptionLiterals.add(perceptionLiteral);

        System.out.println("Agent Perceiving perception ");
        System.out.println(perceptionLiteral.toString());

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
     * Load the properties of the agent from the configuration file
     * @return
     */
    private Properties loadProperties() {
        System.our.println("Current directory: " + FileSystems.getDefault().getPath("."));

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

        // Fetch properties from the config file
        //String aslPath = agentProperties.getProperty("ASL_PATH");
        //String agentType = agentProperties.getProperty("AGENT_TYPE");
        //String agentName = agentProperties.getProperty("AGENT_NAME");
    }
}
