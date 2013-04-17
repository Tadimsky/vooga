package vooga.fighter.model;

import java.awt.Dimension;
import java.util.List;

import util.Location;
import vooga.fighter.controller.GameManager;
import vooga.fighter.model.loaders.MapLoader;
import vooga.fighter.model.loaders.MenuGridLoader;
import vooga.fighter.model.objects.MenuObject;
import vooga.fighter.model.utils.State;
import vooga.fighter.model.utils.UpdatableLocation;

public class MenuGrid {
	private int myColumns;
	private int myRows;
	private MenuObject [][] myGrid;
	private List<MenuObject> myMenuObjects;
	
	public MenuGrid(String Id) {
		myMenuObjects = new MenuGridLoader(Id, this).getMenuObjects();
		myGrid = createEmptyGrid(myMenuObjects.size());
		fillGrid(myMenuObjects, myGrid, myColumns, myRows);
	}
	
	public MenuObject [][] createEmptyGrid(int listsize){
		if(listsize < 4){
			myRows = 1;
			myColumns = listsize;
		}
		else if(listsize%2 == 0){
			myColumns = listsize/2;
			myRows = 2;
		}
		else if(listsize%2 == 1){
			myColumns = listsize/2;
			myRows = 3;
		}
		return new MenuObject[myRows][myColumns];
	}
	
	public MenuObject getMenuObject(int i, int j){
		return myGrid[i][j];
	}
	
	public List<MenuObject> getMenuObjects(){
		return myMenuObjects;
	}
	
	private void fillGrid(List<MenuObject> list, MenuObject [][] grid, int columns, int rows){
		for(int i = 0; i < list.size(); i ++){
			int RowNumber = 0;
				int ColumnNumber = i*(RowNumber+1) -columns; 
				if(ColumnNumber <columns){
				grid[RowNumber][ColumnNumber] = list.get(i);
				int xloc =  (ColumnNumber)*GameManager.SIZE.width/(columns) + GameManager.SIZE.width/(2*columns);
				int yloc =  (RowNumber)*GameManager.SIZE.height/(rows) + GameManager.SIZE.height/(2*rows);
				list.get(i).setLocation(new UpdatableLocation(xloc,yloc));
				int count  = 0;
				for(Object s : list.get(i).getStates()){
					State state = (State) s;
					Dimension size = new Dimension(GameManager.SIZE.width/(2*columns), GameManager.SIZE.height/(2*rows));
					state.populateSize(size, count);
					count ++;
				}
				}
				else{
					RowNumber ++;
				}
			
		}
	}

}
