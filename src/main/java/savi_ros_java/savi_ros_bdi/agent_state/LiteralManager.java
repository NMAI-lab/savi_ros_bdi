/**
 * A class for managing a list of literals.
 *
 * @author Patrick Gavigan
 * @date 6 February 2020
 */

package savi_ros_java.savi_ros_bdi.agent_state;

public class LiteralManager<Literal> extends ItemManager {

    /**
     * Add literals contained in literalString to the manager
     * @param literalString
     */
    public void addItem(String literalString) {

        // Check to see if anything in this string looks like a literal
        if (!validateItemString(literalString)) {
            // node.getLog.error("error message");      // TODO: Use logger error messages for this. http://rosjava.github.io/rosjava_core/hydro/getting_started.html#logging
            System.out.println("ERROR: literal Validation:" + literalString);
            return;
        }

        // Remove any spaces
        literalString = literalString.replace(" ", "");

        // Deal with double brackets around negative numbers
        literalString = literalString.replace("((", "(");
        literalString = literalString.replace("))", ")");
        literalString = literalString.replace("),(", ",");
        literalString = literalString.replace(",(", ",");
        literalString = literalString.replace("),", ",");

        // The literal will end with a ')', use the location of the next one to extract the next literal from
        // literalString. Add it to the queue
        int bracketSpot = literalString.indexOf(')');
        String nextLiteral = literalString.substring(0, bracketSpot + 1);
        items.add(jason.asSyntax.Literal.parseLiteral(literalString));

        // Repeat for string segment that comes after 'nextPerception'
        String newLiteralString = literalString.substring(bracketSpot + 1);
        if (newLiteralString.length() > 0) {
            this.addItem(newLiteralString);
        }
    }



    /**
     * This tests the location of the brackets to see if they make sense for a real literal
     * @return True if this contains something that looks like a literal
     */
    public boolean validateItemString(String literalString) {
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
