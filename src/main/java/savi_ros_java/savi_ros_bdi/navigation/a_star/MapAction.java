package savi_ros_java.savi_ros_bdi.navigation.a_star;

import aima.core.agent.impl.DynamicAction;

public class MapAction extends DynamicAction {

    private double cost;
    private double heuristic;

    public MapAction(String name, double cost, double heuristic) {
        super(name);
        this.cost = cost;
        this.heuristic = heuristic;
    }

    public double getCost() {
        return this.cost;
    }

    public double getHeuristic() {
        return this.heuristic;
    }
}