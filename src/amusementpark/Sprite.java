/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;

import static amusementpark.Grid.SCREEN_HEIGHT;
import static amusementpark.Grid.SCREEN_WIDTH;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
/**
 *
 * @author sukap
 */
public class Sprite {
    protected Point p;
    protected int width;
    protected int height;
    protected int roundLength;
    protected Color color;
    
    public Sprite(final Point point, final int width, final int height, final int roundLength, final Color color) {
        this.p = point;
        this.width = width;
        this.height = height;
        this.roundLength = roundLength;
        this.color = color;
    }
    
    public void draw(final Graphics2D g2) {
        g2.setColor(this.color);
        g2.fillRoundRect(this.p.x, this.p.y, this.width, this.height, this.roundLength, this.roundLength);
    }
    
     
    
   /* public void drawgrid(final Graphics2D g2){
        for (int x = 0; x < SCREEN_WIDTH / GameTile.tileSize; x++) { // loop through as many tiles fit the x-axis.
                    for (int y = 0; y < SCREEN_HEIGHT / GameTile.tileSize; y++) { // do the same with y-axis
                        GameTile greenTile = new GameTile(0, true, x * GameTile.tileSize, y * GameTile.tileSize);
                        gameWindow.add(greenTile);
                    }
                }
        
    }*/
    public void drawBorder (final Graphics2D g2) {
        g2.setColor(color);
        g2.drawRect(p.x, p.y, width, height);
    }
    
    public boolean collides(final Sprite other) {
        final Rectangle rect = new Rectangle(this.p.x, this.p.y, this.width, this.height);
        final Rectangle otherRect = new Rectangle(other.p.x, other.p.y, other.width, other.height);
        return rect.intersects(otherRect);
    }
    
    public boolean collides(final Rectangle other) {
        final Rectangle rect = new Rectangle(this.p.x, this.p.y, this.width, this.height);
        return rect.intersects(other);
    }
    
    public boolean contains (final Point p) {
        final Rectangle rect = new Rectangle(this.p.x, this.p.y, this.width, this.height);
        return rect.contains(p);
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setColor(Color c){
        this.color=c;
    }
    
    public int getX() {
        return this.p.x;
    }
    
    public void setX(final int x) {
        this.p.x = x;
    }
    
    public int getY() {
        return this.p.y;
    }
    
    public void setY(final int y) {
        this.p.y = y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Sprite other = (Sprite)obj;
        return this.p.x == other.p.x && this.p.y == other.p.y;
    }
}
