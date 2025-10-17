package particulate.game.Gases;

import java.awt.Color;

public class Steam extends Gases{

    public Steam(int x, int y)
    {
        super(x, y, false, true, 2, 1);
        setAllPossibleColors(new Color[]{Color.WHITE});
        setColor();
    }
    
}
