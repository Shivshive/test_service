package org.Automation_Service.Business;

import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import webdrivermanager.Managedrivers;

public class BusinessController {
	
	String environment;
	String browser;
	String testcasename;
	JSONObject jobj;
	JSONParser jparser;
	String teststatus = "Failed";
	RemoteWebDriver driver;
	String clientip;
	String clientport;
	String platform;
	
	public BusinessController(JSONObject model)
	{
		jobj = model ;
		environment = (String) jobj.get("environment");
		browser = (String) jobj.get("browser");
		testcasename = (String) jobj.get("testcasename");
		clientip = (String) jobj.get("ip");
		clientport = (String) jobj.get("port");
		platform = (String) jobj.get("platform");
	}
	
	public String newTest()
	{
		Managedrivers driversmanage = new Managedrivers();
		String nodeaddress = "http://"+clientip+":"+clientport+"/wd/hub";
		
		if(!browser.equalsIgnoreCase(null))
		{
			driver = driversmanage.setupDrivers(browser, platform, nodeaddress);
		}
		else
		{
			System.out.println("Browser specified is not in list --> \n1. Chrome \n2.Firefox > v45 \n.3.Internet Explorer");
		}
		
		driver.manage().window().maximize();
		driver.get("https://www.google.com");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to(environment);
		teststatus="passed";
		
		return teststatus;
		
	}

}
