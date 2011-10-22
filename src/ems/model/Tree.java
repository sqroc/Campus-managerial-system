package ems.model;

import java.util.List;

public class Tree {
	private String text;
	private boolean leaf;

	private List<Tree> children;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

}
