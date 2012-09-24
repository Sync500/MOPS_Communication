import java.awt.*;
import java.applet.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Lieblingseis  extends Applet {

    Label   l1 = new Label("Mein Lieblingseis:");
    CheckboxGroup cg = new CheckboxGroup();
    Checkbox c1 = new Checkbox("Heidelbeere",cg,  false), 
             c2 = new Checkbox("Erdbeere",cg,  false),
             c3 = new Checkbox("Vanille",cg, false),
             c4 = new Checkbox("Schokolade",cg,  true);
    TextField t = new TextField("Schokolade", 20);

    public void init () {
      setBackground(Color.pink);
      add(l1);
      add(c1); 
      c1.addItemListener(new EisListener());
      add(c2);
      c2.addItemListener(new EisListener());
      add(c3);
      c3.addItemListener(new EisListener());
      add(c4);
      c4.addItemListener(new EisListener());
      add(t);
    }

class EisListener implements ItemListener
{
 public void itemStateChanged(ItemEvent ie)
    {   
     t.setText((String)ie.getItem());
    }
}

}