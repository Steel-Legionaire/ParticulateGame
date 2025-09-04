import java.awt.Color;
import java.awt.Graphics;

public abstract class Tile 
{

    public int x;
    public int y;
    
    private int tileSize = ParticulateGame.tileSize;

    public boolean updatedThisFrame = false;

    public Color color;
    public boolean isFlammable;
    public boolean isDestructable;
    public int toughness;

    Tile(int x, int y, Color color, boolean isFlammable, boolean isDestructable, int toughness)
    {
        this.x = x;
        this.y = y;
        this.color = color;
        this.isFlammable = isFlammable;
        this.isDestructable = isDestructable;
        this.toughness = toughness;
    }

    public abstract void move();

    public abstract void action();

    public void draw(Graphics pen)
    {
        pen.setColor(color);
        pen.fillRect(x*tileSize, y*tileSize, tileSize, tileSize);
        pen.setColor(Color.BLACK);
    }
}
