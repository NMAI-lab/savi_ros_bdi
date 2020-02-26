package savi_ros_java.savi_ros_bdi.agent_state;

import jason.asSemantics.Message;
import jason.asSyntax.parser.ParseException;

public class MessageManager<Message> extends ItemManager {
    /**
     * Add message contained in messageString to the manager
     * @param messageString
     */
    public void addItem(String messageString) {

        // Check to see if anything in this string looks like a literal
        if (!validateItemString(messageString)) {
            return;
        }

        // Remove any spaces
        messageString = messageString.replace(" ", "");

        // The literal will end with a '>', use the location of the next one to extract the next literal from
        // literalString. Add it to the queue
        int bracketSpot = messageString.indexOf('>');
        String nextLiteral = messageString.substring(0, bracketSpot + 1);
        try {
            items.add(jason.asSemantics.Message.parseMsg(messageString));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Repeat for string segment that comes after 'nextPerception'
        String newMessageString = messageString.substring(bracketSpot + 1);
        this.addItem(newMessageString);
    }

    /**
     * This tests the location of the brackets to see if they make sense for a real literal
     * @return True if this contains something that looks like a literal
     */
    public boolean validateItemString(String literalString) {
        int openBracketSpot = literalString.indexOf('<');
        int closeBracketSpot = literalString.indexOf('>');
        int length = literalString.length();

        if ((openBracketSpot != 0) || ((closeBracketSpot + 1) != length)) {
            return false;
        } else if (openBracketSpot < closeBracketSpot) {
            return true;
        } else {
            return false;
        }
    }
}
