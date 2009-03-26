package com.bluexml.side.clazz.generator.alfresco;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import org.jdom.Document;
import org.jdom.Element;

import com.bluexml.side.application.generator.acceleo.AbstractAcceleoGenerator;
import com.bluexml.side.util.libs.xml.XmlHelper;

public class ClassAlfrescoGenerator extends AbstractAcceleoGenerator {

	/*
	 * final fields used in generation too
	 */

	public static String TEMP_FOLDER = "tmp";
	public static String CONFIGURATION_PARAMETER_CATALINA_HOME = "CATALINA_HOME";
	public static String GENERATOR_OPTIONS_DATAMODEL = "alfresco.dataModel";
	public static String GENERATOR_OPTIONS_SHARE_EXTENSION = "alfresco.share.extension";

	public static String getTEMP_FOLDER() {
		return TEMP_FOLDER;
	}

	public static String MMUri = "http://www.kerblue.org/class/1.0";
	public List<String> classGeneratedFile = null;
	public List<String> classTemplates = null;

	public static String wClientTmpFile = "shared/classes/alfresco/extension/web-client-config-custom." + TEMP_FOLDER;

	public ClassAlfrescoGenerator() {

	}

	public List<String> getClassGeneratedFile() {
		if (classGeneratedFile == null) {
			List<String> classGeneratedFile_ = new ArrayList<String>();
			classGeneratedFile_.add("shared/classes/alfresco/extension/BxDS/model.xml");
			classGeneratedFile_.add("shared/classes/alfresco/extension/BxDS/model.properties");
			classGeneratedFile_.add("shared/classes/alfresco/extension/web-client-config-custom.xml");
			classGeneratedFile_.add("shared/classes/alfresco/messages/webclient_fr_FR.properties");

			classGeneratedFile = classGeneratedFile_;
		}
		return classGeneratedFile;
	}

	public void setClassGeneratedFile(List<String> classGeneratedFile) {
		this.classGeneratedFile = classGeneratedFile;
	}

	public List<String> getClassTemplates() {
		if (classTemplates == null) {
			List<String> result = new ArrayList<String>();
			if (getGeneratorOptionValue(GENERATOR_OPTIONS_DATAMODEL)) {				
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/Model/alfrescoGenerator_model.mt");
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/Model/alfrescoGenerator_model_properties.mt");
				
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/webClient/alfrescoGenerator_web_client_config.mt");
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/webClient/alfrescoGenerator_web_client_properties.mt");
			}			

			if (getGeneratorOptionValue(GENERATOR_OPTIONS_SHARE_EXTENSION)) {
				// only to extends alfresco share Forms
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/shareExtentions/CustomConfiguration/web-framework-config-custom.mt");
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/shareExtentions/SiteWebscripts/AlfrescoFileUploadPatch/file-upload-js-get-lib.mt");
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/shareExtentions/SiteWebscripts/AlfrescoFileUploadPatch/file-upload-response-get-patch.mt");
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/shareExtentions/SiteWebscripts/AlfrescoFileUploadPatch/flash-upload-js-get-patch.mt");
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/shareExtentions/SiteWebscripts/AlfrescoFileUploadPatch/html-upload-js-get-patch.mt");
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/shareExtentions/Webscripts/bluexml-upload-js-post.mt");
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/shareExtentions/Webscripts/bluexml-upload-post-desc.mt");
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/shareExtentions/Webscripts/bluexml-upload-response-html-post.mt");
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/shareExtentions/Webscripts/bluexml-upload-response-json-post.mt");
				result.add("/com.bluexml.side.Class.generator.alfresco/templates/shareExtentions/Webscripts/bluexml-upload-response-status-get.mt");
			}
			classTemplates = result;
		}
		return classTemplates;
	}

	public void setClassTemplates(List<String> classTemplates) {
		this.classTemplates = classTemplates;
	}

	@Override
	protected String getMetamodelURI() {
		return MMUri;
	}

	@Override
	protected List<String> getTemplates() {
		return getClassTemplates();
	}

	public boolean check() {
		// TODO Auto-generated method stub
		return true;
	}

	public Collection<String> complete() throws Exception {
		// register additional generated files :

		// manage all post process
		// manage file that can be in conflict
		List<IFile> conflict = searchForConflict();
		for (IFile f : conflict) {
			if (f.getFullPath().toString().indexOf("web-client-config-custom.xml") != -1) {
				// known conflict, apply specific process

				File ff = f.getFullPath().toFile();
				String generatedPath = getTargetPath() + "/" + wClientTmpFile;
				File gf = getIFile(generatedPath).getFullPath().toFile();
				// open the file as XML jdom
				Document alreadyGenerated = XmlHelper.buildJdomDocument(ff);
				Document generated = XmlHelper.buildJdomDocument(gf);

				for (Object o : generated.getRootElement().getChildren()) {
					Element e = (Element) o;
					alreadyGenerated.getRootElement().addContent(e.detach());
				}

				// write to file
				XmlHelper.writeXmlFile(ff, alreadyGenerated);
				System.out.println("resolved conflict on :" + f.getFullPath());
			} else {
				// unknown conflict
				System.err.println("Unknow conflict detected on :" + f.getFullPath());
			}
		}
		return null;
	}

	public static IFile getIFile(String path) {
		int index = path.lastIndexOf("/");
		String fPath = path.substring(0, index);
		String fName = path.substring(index);
		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IPath fileContainerPath = new Path(fPath);
		IFolder container = myWorkspaceRoot.getFolder(fileContainerPath);
		IFile file = container.getFile(fName);
		if (file.exists()) {
			return file;
		}
		return null;
	}

	private List<IFile> searchForConflict() {
		List<IFile> conflict = new ArrayList<IFile>();
		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

		for (String f : getClassGeneratedFile()) {
			int index = f.lastIndexOf("/");
			String fPath = f.substring(0, index);
			String fName = f.substring(index);
			IPath fileContainerPath = new Path(getTargetPath() + "/" + fPath);
			IFolder container = myWorkspaceRoot.getFolder(fileContainerPath);
			IFile file = container.getFile(fName);
			if (file.exists()) {
				conflict.add(file);
			}
		}
		return conflict;
	}

}
