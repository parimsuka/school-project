package amusementpark;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.util.Random;

public class Guest extends Person{

    //move
    private int mood;
    private long waitingTime;
    private Random rand;
    
    private ArrayList<Trash> trash;
    private int randThrowTime;
    
    
    private boolean hasEaten;
    private long hasEatenTime;
    
    private Building destination;
    private boolean isMoving = true;
    private boolean isWaiting = false;
    
    private Stack<Point> coords;

    public Guest(LabelIcon img) {
        super (img);
        mood = 50;
        rand = new Random();
        hasEaten = false;
        trash = new ArrayList<Trash>();
        randThrowTime = rand.nextInt(20);
    }

    public int getRandThrowTime() {
        return randThrowTime;
    }

    public void setRandThrowTime(int randThrowTime) {
        this.randThrowTime = randThrowTime;
    }
    
    public long getHasEatenTime() {
        return hasEatenTime;
    }

    public void setHasEatenTime() {
        this.hasEatenTime = System.currentTimeMillis();
    }


    public ArrayList<Trash> getTrash() {
        return trash;
    }

    public void setTrash(ArrayList<Trash> trash) {
        this.trash = trash;
    }

    public boolean isIsMoving() {
        return isMoving;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public boolean isHasEaten() {
        return hasEaten;
    }

    public void setHasEaten(boolean hasEaten) {
        this.hasEaten = hasEaten;
        
        if (hasEaten)
            setHasEatenTime();
    }

    public boolean isIsWaiting() {
        return isWaiting;
    }

    public void setIsWaiting(boolean isWaiting) {
        this.isWaiting = isWaiting;
        
        if (isWaiting)
            waitingTime = System.currentTimeMillis();
    }
    
    public void restartWaiting () {
        waitingTime = System.currentTimeMillis();
    }

    public boolean getIsMoving() {
        return isMoving;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public Building getDestination() {
        return destination;
    }

    public void setDestination(Building destination) {
        this.destination = destination;
    }
    
    public void improveMood() {
        mood+=5;
    }

    public void decreaseMood() {
        mood-=1; 
    }

    public LabelIcon getImg() {
        return img;
    }


    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public void switchImgAngry() {
        try {
            img = new LabelIcon("data/angry.png", img.getLocation(), img.getWidth(), img.getHeight());
        }catch (IOException exc) {
            
        }
    }
    
    public void switchImgDefault() {
        try {
            img = new LabelIcon("data/default.png", img.getLocation(), img.getWidth(), img.getHeight());
        }catch (IOException exc) {
            
        }
        
    }
    
    public void switchImgHappy() {
        try {
            img = new LabelIcon("data/happy.png", img.getLocation(), img.getWidth(), img.getHeight());
        }catch (IOException exc) {
            
        }
        
    }
    
    /*public void chooseRandomRestaurant (ArrayList<Restaurant> restaurants, Grid grid) {
        int n;
        do {
            n = rand.nextInt(restaurants.size());
        } while (restaurants.get(n).equals(destination));

        Restaurant r = restaurants.get(n);

        
    }
    
    public void chooseRandomGame (ArrayList<Game> games, Grid grid) {

        
        
        Game g = games.get(n);

        Point p1 = grid.getPoint(getImg().getLocation());
        Point p2 = grid.getPoint(g.getImg().getLocation());
        ArrayList<Point> coordinates = BFS.useBFS(grid.getMatrix(), p1.x, p1.y, p2.x, p2.y);
        if (coordinates != null) {
            setDestination(g);
            setIsMoving(true);
            setCoords(coordinates, grid);
        }
    }*/
    
    public boolean isGuestInsideAGame (ArrayList<Game> game) {
        for (Game g : game)
            for (Guest gst : g.getGuestsInGame()) {
                if (gst.equals(this))
                    return true;
            }
        
        for (Game g : game)
            for (Guest gst : g.getGuestsQueue()) {
                if (gst.equals(this))
                    return true;
            }
        
        return false;
    }
    
    public void eatOrPlay (ArrayList<Building> gamesAndRestaurants, Grid grid) {
        
        int n;
        
        if (gamesAndRestaurants.isEmpty()) {
            return;
        }
        
        
        do {
            n = rand.nextInt(gamesAndRestaurants.size());
        } while (gamesAndRestaurants.get(n).equals(destination));
        
        Building b = gamesAndRestaurants.get (n);
        
        Point p1 = grid.getPoint(getImg().getLocation());
        Point p2 = grid.getPoint(b.getImg().getLocation());
        ArrayList<Point> coordinates = BFS.useBFS(grid.getMatrix(), p1.x, p1.y, p2.x, p2.y);
        if (coordinates != null) {
            setDestination(b);
            setIsMoving(true);
            setCoords(coordinates, grid);
        }

    }
    
}
