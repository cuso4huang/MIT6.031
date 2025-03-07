/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    final static Tweet t1 = new Tweet(1,"huang","@bob", Instant.now());
    final static Tweet t2 = new Tweet(2,"bob","@huang", Instant.now());
    final static Tweet t3 = new Tweet(3,"liu","@bob", Instant.now());

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */
    @Test
    public void testGuessFollowsGraph() {
        List<Tweet> list = new ArrayList<>(List.of(t1, t2, t3));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(list);
        assertEquals(3, followsGraph.size());
        for(Map.Entry<String, Set<String>> entry : followsGraph.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals(3, influencers.size());
        for(String influencer : influencers) {
            System.out.println(influencer);
        }

    }

}
