package others;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class EisAuswahl  extends Applet {
    List  lst = new List(3, true); // 3 rows visible
                            // muliple selections possible (true)
    Label   l = new Label("Meine Eisauswahl für heute:");
    TextArea t = new TextArea(5, 20);
  
    @SuppressWarnings("deprecation")
	public void init () {
      setBackground(Color.pink);
      lst.addItem("Heidelbeere");
      lst.addItem("Erdbeere");
      lst.addItem("Vanille");
      lst.addItem("Schokolade");
      lst.addItem("Pfirsisch");
      lst.addItem("Pistazie");
      lst.addItem("Äpfel");
      add(l);
      add(lst); 
      lst.addItemListener(new EisListener());
      add(t);
    }

class EisListener implements ItemListener
{
 public void itemStateChanged(ItemEvent ie)
    {
        t.setText("");
        String[] items = lst.getSelectedItems();
        for (int i = 0; i < items.length; i++) 
               t.append(items[i] + "\n"); 
    } 

} // end of inner class

} // end of applet 
