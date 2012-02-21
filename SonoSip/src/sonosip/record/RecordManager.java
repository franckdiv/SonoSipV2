package sonosip.record;

import sonosip.views.RecordView;

public class RecordManager {


    private static RecordManager instance;
    
    private RecordView recordView;
     
    public static synchronized  RecordManager getInstance() {
        if (null == instance) {
        	instance = new RecordManager();
        }
        return instance;
    }

    public void setRecordView(RecordView recordView) {
		this.recordView = recordView;
	}
}
