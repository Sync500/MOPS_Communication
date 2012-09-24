package communicationInterface;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class serialize {
	
	/**
	 * @description: data from mops serialize as Object and store in filename
	 * 
	 * @param filename: file store the data
	 * @param classname: serialized class 
	 * @throws IOExeption
	 */
	public void SerializeFile(String filename, Object classname) throws IOException{
		try{
	         FileOutputStream fout = new FileOutputStream(filename);
	         ObjectOutputStream out = new ObjectOutputStream(fout);
	         out.writeObject(classname);
	         out.close();
	         fout.close();
	         System.out.println("Object created");
	         return;
	      }catch(IOException i){
	    	  System.out.println("Object don't created because class or filename not exists");
	          i.printStackTrace();
	      }
	}
	
	/**
	 * @description: created Object deserialze and read data
	 * 
	 * @param filename: file store the data
	 * @param classname: serialized class 
	 * @throws IOExeption
	 */
	public void DeSerializeFile(String filename, Object classname) throws IOException{
		Object obj = null;
        try{
           FileInputStream fin = new FileInputStream(filename);
           ObjectInputStream in = new ObjectInputStream(fin);
           obj = (Object) in.readObject();
           in.close();
           fin.close();
           return;
       }catch(IOException i){
           i.printStackTrace();
       }catch(ClassNotFoundException c){
    	   System.out.println(obj + "not found");
           c.printStackTrace();
       }
	}
}
