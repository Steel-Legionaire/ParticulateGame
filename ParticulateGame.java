
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.chrono.Era;

public class ParticulateGame extends Game  {
    
        public static final String TITLE = "Particulate Refreshed";
        public static final int SCREEN_WIDTH = 1815;
        public static final int SCREEN_HEIGHT = 1038;

        public int playAreaWidth = 1500;
        public int playAreaHeight = 1000;

        public static int tileSize = 5;

        public static Tile[][] grid;

        public Class<?> currentTile = Sand.class;    
        
        boolean isPaused = false;

        boolean floorDropped = false;

        Tile[] fullFloor;
        Tile[] emptyFloor;
        
        boolean eraseMode = false;

        Button[] particleMenu;
        Button[] blockMenu = new Button[]{ new Button(100,100, 100,100, "Test")};
        Button[] optionsMenu;
        Button[] spawnerMenu;

        Button[][] menus = new Button[4][10];

        int selectedMenu = 0;

        Button testButton = new Button(sideMenuX + 50, 50, 100, 50, "Click Me!");
                
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

                menus[0] = particleMenu;
                menus[1] = blockMenu;
                menus[2] = optionsMenu; 
                menus[3] = spawnerMenu;

                System.out.println(menus[1]);

                particleMenu = new Button[]{new Button(sideMenuX + 50, 100, 100, 500, "TEST")};
        }
        

        public void update() 
        {
                if(isPaused){return;}

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



                for(int r=0;r<menus.length;r++)
                {
                        if(menus[r] != null)
                        {
                                for(int c=0;c<menus[r].length;c++)
                                {
                                        if(menus[r][c] != null)
                                        {
                                                //menus[r][c].draw(pen);
                                        }
                                }
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

                        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                System.out.println(e);
                        }
                }
                else if(Eraser.class.equals(clazz))
                {
                        grid[y][x] = null;
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
        int mxg = me.getX() / tileSize;
        int myg = me.getY() / tileSize; 

        int mx = me.getX();
        int my = me.getY();
        
        try{ createTile(mxg, myg, currentTile); }
        catch(ArrayIndexOutOfBoundsException e){}

        if(mx >= sideMenuX)
        {
                if(testButton.clickedButton(mx, my)){
                        System.out.println("Clicked Button!");
                }
        }

    }

    @Override
    public void mouseDragged(MouseEvent me) 
    {
        int mx = me.getX() / tileSize;
        int my = me.getY() / tileSize;

        try {
                if(grid[my][mx] == null || currentTile.equals(Eraser.class))
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