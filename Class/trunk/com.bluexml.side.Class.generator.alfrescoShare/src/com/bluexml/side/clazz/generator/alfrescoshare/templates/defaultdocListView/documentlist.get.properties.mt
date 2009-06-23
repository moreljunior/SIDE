<%
metamodel http://www.kerblue.org/class/1.0

import com.bluexml.side.clazz.generator.alfrescoshare.templates.uploadForm.file-upload-js-get-lib
import com.bluexml.side.clazz.generator.alfrescoshare.templates.servicesTemplates.Common
%>

<%--
  -- This file is a patched copy of Alfresco Share's
  -- Contains additional message for custom Types 
  --%>
  
<%script type="clazz.ClassPackage" name="fileName"%>
<%if (eContainer() == null) {%><%getProperty("alf.share.paths.core.site-webscripts")%>org/alfresco/components/documentlibrary/documentlist.get.properties<%}%>

<%script type="clazz.ClassPackage" name="htmlUploadJs" file="<%fileName%>"%>
## Buttons
button.folders.show=Show Folders
button.folders.hide=Hide Folders
button.view.simple=Simple View
button.view.detailed=Detailed View

## Drop-down Menus
menu.select=Select
menu.select.all=All
menu.select.none=None
menu.select.invert=Invert Selection
menu.select.folders=Folders
menu.select.documents=Documents

## Document Details
details.link-to=Link to: {0}
details.created.on=Created on:
details.created.by=Created by:
details.modified.on=Modified on:
details.modified.by=Modified by:
details.checked-out.on=Checked out on:
details.checked-out.by=Checked out by:
details.by=By:
details.version=Version:
details.size=Size:
details.description=Description:
details.description.none=(None)
details.comments=Comments:
details.tags=Tags:
details.tags.none=(None)

## Actions
actions.description.empty=Empty action set
actions.description.document=Document default
actions.description.folder=Folder default
actions.description.locked=Locked by another user
actions.description.lockOwner=Locked by you
actions.description.workingCopyOwner=Checked-out by you
actions.description.link=Document or Folder link

actions.document.details=Rename...
actions.document.copy-to=Copy to...
actions.document.move-to=Move to...
actions.document.delete=Delete Document
actions.document.download=Download
actions.document.download-again=Download
actions.document.download-original=Download Original
actions.document.edit-offline=Edit Offline
actions.document.edit-online=Edit Online
actions.document.cancel-editing=Cancel Editing
actions.document.upload-new-version=Upload New Version
actions.document.assign-workflow=Assign Workflow...
actions.document.manage-permissions=Manage Permissions...
actions.link.delete=Delete Link
actions.folder.details=Rename...
actions.folder.metadata=View / Edit Metadata
actions.folder.copy-to=Copy to...
actions.folder.move-to=Move to...
actions.folder.delete=Delete Folder
actions.folder.manage-permissions=Manage Permissions...
actions.more=More...

## Tips
tip.locked=Locked by {0} ({1})
tip.editing=Being edited by you
tip.lock-owner=Locked by you
tip.active-workflow=Belongs to {0} active workflows

## Pop-up Messages
message.confirm.delete=Are you sure you want to delete '{0}'?
message.delete.success='{0}' was deleted
message.delete.failure=Could not delete '{0}'
message.edit-offline.success='{0}' can now be edited
message.edit-offline.success.ie7=Download the document using the button below.
message.edit-offline.failure=You cannot edit '{0}'.
message.edit-cancel.success=Editing '{0}' has been cancelled
message.edit-cancel.failure=Could not cancel editing '{0}'.
message.loading=Loading the Document Library...
message.error=Could not access the Document Library
message.empty=No items
message.empty.subfolders=No items. Click "Show Folders" to see {0} subfolder(s) here.

## File Upload (upload new version)
label.filter-description=Same type as {0}

## Customize Dialog
customize.title=Customize
customize.header.actions=Actions

## Custom Types
<%for (getAllAbstractClasses()){%>
### <%getFullName()%>
<%for (attributes){%>
<%getQualifiedName%>=<%getLabel()%>
<%}%>
<%}%>
