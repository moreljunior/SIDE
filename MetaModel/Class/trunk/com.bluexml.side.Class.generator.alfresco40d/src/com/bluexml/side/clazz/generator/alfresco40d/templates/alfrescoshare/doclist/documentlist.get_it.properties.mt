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
<%if (eContainer() == null) {%><%getProperty("alf.share.paths.core.site-webscripts")%>org/alfresco/components/documentlibrary/documentlist.get_it.properties<%}%>

<%script type="clazz.ClassPackage" name="messages" file="<%fileName%>"%>
## Buttons
button.sort.ascending=Ordinamento crescente
button.sort.descending=Ordinamento decrescente
button.folders.show=Mostra cartelle
button.folders.hide=Nascondi cartelle

## Drop-down Menus
menu.select=Seleziona
menu.select.all=Tutti
menu.select.none=Nessuno
menu.select.invert=Inverti selezione
menu.select.folders=Cartelle
menu.select.documents=Documenti

## Details Banners
details.banner.editing=Questo documento \u00e8 bloccato dall'utente attuale per la modifica offline.
details.banner.lock-owner=Questo documento \u00e8 bloccato dall'utente attuale.
details.banner.locked=Questo documento \u00e8 bloccato da {0} per la modifica.
details.banner.google-docs.link=Google Docs
details.banner.google-docs-owner=Questo documento \u00e8 stato caricato dall''utente attuale in {0} per la modifica.
details.banner.google-docs-locked=Questo documento \u00e8 stato caricato da {0} in {1} per la modifica.

## Actions - moved to slingshot.properties
actions.more=Segue...

## Status Indicators
status.active-workflows=Appartiene a {0} workflow attivi
status.editing=Che sta modificando l'utente attuale
status.google-docs-editing=Che sta modificando l'utente attuale tramite Google Docs&trade;
status.google-docs-owner=Bloccato dall'utente attuale per la modifica con Google Docs&trade;
status.google-docs-locked=Bloccato da {0} ({1}) per la modifica con Google Docs&trade;
status.locked=Bloccato da {0} ({1})
status.lock-owner=Bloccato dall'utente attuale
status.rules=La cartella ha regole attive
status.simple-workflow=Applicato un workflow semplice Approva/Respingi
status.exif=Metadati EXIF disponibili
status.geographic=Metadati di geolocalizzazione disponibili
status.transferred-node=Trasferito da un altro repository

## Tips
tip.insitu-rename=Rinomina
tip.insitu-tag=Tag

## File Upload (upload new version)
label.filter-description=Stesso tipo di {0}

## Edit Details Dialog
edit-details.title=Modifica propriet\u00e0: {0}
edit-details.label.edit-metadata=Tutte le propriet\u00e0...

## Help panels, including Drag and Drop
no.items.title=Nessun elemento di contenuto
show.folders=Mostra {0} sottocartelle
standard.upload.title=Crea raccolta.
standard.upload.description=Carica file
dnd.drop.title=Trascina e rilascia per caricare i file.
dnd.drop.doclist.description=Trascinare i file dal desktop e rilasciarli in questo punto per caricarli.
dnd.drop.folder.description=\u00c8 possibile rilasciare i file nelle cartelle.
dnd.upload.description=Carica file
dnd.newfolder.description=Crea cartella
other.options=\u00c8 anche possibile...
dnd.upload.tooltip=<p>\u00c8 anche possibile trascinare i file dal desktop e rilasciarli in Raccolta documenti.</p>
<p>Per utilizzare la funzione di trascinamento e rilascio, chiudere l'uploader in basso.</p>


## SIDE view labels
<%for (getAllReferencedAttributes()){%>
<%getPrefixedQualifiedName().replaceAll(":", "_")%>=<%getLabel()%>
<%}%>
