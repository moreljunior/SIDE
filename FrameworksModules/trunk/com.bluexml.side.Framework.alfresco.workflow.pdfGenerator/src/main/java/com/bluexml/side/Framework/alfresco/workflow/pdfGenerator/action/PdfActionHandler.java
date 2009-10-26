/**
 * 
 */
package com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.repo.workflow.jbpm.JBPMSpringActionHandler;
import org.alfresco.service.ServiceRegistry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.jbpm.graph.exe.ExecutionContext;
import org.springframework.beans.factory.BeanFactory;

import com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.exception.DuplicateInputPdfException;
import com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.exception.DuplicateOutputContentException;
import com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.exception.EmptyKeyActionException;
import com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.exception.EmptyScriptException;
import com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.exception.MissingInputPdfKeyException;
import com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.exception.MissingOutputContentException;
import com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.exception.NoContentException;
import com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.exception.NoPdfFileException;
import com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.exception.ValueActionException;
import com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.generate.FillContent;
import com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.generate.FillPDF;
import com.bluexml.side.Framework.alfresco.workflow.pdfGenerator.language.ConstantsLanguage;

/**
 * @author dchevrier
 *
 */
public class PdfActionHandler extends JBPMSpringActionHandler {

	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(PdfActionHandler.class);
	
	private Element script;
	private FillPDF fillPdf;
	private FillContent fillContent;
	
	private ServiceRegistry services;
	
	public void setFillPdf(FillPDF fillPdf) {
		this.fillPdf = fillPdf;
	}

	public void setFillContent(FillContent fillContent) {
		this.fillContent = fillContent;
	}

	public void setServices(ServiceRegistry services) {
		this.services = services;
	}

	@Override
	protected void initialiseHandler(BeanFactory factory) {
		setServices((ServiceRegistry) factory.getBean(ServiceRegistry.SERVICE_REGISTRY));
		fillContent = new FillContent();
		fillContent.setServiceRegistry(services);
		fillPdf = new FillPDF();
	}

	public void execute(ExecutionContext executionContext) {
		Map<String, String> commands = null;
		try {
			commands = getScriptAsKeysValues();
		} catch (EmptyScriptException e) {
			logger.error("Error :", e);
		}
		String actionValue = null;
		try {
			actionValue = getAction(commands);
		} catch (EmptyKeyActionException e) {
			logger.error("Error :", e);
		}
		try {
			executeActionScript(actionValue,commands);	
		} catch (ValueActionException e) {
			logger.error("Error :", e);
		}
		  catch (DuplicateInputPdfException e) {
			logger.error("Error :", e);
		}
		  catch (MissingInputPdfKeyException e) {
			logger.error("Error :", e);
		}
		  catch (IOException e) {
			logger.error("Error :", e);
		}
		  catch (NoPdfFileException e) {
				logger.error("Error :", e);
		}
		  catch (DuplicateOutputContentException e) {
				logger.error("Error :", e);
		}
		  catch (MissingOutputContentException e) {
				logger.error("Error :", e);
		}
		  catch (NoContentException e) {
				logger.error("Error :", e);
		}
	}

	private void executeActionScript(String actionValue, Map<String, String> commands) throws ValueActionException, DuplicateInputPdfException, 
																							  MissingInputPdfKeyException, IOException, 
																							  NoPdfFileException, DuplicateOutputContentException, 
																							  MissingOutputContentException, NoContentException {
		if (actionValue.equals(ConstantsLanguage.ACTION_VALUES[0])){
			fillContent.execute(commands);
		}
		else if (actionValue.equals(ConstantsLanguage.ACTION_VALUES[1])){
			fillPdf.execute(commands);
		}
		else{
			throw new ValueActionException(ValueActionException.BAD_FORMAT);
		}
	}

	private String getAction(Map<String,String> commands) throws EmptyKeyActionException {
		String actionValue = null;
		if (commands.containsKey(ConstantsLanguage.ACTION_KEY)){
			actionValue = commands.get(ConstantsLanguage.ACTION_KEY);
		}
		else{
			throw new EmptyKeyActionException(EmptyKeyActionException.EMPTY_KEY);
		} 
		return actionValue;
	}

	private Map<String, String> getScriptAsKeysValues() throws EmptyScriptException {
		Map<String,String> commands = new HashMap<String, String>();
		Element expressionElement = script.element("expression");
		String expression = null;
		if (expressionElement != null){
			expression = expressionElement.getTextTrim();
		}
		else{
			throw new EmptyScriptException(EmptyScriptException.EMPTY_SCRIPT);
		}
		String[] expressions = expression.split(ConstantsLanguage.COMMANDS_SEPARATOR);
		for (int index = 0; index < expressions.length; index++) {
			String[] keyValue = expressions[index].split(ConstantsLanguage.KEY_VALUE_SEPARATOR);
			commands.put(keyValue[0], keyValue[1]);
		}
		return commands;
	}


}
