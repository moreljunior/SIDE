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
package com.bluexml.side.util.generator.acceleo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.bluexml.side.util.componentmonitor.ComponentMonitor;
import com.bluexml.side.util.generator.GeneratorException;
import com.bluexml.side.util.generator.acceleo.chain.CustomCChain;
import com.bluexml.side.util.libs.IFileHelper;

import fr.obeo.acceleo.chain.ActionSet;
import fr.obeo.acceleo.chain.ChainFactory;
import fr.obeo.acceleo.chain.EmfMetamodel;
import fr.obeo.acceleo.chain.Folder;
import fr.obeo.acceleo.chain.Generate;
import fr.obeo.acceleo.chain.Generator;
import fr.obeo.acceleo.chain.Log;
import fr.obeo.acceleo.chain.Model;
import fr.obeo.acceleo.chain.Repository;
import fr.obeo.acceleo.ecore.factories.EFactory;
import fr.obeo.acceleo.gen.IGenFilter;
import fr.obeo.acceleo.gen.template.eval.LaunchManager;
import fr.obeo.acceleo.tools.classloaders.AcceleoClassLoader;

public class SimpleAcceleoGenerator {
	private String projectName = ".simpleGenerator";

	private static final String DEFAULT_ENCODING = "UTF-8"; //$NON-NLS-1$
	private String fileEncoding = System.getProperty("file.encoding"); //$NON-NLS-1$

	private String metamodelURI = null;

	private List<String> templates = null;

	private ComponentMonitor monitor = null;

	public SimpleAcceleoGenerator(String metamodelURI, List<String> templates, String projectName, ComponentMonitor monitor) {
		this.metamodelURI = metamodelURI;
		this.templates = templates;
		if (projectName == null) {
			projectName = ".simpleGenerator";
		}
		this.projectName = projectName;
		this.monitor = monitor;
	}

	@SuppressWarnings("unchecked")
	public Collection<IFile> generate(IFile model, String generationPath) throws Exception {
		AcceleoClassLoader.setPreferredClassLoader(this.getClass().getClassLoader());
		// System.out.println("Generate IFile model");

		// References to files in the project
		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

		// System.out.println("Iworkspace: " + myWorkspaceRoot );
		// Temporary project
		IProject tmpProject = myWorkspaceRoot.getProject(projectName);

		// System.out.println("tmpProject: " + tmpProject );
		// System.out.println("tmpProject.exists: " + tmpProject.exists() );

		if (tmpProject.exists()) {
			tmpProject.delete(true, null);
		}
		// create and open if necessary
		if (!tmpProject.exists()) {
			tmpProject.create(null);
		}

		// System.out.println("tmpProject.isOpen(): " + tmpProject.isOpen() );
		if (!tmpProject.isOpen()) {
			tmpProject.open(null);
		}

		// System.out.println("tmpProject.exists(): " + tmpProject.exists() +
		// " -> " + tmpProject.getFullPath());

		// CChain chain = new ChainFactory.eINSTANCE.createChain();
		CustomCChain chain = new CustomCChain();

		// Repository
		Repository repository = ChainFactory.eINSTANCE.createRepository();
		EFactory.eSet(chain, "repository", repository); //$NON-NLS-1$

		// Action Set
		ActionSet actionSet = ChainFactory.eINSTANCE.createActionSet();
		EFactory.eAdd(chain, "actions", actionSet); //$NON-NLS-1$

		// Model file
		Model modelPath = ChainFactory.eINSTANCE.createModel();
		EFactory.eAdd(repository, "files", modelPath); //$NON-NLS-1$
		EFactory.eSet(modelPath, "path", model.getFullPath().toString()); //$NON-NLS-1$
		model.refreshLocal(-1, null);

		// Target folder
		Folder folder = ChainFactory.eINSTANCE.createFolder();

		// System.out.println("folder: " + folder.getPath());

		EFactory.eAdd(repository, "files", folder); //$NON-NLS-1$

		if (generationPath == null || generationPath.length() == 0) {
			monitor.getLog().addErrorLog("No Target path setted.", "There is no target path setted for generation.", null); //$NON-NLS-1$ //$NON-NLS-2$
			throw new Exception("Target path must be setted !"); //$NON-NLS-1$
		}
		if (!new File(generationPath).exists()) {
			new File(IFileHelper.getSystemFolderPath(generationPath)).mkdirs();
		}
		
		EFactory.eSet(folder, "path", generationPath); //$NON-NLS-1$

		// Log
		File flog = new File(IFileHelper.getSystemFolderPath(monitor.getLog().getGeneratorLogFile()));
		flog.delete();
		Log log = ChainFactory.eINSTANCE.createLog();
		EFactory.eAdd(repository, "files", log); //$NON-NLS-1$
		EFactory.eSet(log, "path", monitor.getLog().getGeneratorLogFile()); //$NON-NLS-1$

		// Metamodel file
		EmfMetamodel pim = ChainFactory.eINSTANCE.createEmfMetamodel();
		EFactory.eAdd(repository, "files", pim); //$NON-NLS-1$
		EFactory.eSet(pim, "path", metamodelURI); //$NON-NLS-1$

		for (String templateFile : templates) {

			// System.out.println("Templates: " + templateFile);
			// Generator
			Generator generator = ChainFactory.eINSTANCE.createGenerator();
			EFactory.eAdd(repository, "files", generator); //$NON-NLS-1$
			EFactory.eSet(generator, "path", templateFile); //$NON-NLS-1$

			// Action
			Generate gAction = ChainFactory.eINSTANCE.createGenerate();
			EFactory.eAdd(actionSet, "actions", gAction); //$NON-NLS-1$
			EFactory.eSet(gAction, "folder", folder); //$NON-NLS-1$
			EFactory.eSet(gAction, "log", log); //$NON-NLS-1$
			EFactory.eSet(gAction, "metamodel", pim); //$NON-NLS-1$
			EFactory.eSet(gAction, "model", modelPath); //$NON-NLS-1$
			EFactory.eSet(gAction, "generator", generator); //$NON-NLS-1$
		}

		// Register the default resource factory -- only needed for
		// stand-alone!
		IFile fchain = tmpProject.getFile("side_generation.chain"); //$NON-NLS-1$
		// System.out.println("fchain.getFullPath(): " + fchain.getFullPath());
		// URI chainURI =
		// Resources.createPlatformResourceURI(fchain.getFullPath().toString());
		// System.out.println("log1");
		ResourceSet resourceSet = new ResourceSetImpl();
		// System.out.println("log2");
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		// System.out.println("log3");
		// Resource chainResource = resourceSet.createResource(chainURI);
		// System.out.println("log4");
		// chainResource.getContents().add(chain);
		// System.out.println("log5");
		chain.setFile(fchain);
		// System.out.println("log6");
		// chainResource.save(Collections.EMPTY_MAP);
		// System.out.println("log7");
		// System.out.println("BeforeGen");
		// AbstractGenerator.printConfiguration();
		// System.out.println("log8");
		// try {
		if (monitor != null) {
			chain.launch(genFilter, monitor, LaunchManager.create("run", false)); //$NON-NLS-1$
		} else {
			chain.launch(genFilter, new NullProgressMonitor(), LaunchManager.create("run", false)); //$NON-NLS-1$
		}

		// } catch (CoreException e) {
		// e.printStackTrace();
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		// System.out.println("log9");
		// System.out.println("AfterGen");
		// AbstractGenerator.printConfiguration();

		// files is generated we must update folder before do anything else

		List<IFile> generatedFiles = (List<IFile>) chain.getGeneratedFiles();
		// List<?> generatedFiles = chain.getGeneratedFiles();
		// System.out.println("log10");
		for (Object file : generatedFiles) {
			filter((IFile) file);
		}
		// System.out.println("generatedFiles: " + generatedFiles.toString());

		// IProject myproject = myWorkspaceRoot.getProject(".side_generation");
		tmpProject.delete(true, null);

		Collection<IFile> result = new ArrayList<IFile>();
		for (Object o : chain.getGeneratedFiles()) {
			if (o instanceof IFile) {
				result.add((IFile) o);
			}
		}
		// test if generator has error or not
		if (flog.exists()) {
			monitor.addTextAndLog(Activator.Messages.getString("AbstractAcceleoGenerator_5"), flog.getAbsolutePath()); //$NON-NLS-1$
			throw new GeneratorException(Activator.Messages.getString("AbstractAcceleoGenerator_39")); //$NON-NLS-1$
		}
		// System.out.println("result: " + result.toString());
		return result;
	}

	public void filter(IFile file) throws Exception {

		// System.out.println("filter file: " + file.toString());

		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			File source = IFileHelper.getFile(file);
			// System.out.println("FILTER :: file :" + source.getName());

			InputStream is = new FileInputStream(source);
			BufferedInputStream bis = new BufferedInputStream(is);
			InputStreamReader isr = new InputStreamReader(bis, fileEncoding);
			br = new BufferedReader(isr);

			File destination = new File(source.getAbsolutePath() + ".fixed"); //$NON-NLS-1$
			pw = new PrintWriter(destination, DEFAULT_ENCODING);
			String ligne;
			while ((ligne = br.readLine()) != null) {
				pw.println(ligne);
			}
			pw.close();
			br.close();

			source.delete();
			destination.renameTo(source);
		} catch (Exception e) {
			e.printStackTrace();

			try {
				pw.close();
			} catch (Throwable e1) {
			}
			try {
				br.close();
			} catch (Throwable e1) {
			}
			throw new Exception(Activator.Messages.getString("AbstractAcceleoGenerator_42"), e); //$NON-NLS-1$
		}
	}

	private static final IGenFilter genFilter = new IGenFilter() {
		public boolean filter(java.io.File script, IFile targetFile, EObject object) throws CoreException {
			return true;
		}
	};
}
