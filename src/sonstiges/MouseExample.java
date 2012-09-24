import java.awt.*;
import java.applet.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class MouseExample extends Applet {
   Panel monitor = new Panel(),
         paintPanel  = new Panel();
   TextField oldPosition  = new TextField(20), 
             newPosition = new TextField(20);
   Label l1 = new Label ("Old Position"),
         l2 = new Label ("New Position");
 
   public void init () {
      setLayout(new BorderLayout());
      add(BorderLayout.CENTER,paintPanel);
      paintPanel.setBackground(Color.lightGray);
      paintPanel.addMouseMotionListener(new MyMotionListener());
      paintPanel.addMouseListener(new MyMouseListener());
      add(BorderLayout.SOUTH, monitor);
      monitor.setLayout(new GridLayout(2,2));
      monitor.add(l1);
      monitor.add(oldPosition);
      monitor.add(l2);
      monitor.add(newPosition);
    }

  class MyMouseListener extends MouseAdapter
  { 
       public void mousePressed(MouseEvent m){ 
          Graphics g = paintPanel.getGraphics();
          g.setColor(Color.red);
          g.drawOval(m.getX(), m.getY(), 20, 20);
         
       }
       public void mouseReleased(MouseEvent m){
          Graphics g = paintPanel.getGraphics();
          g.setColor(Color.black);
          g.fillOval(m.getX(), m.getY(), 10, 10);
       }    
    }

  class MyMotionListener extends MouseMotionAdapter{
     
   int x =0 ; 
   int y =0 ; 

   public void mouseMoved(MouseEvent m) {
      oldPosition.setText("x = " + x + " y = " + y);
      x = m.getX();
      y = m.getY(); 
      newPosition.setText("x = " + x + " y = " + y);
     }
  }
}