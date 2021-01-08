// Internal action code for project search.mas2j

package savi_ros_java.savi_ros_bdi.navigation;

import NavigationSupport.*;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class getPath extends DefaultInternalAction {

	public static final int currentIndex = 0;
	public static final int destinationIndex = 1;
	public static final int pathIndex = 2;
	
    @Override

    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'NavigationInternalAction.getPath'");
        
		try {
			// Get the parameters
			Term currentPositionTerm = (Term) args[currentIndex];
			Term destinationPositionTerm = (Term) args[destinationIndex];
			
			String currentPositionString;
			String destinationPositionString;
			
			if (currentPositionTerm.isString()) {
				StringTerm currentPositionStringTerm = (StringTerm)currentPositionTerm;
				currentPositionString = currentPositionStringTerm.getString();
			} else if (currentPositionTerm.isAtom()) {
				Atom currentPositionAtom = (Atom)currentPositionTerm;
				currentPositionString = currentPositionAtom.getFunctor();
			} else {
				throw new JasonException("currentPosition parameter format not supported.");
			}
			
			if (destinationPositionTerm.isString()) {
				StringTerm destinationPositionStringTerm = (StringTerm)destinationPositionTerm;
				destinationPositionString = destinationPositionStringTerm.getString();
			} else if (destinationPositionTerm.isAtom()) {
				Atom destinationPositionAtom = (Atom)destinationPositionTerm;
				destinationPositionString = destinationPositionAtom.getFunctor();
			} else {
				throw new JasonException("destinationPosition parameter format not supported.");
			}
			
			// Get the result
			String path = MapSearchFunctions.getNavigationPath(currentPositionString, destinationPositionString);
			
			ListTerm pathTerm = ListTermImpl.parseList(path); 

			// Unify the result
			boolean pathSuccess = un.unifies(pathTerm, args[pathIndex]);

        	// everything ok, so returns true
        	return pathSuccess;
		}
		catch (Exception e) { // just to show how to throw another kind of exception
			ts.getAg().getLogger().severe("getPath Error: " + e.toString());
		}
		return false;	// If we made it here, something went wrong
    }
}
