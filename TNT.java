import java.awt.Color;

public class TNT extends Tile{


    public boolean isExploding = true;

    public TNT(int x, int y) {
        super(x, y, Color.RED, true, true, 2, 2);
    }

    @Override
    public void move() {

    }

    @Override
    public void action() 
    {

    }

    public void explode()
    {
        System.out.println("BOOM!");
        isExploding = true;
        Tile[][] grid = ParticulateGame.grid;

        int radius = 3;
        int power = 10;

        //grid[y-radius][x-radius] = top left

        
        //System.out.println((y) + " " + (x));
        //System.out.println((y-radius) + " " + (x-radius));

        
        /*grid[y-radius][x-radius] = new Wood(x-radius, y-radius);

        grid[y+radius][x+radius] = new Wood(x+radius, y+radius);

        grid[y+radius][x-radius] = new Wood(x-radius, y+radius);

        grid[y-radius][x+radius] = new Wood(x+radius, y-radius);*/

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
                        if(curTile instanceof TNT)
                        {
                            System.out.println(((TNT)curTile).isExploding);
                            if(!((TNT)curTile).isExploding)
                            {
                                if(!((TNT)curTile).equals(this))
                                {
                                    ((TNT)curTile).explode();
                                }
                                else 
                                {
                                    grid[r][c] = null;
                                }
                            }
                            else 
                            {
                                grid[r][c] = null;
                            }
                            
                        }
                        else
                        {
                            if(curTile.toughness <= power && curTile.isDestructable)
                            {
                                grid[r][c] = null;
                            }
                        }

                    }
                }
                catch(ArrayIndexOutOfBoundsException e){}
            }
        }
    }
    
}
