
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ParticulateGame extends Game  {
    
        public static final String TITLE = "Particulate Refreshed";
        public static final int SCREEN_WIDTH = 1815;
        public static final int SCREEN_HEIGHT = 1038;

        public int playAreaWidth = 1500;
        public int playAreaHeight = 1000;

        public static int tileSize = 10;

        public static Tile[][] grid;
        
		
	public ParticulateGame() 
        {
                grid = new Tile[playAreaHeight/tileSize][playAreaWidth/tileSize];

                grid[1][1] = new Sand(1, 1);
                setGridBoundsWalls();
        }
        

	public void update() 
        {

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
        
    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {}

    @Override
    public void keyReleased(KeyEvent ke) {}

    @Override
    public void mouseClicked(MouseEvent ke) { }

    @Override
    public void mousePressed(MouseEvent me) {}
    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
        
        
    //Launches the Game
    public static void main(String[] args) { new ParticulateGame().start(TITLE, SCREEN_WIDTH,SCREEN_HEIGHT); }
}