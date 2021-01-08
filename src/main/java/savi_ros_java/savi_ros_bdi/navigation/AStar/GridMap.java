package NavigationSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class GridMap {

    private Hashtable<String, List<String>> locationGraph;
    private Hashtable<String, Location> locations;

    public Hashtable<String, List<String>> getLocationGraph() {
        return (Hashtable<String, List<String>>)this.locationGraph.clone();
    }

    public Hashtable<String, Location> getLocations() {
        return (Hashtable<String, Location>)this.locations.clone();
    }

    public GridMap(String path) {
        this.locationGraph = new Hashtable<>();
        this.locations = new Hashtable<>();
        this.loadMapFile(path);
    }

    private void loadMapFile(String path){
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (!this.isSkipLine(data)) {
                    if (data.contains("locationName")) {
                        this.parseLocation(data);
                    } else if (data.contains("possible")) {
                        this.parsePossible(data);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Check if the String line is a comment line or an empty line
     * @param line  The line to be checked
     * @return True if the line should be skipped (comment, blank line, no bracket), False otherwise.
     */
    private Boolean isSkipLine(String line) {
        if (line.contains("*")) {
            return true;
        } else if (line.contains("/")) {
            return true;
        } else if (!line.contains("(")) {
            return true;
        } else if (!line.contains(")")) {
            return true;
        } else {
            return false;
        }
    }

    private void parseLocation(String line) {
        // Example location name string: locationName(b,[1,3]).
        int start;
        int end;
        String name;
        double x;
        double y;

        // Characters between the first '(' and the first ',' is the location name
        start = line.indexOf('(') + 1;
        end = line.indexOf(',') - 1;
        if (start == end) {
            name = Character.toString(line.charAt(start));
        } else {
            name = line.substring(start, end);
        }

        // Characters after '[' and before last ',' is the x location
        start = line.indexOf('[') + 1;
        end = line.lastIndexOf(',') - 1;
        if (start == end) {
            x = Double.parseDouble(Character.toString(line.charAt(start)));
        } else {
            x = Double.parseDouble(line.substring(start, end));
        }

        // Characters after last ',' and before ']' is the y location
        start = line.lastIndexOf(",") + 1;
        end = line.indexOf(']') - 1;
        if (start == end) {
            y = Double.parseDouble(Character.toString(line.charAt(start)));
        } else {
            y = Double.parseDouble(line.substring(start, end));
        }

        // Add the location to the location hashmap
        Location currentLocation = new Location(x,y);
        this.locations.put(name,currentLocation);
    }

    private void parsePossible(String line) {
        // Example string: Possible: possible(o,n).
        int start;
        int end;
        String a;
        String b;

        // Characters between the first '(' and the first ',' is the first location name
        start = line.indexOf('(') + 1;
        end = line.indexOf(',') - 1;
        if (start == end) {
            a = Character.toString(line.charAt(start));
        } else {
            a = line.substring(start, end);
        }

        // Characters between the first ',' and the first ')' is the second location name
        start = line.indexOf(',') + 1;
        end = line.indexOf(')') - 1;
        if (start == end) {
            b = Character.toString(line.charAt(start));
        } else {
            b = line.substring(start, end);
        }

        // Add this to the graph hashmap
        if (this.locationGraph.containsKey(a)) {
            // Append to the list
            List<String> nodes = this.locationGraph.get(a);
            nodes.add(b);
        } else {
            // make a new entry
            List<String> nodes = new ArrayList<String>();
            nodes.add(b);
            this.locationGraph.put(a,nodes);
        }
    }

    public List<String> getPossibleDestinations(String location) {
        return this.locationGraph.get(location);
    }

    public double getCost(String current, String next) {
        Location nextLocation = this.locations.get(next);
        Location currentLocation = this.locations.get(current);
        return currentLocation.range(nextLocation);
    }

    public double getHeuristic(String current, String goal) {
        return this.getCost(current,goal);
    }
}
