package com.programming.network;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

/**
* Clase Grafo, implementa la busqueda de caminos
* y utiliza una Hashtable como estructura de datos
* para almacenar el grafo.
*
*/
public final class Graph{

	
    private TreeMap<Node, Set<Node>> graph = new TreeMap<Node, Set<Node>>();


    public boolean addNode(Node node) {
    	if (graph.containsKey(node)) {
    		return false;
    	}
    	graph.put(node, new HashSet<Node>());
        return true;
    }


    public void addEdge(Node start, Node dest) {
        //System.out.println(this.graph+" - "+start);
        graph.get(start).add(dest);
    }


    public boolean removeEdge(Node start, Node dest) {
        if (graph.containsKey(start) && graph.containsKey(dest)) {
            return graph.get(start).remove(dest);
        }else{
        	return false;
        }
    }

    public boolean edgeExists(Node start, Node end) {
        if (!graph.containsKey(start) || !graph.containsKey(end))
            return false;

        return graph.get(start).contains(end);
    }

    public Set<Node> edgesFrom(Node node) {
        Set<Node> arcs = graph.get(node);
        if (arcs == null)
            return null;

        return Collections.unmodifiableSet(arcs);
    }
    
    private boolean findPath(Node node,Node wanted, List<Node> visited, Stack<Node> path) 
    { 
    	boolean found= false;
        visited.add(node);
        
        if(node.equals(wanted)) {
        	path.push(node);
        	return true;
        }
        Set<Node> edges = edgesFrom(node);
        if(edges == null || edges.size() == 0 ) {
        	return false;
        }else {
        	Iterator<Node> edgesIt = edges.iterator();
            while (edgesIt.hasNext()) 
            { 
                Node n = edgesIt.next(); 
                if (!visited.contains(n)) {
                	found = findPath(n, wanted, visited, path);
                	if(found) {
                		path.push(node);
                		return true;
                	}
                }
            }
        }
        return found;
    } 
    
    public void findPath(Node from, Node to){
    	
    	
    	List<Node> visited = new ArrayList<Node>();
    	Stack<Node> path = new Stack<Node>();
    	boolean isPath = this.findPath(from, to, visited, path);
    	if(isPath) {
    		System.out.print("+");
    		while(!path.isEmpty()) {
        		
        		System.out.print(path.pop());
        		if(!path.isEmpty()) {
        			System.out.print(" => ");
        		}
        	}
    		System.out.println("");
    	}else {
    		System.out.println("-"+from +" => "+to);
    	}
    }
    
    @Override
    public String toString() {
    	
    	StringBuilder builder = new StringBuilder();
    	for (Map.Entry<Node, Set<Node>> entry : this.graph.entrySet())  {
    		Node node = entry.getKey();
    		Set<Node> destinies = entry.getValue();
    		builder.append(node+" va a:"+destinies);
    		builder.append("\r");
    	}
    	return builder.toString();
    }

}