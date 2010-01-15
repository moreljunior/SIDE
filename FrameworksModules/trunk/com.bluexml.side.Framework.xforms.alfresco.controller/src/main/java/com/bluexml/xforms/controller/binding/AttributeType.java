//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.01.15 at 02:53:18 PM CET 
//


package com.bluexml.xforms.controller.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attributeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attributeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="alfrescoName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inAlfresco" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="enumQName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dynamicEnum" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="default" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="multiple" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="fieldSize" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attributeType", propOrder = {
    "name",
    "alfrescoName",
    "inAlfresco",
    "type",
    "enumQName",
    "dynamicEnum",
    "_default",
    "multiple",
    "fieldSize",
    "readOnly"
})
public class AttributeType {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String alfrescoName;
    protected boolean inAlfresco;
    @XmlElement(required = true)
    protected String type;
    protected String enumQName;
    protected boolean dynamicEnum;
    @XmlElement(name = "default", required = true)
    protected String _default;
    protected boolean multiple;
    @XmlElement(required = true)
    protected String fieldSize;
    protected boolean readOnly;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the alfrescoName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlfrescoName() {
        return alfrescoName;
    }

    /**
     * Sets the value of the alfrescoName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlfrescoName(String value) {
        this.alfrescoName = value;
    }

    /**
     * Gets the value of the inAlfresco property.
     * 
     */
    public boolean isInAlfresco() {
        return inAlfresco;
    }

    /**
     * Sets the value of the inAlfresco property.
     * 
     */
    public void setInAlfresco(boolean value) {
        this.inAlfresco = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the enumQName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnumQName() {
        return enumQName;
    }

    /**
     * Sets the value of the enumQName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnumQName(String value) {
        this.enumQName = value;
    }

    /**
     * Gets the value of the dynamicEnum property.
     * 
     */
    public boolean isDynamicEnum() {
        return dynamicEnum;
    }

    /**
     * Sets the value of the dynamicEnum property.
     * 
     */
    public void setDynamicEnum(boolean value) {
        this.dynamicEnum = value;
    }

    /**
     * Gets the value of the default property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefault() {
        return _default;
    }

    /**
     * Sets the value of the default property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefault(String value) {
        this._default = value;
    }

    /**
     * Gets the value of the multiple property.
     * 
     */
    public boolean isMultiple() {
        return multiple;
    }

    /**
     * Sets the value of the multiple property.
     * 
     */
    public void setMultiple(boolean value) {
        this.multiple = value;
    }

    /**
     * Gets the value of the fieldSize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldSize() {
        return fieldSize;
    }

    /**
     * Sets the value of the fieldSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldSize(String value) {
        this.fieldSize = value;
    }

    /**
     * Gets the value of the readOnly property.
     * 
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Sets the value of the readOnly property.
     * 
     */
    public void setReadOnly(boolean value) {
        this.readOnly = value;
    }

}
