import java.awt.Graphics;

import javax.swing.JButton;

public class CustomButton extends JButton {
	int col;
	
	public CustomButton(int i){
		super();
		col =i;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
}
