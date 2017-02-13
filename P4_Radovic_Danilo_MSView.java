import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class P4_Radovic_Danilo_MSView extends JFrame{
	
	// Attributes
		Color color = Color.RED;
		
		Timer t;
		JTextArea textMinesTracker;
		JTextArea timeTracker;
		MyDrawingPanel drawingPanel;
		
		int delay = 1000; //one second
		int time = 0;
		
		static BufferedImage img;
		
		
		int widthOfImage;
		int heightOfImage;
		
		JMenuItem newGameItem;
		JMenuItem exitGameItem;
		JMenuItem setNumMinesItem;
		JMenuItem setGridSizeItem;
		JMenuItem aboutItem;
		JMenuItem howToPlayItem;
		
		int width = P4_Radovic_Danilo_MSController.getNumRows();
		
		int minesLeft = P4_Radovic_Danilo_MSController.getNumMines();
		
	public P4_Radovic_Danilo_MSView(P4_Radovic_Danilo_MSController controller){
		
		try{
			img = ImageIO.read(new File("blank.gif"));
		}catch (IOException e){
			e.printStackTrace();
		} 
		
		
		
		// Create Java Window
		//JFrame window = new JFrame("Danilo Radovic Period 4");
		this.setBounds(100, 100, 440, 550);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// Create GUI elements
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu helpMenu = new JMenu("Help");
		
		//add action listeners to all 6 of these
		newGameItem = new JMenuItem("New Game");
		exitGameItem = new JMenuItem("Exit");
		setNumMinesItem = new JMenuItem("Set # Mines");
		setGridSizeItem = new JMenuItem("Set Grid Size");
		aboutItem = new JMenuItem("About");
		howToPlayItem = new JMenuItem("How To Play");
		
		fileMenu.add(newGameItem);
		fileMenu.add(exitGameItem);
		editMenu.add(setNumMinesItem);
		editMenu.add(setGridSizeItem);
		helpMenu.add(aboutItem);
		helpMenu.add(howToPlayItem);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		this.setJMenuBar(menuBar);

		//JTextField
		textMinesTracker = new JTextArea();
		textMinesTracker.setText(Integer.toString(-1));
		textMinesTracker.setBounds(20, 425, 150, 70);
		
		timeTracker = new JTextArea();
		timeTracker.setText(Integer.toString(time));
		timeTracker.setBounds(250, 425, 150, 70);
		
		// JPanel to draw in
		drawingPanel = new MyDrawingPanel();
		drawingPanel.setBounds(20, 20, 400,400);
		drawingPanel.setBorder(BorderFactory.createEtchedBorder());
		
		
		// Add GUI elements to the Java window's ContentPane
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		  
		JPanel timeElapsed = new JPanel();
		timeElapsed.setBorder(BorderFactory.createTitledBorder("Time Elapsed"));
		timeElapsed.setBounds(250, 425, 150, 70);
		timeElapsed.add(timeTracker);
		
		JPanel panelNumMines = new JPanel();
		panelNumMines.setBorder(BorderFactory.createTitledBorder("# of Mines Left"));
		panelNumMines.setBounds(20, 425, 150, 70);
		panelNumMines.add(textMinesTracker);

		
		mainPanel.add(drawingPanel);		
		mainPanel.add(timeElapsed);
		mainPanel.add(panelNumMines);

		this.getContentPane().add(mainPanel);

		// set visibility to true
		this.setVisible(true);
		
	}
	
	
	
	private class MyDrawingPanel extends JPanel{
		


		// Not required, but gets rid of the serialVersionUID warning.  Google it, if desired.
		static final long serialVersionUID = 1234567890L;
		
		public MyDrawingPanel(){
			textMinesTracker.setText(Integer.toString(minesLeft));
			//System.out.println("track when constructor called");
		}

		public void paintComponent(Graphics g) { //repaint calls this
			
			//width = 10
			
			textMinesTracker.setText(Integer.toString(minesLeft));
			
			widthOfImage = this.getWidth()/P4_Radovic_Danilo_MSController.getNumCols();
			heightOfImage = this.getHeight()/P4_Radovic_Danilo_MSController.getNumRows();
			
			//paint entire grid
				
			for (int i = 0; i < P4_Radovic_Danilo_MSController.getNumRows(); i++) {
				for (int j = 0; j < P4_Radovic_Danilo_MSController.getNumCols(); j++) {
					g.drawImage(P4_Radovic_Danilo_MSController.getImgArray(i , j), j*widthOfImage, i*heightOfImage, widthOfImage, heightOfImage, null);
				}
			}
		
			
		}
		
	}
	
	public void addTheKeyListeners(KeyListener k){
		this.addKeyListener(k);
		this.requestFocus();
	}
	
	public void addCustomMouseListener(MouseListener m) {
	     drawingPanel.addMouseListener(m);
	}

	public void addButtonListeners(ActionListener a) {
		newGameItem.addActionListener(a);
		exitGameItem.addActionListener(a);
		setNumMinesItem.addActionListener(a);
		setGridSizeItem.addActionListener(a);
		aboutItem.addActionListener(a);
		howToPlayItem.addActionListener(a);
		t = new Timer(delay, a);
		t.start();
	}

	
	
}
