
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ParticulateGame extends Game  {
    
        public static final String TITLE = "Particulate Refreshed";
        public static final int SCREEN_WIDTH = 1815;
        public static final int SCREEN_HEIGHT = 1038;

        public int playAreaWidth = 1500;
        public int playAreaHeight = 1000;

        public static int tileSize = 10;

        public static Tile[][] grid;

        public Class<?> currentTile = Sand.class;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
        
                
        public ParticulateGame() 
        {
                grid = new Tile[playAreaHeight / tileSize][playAreaWidth / tileSize];
                setGridBoundsWalls();
        }
        

        public void update() 
        {
                for(int r=0;r<grid.length;r++)
                {
                        for(int c=0;c<grid[r].length;c++)
                        {
                                if(grid[r][c] != null)
                                {
                                        grid[r][c].action();
                                        grid[r][c].move();
                                        
                                }
                        }
                }
        }
        
        public void draw(Graphics pen)
        {    
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

                pen.setColor(Color.WHITE);

                int sx = playAreaWidth + ((SCREEN_WIDTH - playAreaWidth) / 2);
                int sy = SCREEN_HEIGHT / 2;

                pen.drawString("1: Sand", sx, sy);
                pen.drawString("2: Water", sx, sy+10);
                pen.drawString("3: Lava", sx, sy+20);
                pen.drawString("4: Fire", sx, sy+30);
                pen.drawString("5: Wall", sx, sy+40);
                pen.drawString("6: Wood", sx, sy+50);
                pen.drawString("7: TNT", sx, sy+60);
                pen.drawString("8: SandSpawner", sx, sy+70);
                pen.drawString("9: WaterSpawner", sx, sy+80);
                pen.drawString("0: LavaSpawner", sx, sy+90);
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
                if(grid[y][x] == null)
                {
                        try {
                                Constructor<?> argConstructor = clazz.getConstructor(int.class, int.class);
  
                                Tile t = (Tile) argConstructor.newInstance(x, y);

                                //System.out.println(clazz.cast(t));

                                grid[y][x] = t;

                        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                System.out.println(e);
                        }
                }
        }

        public void resetGrid()
        {
                grid = new Tile[playAreaHeight / tileSize][playAreaWidth / tileSize];
                setGridBoundsWalls(); 
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

        else if(ke.getKeyChar() == 'r'){ 
                resetGrid();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {}

    @Override
    public void mouseClicked(MouseEvent me) 
    { 
        int mx = me.getX() / tileSize;
        int my = me.getY() / tileSize; 
        
        createTile(mx, my, currentTile);
    }

    @Override
    public void mouseDragged(MouseEvent me) 
    {
        int mx = me.getX() / tileSize;
        int my = me.getY() / tileSize;

        try {
                if(grid[my][mx] == null)
                {
                        createTile(mx, my, currentTile);        
                }
        } catch (ArrayIndexOutOfBoundsException e) {

        }


    }

    @Override
    public void mousePressed(MouseEvent me) {}
    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mouseMoved(MouseEvent e) {

    }
        
    //Launches the Game
    public static void main(String[] args) { new ParticulateGame().start(TITLE, SCREEN_WIDTH,SCREEN_HEIGHT); }



}