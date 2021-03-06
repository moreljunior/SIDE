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

package com.bluexml.xforms.controller.binding;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bluexml.xforms.controller.binding package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Form_QNAME = new QName("", "form");
    private final static QName _Search_QNAME = new QName("", "search");
    private final static QName _Task_QNAME = new QName("", "task");
    private final static QName _Canister_QNAME = new QName("", "canister");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bluexml.xforms.controller.binding
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link VirtualFieldType }
     * 
     */
    public VirtualFieldType createVirtualFieldType() {
        return new VirtualFieldType();
    }

    /**
     * Create an instance of {@link ActionFieldType }
     * 
     */
    public ActionFieldType createActionFieldType() {
        return new ActionFieldType();
    }

    /**
     * Create an instance of {@link GenInfoType }
     * 
     */
    public GenInfoType createGenInfoType() {
        return new GenInfoType();
    }

    /**
     * Create an instance of {@link SearchFieldType }
     * 
     */
    public SearchFieldType createSearchFieldType() {
        return new SearchFieldType();
    }

    /**
     * Create an instance of {@link ClassType }
     * 
     */
    public ClassType createClassType() {
        return new ClassType();
    }

    /**
     * Create an instance of {@link AspectType }
     * 
     */
    public AspectType createAspectType() {
        return new AspectType();
    }

    /**
     * Create an instance of {@link WorkflowTaskType }
     * 
     */
    public WorkflowTaskType createWorkflowTaskType() {
        return new WorkflowTaskType();
    }

    /**
     * Create an instance of {@link AttributeType }
     * 
     */
    public AttributeType createAttributeType() {
        return new AttributeType();
    }

    /**
     * Create an instance of {@link ModelChoiceType }
     * 
     */
    public ModelChoiceType createModelChoiceType() {
        return new ModelChoiceType();
    }

    /**
     * Create an instance of {@link AssociationType }
     * 
     */
    public AssociationType createAssociationType() {
        return new AssociationType();
    }

    /**
     * Create an instance of {@link FieldType }
     * 
     */
    public FieldType createFieldType() {
        return new FieldType();
    }

    /**
     * Create an instance of {@link FileFieldType }
     * 
     */
    public FileFieldType createFileFieldType() {
        return new FileFieldType();
    }

    /**
     * Create an instance of {@link Mapping }
     * 
     */
    public Mapping createMapping() {
        return new Mapping();
    }

    /**
     * Create an instance of {@link SearchFormType }
     * 
     */
    public SearchFormType createSearchFormType() {
        return new SearchFormType();
    }

    /**
     * Create an instance of {@link FormFieldType }
     * 
     */
    public FormFieldType createFormFieldType() {
        return new FormFieldType();
    }

    /**
     * Create an instance of {@link ReferenceType }
     * 
     */
    public ReferenceType createReferenceType() {
        return new ReferenceType();
    }

    /**
     * Create an instance of {@link FormType }
     * 
     */
    public FormType createFormType() {
        return new FormType();
    }

    /**
     * Create an instance of {@link EnumType }
     * 
     */
    public EnumType createEnumType() {
        return new EnumType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "form", substitutionHeadNamespace = "", substitutionHeadName = "canister")
    public JAXBElement<FormType> createForm(FormType value) {
        return new JAXBElement<FormType>(_Form_QNAME, FormType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchFormType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "search", substitutionHeadNamespace = "", substitutionHeadName = "canister")
    public JAXBElement<SearchFormType> createSearch(SearchFormType value) {
        return new JAXBElement<SearchFormType>(_Search_QNAME, SearchFormType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WorkflowTaskType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "task", substitutionHeadNamespace = "", substitutionHeadName = "canister")
    public JAXBElement<WorkflowTaskType> createTask(WorkflowTaskType value) {
        return new JAXBElement<WorkflowTaskType>(_Task_QNAME, WorkflowTaskType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CanisterType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "canister")
    public JAXBElement<CanisterType> createCanister(CanisterType value) {
        return new JAXBElement<CanisterType>(_Canister_QNAME, CanisterType.class, null, value);
    }

}
