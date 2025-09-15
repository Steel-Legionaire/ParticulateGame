package com.example;
import java.awt.Color;

public class TNT extends Tile{


    public boolean isExploding = false;

    public boolean isStatic = false;
    public boolean isFalling = false;

    int framesBetweenFlashes = 100;
    int framesSinceLastFlash = 0;

    // fuse is how many frames until... BOOOM 
    int fuse = 500;
    int framesWhileLit = 0;

    boolean coloredRed = true;

    public TNT(int x, int y) {
        super(x, y, Color.RED, true, true, 2, 2);
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
        Tile[][] grid = ParticulateGame.grid;

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
                        Tile curTile = grid[r][c];

                        if(curTile != null)
                        {
                            //System.out.println(isExploding);

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
                    super.color = Color.RED;
                    coloredRed = true;
                }
                framesSinceLastFlash = 0;
            }
        }
        framesSinceLastFlash++;
    }
    
}
