package particulate.game.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import particulate.game.Eraser;
import particulate.game.ParticulateGame;
import particulate.game.Gases.Fire;
import particulate.game.Liquids.Lava;
import particulate.game.Liquids.Water;
import particulate.game.Solids.MoveableSolids.Ash;
import particulate.game.Solids.MoveableSolids.Sand;
import particulate.game.Solids.StaticSolids.Bedrock;
import particulate.game.Solids.StaticSolids.Obsidian;
import particulate.game.Solids.StaticSolids.Stone;
import particulate.game.Solids.StaticSolids.TNT;
import particulate.game.Solids.StaticSolids.Wood;
import particulate.game.Solids.StaticSolids.Spawners.AshSpawner;
import particulate.game.Solids.StaticSolids.Spawners.FireSpawner;
import particulate.game.Solids.StaticSolids.Spawners.LavaSpawner;
import particulate.game.Solids.StaticSolids.Spawners.SandSpawner;
import particulate.game.Solids.StaticSolids.Spawners.WaterSpawner;

public class Menu 
{

    JLayeredPane layeredPane = new JLayeredPane();

    private int sideMenuX = 1300;

    private int menuY = 800;
    private int menuX = 0;

    private int buttonIndent = menuX + 60;

    private int buttonHeight = 30;

    private int sideMenuIndent = sideMenuX + 10;
    private int debugInfoY = menuY+15;

    private int ySeperation = 20;
    private int smallButtonWidth = 75;
    private int mediumButtonWidth = 100;
    private int largeButtonWidth = 125;

    private int selectionButtonsY = menuY+5;
    private int firstRowY = selectionButtonsY + buttonHeight + ySeperation;
    private int secondRowY = selectionButtonsY + buttonHeight + ySeperation*2;
    

    // Define Menu Swap Buttons
    private JButton selectParticlesButton = new JButton("Particles");
    private JButton selectBlocksButton = new JButton("Blocks");
    private JButton selectSpawnersButton = new JButton("Spawners");
    private JButton selectOptionsButton = new JButton("Options");
    
    // Define eraser button
    private JButton eraserButton = new JButton("Eraser");
    
    // Define particle buttons
    private JButton sandButton = new JButton("Sand");
    private JButton waterButton = new JButton("Water");
    private JButton lavaButton = new JButton("Lava");
    private JButton fireButton = new JButton("Fire");
    private JButton ashButton = new JButton("Ash");

    // Define Block Buttons
    private JButton stoneButton = new JButton("Stone");
    private JButton bedrockButton = new JButton("Bedrock");
    private JButton obsidianButton = new JButton("Obsidian");
    private JButton woodButton = new JButton("Wood");
    private JButton staticTntButton = new JButton("Satic TNT");
    private JButton fallingTntButton = new JButton("Falling TNT");

    // Define Spawner Buttons
    private JButton sandSpawner = new JButton("Sand Spawner");
    private JButton waterSpawner = new JButton("Water Spawner");
    private JButton lavaSpawner = new JButton("Lava Spawner");
    private JButton fireSpawner = new JButton("Fire Spawner");
    private JButton ashSpawner = new JButton("Ash Spawner");

    // Define Options buttons
    private JButton exitGameButton = new JButton("Exit");
    private JButton savePlayAreaButton = new JButton("Save");
    
    // Define square draw size buttons
    private JButton smallSquareDrawSize = new JButton("Small");
    private JButton mediumSquareDrawSize = new JButton("Medium");
    private JButton largeSquareDrawSize = new JButton("Large");
    private JButton massiveSquareDrawSize = new JButton("Massive");
    
    // Define toggle buttons
    //Button toggleOverrideButton = new Button(seperatorX+10, sideMenuY+75, smallButtonWidth, buttonHeight, "Override");
    private JButton toggleOverrideButton = new JButton("Override");

    private JButton[] particlesMenu = new JButton[]{ sandButton, waterButton, lavaButton, fireButton, ashButton};
    private JButton[] blockMenu = new JButton[]{ stoneButton, bedrockButton, obsidianButton, woodButton, staticTntButton};
    private JButton[] optionsMenu = new JButton[]{ exitGameButton, savePlayAreaButton};
    private JButton[] spawnerMenu = new JButton[]{ sandSpawner, waterSpawner, lavaSpawner, fireSpawner, ashSpawner };

    private JButton[] selectionMenu = new JButton[]{selectParticlesButton, selectBlocksButton, selectSpawnersButton, selectOptionsButton};

    private JButton[] squareDrawSizeButtons = new JButton[]{smallSquareDrawSize, mediumSquareDrawSize, largeSquareDrawSize, massiveSquareDrawSize};

    private JButton[] toggleButtons = new JButton[]{toggleOverrideButton};

    private JButton[][] typeMenu = new JButton[4][10];

    String controls = "R: Reset Play Area    Space: Pause Simulation    S: Save play area    Drag file on screen to load it    Enter: Drop Floor";

    private int selectedMenu = 0;

    private JButton selectedButton = sandButton;
    
    JButton selectedSelectionButton = selectParticlesButton;

    JButton testJButton = new JButton("Test");

    int playAreaHeight= ParticulateGame.playAreaHeight;
    int SCREEN_WIDTH = ParticulateGame.SCREEN_WIDTH;
    int SCREEN_HEIGHT = ParticulateGame.SCREEN_HEIGHT;
    int tileSize = ParticulateGame.tileSize;

    Class<?> currentTile = ParticulateGame.getCurrentTile();
    Color outlineColor = ParticulateGame.getOutlineColor(); 

    private JButton selectedToggleButton = new JButton();

    public Menu(JFrame f)
    {
        defineButtonsPositionAndSize();
        defineButtonFunctions();
        addAllButtonsToLayeredFrame(f);

        typeMenu[0] = particlesMenu;
        typeMenu[1] = blockMenu;
        typeMenu[2] = spawnerMenu;
        typeMenu[3] = optionsMenu;
        swapColorsOfButton(sandButton);
        swapColorsOfButton(selectParticlesButton);
    }

    public void draw(Graphics pen) 
    {
        pen.setFont(new Font("Arial",1,15));

        // Draw side menu background
        pen.setColor(Color.DARK_GRAY);
        pen.fillRect(0, menuY, SCREEN_WIDTH, SCREEN_HEIGHT- playAreaHeight + tileSize);

        pen.setColor(Color.LIGHT_GRAY);
        pen.fillRect(sideMenuX, menuY, SCREEN_WIDTH-sideMenuX, SCREEN_HEIGHT-menuY);

        pen.setColor(Color.BLACK);
        pen.drawString("Brush Sizes: ", sideMenuIndent, menuY+buttonHeight+95);       

        pen.setColor(Color.WHITE);
        pen.drawString(controls, buttonIndent, 1000);

    }

    public void defineButtonFunctions()
    {
        //Define actions for the navigation buttons
        selectParticlesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedSelectionButton.equals(selectParticlesButton))
                        {
                                swapColorsOfButton(selectedSelectionButton);
                                selectedSelectionButton = selectParticlesButton;
                                swapColorsOfButton(selectParticlesButton);
                        }
                        
                        for(JButton b : particlesMenu)
                        {
                                b.setVisible(true);
                        }

                        for(JButton b : typeMenu[selectedMenu])
                        {
                                b.setVisible(false);
                        }

                        selectedMenu = 0;
                }
        });
        selectBlocksButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedSelectionButton.equals(selectBlocksButton))
                        {
                                swapColorsOfButton(selectedSelectionButton);
                                selectedSelectionButton = selectBlocksButton;
                                swapColorsOfButton(selectBlocksButton);
                        }
                        for(JButton b : blockMenu)
                        {
                                b.setVisible(true);
                        }

                        for(JButton b : typeMenu[selectedMenu])
                        {
                                b.setVisible(false);
                        }

                        selectedMenu = 1;
                }
        });
        selectSpawnersButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedSelectionButton.equals(selectSpawnersButton))
                        {
                                swapColorsOfButton(selectedSelectionButton);
                                selectedSelectionButton = selectSpawnersButton;
                                swapColorsOfButton(selectSpawnersButton);
                        }
                        for(JButton b : spawnerMenu)
                        {
                                b.setVisible(true);
                        }

                        for(JButton b : typeMenu[selectedMenu])
                        {
                                b.setVisible(false);
                        }

                        selectedMenu = 2;
                }
        });
        selectOptionsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedSelectionButton.equals(selectOptionsButton))
                        {
                                swapColorsOfButton(selectedSelectionButton);
                                selectedSelectionButton = selectOptionsButton;
                                swapColorsOfButton(selectOptionsButton);
                        }
                        for(JButton b : optionsMenu)
                        {
                                b.setVisible(true);
                        }

                        for(JButton b : typeMenu[selectedMenu])
                        {
                                b.setVisible(false);
                        }

                        selectedMenu = 3;
                }
        });
        
        //Define action for eraser button
        eraserButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(eraserButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = eraserButton;
                                swapColorsOfButton(eraserButton);
                        }
                        ParticulateGame.setCurrentTile(Eraser.class);
                        ParticulateGame.setOutlineColor(Color.PINK); 
                }
        });

        // Define toggle buttons
        toggleOverrideButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedToggleButton.equals(toggleOverrideButton))
                        {
                                swapColorsOfButton(selectedToggleButton);
                                selectedToggleButton = toggleOverrideButton;
                                swapColorsOfButton(toggleOverrideButton);
                        }
                        else
                        {
                                swapColorsOfButton(toggleOverrideButton);
                                selectedToggleButton = new JButton();
                        }
                        
                        ParticulateGame.flipOverride();
                }
        });

        // Define actions for the particle buttons
        sandButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(sandButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = sandButton;
                                swapColorsOfButton(sandButton);
                        }
                        ParticulateGame.setCurrentTile(Sand.class);
                        ParticulateGame.setOutlineColor(Color.YELLOW); 
                }
        });
        waterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(waterButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = waterButton;
                                swapColorsOfButton(waterButton);
                        }
                        ParticulateGame.setCurrentTile(Water.class);
                        ParticulateGame.setOutlineColor(Color.BLUE); 
                }
        });
        lavaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(lavaButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = lavaButton;
                                swapColorsOfButton(lavaButton);
                        }
                        ParticulateGame.setCurrentTile(Lava.class);
                        ParticulateGame.setOutlineColor(new Color(255, 185, 0)); 
                }
        });
        fireButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(fireButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = fireButton;
                                swapColorsOfButton(fireButton);
                        }
                        ParticulateGame.setCurrentTile(Fire.class);
                        ParticulateGame.setOutlineColor( new Color(255,135,0)); 
                }
        });
        ashButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(ashButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = ashButton;
                                swapColorsOfButton(ashButton);
                        }
                        ParticulateGame.setCurrentTile(Ash.class);
                        ParticulateGame.setOutlineColor(Color.LIGHT_GRAY); 
                }
        });

        // Define Blocks menu buttons
        stoneButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(stoneButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = stoneButton;
                                swapColorsOfButton(stoneButton);
                        }
                        ParticulateGame.setCurrentTile(Stone.class);
                        ParticulateGame.setOutlineColor(new Color(130, 130, 130)); 
                }
        });
        bedrockButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(bedrockButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = bedrockButton;
                                swapColorsOfButton(bedrockButton);
                        }
                        ParticulateGame.setCurrentTile(Bedrock.class);
                        ParticulateGame.setOutlineColor(new Color(70, 70, 70)); 
                }
        });
        obsidianButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(obsidianButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = obsidianButton;
                                swapColorsOfButton(obsidianButton);
                        }
                        ParticulateGame.setCurrentTile(Obsidian.class);
                        ParticulateGame.setOutlineColor( new Color(35, 35, 35)); 
                }
        });
        woodButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(woodButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = woodButton;
                                swapColorsOfButton(woodButton);
                        }
                        ParticulateGame.setCurrentTile(Wood.class);
                        ParticulateGame.setOutlineColor( new Color(140, 80, 20)); 
                }
        });
        staticTntButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(staticTntButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = staticTntButton;
                                swapColorsOfButton(staticTntButton);
                        }
                        ParticulateGame.setCurrentTile(TNT.class);
                        ParticulateGame.setOutlineColor(Color.RED); 
                }
        });
        
        // Define spawner buttons
        sandSpawner.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(sandSpawner))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = sandSpawner;
                                swapColorsOfButton(sandSpawner);
                        }
                        ParticulateGame.setCurrentTile(SandSpawner.class);
                        ParticulateGame.setOutlineColor(Color.RED); 
                }
        });
        waterSpawner.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(waterSpawner))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = waterSpawner;
                                swapColorsOfButton(waterSpawner);
                        }
                        ParticulateGame.setCurrentTile(WaterSpawner.class);
                        ParticulateGame.setOutlineColor(Color.CYAN); 
                }
        });
        lavaSpawner.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(lavaSpawner))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = lavaSpawner;
                                swapColorsOfButton(lavaSpawner);
                        }
                        ParticulateGame.setCurrentTile(LavaSpawner.class);
                        ParticulateGame.setOutlineColor( new Color(80, 200, 80)); 
                }
        });
        fireSpawner.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(fireSpawner))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = fireSpawner;
                                swapColorsOfButton(fireSpawner);
                        }
                        ParticulateGame.setCurrentTile(FireSpawner.class);
                        ParticulateGame.setOutlineColor( Color.MAGENTA); 
                }
        });
        ashSpawner.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(ashSpawner))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = ashSpawner;
                                swapColorsOfButton(ashSpawner);
                        }
                        ParticulateGame.setCurrentTile(AshSpawner.class);
                        ParticulateGame.setOutlineColor(Color.GREEN); 
                }
        });

        // Define brush size button functionality
        smallSquareDrawSize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        ParticulateGame.setDrawSize(1);
                }
        });
        mediumSquareDrawSize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        ParticulateGame.setDrawSize(10);
                }
        });
        largeSquareDrawSize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        ParticulateGame.setDrawSize(50);
                }
        });
        massiveSquareDrawSize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        ParticulateGame.setDrawSize(100);
                }
        });
    }

    public void swapColorsOfButton(JButton b)
    {
        Color bg = b.getBackground();
        Color fg = b.getForeground();

        b.setBackground(fg);
        b.setForeground(bg);


    }

    public void drawMatrixInfo(Graphics pen, String tileName, int mX, int mY, int drawSize)
    {
        pen.setColor(Color.BLACK);
        
        pen.drawString("Hovered Over Tile: "+tileName, sideMenuIndent, debugInfoY+10);
        pen.drawString("X: "+mX+" Y: "+mY, sideMenuIndent, debugInfoY+30);
        pen.drawString("Brush Size: "+drawSize, sideMenuIndent, debugInfoY+50);

    }

    public void defineButtonsPositionAndSize()
    {
        // Define navigation buttons
        selectParticlesButton.setBounds(buttonIndent, selectionButtonsY, mediumButtonWidth, buttonHeight);
        selectBlocksButton.setBounds(buttonIndent+mediumButtonWidth, selectionButtonsY, mediumButtonWidth, buttonHeight);
        selectSpawnersButton.setBounds(buttonIndent+mediumButtonWidth*2, selectionButtonsY, mediumButtonWidth, buttonHeight);
        selectOptionsButton.setBounds(buttonIndent+mediumButtonWidth*3, selectionButtonsY, mediumButtonWidth, buttonHeight);

        // Define particle buttons
        sandButton.setBounds(buttonIndent + smallButtonWidth, firstRowY, smallButtonWidth, buttonHeight);
        waterButton.setBounds(buttonIndent + smallButtonWidth*2, firstRowY, smallButtonWidth, buttonHeight);
        lavaButton.setBounds(buttonIndent + smallButtonWidth*3, firstRowY, smallButtonWidth, buttonHeight);
        fireButton.setBounds(buttonIndent + smallButtonWidth*4, firstRowY, smallButtonWidth, buttonHeight);
        ashButton.setBounds(buttonIndent + smallButtonWidth*5, firstRowY, smallButtonWidth, buttonHeight);

        // Define blocks
        stoneButton.setBounds(buttonIndent + smallButtonWidth, firstRowY, smallButtonWidth, buttonHeight);
        bedrockButton.setBounds(buttonIndent + smallButtonWidth*2, firstRowY, mediumButtonWidth, buttonHeight);
        obsidianButton.setBounds(buttonIndent + smallButtonWidth*2+mediumButtonWidth, firstRowY, mediumButtonWidth, buttonHeight);
        woodButton.setBounds(buttonIndent + smallButtonWidth*2+mediumButtonWidth*2, firstRowY, smallButtonWidth, buttonHeight);
        staticTntButton.setBounds(buttonIndent + smallButtonWidth*3+mediumButtonWidth*2, firstRowY, mediumButtonWidth, buttonHeight);

        // Define spawners
        sandSpawner.setBounds(buttonIndent + smallButtonWidth, firstRowY, largeButtonWidth, buttonHeight);
        waterSpawner.setBounds(buttonIndent + smallButtonWidth + largeButtonWidth, firstRowY, largeButtonWidth, buttonHeight);
        lavaSpawner.setBounds(buttonIndent +  + smallButtonWidth+ largeButtonWidth*2, firstRowY, largeButtonWidth, buttonHeight);
        fireSpawner.setBounds(buttonIndent + smallButtonWidth + largeButtonWidth*3, firstRowY, largeButtonWidth, buttonHeight);
        ashSpawner.setBounds(buttonIndent + smallButtonWidth + largeButtonWidth*4, firstRowY, largeButtonWidth, buttonHeight);

        // Define toggle buttons
        toggleOverrideButton.setBounds(sideMenuIndent, menuY+75, mediumButtonWidth, buttonHeight);

        // Define brush size buttons
        smallSquareDrawSize.setBounds(sideMenuIndent,menuY+buttonHeight+100, mediumButtonWidth, buttonHeight);
        mediumSquareDrawSize.setBounds(sideMenuIndent+mediumButtonWidth,menuY+buttonHeight+100, mediumButtonWidth, buttonHeight);
        largeSquareDrawSize.setBounds(sideMenuIndent+mediumButtonWidth*2,menuY+buttonHeight+100, mediumButtonWidth, buttonHeight);
        massiveSquareDrawSize.setBounds(sideMenuIndent+mediumButtonWidth*3,menuY+buttonHeight+100, mediumButtonWidth, buttonHeight);

        eraserButton.setBounds(buttonIndent, firstRowY, smallButtonWidth, buttonHeight);
    }

    public void addAllButtonsToLayeredFrame(JFrame f)
    {
        layeredPane.setLocation(0,menuY);
        layeredPane.setPreferredSize(new Dimension(SCREEN_WIDTH , 1000));
        layeredPane.setLayout(null);

        eraserButton.setFocusable(false);
        eraserButton.setBackground(Color.WHITE);
        eraserButton.setForeground(Color.BLACK);

        layeredPane.add(eraserButton);
        //layeredPane.add(testButton);

        for(JButton b : toggleButtons)
        {
                b.setFocusable(false);
                layeredPane.add(b);
                b.setBackground(Color.WHITE);
                b.setForeground(Color.BLACK);
        }        

        for(JButton b : selectionMenu)
        {
                b.setFocusable(false);
                layeredPane.add(b);
                b.setBackground(Color.WHITE);
                b.setForeground(Color.BLACK);
        }
        
        for(JButton b : particlesMenu)
        {
                b.setFocusable(false);
                layeredPane.add(b);
                b.setBackground(Color.WHITE);
                b.setForeground(Color.BLACK);
        }

        for(JButton b : blockMenu)
        {
                b.setFocusable(false);
                b.setVisible(false);
                layeredPane.add(b);
                b.setBackground(Color.WHITE);
                b.setForeground(Color.BLACK);
        }

        for(JButton b : spawnerMenu)
        {
                b.setFocusable(false);
                b.setVisible(false);
                layeredPane.add(b);
                b.setBackground(Color.WHITE);
                b.setForeground(Color.BLACK);
        }

        for(JButton b : optionsMenu)
        {
                b.setFocusable(false);
                b.setVisible(false);
                layeredPane.add(b);
                b.setBackground(Color.WHITE);
                b.setForeground(Color.BLACK);
        }

        for(JButton b : squareDrawSizeButtons)
        {
                b.setFocusable(false);
                layeredPane.add(b);
                b.setBackground(Color.WHITE);
                b.setForeground(Color.BLACK);
        }

        f.getContentPane().add(layeredPane, BorderLayout.CENTER);
        f.pack();
    }

    public int getX()
    {
        return menuX;
    }

        public int getY()
        {
                return menuY;
        }
}
