package savi_ros_java.savi_ros_bdi.agent_core;

import jason.asSemantics.Agent;
import jason.asSemantics.Event;
import jason.asSemantics.Option;
import jason.asSyntax.Literal;
import jason.asSyntax.LogicalFormula;
import jason.asSyntax.Plan;
import jason.asSyntax.Term;
import jason.bb.BeliefBase;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class SaviAgentBrain extends Agent {

    protected List<String> eventPriorities;

    SaviAgentBrain() {
        super();
        this.setEventPriorities();
    }

    /**
     * Specify the event priorities for the event selection function.
     * This can be overridden if a new priority setting is needed.
     */
    protected void setEventPriorities() {
        this.eventPriorities = Arrays.asList("safety", "health", "map", "mission", "navigation", "movement");
    }
    
    /**
     * Highest priority event has priority
     * Otherwise, first event in the queue is chosen.
     */
    @Override
    public Event selectEvent(Queue<Event> events) {
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
     * Return the highest priority event from the events list.
     */
    protected Event getHighestPriorityEvent(Queue<Event> events) {

        BeliefBase beliefs = this.getBB();
        for (String priority : eventPriorities) {
            for (Event event : events) {
                String trigger = event.getTrigger().getLiteral().getFunctor();
                for (Literal belief : beliefs) {
                    if (belief.getFunctor().contentEquals(priority)) {
                        List<Term> terms = belief.getTerms();
                        for (Term term : terms) {
                            if (term.toString().contentEquals(trigger)) {
                                //System.out.println("Chosen event: " + event.toString());
                                return event;
                            }
                        }
                    }
                }
            }
        }

        // If I made it here, use the default
        //System.out.println("Using the default event");
        return super.selectEvent(events);
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
}
