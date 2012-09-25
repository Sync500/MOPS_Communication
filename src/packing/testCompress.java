/**
 * 
 */
package packing;

import java.io.*;
import java.util.zip.*;

/**
 * @author Syncmaster_extreme
 *
 */

public class testCompress {
	
	public void compress() throws IOException {
	// first compress inputfile.txt into out.gz
		BufferedReader in = new BufferedReader(new FileReader("c:\\f1.txt"));
		BufferedOutputStream out = new BufferedOutputStream(
				new GZIPOutputStream(new FileOutputStream("c:\\out.gz")));
		int c;
		while ( (c = in.read()) != -1){
			out.write(c);
			in.close();
			out.close();
		}
		// now decompress our new file
		@SuppressWarnings("resource")
		BufferedReader in2 = new BufferedReader(new InputStreamReader(
				new GZIPInputStream(new FileInputStream("c:\\out.gz"))));
		String s;
		while ((s = in2.readLine()) != null)
			System.out.println("s ist: " + s);
	} // compress
	
	public static void main( String[] args ){
		testCompress compress = new testCompress();
		try {
			compress.compress();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

