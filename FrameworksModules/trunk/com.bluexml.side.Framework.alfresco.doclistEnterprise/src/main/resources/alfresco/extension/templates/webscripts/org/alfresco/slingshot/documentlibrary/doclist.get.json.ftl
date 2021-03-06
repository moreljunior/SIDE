<#macro dateFormat date>${date?string("dd MMM yyyy HH:mm:ss 'GMT'Z '('zzz')'")}</#macro>
<#assign workingCopyLabel = " " + message("coci_service.working_copy_label")>
<#assign paging = doclist.paging>
<#assign user = doclist.user>
<#assign itemCount = doclist.itemCount>
<#escape x as jsonUtils.encodeJSONString(x)>
{
   "totalRecords": ${paging.totalRecords?c},
   "startIndex": ${paging.startIndex?c},
   "metadata":
   {
      "parent":
      {
   <#if doclist.parent??>
         "nodeRef": "${doclist.parent.nodeRef}",
   </#if>
         "permissions":
         {
            "userRole": "${user.role!""}",
            "userAccess":
            {
            <#list user.permissions?keys as perm>
               <#if user.permissions[perm]?is_boolean>
               "${perm?string}": ${user.permissions[perm]?string}<#if perm_has_next>,</#if>
               </#if>
            </#list>
            }
         }
      },
      "onlineEditing": ${doclist.onlineEditing?string},
      "itemCounts":
      {
         "folders": ${(itemCount.folders!0)?c},
         "documents": ${(itemCount.documents!0)?c}
      }
   },
   "items":
   [
   <#list doclist.items as item>
      <#assign d = item.asset>
      <#assign version = "1.0">
      <#if d.hasAspect("cm:versionable") && d.versionHistory?size != 0><#assign version = d.versionHistory[0].versionLabel></#if>
      <#if item.createdBy??>
         <#assign createdBy = item.createdBy.displayName>
         <#assign createdByUser = item.createdBy.userName>
      <#else>
         <#assign createdBy="" createdByUser="">
      </#if>
      <#if item.modifiedBy??>
         <#assign modifiedBy = item.modifiedBy.displayName>
         <#assign modifiedByUser = item.modifiedBy.userName>
      <#else>
         <#assign modifiedBy="" modifiedByUser="">
      </#if>
      <#if item.lockedBy??>
         <#assign lockedBy = item.lockedBy.displayName>
         <#assign lockedByUser = item.lockedBy.userName>
      <#else>
         <#assign lockedBy="" lockedByUser="">
      </#if>
      <#assign tags><#list item.tags as tag>"${tag}"<#if tag_has_next>,</#if></#list></#assign>
      {
         "index": ${item_index},
         "nodeRef": "${d.nodeRef}",
         "nodeType": "${shortQName(d.type)}",
         "type": "${item.type}",
         "isFolder": ${d.isContainer?string},
         "isLink": ${item.isLink?string},
         "mimetype": "${d.mimetype!""}",
         "fileName": "<#if item.isLink>${item.linkAsset.name}<#else>${d.name}</#if>",
         "displayName": "${d.name?replace(workingCopyLabel, "")}",
         "status": "<#list item.status?keys as s><#if item.status[s]?is_boolean && item.status[s] == true>${s}<#if s_has_next>,</#if></#if></#list>",
         "title": "${d.properties.title!""}",
         "description": "${d.properties.description!""}",
         "author": "${d.properties.author!""}",
         "createdOn": "<@dateFormat d.properties.created />",
         "createdBy": "${createdBy}",
         "createdByUser": "${createdByUser}",
         "modifiedOn": "<@dateFormat d.properties.modified />",
         "modifiedBy": "${modifiedBy}",
         "modifiedByUser": "${modifiedByUser}",
         "lockedBy": "${lockedBy}",
         "lockedByUser": "${lockedByUser}",
         "size": "${d.size?c}",
         "version": "${version}",
         "contentUrl": "api/node/content/${d.storeType}/${d.storeId}/${d.id}/${d.name?url}",
         "webdavUrl": "${d.webdavUrl}",
         "actionSet": "${item.actionSet}",
         "tags": <#noescape>[${tags}]</#noescape>,
         "customProperties":
         {
         	<#include "customViews.ftl">
         },
         "categories": [<#list d.properties.categories![] as c>["${c.name}", "${c.displayPath?replace("/categories/General","")}"]<#if c_has_next>,</#if></#list>],
         "activeWorkflows": "<#list item.activeWorkflows as aw>${aw}<#if aw_has_next>,</#if></#list>",
         "isFavourite": ${item.isFavourite?string},
         "location":
         {
            "site": "${item.location.site!""}",
            "siteTitle": "${item.location.siteTitle!""}",
            "container": "${item.location.container!""}",
            "path": "${item.location.path!""}",
            "file": "${item.location.file!""}"
         },
         "permissions":
         {
            "inherited": ${d.inheritsPermissions?string},
            "roles":
            [
            <#list d.permissions as permission>
               "${permission?string}"<#if permission_has_next>,</#if>
            </#list>
            ],
            "userAccess":
            {
            <#list item.actionPermissions?keys as actionPerm>
               <#if item.actionPermissions[actionPerm]?is_boolean>
               "${actionPerm?string}": ${item.actionPermissions[actionPerm]?string}<#if actionPerm_has_next>,</#if>
               </#if>
            </#list>
            }
         },
         "custom": <#noescape>${item.custom}</#noescape>
      }<#if item_has_next>,</#if>
   </#list>
   ]
}
</#escape>
