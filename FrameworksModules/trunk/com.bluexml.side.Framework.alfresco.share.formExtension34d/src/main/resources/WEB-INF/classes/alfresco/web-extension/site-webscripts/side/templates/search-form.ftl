<#import "/side/templates/search.lib.ftl" as formLib />

<#if formUI == "true">
   <@formLib.renderFormsRuntime formId=formId />
</#if>

<@formLib.renderFormContainer formId=formId>  
   <div id="${formId}-tabview" class="yui-navset">
      <div class="form-field">
         <label for="operator">${msg("form.control.search.operator.default")}</label>
         <@formLib.renderOperator id=formId onchange="" name="default" operators=[['OR','form.control.search.operator.OR',true],['AND','form.control.search.operator.AND',false]]/>      
      </div>
      <ul class="yui-nav">
         <#list form.structure as item>
            <#if item.kind == "set">
               <li<#if item_index == 0> class="selected"</#if>><a href="#tab_${item_index}"><em>${item.label}</em></a></li>
            </#if>
         </#list>
      </ul>                
      <div class="yui-content">
         <#list form.structure as item>
            <#if item.kind == "set">
               <div id="tab_${item_index}">
                  <@formLib.renderSet set=item />
               </div>      
            </#if>
         </#list>
      </div>
   </div>
</@>

<script type="text/javascript">//<![CDATA[
(function() 
{
   new YAHOO.widget.TabView('${formId}-tabview');
})();
//]]></script>
