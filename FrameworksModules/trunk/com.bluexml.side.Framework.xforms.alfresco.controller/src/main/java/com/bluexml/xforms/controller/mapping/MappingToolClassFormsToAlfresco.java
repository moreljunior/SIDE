package com.bluexml.xforms.controller.mapping;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bluexml.xforms.controller.binding.AspectType;
import com.bluexml.xforms.controller.binding.AssociationActions;
import com.bluexml.xforms.controller.binding.AssociationType;
import com.bluexml.xforms.controller.binding.AttributeType;
import com.bluexml.xforms.controller.binding.ClassType;
import com.bluexml.xforms.controller.binding.GenericAssociation;
import com.bluexml.xforms.controller.binding.GenericAssociations;
import com.bluexml.xforms.controller.binding.GenericAttribute;
import com.bluexml.xforms.controller.binding.GenericAttributes;
import com.bluexml.xforms.controller.binding.GenericClass;
import com.bluexml.xforms.controller.binding.GenericClassReference;
import com.bluexml.xforms.controller.binding.Mapping;
import com.bluexml.xforms.controller.binding.ValueType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.bluexml.xforms.controller.alfresco.AlfrescoController;
import com.bluexml.xforms.controller.alfresco.AlfrescoControllerException;
import com.bluexml.xforms.controller.alfresco.AlfrescoTransaction;
import com.bluexml.xforms.messages.MsgId;
import com.bluexml.side.form.utils.DOMUtil;

/**
 * The Class MappingToolClassFormsToAlfresco.
 */
public class MappingToolClassFormsToAlfresco extends MappingToolCommon {

	protected static Log logger = LogFactory.getLog(MappingToolClassFormsToAlfresco.class);

	/**
	 * Instantiates a new mapping tool class forms to alfresco.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param controller
	 *            the controller
	 */
	public MappingToolClassFormsToAlfresco(Mapping mapping, AlfrescoController controller) {
		super(mapping, controller);
	}

	/**
	 * Fill alfresco class.
	 * 
	 * @param login
	 *            the login
	 * @param alfrescoClass
	 *            the alfresco class
	 * @param xformsNode
	 *            the xforms node
	 * @throws ServletException
	 */
	private void fillAlfrescoClass(AlfrescoTransaction transaction, GenericClass alfrescoClass,
			Node xformsNode) throws ServletException {
		Element element = null;
		if (xformsNode instanceof Document) {
			element = ((Document) xformsNode).getDocumentElement();
		} else {
			element = (Element) xformsNode;
		}
		List<Element> children = DOMUtil.getAllChildren(element);

		ClassType classType = null;
		Element sideDataType = DOMUtil.getOneElementByTagName(children,
				MsgId.INT_INSTANCE_SIDE_DATATYPE.getText());
		if (sideDataType != null) {
			classType = getClassType(sideDataType.getTextContent());
		} else {
			classType = getClassType(element.getTagName());
		}

		alfrescoClass.setQualifiedName(classType.getAlfrescoName());
		List<ClassType> classTypes = getParentClassTypes(classType);

		GenericAttributes attributes = alfrescoObjectFactory.createGenericAttributes();
		GenericAssociations associations = alfrescoObjectFactory.createGenericAssociations();
		associations.setAction("replace");
		for (ClassType subClassType : classTypes) {
			xformsAttributesToAlfresco(attributes, children, subClassType);
			xformsAssociationsToAlfresco(transaction, associations, children, subClassType);
		}
		alfrescoClass.setAttributes(attributes);
		alfrescoClass.setAssociations(associations);

		String elementId = xformsIdToAlfresco(children);
		if (elementId != null) {
			alfrescoClass.setId(elementId);
		}
	}

	/**
	 * Gets the content file name.
	 * 
	 * @param transaction
	 *            the login
	 * @param alfClass
	 *            the alf class
	 * 
	 * @return the content file name
	 * 
	 * @throws AlfrescoControllerException
	 *             the alfresco controller exception
	 */
	public String getFileContentFileName(AlfrescoTransaction transaction, GenericClass alfClass)
			throws AlfrescoControllerException {
		GenericAttribute contentAttribute = getFileContentAttribute(alfClass);
		if (contentAttribute != null) {
			return contentAttribute.getValue().get(0).getValue();
		}
		return null;
	}

	/**
	 * Gets the repository content file name.
	 * 
	 * @param transaction
	 *            the login
	 * @param alfClass
	 *            the alf class
	 * 
	 * @return null if no repository content file name was detected
	 * 
	 * @throws AlfrescoControllerException
	 *             the alfresco controller exception
	 */
	public RepoContentInfoBean getRepoContentInfo(AlfrescoTransaction transaction,
			GenericClass alfClass) throws AlfrescoControllerException {
		GenericAttribute contentAttribute = getRepoContentAttribute(alfClass);
		if (contentAttribute != null) {
			String path = contentAttribute.getValue().get(0).getValue();
			String name = contentAttribute.getValue().get(1).getValue();
			String type = contentAttribute.getValue().get(2).getValue();
			return new RepoContentInfoBean(path, name, type);
		}
		return null;
	}

	/**
	 * Gets the content attribute.
	 * 
	 * @param alfClass
	 *            the alf class
	 * 
	 * @return the content attribute
	 */
	private GenericAttribute getFileContentAttribute(GenericClass alfClass) {
		GenericAttribute contentAttribute = null;
		List<GenericAttribute> attributes = alfClass.getAttributes().getAttribute();
		for (GenericAttribute attribute : attributes) {
			if (attribute.getQualifiedName().endsWith("_content")) {
				contentAttribute = attribute;
			}
		}
		return contentAttribute;
	}

	/**
	 * Gets the (repository) content attribute.
	 * 
	 * @param alfClass
	 *            the alf class
	 * 
	 * @return the repository content attribute
	 */
	private GenericAttribute getRepoContentAttribute(GenericClass alfClass) {

		List<GenericAttribute> attributes = alfClass.getAttributes().getAttribute();
		for (GenericAttribute attribute : attributes) {
			if (attribute.getQualifiedName().endsWith("_repositoryContent")) {
				return attribute;
			}
		}
		return null;
	}

	/**
	 * Process association.
	 * 
	 * @param login
	 *            the login
	 * @param associations
	 *            the associations
	 * @param associationType
	 *            the association type
	 * @param associationElement
	 *            the association element
	 * @throws ServletException
	 */
	private void processAssociation(AlfrescoTransaction transaction,
			GenericAssociations associations, AssociationType associationType,
			Element associationElement) throws ServletException {
		String targetId = null;

		Element targetNode = null;
		targetNode = associationElement;

		if (targetNode != null) {
			if (associationType.isInline()) {
				targetId = processSave(transaction, targetNode);
			} else {
				Element specificElement = DOMUtil.getChild(targetNode, MsgId.INT_INSTANCE_SIDEID
						.getText());
				if (specificElement != null) {
					targetId = StringUtils.trimToNull(specificElement.getTextContent());
					targetId = AlfrescoController.patchDataId(targetId);
				}
			}
		}

		if (targetId != null) {
			GenericAssociation association = alfrescoObjectFactory.createGenericAssociation();
			association.setQualifiedName(associationType.getAlfrescoName());
			association.setAction(AssociationActions.ADD);

			GenericClassReference target = alfrescoObjectFactory.createGenericClassReference();
			target.setQualifiedName(associationType.getType().getAlfrescoName());
			target.setValue(targetId);
			association.setTarget(target);

			associations.getAssociation().add(association);
		}

	}

	/**
	 * Sets the content file name.
	 * 
	 * @param alfClass
	 *            the alf class
	 * @param fileName
	 *            the file name
	 */
	public void setFileContentFileName(GenericClass alfClass, String fileName) {
		GenericAttribute contentAttribute = getFileContentAttribute(alfClass);
		if (contentAttribute != null) {
			contentAttribute.getValue().clear();
			ValueType value = alfrescoObjectFactory.createValueType();
			value.setValue(fileName);
			contentAttribute.getValue().add(value);
		}
	}

	/**
	 * Sets the repository content file name as a reference in the format
	 * "workspace://SpacesStore/...".
	 * 
	 * @param alfClass
	 *            the alf class
	 * @param fileName
	 *            the file name
	 */
	public void setRepoContentFileName(GenericClass alfClass, String fileRef) {
		GenericAttribute contentAttribute = getRepoContentAttribute(alfClass);
		if (contentAttribute != null) {
			contentAttribute.getValue().clear();

			ValueType valueName = alfrescoObjectFactory.createValueType();
			valueName.setValue(fileRef);
			contentAttribute.getValue().add(valueName);
		}
	}

	/**
	 * Transform class forms to alfresco.
	 * 
	 * @param transaction
	 *            the login
	 * @param node
	 *            the node
	 * 
	 * @return the com.bluexml.xforms.controller.alfresco.binding. class
	 * 
	 * @throws AlfrescoControllerException
	 *             the alfresco controller exception
	 * @throws ServletException
	 */
	public GenericClass transformClassFormsToAlfresco(AlfrescoTransaction transaction, Node node)
			throws AlfrescoControllerException, ServletException {
		logXML(node, "transformXFormsToAlfresco", "input");

		GenericClass alfrescoClass = alfrescoObjectFactory.createGenericClass();
		fillAlfrescoClass(transaction, alfrescoClass, node);

		// String datas = marshal(alfrescoClass);
		// logXML(null, "transformXFormsToAlfresco", "output", datas);
		// AlfrescoData alfrescoData = new AlfrescoData(datas, alfrescoClass
		// .getId());
		return alfrescoClass;
	}

	/**
	 * Xforms associations to alfresco.
	 * 
	 * @param login
	 *            the login
	 * @param associations
	 *            the associations
	 * @param children
	 *            the children
	 * @param classType
	 *            the class type
	 * @throws ServletException
	 */
	private void xformsAssociationsToAlfresco(AlfrescoTransaction transaction,
			GenericAssociations associations, List<Element> children, ClassType classType)
			throws ServletException {
		List<AssociationType> xformsAssociations = classType.getAssociation();
		xformsAssociationsToAlfresco(transaction, children, associations, xformsAssociations);
	}

	/**
	 * Xforms associations to alfresco.
	 * 
	 * @param login
	 *            the login
	 * @param children
	 *            the children
	 * @param associations
	 *            the associations
	 * @param xformsAssociations
	 *            the xforms associations
	 * @throws ServletException
	 */
	private void xformsAssociationsToAlfresco(AlfrescoTransaction transaction,
			List<Element> children, GenericAssociations associations,
			List<AssociationType> xformsAssociations) throws ServletException {
		for (AssociationType associationType : xformsAssociations) {
			xformsAssociationToAlfresco(transaction, children, associations, associationType);
		}
	}

	/**
	 * Xforms association to alfresco.
	 * 
	 * @param login
	 *            the login
	 * @param children
	 *            the children
	 * @param associations
	 *            the associations
	 * @param associationType
	 *            the association type
	 * @throws ServletException
	 */
	private void xformsAssociationToAlfresco(AlfrescoTransaction transaction,
			List<Element> children, GenericAssociations associations,
			AssociationType associationType) throws ServletException {
		Element associationElement = DOMUtil.getOneElementByTagName(children, associationType
				.getName());
		if (associationElement != null) {
			List<Element> associationElements = DOMUtil.getAllChildren(associationElement);
			for (int i = 0; i < associationElements.size(); i++) {
				if (!associationType.isMultiple() || i != (associationElements.size() - 1)) {
					processAssociation(transaction, associations, associationType,
							associationElements.get(i));
				}
			}
		}
	}

	/**
	 * Xforms attributes to alfresco.
	 * 
	 * @param attributes
	 *            the attributes
	 * @param children
	 *            the children
	 * @param classType
	 *            the class type
	 */
	private void xformsAttributesToAlfresco(GenericAttributes attributes, List<Element> children,
			ClassType classType) {
		List<AttributeType> xformsAttributes = classType.getAttribute();
		xformsAttributesToAlfresco(children, attributes, xformsAttributes);

		List<AspectType> aspects = classType.getAspect();
		for (AspectType aspectType : aspects) {
			Element aspect = DOMUtil.getOneElementByTagName(children, aspectType.getName());
			if (aspect != null) {
				List<AttributeType> aspectAttributes = getAspectType(aspectType).getAttribute();
				List<Element> aspectChildren = DOMUtil.getAllChildren(aspect);
				xformsAttributesToAlfresco(aspectChildren, attributes, aspectAttributes);
			}
		}
	}

	/**
	 * Xforms attributes to alfresco.
	 * 
	 * @param children
	 *            the children
	 * @param attributes
	 *            the attributes
	 * @param xformsAttributes
	 *            the xforms attributes
	 */
	private void xformsAttributesToAlfresco(List<Element> children, GenericAttributes attributes,
			List<AttributeType> xformsAttributes) {
		for (AttributeType xformsAttribute : xformsAttributes) {
			if (xformsAttribute.isInAlfresco()) {
				Element child = DOMUtil.getOneElementByTagName(children, xformsAttribute.getName());
				if (child != null) {
					attributes.getAttribute()
							.add(xformsAttributeToAlfresco(child, xformsAttribute));
				}
			}
		}
	}

	/**
	 * Xforms attribute to alfresco.
	 * 
	 * @param child
	 *            the child
	 * @param attributeType
	 *            the xforms attribute
	 * 
	 * @return the attribute
	 */
	private GenericAttribute xformsAttributeToAlfresco(Element child, AttributeType attributeType) {
		GenericAttribute result = alfrescoObjectFactory.createGenericAttribute();
		result.setQualifiedName(attributeType.getAlfrescoName());
		String enumName = attributeType.isDynamicEnum() ? null : attributeType.getEnumQName();

		String inputTextContent = child.getTextContent();
		String type = attributeType.getType();
		if (attributeType.isMultiple()) {
			convertXformsAttributeToAlfresco(result, inputTextContent, type, enumName);
		} else {
			String value = null;
			if (isAmendable(type, attributeType.isReadOnly())) {
				inputTextContent = getReadOnlyDateOrTimeModifiedValue(type, inputTextContent);
			}
			if (type.equals(MsgId.INT_TYPE_XSD_DATETIME.getText())) {
				String date;
				String time;
				if (attributeType.isReadOnly()) {
					date = extractDateFromDateTimeModified(inputTextContent);
					time = extractTimeFromDateTimeModified(inputTextContent);
				} else {
					date = DOMUtil.getChild(child, "date").getTextContent();
					time = DOMUtil.getChild(child, "time").getTextContent();
				}
				value = getDateTimeFromDateAndTime(date, time);
			} else {
				value = convertXformsAttributeToAlfresco(inputTextContent, type, enumName);
			}
			if (controller.isFileField(attributeType)) {
				logger.debug("Attribute " + attributeType.getAlfrescoName() + " is a FileField");
			}
			result.getValue().clear();
			ValueType valueType = alfrescoObjectFactory.createValueType();
			valueType.setValue(value);
			result.getValue().add(valueType);
			if (controller.isRepositoryContent(attributeType)) {
				// we need a name for the node when uploaded in the repository
				ValueType valueTypeNameAndExt = alfrescoObjectFactory.createValueType();
				String nameAndExt = child.getAttribute("file");
				valueTypeNameAndExt.setValue(nameAndExt);
				result.getValue().add(valueTypeNameAndExt);
				// we also need the MIME type
				ValueType valueTypeMIME = alfrescoObjectFactory.createValueType();
				String mimetype = child.getAttribute("type");
				valueTypeMIME.setValue(mimetype);
				result.getValue().add(valueTypeMIME);
			}
		}

		return result;
	}

	/**
	 * Removes the reference.
	 * 
	 * @param node
	 *            the node
	 * @param elementId
	 *            the element id
	 */
	public void removeReference(Node node, String elementId) {
		String relementId = AlfrescoController.patchDataId(elementId);

		Element element = null;
		if (node instanceof Document) {
			element = ((Document) node).getDocumentElement();
		} else {
			if (node instanceof Element) {
				element = (Element) node;
			}
		}

		List<Element> children = DOMUtil.getAllChildren(element);
		ClassType classType = null;
		Element dataType = DOMUtil.getOneElementByTagName(children,
				MsgId.INT_INSTANCE_SIDE_DATATYPE.getText());
		if (dataType != null) {
			classType = getClassType(dataType.getTextContent());
		} else {
			classType = getClassType(element.getTagName());
		}
		List<ClassType> classTypes = getParentClassTypes(classType);

		List<Element> elementsToRemove = new ArrayList<Element>();

		for (ClassType subClassType : classTypes) {
			List<AssociationType> xformsAssociations = subClassType.getAssociation();
			for (AssociationType associationType : xformsAssociations) {
				Element associationElement = DOMUtil.getOneElementByTagName(children,
						associationType.getName());
				if (associationElement != null) {
					List<Element> associationElements = DOMUtil.getAllChildren(associationElement);
					for (Element association : associationElements) {
						processRemoveReference(relementId, elementsToRemove, associationType,
								association, associationType.isMultiple());
					}
				}
			}
		}
		for (Element elementToRemove : elementsToRemove) {
			element.removeChild(elementToRemove);
		}
	}

	/**
	 * Process remove reference.
	 * 
	 * @param relementId
	 *            the relement id
	 * @param elementsToRemove
	 *            the elements to remove
	 * @param associationType
	 *            the association type
	 * @param associationElement
	 *            the association element
	 * @param multiple
	 *            the multiple
	 */
	private void processRemoveReference(String relementId, List<Element> elementsToRemove,
			AssociationType associationType, Element associationElement, boolean multiple) {

		String targetId = null;

		Element targetNode = null;
		targetNode = associationElement;

		if (targetNode != null) {
			targetId = getId(targetNode);
			if (StringUtils.equals(targetId, relementId)) {
				if (multiple) {
					elementsToRemove.add(associationElement);
				} else {
					Element eltTargetId = DOMUtil.getOneElementByTagName(DOMUtil
							.getAllChildren(targetNode), MsgId.INT_INSTANCE_SIDEID.getText());
					Element eltTargetLabel = DOMUtil.getOneElementByTagName(DOMUtil
							.getAllChildren(targetNode), MsgId.INT_INSTANCE_SIDELABEL.getText());
					eltTargetId.setTextContent("");
					eltTargetLabel.setTextContent("");
				}
			} else if (associationType.isInline()) {
				removeReference(targetNode, relementId);
			}
		}
	}

	/**
	 * Checks for sub types.
	 * 
	 * @param dataType
	 *            the data type
	 * 
	 * @return true, if successful
	 */
	public boolean hasSubTypes(String dataType) {
		return (getClassType(dataType).getSubClass().size() > 0);
	}
}
