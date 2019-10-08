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

	//Se utiliza un TreeMap para evitar repetir keys y para
	//que al insertar elementos con el mismo nombre pero con mayusculas o 
	//minusculas
    private TreeMap<Node, Set<Node>> graph = new TreeMap<Node, Set<Node>>();

    //Regresar falso si el nodo ya esta en el grafo
    public boolean addNode(Node node) {
    	if (graph.containsKey(node)) {
    		return false;
    	}
    	graph.put(node, new HashSet<Node>());
        return true;
    }

    //Obtener el Set del key correspondiente y agregarle la arista
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
    
    //Obtener las aristas del Nodo
    public Set<Node> edgesFrom(Node node) {
        Set<Node> arcs = graph.get(node);
        if (arcs == null)
            return null;

        return Collections.unmodifiableSet(arcs);
    }
    
    /**
     * Metodo recursivo, se manda el nodo visitado, el nodo buscado,
     * la lista de nodos que se han visitado y el stack
     */
    private boolean findPath(Node node,Node wanted, List<Node> visited, Stack<Node> path) 
    { 
    	boolean found= false;
        visited.add(node);
        //Si el nodo visitado es el nodo buscado, se retorna true y regresa
        if(node.equals(wanted)) {
        	path.push(node);
        	return true;
        }
        Set<Node> edges = edgesFrom(node);
        //Si el nodo visitado no es el buscado y no hay mas camino,
        //se retorna falso y regresa
        //Si sí hay camino, se manda llamar findPath, en los nodos adyacentes
        if(edges == null || edges.size() == 0 ) {
        	return false;
        }else {
        	Iterator<Node> edgesIt = edges.iterator();
            while (edgesIt.hasNext()) 
            { 
                Node n = edgesIt.next(); 
                if (!visited.contains(n)) {
                	found = findPath(n, wanted, visited, path);
                	//Si el retorno es verdadero, este nodo forma parte del
                	//camino hacia el nodo buscado, por lo que regresa
                	//true y se agrega a la pila de camino encontrado.
                	if(found) {
                		path.push(node);
                		return true;
                	}
                }
            }
        }
        return found;
    } 
    
    /**
     * Buscar si hay un camino del nodo from al nodo to
     */
    public void findPath(Node from, Node to){
    	
    	
    	//La lista de nodos visitados y el stack con el camino 
    	//correcto se modifican por referencia
    	List<Node> visited = new ArrayList<Node>();
    	Stack<Node> path = new Stack<Node>();
    	boolean isPath = this.findPath(from, to, visited, path);
    	//Si se encuentra un camino, se procesa el path para imprimir la salida
    	//Si no, se imprime la salida solicitada cuando no hay camino
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