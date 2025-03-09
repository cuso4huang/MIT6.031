/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    // Abstraction function:
    //   edges with positive weight, vertices -> a Graph with vertices and edges
    // Representation invariant:
    //   edges's froms + edges's tos in vertices
    //   edges's weight >= 0
    // Safety from rep exposure:
    //


    public ConcreteEdgesGraph() {
        checkRep();
    }

    public void checkRep(){
        for(Edge e: edges){
            assert vertices.contains(e.getSource()) && vertices.contains(e.getTarget());
            assert e.getWeight() >0;
        }
    }
    @Override public boolean add(L vertex) {
        if(vertices.contains(vertex)) return false;
        vertices.add(vertex);
        checkRep();
        return true;
    }
    
    @Override public int set(L source, L target, int weight) {
        if(weight<0){
            throw new IllegalArgumentException("Negative weight");
        }
        if(!vertices.contains(source)){
            add(source);
        }
        if(!vertices.contains(target)){
            add(target);
        }
        int toReturn = -1;
        // check if edge exits
        for(Edge e: edges){
            if(e.getSource().equals(source) && e.getTarget().equals(target)){
                toReturn = e.getWeight();
            }
        }
        if(toReturn == -1){
            edges.add(new Edge<L>(source, target, weight));
            toReturn = weight;
        }
        checkRep();
        return toReturn;
    }
    
    @Override public boolean remove(L vertex) {
        if(!vertices.contains(vertex)) return false;
        vertices.remove(vertex);
        // 删除相关的边
        for(Edge<L> e: edges){
            if(e.getSource().equals(vertex) || e.getTarget().equals(vertex)){
                edges.remove(e);
            }
        }
        checkRep();
        return true;
    }
    
    @Override public Set<L> vertices() {
        HashSet<L> hashSet = new HashSet<>(vertices);
        return hashSet;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> map = new HashMap<>();
        for(Edge<L> e: edges){
            if(e.getTarget().equals(target)){
                map.put(e.getSource(), e.getWeight());
            }
        }
        return map;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        HashMap<L, Integer> map = new HashMap<>();
        for(Edge<L> e: edges){
            if(e.getSource().equals(source)){
                map.put(e.getTarget(), e.getWeight());
            }
        }
        return map;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Edge<L> e: edges){
            sb.append(e.getSource()).append(" -> ").append(e.getTarget()).append(":").append(e.getWeight()).append("\n");
        }
        return sb.toString();
    }
    
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    // TODO fields
    private final L source;
    private final L target;
    private final int weight;
    
    // Abstraction function:
    //AF(source,target,weight) = edge
    // Representation invariant:
    //   source != target
    //   weight >= 0
    // Safety from rep exposure:
    //   all variable is final & L is inmutable

    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }
    public void checkRep(){
        assert source != null && target != null && weight > 0 ;
    }
    public L getSource() {
        return source;
    }
    public L getTarget() {
        return target;
    }
    public int getWeight() {
        return weight;
    }
    public String toString(){
        return source +"==>" + target+" : "+weight;
    }

    
}
