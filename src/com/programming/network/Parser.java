package com.programming.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher; 
import java.util.regex.Pattern; 
/**
*Esta clase es un parser para el archivo de texto
*con los enlaces, desenlaces y preguntas
*/
public class Parser implements Iterator<LineResult>{
	
	//Patron para saber si es un enlace
	private final String LINK_PATTERN = "([A-z])(\\w)*((\\-)|(->|<-))(\\w)*(.)(.*)";
	
	//Patron para saber si es una pregunta
	private final String QUESTION_PATTERN = "([A-z])(\\w)*(=>|<=)(\\w)*(\\?)(.*)";
	
	//Ruta del archivo a leer
	private final String FILE = "./data.txt";
	
	private int currentIndex = 0;
	private List<String> lines;
	private Pattern linkPattern;
	private Pattern questionPattern;
	
	Parser(){
		
		Scanner sc = null;
		try {
			sc = new Scanner(new File(FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		this.lines = new ArrayList<String>();
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			line = line.replaceAll("\\s+","");
			line = line.toLowerCase();
			this.lines.add(line);
		}
		sc.close();
		
		this.linkPattern = Pattern.compile(LINK_PATTERN);
		this.questionPattern = Pattern.compile(QUESTION_PATTERN);
	    
	}

	@Override
	public boolean hasNext() {
		return this.currentIndex < this.lines.size();
	}

	@Override
	public LineResult next() {
		Matcher m;
		String line = this.lines.get(this.currentIndex);
		String group = null;
		LineResult result = null;
		int lastIndex;
		if(Pattern.matches(LINK_PATTERN,line)) {
    		m = linkPattern.matcher(line);
    		m.find();
    		group = m.group();
    		lastIndex = group.lastIndexOf('.');
    		if(lastIndex == -1) {
    			this.currentIndex++;
    			return null;
    		}
    		group = group.substring(0,group.lastIndexOf('.'));
    		result = this.createLineResult(group, false);
    	}else if(Pattern.matches(QUESTION_PATTERN, line)) {
    		m = questionPattern.matcher(line);
			m.find();
			group = m.group();
			group = group.substring(0,group.lastIndexOf('?'));
    		result = this.createLineResult(group, true);
    	}else {
    		//Se comenta para evitar salidas erroneas
    		//System.out.println("invalid line "+line);
    	}
		this.currentIndex++;
		return result;
	}
	/*
	 * Obtener una linea de texto y convertirla
	 * en algo util para el grafo (Objeto LineResult)
	 * */
	private LineResult createLineResult(String line, boolean isQuestion) {
		
		LineResult result = new LineResult();
		String pattern = isQuestion?"\\b(=>|<=)\\b":"\\b((\\-)|(->|<-))\\b";
		Matcher matcher = Pattern.compile(pattern).matcher(line);
		boolean found = matcher.find();
		if(!found) {
			return null;
		}
		int start = matcher.start();
		int end = matcher.end();
		String arrow = matcher.group();
		
		String left = line.substring(0,start);
		String right = line.substring(end);
		
		if(left.length() > 15 || right.length() > 15) {
			return null;
		}
		Node nodeLeft = new Node(left);
		Node nodeRight = new Node(right);
		
		if(isQuestion) {
			result.setLink(false);
			if(arrow.equals("=>")) {
				result.setFrom(nodeLeft);
				result.setTo(nodeRight);
			}else {
				result.setFrom(nodeRight);
				result.setTo(nodeLeft);
			}
		}else {
			result.setLink(true);
			if(arrow.equals("->")) {
				result.setFrom(nodeLeft);
				result.setTo(nodeRight);
			}else if(arrow.equals("<-")){
				result.setFrom(nodeRight);
				result.setTo(nodeLeft);
			}else {
				result.setFrom(nodeLeft);
				result.setTo(nodeRight);
				result.setRemoval(true);
			}
		}
		return result;
		
	}
}
