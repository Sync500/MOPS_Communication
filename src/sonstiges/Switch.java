import java.awt.*;
import java.applet.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Switch extends Applet {
    Button  b  = new Button("off"); 
    List  lst = new List(3, true); 
    Label   l = new Label("Meine Eisauswahl für heute:");
    Label   l2 = new Label("Eisauswahl:");
    TextArea t = new TextArea(5, 20);
  
    @SuppressWarnings("deprecation")
	public void init () {
      setBackground(Color.pink);
      add(l2);
      add(b);
      b.addActionListener(new SwitchListener());
      
      lst.addItem("Heidelbeere");
      lst.addItem("Erdbeere");
      lst.addItem("Vanille");
      lst.addItem("Schokolade");
      lst.addItem("Pfirsisch");
      lst.addItem("Pistazie");
      add(l);
      add(lst); 
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
}

class  SwitchListener implements ActionListener
{
    ItemListener il = new EisListener();
    public void actionPerformed(ActionEvent e)
    {   if(b.getLabel().equals("off"))
	   { lst.addItemListener(il);
	     b.setLabel("on"); }
        else 
          {   lst.removeItemListener(il);
              t.setText("");
              b.setLabel("off");
       
          }
 }
}

}