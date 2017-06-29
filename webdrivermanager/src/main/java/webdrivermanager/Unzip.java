package webdrivermanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {
	
	private static final int buffer_size = 4096;
	
	public void unzipFile(String zipFile , String destdir) throws IOException
	{
		File unzipfiledir = new File(destdir);
		
		if(!unzipfiledir.exists())
		{
			unzipfiledir.mkdirs();
		}
		
		ZipInputStream zipinstrm =  new ZipInputStream(new FileInputStream(zipFile));
		ZipEntry entry = zipinstrm.getNextEntry();
		
		while(entry!=null){             
			
			String fileNm = entry.getName();
			
			File newFileName = new File(destdir + File.separator + fileNm);               
			
			System.out.println("Unzipped file name is : "+ newFileName.getAbsoluteFile());
			
			FileOutputStream foutstrm = new FileOutputStream(newFileName);          
			
			int lenstrm=0;
			byte [] bufferlen = new byte[buffer_size];
			
			while ((lenstrm = zipinstrm.read(bufferlen)) != -1) {
				
				foutstrm.write(bufferlen, 0, lenstrm);
			}              
			foutstrm.close();  
			entry = zipinstrm.getNextEntry();
		}      
         zipinstrm.closeEntry();
         zipinstrm.close();         
         System.out.println("Files decompressed successfully");   
		
		
	}

}
