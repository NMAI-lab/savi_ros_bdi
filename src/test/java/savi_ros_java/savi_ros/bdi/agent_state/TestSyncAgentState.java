package savi_ros_java.savi_ros.bdi.agent_state;

import jason.asSemantics.Message;
import jason.asSyntax.Literal;
import junit.framework.TestCase;

import org.junit.jupiter.api.Test;
import savi_ros_java.savi_ros_bdi.agent_state.LiteralManager;
import savi_ros_java.savi_ros_bdi.agent_state.SyncAgentState;

import java.util.List;

public class TestSyncAgentState extends TestCase {

    @Test
    public void testCase() {

        System.out.println("Starting the TestSyncAgentState testCase");

        LiteralManager literalManager = new LiteralManager();
        List<Literal> literals;

        // Start with something simple
        literalManager.addItem("test(case)");
        literals = literalManager.getItemList();
        System.out.println(literals.toString());

        // Add something with two literals and see what happens
        literalManager.addItem("test(case) another(test)");
        literals = literalManager.getItemList();
        System.out.println(literals.toString());

        // Add something with two literals and see what happens
        literalManager.addItem("test(case)testAnother(test, moreStuff)");
        literals = literalManager.getItemList();
        System.out.println(literals.toString());

        // Add something with numbers
        literalManager.addItem("test(4)testAnother(-5, -6, -7)");
        literals = literalManager.getItemList();
        System.out.println(literals.toString());

        // Add something with numbers and double brackets
        literalManager.addItem("test(4)testAnother((-5), (-6), (-7))");
        literals = literalManager.getItemList();
        System.out.println(literals.toString());

        String angularVelocity = "angularVelocity(1,1,1,1)";
        String linearAcceleration = "linearAcceleration(1,1,1,1)";
        String orientation = "orientation(1,1,1,1,1)";
        String perception = angularVelocity + linearAcceleration + orientation;

        literalManager.addItem(perception);
        literals = literalManager.getItemList();
        System.out.println(literals.toString());

        SyncAgentState agentState = SyncAgentState.getSyncAgentState();
        assertFalse("Checking that empty agentState has no perception available", agentState.isPerceptionAvailable());
        agentState.setPerceptions(perception);
        assertTrue("Checking that agentState has perception available after adding", agentState.isPerceptionAvailable());
        List<Literal> perceptionsList = agentState.getPerceptions();
        System.out.println(perceptionsList.toString());

        assertFalse("Checking that empty agentState has no inbox message available", agentState.checkInboxMailAvailable());
        agentState.addToInbox("<1582740868.91,2,tell,0,anotherTime(1582740868.91)>");
        assertTrue("Checking that agentState has inbox available after adding", agentState.checkInboxMailAvailable());
        List<Message> messageList = agentState.getInbox();
        System.out.println(messageList.toString());

        // Test case with batteryOK perception (no brackets scenario)
        agentState.setPerceptions("battery(OK)");
        assertTrue("Checking that agentState has perception available after adding", agentState.isPerceptionAvailable());
        perceptionsList = agentState.getPerceptions();
        System.out.println(perceptionsList.toString());



        System.out.println("Test complete");
    }
}
