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
import com.bluexml.side.portal.generator.alfresco.templates.services.ClazzService
import com.bluexml.side.clazz.service.alfresco.CommonServices
import com.bluexml.side.portal.generator.alfresco40d.templates.upload.upload-config-lib
%>
<%script type="Portal" name="validatedFilename" post="trim()" %>
<%getProperty("alf.share.paths.web-ext.alf.components")%>/upload/html-upload.get_ja.properties
<%script type="Portal" name="generator" file="<%validatedFilename%>"%>
header.singleUpload=\u30d5\u30a1\u30a4\u30eb\u306e\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9
header.singleUpdate=\u30d5\u30a1\u30a4\u30eb\u306e\u66f4\u65b0

label.singleUploadTip=\u6700\u65b0\u306eFlash Player\u3092\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3059\u308b\u3068\u3001\u8907\u6570\u306e\u30d5\u30a1\u30a4\u30eb\u3092\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9\u3067\u304d\u307e\u3059\u3002 \
<a href="http://www.adobe.com/go/getflashplayer">Adobe Flash Player\u30c0\u30a6\u30f3\u30ed\u30fc\u30c9\u30bb\u30f3\u30bf\u30fc</a>\u304b\u3089\u30c0\u30a6\u30f3\u30ed\u30fc\u30c9\u3057\u3066\u304f\u3060\u3055\u3044\u3002

label.singleUpdateTip=\u65b0\u3057\u3044\u30d0\u30fc\u30b8\u30e7\u30f3\u306e {0} \u3092\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9
button.upload=\u30d5\u30a1\u30a4\u30eb\u306e\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9

section.file=\u30d5\u30a1\u30a4\u30eb\u306e\u9078\u629e
label.file=\u30d5\u30a1\u30a4\u30eb

section.version=\u30d0\u30fc\u30b8\u30e7\u30f3\u60c5\u5831
label.version=\u3053\u306e\u30d0\u30fc\u30b8\u30e7\u30f3\u306e\u6539\u8a02\u30ec\u30d9\u30eb:
label.minorVersion=\u5c0f\u3055\u306a\u5909\u66f4
label.majorVersion=\u5927\u304d\u306a\u5909\u66f4
label.minorVersion.more=\u5c0f\u3055\u306a\u5909\u66f4\uff08{0}\uff09
label.majorVersion.more=\u5927\u304d\u306a\u5909\u66f4\uff08{0}\uff09
label.comments=\u30b3\u30e1\u30f3\u30c8

message.uploading=\u6587\u66f8\u3092\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9\u3057\u3066\u3044\u307e\u3059...
message.success=\u6587\u66f8\u304c\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9\u3055\u308c\u307e\u3057\u305f
message.failure=\u6587\u66f8\u3092\u30a2\u30c3\u30d7\u30ed\u30fc\u30c9\u3067\u304d\u307e\u305b\u3093\u3067\u3057\u305f
message.failure.413=\u30af\u30a9\u30fc\u30bf\u3092\u8d85\u3048\u3066\u3044\u307e\u3059
message.illegalCharacters=\u30d5\u30a1\u30a4\u30eb\u540d\u306b\u3001\u30aa\u30da\u30ec\u30fc\u30b7\u30e7\u30f3\u30b7\u30b9\u30c6\u30e0\u3067\u4f7f\u7528\u3067\u304d\u306a\u3044\u6587\u5b57\u3092\u542b\u3081\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002

type.cm_content=\u30b3\u30f3\u30c6\u30f3\u30c4
<%i18nGenerator%>
