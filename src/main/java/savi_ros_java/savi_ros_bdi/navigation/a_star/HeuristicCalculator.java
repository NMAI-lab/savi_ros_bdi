package savi_ros_java.savi_ros_bdi.navigation.a_star;

import aima.core.search.framework.Node;

import java.util.function.ToDoubleFunction;

public class HeuristicCalculator implements ToDoubleFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    @Override
    public double applyAsDouble(Object value) {
        if (value instanceof Node) {
            Node thisNode = (Node)value;
            MapAction action = (MapAction)thisNode.getAction();
            return action.getHeuristic();
        } else {
            return -1;
        }
    }
}