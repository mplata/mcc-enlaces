package com.programming.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher; 
import java.util.regex.Pattern; 

public class Parser implements Iterator<LineResult>{
	
	//private final String LINK_PATTERN = "([A-z])(\\w)*((\\-)|(->|<-))(\\w)*([?]|[.])(.*)";
	private final String LINK_PATTERN = "([A-z])(\\w)*((\\-)|(->|<-))(\\w)*(.)(.*)";
	//private final String QUESTION_PATTERN = "([A-z])(\\w)*(=>|<=)(\\w)*([?]|[.])(.*)";
	private final String QUESTION_PATTERN = "([A-z])(\\w)*(=>|<=)(\\w)*(\\?)(.*)";
	//private final String FILE = "./data.txt";
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
		if(Pattern.matches(LINK_PATTERN,line)) {
    		m = linkPattern.matcher(line);
    		m.find();
    		group = m.group();
    		group = group.substring(0,group.lastIndexOf('.'));
    		result = this.createLineResult(group, false);
    	}else if(Pattern.matches(QUESTION_PATTERN, line)) {
    		m = questionPattern.matcher(line);
			m.find();
			group = m.group();
			group = group.substring(0,group.lastIndexOf('?'));
    		result = this.createLineResult(group, true);
    	}else {
    		System.out.println("invalid line "+line);
    	}
		this.currentIndex++;
		return result;
	}
	
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
