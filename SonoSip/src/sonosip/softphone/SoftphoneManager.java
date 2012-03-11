package sonosip.softphone;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import sonosip.Activator;
import sonosip.preferences.SipPreferencePage;
import sonosip.preferences.SoftphonePreferencePage;
import sonosip.preferences.UsersPreferencePage;
import sonosip.utils.EventLogger;
import sonosip.views.SoftphoneServerView;
import sonosip.views.SoftphoneView;


public class SoftphoneManager {

    static {
        System.loadLibrary("SonoSip"); 
    }
    
    private static SoftphoneManager instance;
    
    private int connectionStatus;
    
	private SoftphoneView softphoneView;
    private SoftphoneServerView softphoneServerView;

	public void setSoftphoneView(SoftphoneView softphoneView) {
		this.softphoneView = softphoneView;
	}

	public void setSoftphoneServerView(SoftphoneServerView softphoneServerView) {
		this.softphoneServerView = softphoneServerView;
		updateConnectionStatus(this.connectionStatus);
	}
 
    
    public static synchronized  SoftphoneManager getInstance() {
        if (null == instance) {
        	instance = new SoftphoneManager();
        	instance.initSoftphoneManager();
        }
        return instance;
    }

    private SoftphoneManager() {
    	this.connectionStatus = ConnectionStatus.DISCONNECTED;
    	
		IPropertyChangeListener preferenceListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(SipPreferencePage.USER) ||
					event.getProperty().equals(SipPreferencePage.PASSWORD) ||
					event.getProperty().equals(SipPreferencePage.REALM) ||
					event.getProperty().equals(SoftphonePreferencePage.ACTIVATE_SOFTPHONE)) {
					
					if(Activator.getDefault().getPreferenceStore().getBoolean(SoftphonePreferencePage.ACTIVATE_SOFTPHONE)) {
						connectServer();
					} else {
						nDisconnectServer();
					}
				}
				if(event.getProperty().equals(UsersPreferencePage.USER_LIST)) {
					updateSoftphoneUserList();
				}
			}
		};
		
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(preferenceListener); 

    }
    
    private void initSoftphoneManager() {

		try {		
			File rootFolder = new File(Platform.getInstanceLocation().getURL().getFile());
			if(rootFolder.exists()) {							
/**
 * TODO remetre le bon chemin
 */
				//nInitSoftphone(rootFolder.getParentFile().getAbsolutePath() + File.separator + "ext-ress");	
				nInitSoftphone("c:" + File.separator + "ext-ress");	
				
			}
		} catch (Exception e) {
			EventLogger.addError(e.getMessage());
		}	
		
		updateSoftphoneUserList();
		
		if(Activator.getDefault().getPreferenceStore().getBoolean(SoftphonePreferencePage.ACTIVATE_SOFTPHONE)) {
			connectServer();
		}
    }
    

    private native void nInitSoftphone(String ressourcesPath);
    private native void nConnectServer(String user, String password, String realm);
    private native void nDisconnectServer();
    private native void nSetAccessCodeList(String[] accessCodeList);
    private native void nConnectCallerMicrophoneButton(int callId);
    private native void nDisconnectCallerMicrophoneButton(int callId);
    private native void nCancelCallerSpeakRequestButton(int callId);
    private native void nStartRetransmition();
    private native void nStopRetransmition();
    
    
	public void disconnectCallerMicrophoneButton(final int callId) {
		nDisconnectCallerMicrophoneButton(callId);
	}

	public void connectCallerMicrophoneButton(final int callId) {
		nConnectCallerMicrophoneButton(callId);
	}

	public void cancelCallerSpeakRequestButton(final int callId) {
		nCancelCallerSpeakRequestButton(callId);
	}

	public void startRetransmition() {
		nStartRetransmition();
	}

	public void stopRetransmition() {
		nStopRetransmition();
	}

	public void reconnectServer() {
		connectServer();
	}


    
    private void updateSoftphoneUserList() {
		String userList = Activator.getDefault().getPreferenceStore().getString(UsersPreferencePage.USER_LIST);
		String[] userArray = userList.split(UsersPreferencePage.USER_SEPARATOR);
		for (int i = 0 ; i < userArray.length ; i++) {
			String[] userPassword = userArray[i].split(UsersPreferencePage.USER_PASSWORD_SEPARATOR);
			userArray[i] = userPassword[0];
		}
		nSetAccessCodeList(userArray);
    }
    
	private void connectServer() {
		String 	user 				= Activator.getDefault().getPreferenceStore().getString(SipPreferencePage.USER);
		String 	password 			= Activator.getDefault().getPreferenceStore().getString(SipPreferencePage.PASSWORD);
		String 	realm 				= Activator.getDefault().getPreferenceStore().getString(SipPreferencePage.REALM);
		if(user != null && !"".equals(user) && password != null && !"".equals(password) && realm != null && !"".equals(realm)) {				
			nConnectServer(user, password, realm);
		}
	}
	
	static public void sAddCall(int callId, String callPassword, int callStatus) {
		SoftphoneManager.getInstance().addCall(callId, callPassword, callStatus);
	}
	public void addCall(int callId, String callPassword, int callStatus) {
		if(this.softphoneView != null) {
			this.softphoneView.addCall(callId, getCallerNameByPassword(callPassword), callStatus);
		}
	}

	                   
	static public void sUpdateCallStatus(int callId, int callStatus) {
		SoftphoneManager.getInstance().updateCallStatus(callId, callStatus);
	}
	public void updateCallStatus(int callId, int callStatus) {
		if(this.softphoneView != null) {
			this.softphoneView.updateCallStatus(callId, callStatus);
		}
	}

	
	static public void sRemoveCall(int callId) {
		SoftphoneManager.getInstance().removeCall(callId);
	}
	public void removeCall(int callId) {
		if(this.softphoneView != null) {
			this.softphoneView.removeCall(callId);
		}
	}

	
	static public void sUpdateConnectionStatus(int connectionStatus) {
		SoftphoneManager.getInstance().updateConnectionStatus(connectionStatus);
	}
	public void updateConnectionStatus(int connectionStatus) {
		this.connectionStatus = connectionStatus;
		if(this.softphoneServerView != null) {
			this.softphoneServerView.updateConnectionStatus(connectionStatus);
		}
	}
	
	private String getCallerNameByPassword(String password) {
		String userList = Activator.getDefault().getPreferenceStore().getString(UsersPreferencePage.USER_LIST);
		String[] userArray = userList.split(UsersPreferencePage.USER_SEPARATOR);
		for (String user : userArray) {
			String[] userPassword = user.split(UsersPreferencePage.USER_PASSWORD_SEPARATOR);
			if(userPassword[0].equals(password)) {
				return userPassword[1];
			}
		}
		return "Utilisateur inconnu";
	}

}
