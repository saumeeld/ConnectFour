import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Space extends JPanel {
	Color color;
	SpaceType type;
	
	public Space(){
		super();
	    color = Color.GRAY;
		setPreferredSize(new Dimension(50,50));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		type = SpaceType.EMPTY;
	}
	
	public void fill(SpaceType type){
		this.type = type;
		if (type == SpaceType.PLAYER1) {
			color = Color.GREEN;
		} else if (type == SpaceType.PLAYER2) {
			color = Color.BLUE;
		}
		else if (type == SpaceType.EMPTY){
			color = Color.GRAY;
		}
			

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
