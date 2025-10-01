package particulate.game.Solids.MoveableSolids;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Liquids.Water;
import particulate.game.Solids.Solid;

public abstract class MoveableSolid extends Solid
{

    public MoveableSolid(int x, int y, boolean isFlammable, boolean isDestructable, int toughness, int speed) {
        super(x, y, isFlammable, isDestructable, toughness, speed);
    }
    
    @Override
    public void move()
    {
        if(framesSinceLastUpdate == speed)
        {
            CellularMatrix matrix = ParticulateGame.getMatrix();

            if(y+1 > matrix.getRowBounds()){ matrix.setTile(x,y, null); return; }

            Tile bottomCenterTile = matrix.getTile(x, y+1);

            if(bottomCenterTile == null)
            {
                matrix.swapPositions(bottomCenterTile, x, y+1, this);
            }
            else if(bottomCenterTile instanceof Water)
            {
               matrix.swapPositions(bottomCenterTile, this);
            }
            else
            {
                // return either 1 or 0
                int randDir = (int)(Math.random() * 2);

                if(randDir == 0)
                {
                    // Go left.
                    Tile bottomLeftTile = matrix.getTile(x-1, y+1);

                    if(bottomLeftTile == null)
                    {
                        matrix.swapPositions(bottomLeftTile, x-1, y+1, this);
                    }
                    else if(bottomCenterTile instanceof Water)
                    {
                        matrix.swapPositions(bottomLeftTile, this);
                    }
                    else if(bottomLeftTile.y > matrix.getRowBounds())
                    {
                        matrix.setTile(x,y,null);
                    }
                    
                    
                }
                else 
                {
                    Tile bottomRightTile = matrix.getTile(x+1, y+1);

                    if(bottomRightTile == null)
                    {
                        matrix.swapPositions(bottomRightTile, x+1, y+1, this);
                    }
                    else if(bottomCenterTile instanceof Water)
                    {
                        matrix.swapPositions(bottomRightTile, this);
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
