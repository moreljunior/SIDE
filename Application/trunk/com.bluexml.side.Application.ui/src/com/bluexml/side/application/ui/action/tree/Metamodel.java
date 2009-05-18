package com.bluexml.side.application.ui.action.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;

public class Metamodel extends TreeNode {

	private String _label;
	private String _id;
	private String _url;
	private List<TreeNode> _technology;
	private String _description;

	public Metamodel(IConfigurationElement elt) {
		parent = null;
		_id = elt.getAttribute("id");
		_label = elt.getAttribute("name");
		_url = elt.getAttribute("url");
		_description = elt.getAttribute("description");
		_technology = new ArrayList<TreeNode>();
		
		setEnabled(true);
	}

	public String getURL() {
		return _url;
	}

	public String getDescription() {
		return _description;
	}

	public Metamodel(String id) {
		_id = id;
		_label = "";
		_technology = new ArrayList<TreeNode>();
	}

	public Metamodel(String id, String label) {
		_id = id;
		_label = label;
		_technology = new ArrayList<TreeNode>();
	}

	public Collection<TreeNode> getChildren() {
		return _technology;
	}

	public String getLabel() {
		return _label;
	}

	public String getId() {
		return _id;
	}

	public void addTechnology(Technology t) {
		this._technology.add(t);
	}

	

}