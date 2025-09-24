package com.example;
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

    static public Color[] colors;

    Tile(int x, int y, boolean isFlammable, boolean isDestructable, int toughness, int speed)
    {
        this.x = x;
        this.y = y;
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
                System.out.println("Error setting Tiles color: \n"+e);
            }
            
        }
    }

    public void setColor()
    {
        this.color = colors[(int)(Math.random() * colors.length)];
    }
}
