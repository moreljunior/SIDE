package com.bluexml.side.clazz.edit.ui.actions.initializer.creator;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import com.bluexml.side.Util.ecore.ModelInitializationUtils;
import com.bluexml.side.clazz.edit.ui.actions.initializer.InitializerRegister;
import com.bluexml.side.clazz.edit.ui.actions.initializer.ModelCreator;

public abstract class ModelAndDiagramCreator extends ModelCreator {
	protected String diagramTypeId;
	protected EObject rootDiagram;
	protected boolean diagramInitialized = false;

	public ModelAndDiagramCreator(IProject project, String newModelExt, String modelTypeSegment, InitializerRegister register, ASK_USER ask, String modelFileName, String diagramTypeId) throws IOException {
		super(project, newModelExt, modelTypeSegment, register, ask, modelFileName);
		this.diagramTypeId = diagramTypeId;
	}

	/*
	 * (non-Javadoc)
	 * @seecom.bluexml.side.clazz.edit.ui.actions.initializer.ModelInitializer#
	 * initialize()
	 */
	@Override
	public void initialize() throws Exception {
		super.initialize();
		if (!headless && !diagramInitialized) {
			if (rootDiagram == null) {
				rootDiagram = newRootObject;
			}
			ModelInitializationUtils.createDiagramFromExistingModel(rootDiagram, diagramTypeId);
			diagramInitialized = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * @seecom.bluexml.side.clazz.edit.ui.actions.initializer.ModelInitializer#
	 * postInitialization(org.eclipse.core.resources.IFile,
	 * org.eclipse.ui.IWorkbenchPage, org.eclipse.ui.IEditorDescriptor,
	 * org.eclipse.ui.IEditorPart)
	 */
	@Override
	protected void postInitialization(IFile newModelFile, IWorkbenchPage page, IEditorDescriptor desc, IEditorPart editorPart) throws Exception {
		page.closeEditor(editorPart, false);
		// save initialized resource
		saveNewModel();
	}
}
