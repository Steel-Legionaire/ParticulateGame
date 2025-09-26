package particulate.game;

import java.awt.Graphics;
import java.util.ArrayList;

public class CellularMatrix {
    
    private ArrayList<ArrayList<Tile>> matrix;


    public CellularMatrix() {}


    public void drawTiles(Graphics pen)
    {
        for(ArrayList<Tile> array : matrix)
        {
            for(Tile t : array)
            {
                t.draw(pen);
            }
        }
    }
}
