package WLAN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Data implements Serializable{
	
	public String message;
	transient public String message2; //trasient var - dont used
	public int number1;
	public int number2;
	BufferedReader reader;
	InputStreamReader isr;
	
	Data() throws IOException{
		message = "Testing the Socket";
		message2 = "open session";
		number1 = 55;
		number2 = 15;
	}
	
// unused
	public String setMessage(){
		reader = new BufferedReader(isr = new InputStreamReader(System.in));
		try {
			System.out.println("Whats your Message?");
			message = reader.readLine();
			System.out.println("Message is: ");
			return message;
		} catch (IOException e) {
			return "InputStream not readably!";
		} // catch
	}
	
	public String getOBject_message() throws IOException{
		message = "Message: " + message + "\n number1: " + number1 + "\n number2: " + number2 +"";
		return message;
	}
	
	public int getObject_calculatet(){
		return number1 + number2;
	}
}
