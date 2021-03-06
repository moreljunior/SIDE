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
<%if (eContainer() == null) {%><%getProperty("alf.share.paths.core.site-webscripts")%>org/alfresco/components/search/search.get_fr.properties<%}%>
message.loading=Chargement...
message.empty=Pas de r\u00e9sultat trouv\u00e9
message.error=Erreur durant la recherche
message.insite=dans le site
message.ofsize=de taille
message.modifiedby=modifi\u00e9 par
message.modifiedon=modifi\u00e9 : 
message.preview=Aper\u00e7u
message.singlesite=Site {0}
message.allsites=Tous les sites
message.infolderpath=Dans le dossier
message.repository=Entrep\u00f4t

label.folder=Dossier
label.document=Document
label.blogpost=Article de blog
label.forumpost=Sujet de forum
label.calendarevent=\u00c9v\u00e9nement de calendrier
label.wikipage=Page de wiki
label.link=Lien
label.datalist=Liste de donn\u00e9es
label.datalistitem=El\u00e9ment de liste de donn\u00e9es
label.unknown=Inconnu
label.sortby=Trier par : {0}
label.download=T\u00e9l\u00e9charger
label.viewinbrowser=Visualiser dans le navigateur

button.search=Rechercher

search.info.resultinfo={0} r\u00e9sultat(s)
search.info.resultinfomore=Plus de {0} r\u00e9sultats
search.info.searching=Recherche...
search.info.foundinsite=trouv\u00e9(s) dans le site {0}.
search.info.foundinallsite=trouv\u00e9(s) dans tous les sites.
search.info.foundinrepository=trouv\u00e9(s) dans l'entrep\u00f4t.

search.sort.relevance=Pertinence
search.sort.size=Taille
search.sort.mimetype=Type MIME
search.sort.type=Type

pagination.template={PreviousPageLink} {PageLinks} {NextPageLink}
pagination.template.page-report=


## Custom Types
<%for (getAllAbstractClasses().nSort("name")){%>
### <%getFullName()%>
<%for (getSortedAttibutes()){%>
<%getQualifiedName%>=<%getLabel()%>
<%}%>
<%}%>
