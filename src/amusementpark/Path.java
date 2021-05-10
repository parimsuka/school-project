/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;

import java.awt.Point;
import java.io.IOException;
import java.util.Stack;

/**
 *
 * @author sukap
 */
public class Path extends Building{
    
        //Showing in the menu
        private LabelIcon imgMenu;
        private ImageButton deleteButton;
	
    public Path (LabelIcon img, LabelIcon imgMenu) {
        super (img);
        this.imgMenu = imgMenu;
        
        try {
            deleteButton = new ImageButton("data/close6.png", new Point(900, 700), 50, 50);
        } catch (IOException e) {

        }

        imgMenu.setVisible(false);
        deleteButton.setVisible(false);
    }

    public LabelIcon getImgMenu() {
        return imgMenu;
    }

    public ImageButton getDeleteButton() {
        return deleteButton;
    }

    public void showMenu () {
        imgMenu.setVisible(true);
        deleteButton.setVisible(true);
    }
    
    public void hideMenu () {
        imgMenu.setVisible(false);
        deleteButton.setVisible(false);
    }
    
    public void processMovement (Grid grid, Point clickedPoint, Point newLocation) {
        clickedPoint = grid.getPoint(clickedPoint);
        if (clickedPoint.x != -1) {
             grid.remove1BoxMatrix(clickedPoint.x, clickedPoint.y);
        }
        
        newLocation = grid.getPoint(newLocation);
        grid.add1BoxMatrix(newLocation.x, newLocation.y, 1);  //1 for path
   
    }
}
