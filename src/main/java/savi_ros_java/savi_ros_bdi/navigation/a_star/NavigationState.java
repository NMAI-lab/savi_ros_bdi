package savi_ros_java.savi_ros_bdi.navigation.a_star;

import java.util.List;

public class NavigationState {
    private EnvironmentMap environment;
    private String currentLocation;
    private String destinationLocation;

    public NavigationState(EnvironmentMap environment, String current, String destination) {
        this.environment = environment;
        setCurrentLocation(current);
        setDestinationLocation(destination);
    }

    public NavigationState(NavigationState original) {
        this(original.environment,original.getCurrentLocation(), original.getGoalLocation());
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public String getCurrentLocation() {
        return this.currentLocation;
    }

    public String getGoalLocation() {
        return this.destinationLocation;
    }

    public NavigationState act(MapAction action) {
        NavigationState nextState = new NavigationState(this);
        nextState.setCurrentLocation(action.getName());
        return nextState;
    }

    /**
     * Estimate heuristic cost the the goal destination
     * @return
     */
    public double getHeuristic() {
        return this.environment.getHeuristic(this.getCurrentLocation(),this.getGoalLocation());
    }

    /**
     * Estimate heuristic cost the the goal destination from a given locationName
     * @return
     */
    public double getHeuristic(String locationName) {
        return this.environment.getHeuristic(locationName,this.getGoalLocation());
    }

    /**
     * Test to see if this is the goal state
     * @return
     */
    public boolean testGoal() {
//        System.out.println("Testing goal");
        return this.getCurrentLocation().equals(this.getGoalLocation());
    }

    public List<String> getPossibleAction() {
        return this.environment.getPossibleDestinations(this.getCurrentLocation());
    }

    public double getActionCost(String actionName) {
        double cost = this.environment.getCost(this.getCurrentLocation(), actionName);
        return cost;
    }
}
