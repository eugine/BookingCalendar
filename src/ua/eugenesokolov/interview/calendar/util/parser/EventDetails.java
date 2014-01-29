package ua.eugenesokolov.interview.calendar.util.parser;

import org.joda.time.LocalDate;
import ua.eugenesokolov.interview.calendar.model.EventTime;

public class EventDetails implements RecordDetails {
	private LocalDate date;
	private EventTime eventTime;

	public EventDetails(LocalDate date, EventTime eventTime) {
		this.date = date;
		this.eventTime = eventTime;
	}

	public LocalDate getDate() {
		return date;
	}

	public EventTime getEventTime() {
		return eventTime;
	}

	@Override
	public RecordDetailsType getRecordDetailsType() {
		return RecordDetailsType.EVENT_DETAILS;
	}
}
