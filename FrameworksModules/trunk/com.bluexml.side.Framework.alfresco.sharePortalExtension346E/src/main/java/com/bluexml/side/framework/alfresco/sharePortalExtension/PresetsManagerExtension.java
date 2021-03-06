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
/**
 * Copyright (C) 2005-2009 Alfresco Software Limited.
 *
 * This file is part of the Spring Surf Extension project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bluexml.side.framework.alfresco.sharePortalExtension;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.extensions.surf.ModelObjectService;
import org.springframework.extensions.surf.PresetsManager;
import org.springframework.extensions.surf.exception.PlatformRuntimeException;
import org.springframework.extensions.surf.types.Component;
import org.springframework.extensions.surf.types.Page;
import org.springframework.extensions.surf.types.TemplateInstance;
import org.springframework.extensions.surf.util.XMLUtil;
import org.springframework.extensions.webscripts.SearchPath;
import org.springframework.extensions.webscripts.Store;

/**
 * Spring util bean responsible for preset model object generation.
 * <p>
 * Presets are defined as XML snippets representing the model objects for a
 * given set. Each file can contain many presets each referenced by a unique ID.
 * The preset definitions can be located in any Store and any number of stores
 * can be searched.
 * <p>
 * A set of parameterised model objects such as page, template instances and
 * component bindings can be defined for a preset. The XML for each model object
 * definition is effectively identical to that used to define the model object
 * within its own file - but nested within the preset structure as follows:
 * 
 * <pre>
 * <?xml version='1.0' encoding='UTF-8'?>
 * <presets>
 *     <preset id="someid">
 *         <components>
 *             ...
 *         </components>
 *         <pages>
 *             ...
 *         </pages>
 *         <template-instances>
 *             ...
 *         </template-instances>
 *     </preset>
 *     <preset id="anotherid">
 *         ...
 *     </preset>
 * </presets>
 * </pre>
 * 
 * One important difference to standard model object XML is that the ID for an
 * object is specified as an attribute on the parent element, for instance:
 * 
 * <pre>
 * <page id="user/${userid}/dashboard">
 * </pre>
 * 
 * See the file slingshot\config\alfresco\site-data\presets\presets.xml for
 * example usage.
 * <p>
 * Each preset supports parameterisation via "token" name/value pair
 * replacements. For example:
 * 
 * <pre>
 *     <preset id="site-dashboard">
 *         <components>
 *             <component>
 *                 <scope>${scope}</scope>
 *                 <region-id>title</region-id>
 *                 <source-id>site/${siteid}/dashboard</source-id>
 *                 <url>/components/title/collaboration-title</url>
 *             </component>
 *             ...
 * </pre>
 * 
 * where the values of "${scope}" and "${siteid}" would be replaced if supplied
 * in the token map during preset construction. See the method constructPreset()
 * below.
 * 
 * @author Kevin Roast
 * @author davidabad
 */
public class PresetsManagerExtension extends PresetsManager {
	Log logger = LogFactory.getLog(PresetsManagerExtension.class);
	private SearchPath searchPath;
	private List<String> files;

	private Document[] documents;

	private ModelObjectService modelObjectService;

	/**
	 * @param modelObjectService
	 *            the model object service
	 */
	public void setModelObjectService(ModelObjectService modelObjectService) {
		this.modelObjectService = modelObjectService;
	}

	/**
	 * @param searchPath
	 *            the SearchPath to set
	 */
	public void setSearchPath(SearchPath searchPath) {
		this.searchPath = searchPath;
	}

	/**
	 * @param files
	 *            the preset files list to set
	 */
	public void setFiles(List<String> files) {
		this.files = files;
	}

	/**
	 * Initialise the presets manager
	 */
	private void init() {
		logger.debug("=== SIDE PresetManager loaded ===");
		if (this.searchPath == null || this.files == null) {
			throw new IllegalArgumentException("SearchPath and Files list are mandatory.");
		}

		// search for our preset XML descriptor documents
		List<Document> docs = new ArrayList<Document>(4);
		for (Store store : this.searchPath.getStores()) {
			logger.debug("store :" + store);
			for (String file : this.files) {
				try {
					if (store.hasDocument(file)) {
						docs.add(XMLUtil.parse(store.getDocument(file)));
					} else {
						logger.debug("file not found :" + file);

						if (file.indexOf("*") != -1) {
							logger.debug("use white card");
							// convert pattern to regexp
							file = file.replaceAll("\\.", "\\\\.");
							file = file.replaceAll("\\*", "\\.\\*");

							for (String filePath : store.getAllDocumentPaths()) {
								logger.debug("file in store :" + filePath);
								if (filePath.matches(file)) {
									logger.debug("matches found :" + filePath);
									// ok presets file found
									docs.add(XMLUtil.parse(store.getDocument(filePath)));
								}
							}

						}

					}
				} catch (IOException ioe) {
					throw new PlatformRuntimeException("Error loading presets XML file: " + file + " in store: " + store.toString(), ioe);
				} catch (DocumentException de) {
					de.printStackTrace();
					throw new PlatformRuntimeException("Error processing presets XML file: " + file + " in store: " + store.toString(), de);
				}
			}
		}
		this.documents = docs.toArray(new Document[docs.size()]);
	}

	
	/**
	 * Construct the model objects for a given preset.
	 * Objects persist to the default store for the appropriate object type.
	 * 
	 * @param id
	 *            Preset ID to use
	 * @param tokens
	 *            Name value pair tokens to replace in preset definition
	 */
	@SuppressWarnings("unchecked")
	public void constructPreset(String id, Map<String, String> tokens) {
		if (id == null) {
			throw new IllegalArgumentException("Preset ID is mandatory.");
		}

		// perform one time init - this cannot be perform in an app handler or by the
		// framework init - as it requires the Alfresco server to be started...
		synchronized (this) {
			if (this.documents == null) {
				init();
			}
		}

		for (Document doc : this.documents) {
			for (Element preset : (List<Element>) doc.getRootElement().elements("preset")) {
				// found preset with matching id?
				if (id.equals(preset.attributeValue("id"))) {
					// any components in the preset?
					Element components = preset.element("components");
					if (components != null) {
						for (Element c : (List<Element>) components.elements("component")) {
							// apply token replacement to each value as it is retrieved
							String title = replace(c.elementTextTrim(Component.PROP_TITLE), tokens);
							String titleId = replace(c.elementTextTrim(Component.PROP_TITLE_ID), tokens);
							String description = replace(c.elementTextTrim(Component.PROP_DESCRIPTION), tokens);
							String descriptionId = replace(c.elementTextTrim(Component.PROP_DESCRIPTION_ID), tokens);
							String typeId = replace(c.elementTextTrim(Component.PROP_COMPONENT_TYPE_ID), tokens);
							String scope = replace(c.elementTextTrim(Component.PROP_SCOPE), tokens);
							String regionId = replace(c.elementTextTrim(Component.PROP_REGION_ID), tokens);
							String sourceId = replace(c.elementTextTrim(Component.PROP_SOURCE_ID), tokens);
							String url = replace(c.elementTextTrim(Component.PROP_URL), tokens);
							String chrome = replace(c.elementTextTrim(Component.PROP_CHROME), tokens);

							// validate mandatory values
							if (scope == null || scope.length() == 0) {
								throw new IllegalArgumentException("Scope is a mandatory property for a component preset.");
							}
							if (regionId == null || regionId.length() == 0) {
								throw new IllegalArgumentException("RegionID is a mandatory property for a component preset.");
							}
							if (sourceId == null || sourceId.length() == 0) {
								throw new IllegalArgumentException("SourceID is a mandatory property for a component preset.");
							}

							// generate component
							Component component = modelObjectService.newComponent(scope, regionId, sourceId);
							component.setComponentTypeId(typeId);
							component.setTitle(title);
							component.setTitleId(titleId);
							component.setDescription(description);
							component.setDescriptionId(descriptionId);
							component.setURL(url);
							component.setChrome(chrome);

							// apply arbituary custom properties
							if (c.element("properties") != null) {
								for (Element prop : (List<Element>) c.element("properties").elements()) {
									String propName = replace(prop.getName(), tokens);
									String propValue = replace(prop.getTextTrim(), tokens);
									component.setCustomProperty(propName, propValue);
								}
							}

							// persist the object
							modelObjectService.saveObject(component);
						}
					}

					// any pages in the preset?
					Element pages = preset.element("pages");
					if (pages != null) {
						for (Element p : (List<Element>) pages.elements("page")) {
							// apply token replacement to each value as it is retrieved
							String pageId = replace(p.attributeValue(Page.PROP_ID), tokens);
							String title = replace(p.elementTextTrim(Page.PROP_TITLE), tokens);
							String titleId = replace(p.elementTextTrim(Page.PROP_TITLE_ID), tokens);
							String description = replace(p.elementTextTrim(Page.PROP_DESCRIPTION), tokens);
							String descriptionId = replace(p.elementTextTrim(Page.PROP_DESCRIPTION_ID), tokens);
							String typeId = replace(p.elementTextTrim(Page.PROP_PAGE_TYPE_ID), tokens);
							String auth = replace(p.elementTextTrim(Page.PROP_AUTHENTICATION), tokens);
							String template = replace(p.elementTextTrim(Page.PROP_TEMPLATE_INSTANCE), tokens);

							// validate mandatory values
							if (pageId == null || pageId.length() == 0) {
								throw new IllegalArgumentException("ID is a mandatory attribute for a page preset.");
							}
							if (template == null || template.length() == 0) {
								throw new IllegalArgumentException("Template is a mandatory property for a page preset.");
							}

							// generate page
							Page page = modelObjectService.newPage(pageId);
							page.setPageTypeId(typeId);
							page.setTitle(title);
							page.setTitleId(titleId);
							page.setDescription(description);
							page.setDescriptionId(descriptionId);
							page.setAuthentication(auth);
							page.setTemplateId(template);

							// apply arbituary custom properties
							if (p.element("properties") != null) {
								for (Element prop : (List<Element>) p.element("properties").elements()) {
									String propName = replace(prop.getName(), tokens);
									String propValue = replace(prop.getTextTrim(), tokens);
									page.setCustomProperty(propName, propValue);
								}
							}

							// persist the object
							modelObjectService.saveObject(page);
						}
					}

					// any template instances in the preset?
					Element templates = preset.element("template-instances");
					if (templates != null) {
						for (Element t : (List<Element>) templates.elements("template-instance")) {
							// apply token replacement to each value as it is retrieved
							String templateId = replace(t.attributeValue(TemplateInstance.PROP_ID), tokens);
							String title = replace(t.elementTextTrim(TemplateInstance.PROP_TITLE), tokens);
							String titleId = replace(t.elementTextTrim(TemplateInstance.PROP_TITLE_ID), tokens);
							String description = replace(t.elementTextTrim(TemplateInstance.PROP_DESCRIPTION), tokens);
							String descriptionId = replace(t.elementTextTrim(TemplateInstance.PROP_DESCRIPTION_ID), tokens);
							String templateType = replace(t.elementTextTrim(TemplateInstance.PROP_TEMPLATE_TYPE), tokens);

							// validate mandatory values
							if (templateId == null || templateId.length() == 0) {
								throw new IllegalArgumentException("ID is a mandatory attribute for a template-instance preset.");
							}
							if (templateType == null || templateType.length() == 0) {
								throw new IllegalArgumentException("Template is a mandatory property for a page preset.");
							}

							// generate template-instance
							TemplateInstance template = modelObjectService.newTemplate(templateId);
							template.setTitle(title);
							template.setTitleId(titleId);
							template.setDescription(description);
							template.setDescriptionId(descriptionId);
							template.setTemplateTypeId(templateType);

							// apply arbituary custom properties
							if (t.element("properties") != null) {
								for (Element prop : (List<Element>) t.element("properties").elements()) {
									String propName = replace(prop.getName(), tokens);
									String propValue = replace(prop.getTextTrim(), tokens);
									template.setCustomProperty(propName, propValue);
								}
							}

							// persist the object
							modelObjectService.saveObject(template);
						}
					}

					// TODO: any chrome, associations, types, themes etc. in the preset...

					// found our preset - no need to process further
					break;
				}
			}
		}
	}

	/**
	 * Replace token strings - marked by ${...} in the given string with
	 * the supplied tokens.
	 * 
	 * @param s
	 *            String to process (can be null - will return null)
	 * @param tokens
	 *            Token map (can be null - will return original string)
	 * @return replaced string or null if input is null or original string if
	 *         tokens is null
	 */
	private static String replace(String s, Map<String, String> tokens) {
		if (s != null && tokens != null) {
			for (Entry<String, String> entry : tokens.entrySet()) {
				String key = "${" + entry.getKey() + "}";
				String value = entry.getValue();
				s = s.replace(key, value);
			}
		}

		return s;
	}

	public class FilenameFilter_ implements FilenameFilter {
		String regexp;

		FilenameFilter_(String regexp) {
			this.regexp = regexp;
		}

		public boolean accept(File dir, String name) {
			boolean check = name.matches(regexp);
			return check;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getPresetsIds() {
		List<String> ids = new ArrayList<String>();
		synchronized (this) {
			if (this.documents == null) {
				init();
			}
		}
		for (Document doc : this.documents) {
			for (Element preset : (List<Element>) doc.getRootElement().elements("preset")) {
				// found preset with matching id?
				ids.add(preset.attributeValue("id"));
			}
		}
		return ids;
	}
}
