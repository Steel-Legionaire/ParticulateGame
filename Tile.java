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
    public int speed;

    public int framesSinceLastUpdate = 0;

    Tile(int x, int y, Color color, boolean isFlammable, boolean isDestructable, int toughness, int speed)
    {
        this.x = x;
        this.y = y;
        this.color = color;
        this.isFlammable = isFlammable;
        this.isDestructable = isDestructable;
        this.toughness = toughness;

        this.speed = speed;
        // Speed is in terms of how many frames until the objects moves again, this does not affect the actions
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
