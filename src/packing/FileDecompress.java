/**
 * 
 */
package packing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * @author Syncmaster_extreme
 *
 */
public class FileDecompress {

	public static final int EOF = -1;
	static String path = "c:\\a\\";
	String ext = ".zip";
	String fout = path+"out"+ext;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	
	public static void saveEntry( ZipFile zf, ZipEntry target ) throws ZipException,IOException{
		File file = new File(path+target.getName() );
		System.out.println("Filename is: " + file);	
//		if (!file.exists()){
//			file.createNewFile();
//			System.out.println("File created");
//		}
		
		if ( target.isDirectory() )
			file.mkdirs();
		else{
			InputStream is = zf.getInputStream( target );
			BufferedInputStream bis = new BufferedInputStream( is );
			new File( file.getParent() ).mkdirs();
			FileOutputStream fos = new FileOutputStream( file );
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			for ( int j; ( j = bis.read() ) != -1; )
				bos.write(j);
				bos.close();
				fos.close();
		}
	}
	
	public static void main( String[] args ){
		FileDecompress decompress = new FileDecompress();  
		try{
		    ZipFile zf = new ZipFile(decompress.fout);
		    for ( Enumeration<? extends ZipEntry> e = zf.entries(); e.hasMoreElements(); ){
		      ZipEntry target = e.nextElement();
			  saveEntry( zf, target );
		    }
		    System.out.println( "Files unpacked" );
		  }
		  catch( FileNotFoundException e ) {
		    System.out.println( "zipfile not found" );
		  }
		  catch( ZipException e ) {
		    System.out.println( "zip error..." );
		  }
		  catch( IOException e ) {
		    System.out.println( "IO error..." );
		      }
	}

} // FileDecompress
