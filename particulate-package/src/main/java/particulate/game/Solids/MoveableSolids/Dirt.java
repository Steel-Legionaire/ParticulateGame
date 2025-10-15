

public class Dirt extends MoveableSolid
{
        public Dirt(int x, int y)
        {
                super(x, y, false, true, 4, 1);
                setAllPossibleColors(new Color[]{new Color(150, 75, 0)});
                setColor();
        }
}