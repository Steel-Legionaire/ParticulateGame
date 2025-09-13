package com.example;
import java.awt.Color;

public class Wood extends Tile{


    public boolean onFire = false;

    // when Wood is on fire, this variable is how long it'll be on fire before it burns and potentially turns to ash
    int framesUntilCanBeBurnt = 300;
    int framesOnFire = 0;

    Color normalColor = new java.awt.Color(139,69,19);
    Color onFireColor = new java.awt.Color(200, 100, 20);


    public Wood(int x, int y) {
        super(x, y, new java.awt.Color(139,69,19), true, true, 4, 50);
    }

    @Override
    public void move() {}

    @Override
    public void action() 
    {
        if(onFire)
        {
            super.color = onFireColor;

            if(framesSinceLastUpdate == speed)
            {
                //System.out.println("is on fire");
                Tile[][] grid = ParticulateGame.grid;

                
                //System.out.println("chanceToGoOut: "+chanceToGoOut);

                int chanceToGoOut = (int)(Math.random() *100) + 1;

                if(chanceToGoOut <= 15)
                {
                    //System.out.println("TEST");
                    onFire = false;
                    framesOnFire = 0;
                    super.color = normalColor;
                }
                else
                {
                        int chanceToSpread = 15;
                        if(grid[y][x-1] instanceof Wood && (int)(Math.random() *100) + 1 <= chanceToSpread)
                        {
                            ((Wood)grid[y][x-1]).onFire = true;
                        }
                        if(grid[y][x+1] instanceof Wood && (int)(Math.random() *100) + 1 <= chanceToSpread)
                        {
                            ((Wood)grid[y][x+1]).onFire = true;
                        }
                        if(grid[y+1][x] instanceof Wood && (int)(Math.random() *100) + 1 <= chanceToSpread)
                        {
                            ((Wood)grid[y+1][x]).onFire = true;
                        }
                        if(grid[y-1][x] instanceof Wood && (int)(Math.random() *100) + 1 <= chanceToSpread)
                        {
                            ((Wood)grid[y-1][x]).onFire = true;
                        }
            


                    if(framesOnFire >= framesUntilCanBeBurnt)
                    {
                        
                        int chanceToBurn = (int)(Math.random() *100) + 1;
                        //System.out.println(chanceToBurn);
                        if(chanceToBurn <= 30)
                        {
                            int chanceToSpawnAsh = (int)(Math.random() *100) + 1;
                            //System.out.println(chanceToSpawnAsh);
                            if(chanceToSpawnAsh <= 25)
                            {
                                grid[y][x] = new Ash(x, y);
                            }
                            else 
                            {
                                grid[y][x] = null;
                            }
                        }



                    }
                }
                framesSinceLastUpdate = 0;
            }
            framesSinceLastUpdate++;
            framesOnFire++;
            
        }
        else
        {
            //System.out.println(framesSinceLastUpdate);
            if(framesSinceLastUpdate < speed)
            {
                framesSinceLastUpdate++;
            }
            super.color = normalColor;
            framesOnFire = 0;
        }
    }
    
}
 