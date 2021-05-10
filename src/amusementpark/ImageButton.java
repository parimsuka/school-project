/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;

import java.io.IOException;
import javax.swing.Icon;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 *
 * @author sukap
 */
public class ImageButton extends JButton{
    private ImageIcon imgIcon;
    
    public ImageButton(final String filepath, final Point p, final int width, final int height) throws IOException {
        this.imgIcon = new ImageIcon(ImageIO.read(new File(filepath)));
        this.setBounds(p.x, p.y, width, height);
        this.resizeIcon(width, height);
        this.setIcon(this.imgIcon);
    }
    
    public void resizeIcon(final int x, final int y) {
        this.imgIcon = new ImageIcon(this.imgIcon.getImage().getScaledInstance(x, y, 4));
    }
}
