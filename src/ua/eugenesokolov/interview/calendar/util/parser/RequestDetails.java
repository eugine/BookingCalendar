package ua.eugenesokolov.interview.calendar.util.parser;

import org.joda.time.DateTime;

public class RequestDetails implements RecordDetails {

	private DateTime timestamp;
	private String employeeId;
	 
	public RequestDetails(DateTime timestamp, String employeeId) {
		this.timestamp = timestamp;
		this.employeeId = employeeId;
	}
	
	public DateTime getTimestamp() {
		return timestamp;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	@Override
	public RecordDetailsType getRecordDetailsType() {
		return RecordDetailsType.REQUEST_DETAILS;
	} 
}
