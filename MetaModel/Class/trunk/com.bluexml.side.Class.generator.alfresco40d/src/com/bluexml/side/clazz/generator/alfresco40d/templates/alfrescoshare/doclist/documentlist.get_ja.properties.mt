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
metamodel http://www.kerblue.org/class/1.0

import templates.alfrescoshare.uploadForm.file-upload-js-get-lib
import templates.servicesTemplates.Common
import com.bluexml.side.clazz.service.alfresco.ClassServices
%>

<%--
  -- This file is a patched copy of Alfresco Share's
  -- Contains additional message for custom Types 
  --%>
  
<%script type="clazz.ClassPackage" name="fileName"%>
<%if (eContainer() == null) {%><%getProperty("alf.share.paths.core.site-webscripts")%>org/alfresco/components/documentlibrary/documentlist.get_ja.properties<%}%>

<%script type="clazz.ClassPackage" name="messages" file="<%fileName%>"%>
## Buttons
button.sort.ascending=\u6607\u9806\u306b\u4e26\u3079\u66ff\u3048
button.sort.descending=\u964d\u9806\u306b\u4e26\u3079\u66ff\u3048
button.folders.show=\u30d5\u30a9\u30eb\u30c0\u306e\u8868\u793a
button.folders.hide=\u30d5\u30a9\u30eb\u30c0\u306e\u975e\u8868\u793a

## Drop-down Menus
menu.select=\u9078\u629e
menu.select.all=\u3059\u3079\u3066
menu.select.none=\u9078\u629e\u89e3\u9664
menu.select.invert=\u9078\u629e\u306e\u53cd\u8ee2
menu.select.folders=\u30d5\u30a9\u30eb\u30c0
menu.select.documents=\u6587\u66f8

## Details Banners
details.banner.editing=\u3053\u306e\u6587\u66f8\u306f\u3001\u3042\u306a\u305f\u304c\u30aa\u30d5\u30e9\u30a4\u30f3\u7de8\u96c6\u7528\u306b\u30ed\u30c3\u30af\u3057\u3066\u3044\u307e\u3059\u3002
details.banner.lock-owner=\u3053\u306e\u6587\u66f8\u306f\u3042\u306a\u305f\u304c\u30ed\u30c3\u30af\u3057\u3066\u3044\u307e\u3059\u3002
details.banner.locked=\u3053\u306e\u6587\u66f8\u306f\u3001{0} \u304c\u7de8\u96c6\u7528\u306b\u30ed\u30c3\u30af\u3057\u3066\u3044\u307e\u3059\u3002
details.banner.google-docs.link=Google Docs
details.banner.google-docs-owner=\u3053\u306e\u6587\u66f8\u306f\u3001\u3042\u306a\u305f\u304c\u7de8\u96c6\u7528\u306b {0} \u3078\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9\u3057\u307e\u3057\u305f\u3002
details.banner.google-docs-locked=\u3053\u306e\u6587\u66f8\u306f\u3001{0} \u304c\u7de8\u96c6\u7528\u306b {1} \u3078\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9\u3057\u307e\u3057\u305f\u3002

## Actions - moved to slingshot.properties
actions.more=\u305d\u306e\u4ed6\u306e\u30a2\u30af\u30b7\u30e7\u30f3...

## Status Indicators
status.active-workflows=\u30ef\u30fc\u30af\u30d5\u30ed\u30fc {0} \u306b\u5c5e\u3057\u3066\u3044\u307e\u3059
status.editing=\u3042\u306a\u305f\u304c\u7de8\u96c6\u4e2d\u3067\u3059
status.google-docs-editing=\u3042\u306a\u305f\u304cGoogle Docs&trade;\u3067\u7de8\u96c6\u4e2d\u3067\u3059
status.google-docs-owner=Google Docs&trade;\u7de8\u96c6\u7528\u306b\u3042\u306a\u305f\u304c\u30ed\u30c3\u30af\u3057\u3066\u3044\u307e\u3059
status.google-docs-locked=Google Docs&trade;\u7de8\u96c6\u7528\u306b {0}\uff08{1}\uff09\u304c\u30ed\u30c3\u30af\u3057\u3066\u3044\u307e\u3059
status.locked={0}\uff08{1}\uff09\u304c\u30ed\u30c3\u30af\u3057\u3066\u3044\u307e\u3059
status.lock-owner=\u3042\u306a\u305f\u304c\u30ed\u30c3\u30af\u3057\u3066\u3044\u307e\u3059
status.rules=\u30d5\u30a9\u30eb\u30c0\u306b\u306f\u9069\u7528\u3055\u308c\u305f\u30eb\u30fc\u30eb\u304c\u3042\u308a\u307e\u3059
status.simple-workflow=\u627f\u8a8d/\u5374\u4e0b\u306e\u7c21\u6613\u30ef\u30fc\u30af\u30d5\u30ed\u30fc\u304c\u9069\u7528\u3055\u308c\u3066\u3044\u307e\u3059
status.exif=EXIF\u30e1\u30bf\u30c7\u30fc\u30bf\u304c\u5229\u7528\u53ef\u80fd\u3067\u3059
status.geographic=\u4f4d\u7f6e\u60c5\u5831\u30e1\u30bf\u30c7\u30fc\u30bf\u304c\u5229\u7528\u53ef\u80fd\u3067\u3059
status.transferred-node=\u5225\u30ea\u30dd\u30b8\u30c8\u30ea\u304b\u3089\u8ee2\u9001\u3055\u308c\u307e\u3057\u305f

## Tips
tip.insitu-rename=\u540d\u524d\u306e\u5909\u66f4
tip.insitu-tag=\u30bf\u30b0

## File Upload (upload new version)
label.filter-description={0} \u3068\u540c\u3058\u30bf\u30a4\u30d7

## Edit Details Dialog
edit-details.title=\u30d7\u30ed\u30d1\u30c6\u30a3\u3092\u7de8\u96c6: {0}
edit-details.label.edit-metadata=\u3059\u3079\u3066\u306e\u30d7\u30ed\u30d1\u30c6\u30a3...

## Help panels, including Drag and Drop
no.items.title=\u30b3\u30f3\u30c6\u30f3\u30c4\u30a2\u30a4\u30c6\u30e0\u306a\u3057
show.folders={0}\u500b\u306e\u30b5\u30d6\u30d5\u30a9\u30eb\u30c0\u3092\u8868\u793a\u3059\u308b
standard.upload.title=\u30e9\u30a4\u30d6\u30e9\u30ea\u3092\u30d3\u30eb\u30c9\u3059\u308b
standard.upload.description=\u30d5\u30a1\u30a4\u30eb\u3092\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9\u3059\u308b
dnd.drop.title=\u30c9\u30e9\u30c3\u30b0&\u30c9\u30ed\u30c3\u30d7\u3067\u30d5\u30a1\u30a4\u30eb\u3092\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9\u3059\u308b
dnd.drop.doclist.description=\u30c7\u30b9\u30af\u30c8\u30c3\u30d7\u304b\u3089\u30d5\u30a1\u30a4\u30eb\u3092\u30c9\u30e9\u30c3\u30b0\u3057\u3066\u3053\u3053\u306b\u30c9\u30ed\u30c3\u30d7\u3059\u308b\u3068\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9\u3055\u308c\u307e\u3059\u3002
dnd.drop.folder.description=\u30d5\u30a1\u30a4\u30eb\u3092\u30d5\u30a9\u30eb\u30c0\u306b\u30c9\u30ed\u30c3\u30d7\u3067\u304d\u307e\u3059\u3002
dnd.upload.description=\u30d5\u30a1\u30a4\u30eb\u3092\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9\u3059\u308b
dnd.newfolder.description=\u30d5\u30a9\u30eb\u30c0\u3092\u4f5c\u6210\u3059\u308b
other.options=\u307e\u305f\u3001\u4ee5\u4e0b\u3082\u5b9f\u884c\u3067\u304d\u307e\u3059\u3002
dnd.upload.tooltip=<p>\u30c7\u30b9\u30af\u30c8\u30c3\u30d7\u304b\u3089\u30d5\u30a1\u30a4\u30eb\u3092\u30c9\u30e9\u30c3\u30b0\u3057\u3066\u30c9\u30ad\u30e5\u30e1\u30f3\u30c8\u30e9\u30a4\u30d6\u30e9\u30ea\u306b\u30c9\u30ed\u30c3\u30d7\u3059\u308b\u3053\u3068\u3082\u3067\u304d\u307e\u3059\u3002</p>
<p>\u30c9\u30e9\u30c3\u30b0&\u30c9\u30ed\u30c3\u30d7\u3092\u884c\u3046\u306b\u306f\u3001\u4ee5\u4e0b\u306e\u30a2\u30c3\u30d7\u30ed\u30fc\u30c0\u3092\u9589\u3058\u3066\u304f\u3060\u3055\u3044\u3002</p>


## SIDE view labels
<%for (getAllReferencedAttributes()){%>
<%getPrefixedQualifiedName().replaceAll(":", "_")%>=<%getLabel()%>
<%}%>
