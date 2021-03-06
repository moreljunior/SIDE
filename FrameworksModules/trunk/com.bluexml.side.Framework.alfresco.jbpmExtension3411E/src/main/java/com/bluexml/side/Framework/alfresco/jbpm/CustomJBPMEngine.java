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
package com.bluexml.side.Framework.alfresco.jbpm;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.zip.ZipInputStream;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.i18n.MessageService;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authority.AuthorityDAO;
import org.alfresco.repo.tenant.TenantService;
import org.alfresco.repo.workflow.BPMEngine;
import org.alfresco.repo.workflow.BPMEngineRegistry;
import org.alfresco.repo.workflow.TaskComponent;
import org.alfresco.repo.workflow.WorkflowComponent;
import org.alfresco.repo.workflow.WorkflowModel;
import org.alfresco.repo.workflow.jbpm.JBPMNode;
import org.alfresco.repo.workflow.jbpm.JBPMNodeList;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.dictionary.AspectDefinition;
import org.alfresco.service.cmr.dictionary.AssociationDefinition;
import org.alfresco.service.cmr.dictionary.ClassDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.service.cmr.dictionary.TypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.repository.datatype.DefaultTypeConverter;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.workflow.WorkflowDefinition;
import org.alfresco.service.cmr.workflow.WorkflowDeployment;
import org.alfresco.service.cmr.workflow.WorkflowException;
import org.alfresco.service.cmr.workflow.WorkflowInstance;
import org.alfresco.service.cmr.workflow.WorkflowInstanceQuery;
import org.alfresco.service.cmr.workflow.WorkflowNode;
import org.alfresco.service.cmr.workflow.WorkflowPath;
import org.alfresco.service.cmr.workflow.WorkflowTask;
import org.alfresco.service.cmr.workflow.WorkflowTaskDefinition;
import org.alfresco.service.cmr.workflow.WorkflowTaskQuery;
import org.alfresco.service.cmr.workflow.WorkflowTaskState;
import org.alfresco.service.cmr.workflow.WorkflowTimer;
import org.alfresco.service.cmr.workflow.WorkflowTransition;
import org.alfresco.service.cmr.workflow.WorkflowInstanceQuery.DatePosition;
import org.alfresco.service.namespace.NamespaceException;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.util.GUID;
import org.hibernate.CacheMode;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxy;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.context.exe.TokenVariableMap;
import org.jbpm.context.exe.VariableInstance;
import org.jbpm.context.exe.variableinstance.DateInstance;
import org.jbpm.context.exe.variableinstance.LongInstance;
import org.jbpm.context.exe.variableinstance.NullInstance;
import org.jbpm.context.exe.variableinstance.StringInstance;
import org.jbpm.db.GraphSession;
import org.jbpm.db.TaskMgmtSession;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.Event;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.Comment;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.job.Timer;
import org.jbpm.jpdl.par.ProcessArchive;
import org.jbpm.jpdl.xml.Problem;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.def.TaskMgmtDefinition;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.dao.DataAccessException;
import org.springframework.util.StringUtils;
import org.springmodules.workflow.jbpm31.JbpmCallback;
import org.springmodules.workflow.jbpm31.JbpmTemplate;
import org.springmodules.workflow.jbpm31.JbpmAccessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * JBoss JBPM based implementation of:
 * 
 * Workflow Definition Component
 * Workflow Component
 * Task Component
 * 
 * @author davidc
 */
public class CustomJBPMEngine extends BPMEngine
    implements WorkflowComponent, TaskComponent
{
    private static Log logger = LogFactory.getLog("com.bluexml.side.Framework.alfresco.jbpm");
    // Implementation dependencies
    protected DictionaryService dictionaryService;
    protected NamespaceService namespaceService;
    protected NodeService nodeService;
    private TenantService tenantService;
    private MessageService messageService;
    protected ServiceRegistry serviceRegistry;
    protected PersonService personService;
    protected AuthorityDAO authorityDAO;
    protected JbpmTemplate jbpmTemplate;
    protected SearchService unprotectedSearchService;
    
    // Company Home
    protected StoreRef companyHomeStore;
    protected String companyHomePath;

    private QNameCache qNameCache = new QNameCache();
    private Map<String, QName> ignoredProperties = new HashMap<String, QName>(3);

    // Note: jBPM query which is not provided out-of-the-box
    // TODO: Check jBPM 3.2 and get this implemented in jBPM
    private final static String COMPLETED_TASKS_QUERY =     
        "select ti " + 
        "from org.jbpm.taskmgmt.exe.TaskInstance as ti " +
        "where ti.actorId = :actorId " +
        "and ti.isOpen = false " +
        "and ti.end is not null";

    // Note: jBPM query which is not provided out-of-the-box
    // TODO: Check jBPMg future and get this implemented in jBPM
    private final static String PROCESS_TIMERS_QUERY =
        "select timer " +
        "from org.jbpm.job.Timer timer " +
        "where timer.processInstance = :process ";
    
    // Workflow Path Seperators
    private final static String WORKFLOW_PATH_SEPERATOR = "-";
    private final static String WORKFLOW_TOKEN_SEPERATOR = "@";
    
    // I18N labels
    private final static String TITLE_LABEL = "title";
    private final static String DESC_LABEL = "description";
    private final static String DEFAULT_TRANSITION_LABEL = "bpm_businessprocessmodel.transition";
    
    private static final String ERR_MANDATORY_TASK_PROPERTIES_MISSING = "Jbpm.engine.mandatory.properties.missing";
    private static final String ERR_DEPLOY_WORKFLOW= "jbpm.engine.deploy.workflow.error";
    private static final String ERR_IS_WORKFLOW_DEPLOYED = "jbpm.engine.is.workflow.deployed.error";
    private static final String ERR_UNDEPLOY_WORKFLOW = "jbpm.engine.undeploy.workflow.error";
    private static final String ERR_GET_WORKFLOW_DEF = "jbpm.engine.get.workflow.definition.error";
    private static final String ERR_GET_WORKFLOW_DEF_BY_ID = "jbpm.engine.get.workflow.definition.by.id.error";
    private static final String ERR_GET_WORKFLOW_DEF_BY_NAME = "jbpm.engine.get.workflow.definition.by.name.error";
    private static final String ERR_GET_ALL_DEFS_BY_NAME = "jbpm.engine.get.all.workflow.definitions.by.name.error";
    private static final String ERR_GET_DEF_IMAGE = "jbpm.engine.get.workflow.definition.image.error";
    private static final String ERR_GET_TASK_DEFS = "jbpm.engine.get.task.definitions.error";
    private static final String ERR_GET_PROCESS_DEF = "jbpm.engine.get.process.definition.error";
    private static final String ERR_START_WORKFLOW = "jbpm.enginestart.workflow.error";
    private static final String ERR_GET_ACTIVE_WORKFLOW_INSTS = "jbpm.engine.get.active.workflows.error";
    private static final String ERR_GET_WORKFLOW_INST_BY_ID = "jbpm.engine.get.workflow.instance.by.id.error";
    private static final String ERR_GET_PROCESS_INSTANCE = "jbpm.engine.get.process.instance.error";
    private static final String ERR_GET_WORKFLOW_PATHS = "jbpm.engine.get.workflow.paths.error";
    private static final String ERR_GET_PATH_PROPERTIES = "jbpm.engine.get.path.properties.error";
    private static final String ERR_CANCEL_WORKFLOW = "jbpm.engine.cancel.workflow.error";
    private static final String ERR_DELETE_WORKFLOW = "jbpm.engine.delete.workflow.error";
    private static final String ERR_SIGNAL_TRANSITION = "jbpm.engine.signal.transition.error";
    protected static final String ERR_INVALID_EVENT = "jbpm.engine.invalid.event";
    private static final String ERR_FIRE_EVENT = "jbpm.engine.fire.event.error";
    private static final String ERR_GET_TASKS_FOR_PATH = "jbpm.engine.get.tasks.for.path.error";
    private static final String ERR_GET_TIMERS = "jbpm.engine.get.timers.error";
    protected static final String ERR_FIND_COMPLETED_TASK_INSTS = "jbpm.engine.find.completed.task.instances.error";
    private static final String ERR_GET_ASSIGNED_TASKS = "jbpm.engine.get.assigned.tasks.error";
    private static final String ERR_GET_POOLED_TASKS = "jbpm.engine.get.pooled.tasks.error";
    private static final String ERR_QUERY_TASKS = "jbpm.engine.query.tasks.error";
    private static final String ERR_GET_TASK_INST = "jbpm.engine.get.task.instance.error";
    private static final String ERR_UPDATE_TASK = "jbpm.engine.update.task.error";
    protected static final String ERR_END_TASK_INVALID_TRANSITION ="jbpm.engine.end.task.invalid.transition";
    private static final String ERR_END_TASK = "jbpm.engine.end.task.error";
    private static final String ERR_GET_TASK_BY_ID = "jbpm.engine.get.task.by.id.error";
    private static final String ERR_COMPILE_PROCESS_DEF_zip = "jbpm.engine.compile.process.definition.zip.error";
    private static final String ERR_COMPILE_PROCESS_DEF_XML = "jbpm.engine.compile.process.definition.xml.error";
    private static final String ERR_COMPILE_PROCESS_DEF_UNSUPPORTED = "jbpm.engine.compile.process.definition.unsupported.error";
    private static final String ERR_GET_TASK_DEF = "jbpm.engine.get.task.definition.error";
    private static final String ERR_GET_JBPM_ID = "jbpm.engine.get.jbpm.id.error";
    private static final String ERR_GET_WORKFLOW_TOKEN_INVALID = "jbpm.engine.get.workflow.token.invalid";
    private static final String ERR_GET_WORKFLOW_TOKEN_NULL = "jbpm.engine.get.workflow.token.is.null";
    private static final String ERR_SET_TASK_PROPS_INVALID_VALUE = "jbpm.engine.set.task.properties.invalid.value";
    private static final String ERR_PACKAGE_ALREADY_ASSOCIATED = "jbpm.engine.package.already.associated.error";
    private static final String ERR_CONVERT_VALUE = "jbpm.engine.convert.value.error";
    private static final String ERR_GET_COMPANY_HOME_INVALID = "jbpm.engine.get.company.home.invalid";
    private static final String ERR_GET_COMPANY_HOME_MULTIPLE = "jbpm.engine.get.company.home.multiple";
    
    public CustomJBPMEngine()
    {
        super();
        ignoredProperties.put(WorkflowModel.PROP_STATUS.getLocalName(), WorkflowModel.PROP_STATUS);
        ignoredProperties.put(WorkflowModel.PROP_PACKAGE_ITEM_ACTION_GROUP.getLocalName(), WorkflowModel.PROP_PACKAGE_ITEM_ACTION_GROUP);
        ignoredProperties.put(WorkflowModel.PROP_PACKAGE_ACTION_GROUP.getLocalName(), WorkflowModel.PROP_PACKAGE_ACTION_GROUP);
    }

    /**
     * Sets the JBPM Template used for accessing JBoss JBPM in the correct context
     * 
     * @param jbpmTemplate
     */
    public void setJBPMTemplate(JbpmTemplate jbpmTemplate)
    {
        this.jbpmTemplate = jbpmTemplate;
    }
    
    /**
     * Sets the Dictionary Service
     * 
     * @param dictionaryService
     */
    public void setDictionaryService(DictionaryService dictionaryService)
    {
        this.dictionaryService = dictionaryService;
    }

    /**
     * Sets the Namespace Service
     * 
     * @param namespaceService
     */
    public void setNamespaceService(NamespaceService namespaceService)
    {
        this.namespaceService = namespaceService;
    }

    /**
     * Sets the Node Service
     * 
     * @param nodeService
     */
    public void setNodeService(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }
    
    /**
     * Sets the Tenant Service
     * 
     * @param tenantService
     */
    public void setTenantService(TenantService tenantService)
    {
        this.tenantService = tenantService;
    }
    
    /**
     * Sets the Message Service
     * 
     * @param messageService
     */
    public void setMessageService(MessageService messageService)
    {
        this.messageService = messageService;
    }

    /**
     * Sets the Person Service
     * 
     * @param personService
     */
    public void setPersonService(PersonService personService)
    {
        this.personService = personService;
    }

    /**
     * Sets the Authority DAO
     * 
     * @param authorityDAO
     */
    public void setAuthorityDAO(AuthorityDAO authorityDAO)
    {
        this.authorityDAO = authorityDAO;
    }

    /**
     * Sets the Service Registry
     *  
     * @param serviceRegistry
     */
    public void setServiceRegistry(ServiceRegistry serviceRegistry)
    {
        this.serviceRegistry = serviceRegistry;
    }

    /**
     * Sets the Company Home Path
     * 
     * @param companyHomePath
     */
    public void setCompanyHomePath(String companyHomePath)
    {
        this.companyHomePath = companyHomePath;
    }

    /**
     * Sets the Company Home Store
     * 
     * @param companyHomeStore
     */
    public void setCompanyHomeStore(String companyHomeStore)
    {
        this.companyHomeStore = new StoreRef(companyHomeStore);
    }

    /**
     * Set the unprotected search service - so we can find the node ref for company home when folk do not have read access to company home
     * TODO: review use with DC
     * 
     * @param unprotectedSearchService
     */
    public void setUnprotectedSearchService(SearchService unprotectedSearchService)
    {
        this.unprotectedSearchService = unprotectedSearchService;
    }
    
    
    //
    // Workflow Definition...
    //
    
    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowDefinitionComponent#deployDefinition(java.io.InputStream)
     */
    public WorkflowDeployment deployDefinition(final InputStream workflowDefinition, final String mimetype)
    {
        try
        {
            return (WorkflowDeployment)jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    // construct process definition
                    if (logger.isDebugEnabled())
                        logger.debug("deployDefinition using custom BX Parser");

                    CompiledProcessDefinition compiledDef = customCompileProcessDefinition(workflowDefinition, mimetype);
                    
                    // deploy the parsed definition
                    context.deployProcessDefinition(compiledDef.def);
                    
                    // return deployed definition
                    WorkflowDeployment workflowDeployment = createWorkflowDeployment(compiledDef);
                    return workflowDeployment;
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_DEPLOY_WORKFLOW);
            throw new WorkflowException(msg, e);
        }
    }

            
    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowDefinitionComponent#isDefinitionDeployed(java.io.InputStream, java.lang.String)
     */
    public boolean isDefinitionDeployed(final InputStream workflowDefinition, final String mimetype)
    {
        try
        {
            return (Boolean) jbpmTemplate.execute(new JbpmCallback()
            {
                public Boolean doInJbpm(JbpmContext context)
                {
                    if (logger.isDebugEnabled())
                        logger.debug("deployDefinition using custom BX Parser");
                    // create process definition from input stream
                    CompiledProcessDefinition processDefinition = customCompileProcessDefinition(workflowDefinition, mimetype);
                    
                    // retrieve process definition from Alfresco Repository
                    GraphSession graphSession = context.getGraphSession();
                    ProcessDefinition existingDefinition = graphSession.findLatestProcessDefinition(processDefinition.def.getName());
                    return (existingDefinition == null) ? false : true;
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_IS_WORKFLOW_DEPLOYED);
            throw new WorkflowException(msg, e);
        }
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowDefinitionComponent#undeployDefinition(java.lang.String)
     */
    public void undeployDefinition(final String workflowDefinitionId)
    {
        try
        {
            jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    // retrieve process definition
                    GraphSession graphSession = context.getGraphSession();
                    ProcessDefinition processDefinition = getProcessDefinition(graphSession, workflowDefinitionId);
                    
                    // undeploy
                    // NOTE: jBPM deletes all "in-flight" processes too
                    // TODO: Determine if there's a safer undeploy we can expose via the WorkflowService contract
                    graphSession.deleteProcessDefinition(processDefinition);
                    
                    // we're done
                    return null;
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_UNDEPLOY_WORKFLOW, workflowDefinitionId);
            throw new WorkflowException(msg, e);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowDefinitionComponent#getDefinitions()
     */
    @SuppressWarnings("unchecked")
    public List<WorkflowDefinition> getDefinitions()
    {
        try
        {
            return (List<WorkflowDefinition>)jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    GraphSession graphSession = context.getGraphSession();
                    List<ProcessDefinition> processDefs = graphSession.findLatestProcessDefinitions();
                    List<WorkflowDefinition> workflowDefs = new ArrayList<WorkflowDefinition>(processDefs.size());
                    for (ProcessDefinition processDef : processDefs)
                    {                                              
                        if (tenantService.isEnabled())
                        {                           
                            try 
                            {
                                tenantService.checkDomain(processDef.getName());
                            }
                            catch (RuntimeException re)
                            {
                                // deliberately skip this one - due to domain mismatch
                                continue;
                            }                           
                        }
                        
                        WorkflowDefinition workflowDef = createWorkflowDefinition(processDef);
                        workflowDefs.add(workflowDef);
                    }
                    return workflowDefs;
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_WORKFLOW_DEF);
            throw new WorkflowException(msg, e);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowDefinitionComponent#getDefinitions()
     */
    @SuppressWarnings("unchecked")
    public List<WorkflowDefinition> getAllDefinitions()
    {
        try
        {
            return (List<WorkflowDefinition>)jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    GraphSession graphSession = context.getGraphSession();
                    List<ProcessDefinition> processDefs = graphSession.findAllProcessDefinitions();
                    List<WorkflowDefinition> workflowDefs = new ArrayList<WorkflowDefinition>(processDefs.size());
                    for (ProcessDefinition processDef : processDefs)
                    {
                        if (tenantService.isEnabled())
                        {                           
                            try 
                            {
                                tenantService.checkDomain(processDef.getName());
                            }
                            catch (RuntimeException re)
                            {
                                // deliberately skip this one - due to domain mismatch
                                continue;
                            } 
                        }
                        
                        WorkflowDefinition workflowDef = createWorkflowDefinition(processDef);
                        workflowDefs.add(workflowDef);
                    }
                    return workflowDefs;
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_WORKFLOW_DEF);
            throw new WorkflowException(msg, e);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowDefinitionComponent#getDefinitionById(java.lang.String)
     */
    public WorkflowDefinition getDefinitionById(final String workflowDefinitionId)
    {
        try
        {
            return (WorkflowDefinition)jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    // retrieve process
                    GraphSession graphSession = context.getGraphSession();
                    ProcessDefinition processDefinition = getProcessDefinition(graphSession, workflowDefinitionId);
                    return processDefinition == null ? null : createWorkflowDefinition(processDefinition);
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_WORKFLOW_DEF_BY_ID, workflowDefinitionId);
            throw new WorkflowException(msg, e);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#getDefinitionByName(java.lang.String)
     */
    public WorkflowDefinition getDefinitionByName(final String workflowName)
    {
        try
        {
            return (WorkflowDefinition)jbpmTemplate.execute(new JbpmCallback()
            {
                @SuppressWarnings("synthetic-access")
                public Object doInJbpm(JbpmContext context)
                {
                    GraphSession graphSession = context.getGraphSession();                                                       
                    ProcessDefinition processDef = graphSession.findLatestProcessDefinition(tenantService.getName(createLocalId(workflowName)));                                      
                    return processDef == null ? null : createWorkflowDefinition(processDef);
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_WORKFLOW_DEF_BY_NAME, workflowName);
            throw new WorkflowException(msg, e);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#getAllDefinitionsByName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<WorkflowDefinition> getAllDefinitionsByName(final String workflowName)
    {
        try
        {
            return (List<WorkflowDefinition>)jbpmTemplate.execute(new JbpmCallback()
            {
                @SuppressWarnings("synthetic-access")
                public Object doInJbpm(JbpmContext context)
                {
                    GraphSession graphSession = context.getGraphSession();
                    List<ProcessDefinition> processDefs = (List<ProcessDefinition>)graphSession.findAllProcessDefinitionVersions(tenantService.getName(createLocalId(workflowName)));
                    List<WorkflowDefinition> workflowDefs = new ArrayList<WorkflowDefinition>(processDefs.size());
                    for (ProcessDefinition processDef : processDefs)
                    {
                        WorkflowDefinition workflowDef = createWorkflowDefinition(processDef);
                        workflowDefs.add(workflowDef);
                    }
                    return workflowDefs;
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_ALL_DEFS_BY_NAME, workflowName);
            throw new WorkflowException(msg, e);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#getDefinitionImage(java.lang.String)
     */
    public byte[] getDefinitionImage(final String workflowDefinitionId)
    {
        try
        {
            return (byte[])jbpmTemplate.execute(new JbpmCallback()
            {
                @SuppressWarnings("synthetic-access")
                public Object doInJbpm(JbpmContext context)
                {
                    GraphSession graphSession = context.getGraphSession();
                    ProcessDefinition processDefinition = getProcessDefinition(graphSession, workflowDefinitionId);                    
                    FileDefinition fileDefinition = processDefinition.getFileDefinition();
                    return (fileDefinition == null) ? null : fileDefinition.getBytes("processimage.jpg");
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_DEF_IMAGE, workflowDefinitionId);
            throw new WorkflowException(msg, e);
        }
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#getAllTaskDefinitions(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<WorkflowTaskDefinition> getTaskDefinitions(final String workflowDefinitionId)
    {
        try
        {
            return (List<WorkflowTaskDefinition>)jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    // retrieve process
                    GraphSession graphSession = context.getGraphSession();
                    ProcessDefinition processDefinition = getProcessDefinition(graphSession, workflowDefinitionId);
                    
                    if (processDefinition == null)
                    {
                        return null;
                    }
                    else
                    {
                        String processName = processDefinition.getName();
                        if (tenantService.isEnabled())
                        {
                            tenantService.checkDomain(processName); // throws exception if domain mismatch
                        }
                        
                        TaskMgmtDefinition taskMgmtDef = processDefinition.getTaskMgmtDefinition();
                        List<WorkflowTaskDefinition> workflowTaskDefs = new ArrayList<WorkflowTaskDefinition>();
                        for (Object task : taskMgmtDef.getTasks().values())
                        {
                            workflowTaskDefs.add(createWorkflowTaskDefinition((Task)task));
                        }
                        return (workflowTaskDefs.size() == 0) ? null : workflowTaskDefs;
                    }
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_TASK_DEFS, workflowDefinitionId);
            throw new WorkflowException(msg, e);
        }
    }
    
    /**
     * Gets a jBPM process definition
     * 
     * @param graphSession  jBPM graph session
     * @param workflowDefinitionId  workflow definition id
     * @return  process definition
     */
    protected ProcessDefinition getProcessDefinition(GraphSession graphSession, String workflowDefinitionId)
    {
        ProcessDefinition processDefinition = graphSession.getProcessDefinition(getJbpmId(workflowDefinitionId));
        
        if ((processDefinition != null) && (tenantService.isEnabled()))
        {
            try
            {
                tenantService.checkDomain(processDefinition.getName()); // throws exception if domain mismatch
            }
            catch (RuntimeException re)
            {
                processDefinition = null;
            }
        }
        
        if (processDefinition == null)
        {
            String msg = messageService.getMessage(ERR_GET_PROCESS_DEF, workflowDefinitionId);
            throw new WorkflowException(msg);
        }
        return processDefinition;
    }
    

    //
    // Workflow Instance Management...
    //

    
    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#startWorkflow(java.lang.String, java.util.Map)
     */
    public WorkflowPath startWorkflow(final String workflowDefinitionId, final Map<QName, Serializable> parameters)
    {
        try
        {
            return (WorkflowPath) jbpmTemplate.execute(new JbpmCallback()
            {
                @SuppressWarnings("synthetic-access")
                public Object doInJbpm(JbpmContext context)
                {
                    // initialise jBPM actor (for any processes that wish to record the initiator)
                    String currentUserName = AuthenticationUtil.getFullyAuthenticatedUser();
                    context.setActorId(currentUserName);

                    // construct a new process
                    GraphSession graphSession = context.getGraphSession();
                    ProcessDefinition processDefinition = getProcessDefinition(graphSession, workflowDefinitionId);
                    ProcessInstance processInstance = new ProcessInstance(processDefinition);
                    processInstance.setKey(GUID.generate());
                 
                    // assign initial process context
                    ContextInstance processContext = processInstance.getContextInstance();
                    processContext.setVariable("cancelled", false);
                    NodeRef companyHome = getCompanyHome();
                    processContext.setVariable("companyhome", new JBPMNode(companyHome, serviceRegistry));
                    NodeRef initiatorPerson = mapNameToPerson(currentUserName);
                    if (initiatorPerson != null)
                    {
                        processContext.setVariable("initiator", new JBPMNode(initiatorPerson, serviceRegistry));
                        NodeRef initiatorHome = (NodeRef)nodeService.getProperty(initiatorPerson, ContentModel.PROP_HOMEFOLDER);
                        if (initiatorHome != null)
                        {
                            processContext.setVariable("initiatorhome", new JBPMNode(initiatorHome, serviceRegistry));
                        }
                    }
                    processContext.setVariable("workflowinstanceid", createGlobalId(new Long(processInstance.getId()).toString()));
                    
                    // create the start task if one exists
                    Token token = processInstance.getRootToken();
                    Task startTask = processInstance.getTaskMgmtInstance().getTaskMgmtDefinition().getStartTask();
                    if (startTask != null)
                    {
                        TaskInstance taskInstance = processInstance.getTaskMgmtInstance().createStartTaskInstance();
                        setTaskProperties(taskInstance, parameters);
                        token = taskInstance.getToken();
                    }

                    // Save the process instance along with the task instance
                    context.save(processInstance);
                    return createWorkflowPath(token);
                }
            });
        }
        catch(JbpmException e)
        {
            throw getStartWorkflowException(workflowDefinitionId, e);
        }
		catch (DataAccessException e) 
		{
		    throw getStartWorkflowException(workflowDefinitionId, e);
		}
    }

    private WorkflowException getStartWorkflowException(final String workflowDefinitionId, Exception e)
    {
        String msg = messageService.getMessage(ERR_START_WORKFLOW, workflowDefinitionId);
        return new WorkflowException(msg, e);
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#getActiveWorkflows(java.lang.String)
     */    
    public List<WorkflowInstance> getActiveWorkflows(final String workflowDefinitionId)
    {
        return getWorkflows(new WorkflowInstanceQuery(workflowDefinitionId, true));
        //3.4.8 return getWorkflowsInternal(workflowDefinitionId, true);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#getCompletedWorkflows(java.lang.String)
     */    
    public List<WorkflowInstance> getCompletedWorkflows(final String workflowDefinitionId)
    {
        return getWorkflows(new WorkflowInstanceQuery(workflowDefinitionId, false));
        //3.4.8 return getWorkflowsInternal(workflowDefinitionId, false);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#getWorkflows(java.lang.String)
     */
    public List<WorkflowInstance> getWorkflows(final String workflowDefinitionId)
    {
        return getWorkflows(new WorkflowInstanceQuery(workflowDefinitionId));
        //3.4.8 return getWorkflowsInternal(workflowDefinitionId, null);
    }
    
/* 3.4.8
    @SuppressWarnings("unchecked")
    private List<WorkflowInstance> getWorkflowsInternal(final String workflowDefinitionId, final Boolean active)
    {
        try
        {
            return (List<WorkflowInstance>) jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    GraphSession graphSession = context.getGraphSession();
                    List<ProcessInstance> processInstances = graphSession.findProcessInstances(getJbpmId(workflowDefinitionId));
                    List<WorkflowInstance> workflowInstances = new ArrayList<WorkflowInstance>(processInstances.size());
                    for (ProcessInstance processInstance : processInstances)
                    {
                        if ((active == null) || (!active && processInstance.hasEnded()) || (active && !processInstance.hasEnded()))
                        {
                            WorkflowInstance workflowInstance = createWorkflowInstance(processInstance);
                            workflowInstances.add(workflowInstance);
                        }
                    }
                    return workflowInstances;
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_ACTIVE_WORKFLOW_INSTS, workflowDefinitionId);
            throw new WorkflowException(msg, e);
        }
    }
*/
    /* add 3.4.11 */
    @SuppressWarnings("unchecked")
	public List<WorkflowInstance> getWorkflows(final WorkflowInstanceQuery query)
    {
        try
        {
            return (List<WorkflowInstance>) jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {




                    Session session = context.getSession();
                    List<ProcessInstance> instances = getProcessInstances(session, query);
                    return convertWorkflows(instances);
                }
            });

        }
        catch (JbpmException e)
        {

            String msg = messageService.getMessage(ERR_GET_ACTIVE_WORKFLOW_INSTS, query.getWorkflowDefinitionId());
            throw new WorkflowException(msg, e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<ProcessInstance> getProcessInstances(Session session, final WorkflowInstanceQuery query)
    {


        return (List<ProcessInstance>) jbpmTemplate.execute(new JbpmCallback()
        {
            public Object doInJbpm(JbpmContext context)
            {
                Session session = context.getSession();

                StringBuilder processSelect = new StringBuilder(1024)
                        .append("select process from org.jbpm.graph.exe.ProcessInstance as process");
                StringBuilder processWhere = new StringBuilder(1024);
                Map<String, Object> processMap = new TreeMap<String, Object>();

                Long processDefId = query.getWorkflowDefinitionId() == null ? null : getJbpmId(query
                        .getWorkflowDefinitionId());
                if (processDefId != null)
                {
                    processSelect.append(" join process.processDefinition as definition");
                    processWhere.append(" and definition.id = :processDefId");
                    processMap.put("processDefId", processDefId);
                }

                List<String> exludedDefs = query.getExcludedDefinitions();
                if (exludedDefs != null && exludedDefs.size() > 0)
                {
                    if (processDefId == null)
                    {
                        processSelect.append(" join process.processDefinition as definition");
                    }

                    for (String exDef : exludedDefs)
                    {
                        exDef = BPMEngineRegistry.getLocalId(exDef);
                        exDef = exDef.replaceAll("\\*", "%");
                        processWhere.append(" and definition.name not like '").append(exDef).append("'");
                    }
                }

                if (Boolean.TRUE.equals(query.getActive()))
                {
                    processWhere.append(" and process.end is null");
                }
                else if (Boolean.FALSE.equals(query.getActive()))
                {
                    processWhere.append(" and process.end is not null");
                }

                // Check start range
                if (query.getStartBefore() != null)
                {
                    processWhere.append(" and process.start <= :processStartBefore");
                    processMap.put("processStartBefore", query.getStartBefore());
                }
                if (query.getStartAfter() != null)
                {
                    processWhere.append(" and process.start >= :processStartAfter");
                    processMap.put("processStartAfter", query.getStartAfter());
                }

                // check end range
                if (query.getEndBefore() != null)
                {
                    processWhere.append(" and process.end <= :processEndBefore");
                    processMap.put("processEndBefore", query.getEndBefore());
                }
                if (query.getEndAfter() != null)
                {
                    processWhere.append(" and process.end >= :processEndAfter");
                    processMap.put("processEndAfter", query.getEndAfter());
                }

                if (query.getCustomProps() != null)
                {
                    processSelect.append(" join process.instances as contextInstance").append(
                            " join contextInstance.tokenVariableMaps as tokenVariableMap");

                    processWhere.append(" and contextInstance.class = org.jbpm.context.exe.ContextInstance").append(
                            " and tokenVariableMap.token = process.rootToken");

                    int i = 0;
                    for (Map.Entry<QName, Object> prop : query.getCustomProps().entrySet())
                    {
                        i++;
                        String variable = "var" + i;
                        processMap.put(variable + "name", mapQNameToName(prop.getKey()));
                        if (prop.getValue() == null)
                        {
                            processSelect.append(", ").append(NullInstance.class.getName()).append(" as ").append(
                                    variable);
                            processWhere.append(" and ").append(variable)
                                    .append(".tokenVariableMap = tokenVariableMap").append(" and ").append(variable)
                                    .append(".name = :").append(variable).append("name");
                        }
                        else
                        {
                            PropertyDefinition propertyDefinition = dictionaryService.getProperty(prop.getKey());
                            if (propertyDefinition == null)
                            {
                                processSelect.append(", ").append(StringInstance.class.getName()).append(" as ")
                                        .append(variable);
                                processWhere.append(" and ").append(variable).append(
                                        ".tokenVariableMap = tokenVariableMap").append(" and ").append(variable)
                                        .append(".name = :").append(variable).append("name").append(" and ").append(
                                                variable).append(".value = :").append(variable).append("value");
                                processMap.put(variable + "value", prop.getValue().toString());
                            }
                            else
                            {
                                String propertyType = propertyDefinition.getDataType().getJavaClassName();
                                if (propertyType.equals("java.lang.String"))
                                {
                                    processSelect.append(", ").append(StringInstance.class.getName()).append(" as ")
                                            .append(variable);
                                    processWhere.append(" and ").append(variable).append(
                                            ".tokenVariableMap = tokenVariableMap").append(" and ").append(variable)
                                            .append(".name = :").append(variable).append("name").append(" and ")
                                            .append(variable).append(".value = :").append(variable).append("value");
                                    processMap.put(variable + "value", prop.getValue().toString());
                                }
                                else if (propertyType.equals("java.lang.Long")
                                        || propertyType.equals("java.lang.Integer"))
                                {
                                    processSelect.append(", ").append(LongInstance.class.getName()).append(" as ")
                                            .append(variable);
                                    processWhere.append(" and ").append(variable).append(
                                            ".tokenVariableMap = tokenVariableMap").append(" and ").append(variable)
                                            .append(".name = :").append(variable).append("name").append(" and ")
                                            .append(variable).append(".value = :").append(variable).append("value");
                                    processMap.put(variable + "value", new Long(prop.getValue().toString()));
                                }
                                else if (propertyType.equals("java.util.Date"))
                                {
                                    processSelect.append(", ").append(DateInstance.class.getName()).append(
                                    " as ").append(variable);
                                    Map<DatePosition, Date> dateProps = (Map<DatePosition, Date>) prop.getValue();
                                    int datePropNum = 0;
                                    for (Map.Entry<DatePosition, Date> dateProp : dateProps.entrySet())
                                    {
                                    	datePropNum++;
                                        if (dateProp.getValue() != null)
                                        {
                                            processWhere.append(" and ").append(variable).append(
                                                    ".tokenVariableMap = tokenVariableMap").append(" and ").append(
                                                    variable).append(".name = :").append(variable).append("name");
                                            processMap.put(variable + "value" + datePropNum, dateProp.getValue());
                                            if (dateProp.getKey() == DatePosition.BEFORE)
                                            {
                                                processWhere.append(" and ").append(variable).append(".value <= :")
                                                        .append(variable).append("value").append(datePropNum);
                                            }
                                            if (dateProp.getKey() == DatePosition.AFTER)
                                            {
                                                processWhere.append(" and ").append(variable).append(".value >= :")
                                                        .append(variable).append("value").append(datePropNum);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (processWhere.length() > 4)
                {
                    processSelect.append(" where");
                    processSelect.append(processWhere, 4, processWhere.length());
                }
                processSelect.append(" order by process.id");
                return session.createQuery(processSelect.toString()).setProperties(processMap).list();
            }
        });
    }


    private List<WorkflowInstance> convertWorkflows(Collection<ProcessInstance> instances)
    {


        List<WorkflowInstance> result = new ArrayList<WorkflowInstance>(instances.size());
        for (ProcessInstance pInstance : instances)
        {
            result.add(createWorkflowInstance(pInstance));
        }
        return result;

    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#getWorkflowById(java.lang.String)
     */
    public WorkflowInstance getWorkflowById(final String workflowId)
    {
        try
        {
            return (WorkflowInstance) jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    // retrieve workflow
                    GraphSession graphSession = context.getGraphSession();
                    ProcessInstance processInstance = getProcessInstanceIfExists(graphSession, workflowId);
                    return processInstance == null ? null : createWorkflowInstance(processInstance);
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_WORKFLOW_INST_BY_ID);
            throw new WorkflowException(msg, e);
        }        
    }
    
    private ProcessInstance getProcessInstanceIfExists(GraphSession graphSession, String workflowId)
    {
        ProcessInstance processInstance = graphSession.getProcessInstance(getJbpmId(workflowId));
        if ((processInstance != null) && (tenantService.isEnabled()))
        {
            try
            {
                tenantService.checkDomain(processInstance.getProcessDefinition().getName()); // throws exception if domain mismatch
            } 
            catch (RuntimeException re)
            {
                processInstance = null;
            }
        }
        return processInstance;
    }
    
    /**
     * Gets a jBPM Process Instance
     * @param graphSession  jBPM graph session
     * @param workflowId  workflow id
     * @return  process instance
     */
    protected ProcessInstance getProcessInstance(GraphSession graphSession, String workflowId)
    {
        ProcessInstance processInstance = getProcessInstanceIfExists(graphSession, workflowId);
        if (processInstance == null)
        {
            String msg = messageService.getMessage(ERR_GET_PROCESS_INSTANCE, workflowId);
            throw new WorkflowException(msg);
        }
        return processInstance;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#getWorkflowPaths(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<WorkflowPath> getWorkflowPaths(final String workflowId)
    {
        try
        {
            return (List<WorkflowPath>) jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    // retrieve process instance
                    GraphSession graphSession = context.getGraphSession();
                    ProcessInstance processInstance = getProcessInstance(graphSession, workflowId);
                    
                    // convert jBPM tokens to workflow posisitons
                    List<Token> tokens = processInstance.findAllTokens();
                    List<WorkflowPath> paths = new ArrayList<WorkflowPath>(tokens.size());
                    for (Token token : tokens)
                    {
                        if (!token.hasEnded())
                        {
                            WorkflowPath path = createWorkflowPath(token);
                            paths.add(path);
                        }
                    }
                    
                    return paths;
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_WORKFLOW_PATHS, workflowId);
            throw new WorkflowException(msg, e);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#getPathProperties(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Map<QName, Serializable> getPathProperties(final String pathId)
    {
        try
        {
            return (Map<QName, Serializable>) jbpmTemplate.execute(new JbpmCallback()
            {
                public Map<QName, Serializable> doInJbpm(JbpmContext context)
                {
                    // retrieve jBPM token for workflow position
                    GraphSession graphSession = context.getGraphSession();
                    Token token = getWorkflowToken(graphSession, pathId);
                    ContextInstance instanceContext = token.getProcessInstance().getContextInstance();
                    Map<QName, Serializable> properties = new HashMap<QName, Serializable>(10);
                    while (token != null)
                    {

                        TokenVariableMap varMap = instanceContext.getTokenVariableMap(token);
                        if (varMap != null)
                        {
                            Map<String, Object> tokenVars = varMap.getVariablesLocally();
                            for (Map.Entry<String, Object> entry : tokenVars.entrySet())
                            {
                                String key = entry.getKey();
                                QName qname = mapNameToQName(key);
                                
                                if (!properties.containsKey(key))
                                {
                                    Serializable value = convertValue(entry.getValue());
                                    properties.put(qname, value);
                                }
                            }
                        }
                        token = token.getParent();
                    }
                                        
                    return properties;
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_PATH_PROPERTIES, pathId);
            throw new WorkflowException(msg, e);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#cancelWorkflow(java.lang.String)
     */
    public WorkflowInstance cancelWorkflow(final String workflowId)
    {

        return cancelWorkflows(Collections.singletonList(workflowId)).get(0);        
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#cancelWorkflows
     */
    @SuppressWarnings("unchecked")
    public List<WorkflowInstance> cancelWorkflows(final List<String>workflowIds)
    {
        return (List<WorkflowInstance>) jbpmTemplate.execute(new JbpmCallback()
        {
            public Object doInJbpm(JbpmContext context)
            {

                // Bypass the cache making sure not to flush it
                Session session = context.getSession();                                        
                CacheMode cacheMode = session.getCacheMode();
                FlushMode flushMode = session.getFlushMode();
                session.setCacheMode(CacheMode.GET);
                session.setFlushMode(FlushMode.MANUAL);
                try
                {
                    List<WorkflowInstance> workflowInstances = new ArrayList<WorkflowInstance>(workflowIds.size());
                    Map<String, ProcessInstance> processInstances = new HashMap<String, ProcessInstance>(workflowIds.size() * 2);
                    GraphSession graphSession = context.getGraphSession();

                    // retrieve and cancel process instances
                    for (String workflowId : workflowIds)
                    {
                        try
                        {
                            ProcessInstance processInstance = getProcessInstance(graphSession, workflowId);
                            processInstance.getContextInstance().setVariable("cancelled", true);
                            processInstance.end();                            
                            processInstances.put(workflowId, processInstance);                                                        
                        }
                        catch(JbpmException e)
                        {
                            String msg = messageService.getMessage(ERR_CANCEL_WORKFLOW, workflowId);
                            throw new WorkflowException(msg, JbpmAccessor.convertJbpmException(e));
                        }
                    }

                    // Flush at the end of the batch
                    session.flush();

                    for (String workflowId : workflowIds)
                    {
                        try
                        {
                            // retrieve process instance
                            ProcessInstance processInstance = processInstances.get(workflowId);
                            // TODO: Determine if this is the most appropriate way to cancel workflow...
                            //       It might be useful to record point at which it was cancelled etc
                            workflowInstances.add(createWorkflowInstance(processInstance));
                            
                            // delete the process instance
                            graphSession.deleteProcessInstance(processInstance, true, true);



                        }
                        catch(JbpmException e)
                        {
                            String msg = messageService.getMessage(ERR_CANCEL_WORKFLOW, workflowId);
                            throw new WorkflowException(msg, JbpmAccessor.convertJbpmException(e));
                        }
                    }

                    // Flush at the end of the batch
                    session.flush();
                    return workflowInstances; 
                }
                finally
                {
                    session.setCacheMode(cacheMode);
                    session.setFlushMode(flushMode);
                }
            }
        });
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#cancelWorkflow(java.lang.String)
     */
    public WorkflowInstance deleteWorkflow(final String workflowId)
    {
        try
        {
            return (WorkflowInstance) jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    // retrieve and cancel process instance
                    GraphSession graphSession = context.getGraphSession();
                    ProcessInstance processInstance = getProcessInstance(graphSession, workflowId);
                    WorkflowInstance workflowInstance = createWorkflowInstance(processInstance);
                    
                    // delete the process instance
                    graphSession.deleteProcessInstance(processInstance, true, true);
                    workflowInstance.active = false;
                    workflowInstance.endDate = new Date();
                    return workflowInstance; 
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_DELETE_WORKFLOW, workflowId);
            throw new WorkflowException(msg, e);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#signal(java.lang.String, java.lang.String)
     */
    public WorkflowPath signal(final String pathId, final String transition)
    {
        try
        {
            return (WorkflowPath) jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    // retrieve jBPM token for workflow position
                    GraphSession graphSession = context.getGraphSession();
                    Token token = getWorkflowToken(graphSession, pathId);
                    
                    // signal the transition
                    if (transition == null)
                    {
                        token.signal();
                    }
                    else
                    {
                        Node node = token.getNode();
                        if (!node.hasLeavingTransition(transition))
                        {
                            throw new WorkflowException("Transition '" + transition + "' is invalid for Workflow path '" + pathId + "'");
                        }
                        token.signal(transition);
                    }
                    
                    // save
                    ProcessInstance processInstance = token.getProcessInstance();
                    context.save(processInstance);
                    
                    // return new workflow path
                    return createWorkflowPath(token);
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_SIGNAL_TRANSITION, transition, pathId);
            throw new WorkflowException(msg, e);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#fireEvent(java.lang.String, java.lang.String)
     */
    public WorkflowPath fireEvent(final String pathId, final String event)
    {
        try
        {
            return (WorkflowPath) jbpmTemplate.execute(new JbpmCallback()
            {
                @SuppressWarnings("unchecked")
                public Object doInJbpm(JbpmContext context)
                {
                    // NOTE: Do not allow jBPM built-in events to be fired
                    if (event.equals(Event.EVENTTYPE_AFTER_SIGNAL) ||
                        event.equals(Event.EVENTTYPE_BEFORE_SIGNAL) ||
                        event.equals(Event.EVENTTYPE_NODE_ENTER) ||
                        event.equals(Event.EVENTTYPE_NODE_LEAVE) ||
                        event.equals(Event.EVENTTYPE_PROCESS_END) ||
                        event.equals(Event.EVENTTYPE_PROCESS_START) ||
                        event.equals(Event.EVENTTYPE_SUBPROCESS_CREATED) ||
                        event.equals(Event.EVENTTYPE_SUBPROCESS_END) ||
                        event.equals(Event.EVENTTYPE_SUPERSTATE_ENTER) ||
                        event.equals(Event.EVENTTYPE_SUPERSTATE_LEAVE) ||
                        event.equals(Event.EVENTTYPE_TASK_ASSIGN) ||
                        event.equals(Event.EVENTTYPE_TASK_CREATE) ||
                        event.equals(Event.EVENTTYPE_TASK_END) ||
                        event.equals(Event.EVENTTYPE_TASK_START) ||
                        event.equals(Event.EVENTTYPE_TIMER) ||
                        event.equals(Event.EVENTTYPE_TRANSITION))
                    {
                        String msg = messageService.getMessage(ERR_INVALID_EVENT, event);
                        throw new WorkflowException(msg);
                    }
                    
                    // retrieve jBPM token for workflow position
                    GraphSession graphSession = context.getGraphSession();
                    Token token = getWorkflowToken(graphSession, pathId);
                    
                    ExecutionContext executionContext = new ExecutionContext(token);
                    TaskMgmtSession taskSession = context.getTaskMgmtSession();
                    List<TaskInstance> tasks = taskSession.findTaskInstancesByToken(token.getId());
                    if (tasks.size() == 0)
                    {
                        // fire the event against current node for the token
                        Node node = token.getNode();
                        node.fireEvent(event, executionContext);
                    }
                    else
                    {
                        // fire the event against tasks associated with the node
                        // NOTE: this will also propagate the event to the node
                        for (TaskInstance task : tasks)
                        {
                            executionContext.setTaskInstance(task);
                            task.getTask().fireEvent(event, executionContext);
                        }
                    }
                    
                    // save
                    ProcessInstance processInstance = token.getProcessInstance();
                    context.save(processInstance);
                    
                    // return new workflow path
                    return createWorkflowPath(token);
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_FIRE_EVENT, event, pathId);
            throw new WorkflowException(msg, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<WorkflowTask> getTasksForWorkflowPath(String pathId)
    {
        return getTasksForWorkflowPath(pathId, false);
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#getTasksForWorkflowPath(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<WorkflowTask> getTasksForWorkflowPath(final String pathId, final boolean sameSession)
    {
        try
        {
            return (List<WorkflowTask>) jbpmTemplate.execute(new JbpmCallback()
            {
                public List<WorkflowTask> doInJbpm(JbpmContext context)
                {
                    // retrieve tasks at specified workflow path
                    GraphSession graphSession = context.getGraphSession();
                    Token token = getWorkflowToken(graphSession, pathId);
                    TaskMgmtSession taskSession = context.getTaskMgmtSession();
                    List<TaskInstance> tasks = taskSession.findTaskInstancesByToken(token.getId());
                    return getWorkflowTasks(tasks, sameSession);
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_TASKS_FOR_PATH, pathId);
            throw new WorkflowException(msg, e);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.WorkflowComponent#getTimers(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<WorkflowTimer> getTimers(final String workflowId)
    {
        try
        {
            return (List<WorkflowTimer>) jbpmTemplate.execute(new JbpmCallback()
            {
                public List<WorkflowTimer> doInJbpm(JbpmContext context)
                {
                    // retrieve process
                    GraphSession graphSession = context.getGraphSession();
                    ProcessInstance process = getProcessInstance(graphSession, workflowId);

                    // retrieve timers for process
                    Session session = context.getSession();
                    Query query = session.createQuery(PROCESS_TIMERS_QUERY);
                    query.setEntity("process", process);
                    List<Timer> timers = query.list();
                    
                    // convert timers to appropriate service response format 
                    List<WorkflowTimer> workflowTimers = new ArrayList<WorkflowTimer>(timers.size());
                    for (Timer timer : timers)
                    {
                        WorkflowTimer workflowTimer = createWorkflowTimer(timer);
                        workflowTimers.add(workflowTimer);
                    }
                    return workflowTimers;
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_TIMERS, workflowId);
            throw new WorkflowException(msg, e);
        }
    }
    
    //
    // Task Management ...
    //
    
    /**
     * {@inheritDoc}
     */
    public List<WorkflowTask> getAssignedTasks(String authority, WorkflowTaskState state)
    {
        return getAssignedTasks(authority, state, false);
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.TaskComponent#getAssignedTasks(java.lang.String, org.alfresco.service.cmr.workflow.WorkflowTaskState)
     */
    @SuppressWarnings("unchecked")
    public List<WorkflowTask> getAssignedTasks(final String authority, final WorkflowTaskState state, final boolean sameSession)
    {
        try
        {
            // arsenyko: I think it's necessary to avoid undesirable growth,
            // since a cache implementation is quite simple and doesn't care about
            // max elements in the cache.
            qNameCache.clear();

            return (List<WorkflowTask>) jbpmTemplate.execute(new JbpmCallback()
            {
                public List<WorkflowTask> doInJbpm(JbpmContext context)
                {
                    // retrieve tasks assigned to authority
                    List<TaskInstance> tasks;
                    if (state.equals(WorkflowTaskState.IN_PROGRESS))
                    {
                        Session session = context.getSession();
                        Query query = session.getNamedQuery("org.alfresco.repo.workflow.findTaskInstancesByActorId");
                        query.setString("actorId", authority);
                        query.setBoolean("true", true);
                        List<WorkflowTask> workflowTasks = getWorkflowTasks(session, query.list());
                        // Do we need to clear a session here? It takes 3 seconds with 2000 workflows.
                        // session.clear();
                        return workflowTasks;

                        //TaskMgmtSession taskSession = context.getTaskMgmtSession();
                        //tasks = taskSession.findTaskInstances(authority);
                    }
                    else
                    {
                        // Note: This method is not implemented by jBPM
                        tasks = findCompletedTaskInstances(context, authority);
						return getWorkflowTasks(tasks, sameSession);

                    }
                    
                }
                
                /**
                 * Gets the completed task list for the specified actor
                 * 
                 * TODO: This method provides a query that's not in JBPM!  Look to have JBPM implement this.
                 * 
                 * @param jbpmContext  the jbpm context
                 * @param actorId  the actor to retrieve tasks for
                 * @return  the tasks
                 */
                @SuppressWarnings("rawtypes")
                private List findCompletedTaskInstances(JbpmContext jbpmContext, String actorId)
                {
                    List result = null;
                    try
                    {
                        Session session = jbpmContext.getSession();
                        Query query = session.createQuery(COMPLETED_TASKS_QUERY);
                        query.setString("actorId", actorId);
                        result = query.list();
                    }
                    catch (Exception e)
                    {
                        String msg = messageService.getMessage(ERR_FIND_COMPLETED_TASK_INSTS, actorId);
                        throw new JbpmException(msg, e);
                    }
                    return result;
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_ASSIGNED_TASKS, authority, state);
            throw new WorkflowException(msg, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<WorkflowTask> getPooledTasks(List<String> authorities)
    {
        return getPooledTasks(authorities, false);
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.TaskComponent#getPooledTasks(java.util.List)
     */
    @SuppressWarnings("unchecked")
    public List<WorkflowTask> getPooledTasks(final List<String> authorities, final boolean sameSession)
    {
        try
        {
            return (List<WorkflowTask>) jbpmTemplate.execute(new JbpmCallback()
            {
                public List<WorkflowTask> doInJbpm(JbpmContext context)
                {
                    // retrieve pooled tasks for all flattened authorities
                    TaskMgmtSession taskSession = context.getTaskMgmtSession();
                    List<TaskInstance> tasks = taskSession.findPooledTaskInstances(authorities);
                    return getWorkflowTasks(tasks, sameSession);
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_POOLED_TASKS, authorities);
            throw new WorkflowException(msg, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<WorkflowTask> queryTasks(WorkflowTaskQuery query)
    {
        return queryTasks(query, false);
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.TaskComponent#queryTasks(org.alfresco.service.cmr.workflow.WorkflowTaskFilter)
     */
    @SuppressWarnings("unchecked")
    public List<WorkflowTask> queryTasks(final WorkflowTaskQuery query, final boolean sameSession)
    {
        try
        {
            return (List<WorkflowTask>) jbpmTemplate.execute(new JbpmCallback()
            {
                public List<WorkflowTask> doInJbpm(JbpmContext context)
                {
                    // Bypass the cache making sure not to flush it
                    Session session = context.getSession();
                    CacheMode cacheMode = session.getCacheMode();
                    try
                    {
                        session.setCacheMode(CacheMode.GET);
                    
                    if ((query.getProcessName() != null) && (tenantService.isEnabled()))
                    {
                        query.setProcessName(tenantService.getName(query.getProcessName()));
                    }
                    
                    Criteria criteria = createTaskQueryCriteria(session, query);
                    List<TaskInstance> tasks = criteria.list();
                    List<WorkflowTask> workflowTasks = getWorkflowTasks(tasks, sameSession);
                    return workflowTasks;
                }
                finally
                {
                    session.setCacheMode(cacheMode);
                }
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_QUERY_TASKS, query);
            throw new WorkflowException(msg, e);
        }
    }
    
    protected List<WorkflowTask> getWorkflowTasks(Session session, List<Object[]> rows)
    {
        List<WorkflowTask> workflowTasks = new ArrayList<WorkflowTask>(rows.size());

        /// ------------------------

        // Preload data into L1 session
        List<Long> taskInstanceIds = new ArrayList<Long>(rows.size());
        List<Long> contextInstanceIds = new ArrayList<Long>(rows.size());
        for (Object[] row : rows)
        {
            TaskInstance ti = (TaskInstance) row[0];
            taskInstanceIds.add(ti.getId());
            ContextInstance ci = (ContextInstance) row[8];
            contextInstanceIds.add(ci.getId());
        }
        Map<Long, TaskInstance> taskInstanceCache = new HashMap<Long, TaskInstance>(rows.size());
        if (taskInstanceIds.size() > 0)
        {
            taskInstanceCache = cacheTasks(session, taskInstanceIds);
        }
        Map<Long, TokenVariableMap> variablesCache = new HashMap<Long, TokenVariableMap>(rows.size());
        if (contextInstanceIds.size() > 0)
        {
            variablesCache = cacheVariables(session, contextInstanceIds);
        }
        taskInstanceIds.clear();
        contextInstanceIds.clear();
        /// ------------------------
        for(Object[] row : rows)
        {
            TaskInstance ti = (TaskInstance) row[0];
            Token token = (Token)row[2];
            ProcessInstance processInstance = (ProcessInstance)row[3];
            Node node = (Node)row[4];
            Task task = (Task)row[5];
            ProcessDefinition processDefinition = (ProcessDefinition)row[6];
            Task startTask = (Task)row[7];
            ContextInstance contextInstance = (ContextInstance) row[8];
            
            if (tenantService.isEnabled())
            {                           
                try 
                {
                    tenantService.checkDomain(processDefinition.getName());
                }
                catch (RuntimeException re)
                {
                    // deliberately skip this one - due to domain mismatch - eg. when querying by group authority
                    continue;
                } 
            }
            // TaskInstance with some precached properties  
            TaskInstance helperTi = taskInstanceCache.get(ti.getId());
            // WorkflowTask
            WorkflowTask workflowTask = new WorkflowTask();
            workflowTask.id = createGlobalId(new Long(ti.getId()).toString());
            workflowTask.name = ti.getName();
            workflowTask.state = getWorkflowTaskState(ti);
            // WorkflowPath
            WorkflowPath path = new WorkflowPath();
            String tokenId = token.getFullName().replace("/", WORKFLOW_TOKEN_SEPERATOR);
            path.id = createGlobalId(processInstance.getId() + WORKFLOW_PATH_SEPERATOR + tokenId);
            // WorkflowInstance
            WorkflowInstance workflowInstance = new WorkflowInstance();
            workflowInstance.id = createGlobalId(new Long(processInstance.getId()).toString());
            
            @SuppressWarnings("unchecked")
            Map<String, Object> variables = variablesCache.get(contextInstance.getTokenVariableMap(token).getToken().getId()).getVariables();
            //3.4.8 Map<String, Object> variables = variablesCache.get(contextInstance.getId()).getVariables();
            
            workflowInstance.description = (String)variables.get(mapQNameToName(WorkflowModel.PROP_WORKFLOW_DESCRIPTION));
            String name = tenantService.getBaseName(processDefinition.getName());
            String title = getLabel(name + ".workflow", TITLE_LABEL, name);
            String description = getLabel(name + ".workflow", DESC_LABEL, title);
            workflowInstance.definition = new WorkflowDefinition(createGlobalId(new Long(processDefinition.getId()).toString()),
                                          createGlobalId(name),
                                          new Integer(processDefinition.getVersion()).toString(),
                                          title,
                                          description,
                                          (startTask != null
                                           ? createWorkflowTaskDefinition(startTask)
                                           : null));
            workflowInstance.active = !processInstance.hasEnded();
            JBPMNode initiator = (JBPMNode)variables.get("initiator");
            if (initiator != null)
            {
                workflowInstance.initiator = initiator.getNodeRef(); 
            }
            JBPMNode context = (JBPMNode)variables.get(mapQNameToName(WorkflowModel.PROP_CONTEXT));
            if (context != null)
            {
                workflowInstance.context = context.getNodeRef(); 
            }
            JBPMNode workflowPackage = (JBPMNode)variables.get(mapQNameToName(WorkflowModel.ASSOC_PACKAGE));
            if (workflowPackage != null)
            {
                workflowInstance.workflowPackage = workflowPackage.getNodeRef(); 
            }
            workflowInstance.priority = (Integer)variables.get(mapQNameToName(WorkflowModel.PROP_WORKFLOW_PRIORITY));
            workflowInstance.dueDate = (Date)variables.get(mapQNameToName(WorkflowModel.PROP_WORKFLOW_DUE_DATE));
            workflowInstance.startDate = processInstance.getStart();
            workflowInstance.endDate = processInstance.getEnd();
            path.instance = workflowInstance;
            // WorkflowNode
            path.node = createWorkflowNode(node);
            path.active = !token.hasEnded();
            workflowTask.path = path;
            // WorkflowTaskDefinition
            workflowTask.definition = createWorkflowTaskDefinition(task);
            // WorkflowTaskProperies
            workflowTask.properties = getTaskProperties(helperTi != null ? helperTi : ti, false, variablesCache);
            
            String workflowDisplayId = processDefinition.getName() + ".task." + workflowTask.name;
            workflowTask.title = getLabel(workflowDisplayId, TITLE_LABEL, null);
            if (workflowTask.title == null)
            {
                workflowTask.title = workflowTask.definition.metadata.getTitle();
                if (workflowTask.title == null)
                {
                    workflowTask.title = workflowTask.name;
                }
            }
            workflowTask.description = getLabel(workflowDisplayId, DESC_LABEL, null);
            if (workflowTask.description == null)
            {
                description = workflowTask.definition.metadata.getDescription();
                workflowTask.description = (description == null) ? workflowTask.title : description;
            }

            workflowTasks.add(workflowTask);
        }
        return workflowTasks;
    }
    
    private Map<Long, TokenVariableMap> cacheVariables(Session session, List<Long> ids)
    {
        // Preload data into L1 session
        int batchSize = 800;            // Must limit IN clause size!
        List<Long> batch = new ArrayList<Long>(ids.size());
        Map<Long, TokenVariableMap> cachedResults = new HashMap<Long, TokenVariableMap>();
        for (Long id : ids)
        {
            batch.add(id);
            if (batch.size() >= batchSize)
            {
                cacheVariablesNoBatch(session, batch, cachedResults);
                batch.clear();
            }
        }
        if (batch.size() > 0)
        {
            cacheVariablesNoBatch(session, batch, cachedResults);
        }
        batch.clear();
        return cachedResults;
    }

    @SuppressWarnings("unchecked")
    private void cacheVariablesNoBatch(Session session, List<Long> contextInstanceIds, Map<Long, TokenVariableMap> variablesCache)
    {
        Query query = session.getNamedQuery("org.alfresco.repo.workflow.cacheInstanceVariables");
        query.setParameterList("ids", contextInstanceIds);
        query.setCacheMode(CacheMode.PUT);
        query.setFlushMode(FlushMode.MANUAL);
        query.setCacheable(true);
        
        List<TokenVariableMap> results = (List<TokenVariableMap>) query.list();
        for (TokenVariableMap tokenVariableMap : results)
        {
            variablesCache.put(tokenVariableMap.getToken().getId(), tokenVariableMap);
            //3.4.8 variablesCache.put(tokenVariableMap.getContextInstance().getId(), tokenVariableMap);
        }
    }
    
    private Map<Long, TaskInstance> cacheTasks(Session session, List<Long> ids)
    {
        // Preload data into L1 session
        int batchSize = 800;            // Must limit IN clause size!
        List<Long> batch = new ArrayList<Long>(ids.size());
        Map<Long, TaskInstance> cachedResults = new HashMap<Long, TaskInstance>();
        for (Long id : ids)
        {
            batch.add(id);
            if (batch.size() >= batchSize)
            {
                cacheTasksNoBatch(session, batch, cachedResults);
                batch.clear();
            }
        }
        if (batch.size() > 0)
        {
            cacheTasksNoBatch(session, batch, cachedResults);
        }
        batch.clear();
        return cachedResults;
    }

    @SuppressWarnings("unchecked")
    private void cacheTasksNoBatch(Session session, List<Long> taskInstanceIds, Map<Long, TaskInstance> returnMap)
    {
        Query query = session.getNamedQuery("org.alfresco.repo.workflow.cacheTaskInstanceProperties");
        query.setParameterList("ids", taskInstanceIds);
        query.setCacheMode(CacheMode.PUT);
        query.setFlushMode(FlushMode.MANUAL);
        query.setCacheable(true);
        
        List<TaskInstance> results = (List<TaskInstance>) query.list();
        for (TaskInstance taskInstance : results)
        {
            returnMap.put(taskInstance.getId(), taskInstance);
        }
    }
    

    protected List<WorkflowTask> getWorkflowTasks(List<TaskInstance> tasks)
    {



        return getWorkflowTasks(tasks, false);
    }
    
    protected List<WorkflowTask> getWorkflowTasks(List<TaskInstance> tasks, boolean sameSession)
    {
        final List<TaskInstance> filteredTasks;
        if (tenantService.isEnabled())
        {
            filteredTasks = new ArrayList<TaskInstance>(tasks.size());
            for (TaskInstance task : tasks)
            {
                try
                {
                    tenantService.checkDomain(task.getTask().getProcessDefinition().getName());
                    filteredTasks.add(task);
                }
                catch (RuntimeException re)
                {
                    // deliberately skip this one - due to domain mismatch - eg. when querying by group authority
                    continue;
                }
            }
        }
        else
        {
            filteredTasks = tasks;
        }
        
        List<WorkflowTask> workflowTasks;
        if (sameSession)
        {
            workflowTasks = new AbstractList<WorkflowTask>()
            {
                @Override
                public WorkflowTask get(int index)
                {
                    TaskInstance task = filteredTasks.get(index);
                    return createWorkflowTask(task);
                }

                @Override
                public int size()
                {
                    return filteredTasks.size();
                }
            };
        }
        else
        {
            workflowTasks = new ArrayList<WorkflowTask>(filteredTasks.size());
            for (TaskInstance task : filteredTasks)
            {
                WorkflowTask workflowTask = createWorkflowTask(task);
                workflowTasks.add(workflowTask);
            }
         }
        
        return workflowTasks;
    }

    /**
     * Construct a JBPM Hibernate query based on the Task Query provided
     * 
     * @param session
     * @param query
     * @return  jbpm hiberate query criteria
     */
    private Criteria createTaskQueryCriteria(Session session, WorkflowTaskQuery query)
    {
        Criteria task = session.createCriteria(TaskInstance.class);
        
        // task id
        if (query.getTaskId() != null)
        {
            task.add(Restrictions.eq("id", getJbpmId(query.getTaskId())));
        }
        
        // task state
        if (query.getTaskState() != null)
        {
            WorkflowTaskState state = query.getTaskState();
            if (state == WorkflowTaskState.IN_PROGRESS)
            {
                task.add(Restrictions.eq("isOpen", true));
                task.add(Restrictions.isNull("end"));
            }
            else if (state == WorkflowTaskState.COMPLETED)
            {
                task.add(Restrictions.eq("isOpen", false));
                task.add(Restrictions.isNotNull("end"));
            }
        }
        
        // task name
        if (query.getTaskName() != null)
        {
            task.add(Restrictions.eq("name", query.getTaskName().toPrefixString(namespaceService)));
        }
        
        // task actor
        if (query.getActorId() != null)
        {
            task.add(Restrictions.eq("actorId", query.getActorId()));
        }
        
        // task custom properties
        if (query.getTaskCustomProps() != null)
        {
            Map<QName, Object> props = query.getTaskCustomProps();
            if (props.size() > 0)
            {
                Criteria variables = task.createCriteria("variableInstances");
                Disjunction values = Restrictions.disjunction();
                for (Map.Entry<QName, Object> prop : props.entrySet())
                {
                    Conjunction value = Restrictions.conjunction();
                    value.add(Restrictions.eq("name", mapQNameToName(prop.getKey())));
                    value.add(Restrictions.eq("value", prop.getValue().toString()));
                    values.add(value);
                }
                variables.add(values);   
            }
        }
        
        // process criteria
        Criteria process = createProcessCriteria(task, query);
        
        // process custom properties
        if (query.getProcessCustomProps() != null)
        {
            // TODO: Due to Hibernate bug http://opensource.atlassian.com/projects/hibernate/browse/HHH-957
            //       it's not possible to perform a sub-select with the criteria api.  For now issue a
            //       secondary query and create an IN clause.
            Map<QName, Object> props = query.getProcessCustomProps();
            if (props.size() > 0)
            {
                // create criteria for process variables
                Criteria variables = session.createCriteria(VariableInstance.class);
                variables.setProjection(Projections.distinct(Property.forName("processInstance")));
                Disjunction values = Restrictions.disjunction();
                for (Map.Entry<QName, Object> prop : props.entrySet())
                {
                    Conjunction value = Restrictions.conjunction();
                    value.add(Restrictions.eq("name", mapQNameToName(prop.getKey())));
                    value.add(Restrictions.eq("value", prop.getValue().toString()));
                    values.add(value);
                }
                variables.add(values);
                
                // note: constrain process variables to same criteria as tasks
                createProcessCriteria(variables, query);
                
                Disjunction processIdCriteria = createProcessIdCriteria(variables);

                // retrieve list of processes matching specified variables
                //List<?> processList = variables.list();
                //Object[] processIds = getProcessIds( processList);

                // constrain tasks by process list
                process = (process == null) ? task.createCriteria("processInstance") : process;
                process.add(processIdCriteria);
                //process.add(Restrictions.in("id", processIds));
            }
        }

        // order by
        if (query.getOrderBy() != null)
        {
            WorkflowTaskQuery.OrderBy[] orderBy = query.getOrderBy();
            for (WorkflowTaskQuery.OrderBy orderByPart : orderBy)
            {
                if (orderByPart == WorkflowTaskQuery.OrderBy.TaskActor_Asc)
                {
                    task.addOrder(Order.asc("actorId"));
                }
                else if (orderByPart == WorkflowTaskQuery.OrderBy.TaskActor_Desc)
                {
                    task.addOrder(Order.desc("actorId"));
                } 
                else if (orderByPart == WorkflowTaskQuery.OrderBy.TaskCreated_Asc)
                {
                    task.addOrder(Order.asc("create"));
                } 
                else if (orderByPart == WorkflowTaskQuery.OrderBy.TaskCreated_Desc)
                {
                    task.addOrder(Order.desc("create"));
                } 
                else if (orderByPart == WorkflowTaskQuery.OrderBy.TaskDue_Asc)
                {
                    task.addOrder(Order.asc("dueDate"));
                } 
                else if (orderByPart == WorkflowTaskQuery.OrderBy.TaskDue_Desc)
                {
                    task.addOrder(Order.desc("dueDate"));
                } 
                else if (orderByPart == WorkflowTaskQuery.OrderBy.TaskId_Asc)
                {
                    task.addOrder(Order.asc("id"));
                } 
                else if (orderByPart == WorkflowTaskQuery.OrderBy.TaskId_Desc)
                {
                    task.addOrder(Order.desc("id"));
                } 
                else if (orderByPart == WorkflowTaskQuery.OrderBy.TaskName_Asc)
                {
                    task.addOrder(Order.asc("name"));
                } 
                else if (orderByPart == WorkflowTaskQuery.OrderBy.TaskName_Desc)
                {
                    task.addOrder(Order.desc("name"));
                } 
                else if (orderByPart == WorkflowTaskQuery.OrderBy.TaskState_Asc)
                {
                    task.addOrder(Order.asc("end"));
                } 
                else if (orderByPart == WorkflowTaskQuery.OrderBy.TaskState_Desc)
                {
                    task.addOrder(Order.desc("end"));
                } 
            }
        }
        
        // limit results
        if (query.getLimit() != -1)
        {
            task.setMaxResults(query.getLimit());
        }
        
        return task;
    }

    /**
     * @param variables
     * @return
     */
    private Disjunction createProcessIdCriteria(Criteria variables)
    {
        // retrieve list of processes matching specified variables
        List<?> processList = variables.list();
        Object[] processIds = getProcessIds( processList);

        // ALF-5841 fix
        int batch = 0;
        List<Object> buf = new ArrayList<Object>(1000);

        Disjunction ids = Restrictions.disjunction();
        for (Object id : processIds)
        {
            if (batch < 1000)
            {
                batch++;
                buf.add(id);
            }
            else
            {
                ids.add(Restrictions.in("id", buf));
                batch = 0;
                buf.clear();
            }
        }

        if (!buf.isEmpty())
        {
            ids.add(Restrictions.in("id", buf));
        }
        return ids;
    }

	private Object[] getProcessIds(List<?> processList) {
		ArrayList<Object> ids = new ArrayList<Object>(processList.size());
		if (processList.isEmpty())
		{
		    ids.add(new Long(-1));
		}
		else
		{
			for (Object obj : processList)
			{
				ProcessInstance instance = (ProcessInstance) obj;
				ids.add(instance.getId());
			}
		}
		return ids.toArray();
	}
    
    /**
     * Create process-specific query criteria
     * 
     * @param root
     * @param query
     * @return
     */
    private Criteria createProcessCriteria(Criteria root, WorkflowTaskQuery query)
    {
        Criteria process = null;
        
        // process active?
        if (query.isActive() != null)
        {
            process = (process == null) ? root.createCriteria("processInstance") : process;
            if (query.isActive())
            {
                process.add(Restrictions.isNull("end"));
            }
            else
            {
                process.add(Restrictions.isNotNull("end"));
            }
        }
        
        // process id
        List<String> processIds;
        if (query.getProcessId() != null)
        {
            process = (process == null) ? root.createCriteria("processInstance") : process;
            process.add(Restrictions.eq("id", getJbpmId(query.getProcessId())));
        }
        else if ((processIds = query.getProcessIds()) != null)
        {
            process = (process == null) ? root.createCriteria("processInstance") : process;
            Long[] jbpmIds = new Long[processIds.size()];
            int i = 0;
            for (String processId : processIds)
            {
                jbpmIds[i++] = getJbpmId(processId);
            }
            process.add(Restrictions.in("id", jbpmIds));
        }
        
        // process name
        if (query.getProcessName() != null)
        {
            process = (process == null) ? root.createCriteria("processInstance") : process;
            Criteria processDef = process.createCriteria("processDefinition");
            
            String processName = null;
            if (tenantService.isEnabled())
            {
                QName baseProcessName = tenantService.getBaseName(query.getProcessName(), true);
                processName = tenantService.getName(baseProcessName.toPrefixString(namespaceService));
            }
            else
            {
                processName = query.getProcessName().toPrefixString(namespaceService);
            }
            
            processDef.add(Restrictions.eq("name", processName));
        }
        
        return process;
    }
    
    /**
     * Gets a jBPM Task Instance
     * @param taskSession  jBPM task session
     * @param taskId  task id
     * @return  task instance
     */
    protected TaskInstance getTaskInstance(TaskMgmtSession taskSession, String taskId)
    {
        TaskInstance taskInstance = taskSession.getTaskInstance(getJbpmId(taskId));
        
        if ((taskInstance != null) && (tenantService.isEnabled()))
        {
            try
            {
                tenantService.checkDomain(taskInstance.getTask().getProcessDefinition().getName()); // throws exception if domain mismatch
            } 
            catch (RuntimeException re)
            {
                taskInstance = null;
            }
        }
        
        if (taskInstance == null)
        {
            String msg = messageService.getMessage(ERR_GET_TASK_INST, taskId);
            throw new WorkflowException(msg);
        }
        return taskInstance;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.TaskComponent#updateTask(java.lang.String, java.util.Map, java.util.Map, java.util.Map)
     */
    public WorkflowTask updateTask(final String taskId, final Map<QName, Serializable> properties, final Map<QName, List<NodeRef>> add, final Map<QName, List<NodeRef>> remove)
    {
        try
        {
            return (WorkflowTask) jbpmTemplate.execute(new JbpmCallback()
            {
                @SuppressWarnings("unchecked")
				public Object doInJbpm(JbpmContext context)
                {
                    // retrieve task
                    TaskMgmtSession taskSession = context.getTaskMgmtSession();
                    TaskInstance taskInstance = getTaskInstance(taskSession, taskId);

                    // create properties to set on task instance
                    Map<QName, Serializable> newProperties = properties !=null ? properties : new HashMap<QName, Serializable>(10);
                    
                    if (add != null || remove != null)
                    {
                        Map<QName, Serializable> existingProperties = getTaskProperties(taskInstance, false, null);
                        
                        if (add != null)
                        {
                            // add new associations
                            for (Entry<QName, List<NodeRef>> toAdd : add.entrySet())
                            {
                                // retrieve existing list of noderefs for association
                                QName key = toAdd.getKey();
                                Serializable existingValue = newProperties.get(key);
                                  
                                if (existingValue == null)
                                {
                                    existingValue = existingProperties.get(key);
                                }
                                // make the additions
                                if (existingValue == null)
                                {
                                    newProperties.put(key, (Serializable)toAdd.getValue());
                                }
                                else
                                {
                                    List<NodeRef> existingAdd;
                                    if (existingValue instanceof List<?>)
                                    {
                                        existingAdd = (List<NodeRef>) existingValue;
                                    }
                                    else
                                    {
                                        existingAdd = new LinkedList<NodeRef>();
                                        existingAdd.add((NodeRef) existingValue);
                                    }

                                    for (NodeRef nodeRef : toAdd.getValue())
                                    {
                                        if (!(existingAdd.contains(nodeRef)))
                                        {
                                            existingAdd.add(nodeRef);
                                        }
                                    }
                                    newProperties.put(key, (Serializable) existingAdd);
                                }
                            }
                        }

                        if (remove != null)
                        {
                            // add new associations
                            for (Entry<QName, List<NodeRef>> toRemove: remove.entrySet())
                            {
                                // retrieve existing list of noderefs for association
                                QName key = toRemove.getKey();
                                Serializable existingValue = newProperties.get(key);
                                  
                                if (existingValue == null)
                                {
                                    existingValue = existingProperties.get(key);
                                }
                                // make the subtractions
                                if (existingValue != null)
                                {
                                    if(existingValue instanceof List<?>)
                                    {
                                        List<NodeRef> existingRemove = (List<NodeRef>) existingValue;
                                            existingRemove.removeAll(toRemove.getValue());
                                        newProperties.put(key, (Serializable) existingRemove);
                                    }
                                    else if(toRemove.getValue().contains(existingValue))
                                    {
                                        newProperties.put(key, new LinkedList<NodeRef>());
                                    }
                                }
                            }
                        }
                    }
                    
                    // update the task
                    if (newProperties.isEmpty() == false)
                    {
                        setTaskProperties(taskInstance, newProperties);
                        
                        // save
                        ProcessInstance processInstance = taskInstance.getToken().getProcessInstance();
                        context.save(processInstance);
                    }
                    
                    // note: the ending of a task may not have signalled (i.e. more than one task exists at
                    //       this node)
                    return createWorkflowTask(taskInstance);
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_UPDATE_TASK, taskId);
            throw new WorkflowException(msg, e);
        }        
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.TaskComponent#startTask(java.lang.String)
     */
    public WorkflowTask startTask(String taskId)
    {
        // TODO:
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.TaskComponent#suspendTask(java.lang.String)
     */
    public WorkflowTask suspendTask(String taskId)
    {
        // TODO:
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.TaskComponent#endTask(java.lang.String, java.lang.String)
     */
    public WorkflowTask endTask(final String taskId, final String transition)
    {
        try
        {
            return (WorkflowTask) jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    // retrieve task
                    TaskMgmtSession taskSession = context.getTaskMgmtSession();
                    TaskInstance taskInstance = getTaskInstance(taskSession, taskId);

                    // ensure all mandatory properties have been provided
                    QName[] missingProps = getMissingMandatoryTaskProperties(taskInstance);
                    if (missingProps != null && missingProps.length > 0)
                    {
                        String props = "";
                        for (int i = 0; i < missingProps.length; i++)
                        {
                            props += missingProps[i].toString() + ((i < missingProps.length -1) ? "," : "");
                        }
                        String msg = messageService.getMessage(ERR_MANDATORY_TASK_PROPERTIES_MISSING, props);
                        throw new WorkflowException(msg);

                    }
                    
                    // signal the transition on the task
                    if (transition == null)
                    {
                        taskInstance.end();
                    }
                    else
                    {
                        Node node = taskInstance.getToken().getNode();
                        if (node.getLeavingTransition(transition) == null)
                        {
                            String msg = messageService.getMessage(ERR_END_TASK_INVALID_TRANSITION, transition, taskId);
                            throw new WorkflowException(msg);
                        }
                        taskInstance.end(transition);
                    }
                    
                    // save
                    ProcessInstance processInstance = taskInstance.getToken().getProcessInstance();
                    context.save(processInstance);
                    
                    // note: the ending of a task may not have signalled (i.e. more than one task exists at
                    //       this node)
                    return createWorkflowTask(taskInstance);
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_END_TASK, transition, taskId);
            throw new WorkflowException(msg, e);
        }        
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.workflow.TaskComponent#getTaskById(java.lang.String)
     */
    public WorkflowTask getTaskById(final String taskId)
    {
        try
        {
            return (WorkflowTask) jbpmTemplate.execute(new JbpmCallback()
            {
                public Object doInJbpm(JbpmContext context)
                {
                    // retrieve task
                    TaskMgmtSession taskSession = context.getTaskMgmtSession();
                    TaskInstance taskInstance = getTaskInstance(taskSession, taskId);
                    return taskInstance == null ? null : createWorkflowTask(taskInstance);
                }
            });
        }
        catch(JbpmException e)
        {
            String msg = messageService.getMessage(ERR_GET_TASK_BY_ID, taskId);
            throw new WorkflowException(msg, e);
        }        
    }
    
    //
    // Helpers...
    //

    /**
     * Process Definition with accompanying problems
     */
    private static class CompiledProcessDefinition
    {
        public CompiledProcessDefinition(ProcessDefinition def, List<Problem> problems)
        {
            this.def = def;
            this.problems = new String[problems.size()];
            int i = 0;
            for (Problem problem : problems)
            {
                this.problems[i++] = problem.toString();
            }
        }
        
        protected ProcessDefinition def;
        protected String[] problems;
    }
    

    /**
     * Construct a Process Definition from the provided Process Definition stream
     * 
     * @param workflowDefinition  stream to create process definition from  
     * @param mimetype  mimetype of stream
     * @return  process definition
     */
    @SuppressWarnings("unchecked")
    protected CompiledProcessDefinition customCompileProcessDefinition(InputStream definitionStream, String mimetype)
    {
        String actualMimetype = (mimetype == null) ? MimetypeMap.MIMETYPE_ZIP : mimetype;
        CompiledProcessDefinition compiledDef = null;
        
        // parse process definition from jBPM process archive file
        
        if (actualMimetype.equals(MimetypeMap.MIMETYPE_ZIP))
        {
            ZipInputStream zipInputStream = null;
            try
            {
                zipInputStream = new ZipInputStream(definitionStream);
                ProcessArchive reader = new ProcessArchive(zipInputStream);
                ProcessDefinition def = reader.parseProcessDefinition();
                compiledDef = new CompiledProcessDefinition(def, reader.getProblems());
            }
            catch(Exception e)
            {
                String msg = messageService.getMessage(ERR_COMPILE_PROCESS_DEF_zip);
                throw new JbpmException(msg, e);
            }
            finally
            {
                if (zipInputStream != null)
                {
                    try
                    {
                        zipInputStream.close();
                    } catch (IOException e)
                    {
                        // Intentionally empty!
                    }
                }
            }
        }
        
        // parse process definition from jBPM xml file
        
        else if (actualMimetype.equals(MimetypeMap.MIMETYPE_XML))
        {
            try
            {
                if (logger.isDebugEnabled())
                    logger.debug("Start deployment using BX CustomJBPMJpdlXmlReader");
          	CustomJBPMJpdlXmlReader jpdlReader = new CustomJBPMJpdlXmlReader(definitionStream); 
                ProcessDefinition def = jpdlReader.readProcessDefinition();
                compiledDef = new CompiledProcessDefinition(def, jpdlReader.getProblems());
            }
            catch(Exception e)
            {
                String msg = messageService.getMessage(ERR_COMPILE_PROCESS_DEF_XML);
                throw new JbpmException(msg, e);
            }
        }
        else
        {
            String msg = messageService.getMessage(ERR_COMPILE_PROCESS_DEF_UNSUPPORTED, mimetype);
            throw new JbpmException(msg);
        }
               
        if ((compiledDef != null) && tenantService.isEnabled()) 
        {
            compiledDef.def.setName(tenantService.getName(compiledDef.def.getName()));
        }       

        return compiledDef;
    }

    /**
     * Gets the Task definition of the specified Task
     * 
     * @param task  the task
     * @return  the task definition
     */
    private TypeDefinition getTaskDefinition(Task task)
    {
        // TODO: Extend jBPM task instance to include dictionary definition qname? 
        QName typeName = QName.createQName(task.getName(), namespaceService);
        TypeDefinition typeDef = dictionaryService.getType(typeName);
        if (typeDef == null)
        {
            typeDef = dictionaryService.getType(task.getStartState() == null ? WorkflowModel.TYPE_WORKFLOW_TASK : WorkflowModel.TYPE_START_TASK);
            if (typeDef == null)
            {
                String msg = messageService.getMessage(ERR_GET_TASK_DEF, WorkflowModel.TYPE_WORKFLOW_TASK);
                throw new WorkflowException( msg);
            }
        }
        return typeDef;
    }
    
    /**
     * Convert the specified Type definition to an anonymous Type definition.
     * 
     * This collapses all mandatory aspects into a single Type definition.
     * 
     * @param typeDef  the type definition
     * @return  the anonymous type definition
     */
    private TypeDefinition getAnonymousTaskDefinition(TypeDefinition typeDef)
    {
        List<AspectDefinition> aspects = typeDef.getDefaultAspects();
        List<QName> aspectNames = new ArrayList<QName>(aspects.size());
        getMandatoryAspects(typeDef, aspectNames);
        return dictionaryService.getAnonymousType(typeDef.getName(), aspectNames);
    }

    /**
     * Gets a flattened list of all mandatory aspects for a given class
     * 
     * @param classDef  the class
     * @param aspects  a list to hold the mandatory aspects
     */
    private void getMandatoryAspects(ClassDefinition classDef, List<QName> aspects)
    {
        for (AspectDefinition aspect : classDef.getDefaultAspects())
        {
            QName aspectName = aspect.getName();
            if (!aspects.contains(aspectName))
            {
                aspects.add(aspect.getName());
                getMandatoryAspects(aspect, aspects);
            }
        }
    }
    
    /**
     * Get JBoss JBPM Id from Engine Global Id
     * 
     * @param id  global id
     * @return  JBoss JBPM Id
     */
    protected long getJbpmId(String id)
    {
        try
        {
            String theLong = createLocalId(id);
            return new Long(theLong);
        }
        catch(NumberFormatException e)
        {
            String msg = messageService.getMessage(ERR_GET_JBPM_ID, id);
            throw new WorkflowException(msg, e);
        }
    }

    /**
     * Get the JBoss JBPM Token for the Workflow Path
     * 
     * @param session   JBoss JBPM Graph Session
     * @param pathId  workflow path id
     * @return  JBoss JBPM Token
     */
    protected Token getWorkflowToken(GraphSession session, String pathId)
    {
        // extract process id and token path within process
        String[] path = pathId.split(WORKFLOW_PATH_SEPERATOR);
        if (path.length != 2)
        {
            String msg = messageService.getMessage(ERR_GET_WORKFLOW_TOKEN_INVALID, pathId);
            throw new WorkflowException(msg);
        }

        // retrieve jBPM token for workflow position
        ProcessInstance processInstance = getProcessInstance(session, path[0]);
        String tokenId = path[1].replace(WORKFLOW_TOKEN_SEPERATOR, "/");
        Token token = processInstance.findToken(tokenId);
        if (token == null)
        {
            String msg = messageService.getMessage(ERR_GET_WORKFLOW_TOKEN_NULL, pathId);
            throw new WorkflowException(msg);
        }
        
        return token;
    }

    /**
     * Gets Properties of Task
     * 
     * @param instance  task instance
     * @param properties  properties to set
     * @param variablesCache cahce og context instance variables if any exists

     */
    @SuppressWarnings("unchecked")
    protected Map<QName, Serializable> getTaskProperties(TaskInstance instance, boolean localProperties, Map<Long, TokenVariableMap> variablesCache)
    {
    	// retrieve type definition for task
        TypeDefinition taskDef = getAnonymousTaskDefinition(getTaskDefinition(instance.getTask()));
        Map<QName, PropertyDefinition> taskProperties = taskDef.getProperties();
        Map<QName, AssociationDefinition> taskAssocs = taskDef.getAssociations();
    	
        // build properties from jBPM context (visit all tokens to the root)
        Map<String, Object> vars = instance.getVariablesLocally();
        if (!localProperties)
        {
            ContextInstance context = instance.getContextInstance();
            Token token = instance.getToken();
            while (token != null)
            {
                //TokenVariableMap varMap = context.getTokenVariableMap(token);
                TokenVariableMap varMap = null;
                if (variablesCache != null && variablesCache.containsKey(context.getTokenVariableMap(token).getToken().getId()))
                {
                    varMap = variablesCache.get(context.getTokenVariableMap(token).getToken().getId());
                /*3.4.8 if (variablesCache != null && variablesCache.containsKey(context.getId()))
                {
                    varMap = variablesCache.get(context.getId());*/
                }
                else
                {
                    varMap = context.getTokenVariableMap(token);
                }
 
                if (varMap != null)
                {
                    Map<String, Object> tokenVars = varMap.getVariablesLocally();
                    for (Map.Entry<String, Object> entry : tokenVars.entrySet())
                    {
                        if (!vars.containsKey(entry.getKey()))
                        {
                            vars.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                token = token.getParent();
            }
        }
        
        // map arbitrary task variables
        Map<QName, Serializable> properties = new HashMap<QName, Serializable>(10);
        for (Entry<String, Object> entry : vars.entrySet())
        {
            String key = entry.getKey();
            QName qname = mapNameToQName(key);

            // add variable, only if part of task definition or locally defined on task
            boolean isAssoc = taskAssocs.containsKey(qname);
            if (taskProperties.containsKey(qname) || isAssoc || instance.hasVariableLocally(key))
            {
	            Serializable value = convertValue(entry.getValue());
	            properties.put(qname, value);
            }
        }

        // map jBPM task instance fields to properties
        properties.put(WorkflowModel.PROP_TASK_ID, instance.getId());
        properties.put(WorkflowModel.PROP_DESCRIPTION, instance.getDescription());
        properties.put(WorkflowModel.PROP_START_DATE, instance.getStart());
        properties.put(WorkflowModel.PROP_DUE_DATE, instance.getDueDate());
        properties.put(WorkflowModel.PROP_COMPLETION_DATE, instance.getEnd());
        properties.put(WorkflowModel.PROP_PRIORITY, instance.getPriority());
        properties.put(ContentModel.PROP_CREATED, instance.getCreate());
        properties.put(ContentModel.PROP_OWNER, instance.getActorId());
        
        // map jBPM comments
        // NOTE: Only use first comment in list
        List<Comment> comments = instance.getComments();
        if (comments != null && comments.size() > 0)
        {
            properties.put(WorkflowModel.PROP_COMMENT, comments.get(0).getMessage());
        }
        
        // map jBPM task instance collections to associations
        Set<PooledActor> pooledActors = instance.getPooledActors();
        if (pooledActors != null)
        {
            List<NodeRef> pooledNodeRefs = new ArrayList<NodeRef>(pooledActors.size());
            for (PooledActor pooledActor : pooledActors)
            {
                NodeRef pooledNodeRef = null;
                String pooledActorId = pooledActor.getActorId();
                if (AuthorityType.getAuthorityType(pooledActorId) == AuthorityType.GROUP)
                {
                    pooledNodeRef = mapNameToAuthority(pooledActorId);
                }
                else
                {
                    pooledNodeRef = mapNameToPerson(pooledActorId);
                }
                if (pooledNodeRef != null)
                {
                    pooledNodeRefs.add(pooledNodeRef);
                }
            }
            properties.put(WorkflowModel.ASSOC_POOLED_ACTORS, (Serializable)pooledNodeRefs);
        }

        return properties;
    }
    
    /**
     * Sets Properties of Task
     * 
     * @param instance  task instance
     * @param properties  properties to set
     */
    protected void setTaskProperties(TaskInstance instance, Map<QName, Serializable> properties)
    {
        if (properties == null)
        {
            return;
        }

        // establish task definition
        TypeDefinition taskDef = getAnonymousTaskDefinition(getTaskDefinition(instance.getTask()));
        Map<QName, PropertyDefinition> taskProperties = taskDef.getProperties();
        Map<QName, AssociationDefinition> taskAssocs = taskDef.getAssociations();

        // map each parameter to task
        for (Entry<QName, Serializable> entry : properties.entrySet())
        {
            QName key = entry.getKey();
            Serializable value = entry.getValue();
            
            // determine if writing property
            // NOTE: some properties map to fields on jBPM task instance whilst
            //       others are set in the general variable bag on the task
            PropertyDefinition propDef = taskProperties.get(key);
            if (propDef != null)
            {
                if (propDef.isProtected())
                {
                    // NOTE: only write non-protected properties
                    continue;
                }
                
                // convert property value
                if (value instanceof Collection<?>)
                {
                    value = (Serializable)DefaultTypeConverter.INSTANCE.convert(propDef.getDataType(), (Collection<?>)value);
                }
                else
                {
                    value = (Serializable)DefaultTypeConverter.INSTANCE.convert(propDef.getDataType(), value);
                }

                // convert NodeRefs to JBPMNodes
                DataTypeDefinition dataTypeDef = propDef.getDataType();
                if (dataTypeDef.getName().equals(DataTypeDefinition.NODE_REF))
                {
                    value = convertNodeRefs(propDef.isMultiValued(), value);
                }
                
                // map property to specific jBPM task instance field
                if (key.equals(WorkflowModel.PROP_DESCRIPTION))
                {
                    if (value != null && !(value instanceof String))
                    {
                        throw getInvalidPropertyValueException(key, value);
                    }
                    instance.setDescription((String)value);
                    continue;
                }
                if (key.equals(WorkflowModel.PROP_DUE_DATE))
                {
                    if (value != null && !(value instanceof Date))
                    {
                        throw getInvalidPropertyValueException(key, value);
                    }
                    instance.setDueDate((Date)value);
                    continue;
                }
                else if (key.equals(WorkflowModel.PROP_PRIORITY))
                {
                    if (!(value instanceof Integer))
                    {
                        throw getInvalidPropertyValueException(key, value);
                    }
                    instance.setPriority((Integer)value);
                    continue;
                }
                else if (key.equals(WorkflowModel.PROP_COMMENT))
                {
                    if (!(value instanceof String))
                    {
                        throw getInvalidPropertyValueException(key, value);
                    }
                        
                    // NOTE: Only use first comment in list
                    final List<?> comments = instance.getComments();
                    if (comments != null && comments.size() > 0)
                    {
                        // remove existing comments
                        // TODO: jBPM does not provide assistance here
                        jbpmTemplate.execute(new JbpmCallback()
                        {
                            public Object doInJbpm(JbpmContext context)
                            {
                                Session session = context.getSession();
                                for (Object obj: comments)
                                {
                                	Comment comment = (Comment) obj;
                                    comment.getToken().getComments().remove(comment);
                                    session.delete(comment);
                                }
                                comments.clear();
                                return null;
                            }
                        });
                    }
                    instance.addComment((String)value);
                    continue;
                }
                else if (key.equals(ContentModel.PROP_OWNER))
                {
                    if (value != null && !(value instanceof String))
                    {
                        throw getInvalidPropertyValueException(key, value);
                    }
                    String actorId = (String)value;
                    String existingActorId = instance.getActorId();
                    if (existingActorId == null || !existingActorId.equals(actorId))
                    {
                        instance.setActorId((String)value, false);
                    }
                    continue;
                }
            }
            else
            {
                // determine if writing association
                AssociationDefinition assocDef = taskAssocs.get(key);
                if (assocDef != null)
                {
                    // convert association to JBPMNodes
                    value = convertNodeRefs(assocDef.isTargetMany(), value);
                    
                    // map association to specific jBPM task instance field
                    if (key.equals(WorkflowModel.ASSOC_POOLED_ACTORS))
                    {
                        String[] pooledActors = null;
                        if (value instanceof JBPMNodeList)
                        {
                            JBPMNodeList actors = (JBPMNodeList)value;
                            pooledActors = new String[actors.size()];
                            int i = 0;
                            for (JBPMNode actor : actors)
                            {
                                pooledActors[i++] = mapAuthorityToName(actor.getNodeRef());
                            }
                        }
                        else if (value instanceof JBPMNode)
                        {
                            JBPMNode node = (JBPMNode)value;
                            pooledActors = new String[] {mapAuthorityToName(node.getNodeRef())};
                        }
                        else
                        {
                            throw getInvalidPropertyValueException(key, value);
                        }
                        instance.setPooledActors(pooledActors);
                        continue;
                    }
                    else if (key.equals(WorkflowModel.ASSOC_PACKAGE))
                    {
                        // Attach workflow definition & instance id to Workflow Package in Repository
                        String name = mapQNameToName(key);
                        JBPMNode existingWorkflowPackage = (JBPMNode)instance.getVariable(name);
                        
                        // first check if provided workflow package has already been associated with another workflow instance
                        if (existingWorkflowPackage != null && value != null)
                        {
                            NodeRef newPackageNodeRef = ((JBPMNode)value).getNodeRef();
                            ProcessInstance processInstance = instance.getToken().getProcessInstance();
                            String packageInstanceId = (String)nodeService.getProperty(newPackageNodeRef, WorkflowModel.PROP_WORKFLOW_INSTANCE_ID);
                            if (packageInstanceId != null && packageInstanceId.length() > 0 && (processInstance.getId() == getJbpmId(packageInstanceId)))
                            {
                                String workflowInstanceId = createGlobalId(new Long(processInstance.getId()).toString());
                                // skip this entry as the package is already associated to this workflow (see ALF-7622).
                                if (workflowInstanceId.equals(packageInstanceId))
                                {
                                    continue;
                                }
                                else
                                {

									String msg = messageService.getMessage(ERR_PACKAGE_ALREADY_ASSOCIATED, newPackageNodeRef, workflowInstanceId, packageInstanceId);
									throw new WorkflowException(msg); 
								}
                            }
                        }

                        // initialise workflow package
                        if (existingWorkflowPackage == null && value != null)
                        {
                            // initialise workflow package
                            NodeRef newPackageNodeRef = ((JBPMNode)value).getNodeRef();
                            ProcessInstance processInstance = instance.getToken().getProcessInstance();
                            WorkflowInstance workflowInstance = createWorkflowInstance(processInstance);
                            nodeService.setProperty(newPackageNodeRef, WorkflowModel.PROP_WORKFLOW_DEFINITION_ID, workflowInstance.definition.id);
                            nodeService.setProperty(newPackageNodeRef, WorkflowModel.PROP_WORKFLOW_DEFINITION_NAME, workflowInstance.definition.name);
                            nodeService.setProperty(newPackageNodeRef, WorkflowModel.PROP_WORKFLOW_INSTANCE_ID, workflowInstance.id);
                        }
                        
                        // NOTE: Fall-through to allow setting of Workflow Package on Task Instance
                    }
                }
                
                // untyped value, perform minimal conversion
                else
                {
                    if (value instanceof NodeRef)
                    {
                        value = new JBPMNode((NodeRef)value, serviceRegistry);
                    }
                }
            }
            
            // no specific mapping to jBPM task has been established, so place into
            // the generic task variable bag
            String name = mapQNameToName(key);
            instance.setVariableLocally(name, value);
        }
    }

    private WorkflowException getInvalidPropertyValueException(QName key, Serializable value)
    {
        String msg = messageService.getMessage(ERR_SET_TASK_PROPS_INVALID_VALUE, value, key);
        return new WorkflowException(msg);
    }

    /**
     * Sets Default Properties of Task
     * 
     * @param instance  task instance
     */
    protected void setDefaultTaskProperties(TaskInstance instance)
    {
        Map<QName, Serializable> existingValues = getTaskProperties(instance, false, null);
        //3.4.8 Map<QName, Serializable> existingValues = getTaskProperties(instance, true, null);
        Map<QName, Serializable> defaultValues = new HashMap<QName, Serializable>();

        // construct an anonymous type that flattens all mandatory aspects
        ClassDefinition classDef = getAnonymousTaskDefinition(getTaskDefinition(instance.getTask()));
        Map<QName, PropertyDefinition> propertyDefs = classDef.getProperties(); 

        // for each property, determine if it has a default value
        for (Map.Entry<QName, PropertyDefinition> entry : propertyDefs.entrySet())
        {
            String defaultValue = entry.getValue().getDefaultValue();
            if (defaultValue != null)
            {
                if (existingValues.get(entry.getKey()) == null || ignoredProperties.containsValue(entry.getKey()))
                //3.4.8 if (existingValues.get(entry.getKey()) == null)
                {
                    defaultValues.put(entry.getKey(), defaultValue);
                }
            }
        }

        // special case for task description default value
        String description = (String)existingValues.get(WorkflowModel.PROP_DESCRIPTION);
        if (description == null || description.length() == 0)
        {
            description = (String)instance.getContextInstance().getVariable(mapQNameToName(WorkflowModel.PROP_WORKFLOW_DESCRIPTION));
            if (description != null && description.length() > 0)
            {
                defaultValues.put(WorkflowModel.PROP_DESCRIPTION, description);
            }
            else
            {
                WorkflowTask task = createWorkflowTask(instance);
                defaultValues.put(WorkflowModel.PROP_DESCRIPTION, task.title);
            }
        }

        // assign the default values to the task
        if (defaultValues.size() > 0)
        {
            setTaskProperties(instance, defaultValues);
        }
    }

    /**
     * Sets default description for the Task
     * 
     * @param instance  task instance
     */
    public void setDefaultStartTaskDescription(TaskInstance instance)
    {
        String description = instance.getTask().getDescription();
        if (description == null || description.length() == 0)
        {
            description = (String)instance.getContextInstance().getVariable(mapQNameToName(WorkflowModel.PROP_WORKFLOW_DESCRIPTION));
            if (description != null && description.length() > 0)
            {
                Map<QName, Serializable> defaultValues = new HashMap<QName, Serializable>();
                defaultValues.put(WorkflowModel.PROP_DESCRIPTION, description);
                setTaskProperties(instance, defaultValues);
            }
        }
    }

    /**
     * Initialise Workflow Instance properties
     * 
     * @param startTask  start task instance
     */
    protected void setDefaultWorkflowProperties(TaskInstance startTask)
    {
        Map<QName, Serializable> taskProperties = getTaskProperties(startTask, true, null);
        
        ContextInstance processContext = startTask.getContextInstance();
        String workflowDescriptionName = mapQNameToName(WorkflowModel.PROP_WORKFLOW_DESCRIPTION);
        if (!processContext.hasVariable(workflowDescriptionName))
        {
            processContext.setVariable(workflowDescriptionName, taskProperties.get(WorkflowModel.PROP_WORKFLOW_DESCRIPTION));
        }
        String workflowDueDateName = mapQNameToName(WorkflowModel.PROP_WORKFLOW_DUE_DATE);
        if (!processContext.hasVariable(workflowDueDateName))
        {
            processContext.setVariable(workflowDueDateName, taskProperties.get(WorkflowModel.PROP_WORKFLOW_DUE_DATE));
        }
        String workflowPriorityName = mapQNameToName(WorkflowModel.PROP_WORKFLOW_PRIORITY);
        if (!processContext.hasVariable(workflowPriorityName))
        {
            processContext.setVariable(workflowPriorityName, taskProperties.get(WorkflowModel.PROP_WORKFLOW_PRIORITY));
        }
        String workflowPackageName = mapQNameToName(WorkflowModel.ASSOC_PACKAGE);
        if (!processContext.hasVariable(workflowPackageName))
        {
            Serializable packageNodeRef = taskProperties.get(WorkflowModel.ASSOC_PACKAGE);
            processContext.setVariable(workflowPackageName, convertNodeRefs(packageNodeRef instanceof List<?>, packageNodeRef));
        }
        String workflowContextName = mapQNameToName(WorkflowModel.PROP_CONTEXT);
        if (!processContext.hasVariable(workflowContextName))
        {
            Serializable contextRef = taskProperties.get(WorkflowModel.PROP_CONTEXT);
            processContext.setVariable(workflowContextName, convertNodeRefs(contextRef instanceof List<?>, contextRef));
        }
    }
    
    /**
     * Get missing mandatory properties on Task
     * 
     * @param instance  task instance
     * @return array of missing property names (or null, if none)
     */
    protected QName[] getMissingMandatoryTaskProperties(TaskInstance instance)
    {
        List<QName> missingProps = null;

        // retrieve properties of task
        Map<QName, Serializable> existingValues = getTaskProperties(instance, false, null);
        
        // retrieve definition of task
        ClassDefinition classDef = getAnonymousTaskDefinition(getTaskDefinition(instance.getTask()));
        Map<QName, PropertyDefinition> propertyDefs = classDef.getProperties(); 
        Map<QName, AssociationDefinition> assocDefs = classDef.getAssociations();

        // for each property, determine if it is mandatory
        for (Map.Entry<QName, PropertyDefinition> entry : propertyDefs.entrySet())
        {
            QName name = entry.getKey();
            if (!(name.getNamespaceURI().equals(NamespaceService.CONTENT_MODEL_1_0_URI) || (name.getNamespaceURI().equals(NamespaceService.SYSTEM_MODEL_1_0_URI))))
            {
                boolean isMandatory = entry.getValue().isMandatory();
                if (isMandatory)
                {
                    Object value = existingValues.get(entry.getKey());
                    if (value == null || (value instanceof String && ((String)value).length() == 0))
                    {
                        if (missingProps == null)
                        {
                            missingProps = new ArrayList<QName>();
                        }
                        missingProps.add(entry.getKey());
                    }
                }
            }
        }
        for (Map.Entry<QName, AssociationDefinition> entry : assocDefs.entrySet())
        {
            QName name = entry.getKey();
            if (!(name.getNamespaceURI().equals(NamespaceService.CONTENT_MODEL_1_0_URI) || (name.getNamespaceURI().equals(NamespaceService.SYSTEM_MODEL_1_0_URI))))
            {
                boolean isMandatory = entry.getValue().isTargetMandatory();
                if (isMandatory)
                {
                    Object value = existingValues.get(entry.getKey());
                    if (value == null || (value instanceof List<?> && ((List<?>)value).isEmpty()))
                    {
                        if (missingProps == null)
                        {
                            missingProps = new ArrayList<QName>();
                        }
                        missingProps.add(entry.getKey());
                    }
                }
            }
        }

        return (missingProps == null) ? null : missingProps.toArray(new QName[missingProps.size()]);
    }
        
    /**
     * Convert a jBPM Value to an Alfresco value
     * 
     * @param value jBPM value
     * @return  alfresco value
     */
    private Serializable convertValue(Object value)
    {
        Serializable alfValue = null;
        
        if (value == null)
        {
            // NOOP
        }
        else if (value instanceof JBPMNode)
        {
            alfValue = ((JBPMNode)value).getNodeRef();
        }
        else if (value instanceof JBPMNodeList)
        {
            JBPMNodeList nodes = (JBPMNodeList)value;
            List<NodeRef> nodeRefs = new ArrayList<NodeRef>(nodes.size());
            for (JBPMNode node : nodes)
            {
                nodeRefs.add(node.getNodeRef());
            }
            alfValue = (Serializable)nodeRefs;
        }
        else if (value instanceof Serializable)
        {
            alfValue = (Serializable)value;
        }
        else
        {
            String msg = messageService.getMessage(ERR_CONVERT_VALUE, value);
            throw new WorkflowException(msg);
        }
        return alfValue;
    }
    
    /**
     * Convert a Repository association to JBPMNodeList or JBPMNode
     * 
     * @param isMany true => force conversion to list
     * @param value  value to convert
     * @return JBPMNodeList or JBPMNode
     */
    @SuppressWarnings("unchecked")
	private Serializable convertNodeRefs(boolean isMany, Serializable value)
    {
        if (value instanceof NodeRef)
        {
            if (isMany)
            {
                // convert single node ref to list of node refs
                JBPMNodeList values = new JBPMNodeList(); 
                values.add(new JBPMNode((NodeRef)value, serviceRegistry));
                value = (Serializable)values;
            }
            else
            {
                value = new JBPMNode((NodeRef)value, serviceRegistry);
            }
        }
        else if (value instanceof List)
        {
            if (isMany)
            {
                JBPMNodeList values = new JBPMNodeList();
                for (NodeRef nodeRef : (List<NodeRef>)value)
                {
                    values.add(new JBPMNode(nodeRef, serviceRegistry));
                }
                value = (Serializable)values;
            }
            else
            {
                List<NodeRef> nodeRefs = (List<NodeRef>)value;
                value = (nodeRefs.size() == 0 ? null : new JBPMNode(nodeRefs.get(0), serviceRegistry));
            }
        }
        
        return value;
    }
    
    /**
     * Convert person name to an Alfresco Person
     * 
     * @param names  the person name to convert
     * @return  the Alfresco person
     */
    private NodeRef mapNameToPerson(String name)
    {
        NodeRef authority = null;
        if (name != null)
        {
            // TODO: Should this be an exception?
            if (personService.personExists(name))
            {
                authority = personService.getPerson(name);
            }
        }
        return authority;
    }

    /**
     * Convert authority name to an Alfresco Authority
     * 
     * @param names  the authority names to convert
     * @return  the Alfresco authorities
     */
    private NodeRef mapNameToAuthority(String name)
    {
        NodeRef authority = null;
        if (name != null)
        {
            // TODO: Should this be an exception?
            if (authorityDAO.authorityExists(name))
            {
                authority = authorityDAO.getAuthorityNodeRefOrNull(name);
            }
        }
        return authority;
    }

    /**
     * Convert Alfresco authority to actor id
     *  
     * @param authority
     * @return  actor id
     */
    private String mapAuthorityToName(NodeRef authority)
    {
        String name = null;
        QName type = nodeService.getType(authority);
        
        if (dictionaryService.isSubClass(type, ContentModel.TYPE_PERSON))
        {
            name = (String)nodeService.getProperty(authority, ContentModel.PROP_USERNAME);
        }
        else
        {
            name = authorityDAO.getAuthorityName(authority);
        }
        return name;
    }
    
    /**
     * Map jBPM variable name to QName
     * 
     * @param name  jBPM variable name
     * @return  qname
     */
    private QName mapNameToQName(String name)
    {
        //QName qname = null;
		QName qname = qNameCache.getNameToQName(name);
        if (qname == null)
        {

        // NOTE: Map names using old conversion scheme (i.e. : -> _) as well as new scheme (i.e. } -> _)
        String qnameStr = (name.indexOf('}') == -1) ? name.replaceFirst("_", ":") : name.replace("}", ":");
        try
        {
            qname = QName.createQName(qnameStr, this.namespaceService);
        }
        catch(NamespaceException e)
        {
            qname = QName.createQName(name, this.namespaceService);
        }
            qNameCache.putNameToQName(name, qname);
        }
        return qname;
    }

    /**
     * Map QName to jBPM variable name
     * 
     * @param name  QName
     * @return  jBPM variable name
     */
    private String mapQNameToName(QName name)
    {
        // NOTE: Map names using old conversion scheme (i.e. : -> _) as well as new scheme (i.e. } -> _)
        // NOTE: Use new scheme 
        String cachedName = qNameCache.getQNameToName(name);
        if (cachedName == null)
        {

        String nameStr = name.toPrefixString(this.namespaceService);
        if (nameStr.indexOf('_') != -1 && nameStr.indexOf('_') < nameStr.indexOf(':'))
        {
                cachedName = nameStr.replace(':', '}');
            }
            cachedName = nameStr.replace(':', '_');
        }
        qNameCache.putQNameToName(name, cachedName);
        return cachedName;
            //return nameStr.replace(':', '}');
        //}
        //return nameStr.replace(':', '_');
    }
    
    /**
     * Get an I18N Label for a workflow item
     * 
     * @param displayId  message resource id lookup
     * @param labelKey  label to lookup (title or description)
     * @param defaultLabel  default value if not found in message resource bundle
     * @return  the label
     */
    private String getLabel(String displayId, String labelKey, String defaultLabel)
    {
        String key = StringUtils.replace(displayId, ":", "_");
        key += "." + labelKey;
        String label = messageService.getMessage(key);
        
        return (label == null) ? defaultLabel : label;
    }
    
    /**
     * Gets the Company Home
     *  
     * @return  company home node ref
     */
    private NodeRef getCompanyHome()
    {
        if (tenantService.isEnabled())
        {
           try
           {
               return tenantService.getRootNode(nodeService, serviceRegistry.getSearchService(), namespaceService, companyHomePath, nodeService.getRootNode(companyHomeStore));           
           }
           catch (RuntimeException re)
           {
               String msg = messageService.getMessage(ERR_GET_COMPANY_HOME_INVALID, companyHomePath);
               throw new IllegalStateException(msg, re);
           }
        }
        else
        {
            List<NodeRef> refs = unprotectedSearchService.selectNodes(nodeService.getRootNode(companyHomeStore), companyHomePath, null, namespaceService, false);
            if (refs.size() != 1)
            {
                String msg = messageService.getMessage(ERR_GET_COMPANY_HOME_MULTIPLE, companyHomePath, refs.size());
                throw new IllegalStateException(msg);
            }
            return refs.get(0);
        }
    }
    
    //
    // Workflow Data Object Creation...
    //
    
    /**
     * Creates a Workflow Path
     * 
     * @param token  JBoss JBPM Token
     * @return  Workflow Path
     */
    protected WorkflowPath createWorkflowPath(Token token)
    {
        WorkflowPath path = new WorkflowPath();
        String tokenId = token.getFullName().replace("/", WORKFLOW_TOKEN_SEPERATOR);
        path.id = createGlobalId(token.getProcessInstance().getId() + WORKFLOW_PATH_SEPERATOR + tokenId);
        path.instance = createWorkflowInstance(token.getProcessInstance());
        path.node = createWorkflowNode(token.getNode());
        path.active = !token.hasEnded();
        return path;
    }
    
    /**
     * Creates a Workflow Node
     * 
     * @param node  JBoss JBPM Node
     * @return   Workflow Node
     */
    @SuppressWarnings("unchecked")
    protected WorkflowNode createWorkflowNode(Node node)
    {
        String processName = node.getProcessDefinition().getName();

        if (tenantService.isEnabled())
        {
            tenantService.checkDomain(processName); // throws exception if domain mismatch
        }
        
        WorkflowNode workflowNode = new WorkflowNode();
        workflowNode.name = node.getName();
        workflowNode.title = getLabel(processName + ".node." + workflowNode.name, TITLE_LABEL, workflowNode.name);
        workflowNode.description = getLabel(processName + ".node." + workflowNode.name, DESC_LABEL, workflowNode.title);
        workflowNode.type = getRealNode(node).getClass().getSimpleName();
        // TODO: Is there a formal way of determing if task node?
        workflowNode.isTaskNode = workflowNode.type.equals("TaskNode");
        @SuppressWarnings("rawtypes")

        List transitions = node.getLeavingTransitions();
        workflowNode.transitions = new WorkflowTransition[(transitions == null) ? 0 : transitions.size()];
        if (transitions != null)
        {
            int i = 0;
            for (Transition transition : (List<Transition>)transitions)
            {
                workflowNode.transitions[i++] = createWorkflowTransition(transition);
            }
        }
        return workflowNode;
    }
    
    /**
     * Create a Workflow Transition
     * 
     * @param transition  JBoss JBPM Transition
     * @return  Workflow Transition
     */
    protected WorkflowTransition createWorkflowTransition(Transition transition)
    {
        if (tenantService.isEnabled())
        {
            tenantService.checkDomain(transition.getProcessDefinition().getName()); // throws exception if domain mismatch
        }
        
        WorkflowTransition workflowTransition = new WorkflowTransition();
        workflowTransition.id = transition.getName();
        Node node = transition.getFrom();
        workflowTransition.isDefault = node.getDefaultLeavingTransition().equals(transition);
        if (workflowTransition.id == null || workflowTransition.id.length() == 0)
        {
            workflowTransition.title = getLabel(DEFAULT_TRANSITION_LABEL, TITLE_LABEL, workflowTransition.id);
            workflowTransition.description = getLabel(DEFAULT_TRANSITION_LABEL, DESC_LABEL, workflowTransition.title);
        }
        else
        {
            String nodeName = node.getName();
            String processName = node.getProcessDefinition().getName();
            workflowTransition.title = getLabel(processName + ".node." + nodeName + ".transition." + workflowTransition.id, TITLE_LABEL, workflowTransition.id);
            workflowTransition.description = getLabel(processName + ".node." + nodeName + ".transition." + workflowTransition.id, DESC_LABEL, workflowTransition.title);
        }
        return workflowTransition;
    }
        
    /**
     * Creates a Workflow Instance
     * 
     * @param instance  JBoss JBPM Process Instance
     * @return  Workflow instance
     */
    protected WorkflowInstance createWorkflowInstance(ProcessInstance instance)
    {
        if (tenantService.isEnabled())
        {
            tenantService.checkDomain(instance.getProcessDefinition().getName()); // throws exception if domain mismatch
        }
        
        WorkflowInstance workflowInstance = new WorkflowInstance();
        workflowInstance.id = createGlobalId(new Long(instance.getId()).toString());
        workflowInstance.description = (String)instance.getContextInstance().getVariable(mapQNameToName(WorkflowModel.PROP_WORKFLOW_DESCRIPTION));
        workflowInstance.priority = (Integer)instance.getContextInstance().getVariable(mapQNameToName(WorkflowModel.PROP_WORKFLOW_PRIORITY));
        workflowInstance.dueDate = (Date)instance.getContextInstance().getVariable(mapQNameToName(WorkflowModel.PROP_WORKFLOW_DUE_DATE));
        workflowInstance.definition = createWorkflowDefinition(instance.getProcessDefinition());
        workflowInstance.active = !instance.hasEnded();
        JBPMNode initiator = (JBPMNode)instance.getContextInstance().getVariable("initiator");
        if (initiator != null)
        {
            workflowInstance.initiator = initiator.getNodeRef(); 
        }
        JBPMNode context = (JBPMNode)instance.getContextInstance().getVariable(mapQNameToName(WorkflowModel.PROP_CONTEXT));
        if (context != null)
        {
            workflowInstance.context = context.getNodeRef(); 
        }
        JBPMNode workflowPackage = (JBPMNode)instance.getContextInstance().getVariable(mapQNameToName(WorkflowModel.ASSOC_PACKAGE));
        if (workflowPackage != null)
        {
            workflowInstance.workflowPackage = workflowPackage.getNodeRef(); 
        }
        workflowInstance.startDate = instance.getStart();
        workflowInstance.endDate = instance.getEnd();
        return workflowInstance;
    }

    /**
     * Creates a Workflow Definition
     * 
     * @param definition  JBoss Process Definition
     * @return  Workflow Definition
     */
    protected WorkflowDefinition createWorkflowDefinition(ProcessDefinition definition)
    {
        if (tenantService.isEnabled())
        {
            tenantService.checkDomain(definition.getName()); // throws exception if domain mismatch
        }
        
        final Task startTask = definition.getTaskMgmtDefinition().getStartTask();
        String name = tenantService.getBaseName(definition.getName());
        final String title = getLabel(name + ".workflow", TITLE_LABEL, name);
        final String description = getLabel(name + ".workflow", DESC_LABEL, title);
        return new WorkflowDefinition(createGlobalId(new Long(definition.getId()).toString()),
                                      createGlobalId(name),
                                      new Integer(definition.getVersion()).toString(),
                                      title,
                                      description,
                                      (startTask != null
                                       ? createWorkflowTaskDefinition(startTask)
                                       : null));
    }
    
    /**
     * Creates a Workflow Task
     * 
     * @param task  JBoss Task Instance
     * @return  Workflow Task
     */
    protected WorkflowTask createWorkflowTask(TaskInstance task)
    {
        String processName = task.getTask().getProcessDefinition().getName();
        
        if (tenantService.isEnabled())
        {
            tenantService.checkDomain(processName); // throws exception if domain mismatch
        }
        
        WorkflowTask workflowTask = new WorkflowTask();
        workflowTask.id = createGlobalId(new Long(task.getId()).toString());
        workflowTask.name = task.getName();
        workflowTask.path = createWorkflowPath(task.getToken());
        workflowTask.state = getWorkflowTaskState(task);
        workflowTask.definition = createWorkflowTaskDefinition(task.getTask());
        workflowTask.properties = getTaskProperties(task, false, null);
        workflowTask.title = getLabel(processName + ".task." + workflowTask.name, TITLE_LABEL, null);
        if (workflowTask.title == null)
        {
            workflowTask.title = workflowTask.definition.metadata.getTitle();
            if (workflowTask.title == null)
            {
                workflowTask.title = workflowTask.name;
            }
        }
        workflowTask.description = getLabel(processName + ".task." + workflowTask.name, DESC_LABEL, null);
        if (workflowTask.description == null)
        {
            String description = workflowTask.definition.metadata.getDescription();
            workflowTask.description = (description == null) ? workflowTask.title : description;
        }
        return workflowTask;
    }
 
    /**
     * Creates a Workflow Task Definition
     * 
     * @param task  JBoss JBPM Task
     * @return  Workflow Task Definition
     */
    protected WorkflowTaskDefinition createWorkflowTaskDefinition(Task task)
    {
        WorkflowTaskDefinition taskDef = new WorkflowTaskDefinition();
        taskDef.id = task.getName();
        Node node = (task.getStartState() == null ? task.getTaskNode() : task.getStartState());
        taskDef.node = createWorkflowNode(node);
        taskDef.metadata = getTaskDefinition(task);
        return taskDef;
    }
    
    /**
     * Creates a Workflow Deployment
     * 
     * @param compiledDef  compiled JBPM process definition
     * @return  workflow deployment
     */
    protected WorkflowDeployment createWorkflowDeployment(CompiledProcessDefinition compiledDef)
    {
        WorkflowDeployment deployment = new WorkflowDeployment();
        deployment.definition = createWorkflowDefinition(compiledDef.def);
        deployment.problems = compiledDef.problems;
        return deployment;
    }
    
    /**
     * Creates a Workflow Timer
     * @param timer  jBPM Timer
     * @return  workflow timer
     */
    protected WorkflowTimer createWorkflowTimer(Timer timer)
    {
        WorkflowTimer workflowTimer = new WorkflowTimer();
        workflowTimer.id = createGlobalId(new Long(timer.getId()).toString());
        workflowTimer.name = timer.getName();
        workflowTimer.error = timer.getException();
        workflowTimer.dueDate = timer.getDueDate();
        workflowTimer.path = createWorkflowPath(timer.getToken());
        TaskInstance taskInstance = timer.getTaskInstance();
        if (taskInstance != null)
        {
            workflowTimer.task = createWorkflowTask(taskInstance);
        }
        return workflowTimer;
    }
    
    /**
     * Get the Workflow Task State for the specified JBoss JBPM Task
     * 
     * @param task  task
     * @return  task state
     */
    protected WorkflowTaskState getWorkflowTaskState(TaskInstance task)
    {
        if (task.hasEnded())
        {
            return WorkflowTaskState.COMPLETED;
        }
        else
        {
            return WorkflowTaskState.IN_PROGRESS;
        }
    }

    /**
     * Helper to retrieve the real jBPM Node
     * 
     * @param node  Node
     * @return  real Node (i.e. the one that's not a Hibernate proxy)
     */
    private Node getRealNode(Node node)
    {
        if (node instanceof HibernateProxy)
        {
            Node realNode = (Node)((HibernateProxy)node).getHibernateLazyInitializer().getImplementation();        
            return realNode;
        }
        else
        {
            return node;
        }
    }

    /**
     * Basic cache implementation for performance improvement with mapping QNames and Names of properties.
     */
    private class QNameCache
    {
        private Map<QName, String> mapQNameToNameCache;
        private Map<String, QName> mapNameToQNameCache;
        
        private ReentrantReadWriteLock qNameToNameLock = new ReentrantReadWriteLock();
        private WriteLock qNameToNameWriteLock = qNameToNameLock.writeLock();
        private ReadLock qNameToNameReadLock = qNameToNameLock.readLock();

        private ReentrantReadWriteLock nameToQNameLock = new ReentrantReadWriteLock();
        private WriteLock nameToQNameWriteLock = nameToQNameLock.writeLock();
        private ReadLock nameToQNameReadLock = nameToQNameLock.readLock();
        
        QNameCache()
        {
            mapQNameToNameCache = new HashMap<QName, String>();
            mapNameToQNameCache = new HashMap<String, QName>();
        }
        
        String getQNameToName(QName qName)
        {
            qNameToNameReadLock.lock();
            try
            {
                return mapQNameToNameCache.get(qName);
            }
            finally
            {
                qNameToNameReadLock.unlock();
            }
        }
        
        void putQNameToName(QName qName, String name)
        {
            qNameToNameWriteLock.lock();
            try
            {
                mapQNameToNameCache.put(qName, name);
            }
            finally
            {
                qNameToNameWriteLock.unlock();
            }
        }
        
        QName getNameToQName(String name)
        {
            nameToQNameReadLock.lock();
            try
            {
                return mapNameToQNameCache.get(name);
            }
            finally
            {
                nameToQNameReadLock.unlock();
            }
        }
        
        void putNameToQName(String name, QName qName)
        {
            nameToQNameWriteLock.lock();
            try
            {
                mapNameToQNameCache.put(name, qName);
            }
            finally
            {
                nameToQNameWriteLock.unlock();
            }
        }
        
        void clear()
        {
            nameToQNameWriteLock.lock();
            try
            {
                mapNameToQNameCache.clear();
            }
            finally
            {
                nameToQNameWriteLock.unlock();
            }
            qNameToNameWriteLock.lock();
            try
            {
                mapQNameToNameCache.clear();
            }
            finally
            {
                qNameToNameWriteLock.unlock();
            }
            
        }

    }
}
