
package com.bluexml.xforms.controller.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for formFieldType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="formFieldType">
 *   &lt;complexContent>
 *     &lt;extension base="{}fieldType">
 *       &lt;sequence>
 *         &lt;element name="alfrescoName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="staticEnumType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="default" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dummyValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="multiple" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="searchEnum" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "formFieldType", propOrder = {
    "alfrescoName",
    "type",
    "staticEnumType",
    "_default",
    "dummyValue",
    "multiple",
    "searchEnum",
    "readOnly"
})
@XmlSeeAlso({
    FileFieldType.class
})
public class FormFieldType
    extends FieldType
{

    @XmlElement(required = true)
    protected String alfrescoName;
    @XmlElement(required = true)
    protected String type;
    protected String staticEnumType;
    @XmlElement(name = "default", required = true)
    protected String _default;
    @XmlElement(required = true)
    protected String dummyValue;
    protected boolean multiple;
    protected boolean searchEnum;
    protected boolean readOnly;

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
     * Gets the value of the staticEnumType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaticEnumType() {
        return staticEnumType;
    }

    /**
     * Sets the value of the staticEnumType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaticEnumType(String value) {
        this.staticEnumType = value;
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
     * Gets the value of the dummyValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDummyValue() {
        return dummyValue;
    }

    /**
     * Sets the value of the dummyValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDummyValue(String value) {
        this.dummyValue = value;
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
     * Gets the value of the searchEnum property.
     * 
     */
    public boolean isSearchEnum() {
        return searchEnum;
    }

    /**
     * Sets the value of the searchEnum property.
     * 
     */
    public void setSearchEnum(boolean value) {
        this.searchEnum = value;
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
