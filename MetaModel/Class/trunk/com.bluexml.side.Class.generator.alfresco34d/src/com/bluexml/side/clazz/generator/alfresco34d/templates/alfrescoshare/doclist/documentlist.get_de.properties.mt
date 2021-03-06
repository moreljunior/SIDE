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
%>

<%--
  -- This file is a patched copy of Alfresco Share's
  -- Contains additional message for custom Types 
  --%>
  
<%script type="clazz.ClassPackage" name="fileName"%>
<%if (eContainer() == null) {%><%getProperty("alf.share.paths.core.site-webscripts")%>org/alfresco/components/documentlibrary/documentlist.get_de.properties<%}%>

<%script type="clazz.ClassPackage" name="messages" file="<%fileName%>"%>
## Buttons
button.folders.show=Ordner anzeigen
button.folders.hide=Ordner ausblenden
button.view.simple=Einfache Ansicht
button.view.detailed=Detaillierte Ansicht

## Drop-down Menus
menu.select=Ausw\u00e4hlen
menu.select.all=Alle
menu.select.none=Keine 
menu.select.invert=Auswahl umkehren
menu.select.folders=Ordner
menu.select.documents=Dokumente

## Document Details
details.link-to=Link zu: {0}
details.created.on=Erstellt am:
details.created.by=Erstellt von:
details.modified.on=Ge\u00e4ndert am:
details.modified.by=Ge\u00e4ndert von:
details.editing-started.on=Bearbeitung gestartet am:
details.editing-started.by=Bearbeitung gestartet von:
details.by=Von:
details.version=Version:
details.size=Gr\u00f6\u00dfe:
details.description=Beschreibung:
details.description.none=(Kein)
details.comments=Kommentare:
details.tags=Tags:
details.tags.none=(Kein)

## Details Banners
details.banner.editing=Dieses Dokument ist von Ihnen f\u00fcr das Offline-Editieren gesperrt.
details.banner.lock-owner=Dieses Dokument ist von Ihnen gesperrt.
details.banner.locked=Dieses Dokument ist von {0} f\u00fcr die Bearbeitung gesperrt.
details.banner.google-docs.link=Google Docs
details.banner.google-docs-owner=Dieses Dokument wurde von Ihnen nach {0} zum Bearbeiten hochgeladen.
details.banner.google-docs-locked=Dieses Dokument wurde von {0} nach {1} zum Bearbeiten hochgeladen.

## Actions - moved to slingshot.properties
actions.more=Mehr...

## Tips
tip.active-workflow=Befindet sich in {0} aktiven Workflows
tip.editing=Wird von Ihnen bearbeitet
tip.favourite-document.add=Dokument zu Favoriten hinzuf\u00fcgen
tip.favourite-document.remove=Dokument aus Favoriten entfernen
tip.favourite-folder.add=Ordner zu Favoriten hinzuf\u00fcgen
tip.favourite-folder.remove=Ordner aus Favoriten entfernen
tip.google-docs-owner=Von Ihnen zur Bearbeitung in Google Docs&trade; gesperrt
tip.google-docs-locked=Von {0} ({1}) zur Bearbeitung in Google Docs&trade; gesperrt
tip.locked=Gesperrt von {0} ({1})
tip.lock-owner=Von Ihnen gesperrt
tip.rules=Diesem Ordner sind Regeln zugeordnet 
tip.simple-workflow=Einfacher Annehmen/Ablehnen Workflow angewandt
tip.transferred-node=Aus anderem Repository \u00fcbertragen

## Pop-up Messages
message.confirm.delete.title=Datei l\u00f6schen
message.confirm.delete=M\u00f6chten Sie ''{0}'' wirklich l\u00f6schen?
message.delete.success=''{0}'' wurde gel\u00f6scht
message.delete.failure=''{0}''s'' kann nicht gel\u00f6scht werden
message.details.success=Details wurden erfolgreich aktualisiert
message.details.failure=Details konnten nicht aktualisiert werden
message.edit-offline.success=''{0}'' kann nun bearbeitet werden
message.edit-offline.success.ie7=Dokument \u00fcber nachstehende Schaltfl\u00e4che herunterladen.
message.edit-offline.failure=Sie k\u00f6nnen ''{0}'' nicht bearbeiten.
message.edit-cancel.success=Die Bearbeitung von ''{0}'' wurde abgebrochen
message.edit-cancel.failure=Die Bearbeitung von ''{0}'' konnte nicht abgebrochen werden.
message.loading=Lade die Dokumenten-Bibliothek...
message.error=Kann nicht auf die Dokumenten-Bibliothek zugreifen
message.empty=Keine Artikel
message.empty.subfolders=Keine Artikel. "{0}" anklicken, um {1} Unterordner hier zu sehen.
message.empty.subfolders.link=Ordner anzeigen
message.favourite.failure=Konnte Favoritenliste nicht aktualisieren
message.simple-workflow.approved=Datei ist als akzeptiert markiert
message.simple-workflow.rejected=Datei ist als abgelehnt markiert
message.simple-workflow.failure=Die Workflow-Aktion konnte nicht abgeschlossen werden.
message.checkout-google.inprogress=''{0}'' wird nach Google Docs ausgecheckt
message.checkout-google.success=''{0}'' wurde in Google Docs eingef\u00fcgt
message.checkout-google.failure=Konnte ''{0}'' nicht in Google Docs einchecken
message.checkout-google.failure=Sie k\u00f6nnen ''{0}'' nicht nach Google Docs auschecken
message.checkin-google.inprogress=''{0}'' wird von Google Docs eingecheckt
message.checkin-google.success=''{0}'' wurde von Google Docs eingecheckt
message.checkin-google.failure=Sie k\u00f6nnen ''{0}'' nicht von Google Docs einchecken

## File Upload (upload new version)
label.filter-description=Gleicher Typ wie {0}

## Edit Details Dialog
edit-details.title=Details f\u00fcr {0}
edit-details.label.edit-metadata=Seite zum \u00c4ndern der Metadaten...

## Customize Dialog
customize.title=Anpassen
customize.header.actions=Aktionen

# SIDE sort extension
label.sortby=Sortieren nach: Name
search.sort.size=Gr\u00f6\u00dfe
search.sort.mimetype=MimeType
search.sort.type=Typ

## Custom Types
<%for (getAllAbstractClasses().nSort("name")){%>
### <%getFullName()%>
<%for (getSortedAttibutes()){%>
<%getQualifiedName%>=<%getLabel()%>
<%}%>
<%}%>
