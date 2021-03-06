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
// Copyright 2001-2007 ChibaXForms GmbH
/*
 *
 *    Artistic License
 *
 *    Preamble
 *
 *    The intent of this document is to state the conditions under which a Package may be copied, such that
 *    the Copyright Holder maintains some semblance of artistic control over the development of the
 *    package, while giving the users of the package the right to use and distribute the Package in a
 *    more-or-less customary fashion, plus the right to make reasonable modifications.
 *
 *    Definitions:
 *
 *    "Package" refers to the collection of files distributed by the Copyright Holder, and derivatives
 *    of that collection of files created through textual modification.
 *
 *    "Standard Version" refers to such a Package if it has not been modified, or has been modified
 *    in accordance with the wishes of the Copyright Holder.
 *
 *    "Copyright Holder" is whoever is named in the copyright or copyrights for the package.
 *
 *    "You" is you, if you're thinking about copying or distributing this Package.
 *
 *    "Reasonable copying fee" is whatever you can justify on the basis of media cost, duplication
 *    charges, time of people involved, and so on. (You will not be required to justify it to the
 *    Copyright Holder, but only to the computing community at large as a market that must bear the
 *    fee.)
 *
 *    "Freely Available" means that no fee is charged for the item itself, though there may be fees
 *    involved in handling the item. It also means that recipients of the item may redistribute it under
 *    the same conditions they received it.
 *
 *    1. You may make and give away verbatim copies of the source form of the Standard Version of this
 *    Package without restriction, provided that you duplicate all of the original copyright notices and
 *    associated disclaimers.
 *
 *    2. You may apply bug fixes, portability fixes and other modifications derived from the Public Domain
 *    or from the Copyright Holder. A Package modified in such a way shall still be considered the
 *    Standard Version.
 *
 *    3. You may otherwise modify your copy of this Package in any way, provided that you insert a
 *    prominent notice in each changed file stating how and when you changed that file, and provided that
 *    you do at least ONE of the following:
 *
 *        a) place your modifications in the Public Domain or otherwise make them Freely
 *        Available, such as by posting said modifications to Usenet or an equivalent medium, or
 *        placing the modifications on a major archive site such as ftp.uu.net, or by allowing the
 *        Copyright Holder to include your modifications in the Standard Version of the Package.
 *
 *        b) use the modified Package only within your corporation or organization.
 *
 *        c) rename any non-standard executables so the names do not conflict with standard
 *        executables, which must also be provided, and provide a separate manual page for each
 *        non-standard executable that clearly documents how it differs from the Standard
 *        Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    4. You may distribute the programs of this Package in object code or executable form, provided that
 *    you do at least ONE of the following:
 *
 *        a) distribute a Standard Version of the executables and library files, together with
 *        instructions (in the manual page or equivalent) on where to get the Standard Version.
 *
 *        b) accompany the distribution with the machine-readable source of the Package with
 *        your modifications.
 *
 *        c) accompany any non-standard executables with their corresponding Standard Version
 *        executables, giving the non-standard executables non-standard names, and clearly
 *        documenting the differences in manual pages (or equivalent), together with instructions
 *        on where to get the Standard Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    5. You may charge a reasonable copying fee for any distribution of this Package. You may charge
 *    any fee you choose for support of this Package. You may not charge a fee for this Package itself.
 *    However, you may distribute this Package in aggregate with other (possibly commercial) programs as
 *    part of a larger (possibly commercial) software distribution provided that you do not advertise this
 *    Package as a product of your own.
 *
 *    6. The scripts and library files supplied as input to or produced as output from the programs of this
 *    Package do not automatically fall under the copyright of this Package, but belong to whomever
 *    generated them, and may be sold commercially, and may be aggregated with this Package.
 *
 *    7. C or perl subroutines supplied by you and linked into this Package shall not be considered part of
 *    this Package.
 *
 *    8. The name of the Copyright Holder may not be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *    9. THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 *    WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 *    MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 */
// Copyright 2005 Chibacon Liss�/Turner GbR
package org.chiba.agent.web.servlet;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chiba.agent.web.servlet.compositecontrols.CompositeControlFactory;
import org.chiba.agent.web.servlet.compositecontrols.CompositeControlValue;
import org.chiba.agent.web.session.XFormsSession;
import org.chiba.agent.web.upload.MonitoredDiskFileItemFactory;
import org.chiba.agent.web.upload.UploadInfo;
import org.chiba.agent.web.upload.UploadListener;
import org.chiba.processor.XFormsProcessor;
import org.chiba.xml.events.DOMEventNames;
import org.chiba.xml.xforms.XFormsElement;
import org.chiba.xml.xforms.config.Config;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xforms.ui.AbstractFormControl;

/**
 * Default implementation for handling HTTP requests.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: HttpRequestHandler.java 2875 2007-09-28 09:43:30Z lars $
 */
public class HttpRequestHandler {
    private static final Log LOGGER = LogFactory.getLog(HttpRequestHandler.class);

    // todo: ioc
    public static final String DATA_PREFIX_PROPERTY = "chiba.web.dataPrefix";
    public static final String TRIGGER_PREFIX_PROPERTY = "chiba.web.triggerPrefix";
    public static final String SELECTOR_PREFIX_PROPERTY = "chiba.web.selectorPrefix";
    public static final String REMOVE_UPLOAD_PREFIX_PROPERTY = "chiba.web.removeUploadPrefix";
    public static final String DAYTIMEDURATION_PREFIX_PROPERTY = "chiba.web.dayTimeDurationPrefix";
    public static final String DATETIME_PREFIX_PROPERTY = "chiba.web.dateTimePrefix";
    public static final String DATA_PREFIX_DEFAULT = "d_";
    public static final String TRIGGER_PREFIX_DEFAULT = "t_";
    public static final String SELECTOR_PREFIX_DEFAULT = "s_";
    public static final String REMOVE_UPLOAD_PREFIX_DEFAULT = "ru_";

    // todo: remove
    private String removeUploadPrefix;

    private XFormsProcessor xformsProcessor;
    private String uploadRoot;
    private String sessionKey;
    private String dataPrefix;
    private String selectorPrefix;
    private String triggerPrefix;

    //temporary storage for composite controls
    private HashMap compositeControlValues = new HashMap();


    //    public HttpRequestHandler(ChibaBean chibaBean) {
    public HttpRequestHandler(XFormsProcessor aXFormsProcessor) {
        this.xformsProcessor = aXFormsProcessor;
    }

    public void setUploadRoot(String uploadRoot) {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("Upload will be stored to: " + uploadRoot);
        }
        this.uploadRoot = uploadRoot;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * Handles a HTTP request.
     * <p/>
     * After parsing the request will processed in following steps:
     * <ol>
     * <li>Upload controls are updated if any.</li>
     * <li>All other controls are updated if any changes arrive with the request.</li>
     * <li>Repeat indices are updated if any.</li>
     * <li>Triggers are activated if any.</li>
     * </ol>
     * <p/>
     * <b>Note:</b> In case the request is <code>multipart/form-data</code>-encoded,
     * it will be processed with <code>org.apache.commons.fileupload.FileUpload</code>
     * which appears to <i>consume</i> all request parameters.
     *
     * @param request a HTTP request.
     * @throws XFormsException if any error occurred during request processing.
     */
    public void handleRequest(HttpServletRequest request) throws XFormsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("handle request: " + request.getRequestURI());
        }

        Map[] parameters;
        try {
            parameters = parseRequest(request);
        }
        catch (Exception e) {
            throw new XFormsException("could not parse request", e);
        }

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("parameter: " + parameters[0]);
        }
        // todo: implement action block behaviour ?
        if (parameters[0] != null) {
            processUploadParameters(parameters[0], request);
        }
        if (parameters[1] != null) {
            processControlParameters(parameters[1]);
        }
        if (parameters[2] != null) {
            processRepeatParameters(parameters[2]);
        }
        if (parameters[3] != null) {
            processTriggerParameters(parameters[3]);
        }
    }

    public void handleUpload(HttpServletRequest request) throws XFormsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("handle request: " + request.getRequestURI());
        }

        Map[] parameters;
        try {
            parameters = parseRequest(request);
        }
        catch (Exception e) {
            throw new XFormsException("could not parse request", e);
        }
        if (parameters[0] != null) {
            processUploadParameters(parameters[0], request);
        }
    }


    /**
     * Parses a HTTP request. Returns an array containing maps for upload
     * controls, other controls, repeat indices, and trigger. The individual
     * maps may be null in case no corresponding parameters appear in the
     * request.
     *
     * @param request a HTTP request.
     * @return an array of maps containing the parsed request parameters.
     * @throws FileUploadException          if an error occurred during file upload.
     * @throws UnsupportedEncodingException if an error occurred during
     *                                      parameter value decoding.
     */
    protected Map[] parseRequest(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException {
        Map[] parameters = new Map[4];

        if (FileUpload.isMultipartContent(new ServletRequestContext(request))) {
            UploadListener uploadListener = new UploadListener(request, this.sessionKey);
            DiskFileItemFactory factory = new MonitoredDiskFileItemFactory(uploadListener);
            factory.setRepository(new File(this.uploadRoot));
            ServletFileUpload upload = new ServletFileUpload(factory);

            String encoding = request.getCharacterEncoding();
            if (encoding == null) {
                encoding = "UTF-8";
            }

            Iterator iterator = upload.parseRequest(request).iterator();
            FileItem item;
            while (iterator.hasNext()) {
                item = (FileItem) iterator.next();
                if (LOGGER.isDebugEnabled()) {
                    if (item.isFormField()) {
                        LOGGER.debug("request param: " + item.getFieldName() + " - value='" + item.getString() + "'");
                    } else {
                        LOGGER.debug("file in request: " + item.getName());
                    }

                }
                parseMultiPartParameter(item, encoding, parameters);
            }
        } else {
            Enumeration enumeration = request.getParameterNames();
            String name;
            String[] values;
            while (enumeration.hasMoreElements()) {
                name = (String) enumeration.nextElement();
                values = request.getParameterValues(name);

                parseURLEncodedParameter(name, values, parameters);
            }
        }

        return parameters;
    }

    /**
     * Parses a <code>application/x-www-form-urlencoded</code>-encoded request
     * parameter and stores it in the parameter map.
     *
     * @param name       the paremeter name.
     * @param values     the paremeter value(s).
     * @param parameters the parameters map.
     */
    protected void parseURLEncodedParameter(String name, String[] values, Map[] parameters) {
        if (name.startsWith(getDataPrefix()) || name.startsWith(CompositeControlValue.prefix)) {
            StringBuffer buffer = new StringBuffer(values[0]);
            for (int index = 1; index < values.length; index++) {
                buffer.append(" ").append(values[index]);
            }

            parameters[1] = parseControlParameter(name, buffer.toString().trim(), parameters[1]);
        } else if (name.startsWith(getSelectorPrefix())) {
            parameters[2] = parseRepeatParameter(name, values[0], parameters[2]);
        } else if (name.startsWith(getTriggerPrefix())) {
            parameters[3] = parseTriggerParameter(name, values[0], parameters[3]);
        }
    }

    /**
     * Parses a <code>multipart/form-data</code>-encoded request parameter and
     * stores it in the parameter map.
     *
     * @param item       the uploaded file item.
     * @param encoding   the parameter encoding.
     * @param parameters the parameters map.
     * @throws UnsupportedEncodingException if an error occurred during
     *                                      parameter value decoding.
     */
    protected void parseMultiPartParameter(FileItem item, String encoding, Map[] parameters) throws UnsupportedEncodingException {
        String name = item.getFieldName();
        if (name.startsWith(getDataPrefix()) || name.startsWith(CompositeControlValue.prefix)) {
            if (item.isFormField()) {
                parameters[1] = parseControlParameter(name, item.getString(encoding), parameters[1]);
            } else {
                parameters[0] = parseUploadParameter(name, item, parameters[0]);
            }
        } else if (name.startsWith(getSelectorPrefix())) {
            parameters[2] = parseRepeatParameter(name, item.getString(encoding), parameters[2]);
        } else if (name.startsWith(getTriggerPrefix())) {
            parameters[3] = parseTriggerParameter(name, item.getString(encoding), parameters[3]);
        }
    }

    protected Map parseUploadParameter(String name, FileItem item, Map uploads) {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("Upload param-name: " + name);
            LOGGER.debug("Upload item size: " + item.getSize() + " bytes");
        }
        if (uploads == null) {
            uploads = new HashMap();
        }

        String id = name.substring(getDataPrefix().length());
        uploads.put(id, item);
        return uploads;
    }

    protected Map parseControlParameter(String name, String value, Map controls) {
        if (controls == null) {
            controls = new HashMap();
        }

        //id number of the control
        String id = null;

        //is this a composite or standard control?
        if (name.startsWith(CompositeControlValue.prefix)) {
            //composite bound control

            id = name.substring(name.lastIndexOf('_') + 1);

            //get the control
            CompositeControlValue ccValue = (CompositeControlValue) compositeControlValues.get(id);
            if (ccValue == null)
                ccValue = CompositeControlFactory.createCompositeControl(name);

            //set this part of the composite value
            ccValue.setPart(name, value);

            //is the composite value complete?
            if (ccValue.isComplete()) {
                //yes, set the composite value
                value = ccValue.toString();
                compositeControlValues.remove(id);
            } else {
                //no, store the currect composite control for the next pass
                compositeControlValues.put(id, ccValue);
                return controls;
            }
        } else {
            //standard bound control
            id = name.substring(getDataPrefix().length());
        }

        //get existing list of values
        String list = (String) controls.get(id);
        if (list == null) {
            //set the value
            list = value;
        } else {
            //add the value to the list
            list = list.concat(" ").concat(value).trim();
        }

        //store the controls updated value
        controls.put(id, list);

        return controls;
    }

    protected Map parseRepeatParameter(String name, String value, Map repeats) {
        if (repeats == null) {
            repeats = new HashMap();
        }

        int separator = value.lastIndexOf(':');
        String id = value.substring(0, separator);
        String index = value.substring(separator + 1);

        repeats.put(id, index);
        return repeats;
    }

    protected Map parseTriggerParameter(String name, String value, Map trigger) {
        if (trigger == null) {
            trigger = new HashMap();
        }

        String id = name.substring(getTriggerPrefix().length());
        int x = id.lastIndexOf(".x");
        if (x > -1) {
            id = id.substring(0, x);
        }
        int y = id.lastIndexOf(".y");
        if (y > -1) {
            id = id.substring(0, y);
        }

        trigger.put(id, DOMEventNames.ACTIVATE);
        return trigger;
    }

    protected void processUploadParameters(Map uploads, HttpServletRequest request) throws XFormsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("updating " + uploads.keySet().size() + " uploads(s)");
        }

        try {
            // update repeat indices
            Iterator iterator = uploads.keySet().iterator();
            String id;
            FileItem item;
            byte[] data;

            while (iterator.hasNext()) {
                id = (String) iterator.next();
                item = (FileItem) uploads.get(id);


                if (item.getSize() > 0) {
                    LOGGER.debug("i'm here");
                    String name = item.getName();
                    if (name.contains("/")) {
                    	name = name.substring(name.lastIndexOf('/') + 1);
                    }
                    if (name.contains("\\")) {
                    	name = name.substring(name.lastIndexOf('\\') + 1);
                    }
					if (this.xformsProcessor.isFileUpload(id, "anyURI")) {
                        if(LOGGER.isDebugEnabled()){
                            LOGGER.debug("found upload type 'anyURI'");
                        }
                        
                        String localPath = new StringBuffer()
                                .append(System.currentTimeMillis())
                                .append('/')
                                .append(name)
                                .toString();


                        File localFile = new File(this.uploadRoot, localPath);
                        localFile.getParentFile().mkdirs();
                        item.write(localFile);

                        if(LOGGER.isDebugEnabled()){
                            LOGGER.debug("saving data to path: " + localFile);
                        }


                        // todo: externalize file handling and uri generation
                        data = localFile.toURI().toString().getBytes("UTF-8");
                    } else {
                        data = item.get();
                    }

                    this.xformsProcessor.setUploadValue(id, item.getContentType(), name, data);

                    // After the value has been set and the RRR took place, create new UploadInfo with status set to 'done'
                    request.getSession().setAttribute(XFormsSession.ADAPTER_PREFIX + sessionKey + "-uploadInfo", new UploadInfo(1, 0, 0, 0, "done"));
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("ignoring empty upload " + id);
                    }
                    // todo: removal ?
                }

                item.delete();
            }
        }
        catch (Exception e) {
        	LOGGER.error(e);
            throw new XFormsException(e);
        }
    }

    protected void processControlParameters(Map controls) throws XFormsException {
        // first filter out all unchanged controls ...
        Iterator iterator = controls.keySet().iterator();
        String id;
        String value;
        int unchanged = 0;
        while (iterator.hasNext()) {
            id = (String) iterator.next();
            value = (String) controls.get(id);

            if (!this.hasControlChanged(id, value)) {
                controls.put(id, null);
                unchanged++;
            }
        }

        if (LOGGER.isDebugEnabled()) {
            int all = controls.keySet().size();
            int changed = all - unchanged;
            if (changed > 0) {
                LOGGER.debug("updating " + changed + " of " + all + " control(s)");
            }
        }

        // ... then update changed controls to avoid side-effects
        iterator = controls.keySet().iterator();
        while (iterator.hasNext()) {
            id = (String) iterator.next();
            value = (String) controls.get(id);

            if (value != null) {
                this.xformsProcessor.setControlValue(id, value);
            }
        }
    }

    /**
     * Checks wether the value of the specified form control might have changed.
     *
     * @param id    the id of the form control.
     * @param value the value to check.
     * @return <code>true</code> if the given value differs from the specified
     *         control's value, otherwise <code>false</code>.
     * @throws XFormsException if no document container is present or the
     *                         control is unknown.
     */

    private final boolean hasControlChanged(String id, String value) throws XFormsException {
        // sanity checks
        XFormsElement element = this.xformsProcessor.lookup(id);
        if (element == null || !(element instanceof AbstractFormControl)) {
            throw new XFormsException("id '" + id + "' does not identify a form control");
        }

        // check control value
        AbstractFormControl control = (AbstractFormControl) element;
        Object controlValue = control.getValue();
        if (controlValue == null) {
            // prevents controls being not bound or disabled from updates
            return false;
        }
        //todo: rather doubt that this equality check works correct
        return !controlValue.equals(value);
    }


    protected void processRepeatParameters(Map repeats) throws XFormsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("updating " + repeats.keySet().size() + " repeat(s)");
        }

        // update repeat indices
        Iterator iterator = repeats.keySet().iterator();
        String id;
        int index;
        while (iterator.hasNext()) {
            id = (String) iterator.next();
            index = Integer.parseInt((String) repeats.get(id));

            // todo: change detection ?
            this.xformsProcessor.setRepeatIndex(id, index);
        }
    }

    protected void processTriggerParameters(Map trigger) throws XFormsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("activating " + trigger.keySet().size() + " trigger");
        }

        // update repeat indices
        Iterator iterator = trigger.keySet().iterator();
        String id;
        String event;
        while (iterator.hasNext()) {
            id = (String) iterator.next();
            event = (String) trigger.get(id);

            this.xformsProcessor.dispatch(id, event);
        }
    }

    // todo: remove and introduce setters (ioc)
    protected final String getTriggerPrefix() {
        if (this.triggerPrefix == null) {
            try {
                this.triggerPrefix = Config.getInstance().getProperty(TRIGGER_PREFIX_PROPERTY, TRIGGER_PREFIX_DEFAULT);
            }
            catch (Exception e) {
                this.triggerPrefix = TRIGGER_PREFIX_DEFAULT;
            }
        }

        return this.triggerPrefix;
    }

    protected final String getDataPrefix() {
        if (this.dataPrefix == null) {
            try {
                this.dataPrefix = Config.getInstance().getProperty(DATA_PREFIX_PROPERTY, DATA_PREFIX_DEFAULT);
            }
            catch (Exception e) {
                this.dataPrefix = DATA_PREFIX_DEFAULT;
            }
        }

        return this.dataPrefix;
    }

    protected final String getRemoveUploadPrefix() {
        if (this.removeUploadPrefix == null) {
            try {
                this.removeUploadPrefix = Config.getInstance().getProperty(REMOVE_UPLOAD_PREFIX_PROPERTY, REMOVE_UPLOAD_PREFIX_DEFAULT);
            }
            catch (Exception e) {
                this.removeUploadPrefix = REMOVE_UPLOAD_PREFIX_DEFAULT;
            }
        }

        return this.removeUploadPrefix;
    }

    protected final String getSelectorPrefix() {
        if (this.selectorPrefix == null) {
            try {
                this.selectorPrefix = Config.getInstance().getProperty(SELECTOR_PREFIX_PROPERTY, SELECTOR_PREFIX_DEFAULT);
            }
            catch (Exception e) {
                this.selectorPrefix = SELECTOR_PREFIX_DEFAULT;
            }
        }

        return this.selectorPrefix;
    }
}

// end of class
