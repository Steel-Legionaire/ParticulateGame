package particulate.game.Solids.MoveableSolids;
import java.awt.Color;

public class Sand extends MoveableSolid{
    private static final Color[] COLORS = new Color[]{new Color(219, 212, 157), new Color(199, 192, 137), new Color(222, 215, 161), new Color(204, 199, 143), new Color(233, 223, 174)};
    public Sand(int x, int y) {
        super(x, y, false, true, 2,1);

        setAllPossibleColors(COLORS);
        setColor();
        
    }

    @Override
    public void action() { return; }
    
}
