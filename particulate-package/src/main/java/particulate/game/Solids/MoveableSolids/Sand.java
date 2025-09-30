package particulate.game.Solids.MoveableSolids;
import java.awt.Color;

public class Sand extends MoveableSolid{

    private static final Color[] COLORS = new Color[]{new Color(210, 190, 10), new Color(220, 200, 20), new Color(230, 210, 30), new Color(240, 220, 40), new Color(245, 225, 50)};

    public Sand(int x, int y) {
        super(x, y, false, true, 2,1);

        setAllPossibleColors(COLORS);
        setColor();
        
    }

    @Override
    public void action() { return; }
    
}
