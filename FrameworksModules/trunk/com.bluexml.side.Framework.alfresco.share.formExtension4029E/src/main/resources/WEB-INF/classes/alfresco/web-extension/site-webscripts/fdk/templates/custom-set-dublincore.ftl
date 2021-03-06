<#if form.fields["prop_cm_publisher"]??>
   <#if form.mode == "view">
      <@formLib.renderField field=form.fields["prop_cm_publisher"] />
      <@formLib.renderField field=form.fields["prop_cm_contributor"] />
      <@formLib.renderField field=form.fields["prop_cm_type"] />
      <@formLib.renderField field=form.fields["prop_cm_identifier"] />
      <@formLib.renderField field=form.fields["prop_cm_dcsource"] />
      <@formLib.renderField field=form.fields["prop_cm_coverage"] />
      <@formLib.renderField field=form.fields["prop_cm_rights"] />
      <@formLib.renderField field=form.fields["prop_cm_subject"] />
   <#else>
      <div class="yui-g">
         <div class="yui-u first">
            <@formLib.renderField field=form.fields["prop_cm_publisher"] />
         </div>
         <div class="yui-u">
            <@formLib.renderField field=form.fields["prop_cm_contributor"] />
         </div>
      </div>
      <div class="yui-g">
         <div class="yui-u first">
            <@formLib.renderField field=form.fields["prop_cm_type"] />
         </div>
         <div class="yui-u">
            <@formLib.renderField field=form.fields["prop_cm_identifier"] />
         </div>
      </div>
      <div class="yui-g">
         <div class="yui-u first">
            <@formLib.renderField field=form.fields["prop_cm_dcsource"] />
         </div>
         <div class="yui-u">
            <@formLib.renderField field=form.fields["prop_cm_coverage"] />
         </div>
      </div>
      <div class="yui-g">
         <div class="yui-u first">
            <@formLib.renderField field=form.fields["prop_cm_rights"] />
         </div>
         <div class="yui-u">
            <@formLib.renderField field=form.fields["prop_cm_subject"] />
         </div>
      </div>
        
      <div style="clear: both;"></div>
   </#if>
</#if>
