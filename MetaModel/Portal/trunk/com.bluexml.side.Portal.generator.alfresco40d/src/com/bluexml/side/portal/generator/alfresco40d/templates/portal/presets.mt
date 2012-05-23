<%
metamodel http://www.kerblue.org/portal/1.0
import com.bluexml.side.portal.generator.alfresco.service.ShareGeneratorServices
%>

<%-- Templates creation --%>
<%script type="Portal" name="createMessages"%>
<%if (eContainer() == null) {%>
<%getProperty("alf.share.paths.core.site-data")%>presets/side-<%name%>-presets.xml
<%}%>
<%script type="Portal" name="alfrescoGenerator" file="<%createMessages%>"%>
<?xml version='1.0' encoding='UTF-8'?>
<presets>
   <!-- Well known preset used to generate the default Collaboration Site dashboard -->
   <preset id="site-<%name%>">
      <components>         
         <!-- title -->
         <component>
            <scope>page</scope>
            <region-id>title</region-id>
            <source-id>site/${siteid}/dashboard</source-id>
            <url>/components/title/collaboration-title</url>
         </component>
         <!-- navigation -->
         <component>
            <scope>page</scope>
            <region-id>navigation</region-id>
            <source-id>site/${siteid}/dashboard</source-id>
            <url>/components/navigation/collaboration-navigation</url>
         </component>
         <!-- dashboard components -->
         <component>
            <scope>page</scope>
            <region-id>full-width-dashlet</region-id>
            <source-id>site/${siteid}/dashboard</source-id>
            <url>/components/dashlets/dynamic-welcome</url>
            <properties>
               <dashboardType>site</dashboardType>
            </properties>
         </component>
         <component>
            <scope>page</scope>
            <region-id>component-1-1</region-id>
            <source-id>site/${siteid}/dashboard</source-id>
            <url>/components/dashlets/colleagues</url>
            <properties>
               <height>504</height>
            </properties>
         </component>
         <component>
            <scope>page</scope>
            <region-id>component-2-1</region-id>
            <source-id>site/${siteid}/dashboard</source-id>
            <url>/components/dashlets/docsummary</url>
         </component>
         <component>
            <scope>page</scope>
            <region-id>component-2-2</region-id>
            <source-id>site/${siteid}/dashboard</source-id>
            <url>/components/dashlets/activityfeed</url>
         </component>
      </components>
      <pages>
         <page id="site/${siteid}/dashboard">
            <title>Collaboration Site Dashboard</title>
            <title-id>page.siteDashboard.title</title-id>
            <description>Collaboration site's dashboard page</description>
            <description-id>page.siteDashboard.description</description-id>
            <template-instance>dashboard-2-columns-wide-right</template-instance>
            <authentication>user</authentication>
            <properties>
               <sitePages><%getPublicPageList()%></sitePages>
            </properties>
         </page>
      </pages>
   </preset>
   
</presets>