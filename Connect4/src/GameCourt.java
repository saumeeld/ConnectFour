/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;


/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	public boolean playing = false; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)

	// Game constants
	public static final int COURT_WIDTH = 300;
	public static final int COURT_HEIGHT = 300;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;
	File save;
	boolean win = false;
	int colChoice;
	SpaceType player;
	
	Space[][] board;
	
	public GameCourt(JLabel status)  {
		super(new GridLayout(9, 7));
		setPreferredSize(new Dimension(400, 400));
		
		board= new Space[7][8];
		player = SpaceType.PLAYER1;
		save = new File("saveFile.txt");
		
		
		for (int i = 0; i < 7*9; i++) {
			if (i<7){
				
				CustomButton b = new CustomButton(i);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						colChoice = b.col;
						win = GameCourt.this.placePiece();
						
						PrintWriter saver;
						try {
							saver = new PrintWriter(save);
							GameCourt.this.saveToFile(saver);
							saver.close();
						} catch (FileNotFoundException e) {
										e.printStackTrace();
						}
						
						
						if (win){
							String winningPlayer;
							if(player == SpaceType.PLAYER1) winningPlayer= "Player 2 is the winner";
							else winningPlayer= "Player 1 is the winner";
							JOptionPane.showMessageDialog(null, winningPlayer, "Winner", JOptionPane.INFORMATION_MESSAGE); 
							GameCourt.this.reset();
						}
					}
				});
				add(b);
			}
			else{
				Space s = new Space();
				int x= i%7;
				int y = (i/7)-1;
				 add(s);
				 board[x][y] = s;
			}
        }
		
		
		
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
			
		

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		this.status = status;
	
		
	}
	
	public boolean placePiece(){
		boolean checkPiece, checkSurroundings;
		for (int j=0;j<8;j++){
			
			if(board[colChoice][j].type!= SpaceType.EMPTY){
				 
				if (j==0){
					JOptionPane.showMessageDialog(null, "You can't place here", "Error", JOptionPane.INFORMATION_MESSAGE); 
					return false;
				}
					board[colChoice][j-1].fill(player);
					checkPiece= checkWin(board,colChoice,j-1);
				
					if(colChoice==0 && j-1==0){
							checkSurroundings = 
									(checkWin(board, colChoice,j) ||
									 checkWin(board, colChoice+1,j-1)||
									 checkWin(board, colChoice+1,j));
					}
				
					else if(colChoice==0){
							checkSurroundings = 
									(checkWin(board, colChoice,j) ||
									 checkWin(board, colChoice+1,j-1)||
									 checkWin(board, colChoice+1,j-2)||
									 checkWin(board, colChoice+1,j));
					}
					else if(colChoice==6 && j-1==0){
							checkSurroundings = 
									(checkWin(board, colChoice,j) ||
							   		 checkWin(board, colChoice-1,j-1)||
									 checkWin(board, colChoice-1,j));
					}
					else if(colChoice==6){
						checkSurroundings = 
								(checkWin(board, colChoice,j) ||
								 checkWin(board, colChoice-1,j-1)||
								 checkWin(board, colChoice-1,j-2)||
								 checkWin(board, colChoice-1,j));
					}
					else if(j-1==0){
						checkSurroundings = 
								(checkWin(board, colChoice,j) ||
								 checkWin(board, colChoice-1,j)||
								 checkWin(board, colChoice+1,j)||
								 checkWin(board, colChoice+1,j-1)||
								 checkWin(board, colChoice-1,j-1));
					}
					else{
						checkSurroundings = 
								(checkWin(board, colChoice,j) ||
								 checkWin(board, colChoice-1,j)||
								 checkWin(board, colChoice+1,j)||
								 checkWin(board, colChoice+1,j-1)||
								 checkWin(board, colChoice-1,j-1)||
								 checkWin(board, colChoice+1,j-2)||
								 checkWin(board, colChoice-1,j-2));
					}
					
					if (player == SpaceType.PLAYER1){
						player = SpaceType.PLAYER2;
					}
					else if(player == SpaceType.PLAYER2){
						player = SpaceType.PLAYER1;
					}
					
				return(checkPiece||checkSurroundings);
			}
			
		
		
			else if(j==7){
				
					board[colChoice][j].fill(player);
					checkPiece= checkWin(board,colChoice,7);
				
					if(colChoice==0){
						checkSurroundings= 
								(checkWin(board, colChoice+1,7) ||
								 checkWin(board, colChoice+1,6));
					}
					else if(colChoice==6){
						checkSurroundings= 
								(checkWin(board, colChoice-1,7) ||
								 checkWin(board, colChoice-1,6));
					}
				
					else{
						checkSurroundings= 
								(checkWin(board, colChoice+1,7) ||
								 checkWin(board, colChoice+1,6) ||
								 checkWin(board, colChoice-1,7) ||
								 checkWin(board, colChoice-1,6));
					}
					
					if (player == SpaceType.PLAYER1){
						player = SpaceType.PLAYER2;
					}
					else if(player == SpaceType.PLAYER2){
						player = SpaceType.PLAYER1;
					}
					
					return (checkPiece || checkSurroundings);
			}		
		}
		
		
		if (player == SpaceType.PLAYER1){
			player = SpaceType.PLAYER2;
		}
		else if(player == SpaceType.PLAYER2){
			player = SpaceType.PLAYER1;
		}
		
		return true;
	}
	
	
	
	
	
	public static boolean checkWin(Space[][]board, int col, int row){
		if (board[col][row].type == SpaceType.EMPTY) return false;
		
		boolean down=true,left=true,right=true,rUpDiag=true,
				lUpDiag=true,rDownDiag=true,lDownDiag=true;
		
		if(row<=4){
			for (int j=row; j<row+3;j++){
					if(board[col][j].type != board[col][j+1].type){
						down =false;
					}
				}		
			}
		else down=false;
		
		if(col<=3){
			for (int i=col; i<col+3;i++){
				if(board[i][row].type != board[i+1][row].type){
					right =false;
				}
			}		
		}
		else right = false;
		
		if(col>=3){
			for (int i=col; i>col-3;i--){
				if(board[i][row].type != board[i-1][row].type){
					left =false;
				}
			}		
		}
		else left=false;
		
		if (row>=3 && col<=3){
			int i=col;
			int j=row;
			while ((i<col+3)&&(j>row-3)){
				if(board[i][j].type != board [i+1][j-1].type){
					rUpDiag=false;
				}
				i++;
				j--;
			}
		}
		else rUpDiag=false;
		
		if (row<=4 && col<=3){
			int i=col;
			int j=row;
			while ((i<col+3)&&(j<row+3)){
				if(board[i][j].type != board [i+1][j+1].type){
					rDownDiag=false;
				}
				i++;
				j++;
			}
		}
		else rDownDiag=false;
		
		if (row<=4 && col>=3){
			int i=col;
			int j=row;
			while ((i>col-3)&&(j<row+3)){
				if(board[i][j].type != board [i-1][j+1].type){
					lDownDiag=false;
				}
				i--;
				j++;
			}
		}
		else lDownDiag=false;
		
		if (row>=3 && col>=3){
			int i=col;
			int j=row;
			while ((i>col-3)&&(j>row-3)){
				if(board[i][j].type != board [i-1][j-1].type){
					lUpDiag=false;
				}
				i--;
				j--;
			}
		}
		else lUpDiag=false;
		return down||right||left||rUpDiag||rDownDiag||lUpDiag||lDownDiag;
		
	}
	
	
	
	
	
	


	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
	
		try {
			player = SpaceType.PLAYER1;
			win = false;
			
			PrintWriter resetter;
			resetter = new PrintWriter(save);
			for (int i=0;i<7;i++){
				for(int j=0;j<8;j++){
					resetter.print('o');
				}
			}
		
		for (int i =0; i<7; i++){
			for (int j=0; j<8; j++){
				board[i][j].fill(SpaceType.EMPTY);
			}
		}
		
		resetter.close();
		
		playing = true;
		status.setText("Running...");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	
	public void saveToFile(PrintWriter p){
		String saving = "x";
		for (int i=0;i<7;i++){
			for(int j=0;j<8;j++){
				if (board[i][j].type == SpaceType.EMPTY) saving = "o \n";
				else if (board[i][j].type == SpaceType.PLAYER1) saving = "R \n";
				else if (board[i][j].type == SpaceType.PLAYER2) saving = "B \n";
				p.print(saving);
			}
		}
	String lastPlayer = "1";
	if (player == SpaceType.PLAYER1) lastPlayer = "1";
	else lastPlayer = "2";
	p.print(lastPlayer);
	
		
	}
	
	public void openSave(){
		
		try {
			Scanner reader = new Scanner(save);
			char spaceInfo = 'o';
			SpaceType type = SpaceType.EMPTY;
			for(int i=0;i<7;i++){
				for(int j=0;j<8;j++){
					if (reader.hasNextLine()){
						spaceInfo = reader.nextLine().charAt(0);
					}
					if (spaceInfo == 'o') type = SpaceType.EMPTY;
					else if (spaceInfo == 'R') type = SpaceType.PLAYER1;
					else if (spaceInfo == 'B') type = SpaceType.PLAYER2;
				board[i][j].fill(type);
				}
			}
			if(reader.hasNextLine()){
			if (reader.nextLine()== "1") player = SpaceType.PLAYER1;
			else player= SpaceType.PLAYER2;
			}
			else{
				player = SpaceType.PLAYER1;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {

			// update the display
			repaint();
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
