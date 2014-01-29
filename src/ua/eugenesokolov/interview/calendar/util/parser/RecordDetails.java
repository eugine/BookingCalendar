package ua.eugenesokolov.interview.calendar.util.parser;

public interface RecordDetails {
	
	public enum RecordDetailsType {
		REQUEST_DETAILS,
		EVENT_DETAILS
	}
	
	public RecordDetailsType getRecordDetailsType();

}
