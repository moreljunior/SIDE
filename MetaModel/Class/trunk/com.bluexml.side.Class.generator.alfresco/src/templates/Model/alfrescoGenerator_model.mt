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
<%--
Copyright (C) BlueXML 2005-2008

This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Boston, MA 02111.
 --%>
<%
metamodel http://www.kerblue.org/class/1.0
import templates.servicesTemplates.Common
import templates.servicesTemplates.Attribute
import templates.servicesTemplates.Association
import templates.Model.alfrescoGenerator_model_aspects
import templates.Model.alfrescoGenerator_model_classes
import templates.Model.alfrescoGenerator_model_imports
import templates.Model.alfrescoGenerator_model_NS
import templates.Model.alfrescoGenerator_model_constraints
import templates.Model.alfrescoGenerator_model_dataTypes
import com.bluexml.side.clazz.generator.alfresco.ClassAlfrescoGenerator
import com.bluexml.side.clazz.service.alfresco.ClassServices
import com.bluexml.side.clazz.service.alfresco.CommonServices
import com.bluexml.side.clazz.service.alfresco.AttributeServices
import com.bluexml.side.clazz.service.alfresco.AssociationServices
%>


<%script type="clazz.Model" name="validatedFilename"%>
<%if (eContainer() == null) {%><%getConfModulePath()%>/model/<%name%>Model.xml<%}%>
<%script type="clazz.Model" name="generator" file="<%validatedFilename%>"%>
<?xml version='1.0' encoding='iso-8859-1'?>

<model name="<%name%>:model" xmlns="http://www.alfresco.org/model/dictionary/1.0" xmlns:xi="http://www.w3.org/2001/XInclude">
	<!-- Definition of the model -->

	<description><%name%></description>
	<author>Generated by SIDE (http://www.bluexml.com)</author>
	<version>1.0</version>
	
	<%model_imports%>
	
	<%model_ns%>
	
	<data-types>
		<%for (customDataTypeSet){%>
		<%alfrescoGeneratorModelDataType%>
		<%}%>
	</data-types>
	
	<%model_constraints%>
	
	<types>
		<%alfrescoGenerator_Classes()%>		
	</types>
	<aspects>
		<%alfrescoGenerator_model_aspects()%>		
	</aspects>
	
</model>
