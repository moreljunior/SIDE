/*******************************************************************************
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
package com.bluexml.side.Workflow.modeler.diagram.figures;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.swt.graphics.Image;
import org.topcased.draw2d.figures.ILabel;
import org.topcased.draw2d.figures.ILabelFigure;

/**
 * @_generated
 */
public class SwimlaneFigure extends ImageWithLabelFigure implements ILabelFigure {
	/**
	 * Constructor
	 *
	 * @_generated
	 */
	public SwimlaneFigure() {
		super(new Image(null, SwimlaneFigure.class
				.getResourceAsStream("img/agent.png")), PositionConstants.TOP);
	}

	public ILabel getLabel() {
		return label;
	}

}