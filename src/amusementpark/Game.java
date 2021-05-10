package amusementpark;

import java.awt.Font;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Game extends Building {
    
    private Queue<Guest> guestsInGame;
    private Queue<Guest> guestsQueue;
    
    private String gameType;
    private int buildingTime;
    
    private int useLimit;
    private int useCounter;
    
    private long builtTime;
    private long operateTime;
    
    private long brokenTime;

    public enum State {
        IN_USE, WAITING_GUEST, UNDER_REPAIR, BEING_BUILT
    }

    private int capacity;
    private int operateprice;
    private int repairPrice;
    private int turnTime;
    private State gameState = State.BEING_BUILT;

    //Showing in the menu
    private LabelIcon imgMenu;
    private JLabel capacityText;
    private JLabel operatePriceText;
    private JLabel repairPriceText;
    private JLabel stateText;
    private JLabel guestsInGameText;
    private JLabel guestsQueueText;
    private ImageButton deleteButton;

    public Game(LabelIcon img, int capacity, int operateprice, int repairPrice, int turnTime, LabelIcon imgMenu, int useLimit, String gameType) {
        super(img);
        
        builtTime = System.currentTimeMillis();
        
        
        this.useLimit = useLimit;
        useCounter = 0;
        guestsInGame = new LinkedList<Guest>();
        guestsQueue = new LinkedList<Guest>();
        
        this.capacity = capacity;
        this.operateprice = operateprice;
        this.repairPrice = repairPrice;
        this.imgMenu = imgMenu;
        this.turnTime = turnTime;
        this.gameType = gameType;
        switch (gameType) {
            case "Game1":
                buildingTime = 10;
            break;
            case "Game2":
                buildingTime = 15;
            break;
            case "Game3":
                buildingTime = 20;
            break;
        }

        (capacityText = new JLabel("Maximum capacity: " + capacity)).setBounds(500, 660, 200, 25);
        (operatePriceText = new JLabel("Operate price: " + operateprice)).setBounds(500, 690, 200, 25);
        (repairPriceText = new JLabel("Repair price: " + repairPrice)).setBounds(500, 720, 200, 25);
        (stateText = new JLabel("Current state: " + gameState)).setBounds(500, 750, 300, 25);
        (guestsInGameText = new JLabel("Guests inside the Game: " + guestsInGame.size())).setBounds(500, 780, 300, 25);
        (guestsQueueText = new JLabel("Guests waiting: " + guestsQueue.size())).setBounds(500, 810, 300, 25);
        //Fonts
        capacityText.setFont(new Font("Serif", Font.BOLD, 20));
        operatePriceText.setFont(new Font("Serif", Font.BOLD, 20));
        repairPriceText.setFont(new Font("Serif", Font.BOLD, 20));
        stateText.setFont(new Font("Serif", Font.BOLD, 20));

        try {
            deleteButton = new ImageButton("data/close6.png", new Point(900, 700), 50, 50);
        } catch (IOException e) {

        }

        imgMenu.setVisible(false);
        capacityText.setVisible(false);
        operatePriceText.setVisible(false);
        repairPriceText.setVisible(false);
        stateText.setVisible(false);
        guestsInGameText.setVisible(false);
        guestsQueueText.setVisible(false);
        deleteButton.setVisible(false);
    }

    public int getBuildingTime() {
        return buildingTime;
    }

    public int getUseLimit() {
        return useLimit;
    }

    public long getBrokenTime() {
        return brokenTime;
    }

    public void setBrokenTime() {
        brokenTime = System.currentTimeMillis();
    }

    
    
    public void restartUseCounter() {
        useCounter = 0;
    }
    public void increaseUseCounter() {
        useCounter++;
    }

    public int getUseCounter() {
        return useCounter;
    }
    
    

    public Queue<Guest> getGuestsInGame() {
        return guestsInGame;
    }

    public void setGuestsInGame(Queue<Guest> guestsInGame) {
        this.guestsInGame = guestsInGame;
    }

    public Queue<Guest> getGuestsQueue() {
        return guestsQueue;
    }

    public void setGuestsQueue(Queue<Guest> guestsQueue) {
        this.guestsQueue = guestsQueue;
    }
    
    

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
    
    public long getBuiltTime () {
        return builtTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public LabelIcon getImgMenu() {
        return imgMenu;
    }

    public void setImgMenu(LabelIcon imgMenu) {
        this.imgMenu = imgMenu;
    }

    public JLabel getCapacityText() {
        return capacityText;
    }

    public JLabel getOperatePriceText() {
        return operatePriceText;
    }

    public JLabel getRepairPriceText() {
        return repairPriceText;
    }

    public JLabel getStateText() {
        return stateText;
    }

    public ImageButton getDeleteButton() {
        return deleteButton;
    }

    public int getOperateprice() {
        return operateprice;
    }

    public int getRepairPrice() {
        return repairPrice;
    }

    public State getGameState() {
        return gameState;
    }

    public void setGameState(State gameState) {
        this.gameState = gameState;
    }

    public JLabel getGuestsInGameText() {
        return guestsInGameText;
    }

    public void setGuestsInGameText(JLabel guestsInGameText) {
        this.guestsInGameText = guestsInGameText;
    }

    public JLabel getGuestsQueueText() {
        return guestsQueueText;
    }

    public void setGuestsQueueText(JLabel guestsQueueText) {
        this.guestsQueueText = guestsQueueText;
    }

    
    
    public void operate(Player p, JLabel balanceText) {
        
        int bal = p.getBalance() - getOperateprice();
        
        if (bal < 0) {
            //To be checked later
            
        } else {
            
            operateTime = System.currentTimeMillis();
            
            p.setBalance(bal);
            balanceText.setText("Current Balance: "+String.valueOf(p.getBalance()));
            
            setGameState(State.IN_USE);
            
            for (Guest gst : getGuestsInGame()) {
                gst.improveMood();
            }
            
        }
        
        //After the game operates, the queue is empty
        //emptyQueue(p, balanceText);
        
    }
    
    public void emptyQueue (Player p, JLabel balanceText) {
        
        
        guestsInGame.clear();
        
        
        //After that, the guests waiting, will go in the game
        if (guestsQueue.size() > capacity) {
            for (int i = 0; i < capacity; i++) {
                guestsInGame.add (guestsQueue.poll());
            }
            operate(p, stateText);
        } else {
            for (Guest g : guestsQueue) {
                guestsInGame.add(g);
            }
        }
        
        guestsQueue.clear();
        
        guestsInGameText.setText("Guests inside the Game: " + guestsInGame.size());
        guestsQueueText.setText("Guests waiting: " + guestsQueue.size());
    }
    
    public void clearQueues () {
        guestsInGame.clear();
        guestsQueue.clear();
        
        guestsInGameText.setText("Guests inside the Game: " + guestsInGame.size());
        guestsQueueText.setText("Guests waiting: " + guestsQueue.size());
    }

    public long getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(long operateTime) {
        this.operateTime = operateTime;
    }

    public int getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(int turnTime) {
        this.turnTime = turnTime;
    }

    
    
    
    public void breakGame() {

    }

    public void repairGame() {

    }

    public void showMenu() {
        imgMenu.setVisible(true);
        capacityText.setVisible(true);
        operatePriceText.setVisible(true);
        repairPriceText.setVisible(true);
        stateText.setVisible(true);
        guestsInGameText.setVisible(true);
        guestsQueueText.setVisible(true);
        deleteButton.setVisible(true);
    }

    public void hideMenu() {
        imgMenu.setVisible(false);
        capacityText.setVisible(false);
        operatePriceText.setVisible(false);
        repairPriceText.setVisible(false);
        stateText.setVisible(false);
        guestsInGameText.setVisible(false);
        guestsQueueText.setVisible(false);
        deleteButton.setVisible(false);
    }

    public void processMovement(Grid grid, Point clickedPoint, Point newLocation) {
        clickedPoint = grid.getPoint(clickedPoint);
        if (clickedPoint.x != -1) {
            grid.removeBuildingMatrix(clickedPoint.x, clickedPoint.y);
        }

        newLocation = grid.getPoint(newLocation);
        grid.addBuildingMatrix(newLocation.x, newLocation.y, 3);   // 3 for Games
    }

    
	
    public void switchGameToWaitingGuests() {
        
        
                switch(gameType) {
                    case "Game1" :
                        try {
                            setImg(new LabelIcon("data/game1waiting.png", getImg().getLocation(), getImg().getWidth(), getImg().getHeight()));
                        }catch (IOException exc) {

                        }
                        break;
                    case "Game2" :
                        try {
                            setImg(new LabelIcon("data/game2waiting.png", getImg().getLocation(), getImg().getWidth(), getImg().getHeight()));
                        }catch (IOException exc) {

                        }
                        break;
                    case "Game3" :
                        try {
                            setImg(new LabelIcon("data/game3waiting.png", getImg().getLocation(), getImg().getWidth(), getImg().getHeight()));
                        }catch (IOException exc) {

                        }
                        break;
                }
            }
    
    public void switchGameToInUse() {
       
        
        
                switch(gameType) {
                    case "Game1" :
                        try {
                            setImg(new LabelIcon("data/game1inuse.png", getImg().getLocation(), getImg().getWidth(), getImg().getHeight()));
                        }catch (IOException exc) {

                        }
                        break;
                    case "Game2" :
                        try {
                            setImg(new LabelIcon("data/game2inuse.png", getImg().getLocation(), getImg().getWidth(), getImg().getHeight()));
                        }catch (IOException exc) {

                        }
                        break;
                    case "Game3" :
                        try {
                            setImg(new LabelIcon("data/game3inuse.png", getImg().getLocation(), getImg().getWidth(), getImg().getHeight()));
                        }catch (IOException exc) {

                        }
                        break;
                }
            }
    
    public void switchGameToRepair() {
        
        
                switch(gameType) {
                    case "Game1" :
                        try {
                            setImg(new LabelIcon("data/game1repair.png", getImg().getLocation(), getImg().getWidth(), getImg().getHeight()));
                        }catch (IOException exc) {

                        }
                        break;
                    case "Game2" :
                        try {
                            setImg(new LabelIcon("data/game2repair.png", getImg().getLocation(), getImg().getWidth(), getImg().getHeight()));
                        }catch (IOException exc) {

                        }
                        break;
                    case "Game3" :
                        try {
                            setImg(new LabelIcon("data/game3repair.png", getImg().getLocation(), getImg().getWidth(), getImg().getHeight()));
                        }catch (IOException exc) {

                        }
                        break;
                }
            }
    

    
}
    
//}
