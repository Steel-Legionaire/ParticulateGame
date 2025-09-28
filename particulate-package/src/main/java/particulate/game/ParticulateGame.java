package particulate.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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

import particulate.game.Gases.Fire;
import particulate.game.Liquids.Lava;
import particulate.game.Liquids.Water;
import particulate.game.Solids.MoveableSolids.Sand;
import particulate.game.Solids.StaticSolids.Bedrock;
import particulate.game.Solids.StaticSolids.Stone;
import particulate.game.Solids.StaticSolids.TNT;
import particulate.game.Solids.StaticSolids.Wood;
import particulate.game.Solids.StaticSolids.Spawners.LavaSpawner;
import particulate.game.Solids.StaticSolids.Spawners.SandSpawner;
import particulate.game.Solids.StaticSolids.Spawners.WaterSpawner;
import particulate.game.ui.Menu;

public class ParticulateGame extends Game  {
    
        public static final String TITLE = "Particulate Refreshed";
        public static final int SCREEN_WIDTH = 1815;
        public static final int SCREEN_HEIGHT = 1037;

        public static int playAreaWidth = 1500;
        public int playAreaHeight = 1000;
        
        int outlinedTileX = 0;
        int outlinedTileY = 0;

        public static int tileSize = 5;

        public static Tile[][] grid;

        public static Class<?> currentTile = Sand.class;    
        
        boolean isPaused = false;
        boolean dropMenuActive = true;
        boolean floorDropped = false;

        Tile[] fullFloor;
        Tile[] emptyFloor;
        
        boolean eraseMode = false;

        
        static int drawSize = 1;
        


        boolean mouseHeld = false;



        int selectedMenu = 0;

        static int tempMsgX = (SCREEN_WIDTH/2) - 80;
        static int tempMsgY = 50;
        static int tempMsgDurationMilis = 3000;

        static Color outlineColor = Color.YELLOW;

        Menu menu = new Menu();

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

                // Leftover code to create a large square of walls
                //for(int i=0; i<50; i++)
                //{
                //        for(int k=0; k<50; k++)
                //        {
                //                grid[50+i][50+k] = new Wall(50+k, 50+i);
                //        }
                //}

                /*ArrayList<int[]> test = traceThroughGrid(299, 199, 0, 0);
                System.out.println(test.size());
                for(int[] i : test)
                {

                        System.out.println("("+i[0]+","+i[1]+")");
                        createTile(i[0], i[1], Stone.class);
                        
                }*/

        }
        

        public void update() 
        { 
                if(!isPaused)
                { 
                        for(int r=grid.length-1;r>=0;r--)
                        {
                                
                                for(int c=0;c<grid[r].length;c++)
                                {
                                        Tile t = grid[r][c];

                                        if(t != null)
                                        {
                                                t.action();
                                                t.move();
                                        }
                                }
                        }      
                }


                if(mouseHeld && outlinedTileX < grid[0].length)
                {
                        createTile(outlinedTileX, outlinedTileY, currentTile);
                }
        }
        
        public void draw(Graphics pen)
        {    
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
                                if(grid[r][c] != null)
                                {
                                        grid[r][c].draw(pen);
                                }
                        }
                }

                // Draw in outline for the brush
                pen.setColor(outlineColor);
                pen.drawRect((outlinedTileX - ((int)(drawSize / 2))) * tileSize, (outlinedTileY - ((int)(drawSize / 2))) * tileSize, tileSize * drawSize, tileSize * drawSize );


                menu.draw(pen);
                
                
        }       


        public ArrayList<int[]> traceThroughGrid(int startX, int startY, int endX, int endY)
        {
                // slope = y2 - y2 / x2 - x1
                ArrayList<int[]> allCoords = new ArrayList<int[]>(); 

                int y;
                double slope = (double)(endY - startY)  / (double)(endX - startX);

                if(startX > endX)
                {
                        // endX is behind startX
                        for( int x = startX; x >= endX; x--)
                        {
                                y = Math.abs((int) Math.round(slope * x));
                                allCoords.add(new int[]{x, y});
                        }
                }
                else if(startX < endX)
                {
                        // endX is in front of startX
                        for( int x = startX; x <= endX; x++)
                        {
                                y = Math.abs((int) Math.round(slope * x));
                                allCoords.add(new int[]{x, y});
                        }
                }
                else
                {
                        // in same spot
                        
                }

                
                return allCoords;
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
                        if(clazz.equals(Eraser.class) && y>=1 && x>=1 && y <= grid.length-1 && x <= grid[y].length-1)
                        {

                                if(grid[y][x] != null && grid[y][x].isDestructable)
                                {

                                        grid[y][x] = null;
                                }

                                return;
                        }

                        if(x >= 1 && y >= 1 && y <= grid.length-1 && x <= grid[y].length-1 && grid[y][x] == null)
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
                }                                
                else 
                {
                        if(drawSize % 2 == 1)
                        {
                                for(int r = y - ((int)(drawSize / 2)); r < y + ((int)(drawSize / 2)) + 1; r++)
                                {
                                        if(r<1){ continue; }
                                        else if(r>=grid.length-1){ break; }

                                        for(int c = x - ((int)(drawSize / 2)); c < x+((int)(drawSize / 2)) + 1; c++)
                                        {
                                                if(c < 1){continue;} 
                                                else if(c >= grid[0].length-1){ break; }

                                                
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
                        else
                        {
                                for(int r = y - ((int)(drawSize / 2)); r < y + ((int)(drawSize / 2)); r++)
                                {
                                        if(r<1){ continue; }
                                        else if(r>=grid.length-1){ break; }

                                        for(int c = x - ((int)(drawSize / 2)); c < x+((int)(drawSize / 2)); c++)
                                        {
                                                if(c < 1){continue;} 
                                                else if(c >= grid[0].length-1){ break; }

                                                
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

        public static void saveGridToTextFile()
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
                        showTemporaryMessage(Game.frame, "Saved Grid Succesfully! Saved to: "+filePath, tempMsgX, tempMsgY, tempMsgDurationMilis);
                } catch (IOException e) {
                        e.printStackTrace();
                        showTemporaryMessage(Game.frame, "Error LoadingGrid!", tempMsgX, tempMsgY, tempMsgDurationMilis);
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

    public static void setOutlineColor(Color c){ outlineColor = c; }
    public static Color getOutlineColor(){ return outlineColor; }

    public static void setCurrentTile(Class<?> t){ currentTile = t; }
    public static Class<?> getCurrentTile(){ return currentTile; }

    public static void setDrawSize(int ds){ drawSize = ds;}

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

                
                if(mx >= menu.getX())
                {

                        menu.clicked(mx, my);
                }

        }

    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        int notches = mwe.getWheelRotation(); // Positive for down, negative for up
            if (notches < 0) {
                drawSize++;
            } else {
                if(drawSize > 1)
                {
                        drawSize--;
                }
            }
    }

    @Override
    public void mouseDragged(MouseEvent me) 
    {
        int mxg = (me.getX() / tileSize )- 2;
        int myg = (me.getY() / tileSize )- 7;
        outlinedTileX = mxg;
        outlinedTileY = myg;

    }

    @Override
    public void mousePressed(MouseEvent me) 
    {
        mouseHeld = true;
        
        int mxg = (me.getX() / tileSize )- 2;
        int myg = (me.getY() / tileSize )- 7;

        int mx = me.getX();
        int my = me.getX();

        outlinedTileX = mxg;
        outlinedTileY = myg;

        //ArrayList<int[]> path = traceThroughGrid(mxLastFrame, myLastFrame, mx, my);
        if(!(me.isShiftDown()))
        {
                
                try {
                        
                        if(mxg < grid[0].length)
                        {
                                createTile(mxg, myg, currentTile); 
                                //for(int[] pos : path)
                                //{
                                //        createTile((pos[0] / tileSize) - 2, (pos[1] / tileSize) - 2, currentTile); 
                                //}
                                    
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