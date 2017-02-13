import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class P4_Radovic_Danilo_AI {

	int state = 1; //by default, state is in GUESSING_MODE
	int GUESSING_MODE = 1;
	int FLAG_MODE = 2;
	int REVEAL_MODE = 3;
	
	int currentRow;
	int currentCol;
	
	int w;
	
	int timeInModeLoop = 0;
	int nextTimeInLoop = 0;
	
	String[][] board;
	
	
	int[][] neigh = { {0,1}, {0,-1}, {1,0},	{-1,0},	{1,1}, {1,-1}, {-1,1}, {-1,-1}};

	public P4_Radovic_Danilo_AI(String[][] newBoard){ 	//board is the outerArray in the model
		w = newBoard.length;
		board = new String[w][w];
		//System.out.println("AI board:");
		for (int i = 0; i < newBoard.length; i++) {
			for (int j = 0; j < newBoard.length; j++) {
				board[i][j] = newBoard[i][j];
				//System.out.print(board[i][j]);
			}
			//System.out.println();
		}
		
		
	}
	
	
	public int totalRevealed(){
		int count = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(board[i][j] != "-"){
					count++;
				}
			}
		}
		return count;
	}
	
	public int trackNeighbors(int row, int col){
		int numNeighbors = 0;
		
		for(int i = 0; i < 8; i++){
			if(neigh[i][0] + row < board.length && neigh[i][0] + row >= 0){
				if(neigh[i][1] + col < board.length && neigh[i][1] + col >= 0){
					if(board[neigh[i][0] + row][neigh[i][1] + col] == "-" || board[neigh[i][0] + row][neigh[i][1] + col] == "F"){
						numNeighbors++;
					}
				}
			}
		}
		return numNeighbors;
	}
	
	public int trackFlags(int row, int col){
		int numNeighbors = 0;
		
		for(int i = 0; i < 8; i++){
			if(neigh[i][0] + row < board.length && neigh[i][0] + row >= 0){
				if(neigh[i][1] + col < board.length && neigh[i][1] + col >= 0){
					if(board[neigh[i][0] + row][neigh[i][1] + col] == "F"){
						numNeighbors++;
					}
				}
			}
		}
		return numNeighbors;
	}
	
	public boolean isNumber(String s){
		//System.out.println(s);
		if(s != null && (s.equals("1") == true || s.equals("2") == true || s.equals("3") == true || s.equals("4") == true || s.equals("5") == true || s.equals("6") == true || s.equals("7") == true || s.equals("8") == true)){
			return true;
		}else{
			return false;
		}
	}
	
	public void flagNeighbors(int row, int col){
		for(int i = 0; i < 8; i++){
			if(neigh[i][0] + row < board.length && neigh[i][0] + row >= 0){
				if(neigh[i][1] + col < board.length && neigh[i][1] + col >= 0){
					if(board[neigh[i][0] + row][neigh[i][1] + col] == "-"){
						P4_Radovic_Danilo_MSController.playGameAndUpdateBoard(true, (neigh[i][0] + row), (neigh[i][1] + col));
					}
				}
			}
		}
	}
	
	public void uncoverNeighbors(int row, int col){
		for(int i = 0; i < 8; i++){
			if(neigh[i][0] + row < board.length && neigh[i][0] + row >= 0){
				if(neigh[i][1] + col < board.length && neigh[i][1] + col >= 0){
					if(board[neigh[i][0] + row][neigh[i][1] + col] == "-"){
						P4_Radovic_Danilo_MSController.playGameAndUpdateBoard(false, (neigh[i][0] + row), (neigh[i][1] + col));
					}
				}
			}
		}
	}
	
	public void aiActionWhenMousePressed(){
	
		if(state == GUESSING_MODE){
			timeInModeLoop = 0;
			//System.out.println("In guessing mode");
			//pick random position that is NOT flagged and IS covered (so "-")
			if(totalRevealed() >= (int)(0.2*w*w) ){
				state = FLAG_MODE;
			}else{} //state stays at GUESSING_MODE
			
			Random r = new Random();
			int ranRow;
			int ranCol;
			do{
				ranRow = r.nextInt(w);//bound stated is EXCLUSIVE
				ranCol = r.nextInt(w);
				//System.out.println(w);
				//System.out.println("Is this stalling?????");
			}while(board[ranRow][ranCol] == null || board[ranRow][ranCol].equals("-") != true);
			
			P4_Radovic_Danilo_MSController.playGameAndUpdateBoard(false, ranRow, ranCol);
			
			if(totalRevealed() >= (int)(0.2*w*w) ){
				state = FLAG_MODE;
			}else{} //state stays at GUESSING_MODE
			
		}else if(state == FLAG_MODE){
			//go through entire board, when you hit a tile w/ a number on it,
	//				check all 8 neighbors for how many of those are covered.
	//				if # covered = # on tile, flag it
			nextTimeInLoop = timeInModeLoop;
			//System.out.println("In flag mode");
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if(isNumber(board[i][j])==true && trackNeighbors(i,j) == Integer.parseInt(board[i][j])){ 	
						//make sure that tile has a # on it
						flagNeighbors(i , j);
					}else{
						//do nothing for this one
					}
					
				}
			}
			
			state = REVEAL_MODE;
		}else if(state == REVEAL_MODE){
			//go through entire board, when you hit a tile w/ a # on it,
	//				check all 8 neighbors for how many of those are mines.
	//				if # mines = # on tile, reveal all other tiles that are NOT flagged
			//System.out.println("in reveal mode");
			timeInModeLoop = 0;
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if(isNumber(board[i][j])==true && (trackFlags(i,j) == Integer.parseInt(board[i][j]) ) ){
						//if the # of tiles flagged = # on tile, then uncover rest of them
						uncoverNeighbors(i,j); 	
						timeInModeLoop++;
					}else{
						//do nothing
					}
				}
			}
			//System.out.println("time in mode loop:" + timeInModeLoop);
			//System.out.println("next time in loop : " + nextTimeInLoop);
			//only go to guessing mode if no determination can be made
			if(timeInModeLoop == nextTimeInLoop){
				state = GUESSING_MODE;
			}else{
				state = FLAG_MODE;
				//timeInModeLoop = 0;
			}	
				
		}else{
			System.out.println("This mode should not even be reached...");
		}//shouldn't even reach this code...
	
	
	}
	
}