package com.bluexml.side.application.ui.action.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;

import com.bluexml.side.Util.ecore.EResourceUtils;
import com.bluexml.side.application.Application;
import com.bluexml.side.application.ApplicationFactory;
import com.bluexml.side.application.ComponantConfiguration;
import com.bluexml.side.application.Configuration;
import com.bluexml.side.application.ConfigurationParameters;
import com.bluexml.side.application.DeployerConfiguration;
import com.bluexml.side.application.GeneratorConfiguration;
import com.bluexml.side.application.Model;
import com.bluexml.side.application.ModelElement;
import com.bluexml.side.application.ModuleConstraint;
import com.bluexml.side.application.Option;
import com.bluexml.side.application.ui.action.ApplicationDialog;
import com.bluexml.side.application.ui.action.tree.Deployer;
import com.bluexml.side.application.ui.action.tree.Generator;
import com.bluexml.side.application.ui.action.tree.ImplNode;
import com.bluexml.side.application.ui.action.tree.TreeElement;
import com.bluexml.side.util.dependencies.DependencesManager;
import com.bluexml.side.util.libs.FileHelper;
import com.bluexml.side.util.security.Checkable;

public class ApplicationUtil {
	public static final String EXTENSIONPOINT_ID = "com.bluexml.side.Application.com_bluexml_application_configuration";

	public static final String APPLICATION_METAMODEL = "metamodel";
	public static final String APPLICATION_METAMODEL_ID = "id";
	public static final String APPLICATION_METAMODEL_NAME = "name";

	public static final String APPLICATION_TECHNO = "technology";

	public static final String APPLICATION_TECHVERSION = "technologyVersion";

	public static final String APPLICATION_GENERATORVERSION = "generatorVersion";

	public static final String APPLICATION_DEPLOYERVERSION = "deployerVersion";

	public static final String APPLICATION_OPTION = "option";

	public static final String APPLICATION_CONSTRAINTS = "moduleDependence";
	public static final String APPLICATION_CONSTRAINTS_MODULEID = "moduleId";
	public static final String APPLICATION_CONSTRAINTS_MODULETYPE = "moduleType";
	public static final String APPLICATION_CONSTRAINTS_TECHVERSION = "technologyVersion";
	public static final String APPLICATION_CONSTRAINTS_VERSIONMIN = "versionMin";
	public static final String APPLICATION_CONSTRAINTS_VERSIONMAX = "versionMax";

	/**
	 * Return the configuration corresponding to the given key in the current
	 * configuration. Return null if not found.
	 *
	 * @param key
	 * @return
	 */
	public static ConfigurationParameters getConfigurationParmeterByKey(String key) {
		ConfigurationParameters result = null;
		Configuration config = ApplicationDialog.getCurrentConfiguration();
		int i = 0;
		int size = config.getParameters().size();
		while (i < size && result == null) {
			ConfigurationParameters param = config.getParameters().get(i);
			if (param.getKey().equals(key)) {
				result = param;
			}
			i++;
		}
		return result;
	}

	/**
	 * Return models of the application
	 *
	 * @param application
	 * @return
	 */
	public static List<Model> getModels(Application application) {
		List<Model> result = new ArrayList<Model>();
		for (ModelElement elem : application.getElements()) {
			if (elem instanceof Model) {
				result.add((Model) elem);
			}
		}
		return result;
	}

	/**
	 * Delete the given generator from the given configuration
	 *
	 * @param config
	 * @param in
	 */
	public static void deleteGeneratorFromConf(Configuration config, Generator in) {
		Set<GeneratorConfiguration> eltsGc = new HashSet<GeneratorConfiguration>();

		for (GeneratorConfiguration gc : config.getGeneratorConfigurations()) {
			if (gc.getId().equals(in.getId()) && gc.getId_techno_version().equals(in.getParent().getId()) && gc.getId_metamodel().equals(in.getParent().getParent().getParent().getId())) {
				eltsGc.add(gc);
			}
		}
		config.getGeneratorConfigurations().removeAll(eltsGc);
	}

	/**
	 * Delete the given deployer from the given configuration
	 *
	 * @param config
	 * @param in
	 */
	public static void deleteDeployerFromConf(Configuration config, Deployer in) {
		Set<DeployerConfiguration> eltsDc = new HashSet<DeployerConfiguration>();
		for (DeployerConfiguration dc : config.getDeployerConfigurations()) {
			if (dc.getId().equals(in.getId()) && dc.getId_techno_version().equals(in.getParent().getId()) && dc.getImpl_class().equals(((Deployer) in).getLaunchClass())) {
				eltsDc.add(dc);
			}
		}
		config.getDeployerConfigurations().removeAll(eltsDc);
	}

	/**
	 * Return the list of componant configuration for a specific config
	 *
	 * @param config
	 * @return
	 */
	public static List<ComponantConfiguration> getComponantConfigurations(Configuration config) {
		List<ComponantConfiguration> l = new ArrayList<ComponantConfiguration>();
		l.addAll(config.getDeployerConfigurations());
		l.addAll(config.getGeneratorConfigurations());
		return l;
	}

	public static ComponantConfiguration getComponantConfiguration(Configuration config, String componantId) {
		List<ComponantConfiguration> l = getComponantConfigurations(config);
		for (ComponantConfiguration c : l) {
			if (c.getId().equals(componantId)) {
				return c;
			}
		}
		return null;
	}

	public static boolean ComponantConfigurationsContains(Configuration config, String componantId) {
		if (getComponantConfiguration(config, componantId) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Return a map with association model <> metaModel name
	 *
	 * @param models
	 * @param doValidation
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	public static Map<String, List<IFile>> getAssociatedMetaModel(List<Model> models) throws Exception {
		Map<String, List<IFile>> result = new HashMap<String, List<IFile>>();
		for (Model model : models) {

			IFile file = getIFileForModel(model);

			EPackage metaModel = getMetaModelForModel(model);

			if (metaModel != null) {
				if (!result.containsKey(metaModel.getNsURI())) {
					result.put(metaModel.getNsURI(), new ArrayList<IFile>());
				}

				if (file.exists()) {
					result.get(metaModel.getNsURI()).add(file);
				} else {
					throw new IOException(System.getProperty("line.separator") + "No model found at " + file.getFullPath());
				}
			}
		}
		return result;
	}

	/**
	 * Return the metamodel of a given model
	 *
	 * @param model
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static EPackage getMetaModelForModel(Model model) throws Exception {
		Resource loadedModel = getResourceForModel(model);
		EPackage metaModel = getMetaModelEpackage(loadedModel);
		return metaModel;
	}

	/**
	 * Return the resource for the given model and resource set
	 *
	 * @param rs
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static Resource getResourceForModel(Model model) throws Exception {
		ResourceSet rs = getRessourceSetForModel(model);
		IFile file = getIFileForModel(model);
		String fullPath = file.getRawLocation().toOSString();
		Resource loadedModel;
		try {
			loadedModel = EResourceUtils.openModel(fullPath, null, rs);
		} catch (IOException e) {
			IOException ioe = new IOException(System.getProperty("line.separator") + "Error with file " + file.getName() + " (check that it's a correct model file) [" + e.getMessage() + "]");
			ioe.setStackTrace(e.getStackTrace());
			throw ioe;
		}
		return loadedModel;
	}

	/**
	 * Return the IFiel for the given Model
	 *
	 * @param model
	 * @return
	 * @throws IOException
	 */
	public static IFile getIFileForModel(Model model) throws IOException {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(model.getFile()));
		if (!file.exists()) {
			throw new IOException(System.getProperty("line.separator") + "File " + file.getName() + " doesn't exist.");
		}
		return file;
	}

	/**
	 * Return the ressourceSet for a model
	 *
	 * @param model
	 * @return
	 * @throws IOException
	 */
	public static ResourceSet getRessourceSetForModel(Model model) throws IOException {
		Resource modelResource = null;
		try {
			modelResource = EResourceUtils.createResource(model.getFile());
		} catch (IOException e) {
			throw new IOException(System.getProperty("line.separator") + "Error for file/model " + model.getName());
		}
		ResourceSet rs = modelResource.getResourceSet();
		return rs;
	}

	/**
	 * Return the meta model EPackage
	 *
	 * @param r
	 * @return
	 */
	public static EPackage getMetaModelEpackage(Resource r) {
		EPackage result = null;
		if (r != null) {
			if (r.getContents() != null && r.getContents().size() > 0) {
				result = (EPackage) r.getContents().get(0).eClass().getEPackage();
			}
		}
		return result;
	}

	/**
	 * Return the root element of a model
	 *
	 * @param model
	 * @return
	 */
	public static EObject getRootElement(Resource model) {
		EObject te = null;
		if (model.getContents() != null && model.getContents().size() > 0) {
			EObject eo = model.getContents().get(0);
			te = getTopElement(eo);

		}
		return te;
	}

	/**
	 * Take a EObject and will return the top container
	 *
	 * @param eo
	 * @return
	 */
	public static EObject getTopElement(EObject eo) {
		if (eo.eContainer() != null) {
			return getTopElement(eo.eContainer());
		} else {
			return eo;
		}
	}

	/**
	 * Launch validation on given EObject
	 *
	 * @param eo
	 * @return
	 */
	public static boolean validate(EObject eo) {
		Diagnostician diag = new Diagnostician();
		BasicDiagnostic diagnostics = diag.createDefaultDiagnostic(eo);
		return diag.validate(eo, diagnostics);
	}

	public static boolean validate(IFile modelFile) throws Exception {
		Resource loadedModel = null;
		String fullPath = modelFile.getRawLocation().toOSString();
		Resource modelResource = null;
		try {
			modelResource = EResourceUtils.createResource(modelFile.getFullPath().toOSString());
		} catch (IOException e) {
			throw new IOException(System.getProperty("line.separator") + "Error for file/model " + modelFile.getName());
		}
		ResourceSet rs = modelResource.getResourceSet();
		if (!modelFile.exists()) {
			throw new IOException(System.getProperty("line.separator") + "File " + modelFile.getName() + " doesn't exist.");
		}
		try {
			loadedModel = EResourceUtils.openModel(fullPath, null, rs);
			EObject te = getRootElement(loadedModel);
			if (te != null) {
				return validate(te);
			} else {
				throw new IOException(System.getProperty("line.separator") + "No root element found in " + modelFile.getFullPath() + ". Model empty?");
			}
		} catch (IOException e) {
			IOException ioe = new IOException(System.getProperty("line.separator") + "Error with file " + modelFile.getName() + " (check that it's a correct model file)");
			ioe.setStackTrace(e.getStackTrace());
			throw ioe;
		}
	}

	/**
	 * Check if the element given is active in the key
	 *
	 * @param el
	 *            : the element
	 * @return true if valid, false if not
	 */
	@SuppressWarnings("unchecked")
	public static Boolean checkElementValidity(TreeElement el) {
		// If the element is a component and not valid we don't enable it
		try {
			ImplNode iN = ((ImplNode) el);
			Class<Checkable> gen;
			if (Platform.getBundle(iN.getId()) != null) {
				gen = Platform.getBundle(iN.getId()).loadClass(iN.getLaunchClass());
				Checkable gener = gen.newInstance();
				return gener.check();
			} else {
				throw new Exception("Error : " + iN.getId() + " isn't found as a plugin. Check your extension file.");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * scan all plugins and build the complete list of ModuleConstraint
	 *
	 * @return
	 */
	public static List<com.bluexml.side.util.dependencies.ModuleConstraint> buildOfflineConfiguration() {
		List<com.bluexml.side.util.dependencies.ModuleConstraint> lmc = new ArrayList<com.bluexml.side.util.dependencies.ModuleConstraint>();
		IConfigurationElement[] contributions = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSIONPOINT_ID);
		// get all module constraints and construct instance of
		// com.bluexml.side.util.dependencies.ModuleConstraint
		for (IConfigurationElement config_exp : contributions) {
			String nodeName = APPLICATION_CONSTRAINTS;
			List<IConfigurationElement> matchs = getIConfigurationElementsByName(config_exp, nodeName);
			for (IConfigurationElement configurationElement : matchs) {
				String moduleId = configurationElement.getAttribute(APPLICATION_CONSTRAINTS_MODULEID);
				String moduleType = configurationElement.getAttribute(APPLICATION_CONSTRAINTS_MODULETYPE);
				String versionMax = configurationElement.getAttribute(APPLICATION_CONSTRAINTS_VERSIONMAX);
				String versionMin = configurationElement.getAttribute(APPLICATION_CONSTRAINTS_VERSIONMIN);
				String technologyVersion = configurationElement.getAttribute(APPLICATION_CONSTRAINTS_TECHVERSION);
				com.bluexml.side.util.dependencies.ModuleConstraint mc = new com.bluexml.side.util.dependencies.ModuleConstraint(moduleId, technologyVersion, moduleType, versionMin, versionMax);
				lmc.add(mc);
			}
		}

		return lmc;
	}

	/**
	 * take a configuration and update all properties from SIDE extension, this
	 * manage : <li>deleted elements (options, dependencies)</li> <li>added
	 * elements</li> <li>updates elements</li>
	 *
	 * @param config
	 * @throws Exception
	 */
	public static void updateConfigurationFromExtensionPoint(Configuration config) throws Exception {

		// scan all generator
		List<GeneratorConfiguration> lgen = config.getGeneratorConfigurations();
		for (GeneratorConfiguration generatorConfiguration : lgen) {
			// get the extension declaration fragment
			IConfigurationElement extFrag = getIConfigurationElement(generatorConfiguration);
			IConfigurationElement techVersion = (IConfigurationElement) extFrag.getParent();
			IConfigurationElement technology = (IConfigurationElement) techVersion.getParent();
			IConfigurationElement metamodel = (IConfigurationElement) technology.getParent();

			// update properties
			// metamodel
			generatorConfiguration.setId_metamodel(metamodel.getAttribute("id"));
			generatorConfiguration.setMetaModelName(metamodel.getAttribute("name"));
			// technology
			generatorConfiguration.setTechnologyName(technology.getAttribute("id"));
			// technology_version
			generatorConfiguration.setId_techno_version(techVersion.getAttribute("id"));
			generatorConfiguration.setTechnologyVersionName(techVersion.getAttribute("version"));
			// generatorVersion
			generatorConfiguration.setGeneratorName(extFrag.getAttribute("version"));
			generatorConfiguration.setImpl_class(extFrag.getAttribute("class"));

			Map<String, IConfigurationElement> dependencies_ext = new HashMap<String, IConfigurationElement>();

			// Obligatory dependences
			Map<String, IConfigurationElement> dependencies_extObligatory = new HashMap<String, IConfigurationElement>();
			// add generator/deployer dependencies to check
			IConfigurationElement[] arrayOfdependencies_ext = extFrag.getChildren(APPLICATION_CONSTRAINTS);
			for (IConfigurationElement configurationElement : arrayOfdependencies_ext) {
				dependencies_ext.put(configurationElement.getAttribute(APPLICATION_CONSTRAINTS_MODULEID), configurationElement);
				dependencies_extObligatory.put(configurationElement.getAttribute(APPLICATION_CONSTRAINTS_MODULEID), configurationElement);
			}

			// check options
			EList<Option> options = generatorConfiguration.getOptions();
			List<String> options_ext = new ArrayList<String>();
			IConfigurationElement[] arrayOfoptions_ext = extFrag.getChildren("option");
			for (IConfigurationElement configurationElement : arrayOfoptions_ext) {
				options_ext.add(configurationElement.getAttribute("key"));

				IConfigurationElement[] arrayOfConstraints_ext = configurationElement.getChildren(APPLICATION_CONSTRAINTS);
				for (IConfigurationElement configurationElement2 : arrayOfConstraints_ext) {
					// add to list of dependencies to check
					dependencies_ext.put(configurationElement2.getAttribute(APPLICATION_CONSTRAINTS_MODULEID), configurationElement2);
				}
			}

			List<Option> optionsToRemove = new ArrayList<Option>();
			for (Option option : options) {
				// check if defined
				if (!options_ext.contains(option.getKey())) {
					// remove this
					optionsToRemove.add(option);
					// System.out.println("toRemove !"+option);
				} else {
					// check option constraints
				}
			}
			generatorConfiguration.getOptions().removeAll(optionsToRemove);

			// check dependencies
			EList<ModuleConstraint> mcs = generatorConfiguration.getModuleContraints();
			List<String> updatedConstraints = new ArrayList<String>();
			List<ModuleConstraint> mcsToRemove = new ArrayList<ModuleConstraint>();
			for (ModuleConstraint moduleConstraint : mcs) {
				if (!dependencies_ext.containsKey(moduleConstraint.getModuleId())) {
					// to remove
					mcsToRemove.add(moduleConstraint);
				} else {
					// update
					IConfigurationElement configurationElement = dependencies_ext.get(moduleConstraint.getModuleId());
					moduleConstraint.setModuleType(configurationElement.getAttribute(APPLICATION_CONSTRAINTS_MODULETYPE));
					moduleConstraint.setVersionMax(configurationElement.getAttribute(APPLICATION_CONSTRAINTS_VERSIONMAX));
					moduleConstraint.setVersionMin(configurationElement.getAttribute(APPLICATION_CONSTRAINTS_VERSIONMIN));
					moduleConstraint.setTechnologyVersion(configurationElement.getAttribute(APPLICATION_CONSTRAINTS_TECHVERSION));
					updatedConstraints.add(moduleConstraint.getModuleId());
				}
			}
			generatorConfiguration.getModuleContraints().removeAll(mcsToRemove);

			// add new constraints
			List<String> lnewConstraints = new ArrayList<String>();
			Set<String> s = dependencies_extObligatory.keySet();
			s.removeAll(updatedConstraints);
			lnewConstraints.addAll(s);
			for (String string : lnewConstraints) {
				IConfigurationElement newConstraint_ext = dependencies_ext.get(string);
				ModuleConstraint moduleConstraint = ApplicationFactory.eINSTANCE.createModuleConstraint();
				moduleConstraint.setModuleId(newConstraint_ext.getAttribute(APPLICATION_CONSTRAINTS_MODULEID));
				moduleConstraint.setModuleType(newConstraint_ext.getAttribute(APPLICATION_CONSTRAINTS_MODULETYPE));
				moduleConstraint.setVersionMax(newConstraint_ext.getAttribute(APPLICATION_CONSTRAINTS_VERSIONMAX));
				moduleConstraint.setVersionMin(newConstraint_ext.getAttribute(APPLICATION_CONSTRAINTS_VERSIONMIN));
				moduleConstraint.setTechnologyVersion(newConstraint_ext.getAttribute(APPLICATION_CONSTRAINTS_TECHVERSION));
				generatorConfiguration.getModuleContraints().add(moduleConstraint);
			}
		}

	}

	/**
	 * search in all SIDE extension, an extension fragment that match with this
	 * ComponantConfiguration
	 *
	 * @param conf
	 * @return
	 * @throws Exception
	 */
	public static IConfigurationElement getIConfigurationElement(ComponantConfiguration conf) throws Exception {
		IConfigurationElement[] contributions = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSIONPOINT_ID);
		for (IConfigurationElement config_exp : contributions) {
			// System.err.println("DEBUG getIConfigurationElement: " +
			// config_exp.getName() + " " + config_exp.getNamespaceIdentifier()
			// + " (" + config_exp.getAttribute("id") + " " +
			// config_exp.getAttribute("name") + ")");
			Map<String, String> query = new HashMap<String, String>();
			query.put("id", conf.getId());
			String nodeName = "";
			if (conf instanceof GeneratorConfiguration) {
				nodeName = "generatorVersion";
			} else {
				nodeName = "deployerVersion";
			}
			List<IConfigurationElement> matchs = getIConfigurationElementBy(config_exp, nodeName, query);
			if (matchs.size() == 1) {
				return matchs.get(0);
			} else if (matchs.size() > 1) {
				throw new Exception("something wrong in extension entries");
			}
		}
		throw new Exception("Extension fragment not found");
	}

	/**
	 * search in extension fragment that match with given name and a set of
	 * attributes
	 *
	 * @param parent
	 * @param nodeName
	 * @param parametersMatchs
	 * @return
	 */
	public static List<IConfigurationElement> getIConfigurationElementBy(IConfigurationElement parent, String nodeName, Map<String, String> parametersMatchs) {
		List<IConfigurationElement> l = getIConfigurationElementsByName(parent, nodeName);
		ArrayList<IConfigurationElement> result = new ArrayList<IConfigurationElement>();
		for (IConfigurationElement configurationElement : l) {
			if (parametersMatchs(configurationElement, parametersMatchs)) {
				result.add(configurationElement);
			}
		}
		return result;
	}

	/**
	 * return a list of extension fragment that match with the given name
	 *
	 * @param parent
	 * @param name
	 * @return
	 */
	public static List<IConfigurationElement> getIConfigurationElementsByName(IConfigurationElement parent, String name) {
		ArrayList<IConfigurationElement> l = new ArrayList<IConfigurationElement>();
		if (parent.getName().equals(name)) {
			l.add(parent);
		}
		for (IConfigurationElement configurationElement : parent.getChildren()) {
			List<IConfigurationElement> ll = getIConfigurationElementsByName(configurationElement, name);
			l.addAll(ll);
		}
		return l;
	}

	/**
	 * test if the given extension fragment match with all attributes values
	 *
	 * @param node
	 * @param parametersMatchs
	 * @return
	 */
	public static boolean parametersMatchs(IConfigurationElement node, Map<String, String> parametersMatchs) {
		Set<String> g = new HashSet<String>();
		String[] attrs = node.getAttributeNames();
		for (String string : attrs) {
			g.add(string);
		}
		boolean okSubSet = g.containsAll(parametersMatchs.keySet());
		if (!okSubSet) {
			return false;
		}
		for (Map.Entry<String, String> match : parametersMatchs.entrySet()) {
			if (!node.getAttribute(match.getKey()).equals(match.getValue())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * build a tmp project containning all dependencies and use mvn
	 * dependency:go-offline to populate local copy (.m2/repository), so SIDE
	 * can work offline
	 *
	 * @throws Exception
	 */
	public static void prepareForOffline() throws Exception {
		File tmpFile = File.createTempFile("side", "offline");
		File tmpFolder = new File(tmpFile.getParentFile(), "tmpFolder");
		FileHelper.deleteFile(tmpFolder, false);
		tmpFolder.mkdirs();
		List<com.bluexml.side.util.dependencies.ModuleConstraint> lmc = buildOfflineConfiguration();
		DependencesManager dm = new DependencesManager(lmc, false);
		dm.goOffline(tmpFolder);

	}
}
