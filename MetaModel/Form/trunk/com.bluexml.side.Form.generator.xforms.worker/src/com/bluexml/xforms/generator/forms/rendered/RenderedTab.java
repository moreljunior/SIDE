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
package com.bluexml.xforms.generator.forms.rendered;

import org.jdom.Element;

import com.bluexml.xforms.generator.forms.Renderable;
import com.bluexml.xforms.generator.forms.Rendered;
import com.bluexml.xforms.generator.forms.XFormsGenerator;

/**
 * The Class RenderedTab.
 */
public class RenderedTab extends Rendered {

	/**
	 * Instantiates a new rendered tab.
	 * 
	 * @param title
	 *            the title
	 * @param showTab
	 *            the show tab
	 */
	public RenderedTab(String title, boolean showTab) {
		if (showTab) {
			xformsElement = XFormsGenerator.createElement("div", XFormsGenerator.NAMESPACE_XHTML);
			xformsElement.setAttribute("dojoType", "ContentPane");
			xformsElement.setAttribute("label", title);
		} else {
			xformsElement = XFormsGenerator.createElementWithLabel("group",
					XFormsGenerator.NAMESPACE_XFORMS, title);
		}
		xformsElement.setAttribute("style", "height: 100%");
		xformsElement.setAttribute("id", XFormsGenerator.getId("contentPaneTab"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluexml.xforms.generator.forms.Rendered#isHolder()
	 */
	@Override
	public boolean isHolder() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bluexml.xforms.generator.forms.Rendered#addRendered(com.bluexml.xforms.generator.forms
	 * .Rendered, com.bluexml.xforms.generator.forms.Renderable)
	 */
	@Override
	public void addRendered(Rendered rendered, Renderable renderable) {
		super.addRendered(rendered, renderable);
		Element renderedElement = rendered.getXformsElement();
		if (renderedElement != null) {
			xformsElement.addContent(renderedElement);
		}
	}

}
