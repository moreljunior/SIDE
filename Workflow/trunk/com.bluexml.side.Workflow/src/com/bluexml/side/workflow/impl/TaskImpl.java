/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bluexml.side.workflow.impl;

import com.bluexml.side.workflow.Assignment;
import com.bluexml.side.workflow.Event;
import com.bluexml.side.workflow.Swimlane;
import com.bluexml.side.workflow.Task;
import com.bluexml.side.workflow.Timer;
import com.bluexml.side.workflow.WorkflowPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bluexml.side.workflow.impl.TaskImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.bluexml.side.workflow.impl.TaskImpl#getSwimlane <em>Swimlane</em>}</li>
 *   <li>{@link com.bluexml.side.workflow.impl.TaskImpl#getAssignment <em>Assignment</em>}</li>
 *   <li>{@link com.bluexml.side.workflow.impl.TaskImpl#getEvent <em>Event</em>}</li>
 *   <li>{@link com.bluexml.side.workflow.impl.TaskImpl#getTimer <em>Timer</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TaskImpl extends EObjectImpl implements Task {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSwimlane() <em>Swimlane</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSwimlane()
	 * @generated
	 * @ordered
	 */
	protected Swimlane swimlane;

	/**
	 * The cached value of the '{@link #getAssignment() <em>Assignment</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssignment()
	 * @generated
	 * @ordered
	 */
	protected EList<Assignment> assignment;

	/**
	 * The cached value of the '{@link #getEvent() <em>Event</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvent()
	 * @generated
	 * @ordered
	 */
	protected EList<Event> event;

	/**
	 * The cached value of the '{@link #getTimer() <em>Timer</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimer()
	 * @generated
	 * @ordered
	 */
	protected EList<Timer> timer;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WorkflowPackage.Literals.TASK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.TASK__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Swimlane getSwimlane() {
		if (swimlane != null && swimlane.eIsProxy()) {
			InternalEObject oldSwimlane = (InternalEObject)swimlane;
			swimlane = (Swimlane)eResolveProxy(oldSwimlane);
			if (swimlane != oldSwimlane) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, WorkflowPackage.TASK__SWIMLANE, oldSwimlane, swimlane));
			}
		}
		return swimlane;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Swimlane basicGetSwimlane() {
		return swimlane;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSwimlane(Swimlane newSwimlane, NotificationChain msgs) {
		Swimlane oldSwimlane = swimlane;
		swimlane = newSwimlane;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WorkflowPackage.TASK__SWIMLANE, oldSwimlane, newSwimlane);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSwimlane(Swimlane newSwimlane) {
		if (newSwimlane != swimlane) {
			NotificationChain msgs = null;
			if (swimlane != null)
				msgs = ((InternalEObject)swimlane).eInverseRemove(this, WorkflowPackage.SWIMLANE__MANAGE, Swimlane.class, msgs);
			if (newSwimlane != null)
				msgs = ((InternalEObject)newSwimlane).eInverseAdd(this, WorkflowPackage.SWIMLANE__MANAGE, Swimlane.class, msgs);
			msgs = basicSetSwimlane(newSwimlane, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.TASK__SWIMLANE, newSwimlane, newSwimlane));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Assignment> getAssignment() {
		if (assignment == null) {
			assignment = new EObjectContainmentEList<Assignment>(Assignment.class, this, WorkflowPackage.TASK__ASSIGNMENT);
		}
		return assignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Event> getEvent() {
		if (event == null) {
			event = new EObjectContainmentEList<Event>(Event.class, this, WorkflowPackage.TASK__EVENT);
		}
		return event;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Timer> getTimer() {
		if (timer == null) {
			timer = new EObjectContainmentEList<Timer>(Timer.class, this, WorkflowPackage.TASK__TIMER);
		}
		return timer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case WorkflowPackage.TASK__SWIMLANE:
				if (swimlane != null)
					msgs = ((InternalEObject)swimlane).eInverseRemove(this, WorkflowPackage.SWIMLANE__MANAGE, Swimlane.class, msgs);
				return basicSetSwimlane((Swimlane)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case WorkflowPackage.TASK__SWIMLANE:
				return basicSetSwimlane(null, msgs);
			case WorkflowPackage.TASK__ASSIGNMENT:
				return ((InternalEList<?>)getAssignment()).basicRemove(otherEnd, msgs);
			case WorkflowPackage.TASK__EVENT:
				return ((InternalEList<?>)getEvent()).basicRemove(otherEnd, msgs);
			case WorkflowPackage.TASK__TIMER:
				return ((InternalEList<?>)getTimer()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case WorkflowPackage.TASK__NAME:
				return getName();
			case WorkflowPackage.TASK__SWIMLANE:
				if (resolve) return getSwimlane();
				return basicGetSwimlane();
			case WorkflowPackage.TASK__ASSIGNMENT:
				return getAssignment();
			case WorkflowPackage.TASK__EVENT:
				return getEvent();
			case WorkflowPackage.TASK__TIMER:
				return getTimer();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case WorkflowPackage.TASK__NAME:
				setName((String)newValue);
				return;
			case WorkflowPackage.TASK__SWIMLANE:
				setSwimlane((Swimlane)newValue);
				return;
			case WorkflowPackage.TASK__ASSIGNMENT:
				getAssignment().clear();
				getAssignment().addAll((Collection<? extends Assignment>)newValue);
				return;
			case WorkflowPackage.TASK__EVENT:
				getEvent().clear();
				getEvent().addAll((Collection<? extends Event>)newValue);
				return;
			case WorkflowPackage.TASK__TIMER:
				getTimer().clear();
				getTimer().addAll((Collection<? extends Timer>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case WorkflowPackage.TASK__NAME:
				setName(NAME_EDEFAULT);
				return;
			case WorkflowPackage.TASK__SWIMLANE:
				setSwimlane((Swimlane)null);
				return;
			case WorkflowPackage.TASK__ASSIGNMENT:
				getAssignment().clear();
				return;
			case WorkflowPackage.TASK__EVENT:
				getEvent().clear();
				return;
			case WorkflowPackage.TASK__TIMER:
				getTimer().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case WorkflowPackage.TASK__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case WorkflowPackage.TASK__SWIMLANE:
				return swimlane != null;
			case WorkflowPackage.TASK__ASSIGNMENT:
				return assignment != null && !assignment.isEmpty();
			case WorkflowPackage.TASK__EVENT:
				return event != null && !event.isEmpty();
			case WorkflowPackage.TASK__TIMER:
				return timer != null && !timer.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //TaskImpl
