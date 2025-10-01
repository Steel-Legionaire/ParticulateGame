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

    int buttonHeight = 25;
    int sideMenuX = 0;
    int sideMenuY = 800;

    int buttonX = sideMenuX + 60;
    int buttonY = sideMenuY + 60;

    int smallButtonWidth = 75;
    int mediumButtonWidth = 100;
    int largeButtonWidth = 125;

    int smallXSeperation = smallButtonWidth + 10;
    int mediumXSeperation = mediumButtonWidth + 10;
    int largeXSeperation = largeButtonWidth + 10;
    int ySeperation = buttonHeight + 10;

    

    // Define Menu Swap Buttons
    Button selectParticlesButton = new Button(buttonX, sideMenuY+10, 200, 30, "Particles");
    Button selectBlocksButton = new Button(buttonX + (200+20), sideMenuY+10, 200, 30, "Blocks");
    Button selectSpawnersButton = new Button(buttonX + (420 + 20), sideMenuY+10, 200, 30, "Spawners");
    Button selectOptionsButton = new Button(buttonX + (640 + 20), sideMenuY+10, 200, 30, "Options");
    
    // Define eraser button
    Button eraserButton = new Button(buttonX, buttonY, smallButtonWidth, buttonHeight, "Eraser"); 
    
    // Define particle buttons
    Button sandButton = new Button(buttonX + smallXSeperation, buttonY, smallButtonWidth, buttonHeight, "Sand");
    Button waterButton = new Button(buttonX + smallXSeperation*2, buttonY, smallButtonWidth, buttonHeight, "Water");
    Button lavaButton = new Button(buttonX  + smallXSeperation*3, buttonY, smallButtonWidth, buttonHeight, "Lava");
    Button fireButton = new Button(buttonX + smallXSeperation*4, buttonY, smallButtonWidth, buttonHeight, "Fire");
    Button ashButton = new Button(buttonX + smallXSeperation*5, buttonY, smallButtonWidth, buttonHeight, "Ash");

    // Define Block Buttons
    Button stoneButton = new Button(buttonX + smallXSeperation, buttonY, smallButtonWidth, buttonHeight, "Stone");
    Button bedrockButton = new Button(buttonX + smallXSeperation*2, buttonY, smallButtonWidth, buttonHeight, "Bedrock");
    Button obsidianButton = new Button(buttonX + smallXSeperation*3, buttonY, smallButtonWidth, buttonHeight, "Obsidian");
    Button woodButton = new Button(buttonX + smallXSeperation*4, buttonY, smallButtonWidth, buttonHeight, "Wood");
    Button staticTntButton = new Button(buttonX + smallXSeperation*5, buttonY, mediumButtonWidth, buttonHeight, "Satic TNT");
    Button fallingTntButton = new Button(buttonX + smallXSeperation*6, buttonY, smallButtonWidth, buttonHeight, "Falling TNT");

    // Define Spawner Buttons
    Button sandSpawner = new Button(buttonX + largeXSeperation, buttonY, largeButtonWidth, buttonHeight, "Sand Spawner");
    Button waterSpawner = new Button(buttonX + (largeXSeperation * 2), buttonY, largeButtonWidth, buttonHeight, "Water Spawner");
    Button lavaSpawner = new Button(buttonX + largeXSeperation*3, buttonY, largeButtonWidth, buttonHeight, "Lava Spawner");
    Button fireSpawner = new Button(buttonX + largeXSeperation*4, buttonY, largeButtonWidth, buttonHeight, "Fire Spawner");
    Button ashSpawner = new Button(buttonX + largeXSeperation*5, buttonY, largeButtonWidth, buttonHeight, "Ash Spawner");

    // Define Options buttons
    Button exitGameButton = new Button(buttonX, buttonY, smallButtonWidth, buttonHeight, "Exit");
    Button savePlayAreaButton = new Button(buttonX + smallXSeperation, buttonY, smallButtonWidth, buttonHeight, "Save");
    
    // Define square draw size buttons
    Button smallSquareDrawSize = new Button(buttonX + smallXSeperation, buttonY + ySeperation, smallButtonWidth, buttonHeight, "Small");
    Button mediumSquareDrawSize = new Button(buttonX + smallXSeperation*2, buttonY + ySeperation, smallButtonWidth, buttonHeight, "Medium");
    Button largeSquareDrawSize = new Button(buttonX + smallXSeperation*3, buttonY + ySeperation, smallButtonWidth, buttonHeight, "Large");
    Button massiveSquareDrawSize = new Button(buttonX + smallXSeperation*4, buttonY + ySeperation, smallButtonWidth, buttonHeight, "Massive");
    


    Button[] particlesMenu = new Button[]{ sandButton, waterButton, lavaButton, fireButton, ashButton};
    Button[] blockMenu = new Button[]{ stoneButton, bedrockButton, obsidianButton, woodButton, staticTntButton};
    Button[] optionsMenu = new Button[]{ exitGameButton, savePlayAreaButton, smallSquareDrawSize, mediumSquareDrawSize, largeSquareDrawSize, massiveSquareDrawSize};
    Button[] spawnerMenu = new Button[]{ sandSpawner, waterSpawner, lavaSpawner, fireSpawner, ashSpawner };


    Button[] selectionMenu = new Button[]{selectParticlesButton, selectBlocksButton, selectSpawnersButton, selectOptionsButton};

    Button[][] typeMenus = new Button[4][10];

    Button[] squareDrawSizeButtons = new Button[]{smallSquareDrawSize, mediumSquareDrawSize, largeSquareDrawSize, massiveSquareDrawSize};

    String[] controlsList = new String[]{ "r: Reset Play Area", "e: Select Eraser", "Space: Pause Simulation", "s: save play area", "Drag file on screen", " to load it", "Enter: Drop Floor", "1: Sand", "2: Water", "3: Lava", "4: Fire", "5: Wall", "6: Wood", "7: Tnt"};

    private int selectedMenu = 0;

    private Button selectedButton = sandButton;
    
    Button selectedSelectionButton = selectParticlesButton;

    public Menu()
    {
        sandButton.swapColors();
        selectParticlesButton.swapColors();

        typeMenus[0] = particlesMenu;
        typeMenus[1] = blockMenu;
        typeMenus[2] = spawnerMenu;
        typeMenus[3] = optionsMenu;
    }

    public void draw(Graphics pen) 
    {
        int playAreaHeight= ParticulateGame.playAreaHeight;
        int SCREEN_WIDTH = ParticulateGame.SCREEN_WIDTH;
        int SCREEN_HEIGHT = ParticulateGame.SCREEN_HEIGHT;
        int tileSize = ParticulateGame.tileSize;

        // Draw side menu background
        pen.setColor(Color.DARK_GRAY);
        pen.fillRect(0, sideMenuY, SCREEN_WIDTH, SCREEN_HEIGHT- playAreaHeight + tileSize);

        for(Button b : selectionMenu){ b.draw(pen); }

        for(Button b : typeMenus[selectedMenu])
        {
                b.draw(pen);
        }

        if(selectedMenu != 3)
        {
                eraserButton.draw(pen);
        }
        else
        {
                pen.setColor(Color.WHITE);
                pen.drawString("BrushSizes", buttonX, buttonY+(ySeperation+18));       
        }

        /*pen.setColor(Color.WHITE);
        for(int i=0; i<controlsList.length; i++)
        {
                pen.drawString(controlsList[i], sideMenuX + 60, 150 + (i * 25));
        }*/
    }

    public void clicked(int mx, int my)
    {
        Class<?> currentTile = ParticulateGame.getCurrentTile();
        Color outlineColor = ParticulateGame.getOutlineColor(); 
        
        if(selectParticlesButton.clickedButton(mx, my))
        {
                selectedMenu = 0;
                
                if(!selectParticlesButton.equals(selectedSelectionButton))
                {
                        selectedSelectionButton.swapColors();
                        selectParticlesButton.swapColors();
                        selectedSelectionButton = selectParticlesButton;
                }
                
        }
        else if(selectBlocksButton.clickedButton(mx, my))
        {
                selectedMenu = 1;

                if(!selectBlocksButton.equals(selectedSelectionButton))
                {
                        selectedSelectionButton.swapColors();
                        selectBlocksButton.swapColors();
                        selectedSelectionButton = selectBlocksButton;
                }
        }
        else if(selectSpawnersButton.clickedButton(mx, my))
        {
                selectedMenu = 2;
                if(!selectSpawnersButton.equals(selectedSelectionButton))
                {
                        selectedSelectionButton.swapColors();
                        selectSpawnersButton.swapColors();
                        selectedSelectionButton = selectSpawnersButton;
                }
        }
        else if(selectOptionsButton.clickedButton(mx, my))
        {
                selectedMenu = 3;
                if(!selectOptionsButton.equals(selectedSelectionButton))
                {
                        selectedSelectionButton.swapColors();
                        selectOptionsButton.swapColors();
                        selectedSelectionButton = selectOptionsButton;
                }
        }
        
        if(eraserButton.clickedButton(mx, my))
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
                else if(savePlayAreaButton.clickedButton(mx, my)) { ParticulateGame.saveGridToTextFile(); }

            }

        ParticulateGame.setCurrentTile(currentTile);
        ParticulateGame.setOutlineColor(outlineColor); 
    }

    public int getX()
    {
        return sideMenuX;
    }

        public int getY()
        {
                return sideMenuY;
        }
}
