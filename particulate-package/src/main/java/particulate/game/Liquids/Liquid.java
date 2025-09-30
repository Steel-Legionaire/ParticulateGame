package particulate.game.Liquids;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;

public class Liquid extends Tile{

    int direction;

    public Liquid(int x, int y, boolean isFlammable, boolean isDestructable, int toughness, int speed) {
        super(x, y, isFlammable, isDestructable, toughness, speed);

        direction = (int)(Math.random() *2); // spawn with a random direction
    }
    
    @Override
    public void move()
    {
        CellularMatrix matrix = ParticulateGame.getMatrix();

        if(!matrix.yWithinBounds(y))
        {
            matrix.setTile(x, y, null);
        }
        else
        {
            Tile bottomTile = matrix.getTile(x,y+1);
            Tile rightTile = matrix.getTile(x+1, y);
            Tile leftTile = matrix.getTile(x-1, y);

            if(framesSinceLastUpdate == speed)
            {

                if(bottomTile == null)
                {
                    matrix.setTile(x, y, null);
                    this.y++;
                    matrix.setTile(x, y, this);
                }
                else
                {
                    if(direction == 1)
                    {
                        if(rightTile == null)
                        {
                            matrix.setTile(x, y, null);
                            this.x++;
                            matrix.setTile(x, y, this);
                        }
                        else
                        {
                            direction = 0;
                        }
                    }
                    else
                    {
                        if(leftTile == null)
                        {
                            matrix.setTile(x, y, null);
                            this.x--;
                            matrix.setTile(x, y, this);
                        }
                        else
                        {
                            direction = 1;
                        }
                    }
                }
                framesSinceLastUpdate = 0;
            }
            else
            {
                framesSinceLastUpdate++;
            }
        }
    }

    @Override
    public void darkenTile() {}

    @Override
    public void recieveHeat()
    {
        // turn water to steam
    }
}
