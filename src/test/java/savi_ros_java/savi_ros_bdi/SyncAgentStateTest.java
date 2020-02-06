package savi_ros_java.savi_ros_bdi;

//import static savi_ros_java.savi_ros_bdi.SyncAgentState;

import java.io.*;
import java.util.*;

import jason.asSyntax.Literal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SyncAgentStateTest {



    @Test
    void perceptionBufferTest() {

        List<Literal> perceptionQueue = new LinkedList<>();
        String testPerception = "test(a) test(b) test(c)";
        addNextLiteral(perceptionQueue, testPerception);
        System.out.println(perceptionQueue.toString());
    }


    public List<Literal> getLiteralList(List<Literal> perceptionQueue) {
        List<Literal> returnList = new LinkedList<>(perceptionQueue);
        perceptionQueue = new LinkedList<>();
        return returnList;
    }

    public void addNextLiteral(List<Literal> perceptionQueue, String literalString) {

        // Check to see if anything in this string looks like a literal
        if (!validateLiteralString(literalString)) {
            return;
        }

        // The literal will end with a ')', use the location of the next one to extract the next literal from
        // literalString. Add it to the queue
        int bracketSpot = literalString.indexOf(')');
        String nextPerception = literalString.substring(0, bracketSpot + 1);
        perceptionQueue.add(Literal.parseLiteral(nextPerception));

        // Repeat for string segment that comes after 'nextPerception'
        String newLiteralString = literalString.substring(bracketSpot + 1);
        addNextLiteral(perceptionQueue, newLiteralString);
    }


    /**
     * This tests the location of the brackets to see if they make sense for a real literal
     * @return True if this contains something that looks like a literal
     */
    public boolean validateLiteralString(String literalString) {
        int openBracketSpot = literalString.indexOf('(');
        int closeBracketSpot = literalString.indexOf(')');

        if ((openBracketSpot <= 0) || (closeBracketSpot <= 0)) {
            return false;
        } else if (openBracketSpot < closeBracketSpot) {
            return true;
        } else {
            return false;
        }
    }

}