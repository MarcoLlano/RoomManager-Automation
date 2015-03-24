package framework.pages.admin.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import framework.common.UIMethods;
import framework.pages.admin.conferencerooms.RoomBaseAbstractPage;
import framework.selenium.SeleniumDriverManager;

/**
 * @author Marco Llano
 *
 */
public class ResourceBaseAbstractPage {
	protected WebDriver driver;
	protected WebDriverWait wait;

	@FindBy(xpath = "//div[@class='input-control text']/input[@ng-model='resource.name']") 
	WebElement resourceNameTxtBox;
	
	@FindBy(xpath = "//div[@class='input-control text']/input[@ng-model='resource.customName']") 
	WebElement resourceDisplayNameTxtBox;
	
	@FindBy(xpath = "//span[contains(text(),'Save')and@class='ng-binding']") 
	WebElement saveResourceBtn;
	
	@FindBy(xpath = "//button[@ng-click='cancel()']") 
	WebElement cancelBtn;
	
	@FindBy(xpath = "//div[@class='input-control text']/textarea[@ng-model='resource.description']") 
	WebElement resourceDescriptionTxtBox;
	
	@FindBy(id = "convert") 
	WebElement resourceOpenIconBtn;
	
	@FindBy(xpath = "//div[@class = 'row v-space ng-scope']")
	WebElement background;
	
	public ResourceBaseAbstractPage() {		
		driver = SeleniumDriverManager.getManager().getDriver();
		wait = SeleniumDriverManager.getManager().getWait();
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * [ML]This method receives a resource name, then set this value into resource name field in resource info page
	 * @param resourceName
	 * @return
	 */
	public ResourceBaseAbstractPage setResourceName(String resourceName) {
		wait.until(ExpectedConditions.visibilityOf(resourceNameTxtBox));
		resourceNameTxtBox.clear();
		resourceNameTxtBox.sendKeys(resourceName);
		return this;
	}
	
	/**
	 * [ML]This method receives a resource display name, then set this value into resource display name field in
	 *  resource info page
	 * @param resourceDisplayName
	 * @return the current page
	 */
	public ResourceBaseAbstractPage setResourceDisplayName(String resourceDisplayName) {
		resourceDisplayNameTxtBox.clear();
		resourceDisplayNameTxtBox.sendKeys(resourceDisplayName);
		return this;
	}
	
	/**
	 * [ML]This method receives a resource description, then set this value into resource description field in 
	 * resource info page
	 * @param resourceDescription
	 * @return the current page
	 */
	public ResourceBaseAbstractPage setResourceDescription(String resourceDescription) {
		resourceDescriptionTxtBox.clear();
		resourceDescriptionTxtBox.sendKeys(resourceDescription);
		return this;
	}
	
	/**
	 * [ML]This method receives a resource icon value, then set click on icon button
	 * @param iconTitle
	 * @return the current page
	 */
	public ResourceBaseAbstractPage selectResourceIcon(String iconTitle) {
		By icon = By.xpath("//button[@value='" + iconTitle +"']"); 
		wait.until(ExpectedConditions.elementToBeClickable(icon));
		driver.findElement(icon).click();
		return this;
	}
	
	/**
	 * [ML]This method receives a direction text [previous,next], then click on button belong to direction
	 * to search for an icon name.
	 * @param direction
	 * @return
	 */
	public ResourceBaseAbstractPage clickPreviusNextIconPageBtn(String direction) {
		By iconButton = By.xpath("//button[@class='btn btn-primary btn-" + direction + "']");
		driver.findElement(iconButton).click();
		return this;
	}
	
	/**
	 * [ML]This method click on found icon from selectResourceIcon method
	 * @return
	 */
	public ResourceBaseAbstractPage clickResourceIcon() {
		wait.until(ExpectedConditions.elementToBeClickable(resourceOpenIconBtn));
		resourceOpenIconBtn.click();
		return this;
	}
	
	/**
	 * [ML]This method click on save button if a resource is created or edited.
	 * @return
	 */
	public ResourcesPage clickSaveResourceBtn() {
		saveResourceBtn.click();
		UIMethods.waitForMaskDisappearsAndClickElement(background);
		return new ResourcesPage();
	}
	
	/**
	 * [ML]Return the resource icon name from in resourceInfoPage if is present
	 * @param iconTitle
	 * @return boolean if is or not present
	 */
	public boolean getResourceIcon(String iconTitle) {
		By resourceIcon = By.xpath(".//*[@id='resourcesGrid']/descendant::*/span[@class='fa " +
				iconTitle + "']");
		return UIMethods.isElementPresent(resourceIcon);
	}
	
	/**
	 * [CG]This method click on save button if a resource exists already and returns previous page.
	 * @return
	 */
	public Object clickSaveResourceWithErrorBtn() {
		saveResourceBtn.click();
		return this;
	}
	
	/**
	 * [CG]This method click on cancel button.
	 * @return
	 */
	public ResourcesPage clickCancelResourceBtn() {
		cancelBtn.click();
		UIMethods.waitForMaskDisappearsAndClickElement(background);
		return new ResourcesPage();
	}
	
	/**
	 * [CG]This method cleans resource name field in resource info page
	 * @return
	 */
	public ResourceInfoPage clearResourceName() {
		wait.until(ExpectedConditions.visibilityOf(resourceNameTxtBox));
		resourceNameTxtBox.clear();
		return new ResourceInfoPage();
	}
	
	/**
	 * [CG]This method cleans resource display name field in resource info page
	 * @return
	 */
	public ResourceInfoPage clearResourceDisplayName() {
		wait.until(ExpectedConditions.visibilityOf(resourceDisplayNameTxtBox));
		resourceDisplayNameTxtBox.clear();		
		return new ResourceInfoPage();
	}
	
	/**
	 * [CG]Method that returns true if an error message is displayed 
	 * @param message
	 * @return
	 */
	public boolean verifyErrorMessage(String message) {
		return RoomBaseAbstractPage.isErrorMessageCorrect(message);
	}
}
