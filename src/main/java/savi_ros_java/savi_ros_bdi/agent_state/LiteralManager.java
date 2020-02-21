/**
 * A class for managing a list of literals.
 *
 * @author Patrick Gavigan
 * @date 6 February 2020
 */

package savi_ros_java.savi_ros_bdi.agent_state;

import jason.asSyntax.Literal;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LiteralManager {

    /**
     * List of literals being managed.
     */
    private Queue<Literal> literals;

    /**
     * Default constructor.
     */
    public LiteralManager() {
        this.literals = new LinkedList<>();
    }

    /**
     * Check if there are any literals in the list.
     * @return
     */
    public boolean isLiteralAvailable() {
        return !this.literals.isEmpty();
    }

    /**
     * Add literals contained in literalString to the manager
     * @param literalString
     */
    public void addLiteral(String literalString) {

        // Check to see if anything in this string looks like a literal
        if (!validateLiteralString(literalString)) {
            return;
        }

        // The literal will end with a ')', use the location of the next one to extract the next literal from
        // literalString. Add it to the queue
        int bracketSpot = literalString.indexOf(')');
        String nextLiteral = literalString.substring(0, bracketSpot + 1);
        literals.add(convertToLiteral(nextLiteral));

        // Repeat for string segment that comes after 'nextPerception'
        String newLiteralString = literalString.substring(bracketSpot + 1);
        this.addLiteral(newLiteralString);
    }


    /**
     * Convert a single literal in String form to an instance of the Literal class.
     * Removes any spaces that should not be there, and addressed issues with brackets around negative numbers.
     * @param   literalString
     * @return  Literal
     */
    private Literal convertToLiteral(String literalString) {

        // Remove any spaces
        literalString = literalString.replace(" ", "");

        // Deal with double brackets around negative numbers
        // Find every instance of a negative number
        String negativeCharacter = "-";
        int index = literalString.indexOf(negativeCharacter);
        while (index >= 0) {
            index = literalString.indexOf(negativeCharacter, index + 1);

            // Check if there is a bracket before the negative sign

        }

        // Make the literal and return
        return Literal.parseLiteral(literalString);
    }

    /**
     * Get a copy of the literal list and clear contents
     * @return
     */
    public List<Literal> getLiterals() {
        List<Literal> returnList = new LinkedList<>(this.literals);
        this.literals = new LinkedList<>();
        return returnList;
    }

    public Literal getNextLiteral() {
        if (this.isLiteralAvailable()) {
            return this.literals.remove();
        } else {
            return null;
        }
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
