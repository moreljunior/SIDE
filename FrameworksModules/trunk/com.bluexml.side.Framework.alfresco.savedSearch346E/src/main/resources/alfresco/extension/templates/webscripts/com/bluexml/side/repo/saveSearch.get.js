const
DEFAULT_MAX_RESULTS = 250;
const
dataListHomeLocation = "/app:company_home/st:sites/cm:{site}/cm:dataLists";
const
containerType = "dl:dataList";

model.saved = saveSearch();

function saveSearch() {
	// get service args
	var currentSite = url.templateArgs.site;
	var saveName = args["saveName"];
	var savedSearchType = args["dataType"];

	// get search args
	var params = {
		siteId : (args.site !== null) ? args.site : null,
		containerId : (args.container !== null) ? args.container : null,
		repo : (args.repo !== null) ? (args.repo == "true") : false,
		term : (args.term !== null) ? args.term : null,
		tag : (args.tag !== null) ? args.tag : null,
		query : (args.query !== null) ? args.query : null,
		sort : (args.sort !== null) ? args.sort : null,
		maxResults : (args.maxResults !== null) ? parseInt(args.maxResults, 10) : DEFAULT_MAX_RESULTS
	};

	var locationToSave = dataListHomeLocation.replace(/\{site\}/, search.ISO9075Encode(currentSite));
	// get the datalistHome
	var datalistHome = null;
	var resultset = search.xpathSearch(locationToSave);
	if (resultset.length > 0) {
		datalistHome = resultset[0];
	} else {
		// cm:dataLists do not exists we must create it
		// get the site folder
		var parentPath = locationToSave.substring(0, locationToSave.lastIndexOf("/"));
		parentNode = search.xpathSearch(parentPath)[0];
		datalistHome = parentNode.createFolder("dataLists");
	}

	// get the dataList
	var savedSearchDataListContainer = null;
	for each ( var child in datalistHome.children) {
		if (child.name == "savedSearch") {
			savedSearchDataListContainer = child;
			break;
		}
	}
	if (savedSearchDataListContainer == null) {
		// must create the list container
		savedSearchDataListContainer = datalistHome.createNode("savedSearch", containerType);
	}
	// create new savedSearch
	var saved = savedSearchDataListContainer.createNode(saveName, savedSearchType);
	saved.properties.content.setMimetype("text/plain");
	// remove heading and trailing '(' ')'
	var sub1 = params.toSource().substring(1);
	var dataString = sub1.substring(0, sub1.length - 1);
	saved.content = dataString;
	// saved.guessEncoding();
	saved.save();

	return saved;
}