package particulate.game.ui;

import java.awt.Color;
import java.awt.Graphics;

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

    int buttonWidth = 200;
    int buttonHeight = 50;
    int sideMenuX = 1500;

    int buttonX = sideMenuX + 60;

    // Define Block Buttons
    Button stoneButton = new Button(buttonX, 75, buttonWidth, buttonHeight, "Stone");
    Button bedrockButton = new Button(buttonX, 150, buttonWidth, buttonHeight, "Bedrock");
    Button obsidianButton = new Button(buttonX, 225, buttonWidth, buttonHeight, "Obsidian");
    Button woodButton = new Button(buttonX, 300, buttonWidth, buttonHeight, "Wood");
    Button staticTntButton = new Button(buttonX, 375, buttonWidth, buttonHeight, "Satic TNT");
    Button fallingTntButton = new Button(buttonX, 450, buttonWidth, buttonHeight, "Falling TNT");

    // Define particle buttons
    Button sandButton = new Button(buttonX, 75, buttonWidth, buttonHeight, "Sand");
    Button waterButton = new Button(buttonX, 150, buttonWidth, buttonHeight, "Water");
    Button lavaButton = new Button(buttonX, 225, buttonWidth, buttonHeight, "Lava");
    Button fireButton = new Button(buttonX, 300, buttonWidth, buttonHeight, "Fire");
    Button ashButton = new Button(buttonX, 375, buttonWidth, buttonHeight, "Ash");

    // Define Spawner Buttons
    Button sandSpawner = new Button(buttonX, 75, buttonWidth, buttonHeight, "Sand Spawner");
    Button waterSpawner = new Button(buttonX, 150, buttonWidth, buttonHeight, "Water Spawner");
    Button lavaSpawner = new Button(buttonX, 225, buttonWidth, buttonHeight, "Lava Spawner");
    Button fireSpawner = new Button(buttonX, 300, buttonWidth, buttonHeight, "Fire Spawner");
    Button ashSpawner = new Button(buttonX, 375, buttonWidth, buttonHeight, "Ash Spawner");

    // Define Options button and menu buttons
    Button optionsButton = new Button(sideMenuX + 135, 930, 50, buttonHeight, "\u2699" );
    Button exitGameButton = new Button(buttonX, 75, buttonWidth, buttonHeight, "Exit Game");

    // Define page switcher buttons
    Button pageLeftButton = new Button(sideMenuX + 60, 930, 50, buttonHeight, "\u2190");
    Button pageRightButton = new Button(sideMenuX + 210, 930, 50, buttonHeight, "\u2192");
    
    // Define eraser button
    Button eraserButton = new Button(buttonX, 855, buttonWidth, buttonHeight, "Eraser"); 

    // Define square draw size buttons
    Button smallSquareDrawSize = new Button(buttonX, 515, buttonWidth, buttonHeight, "Small");
    Button mediumSquareDrawSize = new Button(buttonX, 575, buttonWidth, buttonHeight, "Medium");
    Button largeSquareDrawSize = new Button(buttonX, 635, buttonWidth, buttonHeight, "Large");
    Button massiveSquareDrawSize = new Button(buttonX, 695, buttonWidth, buttonHeight, "Massive");

    // Define save play area button
    Button savePlayAreaButton = new Button(buttonX, 775, buttonWidth, buttonHeight, "Save Play Area");

    Button[] particlesMenu = new Button[]{ sandButton, waterButton, lavaButton, fireButton, ashButton};
    Button[] blockMenu = new Button[]{ stoneButton, bedrockButton, obsidianButton, woodButton, staticTntButton};
    Button[] optionsMenu = new Button[]{ exitGameButton, savePlayAreaButton};
    Button[] spawnerMenu = new Button[]{ sandSpawner, waterSpawner, lavaSpawner, fireSpawner, ashSpawner };

    Button[][] menus = new Button[4][10];

    Button[] squareDrawSizeButtons = new Button[]{smallSquareDrawSize, mediumSquareDrawSize, largeSquareDrawSize, massiveSquareDrawSize};

    String[] controlsList = new String[]{ "r: Reset Play Area", "e: Select Eraser", "Space: Pause Simulation", "s: save play area", "Drag file on screen", " to load it", "Enter: Drop Floor", "1: Sand", "2: Water", "3: Lava", "4: Fire", "5: Wall", "6: Wood", "7: Tnt"};

    private int selectedMenu = 0;

    private Button selectedButton = sandButton;
    

    public Menu()
    {
        sandButton.swapColors();
        menus[0] = particlesMenu;
        menus[1] = blockMenu;
        menus[2] = spawnerMenu;
        menus[3] = optionsMenu;
    }

    public void draw(Graphics pen) 
    {
        int playAreaWidth = ParticulateGame.playAreaWidth;
        int SCREEN_WIDTH = ParticulateGame.SCREEN_WIDTH;
        int SCREEN_HEIGHT = ParticulateGame.SCREEN_HEIGHT;
        int tileSize = ParticulateGame.tileSize;

        // Draw side menu background
        pen.setColor(Color.DARK_GRAY);
        pen.fillRect(playAreaWidth, 0, SCREEN_WIDTH - playAreaWidth + tileSize, SCREEN_HEIGHT);
        
        // Side menu buttons 
        pageLeftButton.draw(pen);
        pageRightButton.draw(pen);
        optionsButton.draw(pen);
        eraserButton.draw(pen);
        
        if(menus[selectedMenu] != null)
        {
                for(int c=0;c<menus[selectedMenu].length;c++)
                {
                        if(menus[selectedMenu][c] != null)
                        {
                                menus[selectedMenu][c].draw(pen);

                                pen.setColor(Color.WHITE);      
                                //System.out.println(selectedMenu);

                                if(selectedMenu == 0)
                                {
                                        pen.drawString("Particles", sideMenuX + 115, 50);
                                }
                                else if(selectedMenu == 1)
                                {
                                        pen.drawString("Blocks", sideMenuX + 130, 50);
                                }
                                else if(selectedMenu == 2)
                                {
                                        pen.drawString("Spawners", sideMenuX + 115, 50);
                                }
                                else if(selectedMenu == 3)
                                {
                                        //System.out.println("IN OPTIONS MENU");
                                        pen.drawString("Options", sideMenuX + 115, 50);

                                        // Draw all controls strings

                                        for(int i=0; i<controlsList.length; i++)
                                        {
                                                pen.drawString(controlsList[i], sideMenuX + 60, 150 + (i * 25));
                                        }

                                        pen.drawString("Square Draw Sizes", sideMenuX + 70, 500);

                                        for(Button b : squareDrawSizeButtons)
                                        {
                                                b.draw(pen);
                                        }


                                }

                        }

                }
        }
    }

    public void clicked(int mx, int my)
    {
        Class<?> currentTile = ParticulateGame.getCurrentTile();
        Color outlineColor = ParticulateGame.getOutlineColor(); 
        
        if(pageLeftButton.clickedButton(mx, my))
            { 
                    if(selectedMenu > 0)
                    {
                        selectedMenu--;
                    }
                    else if(selectedMenu == 0)
                    {
                        selectedMenu = 2;
                    }
            }
            else if(pageRightButton.clickedButton(mx, my))
            { 
                    if(selectedMenu < 2)
                    {
                            selectedMenu++;
                    }
                    else if(selectedMenu == 2)
                    { 
                            selectedMenu = 0;
                    }

            }
            else if(optionsButton.clickedButton(mx, my))
            { 
                    if(selectedMenu == 3)
                    {
                            selectedMenu = 0;
                    }
                    else
                    {
                            selectedMenu = 3;
                    }       
                        
            }
            else if(eraserButton.clickedButton(mx, my)) 
            { 
                    currentTile = Eraser.class; 
                    if(!eraserButton.equals(selectedButton))
                    {
                            selectedButton.swapColors();
                            eraserButton.swapColors();
                            selectedButton = eraserButton;
                            outlineColor = Color.PINK;
                    }
            }

            if(selectedMenu == 0)
            {
                    if(sandButton.clickedButton(mx, my))
                    { 
                            currentTile = Sand.class; 
                            if(!sandButton.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    sandButton.swapColors();
                                    selectedButton = sandButton;
                                    outlineColor = Color.YELLOW;
                            }
                    }
                    else if(waterButton.clickedButton(mx, my)) 
                    { 
                            currentTile = Water.class; 
                            if(!waterButton.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    waterButton.swapColors();
                                    selectedButton = waterButton;
                                    outlineColor = Color.BLUE;
                            }
                    }
                    else if(lavaButton.clickedButton(mx, my)) 
                    { 
                            currentTile = Lava.class; 
                            if(!lavaButton.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    lavaButton.swapColors();
                                    selectedButton = lavaButton;
                                    outlineColor = new Color(255, 185, 0);
                            }
                    }
                    else if(fireButton.clickedButton(mx, my)) 
                    { 
                            currentTile = Fire.class; 
                            if(!fireButton.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    fireButton.swapColors();
                                    selectedButton = fireButton;
                                    outlineColor = new Color(255,135,0);
                            }
                    }
                    else if(ashButton.clickedButton(mx, my)) 
                    { 
                            currentTile = Ash.class; 
                            if(!ashButton.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    ashButton.swapColors();
                                    selectedButton = ashButton;
                                    outlineColor = new Color(230,230,230);
                            }
                    }
            }
            else if(selectedMenu == 1)
            {
                    if( stoneButton.clickedButton(mx, my)) 
                    { 
                            currentTile = Stone.class; 
                            if(!stoneButton.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    stoneButton.swapColors();
                                    selectedButton = stoneButton;
                                    outlineColor = new Color(140, 140, 140);
                            }
                    }
                    else if(bedrockButton.clickedButton(mx, my)) 
                    { 
                            currentTile = Bedrock.class; 
                            if(!bedrockButton.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    bedrockButton.swapColors();
                                    selectedButton = bedrockButton;
                                    outlineColor = Color.DARK_GRAY;
                            }
                    }
                    else if(obsidianButton.clickedButton(mx, my)) 
                    {
                            currentTile = Obsidian.class; 
                            if(!obsidianButton.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    obsidianButton.swapColors();
                                    selectedButton = obsidianButton;
                                    outlineColor = Color.BLACK;
                            }
                            }
                    else if(woodButton.clickedButton(mx, my)) 
                    { 
                            currentTile = Wood.class; 
                            if(!woodButton.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    woodButton.swapColors();
                                    selectedButton = woodButton;
                                    outlineColor = new Color(129, 64, 18);
                            }
                    }
                    else if(staticTntButton.clickedButton(mx, my)) 
                    {
                            currentTile = TNT.class; 
                            if(!staticTntButton.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    staticTntButton.swapColors();
                                    selectedButton = staticTntButton;
                                    outlineColor = Color.RED;
                            }
                    }
                    else if(fallingTntButton.clickedButton(mx, my)) 
                    {
                            currentTile = TNT.class; 
                            if(!fallingTntButton.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    fallingTntButton.swapColors();
                                    selectedButton = fallingTntButton;
                                    outlineColor = Color.RED;
                            }
                    }
            }
            else if(selectedMenu == 2)
            {
                    if(sandSpawner.clickedButton(mx, my)) 
                    {
                            currentTile = SandSpawner.class; 
                            if(!sandSpawner.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    sandSpawner.swapColors();
                                    selectedButton = sandSpawner;
                                    outlineColor = Color.RED;
                            }
                    }
                    else if(waterSpawner.clickedButton(mx, my)) 
                    { 
                            currentTile = WaterSpawner.class; 
                            if(!waterSpawner.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    waterSpawner.swapColors();
                                    selectedButton = waterSpawner;
                                    outlineColor = Color.CYAN;
                            }
                    }
                    else if(lavaSpawner.clickedButton(mx, my)) 
                    { 
                            currentTile = LavaSpawner.class;
                            if(!lavaSpawner.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    lavaSpawner.swapColors();
                                    selectedButton = lavaSpawner;
                                    outlineColor = Color.GREEN;
                            }
                        }
                    else if(fireSpawner.clickedButton(mx, my)) 
                    { 
                            currentTile = FireSpawner.class; 
                            if(!fireSpawner.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    fireSpawner.swapColors();
                                    selectedButton = fireSpawner;
                                    outlineColor = Color.MAGENTA;
                            }
                    }
                    else if(ashSpawner.clickedButton(mx, my)) 
                    { 
                            currentTile = AshSpawner.class; 
                            if(!ashSpawner.equals(selectedButton))
                            {
                                    selectedButton.swapColors();
                                    ashSpawner.swapColors();
                                    selectedButton = ashSpawner;
                                    outlineColor = Color.GRAY;
                            }
                    }
            }
            else if(selectedMenu == 3)
            {
                if(exitGameButton.clickedButton(mx, my)){ System.exit(0); }
                else if(smallSquareDrawSize.clickedButton(mx, my)){ ParticulateGame.setDrawSize(1); }
                else if(mediumSquareDrawSize.clickedButton(mx, my)){ParticulateGame.setDrawSize(10); }
                else if(largeSquareDrawSize.clickedButton(mx, my)){ ParticulateGame.setDrawSize(50); }
                else if(massiveSquareDrawSize.clickedButton(mx, my)){ ParticulateGame.setDrawSize(100); }
                //else if(savePlayAreaButton.clickedButton(mx, my)) { ParticulateGame.saveGridToTextFile(); }

            }

        ParticulateGame.setCurrentTile(currentTile);
        ParticulateGame.setOutlineColor(outlineColor); 
    }

    public int getX()
    {
        return sideMenuX;
    }
}
