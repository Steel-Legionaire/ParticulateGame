import java.awt.Color;

public class TNT extends Tile{


    public boolean isExploding = false;

    public TNT(int x, int y) {
        super(x, y, Color.RED, true, true, 2, 2);
    }

    @Override
    public void move() {

    }

    @Override
    public void action() 
    {
        if(isExploding)
        {
            explode();
        }
    }

    public void explode()
    {
        //System.out.println("BOOM!");
        isExploding = true;
        Tile[][] grid = ParticulateGame.grid;

        int radius = 6;
        int power = 10;

        for(int r=y-radius; r<y+radius; r++)
        {
            for(int c = x-radius; c<x+radius; c++)
            {
                try
                {
                    Tile curTile = grid[r][c];

                    if(curTile != null)
                    {
                        //System.out.println(isExploding);
                        if(curTile instanceof TNT && !(curTile.equals(this)))
                        {

                            if(((TNT)curTile).isExploding)
                            {
                                continue;
                            }
                            else
                            {
                                // TNT is NOT exploding
                                ((TNT)curTile).isExploding = true;
                            }                      
                        }
                        else
                        {
                            if(curTile.toughness <= power && curTile.isDestructable)
                            {
                                double distance = Math.sqrt(Math.pow(c - x, 2) + Math.pow(r - y, 2));

                                int innerRad = (int)(radius/4);
                                
                                if(distance <= innerRad)
                                {
                                    grid[r][c] = null;
                                }
                                else if (distance <= radius) {
                                    double probability = Math.max(0, 1 - (distance / radius));

                                    if(Math.random() < probability)
                                    {
                                        grid[r][c] = null;
                                    }
                                }
                                
                            }
                        }

                    }
                }
                catch(ArrayIndexOutOfBoundsException e){}
            }
        }
    }
    
}
