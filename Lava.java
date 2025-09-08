import java.awt.Color;

public class Lava extends Tile{

    int direction = (int)(Math.random() * 2);

    public Lava(int x, int y) {
        super(x, y, Color.ORANGE, true, true, 2, 8);
    }

    @Override
    public void move() {
        Tile[][] grid = ParticulateGame.grid;

        if(framesSinceLastUpdate == speed)
        {

            if(grid[y+1][x] == null)
            {
                grid[y][x] = null;
                this.y++;
                grid[y][x] = this;
            }
            else
            {
                if(direction == 1)
                {
                    if(grid[y][x+1] == null)
                    {
                        grid[y][x] = null;
                        this.x++;
                        grid[y][x] = this;
                    }
                    else
                    {
                        direction = 0;
                    }
                }
                else
                {
                    if(grid[y][x-1] == null)
                    {
                        grid[y][x] = null;
                        this.x--;
                        grid[y][x] = this;
                    }
                    else
                    {
                        direction = 1;
                    }
                }
            }

            ParticulateGame.grid = grid;

            framesSinceLastUpdate = 0;
        }
        else
        {
            framesSinceLastUpdate++;
        }

    }

    @Override
    public void action() {
        Tile[][] grid = ParticulateGame.grid;

        if(grid[y-1][x] != null && grid[y-1][x].getClass().equals(Water.class))
        {
            grid[y][x] = new Obsidian(x,y);
        }

        if(grid[y][x+1] != null && grid[y][x+1].getClass().equals(Water.class))
        {
            grid[y][x] = new Obsidian(x,y);
        }

        if(grid[y+1][x] != null && grid[y+1][x].getClass().equals(Water.class))
        {
            grid[y][x] = new Obsidian(x,y);
        }

        if(grid[y][x-1] != null && grid[y][x-1].getClass().equals(Water.class))
        {
            grid[y][x] = new Obsidian(x,y);
        }
    }
    
}
