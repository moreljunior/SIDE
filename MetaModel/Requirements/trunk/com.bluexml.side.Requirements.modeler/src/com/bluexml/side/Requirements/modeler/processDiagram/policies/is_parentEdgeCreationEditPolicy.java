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
/*******************************************************************************
 * No CopyrightText Defined in the configurator file.
 ******************************************************************************/
package com.bluexml.side.Requirements.modeler.processDiagram.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.commands.Command;
import org.topcased.modeler.commands.CreateTypedEdgeCommand;
import org.topcased.modeler.di.model.GraphEdge;
import org.topcased.modeler.di.model.GraphElement;
import org.topcased.modeler.di.model.SimpleSemanticModelElement;
import org.topcased.modeler.edit.policies.AbstractEdgeCreationEditPolicy;
import org.topcased.modeler.utils.SourceTargetData;
import org.topcased.modeler.utils.Utils;

import com.bluexml.side.Requirements.modeler.processDiagram.ProSimpleObjectConstants;
import com.bluexml.side.Requirements.modeler.processDiagram.commands.is_parentEdgeCreationCommand;

/**
 * is_parent edge creation
 *
 * @generated
 */
public class is_parentEdgeCreationEditPolicy extends
		AbstractEdgeCreationEditPolicy {
	/**
	 * @see org.topcased.modeler.edit.policies.AbstractEdgeCreationEditPolicy#createCommand(org.eclipse.gef.EditDomain, org.topcased.modeler.di.model.GraphEdge, org.topcased.modeler.di.model.GraphElement)
	 * @generated
	 */
	protected CreateTypedEdgeCommand createCommand(EditDomain domain,
			GraphEdge edge, GraphElement source) {
		return new is_parentEdgeCreationCommand(domain, edge, source);
	}

	/**
	 * @see org.topcased.modeler.edit.policies.AbstractEdgeCreationEditPolicy#checkEdge(org.topcased.modeler.di.model.GraphEdge)
	 * @generated
	 */
	protected boolean checkEdge(GraphEdge edge) {
		if (edge.getSemanticModel() instanceof SimpleSemanticModelElement) {
			return ProSimpleObjectConstants.SIMPLE_OBJECT_IS_PARENT
					.equals(((SimpleSemanticModelElement) edge
							.getSemanticModel()).getTypeInfo());
		}
		return false;
	}

	/**
	 * @see org.topcased.modeler.edit.policies.AbstractEdgeCreationEditPolicy#checkSource(org.topcased.modeler.di.model.GraphElement)
	 * @generated
	 */
	protected boolean checkSource(GraphElement source) {
		EObject object = Utils.getElement(source);
		if (object instanceof com.bluexml.side.requirements.Entity) {
			return true;
		}
		return false;
	}

	/**
	 * @see org.topcased.modeler.edit.policies.AbstractEdgeCreationEditPolicy#checkTargetForSource(org.topcased.modeler.di.model.GraphElement, org.topcased.modeler.di.model.GraphElement)
	 * @generated
	 */
	protected boolean checkTargetForSource(GraphElement source,
			GraphElement target) {
		EObject sourceObject = Utils.getElement(source);
		EObject targetObject = Utils.getElement(target);

		if (sourceObject instanceof com.bluexml.side.requirements.Entity
				&& targetObject instanceof com.bluexml.side.requirements.Entity) {
			if (!sourceObject.equals(targetObject)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.topcased.modeler.edit.policies.AbstractEdgeCreationEditPolicy#checkCommand(org.eclipse.gef.commands.Command)
	 * @generated
	 */
	protected boolean checkCommand(Command command) {
		return command instanceof is_parentEdgeCreationCommand;
	}

	/**
	 * @see org.topcased.modeler.edit.policies.AbstractEdgeCreationEditPolicy#getSourceTargetData(org.topcased.modeler.di.model.GraphElement, org.topcased.modeler.di.model.GraphElement)
	 * @generated
	 */
	protected SourceTargetData getSourceTargetData(GraphElement source,
			GraphElement target) {
		EObject sourceObject = Utils.getElement(source);
		EObject targetObject = Utils.getElement(target);

		if (sourceObject instanceof com.bluexml.side.requirements.Entity
				&& targetObject instanceof com.bluexml.side.requirements.Entity) {
			return new SourceTargetData(false, false, SourceTargetData.NONE,
					null, null, null, null, null, "parent", null, null);
		}
		return null;
	}

}
