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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

import particulate.game.Gases.Fire;
import particulate.game.Liquids.Lava;
import particulate.game.Liquids.Water;
import particulate.game.Solids.MoveableSolids.Sand;
import particulate.game.Solids.StaticSolids.Stone;
import particulate.game.Solids.StaticSolids.TNT;
import particulate.game.Solids.StaticSolids.Wood;
import particulate.game.Solids.StaticSolids.Spawners.LavaSpawner;
import particulate.game.Solids.StaticSolids.Spawners.SandSpawner;
import particulate.game.Solids.StaticSolids.Spawners.WaterSpawner;
import particulate.game.ui.Menu;

public class ParticulateGame extends Game  {
    
        public static final String TITLE = "Particulate Refreshed";
        public static final int SCREEN_WIDTH = 1817;
        public static final int SCREEN_HEIGHT = 1040;

        public static int playAreaWidth = 1800;
        public static int playAreaHeight = 800;
        
        int outlinedTileX = 0;
        int outlinedTileY = 0;

        public static int tileSize = 5;

        public static CellularMatrix matrix;
        //public int matrixHeight;
        //public int matrixWidth;
        

        public static Class<?> currentTile = Sand.class;    
        
        boolean isPaused = false;
        boolean dropMenuActive = true;
        boolean floorDropped = false;

        
        
        boolean eraseMode = false;
        
        static int drawSize = 1;

        boolean mouseHeld = false;

        int selectedMenu = 0;

        static int tempMsgX = (SCREEN_WIDTH/2) - 80;
        static int tempMsgY = 50;
        static int tempMsgDurationMilis = 3000;

        static Color outlineColor = Color.YELLOW;

        boolean frameEven = false;

        Menu menu = new Menu();

        public ParticulateGame() 
        {
                matrix = new CellularMatrix(playAreaWidth / tileSize, playAreaHeight / tileSize);
                matrix.setGridBoundsWalls();

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
                        if(frameEven)
                        {
                                for(int r = matrix.getRowBounds(); r >= 0; r--)
                                {
                                        for(int c = 0; c < matrix.getCollumnBounds(); c++)
                                        {
                                                Tile t = matrix.getTile(c, r);
        
                                                if(t != null)
                                                {
                                                        t.action();
                                                        t.move();
                                                }
                                        }
                                }   
                        }
                        else
                        {
                                for(int r = matrix.getRowBounds(); r >= 0; r--)
                                {
                                        for(int c = matrix.getCollumnBounds(); c >0; c--)
                                        {
                                                Tile t = matrix.getTile(c, r);
        
                                                if(t != null)
                                                {
                                                        t.action();
                                                        t.move();
                                                }
                                        }
                                }   
                        }
   
                }

                if(mouseHeld && outlinedTileY < menu.getY())
                {
                        matrix.createTile(outlinedTileX, outlinedTileY, currentTile, drawSize);
                }

                frameEven = !frameEven;
        }
        
        public void draw(Graphics pen)
        {    
                menu.draw(pen);

                matrix.drawAllTiles(pen, tileSize);

                // Draw in outline for the brush
                pen.setColor(outlineColor);
                pen.drawRect((outlinedTileX - ((int)(drawSize / 2))) * tileSize, (outlinedTileY - ((int)(drawSize / 2))) * tileSize, tileSize * drawSize, tileSize * drawSize );

                

                // Draw info on position and tile
                Tile hoveredTile = matrix.withinBounds(outlinedTileX, outlinedTileY) ? matrix.getTileAtLocation(outlinedTileX, outlinedTileY) : null;

                if(matrix.withinBounds(outlinedTileX, outlinedTileY) && hoveredTile != null)
                {
                        String[] splitString = hoveredTile.getClass().getName().split("\\.");

                        menu.drawMatrixInfo(pen, splitString[splitString.length-1], outlinedTileX, outlinedTileY, drawSize);
                }
                else
                {
                        menu.drawMatrixInfo(pen,"", outlinedTileX, outlinedTileY, drawSize);
                }
                

                /*pen.setColor(Color.MAGENTA);
                if(matrix.withinBounds(outlinedTileX, outlinedTileY))
                {
                        pen.drawString(matrix.getTile(outlinedTileX, outlinedTileY)+"", 100, 50);
                }
                else
                {
                        pen.drawString("null", 100, 50);
                }*/
        }       

        public static void saveGridToTextFile()
        {
                String filePath = getUniqueFileName("savedPlayArea", "txt");

                StringBuilder sb = new StringBuilder();

                for(int r=0; r < matrix.getRowBounds()+1; r++)
                {
                        for(int c=0; c < matrix.getCollumnBounds()+1; c++)
                        { 
                                // get the class at the curret position and chop off the @segf98shg at the end
                                Tile t = matrix.getTile(c,r);

                                if(t == null){ continue; }

                                String classStr = (t.toString().split("@"))[0];

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
    
        public void setGridToReadInTextFile(String filecontent)
        {
                Tile[][] importedGrid = new Tile[matrix.getRowBounds()+1][matrix.getCollumnBounds()+1];

                String[] listOfEveryTile = filecontent.split("\n");
                for(String s : listOfEveryTile)
                {
                        String[] classAndPositions = s.split(",");

                        Class<?> clazz;
                        try 
                        {
                                clazz = Class.forName(classAndPositions[0]);
                                Constructor<?> argConstructor = clazz.getConstructor(int.class, int.class);

                                int y = Integer.parseInt(classAndPositions[1]);
                                int x = Integer.parseInt(classAndPositions[2]);
                
                                Tile t = (Tile) argConstructor.newInstance(x, y);
                                importedGrid[y][x] = t;

                        } 
                        catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
                        {
                                e.printStackTrace();
                        }


                }       

                matrix.setMatrix(importedGrid);

                showTemporaryMessage(Game.frame, "Loaded Grid Succesfully!", tempMsgX, tempMsgY, tempMsgDurationMilis);
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
    
        public static void setOutlineColor(Color c){ outlineColor = c; }
        public static Color getOutlineColor(){ return outlineColor; }

        public static void setCurrentTile(Class<?> t){ currentTile = t; }
        public static Class<?> getCurrentTile(){ return currentTile; }

        public static void setDrawSize(int ds){ drawSize = ds;}

        public static CellularMatrix getMatrix(){ return matrix;}
        
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

                else if(ke.getKeyChar() == 'r'){ matrix.resetGrid(); }
                else if(ke.getKeyChar() == ' '){ isPaused = !isPaused; }
                else if(ke.getKeyCode() == 10){ matrix.dropFloor(); }

                else if(ke.getKeyChar() == 'e'){  currentTile = Eraser.class;}

        }

        @Override
        public void mouseClicked(MouseEvent me) 
        { 
                int mx = me.getX();
                int my = me.getY();

                if(mx >= menu.getX())
                {
                        menu.clicked(mx, my);
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

                outlinedTileX = mxg;
                outlinedTileY = myg;

                //ArrayList<int[]> path = traceThroughGrid(mxLastFrame, myLastFrame, mx, my);
                if(!(me.isShiftDown()))
                {
                        
                        try {
                                
                                if(mxg < matrix.getCollumnBounds())
                                {
                                        matrix.createTile(mxg, myg, currentTile, drawSize); 
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
                                if(matrix.getTile(mxg, myg) instanceof TNT)
                                {
                                        ((TNT)matrix.getTile(mxg, myg)).explode();  
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
        public void keyTyped(KeyEvent ke) {}

        @Override
        public void mouseEntered(MouseEvent me) {}

        @Override
        public void mouseExited(MouseEvent me) {}

        @Override
        public void keyReleased(KeyEvent ke) {}

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