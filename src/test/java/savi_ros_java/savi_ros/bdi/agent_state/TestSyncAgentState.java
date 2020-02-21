package savi_ros_java.savi_ros.bdi.agent_state;

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
        literalManager.addLiteral("test(case)");
        literals = literalManager.getLiterals();
        System.out.println(literals.toString());

        // Add something with two literals and see what happens
        literalManager.addLiteral("test(case) another(test)");
        literals = literalManager.getLiterals();
        System.out.println(literals.toString());

        // Add something with two literals and see what happens
        literalManager.addLiteral("test(case)testAnother(test, moreStuff)");
        literals = literalManager.getLiterals();
        System.out.println(literals.toString());

        // Add something with numbers
        literalManager.addLiteral("test(4)testAnother(-5, -6, -7)");
        literals = literalManager.getLiterals();
        System.out.println(literals.toString());

        // Add something with numbers and double brackets
        literalManager.addLiteral("test(4)testAnother((-5), (-6), (-7))");
        literals = literalManager.getLiterals();
        System.out.println(literals.toString());

        String angularVelocity = "angularVelocity(1,1,1,1)";
        String linearAcceleration = "linearAcceleration(1,1,1,1)";
        String orientation = "orientation(1,1,1,1,1)";
        String perception = angularVelocity + " " + linearAcceleration + " " + orientation;

        literalManager.addLiteral(perception);
        literals = literalManager.getLiterals();
        System.out.println(literals.toString());



        //SyncAgentState agentState = SyncAgentState.getSyncAgentState();


        System.out.println("Test complete");
    }
}
