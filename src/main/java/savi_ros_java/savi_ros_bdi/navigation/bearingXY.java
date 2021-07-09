package savi_ros_java.savi_ros_bdi.navigation;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;


public class bearingXY extends DefaultInternalAction {


    private static final long serialVersionUID = 1L;

    public static final int x1Index = 0;
    public static final int y1Index = 1;
    public static final int x2Index = 2;
    public static final int y2Index = 3;
    public static final int bearingResultIndex = 4;

    @Override

    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {

        // execute the internal action

        ts.getAg().getLogger().info("executing internal action 'navigation.bearingXY'");

        try {
            // Get the parameters
            NumberTerm x1Term = (NumberTerm) args[x1Index];
            NumberTerm y1Term = (NumberTerm) args[y1Index];
            NumberTerm x2Term = (NumberTerm) args[x2Index];
            NumberTerm y2Term = (NumberTerm) args[y2Index];

            // Lat and Lon as radians
            double x1 = x1Term.solve();
            double y1 = y1Term.solve();
            double x2 = x2Term.solve();
            double y2 = y2Term.solve();

            // Calculate results
            //double range = calculateRange(lat1, lon1, lat2, lon2, "meters");
            double bearingResult = bearingXY.calculateBearing(x1, y1, x2, y2);

            // Create result terms
            //NumberTerm rangeTerm = new NumberTermImpl(range);
            NumberTerm bearingTerm = new NumberTermImpl(bearingResult);

            // Unify and return
            boolean successBearing = un.unifies(bearingTerm, args[bearingResultIndex]);
            return successBearing;

        } catch (Exception e) {
            ts.getAg().getLogger().info(e.toString());
            throw new Exception("Error in 'navigation.bearingXY'.");
        }
    }

    public static double calculateBearing(double x1, double y1, double x2, double y2) {

        double range = Math.sqrt(((x2-x1) * (x2-x1)) + ((y2-y1) * (y2-y1)));
        if (range < 1) {
            return 0.0;
        }

        double bearing  = (Math.toDegrees(Math.atan2((y2-y1),(x2-x1))));

        while (bearing > 180) {
            bearing = bearing - 360.0;
        }
        while (bearing < -180) {
            bearing = bearing + 360;
        }
        return bearing;
    }

}