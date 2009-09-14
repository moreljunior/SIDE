package com.bluexml.side.integration.standalone.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.osgi.framework.Bundle;

import com.bluexml.side.application.Application;
import com.bluexml.side.application.ApplicationPackage;
import com.bluexml.side.application.Configuration;
import com.bluexml.side.application.ConfigurationParameters;
import com.bluexml.side.application.DeployerConfiguration;
import com.bluexml.side.application.GeneratorConfiguration;
import com.bluexml.side.application.Model;
import com.bluexml.side.application.Option;
import com.bluexml.side.application.StaticConfigurationParameters;
import com.bluexml.side.application.ui.action.ApplicationDialog;
import com.bluexml.side.application.ui.action.utils.ApplicationUtil;
import com.bluexml.side.util.componentmonitor.NullComponentMonitor;
import com.bluexml.side.util.dependencies.DependencesManager;
import com.bluexml.side.util.dependencies.ModuleConstraint;
import com.bluexml.side.util.deployer.Deployer;
import com.bluexml.side.util.documentation.LogSave;
import com.bluexml.side.util.documentation.structure.enumeration.LogType;
import com.bluexml.side.util.generator.AbstractGenerator;
import com.bluexml.side.util.libs.IFileHelper;

public class Generate extends Thread {

	private String logPath;
	private String genPath;
	protected String lineSeparator = System.getProperty("line.separator"); //$NON-NLS-1$
	protected String fileSeparator = System.getProperty("file.separator"); //$NON-NLS-1$
	/* ############### GeneratePopUp.java method ################ */
	public Configuration configuration;
	public List<String> staticParameters;
	public List<Model> models;
	public Application application;

	/**
	 * Analyse the model.application file and extract the configuration
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public Generate(File filePath, String name) {

		// System.out.println("Name: " + name);

		// Create the IFile
		IFile file = null;
		try {
			// IWorkspace ws = ResourcesPlugin.getWorkspace();
			// IProject project= ws.getRoot().getProject("StandAlone");
			// if (!project.exists())
			// project.create(null);
			// if (!project.isOpen())
			// project.open(null);
			// IPath location = new Path(filePath.getAbsolutePath());

			// file = project.getFile(location.lastSegment());

			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath location = Path.fromOSString(filePath.getAbsolutePath());
			file = workspace.getRoot().getFileForLocation(location);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// System.out.println("getWorkspace: " +
		// ResourcesPlugin.getWorkspace());
		// System.out.println("getRoot: "
		// + ResourcesPlugin.getWorkspace().getRoot().exists() + " -> "
		// + ResourcesPlugin.getWorkspace().getRoot());
		// System.out.println("getPath: "
		// + ResourcesPlugin.getWorkspace().getRoot().getFile(
		// new Path(filePath.getAbsolutePath())));

		// System.out.println("\tfile.exists(): " + file.exists());

		URI uri = null;
		// System.out.println("\tURI");
		try {

			// System.out.println("\tgetRawLocation: " + file.getRawLocation());
			// System.out.println("\ttoFile: " +
			// file.getRawLocation().toFile());
			// System.out.println("\tpath: "
			// + file.getRawLocation().toFile().getPath());

			String absolutePath = file.getRawLocation().toFile().getAbsolutePath();
			// System.out.println("\tabsolutePath: " + absolutePath);

			uri = URI.createFileURI(new File(absolutePath).getAbsolutePath());

			// System.out.println("URI: " + uri);

		} catch (Exception e) {
			System.out.println("Exception : " + e.getClass());
		}

		// System.out.println("\tXMI");
		XMIResource resource = new XMIResourceImpl(uri);
		// System.out.println("\tFILE INPUT");

		FileInputStream fi = null;
		try {
			fi = new FileInputStream(file.getRawLocation().toFile());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		// System.out.println("\tMAP");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(ApplicationPackage.eINSTANCE.getNsURI(), ApplicationPackage.eINSTANCE);
		map.put(XMLResource.OPTION_SCHEMA_LOCATION_IMPLEMENTATION, Boolean.TRUE);

		// System.out.println("\tLOAD");
		try {
			resource.load(fi, map);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			application = (Application) resource.getContents().get(0);
			// System.out.println("\tapplication: " + application);
			staticParameters = ApplicationDialog.staticFieldsName;
			// System.out.println("\tstaticParameters: " + staticParameters);
			configuration = application.getConfiguration(name);
			// System.out.println("\tconfiguration: " + configuration);
			models = ApplicationUtil.getModels(application);
			// System.out.println("\tmodels: " + models);
		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/* ############################### */
	/**
	 * Launch generation on all generator version selected
	 * 
	 * @param configuration
	 * @param staticParameters
	 * @param models
	 * @param logLink2
	 * @param progressBar
	 * @param label
	 * @param styletext
	 */
	// public void run(Configuration configuration, List<String>
	// staticParameters, List<Model> models) throws ClassNotFoundException,
	// InstantiationException, IllegalAccessException, IOException {
	public void run() {
		// System.out.println("Run !!!!!!");

		// First we seek the generator parameters, and separate fields
		// of dynamic fields
		Map<String, String> configurationParameters = new HashMap<String, String>();
		Map<String, String> generationParameters = new HashMap<String, String>();
		// System.out.println("log0");
		for (ConfigurationParameters param : configuration.getParameters()) {
			if (staticParameters.contains(param.getKey())) {
				configurationParameters.put(param.getKey(), param.getValue());
				// TODO : Check to know if Log Path and Generation Path are set
				// :

			} else {
				generationParameters.put(param.getKey(), param.getValue());
			}
		}

		// System.out.println("log1");
		// Secondly we get the meta-model associated to a model
		HashMap<String, List<IFile>> modelsInfo = null;
		boolean skipValidation = true;
		if (configurationParameters.containsKey(ApplicationDialog.KEY_SKIPVALIDATION)) {
			skipValidation = Boolean.valueOf(configurationParameters.get(ApplicationDialog.KEY_SKIPVALIDATION));
		}
		// System.out.println("log2");
		try {
			modelsInfo = (HashMap<String, List<IFile>>) ApplicationUtil.getAssociatedMetaModel(models);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("log3");
		// Validation :
		boolean modelWithError = false;
		if (!skipValidation) {
			Iterator<List<IFile>> it = modelsInfo.values().iterator();
			List<IFile> listModel;
			while (it.hasNext()) {
				listModel = it.next();
				for (IFile m : listModel) {
					try {
						if (!ApplicationUtil.validate(m)) {
							modelWithError = true;
						}

					} catch (Exception e) {
						modelWithError = true;
						e.printStackTrace();
					}
				}
			}
		}
		// System.out.println("modelWithError: " + modelWithError);
		// System.out.println("log4");
		if (!modelWithError) {
			logPath = getLogPath(configuration, configurationParameters);
			genPath = getGenerationPath(configuration, configurationParameters);

			// System.out.println("logPath: " + logPath);
			// System.out.println("genPath: " + genPath);

			try {
				// System.out.println("configuration: " + configuration);
				// System.out.println("modelsInfo: " + modelsInfo);
				// System.out.println("configurationParameters: "
				// + configurationParameters);
				// System.out.println("generationParameters: "
				// + generationParameters);
				generate(configuration, modelsInfo, configurationParameters, generationParameters);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			// Refresh log and generation folder
			refreshFolders();
		}
		// System.out.println("log5");
	}

	/**
	 * Refresh log and generation paths
	 */
	private void refreshFolders() {
		try {
			IFileHelper.refreshFolder(logPath);
			IFileHelper.refreshFolder(genPath);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the log path (folder path + configuration name)
	 * 
	 * @param configuration
	 * @param configurationParameters
	 * @return
	 */
	private String getLogPath(Configuration configuration, Map<String, String> configurationParameters) {
		return configurationParameters.get(StaticConfigurationParameters.GENERATIONOPTIONSLOG_PATH.getLiteral()) + System.getProperty("file.separator") + configuration.getName();
	}

	private String getGenerationPath(Configuration configuration, Map<String, String> configurationParameters) {
		return configurationParameters.get(StaticConfigurationParameters.GENERATIONOPTIONSDESTINATION_PATH.getLiteral()) + System.getProperty("file.separator");
	}

	private void generate(final Configuration configuration, final HashMap<String, List<IFile>> modelsInfo, final Map<String, String> configurationParameters, final Map<String, String> generationParameters) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {

		// System.out.println("Generate: ");

		// System.out.println("\tRun");
		// Clean if needed :
		boolean doClean = Boolean.parseBoolean(configurationParameters.get(ApplicationDialog.KEY_DOCLEAN));
		if (doClean) {
			try {
				clean();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		// System.out.println("\tconfiguration: " + configuration);
		// System.out.println("\tmodelsInfo: " + modelsInfo);
		// System.out.println("\tconfigurationParameters: "
		// + configurationParameters);
		// System.out.println("\tgenerationParameters: " +
		// generationParameters);

		boolean error = generate_(configuration, modelsInfo, configurationParameters, generationParameters);

		// System.out.println("\tError: " + error);

		error &= deploy_(configuration, modelsInfo, configurationParameters, generationParameters);

		try {
			LogSave.buildGeneraLogFile(logPath);
			IFileHelper.refreshFolder(logPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void clean() throws CoreException {
		IFileHelper.deleteFolderContent(IFileHelper.getIFolder(logPath));
		IFileHelper.deleteFolderContent(IFileHelper.getIFolder(genPath));
	}

	private AbstractGenerator getGeneratorInstance(GeneratorConfiguration elem) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String launchGeneratorClass = elem.getImpl_class();
		String idGenerator = elem.getId();
		Bundle plugin = Platform.getBundle(idGenerator);
		Class<?> gen;
		Object genObj = null;
		if (plugin != null) {
			gen = plugin.loadClass(launchGeneratorClass);
			genObj = gen.newInstance();
			if (genObj instanceof AbstractGenerator) {
				AbstractGenerator generator = (AbstractGenerator) genObj;
				return generator;
			}
		}
		return null;
	}

	private boolean generate_(final Configuration configuration, final HashMap<String, List<IFile>> modelsInfo, final Map<String, String> configurationParameters, final Map<String, String> generationParameters) {
		// For all generator version we will call generation method
		boolean error = false;

		// System.out.println("Generate_");

		for (GeneratorConfiguration elem : configuration.getGeneratorConfigurations()) {
			String id_techno_version = elem.getId_techno_version();
			configurationParameters.put("technologyVersion", id_techno_version);
			configurationParameters.put("generatorName", elem.getGeneratorName());
			configurationParameters.put("generatorId", elem.getId());
			configurationParameters.put("metaModelName", elem.getMetaModelName());
			configurationParameters.put("technologyName", elem.getTechnologyName());
			configurationParameters.put("technologyVersionName", elem.getTechnologyVersionName());

			// We get the option for this generator
			Map<String, Boolean> generatorOptions = new HashMap<String, Boolean>();
			for (Option option : elem.getOptions()) {
				generatorOptions.put(option.getKey(), true);
			}

			AbstractGenerator generator = null;
			try {
				generator = getGeneratorInstance(elem);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}

			// We initialize the generator with all data collected in
			// application model
			if (generator != null) {

				// We generate only if there is meta-model available for
				// the generator
				if (generator.shouldGenerate(modelsInfo, elem.getId_metamodel())) {

					NullComponentMonitor generationMonitor = new NullComponentMonitor(configurationParameters, LogType.GENERATION);

					try {
						List<ModuleConstraint> lmc = new ArrayList<ModuleConstraint>();
						EList<com.bluexml.side.application.ModuleConstraint> l = elem.getModuleContraints();
						for (int c = 0; c < l.size(); c++) {
							com.bluexml.side.application.ModuleConstraint current = l.get(c);
							lmc.add(new ModuleConstraint(current.getModuleId(), current.getTechnologyVersion(), current.getModuleType(), current.getVersionMin(), current.getVersionMax()));
						}
						DependencesManager dm = new DependencesManager(lmc, false);

						generator.initialize(generationParameters, generatorOptions, configurationParameters, dm, generationMonitor);
					} catch (Exception e) {
						error = true;
						generationMonitor.getLog().addErrorLog("Initialization error : " + e.getMessage(), e, null);
						e.printStackTrace();
					}

					// The first one
					if (modelsInfo.size() > 0) {
						try {
							generator.generate(modelsInfo, elem.getId_metamodel());
							
							// System.out.println("\tlog92");
							generator.complete();
							// System.out.println("\tlog93");

							// System.out.println("\tlog10");
							// TODO : add feedback

						} catch (Exception e) {
							error = true;
							generationMonitor.getLog().addErrorLog("Generation error : " + e.getMessage(), e, null);
							e.printStackTrace();
						}

						// System.out.println("\tlog11");
						try {
							generator.createStampFile();
						} catch (Exception e) {
							generationMonitor.getLog().addErrorLog("Generation error : Stamp file error. " + e.getMessage(), e, null);
							e.printStackTrace();
						}
						// System.out.println("\tlog12");
					}
				}
				// System.out.println("\tlog13");
				String fileName = "gen_" + generator.getTechVersion() + ".xml";
				try {
					if (generator.getMonitor() != null) {
						generator.getMonitor().getLog().saveLog(fileName, logPath + fileSeparator + "work" + fileSeparator);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				error = true;
			}
		}
		return error;
	}

	private boolean deploy_(final Configuration configuration, final HashMap<String, List<IFile>> modelsInfo, final Map<String, String> configurationParameters, final Map<String, String> generationParameters) {
		boolean error = false;
		List<DeployerConfiguration> ldeployers = configuration.getDeployerConfigurations();
		for (DeployerConfiguration depConf : ldeployers) {
			String deployerClassName = depConf.getImpl_class();
			String id_deployer = depConf.getId();
			String id_techno = depConf.getId_techno_version();
			configurationParameters.put("technologyVersion", id_techno);
			configurationParameters.put("deployerName", depConf.getDeployerName());
			configurationParameters.put("deployerId", id_deployer);
			configurationParameters.put("metaModelName", depConf.getMetaModelName());
			configurationParameters.put("technologyName", depConf.getTechnologyName());
			configurationParameters.put("technologyVersionName", depConf.getTechnologyVersionName());
			configurationParameters.put("configurationName", configuration.getName());

			List<Option> options = depConf.getOptions();
			// We get the option for this generator
			List<String> deployerOptions = new ArrayList<String>();
			for (Option option : options) {
				deployerOptions.add(option.getKey());
			}
			Bundle plugin = Platform.getBundle(id_deployer);

			Class<?> gen;
			Object genObj = null;
			try {
				gen = plugin.loadClass(deployerClassName);
				genObj = gen.newInstance();
			} catch (ClassNotFoundException e1) {
				error = true;
				e1.printStackTrace();
			} catch (InstantiationException e) {
				error = true;
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				error = true;
				e.printStackTrace();
			} catch (Exception e) {
				error = true;
				e.printStackTrace();
			}
			try {
				IFileHelper.refreshFolder(logPath);
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
			if (genObj instanceof Deployer) {
				Deployer deployer = (Deployer) genObj;
				
				NullComponentMonitor deployerMonitor = new NullComponentMonitor(configurationParameters, LogType.DEPLOYMENT);
				deployer.initialize(configurationParameters, generationParameters, deployerOptions, deployerMonitor);
				try {
					deployer.deploy();
				} catch (Exception e) {
					e.printStackTrace();
					error = true;
				}

				try {
					deployer.moveStampFile(logPath);
				} catch (Exception e) {
					e.printStackTrace();
				}

				String fileName = "dep_" + deployer.getTechVersion() + ".xml";
				try {
					deployerMonitor.getLog().saveLog(fileName, logPath + System.getProperty("file.separator") + "work" + System.getProperty("file.separator"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return error;
	}
}
