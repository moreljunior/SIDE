/*
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
*/
package com.bluexml.side.framework.alfresco.shareLanguagePicker;

import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.surf.FrameworkUtil;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.exception.ConnectorServiceException;
import org.springframework.extensions.surf.util.URLEncoder;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.connector.Connector;
import org.springframework.extensions.webscripts.connector.Response;
import org.springframework.extensions.webscripts.connector.User;

import com.bluexml.side.framework.alfresco.shareLanguagePicker.connector.MyHttpRequestServletAdaptator;
/**
 * SIDE Extension
 * Methods to compute Local
 * @author davidabad
 */
public class LanguageSetter {
	private static final String ACCEPT_LANGUAGE = "Accept-Language";
	public static final String SHARE_LANG = "shareLang";
	private static final Log logger = LogFactory.getLog(LanguageSetter.class);
	static String preferences_values = "{http://www.alfresco.org/model/content/1.0}preferenceValues";
	public static String profile_language = "user_language";
	public static String userSessionok = "userSessionLanguageSets";

	public static String getLanguageFromLayoutParam(HttpServletRequest req, RequestContext context) throws ServletException {
		logger.debug("# getLanguageFromLayoutParam ...");
		User user = context.getUser();
		String userId = context.getUserId();
		String urlLang = req.getParameter(SHARE_LANG);
		HttpSession currentSession = req.getSession();
		String sessionLang = (String) currentSession.getAttribute(SHARE_LANG);
		if (logger.isTraceEnabled()) {
			logger.trace("urlLang : " + urlLang);
			logger.trace("sessionLang : " + sessionLang);
		}

		// set language

		String language = null;
		// is no user or guest use browser language
		if (user != null && !userId.toLowerCase().equals("guest")) {
			if (urlLang != null) {
				language = urlLang;
				if (logger.isDebugEnabled()) {
					logger.debug("Use language in url :" + urlLang);
				}
			} else if (sessionLang != null && currentSession.getAttribute(userSessionok) != null) {
				// only if language already loaded from preference
				if (logger.isDebugEnabled()) {
					logger.debug("Use language stored in session :" + sessionLang);
				}
				language = sessionLang;

			} else {
				// initialize user language
				try {
					language = getUserLanguage(userId, req);
					logger.debug("Use language from user preferences :" + language);

				} catch (ConnectorServiceException e) {
					throw new ServletException(e);
				} catch (JSONException e) {
					throw new ServletException(e);
				}
				if (language == null) {
					// language not yet set so use default from browser
					language = getLanguageFromBrowser(req);
					logger.debug("Use language from browser :" + language);
				}
				currentSession.setAttribute(userSessionok, true);
			}
		} else {
			// language not yet set so use default from browser
			language = getLanguageFromBrowser(req);
			logger.debug("no user or Guest, use language from browser :" + language);
		}

		return language;

	}

	private static String getLanguageFromBrowser(HttpServletRequest req) {
		// set language locale from browser header
		String acceptLang = req.getHeader(ACCEPT_LANGUAGE);
		String language = null;
		if (acceptLang != null && acceptLang.length() != 0) {
			StringTokenizer t = new StringTokenizer(acceptLang, ",; ");
			// get language and convert to java locale format
			language = t.nextToken().replace('-', '_');
		} else {
			language = Locale.getDefault().getLanguage();
		}
		return language;
	}

	protected static String getUserLanguage(String currentUserId, HttpServletRequest req) throws ConnectorServiceException, ServletException, JSONException {
		// get a connector whose connector session is bound to the current
		// session
		Connector connector = FrameworkUtil.getConnector(req.getSession(), currentUserId, "alfresco");

		// build the REST URL to retrieve user preferences
		String uri = "/api/people/" + URLEncoder.encode(currentUserId) + "/preferences?pf=" + profile_language;

		// invoke and check for OK response
		Response response = connector.call(uri);
		if (Status.STATUS_OK != response.getStatus().getCode()) {
			throw new ServletException("failed to retrieve user preferences: " + response.getStatus().getMessage(), (Exception) response.getStatus().getException());
		}

		// Load the user properties via the JSON parser
		String responseString = response.getResponse();
		JSONObject root = new JSONObject(responseString);
		String language = null;
		if (!root.isNull(profile_language)) {
			language = root.getString(profile_language);
		}

		return language;
	}

	/**
	 * 
	 * @param req
	 * @return
	 */
	public static HttpServletRequest getMyRequestInstance(HttpServletRequest req) {
		// we dont use getLanguageFromLayoutParam because this may cause loop getLanguageFromLayoutParam -> alfrescoConnector -> getMyRequestInstance -> getLanguageFromLayoutParam
		String value = (String) req.getSession().getAttribute(LanguageSetter.SHARE_LANG);
		if (StringUtils.trimToNull(value) != null) {
			MyHttpRequestServletAdaptator myreq = new MyHttpRequestServletAdaptator(req);
			String key = ACCEPT_LANGUAGE;

			String replace = value.replace('_', '-');
			logger.debug("getMyRequestInstance set Language to " + replace);
			myreq.setHeader(key, replace);
			return myreq;
		} else {
			logger.debug("getMyRequestInstance no language stored in session use ");
		}
		
		return req;
		
	}
}
