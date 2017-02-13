//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.*;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class P4_Radovic_Danilo_MSController {
	
	public static P4_Radovic_Danilo_MSModel myModel;
	public static P4_Radovic_Danilo_MSView myView;
	public static P4_Radovic_Danilo_AI ai;
	
	static BufferedImage flag;
	static BufferedImage question_mark;
	static BufferedImage zero_blank;
	static BufferedImage one;
	static BufferedImage two;
	static BufferedImage three;
	static BufferedImage four;
	static BufferedImage five;
	static BufferedImage six;
	static BufferedImage seven;
	static BufferedImage eight;
	static BufferedImage mine;
	static BufferedImage covered;
	
	int timesAIUsed = 0;
	
	
	
	static BufferedImage[][] imgArray; 
	
	static int row;
	static int col;
	
	public P4_Radovic_Danilo_MSController(){
		
		
		
		myModel = new P4_Radovic_Danilo_MSModel();
		myView = new P4_Radovic_Danilo_MSView(this);
		
		myView.addCustomMouseListener(new CustomMouseListener());
		myView.addButtonListeners(new CustomActionListener());
		myView.addTheKeyListeners(new CustomKeyListener());
		
		
		try{
			flag = ImageIO.read(new File("bomb_flagged.gif"));
			question_mark = ImageIO.read(new File("bomb_question.gif"));
			zero_blank = ImageIO.read(new File("num_0.gif"));
			one = ImageIO.read(new File("num_1.gif"));
			two = ImageIO.read(new File("num_2.gif"));
			three = ImageIO.read(new File("num_3.gif"));
			four = ImageIO.read(new File("num_4.gif"));
			five = ImageIO.read(new File("num_5.gif"));
			six = ImageIO.read(new File("num_6.gif"));
			seven = ImageIO.read(new File("num_7.gif"));
			eight = ImageIO.read(new File("num_8.gif"));
			mine = ImageIO.read(new File("bomb_death.gif"));
			covered = ImageIO.read(new File("blank.gif"));
		}catch(Exception e){
			System.out.println("Error reading the image");
		}
		
		myModel.setArrays();
		myModel.setUpInnerArray();
		myModel.setUpOuterArray();
		
		
		imgArray = new BufferedImage[myModel.width][myModel.width];
		
		for (int i = 0; i < imgArray.length; i++) {
			for (int j = 0; j < imgArray.length; j++) {
				imgArray[i][j] = covered;
				//System.out.println(myModel.outerArray[i][j]);
			}
		}
		
		ai = new P4_Radovic_Danilo_AI(myModel.outerArray);

		
	}
	
	static public BufferedImage getImgArray(int row, int col){
		return imgArray[row][col];
	}

	static public int getNumRows(){
		return myModel.width;
	}
	static public int getNumCols(){
		return myModel.width;
	}
	static public void updateTime(){
		myView.time++;
	}
	static public int getRow(){
		return row;
	}
	static public int getCol(){
		return col;
	}
	
	static public void resetTilesUncovered(){
		myModel.tilesUncovered = 0;
	}
	
	static public int getNumMines(){
		return myModel.numMines;
	}
	static public void setNumMines(String newMines){
		myModel.numMines = Integer.parseInt(newMines);
		myView.minesLeft = Integer.parseInt(newMines);
	}
	
	static public void setWidth(String w){
		myModel.width = Integer.parseInt(w);
	}
	static public void updateMinesFlagged(int plus){ //1 for plus 2 for minus
		if(plus == 1){
			myView.minesLeft++;
		}else{
			myView.minesLeft--;
		}
		
	}
	
	static public void uncoverAllMines(){
		for (int i = 0; i < imgArray.length; i++) {
			for (int j = 0; j < imgArray.length; j++) {
				convertInnerArrayMinesToImgArray(i, j);
			}
		}
		myView.repaint();
	}
	
	static public void convertInnerArrayMinesToImgArray(int i, int j){
		if(myModel.innerArray[i][j] == "*"){
			imgArray[i][j] = mine;
			//myView.repaint();
		}
		myView.repaint();
	}
	
	static public void convertOuterArrayToImgArray(int i, int j){
		if(myModel.outerArray[i][j] == null){
			imgArray[i][j] = zero_blank;
			//myView.repaint();
		}else if(myModel.outerArray[i][j].equals("1")){
			imgArray[i][j] = one;
			//myView.repaint();
		}else if(myModel.outerArray[i][j].equals("2")){
			imgArray[i][j] = two;
			//myView.repaint();
		}else if(myModel.outerArray[i][j].equals("3")){
			imgArray[i][j] = three;
			//myView.repaint();
		}else if(myModel.outerArray[i][j].equals("4")){
			imgArray[i][j] = four;
			//myView.repaint();
		}else if(myModel.outerArray[i][j].equals("5")){
			imgArray[i][j] = five;
			//myView.repaint();
		}else if(myModel.outerArray[i][j].equals("6")){
			imgArray[i][j] = six;
			//myView.repaint();
		}else if(myModel.outerArray[i][j].equals("7")){
			imgArray[i][j] = seven;
			//myView.repaint();
		}else if(myModel.outerArray[i][j].equals("8")){
			imgArray[i][j] = eight;
			//myView.repaint();
		}else if(myModel.outerArray[i][j] == "*"){
			imgArray[i][j] = mine;
			//myView.repaint();
		}else if(myModel.outerArray[i][j].equals("F")){
			imgArray[i][j] = flag;
			//myView.repaint();
		}
		myView.repaint();
	}
	
	
	private class CustomActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			//System.out.println("Action -> " + e.getActionCommand());
			
			if(e.getSource() == myView.t){
				updateTime();
				myView.timeTracker.setText(Integer.toString(myView.time));
			}
			
			if(e.getActionCommand() != null){
				if (e.getActionCommand().equals("New Game")){
					//reset all graphics, reset innerArray and outerArray
					myModel.setArrays();
					myModel.setUpInnerArray();
					myModel.setUpOuterArray();
					resetTilesUncovered();
					setNumMines(Integer.toString(myModel.numMines));
					for (int i = 0; i < imgArray.length; i++) {
						for (int j = 0; j < imgArray.length; j++) {
							imgArray[i][j] = covered;
						}
					}
					ai = new P4_Radovic_Danilo_AI(myModel.outerArray);
					myView.time = 0;
					myView.t.start();
					myView.repaint();
				}
				if (e.getActionCommand().equals("Exit")){
					myView.dispose();
				}
				if (e.getActionCommand().equals("Set # Mines")){
					//set the number, redraw grid using setArrays()
					myView.t.stop();
					myView.time = 0;
					setNumMines(JOptionPane.showInputDialog("Enter the number of mines that will be on the board: "));
					
					myModel.setArrays();
					myModel.setUpInnerArray();
					myModel.setUpOuterArray();
					resetTilesUncovered();
					for (int i = 0; i < imgArray.length; i++) {
						for (int j = 0; j < imgArray.length; j++) {
							imgArray[i][j] = covered;
						}
					}
					ai = new P4_Radovic_Danilo_AI(myModel.outerArray);
					
					myView.t.start();
					
					myView.repaint();
					
				}
				if (e.getActionCommand().equals("Set Grid Size")){
					//set the number, redraw the grid, use setArrays()
					myView.t.stop();
					myView.time = 0;
					setWidth(JOptionPane.showInputDialog("Enter the width of tiles that will be on the board: "));
					myModel.setArrays();
					myModel.setUpInnerArray();
					myModel.setUpOuterArray();
					resetTilesUncovered();
					setNumMines(Integer.toString(myModel.numMines));
					
					imgArray = new BufferedImage[myModel.width][myModel.width];
					for (int i = 0; i < imgArray.length; i++) {
						for (int j = 0; j < imgArray.length; j++) {
							imgArray[i][j] = covered;
						}
					}
					ai = new P4_Radovic_Danilo_AI(myModel.outerArray);
					myView.t.start();
					myView.repaint();
				}
				if (e.getActionCommand().equals("About")){
					JEditorPane helpContent;
					try {
						helpContent = new JEditorPane(new URL("file:///Users/XYZ/Desktop/about.html"));
						JScrollPane helpPane = new JScrollPane(helpContent); 
						JOptionPane.showMessageDialog(null, helpPane, "About", JOptionPane.PLAIN_MESSAGE, null);
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				if (e.getActionCommand().equals("How To Play")){
					JEditorPane helpContent;
					try {
						helpContent = new JEditorPane(new URL("file:///Users/XYZ/Desktop/howToPlay.html"));
						JScrollPane helpPane = new JScrollPane(helpContent); 
						JOptionPane.showMessageDialog(null, helpPane, "How To Play", JOptionPane.PLAIN_MESSAGE, null);
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			

			
			
		}
		
	}
	
	public static void playGameAndUpdateBoard(boolean flagBool, int row, int col){ //used by both AI and user
		
		myModel.playGame(flagBool, row, col);
		
		if(flagBool == true){
			updateMinesFlagged(2);
		}
		
		for (int i = 0; i < imgArray.length; i++) {
			for (int j = 0; j < imgArray.length; j++) {
				ai.board[i][j] = myModel.outerArray[i][j];
				
				convertOuterArrayToImgArray(i, j);
				
			}
		}
		
		if(myModel.outerArray[row][col] == "*"){
			//show pop up menu saying you lost
			//yes = 0, no = 1, cancel = 2
			
			uncoverAllMines();
			
			myView.t.stop();
			myView.time = 0;
			int userAnswer = JOptionPane.showConfirmDialog(null, "You LOST!! Do you want to play another game?");
			if(userAnswer == 0){ //yes
				//reset all graphics, reset innerArray and outerArray
				myModel.setArrays();
				myModel.setUpInnerArray();
				myModel.setUpOuterArray();
				resetTilesUncovered();
				setNumMines(Integer.toString(myModel.numMines));
				
				for (int i = 0; i < imgArray.length; i++) {
					for (int j = 0; j < imgArray.length; j++) {
						imgArray[i][j] = covered;
					}
				}
				ai = new P4_Radovic_Danilo_AI(myModel.outerArray);
				myView.repaint();
				myView.t.start();
			}else if(userAnswer == 1){ //no
				myView.dispose();
			}else{
				//user input was cancelled, do nothing
			}
			
		}
		
		
		if(myModel.tilesUncovered >= (getNumRows()*getNumRows() - getNumMines())){
			//print congratulations 
			myView.t.stop();
			myView.time = 0;
			
			int userAnswer = JOptionPane.showConfirmDialog(null, "You WON!! Do you want to play another game?");
			if(userAnswer == 0){ //yes
				//reset all graphics, reset innerArray and outerArray
				myModel.setArrays();
				myModel.setUpInnerArray();
				myModel.setUpOuterArray();
				resetTilesUncovered();
				setNumMines(Integer.toString(myModel.numMines));
				
				for (int i = 0; i < imgArray.length; i++) {
					for (int j = 0; j < imgArray.length; j++) {
						imgArray[i][j] = covered;
					}
				}
				ai = new P4_Radovic_Danilo_AI(myModel.outerArray);
				
				myView.repaint();
				myView.t.start();
			}else if(userAnswer == 1){ //no
				myView.dispose();
			}else{
				//user input was cancelled, do nothing
			}
		}
	}
	
	
	private class CustomKeyListener extends KeyAdapter{
		
		public void keyReleased(KeyEvent e){
			if(e.getKeyChar() == 'a'){
				//System.out.println("AI is being called through key presses");
				ai.aiActionWhenMousePressed();
				
			}
		}
		
	}

	private class CustomMouseListener implements MouseListener {
	    @Override
	    public void mouseClicked(MouseEvent e) {
			
	    	col = ( e.getX() - e.getX()%(myView.widthOfImage) ) / myView.widthOfImage;
			row = ( e.getY() - e.getY()%(myView.heightOfImage) ) / myView.heightOfImage;
	    	
			if(e.getModifiersEx() == 128 && e.getButton() == MouseEvent.BUTTON1){
				//tell view to place flag image,  tell model that outerArray is a flag
				if(imgArray[row][col] == covered){			//&& imgArray[row][col] != flag
					myModel.playGame(true, row, col);
					imgArray[row][col] = flag;
					updateMinesFlagged(2);
					myView.repaint();
				}else if(imgArray[row][col] == flag){
					//myModel.playGame(true, row, col);
					imgArray[row][col] = question_mark;				//1 for plus, 2 for minus
					myView.repaint();
				}else if(imgArray[row][col] == question_mark){ //mark is 0
					myModel.playGame(true, row, col);
					imgArray[row][col] = covered;
					updateMinesFlagged(1);
					myView.repaint();
				}else{}
				
				
				
			}else if(e.getButton() == MouseEvent.BUTTON1){
				
				playGameAndUpdateBoard(false, row, col);
				
			}else{}
			
	    }

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		
	}

	
	
	
}
