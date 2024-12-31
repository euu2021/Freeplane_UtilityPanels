/////////// Latest FP version that works with the script: freeplane-1.12.8-pre03. Compatibility with later version will be added in the future.

/*
version 1.18: Tags: now, adding, adds to all selected nodes.
 Tags: right click on tag opens context menu. Option to remove tag.

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
 */

// @ExecutionModes({ON_SINGLE_NODE="/menu_bar/euu"})

import groovy.transform.Field

import groovy.json.JsonOutput
import groovy.json.JsonGenerator
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

import javax.swing.*
import javax.swing.border.TitledBorder
import javax.swing.event.ListSelectionListener
import javax.swing.Timer
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.event.KeyEvent
import javax.swing.KeyStroke
import javax.swing.InputMap
import javax.swing.ActionMap
import javax.swing.AbstractAction

import java.util.List
import java.util.regex.Pattern

import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.dnd.*
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSourceAdapter
import java.awt.dnd.DropTargetAdapter
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

import org.freeplane.features.mode.Controller
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.INodeSelectionListener
import org.freeplane.features.nodestyle.NodeStyleController
import org.freeplane.features.styles.LogicalStyleController.StyleOption
import org.freeplane.features.ui.IMapViewChangeListener
import org.freeplane.features.map.IMapChangeListener
import org.freeplane.features.map.NodeDeletionEvent
import org.freeplane.features.map.MapChangeEvent
import org.freeplane.features.link.NodeLinkModel
import org.freeplane.features.map.clipboard.MapClipboardController;
import org.freeplane.features.map.mindmapmode.clipboard.MMapClipboardController;
import org.freeplane.features.map.mindmapmode.MMapController;
import org.freeplane.api.NodeChangeListener
import org.freeplane.api.NodeChanged
import org.freeplane.api.NodeChanged.ChangedElement
import org.freeplane.features.highlight.HighlightController;
import org.freeplane.features.highlight.NodeHighlighter;
import org.freeplane.features.map.IMapSelection;
import org.freeplane.features.map.clipboard.MindMapNodesSelection;
import org.freeplane.view.swing.map.MapView;
import org.freeplane.features.filter.Filter
import org.freeplane.features.map.NodeModel
import org.freeplane.view.swing.map.MapView;
import org.freeplane.features.map.IMapSelection;
import org.freeplane.features.mode.Controller;
import org.freeplane.features.icon.IconController;
import org.freeplane.features.icon.Tag;
import org.freeplane.features.icon.TagCategories;
import org.freeplane.features.icon.mindmapmode.MIconController;

//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ User settings ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

panelTextFontName = "Dialog"
panelTextFontSize = 15
fontForListItens = Font.PLAIN

nodeTextPanelFixedHeight = 100

retractedWidthFactorForMasterPanel = 20 //the higher the factor, the smaller the panels width
expandedWidthFactorForMasterPanel = 4 //the higher the factor, the wider the panels width
widthFactorForInspector = 15 //the higher the factor, the smaller the inspector panel width

@Field selectionDelay = 100 //miliseconds

reverseAncestorsList = true

paddingBeforeHorizontalScrollBar = 30

additionalInspectorDistanceToTheBottomOfTheScreen = 175

widthOfTheClearButtonOnQuickSearchPanel = 30

@Field KeyStroke keyStrokeToQuickSearch = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK)

//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ User settings ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

@Field DefaultListModel<String> allTags = new DefaultListModel<String>()

fontForItems = new Font(panelTextFontName, fontForListItens, panelTextFontSize)

uniqueIdForScript = 999
deleteCurrentListenersFromPreviousExecutions()

@Field List<NodeModel> history = []
@Field List<NodeModel> pinnedItems = []
@Field List<NodeModel> quickSearchResults = []
@Field List<JPanel> visibleInspectors = []
@Field List<String> savedSearchCriteria = []
savedSearchCriteria.add("")

@Field JScrollPane parentPanel
@Field JPanel masterPanel
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

mapViewWindowForSizeReferences = Controller.currentController.mapViewManager.mapView.parent

@Field String searchText = ""
@Field String lastSearchText = ""

@Field NodeModel currentlySelectedNode

@Field MIconController iconController = (MIconController) Controller.currentModeController.getExtension(IconController.class)


@Field Set<NodeModel> cachedHighlightedNodes = new HashSet<>()

@Field DocumentListener searchTextBoxListener

@Field Timer liveSearchTimer = new Timer(200, null);
liveSearchTimer.setRepeats(false);

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
    if (freezeInspectors || isMouseOverSearchBox) return
    

    if(currentSourcePanel == recentSelectedNodesPanel || currentSourcePanel == quickSearchPanel || currentSourcePanel == pinnedItemsPanel || currentSourcePanel == tagsPanel) {
        bounds = masterPanel.getBounds()
        bounds.width = calculateExpandedWidthForMasterPanel()
        masterPanel.setBounds(bounds)
        isMasterPanelExpanded = true


        visibleInspectors.each{
            if(!inspectorUpdateSelection) {
                it.setVisible(false)
            }
            else{
                if(it != visibleInspectors[0]) {
                    it.setVisible(false)
                }
            }
        }

        if(!inspectorUpdateSelection) {
            visibleInspectors.clear()
        }
        else {
            visibleInspectors.removeAll { it != visibleInspectors[0] }
            if(visibleInspectors.size() != 0) {
                setInspectorLocation(visibleInspectors[0], masterPanel)
            }
        }
    }

    if (lastMouseLocation) {
        int index = currentList.locationToIndex(lastMouseLocation)

        Rectangle cellBounds = currentList.getCellBounds(index, index)
        if (cellBounds != null && cellBounds.contains(lastMouseLocation)) {
            if (index >= 0) {
                NodeModel subNode = currentListModel.getElementAt(index)

                subInspectorPanel = createInspectorPanel(subNode, currentSourcePanel)

                visibleInspectors.add(subInspectorPanel)
                locationOfTheInspectorOfTheCurrentPanelUnderMouse = subInspectorPanel.getLocation().x
                visibleInspectors.each{
                    if(it.getLocation().x > locationOfTheInspectorOfTheCurrentPanelUnderMouse + 0.1){
                        it.setVisible(false)}

                    if(it != subInspectorPanel && it.getLocation().x == locationOfTheInspectorOfTheCurrentPanelUnderMouse){
                        it.setVisible(false)
                    }
                }
            }
        }
        else {
            if(inspectorUpdateSelection && visibleInspectors.size() == 1) {
                visibleInspectors[0].setVisible(true)
            }
        }
    }
})

class NodeModelTransferable implements Transferable {
    private static final DataFlavor NODE_MODEL_FLAVOR = new DataFlavor(NodeModel.class, "NodeModel");
    private final NodeModel nodeModel;

    public NodeModelTransferable(NodeModel nodeModel) {
        this.nodeModel = nodeModel;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{NODE_MODEL_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return NODE_MODEL_FLAVOR.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return nodeModel;
    }
}

loadSettings()
createPanels()

INodeSelectionListener mySelectionListener = new INodeSelectionListener() {
    @Override
    public void onDeselect(NodeModel node) {
        SwingUtilities.invokeLater { updateAllGUIs() }
    }

    @Override
    public void onSelect(NodeModel node) {
        currentlySelectedNode = node
        if (history.contains(node)) {
            history.remove(node)
        }
        history.add(0, node)

        if (history.size() > 200) {
            history.remove(200)
        }

        saveSettings()

        SwingUtilities.invokeLater { updateAllGUIs() }

        parentPanel.revalidate()
        parentPanel.repaint()

        if (freezeInspectors || isMouseOverSearchBox) {
            return
        }
        if (inspectorUpdateSelection) {
            visibleInspectors.each {
                it.setVisible(false)
            }
            visibleInspectors.clear()
            parentPanel.revalidate()
            parentPanel.repaint()
            JPanel subInspectorPanel = createInspectorPanel(node, recentSelectedNodesPanel)
            visibleInspectors.add(subInspectorPanel)
        }
    }

}

createdSelectionListener = mySelectionListener

Controller.currentController.modeController.mapController.addNodeSelectionListener(mySelectionListener)

SwingUtilities.invokeLater { updateAllGUIs() }

IMapViewChangeListener myMapViewChangeListener = new IMapViewChangeListener() {
    public void afterViewChange(final Component oldView, final Component newView) {
        if (newView == null) {
            return
        }

        searchText = ""
        quickSearchResults.clear()

        parentPanel.remove(recentSelectedNodesPanel)
        parentPanel.remove(pinnedItemsPanel)
        parentPanel.remove(tagsPanel)
        parentPanel.remove(quickSearchPanel)
        saveSettings()
        masterPanel.setVisible(false)
        createPanels()
        masterPanel.revalidate()
        masterPanel.repaint()
        SwingUtilities.invokeLater { updateAllGUIs() }
    }
}

createdMapViewChangeListener = myMapViewChangeListener

Controller.currentController.mapViewManager.addMapViewChangeListener(myMapViewChangeListener)

IMapChangeListener myMapChangeListener = new IMapChangeListener() {
    @Override
    public void onNodeDeleted(NodeDeletionEvent nodeDeletionEvent) {
        NodeModel deletedNode = nodeDeletionEvent.node
        history.remove(deletedNode)
        pinnedItems.remove(deletedNode)
        saveSettings()
        SwingUtilities.invokeLater { updateAllGUIs() }
    }

}

Controller.currentController.modeController.getMapController().addUIMapChangeListener(myMapChangeListener)


viewportSizeChangeListener = new ComponentAdapter() {
    @Override
    public void componentResized(final ComponentEvent e) {
        parentPanel.remove(recentSelectedNodesPanel)
        parentPanel.remove(pinnedItemsPanel)
        parentPanel.remove(tagsPanel)
        parentPanel.remove(quickSearchPanel)
        saveSettings()
        masterPanel.setVisible(false)
        createPanels()
        masterPanel.revalidate()
        masterPanel.repaint()
        SwingUtilities.invokeLater { updateAllGUIs() }
    }
}

mapViewWindowForSizeReferences.addComponentListener(viewportSizeChangeListener);


Controller controllerForHighlighter = Controller.currentModeController.controller
controllerForHighlighter.getExtension(HighlightController.class).addNodeHighlighter(new NodeHighlighter() {

    @Override
    public boolean isNodeHighlighted(NodeModel node, boolean isPrinting) {
        if(searchText.equals("")) { return  }
        if (isPrinting) {
            return false;
        }
        return quickSearchResults.contains(node) || isFoldedWithHighlightedDescendants(node);
    }

    @Override
    public void configure(NodeModel node, Graphics2D g, boolean isPrinting) {
        boolean isFound = quickSearchResults.contains(node);
        boolean hasFoldedDescendants = isFoldedWithHighlightedDescendants(node);

        if (isFound && hasFoldedDescendants) {
            g.setColor(new Color(255, 165, 0, 255));
            g.setStroke(new BasicStroke(5F, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10, new float[]{10, 2}, 0));
        } else if (isFound) {
            g.setColor(new Color(0, 255, 0, 255));
            g.setStroke(new BasicStroke(5F, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10, new float[]{10, 2}, 0));
        } else if (hasFoldedDescendants) {
            g.setColor(new Color(255, 0, 0, 255));
            g.setStroke(new BasicStroke(5F, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10, new float[]{10, 2}, 0));
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
});

def refreshHighlighterCache() {
    cachedHighlightedNodes.clear()
}



return


// ------------------ methods definitions ------------------------

def createPanels(){
    parentPanel = Controller.currentController.mapViewManager.mapView.parent.parent as JScrollPane
    Dimension parentSize = parentPanel.getSize()


    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Master Panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    masterPanel = new JPanel()
    masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS))

    masterPanel.setOpaque(false)

    masterPanel.setBounds(0, 0, calculateRetractedWidthForMasterPanel(), (int) mapViewWindowForSizeReferences.height -5)

    masterPanel.addMouseListener(sharedMouseListener)



    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Master Panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Recent Nodes Panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    recentSelectedNodesPanel = new JPanel(new BorderLayout()) {
        protected void paintComponent(Graphics g)
        {
            g.setColor( getBackground() )
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    recentSelectedNodesPanel.setOpaque(false)
    recentSelectedNodesPanel.setBackground( new Color(0, 0, 0, 0) )

    int recentSelectedNodesPanelWidth = 80
    int recentSelectedNodesPanelHeight = 170

    recentSelectedNodesPanel.setBounds(0, 0, recentSelectedNodesPanelWidth, recentSelectedNodesPanelHeight)


    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Recent Nodes Panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑




    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Pinned Items Panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    pinnedItemsPanel = new JPanel(new BorderLayout()) {
        protected void paintComponent(Graphics g)
        {
            g.setColor( getBackground() )
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    pinnedItemsPanel.setOpaque(false)
    pinnedItemsPanel.setBackground( new Color(0, 0, 0, 0) )

    int pinnedPanelHeight = 130
    pinnedItemsPanel.setBounds(0, recentSelectedNodesPanelHeight + 20, recentSelectedNodesPanelWidth, pinnedPanelHeight)

    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Pinned Items Panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑




    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Tags Panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    tagsPanel = new JPanel(new BorderLayout()) {
        protected void paintComponent(Graphics g)
        {
            g.setColor( getBackground() )
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    tagsPanel.setOpaque(false)
    tagsPanel.setBackground( new Color(0, 0, 0, 0) )

    int tagsPanelHeight = 130
    tagsPanel.setBounds(0, recentSelectedNodesPanelHeight + 20, recentSelectedNodesPanelWidth, tagsPanelHeight)

    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Tags Panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑




    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Quick Search Panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓


    quickSearchPanel = new JPanel(new BorderLayout()) {
        protected void paintComponent(Graphics g)
        {
            g.setColor( getBackground() )
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    quickSearchPanel.setOpaque(false)
    quickSearchPanel.setBackground( new Color(0, 0, 0, 0) )

    int quickSearchPanelHeight = 130
    quickSearchPanel.setBounds(0, recentSelectedNodesPanelHeight + 170, recentSelectedNodesPanelWidth, quickSearchPanelHeight)



    JComboBox<String> searchField = new JComboBox<>(savedSearchCriteria.toArray(new String[0]));
    searchField.setEditable(true);
    searchField.setSelectedItem("")

    searchEditor = (JTextField) searchField.getEditor().getEditorComponent();

    searchEditor.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            scheduleLiveSearch();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            scheduleLiveSearch();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            scheduleLiveSearch();
        }

        private void scheduleLiveSearch() {
            liveSearchTimer.stop();
            liveSearchTimer.start();
        }
    });



    liveSearchTimer.addActionListener(new ActionListener() {
        @Override
        void actionPerformed(ActionEvent e) {
            searchText = searchEditor.getText().trim();

            if (!searchText.equals(lastSearchText)) {
                lastSearchText = searchText
                refreshList(searchText)
            }

            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate();
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint();
            updateAllGUIs();

        }

        private void refreshList(String searchText) {
            quickSearchResults.clear();
            refreshHighlighterCache()
            if (!searchText.isEmpty()) {
                NodeModel rootNode = Controller.getCurrentController().getSelection().selectionRoot;
                searchNodesRecursively(rootNode, searchText, quickSearchResults);

                if (!savedSearchCriteria.contains(searchText)) {
                    savedSearchCriteria.add(0, searchText);
                } else {
                    savedSearchCriteria.remove(searchText);
                    savedSearchCriteria.add(0, searchText);
                }

                saveSettings()

                int caretPosition = searchEditor.getCaretPosition();

                searchField.removeAllItems();
                for (String term : savedSearchCriteria) {
                    searchField.addItem(term);
                }

                searchEditor.setText(searchText);

                if (!searchField.isPopupVisible()) {
                    searchEditor.setCaretPosition(Math.min(caretPosition, searchText.length()));
                }
            }
        }
    });

    JButton clearButton = new JButton("X");
    clearButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            searchField.setSelectedItem("");
            quickSearchResults.clear();
            updateAllGUIs();
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate();
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint();
        }
    });

    clearButton.setPreferredSize(new Dimension(widthOfTheClearButtonOnQuickSearchPanel, 1));
    clearButton.setForeground(Color.BLACK);
    clearButton.setBackground(Color.WHITE);
    clearButton.setBorder(BorderFactory.createEtchedBorder());
    clearButton.setOpaque(true);
    clearButton.setBorderPainted(true);
    clearButton.setFocusPainted(false);

    JPanel panelForSearchBox = new JPanel(new BorderLayout());

    panelForSearchBox.add(searchField, BorderLayout.CENTER);
    panelForSearchBox.add(clearButton, BorderLayout.EAST);

    panelForSearchBox.setOpaque(false)
    panelForSearchBox.setBackground( new Color(0, 0, 0, 0) )

    quickSearchPanel.add(panelForSearchBox, BorderLayout.NORTH);

    innerPanelInQuickSearchPanel = new JPanel(new BorderLayout());

    innerPanelInQuickSearchPanel.setOpaque(false)
    innerPanelInQuickSearchPanel.setBackground( new Color(0, 0, 0, 0) )

    quickSearchPanel.add(innerPanelInQuickSearchPanel, BorderLayout.CENTER);


    searchField.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            isMouseOverSearchBox = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isMouseOverSearchBox = false;
        }
    });



    panelForSearchBox.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            isMouseOverSearchBox = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isMouseOverSearchBox = false;
        }
    });


    searchEditor.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            isMouseOverSearchBox = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isMouseOverSearchBox = false;
        }
    });


    clearButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            isMouseOverSearchBox = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isMouseOverSearchBox = false;
        }
    });

    addQuickSearchShortcut(searchField)



    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Quick Search Panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


    masterPanel.add(recentSelectedNodesPanel)
//    masterPanel.setComponentZOrder(recentSelectedNodesPanel, 0)

    masterPanel.add(Box.createVerticalStrut(20))

    masterPanel.add(pinnedItemsPanel)
//    masterPanel.setComponentZOrder(pinnedItemsPanel, 0)

    masterPanel.add(Box.createVerticalStrut(20))

    masterPanel.add(tagsPanel)
//    masterPanel.setComponentZOrder(tagsPanel, 0)

    masterPanel.add(Box.createVerticalStrut(20))

    masterPanel.add(quickSearchPanel)
//    masterPanel.setComponentZOrder(quickSearchPanel, 0)


    masterPanel.revalidate()
    masterPanel.repaint()

    masterPanel.setVisible(true)



    parentPanel.add(masterPanel)
    parentPanel.setComponentZOrder(masterPanel, 0)

    parentPanel.revalidate()
    parentPanel.repaint()
}

def updateAllGUIs() {
    updateRecentNodesGui()
    updatePinnedItemsGui()
    updateQuickSearchGui()
    updateTagsGui()
}

def updateRecentNodesGui() {
    updateSpecifiedGUIs(history, recentSelectedNodesPanel, recentSelectedNodesPanel)
}

def updatePinnedItemsGui() {
    updateSpecifiedGUIs(pinnedItems, pinnedItemsPanel, pinnedItemsPanel)
}


def updateQuickSearchGui() {
    updateSpecifiedGUIs(quickSearchResults, innerPanelInQuickSearchPanel, quickSearchPanel)
}

def updateSpecifiedGUIs(List<NodeModel> nodes, JPanel jListPanel, JPanel panelPanel) {
    jListPanel.removeAll()

    DefaultListModel<NodeModel> listModel = new DefaultListModel<>()
    nodes.each { listModel.addElement(it) }
    JList<NodeModel> jList = new JList<>(listModel)
    commonJListsConfigs(jList, listModel, panelPanel)


    JScrollPane scrollPane = new JScrollPane(jList)
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
    jListPanel.revalidate()
    jListPanel.repaint()
}

def updateTagsGui() {
    tagsPanel.removeAll()

    DefaultListModel<String> listModel = new DefaultListModel<>()
    NodeModel selectedNode = currentlySelectedNode
    loadTagsIntoModel(listModel, selectedNode)

    JList<String> jList = new JList<>(listModel)
//    commonJListsConfigs(jList, listModel, tagsPanel)

    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Tag List Configs ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓



    //configureListSelection(theJlist);

    jList.addListSelectionListener({ e ->
        if (!e.getValueIsAdjusting()) {
            int selectedItemIndex = jList.getSelectedIndex()
            if (selectedItemIndex != -1) {
                tagSelected = jList.getModel().getElementAt(selectedItemIndex)
                List<Tag> tagToInsert = new ArrayList<Tag>()
                tagToInsert.add(tagSelected)
                iconController.insertTagsIntoSelectedNodes(tagToInsert)
            }
        }
    } as ListSelectionListener)

    //configureListContextMenu(theJlist)

    jList.addMouseListener(new MouseAdapter() {
        @Override
        void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                int selectedItemIndex = jList.locationToIndex(e.getPoint())
                if (selectedItemIndex >= 0) {
                    tagSelected = jList.getModel().getElementAt(selectedItemIndex)
                    Set<Tag> tagToRemove = new HashSet<Tag>()
                    tagToRemove.add(tagSelected)

                    JPopupMenu popupMenu = new JPopupMenu()
                    JMenuItem menuItem

                    menuItem = new JMenuItem("Remove")
                    menuItem.addActionListener({
                        iconController.removeSelectedTagsFromSelectedNodes(tagToRemove)
                        updateAllGUIs()
                    })

                    menuItem.addMouseListener(sharedMouseListener)
                    popupMenu.add(menuItem)
                    popupMenu.show(e.getComponent(), e.getX(), e.getY())
                }
            }
        }
    })

    //configureListCellRenderer(theJlist, thePanelPanel)

    jList.setCellRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
            if (value instanceof Tag) {
                Tag currentTag = (Tag) value

//                configureLabelForNode(label, currentNode, tagsPanel)
                Color backgroundColor = currentTag.getColor()
//                Color fontColor = NodeStyleController.getController().getColor(node, StyleOption.FOR_UNSELECTED_NODE)
                String hexColor = String.format("#%02x%02x%02x", backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue());
//                String fontColorHex = String.format("#%02x%02x%02x", fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue());

                fontForItems = new Font(panelTextFontName, fontForListItens, panelTextFontSize)

                label.setBackground(backgroundColor)
//                component.setForeground(fontColor)
                label.setFont(fontForItems)
            }
            if (isSelected) {
                label.setBackground(list.getSelectionBackground())
                label.setForeground(list.getSelectionForeground())
            }
            return label
        }
    })


    //configureMouseMotionListener(jList, listModel, tagsPanel)
    jList.addMouseMotionListener(new MouseAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
            if (freezeInspectors || isMouseOverSearchBox) {return}

            hoverTimer.stop()
//            currentList = list
//            currentListModel = listModel
            currentSourcePanel = tagsPanel
            lastMouseLocation = e.getPoint()
            mouseOverList = true
            hoverTimer.restart()


            if(currentSourcePanel == recentSelectedNodesPanel || currentSourcePanel == quickSearchPanel || currentSourcePanel == pinnedItemsPanel || currentSourcePanel == tagsPanel) {
                bounds = masterPanel.getBounds()
                bounds.width = calculateExpandedWidthForMasterPanel()
                masterPanel.setBounds(bounds)
                masterPanel.revalidate()
                masterPanel.repaint()
                isMasterPanelExpanded = true
                if(visibleInspectors.size() != 0) {
                    setInspectorLocation(visibleInspectors[0], masterPanel)
                }
            }
        }
    })

    //The same as the commonJListsConfigs() ↓
    configureListFont(jList)
    configureMouseExitListener(jList)


    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Tag List Configs ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    JScrollPane scrollPane = new JScrollPane(jList) {
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    scrollPane.setBackground(new Color(0, 0, 0, 0))
    jList.setOpaque(false)
    scrollPane.setOpaque(false)
    scrollPane.getViewport().setOpaque(false)

    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
    scrollPane.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    scrollPane.getHorizontalScrollBar().addMouseListener(sharedMouseListener)
    addMouseListenerToScrollBarButtons(scrollPane.getVerticalScrollBar())
    addMouseListenerToScrollBarButtons(scrollPane.getHorizontalScrollBar())


    tagsPanel.add(scrollPane, BorderLayout.CENTER)
    tagsPanel.revalidate()
    tagsPanel.repaint()
}


JPanel createInspectorPanel(NodeModel node, JPanel sourcePanel) {

    JPanel inspectorPanel = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    inspectorPanel.putClientProperty("referenceNode", node)

    inspectorPanel.setLayout(new BorderLayout())
    inspectorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5))
    inspectorPanel.setBackground(Color.LIGHT_GRAY)


    ////////////// Node Text Panel ///////////////


    JTextPane textLabel = new JTextPane();
    textLabel.setContentType("text/html")

    configureLabelForNode(textLabel, node, inspectorPanel)

    JScrollPane textScrollPane = new JScrollPane(textLabel)
    textScrollPane.setPreferredSize(new Dimension(200, nodeTextPanelFixedHeight))

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

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
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

    DefaultListModel<NodeModel> ancestorLineModel = new DefaultListModel<>()

    if(reverseAncestorsList) {
        node.getPathToRoot().reverse().each {
            ancestorLineModel.addElement(it)
        }
    }
    else{
        node.getPathToRoot().each {
            ancestorLineModel.addElement(it)
        }
    }
    ancestorLineModel.removeElement(node)

    JList<NodeModel> ancestorsLineList = new JList<>(ancestorLineModel)
    commonJListsConfigs(ancestorsLineList, ancestorLineModel, inspectorPanel)



    TitledBorder titledBorderAncestors = BorderFactory.createTitledBorder("Ancestors")
    titledBorderAncestors.setTitleJustification(TitledBorder.LEFT)
    titledBorderAncestors.setTitleFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))
    ancestorsLineList.setBorder(titledBorderAncestors)

    JScrollPane scrollPaneAncestorsLineList = new JScrollPane(ancestorsLineList){
        protected void paintComponent(Graphics g)
        {
            g.setColor( getBackground() )
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }


    ancestorsLineList.setSize(ancestorsLineList.getPreferredSize())
    ancestorsLineList.revalidate()
    Dimension listPreferredSize = ancestorsLineList.getPreferredSize()

    int maxHeight = (int) mapViewWindowForSizeReferences.height -additionalInspectorDistanceToTheBottomOfTheScreen

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
    if(node.isRoot()) {}
    else {
        node.parent.getChildren().each {
            siblingsModel.addElement(it)
        }
    }

    JList<NodeModel> siblingsList = new JList<>(siblingsModel)
    commonJListsConfigs(siblingsList, siblingsModel, inspectorPanel)

    TitledBorder titledBorderSiblings = BorderFactory.createTitledBorder("Siblings")
    titledBorderSiblings.setTitleJustification(TitledBorder.LEFT)
    titledBorderSiblings.setTitleFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))
    siblingsList.setBorder(titledBorderSiblings)

    JScrollPane scrollPanelSiblingsList = new JScrollPane(siblingsList)


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

    int selectedIndex = siblingsModel.indexOf(node)
    if (selectedIndex >= 0) {
        SwingUtilities.invokeLater {
            siblingsList.ensureIndexIsVisible(selectedIndex);
        }
    }


    //////////////////////////////////////////////////





    //////////////////   Children panel  //////////////////



    DefaultListModel<NodeModel> childrenModel = new DefaultListModel<>()
    node.children.each {
        childrenModel.addElement(it)
    }

    JList<NodeModel> childrenList = new JList<>(childrenModel)
    commonJListsConfigs(childrenList, childrenModel, inspectorPanel)

    TitledBorder titledBorderChildren = BorderFactory.createTitledBorder("Children")
    titledBorderChildren.setTitleJustification(TitledBorder.LEFT)
    titledBorderChildren.setTitleFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))
    childrenList.setBorder(titledBorderChildren)

    JScrollPane scrollPaneChildrenList = new JScrollPane(childrenList)


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
    columnsPanel.setLayout(new GridLayout())

    columnsPanel.setBackground( new Color(0, 0, 0, 0) )

    int ammountOfPannelsInInspector = 1

    if(ancestorLineModel.getSize() > 0) {
        columnsPanel.add(scrollPaneAncestorsLineList);
    }

    if(siblingsModel.getSize() > 1) {
        columnsPanel.add(scrollPanelSiblingsList);
        ammountOfPannelsInInspector++
    }
    if(childrenModel.getSize() > 0) {
        columnsPanel.add(scrollPaneChildrenList);
        ammountOfPannelsInInspector++
    }


    JPanel verticalStackPanel = new JPanel()
    verticalStackPanel.setLayout(new BoxLayout(verticalStackPanel, BoxLayout.Y_AXIS))
    verticalStackPanel.setBackground( new Color(0, 0, 0, 0) )

    verticalStackPanel.add(buttonPanel, BorderLayout.NORTH)
    verticalStackPanel.add(textScrollPane, BorderLayout.NORTH)
    verticalStackPanel.add(columnsPanel, BorderLayout.NORTH)

    inspectorPanel.add(verticalStackPanel, BorderLayout.NORTH)

    verticalStackPanel.revalidate()

    inspectorPanel.setSize(calculateInspectorWidth(ammountOfPannelsInInspector), (int) inspectorPanel.getPreferredSize().height)

    inspectorPanel.revalidate();
    inspectorPanel.repaint();


    /////////////////////////////////////////


    setInspectorLocation(inspectorPanel, sourcePanel)
    inspectorPanel.setVisible(true)
    parentPanel.add(inspectorPanel)
    parentPanel.setComponentZOrder(inspectorPanel, 0)
    parentPanel.revalidate()
    parentPanel.repaint()

    return inspectorPanel
}

void hideInspectorPanelIfNeeded() {
    if (freezeInspectors || isMouseOverSearchBox) {return}
    if (!mouseOverList) {

        visibleInspectors.each{
            if(!inspectorUpdateSelection) {
                it.setVisible(false)
            }
            else{
                if(it != visibleInspectors[0]) {
                    it.setVisible(false)
                }
            }
        }

        if(!inspectorUpdateSelection) {
            visibleInspectors.clear()
        }
        else {
            visibleInspectors.removeAll { it != visibleInspectors[0] }
            if(visibleInspectors.size() != 0) {
                setInspectorLocation(visibleInspectors[0], masterPanel)
            }
        }

        if(inspectorUpdateSelection && visibleInspectors.size() > 0) {
            visibleInspectors[0].setVisible(true)
        }

        bounds = masterPanel.getBounds()
        bounds.width = calculateRetractedWidthForMasterPanel()
        masterPanel.setBounds(bounds)
        isMasterPanelExpanded = false

        parentPanel.revalidate()
        parentPanel.repaint()

        if(visibleInspectors.size() != 0 && inspectorUpdateSelection) {
            setInspectorLocation(visibleInspectors[0], masterPanel)
        }

        return
    }
}

void configureLabelForNode(JComponent component, NodeModel node, JPanel sourcePanel) {
    Color backgroundColor = NodeStyleController.getController().getBackgroundColor(node, StyleOption.FOR_UNSELECTED_NODE)
    Color fontColor = NodeStyleController.getController().getColor(node, StyleOption.FOR_UNSELECTED_NODE)
    String hexColor = String.format("#%02x%02x%02x", backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue());
    String fontColorHex = String.format("#%02x%02x%02x", fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue());

    fontForItems = new Font(panelTextFontName, fontForListItens, panelTextFontSize)

    component.setBackground(backgroundColor)
    component.setForeground(fontColor)
    component.setFont(fontForItems)

    String textWithHighlight
    def searchedTerms = searchText

    currentMapView = Controller.currentController.MapViewManager.mapView

    if (component instanceof JLabel) {
        JLabel label = (JLabel) component

        String prefix = "";

        if (currentMapView.currentRootParentView != null) {
            if (node.getPathToRoot().find { it == currentMapView.mapSelection.selectionRoot } == null) {
                prefix += "⚠|";
            }
        }

        if (pinnedItems.contains(node)) {
            prefix += "📌";
        }

        NodeModel storedNode = (NodeModel) sourcePanel.getClientProperty("referenceNode")

        if (storedNode == node) {
            label.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }

        String labelText = prefix + node.text;


        if (quickSearchResults.contains(node)) {
            textWithHighlight = highlightSearchTerms(labelText, searchedTerms);
        } else {
            textWithHighlight = labelText
        }

        label.setText(textWithHighlight)

        label.revalidate()
        label.repaint()




        return

    }
    else if (component instanceof JTextPane) {
        JTextPane textPane = (JTextPane) component;
        if (quickSearchResults.contains(node)) {
            textWithHighlight = highlightSearchTerms(node.text, searchedTerms);
        }
        else {
            textWithHighlight = node.text
        }

        String htmlContent = "<html><head>" +
                "<style type='text/css'>body { font-family: $panelTextFontName, sans-serif; font-size: $panelTextFontSize px; color: $fontColorHex; }</style>" +
                "</head><body>" +
                textWithHighlight +
                "</body></html>";

        textPane.setText(htmlContent);


        textPane.setEditable(false);
    }

    component.setOpaque(true)

}


String highlightSearchTerms(String text, String searchTerms) {
    String highlightedText = text;
    String[] terms = searchTerms.split("\\s+");

    for (String term : terms) {
        if (term.isEmpty()) continue;
        highlightedText = highlightedText.replaceAll("(?i)(${Pattern.quote(term)})", "<span style='background-color:#00ff00;'>${'$'}1</span>");

    }

    return "<html>" + highlightedText + "</html>";
}



//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Lists configs ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

void commonJListsConfigs(JList<NodeModel> theJlist, DefaultListModel<NodeModel> theListModel, JPanel thePanelPanel) {
    configureDragAndDrop(theJlist);
    configureListFont(theJlist);
    configureListSelection(theJlist);
    configureListContextMenu(theJlist);
    configureListCellRenderer(theJlist, thePanelPanel)
    configureMouseMotionListener(theJlist, theListModel, thePanelPanel)
    configureMouseExitListener(theJlist)
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
            if (SwingUtilities.isRightMouseButton(e)) {
                int index = list.locationToIndex(e.getPoint())
                if (index >= 0) {
                    NodeModel selectedItem = list.getModel().getElementAt(index)

                    JPopupMenu popupMenu = new JPopupMenu()
                    JMenuItem menuItem

                    if (pinnedItems.contains(selectedItem)) {
                        menuItem = new JMenuItem("Unpin")
                        menuItem.addActionListener({
                            pinnedItems.remove(selectedItem)
                            saveSettings()
                            updateAllGUIs()
                        })
                    } else {
                        menuItem = new JMenuItem("Pin")
                        menuItem.addActionListener({
                            pinnedItems.add(selectedItem)
                            saveSettings()
                            updateAllGUIs()
                        })
                    }
                    menuItem.addMouseListener(sharedMouseListener)
                    popupMenu.add(menuItem)
                    popupMenu.show(e.getComponent(), e.getX(), e.getY())
                }
            }
        }
    })
}


void configureDragAndDrop(JList<NodeModel> list) {
    DragSource dragSource = DragSource.getDefaultDragSource();
    dragSource.createDefaultDragGestureRecognizer(list, DnDConstants.ACTION_MOVE, new DragGestureListener() {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            if (!list.isSelectionEmpty()) {
                int index = list.getSelectedIndex();
                NodeModel selectedNodeModel = list.getModel().getElementAt(index);

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
                    void setSiblingMaxLevel(int i) {

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
                ((MindMapNodesSelection) transferable).setDropAction("MOVE");

                dragSource.startDrag(dge, DragSource.DefaultMoveDrop, transferable, new DragSourceAdapter() {});
            }
        }
    });

    new DropTarget(list, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
        @Override
        public void drop(DropTargetDropEvent dtde) {
            try {
                Point dropLocation = dtde.getLocation();
                int index = list.locationToIndex(dropLocation);
                ListModel<NodeModel> model = list.getModel();
                NodeModel targetNodeModel = null;
                if (index >= 0 && index < model.getSize()) {
                    targetNodeModel = model.getElementAt(index);
                }

                Transferable transferableNode = dtde.getTransferable()
                DataFlavor freeplaneNodesFlavor = new DataFlavor("application/freeplane-nodes; class=java.util.Collection", "application/freeplane-nodes");
                if (transferableNode.isDataFlavorSupported(freeplaneNodesFlavor)) {
                    Object data = transferableNode.getTransferData(freeplaneNodesFlavor)
                    Collection<NodeModel> nodeModels = null;
                    if (data instanceof Collection<?>) {
                        Collection<?> collection = (Collection<?>) data;
                        boolean allNodes = collection.stream().allMatch(element -> element instanceof NodeModel);
                        if (allNodes) {
                            nodeModels = (Collection<NodeModel>) collection;
                        } else {
                        }
                    }
                    if (targetNodeModel != null && nodeModels != null) {
                        List<NodeModel> nodesToMove = new ArrayList<>(nodeModels);

                        if (nodesToMove[0] == targetNodeModel) {
                            Controller.currentController.mapViewManager.mapView.getMapSelection().selectAsTheOnlyOneSelected(targetNodeModel)
                            return
                        }

                        final MMapController mapController = (MMapController) Controller.getCurrentModeController().getMapController();

                        mapController.moveNodesAsChildren(nodesToMove, targetNodeModel);
                    }
                    return
                }
                if (dtde.isDataFlavorSupported(NodeModelTransferable.NODE_MODEL_FLAVOR)) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    Transferable transferable = dtde.getTransferable();

                    NodeModel sourceNodeModel = (NodeModel) transferable.getTransferData(NodeModelTransferable.NODE_MODEL_FLAVOR);

                    if (targetNodeModel != null) {
                        List<NodeModel> nodesToMove = Arrays.asList(sourceNodeModel);
                        final MMapController mapController = (MMapController) Controller.getCurrentModeController().getMapController();
                        mapController.moveNodesAsChildren(nodesToMove, targetNodeModel);
                    }

                    dtde.dropComplete(true);
                } else {
                    dtde.rejectDrop();
                }
            } catch (Exception e) {
                e.printStackTrace();
                dtde.rejectDrop();
            }
        }
    });
}

void configureListCellRenderer(JList<NodeModel> listParameter, JPanel sourcePanel) {
    listParameter.setCellRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
            if (value instanceof NodeModel) {
                NodeModel currentNode = (NodeModel) value
                configureLabelForNode(label, currentNode, sourcePanel)
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
            if (freezeInspectors || isMouseOverSearchBox) {return}

            hoverTimer.stop()
            currentList = list
            currentListModel = listModel
            currentSourcePanel = sourcePanel
            lastMouseLocation = e.getPoint()
            mouseOverList = true
            hoverTimer.restart()


            if(currentSourcePanel == recentSelectedNodesPanel || currentSourcePanel == quickSearchPanel || currentSourcePanel == pinnedItemsPanel || currentSourcePanel == tagsPanel) {
                bounds = masterPanel.getBounds()
                bounds.width = calculateExpandedWidthForMasterPanel()
                masterPanel.setBounds(bounds)
                masterPanel.revalidate()
                masterPanel.repaint()
                isMasterPanelExpanded = true
                if(visibleInspectors.size() != 0) {
                    setInspectorLocation(visibleInspectors[0], masterPanel)
                }
            }
        }
    })
}

void configureMouseExitListener(JList<NodeModel> list) {
    list.addMouseListener(new MouseAdapter() {
        @Override
        void mouseExited(MouseEvent e) {
            mouseOverList = false
            hideInspectorTimer.restart()
        }
    })
}

//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Lists configs ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


private void saveSettings() {
    File file = getSettingsFile()

    List<String> pinnedItemsIds = pinnedItems.collect { it.id }
    List<String> recentNodesIds = history.collect { it.id }

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
        return
    }

    try {
        String content = file.text
        def settings = new JsonSlurper().parseText(content)

        pinnedItems = settings.pinnedItems.collect { id ->
            Controller.currentController.map.getNodeForID(id)
        }.findAll { it != null }

        if (settings.recentSearches instanceof List) {
            savedSearchCriteria.clear()
            savedSearchCriteria.addAll(settings.recentSearches)
        }

        if (settings.recentNodes instanceof List) {
            history.clear()
            history.addAll(settings.recentNodes.collect { id ->
                Controller.currentController.map.getNodeForID(id)
            }.findAll { it != null })
        }

    } catch (Exception e) {
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
    int x = sourcePanel.getLocation().x + sourcePanel.width

    int y = 0
    inspectorPanel.setLocation(x, y)
}

def searchNodesRecursively(NodeModel node, String searchText, List<NodeModel> results) {
    String[] terms = searchText.toLowerCase().split("\\s+");

    def termsMatchedInNode = terms.findAll { term ->
        node.text?.toLowerCase().contains(term)
    }

    def remainingTerms = terms - termsMatchedInNode

    if (!termsMatchedInNode.isEmpty() && remainingTerms.every { term -> containsTermInAncestors(node, term) }) {
        results.add(node);
    }

    node.children.each { child ->
        searchNodesRecursively(child, searchText, results);
    }
}

def containsTermInAncestors(NodeModel node, String term) {
    node = node.parent;
    while (node != null) {
        if (node.text?.toLowerCase().contains(term)) {
            return true;
        }
        node = node.parent;
    }
    return false;
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

def getAllTags(NodeModel nodez) {
    Set<String> tagss = new HashSet<>()


    node.mindMap.root.findAll().each { nodes ->

        tagss.addAll(iconController.getTags(nodes.delegate))
    }
    return tagss.toList()
}

