package amusementpark;

import java.awt.Font;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JLabel;

public class Garden extends Building{

	int moodImprove;
        
        //Showing in the menu
        private LabelIcon imgMenu;
        private JLabel moodImproveText;
        private ImageButton deleteButton;
        
	public Garden(LabelIcon img, int moodImprove, LabelIcon imgMenu) {
            super (img);
            this.moodImprove = moodImprove;
            this.imgMenu = imgMenu;
            
            (moodImproveText = new JLabel("Improves mood for: " + moodImproveText)).setBounds(500, 710, 150, 25);
            moodImproveText.setFont(new Font("Serif", Font.BOLD, 20));
            try {
                deleteButton = new ImageButton("data/close6.png", new Point(900, 700), 50, 50);
            } catch (IOException e) {

            }
            
            imgMenu.setVisible(false);
            moodImproveText.setVisible(false);
            deleteButton.setVisible(false);
	}
        
	public int getMoodImprove() {
		return moodImprove;
	}

        public LabelIcon getImgMenu() {
            return imgMenu;
        }

        public JLabel getMoodImproveText() {
            return moodImproveText;
        }

        public ImageButton getDeleteButton() {
            return deleteButton;
        }
        
        
	
        public void showMenu () {
            imgMenu.setVisible(true);
            moodImproveText.setVisible(true);
            deleteButton.setVisible(true);
        }

        public void hideMenu () {
            imgMenu.setVisible(false);
            moodImproveText.setVisible(false);
            deleteButton.setVisible(false);
        }
        
        public void processMovement (Grid grid, Point clickedPoint, Point newLocation) {
        clickedPoint = grid.getPoint(clickedPoint);
        if (clickedPoint.x != -1) {
             grid.remove1BoxMatrix(clickedPoint.x, clickedPoint.y);
        }
        
        newLocation = grid.getPoint(newLocation);
        grid.add1BoxMatrix(newLocation.x, newLocation.y, 4);  //4 for Gardens
        }
}
