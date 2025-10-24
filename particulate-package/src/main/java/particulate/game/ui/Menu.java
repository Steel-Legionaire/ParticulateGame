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

import particulate.game.CellularMatrix;
import particulate.game.Eraser;
import particulate.game.ParticulateGame;
import particulate.game.Gases.Fire;
import particulate.game.Gases.Steam;
import particulate.game.Liquids.Lava;
import particulate.game.Liquids.Water;
import particulate.game.Solids.MoveableSolids.Ash;
import particulate.game.Solids.MoveableSolids.Dirt;
import particulate.game.Solids.MoveableSolids.Sand;
import particulate.game.Solids.StaticSolids.Bedrock;
import particulate.game.Solids.StaticSolids.Obsidian;
import particulate.game.Solids.StaticSolids.Stone;
import particulate.game.Solids.StaticSolids.TNT;
import particulate.game.Solids.StaticSolids.Wood;
import particulate.game.Solids.StaticSolids.Spawners.AshSpawner;
import particulate.game.Solids.StaticSolids.Spawners.DirtSpawner;
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
    private JButton selectLandMenuButton = new JButton("Land");
    private JButton selectLiquidsMenuButton = new JButton("Liquids");
    private JButton selectExplosivesMenuButton = new JButton("Explosives"); 
    private JButton selectGasesMenuButton = new JButton("Gases");
    private JButton selectSpawnersButton = new JButton("Spawners");
    private JButton miscSelectMenuButton = new JButton("Misc");
    private JButton selectOptionsButton = new JButton("Options");
    
    // Define eraser button
    private JButton eraserButton = new JButton("Eraser");
    
    // Define Moveable Solids buttons
    private JButton sandButton = new JButton("Sand");
    private JButton ashButton = new JButton("Ash");
    private JButton dirtButton = new JButton("Dirt");

    // Define Gasses buttons
    private JButton fireButton = new JButton("Fire");
    private JButton steamButton = new JButton("Steam");

    // Define Liquid Buttons
    private JButton waterButton = new JButton("Water");
    private JButton lavaButton = new JButton("Lava");

    // Define Solid Buttons
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
    private JButton dirtSpawner = new JButton("Dirt Spawner");

    // Define Options buttons
    private JButton exitGameButton = new JButton("Exit");
    private JButton savePlayAreaButton = new JButton("Save");

    // Define misc menu buttons
    private JButton heatRay = new JButton("Heat Ray");
    private JButton freezeRay = new JButton("Freeze Ray");
    
    // Define square draw size buttons
    private JButton smallSquareDrawSize = new JButton("Small");
    private JButton mediumSquareDrawSize = new JButton("Medium");
    private JButton largeSquareDrawSize = new JButton("Large");
    private JButton massiveSquareDrawSize = new JButton("Massive");
    
    // Define toggle buttons
    private JButton toggleOverrideButton = new JButton("Override");

    private JButton[] landMenuButtons = new JButton[]{ sandButton, stoneButton, bedrockButton, obsidianButton, woodButton, ashButton, dirtButton};
    private JButton[] liquidsMenu = new JButton[]{waterButton, lavaButton};
    private JButton[] explosivesMenu = new JButton[]{ staticTntButton };
    private JButton[] gasesMenu = new JButton[]{ fireButton, steamButton };
    private JButton[] spawnerMenu = new JButton[]{ sandSpawner, waterSpawner, lavaSpawner, fireSpawner, ashSpawner, dirtSpawner };
    private JButton[] miscMenu = new JButton[]{heatRay, freezeRay};
    private JButton[] optionsMenu = new JButton[]{ exitGameButton, savePlayAreaButton};

    private JButton[] selectionMenu = new JButton[]{selectLandMenuButton, selectLiquidsMenuButton, selectExplosivesMenuButton, selectGasesMenuButton, selectSpawnersButton, miscSelectMenuButton, selectOptionsButton};

    private JButton[] squareDrawSizeButtons = new JButton[]{smallSquareDrawSize, mediumSquareDrawSize, largeSquareDrawSize, massiveSquareDrawSize};

    private JButton[] toggleButtons = new JButton[]{toggleOverrideButton};

    private JButton[][] typeMenu = new JButton[7][20];

    String controls = "R: Reset Play Area    Space: Pause Simulation    S: Save play area    Drag file on screen to load it    Enter: Drop Floor    ctrl + left click: Activate Line Tool";

    private int selectedMenu = 0;

    private JButton selectedButton = sandButton;
    
    JButton selectedSelectionButton = selectLandMenuButton;

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

        typeMenu[0] = landMenuButtons;
        typeMenu[1] = liquidsMenu;
        typeMenu[2] = explosivesMenu;
        typeMenu[3] = gasesMenu;
        typeMenu[4] = spawnerMenu;
        typeMenu[5] = miscMenu;
        typeMenu[6] = optionsMenu;

        swapColorsOfButton(sandButton);
        swapColorsOfButton(selectLandMenuButton);
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
        selectLandMenuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedSelectionButton.equals(selectLandMenuButton))
                        {
                                swapColorsOfButton(selectedSelectionButton);
                                selectedSelectionButton = selectLandMenuButton;
                                swapColorsOfButton(selectLandMenuButton);
                        }
                        
                        for(JButton b : landMenuButtons)
                        {
                                b.setVisible(true);
                        }

                        for(JButton b : typeMenu[selectedMenu])
                        {
                                b.setVisible(false);
                        }

                        eraserButton.setVisible(true);

                        selectedMenu = 0;
                }
        });
        selectLiquidsMenuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedSelectionButton.equals(selectLiquidsMenuButton))
                        {
                                swapColorsOfButton(selectedSelectionButton);
                                selectedSelectionButton = selectLiquidsMenuButton;
                                swapColorsOfButton(selectLiquidsMenuButton);
                        }
                        for(JButton b : liquidsMenu)
                        {
                                b.setVisible(true);
                        }

                        for(JButton b : typeMenu[selectedMenu])
                        {
                                b.setVisible(false);
                        }

                        eraserButton.setVisible(true);

                        selectedMenu = 1;
                }
        });
        selectExplosivesMenuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedSelectionButton.equals(selectExplosivesMenuButton))
                        {
                                swapColorsOfButton(selectedSelectionButton);
                                selectedSelectionButton = selectExplosivesMenuButton;
                                swapColorsOfButton(selectExplosivesMenuButton);
                        }
                        for(JButton b : explosivesMenu)
                        {
                                b.setVisible(true);
                        }

                        for(JButton b : typeMenu[selectedMenu])
                        {
                                b.setVisible(false);
                        }

                        eraserButton.setVisible(true);

                        selectedMenu = 2;
                }
        });
        selectGasesMenuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedSelectionButton.equals(selectGasesMenuButton))
                        {
                                swapColorsOfButton(selectedSelectionButton);
                                selectedSelectionButton = selectGasesMenuButton;
                                swapColorsOfButton(selectGasesMenuButton);
                        }
                        for(JButton b : gasesMenu)
                        {
                                b.setVisible(true);
                        }

                        for(JButton b : typeMenu[selectedMenu])
                        {
                                b.setVisible(false);
                        }

                        eraserButton.setVisible(true);

                        selectedMenu = 3;
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

                        eraserButton.setVisible(true);

                        selectedMenu = 4;
                }
        });
        miscSelectMenuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedSelectionButton.equals(miscSelectMenuButton))
                        {
                                swapColorsOfButton(selectedSelectionButton);
                                selectedSelectionButton = miscSelectMenuButton;
                                swapColorsOfButton(miscSelectMenuButton);
                        }
                        for(JButton b : miscMenu)
                        {
                                b.setVisible(true);
                        }

                        for(JButton b : typeMenu[selectedMenu])
                        {
                                b.setVisible(false);
                        }

                        eraserButton.setVisible(true);

                        selectedMenu = 5;
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

                        eraserButton.setVisible(false);

                        selectedMenu = 6;
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

        // Define actions for the land buttons
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
        dirtButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(dirtButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = dirtButton;
                                swapColorsOfButton(dirtButton);
                        }
                        ParticulateGame.setCurrentTile(Dirt.class);
                        ParticulateGame.setOutlineColor( new Color(180, 80, 0)); 
                }
        });

        // Define exlposives buttons
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

        // Define Gases buttons
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


        steamButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(steamButton))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = steamButton;
                                swapColorsOfButton(steamButton);
                        }
                        ParticulateGame.setCurrentTile(Steam.class);
                        ParticulateGame.setOutlineColor( Color.WHITE); 
                }
        });
        

        // Define liquids buttons
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
        dirtSpawner.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(!selectedButton.equals(dirtSpawner))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = dirtSpawner;
                                swapColorsOfButton(dirtSpawner);
                        }
                        ParticulateGame.setCurrentTile(DirtSpawner.class);
                        ParticulateGame.setOutlineColor(Color.YELLOW); 
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
    
        // Define options menu buttons
        savePlayAreaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        ParticulateGame.saveGridToTextFile();
                }
        });

        exitGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                }
        });

        // Define misc menu buttons

        heatRay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        
                        if(!selectedButton.equals(heatRay))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = heatRay;
                                swapColorsOfButton(heatRay);

                                ParticulateGame.heatRayActive = true;
                                ParticulateGame.freezeRayActive = false;
                                ParticulateGame.setOutlineColor(Color.RED);
                        }
                }
        });

        freezeRay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        
                        if(!selectedButton.equals(freezeRay))
                        {
                                swapColorsOfButton(selectedButton);
                                selectedButton = freezeRay;
                                swapColorsOfButton(freezeRay);

                                ParticulateGame.freezeRayActive = true;
                                ParticulateGame.heatRayActive = false;
                                ParticulateGame.setOutlineColor(Color.BLUE);
                        }
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
        
        CellularMatrix matrix = ParticulateGame.getMatrix();

        String heatVal = matrix.withinBounds(mX, mY) && matrix.getTile(mX, mY) != null ? matrix.getTile(mX, mY).getHeat()+"C" : "null";

        pen.drawString("Hovered Over Tile: "+tileName, sideMenuIndent, debugInfoY+10);
        pen.drawString("Heat: "+heatVal, sideMenuIndent+200, debugInfoY+10);
        pen.drawString("X: "+mX+" Y: "+mY, sideMenuIndent, debugInfoY+30);
        pen.drawString("Brush Size: "+drawSize, sideMenuIndent, debugInfoY+50);

    }

    public void defineButtonsPositionAndSize()
    {
        // Define navigation buttons
        selectLandMenuButton.setBounds(buttonIndent, selectionButtonsY, mediumButtonWidth, buttonHeight);
        selectLiquidsMenuButton.setBounds(buttonIndent+mediumButtonWidth, selectionButtonsY, mediumButtonWidth, buttonHeight);
        selectExplosivesMenuButton.setBounds(buttonIndent+mediumButtonWidth*2, selectionButtonsY, mediumButtonWidth, buttonHeight);
        selectGasesMenuButton.setBounds(buttonIndent+mediumButtonWidth*3, selectionButtonsY, mediumButtonWidth, buttonHeight);
        selectSpawnersButton.setBounds(buttonIndent+mediumButtonWidth*4, selectionButtonsY, mediumButtonWidth, buttonHeight);
        miscSelectMenuButton.setBounds(buttonIndent+mediumButtonWidth*5, selectionButtonsY, mediumButtonWidth, buttonHeight);
        selectOptionsButton.setBounds(buttonIndent+mediumButtonWidth*6, selectionButtonsY, mediumButtonWidth, buttonHeight);

        // Define land buttons
        sandButton.setBounds(buttonIndent + smallButtonWidth, firstRowY, smallButtonWidth, buttonHeight);
        ashButton.setBounds(buttonIndent + smallButtonWidth*2, firstRowY, smallButtonWidth, buttonHeight);
        stoneButton.setBounds(buttonIndent + smallButtonWidth*3, firstRowY, smallButtonWidth, buttonHeight);
        bedrockButton.setBounds(buttonIndent + smallButtonWidth*4, firstRowY, mediumButtonWidth, buttonHeight);
        obsidianButton.setBounds(buttonIndent + smallButtonWidth*4+mediumButtonWidth, firstRowY, mediumButtonWidth, buttonHeight);
        woodButton.setBounds(buttonIndent + smallButtonWidth*4 + mediumButtonWidth*2, firstRowY, smallButtonWidth, buttonHeight);
        dirtButton.setBounds(buttonIndent + smallButtonWidth*5 + mediumButtonWidth*2, firstRowY, smallButtonWidth, buttonHeight);

        // Define liquids buttons
        waterButton.setBounds(buttonIndent + smallButtonWidth, firstRowY, smallButtonWidth, buttonHeight);
        lavaButton.setBounds(buttonIndent + smallButtonWidth*2, firstRowY, smallButtonWidth, buttonHeight);

        // Define gases buttons 
        fireButton.setBounds(buttonIndent + smallButtonWidth, firstRowY, smallButtonWidth, buttonHeight);
        steamButton.setBounds(buttonIndent + smallButtonWidth*2, firstRowY, smallButtonWidth, buttonHeight);

        // Define explosive buttons
        staticTntButton.setBounds(buttonIndent + smallButtonWidth, firstRowY, mediumButtonWidth, buttonHeight);

        // Define spawners
        sandSpawner.setBounds(buttonIndent + smallButtonWidth, firstRowY, largeButtonWidth, buttonHeight);
        waterSpawner.setBounds(buttonIndent + smallButtonWidth + largeButtonWidth, firstRowY, largeButtonWidth, buttonHeight);
        lavaSpawner.setBounds(buttonIndent +  + smallButtonWidth+ largeButtonWidth*2, firstRowY, largeButtonWidth, buttonHeight);
        fireSpawner.setBounds(buttonIndent + smallButtonWidth + largeButtonWidth*3, firstRowY, largeButtonWidth, buttonHeight);
        ashSpawner.setBounds(buttonIndent + smallButtonWidth + largeButtonWidth*4, firstRowY, largeButtonWidth, buttonHeight);
        dirtSpawner.setBounds(buttonIndent + smallButtonWidth + largeButtonWidth*5, firstRowY, largeButtonWidth, buttonHeight);

        // Define toggle buttons
        toggleOverrideButton.setBounds(sideMenuIndent, menuY+75, mediumButtonWidth, buttonHeight);

        // Define brush size buttons
        smallSquareDrawSize.setBounds(sideMenuIndent,menuY+buttonHeight+100, mediumButtonWidth, buttonHeight);
        mediumSquareDrawSize.setBounds(sideMenuIndent+mediumButtonWidth,menuY+buttonHeight+100, mediumButtonWidth, buttonHeight);
        largeSquareDrawSize.setBounds(sideMenuIndent+mediumButtonWidth*2,menuY+buttonHeight+100, mediumButtonWidth, buttonHeight);
        massiveSquareDrawSize.setBounds(sideMenuIndent+mediumButtonWidth*3,menuY+buttonHeight+100, mediumButtonWidth, buttonHeight);

        eraserButton.setBounds(buttonIndent, firstRowY, smallButtonWidth, buttonHeight);

        // Define Misc menu bounds
        heatRay.setBounds(buttonIndent+smallButtonWidth, firstRowY, mediumButtonWidth, buttonHeight);
        freezeRay.setBounds(buttonIndent+smallButtonWidth+mediumButtonWidth, firstRowY, mediumButtonWidth, buttonHeight);

        // Define options menu bounds
        exitGameButton.setBounds(buttonIndent, firstRowY, smallButtonWidth, buttonHeight);
        savePlayAreaButton.setBounds(buttonIndent+smallButtonWidth, firstRowY, smallButtonWidth, buttonHeight);
        
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
        
        for(JButton b : landMenuButtons)
        {
                b.setFocusable(false);
                layeredPane.add(b);
                b.setBackground(Color.WHITE);
                b.setForeground(Color.BLACK);
        }

        for(JButton b : liquidsMenu)
        {
                b.setFocusable(false);
                b.setVisible(false);
                layeredPane.add(b);
                b.setBackground(Color.WHITE);
                b.setForeground(Color.BLACK);
        }

        for(JButton b : gasesMenu)
        {
                b.setFocusable(false);
                b.setVisible(false);
                layeredPane.add(b);
                b.setBackground(Color.WHITE);
                b.setForeground(Color.BLACK);
        }

        for(JButton b : explosivesMenu)
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

        for(JButton b : miscMenu)
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
