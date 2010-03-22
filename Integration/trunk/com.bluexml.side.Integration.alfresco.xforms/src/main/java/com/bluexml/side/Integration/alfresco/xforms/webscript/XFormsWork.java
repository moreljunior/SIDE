package com.bluexml.side.Integration.alfresco.xforms.webscript;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.search.impl.lucene.ADMLuceneSearcherImpl;
import org.alfresco.repo.search.impl.lucene.LuceneConfig;
import org.alfresco.repo.search.impl.lucene.LuceneIndexer;
import org.alfresco.repo.search.impl.lucene.LuceneSearcher;
import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.security.authority.AuthorityDAO;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.dictionary.AspectDefinition;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.service.cmr.dictionary.TypeDefinition;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.LimitBy;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.cmr.workflow.WorkflowPath;
import org.alfresco.service.cmr.workflow.WorkflowService;
import org.alfresco.service.cmr.workflow.WorkflowTask;
import org.alfresco.service.cmr.workflow.WorkflowTaskQuery;
import org.alfresco.service.cmr.workflow.WorkflowTaskState;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.QueryParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.bluexml.side.Integration.alfresco.xforms.webscript.XFormsWebscript.XFormsQueryType;
import com.bluexml.side.form.utils.DOMUtil;
import com.thoughtworks.xstream.XStream;

public class XFormsWork implements RunAsWork<String> {

	/** */
	private static Log logger = LogFactory.getLog(XFormsWork.class);
	private static XStream xstream = null;

	private final XFormsWebscript formsWebscript;
	private final XFormsQueryType queryType;
	private final DataLayer dataLayer;
	private final Map<String, String> parameters;
	private final ServiceRegistry serviceRegistry;

	private static final String BLUEXML_MODEL_URI = "http://www.bluexml.com/model/content/";
	private static final String BLUEXML_WORKFLOW_URI = "http://www.bluexml.com/model/workflow/";

	private static Map<QName, Set<QName>> searchableAttributesCache = new HashMap<QName, Set<QName>>();

	// Variable used to return the id of the Class which is suspected to
	// generate a problem (exception)
	private String faultyId = null;

	/*
	 * public XFormsWork(XFormsWebscript formsWebscript, XFormsQueryType queryType, BrowseBean
	 * browseBean, DataLayer dataLayer, Map<String, String> parameters) { super();
	 * this.formsWebscript = formsWebscript; this.queryType = queryType; this.browseBean =
	 * browseBean; this.dataLayer = dataLayer; this.parameters = parameters; }
	 */

	public XFormsWork(XFormsWebscript formsWebscript, XFormsQueryType queryType,
			Map<String, String> parameters, ServiceRegistry serviceRegistry) {
		super();
		this.formsWebscript = formsWebscript;
		this.queryType = queryType;
		this.dataLayer = new DataLayer(serviceRegistry);
		this.parameters = parameters;
		this.serviceRegistry = serviceRegistry;
	}

	private class DoWorkInTransaction implements RetryingTransactionCallback<String> {

		/**
		 * 
		 */
		public DoWorkInTransaction() {
			super();
		}

		@SuppressWarnings("synthetic-access")
		public String execute() throws Throwable {
			String result = null;
			faultyId = null; // reset faultyId on each execution

			dataLayer.setInTransaction(true);
			// if (queryType == XFormsWebscript.XFormsQueryType.create) {
			// result = create();
			// }
			// if (queryType == XFormsWebscript.XFormsQueryType.update) {
			// result = update();
			// }
			if (queryType == XFormsWebscript.XFormsQueryType.read) {
				result = read();
			} else if (queryType == XFormsWebscript.XFormsQueryType.list) {
				result = list();
			} else if (queryType == XFormsWebscript.XFormsQueryType.batch) {
				result = batch();
			} else if (queryType == XFormsWebscript.XFormsQueryType.workflow) {
				result = wfManage();
			} else if (queryType == XFormsWebscript.XFormsQueryType.addToPackage) {
				result = addInPackage();
			} else if (queryType == XFormsWebscript.XFormsQueryType.nodeinfo) {
				result = nodeInfo();
			} else if (queryType == XFormsWebscript.XFormsQueryType.upload) {
				result = upload();
			} else if (queryType == XFormsWebscript.XFormsQueryType.service) {
				result = service();
			} else if (queryType == XFormsWebscript.XFormsQueryType.createPath) {
				result = createPath();
			} else if (queryType == XFormsWebscript.XFormsQueryType.labels) {
				result = labels();
			} else if (queryType == XFormsWebscript.XFormsQueryType.enum_) {
				result = enum_();
			} else if (queryType == XFormsWebscript.XFormsQueryType.auth) {
				result = authenticate();
			} else if (queryType == XFormsWebscript.XFormsQueryType.delete) {
				result = delete();
			} else if (queryType == XFormsWebscript.XFormsQueryType.help) {
				result = help();
			}
			dataLayer.setInTransaction(false);

			logger
					.debug("BlueXML XForms webscript worker (opcode '" + queryType
							+ "'). Returning:");
			logger.debug(result);
			logger.debug(">-----------<");

			return result;
		}

	}

	public String doWork() {
		String result = null;
		try {
			boolean readOnly = false;
			boolean requiresNew = true;
			if (queryType == XFormsWebscript.XFormsQueryType.read
					|| queryType == XFormsWebscript.XFormsQueryType.labels
					|| queryType == XFormsWebscript.XFormsQueryType.enum_
					|| queryType == XFormsWebscript.XFormsQueryType.list) {
				readOnly = true;
			}
			RetryingTransactionHelper transactionHelper = formsWebscript.getTransactionHelper();
			result = transactionHelper.doInTransaction(new DoWorkInTransaction(), readOnly,
					requiresNew);
		} catch (Exception e) {
			logger.error(e, e);
			StringBuffer sb = new StringBuffer();
			HashSet<Throwable> causes = new HashSet<Throwable>();
			causes.add(e);
			exceptionToString(e, sb, causes);
			result = sb.toString();
		}
		return result;
	}

	private static XStream getXStream() {
		if (xstream == null) {
			xstream = new XStream();
		}
		return xstream;
	}

	/**
	 * Displays a help text for the <b>public</b> services of this webscript.
	 * 
	 * @return the help text.
	 */
	protected String help() {
		StringBuffer buffer = new StringBuffer();
		// don't show the lines that are commented out: internal use
		buffer.append("BlueXML XForms Webscript for Alfresco\n");
		buffer.append("-------------------------------------\n");
		buffer.append("List of services (see the documentation for specifics).\n");
		buffer.append("\n");
		buffer.append("/xforms/auth: authenticate a user.\n");
		buffer.append("/xforms/batch: perform a set of CRUD operations against the repository.\n");
		// buffer.append("/xforms/delete: delete a content from the repository.\n");
		buffer.append("/xforms/enum: list items of a dynamic enumeration.\n");
		buffer.append("/xforms/help: show this help.\n");
		// Amenel: never got to know the use of "labels" TODO: check and test
		buffer.append("/xforms/labels: get a translation for a dynamic enumeration litteral.\n");
		buffer.append("/xforms/list: list all objects of a given type.\n");
		buffer.append("/xforms/mkdir: create a path in Alfresco.\n");
		// buffer.append("/xforms/package: \n"); // don't show: internal use
		buffer.append("/xforms/read: read an object of any type from the repository.\n");
		// buffer.append("/xforms/service: call functions of chosen services from the Alfresco's API.\n");
		buffer.append("/xforms/upload: upload a file to the repository.\n");
		buffer.append("/xforms/workflow: call functions of the WorkflowService interface.\n");
		buffer.append("-------------------------------------\n");

		return buffer.toString();
	}

	/**
	 * Tests whether user credentials authenticate successfully with Alfresco.
	 * <p/>
	 * Parameters: "username", "password".
	 * <p/>
	 * 
	 * @return "success" if successful, "failure" if an exception occurred or the auth failed.
	 */
	protected String authenticate() {
		AuthenticationService authService = serviceRegistry.getAuthenticationService();
		String username = parameters.get("username");
		String password = parameters.get("password");

		try {
			authService.authenticate(username, password.toCharArray());
		} catch (AuthenticationException e) {
			return "failure";
		}
		return "success";
	}

	/**
	 * Builds some information about the content of a node.
	 * <p>
	 * Parameter: "nodeId": complete (store + workspace) id.
	 * </p>
	 * 
	 * @return an empty string if any problem or the comma-separated string containing the
	 *         information built. Currently, {full node id}, {content size in bytes}.
	 */
	protected String nodeInfo() {
		String nodeId = parameters.get("nodeId");

		try {
			String result;
			NodeRef nodeRef = new NodeRef(nodeId);
			Serializable name = this.serviceRegistry.getNodeService().getProperty(nodeRef,
					ContentModel.PROP_NAME);
			Serializable content = this.serviceRegistry.getNodeService().getProperty(nodeRef,
					ContentModel.PROP_CONTENT);
			if (content == null) {
				return "";
			}
			ContentData contentData = (ContentData) content;

			long size = contentData.getSize();

			result = name.toString() + "," + size;
			return result;

		} catch (Exception e) {
			logger.error("Failed to provide info for node id: " + nodeId + ". An error occurred.");
		}
		return "";
	}

	private void exceptionToString(Throwable e, StringBuffer sb, HashSet<Throwable> causes) {
		exceptionToStringOpenTag("exception", sb);
		exceptionToStringAddType(e.getClass(), sb);
		// exceptionToStringAddEntry("message", e.getMessage(), sb);
		exceptionToStringOpenTag("message", sb);
		sb.append(e.getMessage());
		if (faultyId != null) {
			// Brice : added information to return suspected id
			exceptionToStringAddEntry("suspectedId", faultyId, sb);
		}
		exceptionToStringCloseTag("message", sb);
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String stacktrace = sw.toString();
		exceptionToStringAddEntry("stacktrace", stacktrace, sb);
		Throwable cause = e.getCause();
		if (cause != null && !causes.contains(cause)) {
			causes.add(cause);
			exceptionToStringOpenTag("cause", sb);
			exceptionToString(cause, sb, causes);
			exceptionToStringCloseTag("cause", sb);
		}
		exceptionToStringCloseTag("exception", sb);
	}

	private void exceptionToStringAddType(Class<?> exceptionClass, StringBuffer sb) {
		exceptionToStringOpenTag("type", sb);
		exceptionToStringAddEntry("name", exceptionClass.getCanonicalName(), sb);
		Class<?> superclass = exceptionClass.getSuperclass();
		if (superclass != null) {
			exceptionToStringOpenTag("superclass", sb);
			exceptionToStringAddType(superclass, sb);
			exceptionToStringCloseTag("superclass", sb);
		}
		exceptionToStringCloseTag("type", sb);
	}

	private void exceptionToStringAddEntry(String tagName, String content, StringBuffer sb) {
		exceptionToStringOpenTag(tagName, sb);
		sb.append(StringEscapeUtils.escapeXml(content));
		exceptionToStringCloseTag(tagName, sb);
	}

	private void exceptionToStringOpenTag(String tagName, StringBuffer sb) {
		sb.append("<");
		sb.append(tagName);
		sb.append(">");
	}

	private void exceptionToStringCloseTag(String tagName, StringBuffer sb) {
		sb.append("</");
		sb.append(tagName);
		sb.append(">");
	}

	/**
	 * 
	 * @param xmlResult
	 * @param id
	 * @param label
	 * @param qname
	 *            the actual datatype of the node being added to the results list.
	 */
	private void appendResult(StringBuffer xmlResult, String id, String label, String qname) {
		// the tag names used here must be in sync with the ones defined in the XForms controller
		xmlResult.append("<item><id>");
		xmlResult.append(StringEscapeUtils.escapeXml(id));
		xmlResult.append("</id><label>");
		xmlResult.append(StringEscapeUtils.escapeXml(label));
		xmlResult.append("</label><qname>");
		if (qname != null) {
			xmlResult.append(StringEscapeUtils.escapeXml(qname));
		}
		xmlResult.append("</qname></item>\n");
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	protected String labels() throws Exception {
		String query = parameters.get("query");
		StringBuffer xmlResult = new StringBuffer("");

		xmlResult.append("<results>\n");

		if (query.startsWith("enum;")) {
			String[] ids = query.split(";");
			appendResult(xmlResult, ids[1], getLitteralTranslation(ids[1]), null);
		} else {
			String[] ids = query.split(";");
			for (String id : ids) {
				NodeRef nodeRef = new NodeRef(id);
				// Amenel: I suspect id refers to LitteralTranslation objects
				// but not sure...
				// "value" is dependent on the model (changes should propagate)
				String nodeName = resolveNodeName(nodeRef, "value", false);
				appendResult(xmlResult, nodeRef.toString(), nodeName, null);
			}
		}

		xmlResult.append("</results>");
		return xmlResult.toString();
	}

	private String getLitteralTranslation(String code) throws SQLException {
		java.sql.ResultSet resultSet = SQLRequester.executeQuery(getSQLQueryLitteral(code));
		if (resultSet.next()) {
			return resultSet.getString("VALUE");
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	protected String enum_() {
		String type = parameters.get("type");

		String parent = parameters.get("parent");
		String context = parameters.get("context");
		String query = parameters.get("query");
		String slimit = parameters.get("limit");
		boolean limit = StringUtils.equals(slimit, "true");

		java.sql.ResultSet resultSet = SQLRequester.executeQuery(getSQLQuery(type, parent, context,
				query, limit));

		String code = null;
		String value = null;

		StringBuffer xmlResult = new StringBuffer("");
		xmlResult.append("<results>\n");
		try {
			while (resultSet.next()) {
				code = resultSet.getString("CODE");
				value = resultSet.getString("VALUE");
				if (StringUtils.trimToNull(code) == null) {
					value = resultSet.getString("UUID");
				}
				appendResult(xmlResult, code, value, null);
			}
			SQLRequester.closeQuery(resultSet);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		xmlResult.append("</results>");
		return xmlResult.toString();
	}

	/**
	 * Parameters: <br/>
	 * "type": the data type to search <br/>
	 * "query": the search keyword string<br/>
	 * "queryFilter": an additional search keyword string<br/>
	 * "format": the format pattern for the label of objects<br/>
	 * "maxLength": the length at which labels computed using the format are truncated<br/>
	 * "maxResults": the max number of items allowed in the result set<br/>
	 * "identifier": the local name of a property whose value will be used as the id of results.
	 * Quite obviously, that field MUST 1- be non-null, 2- be an actual identifier (i.e. no value is
	 * duplicated in the value set)<br/>
	 * "filterAssoc" // TODO
	 * 
	 * @return
	 * @throws Exception
	 */
	protected String list() throws Exception {
		class ResultBean { // #1406
			public String id;
			public String label;
			public String qname;

			ResultBean(String nodeId, String nodeLabel, String nodeQName) {
				this.id = nodeId;
				this.label = nodeLabel;
				this.qname = nodeQName;
			}

		}

		// collect parameters
		String type = parameters.get("type");
		String identifier = parameters.get("identifier"); // #1529
		String format = parameters.get("format");
		// URL-decode the format pattern
		if (StringUtils.trimToNull(format) != null) {
			try {
				format = URLDecoder.decode(format, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("UTF-8 is unsupported. Format is defaulted to 'uuid'");
				format = "uuid";
			}
		}

		int maxResults = 10; // arbitrary default value
		String maxParam = parameters.get("maxResults");
		try {
			maxResults = Integer.parseInt(maxParam);
		} catch (NumberFormatException ne) {
			logger.error("'list' op: wrong number in parameter 'maxResults' (" + maxParam + ")");
		}

		int maxLength = 0; // arbitrary default value
		String lengthParam = parameters.get("maxLength");
		try {
			maxLength = Integer.parseInt(lengthParam);
		} catch (NumberFormatException nfe) {
			logger.error("'list' op: wrong number in parameter 'maxLength' (" + lengthParam + ")");
		}
		// ** #1310
		// String filterAssoc = parameters.get("filterAssoc"); // TODO: implement
		// boolean applyFilter = (StringUtils.trimToNull(filterAssoc) != null);
		// ** #1310

		List<String> searchedValues = new ArrayList<String>();
		String query = parameters.get("query");
		if (StringUtils.trimToNull(query) != null) {
			searchedValues.add(query);
		}

		String queryFilter = parameters.get("queryFilter");
		if (StringUtils.trimToNull(queryFilter) != null) {
			searchedValues.add(queryFilter);
		}

		// perform the search
		ResultSet luceneResultSet = getResultSet(type, searchedValues, maxResults);
		int luceneResultLength = luceneResultSet.length();

		//
		// collect items and apply filtering and/or limiting. Node names/labels are also computed.
		//

		// configure the filtering/limiting
		int effectivelyReturned = 0;
		int filteredOut = 0;
		QName identifierQName = null;
		boolean includeSystemProperties = false;
		int returnAtMost;
		if (maxResults > 0) {
			returnAtMost = Math.min(luceneResultLength, maxResults);
		} else {
			returnAtMost = luceneResultLength;
		}
		if (identifier != null) {
			identifierQName = resolveIdentifierQName(identifier, type);
			includeSystemProperties = true;
		}

		// collect items and apply filtering and/or limiting
		List<ResultBean> resultBeanList = new ArrayList<ResultBean>(returnAtMost);
		for (int i = 0; i < returnAtMost; i++) {
			NodeRef nodeRef = luceneResultSet.getNodeRef(i);
			String label = resolveNodeName(nodeRef, format, includeSystemProperties);
			if (maxLength > 0) {
				label = StringUtils.left(label, maxLength);
			}
			QName qname = serviceRegistry.getNodeService().getType(nodeRef); // #1510
			String id;
			if (identifier == null) {
				id = nodeRef.toString();
			} else {
				id = resolveIdentifierValue(nodeRef, identifierQName);
			}
			String qnameStr = qname.toPrefixString(formsWebscript.getNamespacePrefixResolver());
			ResultBean aBean = new ResultBean(id, label, qnameStr);
			boolean isAddableBean = true;
			if (includeSystemProperties) {
				// for system datatypes, search the label (in case indexing is off for that type)
				if ((query != null) && (aBean.label.contains(query) == false)) {
					isAddableBean = false;
					filteredOut++;
				}
			}
			if (isAddableBean) {
				resultBeanList.add(aBean);
				effectivelyReturned++;
			}
		}

		//
		// sort the result list by node name. #1406
		Collections.sort(resultBeanList, new Comparator<ResultBean>() {
			public int compare(ResultBean o1, ResultBean o2) {
				return o1.label.compareTo(o2.label);
			}
		});

		//
		// write all results in the items string buffer
		StringBuffer itemsBuf = new StringBuffer("");
		for (ResultBean aBean : resultBeanList) {
			appendResult(itemsBuf, aBean.id, aBean.label, aBean.qname);
		}

		StringBuffer xmlResult = new StringBuffer("");
		xmlResult.append("<results>\n");
		xmlResult.append("<query>\n");
		xmlResult.append("  <count>").append(luceneResultLength).append("</count>\n");
		xmlResult.append("  <maxResults>").append(maxResults).append("</maxResults>\n");
		xmlResult.append("  <returned>").append(effectivelyReturned).append("</returned>\n");
		xmlResult.append("  <filteredOut>").append(filteredOut).append("</filteredOut>\n");
		xmlResult.append("  <query>").append(StringUtils.trimToEmpty(query)).append("</query>\n");
		xmlResult.append("</query>\n");
		xmlResult.append(itemsBuf);

		xmlResult.append("</results>");
		return xmlResult.toString();
	}

	/**
	 * Returns the qname of the property whose local name matches the identifier.
	 * 
	 * @param identifier
	 *            a non-<code>null</code> string that SHOULD be the local name of one the node's
	 *            properties as can be found in the type definition.
	 * @param type
	 *            the node type whose definition should contain the identifier
	 */
	private QName resolveIdentifierQName(String identifier, String type) { // #1529
		List<QName> listQNames = formsWebscript.getSubTypes(type);
		if ((listQNames != null) && (listQNames.size() != 0)) {
			QName qname = listQNames.get(0);
			TypeDefinition typeDef = serviceRegistry.getDictionaryService().getType(qname);
			Map<QName, PropertyDefinition> properties = typeDef.getProperties();

			for (QName property : properties.keySet()) {
				if (property.getLocalName().equalsIgnoreCase(identifier)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Resolved qname '" + property + "'for identifier '"
								+ identifier + "'");
					}
					return property;
				}
			}
		}

		logger.error("Failed in resolving identifier qname. identifier ='" + identifier
				+ "', datatype='" + type + "'");
		return null;
	}

	/**
	 * Provides the value of the node's identifier property.
	 * 
	 * @param nodeRef
	 *            an existing node
	 * @param identifierQName
	 *            the qname of a node property
	 * @return
	 */
	private String resolveIdentifierValue(NodeRef nodeRef, QName identifierQName) { // #1529
		if (identifierQName == null) {
			return "";
		}
		Serializable value = serviceRegistry.getNodeService().getProperty(nodeRef, identifierQName);

		if (value == null) {
			return "";
		}

		return value.toString();
	}

	/**
	 * Provides the label (i.e. user-readable form) for the node.
	 * 
	 * @param nodeRef
	 * @param format
	 *            the URL-decoded format pattern
	 * @param includeSystemProperties
	 *            if <code>true</code>, system properties are also considered, in addition to
	 *            properties from the data models.
	 * @return
	 */
	private String resolveNodeName(NodeRef nodeRef, String format, boolean includeSystemProperties) {
		return dataLayer.getLabelForNode(nodeRef, format, includeSystemProperties);
	}

	private ResultSet getResultSet(String type, List<String> searchedValues, int maxResults) {
		return getUnsecureLuceneSearcher().query(
				createSearchParameters(type, searchedValues, maxResults));
	}

	private String getSQLQuery(String type, String parent, String context, String query,
			boolean limit) {

		String rparent = StringUtils.trimToNull(parent);
		String rcontext = StringUtils.trimToNull(context);
		String rquery = StringUtils.trimToNull(query);

		StringBuffer sql_query = new StringBuffer("");
		sql_query.append("SELECT distinct L.uuid as UUID, L.code as CODE, LT.value as VALUE");
		sql_query
				.append(" FROM Litteral L, LitteralTranslation LT, EnumerationType ET, Litteral_translated_LitteralTranslation LTL, EnumerationType_typeOf_Litteral ETL");
		if (rparent != null) {
			sql_query.append(", Litteral LP, Litteral_parent_Litteral LPL");
		}
		if (rcontext != null) {
			sql_query.append(", VisibilityContext VC, Litteral_visibility_VisibilityContext LVVC");
		}
		sql_query.append(" WHERE L.id = LTL.Litteral");
		sql_query.append(" AND LTL.LitteralTranslation = LT.id");
		sql_query.append(" AND lang = 'fr'");
		sql_query.append(" AND ET.id = ETL.EnumerationType");
		sql_query.append(" AND ETL.Litteral = L.id");
		sql_query.append(" AND ET.name = '");
		sql_query.append(type);
		sql_query.append("'");
		if (rparent != null) {
			sql_query.append(" AND LP.code = '");
			sql_query.append(rparent);
			sql_query.append("' AND LPL.Litteral_source = L.id AND LPL.Litteral_target = LP.id");
		}
		if (rcontext != null) {
			sql_query.append(" AND VC.value = '");
			sql_query.append(rcontext);
			sql_query.append("' AND LVVC.Litteral = L.id AND LVVC.VisibilityContext = VC.id");
		}
		if (rquery != null) {
			sql_query.append(" AND LT.value like '%");
			sql_query.append(rquery);
			sql_query.append("%'");
		}
		if (limit) {
			sql_query.append(" LIMIT 0 , 10");
		}
		return sql_query.toString();
	}

	private String getSQLQueryLitteral(String code) {
		StringBuffer sql_query = new StringBuffer("");
		sql_query.append("SELECT LT.value as VALUE");
		sql_query
				.append(" FROM Litteral L, LitteralTranslation LT, Litteral_translated_LitteralTranslation LTL");
		sql_query.append(" WHERE L.code = '");
		sql_query.append(code);
		sql_query.append("'");
		sql_query.append(" AND L.id = LTL.Litteral");
		sql_query.append(" AND LTL.LitteralTranslation = LT.id");
		sql_query.append(" AND LT.lang = 'fr'");
		// Amenel: why is this condition on "L.code" repeated ?
		sql_query.append(" AND L.code = '");
		sql_query.append(code);
		sql_query.append("'");
		return sql_query.toString();
	}

	protected String delete() {
		String nodeRef = parameters.get("nodeRef");
		dataLayer.delete(nodeRef);
		return nodeRef;
	}

	protected String read() {
		String objectId = parameters.get("objectId");
		String result = dataLayer.read(objectId);
		return result;
	}

	protected String batch() throws Exception {
		String datas = parameters.get("datas");
		// build the DOM tree
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		InputSource source = new InputSource(new StringReader(datas));
		Document document = factory.newDocumentBuilder().parse(source);
		Element batchElement = document.getDocumentElement();

		List<Element> children = DOMUtil.getAllChildren(batchElement);
		Map<String, String> created = new HashMap<String, String>();

		String currentId = null;
		try {
			for (Element element : children) {
				replaceIds(element, created);
				if (StringUtils.equals(element.getTagName(), "create")) {
					Element toCreate = DOMUtil.getChild(element, "class");
					String transactionId = toCreate.getAttribute("id");
					currentId = transactionId; // for faulty id management
					String path = null;
					NodeRef result = dataLayer.create(path, toCreate, null);
					created.put(transactionId, result.toString());
				} else if (StringUtils.equals(element.getTagName(), "update")) {
					Element toUpdate = DOMUtil.getChild(element, "class");
					String nodeId = toUpdate.getAttribute("id");
					currentId = nodeId; // for faulty id management
					NodeRef result = dataLayer.update(nodeId, toUpdate);
					created.put(nodeId, result.toString());
				} else if (StringUtils.equals(element.getTagName(), "delete")) {
					dataLayer.delete(element.getTextContent());
				} else if (StringUtils.equals(element.getTagName(), "requester")) {
					if (StringUtils.equals(element.getTextContent(), "XFormsController") == true) {
						// NOTHING YET
					}
				} else if (StringUtils.equals(element.getTagName(), "attach")) {
					String target = DOMUtil.getChild(element, "targetNode").getTextContent();
					String filename = DOMUtil.getChild(element, "fileName").getTextContent();
					String filepath = DOMUtil.getChild(element, "filePath").getTextContent();
					String mimetype = DOMUtil.getChild(element, "mimeType").getTextContent();
					String contentType = DOMUtil.getChild(element, "contentType").getTextContent();
					String appendStr = DOMUtil.getChild(element, "appendSuffix").getTextContent();
					boolean shouldAppendSuffix = !(StringUtils.equals(appendStr, "false"));

					// we must ensure the receiver is a valid id
					String receiver = target;
					try {
						@SuppressWarnings("unused")
						NodeRef nodeRef = new NodeRef(target);
					} catch (Exception e) {
						receiver = created.get(target);
					}
					dataLayer.attachContent(receiver, filename, filepath, mimetype, contentType,
							shouldAppendSuffix);
				}
			}
		} catch (RuntimeException e) {
			/*
			 * this exception catching enables to retrieve the faulty id which will be an additional
			 * information that will be given to user
			 */
			this.faultyId = currentId;
			throw e;
		}
		StringBuffer sb = new StringBuffer();
		createdToXML(sb, created);
		return sb.toString();
	}

	/**
	 * Service provider for managing workflows. <br/>
	 * Required parameters: "method": an identifier for the service being asked for.<br/>
	 * Optional parameters: service-specific. See their code.
	 * <p>
	 * Services indicated via "method" are either calls to helper functions of ours (prefixed with
	 * "wf" e.g. "wfCollectInstanceProperties") or direct calls to WorkflowService functions.
	 * </p>
	 * 
	 * @return the XML-serialized version of the call result.
	 */
	@SuppressWarnings("unchecked")
	protected String wfManage() {
		WorkflowService wfs = serviceRegistry.getWorkflowService();
		String result = "";
		XStream xstream = getXStream();

		String method = parameters.get("method");

		if (StringUtils.equals(method, "wfStart")) {
			// startWf : need @wfName and @attributes
			String wfName = parameters.get("wfName");
			Map<QName, Serializable> atts = (Map<QName, Serializable>) xstream.fromXML(parameters
					.get("attributes"));
			WorkflowPath res = wfs.startWorkflow(wfName, atts);
			result = xstream.toXML(res);
		} else if (StringUtils.equals(method, "wfUpdate")) {
			// updateWf : need @taskId, @attributes, @add, @remove
			String taskId = parameters.get("taskId");
			Map<QName, Serializable> atts = (Map<QName, Serializable>) xstream.fromXML(parameters
					.get("attributes"));
			Map<QName, List<NodeRef>> add = (Map<QName, List<NodeRef>>) xstream.fromXML(parameters
					.get("add"));
			Map<QName, List<NodeRef>> remove = (Map<QName, List<NodeRef>>) xstream
					.fromXML(parameters.get("remove"));
			WorkflowTask res = wfs.updateTask(taskId, atts, add, remove);
			result = xstream.toXML(res);
		} else if (StringUtils.equals(method, "wfSignal")) {
			// signalWf need @pathId and @transitionId
			String pathId = parameters.get("pathId");
			String transitionId = parameters.get("transitionId");
			result = xstream.toXML(wfs.signal(pathId, transitionId));
		} else if (StringUtils.equals(method, "wfCancel")) {
			// cancelWf need @workflowId
			String workflowId = parameters.get("workflowId");
			result = xstream.toXML(wfs.cancelWorkflow(workflowId));
		} else if (StringUtils.equals(method, "wfDelete")) {
			// deleteWf need @workflowId
			String workflowId = parameters.get("workflowId");
			result = xstream.toXML(wfs.deleteWorkflow(workflowId));
		} else if (StringUtils.equals(method, "wfCollectInstanceProperties")) {
			// we need @workflowId, which is an instance id
			Map<QName, Serializable> properties = wfCollectInstanceProperties(wfs);
			result = xstream.toXML(properties);
		} else {
			// calls a generic method
			// result = serviceMethodCall(wfs, xstream, method);
			Method[] methods = wfs.getClass().getDeclaredMethods();
			Method wfsMethod = null;
			for (Method aMethod : methods) {
				if (StringUtils.equals(method, aMethod.getName())) {
					wfsMethod = aMethod;
				}
			}
			if (wfsMethod != null) {
				Class<?>[] parameterTypes = wfsMethod.getParameterTypes();
				Object[] methodParameters = new Object[parameterTypes.length];
				int i = 0;
				for (Class<?> parameterType : parameterTypes) {
					String sparameter = parameters.get("arg" + i);
					if (parameterType.isInstance("a")) { // String params are not XStream'ed
						methodParameters[i] = sparameter;
					} else { // non-String params MUST be XStream'ed
						methodParameters[i] = (sparameter == null) ? null : xstream
								.fromXML(sparameter);
					}
					i++;
				}
				Object oresult = null;
				try {
					oresult = wfsMethod.invoke(wfs, methodParameters);
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				}
				if (oresult != null) {
					result = xstream.toXML(oresult);
				} else {
					result = "";
				}
			}
		}
		return result;
	}

	/**
	 * Calls a method of a target object with the given parameters.
	 * 
	 * @param target
	 *            the service interface that is to be called
	 * @param xstream
	 * @param method
	 * @return
	 */
	private String serviceMethodCall(Object target, String method, List<Object> methodParams) {
		String result = "";
		XStream xstream = getXStream();
		Method[] methods = target.getClass().getDeclaredMethods();
		Method wfsMethod = null;
		for (Method aMethod : methods) {
			if (StringUtils.equals(method, aMethod.getName())) {
				wfsMethod = aMethod;
			}
		}
		if (wfsMethod != null) {
			Object[] methodParameters = methodParams.toArray();

			Object oresult = null;
			try {
				oresult = wfsMethod.invoke(target, methodParameters);
			} catch (Exception e) {
				return "";
			}
			if (oresult != null) {
				result = xstream.toXML(oresult);
			} else {
				result = "";
			}
		}
		return result;
	}

	/**
	 * 
	 * Collects all non empty BlueXML properties available on completed (and in-progress) tasks of
	 * an instanceId.<br/>
	 * Parameters: "workflowId": the workflow instance id
	 * 
	 * @param wfs
	 *            the WorkflowService object
	 * @return a non null map of relevant properties.
	 */
	private Map<QName, Serializable> wfCollectInstanceProperties(WorkflowService wfs) {
		Map<QName, Serializable> properties = new HashMap<QName, Serializable>();
		String workflowId = parameters.get("workflowId");
		// build a query to get all completed tasks for the workflow instance id
		WorkflowTaskQuery queryCompleted = new WorkflowTaskQuery();
		queryCompleted.setProcessId(workflowId);
		queryCompleted.setTaskState(WorkflowTaskState.COMPLETED);
		List<WorkflowTask> tasksComplete = wfs.queryTasks(queryCompleted);
		// Also collect properties from in-progress forms because the workflow may be updated from
		// another interface than our forms, for instance, through Alfresco's web client
		WorkflowTaskQuery queryToDo = new WorkflowTaskQuery();
		queryToDo.setProcessId(workflowId);
		queryToDo.setTaskState(WorkflowTaskState.IN_PROGRESS);
		List<WorkflowTask> tasksToDo = wfs.queryTasks(queryToDo);
		// merge both lists
		List<WorkflowTask> tasks = new ArrayList<WorkflowTask>(tasksToDo.size()
				+ tasksComplete.size());
		tasks.addAll(tasksComplete);
		tasks.addAll(tasksToDo);
		// get the relevant properties
		for (WorkflowTask task : tasks) {
			Set<QName> qnames = task.properties.keySet();
			for (QName qname : qnames) {
				if (qname.getNamespaceURI().startsWith(BLUEXML_WORKFLOW_URI)) {
					Serializable value = task.properties.get(qname);
					if (value != null) {
						properties.put(qname, value);
					}
				}
			}
		}
		return properties;
	}

	/**
	 * Provides access to either the Alfresco service registry's services or Alfresco's beans.
	 * <p/>
	 * Parameters: "serviceName", "methodName", and "methodParams" (list of parameters specific to
	 * the method being called).
	 * <p/>
	 * Currently supported services/beans (<b>CASE SENSITIVE</b>): NodeService, DictionaryService,
	 * PersonService, AuthorityDAO. <br/>
	 * See the service-specific call functions for supported methods (which are also <b>CASE
	 * SENSITIVE</b>). Parameters to these functions must be of the right types and the list of
	 * parameters <b>MUST</b> have been xstreamed to XML at call time since it will be xstreamed
	 * from XML here.
	 * 
	 * @return the result of the call to the service's method, xstreamed to XML (even if the result
	 *         is a String), or null if an exception occurred.
	 */
	@SuppressWarnings("unchecked")
	protected String service() {
		String serviceName = parameters.get("serviceName");
		String methodName = parameters.get("methodName");
		String paramStr = parameters.get("methodParams");
		XStream xstream = getXStream();
		List<Object> methodParams = (List<Object>) xstream.fromXML(paramStr);
		Object result = null;
		try {
			if (StringUtils.equals(serviceName, "NodeService")) {
				result = callNodeService(methodName, methodParams);
			} else if (StringUtils.equals(serviceName, "DictionaryService")) {
				result = callDictionaryService(methodName, methodParams);
			} else if (StringUtils.equals(serviceName, "PersonService")) {
				result = callPersonService(methodName, methodParams);
			} else if (StringUtils.equals(serviceName, "AuthorityDAO")) {
				result = callAuthorityDAO(methodName, methodParams);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		String resultStr = xstream.toXML(result);
		return resultStr;
	}

	/**
	 * Executes and returns the result of a AuthorityDAO method. <br/>
	 * Supported methods: getAuthorityNodeRefOrNull, getAllAuthorities, getContainingAuthorities.
	 * 
	 * @param methodName
	 * @param methodParams
	 * @return
	 * @throws SecurityException
	 */
	private Object callAuthorityDAO(String methodName, List<Object> methodParams)
			throws SecurityException {
		AuthorityDAO authorityDAO = formsWebscript.getAuthorityDAO();
		Object[] paramArray = methodParams.toArray();
		if (StringUtils.equals(methodName, "getAuthorityNodeRefOrNull")) {
			String authorityName = (String) paramArray[0];
			return authorityDAO.getAuthorityNodeRefOrNull(authorityName);
		}
		if (StringUtils.equals(methodName, "getAllAuthorities")) {
			AuthorityType authorityType = (AuthorityType) paramArray[0];
			return authorityDAO.getAllAuthorities(authorityType);
		}
		if (StringUtils.equals(methodName, "getContainingAuthorities")) {
			AuthorityType authorityType = (AuthorityType) paramArray[0];
			String name = (String) paramArray[1];
			Boolean immediate = (Boolean) paramArray[2];
			return authorityDAO.getContainingAuthorities(authorityType, name, immediate);
		}
		return null;
	}

	/**
	 * Executes and returns the result of a DictionaryService method. Supported methods: getType.
	 * 
	 * @param methodName
	 * @param methodParams
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private Object callDictionaryService(String methodName, List<Object> methodParams)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		DictionaryService theService = serviceRegistry.getDictionaryService();
		Class<?> theClass = theService.getClass();
		Object[] paramArray = methodParams.toArray();
		if (StringUtils.equals(methodName, "getType")) {
			Method method = theClass.getMethod(methodName, new Class[] { QName.class });
			QName theType = (QName) paramArray[0];
			return method.invoke(theService, new Object[] { theType });
		}
		return null;
	}

	/**
	 * Supported methods: getProperty, getPath, getType.
	 * 
	 * @param methodName
	 * @param methodParams
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private Object callNodeService(String methodName, List<Object> methodParams)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		NodeService theService = serviceRegistry.getNodeService();
		Class<?> theClass = theService.getClass();
		Object[] paramArray = methodParams.toArray();
		if (StringUtils.equals(methodName, "getProperty")) {
			Method method = theClass.getMethod(methodName,
					new Class[] { NodeRef.class, QName.class });
			NodeRef ref = (NodeRef) paramArray[0];
			QName qname = (QName) paramArray[1];
			return method.invoke(theService, new Object[] { ref, qname });
		} else if (StringUtils.equals(methodName, "getPath")) {
			Method method = theClass.getMethod(methodName, new Class[] { NodeRef.class });
			NodeRef ref = (NodeRef) paramArray[0];
			return method.invoke(theService, new Object[] { ref });
		} else if (StringUtils.equals(methodName, "getType")) {
			Method method = theClass.getMethod(methodName, new Class[] { NodeRef.class });
			NodeRef ref = (NodeRef) paramArray[0];
			return method.invoke(theService, new Object[] { ref });
		}
		return null;
	}

	/**
	 * Calls a PersonService method.
	 * 
	 * @param methodName
	 *            the method to call, case-sensitive.
	 * @param methodParams
	 *            the parameters to pass to the method.
	 * @return the result of the call.
	 */
	private Object callPersonService(String methodName, List<Object> methodParams) {
		return serviceMethodCall(serviceRegistry.getPersonService(), methodName, methodParams);
	}

	/**
	 * Ensures a path exists.<br/>
	 * Parameters: "path" (e.g. /app:company_home/app:dictionary/cm:BXDSDATA)
	 * 
	 * @return a node ref to the deepest folder (e.g. "cm:BXDSDATA")
	 */
	protected String createPath() {
		NodeRef result;
		String path = parameters.get("path");
		try {
			result = dataLayer.createPath(path);
		} catch (Exception e) {
			logger.error("Error while creating path.", e);
			return "";
		}
		return result.toString();
	}

	/**
	 * Adds a content to a folder that already has the aspect "workflow package".<br/>
	 * If a valid package node was provided at call time, that same package is returned. Otherwise
	 * (i.e. if the package is <code>null</code>), a new package is created.<br/>
	 * Parameters: "content", "package"
	 * 
	 * @return the noderef (as a serialized XML string) of the package, or <code>null</code> if the
	 *         content is not effectively associated with the package when the job to be done is
	 *         over.
	 */
	protected String addInPackage() {
		String nodeStr = parameters.get("content");
		String packageStr = parameters.get("package");

		NodeRef wkPackage;
		if (packageStr == null) { // it's up to us to create the package
			wkPackage = serviceRegistry.getWorkflowService().createPackage(null);
		} else { // one was provided
			wkPackage = new NodeRef(packageStr);
		}
		String resultXStreamedPackage = xstream.toXML(wkPackage);

		if (nodeStr == null) { // #1284: workflows may have no attached data
			return resultXStreamedPackage;
		}
		NodeService nodeService = serviceRegistry.getNodeService();
		NodeRef noderef = new NodeRef(nodeStr);
		ChildAssociationRef childAssoc = nodeService.getPrimaryParent(noderef);
		nodeService
				.addChild(wkPackage, noderef, ContentModel.ASSOC_CONTAINS, childAssoc.getQName());

		// check that the association is effective
		List<ChildAssociationRef> assocs = nodeService.getChildAssocs(wkPackage);
		for (ChildAssociationRef asso : assocs) {
			if (asso.getChildRef().equals(noderef)) {
				return resultXStreamedPackage;
			}
		}
		return null;
	}

	/**
	 * Uploads an existing file to the repository at a specific location. Parameters: "filename"
	 * (name+extension), filepath (complete filesystem path, including filename and extension),
	 * "location" (path in the repository), "mimetype", "suffixAppend" (whether existing filenames
	 * should be appended with a number if not available, e.g. 'filename.ext' becomes 'filename
	 * (1).ext'. DEFAULTS to true)
	 * <p/>
	 * The file system path must be a valid path (not an escaped URL)<br/>
	 * .
	 * 
	 * @return the node ref (protocol, store and id) to the newly created node, or empty string if
	 *         any errors occur (e.g. the parent folder does not exist).
	 */
	protected String upload() {
		NodeRef parent, newNode = null;
		String FAILURE = "";

		String filename = parameters.get("filename");
		String filepath = parameters.get("filepath");
		String location = parameters.get("location");
		String mimeType = parameters.get("mimetype");
		String suffixAppend = parameters.get("suffixAppend");
		boolean shouldAppendSuffix = !(StringUtils.equals(suffixAppend, "false"));
		// test for the parent folder
		List<NodeRef> results = null;
		try {
			results = dataLayer.request(location);
		} catch (Exception e) {
			logger.error("Error while testing existence of upload location: " + location, e);
			return FAILURE;
		}
		if (results.size() == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug(location + " does not exist: trying to create it.");
			}
			try {
				NodeRef createdPath = dataLayer.createPath(location);
				results = new ArrayList<NodeRef>(1);
				results.add(createdPath);
				if (logger.isDebugEnabled()) {
					logger.debug("Suceeded in creating upload location '" + location + "'.");
				}
			} catch (Exception e) {
				logger.error("Creation of '" + location + "' failed. Giving up on the upload.", e);
				return FAILURE;
			}
		}
		parent = results.get(0);
		// set node type and other qnames
		QName assocTypeQName = ContentModel.ASSOC_CONTAINS;
		QName assocQName = QName.createQName("{http://www.alfresco.org/model/content/1.0}"
				+ filename);
		QName nodeTypeQName = ContentModel.PROP_CONTENT;
		// create the node
		newNode = serviceRegistry.getNodeService().createNode(parent, assocTypeQName, assocQName,
				nodeTypeQName).getChildRef();

		return dataLayer.uploadContentToNode(newNode, filename, filepath, mimeType, nodeTypeQName,
				true, shouldAppendSuffix);
	}

	private void createdToXML(StringBuffer sb, Map<String, String> created) {
		exceptionToStringOpenTag("ids", sb);

		Set<Entry<String, String>> entrySet = created.entrySet();
		for (Entry<String, String> entry : entrySet) {
			exceptionToStringOpenTag("id", sb);
			exceptionToStringAddEntry("from", entry.getKey(), sb);
			exceptionToStringAddEntry("to", entry.getValue(), sb);
			exceptionToStringCloseTag("id", sb);
		}

		exceptionToStringCloseTag("ids", sb);
	}

	private void replaceIds(Element element, Map<String, String> created) {
		if (StringUtils.equals(element.getTagName(), "target")
				|| StringUtils.equals(element.getTagName(), "associationClass")) {
			String newId = created.get(element.getTextContent());
			if (newId != null) {
				element.setTextContent(newId);
			}
		}
		String elementId = element.getAttribute("id");
		String newId = created.get(elementId);
		if (newId != null) {
			element.setAttribute("id", newId);
		}
		List<Element> children = DOMUtil.getAllChildren(element);
		for (Element child : children) {
			replaceIds(child, created);
		}
	}

	private LuceneSearcher getUnsecureLuceneSearcher() {
		LuceneIndexer indexer = formsWebscript.getIndexerAndSearcherFactory().getIndexer(
				formsWebscript.getStoreRef());
		LuceneConfig config = formsWebscript.getIndexerAndSearcherFactory();
		ADMLuceneSearcherImpl searcher = ADMLuceneSearcherImpl.getSearcher(formsWebscript
				.getStoreRef(), indexer, config);
		searcher.setTenantService(formsWebscript.getTenantService());
		searcher.setDictionaryService(serviceRegistry.getDictionaryService());
		searcher.setNamespacePrefixResolver(formsWebscript.getNamespacePrefixResolver());
		return searcher;
	}

	/**
	 * Builds a search string with a type clause and one clause per search value.
	 * 
	 * @param type
	 * @param searchedValues
	 * @return
	 */
	private StringBuilder getLuceneQuery(String type, List<String> searchedValues) {
		StringBuilder query = new StringBuilder();

		List<QName> subTypes = formsWebscript.getSubTypes(type);

		// the type clause
		query.append("(");
		for (Iterator<QName> iterator = subTypes.iterator(); iterator.hasNext();) {
			QName name = iterator.next();
			query.append("TYPE:\"");
			query.append(name.toString());
			query.append("\"");
			if (iterator.hasNext()) {
				query.append(" OR ");
			}
		}
		query.append(")");

		// add one clause per significant search value
		for (String strRequest : searchedValues) {
			StringBuilder clause = getLuceneClauseForSearchValue(subTypes.get(0), strRequest);
			if (clause.length() > 0) {
				query.append(" AND ");
				query.append(clause);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Built Lucene query: -->" + query + "<--");
		}

		return query;
	}

	/**
	 * Gets a search string fragment in which search criteria match the given type's searchable
	 * attributes with the search keyword.
	 * 
	 * @param mainTypeQName
	 *            the type of nodes being searched
	 * @param keyword
	 *            the search keyword
	 * @return the search string fragment surrounded by parentheses, or empty string if no
	 *         searchable attributes are found.
	 */
	private StringBuilder getLuceneClauseForSearchValue(QName mainTypeQName, String keyword) {
		StringBuilder query = new StringBuilder();

		if (StringUtils.trimToNull(keyword) != null) {
			String escapedRequest = QueryParser.escape(keyword);
			Set<QName> attributesArray = getSearchableAttributes(mainTypeQName);

			Iterator<QName> it = attributesArray.iterator();

			if (it.hasNext()) { // at least one searchable attribute before returning non-empty
				query.append("(");
				while (it.hasNext()) {
					String attribute = it.next().toString();
					appendAttribute(query, attribute);
					query.append("*").append(escapedRequest).append("*");
					query.append(" OR ");
					appendAttribute(query, attribute);
					query.append("\"").append(escapedRequest).append("\"");
					if (it.hasNext()) {
						query.append(" OR ");
					}
				}
				query.append(")");
			}
		}
		return query;
	}

	private void appendAttribute(StringBuilder query, String attribute) {
		query.append("@");
		query.append(attribute.replace(":", "\\:").replace("}", "\\}").replace("{", "\\{"));
		query.append(":");
	}

	/**
	 * Gets the set of searchable attributes for the given type. If the set does not exist, it will
	 * be computed and registered in a cache.
	 * 
	 * @param type
	 * @return the set
	 */
	private synchronized Set<QName> getSearchableAttributes(QName type) {
		Set<QName> result = searchableAttributesCache.get(type);
		if (result == null) {
			result = computeSearchableAttributes(type);
			searchableAttributesCache.put(type, result);
		}
		return result;
	}

	/**
	 * Collects all searchable properties for a given type, whether they a defined in the type or in
	 * a related aspects or in a parent type or in one the parent's aspects.
	 * 
	 * @param pCurrentType
	 *            the type to start from. It is the bottom-most type in the hierarchy of collected
	 *            types.
	 * @return the set of searchable attributes
	 */
	private Set<QName> computeSearchableAttributes(QName pCurrentType) {
		QName currentType = pCurrentType;
		List<QName> classTypes = new ArrayList<QName>();

		// we collect the hierarchy of parent classes
		while (currentType.getNamespaceURI().startsWith(BLUEXML_MODEL_URI)) {
			classTypes.add(currentType);
			TypeDefinition nodeRefTypeDefinition = serviceRegistry.getDictionaryService().getType(
					currentType);
			QName parentType = nodeRefTypeDefinition.getParentName();
			currentType = parentType;
		}

		// the list of searchable properties from all types in the hierarchy
		Map<QName, PropertyDefinition> typeProperties = new HashMap<QName, PropertyDefinition>();

		// we look into the definition of each collected type for the searchable properties
		for (QName type : classTypes) {
			TypeDefinition typeDefinition = serviceRegistry.getDictionaryService().getType(type);
			addSearchableAttributes(typeProperties, typeDefinition.getProperties());
			for (AspectDefinition ad : typeDefinition.getDefaultAspects()) {
				addSearchableAttributes(typeProperties, ad.getProperties());
			}
		}
		return typeProperties.keySet();
	}

	/**
	 * Filters the definition properties and adds the seachable ones to the selection.
	 * 
	 * @param typeProperties
	 *            the selection (of searchable properties) that is being built
	 * @param properties
	 *            the properties from the type dictionary definition
	 */
	private void addSearchableAttributes(Map<QName, PropertyDefinition> typeProperties,
			Map<QName, PropertyDefinition> properties) {
		Set<Entry<QName, PropertyDefinition>> propertiesEntrySet = properties.entrySet();
		for (Entry<QName, PropertyDefinition> entry : propertiesEntrySet) {
			PropertyDefinition value = entry.getValue();
			QName key = entry.getKey();
			if (value.isIndexed() && key.getNamespaceURI().startsWith(BLUEXML_MODEL_URI)) {
				typeProperties.put(key, value);
			}
		}
	}

	// %% changed visibility to private
	private SearchParameters createSearchParameters(String type, List<String> searchedValues,
			int maxResults) {
		return createSearchParameters(getLuceneQuery(type, searchedValues), maxResults);
	}

	// %%
	private SearchParameters createSearchParameters(StringBuilder query, int maxResults) {
		// setup search parameters, including limiting the results
		SearchParameters searchParams = new SearchParameters();
		searchParams.addStore(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE);
		searchParams.setLanguage(SearchService.LANGUAGE_LUCENE);
		searchParams.setQuery(query.toString());
		if (maxResults > 0) {
			searchParams.setLimit(maxResults);
			searchParams.setLimitBy(LimitBy.FINAL_SIZE);
		}
		return searchParams;
	}

}
