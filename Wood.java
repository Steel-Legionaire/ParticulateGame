public class Wood extends Tile{


    public boolean onFire = false;

    public Wood(int x, int y) {
        super(x, y, new java.awt.Color(139,69,19), true, true, 4, 3);
    }

    @Override
    public void move() {}

    @Override
    public void action() 
    {
        System.out.println(onFire);
    }
    
}
