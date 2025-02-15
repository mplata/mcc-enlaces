package com.programming.network;
/**
* Clase Nodo
*/
public class Node implements Comparable<Node>{
	
	private String label;
	
	public Node(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		return this.label;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		if (this.label == null) {
			if (other.label != null)
				return false;
		} else {
			if (!this.label.equalsIgnoreCase(other.label)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int compareTo(Node o) {
		int res = this.label.compareToIgnoreCase(o.getLabel());
		return res;
	}
	
	
	
}
