/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 *
 * @author Incognita
 */
public class Grid {

    static final int UNIT_SIZE = 40;
    static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    static final int SCREEN_WIDTH = (int) size.getWidth();
    static final int SCREEN_HEIGHT = (int) size.getHeight();
    private int x, y;
    private ArrayList<ArrayList<Sprite>> gridBoxes;
    
    private int [] [] matrix;
    
    

    public Grid() {
        gridBoxes = new ArrayList<ArrayList<Sprite>>();
        matrix = new int [14] [38];
        
        x = 7;
        y = UNIT_SIZE*2;
        
        
        //Initializing new arraylists
        for(int i = 0; i < 14; i++)  {
            gridBoxes.add(new ArrayList<Sprite>());
        }

        //43 rectangles for a row
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 38; j++) {
                gridBoxes.get(i).add(new Sprite(new Point(x, y), UNIT_SIZE, UNIT_SIZE, 5, Color.gray));
                x += UNIT_SIZE;
            }
            x = 7;
            y += UNIT_SIZE;
        }
    }

    public void draw(final Graphics2D g2) {
        for (int i = 0; i < gridBoxes.size(); i++)
            for (Sprite s: gridBoxes.get (i)) {
                s.drawBorder(g2);
            }
    }
    
    public void colorGrid(final Graphics2D g2) {
        for (int i = 0; i < gridBoxes.size(); i++)
            for (Sprite s: gridBoxes.get (i)) {
                s.drawBorder(g2);
            }
    }
    
    public void setColorDefault(final Graphics2D g2) {
        for (int i = 0; i < gridBoxes.size(); i++)
            for (Sprite s: gridBoxes.get (i)) {
                s.setColor(Color.gray);
            }
    }
    
    public ArrayList<ArrayList<Sprite>> getGrid(){
        return gridBoxes;
    }

    public int[][] getMatrix() {
        return matrix;
    }
    
    public Point getPoint (Point p) {
        for (int i = 0; i < gridBoxes.size(); i++) {
            for (int j = 0; j < gridBoxes.get (i).size(); j++) {
                if (gridBoxes.get(i).get(j).contains(p))
                    return new Point(i, j);
            }
        }
    
        return new Point (-1, -1);
    }
    
    public void add1BoxMatrix (int x, int y, int nr) {
        matrix [x] [y] = nr;
    }
    
    public void remove1BoxMatrix (int x, int y) {
        matrix [x] [y] = 0;
    }
    
        
    public void addBuildingMatrix (int x, int y, int nr) {
        matrix [x] [y] = nr;
        matrix [x] [y+1] = nr;
        matrix [x+1] [y] = nr;
        matrix [x+1] [y+1] = nr;
    }
    
    public void removeBuildingMatrix (int x, int y) {
        matrix [x] [y] = 0;
        matrix [x] [y+1] = 0;
        matrix [x+1] [y] = 0;
        matrix [x+1] [y+1] = 0;
    }
    
    public void showMatrix () {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix [i] [j] + " ");
            }
            System.out.println();
        }
    }
}

