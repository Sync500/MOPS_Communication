import java.awt.*;
import java.applet.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ChangeButton  extends Applet {
   int count  = 0; 
   String buttonText   = "Number of Clicks: ";
   Button b = new Button(buttonText + count);
  
    public void init () {
      setBackground(Color.yellow);
      add(b); 
      b.setBackground(Color.blue);
      b.setForeground(Color.yellow);
      b.addActionListener(new ChangeButtonListener());
    }

	class ChangeButtonListener implements ActionListener{
	    public void actionPerformed(ActionEvent e){   ++count;
	        b.setLabel(buttonText + count);  
	    }
	}
}