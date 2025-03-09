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
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   List<Vertex> -> graph
    // Representation invariant:
    //   in vertex
    // Safety from rep exposure:
    //   vertex is private and final
    
    public ConcreteVerticesGraph() {

    }

    public void checkRep(){
        Set<L> sourceSet = new HashSet<>();
        Set<L> targetSet = new HashSet<>();
        for(Vertex<L> v : vertices){
            assert !sourceSet.contains(v.getLabel());
            sourceSet.add(v.getLabel());
            for(L target:v.getTargetMap().keySet()){
                targetSet.add(target);
            }
        }
        for(L source:sourceSet){
            assert targetSet.contains(source);
        }

    }
    
    @Override public boolean add(L vertex) {
        Vertex<L> v = new Vertex<L>(vertex);
        for(Vertex<L> v2 : vertices){
            assert !v2.getLabel().equals(vertex);
        }
        vertices.add(v);
        checkRep();
        return true;

    }
    
    @Override public int set(L source, L target, int weight) {
        boolean found = false;
        for(Vertex<L> v : vertices){
            if(v.getLabel().equals(source)){
                found = true;
                v.setWeight(target, weight);
            }
        }
        if(!found){
            Vertex<L> v = new Vertex<L>(source);
            v.setWeight(target, weight);
            vertices.add(v);
        }
        checkRep();
        return weight;
    }
    
    @Override public boolean remove(L vertex) {
        for(Vertex<L> v : vertices){
            if(v.getLabel().equals(vertex)){
                vertices.remove(v);
                checkRep();
                return true;
            }
        }
        return false;
    }

    @Override public Set<L> vertices() {
        Set<L> set = new HashSet<>();
        for(Vertex<L> v : vertices){
            set.add(v.getLabel());
            for(L target:v.getTargetMap().keySet()){
                set.add(target);
            }
        }
        return set;
    }

    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> map = new HashMap<>();
        for(Vertex<L> v : vertices){
            Map<L, Integer> targetMap = v.getTargetMap();
            if(targetMap.containsKey(target)){
                map.put(v.getLabel(), targetMap.get(target));
            }
        }
        return map;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> map = new HashMap<>();
        for(Vertex<L> v : vertices){
            map.put(v.getLabel(), v.getTargetMap().get(source));
        }
        return map;
    }
    
    // TODO toString()
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Vertex<L> v : vertices){
            for(L target:v.getTargetMap().keySet()){
                sb.append(v.getLabel()).append(" -> ").append(target).append(": ").append(v.getTargetMap().get(target)).append("\n");
            }
        }
        return sb.toString();
    }

    
}

/**
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    private final Map<L,Integer> targetMap = new HashMap<>();
    private L label;
    // Abstraction function:
    //   AF(map<l,integer>,l) = vertex
    // Representation invariant:
    //   label in keySet cannot be equal to label
    //   weight in valueSet must be greater than zero
    // Safety from rep exposure:
    //  label is immutable, map is private
    
    public Vertex(L label) {
        this.label = label;

    }
    public void checkRep(){
        for(L label : targetMap.keySet()){
            assert !targetMap.containsKey(this.label) && targetMap.get(label)>0;
        }
    }
    
    public L getLabel() {
        return label;
    }
    public int setWeight(L label, int weight) {
        if(weight < 0 || label.equals(this.label)){
            return -1;
        }
        targetMap.put(label, weight);
        checkRep();
        return weight;
    }
    public int getWeight(L label) {
        if(targetMap.containsKey(label)){
            return targetMap.get(label);
        }
        return -1;
    }
    public boolean remove(L label) {
        if(label.equals(this.label)){
            return false;
        }
        targetMap.remove(label);
        checkRep();
        return true;
    }
    public Map<L,Integer> getTargetMap() {
        return new HashMap<>(targetMap);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(L label : targetMap.keySet()){
            sb.append(this.label).append(" -> ").append(label).append(": ").append(targetMap.get(label)).append('\n');
        }
        return sb.toString();
    }
    
}
