package sonosip.softphone;

import sonosip.utils.EventLogger;
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
    

    private native void nConnectServer(String user, String password, String realm);
    private native void nDisconnectServer();
    private native void nSetAccessCodeList(String[] accessCodeList);
    private native void nConnectCallerMicrophoneButton(int callId);
    private native void nDisconnectCallerMicrophoneButton(int callId);
    private native void nCancelCallerSpeakRequestButton(int callId);
    private native void nStartRetransmition();
    private native void nStopRetransmition();
    
    
	public void disconnectCallerMicrophoneButton(final int callId) {
		new Thread() {
			public void run() {
				try {
					nDisconnectCallerMicrophoneButton(callId);
				} catch (Exception e) {
					EventLogger.addError(e.getMessage());
				}
			}
		}.start();
	}

	public void connectCallerMicrophoneButton(final int callId) {
		new Thread() {
			public void run() {
				try {
					nConnectCallerMicrophoneButton(callId);
				} catch (Exception e) {
					EventLogger.addError(e.getMessage());
				}
			}
		}.start();
	}

	public void cancelCallerSpeakRequestButton(final int callId) {
		new Thread() {
			public void run() {
				try {
					nCancelCallerSpeakRequestButton(callId);
				} catch (Exception e) {
					EventLogger.addError(e.getMessage());
				}
			}
		}.start();
	}

	public void startRetransmition() {
		new Thread() {
			public void run() {
				try {
					nStartRetransmition();
				} catch (Exception e) {
					EventLogger.addError(e.getMessage());
				}
			}
		}.start();
	}

	public void stopRetransmition() {
		new Thread() {
			public void run() {
				try {
					nStopRetransmition();
				} catch (Exception e) {
					EventLogger.addError(e.getMessage());
				}
			}
		}.start();
	}

	public void reconnectServer() {
		new Thread() {
			public void run() {
				try {
//					nConnectServer();
				} catch (Exception e) {
					EventLogger.addError(e.getMessage());
				}
			}
		}.start();
	}

	
	
	
	public void addCall(int callId, String callPassword) {
		
	}
	
	public void updateCallStatus(int callId, int callStatus) {
		
	}

	public void removeCall(int callId) {
		
	}
	
	public void updateConnectionStatus(int connectionStatus) {
		
	}

}
