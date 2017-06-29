package webdrivermanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Managedrivers {
	
	private static final int buffer_size = 4096;

	public boolean downloaddrivers(String sourceurl, String destdir) throws IOException
	{
		boolean flag = false;
		URL url = new URL(sourceurl);
		HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
		int responsecode = httpcon.getResponseCode();
		
		if(responsecode == httpcon.HTTP_OK)
		{
			String contentype = httpcon.getContentType();
			Long contentlength = (long) httpcon.getContentLength();
			int readtimeout = httpcon.getReadTimeout();
			String filename = sourceurl.substring(sourceurl.lastIndexOf("/")+1, sourceurl.length());
			String savefileas = destdir+File.separator+filename;
			
			System.out.println("contentype "+contentype);
			System.out.println("contentlength "+contentlength);
			System.out.println("readtimeout "+readtimeout);
			System.out.println("filename "+filename);
			System.out.println("SaveFileAs "+savefileas);
			
			
			InputStream sip = httpcon.getInputStream();
			
			File file = new File(destdir);
			if(!file.exists())
			{
				file.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(savefileas);
//			OutputStream fos = new OutputStream(savefileas);
			
			byte [] buffer = new byte[buffer_size];		
			
			int counter = 0;
			int size = sip.available();
			
			System.out.println("Size is "+size);
			
			while((counter=sip.read(buffer))!= -1)
			{
//				fos.flush();
				fos.write(buffer, 0, counter);
			}
			
			sip.close();
			fos.close();
			flag = true;
			System.out.println("Download Complete.");
			
			/**
			 * Sample code for Unzipping downloaded drivers
			 * **/
			
			if((filename.substring(filename.lastIndexOf(".")+1)).equalsIgnoreCase("zip"))
			{
				Unzip unzipfile = new Unzip();
				unzipfile.unzipFile(savefileas, destdir);
			}
			
			

		}
		else
		{
			System.out.println("Download Failure "+responsecode);
		}	
		return flag;
	}
	
	public RemoteWebDriver setupDrivers(String browser, String platform, String clientnodeurl)
	{
		Boolean flag = false;
		String chromeurl = "https://chromedriver.storage.googleapis.com/2.30/chromedriver_win32.zip";
		String ieurl = "http://selenium-release.storage.googleapis.com/3.4/IEDriverServer_x64_3.4.0.zip";
		String firefoxurl = "https://github.com/mozilla/geckodriver/releases/download/v0.17.0/geckodriver-v0.17.0-arm7hf.tar.gz";
		Managedrivers drivermanage = new Managedrivers();
		String savelocation = "c:"+File.separator+"GreenArrow_Drivers";
		RemoteWebDriver driver = null;
		DesiredCapabilities cap = null;
		
		try
		{
			cap = new DesiredCapabilities();

			/**Setting drivers**/
			if(browser.equalsIgnoreCase("chrome"))
			{
				drivermanage.downloaddrivers(chromeurl, savelocation);
				System.setProperty("webdriver.chrome.driver", savelocation+"chromedriver.exe");
				cap = DesiredCapabilities.chrome();
			}
			else if(browser.equalsIgnoreCase("firefox"))
			{
				drivermanage.downloaddrivers(firefoxurl, savelocation);
				System.setProperty("webdriver.gecko.driver", savelocation+"geckodriver.exe");
				cap = DesiredCapabilities.firefox();
			}
			else if(browser.equalsIgnoreCase("iexplorer"))
			{
				drivermanage.downloaddrivers(firefoxurl, savelocation);
				System.setProperty("webdriver.gecko.driver", savelocation+"IEDriverserver.exe");
				cap = DesiredCapabilities.internetExplorer();
			}
			else
			{
				System.out.println("Driver not in list.");
			}

			/**Setting platform**/
//			cap.setCapability(CapabilityType.PLATFORM, "platform");
			cap.setPlatform(Platform.WINDOWS);
			/**Calling remote driver **/
			driver = new RemoteWebDriver(new URL(clientnodeurl),cap); 		
		}
		
		catch(Exception e)
		{
			System.out.println("Driver cannot be initialized. Values Null");
			e.printStackTrace();
		}
		
		return driver;
	}
	
	public void deleteFile(String filepath)
	{
		File file = new File(filepath);
		
		if(file.exists())
		{
			file.delete();
			System.out.println("File Deleted..");	
		}
		else
		{
			System.out.println("File does not exists..");
		}
		
	}


}
