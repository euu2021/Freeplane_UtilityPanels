> [!WARNING]  
> There are 2 versions: add-on and [script](https://github.com/euu2021/Freeplane_UtilityPanels/blob/main/utilityPanels.groovy) . The add-on version is updated less frequently, so it may not be compatible with latest Freeplane version. If the add-on version doesn't start, try the script version.

---
<p align="center">
  <img width="500"  src="https://github.com/user-attachments/assets/d0ad8158-e22e-400b-961f-720248cf20ee">
</p>

---

UtilityPanels is a script that creates integrated panels in the Freeplane interface.

![java_ID1CJC5S2N](https://github.com/user-attachments/assets/e56b1af7-15e7-4080-8c63-ec02d8f3a65d)



# Main Features

## Recent nodes panel

- A list with recently selected nodes.
  
![java_Mthv2Gb2bT](https://github.com/user-attachments/assets/7f250dfc-7b71-4745-a95c-c283930544f7)


## Pinned nodes panel

Pinned nodes are saved on FreePlane restarts
  
![chrome_aAZD6TPbjq](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/c35baba5-7a97-49d4-b938-acd88ce61cae)

## Tags panel

<img width="524" alt="2025-03-21_14-58" src="https://github.com/user-attachments/assets/eb2fbac3-0705-4c7f-a927-8c28083c9a1c" />

## Quick Search panel, with transversal search

Results are highlighted everywhere: in the panels, in the inspectors, and in the map:

![image](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/00766e42-84a9-4acf-bdaf-4f85b7ac63c8)

## Breadcrumbs bar

A bar above the map:

![java_8h8bXxzVTx](https://github.com/user-attachments/assets/e6b47c1e-2802-4cd0-a598-d083977f20e4)

## Inspector

- Inspector tooltip:

![java_QqJ9lfYHoY](https://github.com/user-attachments/assets/609886fe-cb7c-4a7a-8f01-51cac31b294a)


- Infinite inception navigation in the Inspector tooltip:

![java_Fau6l6yDOX](https://github.com/user-attachments/assets/bc309037-7e2c-4d05-9da9-0e80f35c3b42)


# Other features

## Drag and Drop operations
It's possible to do drag and drop (move nodes) between itens in the panel, nodes in the map, and vice versa.

![dragff3 borda2](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/e284b3d5-5662-4826-8a49-d37b323578d7)

## Clicking the panel item navigates to the node.

![javaw_UtEw62hVSn](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/ed551399-268f-47bd-a8b8-81703f954db4)

## Tags Panel features
Single click adds the node to selected nodes.

Right click gives the option to remove from selected nodes.

Hoverving over the tags on the panel show the nodes with the tag: 

<img width="417" alt="2025-03-21_17-46" src="https://github.com/user-attachments/assets/43d509f2-9fa5-48b6-8630-5bed4db68ab1" />

Combine tags for the search
<img width="319" alt="2025-03-21_17-47" src="https://github.com/user-attachments/assets/7e3391b6-b9fe-4748-92c5-5fd1617ef253" />


## Settings
Right click on a list item:

<img width="194" alt="2025-03-21_17-48" src="https://github.com/user-attachments/assets/b797c9c9-cd09-44c3-a1b4-84fec2b3a5e7" />


<img width="534" alt="2025-03-21_17-49" src="https://github.com/user-attachments/assets/584a7e73-80f0-4c77-8883-18c9b7decb75" />


## Update Selection
It makes the first inspect be about the selected node. The first inspector has this option in the 3 lines menu:
<img width="173" alt="2025-03-21_17-50" src="https://github.com/user-attachments/assets/68c01502-0597-4155-89ae-09304e487131" />
  
## Hotkeys
If Freeplane is already using the hotkey for other function (it happens with the Ctrl+F, for example), it's necessary, first, to clear the Freeplane hotkey assignment.

Currently:
- Ctrl + F: focus the QuickSearch panel search box
- Ctrl + U: toggles hide and show panels

Can be changed in settings window.

## Transversal Search in QuickSearch panel
(inspired by the [Jumper Add-On](https://github.com/lilive/Freeplane-Jumper))
Transversal search is a technique that finds relevant nodes not only based on the text they contain, but also by looking at the content of their parent nodes. For example, if you search for the terms "a", "b", and "c", a node with the text "a" might be considered a match if one of its ancestors contains "b" and another contains "c". This means that even if the node itself doesn't include all the search terms, the surrounding context provided by its ancestry is taken into account, yielding more meaningful and flexible search results.

![image](https://github.com/user-attachments/assets/205ecca4-95bf-4a0b-90a0-304013f9f1b5)


When performing a search with multiple terms (for example, "a", "b", and "c"), the algorithm processes each node in the mind map as follows:

1. **Initial Check:** 
   The algorithm first checks if the node's text contains at least one of the search terms. If the node does not contain any of the terms, it is immediately disregarded.

2. **Secondary Verification:** 
   If the node contains at least one search term directly, the algorithm then verifies that every search term that is missing in the node’s text is found in one of its ancestor nodes. In other words, for each term not directly present in the node, the algorithm looks up the node’s parent chain to see if the term appears there.

3. **Match Condition:** 
   The node is considered a match (i.e., a positive result) only if every search term is present either directly in the node or in one of its ancestors.

This approach allows the search function to combine context from a node and its hierarchical position. As a result, a node might be considered relevant if it has partial information, while the remaining search terms are provided by its ancestral context.


## Hide panels

Toggle hide/show panels with Ctrl + U hotkey.

## Freeze Inspectors

The inspectors have a freeze option, so the user can easily do drag and drop operations, or navigate the map while the inspectors stay on screen.

<img width="393" alt="2025-03-21_14-55" src="https://github.com/user-attachments/assets/2cb3bd71-cbb1-4d3b-b1b5-40b9a0c2d673" />




# Other interesting aspects:

- The panel follows the active tab

![chrome_6rDGvc0k22](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/5f95de56-da52-4847-9506-ad2004f3c5e5)

- deleting a node in the map, immediately deletes the item in the list:

 ![chrome_Fs2nVtzqKp](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/a642a622-71f4-41c7-bb65-fff36764d095)

 - the nodes get marked with a ⚠️ sign in the panels when clicking on them will lead to a Jump Out in the current map view:

![javaw_t0YcE237zu](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/ac451cbb-ac9a-4034-a45f-b462922a8d5f)

- the nodes get marked with a ○ sign in the panels when it has children:

 ![image](https://github.com/user-attachments/assets/a4378a6d-4857-446b-9b8b-26970afa4f6a)

- the selected node get marked with a red border in the panels:
 
![image](https://github.com/user-attachments/assets/935f57cd-5537-44b8-8771-3bd6e13e123d)

- The panels are transparent:

![javaw_2jN2VSzuVX](https://github.com/euu2021/Freeplane_UtilityPanels/assets/77707706/b794fe6b-8fa7-4b25-99f6-d2a2e7b19073)

- Panels autoexpand on mouse hover:
  
![java_yDJOmoi0pM](https://github.com/user-attachments/assets/62a33efc-0d9d-483f-ad6f-f4f7945f951b)

- Panels autosizing according to the size of the Freeplane window:

![SKdsOzKgIy](https://github.com/user-attachments/assets/aeecc029-bb00-4ebf-a614-c6902049c800)

Proportions can be changed in settings window.

- Text autosizing in inspector (notice that the size is smaller when the text is longer, in order to try to fit the maximum ammount of text)

![java_ggp6aUZLfC](https://github.com/user-attachments/assets/2e09bd1f-053e-4599-b984-6c82f809febc)

Minimum and maximum sizes be changed in settings window.

- Right to left (RTL) text orientation

Can be activated in settings window.

- Auto position (to avoid overlap with the selected node):

![java_KQbwI5AUzb](https://github.com/user-attachments/assets/4a9e8e4d-8549-49d1-95c2-c33f6e9c6dcc)

- Inside right click contextual menu: Option to open new view with node selected


![image](https://github.com/user-attachments/assets/0ae712b8-f96d-44ee-af48-ec8a79c3f239)





# Disclaimer

Some of the features modify the information in the map, so do extensive testing before using the script on important maps. And create backups.
