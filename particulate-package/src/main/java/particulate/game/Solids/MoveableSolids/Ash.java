package particulate.game.Solids.MoveableSolids;

import java.awt.Color;


public class Ash extends MoveableSolid{

    private static final Color[] COLORS = new Color[]{ new Color(230,230,230), new Color( 200, 200, 200), new Color(215, 215, 215), new Color(240, 240, 240)};

    public Ash(int x, int y) {
        super(x, y, false, true, 1, 2);
        
        setAllPossibleColors(COLORS);
        setColor();
    }

    @Override
    public void action() { }
    
}
