package particulate.game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

public class Button {
        
    
    public int x,y,w,h;
    public String txt;
    public Color bgColor;
    public Color txtColor;
    public Font font = new Font("Arial",1,15);

    int strtVal = 10;
    int counter = strtVal;

    boolean turnGrey = false;


    public Button(int x, int y, int w, int h, String txt, Color bgColor, Color txtColor)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.bgColor = bgColor;
        this.txtColor = txtColor;
        this.txt = txt;
    }

    public Button(int x, int y, int w, int h, String txt)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.bgColor = Color.WHITE;
        this.txtColor = Color.BLACK;
        this.txt = txt;
    }
    
    public void draw(Graphics pen)
    {
        pen.setFont(font);

        if(turnGrey)
        {
            if(counter > 0)
            {
                // Counter for being grey is still ongoin so inverse the txt and bg colors
                pen.setColor(txtColor);
                pen.fillRect(x, y, w, h);
                pen.setColor(bgColor);
                counter--;
            }
            else
            {
                pen.setColor(bgColor);
                pen.fillRect(x, y, w, h);
                pen.setColor(txtColor);

                turnGrey = false;
                counter = strtVal;
            }
        }
        else{
                pen.setColor(bgColor);
                pen.fillRect(x, y, w, h);
                pen.setColor(txtColor);
        }
        
        pen.drawString(txt, (x+(w/2))-(pen.getFontMetrics().stringWidth(txt)/2), (y+(h/2))+(pen.getFontMetrics().getHeight()/4));
    }


    public boolean clickedButton(int mX, int mY){
        //System.out.println("mX: "+mX+"\nbX: "+x+"\nw: "+w+"\nbX+w: "+(x+w));
        //System.out.println("mY: "+mY+"\nbY: "+((y+h*2))+"\nh: "+h+"\ny+h: "+(y+(h*2)));
        //System.out.println("\n\n\n\n");
        if((mY >= (y+h+(h/2))-5 && mY <= y+(h*2)+5)   &&  (mX >= x && mX <= x+w)){
                
            turnGrey = true;
            return true;
        }
        return false;
    }

    public void swapColors()
    {
        Color temp = bgColor;
        bgColor = txtColor;
        txtColor = temp;
    }
}
