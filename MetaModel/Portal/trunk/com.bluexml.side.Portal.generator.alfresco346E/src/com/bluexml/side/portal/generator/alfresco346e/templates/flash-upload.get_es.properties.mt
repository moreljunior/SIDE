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
%>
<%script type="Portal" name="validatedFilename" post="trim()" %>
<%getProperty("alf.share.paths.web-ext.alf.components")%>/upload/flash-upload.get_es.properties
<%script type="Portal" name="generator" file="<%validatedFilename%>"%>
header.singleUpload=A\u00f1adir fichero
header.multiUpload=A\u00f1adir fichero(s)
header.singleUpdate=Actualizar fichero

label.browse=Seleccione el fichero(s) a a\u00f1adir
label.multiUploadTip=Utilice CTRL o SHIFT para seleccionar m\u00faltiples ficheros
label.singleUpdateTip=Pulse en el icono para subir una nueva versi\u00f3n de {0}
label.noFiles=Ning\u00fan fichero a mostrar, pulse el icono para seleccionar los ficheros que desea subir
label.noFlash=Necesita como m\u00ednimo la versi\u00f3n 9.0.45 de Flash Player para utilizar este componente. Puede descargar la \u00faltima versi\u00f3n del visor Flash desde el \
  <a href="http://www.adobe.com/go/getflashplayer">Centro de descargas de Adobe Flash Player</a>.
label.success=Completado
label.failure=Error
label.failure.413=Error: Cuota excedida
label.illegalChars=Error: El nombre del fichero contiene caracteres no admitidos
button.upload=A\u00f1adir fichero(s)
label.uploadStatus=Estado: {0}/{1} subido(s) ({2} err\u00f3neos)
message.cancelStatus=Subida(s) cancelada(s); {0} fichero(s) subido(s)
message.zeroByteFileSelected=Ha seleccionado el fichero {0}. No tiene ning\u00fan contenido y no se puede subir.
message.flashError.title=Error de comunicaci\u00f3n Flash
message.flashError.message=El navegador no puede comunicarse con el cargador Flash. Puede continuar subiendo en modo de un \u00fanico fichero, o recargar la p\u00e1gina para reiniciar Flash.

section.version=Informaci\u00f3n de la versi\u00f3n
label.version=Esta versi\u00f3n tiene:
label.minorVersion=cambios menores
label.majorVersion=cambios mayores
label.minorVersion.more=cambios menores ({0})
label.majorVersion.more=cambios mayores ({0})
label.comments=Comentarios

type.cm_content=Contenido
<%for (pageSet[ID.toLowerCase().trim() == "documentlibrary"].portlets.associationPortlet[name.toLowerCase().trim() == "uploadabletypes" && isPortletInternal != null]){%>
<%for (isPortletInternal.form.forms){%>
type.<%filter("form.ClassReference").real_class.getPrefixedQName("_")%><%filter("form.ClassReference").real_class.getLabel()%>
<%}%>
<%}%>
