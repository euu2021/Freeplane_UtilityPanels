/***************************************************************************

 version 1.48: Updated to be compatible with Freeplane 1.12.12.

 version 1.47: Quick search panel: Lines connecting the results in the quick search panel to the respective nodes.
 Quick search panel: now, it's the first panel.
 Quick search panel: now, it shows the number of results and the status of the search.
 Recent nodes panel: changed the backend logic, to rely on the data from the Freeplane internal node history list. This is useful, to allow disabling the node change listener when the panels are hidden. The negative side effect is that recent nodes are not saved between sessions, anymore.
 Faster when the panels are hidden. Now, when hidden, most listeners are removed, to save performance.

 version 1.46: New Panel: In-place Siblings Preview.

 version 1.45: Bugfix: RTL not working in some panels. https://github.com/euu2021/Freeplane_UtilityPanels/issues/55
 Bugfix: included mouse listeners to the hatch overlay.
 Bugfix: inspectors recreation wasn't correct on master panel retract action.
 Purple border is back. Now, only in inspectors. https://github.com/euu2021/Freeplane_UtilityPanels/issues/52#issuecomment-2774213690

 version 1.44: New Panel: Styles Panel.

 version 1.43: Changed the appearance of the nodes, in the lists, that are not visible in the map. Now, they are also hatched.
 Even Smarter Update Selection: inspector panel only shows if any of the siblings or children of the current node are not visible in the map. https://github.com/euu2021/Freeplane_UtilityPanels/issues/54

 version 1.42: Bugfix: selecting a node on the inspector was closing the inspector. https://github.com/euu2021/Freeplane_UtilityPanels/issues/53#issue-2955896709
 Bugfix: leaving the inspector should go back to the selected node inspector. https://github.com/euu2021/Freeplane_UtilityPanels/issues/53#issuecomment-2763042689
 Fixed reserved area for inspector panels.
 Removed the Purple border around items that had inspector panels. https://github.com/euu2021/Freeplane_UtilityPanels/issues/52#issuecomment-2760093127

 version 1.41: Now, at the script startup, it checks if the script is already running. If it is, then the user is warned and the startup stops.
 Smart Update Selection: inspector panel only shows if any of the siblings or children of the current node are not visible in the map. https://github.com/euu2021/Freeplane_UtilityPanels/issues/52
 If a node is not appearing on the screen, it gets a Blue border on the lists.

 version 1.40: Bugfix: Transversal search was finding all descendants.
 Bugfix: on node delete the descendants were not removed from the recent nodes and pinned nodes lists.
 Bugfix: quicksearch panel was blinking if map overview was hidden. Fixed by adding repaints and revalidates.

 version 1.39: option to also hide the inspectors, even if "Update Selection" is activated. This option is active, by default. https://github.com/euu2021/Freeplane_UtilityPanels/issues/18#issuecomment-2724134882
 Bugfix: DnD not working when dragging from the map.
 Quicksearch was accidentally simplified on a previous version. Now, it has transversal search, again.
 Redesign of the buttons at the top of the inspector panel. https://github.com/euu2021/Freeplane_UtilityPanels/issues/31
 Bugfix: inspectors disappeared on map view change, even if Freeze was activated.
 Freeze inspectors: now, inspectors to the right of the frozen inspector are hidden. https://github.com/euu2021/Freeplane_UtilityPanels/issues/47#issue-2905561263
 Settings UI: on list item right-click contextual menu there is now a Settings option. The settings are saved in the json.
 Solved the performance degradation problem. The problem was that, on each mapview change, the createPanels() was called, but previous panels weren't properly cleared. https://github.com/euu2021/Freeplane_UtilityPanels/issues/41

 version 1.38: Hotkey toggle panels visibility. By default, Ctrl+U (can be configured in user settings). https://github.com/euu2021/Freeplane_UtilityPanels/issues/18#issuecomment-2565581115

 version 1.37: Visual feedback in drag and drop operation on lists in panels. Ie, while dragging, the items are highlighted in the list.
 Fixed label prefixes not showing on breadcrumbs panel.
 Quicksearch: now, uses a SwingWorker, to avoid locking the interface while searching.
 Quicksearch: results are limited to 500.
 Quicksearch: savedSearchCriteria combobox is limited to 200 items.
 Quicksearch: when cursor is in the quicksearch field, pressing ESC erases the search bar content.
 Add viewport reserved area methods for utility panels. https://github.com/euu2021/Freeplane_UtilityPanels/pull/45

 version 1.36: Included automatic import of the SwingX library.
 Bugfix first item of lists not being selected on hover. https://github.com/euu2021/Freeplane_UtilityPanels/issues/41#issuecomment-2645055560

 version 1.35: Improved responsiveness on node selection. Fixed unnecessary tag check of all nodes on each node selection.
 Fixed Index Out Of Bounds Exception on hovering over breadcrumbs panel.
 Updated to work with Freeplane 1.12.9 (stable). There was a change in the signature of the method isNodeHighlighted.

 version 1.34: Cleaned unused imports.
 Uniform Border Color for Selected Nodes Across All Panels.
 Fixed breadcrumbs panel not showing up when a new view was created.
 Fixed jumping text field.
 Improved responsiveness on node selection. Disabled listeners of listModels.

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

@Grab(group='org.swinglabs.swingx', module='swingx-core', version='1.6.5-1')

import groovy.lang.Lazy
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
import org.w3c.dom.ls.LSOutput

import javax.swing.*
import javax.swing.border.Border
import javax.swing.border.TitledBorder
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.event.ListSelectionListener
import javax.swing.SwingWorker
import javax.swing.SwingUtilities
import java.util.List
import java.util.ArrayList
import java.util.concurrent.ExecutionException
import java.awt.*
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.UnsupportedFlavorException
import java.awt.dnd.*
import java.awt.event.*
import java.util.List
import java.util.regex.Pattern
import javax.swing.SwingUtilities
import java.awt.Window

import java.awt.MouseInfo
import java.awt.Point
import java.awt.event.MouseEvent
import javax.swing.JComponent
import org.freeplane.core.extension.IExtension
import org.freeplane.core.ui.components.UITools
import org.freeplane.core.util.Compat
import org.freeplane.features.link.ConnectorModel
import org.freeplane.features.link.ConnectorShape
import org.freeplane.features.link.Connectors
import org.freeplane.features.link.LinkController
import org.freeplane.features.link.mindmapmode.MLinkController
import org.freeplane.features.map.FreeNode
import org.freeplane.features.map.NodeModel
import org.freeplane.features.map.NodeModel.Side
import org.freeplane.features.map.mindmapmode.MMapController
import org.freeplane.features.mode.Controller
import org.freeplane.features.mode.ModeController
import org.freeplane.features.mode.mindmapmode.MModeController
import org.freeplane.features.map.mindmapmode.InsertionRelation
import org.freeplane.features.styles.MapStyleModel
import org.freeplane.features.styles.MapViewLayout
import org.freeplane.view.swing.map.MapView
import org.freeplane.view.swing.map.MapViewScrollPane
import org.freeplane.view.swing.map.NodeView
import org.freeplane.view.swing.map.link.ConnectorView
import org.freeplane.view.swing.map.link.InclinationRecommender
import org.freeplane.view.swing.ui.DefaultMapMouseListener
import org.freeplane.plugin.script.proxy.ProxyUtils
import org.freeplane.plugin.script.proxy.ProxyFactory
import org.freeplane.plugin.script.proxy.ScriptUtils

import java.lang.reflect.Field
import org.freeplane.view.swing.map.MapViewController
import static javax.swing.JOptionPane.showMessageDialog

import org.freeplane.features.map.INodeView;
import org.freeplane.view.swing.map.NodeView
import org.freeplane.view.swing.map.MapView
import org.freeplane.features.map.mindmapmode.MMapController
import org.freeplane.features.mode.Controller
import org.freeplane.features.mode.Controller
import org.freeplane.view.swing.map.NodeView
import javax.swing.JViewport
import java.awt.Rectangle
import org.freeplane.plugin.script.proxy.ProxyUtils
import org.freeplane.plugin.script.proxy.ProxyFactory
import org.freeplane.plugin.script.proxy.ScriptUtils
import org.freeplane.view.swing.features.nodehistory.NodeHistory

import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.border.AbstractBorder

import org.freeplane.api.MindMap as ApiMindMap

import org.freeplane.plugin.script.proxy.NodeProxy as ProxyNode
import org.freeplane.features.map.MapModel;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.Controller;
import org.freeplane.features.styles.MapStyleModel;
import org.freeplane.plugin.script.ScriptContext
import org.freeplane.plugin.script.proxy.ScriptUtils

import java.awt.font.TextAttribute
import java.util.HashMap
import java.awt.geom.Line2D
import java.util.Random

import java.util.List
import java.util.Map
import java.util.Random
import java.awt.RenderingHints
import java.awt.BasicStroke

import javax.swing.event.ChangeListener
import javax.swing.event.ChangeEvent

@groovy.transform.Field uniqueIdForScript = 999
@groovy.transform.Field boolean isScrolling = false



if(checkIfUtilityPanelsIsAlreadyRunning()) return







/*

888     888
888     888
888     888
888     888 .d8888b   .d88b.  888d888
888     888 88K      d8P  Y8b 888P"
888     888 "Y8888b. 88888888 888
Y88b. .d88P      X88 Y8b.     888
 "Y88888P"   88888P'  "Y8888  888



 .d8888b.           888    888    d8b
d88P  Y88b          888    888    Y8P
Y88b.               888    888
 "Y888b.    .d88b.  888888 888888 888 88888b.   .d88b.  .d8888b
    "Y88b. d8P  Y8b 888    888    888 888 "88b d88P"88b 88K
      "888 88888888 888    888    888 888  888 888  888 "Y8888b.
Y88b  d88P Y8b.     Y88b.  Y88b.  888 888  888 Y88b 888      X88
 "Y8888P"   "Y8888   "Y888  "Y888 888 888  888  "Y88888  88888P'
                                                    888
                                               Y8b d88P
                                                "Y88P"

↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
*/

// remember to include new settings at the methods showSettingsDialog(), saveSettings() and loadSettings()

panelTextFontName = "Dialog"
panelTextFontSize = 15
minFontSize = 8
fontForListItens = Font.PLAIN

nodeTextPanelFixedHeight = 100

retractedWidthFactorForMasterPanel = 20
expandedWidthFactorForMasterPanel = 4
widthFactorForInspector = 15

@groovy.transform.Field selectionDelay = 150

reverseAncestorsList = false

paddingBeforeHorizontalScrollBar = 30

additionalInspectorDistanceToTheBottomOfTheScreen = 175

widthOfTheClearButtonOnQuickSearchPanel = 30

@groovy.transform.Field KeyStroke keyStrokeToQuickSearch = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK)

@groovy.transform.Field boolean showOnlyBreadcrumbs = false

showAncestorsOnFirstInspector = false

@groovy.transform.Field rtlOrientation = true

@groovy.transform.Field KeyStroke keyStrokeToShowPanels = KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK)

@groovy.transform.Field hideInspectorsEvenIfUpdateSelection = true

@groovy.transform.Field showInPlaceSiblingsPreview = true

@groovy.transform.Field showInspectorsOnSiblingsPreviewHover = true

@groovy.transform.Field boolean fastScrollToCenter = true

//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ User settings ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑









/*

888     888                 d8b          888      888
888     888                 Y8P          888      888
888     888                              888      888
Y88b   d88P 8888b.  888d888 888  8888b.  88888b.  888  .d88b.  .d8888b
 Y88b d88P     "88b 888P"   888     "88b 888 "88b 888 d8P  Y8b 88K
  Y88o88P  .d888888 888     888 .d888888 888  888 888 88888888 "Y8888b.
   Y888P   888  888 888     888 888  888 888 d88P 888 Y8b.          X88
    Y8P    "Y888888 888     888 "Y888888 88888P"  888  "Y8888   88888P'


↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
*/


@groovy.transform.Field DefaultListModel<String> allTags = new DefaultListModel<String>()

fontForItems = new Font(panelTextFontName, fontForListItens, panelTextFontSize)


@groovy.transform.Field DefaultListModel<NodeModel> ancestorsOfCurrentNode = new DefaultListModel<>()
@groovy.transform.Field DefaultListModel<NodeModel> history = new DefaultListModel<>()
@groovy.transform.Field DefaultListModel<NodeModel> pinnedItems = new DefaultListModel<>()
@groovy.transform.Field DefaultListModel<NodeModel> quickSearchResults = new DefaultListModel<>()

@groovy.transform.Field DefaultListModel<Tags> selectedTagsInPanelModel = new DefaultListModel<>()

//@groovy.transform.Field List<NodeModel> ancestorsOfCurrentNode = []
//@groovy.transform.Field List<NodeModel> history = []
//@groovy.transform.Field List<NodeModel> pinnedItems = []
//@groovy.transform.Field List<NodeModel> quickSearchResults = []
@groovy.transform.Field List<JPanel> visibleInspectors = []
@groovy.transform.Field List<JPanel> visiblePreviewInspectors = []
@groovy.transform.Field List<JPanel> inPlaceInspectors = []
@groovy.transform.Field List<String> savedSearchCriteria = ['']
@groovy.transform.Field List<Tags> selectedTagsInPanel = []
@groovy.transform.Field List<Tag> hoveredTag = []
@groovy.transform.Field DefaultListModel<NodeModel> nodesThatContainAnyTagInTagsSelectionModel = new DefaultListModel<>()
@groovy.transform.Field DefaultListModel<NodeModel> nodesThatContainHoveredTagModel = new DefaultListModel<>()
@groovy.transform.Field DefaultListModel<Tag> hoveredTagModel = new DefaultListModel<>()
@groovy.transform.Field JList <NodeModel> ancestorsJList = new JList<>()
@groovy.transform.Field JList <NodeModel> historyJList = new JList<>()
@groovy.transform.Field JList <NodeModel> quickSearchResultsJList = new JList<>()

@groovy.transform.Field DefaultListModel<String> listModelForAllTags = new DefaultListModel<>()

//@groovy.transform.Field JScrollPane parentPanel
@groovy.transform.Field JScrollPane parentPanel = Controller.currentController.mapViewManager.mapView.parent.parent as JScrollPane
@groovy.transform.Field JPanel masterPanel
@groovy.transform.Field JPanel breadcrumbPanel
@groovy.transform.Field List<JPanel> panelsInMasterPanels = []
@groovy.transform.Field List<JScrollPane> scrollPanelsInMasterPanelPanels = []
@groovy.transform.Field List <JPanel> activeSiblingPreviewPanels = []
@groovy.transform.Field JPanel recentSelectedNodesPanel
@groovy.transform.Field JPanel pinnedItemsPanel
@groovy.transform.Field JPanel tagsPanel
@groovy.transform.Field JPanel quickSearchPanel
@groovy.transform.Field JPanel innerPanelInQuickSearchPanel
@groovy.transform.Field JPanel inspectorPanel
@groovy.transform.Field List <JPanel> connectingLines = []

@groovy.transform.Field JPanel stylesPanel
@groovy.transform.Field DefaultListModel<ProxyNode> stylesListModel = new DefaultListModel<>()

@groovy.transform.Field JPanel currentSourcePanel

@groovy.transform.Field JComboBox<String> searchField

@groovy.transform.Field JTextField searchEditor

@groovy.transform.Field boolean mouseOverList = false
@groovy.transform.Field boolean freezeInspectors = false
@groovy.transform.Field boolean inspectorUpdateSelection = true
@groovy.transform.Field boolean isMasterPanelExpanded = false
@groovy.transform.Field boolean isMouseOverSearchBox = false
@groovy.transform.Field boolean tagsNeedUpdate = true
@groovy.transform.Field boolean showPanels = true

@groovy.transform.Field int lastMouseModifiers = 0


mapViewWindowForSizeReferences = Controller.currentController.mapViewManager.mapView.parent

@groovy.transform.Field String searchText = ""
@groovy.transform.Field String lastSearchText = ""
@groovy.transform.Field final String ELLIPSIS = "..."
@groovy.transform.Field final String INLINE_EDITOR_ACTIVE = "inline_editor_active"


@groovy.transform.Field List<Map<String, Object>> connectingLineData = []
@groovy.transform.Field JPanel linesOverlayPanel = null
@groovy.transform.Field Component overlayParentPanel = null



@groovy.transform.Field NodeModel currentlySelectedNode = Controller.currentController.MapViewManager.mapView.mapSelection.selectionRoot
@groovy.transform.Field NodeModel hoveredNode


@groovy.transform.Field MIconController iconController = (MIconController) Controller.currentModeController.getExtension(IconController.class)


@groovy.transform.Field int maxNumberOfResults = 500



@groovy.transform.Field Set<NodeModel> cachedHighlightedNodes = new HashSet<>()
@groovy.transform.Field Set<NodeModel> cachedHighlightedNodesTags = new HashSet<>()

@groovy.transform.Field DocumentListener searchTextBoxListener

@groovy.transform.Field final List<Color> COLORS_FOR_LIGHT_BG = [
        Color.BLUE,              // Standard Blue (vibrant)
        Color.RED,               // Standard Red (vibrant)
        new Color(0, 180, 0),    // Green (slightly darker, vibrant)
        Color.MAGENTA,           // Standard Magenta (vibrant)
        Color.CYAN,              // Cyan (can be too light, let's try Teal) // Note: Teal below is commented out
//        new Color(0, 150, 150),  // Teal (Darker Cyan)
        Color.ORANGE.darker(),   // Orange (darkened for contrast)
        new Color(138, 43, 226), // Purple/Violet (BlueViolet)
//        new Color(255, 20, 147), // Hot Pink (DeepPink)
//        new Color(0, 100, 200),  // Slightly different Blue
//        new Color(210, 105, 30)  // Chocolate Brown (contrast)
]

@groovy.transform.Field final List<Color> COLORS_FOR_DARK_BG = [
        Color.CYAN.brighter(),
        Color.MAGENTA.brighter(),
        Color.YELLOW.brighter(),
        new Color(100, 100, 255), // Light Blue
        Color.GREEN.brighter(),
        Color.ORANGE,
        Color.WHITE,
//        Color.LIGHT_GRAY,
        new Color(255, 000, 000),  // Red
        Color.PINK
]

@groovy.transform.Field Timer liveSearchTimer = new Timer(800, null)
liveSearchTimer.setRepeats(false)

@groovy.transform.Field Timer hideInspectorTimer = new Timer(500, null)

hideInspectorTimer.setRepeats(false)
hideInspectorTimer.addActionListener(e -> {
    hideInspectorPanelIfNeeded()
})

@groovy.transform.Field Timer scrollEndTimer = new Timer(300, null)
scrollEndTimer.setRepeats(false)
scrollEndTimer.addActionListener(e -> {
    isScrolling = false
    updateAllPanelsAfterScroll()
})

@groovy.transform.Field MouseListener sharedMouseListener

@groovy.transform.Field Timer hoverTimer = new Timer(selectionDelay, null)
@groovy.transform.Field Point lastMouseLocation = null

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

hoverTimer.setRepeats(false)
hoverTimer.addActionListener(e -> {
    manageInspectorsCreation()
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

//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Variables ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑








/*

888b     d888          d8b               .d8888b.                888
8888b   d8888          Y8P              d88P  Y88b               888
88888b.d88888                           888    888               888
888Y88888P888  8888b.  888 88888b.      888         .d88b.   .d88888  .d88b.
888 Y888P 888     "88b 888 888 "88b     888        d88""88b d88" 888 d8P  Y8b
888  Y8P  888 .d888888 888 888  888     888    888 888  888 888  888 88888888
888   "   888 888  888 888 888  888     Y88b  d88P Y88..88P Y88b 888 Y8b.
888       888 "Y888888 888 888  888      "Y8888P"   "Y88P"   "Y88888  "Y8888


↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
*/
//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Scroll Management Methods ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

def createScrollListener() {
    JScrollBar verticalScrollBar = parentPanel.getVerticalScrollBar()
    JScrollBar horizontalScrollBar = parentPanel.getHorizontalScrollBar()
    
    AdjustmentListener scrollListener = new AdjustmentListener() {
        @Override
        void adjustmentValueChanged(AdjustmentEvent e) {
            if (!isScrolling) {
                isScrolling = true
            }
            scrollEndTimer.restart()
            
            // حرکت دادن پنل‌های In-place Siblings Preview همراه با نقشه
            moveSiblingPreviewPanelsWithMap()
        }
    }
    
    verticalScrollBar.addAdjustmentListener(scrollListener)
    horizontalScrollBar.addAdjustmentListener(scrollListener)
}

//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Scroll Management Methods ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

def moveSiblingPreviewPanelsWithMap() {
    if (!showInPlaceSiblingsPreview || activeSiblingPreviewPanels.isEmpty()) return
    
    def mapView = Controller.currentController.MapViewManager.mapView
    def viewport = mapView.getParent()
    if (!(viewport instanceof JViewport)) return
    
    activeSiblingPreviewPanels.each { previewPanel ->
        NodeModel referenceNode = previewPanel.getClientProperty("referenceNode")
        if (referenceNode) {
            NodeView nodeView = mapView.getNodeView(referenceNode)
            if (nodeView) {
                Point nodePointOnMap = mapView.getNodeContentLocation(nodeView)
                Point nodePointOnScreen = SwingUtilities.convertPoint(mapView, nodePointOnMap, viewport)
                
                boolean positionAtBottom = previewPanel.getClientProperty("positionAtBottom") ?: false
                
                int newX = nodePointOnScreen.x
                int newY
                
                if (!positionAtBottom) {
                    newY = (nodePointOnScreen.y - previewPanel.getHeight()) as int
                } else {
                    newY = (nodePointOnScreen.y + nodeView.getContentPane().height) as int
                }
                
                // به روزرسانی موقعیت پنل بدون ایجاد پنل جدید
                previewPanel.setLocation(newX, newY)
            }
        }
    }
    
    // به روزرسانی موقعیت پنل‌های بازرس پیش‌نمایش
    movePreviewInspectorsWithMap()
}

def movePreviewInspectorsWithMap() {
    if (visiblePreviewInspectors.isEmpty()) return
    
    visiblePreviewInspectors.each { previewInspector ->
        JPanel sourcePanel = findSourcePanelForInspector(previewInspector)
        if (sourcePanel && (activeSiblingPreviewPanels.contains(sourcePanel) || visiblePreviewInspectors.contains(sourcePanel))) {
            setInspectorLocation(previewInspector, sourcePanel)
        }
    }
}

def findSourcePanelForInspector(JPanel inspector) {
    // این متد باید پنل منبع مربوط به این بازرس را پیدا کند
    for (panel in activeSiblingPreviewPanels + visiblePreviewInspectors) {
        if (panel != inspector) {
            return panel
        }
    }
    return null
}

def updateAllPanelsAfterScroll() {
    // به روزرسانی پنل‌های Siblings Preview
    if (showInPlaceSiblingsPreview) {
        refreshSiblingPreviewPanels()
    }
    
    // به روزرسانی پنل‌های بازرس
    if (inspectorUpdateSelection) {
        smartCreateInspectors(currentlySelectedNode)
    }
    
    // فعال کردن اسکرول‌بارها
    updateScrollBars()
    
    // به روزرسانی اتصالات
    ensureOverlayExistsAndRepaint()
    
    parentPanel.revalidate()
    parentPanel.repaint()
}

def updateScrollBars() {
    // اطمینان از فعال بودن اسکرول‌بارهای مورد نیاز
    panelsInMasterPanels.each { panel ->
        JScrollPane scrollPane = findScrollPaneInPanel(panel)
        if (scrollPane) {
            updateScrollBarVisibility(scrollPane)
        }
    }
    
    activeSiblingPreviewPanels.each { previewPanel ->
        JScrollPane scrollPane = findScrollPaneInPanel(previewPanel)
        if (scrollPane) {
            updateScrollBarVisibility(scrollPane)
        }
    }
    
    visiblePreviewInspectors.each { inspector ->
        JScrollPane scrollPane = findScrollPaneInPanel(inspector)
        if (scrollPane) {
            updateScrollBarVisibility(scrollPane)
        }
    }
}

def findScrollPaneInPanel(JPanel panel) {
    for (Component comp : panel.getComponents()) {
        if (comp instanceof JScrollPane) {
            return (JScrollPane) comp
        }
    }
    return null
}

def updateScrollBarVisibility(JScrollPane scrollPane) {
    // این منطق را بر اساس نیاز خود برای نمایش/مخفی کردن اسکرول‌بارها تطبیق دهید
    Component view = scrollPane.getViewport().getView()
    if (view instanceof JList) {
        JList list = (JList) view
        boolean needsVerticalScroll = list.getPreferredSize().height > scrollPane.getViewport().getHeight()
        boolean needsHorizontalScroll = list.getPreferredSize().width > scrollPane.getViewport().getWidth()
        
        scrollPane.setVerticalScrollBarPolicy(needsVerticalScroll ? 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED : JScrollPane.VERTICAL_SCROLLBAR_NEVER)
        scrollPane.setHorizontalScrollBarPolicy(needsHorizontalScroll ? 
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED : JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
    }
}

loadSettings()
createPanels()
createSingleRunListeners()
startListeners()
createHighlighters()

return

//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Main code ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑







/*

888b     d888          888    888                    888
8888b   d8888          888    888                    888
88888b.d88888          888    888                    888
888Y88888P888  .d88b.  888888 88888b.   .d88b.   .d88888 .d8888b
888 Y888P 888 d8P  Y8b 888    888 "88b d88""88b d88" 888 88K
888  Y8P  888 88888888 888    888  888 888  888 888  888 "Y8888b.
888   "   888 Y8b.     Y88b.  888  888 Y88..88P Y88b 888      X88
888       888  "Y8888   "Y888 888  888  "Y88P"   "Y88888  88888P'

↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
*/

def boolean checkIfUtilityPanelsIsAlreadyRunning() {
    def listenerFound = Controller.currentController.modeController.mapController.nodeSelectionListeners.find { listener ->
        try {
            return listener.uniqueIdForScript == uniqueIdForScript
        } catch (Exception ex) {
            return false
        }
    }

    if (listenerFound) {
        showMessageDialog(Controller.currentController.mapViewManager.mapView.parent.parent, "UtilityPanels already running. To show/hide it, use the hotkey (by default, it's Ctrl+U).")
        return true
    }
}

public void manageInspectorsCreation() {
    if (shouldFreeze()) return;
    
    if (panelsInMasterPanels.contains(currentSourcePanel)) {
        expandMasterPanelAndUpdateInspectorsState();
    }
    
    if (activeSiblingPreviewPanels.contains(currentSourcePanel)) {
        clearInspectorsAndPreviewInspectors();
    }
    
    if (!lastMouseLocation) return;
    
    int index = currentList.locationToIndex(lastMouseLocation);
    
    if (!validateMousePositionOnList(index)) return;
    
    Object hoveredItem = currentListModel.getElementAt(index);
    
    if (hoveredItem instanceof NodeModel) {
        NodeModel subNode = (NodeModel) hoveredItem;
        hoveredNode = subNode;
        
        populateAncestorsList(hoveredNode);
        
        if (panelsInMasterPanels.contains(currentSourcePanel) || currentSourcePanel == breadcrumbPanel) {
            cleanAndCreateInspectors(subNode, masterPanel);
        } else if (activeSiblingPreviewPanels.contains(currentSourcePanel)) {
            // فقط اگر تنظیم فعال باشد، پنل‌های بازرس نمایش داده می‌شوند
            if (showInspectorsOnSiblingsPreviewHover) {
                updatePreviewInspectors(subNode);
            }
            currentList.repaint();
        } else {
            // شرایط دیگر
            createSubInspector(hoveredNode, index, subNode);
        }
    } else if (currentSourcePanel == tagsPanel) {
        updateInspectorWithTagsPanel(index);
    }
    
    // دیباگ برای ردیابی
    println "Inspector Creation - Source: ${currentSourcePanel?.getClass()?.simpleName}, " +
            "In-place: ${activeSiblingPreviewPanels.contains(currentSourcePanel)}, " +
            "Preview: ${visiblePreviewInspectors.contains(currentSourcePanel)}"
}

public boolean validateMousePositionOnList(int index) {
    Rectangle cellBounds = currentList.getCellBounds(index, index)

    if (cellBounds == null || !cellBounds.contains(lastMouseLocation)) return false

    if (index < 0 || index >= currentListModel.getSize()) return false

    return true
}

public void createSubInspector(NodeModel hoveredNode, int index, NodeModel subNode) {
    JTextPane textLabelInInspector = (JTextPane) currentSourcePanel.getClientProperty("textLabel")

    configureLabelForNode(textLabelInInspector, hoveredNode, currentSourcePanel, index)

    DefaultListModel<Tags> accessorTagsInNodeModel = (DefaultListModel<Tags>) currentSourcePanel.getClientProperty("tagsInNodeAccessor")
    accessorTagsInNodeModel.clear()
    iconController.getTags(hoveredNode).each {
        accessorTagsInNodeModel.addElement(it)
    }

    subInspectorPanel = createInspectorPanel(subNode, currentSourcePanel)

    updateSubInspectorsLocation(subInspectorPanel)

    parentPanel.revalidate()
    parentPanel.repaint()
}

public void updateSubInspectorsLocation(JPanel subInspectorPanel) {
    if (visiblePreviewInspectors.contains(currentSourcePanel)) {
        visiblePreviewInspectors.add(subInspectorPanel)
        
        // فقط پنل‌هایی که بعد از پنل منبع هستند رو پنهان کن
        int sourceIndex = visiblePreviewInspectors.indexOf(currentSourcePanel)
        for (int i = visiblePreviewInspectors.size() - 1; i > sourceIndex; i--) {
            JPanel panel = visiblePreviewInspectors.get(i)
            if (panel != subInspectorPanel) {
                panel.setVisible(false)
                visiblePreviewInspectors.remove(i)
            }
        }
    } else {
        visibleInspectors.add(subInspectorPanel)
        
        // فقط پنل‌هایی که بعد از پنل منبع هستند رو پنهان کن  
        int sourceIndex = visibleInspectors.indexOf(currentSourcePanel)
        for (int i = visibleInspectors.size() - 1; i > sourceIndex; i--) {
            JPanel panel = visibleInspectors.get(i)
            if (panel != subInspectorPanel) {
                panel.setVisible(false)
                visibleInspectors.remove(i)
            }
        }
    }
}

public void updatePreviewInspectors(NodeModel subNode) {
    // ابتدا پنل‌های بازرس قبلی را پاک کنید
    visiblePreviewInspectors.each {
        it.setVisible(false)
        parentPanel.remove(it)
    }
    visiblePreviewInspectors.clear()

    // ایجاد پنل بازرس اول
    previewInspector = createInspectorPanel(subNode, currentSourcePanel)
    if (previewInspector != null) {
        visiblePreviewInspectors.add(previewInspector)
        
        // ایجاد پنل بازرس دوم (فرزندان)
        previewInspector2 = createInspectorPanel(subNode, previewInspector)
        if (previewInspector2 != null) {
            visiblePreviewInspectors.add(previewInspector2)
        }
    }
    
    // اطمینان از نمایش پنل Update Selection
    if (inspectorUpdateSelection && visibleInspectors.size() >= 2) {
        visibleInspectors[0].setVisible(true)
        visibleInspectors[1].setVisible(true)
    }
}

public void updateInspectorWithTagsPanel(int index) {
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

public void populateAncestorsList(NodeModel hoveredNode) {
    ancestorsOfCurrentNode.clear()
    if (reverseAncestorsList) {
        hoveredNode.getPathToRoot().reverse().each {
            ancestorsOfCurrentNode.addElement(it)
        }
    } else {
        hoveredNode.getPathToRoot().each {
            ancestorsOfCurrentNode.addElement(it)
        }
    }
}

public void clearInspectorsAndPreviewInspectors() {
    visiblePreviewInspectors.each {
        it.setVisible(false)
        parentPanel.remove(it)
    }

    visiblePreviewInspectors.clear()


    visibleInspectors.each {
        if (!inspectorUpdateSelection) {
            it.setVisible(false)
        } else {
            if (it != visibleInspectors[0] && it != visibleInspectors[1]) {
                it.setVisible(false)
            }
        }
    }

    if (!inspectorUpdateSelection) {
        visibleInspectors.clear()
    } else {
        visibleInspectors.removeAll { it != visibleInspectors[0] && it != visibleInspectors[1] }
    }
}

public void expandMasterPanelAndUpdateInspectorsState() {
    expandMasterPanel()

    visiblePreviewInspectors.each {
        it.setVisible(false)
        parentPanel.remove(it)
    }

    visiblePreviewInspectors.clear()


    visibleInspectors.each {
        if (!inspectorUpdateSelection) {
            it.setVisible(false)
        } else {
            if (it != visibleInspectors[0] && it != visibleInspectors[1]) {
                it.setVisible(false)
            }
        }
    }

    if (!inspectorUpdateSelection) {
        visibleInspectors.clear()
    } else {
        visibleInspectors.removeAll { it != visibleInspectors[0] && it != visibleInspectors[1] }
        if (visibleInspectors.size() != 0) {
            setInspectorLocation(visibleInspectors[0], masterPanel)
            if (visibleInspectors.size() > 1) {
                setInspectorLocation(visibleInspectors[1], visibleInspectors[0])
            }
        }
    }
}

private Rectangle getBreadcrumbReservedArea() {
    if (!breadcrumbPanel.isVisible())
        return MapViewScrollPane.EMPTY_RECTANGLE
    return breadcrumbPanel.getBounds()
}

private Rectangle getMasterReservedArea() {
    if (masterPanel == null || !masterPanel.isVisible())
        return MapViewScrollPane.EMPTY_RECTANGLE
    return masterPanel.getBounds()
}

// Add a new method to compute the hidden area for inspector panels.
private Rectangle getInspectorReservedArea() {
    Rectangle combinedBounds = null
    // Combine visibleInspectors and inPlaceInspectors to cover all inspector panels.
    visibleInspectors.each { inspector ->
        if (inspector != null && inspector.isVisible()) {
            Rectangle inspectorBounds = inspector.getBounds()

            if (combinedBounds == null) {
                combinedBounds = new Rectangle(0, inspectorBounds.@y, inspectorBounds.@x + inspectorBounds.@width, inspectorBounds.@height)
            } else {
                combinedBounds = combinedBounds.union(inspectorBounds)
                // Ensure combinedBounds.x remains at 0.
                if (combinedBounds.x != 0) {
                    combinedBounds.width += combinedBounds.x
                    combinedBounds.x = 0
                }
            }
        }
    }
    return combinedBounds ?: MapViewScrollPane.EMPTY_RECTANGLE
}

def createPanels() {
    setupParentPanel()
    createScrollListener()
    
    recentSelectedNodesPanel = createRecentNodesPanel()
    pinnedItemsPanel = createPinnedItemsPanel()
    tagsPanel = createTagsPanel()
    (quickSearchPanel, innerPanelInQuickSearchPanel) = createQuickSearchPanel()
    breadcrumbPanel = createAndAttachBreadcrumbsPanels()
    stylesPanel = createStylesPanel()

    if (!showOnlyBreadcrumbs) {
        assembleMasterPanel(breadcrumbPanel, quickSearchPanel, recentSelectedNodesPanel, pinnedItemsPanel, stylesPanel, tagsPanel, innerPanelInQuickSearchPanel)
    }

    parentPanel.revalidate()
    parentPanel.repaint()
}

public void assembleMasterPanel(JPanel breadcrumbPanel, JPanel quickSearchPanel, JPanel recentSelectedNodesPanel, JPanel pinnedItemsPanel, JPanel stylesPanel, JPanel tagsPanel, JPanel innerPanelInQuickSearchPanel) {
    masterPanel = new JPanel()
    basicMasterPanelConfigs(masterPanel, breadcrumbPanel)

    panelsInMasterPanels = [quickSearchPanel, recentSelectedNodesPanel, pinnedItemsPanel, stylesPanel, tagsPanel]
    scrollPanelsInMasterPanelPanels = []

    panelsInMasterPanels.eachWithIndex { panel, idx ->
        setRTLOrientation(panel)
        addPanelsWithSpacing(panel, idx)
    }

    createJLists(recentSelectedNodesPanel, pinnedItemsPanel, innerPanelInQuickSearchPanel, quickSearchPanel)

    createQuickSearchResultsScrollPane()

    masterPanel.revalidate()
    masterPanel.repaint()
    masterPanel.setVisible(true)

    parentPanel.add(masterPanel)
    parentPanel.setComponentZOrder(masterPanel, 0)
}

public void createQuickSearchResultsScrollPane() {
    JScrollPane quickSearchResultsScrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, quickSearchResultsJList);
    quickSearchResultsScrollPane.getViewport().addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            int firstVisibleIndex = quickSearchResultsJList.getFirstVisibleIndex();
            ensureOverlayExistsAndRepaint()
        }
    });
}

public void createJLists(JPanel recentSelectedNodesPanel, JPanel pinnedItemsPanel, JPanel innerPanelInQuickSearchPanel, JPanel quickSearchPanel) {
    loadHistoryWithFPHistory()
    historyJList = createJList(history, recentSelectedNodesPanel, recentSelectedNodesPanel)
    createJList(pinnedItems, pinnedItemsPanel, pinnedItemsPanel)
    quickSearchResultsJList = createJList(quickSearchResults, innerPanelInQuickSearchPanel, quickSearchPanel)
}

public void addPanelsWithSpacing(JPanel panel, int idx) {
    masterPanel.add(panel)

    if (idx < panelsInMasterPanels.size() - 1) {
        masterPanel.add(Box.createVerticalStrut(20))
    }
}

public void setRTLOrientation(JPanel panel) {
    if (rtlOrientation) {
        panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }
}

public void basicMasterPanelConfigs(JPanel masterPanel, JPanel breadcrumbPanel) {
    masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS))
    masterPanel.setOpaque(false)
    if (rtlOrientation) {
        masterPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        masterPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }
    masterPanel.addMouseListener(sharedMouseListener)
    
    // تغییر این خط - پنل اصلی در سمت راست
    int retractedWidth = calculateRetractedWidthForMasterPanel()
    int xPosition = rtlOrientation ? parentPanel.width - retractedWidth : 0
    
    masterPanel.setBounds(xPosition, breadcrumbPanel.height, retractedWidth, (int) mapViewWindowForSizeReferences.height - 5)
    
    println "Master Panel Initial Position: x=${xPosition}, width=${retractedWidth}, RTL=${rtlOrientation}"
}

public JPanel createStylesPanel() {
    stylesPanel = new JPanel(new BorderLayout()) {
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    stylesPanel.setOpaque(false)
    stylesPanel.setBackground(new Color(0, 0, 0, 0))

    if (rtlOrientation) {
        stylesPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        stylesPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }

    loadStylesIntoModel(stylesListModel)

    JList<ProxyNode> stylesJList = new JList<>(stylesListModel)

    configureListFont(stylesJList)
    if (rtlOrientation) {
        stylesJList.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        stylesJList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }
    stylesJList.setOpaque(false)
    stylesJList.setBackground(new Color(0, 0, 0, 0))


    stylesJList.setCellRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus)
            if (value instanceof NodeModel) {
                styleNode = ProxyFactory.createNode(value, ScriptUtils.getCurrentContext())
                label.setText(styleNode.text)
                label.setBackground(styleNode.style.backgroundColor)
                label.setForeground(styleNode.style.textColor)

                plainBoldItalic = Font.PLAIN
                if (styleNode.style.font.bold && node.style.font.italic) plainBoldItalic = Font.BOLD + Font.ITALIC
                else if (styleNode.style.font.bold) plainBoldItalic = Font.BOLD
                else if (styleNode.style.font.italic) plainBoldItalic = Font.ITALIC

                label.setFont(new Font(styleNode.style.font.name, plainBoldItalic, styleNode.style.font.size))

                Font baseFont = new Font(styleNode.style.font.name, plainBoldItalic, styleNode.style.font.size)

                Map attributes = new HashMap(baseFont.getAttributes())

                attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON)

                Font fontWithStrikethrough = baseFont.deriveFont(attributes)

                if (styleNode.style.font.strikedThrough) label.setFont(fontWithStrikethrough)

                label.setBorder(BorderFactory.createLineBorder(styleNode.style.border.color, 2))

            }
            return label
        }
    })

    stylesJList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                int index = stylesJList.locationToIndex(e.getPoint())
                if (index >= 0) {
                    selectedStyleNode = stylesListModel.getElementAt(index)
                    selectedStyleNodeProxy = ProxyFactory.createNode(selectedStyleNode, ScriptUtils.getCurrentContext())
                    String styleName = selectedStyleNodeProxy.text
                    if (styleName) {
                        def selectedMapNodes = c.selecteds
                        if (!selectedMapNodes.isEmpty()) {
                            selectedMapNodes.each { mapNode ->
                                mapNode.style.setName(styleName)
                            }
                        } else {
                        }
                    }
                }
            }
        }
    })

    stylesJList.addMouseListener(sharedMouseListener)


    JScrollPane scrollPaneStyles = new JScrollPane(stylesJList) {
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    configureScrollPaneForRTL(scrollPaneStyles)
    scrollPaneStyles.setOpaque(false)
    scrollPaneStyles.getViewport().setOpaque(false)
    scrollPaneStyles.setBackground(new Color(0, 0, 0, 0))
    scrollPaneStyles.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)

    scrollPaneStyles.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    scrollPaneStyles.getHorizontalScrollBar().addMouseListener(sharedMouseListener)
    addMouseListenerToScrollBarButtons(scrollPaneStyles.getVerticalScrollBar())
    addMouseListenerToScrollBarButtons(scrollPaneStyles.getHorizontalScrollBar())

    stylesPanel.add(scrollPaneStyles, BorderLayout.CENTER)

    stylesPanel.addMouseListener(sharedMouseListener)

    configureMouseMotionListener(stylesJList, stylesListModel, stylesPanel)
    configureMouseExitListener(stylesJList)
    stylesPanel
}

public JPanel createAndAttachBreadcrumbsPanels() {
    breadcrumbPanel = new JPanel() {
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
            super.paintComponent(g)
        }
    }
    
    // تغییر این خط - از LEFT به RIGHT برای راست‌چین
    breadcrumbPanel.setLayout(new FlowLayout(rtlOrientation ? FlowLayout.RIGHT : FlowLayout.LEFT, 8, 5))
    
    breadcrumbPanel.setBackground(new Color(0, 0, 0, 0))
    breadcrumbPanel.setOpaque(false)

    breadcrumbPanel.setBounds(0, 0, parentPanel.width, 40)

    // تغییر جهت کامپوننت
    if (rtlOrientation) {
        breadcrumbPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        breadcrumbPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }

    ancestorsOfCurrentNode.clear()
    if (reverseAncestorsList) {
        currentlySelectedNode.getPathToRoot().reverse().each {
            ancestorsOfCurrentNode.addElement(it)
        }
    } else {
        currentlySelectedNode.getPathToRoot().each {
            ancestorsOfCurrentNode.addElement(it)
        }
    }

    createBreadcrumbsJList()

    parentPanel.add(breadcrumbPanel)
    parentPanel.setComponentZOrder(breadcrumbPanel, 0)
    breadcrumbPanel
}

public List createQuickSearchPanel() {
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

    JLabel searchStatusLabel = new JLabel(" ")
    searchStatusLabel.setHorizontalAlignment(SwingConstants.CENTER)
    searchStatusLabel.setBackground(Color.WHITE)
    searchStatusLabel.setFont(fontForItems)
    searchStatusLabel.setOpaque(false)

//    JComboBox<String> searchField = new JComboBox<>(savedSearchCriteria.toArray(new String[0]))
    searchField = new JComboBox<>(savedSearchCriteria.toArray(new String[0]))

    searchField.setEditable(true)
    searchField.setSelectedItem("")

    searchEditor = (JTextField) searchField.getEditor().getEditorComponent()

    searchEditor.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clearSearch")
    searchEditor.getActionMap().put("clearSearch", new AbstractAction() {
        @Override
        void actionPerformed(ActionEvent e) {
//            searchField.setSelectedItem("")
//            quickSearchResults.clear()
//            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
//            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
            clearQuickSearch()
            retractMasterPanel()
        }
    })


    searchEditor.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            scheduleLiveSearch()

            parentPanel.revalidate()
            parentPanel.repaint()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            scheduleLiveSearch()
            parentPanel.revalidate()
            parentPanel.repaint()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            scheduleLiveSearch()
            parentPanel.revalidate()
            parentPanel.repaint()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
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

            parentPanel.revalidate()
            parentPanel.repaint()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
        }

        private void refreshList(String searchText) {
            quickSearchResults.clear()
            refreshHighlighterCache()

            if (!searchText.isEmpty()) {
//                expandMasterPanel()
                final NodeModel rootNode = Controller.currentController.getSelection().selectionRoot

                searchStatusLabel.setText("Searching...")
                searchStatusLabel.setOpaque(true)

                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    int resultCount = 0

                    @Override
                    protected Void doInBackground() throws Exception {
                        searchNodesRecursively(rootNode, searchText)
                        return null
                    }

                    @Override
                    protected void done() {
                        SwingUtilities.invokeLater({ ->
                            ensureOverlayExistsAndRepaint()
                            if (resultCount >= maxNumberOfResults) {
                                searchStatusLabel.setText("Max number of results (${maxNumberOfResults}) reached.")
                                searchStatusLabel.setOpaque(true)
                            } else {
                                searchStatusLabel.setText("${resultCount} results. Finished.")
                                searchStatusLabel.setOpaque(true)
                            }
                            parentPanel.revalidate()
                            parentPanel.repaint()
                            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
                            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
                        })
                    }


                    private void searchNodesRecursively(NodeModel node, String searchText2) {
                        if (resultCount >= maxNumberOfResults) {
                            return
                        };
//                        if (resultCount >= 500) return;

                        String nodeText = (node.text ?: "").toLowerCase();
                        String[] terms = searchText2.toLowerCase().split("\\s+");

                        List<String> foundDirect = [];
                        for (String term : terms) {
                            if (nodeText.contains(term)) {
                                foundDirect.add(term);
                            }
                        }

                        boolean nodeMatches = false;
                        if (!foundDirect.isEmpty()) {
                            nodeMatches = true;
                            for (String term : terms) {
                                if (!foundDirect.contains(term)) {
                                    boolean foundInAncestors = false;
                                    NodeModel ancestor = node.parent;
                                    while (ancestor != null && !foundInAncestors) {
                                        String ancestorText = (ancestor.text ?: "").toLowerCase();
                                        if (ancestorText.contains(term)) {
                                            foundInAncestors = true;
                                        }
                                        ancestor = ancestor.parent;
                                    }
                                    if (!foundInAncestors) {
                                        nodeMatches = false;
                                        break;
                                    }
                                }
                            }
                        }

                        if (nodeMatches) {
                            if (resultCount < maxNumberOfResults) {
                                resultCount++;
                                SwingUtilities.invokeLater({ -> quickSearchResults.addElement(node)
                                })
                            }
                        }

                        if (resultCount >= maxNumberOfResults) return;

                        for (child in node.children) {
                            if (resultCount >= maxNumberOfResults) break;
                            searchNodesRecursively(child, searchText2);
                        }
                    }


                }
                worker.execute()

                if (!savedSearchCriteria.contains(searchText)) {
                    savedSearchCriteria.add(0, searchText)
                } else {
                    savedSearchCriteria.remove(searchText)
                    savedSearchCriteria.add(0, searchText)
                }

                while (savedSearchCriteria.size() > 200) {
                    savedSearchCriteria.remove(savedSearchCriteria.size() - 1)
                }

                saveSettings()

                int caretPosition = searchEditor.getCaretPosition()

                searchField.removeAllItems()
                savedSearchCriteria.each { term ->
                    searchField.addItem(term)
                }

                searchEditor.setText(searchText)

                if (!searchField.isPopupVisible()) {
                    searchEditor.setCaretPosition(Math.min(caretPosition, searchText.length()))
                }
                parentPanel.revalidate()
                parentPanel.repaint()
                Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
                Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
            } else {
                searchStatusLabel.setText(" ")
                searchStatusLabel.setOpaque(false)
            }

        }
    })


    JButton clearButton = new JButton("X")
    clearButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearQuickSearch()
            retractMasterPanel()
//            updateAllGUIs()
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


    innerPanelInQuickSearchPanel.add(searchStatusLabel, BorderLayout.SOUTH)

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
    [quickSearchPanel, innerPanelInQuickSearchPanel]
}

public JPanel createTagsPanel() {
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


    NodeModel selectedNode = currentlySelectedNode

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
    tagsPanel
}

public JPanel createPinnedItemsPanel() {
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
    pinnedItemsPanel
}

public JPanel createRecentNodesPanel() {
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
    recentSelectedNodesPanel
}

public setupParentPanel() {
    parentPanel = Controller.currentController.mapViewManager.mapView.parent.parent as JScrollPane

    parentPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeToShowPanels, "togglePanels")
    parentPanel.getActionMap().put("togglePanels", new AbstractAction() {
        @Override
        void actionPerformed(ActionEvent e) {
            togglePanelsVisibility()
        }
    })

    parentPanel.addViewportReservedAreaSupplier(this::getBreadcrumbReservedArea)
    parentPanel.addViewportReservedAreaSupplier(this::getMasterReservedArea)
//    parentPanel.addViewportReservedAreaSupplier(this::getInspectorReservedArea)
    parentPanel
}

def updateAllGUIs() {
//    updateBreadcrumbPanel()
//    updateRecentNodesGui()
//    updatePinnedItemsGui()
//    updateQuickSearchGui()
//    updateTagsGui()
}


JPanel createInspectorPanel(NodeModel nodeNotProxy, JPanel sourcePanel, boolean dummyMode = false) {


    dummyPanel2 = dummyMode

//    isPreviewInspector = false
//
//    if(activeSiblingPreviewPanels.contains(sourcePanel) || (visibleInspectors.contains(sourcePanel) && sourcePanel.isPreviewInspector == true)) {
//        isPreviewInspector = true
//    }


    JPanel inspectorPanel = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g)
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
        }
    }

    inspectorPanel.putClientProperty("referenceNode", nodeNotProxy)

    // ★★★ تغییر اصلی: شرط ساده‌تر برای نمایش پنل‌ها ★★★
    boolean shouldCreateInspector = true
    
    if (!showPanels && hideInspectorsEvenIfUpdateSelection) {
        // فقط برای پنل‌های خاص اجازه نمایش بده
        if (!activeSiblingPreviewPanels.contains(sourcePanel) && 
            !visiblePreviewInspectors.contains(sourcePanel) &&
            sourcePanel != masterPanel) {
            shouldCreateInspector = false
        }
    }
    
    if (showOnlyBreadcrumbs) {
        if (!activeSiblingPreviewPanels.contains(sourcePanel) && 
            !visiblePreviewInspectors.contains(sourcePanel)) {
            shouldCreateInspector = false
        }
    }
    
    if (!shouldCreateInspector) {
        println "Inspector creation blocked for: ${sourcePanel?.getClass()?.simpleName}"
        return inspectorPanel
    }

    // ★★★ اگر به اینجا رسیدیم، پنل را ایجاد کن ★★★
    inspectorPanel.setLayout(new BorderLayout())
//    if (currentlySelectedNode == nodeNotProxy) {
//        inspectorPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 5))
//    }
//    else {inspectorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5))}
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

    if(visibleInspectors.size() == 0 || activeSiblingPreviewPanels.contains(sourcePanel)) {
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

    JPanel buttonPanel = new JPanel(new FlowLayout(rtlOrientation ? FlowLayout.RIGHT : FlowLayout.LEFT, 5, 5))
    buttonPanel.setBackground(Color.LIGHT_GRAY)

    JButton hamburgerButton = new JButton("☰")
    hamburgerButton.setOpaque(true)
    hamburgerButton.setBorderPainted(false)
    hamburgerButton.setFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))

    JPopupMenu menu = new JPopupMenu()

    JCheckBoxMenuItem freezeItem = new JCheckBoxMenuItem("Freeze", freezeInspectors)
    freezeItem.addActionListener(e -> {
        freezeInspectors = freezeItem.getState()
        if (freezeInspectors) {
            inspectorPanel.setBorder(BorderFactory.createLineBorder(new Color(0,255,255), 5))
            int frozenIndex = visibleInspectors.indexOf(inspectorPanel)
            for (int i = visibleInspectors.size() - 1; i > frozenIndex; i--) {
                JPanel panelToRemove = visibleInspectors.get(i)
                panelToRemove.setVisible(false)
                visibleInspectors.remove(i)
            }
        } else {
            inspectorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5))
        }
    })

    JCheckBoxMenuItem updateItem = new JCheckBoxMenuItem("Update Selection", inspectorUpdateSelection)
    updateItem.addActionListener(e -> {
        inspectorUpdateSelection = updateItem.getState()
    })



    menu.add(freezeItem)
    if (visibleInspectors.size() == 0) {
        menu.add(updateItem)
    }

    hamburgerButton.addActionListener(e -> {
        menu.show(hamburgerButton, 0, hamburgerButton.getHeight())
    })

    buttonPanel.add(hamburgerButton)

    buttonPanel.addMouseListener(sharedMouseListener)
    hamburgerButton.addMouseListener(sharedMouseListener)
    freezeItem.addMouseListener(sharedMouseListener)
    updateItem.addMouseListener(sharedMouseListener)


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
//    ancestorsLineList.revalidate()
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
//    siblingsList.revalidate()
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
//    childrenList.revalidate()
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
//    tagsInNode.revalidate()
//    tagsInNode.repaint()
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
//    tagsSelectedList.revalidate()
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
        tagsNeedUpdate = false
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
//    nodesThatContainAnyTagInTagsSelection.revalidate()
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


    if((panelsInMasterPanels.contains(sourcePanel) && visibleInspectors.size() == 0) || (activeSiblingPreviewPanels.contains(sourcePanel) && visiblePreviewInspectors.size() == 0)) {
        columnsPanel.add(scrollPanelSiblingsList)
        ammountOfPannelsInInspector++
    }
//    else{
//        JPanel line = new JPanel()
//        line.setBackground(Color.GRAY)
//        line.setPreferredSize(new Dimension(10, 3))
//        line.setBorder(BorderFactory.createLineBorder(Color.RED, 2))
//
//        columnsPanel.add(line)
//    }
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

//    if(visibleInspectors.size() != 0 || visiblePreviewInspectors.size() != 0) {
    else {
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

//    verticalStackPanel.revalidate()

    inspectorPanel.setSize(calculateInspectorWidth(ammountOfPannelsInInspector), (int) Math.min(mapViewWindowForSizeReferences.height, inspectorPanel.getPreferredSize().height))

//    inspectorPanel.revalidate()
//    inspectorPanel.repaint()


    /////////////////////////////////////////
    if(dummyPanel2 == true) {
        ancestorsLineList.setVisible(false)
        siblingsList.setVisible(false)
        childrenList.setVisible(false)
        tagsInNode.setVisible(false)
        tagsSelectedList.setVisible(false)
        nodesThatContainAnyTagInTagsSelection.setVisible(false)
        verticalStackPanel.setVisible(false)
    }

    setInspectorLocation(inspectorPanel, sourcePanel)
    inspectorPanel.setVisible(true)
    parentPanel.add(inspectorPanel)
    parentPanel.setComponentZOrder(inspectorPanel, 0)

    SwingUtilities.invokeLater({
        ancestorsLineList.revalidate()
        siblingsList.revalidate()
        childrenList.revalidate()
        tagsInNode.revalidate()
        tagsInNode.repaint()
        tagsSelectedList.revalidate()
        nodesThatContainAnyTagInTagsSelection.revalidate()
        verticalStackPanel.revalidate()
        inspectorPanel.revalidate()
        inspectorPanel.repaint()
    })

//    parentPanel.revalidate()
//    parentPanel.repaint()
    parentPanel.addViewportReservedAreaSupplier(this::getInspectorReservedArea)

    // دیباگ نهایی
    println "Inspector created successfully for: ${nodeNotProxy.text.take(20)}..."
    
    return inspectorPanel
}

JPanel createSiblingPreviewPanel(NodeModel nodeNotProxy, boolean positionAtBottom = false, int referenceNodeScreenX, int referenceNodeScreenY) {


    nodeProxy = ProxyFactory.createNode(nodeNotProxy, ScriptUtils.getCurrentContext())

    JPanel siblingsPreviewPanel = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g)
            g.setColor(getBackground())
            g.fillRect(0, 0, getWidth(), getHeight())
        }
    }

    siblingsPreviewPanel.putClientProperty("referenceNode", nodeNotProxy)
    siblingsPreviewPanel.putClientProperty("positionAtBottom", positionAtBottom)

    siblingsPreviewPanel.setLayout(new BorderLayout())
//    siblingsPreviewPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1))
    siblingsPreviewPanel.setBackground(Color.LIGHT_GRAY)




    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Siblings panel ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    DefaultListModel<NodeModel> siblingsUpOrDownModel = new DefaultListModel<>()


    if (!positionAtBottom){

        siblingsUP = []
        nodeProxy.parent.children.each {
            if(it.parent.children.indexOf(it) < nodeProxy.parent.children.indexOf(nodeProxy)) {
                siblingsUP.add(it.delegate)
            }
        }

        siblingsUP.each {
            if(!isNodeVisibleInViewport(it)) {
                siblingsUpOrDownModel.addElement(it)
            }
        }


    }
    else {

        siblingsDown = []
        nodeProxy.parent.children.each {
            if(it.parent.children.indexOf(it) > nodeProxy.parent.children.indexOf(nodeProxy)) {
                siblingsDown.add(it.delegate)
            }
        }

        siblingsDown.each {
            if(!isNodeVisibleInViewport(it)) {
                siblingsUpOrDownModel.addElement(it)
            }
        }

    }


    JList<NodeModel> siblingsList = new JList<>(siblingsUpOrDownModel)


    JScrollPane scrollPanelSiblingsList = new JScrollPane(siblingsList)
    horizontalScrollBarOriginalHeight = 0
    if (scrollPanelSiblingsList.getHorizontalScrollBar().isVisible()) horizontalScrollBarOriginalHeight = scrollPanelSiblingsList.getHorizontalScrollBar().getPreferredSize().height
    scrollPanelSiblingsList.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0))

    final int MAX_VISIBLE_SIBLING_ROWS = 7
    int numItems = siblingsUpOrDownModel.getSize()

    if (numItems > 0) {
        int visibleRows = Math.min(numItems, MAX_VISIBLE_SIBLING_ROWS)
        visibleRows = Math.max(1, visibleRows)

        siblingsList.setVisibleRowCount(visibleRows)

        int preferredWidth = 100
        scrollPanelSiblingsList.setPreferredSize(new Dimension(preferredWidth, scrollPanelSiblingsList.getPreferredSize().height as int))

    }



    if(!positionAtBottom) {
        SwingUtilities.invokeLater {

            if (scrollPanelSiblingsList.getVerticalScrollBar().isVisible()) scrollPanelSiblingsList.getVerticalScrollBar().setValue(scrollPanelSiblingsList.getVerticalScrollBar().getMaximum())

        }
    }




    commonJListsConfigs(siblingsList, siblingsUpOrDownModel, siblingsPreviewPanel)

//    TitledBorder titledBorderSiblings = BorderFactory.createTitledBorder("Siblings")
//    titledBorderSiblings.setTitleJustification(TitledBorder.LEFT)
//    titledBorderSiblings.setTitleFont(new Font(panelTextFontName, Font.PLAIN, panelTextFontSize))
//    siblingsList.setBorder(titledBorderSiblings)

    configureScrollPaneForRTL(scrollPanelSiblingsList)


    siblingsList.addMouseListener(sharedMouseListener)
    scrollPanelSiblingsList.getVerticalScrollBar().addMouseListener(sharedMouseListener)
    scrollPanelSiblingsList.getHorizontalScrollBar().addMouseListener(sharedMouseListener)
    addMouseListenerToScrollBarButtons(scrollPanelSiblingsList.getVerticalScrollBar())
    addMouseListenerToScrollBarButtons(scrollPanelSiblingsList.getHorizontalScrollBar())








    //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Siblings panel ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑



    siblingsPreviewPanel.add(scrollPanelSiblingsList, BorderLayout.CENTER)

    siblingsPreviewPanel.setLocation(referenceNodeScreenX as int, 0)


    siblingsPreviewPanel.setVisible(true)
    parentPanel.add(siblingsPreviewPanel)
    parentPanel.setComponentZOrder(siblingsPreviewPanel, 0)

    Dimension preferredSize = siblingsPreviewPanel.getPreferredSize()

    int currentX = siblingsPreviewPanel.getX()
    int currentY = siblingsPreviewPanel.getY()
    int calculatedWidth = preferredSize.width
    int calculatedHeight = preferredSize.height


    def mapView = Controller.currentController.MapViewManager.mapView
    def viewport = mapView.getParent()
    if (!(viewport instanceof JViewport)) {
        return
    }

    NodeView nodeView = mapView.getNodeView(nodeNotProxy)
    if(nodeView == null) {
        return
    }

    def pointOnMap = mapView.getNodeContentLocation(nodeView)
    verticalSpaceToViewportEdge = 600
    if (pointOnMap != null) {

        UITools.convertPointToAncestor(mapView, pointOnMap, viewport)

        verticalSpaceToViewportEdge = pointOnMap.y

        if(positionAtBottom) {
            verticalSpaceToViewportEdge = (viewport.getHeight() - pointOnMap.y - nodeView.getContentPane().height) as int
        }
    }


    int margin = 5
    currentY = referenceNodeScreenY - calculatedHeight - margin
    currentY = Math.max(0, currentY)

    maxHeight = (int) Math.min(siblingsList.getPreferredSize().height as int, verticalSpaceToViewportEdge)

    nodeWidth = nodeView.getContentPane().width

    siblingsPreviewPanel.setSize(nodeWidth as int, maxHeight as int + horizontalScrollBarOriginalHeight as int)

//    if(!positionAtBottom) {
//        siblingsPreviewPanel.setLocation(referenceNodeScreenX as int, 0)
//
//    }
//    else {
//        siblingsPreviewPanel.setLocation(referenceNodeScreenX as int, viewport.getHeight() as int - siblingsPreviewPanel.getHeight() as int)
//    }

    if(!positionAtBottom) {
        siblingsPreviewPanel.setLocation(referenceNodeScreenX as int, (referenceNodeScreenY - siblingsPreviewPanel.getHeight()) as int)

    }
    else {
        siblingsPreviewPanel.setLocation(referenceNodeScreenX as int, (referenceNodeScreenY + nodeView.getContentPane().height) as int)
    }

    parentPanel.revalidate()
    parentPanel.repaint()

    return siblingsPreviewPanel
}



void hideInspectorPanelIfNeeded() {
    if (shouldFreeze()) return
    if (mouseOverList) return

    // پنل‌های پیش‌نمایش را مخفی نکن اگر مربوط به In-place Siblings Preview هستند
    def previewToRemove = []
    visiblePreviewInspectors.each {
        if (!activeSiblingPreviewPanels.contains(currentSourcePanel)) {
            it.setVisible(false)
            parentPanel.remove(it)
            previewToRemove.add(it)
        }
    }
    visiblePreviewInspectors.removeAll(previewToRemove)

    // پنل‌های بازرس اصلی را مدیریت کن
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
        visibleInspectors.removeAll { it != visibleInspectors[0] && it != visibleInspectors[1] }
        if(visibleInspectors.size() != 0) {
            setInspectorLocation(visibleInspectors[0], masterPanel)
            if(visibleInspectors.size() > 1) {
                setInspectorLocation(visibleInspectors[1], visibleInspectors[0])
            }
        }
    }

    retractMasterPanel()
}

void configureLabelForNode(JComponent component, NodeModel nodeNotProxy, JPanel sourcePanel, int indexOfNode = 0) {
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

//        if(currentMapView.getNodeView(nodeNotProxy) == null || !isNodeOnScreen(nodeNotProxy)) {
//            label.setBorder(BorderFactory.createLineBorder(Color.BLUE, 4))
//        }


        if (currentMapView.getNodeView(nodeNotProxy) == null || !isNodeOnScreen(nodeNotProxy)) {
//            label.setBorder(BorderFactory.createLineBorder(Color.BLUE, 4))
//            label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1))
            label.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 150), 2))
//            label.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2))
//            label.setBorder(new DottedBorder(new Color(0, 0, 150), 4))

            label.overlayEnabled = true
            label.useHatch = true
        } else {
            label.overlayEnabled = false
        }



        if (currentlySelectedNode == nodeNotProxy) {
            label.setBorder(BorderFactory.createLineBorder(Color.RED, 4))
        }

        else if(!panelsInMasterPanels.contains(sourcePanel) && sourcePanel != breadcrumbPanel) {
    // فقط برای پنل‌های In-place Siblings Preview و پنل‌های بازرس مربوط به آنها
    if (activeSiblingPreviewPanels.contains(sourcePanel) || visiblePreviewInspectors.contains(sourcePanel)) {
        // اگر این گره هاور شده است، خط بنفش نشان بده
        if (hoveredNode == nodeNotProxy) {
            label.setBorder(BorderFactory.createLineBorder((new Color(160, 32, 240, 255)), 4))
        }
    }
    // برای پنل‌های بازرس معمولی (اگر لازم است)
    else if (visibleInspectors.size() != 0 && visibleInspectors.any { it.getClientProperty("referenceNode") == nodeNotProxy }) {
        label.setBorder(BorderFactory.createLineBorder((new Color(160, 32, 240, 255)), 4))
    }
}

        if(sourcePanel == quickSearchPanel) {
            Color mapBackgroundColor = Controller.currentController.MapViewManager.mapView.getBackground()
            if (mapBackgroundColor == null) {
                mapBackgroundColor = currentMapView.getBackground()
            }

            boolean backgroundIsDark = isColorDark(mapBackgroundColor)

            List<Color> colorPalette = backgroundIsDark ? COLORS_FOR_DARK_BG : COLORS_FOR_LIGHT_BG


            Color itemColor = colorPalette[indexOfNode % colorPalette.size()]

            label.setBorder(BorderFactory.createMatteBorder(2, 8, 2, 8, itemColor))
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

//    if(theListModel.size() > 0) {
//        connectingLines.each {it.visible = false}
//        connectingLines = []
//        aaa = theJlist == historyJList
//        bbb = theListModel == history
//        ccc = thePanelPanel == recentSelectedNodesPanel
//        if (thePanelPanel == recentSelectedNodesPanel) {
//            for (int i = 0; i < theListModel.size() - 1; i++) {
//                connectListItemToNode(theJlist, i)
//            }
//        }
////        connectListItemToNode(theJlist, 0)
//    }

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
                flagFreezeInspectorsWasOn = freezeInspectors
                if(!flagFreezeInspectorsWasOn) freezeInspectors = true
                currentlySelectedNode = selectedItemNode
                
                // ★★★ ابتدا همه پنل‌های بازرس را پاک کن ★★★
                hideAllInspectorPanels()
                
                // سپس گره را انتخاب کن
                Controller.currentController.mapViewManager.mapView.getMapSelection().selectAsTheOnlyOneSelected(selectedItemNode)
                
                // ★★★ در نهایت اسکرول کن ★★★
                if (fastScrollToCenter) {
                    Controller.currentController.mapViewManager.mapView.getMapSelection().scrollNodeToCenter(selectedItemNode, false)
                }
                
                freezeInspectors = flagFreezeInspectorsWasOn
            }
        }
    } as ListSelectionListener)
}

// ★★★ قدم ۲: اضافه کردن این متد جدید ★★★
def hideAllInspectorPanels() {
    // پاک کردن پنل‌های بازرس پیش‌نمایش
    visiblePreviewInspectors.each {
        it.setVisible(false)
        parentPanel.remove(it)
    }
    visiblePreviewInspectors.clear()
    
    // پاک کردن پنل‌های بازرس معمولی (اگر freeze فعال نباشد)
    if (!freezeInspectors) {
        visibleInspectors.each {
            if (it != visibleInspectors[0] && it != visibleInspectors[1]) {
                it.setVisible(false)
                parentPanel.remove(it)
            }
        }
        visibleInspectors.removeAll { it != visibleInspectors[0] && it != visibleInspectors[1] }
    }
    
    // ریتکت کردن پنل اصلی
    retractMasterPanel()
    
    // به‌روزرسانی رابط
    parentPanel.revalidate()
    parentPanel.repaint()
}

void configureListContextMenu(JList<NodeModel> list) {
    list.addMouseListener(new MouseAdapter() {
        @Override
        void mousePressed(MouseEvent e) {
//            if (shouldShowInspectors()) { return }
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
                        })
                    } else {
                        menuItem = new JMenuItem("Pin")
                        menuItem.addActionListener({
                            pinnedItems.addElement(selectedItem)
                            saveSettings()
                        })
                    }

                    JMenuItem menuItem2 = new JMenuItem("Open in new View")
                    menuItem2.addActionListener({
                        menuUtils.executeMenuItems(['NewMapViewAction'])
                        SwingUtilities.invokeLater {
                            Controller.currentController.mapViewManager.mapView.getMapSelection().selectAsTheOnlyOneSelected(selectedItem)
                        }
                    })

                    menuItem.addMouseListener(sharedMouseListener)
                    popupMenu.add(menuItem)
                    popupMenu.add(menuItem2)

                    popupMenu.addSeparator()
                    JMenuItem settingsItem = new JMenuItem("Settings")
                    settingsItem.addActionListener({ evt ->
                        showSettingsDialog()
                    })
                    settingsItem.addMouseListener(sharedMouseListener)
                    popupMenu.add(settingsItem)

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
                    void scrollNodeToCenter(NodeModel node, boolean slow) {

                    }

                    @Override
                    void scrollNodeToCenter(NodeModel node) {

                    }

                    @Override
                    void scrollNodeTreeToVisible(final NodeModel node, boolean slow) {

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
        public void dragOver(DropTargetDragEvent dtde) {
            Point dropLocation = dtde.getLocation()
            int index = list.locationToIndex(dropLocation)
            list.putClientProperty("dropTargetIndex", index)
            list.repaint()
        }

        @Override
        public void dragExit(DropTargetEvent dte) {
            list.putClientProperty("dropTargetIndex", -1)
            list.repaint()
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            list.putClientProperty("dropTargetIndex", -1)
            list.repaint()
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

                        mapController.moveNodes(nodesToMove, targetNodeModel, InsertionRelation.AS_CHILD)
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
                        mapController.moveNodes(nodesToMove, targetNodeModel, InsertionRelation.AS_CHILD)
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
//            JLabel label = (JLabel) super.getListCellRendererComponent(list,
            JLabel baseLabel = (JLabel) super.getListCellRendererComponent(list,
                    value,
                    index,
                    isSelected,
                    cellHasFocus)

////
            OverlayLabel label = new OverlayLabel(baseLabel.getText(), baseLabel.getIcon(), baseLabel.getHorizontalAlignment())
            label.setComponentOrientation(baseLabel.getComponentOrientation())
            label.setFont(baseLabel.getFont())
            label.setForeground(baseLabel.getForeground())
            label.setBackground(baseLabel.getBackground())
            label.setOpaque(true)
            label.addMouseListener(sharedMouseListener)
//////

            if (value instanceof NodeModel) {
                NodeModel currentNode = (NodeModel) value
                configureLabelForNode(label, currentNode, sourcePanel, index)

                if (sourcePanel == breadcrumbPanel) {
                    String currentText = label.getText()
                    FontMetrics fm = label.getFontMetrics(label.getFont())

                    int availableWidth = list.fixedCellWidth - label.getInsets().left - label.getInsets().right
                    int textWidth = fm.stringWidth(currentText)

                    if (textWidth > availableWidth) {
                        String truncatedText = truncateText(label, currentText, fm, availableWidth)
                        label.setText(truncatedText)
                    } else {
                        label.setText(currentText)
                    }
                }
            }

            if (isSelected) {
                label.setBackground(list.getSelectionBackground())
                label.setForeground(list.getSelectionForeground())
            }
            Object dropIndexObj = list.getClientProperty("dropTargetIndex")
            if (dropIndexObj instanceof Integer && ((Integer) dropIndexObj) == index) {
                label.setBackground(Color.YELLOW)
                label.setOpaque(true)
            }
            return label
        }
    })
}

void configureMouseMotionListener(JList<NodeModel> list, DefaultListModel<NodeModel> listModel, JPanel sourcePanel) {
    list.addMouseMotionListener(new MouseAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
//            mouseOverList = true
            currentSourcePanel = sourcePanel

            lastMouseModifiers = e.getModifiersEx()

            if (shouldFreeze()) {return}

            hoverTimer.stop()
            currentList = list
            currentListModel = listModel
//            currentSourcePanel = sourcePanel
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

                    tagsNeedUpdate = true
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

                        tagsNeedUpdate = true
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

                        tagsNeedUpdate = true

                        refreshHighlighterCacheTags()

                        cleanAndCreateInspectors(currentlySelectedNode, panelsInMasterPanels[0])

                    })

                    JMenuItem menuItemRemoveFromSelection = new JMenuItem("Remove from selection of tags")
                    menuItemRemoveFromSelection.addActionListener({
                        selectedTagsInPanel.removeElement(tagSelected)
                        refreshHighlighterCacheTags()

                        tagsNeedUpdate = true

                        cleanAndCreateInspectors(currentlySelectedNode, panelsInMasterPanels[0])

                    })

                    JMenuItem menuItemClearSelection = new JMenuItem("Clear selection of tags")
                    menuItemClearSelection.addActionListener({
                        selectedTagsInPanel.clear()
                        refreshHighlighterCacheTags()

                        tagsNeedUpdate = true

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
            currentSourcePanel = thePanelPanel
            lastMouseModifiers = e.getModifiersEx()

            if (shouldFreeze()) {return}

            hoverTimer.stop()
            currentList = jList
            currentListModel = theListModel
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

def saveSettings() {
    File file = getSettingsFile()

    List<String> pinnedItemsIds = pinnedItems.collect { it.id }
//    List<String> recentNodesIds = (0..<history.getSize()).collect { index ->
//        history.getElementAt(index).id
//    }

    String jsonString = new JsonBuilder([
            pinnedItems: pinnedItemsIds,
//            recentSearches: savedSearchCriteria,
//            recentNodes: recentNodesIds,
            userSettings: [
                    panelTextFontName: panelTextFontName,
                    panelTextFontSize: panelTextFontSize,
                    minFontSize: minFontSize,
                    fontForListItens: fontForListItens,
                    nodeTextPanelFixedHeight: nodeTextPanelFixedHeight,
                    retractedWidthFactorForMasterPanel: retractedWidthFactorForMasterPanel,
                    expandedWidthFactorForMasterPanel: expandedWidthFactorForMasterPanel,
                    widthFactorForInspector: widthFactorForInspector,
//                    selectionDelay: selectionDelay,
                    reverseAncestorsList: reverseAncestorsList,
                    paddingBeforeHorizontalScrollBar: paddingBeforeHorizontalScrollBar,
                    additionalInspectorDistanceToTheBottomOfTheScreen: additionalInspectorDistanceToTheBottomOfTheScreen,
                    widthOfTheClearButtonOnQuickSearchPanel: widthOfTheClearButtonOnQuickSearchPanel,
                    keyStrokeToQuickSearch: keyStrokeToQuickSearch.toString(),
                    showOnlyBreadcrumbs: showOnlyBreadcrumbs,
                    showAncestorsOnFirstInspector: showAncestorsOnFirstInspector,
                    rtlOrientation: rtlOrientation,
                    keyStrokeToShowPanels: keyStrokeToShowPanels.toString(),
                    hideInspectorsEvenIfUpdateSelection: hideInspectorsEvenIfUpdateSelection,
                    showInPlaceSiblingsPreview: showInPlaceSiblingsPreview,
                    showInspectorsOnSiblingsPreviewHover: showInspectorsOnSiblingsPreviewHover,
                    fastScrollToCenter: fastScrollToCenter
            ]
    ]).toPrettyString()

    try {
        file.text = jsonString
    } catch (Exception e) {
        e.printStackTrace()
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

//        history.clear()
//        def nodesToAdd = settings.recentNodes.collect { id ->
//            Controller.currentController.map.getNodeForID(id)
//        }.findAll { it != null }
//        nodesToAdd.each { node ->
//            history.addElement(node)
//        }

        if (settings.userSettings) {
            panelTextFontName = settings.userSettings.panelTextFontName ?: panelTextFontName
            panelTextFontSize = settings.userSettings.panelTextFontSize ?: panelTextFontSize
            minFontSize = settings.userSettings.minFontSize ?: minFontSize
            fontForListItens = settings.userSettings.fontForListItens ?: fontForListItens
            nodeTextPanelFixedHeight = settings.userSettings.nodeTextPanelFixedHeight ?: nodeTextPanelFixedHeight
            retractedWidthFactorForMasterPanel = settings.userSettings.retractedWidthFactorForMasterPanel ?: retractedWidthFactorForMasterPanel
            expandedWidthFactorForMasterPanel = settings.userSettings.expandedWidthFactorForMasterPanel ?: expandedWidthFactorForMasterPanel
            widthFactorForInspector = settings.userSettings.widthFactorForInspector ?: widthFactorForInspector
//            selectionDelay = settings.userSettings.selectionDelay ?: selectionDelay
            reverseAncestorsList = settings.userSettings.reverseAncestorsList ?: reverseAncestorsList
            paddingBeforeHorizontalScrollBar = settings.userSettings.paddingBeforeHorizontalScrollBar ?: paddingBeforeHorizontalScrollBar
            additionalInspectorDistanceToTheBottomOfTheScreen = settings.userSettings.additionalInspectorDistanceToTheBottomOfTheScreen ?: additionalInspectorDistanceToTheBottomOfTheScreen
            widthOfTheClearButtonOnQuickSearchPanel = settings.userSettings.widthOfTheClearButtonOnQuickSearchPanel ?: widthOfTheClearButtonOnQuickSearchPanel
            keyStrokeToQuickSearch = KeyStroke.getKeyStroke(settings.userSettings.keyStrokeToQuickSearch as String)
            showOnlyBreadcrumbs = settings.userSettings.showOnlyBreadcrumbs ?: showOnlyBreadcrumbs
            showAncestorsOnFirstInspector = settings.userSettings.showAncestorsOnFirstInspector ?: showAncestorsOnFirstInspector
            rtlOrientation = settings.userSettings.rtlOrientation ?: rtlOrientation
            keyStrokeToShowPanels = KeyStroke.getKeyStroke(settings.userSettings.keyStrokeToShowPanels as String)
            hideInspectorsEvenIfUpdateSelection = settings.userSettings.hideInspectorsEvenIfUpdateSelection ?: hideInspectorsEvenIfUpdateSelection
            showInPlaceSiblingsPreview = settings.userSettings.showInPlaceSiblingsPreview ?: showInPlaceSiblingsPreview
            showInspectorsOnSiblingsPreviewHover = settings.userSettings.showInspectorsOnSiblingsPreviewHover ?: showInspectorsOnSiblingsPreviewHover
            fastScrollToCenter = settings.userSettings.fastScrollToCenter != null ? settings.userSettings.fastScrollToCenter : true
        }

    } catch (Exception e) {
        e.printStackTrace()
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
    int x, y
    
    if (rtlOrientation) {
        // در حالت RTL: بازرس در سمت چپ پنل منبع قرار می‌گیرد
        x = sourcePanel.getLocation().x - inspectorPanel.width
        println "RTL Inspector Location: sourceX=${sourcePanel.getLocation().x}, inspectorWidth=${inspectorPanel.width}, finalX=${x}"
    } else {
        // در حالت LTR: بازرس در سمت راست پنل منبع قرار می‌گیرد
        x = sourcePanel.getLocation().x + sourcePanel.width
        println "LTR Inspector Location: sourceX=${sourcePanel.getLocation().x}, sourceWidth=${sourcePanel.width}, finalX=${x}"
    }

    y = sourcePanel.getLocation().y
    if(panelsInMasterPanels.contains(sourcePanel)) {
        y = masterPanel.getLocation().y
    }

    if(activeSiblingPreviewPanels.contains(sourcePanel) || visiblePreviewInspectors.contains(sourcePanel)) {
        viewportHeight = parentPanel.height
        y = Math.min(sourcePanel.getLocation().y, (viewportHeight - inspectorPanel.height) as int)
    }

    inspectorPanel.setLocation(x, y)
    println "Final Inspector Position: x=${x}, y=${y}"
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
    InputMap inputMap = parentPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
    ActionMap actionMap = parentPanel.getActionMap()

    inputMap.put(keyStrokeToQuickSearch, "focusQuickSearch")
    actionMap.put("focusQuickSearch", new AbstractAction() {
        @Override
        void actionPerformed(ActionEvent e) {
            if (!showPanels) {
                showPanels = true
                masterPanel.setVisible(true)
                breadcrumbPanel.setVisible(true)
                visibleInspectors.each { it.setVisible(true) }
                parentPanel.revalidate()
                parentPanel.repaint()
            }
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

def cleanAndCreateInspectors(NodeModel nodeNotProxy, JPanel somePanel, String dummyMode = "no") {

    dummyPanel = "no"

    switch(dummyMode.toLowerCase()) {
        case "siblings":
            dummyPanel = "siblings"
            break
        case "children":
            dummyPanel = "children"
            break
        default:
            dummyPanel = "no"
            break
    }

    visibleInspectors.each {
        it.setVisible(false)
    }
    visibleInspectors.clear()
    JPanel subInspectorPanel
    if (dummyPanel == "siblings") {
        subInspectorPanel = createInspectorPanel(nodeNotProxy, somePanel, true)
    }
    else subInspectorPanel = createInspectorPanel(nodeNotProxy, somePanel)
    visibleInspectors.add(subInspectorPanel)

    JPanel subInspectorPanel2
    if (dummyPanel == "children") {
        subInspectorPanel2 = createInspectorPanel(nodeNotProxy, subInspectorPanel, true)
    }
    else subInspectorPanel2 = createInspectorPanel(nodeNotProxy, subInspectorPanel)
    visibleInspectors.add(subInspectorPanel2)


//    if (visibleInspectors.size() == 2) {
//        subInspectorPanel2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5))
//    }
//    parentPanel.revalidate()
//    parentPanel.repaint()

//    parentPanel.revalidate()
//    parentPanel.repaint()
//    Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
//    Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
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
    
    if (rtlOrientation) {
        // در حالت RTL: پنل به سمت چپ گسترش می‌یابد
        int expandedWidth = calculateExpandedWidthForMasterPanel()
        int widthDifference = expandedWidth - bounds.width
        
        // موقعیت X را به اندازه اختلاف عرض به چپ منتقل کن
        bounds.x = bounds.x - widthDifference
        bounds.width = expandedWidth
        
        println "RTL Expansion: x=${bounds.x}, width=${bounds.width}, difference=${widthDifference}"
    } else {
        // در حالت LTR: رفتار قبلی (گسترش به راست)
        bounds.width = calculateExpandedWidthForMasterPanel()
        println "LTR Expansion: x=${bounds.x}, width=${bounds.width}"
    }
    
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
//    if(!searchEditor.text.equals("") || quickSearchResults.size() > 0) return
    bounds = masterPanel.getBounds()
    int retractedWidth = calculateRetractedWidthForMasterPanel()
    
    if (rtlOrientation) {
        // در حالت RTL: موقعیت پنل به حالت اول برگردد
        int widthDifference = bounds.width - retractedWidth
        bounds.x = bounds.x + widthDifference  // موقعیت X را به راست برگردان
        bounds.width = retractedWidth
        
        println "RTL Retraction: x=${bounds.x}, width=${bounds.width}, difference=${widthDifference}"
    } else {
        // در حالت LTR: رفتار قبلی
        bounds.width = retractedWidth
        println "LTR Retraction: x=${bounds.x}, width=${bounds.width}"
    }
    
    masterPanel.setBounds(bounds)
    panelsInMasterPanels.each {
        it.setVisible(true)
    }
    scrollPanelsInMasterPanelPanels.each {
        scrollPanel = it
        configureScrollPaneForRTL(scrollPanel)
    }
    masterPanel.repaint()
    masterPanel.revalidate()
    isMasterPanelExpanded = false

    smartCreateInspectors(currentlySelectedNode)

    if(visibleInspectors.size() != 0 && inspectorUpdateSelection) {
        setInspectorLocation(visibleInspectors[0], masterPanel)
        if(visibleInspectors.size() > 1) {
            setInspectorLocation(visibleInspectors[1], visibleInspectors[0])
        }
    }
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


def JList createJList(DefaultListModel<NodeModel> nodes, JPanel jListPanel, JPanel panelPanel) {
    JList<NodeModel> jList = new JList<>(nodes)
    commonJListsConfigs(jList, nodes, panelPanel)

    if (rtlOrientation) {
        jList.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        jList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }

    JScrollPane scrollPane = new JScrollPane(jList)
    scrollPanelsInMasterPanelPanels.add(scrollPane)
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

    return jList

//    jListPanel.revalidate()
//    jListPanel.repaint()
}

def createBreadcrumbsJList() {
    ancestorsJList.setModel(ancestorsOfCurrentNode)
    ancestorsJList.setLayoutOrientation(JList.HORIZONTAL_WRAP)
    ancestorsJList.setVisibleRowCount(1)

    ancestorsJList.setFixedCellWidth(200)
    ancestorsJList.setFixedCellHeight(30)

    commonJListsConfigs(ancestorsJList, ancestorsOfCurrentNode, breadcrumbPanel)

    // تغییر جهت برای راست‌چین
    if (rtlOrientation) {
        ancestorsJList.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT)
    } else {
        ancestorsJList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT)
    }

    breadcrumbPanel.add(ancestorsJList)

    // breadcrumbPanel.revalidate()
    // breadcrumbPanel.repaint()
}

def populateTagsListModel(JList<String> tagsJList) {

    tagsJList.setModel(listModelForAllTags)


}

def updateSpecifiedGUIs(List<NodeModel> nodes, JPanel jListPanel, JPanel panelPanel) {
    jListPanel.removeAll()

    DefaultListModel<NodeModel> listModel = new DefaultListModel<>()
    nodes.each { listModel.addElement(it) }
    JList<NodeModel> jList = new JList<>(listModel)
    commonJListsConfigs(jList, listModel, panelPanel)

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

    jListPanel.revalidate()
    jListPanel.repaint()
}

def cleanPreviousScriptExecution() {
    listenersToRemove = []
    Controller.currentController.modeController.mapController.nodeSelectionListeners.each { listener ->
        try {
            if (listener.uniqueIdForScript == uniqueIdForScript) {
                listenersToRemove << listener
            }
        } catch (Exception ex) {
        }
    }
    listenersToRemove.each { listenerToRemove ->
        Controller.currentController.modeController.mapController.removeNodeSelectionListener(listenerToRemove)
    }

    listenersToRemove2 = []

    Controller.currentController.modeController.mapController.mapChangeListeners.each { listener ->
        try {
            if (listener.uniqueIdForScript == uniqueIdForScript) {
                listenersToRemove2 << listener
            }
        } catch (Exception ex) {
        }
    }

    listenersToRemove2.each { listenerToRemove ->
        Controller.currentController.modeController.mapController.removeMapChangeListener(listenerToRemove)
    }

//    listenersToRemove3 = []
//
//    Field field = MapViewController.class.getDeclaredField("mapViewChangeListeners")
//    field.setAccessible(true)
//
//    field.get(Controller.currentController.mapViewManager).viewListeners.each { listener ->
//        try {
//            if (listener.uniqueIdForScript == uniqueIdForScript) {
//                listenersToRemove3 << listener
//            }
//        } catch (Exception ex) {
//        }
//    }
//
//    listenersToRemove3.each { listenerToRemove ->
//        Controller.currentController.mapViewManager.removeMapViewChangeListener(listenerToRemove)
//    }
    
    listenersToRemove3.each { listenerToRemove ->
        Controller.currentController.mapViewManager.removeMapViewChangeListener(listenerToRemove)
    }

    listenersToRemove4 = []

    mindMap.listeners.each { listener ->
        try {
            if (listener.uniqueIdForScript == uniqueIdForScript) {
                listenersToRemove4 << listener
            }
        } catch (Exception ex) {
        }
    }

    listenersToRemove4.each { listenerToRemove ->
        mindMap.removeListener(listenerToRemove)
    }

}

def clearQuickSearch() {
    searchField.setSelectedItem("")
    quickSearchResults.clear()
    ensureOverlayExistsAndRepaint()
    Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
    Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
}

def togglePanelsVisibility() {
    boolean visible = masterPanel.isVisible()
    if (visible) {
//        inspectorUpdateSelection = false
        masterPanel.setVisible(false)
        breadcrumbPanel.setVisible(false)
        visibleInspectors.each { it.setVisible(false) }
//        visibleInspectors.clear()
        visiblePreviewInspectors.each { it.setVisible(false) }
//        visiblePreviewInspectors.clear()
        clearQuickSearch()
        showPanels = false
        cleanPreviousScriptExecution()
    } else {
//        inspectorUpdateSelection = true
        startListeners()
        loadHistoryWithFPHistory()
        currentlySelectedNode = ScriptUtils.node().delegate
        showPanels = true
        masterPanel.setVisible(true)
        breadcrumbPanel.setVisible(true)
        visibleInspectors.each { it.setVisible(true) }
    }
    parentPanel.revalidate()
    parentPanel.repaint()
}


def shouldShowInspectors() {
//    return (showOnlyBreadcrumbs || (!showPanels && hideInspectorsEvenIfUpdateSelection))
    shouldShowInspectors = true
    if (showOnlyBreadcrumbs) shouldShowInspectors = false
    if (!showPanels && hideInspectorsEvenIfUpdateSelection) shouldShowInspectors = false
//    if (mouseOverList && (activeSiblingPreviewPanels.contains(currentSourcePanel) || visiblePreviewInspectors.contains(currentSourcePanel))) shouldShowInspectors = true
    return shouldShowInspectors
//    return (showOnlyBreadcrumbs || !visiblePreviewInspectors.contains(currentSourcePanel) || !activeSiblingPreviewPanels.contains(currentSourcePanel) || (!showPanels && hideInspectorsEvenIfUpdateSelection))
}

def showSettingsDialog() {
    Window owner = SwingUtilities.getWindowAncestor(Controller.currentController.getMapViewManager().getMapViewComponent())
    JDialog settingsDialog = new JDialog(owner, "Settings", true)
    settingsDialog.setLayout(new BorderLayout())

    JPanel settingsPanel = new JPanel(new GridLayout(0, 2, 5, 5))

    // Panel Text Font Name (String)
    settingsPanel.add(new JLabel("Panel Text Font Name:"))
    JTextField fontNameField = new JTextField(panelTextFontName)
    fontNameField.setToolTipText("Enter the panel text font name (e.g., 'Dialog')")
    settingsPanel.add(fontNameField)

    // Panel Text Font Size (Integer)
    settingsPanel.add(new JLabel("Panel Text Font Size:"))
    JTextField fontSizeField = new JTextField(panelTextFontSize.toString())
    settingsPanel.add(fontSizeField)

    // Minimum Font Size (Integer)
    settingsPanel.add(new JLabel("Minimum Font Size:"))
    JTextField minFontField = new JTextField(minFontSize.toString())
    minFontField.setToolTipText("Minimum size to be used on dynamic sizing of fonts.")
    settingsPanel.add(minFontField)

    // Font for List Items (Font Style) - combo box for PLAIN, BOLD, ITALIC, BOLD+ITALIC
    settingsPanel.add(new JLabel("Font Style for List Items:"))
    String[] fontStyles = ["PLAIN", "BOLD", "ITALIC", "BOLD+ITALIC"]
    JComboBox<String> fontStyleCombo = new JComboBox<>(fontStyles)
    fontStyleCombo.setSelectedIndex(fontForListItens)
    settingsPanel.add(fontStyleCombo)

    // Node Text Panel Fixed Height (Integer)
    settingsPanel.add(new JLabel("Node Text Panel Fixed Height:"))
    JTextField nodeTextPanelHeightField = new JTextField(nodeTextPanelFixedHeight.toString())
    settingsPanel.add(nodeTextPanelHeightField)

    // Retracted Width Factor for Master Panel (Integer)
    settingsPanel.add(new JLabel("Retracted Width Factor for Master Panel (see tooltip):"))
    JTextField retractedFactorField = new JTextField(retractedWidthFactorForMasterPanel.toString())
    retractedFactorField.setToolTipText("The higher the factor, the smaller the panels width.")
    settingsPanel.add(retractedFactorField)

    // Expanded Width Factor for Master Panel (Integer)
    settingsPanel.add(new JLabel("Expanded Width Factor for Master Panel (see tooltip):"))
    JTextField expandedFactorField = new JTextField(expandedWidthFactorForMasterPanel.toString())
    expandedFactorField.setToolTipText("The higher the factor, the wider the panels width.")
    settingsPanel.add(expandedFactorField)

    // Width Factor for Inspector Panel (Integer)
    settingsPanel.add(new JLabel("Width Factor for Inspector Panel (see tooltip):"))
    JTextField inspectorWidthFactorField = new JTextField(widthFactorForInspector.toString())
    inspectorWidthFactorField.setToolTipText("The higher the factor, the smaller the inspector panel width.")
    settingsPanel.add(inspectorWidthFactorField)

    // Selection Delay (ms) (Integer)
//    settingsPanel.add(new JLabel("Selection Delay (ms):"))
//    JTextField selectionDelayField = new JTextField(selectionDelay.toString())
//    selectionDelayField.setToolTipText("In milliseconds.")
//    settingsPanel.add(selectionDelayField)

    // Reverse Ancestors List (Boolean)
    settingsPanel.add(new JLabel("Reverse Ancestors List:"))
    JCheckBox reverseAncestorsCheck = new JCheckBox("", reverseAncestorsList)
    settingsPanel.add(reverseAncestorsCheck)

    // Padding Before Horizontal Scroll Bar (Integer)
    settingsPanel.add(new JLabel("Padding Before Horizontal Scroll Bar:"))
    JTextField paddingField = new JTextField(paddingBeforeHorizontalScrollBar.toString())
    settingsPanel.add(paddingField)

    // Additional Inspector Distance to the Bottom (Integer)
    settingsPanel.add(new JLabel("Additional Inspector Distance to Bottom:"))
    JTextField additionalDistanceField = new JTextField(additionalInspectorDistanceToTheBottomOfTheScreen.toString())
    settingsPanel.add(additionalDistanceField)

    // Width of the Clear Button on Quick Search Panel (Integer)
    settingsPanel.add(new JLabel("Width of Clear Button on Quick Search Panel:"))
    JTextField clearButtonWidthField = new JTextField(widthOfTheClearButtonOnQuickSearchPanel.toString())
    settingsPanel.add(clearButtonWidthField)

    // Quick Search Hotkey (KeyStroke)
    settingsPanel.add(new JLabel("Quick Search Hotkey:"))
    JTextField quickSearchHotkeyField = new JTextField(keyStrokeToQuickSearch.toString())
    settingsPanel.add(quickSearchHotkeyField)

    // Show Only Breadcrumbs (Boolean)
    settingsPanel.add(new JLabel("Show Only Breadcrumbs:"))
    JCheckBox showOnlyBreadcrumbsCheck = new JCheckBox("", showOnlyBreadcrumbs)
    settingsPanel.add(showOnlyBreadcrumbsCheck)

    // Show Ancestors on First Inspector (Boolean)
    settingsPanel.add(new JLabel("Show Ancestors on First Inspector:"))
    JCheckBox showAncestorsCheck = new JCheckBox("", showAncestorsOnFirstInspector)
    settingsPanel.add(showAncestorsCheck)

    // Right-to-Left Orientation (Boolean)
    settingsPanel.add(new JLabel("Right-to-Left Orientation:"))
    JCheckBox rtlOrientationCheck = new JCheckBox("", rtlOrientation)
    settingsPanel.add(rtlOrientationCheck)

    // Show in-Place Siblings Preview (Boolean)
    settingsPanel.add(new JLabel("Show in-Place Siblings Preview:"))
    JCheckBox showInPlaceSiblingsPreviewCheck = new JCheckBox("", showInPlaceSiblingsPreview)
    settingsPanel.add(showInPlaceSiblingsPreviewCheck)

    // Show Inspectors on Siblings Preview Hover (Boolean)
    settingsPanel.add(new JLabel("Show Inspectors on Siblings Preview Hover:"))
    JCheckBox showInspectorsOnSiblingsPreviewHoverCheck = new JCheckBox("", showInspectorsOnSiblingsPreviewHover)
    showInspectorsOnSiblingsPreviewHoverCheck.setToolTipText("Show inspector panels when hovering over nodes in In-place Siblings Preview panels")
    settingsPanel.add(showInspectorsOnSiblingsPreviewHoverCheck)

    // Hotkey to Show Panels (KeyStroke)
    settingsPanel.add(new JLabel("Hotkey to Show Panels:"))
    JTextField showPanelsHotkeyField = new JTextField(keyStrokeToShowPanels.toString())
    settingsPanel.add(showPanelsHotkeyField)

    // Hide Inspectors Even If Update Selection (Boolean)
    settingsPanel.add(new JLabel("Hide Inspectors Even If Update Selection:"))
    JCheckBox hideInspectorsCheck = new JCheckBox("", hideInspectorsEvenIfUpdateSelection)
    settingsPanel.add(hideInspectorsCheck)

    // ★★★ قدم ۵: اضافه کردن این بخش ★★★
    settingsPanel.add(new JLabel("Fast Scroll to Center:"))
    JCheckBox fastScrollCheck = new JCheckBox("", fastScrollToCenter)
    fastScrollCheck.setToolTipText("When enabled, nodes will scroll to center quickly when clicked in panels")
    settingsPanel.add(fastScrollCheck)

    settingsDialog.add(new JScrollPane(settingsPanel), BorderLayout.CENTER)

    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT))
    JButton okButton = new JButton("OK")
    okButton.addActionListener({ e ->
        panelTextFontName = fontNameField.getText()
        try { panelTextFontSize = Integer.parseInt(fontSizeField.getText()) } catch(Exception ex) {}
        try { minFontSize = Integer.parseInt(minFontField.getText()) } catch(Exception ex) {}
        fontForListItens = fontStyleCombo.getSelectedIndex()
        try { nodeTextPanelFixedHeight = Integer.parseInt(nodeTextPanelHeightField.getText()) } catch(Exception ex) {}
        try { retractedWidthFactorForMasterPanel = Integer.parseInt(retractedFactorField.getText()) } catch(Exception ex) {}
        try { expandedWidthFactorForMasterPanel = Integer.parseInt(expandedFactorField.getText()) } catch(Exception ex) {}
        try { widthFactorForInspector = Integer.parseInt(inspectorWidthFactorField.getText()) } catch(Exception ex) {}
        reverseAncestorsList = reverseAncestorsCheck.isSelected()
        try { paddingBeforeHorizontalScrollBar = Integer.parseInt(paddingField.getText()) } catch(Exception ex) {}
        try { additionalInspectorDistanceToTheBottomOfTheScreen = Integer.parseInt(additionalDistanceField.getText()) } catch(Exception ex) {}
        try { widthOfTheClearButtonOnQuickSearchPanel = Integer.parseInt(clearButtonWidthField.getText()) } catch(Exception ex) {}
        try {
            keyStrokeToQuickSearch = KeyStroke.getKeyStroke(quickSearchHotkeyField.getText())
        } catch(Exception ex) {}
        showOnlyBreadcrumbs = showOnlyBreadcrumbsCheck.isSelected()
        showAncestorsOnFirstInspector = showAncestorsCheck.isSelected()
        rtlOrientation = rtlOrientationCheck.isSelected()
        showInPlaceSiblingsPreview = showInPlaceSiblingsPreviewCheck.isSelected()
        showInspectorsOnSiblingsPreviewHover = showInspectorsOnSiblingsPreviewHoverCheck.isSelected()
        try {
            keyStrokeToShowPanels = KeyStroke.getKeyStroke(showPanelsHotkeyField.getText())
        } catch(Exception ex) {}
        hideInspectorsEvenIfUpdateSelection = hideInspectorsCheck.isSelected()
        fastScrollToCenter = fastScrollCheck.isSelected()

        saveSettings()
        reloadPanels()
        settingsDialog.dispose()
    } as ActionListener)

    JButton cancelButton = new JButton("Cancel")
    cancelButton.addActionListener({ e -> settingsDialog.dispose() } as ActionListener)

    buttonsPanel.add(okButton)
    buttonsPanel.add(cancelButton)
    settingsDialog.add(buttonsPanel, BorderLayout.SOUTH)

    settingsDialog.pack()
    settingsDialog.setLocationRelativeTo(owner)
    settingsDialog.setVisible(true)
}

def reloadPanels() {
    if (masterPanel != null && masterPanel.isVisible()) {
        parentPanel.remove(masterPanel)
        masterPanel = null
    }
    if(breadcrumbPanel != null) {
        parentPanel.remove(breadcrumbPanel)
        breadcrumbPanel = null
    }

    visibleInspectors.each {it.setVisible(false)}
    createPanels()
    if (!showOnlyBreadcrumbs) visibleInspectors.each { it.setVisible(true) }
    if(!showPanels) {
        masterPanel.setVisible(false)
        breadcrumbPanel.setVisible(false)
        visibleInspectors.each {it.setVisible(false)}
    }
}



def boolean areNodesVisible(NodeModel nodeNotProxy, String testMode = "both") {

    def mapView = Controller.currentController.MapViewManager.mapView
    def viewport = mapView.getParent()
    if (!(viewport instanceof JViewport)) {
        return false
    }

    def proxyVersion = ProxyFactory.createNode(nodeNotProxy, ScriptUtils.getCurrentContext())
    if (proxyVersion.folded && testMode != "siblings") return false

    def siblings = []
    if (proxyVersion == c.viewRoot) {
        siblings = [proxyVersion.delegate]
    } else {
        siblings = proxyVersion.parent.children.collect { it.delegate }
    }

    def children = proxyVersion.children.collect { it.delegate }

    def nodesToTest = []
    switch(testMode.toLowerCase()) {
        case "both":
            nodesToTest = siblings + children
            break
        case "siblings":
            nodesToTest = siblings
            break
        case "children":
            nodesToTest = children
            break
        default:
            nodesToTest = siblings + children
            break
    }

    boolean allVisible = true
    def nodesSnapshot = nodesToTest.toList()
    for (n in nodesSnapshot) {
        if (!isNodeOnScreen(n)) {
            allVisible = false
            break
        }
    }

    return allVisible
}





def boolean isNodeOnScreen(NodeModel nodeNotProxy) {
    if (ProxyFactory.createNode(nodeNotProxy, ScriptUtils.getCurrentContext()) != c.viewRoot) {
        def mapView = Controller.currentController.MapViewManager.mapView
        def viewport = mapView.getParent()
        NodeView nv = mapView.getNodeView(nodeNotProxy)
        if(nv == null) return false
        def point = mapView.getNodeContentLocation(nv)
        boolean visible = viewport.getViewRect().contains(point)
        if (!visible) {
            return false
        }
    }
    return true
}


def boolean isNodeVisibleInViewport(NodeModel nodeNotProxy) {

    def mapView = Controller.currentController.MapViewManager.mapView
    def viewport = mapView.getParent()
    if (!(viewport instanceof JViewport)) {
        return false
    }

    NodeView nodeView2 = mapView.getNodeView(nodeNotProxy)
    if(nodeView2 == null) {
        return false
    }

    def pointOnMap = mapView.getNodeContentLocation(nodeView2)

    if (pointOnMap == null) return false

    nodeView2.getContentPane().height

    Rectangle r = new Rectangle()
    r.x = pointOnMap.x
    r.y = pointOnMap.y
    r.width = nodeView2.getContentPane().width
    r.height = nodeView2.getContentPane().height

    def viewRect = viewport.getViewRect()

    visible = viewRect.intersects(r)

    return visible
}




def smartCreateInspectors(NodeModel nodeNotProxy) {
    if(!inspectorUpdateSelection || shouldFreeze()) return
    if(areNodesVisible(currentlySelectedNode)) {
        if(visibleInspectors.size() != 0) {
            visibleInspectors.each {
                it.setVisible(false)
            }
            visibleInspectors.clear()
            parentPanel.revalidate()
            parentPanel.repaint()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
            Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
        }
    }
    else if (! areNodesVisible(currentlySelectedNode, "siblings") && ! areNodesVisible(currentlySelectedNode, "children")) {
        if(visibleInspectors.size() != 0 && !visibleInspectors[0].dummyPanel2 && !visibleInspectors[1].dummyPanel2 && currentlySelectedNode == visibleInspectors[0].getClientProperty("referenceNode") && currentlySelectedNode == visibleInspectors[1].getClientProperty("referenceNode")) return
        cleanAndCreateInspectors(nodeNotProxy, panelsInMasterPanels[0])
    }
    else if (! areNodesVisible(currentlySelectedNode, "siblings")) {
        if(visibleInspectors.size() != 0 && !visibleInspectors[0].dummyPanel2 && currentlySelectedNode == visibleInspectors[0].getClientProperty("referenceNode")) return
        cleanAndCreateInspectors(nodeNotProxy, panelsInMasterPanels[0], "children")
    }
    else {
        if(visibleInspectors.size() != 0 && !visibleInspectors[1].dummyPanel2 && currentlySelectedNode == visibleInspectors[1].getClientProperty("referenceNode")) return
        cleanAndCreateInspectors(nodeNotProxy, panelsInMasterPanels[0], "siblings")
    }
}



def createComponentChangeListener() {
    mapView1 = Controller.currentController.MapViewManager.mapView
    if (mapView1.componentListeners.any { it.getClass().getName().startsWith("UtilityPanels") }) return

    mapView1.addComponentListener(new ComponentAdapter() {
        public void componentMoved(ComponentEvent e) {
            ensureOverlayExistsAndRepaint()

            if(!showPanels) {
                currentlySelectedNode = ScriptUtils.node().delegate
            }

            if(inspectorUpdateSelection) smartCreateInspectors(currentlySelectedNode)

            nv3 = mapView1.getNodeView(currentlySelectedNode)
            if (Boolean.TRUE.equals(nv3.getMainView().getClientProperty(INLINE_EDITOR_ACTIVE))) {
                return
            }

            if (showInPlaceSiblingsPreview) {
                if (isScrolling) {
                    moveSiblingPreviewPanelsWithMap() // حرکت دادن پنل‌ها بدون ایجاد جدید
                } else {
                    refreshSiblingPreviewPanels() // ایجاد پنل‌های جدید
                }
            }
            else if (activeSiblingPreviewPanels.size() != 0) {
                activeSiblingPreviewPanels.each {
                    it.setVisible(false)
                }
                activeSiblingPreviewPanels.clear()
            }
        }
    })
}


class OverlayLabel extends JLabel {
    boolean overlayEnabled = false
    boolean useHatch = false

    OverlayLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment)
        setOpaque(true)
    }


    private TexturePaint createHatchPattern(Color bg) {
        Color hatchColor = getHatchColorForBackground(bg)
        int size = 8
        BufferedImage hatchImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
        Graphics2D g2 = hatchImage.createGraphics()


        g2.setColor(hatchColor)

        g2.drawLine(0, 7, 7, 0)
        g2.drawLine(0, 0, 7, 7)


        g2.dispose()
        return new TexturePaint(hatchImage, new Rectangle2D.Double(0, 0, size, size))
    }





    private Color getHatchColorForBackground(Color bg) {
        double luminance = (0.299 * bg.getRed() + 0.587 * bg.getGreen() + 0.114 * bg.getBlue()) / 255.0
        float factor = (float)(1.0 - luminance)
        Color lightBlue = new Color(220, 255, 255)
        Color darkBlue = new Color(0, 0, 139)

        int r = (int)(lightBlue.getRed() * factor + darkBlue.getRed() * (1 - factor))
        int g = (int)(lightBlue.getGreen() * factor + darkBlue.getGreen() * (1 - factor))
        int b = (int)(lightBlue.getBlue() * factor + darkBlue.getBlue() * (1 - factor))

        return new Color(r, g, b, 40)
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g)
        if (overlayEnabled && useHatch) {
            Graphics2D g2d = (Graphics2D) g.create()
            TexturePaint hatchPattern = createHatchPattern(getBackground())
            g2d.setPaint(hatchPattern)
            g2d.fillRect(0, 0, getWidth(), getHeight())
            g2d.dispose()
        }
    }


}


class DottedBorder extends AbstractBorder {
    private Color color
    private int thickness
    private float dotLength
    private float gapLength

    DottedBorder(Color color, int thickness, float dotLength = 1f, float gapLength = 3f) {
        this.color = color
        this.thickness = thickness
        this.dotLength = dotLength
        this.gapLength = gapLength
    }

    @Override
    void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create()
        g2d.setColor(color)
        float[] dash = [dotLength, gapLength] as float[]
        g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1f, dash, 0))
        g2d.drawRect(x, y, width - 1, height - 1)
        g2d.dispose()
    }
}


def Point listItemPosition(JList list, int itemIndex) {
    Rectangle cellBounds = list.getCellBounds(itemIndex, itemIndex)
    Point startingPoint = new Point(cellBounds.x as int, cellBounds.y as int)
    def mapView = Controller.currentController.MapViewManager.mapView
    UITools.convertPointToAncestor(masterPanel, startingPoint, Controller.currentController.mapViewManager.mapView.parent.parent)
    return startingPoint
}

def void connectListItemToNode(JList list, int itemIndex) {

    mapView4 = Controller.currentController.MapViewManager.mapView
    finalMapView = mapView4

    Rectangle itemBounds2 = list.getCellBounds(itemIndex, itemIndex)


    Point itemPointInList2 = itemBounds2.getLocation()


    startPoint = itemPointInList2

    UITools.convertPointToAncestor(list, startPoint, Controller.currentController.mapViewManager.mapView.parent.parent)


    newStartPoint = startPoint

    correspondingNode = list.getModel().getElementAt(itemIndex)



    NodeView correspondingNodeView = null
    correspondingNode.views.each {
        if(it.map.name == finalMapView.name) {
            correspondingNodeView = it
        }
    }

    if(correspondingNodeView == null) {
        return
    }

    Point correspondingNodeLocation = finalMapView.getNodeContentLocation(correspondingNodeView)
    UITools.convertPointToAncestor(finalMapView, correspondingNodeLocation, Controller.currentController.mapViewManager.mapView.parent.parent)


    def finalStart = new Point(startPoint.x as int, startPoint.y as int)
    def finalNodeLocation = new Point(correspondingNodeLocation.x as int, correspondingNodeLocation.y as int)


    Random seededRandom = new Random(itemIndex);
    float hue = seededRandom.nextFloat();
    float saturation = 0.7f + seededRandom.nextFloat() * 0.3f; // [0.7, 1.0)
    float brightness = 0.6f + seededRandom.nextFloat() * 0.3f; // [0.6, 0.9)
    final Color itemColor = Color.getHSBColor(hue, saturation, brightness);


    JPanel overlay = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g)

            Graphics2D g2d = (Graphics2D) g.create();
            try {
                g2d.setColor(itemColor);

                g2d.setStroke(new BasicStroke(1.5f))
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

                g2d.drawLine(finalStart.x as int, finalStart.y as int,
                        finalNodeLocation.x as int, finalNodeLocation.y as int)
            } finally {
                g2d.dispose();
            }
        }
    }


    overlay.setOpaque(false)
    overlay.setBounds(0, 0, finalMapView.getWidth(), finalMapView.getHeight())

    overlayFinal = overlay
    parentPanel2 = Controller.currentController.mapViewManager.mapView.parent.parent
    parentPanel2.add(overlay)
    parentPanel2.setComponentZOrder(overlay, 0)

    connectingLines.add(overlayFinal)
}



def void calculateConnectionData(JList list, int itemIndex) {

    mapView4 = Controller.currentController.MapViewManager.mapView
    finalMapView = mapView4

    Rectangle itemBounds = list.getCellBounds(itemIndex, itemIndex)
    if (itemBounds == null) {
        return
    }
    Point startPointRelative = itemBounds.getLocation()
    Point startPoint = new Point(startPointRelative)
    if (overlayParentPanel == null) {
        overlayParentPanel = Controller.currentController.mapViewManager.mapView.parent.parent
    }
    if (overlayParentPanel == null) {
        return
    }
    UITools.convertPointToAncestor(list, startPoint, overlayParentPanel)

    correspondingNode = list.getModel().getElementAt(itemIndex)
    if (!(correspondingNode instanceof NodeModel)) return

    NodeView correspondingNodeView = null
    correspondingNode.views.each { view ->
        if (view.map.name == finalMapView.name) {
            correspondingNodeView = view
        }
    }
    correspondingNodeProxy = ProxyFactory.createNode(correspondingNode, ScriptUtils.getCurrentContext())
    ascendants = correspondingNodeProxy.pathToRoot

    List<Node> filteredAscendants = ascendants.findAll { ascendantNode ->
        boolean keepBasedOnFolded = ascendantNode.folded
        boolean keepBasedOnView = false
        if (ascendantNode.delegate?.views != null && !ascendantNode.delegate.views.isEmpty()) {
            keepBasedOnView = (ascendantNode.delegate.views.find { view -> view.map.name == finalMapView.name } != null)
        }
        return keepBasedOnFolded && keepBasedOnView
    }

    isInsideFoldedNode = false

    if (correspondingNodeView == null) {
        if(filteredAscendants.size() == 0) return
        filteredAscendants[0].delegate.views.each { view ->
            if (view.map.name == finalMapView.name) {
                correspondingNodeView = view
                isInsideFoldedNode = true
            }
        }
        if (correspondingNodeView == null) return
    }


    viewRoot = c.viewRoot

    isInCurrentRootViewBranch = ascendants.contains(viewRoot)

    if(!isInCurrentRootViewBranch) return


    Point nodeLocationRelative = finalMapView.getNodeContentLocation(correspondingNodeView)
    Point endPoint = new Point(nodeLocationRelative)
    UITools.convertPointToAncestor(finalMapView, endPoint, overlayParentPanel)




    Color mapBackgroundColor = finalMapView.getBackground()
    if (mapBackgroundColor == null) {
        mapBackgroundColor = currentMapView.getBackground()
    }

    boolean backgroundIsDark = isColorDark(mapBackgroundColor)

    List<Color> colorPalette = backgroundIsDark ? COLORS_FOR_DARK_BG : COLORS_FOR_LIGHT_BG


    Color itemColor = colorPalette[itemIndex % colorPalette.size()]



    connectingLineData.add([
            start: startPoint,
            end: endPoint,
            color: itemColor,
            targetNodeIsInsideFoldedNode: isInsideFoldedNode,
            cellHeight: itemBounds.height,
            nodeHeight: correspondingNodeView.getContentPane().height
    ])
}





def void ensureOverlayExistsAndRepaint() {

    removeOverlay()

    if (quickSearchResults.size() > 0 && quickSearchResultsJList != null) {
        if (overlayParentPanel == null) {
            overlayParentPanel = Controller.currentController.mapViewManager.mapView.parent.parent
        }

        if (overlayParentPanel != null) {
            for (int i = 0; i < quickSearchResults.size(); i++) {
                calculateConnectionData(quickSearchResultsJList, i)
            }
        } else {
        }
    }

    if (connectingLineData.isEmpty()) {
        removeOverlay()
        return
    }


    if (overlayParentPanel == null) {
        overlayParentPanel = Controller.currentController.mapViewManager.mapView.parent.parent
        if (overlayParentPanel == null) {
            return
        }
    }

    if (linesOverlayPanel == null) {
        linesOverlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g)

                Graphics2D g2d = (Graphics2D) g.create()
                try {
                    float strokeWidth = 5.5f;

                    Stroke solidStroke = new BasicStroke(strokeWidth);

                    float[] dashPattern = [1f, 4f] as float[];
                    Stroke dashedStroke = new BasicStroke(
                            strokeWidth,
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_BEVEL,
                            0,
                            dashPattern,
                            0f
                    );

                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)


                    connectingLineData.eachWithIndex { data, index ->
                        Point start = data.start as Point
                        Point end = data.end as Point
                        Color color = data.color as Color
                        Boolean isInsideFolded = data.targetNodeIsInsideFoldedNode as Boolean


                        if (start != null && end != null && color != null) {

                            if (isInsideFolded == true) {
                                g2d.setStroke(dashedStroke);
                            } else {
                                g2d.setStroke(solidStroke);
                            }

                            g2d.setColor(color)

                            g2d.drawLine((start.x + masterPanel.width) as int, (start.y + ((data.cellHeight as int) / 2)) as int,
                                    end.x as int, ((end.y + ((data.nodeHeight as int) / 2)) as int))
                        } else {
                        }
                    }
                } finally {
                    g2d.dispose()
                }
            }
        }
        linesOverlayPanel.setOpaque(false)
        linesOverlayPanel.setName("ConnectingLinesOverlay")

        overlayParentPanel.add(linesOverlayPanel)
        overlayParentPanel.setComponentZOrder(linesOverlayPanel, 0)
    }

    linesOverlayPanel.setBounds(0, 0, overlayParentPanel.getWidth(), overlayParentPanel.getHeight())
    overlayParentPanel.revalidate()
    overlayParentPanel.repaint()
}


def void removeOverlay() {
    if (linesOverlayPanel != null && overlayParentPanel != null) {
        overlayParentPanel.remove(linesOverlayPanel)
        overlayParentPanel.revalidate()
        overlayParentPanel.repaint()
        linesOverlayPanel = null
    }
    overlayParentPanel = null
    connectingLineData.clear()
}


double calculateLuminance(Color color) {
    if (color == null) return 128;
    return 0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue();
}


boolean isColorDark(Color color) {
    double luminanceThreshold = 128.0;
    return calculateLuminance(color) < luminanceThreshold;
}






//////////////////////////////////////


def loadStylesIntoModel(DefaultListModel<ProxyNode> model) {
    model.clear()
    try {
        def userStylesParent = getUserDefinedStylesParentNode()

        if (userStylesParent != null && userStylesParent.children != null) {
            userStylesParent.children.each { styleNode ->
                model.addElement(styleNode.delegate)
            }
        } else {
        }
    } catch (Exception e) {
        e.printStackTrace()
    }
}

def static getUserDefinedStylesParentNode(x = null){
    return getUserDefinedStylesParentNode((ScriptContext) null)
}


def static getUserDefinedStylesParentNode(ScriptContext scriptContext){
    MapModel mapa = Controller.getCurrentController().getMap();
    return getUserDefinedStylesParentNode(mapa, scriptContext)
}

def static getUserDefinedStylesParentNode(ApiMindMap mapaProxy, ScriptContext scriptContext){
    return getUserDefinedStylesParentNode(mapaProxy.delegate, scriptContext)
}

def static getUserDefinedStylesParentNode(MapModel mapa, ScriptContext scriptContext){
    if(!mapa) {
        return getUserDefinedStylesParentNode(scriptContext)
    }
    MapStyleModel styleModel = MapStyleModel.getExtension(mapa);
    MapModel styleMap = styleModel.getStyleMap();
    NodeModel userStyleParentNode = styleModel.getStyleNodeGroup(styleMap, MapStyleModel.STYLES_USER_DEFINED);
    def userDefinedParentNode = new ProxyNode(userStyleParentNode, scriptContext)
    return userDefinedParentNode
}

def refreshSiblingPreviewPanels() {
    // اگر در حال اسکرول هستیم، فقط موقعیت پنل‌های موجود را به روز کنیم
    if (isScrolling) {
        moveSiblingPreviewPanelsWithMap()
        return
    }
    
    // غیر این صورت، منطق عادی ایجاد/حذف پنل‌ها
    activeSiblingPreviewPanels.each {
        it.visible = false
        parentPanel.remove(it)
    }
    activeSiblingPreviewPanels.clear()

    def mapView = Controller.currentController.MapViewManager.mapView
    def viewport = mapView.getParent()
    if (!(viewport instanceof JViewport)) {
        return
    }

    c.viewRoot.findAll().each {

        if (it == c.viewRoot) return

        def testedNode = it
        if (testedNode == null) {
            return
        }

        NodeView testedNodeView = mapView.getNodeView(testedNode.delegate)
        if (testedNodeView == null) {
            return
        }
        Point selectedPointOnMap = mapView.getNodeContentLocation(testedNodeView)
        Point selectedPointOnScreen = SwingUtilities.convertPoint(mapView, selectedPointOnMap, viewport)
        referenceNodeScreenX = selectedPointOnScreen.x
        referenceNodeScreenY = selectedPointOnScreen.y

//        if(referenceNodeScreenX < 0 || referenceNodeScreenX > viewport.getViewRect().width) return

        if(testedNode.parent.children.size() == 1) return

        def parentNode = testedNode.parent
        def siblings = parentNode.children
        int selectedIndex = siblings.indexOf(testedNode)
        if (selectedIndex < 0) {
            return
        }

        NodeModel offScreenSiblingAbove = null

        if(selectedIndex > 0 && !isNodeVisibleInViewport(siblings[selectedIndex - 1].delegate)) offScreenSiblingAbove = siblings[selectedIndex - 1].delegate

        if (offScreenSiblingAbove != null) {

            NodeView offScreenSiblingAboveNodeView = mapView.getNodeView(offScreenSiblingAbove)
            Point offScreenSiblingAboveSelectedPointOnMap = mapView.getNodeContentLocation(offScreenSiblingAboveNodeView)
            Point offScreenSiblingAboveSelectedPointOnScreen = SwingUtilities.convertPoint(mapView, offScreenSiblingAboveSelectedPointOnMap, viewport)
            offScreenSiblingAboveXPoint = offScreenSiblingAboveSelectedPointOnScreen.x
            offScreenSiblingAboveYPoint = offScreenSiblingAboveSelectedPointOnScreen.y

            if (referenceNodeScreenY >= 0 && offScreenSiblingAboveYPoint <= 0 && referenceNodeScreenX > 0 && referenceNodeScreenX < viewport.getWidth()) {

                siblingsPreviewPanelCreated = createSiblingPreviewPanel(testedNode.delegate, false, referenceNodeScreenX as int, referenceNodeScreenY as int)

                activeSiblingPreviewPanels << siblingsPreviewPanelCreated

            }

        } else {
        }

        NodeModel offScreenSiblingBelow = null

        if(selectedIndex + 1 < siblings.size() && !isNodeVisibleInViewport(siblings[selectedIndex + 1].delegate)) offScreenSiblingBelow = siblings[selectedIndex + 1].delegate

        if (offScreenSiblingBelow != null) {

            NodeView offScreenSiblingBelowNodeView = mapView.getNodeView(offScreenSiblingBelow)
            Point offScreenSiblingBelowSelectedPointOnMap = mapView.getNodeContentLocation(offScreenSiblingBelowNodeView)
            Point offScreenSiblingBelowSelectedPointOnScreen = SwingUtilities.convertPoint(mapView, offScreenSiblingBelowSelectedPointOnMap, viewport)
            offScreenSiblingBelowXPoint = offScreenSiblingBelowSelectedPointOnScreen.x
            offScreenSiblingBelowYPoint = offScreenSiblingBelowSelectedPointOnScreen.y

            if (referenceNodeScreenY < viewport.getHeight() && offScreenSiblingBelowYPoint >= viewport.getHeight() && referenceNodeScreenX > 0 && referenceNodeScreenX < viewport.getWidth()) {

                siblingsPreviewPanelCreated = createSiblingPreviewPanel(testedNode.delegate, true, referenceNodeScreenX as int, referenceNodeScreenY as int)

                activeSiblingPreviewPanels << siblingsPreviewPanelCreated

            }

        } else {
        }
    }
}

public void createSingleRunListeners() {
    String searchText
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

//        masterPanel.setVisible(false)
//        breadcrumbPanel.setVisible(false)
//        visibleInspectors.each {it.setVisible(false)}
//        SwingUtilities.invokeLater {
//            createPanels()
//            visibleInspectors.each { it.setVisible(true) }
//            masterPanel.revalidate()
//            masterPanel.repaint()
//            breadcrumbPanel.revalidate()
//            breadcrumbPanel.repaint()
//            parentPanel.revalidate()
//            parentPanel.repaint()
//            if(!showPanels) {
//                masterPanel.setVisible(false)
//                breadcrumbPanel.setVisible(false)
//                visibleInspectors.each {it.setVisible(false)}
//            }
//        }

            reloadPanels()


            createComponentChangeListener()
            refreshSiblingPreviewPanels()
//        SwingUtilities.invokeLater { updateAllGUIs() }
        }
    }
    Controller.currentController.mapViewManager.addMapViewChangeListener(myMapViewChangeListener)
}

def startListeners() {

    createComponentChangeListener()

    INodeSelectionListener mySelectionListener = new INodeSelectionListener() {
        @Override
        public void onDeselect(NodeModel node) {

        }

        @Override
        public void onSelect(NodeModel node) {

            if (node == currentlySelectedNode) {
                return
            }

//        recentSelectedNodesPanel.removeAll()
//        breadcrumbPanel.removeAll()

            currentlySelectedNode = node
            hoveredTagModel.clear()

            def newHistoryModel = new DefaultListModel<NodeModel>()

            newHistoryModel = history

            if (newHistoryModel.contains(node)) {
                newHistoryModel.removeElement(node)
            }
            newHistoryModel.insertElementAt(node, 0)
            if (newHistoryModel.getSize() > 200) {
                newHistoryModel.removeElementAt(200)
            }

            historyJList.setModel(newHistoryModel)
            history = newHistoryModel
//
//        if (history.contains(node)) {
//            history.removeElement(node)
//        }
//        history.insertElementAt(node, 0)
//        if (history.getSize() > 200) {
//            history.removeElementAt(200)
//        }

            saveSettings()

//        def newAncestorsModel = new DefaultListModel<NodeModel>()
//        if (reverseAncestorsList) {
//            node.getPathToRoot().reverse().each { newAncestorsModel.addElement(it) }
//        } else {
//            node.getPathToRoot().each { newAncestorsModel.addElement(it) }
//        }
//
//        ancestorsJList.setModel(newAncestorsModel)
//        ancestorsOfCurrentNode = newAncestorsModel

            ancestorsOfCurrentNode.clear()
            if(reverseAncestorsList) {
                node.getPathToRoot().reverse().each {
                    ancestorsOfCurrentNode.addElement(it)
                }
            }
            else{
                node.getPathToRoot().each {
                    ancestorsOfCurrentNode.addElement(it)
                }
            }


            if (freezeInspectors || isMouseOverSearchBox) {
                return
            }

            if (inspectorUpdateSelection) {

//            if(areNodesVisible(currentlySelectedNode)) {
//                visibleInspectors.each {
//                    it.setVisible(false)
//                }
//                parentPanel.revalidate()
//                parentPanel.repaint()
//                Controller.getCurrentController().getMapViewManager().getMapViewComponent().revalidate()
//                Controller.getCurrentController().getMapViewManager().getMapViewComponent().repaint()
//
//            }
//            else if (! areNodesVisible(currentlySelectedNode, "siblings") && ! areNodesVisible(currentlySelectedNode, "children")) {
//                cleanAndCreateInspectors(node, panelsInMasterPanels[0])
//            }
//            else if (! areNodesVisible(currentlySelectedNode, "siblings")) {
//                cleanAndCreateInspectors(node, panelsInMasterPanels[0], "children")
//            }
//            else {
//                cleanAndCreateInspectors(node, panelsInMasterPanels[0], "siblings")
//            }

                smartCreateInspectors(node)
            }


            ////////////////inplaceinspector////////////

//        mapView = Controller.currentController.mapViewManager.mapView
//
//
//        NodeView root = mapView.getRoot()
//        final JComponent rootContent = root.getMainView()
//        final Point contentPt = new Point()
//        UITools.convertPointToAncestor(rootContent, contentPt, mapView)
//        final float zoom = mapView.getZoom()
////final Point eventPoint = e.getPoint()
//        eventPoint = MouseInfo.getPointerInfo().getLocation()
//        SwingUtilities.convertPointFromScreen(eventPoint, mapView)
////        int x =(int) ((eventPoint.x - contentPt.x)/zoom)
//        int x =(int) ((eventPoint.x - contentPt.x))
//        final int y =(int) ((eventPoint.y - contentPt.y)/zoom)
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

    IMapChangeListener myMapChangeListener = new IMapChangeListener() {
        @Override
        public void onNodeDeleted(NodeDeletionEvent nodeDeletionEvent) {
            NodeModel deletedNode = nodeDeletionEvent.node
            allDeletedNodes = ProxyUtils.findImpl(null, deletedNode, false)
            allDeletedNodes.each {
//        history.removeElement(deletedNode)
//        pinnedItems.removeElement(deletedNode)
                history.removeElement(it)
                pinnedItems.removeElement(it)
            }
            saveSettings()
//        SwingUtilities.invokeLater { updateAllGUIs() }
        }

    }

    Controller.currentController.modeController.getMapController().addUIMapChangeListener(myMapChangeListener)


    NodeChangeListener myNodeChangeListenerZZ= {NodeChanged event->
        if(event.changedElement == NodeChanged.ChangedElement.TAGS) {
            loadTagsIntoModel(listModelForAllTags, currentlySelectedNode)
            tagsNeedUpdate = true
//        updateAllGUIs()
//        masterPanel.revalidate()
//        masterPanel.repaint()
        }
    } as NodeChangeListener

    mindMap.addListener(myNodeChangeListenerZZ)


    viewportSizeChangeListener = new ComponentAdapter() {
        @Override
        public void componentResized(final ComponentEvent e) {
//        panelsInMasterPanels.each {
//            parentPanel.remove(it)
//        }

            saveSettings()

//        masterPanel.setVisible(false)
//        breadcrumbPanel.setVisible(false)
//        visibleInspectors.each {it.setVisible(false)}
//        createPanels()
//        visibleInspectors.each { it.setVisible(true) }
//        masterPanel.revalidate()
//        masterPanel.repaint()
//        breadcrumbPanel.revalidate()
//        breadcrumbPanel.repaint()
//        parentPanel.revalidate()
//        parentPanel.repaint()
//        if(!showPanels) {
//            masterPanel.setVisible(false)
//            breadcrumbPanel.setVisible(false)
//            visibleInspectors.each {it.setVisible(false)}
//        }

            reloadPanels()

//        SwingUtilities.invokeLater { updateAllGUIs() }
        }
    }

    mapViewWindowForSizeReferences.addComponentListener(viewportSizeChangeListener)


}


def loadHistoryWithFPHistory() {
    history.clear()

    NodeHistory historyInternal = Controller.currentController.getExtension(NodeHistory.class)

    historyInternal.nodes
            .reverse()
            .collect { it.node }
            .unique()
            .each { history.addElement(it) }
}



def createHighlighters() {
    Controller controllerForHighlighter = Controller.currentModeController.controller
    controllerForHighlighter.getExtension(HighlightController.class).addNodeHighlighter(new NodeHighlighter() {

        @Override
        public boolean isNodeHighlighted(NodeModel node, IMapSelection selection, boolean isPrinting) {
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
        public boolean isNodeHighlighted(NodeModel node, IMapSelection selection, boolean isPrinting) {
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




    controllerForHighlighter.getExtension(HighlightController.class).addNodeHighlighter(new NodeHighlighter() {

        @Override
        public boolean isNodeHighlighted(NodeModel node, IMapSelection selection, boolean isPrinting) {
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
        public boolean isNodeHighlighted(NodeModel node, IMapSelection selection, boolean isPrinting) {
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


}

def refreshHighlighterCache() {
    cachedHighlightedNodes.clear()
}

def refreshHighlighterCacheTags() {
    cachedHighlightedNodesTags.clear()
}
