/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Incognita
 */
public abstract class Person {
    
   LabelIcon img;
   
    private Stack<Point> coords;
   
   public Person(LabelIcon img){
       this.img=img;
       coords = new Stack<Point>();
   }

    public Stack<Point> getCoords() {
        return coords;
    }

   
   
    public LabelIcon getImg() {
        return img;
    }
    
    
        public void setCoords(ArrayList<Point> coords, Grid grid) {
        Point p;
        for (int i = 0; i < coords.size() - 1; i++) {
            p = coords.get(i);
            Point p2 = coords.get(i + 1);

            int X = p.x - p2.x;
            int Y = p.y - p2.y;

            if (X == 0 && Y == 1) {
                //Move to the right
                moveRight(p, grid);

            } else if (X == 0 && Y == -1) {
                //Move to the left
                moveLeft(p, grid);

            } else if (X == -1 && Y == 0) {
                //Move up
                moveUp(p, grid);

            } else {
                //Move down
                moveDown(p, grid);

            }

        }

        p = coords.get(coords.size() - 1);
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX(), grid.getGrid().get(p.x).get(p.y).getY()));
    }

    public void moveRight(Point p, Grid grid) {
        coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX(), grid.getGrid().get(p.x).get(p.y).getY()));
        coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX() - 10, grid.getGrid().get(p.x).get(p.y).getY()));
        coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX() - 20, grid.getGrid().get(p.x).get(p.y).getY()));
        coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX() - 30, grid.getGrid().get(p.x).get(p.y).getY()));
    }

    public void moveLeft(Point p, Grid grid) {
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX(), grid.getGrid().get(p.x).get(p.y).getY()));
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX() + 10, grid.getGrid().get(p.x).get(p.y).getY()));
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX() + 20, grid.getGrid().get(p.x).get(p.y).getY()));
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX() + 30, grid.getGrid().get(p.x).get(p.y).getY()));
    }

    public void moveUp(Point p, Grid grid) {
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX(), grid.getGrid().get(p.x).get(p.y).getY()));
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX(), grid.getGrid().get(p.x).get(p.y).getY() + 10));
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX(), grid.getGrid().get(p.x).get(p.y).getY() + 20));
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX(), grid.getGrid().get(p.x).get(p.y).getY() + 30));
    }

    public void moveDown(Point p, Grid grid) {
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX(), grid.getGrid().get(p.x).get(p.y).getY()));
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX(), grid.getGrid().get(p.x).get(p.y).getY() - 10));
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX(), grid.getGrid().get(p.x).get(p.y).getY() - 20));
        this.coords.add(new Point(grid.getGrid().get(p.x).get(p.y).getX(), grid.getGrid().get(p.x).get(p.y).getY() - 30));

    }
   
    
}
