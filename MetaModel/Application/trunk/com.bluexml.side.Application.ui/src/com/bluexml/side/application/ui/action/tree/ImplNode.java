package com.bluexml.side.application.ui.action.tree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * this class match to all Plugins that implements extension for Generator or Deployer and other plugins like thats
 * @author davidabad
 *
 */
public abstract class ImplNode extends TreeNode {
	protected String id;
	protected String version;
	protected String launchClass;
	protected Set<TreeNode> options = new HashSet<TreeNode>();
	
	
	
	public ImplNode(IConfigurationElement elt, TechnologyVersion tv,TreeView root) {
		super(root);
		root.addOption(this);
		for (IConfigurationElement child : elt.getChildren()) {
			if (child.getName().equalsIgnoreCase("mustBeChecked")) {
				mustbechecked.add(new CheckConstraints(child,this));
			}
			if (child.getName().equalsIgnoreCase("mustBeUnChecked")) {
				mustbeUnchecked.add(new CheckConstraints(child,this));
			}
			if (child.getName().equalsIgnoreCase("moduleDependence")) {
				integrationModules.add(new ModuleConstraints(child,this));
			}
		}
		
		
	}
	
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
		updateApplication();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		updateApplication();
	}
	public abstract void updateApplication();

	 
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLaunchClass() {
		return launchClass;
	}

	public void setLaunchClass(String launchClass) {
		this.launchClass = launchClass;
	}
	
	public void addOption(OptionComponant option) {
		options.add(option);
	}

	public Collection<TreeNode> getChildren() {
		return options;
	}
}
