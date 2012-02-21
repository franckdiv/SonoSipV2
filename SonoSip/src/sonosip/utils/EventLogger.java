package sonosip.utils;

import java.util.Vector;

import sonosip.views.EventView;

public class EventLogger {


    private static EventLogger instance;
	
    private EventView eventView;
    private Vector<Event> eventHolder;

    public static synchronized  EventLogger getInstance() {
        if (null == instance) {
        	instance = new EventLogger();
        }
        return instance;
    }

    private EventLogger() {
    	eventHolder = new Vector<Event>();
	}

    public static void setEventView(EventView eventView) {
    	EventLogger.getInstance().eventView = eventView;
    	for (Event event : EventLogger.getInstance().eventHolder) {
    		EventLogger.getInstance().eventView.addEvent(event);
		}
	}
    
    private void addEvent(int eventType, String message) {
    	Event event  = new Event(eventType, message);
    	if(eventView != null) {
    		eventView.addEvent(event);
    	} else {
    		eventHolder.add(event);
    	}
    }
    
	public static void addInfo(String message) {
			EventLogger.getInstance().addEvent(Event.EVENT_TYPE_INFO, message);
	}
	
	public static void addError(String message) {
		EventLogger.getInstance().addEvent(Event.EVENT_TYPE_ERROR, message);
	}
	
	public static void addAlert(String message) {
		EventLogger.getInstance().addEvent(Event.EVENT_TYPE_ALERT, message);
	}
}
