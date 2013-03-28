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
package com.bluexml.side.util.componentmonitor.headLessinterface;
import org.eclipse.swt.graphics.Point;

import com.bluexml.side.util.componentmonitor.guiAdapter.AdaptedRunable.WidgetNotAvailable;
public interface ProgressBarInterface {

	public Point computeSize(int wHint, int hHint, boolean changed) throws WidgetNotAvailable;

	public int getMaximum() throws WidgetNotAvailable;

	public int getMinimum() throws WidgetNotAvailable;

	public int getSelection() throws WidgetNotAvailable;

	public int getState() throws WidgetNotAvailable;

	public void setMaximum(int value);

	public void setMinimum(int value);

	public void setSelection(int selection);

	public void setState(int state);
}
