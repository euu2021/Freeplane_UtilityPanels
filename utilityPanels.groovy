
/*
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



//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ User settings ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓


panelTextFontName = "Dialog"
panelTextFontSize = 15
fontForListItens = Font.PLAIN

nodeTextPanelFixedHeight = 100

retractedWidthFactorForMasterPanel = 20 //the higher the factor, the smaller the panels width
expandedWidthFactorForMasterPanel = 4 //the higher the factor, the wider the panels width
widthFactorForInspector = 5 //the higher the factor, the smaller the inspector panel width

@Field selectionDelay = 100 //miliseconds

reverseAncestorsList = true

paddingBeforeHorizontalScrollBar = 30

//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ User settings ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

fontForItems = new Font(panelTextFontName, fontForListItens, panelTextFontSize)

uniqueIdForScript = 999
deleteCurrentListenersFromPreviousExecutions()

@Field List<NodeModel> history = []
@Field List<NodeModel> pinnedItems = []
@Field List<NodeModel> quickSearchResults = []
@Field List<JPanel> visibleInspectors = []

@Field JScrollPane parentPanel
@Field JPanel masterPanel
@Field JPanel recentSelectedNodesPanel
@Field JPanel inspectorPanel
@Field JPanel pinnedItemsPanel
@Field JPanel quickSearchPanel
@Field JPanel innerPanelInQuickSearchPanel

@Field boolean mouseOverList = false
@Field boolean freezeInspectors = false
@Field boolean inspectorUpdateSelection = false
@Field boolean isMasterPanelExpanded = false

mapViewWindowForSizeReferences = Controller.currentController.mapViewManager.mapView.parent

@Field boolean quickSearchNeedsRefresh = true
@Field String searchText = ""
@Field DocumentListener searchTextBoxListener

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
    if (freezeInspectors) return
    

    if(currentSourcePanel == recentSelectedNodesPanel || currentSourcePanel == quickSearchPanel || currentSourcePanel == pinnedItemsPanel) {
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
                setInspectorLocation(visibleInspectors[0], masterPanel, false)
            }
        }
    }

    if (lastMouseLocation) {
        int index = currentList.locationToIndex(lastMouseLocation)

        Rectangle cellBounds = currentList.getCellBounds(index, index)
        if (cellBounds != null && cellBounds.contains(lastMouseLocation)) {
            if (index >= 0) {
                NodeModel subNode = currentListModel.getElementAt(index)

                if(inspectorUpdateSelection && visibleInspectors.size() == 1) {
                    subInspectorPanel = createInspectorPanel(subNode, visibleInspectors[0])
                }
                else {
                    subInspectorPanel = createInspectorPanel(subNode, currentSourcePanel)
                }

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
        quickSearchNeedsRefresh = true
        try {
            lilive.jumper.Jumper.instance.headlessEnd()
        }
        catch (Exception e) {
        }
    }

    @Override
    public void onSelect(NodeModel node) {
        if (!history.contains(node)) {
            history.add(node)
            if (history.size() > 10) {
                history.remove(0)
            }
            SwingUtilities.invokeLater { updateAllGUIs() }
        }
        parentPanel.revalidate()
        parentPanel.repaint()
        if (freezeInspectors == true) {return}
        if (inspectorUpdateSelection == true) {
            visibleInspectors.each{
                it.setVisible(false)
            }
            visibleInspectors.clear()
            parentPanel.revalidate()
            parentPanel.repaint()
            subInspectorPanel2 = createInspectorPanel(node, recentSelectedNodesPanel)
            visibleInspectors.add(subInspectorPanel2)
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
        parentPanel.remove(recentSelectedNodesPanel)
        parentPanel.remove(pinnedItemsPanel)
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

createdMapChangeListener = myMapChangeListener

Controller.currentController.modeController.getMapController().addUIMapChangeListener(myMapChangeListener)


viewportSizeChangeListener = new ComponentAdapter() {
    @Override
    public void componentResized(final ComponentEvent e) {
        parentPanel.remove(recentSelectedNodesPanel)
        parentPanel.remove(pinnedItemsPanel)
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
        return !isPrinting
                && quickSearchResults.contains(node)
    }

    @Override
    public void configure(NodeModel node, Graphics2D g, boolean isPrinting) {
        g.setColor(new Color(0, 255, 0,255));
        g.setStroke(new BasicStroke(5F, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10, new float[] { 10, 2 }, 0));
    }
});

NodeChangeListener myNodeChangeListener = { NodeChanged event ->
    /* enum ChangedElement {TEXT, DETAILS, NOTE, ICON, ATTRIBUTE, FORMULA_RESULT, UNKNOWN} */
    if (
            event.changedElement == NodeChanged.ChangedElement.TEXT
                    || event.changedElement == NodeChanged.ChangedElement.DETAILS
                    || event.changedElement == NodeChanged.ChangedElement.NOTE
                    || event.changedElement == NodeChanged.ChangedElement.ATTRIBUTE) {

        quickSearchNeedsRefresh = true

        if (searchText == "") {return}
        String[] searchWords = searchText.split("\\s+");
        boolean containsSearchText = false;
        for (String word : searchWords) {
            if (event.node.text.contains(word)) {
                containsSearchText = true;
                break
            }
        }
        if (containsSearchText) {
            searchTextBoxListener.doLiveSearch()
            return
        }
        else {
        }

        try {
            lilive.jumper.Jumper.instance.headlessEnd()
        }
        catch (Exception e) {
        }
    }
} as NodeChangeListener

mindMap.addListener(myNodeChangeListener)

return


// ------------------ methods definitions ------------------------

def createPanels(){
    parentPanel = Controller.currentController.mapViewManager.mapView.parent.parent as JScrollPane
    Dimension parentSize = parentPanel.getSize()


    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Master Panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    masterPanel = new JPanel()
    masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS))

    masterPanel.setOpaque(false)

    masterPanel.setBounds(0, 0, calculateRetractedWidthForMasterPanel(), (int) mapViewWindowForSizeReferences.height -05)



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

    JTextField searchField = new JTextField();

    searchTextBoxListener = new DocumentListener() {

        public void insertUpdate(DocumentEvent e) {
            doLiveSearch();
        }

        public void removeUpdate(DocumentEvent e) {
            if (searchField.getText() == "") {
                searchText = ""
                quickSearchResults.clear()
                updateAllGUIs()
                Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
                Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
                return
            }
            doLiveSearch()
        }

        public void changedUpdate(DocumentEvent e) {
        }

        public void doLiveSearch() {
            searchText = searchField.getText();
            quickSearchResults.clear()
            if (lilive.jumper.Jumper.instance && quickSearchNeedsRefresh == false) {
                lilive.jumper.Jumper.startHeadlessCall(searchText)
            }
            else {
                lilive.jumper.Jumper.startHeadlessCall(searchText)
                PropertyChangeListener searchCompleteListener = new PropertyChangeListener() {
                    void propertyChange(PropertyChangeEvent evt) {
                        if ("searchInProgress".equals(evt.getPropertyName()) && Boolean.FALSE.equals(evt.getNewValue())) {
                            if (lilive.jumper.Jumper.retrieveResultsAsNodeProxyList().size() == 0) {
                                Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
                                Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
                                return
                            }
                            quickSearchResults.clear()
                            lilive.jumper.Jumper.retrieveResultsAsNodeProxyList().each { quickSearchResults.add(it.delegate) }
                            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
                            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
                            updateAllGUIs()
                        }
                    }
                }
                lilive.jumper.Jumper.instance.addPropertyChangeListener(searchCompleteListener)
                quickSearchNeedsRefresh = false
            }
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
            updateAllGUIs()
        }
    };
    searchField.getDocument().addDocumentListener(searchTextBoxListener)

    JButton clearButton = new JButton("X");
    clearButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            searchField.setText("");
            searchText = ""
            quickSearchResults.clear()
            updateAllGUIs()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
        }
    });

    clearButton.setPreferredSize(new Dimension(10, 1));
    clearButton.setForeground(Color.BLACK);
    clearButton.setBackground(Color.WHITE);
    clearButton.setBorder(BorderFactory.createEtchedBorder());
    clearButton.setOpaque(true);
    clearButton.setBorderPainted(true);
    clearButton.setFocusPainted(false);

    clearButton.setContentAreaFilled(true);
    clearButton.setFocusable(false);

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

    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Quick Search Panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


    masterPanel.add(recentSelectedNodesPanel)
//    masterPanel.setComponentZOrder(recentSelectedNodesPanel, 0)

    masterPanel.add(Box.createVerticalStrut(20))

    masterPanel.add(pinnedItemsPanel)
//    masterPanel.setComponentZOrder(pinnedItemsPanel, 0)

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

//    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER)
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)


    jListPanel.add(scrollPane, BorderLayout.CENTER)
    jListPanel.revalidate()
    jListPanel.repaint()
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
    inspectorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK))
    inspectorPanel.setBackground( new Color(0, 0, 0, 0) )


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

    /////////////////////////////////////////////////////////


    /////////////////////////// Buttons panel //////////////////

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER))
    buttonPanel.setBackground(Color.LIGHT_GRAY)

    JButton button1 = new JButton("Freeze")
    button1.addActionListener(e -> {
        freezeInspectors = !freezeInspectors

        if (freezeInspectors) {
            button1.setBackground(Color.BLUE)
            button1.setForeground(Color.BLACK)
        } else {
            button1.setBackground(Color.WHITE)
            button1.setForeground(Color.BLACK)
        }
    })
    button1.setOpaque(true)
    button1.setBorderPainted(false)
    button1.setFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))


    JButton button2 = new JButton("Update Selection")
    button2.addActionListener(e -> {
        inspectorUpdateSelection = !inspectorUpdateSelection

        if (inspectorUpdateSelection) {
            button2.setBackground(Color.BLUE)
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
        button2.setBackground(Color.BLUE)
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

    int maxHeight = (int) mapViewWindowForSizeReferences.height -175

    int finalHeight = Math.min(listPreferredSize.height, maxHeight)
    scrollPaneAncestorsLineList.setPreferredSize(new Dimension(200, finalHeight + paddingBeforeHorizontalScrollBar))


    ancestorsLineList.addMouseListener(sharedMouseListener)
    scrollPaneAncestorsLineList.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    scrollPaneAncestorsLineList.getHorizontalScrollBar().addMouseListener(sharedMouseListener)


    /////////////////////////////////////////////////////////





    ////////////////// Siblings panel /////////////////////



    DefaultListModel<NodeModel> siblingsModel = new DefaultListModel<>()
    if(node.isRoot()) {}
    else {
        node.parent.getChildren().each {
            siblingsModel.addElement(it)
        }
        //siblingsModel.removeElement(node)
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



    columnsPanel.add(scrollPaneAncestorsLineList);
    columnsPanel.add(scrollPanelSiblingsList);
    columnsPanel.add(scrollPaneChildrenList);

    JPanel verticalStackPanel = new JPanel()
    verticalStackPanel.setLayout(new BoxLayout(verticalStackPanel, BoxLayout.Y_AXIS))

    verticalStackPanel.add(buttonPanel, BorderLayout.NORTH)
    verticalStackPanel.add(textScrollPane, BorderLayout.NORTH)
    verticalStackPanel.add(columnsPanel, BorderLayout.NORTH)

    inspectorPanel.add(verticalStackPanel, BorderLayout.NORTH)

    verticalStackPanel.revalidate()

    inspectorPanel.setSize(calculateInspectorWidth(), (int) inspectorPanel.getPreferredSize().height)

    inspectorPanel.revalidate();
    inspectorPanel.repaint();


    /////////////////////////////////////////


    setInspectorLocation(inspectorPanel, sourcePanel, false)
    inspectorPanel.setVisible(true)
    parentPanel.add(inspectorPanel)
    parentPanel.setComponentZOrder(inspectorPanel, 0)
    parentPanel.revalidate()
    parentPanel.repaint()

    return inspectorPanel
}

void hideInspectorPanelIfNeeded() {
    if (freezeInspectors == true) {return}
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
                setInspectorLocation(visibleInspectors[0], masterPanel, true)
            }
        }

        bounds = masterPanel.getBounds()
        bounds.width = calculateRetractedWidthForMasterPanel()
        masterPanel.setBounds(bounds)
        isMasterPanelExpanded = false

        parentPanel.revalidate()
        parentPanel.repaint()
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
            if (freezeInspectors == true) {return}

            hoverTimer.stop()
            currentList = list
            currentListModel = listModel
            currentSourcePanel = sourcePanel
            lastMouseLocation = e.getPoint()
            hoverTimer.restart()

            bounds = masterPanel.getBounds()
            bounds.width = calculateExpandedWidthForMasterPanel()
            masterPanel.setBounds(bounds)
            isMasterPanelExpanded = true
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


private void saveSettings(){

    File file = getSettingsFile()

    List<String> pinnedItemsIds = pinnedItems.collect { it.id }

    String jsonString = "[" + pinnedItemsIds.collect { "\"$it\"" }.join(", ") + "]"

    try {
        file.text = jsonString
    }
    catch (Exception e) {
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

        List<String> pinnedItemsIds = new JsonSlurper().parseText(content)

        pinnedItems = pinnedItemsIds.collect { id ->
            Controller.currentController.map.getNodeForID(id)
        }.findAll { it != null }
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

def int calculateInspectorWidth() {
    width = mapViewWindowForSizeReferences.width / widthFactorForInspector
    return width
}

def setInspectorLocation(JPanel inspectorPanel, JPanel sourcePanel, boolean forceRetractedPosition) {
    int x

    if(isMasterPanelExpanded && !forceRetractedPosition) {
        x = sourcePanel.getLocation().x + calculateExpandedWidthForMasterPanel() + 5
    }
    else {
        x = sourcePanel.getLocation().x + calculateRetractedWidthForMasterPanel() + 5
    }
    int y = 0
    inspectorPanel.setLocation(x, y)
}
