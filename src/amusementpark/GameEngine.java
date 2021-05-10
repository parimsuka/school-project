/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusementpark;

import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Point;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import java.awt.Window;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import java.awt.Window;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Stack;
import static java.lang.String.valueOf;
import java.util.Iterator;
import java.util.Queue;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author sukap
 */
public class GameEngine extends JPanel{
    private JFrame mainFrame;
    private Color background;
    private Timer newFrameTimer;
    private JLabel balanceText;
    private JLabel guestLabel;
    private Player player;
    private Sprite inventory;
    private Sprite playernameSection;
    private Sprite balanceSection;
    private Sprite guestSection;
    private JLabel playerNameLabel;
    private ImageButton buildingButton;
    private ImageButton gridButton;
    private ImageButton startSimulation;
    private ImageButton addCleaner;
    private ImageButton addRepair;
    private ArrayList<ImageButton> buildingMenu;
    private ArrayList<ImageButton> restaurantMenu;
    private ArrayList<ImageButton> gameMenu;
    private ArrayList<ImageButton> gardenMenu;
    private ArrayList<ImageButton> pathMenu;
    private ArrayList<ImageButton> trashMenu;
    private Grid grid;
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) size.getWidth();
    int height = (int) size.getHeight();

    private ArrayList<ArrayList<Sprite>> gridBoxes;
    
    private ArrayList<LabelIcon> buildingsInPark;
    private ArrayList<Building> gamesAndRestaurants;
    private ArrayList<Garden> gardens;
    private ArrayList<Path> path;
    private ArrayList<TrashCan> trashCans;
    
    private ArrayList<Cleaner> cleaners;
    private ArrayList<Repairman> repairmen;
    
    
    
    private ArrayList<Guest> guests;
    
    private Queue<Game> brokenGames;
    
    private boolean showGrid = false;
    private LabelIcon enter;
    
    
    private long startTime = System.currentTimeMillis();
    
    private Timer addGuestTimer;
    
    private boolean pause;
    
    public GameEngine(final JFrame frame, String playername) {
        background = new Color(154, 207, 123);
        setLayout(null);
        player = new Player(playername);
        playernameSection = new Sprite(new Point(width-205, 5), 200, 30, 20, new Color(178, 221, 239));
        playerNameLabel = new JLabel(player.getName());
        playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerNameLabel.setBounds(width-205, 5, 200, 30);
        
        pause = false;
        this.grid = new Grid();
        this.gridBoxes = this.grid.getGrid();
        
        inventory = new Sprite(new Point(5, height-225), width-10, 220, 50, new Color(214, 232, 213));
        
        
        balanceSection = new Sprite(new Point(5, 5), 200, 30, 20, new Color (178, 221, 239));
        ImageIcon balanceIcon = new ImageIcon(new ImageIcon("data/coin.png").getImage().getScaledInstance(20, 20, 4));
        (this.balanceText = new JLabel("Current Balance: "+String.valueOf(player.getBalance()), balanceIcon, 2)).setBounds(20, 10, 175, 20);
        this.add(this.balanceText);

        try {
            
            buildingsInPark = new ArrayList<>();
            gamesAndRestaurants = new ArrayList<Building>();
            gardens = new ArrayList<Garden>();
            path = new ArrayList<Path>();
            trashCans = new ArrayList<TrashCan>();
            cleaners = new ArrayList<Cleaner>();
            repairmen = new ArrayList<Repairman>();
            
            guests = new ArrayList<Guest>();
//            Guest info
            guestSection = new Sprite(new Point(width/2-90, 5), 180, 30, 20, new Color (178, 221, 239));
            (this.guestLabel = new JLabel("Guests: "+String.valueOf(guests.size()), 2)).setBounds(width/2-90, 5, 180, 30);
            guestLabel.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(this.guestLabel);
            
//            Build Button 
            buildingButton = new ImageButton("data/buildingicon.png", new Point(40, height-210), 70, 70);
//            Grid Button
            gridButton = new ImageButton("data/grid.png", new Point(40, height-130), 70, 70);
            
//              Start Simulation Button
            startSimulation = new ImageButton("data/start.png", new Point(width-100, height-200), 80, 80);
//              Buy cleaner button
            addCleaner = new ImageButton("data/buycleaner.png" , new Point(width-100, height-100) , 80, 80);
            addRepair = new ImageButton("data/buyrepairman.png" , new Point(width-200, height-100) , 80, 80);
            
            brokenGames = new LinkedList<Game>();
            
            buildingMenu = new ArrayList<>(Arrays.asList(new ImageButton("data/restaurantmenu.png", new Point(150, height-210), 100, 100), new ImageButton("data/gamemenu.png", new Point(300, height-210), 100, 100), new ImageButton("data/gardenmenu.png", new Point(450, height-210), 100, 100), new ImageButton("data/pathmenu.png", new Point(600, height-210), 100, 100), new ImageButton("data/trashmenu.png", new Point(750, height-210), 100, 100)));
            
            restaurantMenu =  new ArrayList<>(Arrays.asList(new ImageButton("data/restaurant1.png", new Point(320, 650), 120, 120), new ImageButton("data/restaurant2.png", new Point(570, 650), 120, 120), new ImageButton("data/restaurant3.png", new Point(820, 650), 120, 120), new ImageButton("data/c150.jpg", new Point(300, 785), 160, 45), new ImageButton("data/c200.jpg", new Point(550, 785), 160, 45), new ImageButton("data/c250.jpg", new Point(800, 785), 160, 45)));
            gameMenu = new ArrayList<>(Arrays.asList(new ImageButton("data/game1.png", new Point(320, 650), 120, 120), new ImageButton("data/game2.png", new Point(570, 650), 120, 120), new ImageButton("data/game3.png", new Point(820, 650), 120, 120), new ImageButton("data/c300.jpg", new Point(300, 785), 160, 45), new ImageButton("data/c400.jpg", new Point(550, 785), 160, 45), new ImageButton("data/c500.jpg", new Point(800, 785), 160, 45)));
            gardenMenu = new ArrayList<>(Arrays.asList(new ImageButton("data/garden1.png", new Point(320, 650), 120, 120), new ImageButton("data/garden2.png", new Point(570, 650), 120, 120), new ImageButton("data/garden3.png", new Point(820, 650), 120, 120), new ImageButton("data/c15.jpg", new Point(300, 785), 160, 45), new ImageButton("data/c20.jpg", new Point(550, 785), 160, 45), new ImageButton("data/c40.jpg", new Point(800, 785), 160, 45)));
            pathMenu = new ArrayList<>(Arrays.asList(new ImageButton("data/path1.png", new Point(320, 650), 120, 120), new ImageButton("data/path2.png", new Point(570, 650), 120, 120), new ImageButton("data/path3.png", new Point(820, 650), 120, 120), new ImageButton("data/c10.jpg", new Point(300, 785), 160, 45), new ImageButton("data/c15.jpg", new Point(550, 785), 160, 45), new ImageButton("data/c20.jpg", new Point(800, 785), 160, 45)));
            trashMenu = new ArrayList<>(Arrays.asList(new ImageButton("data/trash1.png", new Point(320, 650), 120, 120), new ImageButton("data/c20.jpg", new Point(300, 785), 160, 45)));
            
            setButtonsBackground();
            clearMenu();
        } 
        catch (IOException ex) {}
        buildingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMenu();
                show("buildingMenu");
                repaint();
                requestFocus();
            }
        });
        
        gridButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGrid = !showGrid;
                repaint();
                requestFocus();
            }
        });
        
        startSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGuestTimer = new Timer(6000, new addGuestListener());
                addGuestTimer.start();
            }
        });
        
        addCleaner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Hire cleaner
                
                
                try {
                    LabelIcon l = new LabelIcon("data/cleaner.png", gridBoxes.get(6).get(0).p.getLocation(), 40, 40);
                    Cleaner c = new Cleaner(l);
                    cleaners.add(c);
                    add (c.getImg());
                    setComponentZOrder(c.getImg(), 0);
                    
                    
                    player.setBalance(player.getBalance() - 50);
                    balanceText.setText("Current Balance: "+String.valueOf(player.getBalance()));
                    
                    c.clean (grid, path);
                } catch (IOException exc) {
                    
                }
                
                
            }
        });
        
        addRepair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Hire repairman
                
                
                try {
                    LabelIcon l = new LabelIcon("data/repairman.png", gridBoxes.get(6).get(0).p.getLocation(), 40, 40);
                    Repairman r = new Repairman(l);
                    repairmen.add(r);
                    add (r.getImg());
                    setComponentZOrder(r.getImg(), 0);
                    
                    
                    player.setBalance(player.getBalance() - 50);
                    balanceText.setText("Current Balance: "+String.valueOf(player.getBalance()));
                    
                } catch (IOException exc) {
                    
                }
            }
        });
        
        
        //Adding actions for the building menu
        // ----1  Restaurant Menu
        buildingMenu.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMenu();
                show ("restaurantMenu");
                repaint();
                requestFocus();
            }
        });
        
        // ----2 Game Menu
        buildingMenu.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMenu();
                show ("gameMenu");
                repaint();
                requestFocus();
            }
        });
        
        // ----3 Garden Menu
        buildingMenu.get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMenu();
                show ("gardenMenu");
                repaint();
                requestFocus();
            }
        });
        
        // ----4 Path Menu
        buildingMenu.get(3).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMenu();
                show ("pathMenu");
                repaint();
                requestFocus();
            }
        });
        
        // ----5 Trash Menu
        buildingMenu.get(4).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMenu();
                show ("trashMenu");
                repaint();
                requestFocus();
            }
        });
        
        
        

        //Adding action listeners to the buying buttons
        //Buying button for the types of restaurant
        for (int i = 3; i < 6; i++) {
            int priceRestaurant = i*50;
            String s = "data/restaurant" + Integer.toString(i-2)  + ".png";
            restaurantMenu.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (player.getBalance() >= priceRestaurant) {
                        try {
                            player.setBalance(player.getBalance()-priceRestaurant);
                            balanceText.setText("Current Balance: "+String.valueOf(player.getBalance()));
                            LabelIcon l = new LabelIcon((s), new Point (0, 40), 80, 80);
                            LabelIcon lMenu = new LabelIcon((s), new Point (400, 700), 80, 80);
                            Restaurant r = new Restaurant(l, 0, 0,5, 5,lMenu);
                            gamesAndRestaurants.add(r);
                            dragAndDrop dr = new dragAndDrop(r);
                            add (gamesAndRestaurants.get (gamesAndRestaurants.size()-1).getImg());
                            add (lMenu);
                            add (r.getFoodPriceText());
                            add (r.getDrinkPriceText());
                            add (r.getCapacityText());
                            add (r.getStateText());
                            add (r.getGuestsInResText());
                            add (r.getGuestsQueueText());
                            add (r.getDeleteButton());
                            
                            r.getDeleteButton().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    r.hideMenu();
                                    gamesAndRestaurants.remove(r);
                                    remove(r.getImg());
                                }
                            });

                            gamesAndRestaurants.get (gamesAndRestaurants.size()-1).getImg().addMouseListener(dr);
                            gamesAndRestaurants.get (gamesAndRestaurants.size()-1).getImg().addMouseMotionListener(dr);
                        } catch (IOException exc) {}
                    } else {
                        // Exception: NO MONEY, POOR AF
                    }

                }
            });
        }
        
        //Buying button for the types of games
        
        
        
        //Game 1
        final int priceGame1 = 300;
        gameMenu.get(3).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getBalance() >= priceGame1) {
                    try {
                        player.setBalance(player.getBalance()-priceGame1);
                        balanceText.setText("Current Balance: "+String.valueOf(player.getBalance()));
                        LabelIcon l = new LabelIcon(("data/game1built.png"), new Point (0, 40), 80, 80);//here is the default positiion
                        LabelIcon lMenu = new LabelIcon(("data/game1.png"), new Point (400, 700), 80, 80);
                        Game g = new Game(l, 3, 10, 0, 5,lMenu, 1, "Game1");
                        gamesAndRestaurants.add(g);
                        dragAndDrop dr = new dragAndDrop(g);
                        add(gamesAndRestaurants.get (gamesAndRestaurants.size()-1).getImg());
                        add (g.getCapacityText());
                        add (g.getOperatePriceText());
                        add (g.getRepairPriceText());
                        add (g.getStateText());
                        add (g.getImgMenu());
                        add (g.getGuestsInGameText());
                        add (g.getGuestsQueueText());
                        add (g.getDeleteButton());
                        
                        g.getDeleteButton().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    /*if(games) {
                                        remove(games.get(0).getImg());
                                        games.get(0).switchGameToWaitingGuests(String GameType);
                                        add(games.get(0).getImg());
                                    }*/
                                    g.hideMenu();
                                    gamesAndRestaurants.remove(g);
                                    remove(g.getImg());
                                }
                        });

                        gamesAndRestaurants.get (gamesAndRestaurants.size()-1).getImg().addMouseListener(dr);
                        gamesAndRestaurants.get (gamesAndRestaurants.size()-1).getImg().addMouseMotionListener(dr);
                    } catch (IOException exc) {}
                } else {
                    // Exception: NO MONEY, POOR AF
                }

            }
        });
        
        //Game 2
        final int priceGame2 = 400;
        gameMenu.get(4).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getBalance() >= priceGame2) {
                    try {
                        player.setBalance(player.getBalance()-priceGame2);
                        balanceText.setText("Current Balance: "+String.valueOf(player.getBalance()));
                        LabelIcon l = new LabelIcon(("data/game2built.png"), new Point (0, 40), 80, 80);//here is the default positiion
                        LabelIcon lMenu = new LabelIcon(("data/game2.png"), new Point (400, 700), 80, 80);
                        Game g = new Game(l, 10, 15, 0, 10,lMenu, 5, "Game2");
                        gamesAndRestaurants.add(g);
                        dragAndDrop dr = new dragAndDrop(g);
                        add(gamesAndRestaurants.get (gamesAndRestaurants.size()-1).getImg());
                        add (g.getCapacityText());
                        add (g.getOperatePriceText());
                        add (g.getRepairPriceText());
                        add (g.getStateText());
                        add (g.getImgMenu());
                        add (g.getGuestsInGameText());
                        add (g.getGuestsQueueText());
                        add (g.getDeleteButton());
                        
                        g.getDeleteButton().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    g.hideMenu();
                                    gamesAndRestaurants.remove(g);
                                    remove(g.getImg());
                                }
                        });

                        gamesAndRestaurants.get (gamesAndRestaurants.size()-1).getImg().addMouseListener(dr);
                        gamesAndRestaurants.get (gamesAndRestaurants.size()-1).getImg().addMouseMotionListener(dr);
                    } catch (IOException exc) {}
                } else {
                    // Exception: NO MONEY, POOR AF
                }

            }
        });
        
        //Game 3
        final int priceGame3 = 500;
        gameMenu.get(5).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getBalance() >= priceGame3) {
                    try {
                        player.setBalance(player.getBalance()-priceGame3);
                        balanceText.setText("Current Balance: "+String.valueOf(player.getBalance()));
                        LabelIcon l = new LabelIcon(("data/game3built.png"), new Point (0, 40), 80, 80);//here is the default positiion
                        LabelIcon lMenu = new LabelIcon(("data/game3.png"), new Point (400, 700), 80, 80);
                        Game g = new Game(l, 20, 25, 0, 15,lMenu, 7, "Game3");
                        gamesAndRestaurants.add(g);
                        dragAndDrop dr = new dragAndDrop(g);
                        add(gamesAndRestaurants.get (gamesAndRestaurants.size()-1).getImg());
                        add (g.getCapacityText());
                        add (g.getOperatePriceText());
                        add (g.getRepairPriceText());
                        add (g.getStateText());
                        add (g.getImgMenu());
                        add (g.getGuestsInGameText());
                        add (g.getGuestsQueueText());
                        add (g.getDeleteButton());
                        
                        g.getDeleteButton().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    g.hideMenu();
                                    gamesAndRestaurants.remove(g);
                                    remove(g.getImg());
                                }
                        });

                        gamesAndRestaurants.get (gamesAndRestaurants.size()-1).getImg().addMouseListener(dr);
                        gamesAndRestaurants.get (gamesAndRestaurants.size()-1).getImg().addMouseMotionListener(dr);
                    } catch (IOException exc) {}
                } else {
                    // Exception: NO MONEY, POOR AF
                }

            }
        });
        
        //Buying button for the types of gardens
        
        //Price 15 - 20 - 40
        int priceGarden = 15;
        for (int i = 3; i < 6; i++) {
            int tempPrice = priceGarden;
            String s = "data/garden" + Integer.toString(i-2)  + ".png";
            gardenMenu.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (player.getBalance() >= tempPrice) {
                        try {
                            player.setBalance(player.getBalance()-tempPrice);
                            balanceText.setText("Current Balance: "+String.valueOf(player.getBalance()));
                            LabelIcon l = new LabelIcon((s), new Point (0, 40), 40, 40);
                            LabelIcon lMenu = new LabelIcon((s), new Point (400, 700), 80, 80);
                            Garden g = new Garden(l, 0, lMenu);
                            gardens.add(g);
                            dragAndDrop dr = new dragAndDrop(g);
                            add (gardens.get (gardens.size()-1).getImg());
                            add (g.getImgMenu());
                            add (g.getMoodImproveText());
                            add (g.getDeleteButton());
                            
                            g.getDeleteButton().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    g.hideMenu();
                                    gardens.remove(g);
                                    remove(g.getImg());
                                }
                            });

                            gardens.get (gardens.size()-1).getImg().addMouseListener(dr);
                            gardens.get (gardens.size()-1).getImg().addMouseMotionListener(dr);
                        } catch (IOException exc) {}
                    } else {
                        // Exception: NO MONEY, POOR AF
                    }

                }
            });
            
            if (i != 4) {
                priceGarden += 5;
            }else
                priceGarden += 20;
        }
        
        //Buying button for the types of paths
        
        int pricePath = 10;
        for (int i = 3; i < 6; i++) {
            int tempPrice = pricePath;
            String s = "data/path" + Integer.toString(i-2)  + ".png";
            pathMenu.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (player.getBalance() >= tempPrice) {
                        try {
                            player.setBalance(player.getBalance()-tempPrice);
                            balanceText.setText("Current Balance: "+String.valueOf(player.getBalance()));
                            LabelIcon l = new LabelIcon((s), new Point (0, 40), 40, 40);
                            LabelIcon lMenu = new LabelIcon((s), new Point (400, 700), 80, 80);
                            Path p = new Path(l, lMenu);
                            path.add(p);
                            dragAndDrop dr = new dragAndDrop(p);
                            add (path.get (path.size()-1).getImg());
                            add (p.getImgMenu ());
                            add (p.getDeleteButton());                 
                            
                            p.getDeleteButton().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    p.hideMenu();
                                    path.remove(p);
                                    remove(p.getImg());
                                }
                            });

                            path.get (path.size()-1).getImg().addMouseListener(dr);
                            path.get (path.size()-1).getImg().addMouseMotionListener(dr);
                        } catch (IOException exc) {}
                    } else {
                        // Exception: NO MONEY, POOR AF
                    }

                }
            });
            
            pricePath += 5;
        }
        
        //Buying button for the types of trash
        //for (int i = 3; i < 6; i++) {
            final int priceTrashCan = 20;
            String s = "data/trash" + Integer.toString(1)  + ".png";
            trashMenu.get(1).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (player.getBalance() >= priceTrashCan) {
                        try {
                            player.setBalance(player.getBalance()-priceTrashCan);
                            balanceText.setText("Current Balance: "+String.valueOf(player.getBalance()));
                            LabelIcon l = new LabelIcon((s), new Point (0, 40), 40, 40);
                            LabelIcon lMenu = new LabelIcon((s), new Point (400, 700), 80, 80);
                            TrashCan t = new TrashCan(l, lMenu);
                            trashCans.add(t);
                            dragAndDrop dr = new dragAndDrop(t);
                            add (trashCans.get (trashCans.size()-1).getImg());
                            add (t.getImgMenu ());
                            add (t.getDeleteButton());
                            
                            t.getDeleteButton().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    t.hideMenu();
                                    trashCans.remove(t);
                                    remove(t.getImg());
                                }
                            });

                            trashCans.get (trashCans.size()-1).getImg().addMouseListener(dr);
                            trashCans.get (trashCans.size()-1).getImg().addMouseMotionListener(dr);
                        } catch (IOException exc) {}
                    } else {
                        // Exception: NO MONEY, POOR AF
                    }

                }
            });
        //}
        
        
        //Adding buttons in the menu
        add(buildingButton);
        add(gridButton);
        add (startSimulation);
        add (addCleaner);
        add (addRepair);
        add (playerNameLabel);
        for (ImageButton i : buildingMenu)
            add (i);
        for (ImageButton i : restaurantMenu)
            add (i);
        for (ImageButton i : gameMenu)
            add (i);
        for (ImageButton i : gardenMenu)
            add (i);
        for (ImageButton i : pathMenu)
            add (i);
        for (ImageButton i : trashMenu)
            add (i);
        
        
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        newFrameTimer = new Timer(500, new NewFrameListener());
        newFrameTimer.start();

        
    }

    public boolean isPause() {
        return pause;
    }
    
    
    
    @Override
    protected void paintComponent(final Graphics grphcs) {
        super.paintComponent(grphcs);
        setBackground(this.background);
        final Graphics2D gr = (Graphics2D)grphcs;
        inventory.draw(gr);
        balanceSection.draw(gr);
        guestSection.draw(gr);
        playernameSection.draw(gr);
        if (showGrid) {
            grid.draw(gr);
            
        }
    }
    
    public void show (String target) {
        if (target.equals("buildingMenu"))
            for (ImageButton i : buildingMenu)
                i.setVisible(true);
            
        if (target.equals("restaurantMenu"))
            for (ImageButton i : restaurantMenu)
                i.setVisible(true);
        
        if (target.equals("gameMenu"))
            for (ImageButton i : gameMenu)
                i.setVisible(true);
        
        if (target.equals("gardenMenu"))
            for (ImageButton i : gardenMenu)
                i.setVisible(true);
        
        if (target.equals("pathMenu"))
            for (ImageButton i : pathMenu)
                i.setVisible(true);
        
        if (target.equals("trashMenu"))
            for (ImageButton i : trashMenu)
                i.setVisible(true);
    }
    
    public void clearMenu () {
        for (ImageButton i : buildingMenu)
            i.setVisible(false);
        
        for (ImageButton i : restaurantMenu)
            i.setVisible(false);
        
        for (ImageButton i : gameMenu)
            i.setVisible(false);
        
        for (ImageButton i : gardenMenu)
            i.setVisible(false);
        
        for (ImageButton i : pathMenu)
            i.setVisible(false);
        
        for (ImageButton i : trashMenu)
            i.setVisible(false);
        
        for (Building b : gamesAndRestaurants)
            b.hideMenu();
        
        for (Garden g : gardens)
            g.hideMenu();
        
        for (Path p : path)
            p.hideMenu();
        
        for (TrashCan tC : trashCans)
            tC.hideMenu();
    }
    
    public void setButtonsBackground () {
        
        buildingButton.setOpaque(false);
        buildingButton.setContentAreaFilled(false);
        gridButton.setOpaque(false);
        gridButton.setContentAreaFilled(false);
 
        
        for (ImageButton i : buildingMenu) {
            i.setOpaque(false);
            i.setContentAreaFilled(false);
            i.setBorderPainted(false);
        }
        
        for (ImageButton i : restaurantMenu) {
            i.setOpaque(false);
            i.setContentAreaFilled(false);
            i.setBorderPainted(false);
        }
        
        for (ImageButton i : gameMenu) {
            i.setOpaque(false);
            i.setContentAreaFilled(false);
            i.setBorderPainted(false);
        }
        
        for (ImageButton i : gardenMenu) {
            i.setOpaque(false);
            i.setContentAreaFilled(false);
            i.setBorderPainted(false);
        }
        
        for (ImageButton i : pathMenu) {
            i.setOpaque(false);
            i.setContentAreaFilled(false);
            i.setBorderPainted(false);
        }
        
        for (ImageButton i : trashMenu) {
            i.setOpaque(false);
            i.setContentAreaFilled(false);
            i.setBorderPainted(false);
        } 
    }
    
    //Method to check if that box in the grid is empty, if true, built it in those coordinates, if not return it to the previous point.
   
    public boolean collisionGrid(LabelIcon target){
        //boolean flag= false;
         for (int i = 0; i < gridBoxes.size(); i++)
            for (Sprite s: gridBoxes.get (i)) {
                if(s.collides(target.getBounds())){

                    return true;
                }
        }
        return false;
    }
    
    public boolean checkCollision (LabelIcon target, Point p) {
        
        //Checking if the mouse is released inside the grid
        p = getGridBoxLocation(p);
        if (p.x == 0)
            return true;
        
        //1 box buildings
        if (target.getWidth() == 40) {
            
            for (int i = 0; i < gamesAndRestaurants.size(); i++) {
                LabelIcon l = gamesAndRestaurants.get(i).getImg();
                
                if (target != l && l.getBounds().contains(p)) {
                    return true;
                }
            }

            
            for (int i = 0; i < gardens.size(); i++) {
                LabelIcon l = gardens.get(i).getImg();
                
                if (target != l && l.getBounds().contains(p)) {
                    return true;
                }
            }
            
            for (int i = 0; i < path.size(); i++) {
                LabelIcon l = path.get(i).getImg();
                
                if (target != l && l.getBounds().contains(p)) {
                    return true;
                }
            }
            
            for (int i = 0; i < trashCans.size(); i++) {
                LabelIcon l = trashCans.get(i).getImg();
                
                if (target != l && l.getBounds().contains(p)) {
                    return true;
                }
            }
            
        } else {
            //4 box buildings
            
            //We check if all 4 boxes are inside the grid
            Point p2 = new Point(p.x + Grid.UNIT_SIZE, p.y);
            Point p3 = new Point(p.x, p.y + Grid.UNIT_SIZE);
            Point p4 = new Point(p.x + Grid.UNIT_SIZE, p.y + Grid.UNIT_SIZE);
            
            if (getGridBoxLocation(p4).x ==0 )
                return true;
            
            //We check if all 4 boxes are free
            for (int i = 0; i < gamesAndRestaurants.size(); i++) {
                LabelIcon l = gamesAndRestaurants.get(i).getImg();
                
                if (target != l && (l.getBounds().contains(p) || l.getBounds().contains(p2) || l.getBounds().contains(p3) || l.getBounds().contains(p4))) {
                    return true;
                }
            }

            for (int i = 0; i < gardens.size(); i++) {
                LabelIcon l = gardens.get(i).getImg();
                
                if (target != l && (l.getBounds().contains(p) || l.getBounds().contains(p2) || l.getBounds().contains(p3) || l.getBounds().contains(p4))) {
                    return true;
                }
            }
            
            for (int i = 0; i < path.size(); i++) {
                LabelIcon l = path.get(i).getImg();
                
                if (target != l && (l.getBounds().contains(p) || l.getBounds().contains(p2) || l.getBounds().contains(p3) || l.getBounds().contains(p4))) {
                    return true;
                }
            }
            
            for (int i = 0; i < trashCans.size(); i++) {
                LabelIcon l = trashCans.get(i).getImg();
                
                if (target != l && (l.getBounds().contains(p) || l.getBounds().contains(p2) || l.getBounds().contains(p3) || l.getBounds().contains(p4))) {
                    return true;
                }
            }
            
        }
        
        return false;
    }
    
    public void changeStateGame (Game g, String type) {
        
        remove(g.getImg());
        
        switch (type) {
            case "waitingGuests":
                g.switchGameToWaitingGuests();
                g.setGameState(Game.State.WAITING_GUEST);
                for (Guest gst : g.getGuestsInGame()) {
                    gst.setIsWaiting(false);
                }
                for (Guest gst : g.getGuestsQueue()) {
                    gst.setIsWaiting(false);
                }
            break;
            case "inUse":
                g.switchGameToInUse();
                g.setGameState(Game.State.IN_USE);
                for (Guest gst : g.getGuestsInGame()) {
                    gst.setIsWaiting(false);
                }
                for (Guest gst : g.getGuestsQueue()) {
                    gst.setIsWaiting(false);
                }
            break;
            case "repair":
                g.switchGameToRepair();
                g.setGameState(Game.State.UNDER_REPAIR);
                for (Guest gst : g.getGuestsInGame()) {
                    gst.setIsWaiting(false);
                }
                for (Guest gst : g.getGuestsQueue()) {
                    gst.setIsWaiting(false);
                }
            break;
        }
        
        g.getStateText().setText("Current state: " + g.getGameState());
        add (g.getImg());
        dragAndDrop dr = new dragAndDrop(g);
        g.getImg().addMouseListener(dr);
        g.getImg().addMouseMotionListener(dr);
    }
    
    public void addGuest () {
        try {
            LabelIcon g = new LabelIcon("data/default.png", gridBoxes.get(6).get(0).p.getLocation(), 40, 40);
            guests.add (new Guest(g));
            add (guests.get (guests.size()-1).getImg());
            
            setComponentZOrder(guests.get (guests.size()-1).getImg(), 0);
            
            guests.get (guests.size()-1).eatOrPlay(gamesAndRestaurants, grid);
            
            
            player.setBalance(player.getBalance() + 30);
            balanceText.setText("Current Balance: "+String.valueOf(player.getBalance()));
            guestLabel.setText("Guests: "+String.valueOf(guests.size()));
        } catch (IOException exc) {

        }
    }
//    public void remGuest (Guest g) {
//        try {
////            LabelIcon g = new LabelIcon("data/default.png", gridBoxes.get(6).get(0).p.getLocation(), 40, 40);
//            guests.add (new Guest(g));
//            add (guests.get (guests.size()-1).getImg());
//            
//            setComponentZOrder(guests.get (guests.size()-1).getImg(), 0);
//            
//            guests.get (guests.size()-1).eatOrPlay(games, restaurants, grid);
//
//            guestLabel.setText("Guests: "+String.valueOf(guests.size()));
//        } catch (IOException exc) {
//
//        }
//    }
//    
    class NewFrameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!pause) {
                for (Building b : gamesAndRestaurants) {
                    if (b instanceof Game) {
                        Game g = (Game)b;
                        int n = (int)((System.currentTimeMillis() - g.getBuiltTime())/1000);

                        if (g.getGameState() == Game.State.BEING_BUILT) {
                            if (n == g.getBuildingTime()) {
                                //Change picture and state from being built to waiting for guests             
                                changeStateGame (g, "waitingGuests");
                            }
                        } else if (g.getGameState() == Game.State.IN_USE) {
                            n = (int)((System.currentTimeMillis() - g.getOperateTime())/1000);

                            //If the operate time has passed, we reset everything

                            if (n == g.getTurnTime()) {
                                //Empty queues, change the state to waiting for guests, change picture


                                if (g.getUseCounter() == g.getUseLimit()) {
                                    changeStateGame (g, "repair");
                                    g.restartUseCounter();
                                    if (!brokenGames.contains(g))
                                        brokenGames.add(g);
                                    
                                    
                                    //empty all queues
                                    g.clearQueues();
                                } else {
                                    changeStateGame (g, "waitingGuests");
                                     //Empty queues
                                    g.emptyQueue(player, balanceText);
                                }
                               
                            }
                        } else if (g.getGameState() == Game.State.WAITING_GUEST) {

                        } else if (g.getGameState() == Game.State.UNDER_REPAIR) {
                            
                        }
                    } else if (b instanceof Restaurant) {

                        Restaurant r = (Restaurant)b;

                        int n = (int)((System.currentTimeMillis() - r.getOperateStarted())/1000);


                        r.getGuestsInResText().setText("Guests inside the Res: " + r.getGuestsInRes().size());
                        r.getGuestsQueueText().setText("Guests waiting: " + r.getGuestsWaitingRes().size());


                        if (n == r.getOperateTime()) {
                            r.operate(player, balanceText);

                        }


                        if (r.getResState() == Restaurant.State.FULL) {
                                 if (n == Restaurant.getTimein()) {
                                 //Empty queues, change the state to waiting for guests, change picture

                                 r.setResState(Restaurant.State.NOTFULL);

                                 //Empty queues
                                 r.guestLeaves();
                             }
                        }
                    }
                }




                //System.out.println((System.currentTimeMillis() - startTime)/1000 + " seconds passed");
                Iterator<Guest> itr = guests.iterator();
                while (itr.hasNext()) {
                    Guest g = itr.next();

                    if (g.isHasEaten()) {
                        if ((System.currentTimeMillis() - g.getHasEatenTime())/1000 == g.getRandThrowTime()) {
                            //Throw trash
                            g.setHasEaten(false);

                            Point p = grid.getPoint(g.getImg().getLocation());
                            //If he is inside the path
                            if (grid.getMatrix() [p.x] [p.y] == 1) {

                                //Also if there is no trashCan in 1 block radius
                                boolean hasTrashCanNearby = false;
                               if (p.x - 1 >= 0 && p.y - 1 >= 0 && grid.getMatrix() [p.x - 1] [p.y - 1] == 5) {
                                   hasTrashCanNearby = true;
                               }

                               if (p.x - 1 >= 0 && grid.getMatrix() [p.x - 1] [p.y] == 5) {
                                   hasTrashCanNearby = true;
                               }

                               if (p.x - 1 >= 0 && p.y <= 38 && grid.getMatrix() [p.x - 1] [p.y + 1] == 5) {
                                   hasTrashCanNearby = true;
                               }

                               if (p.y <= 38 && grid.getMatrix() [p.x] [p.y + 1] == 5) {
                                   hasTrashCanNearby = true;
                               }

                               if (p.x + 1 <= 14 && p.y + 1 <= 38 && grid.getMatrix() [p.x + 1] [p.y + 1] == 5) {
                                   hasTrashCanNearby = true;
                               }

                               if (p.x + 1 <= 14 && grid.getMatrix() [p.x + 1] [p.y] == 5) {
                                   hasTrashCanNearby = true;
                               }

                               if (p.x + 1 <= 14 && p.y - 1 >= 0 && grid.getMatrix() [p.x + 1] [p.y - 1] == 5) {
                                   hasTrashCanNearby = true;
                               }

                               if (p.y - 1 >= 0 && grid.getMatrix() [p.x] [p.y - 1] == 5) {
                                   hasTrashCanNearby = true;
                               }


                               if (!hasTrashCanNearby) {
                                  try {
                                    g.getTrash().add(new Trash(new LabelIcon("data/trash.png", g.getImg().getLocation(), 40, 40)));


                                    add (g.getTrash().get(g.getTrash().size() - 1).getImg());
                                    setComponentZOrder(g.getTrash().get(g.getTrash().size() - 1).getImg(), 0);
                                    } catch (IOException exc) {

                                    }
                                }


                            } 


                        }
                    }


                    //Checking if there is a garden nearby to increase mood
                            Point p = grid.getPoint(g.getImg().getLocation());
                                boolean hasGardenNearby = false;

                               if (p.x - 1 >= 0 && grid.getMatrix() [p.x - 1] [p.y] == 5) {
                                   hasGardenNearby = true;
                               }


                               if (p.y <= 38 && grid.getMatrix() [p.x] [p.y + 1] == 5) {
                                   hasGardenNearby = true;
                               }                   

                               if (p.x + 1 <= 14 && grid.getMatrix() [p.x + 1] [p.y] == 5) {
                                   hasGardenNearby = true;
                               }

                               if (p.y - 1 >= 0 && grid.getMatrix() [p.x] [p.y - 1] == 5) {
                                   hasGardenNearby = true;
                               }

                               if (hasGardenNearby) {
                                   g.improveMood();
                               }

                    //Checking if the mood changed and if the picture needs to change as well
                    if (g.getMood() >= 70) {
                        remove (g.getImg());
                        g.switchImgHappy();
                        add (g.getImg());
                        setComponentZOrder(g.getImg(), 0);
                    }

                    if (g.getMood() < 70 && g.getMood() >= 30) {
                        remove (g.getImg());
                        g.switchImgDefault();
                        add (g.getImg());
                        setComponentZOrder(g.getImg(), 0);
                    }

                    if (g.getMood() < 30) {
                        remove (g.getImg());
                        g.switchImgAngry();
                        add (g.getImg());
                        setComponentZOrder(g.getImg(), 0);
                    }


                    if (g.isIsWaiting()) {
                        if ((System.currentTimeMillis() - g.getWaitingTime())/1000 == 5) {
                            g.restartWaiting();
                            g.decreaseMood();                     


                            if (g.getDestination() instanceof Restaurant) {
                                g.eatOrPlay(gamesAndRestaurants, grid);
                            }

                            if (g.getMood() <= 0) {
                                //Leave the park
                                Point p1 = grid.getPoint(g.getImg().getLocation());
                                Point p2 = new Point(6, 0);
                                ArrayList<Point> coordinates = BFS.useBFS(grid.getMatrix(), p1.x, p1.y, p2.x, p2.y);
                                if (coordinates != null) {
                                    g.setIsWaiting(false);
                                    g.setDestination(null);
                                    g.setIsMoving(true);
                                    g.setCoords(coordinates, grid);
                                }                        
                            }
                        }
                    }



                    if (g.getIsMoving()) {
                        if (!g.getCoords().isEmpty()) {
                            g.getImg().setLocation(g.getCoords().pop());

                            //If he sees trash on the path, the mood decreases
                            p = grid.getPoint(g.getImg().getLocation());

                            for (Guest gstss : guests) {
                                for (Trash t : gstss.getTrash()) {
                                    if (p.equals(grid.getPoint(t.getImg().getLocation()))) {
                                        g.decreaseMood();
                                    }
                                }
                            }

                        } else {
                            g.setIsMoving(false);
                            if (g.getDestination() instanceof Game) {
                                Game game = (Game)g.getDestination();

                                //Game state = waiting, game still not full
                                if (game.getGameState() == Game.State.WAITING_GUEST) {

                                    //The game is not full
                                    if (game.getGuestsInGame().size () != game.getCapacity()) {
                                        game.getGuestsInGame().add (g);
                                        game.getGuestsInGameText().setText("Guests inside the Game: " + game.getGuestsInGame().size());


                                        //Operate time, the game is full
                                        if (game.getGuestsInGame().size () == game.getCapacity()) {
                                            game.operate(player, balanceText);
                                            game.increaseUseCounter();
                                            //Improving the mood of all the guests in the game and checking if its needed to change picture



                                            remove(game.getImg());
                                            game.switchGameToInUse();
                                            add (game.getImg());
                                        }
                                    }

                                    g.setIsWaiting(true);

                                } else if (game.getGameState() == Game.State.IN_USE) {

                                    game.getGuestsQueue().add (g);
                                    game.getGuestsQueueText().setText("Guests waiting: " + game.getGuestsQueue().size());
    //                               Check for how long have they been waiting
                                    g.setIsWaiting(true);

                                    //Checking if the turntime ended


                                } else if (game.getGameState() == Game.State.UNDER_REPAIR){

                                    game.getGuestsQueue().add (g);
                                    game.getGuestsQueueText().setText("Guests waiting: " + game.getGuestsQueue().size());
    //                               Check for how long have they been waiting
                                    g.setIsWaiting(true);
                                }
                            } else if (g.getDestination() instanceof Restaurant){

                                //The destination is Restaurant
                                     Restaurant restaurant = (Restaurant)g.getDestination();

                                    //Restaurant state = NOTFULL, guest will go on
                                 if (restaurant.getResState() == Restaurant.State.NOTFULL) {

                                     //The restaurant is not full
                                     if (restaurant.getGuestsInRes().size () != restaurant.getCapacity()) {
                                             g.setHasEaten(true);
                                             g.setIsWaiting(true);
                                             restaurant.getGuestsInRes().add (g);

                                             //restaurant.operate(player, balanceText);
                                             if (restaurant.getGuestsInRes().size () == restaurant.getCapacity()) {
                                                     restaurant.setResState(Restaurant.State.FULL);
                                             }

                                         //Operate time, the restaurant is full
                                         if (restaurant.getGuestsInRes().size () == restaurant.getCapacity()) {
                                             restaurant.operate(player, balanceText);
                                         }
                                     } 

                                 } else if (restaurant.getResState() == Restaurant.State.FULL) {
                                     restaurant.getGuestsWaitingRes().add (g);
                                     g.setIsWaiting(true);

                                     //Checking if the turntime ended


                                 }
                            } else {
                                //Remove guest from the frame
                                    itr.remove();
                                    remove(g.getImg());
                                    guestLabel.setText("Guests: " + String.valueOf(guests.size()));
                            }
                        }
                    }  else {
                        /*if (!g.isGuestInsideAGame(games))
                            g.eatOrPlay(games, restaurants, grid);*/
                        if (!g.isIsWaiting())
                            g.eatOrPlay(gamesAndRestaurants, grid);
                    }

                }
                
                
                



                //Iterating cleaner
                Iterator<Cleaner> clnr = cleaners.iterator();
                while (clnr.hasNext()) {
                    Cleaner c = clnr.next();

                    if (!c.getCoords().isEmpty()) {
                            c.getImg().setLocation(c.getCoords().pop());

                            //Checking if there is a trash in that location
                            Point p = grid.getPoint(c.getImg().getLocation());
                            Iterator<Guest> gs = guests.iterator();
                            while (gs.hasNext()) {
                                Guest g = gs.next();

                                Iterator<Trash> trsh = g.getTrash().iterator();
                                while (trsh.hasNext()) {
                                    Trash t = trsh.next();
                                    if (grid.getPoint(t.getImg().getLocation()).equals(p)) {
                                            c.cleanTrash();
                                            if(c.deduction()) {
                                                    player.setBalance(player.getBalance()-c.getSal());
                                                    balanceText.setText("Current Balance: "+String.valueOf(player.getBalance()));
                                                    c.resetTrashCleaned();
                                            }
                                        trsh.remove();
                                        remove(t.getImg());
                                    }
                                }
                            }
                    } else {
                        //No stopping
                        c.clean(grid, path);
                    }
                }
                
                
                Iterator<Repairman> rprman = repairmen.iterator();
                while (rprman.hasNext()) {
                    Repairman r = rprman.next();
                                
                    

                    if (!r.getCoords().isEmpty()) {
                            r.getImg().setLocation(r.getCoords().pop());

                            
                    } else {
                        
                        if (r.getDestination() == null && !brokenGames.isEmpty()) {
                            Game g = brokenGames.poll();
                            r.setDestination(g);
                            r.repair(grid, g);
                            
                        } else if  (r.getDestination() != null) {
                            //He has reached the game
                            if (r.getIsRepairing()) {
                                int n = (int)((System.currentTimeMillis() - r.getRepairTime())/1000);
                            
                                if (n == 3) {
                                    //game is fixed, change state, and make the repairman go back
                                    changeStateGame(r.getDestination(), "waitingGuests");
                                    r.setIsRepairing(false);
                                    r.getDestination().emptyQueue(player, balanceText);
                                    r.setDestination(null);
                                }
                            } else {
                                r.setRepairTime();
                                r.setIsRepairing(true);
                            }
 
                        } else {
                            //He went back to the starting point to wait for a game to break
                            r.goBack (grid);
                        }
                    }
                }


                repaint();
            }
        }

    }
    
    public Point getGridBoxLocation(Point p) {
         for (int i = 0; i < gridBoxes.size(); i++)
            for (Sprite s: gridBoxes.get (i)) {
                if (s.contains(p)) {  
                    return new Point(s.getX(), s.getY());
                }
        }
        
        return new Point(0, 0);
    }
    
    
    class dragAndDrop implements MouseListener,MouseMotionListener{

        Building b;
        private LabelIcon target;
        Point clickedPoint;
        
        public dragAndDrop (Building bld) {
            b = bld;
            target = bld.getImg();
            clickedPoint = target.getLocation();
        }
        
	@Override
	public void mouseDragged(MouseEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                target.setLocation(p.x, p.y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
             
	}

	@Override
	public void mouseClicked(MouseEvent e) {
            clearMenu();
            b.showMenu();
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
            Point p = MouseInfo.getPointerInfo().getLocation();
            
            if (checkCollision(target, p)) {
               target.setLocation(clickedPoint);
            } else {    
               target.setLocation(getGridBoxLocation(p));
               
               b.processMovement(grid, clickedPoint, target.getLocation());
               
               clickedPoint = target.getLocation();

            }
            System.out.println();
            grid.showMatrix();
            
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
    }
    
    
    class addGuestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (guests.size() <= 5)
                addGuest();
            
            for (Repairman r : repairmen) {
                player.setBalance(player.getBalance()-20);
                balanceText.setText("Current Balance: "+String.valueOf(player.getBalance()));
            }
        }
    }
    
}

/**
 * 1. Add what to do when a guests chooses a restaurant  -- Almost
 * 2. Fix when to increase or decrease the mood of the guests  -- Working
 * 2.1 When the mood goes to zero, leave the park.  -- Working
 * 3. Add the trash feature and removing trash    -- Working
 * 3.1 Cleaner should move around randomly  -- Working
 * 3.2 If on that path he finds trash, throw it to the trashCan.  -- Working
 * 
 * 
 * 
 * 
 * 10%, no need to add the repair feature.
 */



