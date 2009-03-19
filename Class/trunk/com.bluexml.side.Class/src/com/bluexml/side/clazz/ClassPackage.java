/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bluexml.side.clazz;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bluexml.side.clazz.ClassPackage#getClassSet <em>Class Set</em>}</li>
 *   <li>{@link com.bluexml.side.clazz.ClassPackage#getAssociationSet <em>Association Set</em>}</li>
 *   <li>{@link com.bluexml.side.clazz.ClassPackage#getAspectSet <em>Aspect Set</em>}</li>
 *   <li>{@link com.bluexml.side.clazz.ClassPackage#getEnumerationSet <em>Enumeration Set</em>}</li>
 *   <li>{@link com.bluexml.side.clazz.ClassPackage#getViews <em>Views</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.bluexml.side.clazz.ClazzPackage#getClassPackage()
 * @model
 * @generated
 */
public interface ClassPackage extends com.bluexml.side.common.Package {
	/**
	 * Returns the value of the '<em><b>Class Set</b></em>' containment reference list.
	 * The list contents are of type {@link com.bluexml.side.clazz.Clazz}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Set</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Set</em>' containment reference list.
	 * @see com.bluexml.side.clazz.ClazzPackage#getClassPackage_ClassSet()
	 * @model containment="true"
	 * @generated
	 */
	EList<Clazz> getClassSet();

	/**
	 * Returns the value of the '<em><b>Association Set</b></em>' containment reference list.
	 * The list contents are of type {@link com.bluexml.side.clazz.Association}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Association Set</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Association Set</em>' containment reference list.
	 * @see com.bluexml.side.clazz.ClazzPackage#getClassPackage_AssociationSet()
	 * @model containment="true"
	 * @generated
	 */
	EList<Association> getAssociationSet();

	/**
	 * Returns the value of the '<em><b>Aspect Set</b></em>' containment reference list.
	 * The list contents are of type {@link com.bluexml.side.clazz.Aspect}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aspect Set</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Aspect Set</em>' containment reference list.
	 * @see com.bluexml.side.clazz.ClazzPackage#getClassPackage_AspectSet()
	 * @model containment="true"
	 * @generated
	 */
	EList<Aspect> getAspectSet();

	/**
	 * Returns the value of the '<em><b>Enumeration Set</b></em>' containment reference list.
	 * The list contents are of type {@link com.bluexml.side.clazz.Enumeration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enumeration Set</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enumeration Set</em>' containment reference list.
	 * @see com.bluexml.side.clazz.ClazzPackage#getClassPackage_EnumerationSet()
	 * @model containment="true"
	 * @generated
	 */
	EList<Enumeration> getEnumerationSet();

	/**
	 * Returns the value of the '<em><b>Views</b></em>' containment reference list.
	 * The list contents are of type {@link com.bluexml.side.clazz.View}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Views</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Views</em>' containment reference list.
	 * @see com.bluexml.side.clazz.ClazzPackage#getClassPackage_Views()
	 * @model containment="true"
	 * @generated
	 */
	EList<View> getViews();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.bluexml.com/OCL body='if self.getContainer().oclIsUndefined() then\r\tself.name\relse\r\tself.getContainer().oclAsType(ClassPackage).getFullName().concat(\'.\').concat(self.name)\rendif'"
	 * @generated
	 */
	String getFullName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.bluexml.com/OCL body='ClassPackage.allInstances()'"
	 * @generated
	 */
	EList<ClassPackage> getAllPackages();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.bluexml.com/OCL body='Clazz.allInstances()'"
	 * @generated
	 */
	EList<Clazz> getAllClasses();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.bluexml.com/OCL body='Enumeration.allInstances()'"
	 * @generated
	 */
	EList<Enumeration> getAllEnumerations();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.bluexml.com/OCL body='Aspect.allInstances()'"
	 * @generated
	 */
	EList<Aspect> getAllAspects();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.bluexml.com/OCL body='Association.allInstances()'"
	 * @generated
	 */
	EList<Association> getAllAssociations();

} // ClassPackage
