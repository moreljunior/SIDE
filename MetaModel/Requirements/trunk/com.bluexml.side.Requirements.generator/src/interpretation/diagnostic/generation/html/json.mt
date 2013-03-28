<%--
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
--%>
<%
metamodel http://www.bluexml.com/rwm/diagnostic/1.0/
import com.bluexml.side.requirements.generator.services.StringEscapeUtilsService
%>
<%script type="Diagnostic.Diagnostic" name="Problem" file="webtool/data/analysis/problem.json"%>
{
	"diagnostic":[
      <%for (problem){%>
      	{
      		"type":"<%elementType%>",
      		"name":"<%if (elementName != null) {%><%escapeHtml(elementName.replaceAll("\n"," "))%><%}%>",
      		"severity":"<%severity.toString()%>",
      		"description":"<%if (description != null) {%><%escapeHtml(description.replaceAll("\n"," "))%><%}%>"
        }<%if (current() != current("Diagnostic").problem.nLast()){%>,<%}%>
      <%}%>
	]
}
