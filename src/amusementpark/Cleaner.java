/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Incognita
 */
public class Cleaner extends Person{
    
    private Random rand;
    private final int sal=50;
    private int trashCleaned=0;
    public int getSal() {
		return sal;
	}
    public void cleanTrash() {
    	this.trashCleaned++;
    }
    public void resetTrashCleaned() {
    	this.trashCleaned=0;
    }
    public boolean deduction() {
    	return this.trashCleaned==1;
    }

	public Cleaner(LabelIcon img){
       super(img);
       rand = new Random();
   }
    
    
    public void clean (Grid grid, ArrayList<Path> path) {
        
        //Finds a random path
        if (path.size() != 0) {
            int n = rand.nextInt(path.size());
            
            Point p1 = grid.getPoint(getImg().getLocation());
            Point p2 = grid.getPoint(path.get(n).getImg().getLocation());
            //Finds way to that random path
            
            ArrayList<Point> coordinates = BFS.useBFS(grid.getMatrix(), p1.x, p1.y, p2.x, p2.y);
            if (coordinates != null) {
                setCoords(coordinates, grid);
            }
            
        }
     
    }
}
