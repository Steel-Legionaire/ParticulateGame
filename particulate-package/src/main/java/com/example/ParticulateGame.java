package com.example;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

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
        boolean dropMenuActive = true;
        boolean floorDropped = false;

        Tile[] fullFloor;
        Tile[] emptyFloor;
        
        boolean eraseMode = false;

        String[] controlsList = new String[]{ "r: Reset Play Area", "e: Select Eraser", "Space: Pause Simulation", "s: save play area", "Drag file on screen", " to load it", "Enter: Drop Floor", "1: Sand", "2: Water", "3: Lava", "4: Fire", "5: Wall", "6: Wood", "7: Tnt"};

        int drawSize = 1;
        
        int buttonWidth = 200;
        int buttonHeight = 50;

        int buttonX = sideMenuX + 60;

        boolean mouseHeld = false;

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

        Button[] particlesButtons = new Button[]{ sandButton, waterButton, lavaButton, fireButton, ashButton};
        Button[] blockMenu = new Button[]{ stoneButton, bedrockButton, obsidianButton, woodButton, staticTntButton};
        Button[] optionsMenu = new Button[]{ exitGameButton, savePlayAreaButton};
        Button[] spawnerMenu = new Button[]{ sandSpawner, waterSpawner, lavaSpawner, fireSpawner };

        Button[][] menus = new Button[4][10];

        Button[] squareDrawSizeButtons = new Button[]{smallSquareDrawSize, mediumSquareDrawSize, largeSquareDrawSize, massiveSquareDrawSize};

        int selectedMenu = 0;

        int tempMsgX = (SCREEN_WIDTH/2) - 80;
        int tempMsgY = 50;
        int tempMsgDurationMilis = 3000;

        ArrayList<ArrayList<Chunk>> chunks;
                
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
                

                chunks = generateChunks();
                System.out.println(chunks.size());

                /*for(ArrayList<Chunk> chunkArr: chunks)
                {
                        for(Chunk c : chunkArr)
                        {
                                System.out.println("topLeft: "+c.getTopLeft()[0]+", "+c.getTopLeft()[1]+" bottomRight: "+c.getBottomRight()[0]+", "+c.getBottomRight()[1]);
                        }
                }*/

                System.out.println(getChunkForCoordinates(0, 0).getShouldStep());

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

                for(ArrayList<Chunk> r : chunks)
                {
                        for( Chunk c : r)
                        {
                                c.shiftShouldStepAndReset();
                        }
                }

                if(mouseHeld)
                {
                        createTile(outlinedTileX, outlinedTileY, currentTile);
                }

                for(int r=grid.length-1;r>=0;r--)
                {
                        for(int c=0;c<grid[r].length;c++)
                        {
                                if(grid[r][c] != null)
                                {
                                        
                                        if(grid[r][c] != null && getChunkForCoordinates(c, r).getShouldStep())
                                        {
                                                grid[r][c].action();
                                                Tile t = grid[r][c];
                                                t.move();
                                                if(t.updatedThisFrame)
                                                {
                                                        // this means the tile has succesfully moved this frame
                                                        // if it has then we are to wake the chunk it is in and any potential neighbors

                                                        System.out.println("HAS MOVED THIS FRAME");
                                                        reportToChunkActive(t);
                                                }
                                        }
                                        
                                        
                                }
                        }
                }
        }
        
        public void draw(Graphics pen)
        {    

                // Draw side menu background
                pen.setColor(Color.DARK_GRAY);
                pen.fillRect(playAreaWidth - tileSize, 0, SCREEN_WIDTH - playAreaWidth + tileSize, SCREEN_HEIGHT);
                
                // Side menu buttons 
                pageLeftButton.draw(pen);
                pageRightButton.draw(pen);
                optionsButton.draw(pen);
                eraserButton.draw(pen);

                // Group tiles by color
                Map<Color, List<Tile>> tileBuckets = new HashMap<>();

                for(int r=0; r< grid.length; r++)
                {
                        for(int c=0; c<grid[r].length; c++)
                        {
                                Tile t = grid[r][c];

                                if(t != null)
                                {
                                        // Add tile to correct color group
                                        tileBuckets
                                                .computeIfAbsent(t.color, k -> new ArrayList<>())
                                                .add(t);
                                }
                        }
                }

                // Draw all tiles, grouped by color 
                for (Map.Entry<Color, List<Tile>> entry : tileBuckets.entrySet())
                {
                        Color color = entry.getKey();
                        List<Tile> tiles = entry.getValue();

                        pen.setColor(color);

                        for(Tile t : tiles)
                        {
                                pen.fillRect(t.x * tileSize, t.y * tileSize, tileSize, tileSize);
                        }
                }

                for(int r=0;r<grid.length;r++)
                {
                        for(int c=0;c<grid[r].length;c++)
                        {
                                if(getChunkForCoordinates(c, r).getShouldStep())
                                {       
                                        pen.setColor(Color.RED);
                                        Chunk chunk = getChunkForCoordinates(c,r);

                                        pen.drawRect(chunk.getTopLeft()[0], chunk.getTopLeft()[1], Chunk.size, Chunk.size);
                                }

                                if(grid[r][c] != null)
                                {       
                                        
                                        
                                        grid[r][c].draw(pen);
                                }
                        }
                }
                //Chunk chunk = getChunkForCoordinates(40, 40);
                //System.out.println("topLeft: "+chunk.getTopLeft()[0]+", "+chunk.getTopLeft()[1]+" bottomRight: "+chunk.getBottomRight()[0]+", "+chunk.getBottomRight()[1]);


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
                                        if(c < 1){continue;} 
                                        else if(c >= grid[0].length){ break; }

                                        
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

        public void saveGridToTextFile()
        {
                String filePath = getUniqueFileName("savedPlayArea", "txt");

                StringBuilder sb = new StringBuilder();

                for(int r=0; r<grid.length; r++)
                {
                        for(int c=0; c<grid[r].length; c++)
                        {
                                // get the class at the curret position and chop off the @segf98shg at the end
                                if(grid[r][c] == null){ continue; }

                                String classStr = (grid[r][c].toString().split("@"))[0];

                                sb.append(classStr+","+r+","+c+"\n");
                                
                        }
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                        writer.write(sb.toString());
                        showTemporaryMessage(super.frame, "Saved Grid Succesfully! Saved to: "+filePath, tempMsgX, tempMsgY, tempMsgDurationMilis);
                } catch (IOException e) {
                        e.printStackTrace();
                        showTemporaryMessage(super.frame, "Error LoadingGrid!", tempMsgX, tempMsgY, tempMsgDurationMilis);
                }
                
        }
    
        public void setGridToReadInTextFile(String filecontent) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
        {
                Tile[][] importedGrid = new Tile[playAreaHeight / tileSize][playAreaWidth / tileSize];

                String[] listOfEveryTile = filecontent.split("\n");
                for(String s : listOfEveryTile)
                {
                        String[] classAndPositions = s.split(",");

                        Class<?> clazz = Class.forName(classAndPositions[0]);

                        Constructor<?> argConstructor = clazz.getConstructor(int.class, int.class);

                        int y = Integer.parseInt(classAndPositions[1]);
                        int x = Integer.parseInt(classAndPositions[2]);
        
                        Tile t = (Tile) argConstructor.newInstance(x, y);
                        importedGrid[y][x] = t;
                }       

                grid = importedGrid;

                showTemporaryMessage(super.frame, "Loaded Grid Succesfully!", tempMsgX, tempMsgY, tempMsgDurationMilis);
        }

        public static void showTemporaryMessage(JFrame frame, String message, int x, int y, int durationMillis) {
                // Create the message label
                JLabel tempMessage = new JLabel(message);
                tempMessage.setOpaque(true);
                tempMessage.setBackground(new Color(0, 0, 0, 170)); // semi-transparent black
                tempMessage.setForeground(Color.WHITE);
                tempMessage.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                tempMessage.setSize(tempMessage.getPreferredSize());
                tempMessage.setLocation(x, y);  // Custom position

                // Add to layered pane so it floats above other components
                JLayeredPane layeredPane = frame.getLayeredPane();
                layeredPane.add(tempMessage, JLayeredPane.PALETTE_LAYER);
                layeredPane.repaint();

                // Set timer to remove it after the specified duration
                new Timer(durationMillis, (ActionEvent e) -> {
                layeredPane.remove(tempMessage);
                layeredPane.repaint();
                }).start();
        }

    public static String getUniqueFileName(String baseName, String extension) {
        String userHome = System.getProperty("user.home");
        String desktopPath = Paths.get(userHome, "Desktop").toString();

        String fileName = baseName + "." + extension;
        File file = new File(desktopPath, fileName);

        int counter = 1;
        while (file.exists()) {
            fileName = baseName + "(" + counter + ")." + extension;
            file = new File(desktopPath, fileName);
            counter++;
        }

        return file.getAbsolutePath();
    }
   
    // following few methods also inspired heavily from DavidMcLaughin208 on github
    // see Chunk class for link
    public ArrayList<ArrayList<Chunk>> generateChunks()
    {
        ArrayList<ArrayList<Chunk>> chunks = new ArrayList<>();

        // number of rows
        // Use Math.ceil to take into account partial chunks (chunks at the edge)
        int rows = (int) Math.ceil((double) grid.length / Chunk.size);

        int columns = (int) Math.ceil((double) grid[0].length / Chunk.size );

        for(int r = 0; r < rows; r++)
        {
                // itterale through the rows add create a new array of Chunks
                chunks.add(new ArrayList<>());
                for(int c = 0; c < columns; c++)
                {
                        // get the x and y of the chunk
                        int xPos = c * Chunk.size;
                        int yPos = r * Chunk.size;
                        Chunk newChunk = new Chunk();

                        // Creat a new chunk object and add it to the current row
                        chunks.get(r).add(newChunk);

                        // Set the chunks values
                        // Use math.min to ensure it stays inside the bounds of the grid
                        newChunk.setTopLeft( Math.min(xPos, grid[0].length), Math.min(yPos, grid.length));
                        newChunk.setBottomRight( Math.min(xPos + Chunk.size, grid[0].length), Math.min(yPos + Chunk.size, grid.length) );
                }
        }

        return chunks;
    }

    public void reportToChunkActive(Tile t)
    {
        reportToChunkActive(t.x, t.y);
    }

    public void reportToChunkActive(int x, int y)
    {
        // if tile is on the left edge set the chunk to the left to step next frame
        if (x % Chunk.size == 0) {
                Chunk chunk = getChunkForCoordinates(x - 1 , y);
                if (chunk != null) chunk.setShouldStepNextFrame(true);
        }
        // if tile is on the right edge set the chunk to the right to step next frame
        if (x % Chunk.size == Chunk.size - 1) {
                Chunk chunk = getChunkForCoordinates(x + 1 , y);
                if (chunk != null) chunk.setShouldStepNextFrame(true);
        }
        // if tile is on the top edge set the chunk to the top to step next frame
        if (y % Chunk.size == 0) {
                Chunk chunk = getChunkForCoordinates(x, y - 1);
                if (chunk != null) chunk.setShouldStepNextFrame(true);
        }
        // if tile is on the bottom edge set the chunk to the bottom to step next frame
        if (y % Chunk.size == Chunk.size - 1) {
                Chunk chunk = getChunkForCoordinates(x, y + 1);
                if (chunk != null) chunk.setShouldStepNextFrame(true);
        }
        // Always set the chunk we are currently in to step next frame
        getChunkForCoordinates(x, y).setShouldStepNextFrame(true);
    }

    public Chunk getChunkForCoordinates(Tile t)
    {

        return getChunkForCoordinates(t.x, t.y);
    }

    public Chunk getChunkForCoordinates(int x, int y)
    {
        int chunkY = y / Chunk.size;
        int chunkX = x / Chunk.size;
        return chunks.get(chunkY).get(chunkX);
    }
   
   
    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) 
    {
        if(ke.getKeyChar() == 's') {saveGridToTextFile(); }
        else if(ke.getKeyChar() == '1') { currentTile = Sand.class; }
        else if (ke.getKeyChar() == '2'){ currentTile = Water.class; }
        else if (ke.getKeyChar() == '3'){ currentTile = Lava.class; }
        else if (ke.getKeyChar() == '4'){ currentTile = Fire.class; }
        else if (ke.getKeyChar() == '5'){ currentTile = Stone.class; }
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
                                if( stoneButton.clickedButton(mx, my)) { currentTile = Stone.class; }
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
                                else if(savePlayAreaButton.clickedButton(mx, my)) { saveGridToTextFile(); }
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
        if(me.getX()/tileSize < grid[0].length+1)
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

    @Override
    public void drop(DropTargetDropEvent event) 
    {
        if(!dropMenuActive) { return; }
        try {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                Transferable t = event.getTransferable();
                List<File> droppedFiles = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);


                        StringBuilder sb = new StringBuilder();

                        try (BufferedReader reader = new BufferedReader(new FileReader(droppedFiles.get(0)))) {

                        String line;
                        
                        while ((line = reader.readLine()) != null) {
                                sb.append(line+"\n");
                        }
                        }

                        String fileContent = sb.toString();
                        //System.out.println("File content:\n\n" + fileContent);
                
                        setGridToReadInTextFile(fileContent);
                

        } catch (Exception ex) {
                ex.printStackTrace();
        }
    }

    // Unused but required
    @Override
    public void dragEnter(DropTargetDragEvent arg0) {}


    @Override
    public void dragExit(DropTargetEvent arg0) {}


    @Override
    public void dragOver(DropTargetDragEvent arg0) {}

    @Override
    public void dropActionChanged(DropTargetDragEvent arg0) {}

    //Launches the Game
    public static void main(String[] args) { new ParticulateGame().start(TITLE, SCREEN_WIDTH,SCREEN_HEIGHT); }

}