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
package com.bluexml.side.util.security;

/**
 * An interface to check if the component can be activate
 * 
 * @author <a href="mailto:pbertrand@bluexml.com">Pierre BERTRAND</a>
 */
public interface Checkable {
	/**
	 * This method check if the user have the license to use this component.
	 * 
	 * @return true if the component can be used.
	 */
	public abstract boolean check();
	
	public abstract boolean checkOption(String optionID);
	
	public abstract String getComponentKey();
}
