/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bluexml.side.workflow;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition: A 'TaskNode' is a task that must be performed by a 'Swimlane'. 'TaskNode' can have one or more 'Attribute'. A task node represents one task that is to be performed by humans. So when execution arrives in a task node, task instances will be created in the task lists of the workflow participants. After that, the node will behave as a wait state. So when the users perform their task, the task completion will trigger the resuming of the execution. In other words, that leads to a new signal being called on the token.
 * 
 * Inherits: 
 * - UserTask.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bluexml.side.workflow.TaskNode#getTimer <em>Timer</em>}</li>
 *   <li>{@link com.bluexml.side.workflow.TaskNode#getSwimlane <em>Swimlane</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.bluexml.side.workflow.WorkflowPackage#getTaskNode()
 * @model annotation="http://www.bluexml.com/OCL TaskMustBePointerByTransition='Transition.allInstances()-> select(t| t.to = self)->size()=1 or self.transition -> notEmpty()\n' TaskMustHaveOneTransitionOut='self.transition -> size() >0'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='TaskMustBePointerByTransition TaskMustHaveOneTransitionOut'"
 * @generated
 */
public interface TaskNode extends UserTask {
	/**
	 * Returns the value of the '<em><b>Timer</b></em>' containment reference list.
	 * The list contents are of type {@link com.bluexml.side.workflow.Timer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timer</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timer</em>' containment reference list.
	 * @see com.bluexml.side.workflow.WorkflowPackage#getTaskNode_Timer()
	 * @model containment="true"
	 * @generated
	 */
	EList<Timer> getTimer();

	/**
	 * Returns the value of the '<em><b>Swimlane</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link com.bluexml.side.workflow.Swimlane#getManage <em>Manage</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Swimlane</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Swimlane</em>' reference.
	 * @see #setSwimlane(Swimlane)
	 * @see com.bluexml.side.workflow.WorkflowPackage#getTaskNode_Swimlane()
	 * @see com.bluexml.side.workflow.Swimlane#getManage
	 * @model opposite="manage" required="true" ordered="false"
	 * @generated
	 */
	Swimlane getSwimlane();

	/**
	 * Sets the value of the '{@link com.bluexml.side.workflow.TaskNode#getSwimlane <em>Swimlane</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Swimlane</em>' reference.
	 * @see #getSwimlane()
	 * @generated
	 */
	void setSwimlane(Swimlane value);

} // TaskNode
