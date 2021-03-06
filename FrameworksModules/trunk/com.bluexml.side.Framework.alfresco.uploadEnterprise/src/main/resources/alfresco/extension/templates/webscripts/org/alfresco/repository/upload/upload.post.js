function main()
{
    var filename = null;
    var content = null;
    var mimetype = null;
    var siteId = null;
    var containerId = null;
    var thumbnailNames = null;
    
    // Upload specific
    var uploadDirectory = null;
    var title = "";
    var overwrite = true; // If a filename clashes for a versionable file 
    
    // Update specific
    var updateNodeRef = null;
    var majorVersion = false;
    var description = "";
    
    //START BlueXML-patch-0  
    var contentType = "";
    //END BlueXML-patch-0  
    
    // Parse file attributes
    for each (field in formdata.fields)
    {
       switch (String(field.name).toLowerCase())
       {
          case "filedata":
             if (field.isFile)
             {
                filename = field.filename;
                content = field.content;
                mimetype = field.mimetype;
             }
             break;
          
          case "siteid":
             siteId = field.value;
             break;
             
          case "containerid":
             containerId = field.value;
             break;
          
          case "uploaddirectory":
             uploadDirectory = field.value;
             // Remove any leading "/" from the uploadDirectory
             if (uploadDirectory.substr(0, 1) == "/")
             {
                uploadDirectory = uploadDirectory.substr(1);
             }
             // Ensure uploadDirectory ends with "/" if not the root folder
             if ((uploadDirectory.length > 0) && (uploadDirectory.substring(uploadDirectory.length - 1) != "/"))
             {
                uploadDirectory = uploadDirectory + "/";
             }
             break;
    
          case "updatenoderef":
             updateNodeRef = field.value;
             break;
    
          case "filename":
             title = field.value;
             break;
    
          case "description":
             description = field.value;
             break;
    
          case "contenttype":
             contentType = field.value;
             break;
             
          //START BlueXML-patch-1     
          case "contentType":
             contentType = field.value;
             break;   
          //END BlueXML-patch-1 
          
          case "majorversion":
             majorVersion = field.value == "true";
             break;
    
          case "overwrite":
             overwrite = field.value == "true";
             break;
          
          case "thumbnails":
             thumbnailNames = field.value;
             break;
       }
    }
    
    // Ensure mandatory file attributes have been located
    if (siteId === null || containerId === null || filename === null || content === null)
    {
       status.code = 400;
       status.message = "Required parameters are missing";
       status.redirect = true;
       return;
    }
    
   var site = siteService.getSite(siteId);
   if (site === null)
   {
      status.code = 404;
      status.message = "Site (" + siteId + ") not found.";
      status.redirect = true;
      return;
   }
   
   // Upload mode, since uploadDirectory was used
   var container = site.getContainer(containerId);
   if (container === null)
   {
      container = site.createContainer(containerId);
   }
   
   if (container === null)
   {
      status.code = 404;
      status.message = "Component container (" + containerId + ") not found.";
      status.redirect = true;
   }
   
   if (updateNodeRef !== null && updateNodeRef != "" && (uploadDirectory === null || uploadDirectory == ""))
   {
      // Update existing file mode
      var workingCopy = search.findNode(updateNodeRef);
      if (workingCopy.isLocked)
      {
         // We cannot update a locked document
         status.code = 404;
         status.message = "Cannot update locked document '" + updateNodeRef + "', supply a reference to its working copy instead.";
         status.redirect = true;
         return;
      }
      
      if (!workingCopy.hasAspect("cm:workingcopy"))
      {
          // Ensure the original file is versionable - may have been uploaded via different route
          if (!workingCopy.hasAspect("cm:versionable"))
          {
              // Ensure the file is versionable - but do not autoversion or create initial version yet
              var props = new Array(2);
              props["cm:autoVersion"] = false;
              props["cm:initialVersion"] = false;
              workingCopy.addAspect("cm:versionable", props);
          }
          
          if (workingCopy.versionHistory == null)
          {
              // Create the first version manually so we have 1.0 before checkout
              workingCopy.createVersion("", true);
          }
        
         // It's not a working copy, do a check out to get the actual working copy
         workingCopy = workingCopy.checkout();
      }
      
      // Update the working copy content
      workingCopy.properties.content.write(content);
      // Reset working copy mimetype and encoding
      workingCopy.properties.content.guessMimetype(filename);
      workingCopy.properties.content.encoding = "UTF-8";
      // check it in again, with supplied version history note
      workingCopy = workingCopy.checkin(description, majorVersion);
      
      model.document = workingCopy;
   }
   else if (uploadDirectory !== null && (updateNodeRef === null || updateNodeRef == ""))
   {
    // Upload file mode
      var destNode = container;
      if (uploadDirectory != "")
      {
         destNode = container.childByNamePath(uploadDirectory);
      }
      if (destNode === null)
      {
         status.code = 404;
         status.message = "Cannot upload file since uploadDirectory '" + uploadDirectory + "' does not exist.";
         status.redirect = true;
         return;
      }
        
      var existingFile = container.childByNamePath(uploadDirectory + filename);
      if (existingFile !== null)
      {
         // File already exists, decide what to do
         if (existingFile.hasAspect("cm:versionable") && overwrite)
         {
            // Upload component was configured to overwrite files if name clashes
            existingFile.properties.content.write(content);
            model.document = existingFile;
            return;
         }
         else
         {
            // Upload component was configured to find a new unique name for clashing filenames
            var suffix = 1;
            var tmpFilename;
            while (existingFile !== null)
            {                               
               tmpFilename = filename.substring(0, filename.lastIndexOf(".")) + "-" + suffix + filename.substring(filename.lastIndexOf("."));
               existingFile = container.childByNamePath(uploadDirectory + tmpFilename);
               suffix++;
            }
            filename = tmpFilename;
         }
      }
        
      // save the new file (original or renamed file) as long as an overwrite hasn't been performed
      var newFile = destNode.createFile(filename);
      newFile.properties.contentType = contentType;
      newFile.properties.content.write(content);
      
      //START BlueXML-patch-2
      newFile.specializeType(contentType);
      //END BlueXML-patch-2
      
      // Reapply mimetype as upload may have been via Flash - which always sends binary mimetype
      newFile.properties.content.guessMimetype(filename);
      newFile.properties.content.encoding = "UTF-8";      
      newFile.save();
      
      // Ensure the file is versionable - but do not autoversion or create initial version
      var props = new Array(2);
      props["cm:autoVersion"] = false;
      props["cm:initialVersion"] = false;
      newFile.addAspect("cm:versionable", props);
      
      // Create thumbnail?
      if (thumbnailNames != null)
      {
         var thumbnails = thumbnailNames.split(",");
         for (var i = 0; i < thumbnails.length; i++)
         {
            var thumbnailName = thumbnails[i];
            if (thumbnailName != "" && thumbnailService.isThumbnailNameRegistered(thumbnailName))
            {
               newFile.createThumbnail(thumbnailName, true);
            }
         }
      }
      
      // Extract metadata - via repo action for now
      var emAction = actions.create("extract-metadata");
      if (emAction != null)
      {
         emAction.execute(newFile);
      }
      
      // Set the title if none set during meta-data extract
      newFile.reset();
      if (newFile.properties.title == null)
      {
        newFile.properties.title = title;
        newFile.save();
      }
      
      model.document = newFile;
   }
   else
   {
      status.code = 404;
      status.message = "Illegal arguments: updateNodeRef OR uploadDirectory must be provided (not both)";
      status.redirect = true;
      return;
   }
}

main();
