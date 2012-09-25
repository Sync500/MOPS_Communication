/**
 * 
 */
package packing;

import java.io.*;
import java.util.zip.*;


/**
 * @author Daniel
 * @version 1.0
 *
 */
public class FileCompress {
	public static final int EOF = -1;
	static String path = "c:\\a\\";
	String ext = ".zip";
	String fout = path+"out2"+ext;
	String fint = path+"f1.txt";
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	
	public void compress() throws IOException {
		FileOutputStream f = new FileOutputStream(fout);
		CheckedOutputStream csum = new CheckedOutputStream(f, new CRC32());
		@SuppressWarnings("resource")
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(csum));
		
		out.setComment("Here is how we compressed in Java");
		// now adding files — any number with putNextEntry() method
		BufferedReader in = new BufferedReader( new FileReader(fint));
		System.out.println("FInput is " +fint);
		out.putNextEntry(new ZipEntry(fint));
		//int c;
			for (int c = in.read(); c != -1; c = in.read() ){
				out.write(c);
			}
			// printing a checksum calculated with CRC32
			System.out.println("Checksum: "+csum.getChecksum().getValue());
			in.close();
//		// Now decompress archive
//		FileInputStream fi = new FileInputStream(fout);
//		CheckedInputStream csumi = new CheckedInputStream(fi,new CRC32());
//		ZipInputStream zipI = new ZipInputStream(new BufferedInputStream(csumi));
//		ZipEntry entry;
//			while ((entry = zipI.getNextEntry()) != null) {
//				System.out.println("Extracting file "+entry);
//				int x;
//				while ( (x = zipI.read()) != -1){
//					System.out.write(x);
//					System.out.println();
//				}
//			}
//		System.out.println("Checksum extracted: "+ csumi.getChecksum().getValue());
//		zipI.close();
	}
	
	public static void main( String[] args ){
		FileCompress compress = new FileCompress();
		try {
			compress.compress();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
} // FileCompress
