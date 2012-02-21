package sonosip.utils;

import java.util.Date;

public class Event {
	public static final int EVENT_TYPE_INFO = 1;
	public static final int EVENT_TYPE_ALERT = 2;
	public static final int EVENT_TYPE_ERROR = 3;
	
	private int eventType;
	private String message;
	private Date date;
	
	public Event(int eventType, String message) {
		this.date = new Date();
		this.eventType = eventType;
		this.message = message;
	}

	public int getEventType() {
		return eventType;
	}

	public String getMessage() {
		return message;
	}

	public Date getDate() {
		return date;
	}
	
}
