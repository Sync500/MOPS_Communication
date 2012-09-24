/**
 * Copyright (c)
 */
package communicationInterface;

import java.awt.*;
import java.awt.event.*;

/**
 * Class description goes here
 * class put access to Interface for communications  
 * @version 1.0 Sep. 2012
 * @author Daniel Fay
 */

public class MyInterface extends Frame implements ActionListener,TextListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1963295514547085759L;
	/**
	 * @param args
	 */	
	TextField t2;
	MyInterface(){
		super();
		setTitle("Graphical user interface (GUI)");
		setSize(250,200);
		setBackground(Color.pink);
		setLayout(new BorderLayout());
		
		// Panel
		Panel p1 = new Panel();	
		Panel p2 = new Panel();
		Panel p3 = new Panel();
		// other elements
		Label l2 = new Label("OFFBOARD");
		Label l3 = new Label("Datei: ");
		TextField t1 = new TextField("mission.txt",15);
		Label l4 = new Label("Ausgabe:");
		t2 = new TextField("",15);
		
		Button b1 = new Button("GET FILE MISSION.TXT");
		b1.setActionCommand("get");
		Button b2 = new Button("EXIT");
		b2.setActionCommand("exit");
		
		// Listener
		b1.addActionListener(this);
		b2.addActionListener(this);
		t2.addTextListener(this);
		
		// create 
		GridBagLayout gbl0 = new GridBagLayout();
		GridBagConstraints gbc0 = new GridBagConstraints();
		p1.setLayout(gbl0);
		Label l5 = new Label("");
		p1.add(l5,gbc0);
		p1.add(l2,gbc0);

		GridBagLayout gbl1 = new GridBagLayout();
		GridBagConstraints gbc1 = new GridBagConstraints();
		p2.setLayout(gbl1);
		p2.add(l3,gbc1);
		gbc1.gridwidth = GridBagConstraints.REMAINDER; // line ended
		p2.add(t1,gbc1);
		gbc1.gridwidth = GridBagConstraints.RELATIVE;
		p2.add(l4,gbc1);
		gbc1.gridwidth = GridBagConstraints.REMAINDER; // line ended
		p2.add(t2,gbc1);
		gbc1.gridwidth = GridBagConstraints.RELATIVE;
		Label l6 = new Label("");
		p2.add(l6,gbc1);
		gbc1.gridwidth = GridBagConstraints.REMAINDER; // line ended
		p2.add(b1,gbc1);
		gbc1.gridwidth = GridBagConstraints.RELATIVE;
		
		Label l7 = new Label("");
		p2.add(l7,gbc1);
		gbc1.gridwidth = GridBagConstraints.REMAINDER; // line ended
		Label l9 = new Label("ONBOARD");
		p2.add(l9,gbc1);
		
		
		p3.add(b2);
		
		add(p1,BorderLayout.NORTH);
		add(p2,BorderLayout.CENTER);
		add(p3,BorderLayout.SOUTH);
		
		ProgEnde pe = new ProgEnde(); // instance of class ProgEnde
		addWindowListener(pe);
		
		setVisible(true);
	
/**	// Panel 3
	Panel p3 = new Panel();
	Label l4 = new Label("ONBOARD"); 
	Label l5 = new Label("Datei: ");
	TextField t2 = new TextField("werte");
	Button b2 = new Button("SEND");
	
	// Panel 4
	Panel p4 = new Panel();
	Label l6 = new Label("AUSGABE");
	List list1 = new List(3, false);	
	list1.add("mission");
	list1.add("werte");
	list1.add("sonstige");
	Button b3 = new Button("SHOW");
	TextArea ta1 = new TextArea("Show the content from the file",5,54,TextArea.SCROLLBARS_VERTICAL_ONLY); 
*/

/*	// append
	p1.add(l1);
	add(p1,BorderLayout.NORTH);
	
	p2.add(l2); // OFFBOARD
	add(p2,BorderLayout.CENTER);
	GridBagLayout gbl1 = new GridBagLayout();
	GridBagConstraints gbc1 = new GridBagConstraints();
	p2.setLayout(gbl1);
	
	p2.add(l3,gbc1);
	gbc1.gridwidth = GridBagConstraints.REMAINDER; // line ended
	p2.add(t1,gbc1);
	gbc1.gridwidth = GridBagConstraints.RELATIVE;
	p2.add(l3,gbc1);
	p2.add(t1,gbc1);
	p2.add(b1);
	add(p2,BorderLayout.SOUTH);
*/
	//
	
/** p3.add(l4); // ONBOARD
	add(p3,BorderLayout.CENTER);
	GridBagLayout gbl2 = new GridBagLayout();
	GridBagConstraints gbc2 = new GridBagConstraints();
	p3.setLayout(gbl2);
	
	p3.add(l5);
	gbc2.gridwidth = GridBagConstraints.REMAINDER; // line ended
	p3.add(t2);
	gbc1.gridwidth = GridBagConstraints.RELATIVE;
	p3.add(l3,gbc2);
	p3.add(t2,gbc2);
	add(p3,BorderLayout.CENTER); 
*/
	}
	
	public void actionPerformed(ActionEvent ae){
		String vergleich = ae.getActionCommand();
		if(vergleich.equals("get")){
			t2.setText("get file mission.txt");
		}else{
			System.exit(0);
		}
	}
	
	public void textValueChanged(TextEvent e){
		//
	}
	
	
	@SuppressWarnings("unused")
	public static void main(String arg[]) {
		// TODO Auto-generated method stub
		MyInterface myInt = new MyInterface();
	}

}
class ProgEnde extends WindowAdapter{
	public void windowClosing(WindowEvent we){
		System.exit(0);
	}
}
