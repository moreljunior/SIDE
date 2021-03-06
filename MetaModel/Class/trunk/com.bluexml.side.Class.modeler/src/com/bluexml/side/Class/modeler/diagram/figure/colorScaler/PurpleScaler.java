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
package com.bluexml.side.Class.modeler.diagram.figure.colorScaler;

import org.eclipse.swt.graphics.Color;

public class PurpleScaler implements ColorScale {
	private static int COLOR_MIN = 170;
	private static int COLOR_MAX = 220;

	public Color getColor(int index, int count) {
		if (index == 1)
			return new Color(null, 255, COLOR_MIN, 255);
		else if (index == count)
			return new Color(null, 255, COLOR_MAX, 255);
		else {
			int i = COLOR_MIN + (COLOR_MAX - COLOR_MIN) * index / count;
			return new Color(null, 255, i, 255);
		}
	}
}
