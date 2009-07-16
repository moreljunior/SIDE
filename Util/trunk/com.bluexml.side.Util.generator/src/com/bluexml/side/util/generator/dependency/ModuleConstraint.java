package com.bluexml.side.util.generator.dependency;

import java.util.ArrayList;
import java.util.Collection;

public class ModuleConstraint {

	private String artifactId = null;
	private String groupId = null;
	private String moduleType = null;
	private ModuleVersion versionMin = null;
	private ModuleVersion versionMax = null;
	private ModuleVersion resolvedVersion = null;

	private static String exclusiveMin = "(";
	private static String exclusiveMax = ")";
	private static String inclusiveMin = "[";
	private static String inclusiveMax = "]";

	public ModuleConstraint(String id, String moduleType, String versionNumMin, String versionNumMax) {
		setGroupAndArtifactId(id);
		this.moduleType = moduleType;
		if (versionNumMin != null) {
			this.versionMin = new ModuleVersion(versionNumMin);
		}
		if (versionNumMax != null) {
			this.versionMax = new ModuleVersion(versionNumMax);
		}
	}

	public ModuleConstraint(String id, String moduleType, ModuleVersion versionNumMin, ModuleVersion versionNumMax) {
		setGroupAndArtifactId(id);
		this.moduleType = moduleType;
		this.versionMin = versionNumMin;
		this.versionMax = versionNumMax;
	}

	public String getModuleType() {
		return moduleType;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getGroupId() {
		return groupId;
	}

	public ModuleVersion getVersionMin() {
		return versionMin;
	}

	public void setVersionMin(String versionMin) {
		this.versionMin = new ModuleVersion(versionMin);
	}

	public ModuleVersion getVersionMax() {
		return versionMax;
	}

	public void setVersionMax(String versionMax) {
		this.versionMax = new ModuleVersion(versionMax);
	}

	public void setGroupAndArtifactId(String id) {
		this.groupId = id.substring(0, id.lastIndexOf("."));
		this.artifactId = id;
		// this.artifactId = id.substring(id.lastIndexOf(".")+1);
	}

	public static Collection<ModuleVersion> getAllMin(Collection<ModuleConstraint> col) {
		Collection<ModuleVersion> ext = new ArrayList<ModuleVersion>();
		for (ModuleConstraint mc : col) {
			ext.add(mc.versionMin);
		}
		return ext;
	}

	public static Collection<ModuleVersion> getAllMax(Collection<ModuleConstraint> col) {
		Collection<ModuleVersion> ext = new ArrayList<ModuleVersion>();
		for (ModuleConstraint mc : col) {
			ext.add(mc.versionMax);
		}
		return ext;
	}

	public String toString() {
		return this.artifactId + " :" + getVersionRange();
	}

	public String getVersionRange() {
		String open = "";
		String close = "";
		String min = "";
		String max = "";
		if (versionMin != null) {
			open = inclusiveMin;
			min = versionMin.toString();
		} else {
			open = exclusiveMin;
		}
		if (versionMax != null) {
			close = inclusiveMax;
			max = versionMax.toString();
		} else {
			close=exclusiveMax;
		}
		return open + min + "," + max + close;
	}

	public String getModuleId() {
		return groupId + "." + artifactId;
	}
	
	public boolean isLastVersion() {
		return versionMax == null;
	}

	public void setResolvedVersion(ModuleVersion resolvedVersion) {
		this.resolvedVersion = resolvedVersion;
	}

	public ModuleVersion getResolvedVersion() {
		return resolvedVersion;
	}
}
