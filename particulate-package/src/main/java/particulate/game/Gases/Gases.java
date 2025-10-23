package particulate.game.Gases;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Liquids.Liquid;

public class Gases extends Tile{

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
                Tile rightTile = matrix.getTile(x+1, y);

                if(rightTile == null)
                {
                    matrix.swapPositions(rightTile, x+1, y, this);
                }
            }
            else if(randDir == 2)
            {
                Tile lefTile = matrix.getTile(x-1, y);

                if(lefTile == null)
                {
                    matrix.swapPositions(lefTile, x-1, y, this);
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
