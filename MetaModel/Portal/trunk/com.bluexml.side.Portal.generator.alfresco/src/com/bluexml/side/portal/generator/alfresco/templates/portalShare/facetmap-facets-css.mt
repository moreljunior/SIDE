<%
metamodel http://www.kerblue.org/portal/1.0
%>

<%script type="Page" name="facetmapfacetscss"%>
<%if (eContainer() != null) {%>
<%ID.toLowerCase().nPut("css_name")%>
<%for (portlets){%>
<%for (associationPortlet){%>
<%if (isPortletInternal != null && isPortletInternal.view != null){%>
<%for (isPortletInternal.view){%>
<%if (current().startsWith("view.FacetMap")){%>
<%getProperty("facetmap.webapps")%>facetmap-facets-<%nGet("css_name").substring(nGet("css_name").lastIndexOf("-") +1)%>/xsl/display/facetmap.css
<%}%>
<%}%>
<%}%>
<%}%>
<%}%>
<%}%>
<%script type="Page" name="facetmapGenerator" file="<%facetmapfacetscss%>" post="trim()"%>
<%if (eContainer() != null) {%>
<%for (portlets){%>
<%for (associationPortlet){%>
<%if (isPortletInternal != null && isPortletInternal.view != null){%>
<%for (isPortletInternal.view){%>
<%if (current().startsWith("view.FacetMap")){%>
.facetmap-browse{
	font-family: arial, helvetica, sans-serif;
	color: #000000;
	font-size : 11pt;
	margin : 2px;
}

.facet-title{
    width: 100%;
    font-size: 125%;
}

.result, .results-title,.facets-title,.chosen-facets-title {
    padding:5px;
}

.facetmap-browse, .facets, .results, .chosen-facets {
	border : 2px solid #828F95;
}

a{
	color:#000000;
	text-decoration:none;
}

a:visited {
	color: #828F95;
}

a:active {
	color: #FFFFFF;
	background: #000000;
}

hr{
	border:0;
	color: #828F95;
	background-color: #828F95;
	margin : 0;
	padding: 0;
}
.hr1{height: 3px;}
.hr2{height: 1px;}

.results-title,.facets-title,.facet-title,.chosen-facets-title{
	font-weight:bold;
	color: #FFFFFF;
	background: #6CA5CE;
}

.results-title,.facets-title,.chosen-facets-title{
	font-size: 145%;
}

.imgIcon, .collapseExpandContainer{
	width:16px;
	height:16px;
	border: none;
}

.inputUpdateSQLOther{
	display: none;
}
<%}%>
<%}%>
<%}%>
<%}%>
<%}%>
<%}%>