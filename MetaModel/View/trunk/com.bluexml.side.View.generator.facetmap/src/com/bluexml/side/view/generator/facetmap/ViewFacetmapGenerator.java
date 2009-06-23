/*******************************************************************************
 * 	Copyright (C) BlueXML 2005-2009
 *
 * This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Boston, MA 02111.
 ******************************************************************************/
package com.bluexml.side.view.generator.facetmap;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.bluexml.side.util.generator.acceleo.AbstractAcceleoGenerator;
import com.bluexml.side.util.libs.FileHelper;
import com.bluexml.side.util.libs.IFileHelper;
import com.bluexml.side.util.libs.zip.ZipManager;
import com.bluexml.side.util.security.SecurityHelper;
import com.bluexml.side.util.settings.SidePreferences;
import com.bluexml.side.view.generator.facetmap.utils.FacetmapConstants;

/**
 * @author <a href="mailto:pbertrand@bluexml.com"> Pierre BERTRAND </a>
 *
 */
public class ViewFacetmapGenerator extends AbstractAcceleoGenerator implements FacetmapConstants {
	public static String GENERATOR_CODE = "CODE_GED_G_C_FACETMAP_2";
	public static String MMUri = "http://www.kerblue.org/view/1.0";

	public ViewFacetmapGenerator(){
		techVersion = "Facetmap 2.x";
		this.setTEMP_FOLDER(getTechVersion());
	}
	
	/* (non-Javadoc)
	 * @see com.bluexml.side.application.generator.acceleo.AbstractAcceleoGenerator#getMetamodelURI()
	 */
	@Override
	protected String getMetamodelURI() {
		return MMUri;
	}

	/* (non-Javadoc)
	 * @see com.bluexml.side.application.generator.acceleo.AbstractAcceleoGenerator#getTemplates()
	 */
	@Override
	protected List<String> getTemplates() {
			List<String> result = new ArrayList<String>();
				//common
				result.add("/com.bluexml.side.View.generator.facetmap/templates/facetmap-cmis2xfml-generation.mt");
				result.add("/com.bluexml.side.View.generator.facetmap/templates/facetmap-cmis-generation.mt");
				result.add("/com.bluexml.side.View.generator.facetmap/templates/facetmap-cmis-properties-generation.mt");
				result.add("/com.bluexml.side.View.generator.facetmap/templates/facetmap-propertyfile-generation.mt");
				//facets
				result.add("/com.bluexml.side.View.generator.facetmap/templates/facetmap-facets-buildproperties-generation.mt");
				result.add("/com.bluexml.side.View.generator.facetmap/templates/facetmap-facets-xslbasicfacets-generation.mt");
				result.add("/com.bluexml.side.View.generator.facetmap/templates/facetmap-facets-xslrightnav-generation.mt");
			return result;
	}

	/* (non-Javadoc)
	 * @see com.bluexml.side.application.security.Checkable#check()
	 */
	public boolean check() {
		return SecurityHelper.check(GENERATOR_CODE,SidePreferences.getKey());
	}
	
	public String getGeneratorParameter(EObject ob,String paramName) throws Exception {
		return configurationParameters.get(paramName);
	}

	public Collection<IFile> complete() throws Exception {
		// Don't support 
		if (groupedModels.entrySet().size()>1)
			throw new Exception("Error too many root packages for facetmap.");
		setTEMP_FOLDER("generator_" + getClass().getName());
		buildPackage(groupedModels.keySet().toArray()[0].toString());
		return generatedFiles;
	}

	@Override
	public IFile buildPackage(String modelId) throws Exception {
		String folder = IFileHelper.getSystemFolderPath(getTemporaryFolder()+FILESEP+modelId)+FILESEP;
		//Destinations
		String destFacets = folder + "zip" + FILESEP + WEBAPP_FACETS;
		String destContent = folder + "zip" + FILESEP + WEBAPP_CONTENT;
		//Copy
		FileHelper.copyFiles(new File(folder + "common"), new File(destFacets), true);
		FileHelper.copyFiles(new File(folder + "common"), new File(destContent), true);
		FileHelper.copyFiles(new File(folder + "facets"), new File(destFacets), true);
		//Zip
		String zipFolder = IFileHelper.getSystemFolderPath(getTargetPath()+FILESEP+getTechVersion())+FILESEP;
		new File(zipFolder).mkdirs();
		File zipFacets = new File(zipFolder + WEBAPP_FACETS + ".zip");
		File zipContent = new File(zipFolder + WEBAPP_CONTENT + ".zip");
		ZipManager.zip(new File(destFacets), zipFacets, false);
		ZipManager.zip(new File(destContent), zipContent, false);
		return null;
	}

}
