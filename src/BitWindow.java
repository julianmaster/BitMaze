import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class BitWindow extends JFrame {
	
	private boolean on = false;
	
	private Color ONColor = Color.WHITE;
	private Color OFFColor = Color.BLACK;
	
	public BitWindow(String title, Dimension dimension) {
		this.setTitle(title);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(dimension);
		this.pack();
        this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public BitWindow(String title, Dimension dimension, String frameIconPath) {
		this(title, dimension);
		try {
			this.setIconImage(ImageIO.read(this.getClass().getResource(frameIconPath)));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setONColor(Color oNColor) {
		ONColor = oNColor;
	}
	
	public void setOFFColor(Color oFFColor) {
		OFFColor = oFFColor;
	}
	
	@Override
	public void paint(Graphics g) {
		if(on) {
			g.setColor(ONColor);
		}
		else {
			g.setColor(OFFColor);
		}
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void setOn(boolean on) {
		this.on = on;
	}
	
	public boolean isOn() {
		return on;
	}
}
