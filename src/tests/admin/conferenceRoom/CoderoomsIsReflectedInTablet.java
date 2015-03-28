package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

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
 * TC09: Verify that the changes on Code of rooms are reflected in tablet app 
 * @author Ruben Blanco
 *
 */
public class CoderoomsIsReflectedInTablet {
	//reading to excel to create variables
	private ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	private List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	private String displayName = testData1.get(0).get("DisplayName");	  	  
	private String roomCode = testData1.get(0).get("Code");
	private String empty = "";
	
	@Test(groups = {"FUNCTIONAL"})
	public void testChangesInRoomCodeAreReflectedIntablet() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setRoomCode(roomCode)
			.clickCancelBtn();
		
		String savedCode = roomsPage.doubleClickOverRoomName(displayName)
			.getRoomCode();  
		roomInfoPage.clickCancelBtn();
		
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		settingsPage.selectRoom(displayName);   
		String tabletRoomCode = homeTabletPage.getRoomCodeLbl();
		
		//Assertion for TC09
		Assert.assertEquals(savedCode, tabletRoomCode);
	}
	
	@AfterClass(groups = {"FUNCTIONAL"})
	public void postcondition() {
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(displayName);
		roomInfoPage.setRoomCode(empty)
			.clickSaveBtn();
		UIMethods.refresh();
	}
}
