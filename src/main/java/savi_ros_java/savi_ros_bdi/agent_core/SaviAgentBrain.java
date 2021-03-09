package savi_ros_java.savi_ros_bdi.agent_core;

import jason.asSemantics.*;
import jason.asSyntax.*;
import java.util.*;
import java.util.regex.*;

public class SaviAgentBrain extends Agent {

    /**
     * Highest priority event has priority
     * Ex: [priotiy(1)]
     * Highest priority is smallest integer greater than 0
     * Otherwise, first event in the queue is chosen.
     */
    @Override
    public Event selectEvent(Queue<Event> events) {
        //System.out.println("I'm in the event selection function!");

        Event selected;
        if (events.size() > 1) {
            selected = this.getHighestPriorityEvent(events);
        } else {
            selected = super.selectEvent(events);
        }
        events.remove(selected);
        return selected;
    }


    /**
     * Select most appropriate option
     * Most specific should be highest priority
     * Default should be lowest priority
     */
    @Override
    public Option selectOption(List<Option> options) {
        //System.out.println("I'm in the option selection function!");
        //System.out.println(options.toString());
        Option selected;

        if (options.size() > 1) {
            selected = this.getHighestPriorityOption(options);
        } else {
            selected = super.selectOption(options);
        }

        //System.out.println(selected.toString());
        options.remove(selected);
        return selected;

    }



    /**
     * Highest priority intention has priority
     * Ex: [priotiy(1)]
     * Highest priority is smallest integer greater than 0
     * Otherwise, first intention in the queue is chosen.
     */
    @Override
    public Intention selectIntention(Queue<Intention> intentions) {
        //System.out.println("I'm in the intention selection function!");
        //System.out.println(intentions.toString());
        Intention selected;

        if (intentions.size() > 1) {
            selected = this.getHighestPriorityIntention(intentions);
        } else {
            selected = super.selectIntention(intentions);
        }

        //System.out.println(selected.toString());
        intentions.remove(selected);
        return selected;
    }

    /**
     * Return the highest priority event from the events list.
     */
    protected Event getHighestPriorityEvent(Queue<Event> events) {
        int priority = -1;    // The "champion" -> the best priority we've found so far (-1 is init)
        Event priorityEvent = null;    // The event that has the highest priority so far

        // Iterate through the list, look for the "champion"
        Iterator<Event> eventInstance = events.iterator();
        while (eventInstance.hasNext()) {
            Event current = eventInstance.next();
            List<Term> annotList = current.getTrigger().getLiteral().getAnnots();
            int currentPriority = this.getPriority(annotList);

            // Deal with first iterantion, need to not have null
            if (priorityEvent == null) {
                priorityEvent = current;
                priority = currentPriority;
            }

            // Looking for priority. Smallest value greater than 0 is highest
            // priority
            if ((currentPriority > 0) && (currentPriority < priority)) {
                priorityEvent = current;
                priority = currentPriority;
            }

            // Special case, if we found a priority 1 case (highest priority)
            // then we don't need to look any further
            if (priority == 1) {
                break;
            }
        }
        //System.out.println("Selected event: " + priorityEvent.toString() + " with priority: " + Integer.toString(priority));
        //events.remove(priorityEvent);
        return priorityEvent;
    }



    /**
     * Return the highest priority option from the options list.
     * 		Default is lowest priority
     *		Anything else is better than default option.
     * 		Need a way to choose between non default options
     */
    protected Option getHighestPriorityOption(List<Option> options) {


        Option priorityOption = null;    // The event that has the highest priority so far

        // Iterate through the list, look for the "champion"
        Iterator<Option> optionInstance = options.iterator();

        // Track first check (for the contingency)
        boolean first = true;

        while (optionInstance.hasNext()) {
            Option current = optionInstance.next();
            Plan currentPlan =  current.getPlan();
            LogicalFormula context = currentPlan.getContext();

            // Add contingency option in case we don't find anything useful
            if (first) {
                priorityOption = current;
                first = false;
            }

            // Context is null for default plans.
            // Need a way of comparing options if there is more than one applicable
            // non default plan
            if (context != null) {
                priorityOption = current;
                break;	// Break out of the loop, we have a non default plan.
            }
        }

        return priorityOption;
    }


    /**
     * Return the highest priority intention from the intentions list.
     */
    protected Intention getHighestPriorityIntention(Queue<Intention> intentions) {
        int priority = -1;    // The "champion" -> the best priority we've found so far (-1 is init)
        Intention priorityIntention = null;    // The event that has the highest priority so far

        // Iterate through the list, look for the "champion"
        Iterator<Intention> intentionInstance = intentions.iterator();
        while (intentionInstance.hasNext()) {
            Intention current = intentionInstance.next();
            Literal myLiteral = Literal.parseLiteral(current.toString());    // This is the one place where the syntax differs from the Event version, otherwise there would not be two versions of this.
            List<Term> annotList = myLiteral.getAnnots();
            int currentPriority = this.getPriority(annotList);

            // Deal with first iterantion, need to not have null
            if (priorityIntention == null) {
                priorityIntention = current;
                priority = currentPriority;
            }

            // Looking for priority. Smallest value greater than 0 is highest
            // priority
            if ((currentPriority > 0) && (currentPriority < priority)) {
                priorityIntention = current;
                priority = currentPriority;
            }

            // Special case, if we found a priority 1 case (highest priority)
            // then we don't need to look any further
            if (priority == 1) {
                break;
            }
        }
        //System.out.println("Selected intention: " + priorityIntention.toString() + " with priority: " + Integer.toString(priority));
        //intentions.remove(priorityIntention);
        return priorityIntention;
    }

    /**
     * Find the priority annotation and return the value.
     * Returns -1 if none is found.
     */
    protected int getPriority(List<Term> annotList) {
        int priority = -1;
        //List<Term> annotList = selected.getTrigger().getLiteral().getAnnots();
        Iterator<Term> t = annotList.iterator();
        while (t.hasNext()) {
            Term current = t.next();
            String currentString = current.toString();
            if (currentString.contains("priority")) {
                Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(currentString);
                while (m.find()) {
                    priority = Integer.parseInt(m.group(1));
                    return priority;
                }
            }
            //System.out.println(annotList.contains(Literal.parseLiteral("priority(1)")));
        }
        return priority;
    }
}