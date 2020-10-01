package savi_ros_java.savi_ros.bdi.agent_state;

import jason.asSemantics.Intention;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestIntentionSelection extends TestCase {

    @Test
    public void testCase() {
        System.out.println("Test case!");
//        SaviAgent myAgent = new SaviAgent("../../../resources/main/settings.cfg");

        //Trigger myTrigger = Trigger.parseTrigger("+unattended_luggage(_,_,_) [priority(1)]");
        Intention myIntention = new Intention();
        //System.out.println(myIntention.toString());
        //System.out.println(myIntention.getAsTerm().isLiteral());
        //List<Term> termList = new LinkedList<Term>();
        //ListTerm myListTerm =
        //termList.add(myIntention.getAsTerm());
        //Literal intentionLiteral = Literal.parseLiteral(myIntention.toString());
        //System.out.println(intentionLiteral.toString());
        //ListTerm annotList = intentionLiteral.getAnnots();
        //annotList.contains()

        //System.out.println(myTrigger.toString());
        Literal myLiteral = Literal.parseLiteral(myIntention.toString());

        //Literal myLiteral = myTrigger.getLiteral();
        List<Term> annotList = myLiteral.getAnnots();

        System.out.println(annotList.toString());


    }
}
