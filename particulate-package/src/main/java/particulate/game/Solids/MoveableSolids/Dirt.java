package particulate.game.Solids.MoveableSolids;

import java.awt.Color;

public class Dirt extends MoveableSolid
{
        public Dirt(int x, int y)
        {
                super(x, y, false, true, 4, 1);
                setAllPossibleColors(new Color[]{new Color(150, 75, 0), new Color(160, 80, 10), new Color(140, 70, 20), new Color(130, 60, 10), new Color(155, 85, 15)});
                setColor();
        }
}