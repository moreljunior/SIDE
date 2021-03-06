/*
    Copyright (C) 2007-20013  BlueXML - www.bluexml.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.bluexml.side.integration.buildHudson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.bluexml.side.integration.buildHudson.updaters.CategoryUpdater;
import com.bluexml.side.integration.buildHudson.updaters.FeatureUpdater;
import com.bluexml.side.integration.buildHudson.updaters.MavenProjectUpdater;
import com.bluexml.side.integration.buildHudson.updaters.PluginsUpdater;
import com.bluexml.side.integration.buildHudson.updaters.ProductUpdater;
import com.bluexml.side.integration.buildHudson.utils.BuilderUtils;
import com.bluexml.side.integration.buildHudson.utils.SVNCommandGenerator;
import com.bluexml.side.integration.buildHudson.utils.Utils;

public class ProjectVersionUpdater {

	/**
	 * options
	 */
	public boolean skipCopyToRepo = false;
	public boolean generateSVNCommit = true;
	public boolean useRepositoryCopy = false;
	public boolean forceProductUpdate = true;
	/**
	 * parameters
	 */
	private String workspace = "";
	private String build_number = "";
	private String svn_revision = "";
	private Properties conf;

	private String propertiesFile;

	/**
	 * computed parameters
	 */
	private String sourceSVNName = "";
	private Date launchDate = null;

	/**
	 * constant
	 */
	public static final String buildStartLine = "****************************************";
	public static final String SIDE_Core = "S-IDE";
	public static final String SIDE_Enterprise = "S-IDE_Enterprise";
	public static final String repositoryCopy = "repositoryCopy";
	public static final String workspaceFolderName = "workspace";
	public static final String svnLog = "svnUpdate.log";
	public static final String bluexmlPackage = "com.bluexml";

	/**
	 * attributes
	 */
	private BuilderUtils bu = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 4) {
				System.out.println("Usage : java -jar Builder.jar <workspace> <build_number> <svn_revision> <propertiesFilePath> [skipCopyToRepo]");
				System.out.println("\t<workspace>          : workspace folder, folder contains S-IDE ...");
				System.out.println("\t<build_number>       : Hudson build number");
				System.out.println("\t<svn_revision>       : the svn revision number");
				System.out.println("\t<propertiesFilePath> : file path of properties file to use");
				System.out.println("\t[skipCopyToRepo]     : optional, disable last action that copy modified files into svn local copy (mainly for testing)");
			}

			// get Parameters
			String workspace = args[0];
			String build_number = args[1];
			String svn_revision = args[2];
			String propertiesFilePath = args[3];
			boolean skipCopyToRepo = false;
			if (args.length == 5 && args[4].equals("skipCopyToRepo")) {
				skipCopyToRepo = true;
			}
			// initialize Builder
			ProjectVersionUpdater builder = new ProjectVersionUpdater(workspace, build_number, svn_revision, propertiesFilePath, skipCopyToRepo);

			// launch version updater
			builder.build();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public ProjectVersionUpdater(String workspace, String build_number, String svn_revision, String propertiesFilePath, boolean skipCopyToRepo) {
		this.workspace = workspace;
		this.build_number = build_number;
		this.svn_revision = svn_revision;
		this.propertiesFile = propertiesFilePath;
		this.conf = BuilderUtils.openProperties(propertiesFilePath);
		if (skipCopyToRepo) {
			this.useRepositoryCopy = true;
			this.skipCopyToRepo = true;
		} else {
			this.useRepositoryCopy = false;
			this.skipCopyToRepo = false;
		}
		this.bu = new BuilderUtils(conf, workspace, build_number, svn_revision, useRepositoryCopy);

		// set computed parameters
		String pathprojectSVN = getUsedWorkspace();
		if (pathprojectSVN.contains("Build_RCP_Enterprise")) {
			sourceSVNName = SIDE_Enterprise;
		} else {
			sourceSVNName = SIDE_Core;
		}
		bu.setSourceSVNName(sourceSVNName);
	}

	public void build() throws Exception {
		launchDate = new Date();
		System.out.println(buildStartLine);
		System.out.println("**** Lancement du Build Automatique ****");
		System.out.println("****************************************");
		System.out.println("**** Parametre ****");
		System.out.println("- workspace = " + workspace);
		System.out.println("- build_number = " + build_number);
		System.out.println("- svn_revision = " + svn_revision);
		System.out.println("- launchDate = " + launchDate);
		System.out.println("- propertiesFile = " + propertiesFile);
		System.out.println("- sourceSVNName = " + sourceSVNName);
		System.out.println("- skipCopyToRepo = " + skipCopyToRepo);
		System.out.println("- ForceNumberVersion = " + bu.getForceNumberVersion());
		// build process

		System.out.println("\nLancé le " + BuilderUtils.getFormatedDate(launchDate) + " à " + BuilderUtils.getTime(launchDate));

		// define projects lists
		List<String> projectList = new ArrayList<String>();
		List<String> projectRealList = new ArrayList<String>();

		List<String> projetPomsList = new ArrayList<String>();
		List<String> projectPoms2Update = new ArrayList<String>();

		List<String> pluginsList = new ArrayList<String>();
		List<String> plugins2UpdateList = new ArrayList<String>();

		List<String> featuresList = new ArrayList<String>();
		List<String> features2UpdateList = new ArrayList<String>();

		List<String> productsList = new ArrayList<String>();
		List<String> products2UpdateList = new ArrayList<String>();

		List<String> projects = new ArrayList<String>();
		List<String> coreProjects = bu.getProjects("project");
		projects.addAll(coreProjects);
		if (isEnterpriseBuild()) {
			// add Enterprise projects
			List<String> enterpriseProjects = bu.getProjects("project.enterprise");
			projects.addAll(enterpriseProjects);
		}
		for (int i = 0; i < projects.size(); i++) {
			if (projects.get(i).length() > 0) {
				projectRealList.add(projects.get(i));
			}
		}
		String pathproject = getUsedWorkspace();

		// copy svn repository to a working folder
		if (useRepositoryCopy) {
			bu.copyFromRepository();
		}

		// get All poms
		System.out.println("Search maven2 projects :");
		File searchFrom = new File(pathproject + "/" + sourceSVNName + "/");
		System.out.println("from " + searchFrom);

		projetPomsList = BuilderUtils.findFile(searchFrom, "pom.xml");

		if (bu.getForceNumberVersion()) {
			// all projects must be updated, no need to check svn logs
			// all poms
			projectPoms2Update.addAll(projetPomsList);
			// all Eclipse projects
			projectList.addAll(projectRealList);

			// beware to remove community projects when building RCP Enterprise
			if (isEnterpriseBuild()) {
				// need to remove from project to update all Core projects
				// (because this part must be readonly for Enterprise)
				projectList.removeAll(coreProjects);
				List<String> toRemove = new ArrayList<String>();
				for (String pom : projectPoms2Update) {
					if (pom.contains("/" + SIDE_Core + "/")) {
						toRemove.add(pom);
					}
				}
				projectPoms2Update.removeAll(toRemove);
			}

		} else {
			// read svn log to list modified projects
			bu.readSvnLog(projetPomsList, projectPoms2Update, projectList, null);
		}
		// dispatch modified project according to project type (plugin or
		// feature)
		for (String element : projectList) {
			if (projectRealList.contains(element)) {
				// on met tous les plugins modifiés dans un tableau
				if (element.indexOf("feature") == -1) {
					plugins2UpdateList.add(element);
				}
				// et tous les features dans un autre
				else {
					features2UpdateList.add(element);
				}
			}
		}

		// dispatch all project according to project type (plugin or feature)
		for (int i = 0; i < projects.size(); i++) {
			if (projects.get(i).indexOf("feature") != -1) {
				featuresList.add(projects.get(i));
			} else {
				pluginsList.add(projects.get(i));
			}
		}

		// list modified products
		for (String element : projectList) {
			if (projectRealList.contains(element)) {
				// on met tous les plugins modifiés dans un tableau
				if (element.indexOf("branding") != -1) {
					products2UpdateList.add(element);
				}
			}
		}

		// list all products
		for (int i = 0; i < projects.size(); i++) {
			if (projects.get(i).indexOf("branding") != -1) {
				productsList.add(projects.get(i));
			}
		}

		// lists initializing done.

		System.out.println(" Found :");
		System.out.println(" Products :" + productsList.size());
		System.out.println(" Features :" + featuresList.size());
		System.out.println(" Plugins :" + pluginsList.size());
		System.out.println(" poms :" + projetPomsList.size());
		System.out.println("");
		System.out.println(" To update :");
		System.out.println(" Products :" + products2UpdateList.size());
		System.out.println(" Features :" + features2UpdateList);
		System.out.println(" Plugins :" + plugins2UpdateList.size());
		System.out.println(" poms :" + projectPoms2Update.size());

		if (featuresList.size() == 0 || pluginsList.size() == 0 || projetPomsList.size() == 0) {
			// something wrong
			throw new Exception("Updater Stoped ! please check configuration somes projects could not be found in repository");
		}

		// launch maven project updater
		// get projects from Core repository
		List<String> corePoms = null;
		if (isEnterpriseBuild()) {
			// just to have Core project in full list
			corePoms = BuilderUtils.findFile(new File(pathproject + "/" + Application.SIDE_Core + "/"), "pom.xml");
			// add them to poms list
			projetPomsList.addAll(corePoms);
		}
		MavenProjectUpdater mpu = new MavenProjectUpdater(projetPomsList, projectPoms2Update, corePoms, bu);
		projectPoms2Update = mpu.checkAndUpdateAllPoms();
		System.out.println("Updated Maven2 projects :" + mpu.getPomsNewsVersion().size());
		for (Map.Entry<String, String> entry : mpu.getPomsNewsVersion().entrySet()) {
			System.out.println("\t- " + entry.getKey() + " : " + entry.getValue());
		}

		if (mpu.getPomsNewsVersion().size() > 0) {
			// maven2 project updated, so must update plugin that contains
			// dependencies

			// since we build all artifacts without products distinction we need
			// to manage all dependencies repositories
			List<String> depRepos = getRepositoriesExtensions();
			for (String id : depRepos) {
				if (!plugins2UpdateList.contains(id)) {
					plugins2UpdateList.add(id);
				}
			}
		}

		if (forceProductUpdate) {
			/**
			 * side.product must be updated so branding too, this to avoid cycle
			 * in update : t0 side.product updated and commited t1 scan svnlog
			 * branding is changed, marked for update ...
			 */

			// get all products and add the container to update list
			for (String brandingPluginId : productsList) {
				if (!plugins2UpdateList.contains(brandingPluginId)) {
					plugins2UpdateList.add(brandingPluginId);
				}
			}
		}

		// launch plugin project updater

		PluginsUpdater pu = null;
		if (isEnterpriseBuild()) {
			pu = new PluginsUpdater(pluginsList, plugins2UpdateList, bu.getProjects("project"), mpu, bu);
		} else {
			pu = new PluginsUpdater(pluginsList, plugins2UpdateList, null, mpu, bu);
		}
		pu.checkAndUpdate();
		System.out.println("Updated Plugins :" + pu.getPluginsNewVersion().size());
		for (Map.Entry<String, String> entry : pu.getPluginsNewVersion().entrySet()) {
			System.out.println("\t- " + entry.getKey() + " : " + entry.getValue());
		}

		// launch feature project updater
		FeatureUpdater fu = null;
		if (isEnterpriseBuild()) {
			fu = new FeatureUpdater(featuresList, features2UpdateList, bu.getProjects("project"), pu, bu);
		} else {
			fu = new FeatureUpdater(featuresList, features2UpdateList, null, pu, bu);
		}

		fu.checkAndUpdateAllFeatures();
		System.out.println("Updated Features :" + fu.getFeaturesNewsVersion().size());
		for (Map.Entry<String, String> entry : fu.getFeaturesNewsVersion().entrySet()) {
			System.out.println("\t- " + entry.getKey() + " : " + entry.getValue());
		}

		// launch product updater
		ProductUpdater produ = new ProductUpdater(fu, bu, forceProductUpdate);
		// boolean sideProductChanges = produ.updateProduct();

		// update all product files

		List<String> updatedproducts = new ArrayList<String>();
		for (String prodId : productsList) {
			if (produ.updateProduct(prodId)) {
				updatedproducts.add(prodId);
			}
		}
		System.out.println("- side.product updated " + updatedproducts.size());
		for (String updatedProduct : updatedproducts) {
			System.out.println(" *" + updatedProduct);
		}

		CategoryUpdater catUp = new CategoryUpdater(fu, bu);

		List<String> updatedCategories = new ArrayList<String>();
		for (String prodId : productsList) {
			try {
				if (catUp.updateCategory(prodId)) {
					updatedCategories.add(prodId);
				}
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("- category.xml updated " + updatedCategories.size());
		for (String updatedcat : updatedCategories) {
			System.out.println(" *" + updatedcat);
		}

		// project version update done.

		if (useRepositoryCopy && !skipCopyToRepo) {
			// get modified files and copy them into svn local copy
			bu.copyToRepository();
		}

		if (generateSVNCommit) {
			// generate ant script to commit changes
			
			SVNCommandGenerator svnCg = new SVNCommandGenerator(bu, launchDate, projectPoms2Update, plugins2UpdateList, features2UpdateList);
			svnCg.createAntFile();
		}

		System.out.println("=========== Summary ================");
		System.out.println("\t- Updated Maven2 projects :" + mpu.getPomsNewsVersion().size() + " / " + projetPomsList.size());
		System.out.println("\t- Updated Plugins :" + pu.getPluginsNewVersion().size() + " / " + pluginsList.size());
		System.out.println("\t- Updated Features :" + fu.getFeaturesNewsVersion().size() + " / " + featuresList.size());
		System.out.println("\t- side.product updated ?" + updatedproducts);
		System.out.println("\t- category.xml updated ?" + updatedCategories);
		System.out.println("====================================");

		// write list of updated artifacts
		Utils.writeListInFile(new File(this.workspace + File.separator + "work" + File.separator + "updatedMavenProjects.txt"), mpu.getPomUpdated());
		Utils.writeListInFile(new File(this.workspace + File.separator + "work" + File.separator + "updatedPlugins.txt"), pu.getPluginsUpdated());
		Utils.writeListInFile(new File(this.workspace + File.separator + "work" + File.separator + "updatedFeatures.txt"), fu.getFeatureUpdated());
		Utils.writeListInFile(new File(this.workspace + File.separator + "work" + File.separator + "updatedProducts.txt"), updatedproducts);
	}

	private List<String> getRepositoriesExtensions() {
		List<String> depRepos = new ArrayList<String>();
		// TODO must be removed to manage the new build process
		if (isEnterpriseBuild()) {
			depRepos.add("com.bluexml.side.Util.dependencies.repository.enterprise");
		}
		depRepos.add("com.bluexml.side.Util.dependencies.repository");
		return depRepos;
	}

	public String getUsedWorkspace() {
		String path = null;
		if (useRepositoryCopy) {
			try {
				path = bu.getRepositoryCopyPath();
			} catch (Exception e) {
				// can't happen
				e.printStackTrace();
			}
		} else {
			path = workspace;
		}
		return path;
	}

	public boolean isEnterpriseBuild() {
		return sourceSVNName.equals(SIDE_Enterprise);
	}

}
