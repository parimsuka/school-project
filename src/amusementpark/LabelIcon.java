/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author sukap
 */
public class LabelIcon extends JLabel{
    private ImageIcon imgIcon;
    
    public LabelIcon(final String filepath, final Point p, final int width, final int height) throws IOException {
        this.imgIcon = new ImageIcon(ImageIO.read(new File(filepath)));
        this.setBounds(p.x, p.y, width, height);
        this.resizeIcon(width, height);
        this.setIcon(this.imgIcon);
    }
    
    public void resizeIcon(final int x, final int y) {
        this.imgIcon = new ImageIcon(this.imgIcon.getImage().getScaledInstance(x, y, 4));
    }
    
    public boolean collides(LabelIcon other) {
        Rectangle rect = other.getBounds();
        Rectangle result = SwingUtilities.computeIntersection(getX(), getY(), getWidth(), getHeight(), rect);
        return (result.getWidth() > 0 && result.getHeight() > 0);
    }
    
}
