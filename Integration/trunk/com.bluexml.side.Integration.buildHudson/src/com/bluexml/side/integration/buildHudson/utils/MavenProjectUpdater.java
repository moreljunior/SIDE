package com.bluexml.side.integration.buildHudson.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

public class MavenProjectUpdater {
	private Logger logger = Logger.getLogger(getClass());
	static Namespace ns = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");
	BuilderUtils bu;
	List<String> pom2update = new ArrayList<String>();
	List<String> pomUpdated = new ArrayList<String>();
	Map<String, String> pomsPathList = new HashMap<String, String>();
	Map<String, String> pomsNewsVersion = new HashMap<String, String>();

	public Map<String, String> getPomsNewsVersion() {
		return pomsNewsVersion;
	}

	// all project with version (updated or not)
	Map<String, String> pomsVersions = new HashMap<String, String>();

	boolean updateDone = false;

	/**
	 * 
	 */
	boolean update_mavenplugins = true;

	public MavenProjectUpdater(List<String> pomsList, List<String> pom2update, BuilderUtils bu) {
		this.bu = bu;
		for (String pom : pomsList) {
			String currentmodule = getModuleIdFromPomPath(pom);
			pomsPathList.put(currentmodule, pom);
		}
		logger.debug("MavenProjectUpdater.MavenProjectUpdater() pomsList " + pomsPathList.keySet());
		for (String pom : pom2update) {
			String currentmodule = getModuleIdFromPomPath(pom);
			this.pom2update.add(currentmodule);
		}

	}

	private String getModuleIdFromPomPath(String pom) {
		String[] paths = pom.split(File.separator);
		String currentmodule = paths[paths.length - 2];
		return currentmodule;
	}

	public void checkAndUpdateAllPoms() throws Exception {
		// mark features
		// use fixed point algorithm to check end condition
		// some feature could be forgotten so search one more time
		// if no changes so job's done
		List<String> oldPoms2update;
		logger.debug("MavenProjectUpdater.checkAndUpdateAllFeatures() checkAllPoms");
		int c = 0;
		do {
			logger.debug("\tMavenProjectUpdater.checkAndUpdateAllFeatures()");
			oldPoms2update = new ArrayList<String>(pom2update);
			// maybe we could find more feature to mark
			// logger.debug("|||| before " + oldPoms2update.size());
			markProjects();
			// logger.debug("|||| before " + oldPoms2update.size());
			// logger.debug("|||| before feature2update" +
			// pom2update.size());
			c++;
		} while (!pom2update.equals(oldPoms2update));
		logger.debug("MavenProjectUpdater.checkAndUpdateAllFeatures() Updates poms DONE in " + c + " iteration");
		/**
		 * TEST we change index of a referred pom to test the update
		 */
		// String tomove =
		// "com.bluexml.side.Framework.alfresco.shareDoclistEnterprise";
		// pom2update.remove(tomove);
		// pom2update.add(tomove);
		updateMarkedModules();
		updateDone = true;
	}

	private void markProjects() throws Exception {
		List<String> module = new ArrayList<String>(pomsPathList.keySet());

		module.removeAll(pom2update);
		if (module.size() > 0) {
			for (String id : module) {
				logger.debug("MavenProjectUpdater.markProjects() Check mvn project :" + id);

				boolean marked = false;

				// mark to update if a dependency is marked
				File pom_xml = new File(getPomPath(id));
				Document doc = new SAXBuilder().build(pom_xml);
				Element root = doc.getRootElement();
				Element version = root.getChild("version", ns);
				pomsVersions.put(id, version.getTextTrim());

				Element dependencies = root.getChild("dependencies", ns);
				if (dependencies != null) {
					List<?> listdependencies = dependencies.getChildren("dependency", ns);
					logger.debug("\t search in project dependencies");
					for (Object object : listdependencies) {
						Element current = (Element) object;
						if (mustBeMarked(current)) {
							marked = true;
						}
					}
					if (update_mavenplugins) {
						// mark to update if a plugin is marked
						logger.debug("\t search in project plugin");
						XPath xpa = XPath.newInstance("/project//plugin");
						List<?> plugins = xpa.selectNodes(doc.getRootElement());
						for (Object object : plugins) {
							Element current = (Element) object;
							if (mustBeMarked(current)) {
								marked = true;
							}
						}
					}

					if (marked) {
						// marked for update
						pom2update.add(id);
						logger.debug("\tMavenProjectUpdater.markProjects() marked for update " + id);
					}
				}
			}
		}
	}

	private boolean mustBeMarked(Element current) throws Exception {
		Element groupId = current.getChild("groupId", ns);
		Element artifactId = current.getChild("artifactId", ns);
		String moduleId = groupId.getTextTrim() + "." + artifactId.getTextTrim();
		if (!pomsPathList.containsKey(moduleId)) {
			// this project is not in our sources
			return false;
		} else if (!isMarked(moduleId)) {
			// project not marked
			// must compare version number
			Element version_dep = current.getChild("version", ns);
			String depVersionNumber = version_dep.getTextTrim();
			// get version from file
			String versionFromFile = getMavenProjectVersion(moduleId);
			if (!depVersionNumber.equals(versionFromFile)) {
				return true;
			} else {
				return false;
			}

		} else {
			logger.debug("must be marked because " + moduleId + " is marked");
			// project is marked
			return true;
		}
	}

	private void updateMarkedModules() throws Exception {
		logger.debug("MavenProjectUpdater.updateMarkedModules() Start");
		for (String module : pom2update) {
			logger.debug("\tMavenProjectUpdater.markProjects() update mvn project :" + module);
			String pomPath = getPomPath(module);
			logger.debug("\tMavenProjectUpdater.markProjects() update mvn project :" + pomPath);
			File pom_xml = new File(pomPath);
			Document doc = new SAXBuilder().build(pom_xml);
			Element root = doc.getRootElement();
			// update version

			Element version = root.getChild("version", ns);
			String oldVersionNumber = version.getTextTrim();
			String[] pattern = bu.getNumVersionPattern();
			String[] number = oldVersionNumber.split("\\.");
			String newVersion = updatepom(number, pattern);
			version.setText(newVersion);
			logger.debug("\tMavenProjectUpdater.updateMarkedModules() update version " + oldVersionNumber + " -> " + newVersion);
			// update dependencies
			Element dependencies = root.getChild("dependencies", ns);
			List<?> listdependencies = dependencies.getChildren("dependency", ns);
			logger.debug("\tMavenProjectUpdater.updateMarkedModules() update dependencies");
			for (Object object : listdependencies) {
				Element current = (Element) object;
				updateRef(pattern, current);

			}
			if (update_mavenplugins) {
				// update plugins
				// mark to update if a plugin is marked
				XPath xpa = XPath.newInstance("/project//plugin");
				List<?> plugins = xpa.selectNodes(doc.getRootElement());
				for (Object object : plugins) {
					Element current = (Element) object;
					updateRef(pattern, current);
				}
			}

			// reccord this as updated
			this.pomUpdated.add(module);
			pomsNewsVersion.put(module, newVersion);
			pomsVersions.put(module, version.getTextTrim());
			// save changes
			try {
				XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
				sortie.output(doc, new FileOutputStream(pom_xml));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		logger.debug("MavenProjectUpdater.updateMarkedModules() Ended");
	}

	private void updateRef(String[] pattern, Element current) throws JDOMException, IOException {
		Element groupId = current.getChild("groupId", ns);
		Element artifactId = current.getChild("artifactId", ns);
		Element current_version = current.getChild("version", ns);
		String current_oldVersionNumber = current_version.getTextTrim();
		String moduleId = groupId.getTextTrim() + "." + artifactId.getTextTrim();

		// check if this module is marked
		if (isMarked(moduleId)) {
			// logger.debug("\t\tMavenProjectUpdater.updateMarkedModules() update ref"
			// + moduleId);
			// compute newVersion, must start from reading the pom
			// because
			// oldVersion +1 do not matchs case oldversion = 1.0.0 but
			// in
			// svn the pom have 1.1.1 becaus many build fail occurs
			String pomPath2 = getPomPath(moduleId);
			File pom_xmlDep = new File(pomPath2);
			// logger.debug("\t\tMavenProjectUpdater.updateMarkedModules() update ref"
			// + pomPath2);
			Document docDep = new SAXBuilder().build(pom_xmlDep);
			Element rootDep = docDep.getRootElement();
			// update version
			// if dependencies has already updated just read version or
			// compute it

			Element versionDep = rootDep.getChild("version", ns);
			String oldVersionNumberDep = versionDep.getTextTrim();

			if (pomUpdated.contains(moduleId)) {
				// version is already updated
				current_version.setText(oldVersionNumberDep);
				logger.debug("MavenProjectUpdater.updateRef() ref already updated");
			} else {
				// version is not updated yet so we compute it
				String[] numberDep = oldVersionNumberDep.split("\\.");
				current_version.setText(updatepom(numberDep, pattern));
				logger.debug("MavenProjectUpdater.updateRef() ref not yet updated");
			}
			String versiontrim = current_version.getTextTrim();
			logger.debug("\t\tMavenProjectUpdater.updateMarkedModules() update ref " + moduleId + " version:" + current_oldVersionNumber + " -> " + versiontrim);

		}
	}

	private boolean isMarked(String moduleId) {
		return pom2update.contains(moduleId);
	}

	private String getPomPath(String moduleId) {
		return this.pomsPathList.get(moduleId);
	}

	/**
	 * if projects have been updated with this instance method return up to date
	 * version else read version from pom.xml
	 * 
	 * @param project
	 * @return
	 * @throws Exception
	 */
	public String getMavenProjectVersion(String project) throws Exception {
		String version = pomsVersions.get(project);
		if (updateDone && version != null) {
			return version;
		} else {
			// must read xml file
			String path = pomsPathList.get(project);
			if (path == null) {
				throw new Exception("Maven project "+project+" not found please check moduleId");
			}
			File pom_xml = new File(path);
			Document doc = new SAXBuilder().build(pom_xml);
			Element root = doc.getRootElement();
			Element versionEl = root.getChild("version", ns);
			return versionEl.getTextTrim();
		}
	}

	/**
	 * change le numéro de version en fonction du pattern pour pom.xml
	 * 
	 * @param number
	 *            un tableau des 3 numéro de version
	 * @param pattern
	 *            un tableau avec les 3 éléments du pattern
	 * @return Le numéro de version sous la forme 1.0.12
	 */
	public static String updatepom(String[] number, String[] pattern) {

		boolean change = false;

		for (int i = 0; i < 3; i++) {
			// test si l'élément est un nombre si une exception est levée,
			// l'élément n'est pas un nombre
			try {
				Integer.valueOf(pattern[i]);
				number[i] = pattern[i];
			} catch (NumberFormatException e) {
				if (change)
					number[i] = "0";
				else {
					if (pattern[i].equals("u")) {
						change = true;
						number[i] = String.valueOf(Integer.valueOf(number[i]) + 1);
					}
				}
			}
		}
		return number[0] + "." + number[1] + "." + number[2];
	}
}
