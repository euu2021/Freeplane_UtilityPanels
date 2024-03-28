import groovy.transform.Field

import javax.swing.*
import javax.swing.border.TitledBorder
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.util.List
import javax.swing.event.ListSelectionListener
javax.swing.Timer

import org.freeplane.features.mode.Controller
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.INodeSelectionListener
import org.freeplane.features.nodestyle.NodeStyleController
import org.freeplane.features.styles.LogicalStyleController.StyleOption
import org.freeplane.features.ui.IMapViewChangeListener
import org.freeplane.features.map.IMapChangeListener
import org.freeplane.features.map.NodeDeletionEvent
import org.freeplane.features.link.NodeLinkModel

uniqueIdForScript = 999
deleteCurrentListenersFromPreviousExecutions()

@Field List<NodeModel> history = []
@Field List<NodeModel> pinnedItems = []
@Field List<JPanel> subInspectors = []

@Field JScrollPane parentPanel
@Field JPanel childPanel
@Field JPanel pinnedItemsPanel
@Field JPanel inspectorPanel

@Field boolean mouseOverInspector = false
@Field boolean mouseOverList = false

firstPanelHeight = 170
fontForItems = new Font("Dialog", Font.PLAIN, 10)

@Field Timer hideInspectorTimer = new Timer(500, null)

hideInspectorTimer.setRepeats(false)
hideInspectorTimer.addActionListener(e -> {
	hideInspectorPanelIfNeeded()
})

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
			return
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

Controller.currentController.modeController.getMapController().addUIMapChangeListener(myMapChangeListener)

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
	int childHeight = firstPanelHeight

	childPanel.setBounds(0, 0, childWidth, childHeight)

	parentPanel.add(childPanel)
	parentPanel.setComponentZOrder(childPanel, 0)
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
	parentPanel.setComponentZOrder(pinnedItemsPanel, 0)
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

	configureListFont(jList)

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
			if (value instanceof NodeModel) {
				NodeModel currentNode = (NodeModel) value
				configureLabelForNode(label, currentNode)
			}
			if (isSelected) {
				label.setBackground(list.getSelectionBackground())
				label.setForeground(list.getSelectionForeground())
			}
			return label
		}
	})

	configureListSelection(jList)
	configureListContextMenu(jList)

	jList.addMouseMotionListener(new MouseAdapter() {
		@Override
		void mouseMoved(MouseEvent e) {
			mouseOverList = true
			int index = jList.locationToIndex(e.getPoint())
			if (index >= 0) {
				NodeModel node = listModel.getElementAt(index)
				if (inspectorPanel == null) {
					inspectorPanel = createInspectorPanel(node)
				} else {
					inspectorPanel.removeAll()
					inspectorPanel.setVisible(false)
					subInspectors.each{
						it.setVisible(false)
					}
					inspectorPanel = createInspectorPanel(node)
				}

			}
			if (hideInspectorTimer.isRunning()) {
				hideInspectorTimer.stop()
			}
		}
	})

	jList.addMouseListener(new MouseAdapter() {
		@Override
		void mouseExited(MouseEvent e) {
			mouseOverList = false
			hideInspectorTimer.restart()
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


JPanel createInspectorPanel(NodeModel node) {

	JPanel inspectorPanel = new JPanel(new BorderLayout())
	inspectorPanel.setLayout(new BorderLayout())
	inspectorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK))
	inspectorPanel.setBackground(Color.WHITE)


	////////////// Node Text Panel ///////////////


	JTextArea textLabel = new JTextArea()

	textLabel.setSize(textLabel.getPreferredSize())
	configureLabelForNode(textLabel, node)

	JScrollPane textScrollPane = new JScrollPane(textLabel)
	textScrollPane.setPreferredSize(new Dimension(200, 200)) 
	textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
	textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED)


	MouseListener sharedMouseListener = new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			hideInspectorTimer.stop()
		}

		@Override
		public void mouseExited(MouseEvent e) {
			hideInspectorTimer.restart() 
		}
	}
	inspectorPanel.addMouseListener(sharedMouseListener)
	textLabel.addMouseListener(sharedMouseListener)
	textScrollPane.addMouseListener(sharedMouseListener)
	textScrollPane.getVerticalScrollBar().addMouseListener(sharedMouseListener)
	textScrollPane.getHorizontalScrollBar().addMouseListener(sharedMouseListener)

	/////////////////////////////////////////////////////////





	////////////////// Ancestors panel /////////////////////

	DefaultListModel<NodeModel> ancestorLineModel = new DefaultListModel<>()
	node.getPathToRoot().each {
		ancestorLineModel.addElement(it)
	}
	ancestorLineModel.removeElement(node)

	JList<NodeModel> ancestorsLineList = new JList<>(ancestorLineModel)
	TitledBorder titledBorderAncestors = BorderFactory.createTitledBorder("Ancestors")
	titledBorderAncestors.setTitleJustification(TitledBorder.CENTER)
	ancestorsLineList.setBorder(titledBorderAncestors)

	ancestorsLineList.setCellRenderer(new DefaultListCellRenderer() {
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
			if (value instanceof NodeModel) {
				NodeModel currentNode = (NodeModel) value
				configureLabelForNode(label, currentNode)
			}
			if (isSelected) {
				label.setBackground(list.getSelectionBackground())
				label.setForeground(list.getSelectionForeground())
			}
			return label
		}
	})

	configureListFont(ancestorsLineList)
	configureListSelection(ancestorsLineList)
	configureListContextMenu(ancestorsLineList)

	ancestorsLineList.addMouseMotionListener(new MouseAdapter() {
		@Override
		public void mouseMoved(MouseEvent e) {
			int index = ancestorsLineList.locationToIndex(e.getPoint())
			if (index >= 0) {
				NodeModel subNode = ancestorLineModel.getElementAt(index)
				subInspectorPanel = createInspectorPanel(subNode)
				int x = childPanel.getLocation().x + (childPanel.getWidth() + 5) * 3.4
				int y = childPanel.getLocation().y
				subInspectorPanel.setLocation(x, y)
				subInspectors.add(subInspectorPanel)
			}
		}
	})

	JScrollPane scrollPaneAncestorsLineList = new JScrollPane(ancestorsLineList)
	scrollPaneAncestorsLineList.setPreferredSize(new Dimension(200, 200)) 
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
		siblingsModel.removeElement(node)
	}

	JList<NodeModel> siblingsList = new JList<>(siblingsModel)
	TitledBorder titledBorderSiblings = BorderFactory.createTitledBorder("Siblings")
	titledBorderSiblings.setTitleJustification(TitledBorder.CENTER)
	siblingsList.setBorder(titledBorderSiblings)

	siblingsList.setCellRenderer(new DefaultListCellRenderer() {
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
			if (value instanceof NodeModel) {
				NodeModel currentNode = (NodeModel) value
				configureLabelForNode(label, currentNode) 
			}
			if (isSelected) {
				label.setBackground(list.getSelectionBackground())
				label.setForeground(list.getSelectionForeground())
			}
			return label
		}
	})

	configureListFont(siblingsList)
	configureListSelection(siblingsList)
	configureListContextMenu(siblingsList)

	siblingsList.addMouseMotionListener(new MouseAdapter() {
		@Override
		public void mouseMoved(MouseEvent e) {
			int index = siblingsList.locationToIndex(e.getPoint())
			if (index >= 0) {
				NodeModel subNode = siblingsModel.getElementAt(index)
				subInspectorPanel = createInspectorPanel(subNode)
				int x = childPanel.getLocation().x + (childPanel.getWidth() + 5) * 3.4
				int y = childPanel.getLocation().y
				subInspectorPanel.setLocation(x, y)
				subInspectors.add(subInspectorPanel)
			}
		}
	})

	JScrollPane scrollPanelSiblingsList = new JScrollPane(siblingsList)
	scrollPanelSiblingsList.setPreferredSize(new Dimension(200, 200))

	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, textScrollPane, scrollPanelSiblingsList)
	splitPane.setMinimumSize(new Dimension(600, 10))
	splitPane.setPreferredSize(new Dimension(200, 300))
	splitPane.setResizeWeight(0.5)



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
		TitledBorder titledBorderChildren = BorderFactory.createTitledBorder("Children")
		titledBorderChildren.setTitleJustification(TitledBorder.CENTER)
		childrenList.setBorder(titledBorderChildren)

		configureListFont(childrenList)
		configureListSelection(childrenList)
		configureListContextMenu(childrenList)

		childrenList.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
				if (value instanceof NodeModel) {
					NodeModel currentNode = (NodeModel) value
					configureLabelForNode(label, currentNode)
				}
				if (isSelected) {
					label.setBackground(list.getSelectionBackground())
					label.setForeground(list.getSelectionForeground())
				}
				return label
			}
		})

		childrenList.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int index = childrenList.locationToIndex(e.getPoint())
				if (index >= 0) {
					NodeModel subNode = childrenModel.getElementAt(index)
					subInspectorPanel = createInspectorPanel(subNode)
					int x = childPanel.getLocation().x + (childPanel.getWidth() + 5) * 3.4
					int y = childPanel.getLocation().y
					subInspectorPanel.setLocation(x, y)
					subInspectors.add(subInspectorPanel)
				}
			}
		})


		JScrollPane scrollPaneChildrenList = new JScrollPane(childrenList)
		scrollPaneChildrenList.setPreferredSize(new Dimension(200, 300)) 
		childrenList.addMouseListener(sharedMouseListener)
		scrollPaneChildrenList.getVerticalScrollBar().addMouseListener(sharedMouseListener)
		scrollPaneChildrenList.getHorizontalScrollBar().addMouseListener(sharedMouseListener)


	////////////////////////////////////////////////////



	/////////////// ConnectorsIn panel //////////////////////

	DefaultListModel<NodeModel> connectorsInModel = new DefaultListModel<>()
	node.children.each {
		connectorsInModel.addElement(it)
	}

	////////

//    DefaultListModel<NodeLinkModel> listOfConnectorsInModel = new DefaultListModel<>()




	////////




	JList<NodeModel> connectorsIn = new JList<>(connectorsInModel)
	connectorsIn.setBorder(BorderFactory.createTitledBorder("ConnectorsIn"))

	configureListFont(connectorsIn)
	configureListSelection(connectorsIn)
	configureListContextMenu(connectorsIn)

	connectorsIn.setCellRenderer(new DefaultListCellRenderer() {
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
			if (value instanceof NodeModel) {
				NodeModel currentNode = (NodeModel) value
				configureLabelForNode(label, currentNode)
			}
			if (isSelected) {
				label.setBackground(list.getSelectionBackground())
				label.setForeground(list.getSelectionForeground())
			}
			return label
		}
	})

	connectorsIn.addMouseMotionListener(new MouseAdapter() {
		@Override
		public void mouseMoved(MouseEvent e) {
			int index = connectorsIn.locationToIndex(e.getPoint())
			if (index >= 0) {
				NodeModel subNode = connectorsInModel.getElementAt(index)
				subInspectorPanel = createInspectorPanel(subNode)
				int x = childPanel.getLocation().x + (childPanel.getWidth() + 5) * 3.4
				int y = childPanel.getLocation().y
				subInspectorPanel.setLocation(x, y)
				subInspectors.add(subInspectorPanel)
			}
		}
	})


	JScrollPane scrollPaneConnectorsIn = new JScrollPane(connectorsIn)
	scrollPaneConnectorsIn.setPreferredSize(new Dimension(200, 300))
	connectorsIn.addMouseListener(sharedMouseListener)
	scrollPaneConnectorsIn.getVerticalScrollBar().addMouseListener(sharedMouseListener)
	scrollPaneConnectorsIn.getHorizontalScrollBar().addMouseListener(sharedMouseListener)


	////////////////////////////////////////////////////









	/////////////// ConnectorsOut panel //////////////////////










	////////////////////////////////////////////////////



	//////////// add the panels /////////////

	JPanel verticalStackPanel = new JPanel()
	verticalStackPanel.setLayout(new BoxLayout(verticalStackPanel, BoxLayout.Y_AXIS))

//    verticalStackPanel.add(scrollPaneConnectorsIn)
	verticalStackPanel.add(scrollPaneAncestorsLineList)
	verticalStackPanel.add(splitPane, BorderLayout.CENTER)
	verticalStackPanel.add(scrollPaneChildrenList)

	inspectorPanel.add(verticalStackPanel, BorderLayout.CENTER)



	/////////////////////////////////////////


	inspectorPanelHeightRelativeToChildPanel = firstPanelHeight * 2
	inspectorPanel.setSize(200, inspectorPanelHeightRelativeToChildPanel)

	int x = childPanel.getLocation().x + childPanel.getWidth() + 5
	int y = childPanel.getLocation().y
	inspectorPanel.setLocation(x, y)
	inspectorPanel.setVisible(true)
	parentPanel.add(inspectorPanel)
	parentPanel.setComponentZOrder(inspectorPanel, 0)
	parentPanel.revalidate()
	parentPanel.repaint()


	return inspectorPanel
}

void hideInspectorPanelIfNeeded() {
	if (!mouseOverInspector && !mouseOverList) {
		subInspectors.each{
			it.setVisible(false)
		}
		inspectorPanel.setVisible(false)
		parentPanel.revalidate()
		parentPanel.repaint()
		return
	}
	inspectorPanel.setVisible(true)
	parentPanel.revalidate()
	parentPanel.repaint()
}

void configureLabelForNode(JComponent component, NodeModel node) {
	Color backgroundColor = NodeStyleController.getController().getBackgroundColor(node, StyleOption.FOR_UNSELECTED_NODE)
	Color fontColor = NodeStyleController.getController().getColor(node, StyleOption.FOR_UNSELECTED_NODE)

	component.setBackground(backgroundColor)
	component.setForeground(fontColor)
	component.setFont(fontForItems)

	if (component instanceof JLabel) {
		JLabel label = (JLabel) component
		label.setText(node.text)
		if (pinnedItems.contains(node)){
			label.setText("📌" + label.getText())}
		currentMapView = Controller.currentController.MapViewManager.mapView
		if (currentMapView.currentRootParentView == null) {return}
		if (node.getPathToRoot().find{it == currentMapView.mapSelection.selectionRoot}){return}
		label.setText("⚠|" + label.getText())
		return

	} else if (component instanceof JTextArea) {
		JTextArea textArea = (JTextArea) component
		textArea.setText(node.text)
		textArea.setWrapStyleWord(true)
		textArea.setLineWrap(true)
		textArea.setEditable(false)
	}

	component.setOpaque(true)

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
					list.setSelectedIndex(index)
					NodeModel selectedItem = list.getModel().getElementAt(index)

					JPopupMenu popupMenu = new JPopupMenu()
					JMenuItem menuItem

					if (pinnedItems.contains(selectedItem)) {
						menuItem = new JMenuItem("Unpin")
						menuItem.addActionListener({
							pinnedItems.remove(selectedItem)
							updateAllGUIs()
						})
					} else {
						menuItem = new JMenuItem("Pin")
						menuItem.addActionListener({
							pinnedItems.add(selectedItem)
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
