/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Incognita
 */
public class Repairman extends Person{
    
    private Game destination;
    
    private long repairTime;
    private boolean isRepairing;
    
    private long salaryTime;
    
    
    public Repairman(LabelIcon img){
       super(img);
       destination = null;
       isRepairing = false;
       
       salaryTime = System.currentTimeMillis();
   }

    public long getSalaryTime() {
        return salaryTime;
    }
    
    

    public Game getDestination() {
        return destination;
    }

    public boolean getIsRepairing() {
        return isRepairing;
    }

    public void setIsRepairing(boolean isRepairing) {
        this.isRepairing = isRepairing;
    }
    
    

    public void setDestination(Game destination) {
        this.destination = destination;
    }

    public long getRepairTime() {
        return repairTime;
    }

    public void setRepairTime() {
        this.repairTime = System.currentTimeMillis();
    }
    
    
    
    
    public void repair (Grid grid, Game g) {
        Point p1 = grid.getPoint(getImg().getLocation());
        Point p2 = grid.getPoint(g.getImg().getLocation());

        ArrayList<Point> coordinates = BFS.useBFS(grid.getMatrix(), p1.x, p1.y, p2.x, p2.y);
        if (coordinates != null) {
            setCoords(coordinates, grid);
        }
    }
    
    public void goBack (Grid grid) {
        Point p1 = grid.getPoint(getImg().getLocation());
        Point p2 = new Point(6, 0);

        ArrayList<Point> coordinates = BFS.useBFS(grid.getMatrix(), p1.x, p1.y, p2.x, p2.y);
        if (coordinates != null) {
            setCoords(coordinates, grid);
        }
    }
}
