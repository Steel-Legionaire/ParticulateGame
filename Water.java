import java.awt.Color;

public class Water extends Tile
{
    int direction;

    public Water(int x, int y) {
        super(x, y, Color.CYAN, false, false, 1);

        direction = (int)(Math.random() *2); // spawn with a random direction

    }

    @Override
    public void move() 
    {
        Tile[][] grid = ParticulateGame.grid;

        if(!updatedThisFrame)
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

            updatedThisFrame = true;
        }
        else
        {
            updatedThisFrame = false;
        }

    }

    @Override
    public void action() {

    }
    
}
