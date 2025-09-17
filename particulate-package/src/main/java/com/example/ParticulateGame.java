package com.example;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ParticulateGame extends Game  {
    
        public static final String TITLE = "Particulate Refreshed";
        public static final int SCREEN_WIDTH = 1815;
        public static final int SCREEN_HEIGHT = 1037;

        public int playAreaWidth = 1500;
        public int playAreaHeight = 1000;

        int sideMenuX = playAreaWidth;

        int outlinedTileX = 0;
        int outlinedTileY = 0;

        public static int tileSize = 5;

        public static Tile[][] grid;

        public Class<?> currentTile = Sand.class;    
        
        boolean isPaused = false;

        boolean floorDropped = false;

        Tile[] fullFloor;
        Tile[] emptyFloor;
        
        boolean eraseMode = false;

        String[] controlsList = new String[]{ "r: Reset Play Area", "e: Select Eraser", "Space: Pause Simulation", "Enter: Drop Floor", "1: Sand", "2: Water", "3: Lava", "4: Fire", "5: Wall", "6: Wood", "7: Tnt", "8: Sand Spawner", "9: Water Spawner", "0: Lava Spawner"};

        int drawSize = 1;
        
        int buttonWidth = 200;
        int buttonHeight = 50;

        int buttonX = sideMenuX + 60;

        boolean mouseHeld = false;

        // Define Block Buttons
        Button wallButton = new Button(buttonX, 75, buttonWidth, buttonHeight, "Wall");
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

        // Define Options button and menu buttons
        Button optionsButton = new Button(sideMenuX + 135, 930, 50, buttonHeight, "\u2699" );
        Button exitGameButton = new Button(buttonX, 75, buttonWidth, buttonHeight, "Exit Game");

        // Define page switcher buttons
        Button pageLeftButton = new Button(sideMenuX + 60, 930, 50, buttonHeight, "\u2190");
        Button pageRightButton = new Button(sideMenuX + 210, 930, 50, buttonHeight, "\u2192");
        
        // Define eraser button
        Button eraserButton = new Button(buttonX, 855, buttonWidth, buttonHeight, "Eraser"); 

        // Define square draw size buttons
        Button smallSquareDrawSize = new Button(buttonX, 575, buttonWidth, buttonHeight, "Small");
        Button mediumSquareDrawSize = new Button(buttonX, 635, buttonWidth, buttonHeight, "Medium");
        Button largeSquareDrawSize = new Button(buttonX, 695, buttonWidth, buttonHeight, "Large");
        Button massiveSquareDrawSize = new Button(buttonX, 755, buttonWidth, buttonHeight, "Massive");

        Button[] particlesButtons = new Button[]{ sandButton, waterButton, lavaButton, fireButton, ashButton};
        Button[] blockMenu = new Button[]{ wallButton, bedrockButton, obsidianButton, woodButton, staticTntButton, fallingTntButton};
        Button[] optionsMenu = new Button[]{ exitGameButton};
        Button[] spawnerMenu = new Button[]{ sandSpawner, waterSpawner, lavaSpawner, fireSpawner };

        Button[][] menus = new Button[4][10];

        Button[] squareDrawSizeButtons = new Button[]{smallSquareDrawSize, mediumSquareDrawSize, largeSquareDrawSize, massiveSquareDrawSize};

        int selectedMenu = 0;

        
                
        public ParticulateGame() 
        {
                grid = new Tile[playAreaHeight / tileSize][playAreaWidth / tileSize];
                setGridBoundsWalls();

                emptyFloor = new Tile[playAreaWidth/tileSize];

                fullFloor = new Tile[playAreaWidth/tileSize];

                for(int c=0; c<fullFloor.length; c++)
                {
                        fullFloor[c] = new Bedrock(c, grid.length-1);
                }

                menus[0] = particlesButtons;
                menus[1] = blockMenu;
                menus[2] = spawnerMenu;
                menus[3] = optionsMenu;
                


                // Leftover code to create a large square of walls
                //for(int i=0; i<50; i++)
                //{
                //        for(int k=0; k<50; k++)
                //        {
                //                grid[50+i][50+k] = new Wall(50+k, 50+i);
                //        }
                //}
                
        }
        

        public void update() 
        {
                if(isPaused){return;}

                if(mouseHeld)
                {
                        createTile(outlinedTileX, outlinedTileY, currentTile);
                }

                for(int r=0;r<grid.length;r++)
                {
                        for(int c=0;c<grid[r].length;c++)
                        {
                                if(grid[r][c] != null)
                                {
                                        grid[r][c].action();
                                        if(grid[r][c] != null)
                                        {
                                                grid[r][c].move();
                                        }
                                        
                                        
                                }
                        }
                }

                /*for(int r=grid.length-1;r>=0;r--)
                {
                        for(int c=0;c<grid[r].length;c++)
                        {
                                if(grid[r][c] != null)
                                {
                                        grid[r][c].action();
                                        if(grid[r][c] != null)
                                        {
                                                grid[r][c].move();
                                        }
                                        
                                        
                                }
                        }
                }*/
        }
        
        public void draw(Graphics pen)
        {    

                pen.setColor(Color.DARK_GRAY);
                pen.fillRect(playAreaWidth - tileSize, 0, SCREEN_WIDTH - playAreaWidth + tileSize, SCREEN_HEIGHT);
                
                pageLeftButton.draw(pen);
                pageRightButton.draw(pen);
                optionsButton.draw(pen);
                eraserButton.draw(pen);

                for(int r=0;r<grid.length;r++)
                {
                        for(int c=0;c<grid[r].length;c++)
                        {
                                if(grid[r][c] != null)
                                {
                                        grid[r][c].draw(pen);
                                }
                        }
                }


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

                                                pen.drawString("Square Draw Sizes", sideMenuX + 70, 550);

                                                for(Button b : squareDrawSizeButtons)
                                                {
                                                        b.draw(pen);
                                                }


                                        }

                                }

                        }
                }
                
                
                pen.drawRect(outlinedTileX * tileSize, outlinedTileY * tileSize, tileSize, tileSize);
        }       

        public void setGridBoundsWalls()
        {
                for(int r=0;r<grid.length;r++)
                {
                        for(int c=0;c<grid[r].length;c++)
                        {
                                if(c==0 || r==grid.length-1 || r==0 || c==grid[r].length-1)
                                {
                                        grid[r][c] = new Bedrock(c, r);
                                }
                        }
                }
        }
        
        public void createTile(int x, int y, Class<?> clazz)
        {
                if(drawSize == 1)
                {
                        if(clazz.equals(Eraser.class) && y < grid.length)
                        {

                                if(grid[y][x] != null && grid[y][x].isDestructable)
                                {

                                        grid[y][x] = null;
                                }

                                return;
                        }

                        if(grid[y][x] == null)
                        {
                                try {
                                        Constructor<?> argConstructor = clazz.getConstructor(int.class, int.class);
        
                                        Tile t = (Tile) argConstructor.newInstance(x, y);

                                        //System.out.println(clazz.cast(t));

                                        grid[y][x] = t;
                                        if(clazz.equals(TNT.class))
                                        {
                                                
                                        }

                                } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                        System.out.println(e);
                                }
                        }
                        else if(Eraser.class.equals(clazz))
                        {
                                grid[y][x] = null;
                        }
                }                                
                else 
                {
                        for(int r = y - ((int)(drawSize / 2)); r < y+((int)(drawSize / 2)) + 1; r++)
                        {
                                if(r<1){ continue; }
                                else if(r>=grid.length){ break; }

                                for(int c = x - ((int)(drawSize / 2)); c < x+((int)(drawSize / 2)) + 1; c++)
                                {
                                        if(c < 1 || c > grid[0].length){ break; }

                                        
                                        if(clazz.equals(Eraser.class))
                                        {

                                                if(grid[r][c] != null && grid[r][c].isDestructable)
                                                {

                                                        grid[r][c] = null;
                                                }
                                        }
                                        else
                                        {
                                                if(grid[r][c] == null)
                                                {
                                                        try {
                                                                Constructor<?> argConstructor = clazz.getConstructor(int.class, int.class);
                                
                                                                Tile t = (Tile) argConstructor.newInstance(c, r);
                                                                grid[r][c] = t;

                                                        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                                                System.out.println(e);
                                                        }
                                                }
                                        }

                                
                                }
                        }
                }
        }

        public void resetGrid()
        {
                grid = new Tile[playAreaHeight / tileSize][playAreaWidth / tileSize];
                setGridBoundsWalls(); 
        }

        public void dropFloor()
        {
                if(floorDropped)
                {
                        grid[grid.length-1] = fullFloor;
                        floorDropped = false;
                }
                else
                {
                        grid[grid.length-1] = emptyFloor;
                        floorDropped = true;

                }
                
        }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) 
    {
        if(ke.getKeyChar() == '1')      { currentTile = Sand.class; }
        else if (ke.getKeyChar() == '2'){ currentTile = Water.class; }
        else if (ke.getKeyChar() == '3'){ currentTile = Lava.class; }
        else if (ke.getKeyChar() == '4'){ currentTile = Fire.class; }
        else if (ke.getKeyChar() == '5'){ currentTile = Wall.class; }
        else if (ke.getKeyChar() == '6'){ currentTile = Wood.class; }
        else if (ke.getKeyChar() == '7'){ currentTile = TNT.class; }
        else if (ke.getKeyChar() == '8'){ currentTile = SandSpawner.class; }
        else if (ke.getKeyChar() == '9'){ currentTile = WaterSpawner.class; }
        else if (ke.getKeyChar() == '0'){ currentTile = LavaSpawner.class; }

        else if(ke.getKeyChar() == 'r'){ resetGrid(); }
        else if(ke.getKeyChar() == ' '){ isPaused = !isPaused; }
        else if(ke.getKeyCode() == 10){ dropFloor(); }

        else if(ke.getKeyChar() == 'e'){  currentTile = Eraser.class;}

    }

    @Override
    public void keyReleased(KeyEvent ke) {}

    @Override
    public void mouseClicked(MouseEvent me) 
    { 
        int mxg = (me.getX() / tileSize) - 2;
        int myg = (me.getY() / tileSize) - 7; 

        int mx = me.getX();
        int my = me.getY();

        if(me.isShiftDown())
        {
                if(grid[myg][mxg] instanceof TNT)
                {
                        ((TNT)grid[myg][mxg]).explode();
                }
        }
        else
        {
                if(mx < sideMenuX + tileSize)
                {
                        createTile(mxg, myg, currentTile); 

                }
                else if(mx >= sideMenuX)
                {
                        
                        if(pageLeftButton.clickedButton(mx, my))
                        { 
                                if(selectedMenu > 0)
                                {
                                        selectedMenu--;
                                }
                        }
                        else if(pageRightButton.clickedButton(mx, my))
                        { 
                                if(selectedMenu < 2)
                                {
                                        selectedMenu++;
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
                        else if(eraserButton.clickedButton(mx, my)) { currentTile = Eraser.class; }

                        if(selectedMenu == 0)
                        {
                                if(sandButton.clickedButton(mx, my)){ currentTile = Sand.class; }
                                else if(waterButton.clickedButton(mx, my)) { currentTile = Water.class; }
                                else if(lavaButton.clickedButton(mx, my)) { currentTile = Lava.class; }
                                else if(fireButton.clickedButton(mx, my)) { currentTile = Fire.class; }
                                else if(ashButton.clickedButton(mx, my)) { currentTile = Ash.class; }
                        }
                        else if(selectedMenu == 1)
                        {
                                if( wallButton.clickedButton(mx, my)) { currentTile = Wall.class; }
                                else if(bedrockButton.clickedButton(mx, my)) { currentTile = Bedrock.class; }
                                else if(obsidianButton.clickedButton(mx, my)) { currentTile = Obsidian.class; }
                                else if(woodButton.clickedButton(mx, my)) { currentTile = Wood.class; }
                                else if(staticTntButton.clickedButton(mx, my)) {currentTile = TNT.class; }
                                else if(fallingTntButton.clickedButton(mx, my)) {currentTile = TNT.class; }
                        }
                        else if(selectedMenu == 2)
                        {
                                if(sandSpawner.clickedButton(mx, my)) { currentTile = SandSpawner.class; }
                                else if(waterSpawner.clickedButton(mx, my)) { currentTile = WaterSpawner.class; }
                                else if(lavaSpawner.clickedButton(mx, my)) { currentTile = LavaSpawner.class; }
                                else if(fireSpawner.clickedButton(mx, my)) { currentTile = FireSpawner.class; }
                        }
                        else if(selectedMenu == 3)
                        {
                                if(exitGameButton.clickedButton(mx, my)){ System.exit(0); }
                                else if(smallSquareDrawSize.clickedButton(mx, my)){ drawSize = 1; }
                                else if(mediumSquareDrawSize.clickedButton(mx, my)){ drawSize = 11; }
                                else if(largeSquareDrawSize.clickedButton(mx, my)){ drawSize = 51; }
                                else if(massiveSquareDrawSize.clickedButton(mx, my)){ drawSize = 101; }
                        }
                }

        }

    }

    @Override
    public void mouseDragged(MouseEvent me) 
    {
        mouseHeld = false;

        int mxg = (me.getX() / tileSize )- 2;
        int myg = (me.getY() / tileSize )- 7;

        int mx = me.getX();

        outlinedTileX = mxg;
        outlinedTileY = myg;

        if(!(me.isShiftDown()))
        {
                
                try {

                        if(mx < sideMenuX + tileSize)
                        {
                                createTile(mxg, myg, currentTile);     
                        }
                           
                        
                } catch (ArrayIndexOutOfBoundsException e) {

                }
        }
        else
        {
                try {
                        if(grid[myg][mxg] instanceof TNT)
                        {
                                ((TNT)grid[myg][mxg]).explode();  
                        }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
        }



    }

    @Override
    public void mousePressed(MouseEvent me) 
    {
        if(me.getX() < sideMenuX)
        {
                mouseHeld = true;
        }
        
    }
    @Override
    public void mouseReleased(MouseEvent me) 
    {
        mouseHeld = false;
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mouseMoved(MouseEvent me) 
    {
        outlinedTileX = (me.getX() / tileSize) - 2;
        outlinedTileY = (me.getY() / tileSize) - 7;
    }
        
    //Launches the Game
    public static void main(String[] args) { new ParticulateGame().start(TITLE, SCREEN_WIDTH,SCREEN_HEIGHT); }



}