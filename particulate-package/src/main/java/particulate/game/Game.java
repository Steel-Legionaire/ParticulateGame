package particulate.game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseWheelListener;

public abstract class Game implements KeyListener, MouseListener, MouseMotionListener, DropTargetListener, MouseWheelListener
{
    public static JFrame frame;
    private GamePanel gamePanel;
    boolean running;
    private ParticulateGame game;    


    protected void start(String title, int width, int height)
    {
        this.game = (ParticulateGame)this;
    	running = true;
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel();
        frame.getContentPane().add(BorderLayout.CENTER, gamePanel);
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.setBackground(new Color(40,40,40));
        frame.addMouseMotionListener(this);
        frame.addMouseWheelListener(game);
        new DropTarget(frame.getContentPane(), this);
        run();
    }


    class GamePanel extends JPanel
    {
        private static final long serialVersionUID = 1L;
        @Override
        public void paintComponent(Graphics g) { game.draw(g); }
    }

    private void run()
    {
        while (true)
        {
           game.update();
            try { Thread.sleep(5); }
            catch (InterruptedException e) {}
            frame.repaint();
        }
    }
}