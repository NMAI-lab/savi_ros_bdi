package savi_ros_java.savi_ros_bdi.navigation;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

public class rangeBearing extends DefaultInternalAction {
    private static final long serialVersionUID = 1L;

    public static final int lat1Index = 0;
    public static final int lon1Index = 1;
    public static final int lat2Index = 2;
    public static final int lon2Index = 3;
    public static final int rangeResultIndex = 4;
    public static final int bearingResultIndex = 5;



    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
        ts.getAg().getLogger().info("executing internal action 'navigation.rangeBearing'");

        try {
            // Get the parameters
            NumberTerm lat1Term = (NumberTerm) args[lat1Index];
            NumberTerm lon1Term = (NumberTerm) args[lon1Index];
            NumberTerm lat2Term = (NumberTerm) args[lat2Index];
            NumberTerm lon2Term = (NumberTerm) args[lon2Index];

            // Lat and Lon as radians
            double lat1 = Math.toRadians(lat1Term.solve());
            double lon1 = Math.toRadians(lon1Term.solve());
            double lat2 = Math.toRadians(lat2Term.solve());
            double lon2 = Math.toRadians(lon2Term.solve());

            double range = calculateRange(lat1, lon1, lat2, lon2, "meters");
            double bearing = calculateBearing(lat1, lon1, lat2, lon2);

            // Create result terms
            NumberTerm rangeTerm = new NumberTermImpl(range);
            NumberTerm bearingTerm = new NumberTermImpl(bearing);

            // Unify and return
            boolean successRange = un.unifies(rangeTerm, args[rangeResultIndex]);
            boolean successBearing = un.unifies(bearingTerm, args[bearingResultIndex]);
            return successRange && successBearing;

        } catch (Exception e) {
            ts.getAg().getLogger().info(e.toString());
            throw new Exception("Error in 'rangeBearing'.");
        }
    }


    // https://www.geodatasource.com/developers/java
    public double calculateRange(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;

            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            } else if (unit.equals("meters")) {
                dist = dist * 1.609344 * 1000.0;
            }
            return (dist);
        }
    }

    public double calculateBearing(double lat1, double lon1, double lat2, double lon2) {
        // https://stackoverflow.com/questions/9457988/bearing-from-one-coordinate-to-another

        double longitude1 = lon1;
        double longitude2 = lon2;
        double latitude1 = Math.toRadians(lat1);
        double latitude2 = Math.toRadians(lat2);
        double longDiff = Math.toRadians(longitude2 - longitude1);
        double y = Math.sin(longDiff) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }

}
