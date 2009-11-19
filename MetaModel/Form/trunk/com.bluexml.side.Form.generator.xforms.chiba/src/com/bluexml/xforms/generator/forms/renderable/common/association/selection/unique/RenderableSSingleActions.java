package com.bluexml.xforms.generator.forms.renderable.common.association.selection.unique;

import java.util.Stack;

import org.jdom.Element;

import com.bluexml.xforms.generator.forms.Renderable;
import com.bluexml.xforms.generator.forms.Rendered;
import com.bluexml.xforms.generator.forms.XFormsGenerator;
import com.bluexml.xforms.generator.forms.modelelement.ModelElementBindSimple;
import com.bluexml.xforms.generator.forms.renderable.common.AssociationBean;
import com.bluexml.xforms.generator.forms.renderable.common.AssociationBean.AssociationType;
import com.bluexml.xforms.generator.forms.renderable.common.association.AbstractRenderable;
import com.bluexml.xforms.generator.forms.renderable.common.association.selection.selector.RenderableSelector;
import com.bluexml.xforms.generator.forms.rendered.RenderedInput;

/**
 * The Class RenderableSSingleActions. Adds LEFT and RIGHT buttons in the N-1 selection widget.
 */
public class RenderableSSingleActions extends AbstractRenderable {

	/** The selector bind id. */
	private ModelElementBindSimple selectorBindId;

	/** The selector bind label. */
	private ModelElementBindSimple selectorBindLabel;

	/** The selector bind for the number of selected items. */
	private ModelElementBindSimple selectorBindNb;

	private RenderableSelector selector;

	/**
	 * Instantiates a new renderable s single actions.
	 * 
	 * @param associationBean
	 *            the association bean
	 * @param selectorBindId
	 *            the selector bind id
	 * @param selectorBindLabel
	 *            the selector bind label
	 */
	public RenderableSSingleActions(AssociationBean associationBean,
			ModelElementBindSimple selectorBindId, ModelElementBindSimple selectorBindLabel,
			ModelElementBindSimple selectorBindNb, RenderableSelector selector) {
		super(associationBean);
		this.selectorBindId = selectorBindId;
		this.selectorBindLabel = selectorBindLabel;
		this.selectorBindNb = selectorBindNb;
		this.selector = selector;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluexml.xforms.generator.forms.Renderable#getPath(java.lang.String, java.util.Stack,
	 * java.util.Stack)
	 */
	@Override
	public Path getPath(String parentPath, Stack<Renderable> parents,
			Stack<Rendered> renderedParents) {
		return ROOT_RELATIVE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluexml.xforms.generator.forms.Renderable#render(java.lang.String, java.util.Stack,
	 * java.util.Stack)
	 */
	@Override
	public Rendered render(String path, Stack<Renderable> parents, Stack<Rendered> renderedParents) {
		RenderedInput rendered = new RenderedInput();

		ModelElementBindSimple bindId = XFormsGenerator.getBind(renderedParents.peek(), 1);
		ModelElementBindSimple bindLabel = XFormsGenerator.getBind(renderedParents.peek(), 2);

		if ((getFormGenerator().isInReadOnlyMode() == false) || bean.isDisabled()) { // #1238
			Element xformsElement = XFormsGenerator.createElement("div",
					XFormsGenerator.NAMESPACE_XHTML);
			xformsElement.addContent(getTriggerSet(renderedParents, bindId, bindLabel));
			xformsElement.addContent(XFormsGenerator.createElement("br",
					XFormsGenerator.NAMESPACE_XHTML));
			xformsElement.addContent(getTriggerReset(bindId, bindLabel));
			rendered.setXformsElement(xformsElement);
		}
		return rendered;
	}

	/**
	 * Gets the trigger reset.
	 * 
	 * @param bindId
	 *            the bind id
	 * @param bindLabel
	 *            the bind label
	 * 
	 * @return the trigger reset
	 */
	private Element getTriggerReset(ModelElementBindSimple bindId, ModelElementBindSimple bindLabel) {
		Element trigger = XFormsGenerator.createTriggerWithLabelImage("resources/images/left.gif");
		Element action = XFormsGenerator.createElement("action", XFormsGenerator.NAMESPACE_XFORMS);
		action.setAttribute("event", "DOMActivate", XFormsGenerator.NAMESPACE_EVENTS);

		Element setvalueId = XFormsGenerator.createElement("setvalue",
				XFormsGenerator.NAMESPACE_XFORMS);
		bindId.addLinkedElement(setvalueId);
		setvalueId.setText("");
		action.addContent(setvalueId);

		Element setvalueLabel = XFormsGenerator.createElement("setvalue",
				XFormsGenerator.NAMESPACE_XFORMS);
		bindLabel.addLinkedElement(setvalueLabel);
		setvalueLabel.setText("");
		action.addContent(setvalueLabel);

		if (bean.isMandatory()) {
			Element setvalueNb = XFormsGenerator.createElement("setvalue",
					XFormsGenerator.NAMESPACE_XFORMS);
			setvalueNb.setAttribute("ref", selectorBindNb.getNodeset());
			setvalueNb.setAttribute("value", ". - 1");
			action.addContent(setvalueNb);
		}

		if (getBean().getAssociationType() == AssociationType.wkflwProcess) {
			// for updating the instances list wrt current process definition
			Element send = XFormsGenerator.createElement("send", XFormsGenerator.NAMESPACE_XFORMS);
			selector.getModelElementUpdater().addLinkedElement(send);
			action.addContent(send);
		}

		trigger.addContent(action);
		return trigger;
	}

	/**
	 * Gets the trigger set.
	 * 
	 * @param renderedParents
	 *            the rendered parents
	 * @param bindId
	 *            the bind id
	 * @param bindLabel
	 *            the bind label
	 * 
	 * @return the trigger set
	 */
	private Element getTriggerSet(Stack<Rendered> renderedParents, ModelElementBindSimple bindId,
			ModelElementBindSimple bindLabel) {
		Element trigger = XFormsGenerator.createTriggerWithLabelImage("resources/images/right.gif");
		Element action = XFormsGenerator.createElement("action", XFormsGenerator.NAMESPACE_XFORMS);
		action.setAttribute("event", "DOMActivate", XFormsGenerator.NAMESPACE_EVENTS);

		Element setvalueId = XFormsGenerator.createElement("setvalue",
				XFormsGenerator.NAMESPACE_XFORMS);
		bindId.addLinkedElement(setvalueId);
		setvalueId.setAttribute("value", selectorBindId.getNodeset());
		action.addContent(setvalueId);

		Element setvalueLabel = XFormsGenerator.createElement("setvalue",
				XFormsGenerator.NAMESPACE_XFORMS);
		bindLabel.addLinkedElement(setvalueLabel);
		setvalueLabel.setAttribute("value", selectorBindLabel.getNodeset());
		action.addContent(setvalueLabel);

		if (bean.isMandatory()) {
			Element setvalueNb = XFormsGenerator.createElement("setvalue",
					XFormsGenerator.NAMESPACE_XFORMS);
			setvalueNb.setAttribute("ref", selectorBindNb.getNodeset());
			setvalueNb.setAttribute("value", ". + 1");
			action.addContent(setvalueNb);
		}

		if (getBean().getAssociationType() == AssociationType.wkflwProcess) {
			// for updating the instances list wrt current process definition
			Element send = XFormsGenerator.createElement("send", XFormsGenerator.NAMESPACE_XFORMS);
			selector.getModelElementUpdater().addLinkedElement(send);
			action.addContent(send);
		}

		trigger.addContent(action);
		return trigger;
	}

}
