<%--
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
--%>
<%
metamodel http://www.kerblue.org/common/1.0/
import com.bluexml.side.util.generator.documentation.services.DocumentationServices
%>
<%script type="ecore.EPackage" name="validatedFilename"%>
<%if (eContainer() == null) {%><%getModelName()%>/doc/settings.xml<%}%>
<%script type="ecore.EPackage" name="mimetype" file="<%validatedFilename%>"%>
<?xml version="1.0" encoding="UTF-8"?>
<office:document-settings xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
    xmlns:xlink="http://www.w3.org/1999/xlink"
    xmlns:config="urn:oasis:names:tc:opendocument:xmlns:config:1.0"
    xmlns:ooo="http://openoffice.org/2004/office" office:version="1.2">
    <office:settings>
        <config:config-item-set config:name="ooo:view-settings">
            <config:config-item config:name="ViewAreaTop" config:type="int">0</config:config-item>
            <config:config-item config:name="ViewAreaLeft" config:type="int">0</config:config-item>
            <config:config-item config:name="ViewAreaWidth" config:type="int"
                >36805</config:config-item>
            <config:config-item config:name="ViewAreaHeight" config:type="int"
                >18284</config:config-item>
            <config:config-item config:name="ShowRedlineChanges" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="InBrowseMode" config:type="boolean"
                >false</config:config-item>
            <config:config-item-map-indexed config:name="Views">
                <config:config-item-map-entry>
                    <config:config-item config:name="ViewId" config:type="string"
                        >view2</config:config-item>
                    <config:config-item config:name="ViewLeft" config:type="int"
                        >10490</config:config-item>
                    <config:config-item config:name="ViewTop" config:type="int"
                        >3002</config:config-item>
                    <config:config-item config:name="VisibleLeft" config:type="int"
                        >0</config:config-item>
                    <config:config-item config:name="VisibleTop" config:type="int"
                        >0</config:config-item>
                    <config:config-item config:name="VisibleRight" config:type="int"
                        >36804</config:config-item>
                    <config:config-item config:name="VisibleBottom" config:type="int"
                        >18283</config:config-item>
                    <config:config-item config:name="ZoomType" config:type="short"
                        >0</config:config-item>
                    <config:config-item config:name="ViewLayoutColumns" config:type="short"
                        >1</config:config-item>
                    <config:config-item config:name="ViewLayoutBookMode" config:type="boolean"
                        >false</config:config-item>
                    <config:config-item config:name="ZoomFactor" config:type="short"
                        >100</config:config-item>
                    <config:config-item config:name="IsSelectedFrame" config:type="boolean"
                        >false</config:config-item>
                </config:config-item-map-entry>
            </config:config-item-map-indexed>
        </config:config-item-set>
        <config:config-item-set config:name="ooo:configuration-settings">
            <config:config-item config:name="AddParaTableSpacing" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="PrintReversed" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="OutlineLevelYieldsNumbering" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="LinkUpdateMode" config:type="short"
                >1</config:config-item>
            <config:config-item config:name="PrintEmptyPages" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="IgnoreFirstLineIndentInNumbering" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="CharacterCompressionType" config:type="short"
                >0</config:config-item>
            <config:config-item config:name="PrintSingleJobs" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="UpdateFromTemplate" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="PrintPaperFromSetup" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="AddFrameOffsets" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="PrintLeftPages" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="RedlineProtectionKey" config:type="base64Binary"/>
            <config:config-item config:name="PrintTables" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="ChartAutoUpdate" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="PrintControls" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="PrinterSetup" config:type="base64Binary"/>
            <config:config-item config:name="IgnoreTabsAndBlanksForLineCalculation"
                config:type="boolean">false</config:config-item>
            <config:config-item config:name="PrintAnnotationMode" config:type="short"
                >0</config:config-item>
            <config:config-item config:name="LoadReadonly" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="AddParaSpacingToTableCells" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="AddExternalLeading" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="ApplyUserData" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="FieldAutoUpdate" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="SaveVersionOnClose" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="SaveGlobalDocumentLinks" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="IsKernAsianPunctuation" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="AlignTabStopPosition" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="ClipAsCharacterAnchoredWriterFlyFrames"
                config:type="boolean">false</config:config-item>
            <config:config-item config:name="CurrentDatabaseDataSource" config:type="string"/>
            <config:config-item config:name="TabAtLeftIndentForParagraphsInList"
                config:type="boolean">false</config:config-item>
            <config:config-item config:name="DoNotCaptureDrawObjsOnPage" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="TableRowKeep" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="PrinterName" config:type="string"/>
            <config:config-item config:name="PrintFaxName" config:type="string"/>
            <config:config-item config:name="ConsiderTextWrapOnObjPos" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="UseOldPrinterMetrics" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="PrintRightPages" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="IsLabelDocument" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="UseFormerLineSpacing" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="AddParaTableSpacingAtStart" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="UseFormerTextWrapping" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="DoNotResetParaAttrsForNumFont" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="PrintProspect" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="PrintGraphics" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="AllowPrintJobCancel" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="CurrentDatabaseCommandType" config:type="int"
                >0</config:config-item>
            <config:config-item config:name="DoNotJustifyLinesWithManualBreak" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="TabsRelativeToIndent" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="UseFormerObjectPositioning" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="PrinterIndependentLayout" config:type="string"
                >high-resolution</config:config-item>
            <config:config-item config:name="UseOldNumbering" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="PrintPageBackground" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="CurrentDatabaseCommand" config:type="string"/>
            <config:config-item config:name="PrintDrawings" config:type="boolean"
                >true</config:config-item>
            <config:config-item config:name="PrintBlackFonts" config:type="boolean"
                >false</config:config-item>
            <config:config-item config:name="UnxForceZeroExtLeading" config:type="boolean"
                >false</config:config-item>
        </config:config-item-set>
    </office:settings>
</office:document-settings>
