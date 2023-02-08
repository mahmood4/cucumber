package hooks;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ConfigReader;

public class MyHooks {

	WebDriver driver;

	@Before
	public void setup() throws IOException {
		Properties prop1 = new Properties();

		prop1.load(MyHooks.class.getClassLoader().getResourceAsStream("config.properties"));
		System.out.println("URL    "+prop1.getProperty("browserVal"));
		String browserName=prop1.getProperty("browserVal");

		//Properties prop = new ConfigReader().intializeProperties();
		driver = DriverFactory.initializeBrowser(prop1.getProperty("browserVal"));
		System.out.println("            "+prop1.getProperty("browserVal"));

		driver.get(prop1.getProperty("url"));
		
	}

	@After
	public void tearDown(Scenario scenario) {
		
		String scenarioName = scenario.getName().replaceAll(" ","_");
		
		if(scenario.isFailed()) {
			
			byte[] srcScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(srcScreenshot,"image/png", scenarioName);
		}
		
		driver.quit();
		
	
	}

}
