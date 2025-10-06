package particulate.game;
import java.awt.Color;
import java.awt.Graphics;

public abstract class Tile 
{

    public int x;
    public int y;

    public boolean updatedThisFrame = false;

    public Color color;
    public boolean isFlammable;
    public boolean isDestructable;
    public int toughness;
    public int speed;

    public int framesSinceLastUpdate = 0;

    protected static Color[] colors;

    int counter = 0;

    protected Tile(int x, int y, boolean isFlammable, boolean isDestructable, int toughness, int speed)
    {
        this.x = x;
        this.y = y;
        this.isFlammable = isFlammable;
        this.isDestructable = isDestructable;
        this.toughness = toughness;

        this.speed = speed;
        // Speed is in terms of how many frames until the objects moves again, this does not affect the actions
        
    }

    public void move(){}

    public void action(){}

    public void draw(Graphics pen)
    {
        return;
    }

    public void setAllPossibleColors(Color[] c)
    {
        colors = new Color[c.length];
        for(int i=0; i<colors.length; i++)
        {
            try{
                colors[i] = c[i];
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                //System.out.println(e);
            }
            
        }
    }

    public void setColor()
    {
        if (colors != null && colors.length > 0) {
            try
            {
                color = colors[(int)(Math.random() * colors.length)];
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                //System.out.println(e);
                color = colors[1];
            }
            
        } else {
            color = Color.MAGENTA; // fallback
        }
        //dSystem.out.println("Color ID: " + System.identityHashCode(color));


    }

    

    public void darkenTile()
    {
        // darken the color 
    }

    public void recieveHeat()
    {
        // increase temp 
    }
}
