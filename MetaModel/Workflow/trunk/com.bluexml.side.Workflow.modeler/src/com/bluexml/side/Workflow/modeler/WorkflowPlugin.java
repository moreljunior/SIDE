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
/*******************************************************************************
 * No CopyrightText Defined in the configurator file.
 ******************************************************************************/
package com.bluexml.side.Workflow.modeler;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.bluexml.side.util.libs.Messages;

/**
 * The main plugin class to be used in the desktop.
 *
 * @generated
 */
public class WorkflowPlugin extends AbstractUIPlugin {
	
	private static final String PLUGIN_ID = "com.bluexml.side.Workflow.modeler";

	/**
	 * The shared instance
	 * @generated
	 */
	private static WorkflowPlugin plugin;
	
	public static final Messages Messages = new Messages(PLUGIN_ID, "com.bluexml.side.Workflow.modeler.messages.messages");

	/**
	 * The constructor.
	 *
	 * @generated
	 */
	public WorkflowPlugin() {
		super();
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 *
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 * @generated
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 *
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 * @generated
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the singleton
	 * @generated
	 */
	public static WorkflowPlugin getDefault() {
		return plugin;
	}

	/**
	 * @return the Plugin Id
	 * @generated
	 */
	public static String getId() {
		return getDefault().getBundle().getSymbolicName();
	}

	/**
	 * Log a message with given level into the Eclipse log file
	 *
	 * @param message the message to log
	 * @param level the message priority
	 * @generated
	 */
	public static void log(String message, int level) {
		IStatus status = null;
		status = new Status(level, getId(), IStatus.OK, message, null);
		log(status);
	}

	/**
	 * Log an exception into the Eclipse log file
	 *
	 * @param e the exception to log
	 * @generated
	 */
	public static void log(Throwable e) {
		if (e instanceof InvocationTargetException)
			e = ((InvocationTargetException) e).getTargetException();

		IStatus status = null;
		if (e instanceof CoreException)
			status = ((CoreException) e).getStatus();
		else
			status = new Status(IStatus.ERROR, getId(), IStatus.OK, "Error", e);

		log(status);
	}

	/**
	 * Log an IStatus
	 *
	 * @param status the status to log
	 * @generated
	 */
	public static void log(IStatus status) {
		ResourcesPlugin.getPlugin().getLog().log(status);
	}

	/**
	 * Display a dialog box with the specified level
	 * 
	 * @param title title dialog box
	 * @param message message displayed
	 * @param level message level
	 * @_generated
	 */
	public static void displayDialog(final String title, final String message,
			int level) {
		if (level == IStatus.INFO) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					MessageDialog.openInformation(getActiveWorkbenchShell(),
							(title == null) ? "Information" : title,
							(message == null) ? "" : message);
				}
			});
		} else if (level == IStatus.WARNING) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					MessageDialog.openWarning(getActiveWorkbenchShell(),
							(title == null) ? "Warning" : title,
							(message == null) ? "" : message);
				}
			});
		} else if (level == IStatus.ERROR) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(getActiveWorkbenchShell(),
							(title == null) ? "Error" : title,
							(message == null) ? "" : message);
				}
			});
		}
	}

	/**
	 * Returns the active workbench shell
	 * 
	 * @return the active workbench shell
	 * @generated
	 */
	public static Shell getActiveWorkbenchShell() {
		IWorkbenchWindow workBenchWindow = getActiveWorkbenchWindow();
		if (workBenchWindow == null) {
			return null;
		}
		return workBenchWindow.getShell();
	}

	/**
	 * Returns the active workbench page or <code>null</code> if none.
	 * 
	 * @return the active workbench page
	 * @generated
	 */
	public static IWorkbenchPage getActivePage() {
		IWorkbenchWindow window = getActiveWorkbenchWindow();
		if (window != null) {
			return window.getActivePage();
		}
		return null;
	}

	/**
	 * Returns the active workbench window
	 * 
	 * @return the active workbench window
	 * @generated
	 */
	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		if (getDefault() == null) {
			return null;
		}
		IWorkbench workBench = getDefault().getWorkbench();
		if (workBench == null) {
			return null;
		}
		return workBench.getActiveWorkbenchWindow();
	}
}
