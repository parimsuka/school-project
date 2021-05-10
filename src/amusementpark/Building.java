/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;

import java.awt.Point;

/**
 *
 * @author sukap
 */
public abstract class Building {
    private LabelIcon img;
    
    public Building (LabelIcon img) {
        this.img = img;
    }
    
    public LabelIcon getImg () {
        return img;
    }

    public void setImg(LabelIcon img) {
        this.img = img;
    }

    
    public abstract void showMenu ();
    public abstract void hideMenu ();
    
    public abstract void processMovement (Grid grid, Point clickedPoint, Point newLocation);
}
