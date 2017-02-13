import java.util.Random;
import java.util.Scanner;

public class P4_Radovic_Danilo_MSModel {

	public static String [][] innerArray;
	public static String [][] outerArray;

	static int[][] neigh = { {0,1}, {0,-1}, {1,0},	{-1,0},	{1,1}, {1,-1}, {-1,1}, {-1,-1}};
	
	static int width = 10;
	static int numMines = 10; //make sure numMines < width*width //DEFAULT = 10
	static int tilesUncovered = 0;
	
	static int userRow = 0; //default
	static int userCol = 0; //default
	
	static String DASH = "-";
	static String MINE = "*";
	static String BLANK = " ";
	static String FLAG = "F";
	
	static String flagOrUncover;
	static boolean inGame = true; //by default you are playing the game
	
	public static void setArrays(){
		innerArray = new String[width][width];
		outerArray = new String[width][width];
	}
	
	public static void setUpInnerArray(){
		//places with neither a mine nor number are "null" strings
		placeMines();
		placeNumbers();
	}
	
	public static void setUpOuterArray(){
		//at first it is just all DASH
		for (int i = 0; i < outerArray.length; i++) {
			for (int j = 0; j < outerArray.length; j++) {
				outerArray[i][j] = DASH;
			}
		}
		System.out.println();
		System.out.println();
	}
	
	
	public static void placeMines(){
		//randomly places mines
		int c = 0;
		Random r = new Random();
		
		while(c < numMines){
			int x = r.nextInt(width);
			int y = r.nextInt(width);
			
			if(innerArray[x][y] == MINE){
				//do nothing
			}else{
				innerArray[x][y] = MINE;
				c++;
			}
		}
	}
	public static void placeNumbers(){
		int numNeighbors = 0;
		for (int i = 0; i < innerArray.length; i++) {
			for (int j = 0; j < innerArray.length; j++) {
				numNeighbors = trackNeighbors(i , j);
				if(numNeighbors == 0){
					//do nothing, keep string at null
				}else if(numNeighbors != 0 && innerArray[i][j] != MINE){
					innerArray[i][j] = Integer.toString(numNeighbors);
				}else{
					//if mine, do nothing
				}
			}
		}
	}
	

	public static int trackNeighbors(int row, int col){
		int numNeighbors = 0;
		
		for(int i = 0; i < 8; i++){
			if(neigh[i][0] + row < width && neigh[i][0] + row >= 0){
				if(neigh[i][1] + col < width && neigh[i][1] + col >= 0){
					if(innerArray[neigh[i][0] + row][neigh[i][1] + col] == MINE){
						numNeighbors++;
					}
				}
			}
		}
		return numNeighbors;
	}
	
	
	
	public static void playGame(boolean flag, int tempRow, int tempCol){ 	//use userX and userY for starting position
		
		if(flag == true){ //flag
			if(outerArray[tempRow][tempCol] == FLAG){
				outerArray[tempRow][tempCol] = DASH;
			}else{
				outerArray[tempRow][tempCol] = FLAG;
			}
		}else{ //uncover
			if(innerArray[tempRow][tempCol] == MINE){
				tilesUncovered++;
				outerArray[tempRow][tempCol] = innerArray[tempRow][tempCol];
			}else if(innerArray[tempRow][tempCol] == null){
				//call recursive function and let it do its magic
				recursivePlay(tempRow, tempCol);
			}else{
				outerArray[tempRow][tempCol] = innerArray[tempRow][tempCol];
				tilesUncovered++;
			}
		}
			
	}
	
	public static void recursivePlay(int tempRow, int tempCol){
		
		outerArray[tempRow][tempCol] = innerArray[tempRow][tempCol];
		tilesUncovered++;
		
		for(int i = 0; i < 8; i++){
			if(neigh[i][0] + tempRow < innerArray.length && neigh[i][0] + tempRow >= 0){
				if(neigh[i][1] + tempCol < innerArray.length && neigh[i][1] + tempCol >= 0){
					if( outerArray[neigh[i][0] + tempRow][neigh[i][1] + tempCol] == DASH || outerArray[neigh[i][0] + tempRow][neigh[i][1] + tempCol] == FLAG){
						
						
						if(innerArray[neigh[i][0] + tempRow][neigh[i][1] + tempCol] == null){ //if is clear
							//call recursive function
							recursivePlay(tempRow + neigh[i][0] , tempCol + neigh[i][1]);
						}else if(innerArray[neigh[i][0] + tempRow][neigh[i][1] + tempCol] == MINE){ //if is mine
							//do nothing, don't uncover it or touch it
						}else{//if is a number 1 to 8
							//uncover only this one
							outerArray[tempRow+ neigh[i][0]][tempCol+ neigh[i][1]] = innerArray[tempRow+ neigh[i][0]][tempCol+ neigh[i][1]];
							tilesUncovered++;
						}
						
						
					}else{ //if already uncovered (BLANK)
						//do nothing to it
					}
					
					
				}
			}
		}
		
	}
}
