package com.programming.network;
/**
* Clase Main, punto de entrada al programa, instancía
* Graph y Parser para poder evaluar el archivo de entrada.
*/
public class Enlaces {
	
	//Ruta del archivo a leer
	private static final String FILE = "./test.txt";
	
    public static void main(String[] args) {
    	
        Graph graph = new Graph();
        
        Parser p = new Parser(FILE);
        LineResult line = null;
        while(p.hasNext()) {
        	line = p.next();
        	if(line != null && line.isRemoval()) {
        		Node from = line.getFrom();
        		Node to = line.getTo();
        		graph.removeEdge(from, to);
        		/*System.out.println("Removiendo enlace "+from+" - "+to);
        		System.out.println("------------");
            	System.out.println(graph);
            	System.out.println("------------");*/
        	}
        	else if(line != null && line.isLink()) {
        		Node from = line.getFrom();
        		Node to = line.getTo();
        		graph.addNode(from);
        		graph.addNode(to);
        		graph.addEdge(from, to);
        		/*System.out.println("Agregando enlace "+from+" - "+to);
        		System.out.println("------------");
            	System.out.println(graph);
            	System.out.println("------------");*/
        	}else if(line != null) {
        		Node from = line.getFrom();
        		Node to = line.getTo();
        		//System.out.println("Pregunta: ¿"+from+" va a "+to+"?");
        		graph.findPath(from , to);
        	}
        }
    }
}
