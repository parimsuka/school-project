/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;

import java.awt.Window;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
/**
 *
 * @author sukap
 */
public class ParkGUI {
    private JFrame frame;
    GraphicsEnvironment graphics;
    GraphicsDevice device;
    private GameEngine gameArea;
    
    public GameEngine getGameArea() {
		return gameArea;
	}

	public ParkGUI(String playername) {
        this.graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.device = this.graphics.getDefaultScreenDevice();
        (this.frame = new JFrame("AmusementPark")).setDefaultCloseOperation(3);
        this.gameArea = new GameEngine(this.frame, playername);
        this.frame.getContentPane().add(this.gameArea);
        this.frame.setUndecorated(true);
        this.frame.setResizable(false);
        this.frame.pack();
        this.frame.setVisible(true);
        this.device.setFullScreenWindow(this.frame);
    }
}
