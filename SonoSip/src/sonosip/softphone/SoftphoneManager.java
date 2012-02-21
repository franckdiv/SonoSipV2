package sonosip.softphone;

import sonosip.views.SoftphoneServerView;
import sonosip.views.SoftphoneView;


public class SoftphoneManager {


    private static SoftphoneManager instance;
    
	private SoftphoneView softphoneView;
    private SoftphoneServerView softphoneServerView;

    
	public void setSoftphoneView(SoftphoneView softphoneView) {
		this.softphoneView = softphoneView;
	}


	public void setSoftphoneServerView(SoftphoneServerView softphoneServerView) {
		this.softphoneServerView = softphoneServerView;
	}
 
    
    public static synchronized  SoftphoneManager getInstance() {
        if (null == instance) {
        	instance = new SoftphoneManager();
        }
        return instance;
    }
    

    private SoftphoneManager() {
    	
    }
	
	public void disconnectCallerMicrophoneButton(int callId) {
	}

	public void connectCallerMicrophoneButton(int callId) {

	}

	public void cancelCallerSpeakRequestButton(int callId) {

	}

	public void startRetransmition() {
		
	}
	
	public void stopRetransmition() {
		
	}
	
	public void reconnectServer() {
		
	}

}
