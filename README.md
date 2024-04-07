


# UtilityPanels for FreePlane
UtilityPanels is a script that creates integrated panels in the Freeplane interface.

![explorer_SLM5empCmN](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/6b102950-96c3-4ac6-93de-09d66a2ff058)


# Features

## Panels

### Recent nodes panel

- A list with recently selected nodes.
  
![javaw_302ZRQ6PV4](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/ab9062e7-b0e3-4f36-b85d-1395ee6fdb6b)


### Pinned nodes panel

Pinned nodes are saved on FreePlane restarts
  
![chrome_aAZD6TPbjq](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/c35baba5-7a97-49d4-b938-acd88ce61cae)

### Quick Search panel

A quick search that uses the Jumper search engine, so it has transversal search, and other good stuff. See instructions below about how to integrate with Jumper.

The Jumper settings are used in the Quick Search panel. In other words, the settings that the user chooses inside Jumper are used by the Quick Search panel.

Results are highlighted everywhere: in the panels, in the inspectors, and in the map:

![image](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/00766e42-84a9-4acf-bdaf-4f85b7ac63c8)

Results auto update when a new match is added in the map:

![quicksearch](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/8f2ad54c-c116-4d46-802b-f02a7e693a0a)


## Inspector

- Inspector tooltip:

![javaw_JGBwl2m7hB](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/97cac151-1934-45b1-80f4-364a84a2d5f4)

- Infinite inception navigation in the Inspector tooltip:

![inception infinite](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/49e4e946-6ddc-432d-90ce-2f7276d43ced)


- Freeze Inspectors

The inspectors have a freeze option, so the user can easily do drag and drop operations, or navigate the map while the inspectors stay on screen.

![image](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/fc562dfd-f313-4eaf-8cde-ccd253947324)


## Other Features

- Drag and Drop operations
It's possible to do drag and drop (move nodes) between itens in the panel, nodes in the map, and vice versa.

![dragff3 borda2](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/e284b3d5-5662-4826-8a49-d37b323578d7)

- Clicking the panel item navigates to the node.

![javaw_UtEw62hVSn](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/ed551399-268f-47bd-a8b8-81703f954db4)





## Other interesting aspects:

- The panel follows the active tab

![chrome_6rDGvc0k22](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/5f95de56-da52-4847-9506-ad2004f3c5e5)

- deleting a node in the map, immediately deletes the item in the list:

 ![chrome_Fs2nVtzqKp](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/a642a622-71f4-41c7-bb65-fff36764d095)

 - the nodes get marked with a ⚠️ sign when clicking on them will lead to a Jump Out in the current map view:

![javaw_t0YcE237zu](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/ac451cbb-ac9a-4034-a45f-b462922a8d5f)

- The panels are transparent:

![javaw_2jN2VSzuVX](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/b794fe6b-8fa7-4b25-99f6-d2a2e7b19073)




## Current limitations that will be improved:
- I don't use multiple maps, so I don't know  how the script behaves in that situation. I know that there are limitations. For example, the navigation doesn't work across multiple maps. 
- the MapOverview (View->Controls->Map overview) must be active in order to avoid the panel blink. It's possible to use without the MapOverview, but the blinking can be annoying.
- Drag and drop operations are limited to the Move operations. In the future, I will add the others, like the option to create connectors.
- The auto update of the Quick Search panel doesn't react to all changes in the map (for example, deleting a node).


## How to make the Quick Search panel work

It needs integration with Jumper. I created a fork of Jumper that allows this integration. 

I will make it easier in the future, but now it's necessary to 
- uninstall the Jumper add-on and delete the folder that it leaves inside the user folder/addons. Don't worry, you will be able to use Jumper normally.
- download the [branch called dev in my fork](https://github.com/euu2021/Freeplane-Jumper/tree/dev) (go into Code/Download ZIP)
- unzip it into a folder
- in Freeplane, go into `Preferences…->Plugins->Scripting->Script classpath: Additional directories containing classes and/or JARs` and set here the path of the folder above

Now, to make the usual Kumper start, you need to run this script: `lilive.jumper.Jumper.start()`

If Jumper, or the Quick Search panel are not showing any results, then [see this](https://github.com/freeplane/freeplane/discussions/1770).

# Todo

Features
- Drag and Drop interaction. So, the user can make drag and drop operations like in the Freeplane map. For example, dragging an item from the list into a node in the map, will make the item node be moved as a child of the map node. Also, for creating connectors.
- Drafts Panel. A panel to store nodes that the user wants to keep as a draft, instead of including in the map.
- Post it panel. A type of panel that is a simple text panel where the user can keep some text. It then will have buttons to quickly tranform the text into a node to be inserted as a child of the select node, or be transformed into a draft node and included in the Draft panel.
- Querry search panel. A type of panel where the user can create a search criteria, and it will show all nodes that match that criteria, and keep updating that list.

Implementation
- easy positioning of the panels, with anchoring
- more items in the list; and scrollbars on hover
- buttons on the panel to do things like minimize, close, resize, move etc
- avoid symbols that can have rendering problems. See issue https://github.com/freeplane/freeplane/discussions/1752#discussioncomment-8933090.

# Disclaimer

Some of the features modify the information in the map, so do extensive testing before using the script on important maps. And create backups.
