import java.awt.Color;

public class Sand extends Tile {
    
    Sand(int x, int y) {
        super(x, y, Color.YELLOW, false, true, 2);
    }

    @Override
    public void move() 
    { 
        Tile[][]grid = ParticulateGame.grid;

        if(!updatedThisFrame)
        {
            if(y+1 > grid.length){return;}

            if(grid[y+1][x] == null)
            {
                grid[y][x] = null;
                y++;
                grid[y][x] = this;

              
            }
            else // sand is on top of another tile
            {
                
                //if(grid[y+1][x+1] == null && grid[y+1][x-1] == null)
                //{
                //    // choose a random one
                //    grid[y][x] = null;
                //}
                //else 

                if(grid[y+1][x-1] == null)
                {
                    //go left
                    grid[y][x] = null;
                    y++;
                    x--;
                    grid[y][x] = this;
                    
                }
                else if(grid[y+1][x+1] == null) 
                {
                    grid[y][x] = null;
                    y++;
                    x++;
                    grid[y][x] = this;
                    // go right
                }
            }
            this.updatedThisFrame = true;
        }
        else{
            updatedThisFrame = false; 
            // updatedThisFrame will become false after this object is checked for the second within the same frame 
        }

        ParticulateGame.grid = grid;
        return; 
    }

    @Override
    public void action() { return; }
    
}
