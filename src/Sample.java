//import static org.junit.Assert.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.IOException;

import javax.swing.JFrame;

//import org.junit.Test;
import amusementpark.*;
import amusementpark.Game.State;

public class Sample {

	/*@Test
	public void Sample() {
		Menu m1 = new Menu();
		m1.setPlayerName("Shuaa");
		assertEquals("Shuaa",m1.getPlayerName().getText());

	}
	@Test
	public void testMenuConstructor() {
		Player p = new Player("Shuaa");
		assertEquals(1000,p.getBalance());	
	}
	@Test
	public void testGuestEnterConstructor() {
		ParkGUI park = new ParkGUI("Shuaa");
		park.getGameArea().addGuest();
		assertEquals(1,park.getGameArea().getGuests().size());
		assertEquals(1030,park.getGameArea().getPlayer().getBalance());
	}
	
		
		
//Cleaner is also an action listener
	@Test
	public void CleanerConstructor() throws IOException {
		ParkGUI park = new ParkGUI("Shuaa");
		park.getGameArea().getAddCleaner().doClick();
		assertEquals(950,park.getGameArea().getPlayer().getBalance());
		assertEquals(1,park.getGameArea().getCleaners().size());

	}	
	 Game is also an action listener
	@Test
	public void gameConstructor() throws IOException, InterruptedException {
		ParkGUI park = new ParkGUI("Shuaa");
		park.getGameArea().getGameMenu().get(3).doClick();
		Game g = (Game) park.getGameArea().getGamesAndRestaurants()
				.get(park.getGameArea().getGamesAndRestaurants().size() - 1);
		assertEquals(State.BEING_BUILT, g.getGameState());
		assertEquals(700, park.getGameArea().getPlayer().getBalance());
		park.getGameArea().changeStateGame(g, "waitingGuests");
		assertEquals(State.WAITING_GUEST, g.getGameState());

	}

	// Trash is also an action listener
	@Test
	public void trashConstructor() throws IOException, InterruptedException {
		ParkGUI park = new ParkGUI("Shuaa");
		park.getGameArea().getTrashMenu().get(1).doClick();
//					Checking if the number of trash cans have increased
		assertEquals(1, park.getGameArea().getTrashCans().size());
//					Checking if the players balance is decreased by 20 if he buys a trash
//					can
		assertEquals(1000 - 20, park.getGameArea().getPlayer().getBalance());

	}

	// Trash is also an action listener
	@Test
	public void gardenConstructor() throws IOException, InterruptedException {
		ParkGUI park = new ParkGUI("Shuaa");
	    		park.getGameArea().getGardenMenu().get(3).doClick();;
	    	
//					Checking if the number of garden cans have increased
		assertEquals(1, park.getGameArea().getGardens().size());
//					Checking if the players balance is decreased by 15 if he buys a garden1
//					can
		assertEquals(1000 - 15, park.getGameArea().getPlayer().getBalance());

	}
	// Path is also an action listener
	@Test
	public void pathConstructor() throws IOException, InterruptedException {
		ParkGUI park = new ParkGUI("Shuaa");
	    		park.getGameArea().getPathMenu().get(3).doClick();;
	    	
//					Checking if the number of paths have increased
		assertEquals(1, park.getGameArea().getPath().size());
//					Checking if the players balance is decreased by 10 if he buys a path1
//					can
		assertEquals(1000 - 10, park.getGameArea().getPlayer().getBalance());

	}
	// Restaurant is also an action listener
	@Test
	public void resConstructor() throws IOException, InterruptedException {
		ParkGUI park = new ParkGUI("Shuaa");
	    		park.getGameArea().getRestaurantMenu().get(3).doClick();;
	    	
//					Checking if the number of restaurants have increased
		assertEquals(1, park.getGameArea().getGamesAndRestaurants().size());
//					Checking if the players balance is decreased by 150 if he buys a restaurant1
//					can
		assertEquals(1000 - 150, park.getGameArea().getPlayer().getBalance());

	}

	// Grid Button is also an action listener
	@Test
	public void gridConstructor() throws IOException, InterruptedException {
		ParkGUI park = new ParkGUI("Shuaa");
	    		park.getGameArea().getGridButton().doClick();;
	    	
//					Checking if the number of restaurants have increased
		assertEquals(true, park.getGameArea().isShowGrid());
	}
	// BFS check
	@Test
	public void BFSConstructor() throws IOException, InterruptedException {
		  final int row = 3;
		  final int  col = 4;
		boolean[][] visited = new boolean[14][38];
		Grid g = new Grid();
		assertFalse(BFS.isValid(g.getMatrix(), visited, row, col, 17, 40));
	}*/

}
