package particulate.game.Solids.StaticSolids;
import java.awt.Color;

public class Obsidian extends StaticSolid{

    private static final Color[] COLORS = new Color[]{ new Color(26, 26, 26), new Color(24, 24, 24), new Color(22, 22, 22) };

    public Obsidian(int x, int y) {
        super(x, y, false, true, 10, 0);
        setAllPossibleColors(COLORS);
        setColor();
    }
}
