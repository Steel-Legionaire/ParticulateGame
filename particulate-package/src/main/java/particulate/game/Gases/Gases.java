package particulate.game.Gases;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;

public class Gases extends Tile{

    int lifeTime = 500;

    protected Gases(int x, int y, boolean isFlammable, boolean isDestructable, int toughness, int speed) {
        super(x, y, isFlammable, isDestructable, toughness, speed);
    }

    @Override
    public void move() 
    {
        CellularMatrix matrix = ParticulateGame.getMatrix();

        if(framesSinceLastUpdate == speed)
        {
            // 0 - up
            // 1 - right
            // 2 - left
            if(lifeTime == 0)
            {
                matrix.setTile(x, y, null);
                return;
            }

            int randDir = (int)(Math.random() * 3);
            //System.out.println(randDir);
            if(randDir == 0)
            {
                Tile topTile = matrix.getTile(x,y-1);

                if(topTile == null)
                {
                    matrix.swapPositions(topTile, x, y-1, this);
                    
                }
            }
            else if(randDir == 1)
            {
                Tile topRightTile = matrix.getTile(x+1,y-1);
                Tile rightTile = matrix.getTile(x+1, y);

                if(topRightTile == null && rightTile == null)
                {
                    matrix.swapPositions(topRightTile, x+1, y-1, this);
                }
            }
            else if(randDir == 2)
            {
                Tile topLeftTile = matrix.getTile(x-1,y-1);
                Tile lefTile = matrix.getTile(x-1, y);

                if(topLeftTile == null && lefTile == null)
                {
                    matrix.swapPositions(topLeftTile, x-1, y-1, this);
                }
            }


            lifeTime--;
            framesSinceLastUpdate = 0; 
        }
        else
        {
            framesSinceLastUpdate++;
        }
    }


    public void setLifeTime(int n)
    {
        lifeTime = n;
    }
}
