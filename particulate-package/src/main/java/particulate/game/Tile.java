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

    public int heat = 15; // Degrees in celcius (room temp of 15)

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
        /*
         * When tile recieves a certain amount of heat, do something. Example: Water turns to steam after recieving 100 heat
         */
    }

    public void recieveWater()
    {
        /*
         * After tile has recieved x amount of water do something like set a boolean wet
         * wet tiles will start "leaking" water to nearby tiles if they can absorb water 
         * Example: Dirt turns to mud after recieving 100 water and once a water tile 
         */
    }
}
