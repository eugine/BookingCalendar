package ua.eugenesokolov.interview.calendar.util.parser;

import org.joda.time.DateTime;

import ua.eugenesokolov.interview.calendar.manager.EmployeeManager;
import ua.eugenesokolov.interview.calendar.model.CalendarEvent;
import ua.eugenesokolov.interview.calendar.model.Employee;
import ua.eugenesokolov.interview.calendar.util.parser.RecordDetails.RecordDetailsType;

public class CalendarEventBuilder {
	
	private EventDetails eventDetails;
	private RequestDetails requestDetails;
	private EmployeeManager employeeManager;

	public CalendarEventBuilder(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	public void clean() {
		eventDetails = null;
		requestDetails = null;
	}
	
	public boolean isCompleted() {
		return eventDetails != null & requestDetails != null;
	}

	public void addCalendarEvent(RecordDetails details) {
		if (details.getRecordDetailsType() == RecordDetailsType.EVENT_DETAILS) {
			eventDetails = (EventDetails) details;
		} else if (details.getRecordDetailsType() == RecordDetailsType.REQUEST_DETAILS) {
			requestDetails = (RequestDetails) details;
		}
	}

	public DateTime getDateTime() {
		return requestDetails == null? null : requestDetails.getTimestamp();
	}

	public CalendarEvent build() {
		if (!isCompleted()) {
			return null;
		}
		Employee employee = employeeManager.getEmployeeById(requestDetails.getEmployeeId());
		return new CalendarEvent(eventDetails.getDate(), employee, eventDetails.getEventTime());
	}

}
