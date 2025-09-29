package particulate.game;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import particulate.game.Solids.StaticSolids.Bedrock;

public class CellularMatrix {
    
    private Tile[][] matrix;
    private int rowBounds;
    private int collumnBounds;

    public CellularMatrix(int width, int height) 
    {
        matrix = new Tile[height][width];

        // subtract 1 from each because we do not want the player to interact with the outer layer of bedrock
        rowBounds = height - 1;
        collumnBounds = width - 1;
    }

    public Tile getTileAtLocation(int x, int y)
    {
        return matrix[y][x];
    }

    public boolean withinBounds(int x, int y)
    {
        return ((x > 0 && x < collumnBounds) && (y > 0 && y < rowBounds));
    }

    public boolean yWithinBounds(int y)
    {
        return (y > 0 && y < rowBounds);
    }

    public boolean xWithinBounds(int x)
    {
        return (x > 0 && x < collumnBounds);
    }

    public void drawAllTiles(Graphics pen, int tileSize)
    {
        // Group tiles by color
        Map<Color, List<Tile>> tileBuckets = new HashMap<>();

        for(int r=0; r < rowBounds+1; r++)
        {
                for(int c=0; c < collumnBounds+1; c++)
                {
                        Tile t = matrix[r][c];

                        if(t != null)
                        {
                                // Add tile to correct color group
                                tileBuckets
                                        .computeIfAbsent(t.color, k -> new ArrayList<>())
                                        .add(t);
                        }
                }
        }

        // Draw all tiles, grouped by color 
        for (Map.Entry<Color, List<Tile>> entry : tileBuckets.entrySet())
        {
                Color color = entry.getKey();
                List<Tile> tiles = entry.getValue();

                pen.setColor(color);

                for(Tile t : tiles)
                {
                        pen.fillRect(t.x * tileSize, t.y * tileSize, tileSize, tileSize);
                }
        }
    }

    public void setGridBoundsWalls()
    {
        for(int r=0; r <= rowBounds; r++)
        {
            for(int c=0; c <= collumnBounds;c++)
            {
                if(c == 0 || r == rowBounds || r == 0 || c == collumnBounds)
                {
                    matrix[r][c] = new Bedrock(c, r);
                }
            }
        }
    }

    public void resetGrid()
    {
        matrix = new Tile[rowBounds+1][collumnBounds+1];
        setGridBoundsWalls(); 
    }

    public void createTile(int x, int y, Class<?> clazz, int drawSize)
    {
        if(!withinBounds(x, y)){ return; }

        if(drawSize == 1)
        {
            Tile tileAtLocation = getTileAtLocation(x, y);

            if(clazz.equals(Eraser.class) && y>=1 && x>=1 && y <= rowBounds && x <= collumnBounds)
            {

                if(tileAtLocation != null && tileAtLocation.isDestructable)
                {

                    matrix[y][x] = null;
                }

                return;
            }

            if(x >= 1 && y >= 1 && y <= rowBounds && x <= collumnBounds && tileAtLocation == null)
            {
                try {
                    Constructor<?> argConstructor = clazz.getConstructor(int.class, int.class);

                    Tile t = (Tile) argConstructor.newInstance(x, y);

                    matrix[y][x] = t;

                } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    System.out.println(e);
                }
            }
        }                                
        else 
        {
            if(drawSize % 2 == 1)
            {
                for(int r = y - ((int)(drawSize / 2)); r < y + ((int)(drawSize / 2)) + 1; r++)
                {
                    if(r < 1){ continue; }
                    else if(r >= rowBounds){ break; }

                    for(int c = x - ((int)(drawSize / 2)); c < x+((int)(drawSize / 2)) + 1; c++)
                    {
                        if(c < 1){continue;} 
                        else if(c >= collumnBounds){ break; }

                        Tile tileAtLocation = getTileAtLocation(c, r);
                        
                        if(clazz.equals(Eraser.class))
                        {
                            if( tileAtLocation != null && tileAtLocation.isDestructable )
                            {

                                matrix[r][c] = null;
                            }
                        }
                        else
                        {
                            if( tileAtLocation == null)
                            {
                                try {
                                    Constructor<?> argConstructor = clazz.getConstructor(int.class, int.class);
    
                                    Tile t = (Tile) argConstructor.newInstance(c, r);
                                    matrix[r][c] = t;

                                } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                    System.out.println(e);
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                for(int r = y - ((int)(drawSize / 2)); r < y + ((int)(drawSize / 2)); r++)
                {
                    if(r<1){ continue; }
                    else if( r >= rowBounds){ break; }

                    for(int c = x - ((int)(drawSize / 2)); c < x+((int)(drawSize / 2)); c++)
                    {
                        if(c < 1){continue;} 
                        else if(c >= collumnBounds ){ break; }

                        Tile tileAtLocation = getTileAtLocation(c, r);
                
                        if(clazz.equals(Eraser.class))
                        {
                            if( tileAtLocation != null && tileAtLocation.isDestructable )
                            {

                                matrix[r][c] = null;
                            }
                        }
                        else
                        {
                            if( tileAtLocation == null)
                            {
                                try {
                                    Constructor<?> argConstructor = clazz.getConstructor(int.class, int.class);
    
                                    Tile t = (Tile) argConstructor.newInstance(c, r);
                                    matrix[r][c] = t;

                                } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                    System.out.println(e);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int getRowBounds(){ return rowBounds; }
    public int getCollumnBounds(){ return collumnBounds; }

    public void setTile(int x, int y, Tile t)
    {
        matrix[y][x] = t;
    }

    public Tile getTile(int x, int y){ return matrix[y][x]; }

    public void setRow(int r, Tile[] ta)
    {
        matrix[r] = ta;
    }

    public void setMatrix(Tile[][] ta)
    {
        matrix = ta;
    }
}
