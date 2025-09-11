import java.awt.Color;

public class FireSpawner extends Tile
{

    public FireSpawner(int x, int y) {
        super(x, y, Color.magenta, false, true, 8, 1);
    }

    @Override
    public void move() 
    {

    }

    @Override
    public void action() 
    {
        Tile[][] grid = ParticulateGame.grid;

        if(grid[y-1][x] == null)
        {
            grid[y-1][x] = new Fire(x, y-1);
        }
    
        ParticulateGame.grid = grid;
    }
    
}
