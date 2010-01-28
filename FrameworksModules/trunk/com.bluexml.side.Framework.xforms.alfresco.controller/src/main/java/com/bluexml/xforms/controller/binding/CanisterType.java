
package com.bluexml.xforms.controller.binding;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for canisterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="canisterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="action" type="{}actionFieldType" maxOccurs="unbounded"/>
 *         &lt;element name="field" type="{}formFieldType" maxOccurs="unbounded"/>
 *         &lt;element name="modelChoice" type="{}modelChoiceType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "canisterType", propOrder = {
    "action",
    "field",
    "modelChoice"
})
@XmlSeeAlso({
    FormType.class,
    WorkflowTaskType.class
})
public abstract class CanisterType {

    @XmlElement(required = true)
    protected List<ActionFieldType> action;
    @XmlElement(required = true)
    protected List<FormFieldType> field;
    @XmlElement(required = true)
    protected List<ModelChoiceType> modelChoice;

    /**
     * Gets the value of the action property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the action property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActionFieldType }
     * 
     * 
     */
    public List<ActionFieldType> getAction() {
        if (action == null) {
            action = new ArrayList<ActionFieldType>();
        }
        return this.action;
    }

    /**
     * Gets the value of the field property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the field property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormFieldType }
     * 
     * 
     */
    public List<FormFieldType> getField() {
        if (field == null) {
            field = new ArrayList<FormFieldType>();
        }
        return this.field;
    }

    /**
     * Gets the value of the modelChoice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modelChoice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModelChoice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModelChoiceType }
     * 
     * 
     */
    public List<ModelChoiceType> getModelChoice() {
        if (modelChoice == null) {
            modelChoice = new ArrayList<ModelChoiceType>();
        }
        return this.modelChoice;
    }

}
