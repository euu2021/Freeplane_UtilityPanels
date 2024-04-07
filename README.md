


# UtilityPanels for FreePlane
UtilityPanels is a script that creates integrated panels in the Freeplane interface.

![explorer_SLM5empCmN](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/6b102950-96c3-4ac6-93de-09d66a2ff058)


# Features

## Panels

### Recent nodes panel

- A list with recently selected nodes.
  
![javaw_302ZRQ6PV4](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/ab9062e7-b0e3-4f36-b85d-1395ee6fdb6b)


### Pinned nodes panel
  
![chrome_aAZD6TPbjq](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/c35baba5-7a97-49d4-b938-acd88ce61cae)

### Quick search panel



## Inspector

- Inspector tooltip:

![javaw_JGBwl2m7hB](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/97cac151-1934-45b1-80f4-364a84a2d5f4)

- Inception navigation in the Inspector tooltip:

![javaw_53IlSUvy3F](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/d069a63d-0ba6-4be4-84a8-c79739c9ab53)

## Other Features

- Drag and Drop operations
It's possible to do drag and drop between itens in the panel, nodes in the map, and vice versa.

![dragff](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/778afe20-0d6e-4fd5-8896-a6a8f36816fe)


![dragff2](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/9e3ac515-f84d-4ac1-92a1-3b9805bbda63)



- Clicking the panel item navigates to the node.

![javaw_UtEw62hVSn](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/ed551399-268f-47bd-a8b8-81703f954db4)





## Other interesting aspects:

- The panel follows the active tab

![chrome_6rDGvc0k22](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/5f95de56-da52-4847-9506-ad2004f3c5e5)

- deleting a node in the map, immediatly deletes the item in the list:

 ![chrome_Fs2nVtzqKp](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/a642a622-71f4-41c7-bb65-fff36764d095)

 - the nodes get marked with a ⚠️ sign when clicking on them will lead to a Jump Out in the current map view:

![javaw_t0YcE237zu](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/ac451cbb-ac9a-4034-a45f-b462922a8d5f)

- The panels are transparent:

![javaw_2jN2VSzuVX](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/b794fe6b-8fa7-4b25-99f6-d2a2e7b19073)




## Current limitations that will be improved:
- pinned nodes are not saved on FreePlane restart
- the navigation doesn't work across multiple maps
- the MapOverview (View->Controls->Map overview) must be active in order to avoid the panel blink. It's possible to use without the MapOverview, but the blinking can be annoying.
- inception navigation is currently limited to two inspector levels

# Todo

Features
- Drag and Drop interacion. So, the user can make drag and drop operations like in the Freeplane map. For example, dragging an item from the list into a node in the map, will make the item node be moved as a child of the map node. Also, for creating connectors.
- Drafts Panel. A panel to store nodes that the user wants to keep as a draft, instead of including in the map.
- Post it panel. A type of panel that is a simple text panel where the user can keep some text. It then will have buttons to quickly tranform the text into a node to be inserted as a child of the select node, or be transformed in a draft node and included in the Draft panel.
- Querry search panel. A type of panel where the user can create a search criteria, and it will show all node that match that criteria, and keep updating that list.

Implementation
- easy positioning of the panels, with anchoring
- more items in the list; and scrollbars on hover
- buttons on the panel to do things like minimize, close, resize, move etc
- avoid symbols that can have rendering problems. See issue https://github.com/freeplane/freeplane/discussions/1752#discussioncomment-8933090.
