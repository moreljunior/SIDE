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
package org.alfresco.repo.forms.processor.node;

import static org.alfresco.repo.forms.processor.node.FormFieldConstants.ASSOC_DATA_ADDED_SUFFIX;
import static org.alfresco.repo.forms.processor.node.FormFieldConstants.ASSOC_DATA_REMOVED_SUFFIX;
import static org.alfresco.repo.forms.processor.node.FormFieldConstants.DOT_CHARACTER;
import static org.alfresco.repo.forms.processor.node.FormFieldConstants.DOT_CHARACTER_REPLACEMENT;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.forms.FormData;
import org.alfresco.repo.forms.FormData.FieldData;
import org.alfresco.service.cmr.dictionary.AssociationDefinition;
import org.alfresco.service.cmr.dictionary.ChildAssociationDefinition;
import org.alfresco.service.cmr.dictionary.InvalidTypeException;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluexml.side.framework.alfresco.formProcessor.CustomFormData;

public class CustomNodeFormProcessor extends NodeFormProcessor {
	private static Log logger = LogFactory.getLog(CustomNodeFormProcessor.class);

	public CustomNodeFormProcessor() {
		propertyNamePattern = Pattern.compile(FormFieldConstants.PROP_DATA_PREFIX + "([^_]*){1}?_(.*){1}?");
		transientPropertyPattern = Pattern.compile(FormFieldConstants.PROP_DATA_PREFIX + "([^_]*){1}?");
		associationNamePattern = Pattern.compile(FormFieldConstants.ASSOC_DATA_PREFIX + "([^_]*){1}?_(.*){1}?(_[a-zA-Z]+)");
		logger.info("[X] Custom Node Processor loaded ...[X]");
	}

	@Override
	protected void persistNode(NodeRef nodeRef, FormData data) {

		// let superclass persist properties
		super.persistNode(nodeRef, data);

		// implements File field persistance
		int fileFieldCount = 0;
		for (FieldData fieldData : data) {
			// NOTE: ignore file fields for now, not supported yet!
			if (fieldData.isFile() == true && fieldData instanceof CustomFormData.FieldData) {
				CustomFormData.FieldData cfd = (CustomFormData.FieldData) fieldData;
				if (fileFieldCount == 0) {
					InputStream inputStream = cfd.getInputStream();
					try {
						if (inputStream.available() > 0) {

							ContentWriter writer = this.contentService.getWriter(nodeRef, ContentModel.PROP_CONTENT, true);
							String mimetype = cfd.getMimetype();

							logger.debug("write content in :" + nodeRef);
							logger.debug("mimeType :" + mimetype);
							logger.debug("encoding :" + writer.getEncoding());

							writer.setMimetype(mimetype);
							writer.putContent(inputStream);
						}
					} catch (InvalidTypeException e1) {
						logger.error(e1);
					} catch (ContentIOException e1) {
						logger.error(e1);
					} catch (InvalidNodeRefException e1) {
						logger.error(e1);
					} catch (IOException e1) {
						logger.error(e1);
					} finally {
						try {
							inputStream.close();
						} catch (IOException e) {
							logger.error("trying to close inputStream fail", e);
						}
					}

				} else {
					// TODO multi file upload not implemented yet
				}
				fileFieldCount++;
			}
		}
	}

	@Override
	protected void processAssociationPersist(NodeRef nodeRef, Map<QName, AssociationDefinition> assocDefs, Map<QName, ChildAssociationDefinition> childAssocDefs, FieldData fieldData, List<org.alfresco.repo.forms.processor.node.AbstractAssocCommand> assocCommands) {
		if (getLogger().isDebugEnabled())
			getLogger().debug("Processing field " + fieldData + " for association persistence");

		String fieldName = fieldData.getName();
		Matcher m = this.associationNamePattern.matcher(fieldName.replaceAll(DOT_CHARACTER_REPLACEMENT, DOT_CHARACTER));
		if (m.matches()) {
			String qNamePrefix = m.group(1);
			String localName = m.group(2);
			String assocSuffix = m.group(3);

			QName fullQName = QName.createQName(qNamePrefix, localName, namespaceService);

			// ensure that the association being persisted is defined in the model
			AssociationDefinition assocDef = assocDefs.get(fullQName);

			// TODO: if the association is not defined on the node, check for the association
			// in all models, however, the source of an association can be critical so we
			// can't just look up the association in the model regardless. We need to
			// either check the source class of the node and the assoc def match or we
			// check that the association was defined as part of an aspect (where by it's
			// nature can have any source type)

			// SIDE : since forms and model are generated by SIDE we make the assertion that fields are valid, so no advanced validation are done
			if (assocDef == null) {
				if (getLogger().isWarnEnabled()) {
					getLogger().debug("Field '" + fieldName + "' as an association definition can not be found in the current model");
				}
				assocDef = this.dictionaryService.getAssociation(fullQName);

				if (assocDef == null) {
					if (getLogger().isWarnEnabled()) {
						getLogger().warn("Ignoring field '" + fieldName + "' as an association definition can not be found in ANY models ");
					}
					return;
				} else {
					if (getLogger().isDebugEnabled()) {
						getLogger().debug("Field '" + fieldName + "' Found as an association definition in another model : " + assocDef.getModel().getName());
					}
				}
			}

			String value = (String) fieldData.getValue();
			String[] nodeRefs = value.split(",");

			// Each element in this array will be a new target node in association
			// with the current node.
			for (String nextTargetNode : nodeRefs) {
				if (nextTargetNode.length() > 0) {
					if (NodeRef.isNodeRef(nextTargetNode)) {
						if (assocSuffix.equals(ASSOC_DATA_ADDED_SUFFIX)) {
							if (assocDef.isChild()) {
								assocCommands.add(new AddChildAssocCommand(nodeRef, new NodeRef(nextTargetNode), fullQName));
							} else {
								assocCommands.add(new AddAssocCommand(nodeRef, new NodeRef(nextTargetNode), fullQName));
							}
						} else if (assocSuffix.equals(ASSOC_DATA_REMOVED_SUFFIX)) {
							if (assocDef.isChild()) {
								assocCommands.add(new RemoveChildAssocCommand(nodeRef, new NodeRef(nextTargetNode), fullQName));
							} else {
								assocCommands.add(new RemoveAssocCommand(nodeRef, new NodeRef(nextTargetNode), fullQName));
							}
						} else {
							if (getLogger().isWarnEnabled()) {
								StringBuilder msg = new StringBuilder();
								msg.append("Ignoring 'fieldName ").append(fieldName).append("' as it does not have one of the expected suffixes (").append(ASSOC_DATA_ADDED_SUFFIX).append(" or ").append(ASSOC_DATA_REMOVED_SUFFIX).append(")");
								getLogger().warn(msg.toString());
							}
						}
					} else {
						if (getLogger().isWarnEnabled()) {
							StringBuilder msg = new StringBuilder();
							msg.append("targetNode ").append(nextTargetNode).append(" is not a valid NodeRef and has been ignored.");
							getLogger().warn(msg.toString());
						}
					}
				}
			}
		} else if (getLogger().isWarnEnabled()) {
			getLogger().warn("Ignoring unrecognised field '" + fieldName + "'");
		}
	}
}
