package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC10: Verify that the changes on Displayed Name of rooms are reflected in tablet app 
 * @author Ruben Blanco
 * 
 */
public class DisplayNameIsReflectedInTablet {
	
	//reading to excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	private String displayName = testData1.get(0).get("DisplayName");	  	  
	private String newDisplayName = testData1.get(0).get("NewDisplayName");	  	  
	
	@Test(groups = {"FUNCTIONAL"})
	public void testChangesInDisplayNameAreReflectedIntablet() throws InterruptedException, IOException, BiffException {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setDisplayName(newDisplayName)
			.clickSaveBtn();		
		
		String savedDisplayName = roomsPage.getRoomDisplayName(newDisplayName);
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		settingsPage.selectRoom(newDisplayName); 	   
		String tabletDisplayName = homeTabletPage.getRoomDisplayNameLbl();
		
		//Assertion for TC10
		Assert.assertEquals(savedDisplayName, tabletDisplayName);
	}
	
	@AfterClass(groups = {"FUNCTIONAL"})
	public void preconditions() throws InterruptedException, BiffException, IOException{
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(newDisplayName);
		
		//Clean room display name with default value
		roomInfoPage.setDisplayName(displayName)
			.clickSaveBtn();
		UIMethods.refresh();
	}
}
