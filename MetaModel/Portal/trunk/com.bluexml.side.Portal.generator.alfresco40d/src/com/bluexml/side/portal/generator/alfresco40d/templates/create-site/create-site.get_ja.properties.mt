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
metamodel http://www.kerblue.org/portal/1.0
import com.bluexml.side.portal.generator.alfresco.service.ShareGeneratorServices
%>

<%-- Templates creation --%>
<%script type="Portal" name="createMessages"%>
<%if (eContainer() == null) {%>
<%getProperty("alf.share.paths.web-ext.alf.modules")%>/create-site.get_ja.properties
<%}%>
<%script type="Portal" name="alfrescoGenerator" file="<%createMessages%>"%>
header.createSite=\u30b5\u30a4\u30c8\u306e\u4f5c\u6210
label.shortName=URL\u540d
label.shortNameHelp=URL\u540d\u306f\u6b21\u306e\u3088\u3046\u306bURL\u306b\u633f\u5165\u3055\u308c\u308b\u305f\u3081\u3001\u30b9\u30da\u30fc\u30b9\u3084\u7279\u6b8a\u6587\u5b57\u306f\u4f7f\u7528\u3057\u306a\u3044\u3067\u304f\u3060\u3055\u3044\u3002<br/>\
 http://domain.com/share/page/site/&lt;URL Name&gt;/dashboard<br/>\
 
label.access=\u516c\u958b\u30ec\u30d9\u30eb
label.isPublic=\u516c\u958b
label.isModerated=\u6761\u4ef6\u4ed8\u304d\u516c\u958b\u30b5\u30a4\u30c8\u306e\u53c2\u52a0\u8005
label.moderatedHelp=\u30b5\u30a4\u30c8\u30de\u30cd\u30fc\u30b8\u30e3\u306f\u30b5\u30a4\u30c8\u306e\u30e1\u30f3\u30d0\u30fc\u3092\u30b3\u30f3\u30c8\u30ed\u30fc\u30eb\u3067\u304d\u307e\u3059\u3002
label.isPrivate=\u975e\u516c\u958b
label.type=\u30bf\u30a4\u30d7
message.failure=\u30b5\u30a4\u30c8\u3092\u4f5c\u6210\u3067\u304d\u307e\u305b\u3093\u3067\u3057\u305f\u3002
message.creating=\u30b5\u30a4\u30c8\u3092\u4f5c\u6210\u3057\u3066\u3044\u307e\u3059...
error.duplicateShortName=URL\u304c\u3059\u3067\u306b\u4f7f\u7528\u3055\u308c\u3066\u3044\u308b\u305f\u3081\u3001\u30b5\u30a4\u30c8\u3092\u4f5c\u6210\u3067\u304d\u307e\u305b\u3093\u3067\u3057\u305f\u3002
error.loggedOut=\u30e6\u30fc\u30b6\u30fc\u30bb\u30c3\u30b7\u30e7\u30f3\u304c\u30bf\u30a4\u30e0\u30a2\u30a6\u30c8\u3057\u3066\u3044\u307e\u3059\u3002\u30ed\u30b0\u30a4\u30f3\u3057\u3066\u304b\u3089\u3001\u3084\u308a\u76f4\u3057\u3066\u304f\u3060\u3055\u3044\u3002
title.collaborationSite=\u30b3\u30e9\u30dc\u30ec\u30fc\u30b7\u30e7\u30f3\u30b5\u30a4\u30c8
#SIDE generated sites
title.site-<%name%>Site=<%name%>-Site
