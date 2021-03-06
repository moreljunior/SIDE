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
<%if (eContainer() == null) {%><%getProperty("alf.share.paths.core.site-webscripts")%>org/alfresco/components/documentlibrary/documentlist.get_de.properties<%}%>

<%script type="clazz.ClassPackage" name="messages" file="<%fileName%>"%>
## Buttons
button.sort.ascending=Aufsteigend sortieren
button.sort.descending=Absteigend sortieren
button.folders.show=Ordner anzeigen
button.folders.hide=Ordner ausblenden

## Drop-down Menus
menu.select=Ausw\u00e4hlen
menu.select.all=Alle
menu.select.none=Keine
menu.select.invert=Auswahl umkehren
menu.select.folders=Ordner
menu.select.documents=Dokumente

## Details Banners
details.banner.editing=Dieses Dokument ist von Ihnen f\u00fcr das Offline-Editieren gesperrt.
details.banner.lock-owner=Dieses Dokument ist von Ihnen gesperrt.
details.banner.locked=Dieses Dokument ist von {0} f\u00fcr die Bearbeitung gesperrt.
details.banner.google-docs.link=Google Text & Tabellen
details.banner.google-docs-owner=Dieses Dokument wurde von Ihnen nach {0} zum Bearbeiten hochgeladen.
details.banner.google-docs-locked=Dieses Dokument wurde von {0} nach {1} zum Bearbeiten hochgeladen.

## Actions - moved to slingshot.properties
actions.more=Mehr...

## Status Indicators
status.active-workflows=Geh\u00f6rt zu {0} aktiven Workflows
status.editing=Wird von Ihnen bearbeitet
status.google-docs-editing=Wird von Ihnen \u00fcber Google Text & Tabellen&trade; bearbeitet
status.google-docs-owner=Von Ihnen zur Bearbeitung in Google Text & Tabellen&trade; gesperrt
status.google-docs-locked=Von {0} ({1}) zur Bearbeitung in Google Text & Tabellen&trade; gesperrt
status.locked=Gesperrt von {0} ({1})
status.lock-owner=Von Ihnen gesperrt
status.rules=Diesem Ordner sind Regeln zugeordnet
status.simple-workflow=Einfacher Workflow zum Annehmen/Ablehnen angewandt
status.exif=EXIF-Metadaten verf\u00fcgbar
status.geographic=Geolocation-Metadaten verf\u00fcgbar
status.transferred-node=Aus anderem Repository \u00fcbertragen

## Tips
tip.insitu-rename=Umbenennen
tip.insitu-tag=Tag

## File Upload (upload new version)
label.filter-description=Gleicher Typ wie {0}

## Edit Details Dialog
edit-details.title=Eigenschaften bearbeiten: {0}
edit-details.label.edit-metadata=Alle Eigenschaften...

## Help panels, including Drag and Drop
no.items.title=Keine Inhaltselemente
show.folders={0} Unterordner anzeigen
standard.upload.title=Erstellen Sie eine eigene Bibliothek!
standard.upload.description=Dateien hochladen
dnd.drop.title=Datei-Upload per Drag-and-Drop!
dnd.drop.doclist.description=Ziehen Sie Dateien von Ihrem Desktop und legen Sie sie hier ab, um sie hochzuladen.
dnd.drop.folder.description=Sie k\u00f6nnen Dateien auf Ordnern ablegen.
dnd.upload.description=Dateien hochladen
dnd.newfolder.description=Ordner erstellen
other.options=Au\u00dferdem k\u00f6nnen Sie...
dnd.upload.tooltip=<p>Sie k\u00f6nnen Dateien auch per Drag-and-Drop von Ihrem Desktop in die Dokumentenbibliothek ziehen.</p>
<p>Dazu m\u00fcssen Sie den Uploader unten schlie\u00dfen.</p>


## SIDE view labels
<%for (getAllReferencedAttributes()){%>
<%getPrefixedQualifiedName().replaceAll(":", "_")%>=<%getLabel()%>
<%}%>
