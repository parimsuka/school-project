package amusementpark;

import java.awt.Font;
import java.awt.Point;
import javax.swing.JLabel;

import amusementpark.Game.State;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class Restaurant extends Building{
	 private LabelIcon imgMenu;
     private int capacity;
     private int operateTime;
     private long operateStarted;
     
     private JLabel foodPriceText;
     private JLabel drinkPriceText;
     private ImageButton deleteButton;
	private JLabel capacityText;
	private JLabel guestsInResText;
	private JLabel guestsQueueText;
	private JLabel stateText;
	private int foodPrice;
	private int drinkPrice;
	private static final int timeIn=5;
	public static int getTimein() {
		return timeIn;
	}
        
        public enum State {
        FULL, NOTFULL
        }

    public long getOperateStarted() {
        return operateStarted;
    }
        
        public void restartOperateTime () {
            operateStarted = System.currentTimeMillis();
        }
        
	private State resState = State.NOTFULL;
        
	
	
	 public JLabel getGuestsInResText() {
		return guestsInResText;
	}

	public void setGuestsInResText(JLabel guestsInResText) {
		this.guestsInResText = guestsInResText;
	}

	public JLabel getGuestsQueueText() {
		return guestsQueueText;
	}

	public void setGuestsQueueText(JLabel guestsQueueText) {
		this.guestsQueueText = guestsQueueText;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public JLabel getCapacityText() {
		return capacityText;
	}

	public void setCapacityText(JLabel capacityText) {
		this.capacityText = capacityText;
	}

	public JLabel getStateText() {
		return stateText;
	}

	public void setStateText(JLabel stateText) {
		this.stateText = stateText;
	}

	public State getResState() {
	        return resState;
	    }

	    public void setResState(State resState) {
	        this.resState = resState;
	    }
	private Queue<Guest> guestsInRes;
    private Queue<Guest> guestsWaitingRes;
    
    public Queue<Guest> getGuestsInRes() {
		return guestsInRes;
	}


	public void setGuestsInRes(Queue<Guest> guestsInRes) {
		this.guestsInRes = guestsInRes;
	}


	public Queue<Guest> getGuestsWaitingRes() {
		return guestsWaitingRes;
	}


	public void setGuestsWaitingRes(Queue<Guest> guestsWaitingRes) {
		this.guestsWaitingRes = guestsWaitingRes;
	}

		//Showing in the menu
               
	public Restaurant(LabelIcon img, int foodPrice, int drinkPrice, int capacity, int operateTime, LabelIcon imgMenu) {
		super (img);
		 
        guestsInRes = new LinkedList<Guest>();
        guestsWaitingRes = new LinkedList<Guest>();
        
		this.foodPrice = foodPrice;
		this.drinkPrice = drinkPrice;
		this.capacity=capacity;
                this.imgMenu = imgMenu;
                this.operateTime = operateTime;
                
                (foodPriceText = new JLabel("Food price: " + foodPrice)).setBounds(500, 610, 150, 25);
                (drinkPriceText = new JLabel("Drink price: " + drinkPrice)).setBounds(500, 640, 150, 25);
                (capacityText = new JLabel("Maximum capacity: " + capacity)).setBounds(500, 670, 150, 25);
                (stateText = new JLabel("Current state: " + resState)).setBounds(500, 700, 150, 25);
                (guestsInResText = new JLabel("Guests inside the Restaurant: " + guestsInRes.size())).setBounds(500, 730, 300, 25);
                (guestsQueueText = new JLabel("Guests waiting: " + guestsWaitingRes.size())).setBounds(500, 770, 300, 25);
                //Fonts
//                capacityText.setFont(new Font("Serif", Font.BOLD, 20));
                foodPriceText.setFont(new Font("Serif", Font.BOLD, 20));
                drinkPriceText.setFont(new Font("Serif", Font.BOLD, 20));
//                stateText.setFont(new Font("Serif", Font.BOLD, 20));
                try {
                    deleteButton = new ImageButton("data/close6.png", new Point(900, 700), 50, 50);
                } catch (IOException e) {
                    
                }
                

                imgMenu.setVisible(false);
                foodPriceText.setVisible(false);
                drinkPriceText.setVisible(false);
                capacityText.setVisible(false);
                stateText.setVisible(false);
                guestsInResText.setVisible(false);
                guestsQueueText.setVisible(false);
                deleteButton.setVisible(false);
	}
        
	 public void operate(Player p, JLabel balanceText) {
	        
		 operateStarted = System.currentTimeMillis();

                 
                p.setBalance(p.getBalance() + foodPrice + drinkPrice);
                balanceText.setText("Current Balance: "+String.valueOf(p.getBalance()));
                guestLeaves();
	            

	        
	    }
	public int getOperateTime() {
		return operateTime;
	}

	public int getFoodPrice() {
		return foodPrice;
	}
        
	public int getDrinkPrice() {
		return drinkPrice;
	}
        
        public JLabel getFoodPriceText() {
		return foodPriceText;
	}
        
	public JLabel getDrinkPriceText() {
		return drinkPriceText;
	}
        
        public ImageButton getDeleteButton () {
            return deleteButton;
        }

        public LabelIcon getImgMenu () {
            return imgMenu;
        }
        
        public void showMenu () {
        	imgMenu.setVisible(true);
            foodPriceText.setVisible(true);
            drinkPriceText.setVisible(true);
            capacityText.setVisible(true);
            stateText.setVisible(true);
            guestsInResText.setVisible(true);
            guestsQueueText.setVisible(true);
            deleteButton.setVisible(true);
        }
        
        public void hideMenu () {
        		imgMenu.setVisible(false);
            foodPriceText.setVisible(false);
            drinkPriceText.setVisible(false);
            capacityText.setVisible(false);
            stateText.setVisible(false);
            guestsInResText.setVisible(false);
            guestsQueueText.setVisible(false);
            deleteButton.setVisible(false);

        }
        
        public void processMovement (Grid grid, Point clickedPoint, Point newLocation) {
            clickedPoint = grid.getPoint(clickedPoint);
            if (clickedPoint.x != -1) {
                 grid.removeBuildingMatrix(clickedPoint.x, clickedPoint.y);
            }

            newLocation = grid.getPoint(newLocation);
            grid.addBuildingMatrix(newLocation.x, newLocation.y, 2);   //2 for Restaurants
        }

        public void guestLeaves() {
            guestsInRes.poll();
            if (!guestsWaitingRes.isEmpty())
                guestsInRes.add(guestsWaitingRes.poll());
            guestsInResText.setText("Guests inside the Res: " + guestsInRes.size());
            guestsQueueText.setText("Guests waiting: " + guestsWaitingRes.size());
        }
}
