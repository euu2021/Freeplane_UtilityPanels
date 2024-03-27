import groovy.transform.Field

import javax.swing.*
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.List
import javax.swing.event.ListSelectionListener

import org.freeplane.features.mode.Controller
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.INodeSelectionListener
import org.freeplane.features.nodestyle.NodeStyleController
import org.freeplane.features.styles.LogicalStyleController.StyleOption
import org.freeplane.features.ui.IMapViewChangeListener
import org.freeplane.features.map.IMapChangeListener
import org.freeplane.features.map.NodeDeletionEvent

uniqueIdForScript = 999
deleteCurrentListenersFromPreviousExecutions()

@Field List<NodeModel> history = []
@Field List<NodeModel> pinnedItems = []

@Field JScrollPane parentPanel
@Field JPanel childPanel
@Field JPanel pinnedItemsPanel

createPanels()

INodeSelectionListener mySelectionListener = new INodeSelectionListener() {
    @Override
    public void onDeselect(NodeModel node) {
        SwingUtilities.invokeLater { updateAllGUIs() }
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
    }
}

createdSelectionListener = mySelectionListener

Controller.currentController.modeController.mapController.addNodeSelectionListener(mySelectionListener)

SwingUtilities.invokeLater { updateAllGUIs() }

IMapViewChangeListener myMapViewChangeListener = new IMapViewChangeListener() {
    public void afterViewChange(final Component oldView, final Component newView) {
        if (newView == null) {
            return;
        }
        parentPanel.remove(childPanel)
        parentPanel.remove(pinnedItemsPanel)
        createPanels()
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
        SwingUtilities.invokeLater { updateAllGUIs() }
    }
}

createdMapChangeListener = myMapChangeListener

Controller.currentController.modeController.getMapController().addUIMapChangeListener(myMapChangeListener);

return




// ------------------ methods definitions ------------------------

def createPanels(){
    parentPanel = Controller.currentController.mapViewManager.mapView.parent.parent as JScrollPane
    Dimension parentSize = parentPanel.getSize()

    childPanel = new JPanel(new BorderLayout()) {
        protected void paintComponent(Graphics g)
        {
            g.setColor( getBackground() )
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    childPanel.setOpaque(false)
    childPanel.setBackground( new Color(0, 0, 0, 0) )

    int childWidth = 80
    int childHeight = 170

    childPanel.setBounds(0, 0, childWidth, childHeight)

    parentPanel.add(childPanel)
    parentPanel.setComponentZOrder(childPanel, 0);
    parentPanel.revalidate()
    parentPanel.repaint()

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
    pinnedItemsPanel.setBounds(0, childHeight + 20, childWidth, pinnedPanelHeight)

    parentPanel.add(pinnedItemsPanel)
    parentPanel.setComponentZOrder(pinnedItemsPanel, 0);
    parentPanel.revalidate()
    parentPanel.repaint()
}

def updateAllGUIs() {
    updateHistoryGui()
    updatePinnedItemsGui()
}

def updateHistoryGui() {
    updateSpecifiedGUIs(history, childPanel)
}

def updatePinnedItemsGui() {
    updateSpecifiedGUIs(pinnedItems, pinnedItemsPanel)
}

def updateSpecifiedGUIs(List<NodeModel> nodes, JPanel panel) {
    panel.removeAll()

    DefaultListModel<NodeModel> listModel = new DefaultListModel<>()
    nodes.each { listModel.addElement(it) }
    JList<NodeModel> jList = new JList<>(listModel)

    Font font = new Font("Dialog", Font.PLAIN, 10)
    jList.setFont(font)

    JScrollPane scrollPane = new JScrollPane(jList)
    scrollPane.setBackground(new Color(0, 0, 0, 0))
    jList.setOpaque(false)
    scrollPane.setOpaque(false)
    scrollPane.getViewport().setOpaque(false)

    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER)
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)

    jList.setCellRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
            NodeModel nodeItem = (NodeModel) value
            label.setFont(font)

            Color nodeBackgroundColor = NodeStyleController.getController().getBackgroundColor(nodeItem, StyleOption.FOR_UNSELECTED_NODE)
            Color nodeFontColor = NodeStyleController.getController().getColor(nodeItem, StyleOption.FOR_UNSELECTED_NODE)

            if (isSelected) {
                label.setBackground(list.getSelectionBackground())
                label.setForeground(list.getSelectionForeground())
            } else {
                label.setBackground(nodeBackgroundColor)
                label.setForeground(nodeFontColor)
            }
            if (pinnedItems.contains(nodeItem)){
                label.setText("📌" + label.getText())}
            currentMapView = Controller.currentController.MapViewManager.mapView
            if (currentMapView.currentRootParentView == null) {return label}
            if (nodeItem.getPathToRoot().find{it == currentMapView.mapSelection.selectionRoot}){return label}
            label.setText("⚠|" + label.getText())
            return label
        }
    })

    jList.addListSelectionListener({ e ->
        if (!e.getValueIsAdjusting()) {
            Integer selectedItemIndex = jList.getSelectedIndex()
            NodeModel selectedItemNode = nodes[selectedItemIndex]
            Controller.currentController.mapViewManager.mapView.getMapSelection().selectAsTheOnlyOneSelected(selectedItemNode)
        }
    } as ListSelectionListener)

    jList.addMouseListener(new MouseAdapter() {
        @Override
        void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                int index = jList.locationToIndex(e.getPoint())
                if (index >= 0) {
                    jList.setSelectedIndex(index)
                    NodeModel selectedItem = listModel.getElementAt(index)

                    JPopupMenu popupMenu = new JPopupMenu()

                    if (pinnedItems.contains(selectedItem)) {
                        JMenuItem unpinItem = new JMenuItem('Unpin')
                        unpinItem.addActionListener({
                            pinnedItems.remove(selectedItem)
                            updatePinnedItemsGui()
                            updateAllGUIs()
                        })
                        popupMenu.add(unpinItem)
                    } else {
                        JMenuItem pinItem = new JMenuItem('Pin')
                        pinItem.addActionListener({
                            pinnedItems.add(selectedItem)
                            updateAllGUIs()
                        })
                        popupMenu.add(pinItem)
                    }
                    popupMenu.show(e.getComponent(), e.getX(), e.getY())
                }
            }
        }
    })

    panel.add(scrollPane, BorderLayout.CENTER)
    panel.revalidate()
    panel.repaint()
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
