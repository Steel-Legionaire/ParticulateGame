package particulate.game.Solids.StaticSolids;
import java.awt.Color;

public class Stone extends StaticSolid{

    private static final Color[] COLORS = new Color[]{new Color(140, 140, 140), new Color(130, 130, 130) };
    
    public Stone(int x, int y) {
        super(x, y, false, true, 5,1);
        setAllPossibleColors(COLORS);
        setColor();
    }  
}
