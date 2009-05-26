/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bluexml.side.clazz;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.bluexml.side.common.CommonPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.bluexml.side.clazz.ClazzFactory
 * @model kind="package"
 * @generated
 */
public interface ClazzPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "clazz";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.kerblue.org/class/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "clazz";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ClazzPackage eINSTANCE = com.bluexml.side.clazz.impl.ClazzPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.ClassModelElementImpl <em>Class Model Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.ClassModelElementImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getClassModelElement()
	 * @generated
	 */
	int CLASS_MODEL_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MODEL_ELEMENT__STEREOTYPES = CommonPackage.MODEL_ELEMENT__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MODEL_ELEMENT__TAGS = CommonPackage.MODEL_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MODEL_ELEMENT__COMMENTS = CommonPackage.MODEL_ELEMENT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MODEL_ELEMENT__METAINFO = CommonPackage.MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MODEL_ELEMENT__DOCUMENTATION = CommonPackage.MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MODEL_ELEMENT__HAS_COMMENTS = CommonPackage.MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Class Model Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MODEL_ELEMENT_FEATURE_COUNT = CommonPackage.MODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.NamedClassModelElementImpl <em>Named Class Model Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.NamedClassModelElementImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getNamedClassModelElement()
	 * @generated
	 */
	int NAMED_CLASS_MODEL_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_CLASS_MODEL_ELEMENT__STEREOTYPES = CLASS_MODEL_ELEMENT__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_CLASS_MODEL_ELEMENT__TAGS = CLASS_MODEL_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_CLASS_MODEL_ELEMENT__COMMENTS = CLASS_MODEL_ELEMENT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_CLASS_MODEL_ELEMENT__METAINFO = CLASS_MODEL_ELEMENT__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_CLASS_MODEL_ELEMENT__DOCUMENTATION = CLASS_MODEL_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_CLASS_MODEL_ELEMENT__HAS_COMMENTS = CLASS_MODEL_ELEMENT__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_CLASS_MODEL_ELEMENT__NAME = CLASS_MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_CLASS_MODEL_ELEMENT__DESCRIPTION = CLASS_MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Named Class Model Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT = CLASS_MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.ClassPackageImpl <em>Class Package</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.ClassPackageImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getClassPackage()
	 * @generated
	 */
	int CLASS_PACKAGE = 2;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__STEREOTYPES = CommonPackage.PACKAGE__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__TAGS = CommonPackage.PACKAGE__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__COMMENTS = CommonPackage.PACKAGE__COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__NAME = CommonPackage.PACKAGE__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__DOCUMENTATION = CommonPackage.PACKAGE__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Stereotype Set</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__STEREOTYPE_SET = CommonPackage.PACKAGE__STEREOTYPE_SET;

	/**
	 * The feature id for the '<em><b>Package Set</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__PACKAGE_SET = CommonPackage.PACKAGE__PACKAGE_SET;

	/**
	 * The feature id for the '<em><b>Class Set</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__CLASS_SET = CommonPackage.PACKAGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Association Set</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__ASSOCIATION_SET = CommonPackage.PACKAGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Aspect Set</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__ASPECT_SET = CommonPackage.PACKAGE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Enumeration Set</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__ENUMERATION_SET = CommonPackage.PACKAGE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Views</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__VIEWS = CommonPackage.PACKAGE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Class Package</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE_FEATURE_COUNT = CommonPackage.PACKAGE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.TitledNamedClassModelElementImpl <em>Titled Named Class Model Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.TitledNamedClassModelElementImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getTitledNamedClassModelElement()
	 * @generated
	 */
	int TITLED_NAMED_CLASS_MODEL_ELEMENT = 17;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLED_NAMED_CLASS_MODEL_ELEMENT__STEREOTYPES = NAMED_CLASS_MODEL_ELEMENT__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLED_NAMED_CLASS_MODEL_ELEMENT__TAGS = NAMED_CLASS_MODEL_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLED_NAMED_CLASS_MODEL_ELEMENT__COMMENTS = NAMED_CLASS_MODEL_ELEMENT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLED_NAMED_CLASS_MODEL_ELEMENT__METAINFO = NAMED_CLASS_MODEL_ELEMENT__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLED_NAMED_CLASS_MODEL_ELEMENT__DOCUMENTATION = NAMED_CLASS_MODEL_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLED_NAMED_CLASS_MODEL_ELEMENT__HAS_COMMENTS = NAMED_CLASS_MODEL_ELEMENT__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLED_NAMED_CLASS_MODEL_ELEMENT__NAME = NAMED_CLASS_MODEL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLED_NAMED_CLASS_MODEL_ELEMENT__DESCRIPTION = NAMED_CLASS_MODEL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLED_NAMED_CLASS_MODEL_ELEMENT__TITLE = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Titled Named Class Model Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.AbstractContainerImpl <em>Abstract Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.AbstractContainerImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAbstractContainer()
	 * @generated
	 */
	int ABSTRACT_CONTAINER = 12;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__STEREOTYPES = TITLED_NAMED_CLASS_MODEL_ELEMENT__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__TAGS = TITLED_NAMED_CLASS_MODEL_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__COMMENTS = TITLED_NAMED_CLASS_MODEL_ELEMENT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__METAINFO = TITLED_NAMED_CLASS_MODEL_ELEMENT__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__DOCUMENTATION = TITLED_NAMED_CLASS_MODEL_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__HAS_COMMENTS = TITLED_NAMED_CLASS_MODEL_ELEMENT__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__NAME = TITLED_NAMED_CLASS_MODEL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__DESCRIPTION = TITLED_NAMED_CLASS_MODEL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__TITLE = TITLED_NAMED_CLASS_MODEL_ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__ATTRIBUTES = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Associations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER__ASSOCIATIONS = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Abstract Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CONTAINER_FEATURE_COUNT = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.AbstractClassImpl <em>Abstract Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.AbstractClassImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAbstractClass()
	 * @generated
	 */
	int ABSTRACT_CLASS = 11;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CLASS__STEREOTYPES = ABSTRACT_CONTAINER__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CLASS__TAGS = ABSTRACT_CONTAINER__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CLASS__COMMENTS = ABSTRACT_CONTAINER__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CLASS__METAINFO = ABSTRACT_CONTAINER__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CLASS__DOCUMENTATION = ABSTRACT_CONTAINER__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CLASS__HAS_COMMENTS = ABSTRACT_CONTAINER__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CLASS__NAME = ABSTRACT_CONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CLASS__DESCRIPTION = ABSTRACT_CONTAINER__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CLASS__TITLE = ABSTRACT_CONTAINER__TITLE;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CLASS__ATTRIBUTES = ABSTRACT_CONTAINER__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Associations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CLASS__ASSOCIATIONS = ABSTRACT_CONTAINER__ASSOCIATIONS;

	/**
	 * The number of structural features of the '<em>Abstract Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CLASS_FEATURE_COUNT = ABSTRACT_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.ClazzImpl <em>Clazz</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.ClazzImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getClazz()
	 * @generated
	 */
	int CLAZZ = 3;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__STEREOTYPES = ABSTRACT_CLASS__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__TAGS = ABSTRACT_CLASS__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__COMMENTS = ABSTRACT_CLASS__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__METAINFO = ABSTRACT_CLASS__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__DOCUMENTATION = ABSTRACT_CLASS__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__HAS_COMMENTS = ABSTRACT_CLASS__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__NAME = ABSTRACT_CLASS__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__DESCRIPTION = ABSTRACT_CLASS__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__TITLE = ABSTRACT_CLASS__TITLE;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__ATTRIBUTES = ABSTRACT_CLASS__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Associations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__ASSOCIATIONS = ABSTRACT_CLASS__ASSOCIATIONS;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__OPERATIONS = ABSTRACT_CLASS_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Generalizations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__GENERALIZATIONS = ABSTRACT_CLASS_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Aspects</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__ASPECTS = ABSTRACT_CLASS_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Is Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__IS_ABSTRACT = ABSTRACT_CLASS_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Is Deprecated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__IS_DEPRECATED = ABSTRACT_CLASS_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Has View</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__HAS_VIEW = ABSTRACT_CLASS_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Clazz</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ_FEATURE_COUNT = ABSTRACT_CLASS_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.AssociationImpl <em>Association</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.AssociationImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAssociation()
	 * @generated
	 */
	int ASSOCIATION = 4;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__STEREOTYPES = TITLED_NAMED_CLASS_MODEL_ELEMENT__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__TAGS = TITLED_NAMED_CLASS_MODEL_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__COMMENTS = TITLED_NAMED_CLASS_MODEL_ELEMENT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__METAINFO = TITLED_NAMED_CLASS_MODEL_ELEMENT__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__DOCUMENTATION = TITLED_NAMED_CLASS_MODEL_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__HAS_COMMENTS = TITLED_NAMED_CLASS_MODEL_ELEMENT__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__NAME = TITLED_NAMED_CLASS_MODEL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__DESCRIPTION = TITLED_NAMED_CLASS_MODEL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__TITLE = TITLED_NAMED_CLASS_MODEL_ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Association Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__ASSOCIATION_TYPE = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Associations Class</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__ASSOCIATIONS_CLASS = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>First End</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__FIRST_END = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Second End</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION__SECOND_END = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Association</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_FEATURE_COUNT = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.AttributeImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 5;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__STEREOTYPES = TITLED_NAMED_CLASS_MODEL_ELEMENT__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TAGS = TITLED_NAMED_CLASS_MODEL_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__COMMENTS = TITLED_NAMED_CLASS_MODEL_ELEMENT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__METAINFO = TITLED_NAMED_CLASS_MODEL_ELEMENT__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__DOCUMENTATION = TITLED_NAMED_CLASS_MODEL_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__HAS_COMMENTS = TITLED_NAMED_CLASS_MODEL_ELEMENT__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NAME = TITLED_NAMED_CLASS_MODEL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__DESCRIPTION = TITLED_NAMED_CLASS_MODEL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TITLE = TITLED_NAMED_CLASS_MODEL_ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Typ</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TYP = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__INITIAL_VALUE = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__VISIBILITY = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Value List</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__VALUE_LIST = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__UNIQUE = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.EnumerationImpl <em>Enumeration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.EnumerationImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getEnumeration()
	 * @generated
	 */
	int ENUMERATION = 6;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__STEREOTYPES = NAMED_CLASS_MODEL_ELEMENT__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__TAGS = NAMED_CLASS_MODEL_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__COMMENTS = NAMED_CLASS_MODEL_ELEMENT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__METAINFO = NAMED_CLASS_MODEL_ELEMENT__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__DOCUMENTATION = NAMED_CLASS_MODEL_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__HAS_COMMENTS = NAMED_CLASS_MODEL_ELEMENT__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__NAME = NAMED_CLASS_MODEL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__DESCRIPTION = NAMED_CLASS_MODEL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Literals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__LITERALS = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__IS_DYNAMIC = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Depends</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__DEPENDS = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Enumeration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_FEATURE_COUNT = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.EnumerationLiteralImpl <em>Enumeration Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.EnumerationLiteralImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getEnumerationLiteral()
	 * @generated
	 */
	int ENUMERATION_LITERAL = 7;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL__NAME = 1;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL__ENUM = 2;

	/**
	 * The number of structural features of the '<em>Enumeration Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_LITERAL_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.OperationImpl <em>Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.OperationImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getOperation()
	 * @generated
	 */
	int OPERATION = 8;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__STEREOTYPES = NAMED_CLASS_MODEL_ELEMENT__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__TAGS = NAMED_CLASS_MODEL_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__COMMENTS = NAMED_CLASS_MODEL_ELEMENT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__METAINFO = NAMED_CLASS_MODEL_ELEMENT__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__DOCUMENTATION = NAMED_CLASS_MODEL_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__HAS_COMMENTS = NAMED_CLASS_MODEL_ELEMENT__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__NAME = NAMED_CLASS_MODEL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__DESCRIPTION = NAMED_CLASS_MODEL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__RETURN_TYPE = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__PARAMETERS = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__VISIBILITY = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__STATIC = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__BODY = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_FEATURE_COUNT = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.ParameterImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 9;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__STEREOTYPES = NAMED_CLASS_MODEL_ELEMENT__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__TAGS = NAMED_CLASS_MODEL_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__COMMENTS = NAMED_CLASS_MODEL_ELEMENT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__METAINFO = NAMED_CLASS_MODEL_ELEMENT__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__DOCUMENTATION = NAMED_CLASS_MODEL_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__HAS_COMMENTS = NAMED_CLASS_MODEL_ELEMENT__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = NAMED_CLASS_MODEL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__DESCRIPTION = NAMED_CLASS_MODEL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Value Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__VALUE_TYPE = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.AspectImpl <em>Aspect</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.AspectImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAspect()
	 * @generated
	 */
	int ASPECT = 10;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT__STEREOTYPES = ABSTRACT_CLASS__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT__TAGS = ABSTRACT_CLASS__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT__COMMENTS = ABSTRACT_CLASS__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT__METAINFO = ABSTRACT_CLASS__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT__DOCUMENTATION = ABSTRACT_CLASS__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT__HAS_COMMENTS = ABSTRACT_CLASS__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT__NAME = ABSTRACT_CLASS__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT__DESCRIPTION = ABSTRACT_CLASS__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT__TITLE = ABSTRACT_CLASS__TITLE;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT__ATTRIBUTES = ABSTRACT_CLASS__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Associations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT__ASSOCIATIONS = ABSTRACT_CLASS__ASSOCIATIONS;

	/**
	 * The number of structural features of the '<em>Aspect</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT_FEATURE_COUNT = ABSTRACT_CLASS_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.ViewImpl <em>View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.ViewImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getView()
	 * @generated
	 */
	int VIEW = 13;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__STEREOTYPES = NAMED_CLASS_MODEL_ELEMENT__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__TAGS = NAMED_CLASS_MODEL_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__COMMENTS = NAMED_CLASS_MODEL_ELEMENT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__METAINFO = NAMED_CLASS_MODEL_ELEMENT__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__DOCUMENTATION = NAMED_CLASS_MODEL_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__HAS_COMMENTS = NAMED_CLASS_MODEL_ELEMENT__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__NAME = NAMED_CLASS_MODEL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__DESCRIPTION = NAMED_CLASS_MODEL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__ATTRIBUTES = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_FEATURE_COUNT = NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.ViewItemImpl <em>View Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.ViewItemImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getViewItem()
	 * @generated
	 */
	int VIEW_ITEM = 14;

	/**
	 * The feature id for the '<em><b>Association</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ITEM__ASSOCIATION = 0;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ITEM__ATTRIBUTE = 1;

	/**
	 * The feature id for the '<em><b>Classe</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ITEM__CLASSE = 2;

	/**
	 * The feature id for the '<em><b>Aspect</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ITEM__ASPECT = 3;

	/**
	 * The feature id for the '<em><b>Role</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ITEM__ROLE = 4;

	/**
	 * The number of structural features of the '<em>View Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_ITEM_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.MetaInfoImpl <em>Meta Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.MetaInfoImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getMetaInfo()
	 * @generated
	 */
	int META_INFO = 15;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_INFO__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_INFO__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Value Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_INFO__VALUE_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Constraint Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_INFO__CONSTRAINT_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Value Set</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_INFO__VALUE_SET = 4;

	/**
	 * The number of structural features of the '<em>Meta Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_INFO_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.MetaInfoGroupImpl <em>Meta Info Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.MetaInfoGroupImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getMetaInfoGroup()
	 * @generated
	 */
	int META_INFO_GROUP = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_INFO_GROUP__NAME = 0;

	/**
	 * The feature id for the '<em><b>Contraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_INFO_GROUP__CONTRAINTS = 1;

	/**
	 * The number of structural features of the '<em>Meta Info Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_INFO_GROUP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.ClassCommentImpl <em>Class Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.ClassCommentImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getClassComment()
	 * @generated
	 */
	int CLASS_COMMENT = 18;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_COMMENT__STEREOTYPES = CommonPackage.COMMENT__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_COMMENT__TAGS = CommonPackage.COMMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_COMMENT__COMMENTS = CommonPackage.COMMENT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_COMMENT__VALUE = CommonPackage.COMMENT__VALUE;

	/**
	 * The number of structural features of the '<em>Class Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_COMMENT_FEATURE_COUNT = CommonPackage.COMMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.AssociationEndImpl <em>Association End</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.AssociationEndImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAssociationEnd()
	 * @generated
	 */
	int ASSOCIATION_END = 19;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__STEREOTYPES = TITLED_NAMED_CLASS_MODEL_ELEMENT__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__TAGS = TITLED_NAMED_CLASS_MODEL_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__COMMENTS = TITLED_NAMED_CLASS_MODEL_ELEMENT__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__METAINFO = TITLED_NAMED_CLASS_MODEL_ELEMENT__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__DOCUMENTATION = TITLED_NAMED_CLASS_MODEL_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__HAS_COMMENTS = TITLED_NAMED_CLASS_MODEL_ELEMENT__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__NAME = TITLED_NAMED_CLASS_MODEL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__DESCRIPTION = TITLED_NAMED_CLASS_MODEL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__TITLE = TITLED_NAMED_CLASS_MODEL_ELEMENT__TITLE;

	/**
	 * The feature id for the '<em><b>Card Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__CARD_MIN = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Card Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__CARD_MAX = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Is Navigable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__IS_NAVIGABLE = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Linked Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END__LINKED_CLASS = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Association End</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSOCIATION_END_FEATURE_COUNT = TITLED_NAMED_CLASS_MODEL_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.FirstEndImpl <em>First End</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.FirstEndImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getFirstEnd()
	 * @generated
	 */
	int FIRST_END = 20;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__STEREOTYPES = ASSOCIATION_END__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__TAGS = ASSOCIATION_END__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__COMMENTS = ASSOCIATION_END__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__METAINFO = ASSOCIATION_END__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__DOCUMENTATION = ASSOCIATION_END__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__HAS_COMMENTS = ASSOCIATION_END__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__NAME = ASSOCIATION_END__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__DESCRIPTION = ASSOCIATION_END__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__TITLE = ASSOCIATION_END__TITLE;

	/**
	 * The feature id for the '<em><b>Card Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__CARD_MIN = ASSOCIATION_END__CARD_MIN;

	/**
	 * The feature id for the '<em><b>Card Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__CARD_MAX = ASSOCIATION_END__CARD_MAX;

	/**
	 * The feature id for the '<em><b>Is Navigable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__IS_NAVIGABLE = ASSOCIATION_END__IS_NAVIGABLE;

	/**
	 * The feature id for the '<em><b>Linked Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END__LINKED_CLASS = ASSOCIATION_END__LINKED_CLASS;

	/**
	 * The number of structural features of the '<em>First End</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIRST_END_FEATURE_COUNT = ASSOCIATION_END_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.impl.SecondEndImpl <em>Second End</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.impl.SecondEndImpl
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getSecondEnd()
	 * @generated
	 */
	int SECOND_END = 21;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__STEREOTYPES = ASSOCIATION_END__STEREOTYPES;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__TAGS = ASSOCIATION_END__TAGS;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__COMMENTS = ASSOCIATION_END__COMMENTS;

	/**
	 * The feature id for the '<em><b>Metainfo</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__METAINFO = ASSOCIATION_END__METAINFO;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__DOCUMENTATION = ASSOCIATION_END__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Has Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__HAS_COMMENTS = ASSOCIATION_END__HAS_COMMENTS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__NAME = ASSOCIATION_END__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__DESCRIPTION = ASSOCIATION_END__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__TITLE = ASSOCIATION_END__TITLE;

	/**
	 * The feature id for the '<em><b>Card Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__CARD_MIN = ASSOCIATION_END__CARD_MIN;

	/**
	 * The feature id for the '<em><b>Card Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__CARD_MAX = ASSOCIATION_END__CARD_MAX;

	/**
	 * The feature id for the '<em><b>Is Navigable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__IS_NAVIGABLE = ASSOCIATION_END__IS_NAVIGABLE;

	/**
	 * The feature id for the '<em><b>Linked Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END__LINKED_CLASS = ASSOCIATION_END__LINKED_CLASS;

	/**
	 * The number of structural features of the '<em>Second End</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECOND_END_FEATURE_COUNT = ASSOCIATION_END_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.AssociationType <em>Association Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.AssociationType
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAssociationType()
	 * @generated
	 */
	int ASSOCIATION_TYPE = 22;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.AttributeType <em>Attribute Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.AttributeType
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAttributeType()
	 * @generated
	 */
	int ATTRIBUTE_TYPE = 23;

	/**
	 * The meta object id for the '{@link com.bluexml.side.clazz.Visibility <em>Visibility</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.bluexml.side.clazz.Visibility
	 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getVisibility()
	 * @generated
	 */
	int VISIBILITY = 24;


	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.ClassModelElement <em>Class Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class Model Element</em>'.
	 * @see com.bluexml.side.clazz.ClassModelElement
	 * @generated
	 */
	EClass getClassModelElement();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.ClassModelElement#getMetainfo <em>Metainfo</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metainfo</em>'.
	 * @see com.bluexml.side.clazz.ClassModelElement#getMetainfo()
	 * @see #getClassModelElement()
	 * @generated
	 */
	EReference getClassModelElement_Metainfo();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.ClassModelElement#getDocumentation <em>Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Documentation</em>'.
	 * @see com.bluexml.side.clazz.ClassModelElement#getDocumentation()
	 * @see #getClassModelElement()
	 * @generated
	 */
	EAttribute getClassModelElement_Documentation();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.ClassModelElement#getHasComments <em>Has Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Has Comments</em>'.
	 * @see com.bluexml.side.clazz.ClassModelElement#getHasComments()
	 * @see #getClassModelElement()
	 * @generated
	 */
	EReference getClassModelElement_HasComments();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.NamedClassModelElement <em>Named Class Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Class Model Element</em>'.
	 * @see com.bluexml.side.clazz.NamedClassModelElement
	 * @generated
	 */
	EClass getNamedClassModelElement();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.NamedClassModelElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.bluexml.side.clazz.NamedClassModelElement#getName()
	 * @see #getNamedClassModelElement()
	 * @generated
	 */
	EAttribute getNamedClassModelElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.NamedClassModelElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.bluexml.side.clazz.NamedClassModelElement#getDescription()
	 * @see #getNamedClassModelElement()
	 * @generated
	 */
	EAttribute getNamedClassModelElement_Description();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.ClassPackage <em>Class Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class Package</em>'.
	 * @see com.bluexml.side.clazz.ClassPackage
	 * @generated
	 */
	EClass getClassPackage();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.ClassPackage#getClassSet <em>Class Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Class Set</em>'.
	 * @see com.bluexml.side.clazz.ClassPackage#getClassSet()
	 * @see #getClassPackage()
	 * @generated
	 */
	EReference getClassPackage_ClassSet();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.ClassPackage#getAssociationSet <em>Association Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Association Set</em>'.
	 * @see com.bluexml.side.clazz.ClassPackage#getAssociationSet()
	 * @see #getClassPackage()
	 * @generated
	 */
	EReference getClassPackage_AssociationSet();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.ClassPackage#getAspectSet <em>Aspect Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Aspect Set</em>'.
	 * @see com.bluexml.side.clazz.ClassPackage#getAspectSet()
	 * @see #getClassPackage()
	 * @generated
	 */
	EReference getClassPackage_AspectSet();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.ClassPackage#getEnumerationSet <em>Enumeration Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Enumeration Set</em>'.
	 * @see com.bluexml.side.clazz.ClassPackage#getEnumerationSet()
	 * @see #getClassPackage()
	 * @generated
	 */
	EReference getClassPackage_EnumerationSet();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.ClassPackage#getViews <em>Views</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Views</em>'.
	 * @see com.bluexml.side.clazz.ClassPackage#getViews()
	 * @see #getClassPackage()
	 * @generated
	 */
	EReference getClassPackage_Views();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.Clazz <em>Clazz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Clazz</em>'.
	 * @see com.bluexml.side.clazz.Clazz
	 * @generated
	 */
	EClass getClazz();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.Clazz#getOperations <em>Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operations</em>'.
	 * @see com.bluexml.side.clazz.Clazz#getOperations()
	 * @see #getClazz()
	 * @generated
	 */
	EReference getClazz_Operations();

	/**
	 * Returns the meta object for the reference list '{@link com.bluexml.side.clazz.Clazz#getGeneralizations <em>Generalizations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Generalizations</em>'.
	 * @see com.bluexml.side.clazz.Clazz#getGeneralizations()
	 * @see #getClazz()
	 * @generated
	 */
	EReference getClazz_Generalizations();

	/**
	 * Returns the meta object for the reference list '{@link com.bluexml.side.clazz.Clazz#getAspects <em>Aspects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Aspects</em>'.
	 * @see com.bluexml.side.clazz.Clazz#getAspects()
	 * @see #getClazz()
	 * @generated
	 */
	EReference getClazz_Aspects();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Clazz#isIsAbstract <em>Is Abstract</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Abstract</em>'.
	 * @see com.bluexml.side.clazz.Clazz#isIsAbstract()
	 * @see #getClazz()
	 * @generated
	 */
	EAttribute getClazz_IsAbstract();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Clazz#isIsDeprecated <em>Is Deprecated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Deprecated</em>'.
	 * @see com.bluexml.side.clazz.Clazz#isIsDeprecated()
	 * @see #getClazz()
	 * @generated
	 */
	EAttribute getClazz_IsDeprecated();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.Clazz#getHasView <em>Has View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Has View</em>'.
	 * @see com.bluexml.side.clazz.Clazz#getHasView()
	 * @see #getClazz()
	 * @generated
	 */
	EReference getClazz_HasView();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.Association <em>Association</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Association</em>'.
	 * @see com.bluexml.side.clazz.Association
	 * @generated
	 */
	EClass getAssociation();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Association#getAssociationType <em>Association Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Association Type</em>'.
	 * @see com.bluexml.side.clazz.Association#getAssociationType()
	 * @see #getAssociation()
	 * @generated
	 */
	EAttribute getAssociation_AssociationType();

	/**
	 * Returns the meta object for the reference list '{@link com.bluexml.side.clazz.Association#getAssociationsClass <em>Associations Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Associations Class</em>'.
	 * @see com.bluexml.side.clazz.Association#getAssociationsClass()
	 * @see #getAssociation()
	 * @generated
	 */
	EReference getAssociation_AssociationsClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.bluexml.side.clazz.Association#getFirstEnd <em>First End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>First End</em>'.
	 * @see com.bluexml.side.clazz.Association#getFirstEnd()
	 * @see #getAssociation()
	 * @generated
	 */
	EReference getAssociation_FirstEnd();

	/**
	 * Returns the meta object for the containment reference '{@link com.bluexml.side.clazz.Association#getSecondEnd <em>Second End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Second End</em>'.
	 * @see com.bluexml.side.clazz.Association#getSecondEnd()
	 * @see #getAssociation()
	 * @generated
	 */
	EReference getAssociation_SecondEnd();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see com.bluexml.side.clazz.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Attribute#getTyp <em>Typ</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Typ</em>'.
	 * @see com.bluexml.side.clazz.Attribute#getTyp()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Typ();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Attribute#getInitialValue <em>Initial Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Value</em>'.
	 * @see com.bluexml.side.clazz.Attribute#getInitialValue()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_InitialValue();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Attribute#getVisibility <em>Visibility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see com.bluexml.side.clazz.Attribute#getVisibility()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Visibility();

	/**
	 * Returns the meta object for the reference '{@link com.bluexml.side.clazz.Attribute#getValueList <em>Value List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value List</em>'.
	 * @see com.bluexml.side.clazz.Attribute#getValueList()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_ValueList();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Attribute#isUnique <em>Unique</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique</em>'.
	 * @see com.bluexml.side.clazz.Attribute#isUnique()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Unique();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.Enumeration <em>Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumeration</em>'.
	 * @see com.bluexml.side.clazz.Enumeration
	 * @generated
	 */
	EClass getEnumeration();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.Enumeration#getLiterals <em>Literals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Literals</em>'.
	 * @see com.bluexml.side.clazz.Enumeration#getLiterals()
	 * @see #getEnumeration()
	 * @generated
	 */
	EReference getEnumeration_Literals();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Enumeration#getIsDynamic <em>Is Dynamic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Dynamic</em>'.
	 * @see com.bluexml.side.clazz.Enumeration#getIsDynamic()
	 * @see #getEnumeration()
	 * @generated
	 */
	EAttribute getEnumeration_IsDynamic();

	/**
	 * Returns the meta object for the reference '{@link com.bluexml.side.clazz.Enumeration#getDepends <em>Depends</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Depends</em>'.
	 * @see com.bluexml.side.clazz.Enumeration#getDepends()
	 * @see #getEnumeration()
	 * @generated
	 */
	EReference getEnumeration_Depends();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.EnumerationLiteral <em>Enumeration Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumeration Literal</em>'.
	 * @see com.bluexml.side.clazz.EnumerationLiteral
	 * @generated
	 */
	EClass getEnumerationLiteral();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.EnumerationLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.bluexml.side.clazz.EnumerationLiteral#getValue()
	 * @see #getEnumerationLiteral()
	 * @generated
	 */
	EAttribute getEnumerationLiteral_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.EnumerationLiteral#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.bluexml.side.clazz.EnumerationLiteral#getName()
	 * @see #getEnumerationLiteral()
	 * @generated
	 */
	EAttribute getEnumerationLiteral_Name();

	/**
	 * Returns the meta object for the container reference '{@link com.bluexml.side.clazz.EnumerationLiteral#getEnum <em>Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Enum</em>'.
	 * @see com.bluexml.side.clazz.EnumerationLiteral#getEnum()
	 * @see #getEnumerationLiteral()
	 * @generated
	 */
	EReference getEnumerationLiteral_Enum();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.Operation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation</em>'.
	 * @see com.bluexml.side.clazz.Operation
	 * @generated
	 */
	EClass getOperation();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Operation#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Return Type</em>'.
	 * @see com.bluexml.side.clazz.Operation#getReturnType()
	 * @see #getOperation()
	 * @generated
	 */
	EAttribute getOperation_ReturnType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.Operation#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see com.bluexml.side.clazz.Operation#getParameters()
	 * @see #getOperation()
	 * @generated
	 */
	EReference getOperation_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Operation#getVisibility <em>Visibility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see com.bluexml.side.clazz.Operation#getVisibility()
	 * @see #getOperation()
	 * @generated
	 */
	EAttribute getOperation_Visibility();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Operation#isStatic <em>Static</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Static</em>'.
	 * @see com.bluexml.side.clazz.Operation#isStatic()
	 * @see #getOperation()
	 * @generated
	 */
	EAttribute getOperation_Static();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Operation#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Body</em>'.
	 * @see com.bluexml.side.clazz.Operation#getBody()
	 * @see #getOperation()
	 * @generated
	 */
	EAttribute getOperation_Body();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see com.bluexml.side.clazz.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.Parameter#getValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Type</em>'.
	 * @see com.bluexml.side.clazz.Parameter#getValueType()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_ValueType();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.Aspect <em>Aspect</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Aspect</em>'.
	 * @see com.bluexml.side.clazz.Aspect
	 * @generated
	 */
	EClass getAspect();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.AbstractClass <em>Abstract Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Class</em>'.
	 * @see com.bluexml.side.clazz.AbstractClass
	 * @generated
	 */
	EClass getAbstractClass();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.AbstractContainer <em>Abstract Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Container</em>'.
	 * @see com.bluexml.side.clazz.AbstractContainer
	 * @generated
	 */
	EClass getAbstractContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.AbstractContainer#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see com.bluexml.side.clazz.AbstractContainer#getAttributes()
	 * @see #getAbstractContainer()
	 * @generated
	 */
	EReference getAbstractContainer_Attributes();

	/**
	 * Returns the meta object for the reference list '{@link com.bluexml.side.clazz.AbstractContainer#getAssociations <em>Associations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Associations</em>'.
	 * @see com.bluexml.side.clazz.AbstractContainer#getAssociations()
	 * @see #getAbstractContainer()
	 * @generated
	 */
	EReference getAbstractContainer_Associations();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.View <em>View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View</em>'.
	 * @see com.bluexml.side.clazz.View
	 * @generated
	 */
	EClass getView();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.View#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see com.bluexml.side.clazz.View#getAttributes()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_Attributes();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.ViewItem <em>View Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View Item</em>'.
	 * @see com.bluexml.side.clazz.ViewItem
	 * @generated
	 */
	EClass getViewItem();

	/**
	 * Returns the meta object for the reference '{@link com.bluexml.side.clazz.ViewItem#getAssociation <em>Association</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Association</em>'.
	 * @see com.bluexml.side.clazz.ViewItem#getAssociation()
	 * @see #getViewItem()
	 * @generated
	 */
	EReference getViewItem_Association();

	/**
	 * Returns the meta object for the reference '{@link com.bluexml.side.clazz.ViewItem#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see com.bluexml.side.clazz.ViewItem#getAttribute()
	 * @see #getViewItem()
	 * @generated
	 */
	EReference getViewItem_Attribute();

	/**
	 * Returns the meta object for the reference '{@link com.bluexml.side.clazz.ViewItem#getClasse <em>Classe</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Classe</em>'.
	 * @see com.bluexml.side.clazz.ViewItem#getClasse()
	 * @see #getViewItem()
	 * @generated
	 */
	EReference getViewItem_Classe();

	/**
	 * Returns the meta object for the reference '{@link com.bluexml.side.clazz.ViewItem#getAspect <em>Aspect</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Aspect</em>'.
	 * @see com.bluexml.side.clazz.ViewItem#getAspect()
	 * @see #getViewItem()
	 * @generated
	 */
	EReference getViewItem_Aspect();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.ViewItem#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Role</em>'.
	 * @see com.bluexml.side.clazz.ViewItem#getRole()
	 * @see #getViewItem()
	 * @generated
	 */
	EAttribute getViewItem_Role();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.MetaInfo <em>Meta Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Info</em>'.
	 * @see com.bluexml.side.clazz.MetaInfo
	 * @generated
	 */
	EClass getMetaInfo();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.MetaInfo#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.bluexml.side.clazz.MetaInfo#getKey()
	 * @see #getMetaInfo()
	 * @generated
	 */
	EAttribute getMetaInfo_Key();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.MetaInfo#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.bluexml.side.clazz.MetaInfo#getValue()
	 * @see #getMetaInfo()
	 * @generated
	 */
	EAttribute getMetaInfo_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.MetaInfo#getValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Type</em>'.
	 * @see com.bluexml.side.clazz.MetaInfo#getValueType()
	 * @see #getMetaInfo()
	 * @generated
	 */
	EAttribute getMetaInfo_ValueType();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.MetaInfo#getConstraintType <em>Constraint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constraint Type</em>'.
	 * @see com.bluexml.side.clazz.MetaInfo#getConstraintType()
	 * @see #getMetaInfo()
	 * @generated
	 */
	EAttribute getMetaInfo_ConstraintType();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.MetaInfo#getValueSet <em>Value Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Set</em>'.
	 * @see com.bluexml.side.clazz.MetaInfo#getValueSet()
	 * @see #getMetaInfo()
	 * @generated
	 */
	EAttribute getMetaInfo_ValueSet();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.MetaInfoGroup <em>Meta Info Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Info Group</em>'.
	 * @see com.bluexml.side.clazz.MetaInfoGroup
	 * @generated
	 */
	EClass getMetaInfoGroup();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.MetaInfoGroup#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.bluexml.side.clazz.MetaInfoGroup#getName()
	 * @see #getMetaInfoGroup()
	 * @generated
	 */
	EAttribute getMetaInfoGroup_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link com.bluexml.side.clazz.MetaInfoGroup#getContraints <em>Contraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contraints</em>'.
	 * @see com.bluexml.side.clazz.MetaInfoGroup#getContraints()
	 * @see #getMetaInfoGroup()
	 * @generated
	 */
	EReference getMetaInfoGroup_Contraints();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.TitledNamedClassModelElement <em>Titled Named Class Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Titled Named Class Model Element</em>'.
	 * @see com.bluexml.side.clazz.TitledNamedClassModelElement
	 * @generated
	 */
	EClass getTitledNamedClassModelElement();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.TitledNamedClassModelElement#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see com.bluexml.side.clazz.TitledNamedClassModelElement#getTitle()
	 * @see #getTitledNamedClassModelElement()
	 * @generated
	 */
	EAttribute getTitledNamedClassModelElement_Title();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.ClassComment <em>Class Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class Comment</em>'.
	 * @see com.bluexml.side.clazz.ClassComment
	 * @generated
	 */
	EClass getClassComment();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.AssociationEnd <em>Association End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Association End</em>'.
	 * @see com.bluexml.side.clazz.AssociationEnd
	 * @generated
	 */
	EClass getAssociationEnd();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.AssociationEnd#getCardMin <em>Card Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Card Min</em>'.
	 * @see com.bluexml.side.clazz.AssociationEnd#getCardMin()
	 * @see #getAssociationEnd()
	 * @generated
	 */
	EAttribute getAssociationEnd_CardMin();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.AssociationEnd#getCardMax <em>Card Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Card Max</em>'.
	 * @see com.bluexml.side.clazz.AssociationEnd#getCardMax()
	 * @see #getAssociationEnd()
	 * @generated
	 */
	EAttribute getAssociationEnd_CardMax();

	/**
	 * Returns the meta object for the attribute '{@link com.bluexml.side.clazz.AssociationEnd#isIsNavigable <em>Is Navigable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Navigable</em>'.
	 * @see com.bluexml.side.clazz.AssociationEnd#isIsNavigable()
	 * @see #getAssociationEnd()
	 * @generated
	 */
	EAttribute getAssociationEnd_IsNavigable();

	/**
	 * Returns the meta object for the reference '{@link com.bluexml.side.clazz.AssociationEnd#getLinkedClass <em>Linked Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Linked Class</em>'.
	 * @see com.bluexml.side.clazz.AssociationEnd#getLinkedClass()
	 * @see #getAssociationEnd()
	 * @generated
	 */
	EReference getAssociationEnd_LinkedClass();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.FirstEnd <em>First End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>First End</em>'.
	 * @see com.bluexml.side.clazz.FirstEnd
	 * @generated
	 */
	EClass getFirstEnd();

	/**
	 * Returns the meta object for class '{@link com.bluexml.side.clazz.SecondEnd <em>Second End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Second End</em>'.
	 * @see com.bluexml.side.clazz.SecondEnd
	 * @generated
	 */
	EClass getSecondEnd();

	/**
	 * Returns the meta object for enum '{@link com.bluexml.side.clazz.AssociationType <em>Association Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Association Type</em>'.
	 * @see com.bluexml.side.clazz.AssociationType
	 * @generated
	 */
	EEnum getAssociationType();

	/**
	 * Returns the meta object for enum '{@link com.bluexml.side.clazz.AttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Attribute Type</em>'.
	 * @see com.bluexml.side.clazz.AttributeType
	 * @generated
	 */
	EEnum getAttributeType();

	/**
	 * Returns the meta object for enum '{@link com.bluexml.side.clazz.Visibility <em>Visibility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Visibility</em>'.
	 * @see com.bluexml.side.clazz.Visibility
	 * @generated
	 */
	EEnum getVisibility();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ClazzFactory getClazzFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.ClassModelElementImpl <em>Class Model Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.ClassModelElementImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getClassModelElement()
		 * @generated
		 */
		EClass CLASS_MODEL_ELEMENT = eINSTANCE.getClassModelElement();

		/**
		 * The meta object literal for the '<em><b>Metainfo</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_MODEL_ELEMENT__METAINFO = eINSTANCE.getClassModelElement_Metainfo();

		/**
		 * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_MODEL_ELEMENT__DOCUMENTATION = eINSTANCE.getClassModelElement_Documentation();

		/**
		 * The meta object literal for the '<em><b>Has Comments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_MODEL_ELEMENT__HAS_COMMENTS = eINSTANCE.getClassModelElement_HasComments();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.NamedClassModelElementImpl <em>Named Class Model Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.NamedClassModelElementImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getNamedClassModelElement()
		 * @generated
		 */
		EClass NAMED_CLASS_MODEL_ELEMENT = eINSTANCE.getNamedClassModelElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_CLASS_MODEL_ELEMENT__NAME = eINSTANCE.getNamedClassModelElement_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_CLASS_MODEL_ELEMENT__DESCRIPTION = eINSTANCE.getNamedClassModelElement_Description();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.ClassPackageImpl <em>Class Package</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.ClassPackageImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getClassPackage()
		 * @generated
		 */
		EClass CLASS_PACKAGE = eINSTANCE.getClassPackage();

		/**
		 * The meta object literal for the '<em><b>Class Set</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_PACKAGE__CLASS_SET = eINSTANCE.getClassPackage_ClassSet();

		/**
		 * The meta object literal for the '<em><b>Association Set</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_PACKAGE__ASSOCIATION_SET = eINSTANCE.getClassPackage_AssociationSet();

		/**
		 * The meta object literal for the '<em><b>Aspect Set</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_PACKAGE__ASPECT_SET = eINSTANCE.getClassPackage_AspectSet();

		/**
		 * The meta object literal for the '<em><b>Enumeration Set</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_PACKAGE__ENUMERATION_SET = eINSTANCE.getClassPackage_EnumerationSet();

		/**
		 * The meta object literal for the '<em><b>Views</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_PACKAGE__VIEWS = eINSTANCE.getClassPackage_Views();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.ClazzImpl <em>Clazz</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.ClazzImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getClazz()
		 * @generated
		 */
		EClass CLAZZ = eINSTANCE.getClazz();

		/**
		 * The meta object literal for the '<em><b>Operations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLAZZ__OPERATIONS = eINSTANCE.getClazz_Operations();

		/**
		 * The meta object literal for the '<em><b>Generalizations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLAZZ__GENERALIZATIONS = eINSTANCE.getClazz_Generalizations();

		/**
		 * The meta object literal for the '<em><b>Aspects</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLAZZ__ASPECTS = eINSTANCE.getClazz_Aspects();

		/**
		 * The meta object literal for the '<em><b>Is Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLAZZ__IS_ABSTRACT = eINSTANCE.getClazz_IsAbstract();

		/**
		 * The meta object literal for the '<em><b>Is Deprecated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLAZZ__IS_DEPRECATED = eINSTANCE.getClazz_IsDeprecated();

		/**
		 * The meta object literal for the '<em><b>Has View</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLAZZ__HAS_VIEW = eINSTANCE.getClazz_HasView();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.AssociationImpl <em>Association</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.AssociationImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAssociation()
		 * @generated
		 */
		EClass ASSOCIATION = eINSTANCE.getAssociation();

		/**
		 * The meta object literal for the '<em><b>Association Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSOCIATION__ASSOCIATION_TYPE = eINSTANCE.getAssociation_AssociationType();

		/**
		 * The meta object literal for the '<em><b>Associations Class</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSOCIATION__ASSOCIATIONS_CLASS = eINSTANCE.getAssociation_AssociationsClass();

		/**
		 * The meta object literal for the '<em><b>First End</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSOCIATION__FIRST_END = eINSTANCE.getAssociation_FirstEnd();

		/**
		 * The meta object literal for the '<em><b>Second End</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSOCIATION__SECOND_END = eINSTANCE.getAssociation_SecondEnd();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.AttributeImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Typ</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__TYP = eINSTANCE.getAttribute_Typ();

		/**
		 * The meta object literal for the '<em><b>Initial Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__INITIAL_VALUE = eINSTANCE.getAttribute_InitialValue();

		/**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__VISIBILITY = eINSTANCE.getAttribute_Visibility();

		/**
		 * The meta object literal for the '<em><b>Value List</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__VALUE_LIST = eINSTANCE.getAttribute_ValueList();

		/**
		 * The meta object literal for the '<em><b>Unique</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__UNIQUE = eINSTANCE.getAttribute_Unique();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.EnumerationImpl <em>Enumeration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.EnumerationImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getEnumeration()
		 * @generated
		 */
		EClass ENUMERATION = eINSTANCE.getEnumeration();

		/**
		 * The meta object literal for the '<em><b>Literals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATION__LITERALS = eINSTANCE.getEnumeration_Literals();

		/**
		 * The meta object literal for the '<em><b>Is Dynamic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUMERATION__IS_DYNAMIC = eINSTANCE.getEnumeration_IsDynamic();

		/**
		 * The meta object literal for the '<em><b>Depends</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATION__DEPENDS = eINSTANCE.getEnumeration_Depends();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.EnumerationLiteralImpl <em>Enumeration Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.EnumerationLiteralImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getEnumerationLiteral()
		 * @generated
		 */
		EClass ENUMERATION_LITERAL = eINSTANCE.getEnumerationLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUMERATION_LITERAL__VALUE = eINSTANCE.getEnumerationLiteral_Value();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUMERATION_LITERAL__NAME = eINSTANCE.getEnumerationLiteral_Name();

		/**
		 * The meta object literal for the '<em><b>Enum</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATION_LITERAL__ENUM = eINSTANCE.getEnumerationLiteral_Enum();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.OperationImpl <em>Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.OperationImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getOperation()
		 * @generated
		 */
		EClass OPERATION = eINSTANCE.getOperation();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION__RETURN_TYPE = eINSTANCE.getOperation_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION__PARAMETERS = eINSTANCE.getOperation_Parameters();

		/**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION__VISIBILITY = eINSTANCE.getOperation_Visibility();

		/**
		 * The meta object literal for the '<em><b>Static</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION__STATIC = eINSTANCE.getOperation_Static();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION__BODY = eINSTANCE.getOperation_Body();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.ParameterImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Value Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__VALUE_TYPE = eINSTANCE.getParameter_ValueType();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.AspectImpl <em>Aspect</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.AspectImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAspect()
		 * @generated
		 */
		EClass ASPECT = eINSTANCE.getAspect();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.AbstractClassImpl <em>Abstract Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.AbstractClassImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAbstractClass()
		 * @generated
		 */
		EClass ABSTRACT_CLASS = eINSTANCE.getAbstractClass();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.AbstractContainerImpl <em>Abstract Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.AbstractContainerImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAbstractContainer()
		 * @generated
		 */
		EClass ABSTRACT_CONTAINER = eINSTANCE.getAbstractContainer();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_CONTAINER__ATTRIBUTES = eINSTANCE.getAbstractContainer_Attributes();

		/**
		 * The meta object literal for the '<em><b>Associations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_CONTAINER__ASSOCIATIONS = eINSTANCE.getAbstractContainer_Associations();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.ViewImpl <em>View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.ViewImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getView()
		 * @generated
		 */
		EClass VIEW = eINSTANCE.getView();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW__ATTRIBUTES = eINSTANCE.getView_Attributes();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.ViewItemImpl <em>View Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.ViewItemImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getViewItem()
		 * @generated
		 */
		EClass VIEW_ITEM = eINSTANCE.getViewItem();

		/**
		 * The meta object literal for the '<em><b>Association</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW_ITEM__ASSOCIATION = eINSTANCE.getViewItem_Association();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW_ITEM__ATTRIBUTE = eINSTANCE.getViewItem_Attribute();

		/**
		 * The meta object literal for the '<em><b>Classe</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW_ITEM__CLASSE = eINSTANCE.getViewItem_Classe();

		/**
		 * The meta object literal for the '<em><b>Aspect</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW_ITEM__ASPECT = eINSTANCE.getViewItem_Aspect();

		/**
		 * The meta object literal for the '<em><b>Role</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIEW_ITEM__ROLE = eINSTANCE.getViewItem_Role();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.MetaInfoImpl <em>Meta Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.MetaInfoImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getMetaInfo()
		 * @generated
		 */
		EClass META_INFO = eINSTANCE.getMetaInfo();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute META_INFO__KEY = eINSTANCE.getMetaInfo_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute META_INFO__VALUE = eINSTANCE.getMetaInfo_Value();

		/**
		 * The meta object literal for the '<em><b>Value Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute META_INFO__VALUE_TYPE = eINSTANCE.getMetaInfo_ValueType();

		/**
		 * The meta object literal for the '<em><b>Constraint Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute META_INFO__CONSTRAINT_TYPE = eINSTANCE.getMetaInfo_ConstraintType();

		/**
		 * The meta object literal for the '<em><b>Value Set</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute META_INFO__VALUE_SET = eINSTANCE.getMetaInfo_ValueSet();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.MetaInfoGroupImpl <em>Meta Info Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.MetaInfoGroupImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getMetaInfoGroup()
		 * @generated
		 */
		EClass META_INFO_GROUP = eINSTANCE.getMetaInfoGroup();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute META_INFO_GROUP__NAME = eINSTANCE.getMetaInfoGroup_Name();

		/**
		 * The meta object literal for the '<em><b>Contraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference META_INFO_GROUP__CONTRAINTS = eINSTANCE.getMetaInfoGroup_Contraints();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.TitledNamedClassModelElementImpl <em>Titled Named Class Model Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.TitledNamedClassModelElementImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getTitledNamedClassModelElement()
		 * @generated
		 */
		EClass TITLED_NAMED_CLASS_MODEL_ELEMENT = eINSTANCE.getTitledNamedClassModelElement();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TITLED_NAMED_CLASS_MODEL_ELEMENT__TITLE = eINSTANCE.getTitledNamedClassModelElement_Title();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.ClassCommentImpl <em>Class Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.ClassCommentImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getClassComment()
		 * @generated
		 */
		EClass CLASS_COMMENT = eINSTANCE.getClassComment();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.AssociationEndImpl <em>Association End</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.AssociationEndImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAssociationEnd()
		 * @generated
		 */
		EClass ASSOCIATION_END = eINSTANCE.getAssociationEnd();

		/**
		 * The meta object literal for the '<em><b>Card Min</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSOCIATION_END__CARD_MIN = eINSTANCE.getAssociationEnd_CardMin();

		/**
		 * The meta object literal for the '<em><b>Card Max</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSOCIATION_END__CARD_MAX = eINSTANCE.getAssociationEnd_CardMax();

		/**
		 * The meta object literal for the '<em><b>Is Navigable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSOCIATION_END__IS_NAVIGABLE = eINSTANCE.getAssociationEnd_IsNavigable();

		/**
		 * The meta object literal for the '<em><b>Linked Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSOCIATION_END__LINKED_CLASS = eINSTANCE.getAssociationEnd_LinkedClass();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.FirstEndImpl <em>First End</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.FirstEndImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getFirstEnd()
		 * @generated
		 */
		EClass FIRST_END = eINSTANCE.getFirstEnd();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.impl.SecondEndImpl <em>Second End</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.impl.SecondEndImpl
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getSecondEnd()
		 * @generated
		 */
		EClass SECOND_END = eINSTANCE.getSecondEnd();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.AssociationType <em>Association Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.AssociationType
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAssociationType()
		 * @generated
		 */
		EEnum ASSOCIATION_TYPE = eINSTANCE.getAssociationType();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.AttributeType <em>Attribute Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.AttributeType
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getAttributeType()
		 * @generated
		 */
		EEnum ATTRIBUTE_TYPE = eINSTANCE.getAttributeType();

		/**
		 * The meta object literal for the '{@link com.bluexml.side.clazz.Visibility <em>Visibility</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.bluexml.side.clazz.Visibility
		 * @see com.bluexml.side.clazz.impl.ClazzPackageImpl#getVisibility()
		 * @generated
		 */
		EEnum VISIBILITY = eINSTANCE.getVisibility();

	}

} //ClazzPackage
