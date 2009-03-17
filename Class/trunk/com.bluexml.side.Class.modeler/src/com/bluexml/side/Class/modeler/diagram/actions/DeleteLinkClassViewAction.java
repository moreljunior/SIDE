package com.bluexml.side.Class.modeler.diagram.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.topcased.modeler.commands.DeleteGraphElementCommand;
import org.topcased.modeler.di.model.GraphEdge;
import org.topcased.modeler.di.model.GraphElement;
import org.topcased.modeler.utils.Utils;

import com.bluexml.side.Class.modeler.diagram.edit.ClazzEditPart;
import com.bluexml.side.Class.modeler.diagram.edit.ViewEditPart;
import com.bluexml.side.Class.modeler.diagram.edit.hasViewEditPart;
import com.bluexml.side.clazz.ClassPackage;
import com.bluexml.side.clazz.Clazz;
import com.bluexml.side.clazz.View;


public class DeleteLinkClassViewAction extends WorkbenchPartAction implements ISelectionChangedListener
{
	public static String ID = "Unlink View";
	
    /**
     * The selected EditPart object
     */
	private ISelection selection;

    /**
     * @param part
     */
    public DeleteLinkClassViewAction(IWorkbenchPart part)
    {
        super(part);
        //setImageDescriptor(OblPlugin.getImageDescriptor("icons/actions/add.gif"));
    }
    
    protected void init()
    {
        setId(ID);
        setText("Unlink View");
    }

    public void run()
    {
    	StructuredSelection ss = (StructuredSelection) this.selection;
    	for (Object o : ss.toList()) {
    		if (o instanceof hasViewEditPart) {
    			//Get edit part and graph element
				hasViewEditPart editPart = (hasViewEditPart) o;
				GraphEdge eo = (GraphEdge) editPart.getModel();
				
				//Get source and target edit part
				ClazzEditPart cep = (ClazzEditPart) editPart.getSource();
				ViewEditPart vep = (ViewEditPart) editPart.getTarget();
				
				//Get source and target model element
				Clazz c = (Clazz) Utils.getElement((GraphElement) cep.getModel());
				View v = (View) Utils.getElement((GraphElement) vep.getModel());
				
				//Remove view from class
				c.getHasView().remove(v);				
				v.getAttributes().removeAll(v.getAttributes());
				
				//Add view to package fo class
				((ClassPackage) c.eContainer()).getViews().add(v);
				
				//Create delete command and execute it
				DeleteGraphElementCommand cmd = new DeleteGraphElementCommand(eo,true);
				editPart.getViewer().getEditDomain().getCommandStack().execute(cmd);
			}
    	}
    }

    /**
     * Determine if the action must appear in the context menu
     * 
     * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
     */
    protected boolean calculateEnabled()
    {
        return true;
    }

    /**
     * Sets the selected EditPart and refreshes the enabled state of this action.
     * 
     * @param event
     * 
     * @see ISelectionChangedListener#selectionChanged(SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event)
    {
        this.selection = event.getSelection();
    }

}
