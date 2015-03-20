package framework.pages.tablet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.selenium.SeleniumDriverManager;
import framework.common.AppConfigConstants;
/**
 * @title  Settings
 * @author Jose Cabrera
 * @description This page allows to select a room
 */
public class SettingsPage {

	//declare the instance of Selenium Webdriver
	private WebDriver driver;
	
	@FindBy(xpath = "//button[@ng-click='saveSelectedRoom()']")
	WebElement acceptBtn;
	
	@FindBy(xpath = "//span[contains(text(),'Settings')]")
	WebElement settingsLbl;
		
	public SettingsPage() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
		driver.get(AppConfigConstants.URL_TABLET);
	}
	
	public HomePage selectRoom(String roomNum) {
		driver.findElement(By.xpath("//h4[contains(text(),'" + roomNum + "')]")).click();
		acceptBtn.click();
		return new HomePage();
	}
	
	/**
	 * [EN] This method verifies if setting title is displayed in the main window of the page.
	 * @return
	 */
	public boolean isSettingsLblDisplayed() {	
			return settingsLbl.isDisplayed();
	}	
}
