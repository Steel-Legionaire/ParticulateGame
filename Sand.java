import java.awt.Color;

public class Sand extends Tile {
    
    Sand(int x, int y) {
        super(x, y, Color.YELLOW, false, true, 2);
    }

    @Override
    public void move() { return; }

    @Override
    public void action() { return; }
    
}
