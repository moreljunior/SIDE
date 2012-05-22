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
<%if (eContainer() == null) {%><%getProperty("alf.share.paths.core.site-webscripts")%>org/alfresco/components/documentlibrary/documentlist.get_nl.properties<%}%>

<%script type="clazz.ClassPackage" name="messages" file="<%fileName%>"%>
## Buttons
button.sort.ascending=Oplopend sorteren
button.sort.descending=Aflopend sorteren
button.folders.show=Mappen weergeven
button.folders.hide=Mappen verbergen

## Drop-down Menus
menu.select=Selecteren
menu.select.all=Alle
menu.select.none=Geen
menu.select.invert=Selectie omkeren
menu.select.folders=Mappen
menu.select.documents=Documenten

## Details Banners
details.banner.editing=Dit document is door u vergrendeld voor offline bewerking.
details.banner.lock-owner=Dit document is vergrendeld door u.
details.banner.locked=Dit document is vergrendeld door {0} voor bewerking.
details.banner.google-docs.link=Google Docs
details.banner.google-docs-owner=Dit document is door u ge\u00fcpload naar {0} voor bewerking.
details.banner.google-docs-locked=Dit document is door {0} ge\u00fcpload naar {1} voor bewerking.

## Actions - moved to slingshot.properties
actions.more=Meer...

## Status Indicators
status.active-workflows=Hoort bij {0} actieve werkstromen
status.editing=Wordt door u bewerkt
status.google-docs-editing=Wordt door u bewerkt via Google Docs&trade;
status.google-docs-owner=Door u vergrendeld voor bewerking met Google Docs&trade;
status.google-docs-locked=Door {0} ({1}) vergrendeld voor bewerking met Google Docs&trade;
status.locked=Vergrendeld door {0} ({1})
status.lock-owner=Vergrendeld door u
status.rules=Op map zijn regels toegepast
status.simple-workflow=Eenvoudige goedkeuring/afwijzing-werkstroom toegepast
status.exif=EXIF-metagegevens beschikbaar
status.geographic=Geolocatiemetagegevens beschikbaar
status.transferred-node=Overgebracht vanuit andere repository

## Tips
tip.insitu-rename=Naam wijzigen
tip.insitu-tag=Tag

## File Upload (upload new version)
label.filter-description=Zelfde type als {0}

## Edit Details Dialog
edit-details.title=Eigenschappen bewerken: {0}
edit-details.label.edit-metadata=Alle eigenschappen...

## Help panels, including Drag and Drop
no.items.title=Geen contentitems
show.folders={0} submap(pen) weergeven
standard.upload.title=Bouw uw bibliotheek!
standard.upload.description=Bestanden uploaden
dnd.drop.title=Sleep bestanden om ze te uploaden!
dnd.drop.doclist.description=Sleep bestanden van uw desktop hierheen om ze te uploaden.
dnd.drop.folder.description=U kunt bestanden naar mappen slepen.
dnd.upload.description=Bestanden uploaden
dnd.newfolder.description=Een map maken
other.options=U kunt ook...
dnd.upload.tooltip=<p>U kunt bestanden ook van uw desktop naar de documentbibliotheek slepen.</p>\
  <p>Als u in plaats hiervan wilt slepen, sluit u het onderstaande uploadprogramma.</p>

## Custom Types
<%for (getAllAbstractClasses().nSort("name")){%>
### <%getFullName()%>
<%for (getSortedAttibutes()){%>
<%getQualifiedName%>=<%getLabel()%>
<%}%>
<%}%>
  