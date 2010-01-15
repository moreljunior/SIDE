
package com.bluexml.xforms.controller.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GenericAssociation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GenericAssociation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="target" type="{}GenericClassReference"/>
 *           &lt;element name="inlineTarget" type="{}GenericClass"/>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;element name="associationClass" type="{}GenericClassReference" minOccurs="0"/>
 *           &lt;element name="inlineAssociationClass" type="{}GenericClass" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="qualifiedName" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="action" type="{}AssociationActions" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenericAssociation", propOrder = {
    "target",
    "inlineTarget",
    "associationClass",
    "inlineAssociationClass"
})
public class GenericAssociation {

    protected GenericClassReference target;
    protected GenericClass inlineTarget;
    protected GenericClassReference associationClass;
    protected GenericClass inlineAssociationClass;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String qualifiedName;
    @XmlAttribute
    protected AssociationActions action;

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link GenericClassReference }
     *     
     */
    public GenericClassReference getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericClassReference }
     *     
     */
    public void setTarget(GenericClassReference value) {
        this.target = value;
    }

    /**
     * Gets the value of the inlineTarget property.
     * 
     * @return
     *     possible object is
     *     {@link GenericClass }
     *     
     */
    public GenericClass getInlineTarget() {
        return inlineTarget;
    }

    /**
     * Sets the value of the inlineTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericClass }
     *     
     */
    public void setInlineTarget(GenericClass value) {
        this.inlineTarget = value;
    }

    /**
     * Gets the value of the associationClass property.
     * 
     * @return
     *     possible object is
     *     {@link GenericClassReference }
     *     
     */
    public GenericClassReference getAssociationClass() {
        return associationClass;
    }

    /**
     * Sets the value of the associationClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericClassReference }
     *     
     */
    public void setAssociationClass(GenericClassReference value) {
        this.associationClass = value;
    }

    /**
     * Gets the value of the inlineAssociationClass property.
     * 
     * @return
     *     possible object is
     *     {@link GenericClass }
     *     
     */
    public GenericClass getInlineAssociationClass() {
        return inlineAssociationClass;
    }

    /**
     * Sets the value of the inlineAssociationClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericClass }
     *     
     */
    public void setInlineAssociationClass(GenericClass value) {
        this.inlineAssociationClass = value;
    }

    /**
     * Gets the value of the qualifiedName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualifiedName() {
        return qualifiedName;
    }

    /**
     * Sets the value of the qualifiedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualifiedName(String value) {
        this.qualifiedName = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link AssociationActions }
     *     
     */
    public AssociationActions getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssociationActions }
     *     
     */
    public void setAction(AssociationActions value) {
        this.action = value;
    }

}
