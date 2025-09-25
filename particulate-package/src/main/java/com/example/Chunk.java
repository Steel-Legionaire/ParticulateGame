package com.example;

public class Chunk 
{

    // This class is taken and slightly modified from DavidMcLaughlin208's GitHub page 
    // where he also uses a chunk based system in his FallingSandGame
    // Github link: https://github.com/DavidMcLaughlin208/FallingSandJava/blob/master/core/src/com/gdx/cellular/util/Chunk.java

    public static int size = 32;
    
    private boolean shouldStep = true;
    private boolean shouldStepNextFrame = true;

    // x and y coordinate on the window, x first then y
    private int[] topLeft;
    private int[] bottomRight;

    public Chunk(int[] topLeft, int[] bottomRight)
    {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Chunk(){}

    public void setTopLeft(int x, int y)
    {
        this.topLeft = new int[]{x,y};
    }
    public int[] getTopLeft()
    {
        return this.topLeft;
    }
    
    public void setBottomRight(int x, int y)
    {
        this.bottomRight = new int[]{x,y};
    }
    public int[] getBottomRight()
    {
        return this.bottomRight ;
    }

    public void setShouldStep(boolean shouldStep) {
        this.shouldStep = shouldStep;
    }

    public boolean getShouldStep() {
        return this.shouldStep;
    }

    public void setShouldStepNextFrame(boolean shouldStepNextFrame) {
        this.shouldStepNextFrame = shouldStepNextFrame;
    }

    public boolean getShouldStepNextFrame() {
        return this.shouldStepNextFrame;
    }

    public void shiftShouldStepAndReset() {
        this.shouldStep = this.shouldStepNextFrame;
        this.shouldStepNextFrame = false;
    }

    public boolean isTileInChunk(Tile t)
    {
        int tileX = t.x * t.tileSize;
        int tileY = t.y * t.tileSize;

        if(tileX >= topLeft[0] && tileX+t.tileSize <= bottomRight[0])
        {
            if(tileY >= topLeft[1] && tileY+t.tileSize <= bottomRight[1])
            {
                return true;
            }
        }

        return false;
    }

}
