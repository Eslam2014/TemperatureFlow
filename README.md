# Temperature flow - Visualization Project

Visualization of the temperature change inside a room. The application allows the user to build 
the room structure by clicking on the room area  such that each click change the clicked cell 
into one of the following cell types: 
- Normal Cell : <br />
This cell represents default cell room with temperature of Tr. The temperature of this kind of cells is modified, over time, by averaging the temperatures of the rounding cells together with its own temperature. 
- Air Conditioner Cell : <br />
This cell represents an air conditioner that has a permanent temperature of Tc. The temperature of this kind of cells does not change over time. 
- Heater Cell : <br />
This cell represents a heater that has a permanent temperature of Th. The temperature of this kind of cells does not change over time. 
Block Cell : <br />
This cell represents a wall that is heat-proof. The temperature of this kind of cells neither changes over time nor affect the surrounding cells.
Window Cell : <br />
This cell represents a window that has a temperature of Tw. The temperature of this kind of cells does not change over time but affects the surrounding cells over time. This cell shall have a border (or any other mark) to differentiate it from Normal Cell.

Cells Type Shortcuts: <br />
Tr: Temperature of the Room. Same value of the color key average value <br />
Tc: Temperature of the Air Conditioner. Same value of the color key minimum value <br />
Th: Temperature of the Heater. Same value of the color key maximum value <br />
Tw: Temperature of the Window. Same value of the color key average value <br />

Project video here.

###  Functions
1. Update the room with the new cell size <br />
 -  When changing the cell size, you have the ability to spread the color of each cell to the new generated cells. i.e. change cell size from 40 to 20 shall spread the value of each cell to the new generated 4 cells. Thus the room state will keep the same and not re-initialized. The same when changing cell size from 20 to 40, each cell will be the average of the 4 cells constructing the new cell. Average is not the case when there is a any known cell type (Heater, Air Conditioner, Block, Window) in these 4 cells, rather, the new cell will be the same as the known cell type, and if there is many, use the following priority to update: (Heater, Air Conditioner, Window, Block).
 
2. Start/Stop visualization
  - Allows to start/stop the visualization of the temperature flow.

3. Change the min and/or max value
 - Changing the color key Min, Max values will not re-initialize the room, but will transform each cell value to match to the new min and max interval.
4. Start Visualization
 - Each cell is updated each iteration (can use timer with a tick each 10ms or let the user select the tick time) by averaging the values of the surrounded cells together with the cell value. While computing the average, you shall not use the new values of the surrounding cells for the same iteration.

