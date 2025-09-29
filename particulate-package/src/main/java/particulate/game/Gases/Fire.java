package particulate.game.Gases;

import java.awt.Color;

import particulate.game.ParticulateGame;
import particulate.game.Tile;
import particulate.game.Solids.StaticSolids.TNT;
import particulate.game.Solids.StaticSolids.Wood;

public class Fire extends Tile{

    private int lifeTime = (int)(Math.random()*20) + 50;

    private static final Color[] COLORS = new Color[]{ new Color(255,135,0), new Color(255,142,0), new Color(255,150,0), new Color(255,158,0), new Color(255,165,0) };
    
    public Fire(int x, int y) {
        super(x, y, false, true, 1, 2);
        setAllPossibleColors(COLORS);
        setColor();
    }

    @Override
    public void move() 
    {



    }

    @Override
    public void action() 
    {

    }
    
}
