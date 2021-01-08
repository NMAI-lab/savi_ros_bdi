package NavigationSupport;

import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.GeneralProblem;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarSearch;

import java.util.*;

public class MapSearchFunctions {

    public static String getNavigationPath(String action) {
        int bracketOpen = action.indexOf("(");
        int bracketClose = action.indexOf(")");
        int comma = action.indexOf(",");
        String start = action.substring(bracketOpen + 1, comma );
        String finish = action.substring(comma + 1, bracketClose);
        return "path(" + getNavigationPath(start, finish) + ")";
    }
	
    public static String getNavigationPath(String start, String finish) {
        String path = "D:\\Local Documents\\ROS_Workspaces\\RoombaWorkspaces\\src\\jason_mobile_agent_ros\\asl\\map.asl";
        Problem<NavigationState, MapAction> problem = MapSearchFunctions.createProblem(start,finish,path);
        SearchForActions<NavigationState, MapAction> aStarSearch = new AStarSearch<>(new GraphSearch<>(), new HeuristicCalculator());
        Optional<List<MapAction>> actions = aStarSearch.findActions(problem);
        String actionString = generateActionString(actions);
        return actionString;
    }

    public static Problem<NavigationState, MapAction> createProblem(String start, String finish, String path) {
        GridMap myMap = new GridMap(path);
        NavigationState startState = new NavigationState(myMap,start,finish);
        return new GeneralProblem(startState, MapSearchFunctions::getPossibleActions, MapSearchFunctions::getResultState,
                MapSearchFunctions::testGoal, MapSearchFunctions::getActionCost);
    }

    private static List getPossibleActions(Object o) {
        NavigationState state = (NavigationState)o;
        List<MapAction> possibleActions = new ArrayList<>();
        List<String> possibleActionStrings = state.getPossibleAction();
        Iterator<String> possibleActionStringsIterator = possibleActionStrings.iterator();
        while (possibleActionStringsIterator.hasNext()) {
            String nextDestination = possibleActionStringsIterator.next();
            double actionCost = state.getActionCost(nextDestination);
            double heuristic = state.getHeuristic(nextDestination);
            MapAction action = new MapAction(nextDestination, actionCost, heuristic);
            possibleActions.add(action);
        }
        return possibleActions;
    }

    private static Object getResultState(Object stateObj, Object actionObj) {
        NavigationState navigationState = (NavigationState)stateObj;
        MapAction mapAction = (MapAction)actionObj;
        return navigationState.act(mapAction);
    }


    private static boolean testGoal(Object o) {
        NavigationState state = (NavigationState)o;
        return state.testGoal();
    }

    private static double getActionCost(Object stateObj, Object actionObj, Object statePrimedObj) {
        MapAction mapAction = (MapAction)actionObj;
        return mapAction.getCost();
    }

    public static String generateActionString(Optional<List<MapAction>> actions) {
        String actionMessage = new String("[");
        List<MapAction> actionList = actions.get();
        ListIterator<MapAction> actionListIterator = actionList.listIterator();
        boolean appended = false;
        while (actionListIterator.hasNext()) {
            if (appended) {
                actionMessage += ",";
            }
            actionMessage += actionListIterator.next().getName();
            appended = true;
        }
        actionMessage = actionMessage + "]";
        return actionMessage;
    }
}
