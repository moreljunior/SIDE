package com.bluexml.xforms.generator.forms.renderable.forms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;

import com.bluexml.side.clazz.Clazz;
import com.bluexml.side.form.FormClass;
import com.bluexml.side.form.FormContainer;
import com.bluexml.side.form.FormElement;
import com.bluexml.side.form.ModelChoiceField;
import com.bluexml.side.form.ModelChoiceWidgetType;
import com.bluexml.xforms.generator.FormGeneratorsManager;
import com.bluexml.xforms.generator.forms.FormTypeRendered;
import com.bluexml.xforms.generator.forms.Renderable;
import com.bluexml.xforms.generator.forms.Rendered;
import com.bluexml.xforms.generator.forms.XFormsGenerator;
import com.bluexml.xforms.generator.forms.renderable.common.AssociationProperties;
import com.bluexml.xforms.generator.forms.renderable.common.CommonRenderableAssociation;
import com.bluexml.xforms.generator.forms.renderable.forms.group.RenderableFormContainer;
import com.bluexml.xforms.generator.forms.rendered.RenderedParentGroup;

/**
 * The Class RenderableModelChoiceField.
 */
public class RenderableModelChoiceField extends RenderableFormElement<ModelChoiceField> {

	/**
	 * Instantiates a new renderable model choice field.
	 * 
	 * @param generationManager
	 *            the generation manager
	 * @param parent
	 *            the parent
	 * @param formElement
	 *            the form element
	 */
	public RenderableModelChoiceField(XFormsGenerator generationManager, FormElement parent,
			ModelChoiceField formElement) {
		super(generationManager, parent, formElement);

		AssociationProperties properties = new AssociationProperties();

		properties.setAssocTitle(formElement.getLabel());
		properties.setDestination((Clazz) generationManager.getFormGenerator().getRealObject(
				formElement.getReal_class()));
		properties.setHiBound(formElement.getMax_bound());
		properties.setLoBound(formElement.getMin_bound());
		properties.setName(FormGeneratorsManager.getUniqueName(formElement));
		properties.setHint(formElement.getHelp_text());

		properties.setDestinationRenderable(null);
		properties.setInline(false);

		properties.setCreateEditFormType(FormTypeRendered.formForm);
		int targetsNb = formElement.getTarget().size();
		if (targetsNb > 0) {
			// we want to allow several targets in the XHTML file
			for (FormContainer targetedForm : formElement.getTarget()) {
				// we need to get the real object, in case the target form is from another file.
				FormContainer realTargetedForm = (FormContainer) getFormGenerator().getRealObject(
						targetedForm);
				if (realTargetedForm instanceof FormClass) {
					if (formElement.getWidget() == ModelChoiceWidgetType.INLINE) {
						properties.setInline(true);
						RenderableFormContainer renderableForm = generationManager
								.getRenderableForm(realTargetedForm);
						properties.setDestinationRenderable(renderableForm);
						properties.addCreateEditFormName(realTargetedForm.getId());
						break; // only one target allowed if inline
					}

					properties.addCreateEditFormName(realTargetedForm.getId());
				}
			}
		}
		// add support for hiding/displaying action buttons
		boolean showingActions = formElement.isShow_actions();
		properties.setShowingActions(showingActions);
		properties.setDisabled(formElement.isDisabled());
		properties.setMandatory(formElement.isMandatory());

		// set maximum number of items to display
		String lsize = "" + formElement.getField_size();
		if (StringUtils.trimToNull(lsize) != null) {
			properties.setFieldSize(lsize);
		}

		String pattern = formElement.getFormat_pattern();
		try {
			if (StringUtils.trimToNull(pattern) != null) {
				pattern = URLEncoder.encode(pattern, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			logger.fatal("Unsupported encoding scheme: UTF-8");
			throw new RuntimeException("Unsupported encoding scheme: UTF-8");
		}
		properties.setFormatPattern(pattern);
		properties.setLabelLength("" + formElement.getLabel_length());

		add(new CommonRenderableAssociation(properties));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluexml.xforms.generator.forms.renderable.forms.RenderableFormElement #compute()
	 */
	@Override
	public void compute() {
		// nothing
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
	public Rendered render(String path, Stack<Renderable> parents, Stack<Rendered> renderedParents,
			boolean isInIMultRepeater) {
		return new RenderedParentGroup(renderedParents);
	}

}
