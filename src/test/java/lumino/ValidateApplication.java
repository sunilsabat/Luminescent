package lumino;


import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ValidateApplication {

	public static void main(String[] args) {
	
	
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
//		To Launch URL
		driver.get("http://localhost:4200/");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		
//		To Count number of displayed cards
		List<WebElement> cards = driver.findElements(By.xpath("//div[@class='image-container']"));
		
		int actualCardCount=cards.size();
		int expectedCardCount=30;
		Assert.assertEquals("Card count is not as per expected value "+expectedCardCount+" and actual count is "+actualCardCount, expectedCardCount, actualCardCount);
		System.out.println("Card count expected value "+expectedCardCount+" and actual count is "+actualCardCount);
		
		String height = "";
//		Same height check
		for (WebElement webElement : cards) {
			String expectedHeight="200px";
			height = webElement.getCssValue("height");
			Assert.assertEquals("Card height is not as per expected value "+expectedHeight+" and actual count is "+height, expectedHeight, height);
		}
		System.out.println("All Card height is "+ height );

//	 	On click og image user must be redirected to new page
		driver.findElement(By.xpath("//div[@class='image-container']")).click();
		
		if(driver.findElement(By.xpath("//a[text()='Back']")).isDisplayed()) {
			System.out.println("user redirected successfully to pdp page by clicking card");
		}else {
			System.out.println("user failed to redirect to pdp page by clicking card");
			Assert.fail();
		}

//		Back button will redirect user back to gallery view
		driver.findElement(By.xpath("//a[text()='Back']")).click();
		if(driver.findElement(By.xpath("//div[@class='image-container']")).isDisplayed()) {
			System.out.println("user redirected successfully to gallery page by clicking back button");
		}else {
			System.out.println("user failed to redirect to gallery page by clicking back button");
			Assert.fail();
		}
		
//		Verify name in Gallery vs name in Detail page
		String galleryNumAndName=driver.findElement(By.xpath("//div[@class='info-container']/span[1]")).getText();
		driver.findElement(By.xpath("//div[@class='image-container']")).click();
		String DetailPageNumandName=driver.findElement(By.xpath("//div[@class='info-container']/span[1]")).getText();
		
		Assert.assertEquals("Name of image in gallery is not same as name in detail page Expected Value= "+galleryNumAndName+" and Actual Value= "+DetailPageNumandName
				,galleryNumAndName, DetailPageNumandName);
		
		
//		Download link should display only if height is Atleast 2000px
		int heightPDP=Integer.parseInt(driver.findElement(By.xpath("//div[@class='image-container']")).getCssValue("height").replace("px", "").trim());
		 if(heightPDP>2000 && driver.findElement(By.xpath("//span[@class='button download']")).isDisplayed() )
		 {
			 System.out.println("Download is visble only if height is greater than 2000px");
		 }else {
			 System.out.println("Download is visble even if height is less than 2000px");
			 Assert.fail("Download is visble even if height is less than 2000px");
		 }
		driver.close();
	}

}
