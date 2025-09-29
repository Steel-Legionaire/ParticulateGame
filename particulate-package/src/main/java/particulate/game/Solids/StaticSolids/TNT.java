package particulate.game.Solids.StaticSolids;
import java.awt.Color;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;

public class TNT extends Tile{

    private static final Color[] COLORS = new Color[]{new Color(200, 0, 0), new Color(220, 0, 0), new Color(255, 0, 0), new Color(255, 30, 30), new Color(255, 60, 60)};
    
    public boolean isExploding = false;

    public boolean isStatic = false;
    public boolean isFalling = false;

    int framesBetweenFlashes = 100;
    int framesSinceLastFlash = 0;

    // fuse is how many frames until... BOOOM 
    int fuse = 500;
    int framesWhileLit = 0;

    boolean coloredRed = true;
    Color spawnedColor;

    public TNT(int x, int y) {
        super(x, y, true, true, 2, 2);
        setAllPossibleColors(COLORS);
        setColor();
        spawnedColor = super.color;
        
        if(isFalling)
        {
                this.isFalling = true;
        }
        else
        {
                isStatic = true;
        }
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
        CellularMatrix matrix = ParticulateGame.getMatrix();

        int radius = 6;
        int power = 10;

        if(framesWhileLit == fuse)
        {
            for(int r=y-radius; r<y+radius; r++)
            {
                for(int c = x-radius; c<x+radius; c++)
                {
                    try
                    {
                        

                        Tile curTile = matrix.getTile(c,r);

                        if(curTile != null)
                        
                        {
                            if(curTile instanceof Bedrock){continue;}
                            //System.out.println(isExploding);

                            if(curTile.toughness <= power && curTile.isDestructable)
                            {
                                double distance = Math.sqrt(Math.pow(c - x, 2) + Math.pow(r - y, 2));

                                int innerRad = (int)(radius/4);
                                
                                if(distance <= innerRad)
                                {
                                    matrix.setTile(c,r, null);
                                }
                                else if (distance <= radius) {
                                    double probability = Math.max(0, 1 - (distance / radius));

                                    if(Math.random() < probability)
                                    {
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
                                            matrix.setTile(c,r, null);
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
        else 
        {
            
            framesWhileLit++;
            if(framesBetweenFlashes == framesSinceLastFlash)
            {  
                if(coloredRed)
                {
                    super.color = Color.WHITE;
                    coloredRed = false;
                }
                else
                {
                    super.color = spawnedColor;
                    coloredRed = true;
                }
                framesSinceLastFlash = 0;
            }
        }
        framesSinceLastFlash++;
    }
    
}
