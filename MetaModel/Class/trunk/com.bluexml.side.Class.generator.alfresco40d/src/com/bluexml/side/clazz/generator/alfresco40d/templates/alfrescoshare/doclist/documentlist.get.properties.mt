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
<%if (eContainer() == null) {%><%getProperty("alf.share.paths.core.site-webscripts")%>org/alfresco/components/documentlibrary/documentlist.get.properties<%}%>

<%script type="clazz.ClassPackage" name="messages" file="<%fileName%>"%>
## Buttons
button.sort.ascending=Sort Ascending
button.sort.descending=Sort Descending
button.folders.show=Show Folders
button.folders.hide=Hide Folders

## Drop-down Menus
menu.select=Select
menu.select.all=All
menu.select.none=None
menu.select.invert=Invert Selection
menu.select.folders=Folders
menu.select.documents=Documents

## Details Banners
details.banner.editing=This document is locked by you for offline editing.
details.banner.lock-owner=This document is locked by you.
details.banner.locked=This document is locked by {0} for editing.
details.banner.google-docs.link=Google Docs
details.banner.google-docs-owner=This document has been uploaded by you to {0} for editing.
details.banner.google-docs-locked=This document has been uploaded by {0} to {1} for editing.

## Actions - moved to slingshot.properties
actions.more=More...

## Status Indicators
status.active-workflows=Belongs to {0} active workflows
status.editing=Being edited by you
status.google-docs-editing=Being edited by you via Google Docs&trade;
status.google-docs-owner=Locked by you for editing with Google Docs&trade;
status.google-docs-locked=Locked by {0} ({1}) for editing with Google Docs&trade;
status.locked=Locked by {0} ({1})
status.lock-owner=Locked by you
status.rules=Folder has rules applied
status.simple-workflow=Simple Approve/Reject workflow applied
status.exif=EXIF metadata available
status.geographic=Geolocation metadata available
status.transferred-node=Transferred from another Repository

## Tips
tip.insitu-rename=Rename
tip.insitu-tag=Tag

## File Upload (upload new version)
label.filter-description=Same type as {0}

## Edit Details Dialog
edit-details.title=Edit Properties: {0}
edit-details.label.edit-metadata=All Properties...

## Help panels, including Drag and Drop
no.items.title=No content items
show.folders=Show {0} subfolder(s)
standard.upload.title=Build your library!
standard.upload.description=Upload files
dnd.drop.title=Drag and drop to upload files!
dnd.drop.doclist.description=Drag files from your desktop and drop them here to upload.
dnd.drop.folder.description=You can drop files onto folders.
dnd.upload.description=Upload files
dnd.newfolder.description=Create a folder
other.options=You can also...
dnd.upload.tooltip=<p>You can also drag and drop files from your desktop into the Document Library.</p>\
   <p>To use drag and drop instead, close the uploader below.</p>


## Custom Types
<%for (getAllAbstractClasses().nSort("name")){%>
### <%getFullName()%>
<%for (getSortedAttibutes()){%>
<%getQualifiedName%>=<%getLabel()%>
<%}%>
<%}%>
