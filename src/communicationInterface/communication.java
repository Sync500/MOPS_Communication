/**
 * 
 */
package communicationInterface;

import java.io.*;

/**
 * @description: class to communicate with onBoard / Offboard
 * 
 * @author Daniel Fay
 * @version: 1.0
 *
 */
public class communication {
	/**
	 * @param args
	 */
	public String event(){
		return null;
	}
	
	/**
	 * @param message: message from data type String
	 * @throws IOException: show error message if the 
	 */
	
	public void sendMessage(String message) throws IOException{
		try{
			ByteArrayOutputStream fostream = new ByteArrayOutputStream(); // buffer of 32 bytes created
			String s = message;
			byte buf[] = s.getBytes();
				for (int i=0;i<buf.length; i++){
					System.out.println(buf[i] + "\n");
				}
			fostream.write(buf);
			System.out.println("Buffer as a string");
			System.out.println(fostream.toString());
//			byte[] b = fostream.toByteArray();
//				for (int i=0; i<b.length; i++) {
//					System.out.print((char) b[i] + "\n");
//				}
			
//			System.out.println("write to an OutputStream()");
//			OutputStream f2 = new FileOutputStream("out.txt");
//			fostream.writeTo(f2);
//			f2.close();
//			System.out.println("reseting");
//			fostream.reset();
//				for (int i=0; i<3; i++){
//					fostream.write('X');
//					System.out.println(fostream.toString());
//				}
			}catch(IOException i){
		          i.printStackTrace();
		     }
	}
	/**
	 * 
	 * @param filename: read data from
	 * @throws IOException
	 */
	
	public void showMessage(String filename) throws IOException{
		System.out.println("read content of: " + filename);
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String s = null;
		
		while((s = br.readLine()) != null){
			System.out.println("content: " + s);
		}
		br.close();
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Data data = new Data();
		data.name ="Fay";
		data.vorname = "Daniel";
		data.telefon = 1234;
		data.unsicher = true;
		
		String filename = "testfile.ser";
		
		serialize ser = new serialize();
		
		//Object create and store data in filename
		ser.SerializeFile(filename, data);
		//show Object data from filename
		ser.DeSerializeFile(filename, data);
		System.out.println(data.name);
		
//		com.sendMessage("Fay");
//		com.showMessage("out.txt");
//		
//		Checksum checksum = new Checksum();
//		if (checksum.create(filename) != 0){
//			System.out.println("chk-file create");
//		}
//		byte[] e = checksum.createChecksum(filename);
//		System.out.println(e);
		
	}
}