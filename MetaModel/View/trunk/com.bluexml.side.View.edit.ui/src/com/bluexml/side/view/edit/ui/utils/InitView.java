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
package com.bluexml.side.view.edit.ui.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.bluexml.side.clazz.AbstractClass;
import com.bluexml.side.clazz.Attribute;
import com.bluexml.side.common.ModelElement;
import com.bluexml.side.util.libs.ui.UIUtils;
import com.bluexml.side.view.AbstractViewOf;
import com.bluexml.side.view.FacetMap;
import com.bluexml.side.view.FieldElement;
import com.bluexml.side.view.ViewFactory;
import com.bluexml.side.view.ViewPackage;
import com.bluexml.side.view.edit.ui.utils.model.ClassUtils;
import com.bluexml.side.view.edit.ui.utils.model.ViewUtils;

public class InitView {

	public static int kindOfList = 0;

	public static void headlessInit(AbstractViewOf av) {
		// clear existing
		av.getChildren().clear();
		av.getDisabled().clear();
		if (av.getViewOf() instanceof AbstractClass) {
			AbstractClass cl = (AbstractClass) av.getViewOf();
			Collection<FieldElement> c = getViewElementForClass(cl, av);
			if (av instanceof FacetMap) {
				askTypeOfList();
				if (kindOfList != 0) {
					AbstractViewOf subList = null;
					if (kindOfList == 1) {
						subList = ViewFactory.eINSTANCE.createDataTable();
					} else {
						subList = ViewFactory.eINSTANCE.createDataList();
					}
					subList.setViewOf(av.getViewOf());
					subList.setMapTo((ModelElement) av.getViewOf());
					headlessInit(subList);
					c.add(subList);
				}
			}
			if (c.size() > 0) {
				av.getChildren().addAll(c);
				av.setName(cl.getName() + "_" + ViewUtils.getTypeAsString(av));
			} else {
				System.out.println("warning view without attributes");				
			}
		}
	}
	
	
	public static Command init(AbstractViewOf av, EditingDomain domain) {
		kindOfList = 0;
		CompoundCommand cmd = new CompoundCommand();

		if (av.getViewOf() != null) {
			boolean doWork = true;
			if (av.getChildren().size() > 0 || av.getDisabled().size() > 0) {
				doWork = UIUtils.showConfirmation("View already set", "View have already been set. Do you want to overwrite it?");
			}
			if (doWork) {
				if (av.getChildren().size() > 0) {
					cmd.append(DeleteCommand.create(domain, av.getChildren()));
				}
				if (av.getDisabled().size() > 0) {
					Command delCmd = DeleteCommand.create(domain, av.getDisabled());
					// TODO : find a better way, tried with RemoveCommand
					// and
					// DeleteCommand
					if (delCmd.canExecute()) {
						cmd.append(delCmd);
					} else {
						av.getDisabled().removeAll(av.getDisabled());
					}
				}
				if (av.getViewOf() instanceof AbstractClass) {
					AbstractClass cl = (AbstractClass) av.getViewOf();
					Collection<FieldElement> c = getViewElementForClass(cl, av);
					if (av instanceof FacetMap) {
						askTypeOfList();
						if (kindOfList != 0) {
							AbstractViewOf subList = null;
							if (kindOfList == 1) {
								subList = ViewFactory.eINSTANCE.createDataTable();
							} else {
								subList = ViewFactory.eINSTANCE.createDataList();
							}
							subList.setViewOf(av.getViewOf());
							subList.setMapTo((ModelElement) av.getViewOf());
							cmd.append(InitView.init(subList, domain));
							c.add(subList);
						}
					}
					if (c.size() > 0) {
						cmd.append(AddCommand.create(domain, av, ViewPackage.eINSTANCE.getFieldContainer_Children(), c));
						av.setName(cl.getName() + "_" + ViewUtils.getTypeAsString(av));
					} else {
						av.setName("REMOVE ME, " + cl.getName() + " do not have attributes");
						UIUtils.showError("No Attribute", cl.getName() + " do not have attributes\n delete this view");
						return null;
					}
					// av.setName(cl.getName() + " (" +
					// ViewUtils.getTypeAsString(av) + ")");
				}
			}
		} else {
			UIUtils.showError("No Class defined", "No class have been defined. \n" + "Choose one and run Initiliaze again.");
		}

		return cmd;
	}

	/**
	 * Show a dialog to ask if we user wants a Data List or a Data Table (for
	 * result on Facet Map)
	 */
	private static void askTypeOfList() {
		final Shell dialog = new Shell(Display.getCurrent().getActiveShell(), SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		dialog.setText("Which kind of list");
		dialog.setSize(250, 150);

		final Button buttonDL = new Button(dialog, SWT.PUSH);
		buttonDL.setText("Data List");
		buttonDL.setBounds(20, 55, 80, 25);

		final Button buttonDT = new Button(dialog, SWT.PUSH);
		buttonDT.setText("Data Table");
		buttonDT.setBounds(120, 55, 80, 25);

		final Label label = new Label(dialog, SWT.NONE);
		label.setText("Which kind of list do you want to use \n to display result ?");
		label.setBounds(20, 15, 200, 40);

		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == buttonDT) {
					kindOfList = 1;
				} else {
					kindOfList = 2;
				}
				dialog.close();
			}
		};

		buttonDL.addListener(SWT.Selection, listener);
		buttonDT.addListener(SWT.Selection, listener);

		dialog.open();

		while (!dialog.isDisposed()) {
			if (!Display.getCurrent().readAndDispatch())
				Display.getCurrent().sleep();
		}
	}

	private static Collection<FieldElement> getViewElementForClass(AbstractClass c, AbstractViewOf av) {
		Map<String, FieldElement> list = new TreeMap<String, FieldElement>();
		if (c != null) {
			// Attributes :
			List<Attribute> atts = null;

			atts = c.getAllAttributes();

			for (Attribute a : atts) {
				FieldElement f = ClassUtils.getField(a, av);
				list.put(f.getName(), f);
			}
		}
		return list.values();
	}

}
