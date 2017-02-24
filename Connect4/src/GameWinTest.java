import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JLabel;

import org.junit.Test;

public class GameWinTest {
	GameCourt tester = new GameCourt(new JLabel(""));
	
	public void resetBoard(GameCourt g){
		for (int i=0; i<7; i++){
			for (int j=0; j<8; j++){
				g.board[i][j].fill(SpaceType.EMPTY);
			}
		}
	}
	
	@Test
    public void testFourHorizontal() {
    	resetBoard(tester);
    	tester.colChoice = 0;
    	tester.placePiece();
    	tester.placePiece();
    	tester.colChoice =1;
    	tester.placePiece();
    	tester.placePiece();
    	tester.colChoice =2;
    	tester.placePiece();
    	tester.placePiece();
    	tester.colChoice =3;
    	    
        assertTrue("Four in bottom row", tester.placePiece());
    }
	
	@Test
	public void testFourHorizontalOppositeDirection() {
    	resetBoard(tester);
    	tester.colChoice = 3;
    	tester.placePiece();
    	tester.placePiece();
    	tester.colChoice =2;
    	tester.placePiece();
    	tester.placePiece();
    	tester.colChoice =1;
    	tester.placePiece();
    	tester.placePiece();
    	tester.colChoice =0;
    	    
        assertTrue("Four in bottom row", tester.placePiece());
    }
	
	@Test
    public void testFourVertical() {
    	resetBoard(tester);
    	tester.colChoice = 0;
    	tester.placePiece();
    	tester.colChoice =1;
    	tester.placePiece();
    	tester.colChoice =0;
    	tester.placePiece();
    	tester.colChoice =1;
    	tester.placePiece();
    	tester.colChoice =0;
    	tester.placePiece();
    	tester.colChoice =1;
    	tester.placePiece();
    	tester.colChoice =0;
    	    
        assertTrue("Four on left column", tester.placePiece());
    }
	
	@Test
	public void testFourRightDiagonalUp() {
    	resetBoard(tester);
    	tester.colChoice = 0;
    	tester.placePiece();
    	tester.colChoice =1;
    	tester.placePiece();
    	tester.colChoice =1;
    	tester.placePiece();
    	tester.colChoice =2;
    	tester.placePiece();
    	tester.colChoice =3;
    	tester.placePiece();
    	tester.colChoice =2;
    	tester.placePiece();
    	tester.placePiece();
    	tester.placePiece();
    	tester.colChoice =3;
    	tester.placePiece();
    	tester.placePiece();
    	    
        assertTrue("Four on left column", tester.placePiece());
    }
	
	@Test
	public void testLeftRightDiagonalUp() {
    	resetBoard(tester);
    	tester.colChoice = 3;
    	tester.placePiece();
    	tester.colChoice =2;
    	tester.placePiece();
    	tester.colChoice =2;
    	tester.placePiece();
    	tester.colChoice =1;
    	tester.placePiece();
    	tester.colChoice =0;
    	tester.placePiece();
    	tester.colChoice =1;
    	tester.placePiece();
    	tester.placePiece();
    	tester.placePiece();
    	tester.colChoice =0;
    	tester.placePiece();
    	tester.placePiece();
    	    
        assertTrue("Four on left column", tester.placePiece());
    }
	
	@Test
	public void checkSaver() throws FileNotFoundException{
		File testFile = new File("SaveFile.txt");
		PrintWriter writer = new PrintWriter(testFile);
		resetBoard(tester);
    	tester.colChoice = 0;
    	tester.placePiece();
    	tester.placePiece();
    	tester.colChoice =1;
    	tester.placePiece();
    	tester.placePiece();
    	tester.colChoice =2;
    	tester.placePiece();
    	tester.placePiece();
    	tester.colChoice =3;
    	
    	tester.saveToFile(writer);
    	boolean check =true;
    	char p= 'o';
    	Scanner reader = new Scanner(testFile);
    	for (int i=1;i<57;i++){
    			if (i==50||i==51||i==52||i==53){
    				if (reader.hasNextLine()) {
	    				p=(reader.nextLine().charAt(0));
	    				if (p!='R') {
	    					check=false;
	    					break;
	    				}
    				}
    			}
    			
    	else if (i==43||i==44||i==45||i==46){
    		if (reader.hasNextLine()) {
				p=(reader.nextLine().charAt(0));
				if (p!='B') {
					check=false;
					break;
				}
    		}
    	}			
    	else {
    		if (reader.hasNextLine()){
    			p=(reader.nextLine().charAt(0));
				if (p!='o') {
					check=false;
					break;
				}
    		}
    	}			
    }
    	reader.close();
    	assertTrue("Save file", check);
    	}
		
	}
	
	
	



