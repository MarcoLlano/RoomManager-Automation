package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC23: Verify a meeting cannot be created when an Out Of Order is active
 * @author Asael Calizaya
 *
 */
public class CannotCreateAMeetingWhenOutOfOrderIsActive {
	SchedulePage schedule = new SchedulePage();
	MeetingMethods meeting = new MeetingMethods();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	List<Map<String, String>> outOfOrderData = excelReader.getMapValues("OutOfOrderPlanning");

	//data from excel to create an out of order
	String roomName = outOfOrderData.get(11).get("Room Name");
	String startDate = outOfOrderData.get(11).get("Start date");
	String endDate = outOfOrderData.get(11).get("End date");
	String startTime = outOfOrderData.get(11).get("Start time (minutes to add)");
	String endTime = outOfOrderData.get(11).get("End time (minutes to add)");
	String title = outOfOrderData.get(11).get("Title");
	String description = outOfOrderData.get(11).get("Description");

	//data from excel to create a meeting
	String meetingOrganizer = meetingData.get(10).get("Organizer");
	String meetingSubject = meetingData.get(10).get("Subject");
	String meetingStartTime = meetingData.get(10).get("Start time");
	String meetingEndTime = meetingData.get(10).get("End time");
	String meetingAttendee = meetingData.get(10).get("Attendee");
	String password = meetingData.get(10).get("Password");

	@BeforeMethod
	public void createOutOfOrder() {
		meeting.createAnOutOfOrder(startDate, endDate, meetingStartTime, endTime, title, description, roomName);
	}

	@Test(groups = "FUNCTIONAL")
	public void testCannotCreateAMeetingWhenOutOfOrderIsActive() {
		HomeTabletPage home = new HomeTabletPage();
		home.clickScheduleBtn()
		.createMeeting(meetingOrganizer, meetingSubject, meetingStartTime, meetingEndTime, meetingAttendee, password);
		
		//TC23
		Assert.assertTrue(schedule.isMessageErrorPopUpDisplayed());
		
		//Files because the error message is incorrect
		Assert.assertTrue(schedule.isMessageErrorCreationMeetingPopUpDisplayed());
	}

	@AfterMethod
	public void deleteData() throws MalformedURLException, IOException {
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
