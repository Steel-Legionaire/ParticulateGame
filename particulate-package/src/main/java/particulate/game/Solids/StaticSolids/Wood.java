package particulate.game.Solids.StaticSolids;
import java.awt.Color;

import particulate.game.CellularMatrix;
import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Solids.MoveableSolids.Ash;

public class Wood extends StaticSolid{


    public boolean onFire = false;

    private static final Color[] COLORS = new Color[] { new Color(129, 64, 18), new Color(134, 67, 19), new Color(139, 69, 19), new Color(145, 72, 21), new Color(150, 75, 22) };
    
    // when Wood is on fire, this variable is how long it'll be on fire before it burns and potentially turns to ash
    int framesUntilCanBeBurnt = 300;
    int framesOnFire = 0;

    Color normalColor;
    Color onFireColor = new java.awt.Color(200, 100, 20);


    public Wood(int x, int y) {
        super(x, y, true, true, 4, 50);
        setAllPossibleColors(COLORS);
        setColor();
        normalColor = super.color;
    }

    @Override
    public void action() 
    {
        if(onFire)
        {
            super.color = onFireColor;

            if(framesSinceLastUpdate == speed)
            {
                //System.out.println("is on fire");
                CellularMatrix matrix = ParticulateGame.getMatrix();
                
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
                    Tile leftTile = matrix.getTile(x-1,y);
                    Tile topTile = matrix.getTile(x, y-1);
                    Tile rightTile = matrix.getTile(x+1, y);
                    Tile bottomTile = matrix.getTile(x, y+1);

                    int chanceToSpread = 15;
                    if(leftTile instanceof Wood && (int)(Math.random() *100) + 1 <= chanceToSpread)
                    {
                        ((Wood)leftTile).onFire = true;
                    }
                    else if(leftTile instanceof TNT)
                    {   
                        ((TNT)(leftTile)).isExploding = true;
                    }
                    
                    if(rightTile instanceof Wood && (int)(Math.random() *100) + 1 <= chanceToSpread)
                    {
                        ((Wood)rightTile).onFire = true;
                    }
                    else if(rightTile instanceof TNT)
                    {   
                        ((TNT)(rightTile)).isExploding = true;
                    }
                    
                    if(bottomTile instanceof Wood && (int)(Math.random() *100) + 1 <= chanceToSpread)
                    {
                        ((Wood)bottomTile).onFire = true;
                    }
                    else if(bottomTile instanceof TNT)
                    {   
                        ((TNT)(bottomTile)).isExploding = true;
                    }

                    if(topTile instanceof Wood && (int)(Math.random() *100) + 1 <= chanceToSpread)
                    {
                        ((Wood)topTile).onFire = true;
                    }
                    else if(topTile instanceof TNT)
                    {   
                        ((TNT)(topTile)).isExploding = true;
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
                                matrix.setTile(x, y, new Ash(x, y));
                            }
                            else 
                            {
                                matrix.setTile(x, y, null);
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
 