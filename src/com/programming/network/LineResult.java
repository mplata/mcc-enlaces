package com.programming.network;

public class LineResult {

	private boolean isLink;
	private boolean isRemoval;
	private Node from;
	private Node to;

	public void setFrom(Node from) {
		this.from = from;
	}

	public void setLink(boolean isLink) {
		this.isLink = isLink;
	}

	public void setTo(Node to) {
		this.to = to;
	}

	public Node getFrom() {
		return from;
	}

	public Node getTo() {
		return to;
	}

	public boolean isLink() {
		return isLink;
	}
	
	public boolean isRemoval() {
		return isRemoval;
	}
	
	public void setRemoval(boolean isRemoval) {
		this.isRemoval = isRemoval;
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		if(this.isLink) {
			res.append("Enlace ->");
		}else {
			res.append("Pregunta =>");
		}
		res.append(" de nodo "+this.from.getLabel()+" a "+this.to.getLabel());
		return res.toString();
	}
}
