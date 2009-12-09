package com.facetmap.simple;

import java.io.File;
import java.net.URL;

import com.facetmap.DataException;
import com.facetmap.InternalException;
import com.facetmap.Map;

public class CustomXmlConverter extends XmlConverter {

	@Override
	public Map createMap(String s) throws DataException, InternalException {
		SimpleFacetMapX simplefacetmap = new SimpleFacetMapX();
		simplefacetmap.setTitle(s);
		SimpleResourceSpace simpleresourcespace = new SimpleResourceSpace(simplefacetmap);
		simplefacetmap.setResourceSpace(simpleresourcespace);
		rParser = new XmlSimpleResourceParser(simpleresourcespace, simplefacetmap);
		return simplefacetmap;
	}

	public CustomXmlConverter(URL url, File file) {
		super(url, file);
	}

	
}
