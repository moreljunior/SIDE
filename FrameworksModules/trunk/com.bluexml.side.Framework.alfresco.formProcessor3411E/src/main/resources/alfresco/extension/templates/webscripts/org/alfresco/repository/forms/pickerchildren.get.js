<import resource="classpath:/alfresco/templates/webscripts/org/alfresco/repository/forms/pickerresults.lib.js">
<import resource="classpath:/alfresco/extension/templates/webscripts/org/alfresco/repository/forms/treenode.lib.js">

/*
 * patched by SIDE to provide support of :
 * url.templateArgs.type=search, allow to compute advanced query
 * url.templateArgs.type=treeNode, allow to use this script to get children of arbitrary child->parent association and provide tree navigation 
 * url.templateArgs.type=category, for scoped tags in share Site (use args["scopedTags"]=true)
 */

function main() {
   var argsFilterType = args['filterType'];
   var argsSelectableType = args['selectableType'];
   var argsSearchTerm = args['searchTerm'];
   var argsMaxResults = args['size'];
   var argsXPath = args['xpath'];
   var pathElements = url.service.split("/");
   var parent = null;
   var rootNode = companyhome;
   var results = [];
   var categoryResults = null;
   var resultObj = null;
   var lastPathElement = null;
   
   var argsSite = args['site'];
   var argsSelectableTypeIsAspect = args['selectableTypeIsAspect'];
   var argsAdvancedQuery = args['advancedQuery'];

   if (logger.isLoggingEnabled()) {
      logger.log("children type = " + url.templateArgs.type);
      logger.log("argsSelectableType = " + argsSelectableType);
      logger.log("argsFilterType = " + argsFilterType);
      logger.log("argsSearchTerm = " + argsSearchTerm);
      logger.log("argsAdvancedQuery = " + argsAdvancedQuery);
      logger.log("argsMaxResults = " + argsMaxResults);
      logger.log("argsXPath = " + argsXPath);
      logger.log("argsSite = " + argsSite);

   }

   try {
      if (url.templateArgs.type == "treeNode") {
         var params = {};

         if (url.templateArgs["store_type"]) {
            params.nodeRef = url.templateArgs["store_type"] + "://" + url.templateArgs["store_id"] + "/" + url.templateArgs["id"];
         }
         params.site = argsSite;

         params.selectableTypeIsAspect = args["selectableTypeIsAspect"];
         params.nodeType = args["nodeType"];
         params.rootProperty = args["rootProperty"];
         params.rootName = args["rootName"];
         params.assoType = args["assoType"];
         params.selectableRoot = args["selectableRoot"] == "true";

         var hasSubfolders = true;
         var argMax = parseInt(args["max"], 10);
         var maxItems = isNaN(argMax) ? -1 : argMax;

         var result = getTreeNodeChidren(params);
         var children = result.children;

         if (children) {
            children.sort(sortByName);
            // get Nodes
            for ( var c = 0; c < children.length; c++) {
               var item = children[c];
               results.push({
                  item : item,
                  selectable : true
               });

               if (maxItems !== -1 && items.length > maxItems) {
                  results.pop();
                  break;
               }
            }
         }
         parent = result.parent;
         rootNode = result.root;
         model.sourceAsso = params.assoType;
      } else {
         // construct the NodeRef from the URL
         var nodeRef = url.templateArgs.store_type + "://" + url.templateArgs.store_id + "/" + url.templateArgs.id;
         // determine if we need to resolve the parent NodeRef

         if (argsXPath != null && argsXPath.match("[,\{]") == null && argsXPath.indexOf("://") == -1) {
            // resolve the provided XPath to a NodeRef
            var nodes = search.xpathSearch(argsXPath);
            if (nodes.length > 0) {
               nodeRef = String(nodes[0].nodeRef);
            }
         }

         // if the last path element is 'doclib' or 'siblings' find parent
         // node
         if (pathElements.length > 0) {
            lastPathElement = pathElements[pathElements.length - 1];

            if (logger.isLoggingEnabled())
               logger.log("lastPathElement = " + lastPathElement);

            if (lastPathElement == "siblings") {
               // the provided nodeRef is the node we want the siblings of
               // so
               // get
               // it's parent
               var node = search.findNode(nodeRef);
               if (node !== null) {
                  nodeRef = node.parent.nodeRef;
               } else {
                  // if the provided node was not found default to
                  // companyhome
                  nodeRef = "alfresco://company/home";
               }
            } else if (lastPathElement == "doclib") {
               // we want to find the document library for the nodeRef
               // provided
               nodeRef = findDoclib(nodeRef);
            }
         }

         if (url.templateArgs.type == "node") {
            // nodeRef input
            if (nodeRef == "alfresco://company/home") {
               parent = companyhome;
            } else if (nodeRef == "alfresco://user/home") {
               parent = userhome;
            } else if (nodeRef == "alfresco://sites/home") {
               parent = companyhome.childrenByXPath("st:sites")[0];
            } else {
               parent = search.findNode(nodeRef);
               if (parent === null) {
                  status.setCode(status.STATUS_NOT_FOUND, "Not a valid nodeRef: '" + nodeRef + "'");
                  return null;
               }
            }

            var query = "+PARENT:\"" + parent.nodeRef + "\"";
            if (argsFilterType != null) {
               // map short name to long name
               var types = {
                  'rma:dispositionSchedule' : '{http://www.alfresco.org/model/recordsmanagement/1.0}dispositionSchedule',
                  'rma:dispositionActionDefinition' : '{http://www.alfresco.org/model/recordsmanagement/1.0}dispositionActionDefinition',
                  'rma:dispositionAction' : '{http://www.alfresco.org/model/recordsmanagement/1.0}dispositionAction',
                  'rma:hold' : '{http://www.alfresco.org/model/recordsmanagement/1.0}hold',
                  'rma:transfer' : '{http://www.alfresco.org/model/recordsmanagement/1.0}transfer',
                  'cm:thumbnail' : '{http://www.alfresco.org/model/content/1.0}thumbnail'
               };

               var filterTypes = argsFilterType.split(',');
               for ( var i = 0, len = filterTypes.length; i < len; i++) {
                  var identifier = filterTypes[i];
                  if (types[identifier]) {
                     query += " -TYPE:\"" + types[identifier] + "\"";
                  }
               }
            }

            // make sure we don't return system folders
            query += " -TYPE:\"{http://www.alfresco.org/model/content/1.0}systemfolder\"";

            if (logger.isLoggingEnabled())
               logger.log("query = " + query);

            var searchResults = search.luceneSearch(query, "@{http://www.alfresco.org/model/content/1.0}name", true);

            // Ensure folders and folderlinks appear at the top of the list
            var containerResults = new Array(), contentResults = new Array();

            for each ( var result in searchResults) {
               if (result.isContainer || result.type == "{http://www.alfresco.org/model/application/1.0}folderlink") {
                  // wrap result and determine if it is selectable in the
                  // UI
                  resultObj = {
                     item : result
                  };
                  resultObj.selectable = isItemSelectable(result, argsSelectableType);

                  containerResults.push(resultObj);
               } else {
                  // wrap result and determine if it is selectable in the
                  // UI
                  resultObj = {
                     item : result
                  };
                  resultObj.selectable = isItemSelectable(result, argsSelectableType);

                  contentResults.push(resultObj);
               }
            }
            results = containerResults.concat(contentResults);
         } else if (url.templateArgs.type == "category") {
            var catAspect = (args["aspect"] != null) ? args["aspect"] : "cm:generalclassifiable";

            // TODO: Better way of finding this
            var rootCategories = classification.getRootCategories(catAspect);
            if (rootCategories != null && rootCategories.length > 0) {
               rootNode = rootCategories[0].parent;
               if (nodeRef == "alfresco://category/root") {
                  parent = rootNode;
                  var scopedTags = args["scopedTags"] == "true";
                  if (scopedTags && argsSite !== null && argsSite.length > 0) {
                     var site = siteService.getSite(argsSite);
                     var siteNode = site.node;
                     // fetch the nearest available tagscope
                     var scope = siteNode.tagScope;
                     categoryResults = [];
                     if (scope != null) {
                        var tagsDetails = null;
                        var topN = args["topN"] != undefined ? parseInt(args["topN"]) : -1;
                        if (topN > -1) {
                           // PENDING:
                           // getTopTags currently throws an AIOOB
                           // exception if
                           // topN >
                           // tags.length() :-/
                           if (scope.tags.length < topN) {
                              topN = scope.tags.length;
                           }  
                           tagsDetails = scope.getTopTags(topN);
                        } else {
                           tagsDetails = scope.tags;
                        }
                        for each (var tagDetail in tagsDetails) {
                           var name = tagDetail.name;
                           // get the tag node
                           var tagNode = parent.childrenByXPath("cm:"+name)[0];
                           categoryResults.push(tagNode);
                        }
                        
                     }
                  } else {
                     categoryResults = rootCategories;
                  }

               } else {
                  parent = search.findNode(nodeRef);
                  categoryResults = parent.children;
               }

               categoryResults.sort(sortByName);

               // make each result an object and indicate it is selectable
               // in
               // the
               // UI
               for each ( var result in categoryResults) {
                  results.push({
                     item : result,
                     selectable : true
                  });
               }
            }
         } else if (url.templateArgs.type == "authority") {
            // default to max of 100 results
            var maxResults = 100;
            if (argsMaxResults != null) {
               // force the argsMaxResults var to be treated as a number
            	maxResults = parseInt(argsMaxResults, 10) || maxResults;
            }

            if (argsSelectableType == "cm:person") {
               findUsers(argsSearchTerm, maxResults, results, argsXPath);
            } else if (argsSelectableType == "cm:authorityContainer") {
               findGroups(argsSearchTerm, maxResults, results, argsXPath);
            } else {
               // combine groups and users
               findGroups(argsSearchTerm, maxResults, results, argsXPath);
               findUsers(argsSearchTerm, maxResults, results, argsXPath);
            }
            // sort the full results by name alphabetically
            // it means that group and person [name=firstname lastname (username)] are mixed by the sort if combined
   			if (results.length > 0) {
      			results.sort(sortItemByName);
   			}
            
         } else if (url.templateArgs.type == "search") {
            // search in given path of the given type
            var type = "";
            if (argsSelectableTypeIsAspect == "true") {
               type = "ASPECT:\"" + argsSelectableType + "\"";
            } else {
               type = "TYPE:\"" + argsSelectableType + "\"";
            }
            var query = type;

            var path = null;
            if (argsXPath) {
               path = argsXPath;
            } else if (argsSite) {
               path = SITES_SPACE_QNAME_PATH + "{site}//*";
            }

            if (path != null) {
               var paths = path.split(",");
               var qnamePaths = "";
               var validePathsCount = 0;
               for ( var pc = 0; pc < paths.length; pc++) {
                  var pathItem = paths[pc];
                  var resolvedPathItem = null;
                  if (pathItem.indexOf("://") != -1) {
                     var regExpNodeRef = new RegExp("([^:]*)://([^/]*)/([^/]*)(.*)");
                     var groups = pathItem.match(regExpNodeRef);
                     if (groups != null) {
                        // resolve nodeRef to xpath
                        var protocol = groups[1];
                        var store = groups[2];
                        var uuid = groups[3];
                        var complement = groups[4];
                        var nodeRef = protocol + "://" + store + "/" + uuid;
                        var node = search.findNode(nodeRef);
                        resolvedPathItem = node.qnamePath + complement;
                     } else {
                        // error
                     }
                  } else if (pathItem != "") {
                     resolvedPathItem = pathItem;
                  }
                  if (resolvedPathItem != null) {
                     qnamePaths += (validePathsCount > 0 ? " OR " : "") + "PATH:\"" + resolvedPathItem + "\"";
                     validePathsCount++;
                  }
               }

               if (argsSite !== null && argsSite.length > 0) {
                  qnamePaths = qnamePaths.replace(/\{site\}/g, "cm:" + search.ISO9075Encode(argsSite));
               }

               query += " AND " + addSubQueryParenthesis(qnamePaths);
            }

            var advancedQuery = argsAdvancedQuery && argsAdvancedQuery != "";

            if (argsSearchTerm != undefined && argsSearchTerm != '' && argsSearchTerm != '*') {
               query += " AND " + "@cm\\:name:*" + argsSearchTerm + "*";
            }

            if (advancedQuery) {
               // TODO this parser is very simple, too simple !, value with
               // space is not allowed here
               query += " AND ";
               var queryArray = argsAdvancedQuery.split(' ');

               for ( var x = 0; x < queryArray.length; x++) {
                  var q_part = " ";
                  var v = queryArray[x];
                  if (v.indexOf(":") != -1) {
                     var part = v.split("=");
                     var key = part[0];
                     var value = part[1];
                     q_part += "@" + key.replace(":", "\\:") + ":'" + value + "'";
                  } else {
                     q_part += " " + v + " ";
                  }
                  query += q_part;
               }
            }
            if (logger.isLoggingEnabled()) {
               logger.log("pickerchildren.get.js : query :" + query);
            }

            var searchResults = search.query({
               query : query
            });

            for each ( var result in searchResults) {
               results.push({
                  item : result,
                  selectable : true
               });
            }
         }
         if (logger.isLoggingEnabled()) {
            logger.log("Found " + results.length + " results");
         }
      }
   } catch (e) {
      var msg = e.message;

      if (logger.isLoggingEnabled())
         logger.log(msg);

      status.setCode(500, msg);

      return;
   }

   model.parent = parent;
   model.rootNode = rootNode;
   model.results = results;
   model.type = url.templateArgs.type;
}

function isItemSelectable(node, selectableType) {
   var selectable = true;

   if (selectableType !== null && selectableType !== "") {
      selectable = node.isSubType(selectableType);

      if (!selectable) {
         // the selectableType could also be an aspect,
         // if the node has that aspect it is selectable
         selectable = node.hasAspect(selectableType);
      }
   }

   return selectable;
}

/* Sort the results by case-insensitive name */
function sortByName(a, b) {
   return (b.properties.name.toLowerCase() > a.properties.name.toLowerCase() ? -1 : 1);
}

/* Sort the user/group results by case-insensitive name */
function sortItemByName(a, b) {
   return (b.item.properties.name.toLowerCase() > a.item.properties.name.toLowerCase() ? -1 : 1);
}

function findUsers(searchTerm, maxResults, results, xpath) {
   // construct query string
   var query = '+TYPE:"cm:person"';

   if (searchTerm != null && searchTerm.length > 0) {
      searchTerm = searchTerm.replace(/\"/g, "");

      query += ' AND (@cm\\:firstName:"' + searchTerm + '*" @cm\\:lastName:"' + searchTerm + '*" @cm\\:userName:"' + searchTerm + '*" )';
   }
   if (xpath != null && xpath.length > 0) {
      query += ' AND PATH:"' + xpath + '"';
   }

   if (logger.isLoggingEnabled())
      logger.log("user query = " + query);

   // do the query
   var searchResults = search.query({
      query : query,
      page : {
         maxItems : maxResults
      },
      sort : [ {
         column : "cm:lastName",
         ascending : true
      }, {
         column : "cm:firstName",
         ascending : true
      } ]
   });

   // create person objet for each result
   for each ( var node in searchResults) {
      // add to results
      results.push({
         item : createPersonResult(node),
         selectable : true
      });
   }
}

function findGroups(searchTerm, maxResults, results, xpath) {
   var searchTermPattern = "*";

   if (searchTerm != null && searchTerm.length > 0) {
      searchTermPattern = searchTermPattern + searchTerm;
   }

   if (logger.isLoggingEnabled())
      logger.log("Finding groups matching pattern: " + searchTermPattern);

   var searchResults = groups.searchGroupsInZone(searchTermPattern, "APP.DEFAULT");
   for each ( var node in searchResults) {
      // find the actual node that represents the group
      var query = '+TYPE:"cm:authorityContainer" AND @cm\\:authorityName:' + node.fullName;

      if (xpath != null && xpath.length > 0) {
         query += ' AND PATH:"' + xpath + '"';
      }

      if (logger.isLoggingEnabled())
         logger.log("group query = " + query);

      var searchResults = search.query({
         query : query
      });

      if (searchResults.length > 0) {
         // add to results
         results.push({
            item : createGroupResult(searchResults[0]),
            selectable : true
         });
      }
   }

}

/**
 * Returns the nodeRef of the document library of the site the given nodeRef is
 * located within. If the nodeRef provided does not live within a site
 * "alfresco://company/home" is returned.
 * 
 * @param nodeRef
 *            The node to find the document library for
 * @return The nodeRef of the doclib or "alfresco://company/home" if the node is
 *         not located within a site
 */
function findDoclib(nodeRef) {
   var resultNodeRef = "alfresco://company/home";

   // find the given node
   var node = search.findNode(nodeRef);
   if (node !== null) {
      // get the name of the site
      var siteName = node.siteShortName;

      if (logger.isLoggingEnabled())
         logger.log("siteName = " + siteName);

      // if the node is in a site find the document library node using an
      // XPath search
      if (siteName !== null) {
         var nodes = search.xpathSearch("/app:company_home/st:sites/cm:" + search.ISO9075Encode(siteName) + "/cm:documentLibrary");
         if (nodes.length > 0) {
            // there should only be 1 result, get the first one
            resultNodeRef = String(nodes[0].nodeRef);
         }
      }
   }

   return resultNodeRef;
}

/**
 * seem that fts-alfresco do not support as expected expression with useless
 * parenthesis so we try to limit them
 * 
 * @param query
 * @returns
 */
function addSubQueryParenthesis(query) {
   if (query.indexOf(" OR ") != -1 || query.indexOf(" AND ") != -1) {
      query = ' ( ' + query + ' ) ';
   }
   return query;
}

main();