




//// LATEST FREEPLANE VERSION THAT WORKS WITH THE SCRIPT: freeplane-1.12.9-pre12






/***************************************************************************

version 1.34: Cleaned unused imports.
 Uniform Border Color for Selected Nodes Across All Panels. https://github.com/euu2021/Freeplane_UtilityPanels/issues/39
 Fixed breadcrumbs panel not showing up when a new view was created.
 Fixed jumping text field.
 Improved responsiveness on node selection. Disabled listeners of listModels. https://github.com/euu2021/Freeplane_UtilityPanels/issues/41


 version 1.33: Fixed Drag and Drop to make it compatible with most recent Freeplane versions.
 Refactored the backend for ListModels, so they don't get recreated on every update.
 RTL Node Text Panel

version 1.32: Ellipsis on labels in the breadcrumbs panel.
    RTL support. New user option: rtlOrientation.

version 1.31: Bugfix: wrong positioning of inspector when masterpanel is expanded.

version 1.30: Breadcrumbs panel has now transparent background.
 The ○ symbol doesn't show up in the Breadcrumbs panel.
 Text in the Node text pane (inside the inspector) automatically adjusts size of the font, to fit the pane. Minimum font size can be set at user settings.
 New option in right click menu of a list item: Open in new View.
 Option to showAncestorsOnFirstInspector, on user settings.

version 1.29: Option to show only the breadcrumbs panel.
    Dropped compatibility with FP <= 1.12.8

version 1.28: Ctrl key freezes the panels.
 New panel: Breadcrumbs, replacing the Ancestors panel.

version 1.27: nodes that have children have a ○ symbol at the start.
 New inspectors design: first panel was simplified. Ancestor panel is now part of the left panels.

version 1.26: first version of the new inspectors design.

version 1.25: Panels on the master panel expand vertically on hover, to fit the whole height of the window.
 Hidden panels in the Inspector leave a space, to show that it's hidden.

version 1.24: improvements in the vertical distribution of panels in the inspector.
 Panel nodes with the tags: now the search is limited to current view root.
 Fixed listener in the Tags Selection panel.
 Correct font color for tag in panels.
 Context menu: option to clear tags selection.
 Context menu: created a separator.
 New panel in Inspector: Tags in Node
 Labels in panels now have labels aesthetics.
 Labels are placed side by side in panels.
 Now, hovering over a tag in the tags panel shows, in the Inspector, the nodes that have that tag.

version 1.23: Fixed bug in performance of tags identifier.
 Fixed the method to get all tags in map.

version 1.22: Fixed bug in colors in the list.

version 1.21: Fixed bug when UP configs file wasn't created.

version 1.20: Fixed bug when node style had no colors set.

version 1.19: Fixed bug when node style had no colors set.

version 1.18: Tags: now, adding, adds to all selected nodes.
 Tags: right click on tag opens context menu. Option to remove tag.
 Tags: basic filtering of the tags list in the tags panel.
 Tags: highlighters for tags. It highlights nodes that have all the tags in the selection.
 Tags: tags selection. A tag is added to the selection on the right click context menu. The tag selection list appears on the fist inspector. Also there, appears a list of nodes containing the tags.

version 1.17: added mouse listeners to scrollbars and scrollbars arrows.
    List of recent nodes is now saved between sessions.
    Tags Panel (very basic).

version 1.16: Quick search: search field is cleared when map view is changed.
 Created caching mechanism in the NodeHighlighter to optimize performance. By storing and reusing the state of highlighted nodes and their descendants, the script reduces unnecessary recursive operations, resulting in faster load times and a more responsive user interface, particularly in complex and large-scale maps
 Automatically remove panels, in the Inspector that have no nodes, including the ancestors panel.
 Included shared mouse listener in context menu and in master panel.
 Removed space between masterpanel and inspectorpanel. Also, removed space between inspector panels. Increased the border for inspector panels.

version 1.15: substring search with multiple terms: Quick Search now supports partial word matching, allowing nodes to be found if their text contains fragments of the searched terms. For example, searching for 'ab 12' will find 'abc 123'.
 Transversal Search: Quick Search now supports transversal search, finding nodes that contain at least one of the searched terms and whose ancestors complement the other terms. For example, searching for 'ab 12' will find a node with text 'abc' if any ancestor contains '12'.
 Folded nodes with matching descendants are now highlighted in red, indicating they have hidden search results. Orange for nodes directly found that also have highlighted descendants and are folded.
 Recent Nodes Panel: improved the logic for the recent nodes panel: selected nodes are now always moved to the top of the list, avoiding duplicates. If the node is not already in the list, it is added at the top.
 Recent Nodes Panel increased the number of nodes that can be stored to 200.
 Automatically remove panels, in the Inspector, that have no nodes. And, inspector gets smaller when there are fewer panels.
 Changed design of inspector panel buttons.
 Update selection is active by default.
 Created the user option: widthOfTheClearButtonOnQuickSearchPanel.
 Fixed mouse click while mouse moving was interpreted as a drag to itself.

version 1.14: add horizontal scrollbar to pinned nodes, quick search and history panels.
 Created option additionalInspectorDistanceToTheBottomOfTheScreen.
 Fixed Blinking "Update Selection" panel when mouse on an empty space of a list.
 Added history of recent searches.
 Added shortcut to quick search.
 Automatically remove panels, in the Inspector, that have no nodes

version 1.13: Quick Search now works without Jumper integration.

version 1.12: In siblings panel, scrollbar rolls automatically to selected node.

version 1.11: Fine tuned reaction to mouse listeners.
    Fixed calculation of inspector locations.

version 1.10: Fixed inspector hiding with Update Selection enabled.
 Fixed Update Selection button only in first inspector.
 Created option paddingBeforeHorizontalScrollBar, to avoid the vertical scrollbar appearing unnecessarily;

version 1.9: Fixed calculation of inspector location.
 Fixed node text panel scrollbar not starting at the top.
 Option to reverse the order of ancestors list.

version 1.8: selection delay
 Fixed size calculations relative to map view window.

version: 1.7: Inspector max height is equal to the window height.
 Not necessary to have Map Overview active anymore.
 Solved graphical glitch problem (actually, it was multiple inspectors being created).
 Master panel expands on hover.
 Width os the Master Panel is relative to the width of the window.
 Master panel adapts to the size (width and height) of the window automatically, when it's resized.
 User settings section, to make things easier to config.
 Quick search panel now is transparent
 Right clicking on a list item doesn't navigate to it, anymore.

version: 1.6: Inspector height adapts to the content.

version: 1.5: performance improvement when Update Selection is enabled. Inspector height adapts to the content.

 *****************************************************************/

// @ExecutionModes({ON_SINGLE_NODE="/menu_bar/euu"})


import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.transform.Field
import org.freeplane.api.NodeChangeListener
import org.freeplane.api.NodeChanged
import org.freeplane.core.ui.components.UITools
import org.freeplane.features.filter.Filter
import org.freeplane.features.highlight.HighlightController
import org.freeplane.features.highlight.NodeHighlighter
import org.freeplane.features.icon.IconController
import org.freeplane.features.icon.Tag
import org.freeplane.features.icon.Tags
import org.freeplane.features.icon.mindmapmode.MIconController
import org.freeplane.features.map.*
import org.freeplane.features.map.clipboard.MapClipboardController
import org.freeplane.features.map.clipboard.MindMapNodesSelection
import org.freeplane.features.map.mindmapmode.MMapController
import org.freeplane.features.mode.Controller
import org.freeplane.features.nodestyle.NodeStyleController
import org.freeplane.features.styles.LogicalStyleController.StyleOption
import org.freeplane.features.ui.IMapViewChangeListener

import javax.swing.*
import javax.swing.border.Border
import javax.swing.border.TitledBorder
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.event.ListSelectionListener
import java.awt.*
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.UnsupportedFlavorException
import java.awt.dnd.*
import java.awt.event.*
import java.util.List
import java.util.regex.Pattern

import java.awt.MouseInfo
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import org.freeplane.core.extension.IExtension;
import org.freeplane.core.ui.components.UITools;
import org.freeplane.core.util.Compat;
import org.freeplane.features.link.ConnectorModel;
import org.freeplane.features.link.ConnectorShape;
import org.freeplane.features.link.Connectors;
import org.freeplane.features.link.LinkController;
import org.freeplane.features.link.mindmapmode.MLinkController;
import org.freeplane.features.map.FreeNode;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.map.NodeModel.Side;
import org.freeplane.features.map.mindmapmode.MMapController;
import org.freeplane.features.mode.Controller;
import org.freeplane.features.mode.ModeController;
import org.freeplane.features.mode.mindmapmode.MModeController;
import org.freeplane.features.styles.MapStyleModel;
import org.freeplane.features.styles.MapViewLayout;
import org.freeplane.view.swing.map.MapView;
import org.freeplane.view.swing.map.NodeView;
import org.freeplane.view.swing.map.link.ConnectorView;
import org.freeplane.view.swing.map.link.InclinationRecommender;
import org.freeplane.view.swing.ui.DefaultMapMouseListener;


//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ User settings ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

panelTextFontName = "Dialog"
panelTextFontSize = 15
minFontSize = 8 //minimum size, to be used on dynamic sizing of fonts
fontForListItens = Font.PLAIN

nodeTextPanelFixedHeight = 100

retractedWidthFactorForMasterPanel = 20 //the higher the factor, the smaller the panels width
expandedWidthFactorForMasterPanel = 4 //the higher the factor, the wider the panels width
widthFactorForInspector = 15 //the higher the factor, the smaller the inspector panel width

@Field selectionDelay = 150 //miliseconds

reverseAncestorsList = false

paddingBeforeHorizontalScrollBar = 30

additionalInspectorDistanceToTheBottomOfTheScreen = 175

widthOfTheClearButtonOnQuickSearchPanel = 30

@Field KeyStroke keyStrokeToQuickSearch = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK)

@Field boolean showOnlyBreadcrumbs = false

showAncestorsOnFirstInspector = true

@Field rtlOrientation = false

//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ User settings ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑












@Field DefaultListModel<String> allTags = new DefaultListModel<String>()

fontForItems = new Font(panelTextFontName, fontForListItens, panelTextFontSize)

uniqueIdForScript = 999
deleteCurrentListenersFromPreviousExecutions()

@Field DefaultListModel<NodeModel> ancestorsOfCurrentNode = new DefaultListModel<>()
@Field DefaultListModel<NodeModel> history = new DefaultListModel<>()
@Field DefaultListModel<NodeModel> pinnedItems = new DefaultListModel<>()
@Field DefaultListModel<NodeModel> quickSearchResults = new DefaultListModel<>()

@Field DefaultListModel<Tags> selectedTagsInPanelModel = new DefaultListModel<>()

//@Field List<NodeModel> ancestorsOfCurrentNode = []
//@Field List<NodeModel> history = []
//@Field List<NodeModel> pinnedItems = []
//@Field List<NodeModel> quickSearchResults = []
@Field List<JPanel> visibleInspectors = []
@Field List<JPanel> inPlaceInspectors = []
@Field List<String> savedSearchCriteria = []
savedSearchCriteria.add("")
@Field List<Tags> selectedTagsInPanel = []
@Field List<Tag> hoveredTag = []
@Field DefaultListModel<NodeModel> nodesThatContainAnyTagInTagsSelectionModel = new DefaultListModel<>()
@Field DefaultListModel<NodeModel> nodesThatContainHoveredTagModel = new DefaultListModel<>()
@Field DefaultListModel<Tag> hoveredTagModel = new DefaultListModel<>()
@Field JList <NodeModel> ancestorsJList = new JList<>()

@Field DefaultListModel<String> listModelForAllTags = new DefaultListModel<>()

@Field JScrollPane parentPanel
@Field JPanel masterPanel
@Field JPanel breadcrumbPanel
@Field List<JPanel> panelsInMasterPanels = []
@Field JPanel recentSelectedNodesPanel
@Field JPanel pinnedItemsPanel
@Field JPanel tagsPanel
@Field JPanel quickSearchPanel
@Field JPanel innerPanelInQuickSearchPanel
@Field JPanel inspectorPanel

@Field JPanel currentSourcePanel

@Field JTextField searchEditor

@Field boolean mouseOverList = false
@Field boolean freezeInspectors = false
@Field boolean inspectorUpdateSelection = true
@Field boolean isMasterPanelExpanded = false
@Field boolean isMouseOverSearchBox = false
@Field boolean tagsNeedUpdate = true

@Field int lastMouseModifiers = 0


mapViewWindowForSizeReferences = Controller.currentController.mapViewManager.mapView.parent

@Field String searchText = ""
@Field String lastSearchText = ""
@Field final String ELLIPSIS = "..."

@Field NodeModel currentlySelectedNode = Controller.currentController.MapViewManager.mapView.mapSelection.selectionRoot
@Field NodeModel hoveredNode


@Field MIconController iconController = (MIconController) Controller.currentModeController.getExtension(IconController.class)


@Field Set<NodeModel> cachedHighlightedNodes = new HashSet<>()
@Field Set<NodeModel> cachedHighlightedNodesTags = new HashSet<>()

@Field DocumentListener searchTextBoxListener

@Field Timer liveSearchTimer = new Timer(200, null)
liveSearchTimer.setRepeats(false)

@Field Timer hideInspectorTimer = new Timer(500, null)

hideInspectorTimer.setRepeats(false)
hideInspectorTimer.addActionListener(e -> {
    hideInspectorPanelIfNeeded()
})

@Field MouseListener sharedMouseListener

sharedMouseListener = new MouseAdapter() {
    @Override
    public void mouseEntered(MouseEvent e) {
        hideInspectorTimer.stop()
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hideInspectorTimer.restart()
    }
}

@Field Timer hoverTimer = new Timer(selectionDelay, null)
@Field Point lastMouseLocation = null

hoverTimer.setRepeats(false)
hoverTimer.addActionListener(e -> {
    if (showOnlyBreadcrumbs) { return }
    if (shouldFreeze()) return
    

    if(panelsInMasterPanels.contains(currentSourcePanel)) {
        expandMasterPanel()


        visibleInspectors.each{
            if(!inspectorUpdateSelection) {
                it.setVisible(false)
            }
            else{
                if(it != visibleInspectors[0] && it != visibleInspectors[1]) {
                    it.setVisible(false)
                }
            }
        }

        if(!inspectorUpdateSelection) {
            visibleInspectors.clear()
        }
        else {
            visibleInspectors.removeAll { it != visibleInspectors[0] && it != visibleInspectors[1]}
            if(visibleInspectors.size() != 0) {
                setInspectorLocation(visibleInspectors[0], masterPanel)
                if(visibleInspectors.size() > 1) {
                    setInspectorLocation(visibleInspectors[1], visibleInspectors[0])
                }
            }
        }
    }

     if (lastMouseLocation) {


        int index = currentList.locationToIndex(lastMouseLocation)

        Rectangle cellBounds = currentList.getCellBounds(index, index)
        if (cellBounds != null && cellBounds.contains(lastMouseLocation)) {
            if (index >= 0) {
                Object hoveredItem = currentListModel.getElementAt(index)
                if(hoveredItem instanceof NodeModel) {
                    NodeModel subNode = currentListModel.getElementAt(index)
                    hoveredNode = subNode

                    ancestorsOfCurrentNode.clear()
                    if(reverseAncestorsList) {
                        hoveredNode.getPathToRoot().reverse().each {
                            ancestorsOfCurrentNode.addElement(it)
                        }
                    }
                    else{
                        hoveredNode.getPathToRoot().each {
                            ancestorsOfCurrentNode.addElement(it)
                        }
                    }

//                    updateAllGUIs()

                    if (panelsInMasterPanels.contains(currentSourcePanel) || currentSourcePanel == breadcrumbPanel) {
                        cleanAndCreateInspectors(subNode, masterPanel)
                    } else {


                        JTextPane textLabelInInspector = (JTextPane) currentSourcePanel.getClientProperty("textLabel")


                        configureLabelForNode(textLabelInInspector, hoveredNode, currentSourcePanel)


                        DefaultListModel<Tags> accessorTagsInNodeModel = (DefaultListModel<Tags>) currentSourcePanel.getClientProperty("tagsInNodeAccessor")
                        accessorTagsInNodeModel.clear()
                        iconController.getTags(hoveredNode).each {
                            accessorTagsInNodeModel.addElement(it)
                        }



                        subInspectorPanel = createInspectorPanel(subNode, currentSourcePanel)

                        visibleInspectors.add(subInspectorPanel)
                        locationOfTheInspectorOfTheCurrentPanelUnderMouse = subInspectorPanel.getLocation().x
                        visibleInspectors.clone().each {
                            if (it != subInspectorPanel && it.getLocation().x >= locationOfTheInspectorOfTheCurrentPanelUnderMouse) {
                                it.setVisible(false)
                                visibleInspectors.remove(it)
                            }
                        }
                        parentPanel.revalidate()
                        parentPanel.repaint()
                    }
                }
                else if(currentSourcePanel == tagsPanel) {
                    Tag tagHovered = currentListModel.getElementAt(index)
                    hoveredTagModel.clear()
                    hoveredTagModel.addElement(tagHovered)

                    List<Tag> tagsListForComparison = []
                    tagsListForComparison.add(tagHovered)



                    nodesThatContainHoveredTagModel.clear()
                    c.viewRoot.findAll().each {
                        if (selectedTagsInPanel.size() == 0 && iconController.getTags(it.delegate).containsAll(tagsListForComparison)) {
                            nodesThatContainHoveredTagModel.addElement(it.delegate)
                        }
                    }
                    cleanAndCreateInspectors(currentlySelectedNode, currentSourcePanel)
                }
            }
        }
    }
})

class NodeModelTransferable implements Transferable {
    private static final DataFlavor NODE_MODEL_FLAVOR = new DataFlavor(NodeModel.class, "NodeModel")
    private final NodeModel nodeModel

    public NodeModelTransferable(NodeModel nodeModel) {
        this.nodeModel = nodeModel
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{NODE_MODEL_FLAVOR}
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return NODE_MODEL_FLAVOR.equals(flavor)
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor)
        }
        return nodeModel
    }
}

loadSettings()
createPanels()

INodeSelectionListener mySelectionListener = new INodeSelectionListener() {
    @Override
    public void onDeselect(NodeModel node) {

    }

    @Override
    public void onSelect(NodeModel node) {
        if (node == currentlySelectedNode) {
            return
        }

        currentlySelectedNode = node
        hoveredTagModel.clear()


        if (history.contains(node)) {
            history.removeElement(node)
        }
        history.insertElementAt(node, 0)
        if (history.getSize() > 200) {
            history.removeElementAt(200)
        }

        saveSettings()

        def newAncestorsModel = new DefaultListModel<NodeModel>()
        if (reverseAncestorsList) {
            node.getPathToRoot().reverse().each { newAncestorsModel.addElement(it) }
        } else {
            node.getPathToRoot().each { newAncestorsModel.addElement(it) }
        }

        ancestorsOfCurrentNode = newAncestorsModel
        ancestorsJList.setModel(ancestorsOfCurrentNode)


        if (freezeInspectors || isMouseOverSearchBox) {
            return
        }
        if (inspectorUpdateSelection) {
            cleanAndCreateInspectors(node, panelsInMasterPanels[0])
        }


        ////////////////inplaceinspector////////////

//        mapView = Controller.currentController.mapViewManager.mapView
//
//
//        NodeView root = mapView.getRoot();
//        final JComponent rootContent = root.getMainView();
//        final Point contentPt = new Point();
//        UITools.convertPointToAncestor(rootContent, contentPt, mapView);
//        final float zoom = mapView.getZoom();
////final Point eventPoint = e.getPoint();
//        eventPoint = MouseInfo.getPointerInfo().getLocation()
//        SwingUtilities.convertPointFromScreen(eventPoint, mapView)
////        int x =(int) ((eventPoint.x - contentPt.x)/zoom);
//        int x =(int) ((eventPoint.x - contentPt.x));
//        final int y =(int) ((eventPoint.y - contentPt.y)/zoom);
//
//
//
//        inPlaceInspectors.each {
//            it.setVisible(false)
//        }
//        inPlaceInspectors.clear()
//        inPlaceInspector = createInspectorPanel(currentlySelectedNode, panelsInMasterPanels[0])
//        inPlaceInspectors.add(inPlaceInspector)
////        mousePosition = MouseInfo.getPointerInfo().getLocation()
////        inPlaceInspector.setBounds((int) mousePosition.getX(),(int) mousePosition.getY(), 300, 300)
//        inPlaceInspector.setBounds((int) x, 500, 300, 300)
//        inPlaceInspector.setVisible(true)

        ////////////////////////

    }

}

createdSelectionListener = mySelectionListener

Controller.currentController.modeController.mapController.addNodeSelectionListener(mySelectionListener)

IMapViewChangeListener myMapViewChangeListener = new IMapViewChangeListener() {
    public void afterViewChange(final Component oldView, final Component newView) {
        if (newView == null) {
            return
        }

        searchText = ""
        quickSearchResults.clear()

//        panelsInMasterPanels.each {
//            parentPanel.remove(it)
//        }
//        breadcrumbPanel.parentPanel.remove(breadcrumbPanel)

        saveSettings()
        masterPanel.setVisible(false)
        breadcrumbPanel.setVisible(false)
        visibleInspectors.each {it.setVisible(false)}
        SwingUtilities.invokeLater {
        createPanels()
        masterPanel.revalidate()
        masterPanel.repaint()
        breadcrumbPanel.revalidate()
        breadcrumbPanel.repaint()
        }
//        SwingUtilities.invokeLater { updateAllGUIs() }
    }
}

createdMapViewChangeListener = myMapViewChangeListener

Controller.currentController.mapViewManager.addMapViewChangeListener(myMapViewChangeListener)

IMapChangeListener myMapChangeListener = new IMapChangeListener() {
    @Override
    public void onNodeDeleted(NodeDeletionEvent nodeDeletionEvent) {
        NodeModel deletedNode = nodeDeletionEvent.node
        history.removeElement(deletedNode)
        pinnedItems.removeElement(deletedNode)
        saveSettings()
//        SwingUtilities.invokeLater { updateAllGUIs() }
    }

}

Controller.currentController.modeController.getMapController().addUIMapChangeListener(myMapChangeListener)


NodeChangeListener myNodeChangeListener= {NodeChanged event->
    if(event.changedElement == NodeChanged.ChangedElement.TAGS) {
        loadTagsIntoModel(listModelForAllTags, currentlySelectedNode)
//        tagsNeedUpdate = true
//        updateAllGUIs()
//        masterPanel.revalidate()
//        masterPanel.repaint()
    }
} as NodeChangeListener

mindMap.addListener(myNodeChangeListener)


viewportSizeChangeListener = new ComponentAdapter() {
    @Override
    public void componentResized(final ComponentEvent e) {
//        panelsInMasterPanels.each {
//            parentPanel.remove(it)
//        }
        saveSettings()
        masterPanel.setVisible(false)
        breadcrumbPanel.setVisible(false)
        visibleInspectors.each {it.setVisible(false)}
        createPanels()
        masterPanel.revalidate()
        masterPanel.repaint()
        breadcrumbPanel.revalidate()
        breadcrumbPanel.repaint()
//        SwingUtilities.invokeLater { updateAllGUIs() }
    }
}

mapViewWindowForSizeReferences.addComponentListener(viewportSizeChangeListener)


Controller controllerForHighlighter = Controller.currentModeController.controller
controllerForHighlighter.getExtension(HighlightController.class).addNodeHighlighter(new NodeHighlighter() {

    @Override
    public boolean isNodeHighlighted(NodeModel node, boolean isPrinting) {
        if(searchText.equals("")) { return  }
        if (isPrinting) {
            return false
        }
        return (quickSearchResults.contains(node))
    }

    @Override
    public void configure(NodeModel node, Graphics2D g, boolean isPrinting) {
        boolean isFound = quickSearchResults.contains(node)

        if (isFound) {
            g.setColor(new Color(0, 255, 0, 255))
            g.setStroke(new BasicStroke(5F, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10, new float[]{10, 2}, 0))
        }
    }

})


controllerForHighlighter.getExtension(HighlightController.class).addNodeHighlighter(new NodeHighlighter() {

    @Override
    public boolean isNodeHighlighted(NodeModel node, boolean isPrinting) {
        if(searchText.equals("")) { return  }
        if (isPrinting) {
            return false
        }
        return (isFoldedWithHighlightedDescendants(node))
    }

    @Override
    public void configure(NodeModel node, Graphics2D g, boolean isPrinting) {
        boolean hasFoldedDescendants = isFoldedWithHighlightedDescendants(node)


        if (hasFoldedDescendants) {
            g.setColor(new Color(1, 125, 32, 255))
            g.setStroke(new BasicStroke(5F, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10, new float[]{10, 2}, 0))
        }


    }

    private boolean isFoldedWithHighlightedDescendants(NodeModel node) {
        if (!node.folded) {
            return false
        }
        if (cachedHighlightedNodes.contains(node)) {
            return true
        }
        boolean hasDescendants = hasHighlightedDescendants(node)
        if (hasDescendants) {
            cachedHighlightedNodes.add(node)
        }
        return hasDescendants
    }

    private boolean hasHighlightedDescendants(NodeModel node) {
        for (NodeModel child : node.children) {
            if (quickSearchResults.contains(child) || hasHighlightedDescendants(child)) {
                return true
            }
        }
        return false
    }
})

def refreshHighlighterCache() {
    cachedHighlightedNodes.clear()
}


controllerForHighlighter.getExtension(HighlightController.class).addNodeHighlighter(new NodeHighlighter() {

    @Override
    public boolean isNodeHighlighted(NodeModel node, boolean isPrinting) {
        if(selectedTagsInPanel.size() == 0) { return  }
        if (isPrinting) {
            return false
        }
        return (iconController.getTags(node).containsAll(selectedTagsInPanel))
    }

    @Override
    public void configure(NodeModel node, Graphics2D g, boolean isPrinting) {
        boolean hasSelectedTags = iconController.getTags(node).containsAll(selectedTagsInPanel)

        if (hasSelectedTags) {
            g.setColor(new Color(0, 183, 255, 255))
            g.setStroke(new BasicStroke(5F, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10, new float[]{10, 2}, 0))
        }

    }

})

controllerForHighlighter.getExtension(HighlightController.class).addNodeHighlighter(new NodeHighlighter() {

    @Override
    public boolean isNodeHighlighted(NodeModel node, boolean isPrinting) {
        if(selectedTagsInPanel.size() == 0) { return  }
        if (isPrinting) {
            return false
        }
        return (isFoldedWithHighlightedDescendantsTags(node))
    }

    @Override
    public void configure(NodeModel node, Graphics2D g, boolean isPrinting) {
        boolean hasFoldedDescendants = isFoldedWithHighlightedDescendantsTags(node)

        if (hasFoldedDescendants) {
            g.setColor(new Color(1, 0, 255, 255))
            g.setStroke(new BasicStroke(5F, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10, new float[]{10, 2}, 0))
        }
    }

    private boolean isFoldedWithHighlightedDescendantsTags(NodeModel node) {
        if (!node.folded) {
            return false
        }
        if (cachedHighlightedNodesTags.contains(node)) {
            return true
        }
        boolean hasDescendants = hasHighlightedDescendants(node)
        if (hasDescendants) {
            cachedHighlightedNodesTags.add(node)
        }
        return hasDescendants
    }

    private boolean hasHighlightedDescendants(NodeModel node) {
        for (NodeModel child : node.children) {
            if (iconController.getTags(child).containsAll(selectedTagsInPanel) || hasHighlightedDescendants(child)) {
                return true
            }
        }
        return false
    }
})

def refreshHighlighterCacheTags() {
    cachedHighlightedNodesTags.clear()
}



return


// ------------------ methods definitions ------------------------

def createPanels() {
    parentPanel = Controller.currentController.mapViewManager.mapView.parent.parent as JScrollPane
    Dimension parentSize = parentPanel.getSize()


    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Master Panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓




    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Master Panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Recent Nodes Panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    recentSelectedNodesPanel = new JPanel(new BorderLayout()) {
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    recentSelectedNodesPanel.setOpaque(false)
    recentSelectedNodesPanel.setBackground(new Color(0, 0, 0, 0))

    if (rtlOrientation) {
        recentSelectedNodesPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        recentSelectedNodesPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }

//    int recentSelectedNodesPanelWidth = 80
//    int recentSelectedNodesPanelHeight = 170

//    recentSelectedNodesPanel.setBounds(0, 0, recentSelectedNodesPanelWidth, recentSelectedNodesPanelHeight)


    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Recent Nodes Panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Pinned Items Panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    pinnedItemsPanel = new JPanel(new BorderLayout()) {
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    pinnedItemsPanel.setOpaque(false)
    pinnedItemsPanel.setBackground(new Color(0, 0, 0, 0))

    if (rtlOrientation) {
        pinnedItemsPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        pinnedItemsPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }


//    pinnedItemsPanel.setMaximumSize(pinnedItemsPanel.getPreferredSize())

//    int pinnedPanelHeight = 130
//    pinnedItemsPanel.setBounds(0, recentSelectedNodesPanelHeight + 20, recentSelectedNodesPanelWidth, pinnedPanelHeight)


    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Pinned Items Panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Tags Panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    tagsPanel = new JPanel(new BorderLayout()) {
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    tagsPanel.setOpaque(false)
    tagsPanel.setBackground(new Color(0, 0, 0, 0))

    if (rtlOrientation) {
        tagsPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        tagsPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }

//    int tagsPanelHeight = 130
//    tagsPanel.setBounds(0, recentSelectedNodesPanelHeight + 20, recentSelectedNodesPanelWidth, tagsPanelHeight)

    //was on the updateTagsGUI():

    //    tagsPanel.removeAll()


    NodeModel selectedNode = currentlySelectedNode
//    if(tagsNeedUpdate) {
//        loadTagsIntoModel(listModelForAllTags, selectedNode)
//    }

    loadTagsIntoModel(listModelForAllTags, selectedNode)

    JList<String> tagsJList = new JList<>(listModelForAllTags)

    if (rtlOrientation) {
        tagsJList.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        tagsJList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }

    // search field
    JTextField tagsSearchField = new JTextField()

    tagsSearchField.addMouseListener(sharedMouseListener)

    tagsSearchField.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        void insertUpdate(DocumentEvent e) { filterTags() }

        @Override
        void removeUpdate(DocumentEvent e) { filterTags() }

        @Override
        void changedUpdate(DocumentEvent e) { filterTags() }

        private void filterTags() {
            String searchText = tagsSearchField.getText().toLowerCase()
            DefaultListModel<String> filteredModel = new DefaultListModel<>()

            for (int i = 0; i < listModelForAllTags.size(); i++) {
                tag = listModelForAllTags.getElementAt(i)
                if (tag.getContent().toLowerCase().contains(searchText)) {
                    filteredModel.addElement(tag)
                }
            }
            tagsJList.setModel(filteredModel)
//            tagsNeedUpdate = true
        }
    })


    // clear button

    JButton tagsClearButton = new JButton("X")
    tagsClearButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedTagsInPanel.clear()
            refreshHighlighterCacheTags()
//            tagsNeedUpdate = true
            tagsJList.setModel(listModelForAllTags)
            cleanAndCreateInspectors(currentlySelectedNode, panelsInMasterPanels[0])
//            updateAllGUIs()
//            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
//            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
        }
    })

    tagsClearButton.setPreferredSize(new Dimension(widthOfTheClearButtonOnQuickSearchPanel, 1))
    tagsClearButton.setForeground(Color.BLACK)
    tagsClearButton.setBackground(Color.WHITE)
    tagsClearButton.setBorder(BorderFactory.createEtchedBorder())
    tagsClearButton.setOpaque(true)
    tagsClearButton.setBorderPainted(true)
    tagsClearButton.setFocusPainted(false)

    tagsClearButton.addMouseListener(sharedMouseListener)


    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Tag List Configs ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓


    commonTagsJListsConfigs(tagsJList, listModelForAllTags, tagsPanel)


    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Tag List Configs ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    JScrollPane scrollPane = new JScrollPane(tagsJList) {
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }

    configureScrollPaneForRTL(scrollPane)
    scrollPane.setBackground(new Color(0, 0, 0, 0))
    tagsJList.setOpaque(false)
    scrollPane.setOpaque(false)
    scrollPane.getViewport().setOpaque(false)

    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
    scrollPane.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    scrollPane.getHorizontalScrollBar().addMouseListener(sharedMouseListener)
    addMouseListenerToScrollBarButtons(scrollPane.getVerticalScrollBar())
    addMouseListenerToScrollBarButtons(scrollPane.getHorizontalScrollBar())

    JPanel panelForField = new JPanel(new BorderLayout()) {
        {
            if (rtlOrientation) {
                setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
            } else {
                setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
            }
        }
    }

    panelForField.add(tagsSearchField, BorderLayout.CENTER)
    panelForField.add(tagsClearButton, BorderLayout.EAST)

    panelForField.setOpaque(false)
    panelForField.setBackground(new Color(0, 0, 0, 0))

    tagsPanel.add(panelForField, BorderLayout.NORTH)

    tagsPanel.add(scrollPane, BorderLayout.CENTER)
    tagsPanel.revalidate()
    tagsPanel.repaint()

//    tagsNeedUpdate = false

    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Tags Panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Quick Search Panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓


    quickSearchPanel = new JPanel(new BorderLayout()) {
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    quickSearchPanel.setOpaque(false)
    quickSearchPanel.setBackground(new Color(0, 0, 0, 0))

    if (rtlOrientation) {
        quickSearchPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        quickSearchPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }

//    int quickSearchPanelHeight = 130
//    quickSearchPanel.setBounds(0, recentSelectedNodesPanelHeight + 170, recentSelectedNodesPanelWidth, quickSearchPanelHeight)


    JComboBox<String> searchField = new JComboBox<>(savedSearchCriteria.toArray(new String[0]))
    searchField.setEditable(true)
    searchField.setSelectedItem("")

    searchEditor = (JTextField) searchField.getEditor().getEditorComponent()

    searchEditor.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            scheduleLiveSearch()
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            scheduleLiveSearch()
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            scheduleLiveSearch()
        }

        private void scheduleLiveSearch() {
            liveSearchTimer.stop()
            liveSearchTimer.start()
        }
    })



    liveSearchTimer.addActionListener(new ActionListener() {
        @Override
        void actionPerformed(ActionEvent e) {
            searchText = searchEditor.getText().trim()

            if (!searchText.equals(lastSearchText)) {
                lastSearchText = searchText
                refreshList(searchText)
            }

            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
//            updateAllGUIs()

        }

        private void refreshList(String searchText) {
            quickSearchResults.clear()
            refreshHighlighterCache()
            if (!searchText.isEmpty()) {
                NodeModel rootNode = Controller.getCurrentController().getSelection().selectionRoot
                searchNodesRecursively(rootNode, searchText, quickSearchResults)

                if (!savedSearchCriteria.contains(searchText)) {
                    savedSearchCriteria.add(0, searchText)
                } else {
                    savedSearchCriteria.remove(searchText)
                    savedSearchCriteria.add(0, searchText)
                }

                saveSettings()

                int caretPosition = searchEditor.getCaretPosition()

                searchField.removeAllItems()
                for (String term : savedSearchCriteria) {
                    searchField.addItem(term)
                }

                searchEditor.setText(searchText)

                if (!searchField.isPopupVisible()) {
                    searchEditor.setCaretPosition(Math.min(caretPosition, searchText.length()))
                }
            }
        }
    })

    JButton clearButton = new JButton("X")
    clearButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            searchField.setSelectedItem("")
            quickSearchResults.clear()
//            updateAllGUIs()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
        }
    })

    clearButton.setPreferredSize(new Dimension(widthOfTheClearButtonOnQuickSearchPanel, 1))
    clearButton.setForeground(Color.BLACK)
    clearButton.setBackground(Color.WHITE)
    clearButton.setBorder(BorderFactory.createEtchedBorder())
    clearButton.setOpaque(true)
    clearButton.setBorderPainted(true)
    clearButton.setFocusPainted(false)

    JPanel panelForSearchBox = new JPanel(new BorderLayout()) {
        {
            if (rtlOrientation) {
                setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
            } else {
                setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
            }
        }
    }

    panelForSearchBox.add(searchField, BorderLayout.CENTER)
    panelForSearchBox.add(clearButton, BorderLayout.EAST)


    panelForSearchBox.setOpaque(false)
    panelForSearchBox.setBackground(new Color(0, 0, 0, 0))


    quickSearchPanel.add(panelForSearchBox, BorderLayout.NORTH)

    innerPanelInQuickSearchPanel = new JPanel(new BorderLayout())

    innerPanelInQuickSearchPanel.setOpaque(false)
    innerPanelInQuickSearchPanel.setBackground(new Color(0, 0, 0, 0))

    quickSearchPanel.add(innerPanelInQuickSearchPanel, BorderLayout.CENTER)


    searchField.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            isMouseOverSearchBox = true
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isMouseOverSearchBox = false
        }
    })



    panelForSearchBox.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            isMouseOverSearchBox = true
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isMouseOverSearchBox = false
        }
    })


    searchEditor.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            isMouseOverSearchBox = true
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isMouseOverSearchBox = false
        }
    })


    clearButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            isMouseOverSearchBox = true
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isMouseOverSearchBox = false
        }
    })

    addQuickSearchShortcut(searchField)


    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Quick Search Panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Breadcrumbs Panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓


    breadcrumbPanel = new JPanel() {
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    breadcrumbPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 5))
//    breadcrumbPanel.setLayout(new FlowLayout(rtlOrientation ? FlowLayout.RIGHT : FlowLayout.LEFT, 8, 5))
    breadcrumbPanel.setBackground(new Color(0, 0, 0, 0))
//    breadcrumbPanel.setBackground(new Color(220, 220, 220))
    breadcrumbPanel.setOpaque(false)

    breadcrumbPanel.setBounds(0, 0, parentPanel.width, 40)


    if (rtlOrientation) {
        breadcrumbPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        breadcrumbPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }

    ancestorsOfCurrentNode.clear()
    if(reverseAncestorsList) {
        currentlySelectedNode.getPathToRoot().reverse().each {
            ancestorsOfCurrentNode.addElement(it)
        }
    }
    else{
        currentlySelectedNode.getPathToRoot().each {
            ancestorsOfCurrentNode.addElement(it)
        }
    }

//    20.times {
//        ancestorsOfCurrentNode.addElement(currentlySelectedNode)
//    }

    createBreadcrumbsJList()

//    listeners2 = ancestorsOfCurrentNode.getListDataListeners()
//    listeners2.each { ancestorsOfCurrentNode.removeListDataListener(it) }

    parentPanel.add(breadcrumbPanel)
    parentPanel.setComponentZOrder(breadcrumbPanel, 0)


    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Ancestor Panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑



    if (!showOnlyBreadcrumbs) {

        masterPanel = new JPanel()
        masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS))

        masterPanel.setOpaque(false)

        if (rtlOrientation) {
            masterPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
        } else {
            masterPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
        }


//    masterPanel.setBounds(0, 100, calculateRetractedWidthForMasterPanel(), (int) mapViewWindowForSizeReferences.height -5)

        masterPanel.addMouseListener(sharedMouseListener)

        masterPanel.setBounds(0, breadcrumbPanel.height, calculateRetractedWidthForMasterPanel(), (int) mapViewWindowForSizeReferences.height - 5)


        panelsInMasterPanels = [recentSelectedNodesPanel, pinnedItemsPanel, tagsPanel, quickSearchPanel]


        panelsInMasterPanels.eachWithIndex { panel, idx ->
            if (rtlOrientation) {
                panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
            } else {
                panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
            }

            masterPanel.add(panel)

            if (idx < panelsInMasterPanels.size() - 1) {
                masterPanel.add(Box.createVerticalStrut(20))
            }
        }


        createJList(history, recentSelectedNodesPanel, recentSelectedNodesPanel)
        listeners = history.getListDataListeners().toList()
        listeners.each { history.removeListDataListener(it) }

        createJList(pinnedItems, pinnedItemsPanel, pinnedItemsPanel)
        createJList(quickSearchResults, innerPanelInQuickSearchPanel, quickSearchPanel)


        masterPanel.revalidate()
        masterPanel.repaint()

        masterPanel.setVisible(true)


        parentPanel.add(masterPanel)
        parentPanel.setComponentZOrder(masterPanel, 0)
    }

    parentPanel.revalidate()
    parentPanel.repaint()
}

def updateAllGUIs() {
//    updateBreadcrumbPanel()
//    updateRecentNodesGui()
//    updatePinnedItemsGui()
//    updateQuickSearchGui()
//    updateTagsGui()
}


JPanel createInspectorPanel(NodeModel nodeNotProxy, JPanel sourcePanel) {

    if (showOnlyBreadcrumbs) { return }

    JPanel inspectorPanel = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g)
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
        }
    }

    inspectorPanel.putClientProperty("referenceNode", nodeNotProxy)

    inspectorPanel.setLayout(new BorderLayout())
    inspectorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5))
    inspectorPanel.setBackground(Color.LIGHT_GRAY)

    if (rtlOrientation) {
        inspectorPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        inspectorPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }


    ////////////// Node Text Panel ///////////////


    JTextPane textLabel = new JTextPane()
    textLabel.setContentType("text/html")

    if (rtlOrientation) {
        textLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        textLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }

    inspectorPanel.putClientProperty("textLabel", textLabel)

    JScrollPane textScrollPane = new JScrollPane(textLabel)

    configureScrollPaneForRTL(textScrollPane)


    textScrollPane.setPreferredSize(new Dimension(200, nodeTextPanelFixedHeight))

    inspectorPanel.putClientProperty("textScrollPane", textScrollPane)

    if(visibleInspectors.size() == 0) {
        configureLabelForNode(textLabel, nodeNotProxy, inspectorPanel)
    }


    textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
    textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED)

    SwingUtilities.invokeLater {
        textScrollPane.getVerticalScrollBar().setValue(0)
        textScrollPane.getHorizontalScrollBar().setValue(0)
    }

    inspectorPanel.addMouseListener(sharedMouseListener)
    textLabel.addMouseListener(sharedMouseListener)
    textScrollPane.addMouseListener(sharedMouseListener)
    textScrollPane.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    textScrollPane.getHorizontalScrollBar().addMouseListener(sharedMouseListener)
    addMouseListenerToScrollBarButtons(textScrollPane.getVerticalScrollBar())
    addMouseListenerToScrollBarButtons(textScrollPane.getHorizontalScrollBar())

    /////////////////////////////////////////////////////////


    /////////////////////////// Buttons panel //////////////////

//    JPanel buttonPanel = new JPanel()
//    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS))
    JPanel buttonPanel = new JPanel(new FlowLayout(rtlOrientation ? FlowLayout.RIGHT : FlowLayout.LEFT, 5, 5))
    buttonPanel.setBackground(Color.LIGHT_GRAY)

    JButton button1 = new JButton("Freeze")
    button1.addActionListener(e -> {
        freezeInspectors = !freezeInspectors

        if (freezeInspectors) {
            button1.setBackground(Color.CYAN)
            button1.setForeground(Color.BLACK)
        } else {
            button1.setBackground(Color.WHITE)
            button1.setForeground(Color.BLACK)
        }
    })
    button1.setOpaque(true)
    button1.setBorderPainted(false)
    button1.setFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))

    if (freezeInspectors) {
        button1.setBackground(Color.CYAN)
        button1.setForeground(Color.BLACK)
    } else {
        button1.setBackground(Color.WHITE)
        button1.setForeground(Color.BLACK)
    }

    JButton button2 = new JButton("Update Selection")
    button2.addActionListener(e -> {
        inspectorUpdateSelection = !inspectorUpdateSelection

        if (inspectorUpdateSelection) {
            button2.setBackground(Color.GRAY)
            button2.setForeground(Color.BLACK)
        } else {
            button2.setBackground(Color.WHITE)
            button2.setForeground(Color.BLACK)
        }
    })
    button2.setOpaque(true)
    button2.setBorderPainted(false)
    button2.setFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))

    if (inspectorUpdateSelection) {
        button2.setBackground(Color.GRAY)
        button2.setForeground(Color.BLACK)
    } else {
        button2.setBackground(Color.WHITE)
        button2.setForeground(Color.BLACK)
    }


    buttonPanel.add(button1)
    if(visibleInspectors.size() == 0) {
        buttonPanel.add(button2)
    }

    buttonPanel.addMouseListener(sharedMouseListener)
    button1.addMouseListener(sharedMouseListener)
    button2.addMouseListener(sharedMouseListener)

    /////////////////////////////////////////////////////////





    ////////////////// Ancestors panel /////////////////////

//    DefaultListModel<NodeModel> ancestorLineModel = new DefaultListModel<>()
//
//    if(reverseAncestorsList) {
//        nodeNotProxy.getPathToRoot().reverse().each {
//            ancestorLineModel.addElement(it)
//        }
//    }
//    else{
//        nodeNotProxy.getPathToRoot().each {
//            ancestorLineModel.addElement(it)
//        }
//    }
//    ancestorLineModel.removeElement(nodeNotProxy)

    JList<NodeModel> ancestorsLineList = new JList<>(ancestorsOfCurrentNode)
    commonJListsConfigs(ancestorsLineList, ancestorsOfCurrentNode, inspectorPanel)

    if (rtlOrientation) {
        ancestorsLineList.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        ancestorsLineList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }

//    TitledBorder titledBorderAncestors = BorderFactory.createTitledBorder("Ancestors")
//    titledBorderAncestors.setTitleJustification(TitledBorder.LEFT)
//    titledBorderAncestors.setTitleFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))
//    ancestorsLineList.setBorder(titledBorderAncestors)

    JScrollPane scrollPaneAncestorsLineList = new JScrollPane(ancestorsLineList){
        protected void paintComponent(Graphics g)
        {
            g.setColor( getBackground() )
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
        configureScrollPaneForRTL(scrollPaneAncestorsLineList)
    ancestorsLineList.setSize(ancestorsLineList.getPreferredSize())
    ancestorsLineList.revalidate()
    Dimension listPreferredSize = ancestorsLineList.getPreferredSize()



    int maxHeight

    if(selectedTagsInPanel.size() > 0 && visibleInspectors.size() == 0) {
//        maxHeight = (int) (mapViewWindowForSizeReferences.height / 1.3) -additionalInspectorDistanceToTheBottomOfTheScreen
        maxHeight = (int) mapViewWindowForSizeReferences.height -additionalInspectorDistanceToTheBottomOfTheScreen
    }
    else {
        maxHeight = (int) mapViewWindowForSizeReferences.height -additionalInspectorDistanceToTheBottomOfTheScreen
    }

    int finalHeight = Math.min(listPreferredSize.height, maxHeight)
    scrollPaneAncestorsLineList.setPreferredSize(new Dimension(200, finalHeight + paddingBeforeHorizontalScrollBar))


    ancestorsLineList.addMouseListener(sharedMouseListener)
    scrollPaneAncestorsLineList.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    scrollPaneAncestorsLineList.getHorizontalScrollBar().addMouseListener(sharedMouseListener)
    addMouseListenerToScrollBarButtons(scrollPaneAncestorsLineList.getVerticalScrollBar())
    addMouseListenerToScrollBarButtons(scrollPaneAncestorsLineList.getHorizontalScrollBar())


    /////////////////////////////////////////////////////////





    ////////////////// Siblings panel /////////////////////



    DefaultListModel<NodeModel> siblingsModel = new DefaultListModel<>()
    if(nodeNotProxy.isRoot()) {}
    else {
        nodeNotProxy.parent.getChildren().each {
            siblingsModel.addElement(it)
        }
    }

    JList<NodeModel> siblingsList = new JList<>(siblingsModel)
    commonJListsConfigs(siblingsList, siblingsModel, inspectorPanel)

//    TitledBorder titledBorderSiblings = BorderFactory.createTitledBorder("Siblings")
//    titledBorderSiblings.setTitleJustification(TitledBorder.LEFT)
//    titledBorderSiblings.setTitleFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))
//    siblingsList.setBorder(titledBorderSiblings)

    JScrollPane scrollPanelSiblingsList = new JScrollPane(siblingsList)
        configureScrollPaneForRTL(scrollPanelSiblingsList)
    siblingsList.setSize(siblingsList.getPreferredSize())
    siblingsList.revalidate()
    Dimension listPreferredSize2 = siblingsList.getPreferredSize()
    int maxHeight2 = maxHeight
    int finalHeight2 = Math.min(listPreferredSize2.height, maxHeight2)

    scrollPanelSiblingsList.setPreferredSize(new Dimension(200, finalHeight2 + paddingBeforeHorizontalScrollBar))

    siblingsList.addMouseListener(sharedMouseListener)
    scrollPanelSiblingsList.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    scrollPanelSiblingsList.getHorizontalScrollBar().addMouseListener(sharedMouseListener)
    addMouseListenerToScrollBarButtons(scrollPanelSiblingsList.getVerticalScrollBar())
    addMouseListenerToScrollBarButtons(scrollPanelSiblingsList.getHorizontalScrollBar())

    int selectedIndex = siblingsModel.indexOf(nodeNotProxy)
    if (selectedIndex >= 0) {
        SwingUtilities.invokeLater {
            siblingsList.ensureIndexIsVisible(selectedIndex)
        }
    }


    //////////////////////////////////////////////////





    //////////////////   Children panel  //////////////////



    DefaultListModel<NodeModel> childrenModel = new DefaultListModel<>()
    nodeNotProxy.children.each {
        childrenModel.addElement(it)
    }

    JList<NodeModel> childrenList = new JList<>(childrenModel)
    commonJListsConfigs(childrenList, childrenModel, inspectorPanel)

//    TitledBorder titledBorderChildren = BorderFactory.createTitledBorder("Children")
//    titledBorderChildren.setTitleJustification(TitledBorder.LEFT)
//    titledBorderChildren.setTitleFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))
//    childrenList.setBorder(titledBorderChildren)

    JScrollPane scrollPaneChildrenList = new JScrollPane(childrenList)

    configureScrollPaneForRTL(scrollPaneChildrenList)
    childrenList.setSize(childrenList.getPreferredSize())
    childrenList.revalidate()
    Dimension listPreferredSize3 = childrenList.getPreferredSize()
    int maxHeight3 = maxHeight
    int finalHeight3 = Math.min(listPreferredSize3.height, maxHeight3)
    scrollPaneChildrenList.setPreferredSize(new Dimension(200, finalHeight3 + paddingBeforeHorizontalScrollBar))


    childrenList.addMouseListener(sharedMouseListener)
    scrollPaneChildrenList.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    scrollPaneChildrenList.getHorizontalScrollBar().addMouseListener(sharedMouseListener)
    addMouseListenerToScrollBarButtons(scrollPaneChildrenList.getVerticalScrollBar())
    addMouseListenerToScrollBarButtons(scrollPaneChildrenList.getHorizontalScrollBar())


    ////////////////////////////////////////////////////



    /////////////// Tags in Node Panel //////////////////////

    DefaultListModel<Tags> tagsInNodeModel = new DefaultListModel<>()

    inspectorPanel.putClientProperty("tagsInNodeAccessor", tagsInNodeModel)



    if(visibleInspectors.size() == 0) {
        iconController.getTags(nodeNotProxy).each {
            tagsInNodeModel.addElement(it)
        }
    }


//    iconController.getTags(nodeNotProxy).each {
//        tagsInNodeModel.addElement(it)
//    }


    JList<NodeModel> tagsInNode = new JList<>(tagsInNodeModel)
    tagsInNode.setLayoutOrientation(JList.HORIZONTAL_WRAP)
    tagsInNode.setVisibleRowCount(1)

    commonTagsJListsConfigs(tagsInNode, tagsInNodeModel, inspectorPanel)

//    TitledBorder titledBorderTagsInNode = BorderFactory.createTitledBorder("Tags in Node")
//    titledBorderTagsInNode.setTitleJustification(TitledBorder.LEFT)
//    titledBorderTagsInNode.setTitleFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))
//    tagsInNode.setBorder(titledBorderTagsInNode)

    JScrollPane scrollPaneTagsInNodeList = new JScrollPane(tagsInNode)
//    JPanel scrollPaneTagsInNodeList = new JPanel(new BorderLayout())
//    scrollPaneTagsInNodeList.add(tagsInNode, BorderLayout.PAGE_START)


    configureScrollPaneForRTL(scrollPaneTagsInNodeList)
    tagsInNode.setSize(tagsInNode.getPreferredSize())
    tagsInNode.revalidate()
    tagsInNode.repaint()
    Dimension listPreferredSize7 = tagsInNode.getPreferredSize()
    int maxHeight7 = maxHeight
    int finalHeight7= Math.min(listPreferredSize7.height, maxHeight7)
    scrollPaneTagsInNodeList.setPreferredSize(new Dimension(200, finalHeight7 + paddingBeforeHorizontalScrollBar))


    tagsInNode.addMouseListener(sharedMouseListener)
    scrollPaneTagsInNodeList.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    scrollPaneTagsInNodeList.getHorizontalScrollBar().addMouseListener(sharedMouseListener)
    addMouseListenerToScrollBarButtons(scrollPaneTagsInNodeList.getVerticalScrollBar())
    addMouseListenerToScrollBarButtons(scrollPaneTagsInNodeList.getHorizontalScrollBar())


    ////////////////////////////////////////////////////



    /////////////// Tags Selection panel //////////////////////


    selectedTagsInPanelModel.clear()

    JList<Tags> tagsSelectedList

    if(selectedTagsInPanel.size() == 0) {
        tagsSelectedList = new JList<>(hoveredTagModel)
        commonTagsJListsConfigs(tagsSelectedList, hoveredTagModel, inspectorPanel)
    }

    else {
        selectedTagsInPanel.each {
            selectedTagsInPanelModel.addElement(it)
        }
        tagsSelectedList = new JList<>(selectedTagsInPanelModel)
        commonTagsJListsConfigs(tagsSelectedList, selectedTagsInPanelModel, inspectorPanel)
    }



    tagsSelectedList.setLayoutOrientation(JList.HORIZONTAL_WRAP)
    tagsSelectedList.setVisibleRowCount(1)


    TitledBorder titledBorderTagsSelection = BorderFactory.createTitledBorder("Tags Selection")
    titledBorderTagsSelection.setTitleJustification(TitledBorder.LEFT)
    titledBorderTagsSelection.setTitleFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))
    tagsSelectedList.setBorder(titledBorderTagsSelection)

    JScrollPane scrollPaneTagsSelectionList = new JScrollPane(tagsSelectedList)


    configureScrollPaneForRTL(scrollPaneTagsSelectionList)
    tagsSelectedList.setSize(tagsSelectedList.getPreferredSize())
    tagsSelectedList.revalidate()
    Dimension listPreferredSize4 = tagsSelectedList.getPreferredSize()
    int maxHeight4 = maxHeight
    int finalHeight4 = Math.min(listPreferredSize4.height, maxHeight4)
    scrollPaneTagsSelectionList.setPreferredSize(new Dimension(200, finalHeight4 + paddingBeforeHorizontalScrollBar))


    tagsSelectedList.addMouseListener(sharedMouseListener)
    scrollPaneTagsSelectionList.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    scrollPaneTagsSelectionList.getHorizontalScrollBar().addMouseListener(sharedMouseListener)
    addMouseListenerToScrollBarButtons(scrollPaneTagsSelectionList.getVerticalScrollBar())
    addMouseListenerToScrollBarButtons(scrollPaneTagsSelectionList.getHorizontalScrollBar())





    ////////////////////////////////////////////////////



    /////////////// Panel: Nodes That Contain Any Tag In Tags Selection //////////////////////



    if(tagsNeedUpdate) {
        nodesThatContainAnyTagInTagsSelectionModel.clear()
        c.viewRoot.findAll().each {
            if (selectedTagsInPanel.size() != 0 && iconController.getTags(it.delegate).containsAll(selectedTagsInPanel)) {
                nodesThatContainAnyTagInTagsSelectionModel.addElement(it.delegate)
            }
        }
    }

    JList<NodeModel> nodesThatContainAnyTagInTagsSelection

    if(selectedTagsInPanel.size() == 0) {
        nodesThatContainAnyTagInTagsSelection = new JList<>(nodesThatContainHoveredTagModel)
        commonJListsConfigs(nodesThatContainAnyTagInTagsSelection, nodesThatContainHoveredTagModel, inspectorPanel)
    }

    else {
        nodesThatContainAnyTagInTagsSelection = new JList<>(nodesThatContainAnyTagInTagsSelectionModel)
        commonJListsConfigs(nodesThatContainAnyTagInTagsSelection, nodesThatContainAnyTagInTagsSelectionModel, inspectorPanel)

    }


    TitledBorder titledBorderNodesThatContainAnyTagInTagsSelection = BorderFactory.createTitledBorder("Nodes with the Tags")
    titledBorderNodesThatContainAnyTagInTagsSelection.setTitleJustification(TitledBorder.LEFT)
    titledBorderNodesThatContainAnyTagInTagsSelection.setTitleFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))
    nodesThatContainAnyTagInTagsSelection.setBorder(titledBorderNodesThatContainAnyTagInTagsSelection)

    JScrollPane scrollPaneNodesThatContainAnyTagInTagsSelection = new JScrollPane(nodesThatContainAnyTagInTagsSelection)


    configureScrollPaneForRTL(scrollPaneNodesThatContainAnyTagInTagsSelection)
    nodesThatContainAnyTagInTagsSelection.setSize(nodesThatContainAnyTagInTagsSelection.getPreferredSize())
    nodesThatContainAnyTagInTagsSelection.revalidate()
    Dimension listPreferredSize5 = nodesThatContainAnyTagInTagsSelection.getPreferredSize()
    int maxHeight5 = maxHeight
    int finalHeight5 = Math.min(listPreferredSize5.height, maxHeight5)
    scrollPaneNodesThatContainAnyTagInTagsSelection.setPreferredSize(new Dimension(200, finalHeight5 + paddingBeforeHorizontalScrollBar))


    nodesThatContainAnyTagInTagsSelection.addMouseListener(sharedMouseListener)
    scrollPaneNodesThatContainAnyTagInTagsSelection.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    scrollPaneNodesThatContainAnyTagInTagsSelection.getHorizontalScrollBar().addMouseListener(sharedMouseListener)
    addMouseListenerToScrollBarButtons(scrollPaneNodesThatContainAnyTagInTagsSelection.getVerticalScrollBar())
    addMouseListenerToScrollBarButtons(scrollPaneNodesThatContainAnyTagInTagsSelection.getHorizontalScrollBar())


    ////////////////////////////////////////////////////





    /////////////// ConnectorsIn panel //////////////////////



    ////////////////////////////////////////////////////




    /////////////// ConnectorsOut panel //////////////////////


    ////////////////////////////////////////////////////



    //////////// add the panels /////////////



    JPanel columnsPanel = new JPanel(){
        protected void paintComponent(Graphics g)
        {
            g.setColor( getBackground() )
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
//    columnsPanel.setLayout(new GridLayout())
    columnsPanel.setLayout(new BoxLayout(columnsPanel, BoxLayout.X_AXIS))

    columnsPanel.setBackground( new Color(0, 0, 0, 0) )

    int ammountOfPannelsInInspector = 1

    if(ancestorsOfCurrentNode.getSize() > 0 && visibleInspectors.size() == 0 && showAncestorsOnFirstInspector) {
        columnsPanel.add(scrollPaneAncestorsLineList)
    }


    if(visibleInspectors.size() == 0) {
        columnsPanel.add(scrollPanelSiblingsList)
        ammountOfPannelsInInspector++
    }
    else{
//        JPanel line = new JPanel()
//        line.setBackground(Color.GRAY)
//        line.setPreferredSize(new Dimension(10, 3))
//        line.setBorder(BorderFactory.createLineBorder(Color.RED, 2))
//
//        columnsPanel.add(line)
    }
//    if(childrenModel.getSize() > 0) {
//        columnsPanel.add(scrollPaneChildrenList)
//        ammountOfPannelsInInspector++
//    }
//    else{
////        JPanel line2 = new JPanel()
////        line2.setBackground(Color.GRAY)
////        line2.setPreferredSize(new Dimension(10, 3))
////
////        columnsPanel.add(line2)
//    }

    if(visibleInspectors.size() != 0) {
        columnsPanel.add(scrollPaneChildrenList)
        ammountOfPannelsInInspector++

    }



    JPanel verticalStackPanel = new JPanel()
    verticalStackPanel.setLayout(new BoxLayout(verticalStackPanel, BoxLayout.Y_AXIS))
//    verticalStackPanel.setLayout(new BoxLayout(verticalStackPanel, rtlOrientation ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS))
    verticalStackPanel.setBackground( Color.BLACK)

    verticalStackPanel.add(buttonPanel, BorderLayout.NORTH)
    verticalStackPanel.add(textScrollPane, BorderLayout.NORTH)





    verticalStackPanel.add(scrollPaneTagsInNodeList, BorderLayout.NORTH)

//    if(iconController.getTags(nodeNotProxy).size() > 0) {
//
//        verticalStackPanel.add(scrollPaneTagsInNodeList, BorderLayout.NORTH)
//    }


    verticalStackPanel.add(columnsPanel, BorderLayout.NORTH)

    if((hoveredTagModel.size() > 0 || selectedTagsInPanel.size() > 0) && visibleInspectors.size() == 0) {
        verticalStackPanel.add(Box.createVerticalStrut(10))

        verticalStackPanel.add(scrollPaneTagsSelectionList, BorderLayout.SOUTH)
        verticalStackPanel.add(scrollPaneNodesThatContainAnyTagInTagsSelection, BorderLayout.SOUTH)
    }


    inspectorPanel.add(verticalStackPanel, BorderLayout.CENTER)

    verticalStackPanel.revalidate()

    inspectorPanel.setSize(calculateInspectorWidth(ammountOfPannelsInInspector), (int) Math.min(mapViewWindowForSizeReferences.height, inspectorPanel.getPreferredSize().height))

    inspectorPanel.revalidate()
    inspectorPanel.repaint()


    /////////////////////////////////////////


    setInspectorLocation(inspectorPanel, sourcePanel)
    inspectorPanel.setVisible(true)
    parentPanel.add(inspectorPanel)
    parentPanel.setComponentZOrder(inspectorPanel, 0)


//    parentPanel.revalidate()
//    parentPanel.repaint()

    return inspectorPanel
}



void hideInspectorPanelIfNeeded() {
    if (shouldFreeze()) {return}
    if (!mouseOverList) {

        visibleInspectors.each{
            if(!inspectorUpdateSelection) {
                it.setVisible(false)
            }
            else{
                if(it != visibleInspectors[0] && it != visibleInspectors[1]) {
                    it.setVisible(false)
                }
            }
        }

        if(!inspectorUpdateSelection) {
            visibleInspectors.clear()
        }
        else {
            visibleInspectors.removeAll { it != visibleInspectors[0] && it != visibleInspectors[1]}
            if(visibleInspectors.size() != 0) {
                setInspectorLocation(visibleInspectors[0], masterPanel)
                if(visibleInspectors.size() > 1) {
                    setInspectorLocation(visibleInspectors[1], visibleInspectors[0])
                }
            }
        }

        if(inspectorUpdateSelection && visibleInspectors.size() > 0) {
            visibleInspectors[0].setVisible(true)
            if(visibleInspectors.size() > 1) {
                visibleInspectors[1].setVisible(true)
            }
        }

        retractMasterPanel()

//        parentPanel.revalidate()
//        parentPanel.repaint()

        if(visibleInspectors.size() != 0 && inspectorUpdateSelection) {
            setInspectorLocation(visibleInspectors[0], masterPanel)
            if(visibleInspectors.size() > 1) {
                setInspectorLocation(visibleInspectors[1], visibleInspectors[0])
            }
        }

        return
    }
}

void configureLabelForNode(JComponent component, NodeModel nodeNotProxy, JPanel sourcePanel) {
    Color backgroundColor = NodeStyleController.getController().getBackgroundColor(nodeNotProxy, StyleOption.FOR_UNSELECTED_NODE)
    Color fontColor = NodeStyleController.getController().getColor(nodeNotProxy, StyleOption.FOR_UNSELECTED_NODE)
    String fontColorHex
    if(fontColor != null) {
         fontColorHex = String.format("#%02x%02x%02x", fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue())
    } else {  fontColorHex = "#000000"}

    fontForItems = new Font(panelTextFontName, fontForListItens, panelTextFontSize)

    if(backgroundColor == null) {backgroundColor = Color.WHITE}
    else {component.setBackground(backgroundColor)}

    if(fontColor == null) {fontColor = Color.BLACK}
    else(component.setForeground(fontColor))

    component.setFont(fontForItems)

    String textWithHighlight
    def searchedTerms = searchText

    currentMapView = Controller.currentController.MapViewManager.mapView

    if (component instanceof JLabel) {
        JLabel label = (JLabel) component

        String prefix = ""

        if (currentMapView.currentRootParentView != null) {
            if (nodeNotProxy.getPathToRoot().find { it == currentMapView.mapSelection.selectionRoot } == null) {
                prefix += "⚠"
            }
        }

        if (pinnedItems.contains(nodeNotProxy)) {
            prefix += "📌"
        }

        if (!nodeNotProxy.isLeaf() && sourcePanel != breadcrumbPanel) {
            prefix += "○ "
        }

        NodeModel storedNode = (NodeModel) sourcePanel.getClientProperty("referenceNode")

        if (currentlySelectedNode == nodeNotProxy) {
            label.setBorder(BorderFactory.createLineBorder(Color.RED, 4))
        }

        else if (visibleInspectors.any{ it.getClientProperty("referenceNode") == nodeNotProxy }) {
            label.setBorder(BorderFactory.createLineBorder(( new Color(160, 32, 240, 255) ), 4))
        }


        String labelText = prefix + nodeNotProxy.text


        if (quickSearchResults.contains(nodeNotProxy)) {
            textWithHighlight = highlightSearchTerms(labelText, searchedTerms)
        } else {
            textWithHighlight = labelText
        }

        label.setText(textWithHighlight)

        label.revalidate()
        label.repaint()




        return

    }
    else if (component instanceof JTextPane) {
        JTextPane textPane = (JTextPane) component
        if (quickSearchResults.contains(nodeNotProxy)) {
            textWithHighlight = highlightSearchTerms(nodeNotProxy.text, searchedTerms)
        }
        else {
            textWithHighlight = nodeNotProxy.text
        }

        String htmlContent = "<html><head>" +
                "<style type='text/css'>body { font-family: $panelTextFontName, sans-serif; font-size: $panelTextFontSize px; color: $fontColorHex; }</style>" +
                "</head><body>" +
                textWithHighlight +
                "</body></html>";

        textPane.setText(htmlContent)


        textPane.setEditable(false)
        SwingUtilities.invokeLater {
            JScrollPane scrollPane = (JScrollPane) sourcePanel.getClientProperty("textScrollPane")

            adjustFontSizeToFitText(component, scrollPane, nodeNotProxy, fontColor)

        }
    }

    component.setOpaque(true)

}


String highlightSearchTerms(String text, String searchTerms) {
    String highlightedText = text
    String[] terms = searchTerms.split("\\s+")

    for (String term : terms) {
        if (term.isEmpty()) continue
        highlightedText = highlightedText.replaceAll("(?i)(${Pattern.quote(term)})", "<span style='background-color:#00ff00;'>${'$'}1</span>")

    }

    return "<html>" + highlightedText + "</html>"
}



//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Lists configs ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

void commonJListsConfigs(JList<NodeModel> theJlist, DefaultListModel<NodeModel> theListModel, JPanel thePanelPanel) {
    configureDragAndDrop(theJlist)
    configureListFont(theJlist)
    configureListSelection(theJlist)
    configureListContextMenu(theJlist)
    configureListCellRenderer(theJlist, thePanelPanel)
    configureMouseMotionListener(theJlist, theListModel, thePanelPanel)
    configureMouseExitListener(theJlist)

    if (rtlOrientation) {
        theJlist.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        theJlist.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }
}

void configureListFont(JList<NodeModel> list) {
    list.setFont(fontForItems)
}

void configureListSelection(JList<NodeModel> list) {
    list.addListSelectionListener({ e ->
        if (!e.getValueIsAdjusting()) {
            int selectedItemIndex = list.getSelectedIndex()
            if (selectedItemIndex != -1) {
                NodeModel selectedItemNode = list.getModel().getElementAt(selectedItemIndex)
                Controller.currentController.mapViewManager.mapView.getMapSelection().selectAsTheOnlyOneSelected(selectedItemNode)
            }
        }
    } as ListSelectionListener)
}

void configureListContextMenu(JList<NodeModel> list) {
    list.addMouseListener(new MouseAdapter() {
        @Override
        void mousePressed(MouseEvent e) {
            if (showOnlyBreadcrumbs) { return }
            if (SwingUtilities.isRightMouseButton(e)) {
                int index = list.locationToIndex(e.getPoint())
                if (index >= 0) {
                    NodeModel selectedItem = list.getModel().getElementAt(index)

                    JPopupMenu popupMenu = new JPopupMenu()
                    JMenuItem menuItem

                    if (pinnedItems.contains(selectedItem)) {
                        menuItem = new JMenuItem("Unpin")
                        menuItem.addActionListener({
                            pinnedItems.removeElement(selectedItem)
                            saveSettings()
//                            updateAllGUIs()
                        })
                    } else {
                        menuItem = new JMenuItem("Pin")
                        menuItem.addActionListener({
                            pinnedItems.addElement(selectedItem)
                            saveSettings()
//                            updateAllGUIs()
                        })
                    }

                    JMenuItem menuItem2
                    menuItem2 = new JMenuItem("Open in new View")
                    menuItem2.addActionListener({
                        menuUtils.executeMenuItems([ 'NewMapViewAction' ])
                        SwingUtilities.invokeLater {
                            Controller.currentController.mapViewManager.mapView.getMapSelection().selectAsTheOnlyOneSelected(selectedItem)
                        }
                    })

                    menuItem.addMouseListener(sharedMouseListener)
                    popupMenu.add(menuItem)
                    popupMenu.add(menuItem2)
                    popupMenu.show(e.getComponent(), e.getX(), e.getY())
                }
            }
        }
    })
}


void configureDragAndDrop(JList<NodeModel> list) {
    DragSource dragSource = DragSource.getDefaultDragSource()
    dragSource.createDefaultDragGestureRecognizer(list, DnDConstants.ACTION_MOVE, new DragGestureListener() {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            if (!list.isSelectionEmpty()) {
                int index = list.getSelectedIndex()
                NodeModel selectedNodeModel = list.getModel().getElementAt(index)

                List<NodeModel> nodeToMove = []
                nodeToMove.add(selectedNodeModel)

                    IMapSelection mapSelectionForTransfer = new IMapSelection() {
                        @Override
                        void centerNode(NodeModel nodeModel) {

                        }

                        @Override
                        void centerNodeSlowly(NodeModel nodeModel) {

                        }

                        @Override
                        void moveNodeTo(NodeModel nodeModel, IMapSelection.NodePosition nodePosition) {

                        }

                        @Override
                        void slowlyMoveNodeTo(NodeModel nodeModel, IMapSelection.NodePosition nodePosition) {

                        }

                        @Override
                        NodeModel getSelected() {
                            return null
                        }

                        @Override
                        NodeModel getSelectionRoot() {
                            return null
                        }

                        @Override
                        NodeModel getSearchRoot() {
                            return null
                        }

                        @Override
                        NodeModel getEffectiveSearchRoot() {
                            return null
                        }

                        @Override
                        Set<NodeModel> getSelection() {
                            return null
                        }

                        @Override
                        List<String> getOrderedSelectionIds() {
                            return null
                        }

                        @Override
                        List<NodeModel> getOrderedSelection() {
                            return null
                        }

                        @Override
                        List<NodeModel> getSortedSelection(boolean b) {
                            return nodeToMove
                        }

                        @Override
                        boolean isSelected(NodeModel nodeModel) {
                            return false
                        }

                        @Override
                        void preserveRootNodeLocationOnScreen() {

                        }

                        @Override
                        void preserveSelectedNodeLocationOnScreen() {

                        }

                        @Override
                        void preserveNodeLocationOnScreen(NodeModel nodeModel) {

                        }

                        @Override
                        void preserveNodeLocationOnScreen(NodeModel nodeModel, float v, float v1) {

                        }

                        @Override
                        void scrollNodeTreeToVisible(NodeModel nodeModel) {

                        }

                        @Override
                        void makeTheSelected(NodeModel nodeModel) {

                        }

                        @Override
                        void makeTheSearchRoot(final NodeModel node) {

                        }

                        @Override
                        void scrollNodeToVisible(NodeModel nodeModel) {

                        }

                        @Override
                        void selectAsTheOnlyOneSelected(NodeModel nodeModel) {

                        }

                        @Override
                        void selectBranch(NodeModel nodeModel, boolean b) {

                        }

                        @Override
                        void selectContinuous(NodeModel nodeModel) {

                        }

                        @Override
                        void selectRoot() {

                        }


                        @Override
                        int size() {
                            return 0
                        }

                        @Override
                        void toggleSelected(NodeModel nodeModel) {

                        }

                        @Override
                        void replaceSelection(NodeModel[] nodeModels) {

                        }

                        @Override
                        Filter getFilter() {
                            return null
                        }

                        @Override
                        void setFilter(Filter filter) {

                        }

                        @Override
                        boolean isFolded(NodeModel nodeModel) {
                            return false
                        }

                        @Override
                        boolean isVisible(NodeModel nodeModel) {
                            return false
                        }
                    }

                Transferable transferable = MapClipboardController.getController().copy(mapSelectionForTransfer)
                ((MindMapNodesSelection) transferable).setDropAction(2)

                dragSource.startDrag(dge, DragSource.DefaultMoveDrop, transferable, new DragSourceAdapter() {})
            }
        }
    })

    new DropTarget(list, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
        @Override
        public void drop(DropTargetDropEvent dtde) {
            try {
                Point dropLocation = dtde.getLocation()
                int index = list.locationToIndex(dropLocation)
                ListModel<NodeModel> model = list.getModel()
                NodeModel targetNodeModel = null
                if (index >= 0 && index < model.getSize()) {
                    targetNodeModel = model.getElementAt(index)
                }

                Transferable transferableNode = dtde.getTransferable()
                DataFlavor freeplaneNodesFlavor = new DataFlavor("application/freeplane-nodes; class=java.util.Collection", "application/freeplane-nodes")
                if (transferableNode.isDataFlavorSupported(freeplaneNodesFlavor)) {
                    Object data = transferableNode.getTransferData(freeplaneNodesFlavor)
                    Collection<NodeModel> nodeModels = null
                    if (data instanceof Collection<?>) {
                        Collection<?> collection = (Collection<?>) data
                        boolean allNodes = collection.stream().allMatch(element -> element instanceof NodeModel)
                        if (allNodes) {
                            nodeModels = (Collection<NodeModel>) collection
                        } else {
                        }
                    }
                    if (targetNodeModel != null && nodeModels != null) {
                        List<NodeModel> nodesToMove = new ArrayList<>(nodeModels)

                        if (nodesToMove[0] == targetNodeModel) {
                            Controller.currentController.mapViewManager.mapView.getMapSelection().selectAsTheOnlyOneSelected(targetNodeModel)
                            return
                        }

                        final MMapController mapController = (MMapController) Controller.getCurrentModeController().getMapController()

                        mapController.moveNodesAsChildren(nodesToMove, targetNodeModel)
                    }
                    return
                }
                if (dtde.isDataFlavorSupported(NodeModelTransferable.NODE_MODEL_FLAVOR)) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE)
                    Transferable transferable = dtde.getTransferable()

                    NodeModel sourceNodeModel = (NodeModel) transferable.getTransferData(NodeModelTransferable.NODE_MODEL_FLAVOR)

                    if (targetNodeModel != null) {
                        List<NodeModel> nodesToMove = Arrays.asList(sourceNodeModel)
                        final MMapController mapController = (MMapController) Controller.getCurrentModeController().getMapController()
                        mapController.moveNodesAsChildren(nodesToMove, targetNodeModel)
                    }

                    dtde.dropComplete(true)
                } else {
                    dtde.rejectDrop()
                }
            } catch (Exception e) {
                e.printStackTrace()
                dtde.rejectDrop()
            }
        }
    })
}


void configureListCellRenderer(JList<NodeModel> listParameter, JPanel sourcePanel) {
    listParameter.setCellRenderer(new DefaultListCellRenderer() {


        @Override
        public Component getListCellRendererComponent(JList<?> list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list,
                    value,
                    index,
                    isSelected,
                    cellHasFocus)


            if (value instanceof NodeModel) {
                NodeModel currentNode = (NodeModel) value
                configureLabelForNode(label, currentNode, sourcePanel)

                if (sourcePanel == breadcrumbPanel && index > 0) {
                    String oldText = label.getText()
                    label.setText(" > " + oldText)


                    String text = value.toString()
                    FontMetrics fm = label.getFontMetrics(label.getFont())

                    // Calcular a largura disponível para o texto
                    int availableWidth = list.fixedCellWidth - label.insets.left - label.insets.right

                    // Medir a largura do texto
                    int textWidth = fm.stringWidth(text)

                    if (textWidth > availableWidth) {
                        String truncatedText = truncateText(label, text, fm, availableWidth)
                        label.text = truncatedText
                    } else {
                        label.text = text
                    }

                }
            }

            if (isSelected) {
                label.setBackground(list.getSelectionBackground())
                label.setForeground(list.getSelectionForeground())
            }
            return label
        }
    })
}



void configureMouseMotionListener(JList<NodeModel> list, DefaultListModel<NodeModel> listModel, JPanel sourcePanel) {
    list.addMouseMotionListener(new MouseAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
            if (showOnlyBreadcrumbs) {
                return
            }

            lastMouseModifiers = e.getModifiersEx()

            if (shouldFreeze()) {return}

            hoverTimer.stop()
            currentList = list
            currentListModel = listModel
            currentSourcePanel = sourcePanel
            lastMouseLocation = e.getPoint()
            mouseOverList = true
            hoverTimer.restart()


            if(panelsInMasterPanels.contains(currentSourcePanel)) {
                expandMasterPanel()

                if(visibleInspectors.size() != 0) {
                    setInspectorLocation(visibleInspectors[0], masterPanel)
                    if(visibleInspectors.size() > 1) {
                        setInspectorLocation(visibleInspectors[1], visibleInspectors[0])
                    }
                }
            }
        }
    })
}

void configureMouseExitListener(JList<NodeModel> list) {
    list.addMouseListener(new MouseAdapter() {
        @Override
        void mouseExited(MouseEvent e) {
            if (showOnlyBreadcrumbs) { return }
            mouseOverList = false
            hideInspectorTimer.restart()
        }
    })
}


void commonTagsJListsConfigs(JList<String> jList, DefaultListModel<String> theListModel, JPanel thePanelPanel) {
//    configureDragAndDrop(theJlist)
    configureListFont(jList)

//    configureListSelection(theJlist)

    jList.addMouseListener(new MouseAdapter() {
        @Override
        void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                int index = jList.locationToIndex(e.getPoint())
                if (index != -1) {
                    Tag tagSelected = jList.getModel().getElementAt(index)

                    List<Tag> tagToInsert = new ArrayList<Tag>()
                    tagToInsert.add(tagSelected)
                    iconController.insertTagsIntoSelectedNodes(tagToInsert)
                    selectedTagsInPanel.clear()

//                    tagsNeedUpdate = true
//
//                    updateAllGUIs()
                    Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
                    Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()


                }
            } else if (SwingUtilities.isRightMouseButton(e)) {
                int selectedItemIndex = jList.locationToIndex(e.getPoint())
                if (selectedItemIndex >= 0) {
                    jList.setSelectedIndex(selectedItemIndex)
                    Tag tagSelected = jList.getModel().getElementAt(selectedItemIndex)
                    Set<Tag> tagToRemove = new HashSet<Tag>()
                    tagToRemove.add(tagSelected)

                    JPopupMenu popupMenu = new JPopupMenu()
                    JMenuItem menuItemRemove = new JMenuItem("Remove from selected nodes")
                    menuItemRemove.addActionListener({
                        iconController.removeSelectedTagsFromSelectedNodes(tagToRemove)

//                        tagsNeedUpdate = true
//
//                        updateAllGUIs()
                        Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
                        Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
                    })

                    JMenuItem menuItemAddToSelection = new JMenuItem("Add to selection of tags")
                    menuItemAddToSelection.addActionListener({
                        if(selectedTagsInPanel.contains(tagSelected)) {
                            return
                        }
                        selectedTagsInPanel.add(tagSelected)

//                        tagsNeedUpdate = true

                        refreshHighlighterCacheTags()

                        cleanAndCreateInspectors(currentlySelectedNode, panelsInMasterPanels[0])

                    })

                    JMenuItem menuItemRemoveFromSelection = new JMenuItem("Remove from selection of tags")
                    menuItemRemoveFromSelection.addActionListener({
                        selectedTagsInPanel.removeElement(tagSelected)
                        refreshHighlighterCacheTags()

//                        tagsNeedUpdate = true

                        cleanAndCreateInspectors(currentlySelectedNode, panelsInMasterPanels[0])

                    })

                    JMenuItem menuItemClearSelection = new JMenuItem("Clear selection of tags")
                    menuItemClearSelection.addActionListener({
                        selectedTagsInPanel.clear()
                        refreshHighlighterCacheTags()

//                        tagsNeedUpdate = true

                        cleanAndCreateInspectors(currentlySelectedNode, panelsInMasterPanels[0])

                    })

                    menuItemRemove.addMouseListener(sharedMouseListener)
                    popupMenu.add(menuItemRemove)

                    popupMenu.addSeparator()

                    menuItemAddToSelection.addMouseListener(sharedMouseListener)
                    popupMenu.add(menuItemAddToSelection)

                    menuItemRemoveFromSelection.addMouseListener(sharedMouseListener)
                    popupMenu.add(menuItemRemoveFromSelection)

                    menuItemClearSelection.addMouseListener(sharedMouseListener)
                    popupMenu.add(menuItemClearSelection)

                    popupMenu.show(e.getComponent(), e.getX(), e.getY())
                }
            }
        }
    })

//    configureListContextMenu(theJlist)

    if (rtlOrientation) {
        jList.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        jList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }


//    configureListCellRenderer(theJlist, thePanelPanel)

    jList.setCellRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
            if (value instanceof Tag) {
                Tag currentTag = (Tag) value

//                configureLabelForNode(label, currentNode, tagsPanel)
                Color backgroundColor = currentTag.getColor()
                Color fontColor = UITools.getTextColorForBackground(backgroundColor)
                String hexColor = String.format("#%02x%02x%02x", backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue())
//                String fontColorHex = String.format("#%02x%02x%02x", fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue())

                fontForItems = new Font(panelTextFontName, fontForListItens, panelTextFontSize)

                label.setBackground(backgroundColor)
                label.setForeground(fontColor)
                label.setFont(fontForItems)


                label.setBorder(new RoundedCornerBorder(Color.BLACK, 2, 15))

                if (rtlOrientation) {
                    label.setHorizontalAlignment(SwingConstants.RIGHT)
                } else {
                    label.setHorizontalAlignment(SwingConstants.LEFT)
                }


            }
            if (isSelected) {
                label.setBackground(list.getSelectionBackground())
                label.setForeground(list.getSelectionForeground())

            }
            return label
        }
    })


//    configureMouseMotionListener(theJlist, theListModel, thePanelPanel)

    jList.addMouseMotionListener(new MouseAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
            lastMouseModifiers = e.getModifiersEx()

            if (shouldFreeze()) {return}

            hoverTimer.stop()
            currentList = jList
            currentListModel = theListModel
            currentSourcePanel = thePanelPanel
            lastMouseLocation = e.getPoint()
            mouseOverList = true
            hoverTimer.restart()


            if(panelsInMasterPanels.contains(currentSourcePanel)) {
                expandMasterPanel()

                if(visibleInspectors.size() != 0) {
                    setInspectorLocation(visibleInspectors[0], masterPanel)
                    if(visibleInspectors.size() > 1) {
                        setInspectorLocation(visibleInspectors[1], visibleInspectors[0])
                    }
                }
            }
        }
    })

    configureMouseExitListener(jList) //ok
}

//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Lists configs ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


private void saveSettings() {
    File file = getSettingsFile()

    List<String> pinnedItemsIds = pinnedItems.collect { it.id }
    List<String> recentNodesIds = (0..<history.getSize()).collect { index ->
        history.getElementAt(index).id
    }


    String jsonString = new JsonBuilder([
            pinnedItems: pinnedItemsIds,
            recentSearches: savedSearchCriteria,
            recentNodes: recentNodesIds
    ]).toPrettyString()

    try {
        file.text = jsonString
    } catch (Exception e) {
    }
}



File getSettingsFile(){
    File file = new File(
            c.getUserDirectory().toString()
                    + File.separator
                    + 'utilityPanelsConfig.json'
    )
}


private void loadSettings() {
    File file = getSettingsFile()

    if (!file.exists()) {
        try {
            saveSettings()
        } catch (Exception e) {
            e.printStackTrace()
        }
        return
    }

    try {
        String content = file.text
        def settings = new JsonSlurper().parseText(content)

//        pinnedItems = settings.pinnedItems.collect { id ->
//            Controller.currentController.map.getNodeForID(id)
//        }.findAll { it != null }

        pinnedItems.clear()

        def nodesToAdd2 = settings.pinnedItems.collect { id ->
            Controller.currentController.map.getNodeForID(id)
        }.findAll { it != null }

        nodesToAdd2.each { node ->
            pinnedItems.addElement(node)
        }

        if (settings.recentSearches instanceof List) {
            savedSearchCriteria.clear()
            savedSearchCriteria.addAll(settings.recentSearches)
        }



        history.clear()

        def nodesToAdd = settings.recentNodes.collect { id ->
            Controller.currentController.map.getNodeForID(id)
        }.findAll { it != null }

        nodesToAdd.each { node ->
            history.addElement(node)
        }


    } catch (Exception e) {
        e.printStackTrace()
    }
}




def deleteCurrentListenersFromPreviousExecutions() {
    def listenersToRemove = []

    Controller.currentController.modeController.mapController.nodeSelectionListeners.each { listener ->
        try {
            if (listener.uniqueIdForScript == 999) {
                listenersToRemove << listener
            }
        } catch (Exception ex) {
        }
    }

    listenersToRemove.each { listenerToRemove ->
        Controller.currentController.modeController.mapController.removeNodeSelectionListener(listenerToRemove)
    }

    def listenersToRemove2 = []

    Controller.currentController.modeController.mapController.mapChangeListeners.each { listener ->
        try {
            if (listener.uniqueIdForScript == 999) {
                listenersToRemove2 << listener
            }
        } catch (Exception ex) {
        }
    }

    listenersToRemove2.each { listenerToRemove ->
        Controller.currentController.modeController.mapController.removeMapChangeListener(listenerToRemove)
    }
}

def int calculateRetractedWidthForMasterPanel() {
    width = mapViewWindowForSizeReferences.width / retractedWidthFactorForMasterPanel
    return width
}

def int calculateExpandedWidthForMasterPanel() {
    retractedWidth = calculateRetractedWidthForMasterPanel()
    width = retractedWidth * expandedWidthFactorForMasterPanel
    return width
}

def int calculateInspectorWidth(int ammountOfPannelsInInspector) {
    width = mapViewWindowForSizeReferences.width / widthFactorForInspector
    width = width * ammountOfPannelsInInspector
    return width
}

def setInspectorLocation(JPanel inspectorPanel, JPanel sourcePanel) {
    if (showOnlyBreadcrumbs) { return }
    int x = sourcePanel.getLocation().x + sourcePanel.width

    int y = masterPanel.getLocation().y
    inspectorPanel.setLocation(x, y)
}

def searchNodesRecursively(NodeModel node, String searchText, DefaultListModel<NodeModel> results) {
    String[] terms = searchText.toLowerCase().split("\\s+")

    def termsMatchedInNode = terms.findAll { term ->
        node.text?.toLowerCase().contains(term)
    }

    def remainingTerms = terms - termsMatchedInNode

    if (!termsMatchedInNode.isEmpty() && remainingTerms.every { term -> containsTermInAncestors(node, term) }) {
        results.addElement(node)
    }

    node.children.each { child ->
        searchNodesRecursively(child, searchText, results)
    }
}

def containsTermInAncestors(NodeModel node, String term) {
    node = node.parent
    while (node != null) {
        if (node.text?.toLowerCase().contains(term)) {
            return true
        }
        node = node.parent
    }
    return false
}




def addQuickSearchShortcut(JComboBox searchField) {
    InputMap inputMap = searchField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
    ActionMap actionMap = searchField.getActionMap()

    inputMap.put(keyStrokeToQuickSearch, "focusQuickSearch")
    actionMap.put("focusQuickSearch", new AbstractAction() {
        @Override
        void actionPerformed(ActionEvent e) {
            searchField.requestFocusInWindow()
        }
    })
}

def addMouseListenerToScrollBarButtons(JScrollBar scrollBar) {
    for (Component component : scrollBar.getComponents()) {
        if (component instanceof JButton) {
            component.addMouseListener(sharedMouseListener)
        }
    }
}



def loadTagsIntoModel(DefaultListModel<String> model, NodeModel node) {
    model.clear()
    List<String> tags = getAllTags(node)
    tags.each { model.addElement(it) }
}

def getAllTags(NodeModel nodeNotProxy) {
    Set<String> tagss = new HashSet<>()

        tagss.addAll(Controller.getCurrentController().getMap().getIconRegistry().getTagCategories().tagsAsListModel)

    return tagss.toList()
}

def cleanAndCreateInspectors(NodeModel nodeNotProxy, JPanel somePanel) {
    if (showOnlyBreadcrumbs) { return }

    visibleInspectors.each {
        it.setVisible(false)
    }
    visibleInspectors.clear()
    JPanel subInspectorPanel = createInspectorPanel(nodeNotProxy, somePanel)
    visibleInspectors.add(subInspectorPanel)

    JPanel subInspectorPanel2 = createInspectorPanel(nodeNotProxy, subInspectorPanel)
    visibleInspectors.add(subInspectorPanel2)
//    parentPanel.revalidate()
//    parentPanel.repaint()
}



public class RoundedCornerBorder implements Border {
    private Color color
    private int thickness
    private int radius

    public RoundedCornerBorder(Color color, int thickness, int radius) {
        this.color = color
        this.thickness = thickness
        this.radius = radius
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.thickness, this.thickness + 5, this.thickness, this.thickness + 5)
    }

    @Override
    public boolean isBorderOpaque() {
        return false
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g
        g2.setColor(color)
        g2.setStroke(new BasicStroke(thickness))
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius)
    }
}

def expandMasterPanel() {
    bounds = masterPanel.getBounds()
    bounds.width = calculateExpandedWidthForMasterPanel()
    masterPanel.setBounds(bounds)
        panelsInMasterPanels.each {
            if(it != currentSourcePanel) {
                it.setVisible(false)
            }
        }

    masterPanel.revalidate()
    masterPanel.repaint()
    parentPanel.revalidate()
    parentPanel.repaint()
    isMasterPanelExpanded = true
}

def retractMasterPanel() {
    bounds = masterPanel.getBounds()
    bounds.width = calculateRetractedWidthForMasterPanel()
    masterPanel.setBounds(bounds)
    panelsInMasterPanels.each { it.setVisible(true) }
    masterPanel.revalidate()
    masterPanel.repaint()
    isMasterPanelExpanded = false
}



def isCtrlPressed() {
    return (lastMouseModifiers & InputEvent.CTRL_DOWN_MASK) != 0
}

def shouldFreeze() {
    return freezeInspectors || isMouseOverSearchBox || isCtrlPressed()
}


void adjustFontSizeToFitText(
        JTextPane textPane,
        JScrollPane scrollPane,
        NodeModel nodeNotProxy,
        Color fontColor) {


    Closure<String> generateHtml = { int fontSize, String text, String fontColorHex ->
        return """
            <html>
              <head>
                <style type='text/css'>
                  body {
                    font-family: ${panelTextFontName}, sans-serif; 
                    font-size: ${fontSize}px;
                    color: ${fontColorHex};
                  }
                </style>
              </head>
              <body>
                ${text}
              </body>
            </html>
        """
    }

    String fontColorHex = "#000000"
    if (fontColor != null) {
        fontColorHex = String.format("#%02x%02x%02x", fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue())
    }

    int fontSize = panelTextFontSize
    String texto = nodeNotProxy.text


    textPane.setEditable(false)
    textPane.setMargin(new Insets(0,0,0,0))


    while (fontSize >= minFontSize) {
        textPane.setText(generateHtml(fontSize, texto, fontColorHex))

        textPane.setSize(new Dimension(scrollPane.getViewport().getWidth(), Integer.MAX_VALUE))
        Dimension preferred = textPane.getPreferredSize()

        int availableHeight = scrollPane.getViewport().getHeight()

        if (preferred.height <= availableHeight) {
            break
        }

        fontSize--
    }


}

private String truncateText(JLabel label, String text, FontMetrics fm, int maxWidth) {
    StringBuilder truncated = new StringBuilder()
    int ellipsisWidth = fm.stringWidth(ELLIPSIS)
    int currentWidth = 0

    for (int i = 0; i < text.length(); i++) {
        char c = text.charAt(i)
        currentWidth += fm.charWidth(c)
        if (currentWidth + ellipsisWidth > maxWidth) {
            truncated.append(ELLIPSIS)
            break
        }
        truncated.append(c)
    }

    return truncated.toString()
}

def configureScrollPaneForRTL(JScrollPane scrollPane) {
    if (rtlOrientation) {
        SwingUtilities.invokeLater {
            scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum())
        }
    } else {
        SwingUtilities.invokeLater {
            scrollPane.getHorizontalScrollBar().setValue(0)
        }
    }
}


def createJList(DefaultListModel<NodeModel> nodes, JPanel jListPanel, JPanel panelPanel) {
    JList<NodeModel> jList = new JList<>(nodes)
    commonJListsConfigs(jList, nodes, panelPanel)

    if (rtlOrientation) {
        jList.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        jList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }


    JScrollPane scrollPane = new JScrollPane(jList)
    configureScrollPaneForRTL(scrollPane)

    scrollPane.setBackground(new Color(0, 0, 0, 0))
    jList.setOpaque(false)
    scrollPane.setOpaque(false)
    scrollPane.getViewport().setOpaque(false)


    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
    scrollPane.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    scrollPane.getHorizontalScrollBar().addMouseListener(sharedMouseListener)
    addMouseListenerToScrollBarButtons(scrollPane.getVerticalScrollBar())
    addMouseListenerToScrollBarButtons(scrollPane.getHorizontalScrollBar())


    jListPanel.add(scrollPane, BorderLayout.CENTER)

//    jListPanel.revalidate()
//    jListPanel.repaint()
}

def createBreadcrumbsJList() {
//    breadcrumbPanel.removeAll()
//    if (!ancestorsOfCurrentNode || ancestorsOfCurrentNode.isEmpty()) {
//        breadcrumbPanel.revalidate()
//        breadcrumbPanel.repaint()
//        return
//    }

//    DefaultListModel<NodeModel> listModel = new DefaultListModel<>()
//    ancestorsOfCurrentNode.each { listModel.addElement(it) }

//    JList<NodeModel> jList = new JList<>(ancestorsOfCurrentNode)
    ancestorsJList.setModel(ancestorsOfCurrentNode)
    ancestorsJList.setLayoutOrientation(JList.HORIZONTAL_WRAP)
    ancestorsJList.setVisibleRowCount(1)

    ancestorsJList.setFixedCellWidth(200)
    ancestorsJList.setFixedCellHeight(30)

    commonJListsConfigs(ancestorsJList, ancestorsOfCurrentNode, breadcrumbPanel)

    if (rtlOrientation) {
        ancestorsJList.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        ancestorsJList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }

    breadcrumbPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 5))
//    breadcrumbPanel.setLayout(new FlowLayout(rtlOrientation ? FlowLayout.RIGHT : FlowLayout.LEFT, 8, 5)) // RTL Support

    breadcrumbPanel.add(ancestorsJList)

//    breadcrumbPanel.revalidate()
//    breadcrumbPanel.repaint()
}


def populateTagsListModel(JList<String> tagsJList) {

    tagsJList.setModel(listModelForAllTags)


}
